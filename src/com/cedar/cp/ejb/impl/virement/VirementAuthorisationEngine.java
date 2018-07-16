// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:35
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.virement;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.virement.VirementAuthPointImpl;
import com.cedar.cp.dto.model.virement.VirementGroupImpl;
import com.cedar.cp.dto.model.virement.VirementLineImpl;
import com.cedar.cp.dto.model.virement.VirementRequestImpl;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementEVO;
import com.cedar.cp.ejb.impl.virement.VirementAuthNode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

public class VirementAuthorisationEngine extends AbstractDAO {

   private static final String LOAD_AUTH_HIERARCHY_SQL = "select distinct ptr.*, bu.user_id, bu.name, bu.virement_auth_required from ( \tselect bu.structure_element_id, u.user_id, u.name, \tdecode( ra.virement_auth_status, 1, \'Y\', 2, \'N\', null ) as virement_auth_required \tfrom usr u, \t     budget_user bu, \t     responsibility_area ra \twhere u.user_id = bu.user_id and \t      bu.model_id = ra.model_id (+) and \t      bu.structure_element_id = ra.structure_element_id (+) and          bu.read_only != \'Y\' and \t      bu.model_id = ? ) bu, ( \tselect se.* \tfrom structure_element se \tconnect by structure_element_id = prior parent_id \tstart with structure_element_id in \t\t( select distinct se.structure_element_id as target_id \t\t  from structure_element se, virement_request vr, virement_request_group vrg, \t\t\t   virement_request_line vrl \t\t  where vr.request_id = ? and \t\t\t\tvr.request_id = vrg.request_id and \t\t\t\tvrg.request_group_id = vrl.request_group_id and \t\t\t\tse.structure_element_id = vrl.dim0 ) ) ptr where ptr.structure_element_id = bu.structure_element_id (+)";
   private VirementRequestImpl mRequest;
   private Logger mLog = Logger.getLogger(VirementAuthNode.class);


   public VirementAuthorisationEngine(VirementRequestImpl request) {
      this.mRequest = request;
   }

   public boolean selectAuthorisers() throws ValidationException {
      if(!this.isAuthorisationEnabled()) {
         return false;
      } else {
         VirementAuthNode root = this.queryAuthorisationHierarchy(this.getRequest().getModelId(), this.getRequest().getRequestId());
         List allLeavesRequiringAuthorisation = VirementAuthNode.queryNodesRequiringAuthorisation(root.getLeaves());
         Iterator apIter;
         if(!allLeavesRequiringAuthorisation.isEmpty()) {
            VirementRequestImpl allAuthorised = this.getRequest();
            apIter = allLeavesRequiringAuthorisation.iterator();

            while(apIter.hasNext()) {
               VirementAuthNode authPoint = (VirementAuthNode)apIter.next();
               VirementAuthNode raNode = authPoint.findNearestRA();
               if(raNode == null) {
                  throw new ValidationException("No available authorisers for " + authPoint.getStructureElement().getVisId());
               }

               VirementAuthPointImpl authPoint1 = allAuthorised.addToAuthPoint((StructureElementRefImpl)raNode.getStructureElement().getEntityRef(), allAuthorised.getLinesForBudgetLocation(authPoint.getStructureElement().getStructureElementId()));
               authPoint1.getAvailableAuthorisers().addAll(raNode.getBudgetHolders());
            }

            this.mLog.debug("Choosing authorisers for request:");
            this.mLog.debug(this.getRequest().toString());

            boolean apIter1;
            do {
               apIter1 = false;
               if(this.applyBusinessRuleOne(root)) {
                  apIter1 = true;
               }

               if(this.applyBusinessRuleTwo(root)) {
                  apIter1 = true;
               }

               if(this.applyBusinessRuleThree(root)) {
                  apIter1 = true;
               }
            } while(apIter1);
         }

         boolean allAuthorised1 = true;
         apIter = this.getRequest().getAuthorisationPoints().values().iterator();

         while(apIter.hasNext()) {
            VirementAuthPointImpl authPoint2 = (VirementAuthPointImpl)apIter.next();
            if(authPoint2.getAvailableAuthorisers().contains(this.getOwnerRef())) {
               authPoint2.setAuthUser(this.getOwnerRef());
               authPoint2.setStatus(1);
               authPoint2.setNotes("Automatic authorisation by owner.");
            } else {
               authPoint2.setAuthUser((UserRefImpl)null);
               authPoint2.setStatus(0);
               authPoint2.setNotes("");
               allAuthorised1 = false;
            }
         }

         this.getRequest().setRequestStatus(allAuthorised1?2:1);
         this.mLog.debug("Selected:");
         this.mLog.debug(this.getRequest().toString());
         return this.getRequest().getRequestStatus() == 1;
      }
   }

   private boolean applyBusinessRuleOne(VirementAuthNode root) throws ValidationException {
      boolean changesMade = false;
      VirementAuthPointImpl authPoint;
      if(this.getAuthRuleOne()) {
         this.mLog.debug("Applying business rule one:");
         VirementAuthNode apIter = VirementAuthNode.findSingleRaForUser(this.getOwnerRef(), root.convertToAuthNodes(this.getRequest().getAllAuthPointSERefs()));
         if(apIter != null) {
            this.mLog.debug("\tFound single ra for whole request:" + apIter.getStructureElement().getEntityRef());
            if(this.getRequest().getAuthorisationPoints().size() != 1 || this.getRequest().getAuthPoint((StructureElementRefImpl)apIter.getStructureElement().getEntityRef()) == null) {
               authPoint = this.getRequest().addToAuthPoint((StructureElementRefImpl)apIter.getStructureElement().getEntityRef(), this.getRequest().getAllLines());
               authPoint.getAvailableAuthorisers().addAll(apIter.getBudgetHolders());
               changesMade = true;
               this.mLog.debug("\t*** Assigned all lines to ra:" + authPoint.getRAElement());
            }
         }
      } else {
         this.mLog.debug("Applying inverse of business rule one:");
         Iterator apIter1 = (new HashMap(this.getRequest().getAuthorisationPoints())).values().iterator();

         while(apIter1.hasNext()) {
            authPoint = (VirementAuthPointImpl)apIter1.next();
            VirementAuthNode authNode = root.findNode(authPoint.getRAElement().getStructureElementPK().getStructureElementId());
            VirementAuthNode newAuthNode = authNode;

            while(newAuthNode.getBudgetHolders() != null && this.getOwnerRef() != null && newAuthNode.getBudgetHolders().size() == 1 && newAuthNode.getBudgetHolders().contains(this.getOwnerRef())) {
               if(newAuthNode.getParent() == null) {
                  throw new ValidationException("No alternate authoriser for responsibility area:" + authNode.getStructureElement().getVisId());
               }

               newAuthNode = newAuthNode.getParent().findNearestRA();
               if(newAuthNode == null) {
                  throw new ValidationException("No alternate authoriser for responsibility area:" + authNode.getStructureElement().getVisId());
               }
            }

            if(newAuthNode != authNode) {
               StructureElementRefImpl seRef = (StructureElementRefImpl)newAuthNode.getStructureElement().getEntityRef();
               authPoint = this.getRequest().addToAuthPoint(seRef, new ArrayList(authPoint.getLines()));
               authPoint.getAvailableAuthorisers().addAll(newAuthNode.getBudgetHolders());
               changesMade = true;
               this.mLog.debug("\t*** Selected new ra:" + seRef + " since existing ra only contained issuer as budget holder");
            }

            if(authPoint.getAvailableAuthorisers().contains(this.getOwnerRef())) {
               authPoint.getAvailableAuthorisers().remove(this.getOwnerRef());
               changesMade = true;
            }
         }
      }

      return changesMade;
   }

   private boolean applyBusinessRuleTwo(VirementAuthNode root) throws ValidationException {
      boolean changesMade = false;
      if(this.getAuthRuleTwo()) {
         this.mLog.debug("Applying business rule two:");
         Iterator gIter = this.getRequest().getVirementGroups().iterator();

         while(gIter.hasNext()) {
            VirementGroupImpl group = (VirementGroupImpl)gIter.next();
            Set ras = this.getRequest().queryAuthPointsForGroup(group);
            if(ras.size() != 1) {
               this.mLog.debug("\tGroup has more than one resp area");
               VirementAuthNode newAuthNode = root.findHigherRA(root.convertAuthPointToAuthNodes(ras), root.convertToAuthNodes((Collection)group.getRows()));
               if(newAuthNode != null) {
                  VirementAuthPointImpl point = this.getRequest().addToAuthPoint((StructureElementRefImpl)newAuthNode.getStructureElement().getEntityRef(), group.getRows());
                  point.getAvailableAuthorisers().addAll(newAuthNode.getBudgetHolders());
                  changesMade = true;
                  this.mLog.debug("\t*** Changed all lines in group to user resp area:" + point.getRAElement());
               }
            }
         }
      }

      return changesMade;
   }

   private boolean applyBusinessRuleThree(VirementAuthNode root) throws ValidationException {
      boolean changesMade = false;
      if(this.getAuthRuleThree()) {
         this.mLog.debug("Checking authRuleThree");
         Iterator lIter = this.getRequest().getAllLines().iterator();

         while(lIter.hasNext()) {
            VirementLineImpl line = (VirementLineImpl)lIter.next();
            VirementAuthNode lineNode = root.findNode(line.getBudgetLocationId());
            List userRAs = lineNode.findAllRAsForUser(this.getOwnerRef());
            if(!userRAs.isEmpty()) {
               VirementAuthNode userRaNode = (VirementAuthNode)userRAs.get(0);
               this.mLog.debug("\tUsers Ra:" + userRaNode.getStructureElement().getEntityRef());
               VirementAuthPointImpl authPoint = (VirementAuthPointImpl)this.getRequest().queryAuthPointForLine(line);
               if(authPoint != null) {
                  VirementAuthNode authNode = root.findNode(authPoint.getRAElement().getStructureElementPK().getStructureElementId());
                  this.mLog.debug("\tCurrent Ra:" + authNode.getStructureElement().getEntityRef());

                  VirementAuthNode newAuthNode;
                  for(newAuthNode = authNode; newAuthNode != null && userRaNode.isParent(newAuthNode) && newAuthNode != userRaNode; newAuthNode = newAuthNode.findHigherRA()) {
                     ;
                  }

                  if(newAuthNode != null && newAuthNode != authNode) {
                     this.mLog.debug("\t*** Changed Ra to :" + newAuthNode.getStructureElement().getEntityRef());
                     VirementAuthPointImpl newAuthPoint = this.getRequest().addToAuthPoint((StructureElementRefImpl)newAuthNode.getStructureElement().getEntityRef(), line);
                     newAuthPoint.getAvailableAuthorisers().addAll(newAuthNode.getBudgetHolders());
                     changesMade = true;
                  }
               }
            }
         }
      }

      return changesMade;
   }

   public boolean isAuthorisationEnabled() {
      return this.getSystemProperty("VIRE: AUTHORISATION_REQUIRED", true);
   }

   private boolean getSystemProperty(String property, boolean defaultValue) {
      boolean var3;
      try {
         var3 = SystemPropertyHelper.queryBooleanSystemProperty(this.getConnection(), property, defaultValue);
      } finally {
         this.closeConnection();
      }

      return var3;
   }

   public boolean getAuthRuleOne() {
      return this.getSystemProperty("VIRE: AUTH_RULE_1", false);
   }

   public boolean getAuthRuleTwo() {
      return this.getSystemProperty("VIRE: AUTH_RULE_2", true);
   }

   public boolean getAuthRuleThree() {
      return this.getSystemProperty("VIRE: AUTH_RULE_3", true);
   }

   public String getEntityName() {
      return "VirementAuthorisationEngine";
   }

   private VirementAuthNode queryAuthorisationHierarchy(int modelId, int virementRequestId) {
      PreparedStatement ps = null;
      ResultSet rs = null;

      VirementAuthNode node1;
      try {
         ps = this.getConnection().prepareStatement("select distinct ptr.*, bu.user_id, bu.name, bu.virement_auth_required from ( \tselect bu.structure_element_id, u.user_id, u.name, \tdecode( ra.virement_auth_status, 1, \'Y\', 2, \'N\', null ) as virement_auth_required \tfrom usr u, \t     budget_user bu, \t     responsibility_area ra \twhere u.user_id = bu.user_id and \t      bu.model_id = ra.model_id (+) and \t      bu.structure_element_id = ra.structure_element_id (+) and          bu.read_only != \'Y\' and \t      bu.model_id = ? ) bu, ( \tselect se.* \tfrom structure_element se \tconnect by structure_element_id = prior parent_id \tstart with structure_element_id in \t\t( select distinct se.structure_element_id as target_id \t\t  from structure_element se, virement_request vr, virement_request_group vrg, \t\t\t   virement_request_line vrl \t\t  where vr.request_id = ? and \t\t\t\tvr.request_id = vrg.request_id and \t\t\t\tvrg.request_group_id = vrl.request_group_id and \t\t\t\tse.structure_element_id = vrl.dim0 ) ) ptr where ptr.structure_element_id = bu.structure_element_id (+)");
         ps.setInt(1, modelId);
         ps.setInt(2, virementRequestId);
         rs = ps.executeQuery();

         HashMap e;
         VirementAuthNode i;
         for(e = new HashMap(); rs.next(); e.put(new Integer(i.getStructureElement().getStructureElementId()), i)) {
            StructureElementEVO root = new StructureElementEVO(rs.getInt("structure_id"), rs.getInt("structure_element_id"), rs.getString("vis_id"), rs.getString("description"), rs.getInt("parent_id"), rs.getInt("child_index"), rs.getInt("depth"), rs.getInt("position"), rs.getInt("end_position"), rs.getString("leaf").equalsIgnoreCase("Y"), rs.getString("is_credit").equalsIgnoreCase("Y"), rs.getString("disabled").equalsIgnoreCase("Y"), rs.getString("not_plannable").equalsIgnoreCase("Y"), -1, (String)null, (Timestamp)null);
            i = (VirementAuthNode)e.get(new Integer(root.getStructureElementId()));
            Boolean node = rs.getString("virement_auth_required") != null?(rs.getString("virement_auth_required").equalsIgnoreCase("Y")?Boolean.TRUE:Boolean.FALSE):null;
            if(i == null) {
               i = new VirementAuthNode(root, node, new ArrayList(), new ArrayList(), (VirementAuthNode)null, this.getRequest().getLinesForBudgetLocation(root.getStructureElementId()));
            }

            if(rs.getString("name") != null) {
               i.getBudgetHolders().add(new UserRefImpl(new UserPK(rs.getInt("user_id")), rs.getString("name")));
            }
         }

         VirementAuthNode root1 = null;
         Iterator i1 = e.values().iterator();

         while(i1.hasNext()) {
            node1 = (VirementAuthNode)i1.next();
            if(node1.getStructureElement().getParentId() != 0) {
               VirementAuthNode parent = (VirementAuthNode)e.get(new Integer(node1.getStructureElement().getParentId()));
               parent.getChildren().add(node1);
               node1.setParent(parent);
            } else {
               root1 = node1;
            }
         }

         node1 = root1;
      } catch (SQLException var13) {
         throw this.handleSQLException("Failed to load authorisation hierarchy", var13);
      } finally {
         this.closeResultSet(rs);
         this.closeStatement(ps);
         this.closeConnection();
      }

      return node1;
   }

   private UserRefImpl getOwnerRef() {
      return (UserRefImpl)this.mRequest.getOwningUserRef();
   }

   private VirementRequestImpl getRequest() {
      return this.mRequest;
   }
}
