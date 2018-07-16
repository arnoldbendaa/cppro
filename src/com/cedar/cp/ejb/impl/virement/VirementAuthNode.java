// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:35
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.virement;

import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.virement.VirementAuthPointImpl;
import com.cedar.cp.dto.model.virement.VirementLineImpl;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.ejb.impl.dimension.StructureElementEVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class VirementAuthNode implements Serializable, Comparable {

   private StructureElementEVO mStructureElement;
   private Boolean mVirementAuthorisationRequired;
   private List mBudgetHolders;
   private List mChildren;
   private List mRequestLines;
   private VirementAuthNode mParent;


   public VirementAuthNode(StructureElementEVO structureElement, Boolean virementAuthorisationRequired, List budgetHolders, List children, VirementAuthNode parent, List requestLines) {
      this.mStructureElement = structureElement;
      this.mVirementAuthorisationRequired = virementAuthorisationRequired;
      this.mBudgetHolders = budgetHolders;
      this.mChildren = children;
      this.mParent = parent;
      this.mRequestLines = requestLines;
   }

   public List getBudgetHolders() {
      return this.mBudgetHolders;
   }

   public List getChildren() {
      return this.mChildren;
   }

   public VirementAuthNode getParent() {
      return this.mParent;
   }

   public StructureElementEVO getStructureElement() {
      return this.mStructureElement;
   }

   public Boolean getVirementAuthorisationRequired() {
      return this.mVirementAuthorisationRequired;
   }

   public void setParent(VirementAuthNode parent) {
      this.mParent = parent;
   }

   public VirementAuthNode findNode(int seid) {
      if(this.getStructureElement().getStructureElementId() == seid) {
         return this;
      } else {
         Iterator cIter = this.mChildren.iterator();

         VirementAuthNode ans;
         do {
            if(!cIter.hasNext()) {
               return null;
            }

            VirementAuthNode child = (VirementAuthNode)cIter.next();
            ans = child.findNode(seid);
         } while(ans == null);

         return ans;
      }
   }

   public boolean isAuthorisationRequired() {
      return this.mVirementAuthorisationRequired == null?(this.mParent != null?this.mParent.isAuthorisationRequired():true):this.mVirementAuthorisationRequired.booleanValue();
   }

   public List getLeaves() {
      return this.getLeaves(new ArrayList());
   }

   public List getLeaves(List l) {
      if(this.mChildren != null && !this.mChildren.isEmpty()) {
         Iterator cIter = this.mChildren.iterator();

         while(cIter.hasNext()) {
            VirementAuthNode virementAuthNode = (VirementAuthNode)cIter.next();
            virementAuthNode.getLeaves(l);
         }
      } else if(this.mStructureElement.getLeaf()) {
         l.add(this);
      }

      return l;
   }

   public VirementAuthNode findNearestRA() {
      return this.hasBudgetHolder()?this:(this.mParent != null?this.mParent.findNearestRA():null);
   }

   public VirementAuthNode findHigherRA() {
      return this.getParent() != null?this.getParent().findNearestRA():null;
   }

   public List findAllRAsForUser(UserRefImpl user) {
      ArrayList result = new ArrayList();

      for(VirementAuthNode node = this.findNearestRA(); node != null; node = node.getParent() != null?node.getParent().findNearestRA():null) {
         if(user == null || node.getBudgetHolders().contains(user)) {
            result.add(node);
         }
      }

      return result;
   }

   public boolean isParent(VirementAuthNode child) {
      for(VirementAuthNode node = child; node != null; node = node.getParent()) {
         if(node.equals(this)) {
            return true;
         }
      }

      return false;
   }

   public boolean isParentOfAll(Collection nodes) {
      Iterator VirementAuthNode = nodes.iterator();

      VirementAuthNode node;
      do {
         if(!VirementAuthNode.hasNext()) {
            return true;
         }

         node = (VirementAuthNode)VirementAuthNode.next();
      } while(this.isParent(node));

      return false;
   }

   public static VirementAuthNode findSingleRaForUser(UserRefImpl user, Collection leaves) {
      Set ras = findAllRAsForUser(user, leaves);
      Iterator raIter = ras.iterator();

      VirementAuthNode virementAuthPoint;
      do {
         if(!raIter.hasNext()) {
            return null;
         }

         virementAuthPoint = (VirementAuthNode)raIter.next();
      } while(!virementAuthPoint.isParentOfAll(leaves));

      return virementAuthPoint;
   }

   public static Set findAllRAsForUser(UserRefImpl user, Collection leaves) {
      TreeSet ras = new TreeSet();
      Iterator lIter = leaves.iterator();

      while(lIter.hasNext()) {
         VirementAuthNode node = (VirementAuthNode)lIter.next();
         ras.addAll(node.findAllRAsForUser(user));
      }

      return ras;
   }

   public boolean hasBudgetHolder() {
      return !this.mBudgetHolders.isEmpty();
   }

   public int compareTo(Object o) {
      if(o instanceof VirementAuthNode) {
         return ((VirementAuthNode)o).getStructureElement().getPosition() - this.getStructureElement().getPosition();
      } else {
         throw new IllegalArgumentException("Unexpected class in compareTo in VirementAuthNode:" + o);
      }
   }

   public static List queryNodesRequiringAuthorisation(List leaves) {
      ArrayList answer = new ArrayList();
      Iterator lIter = leaves.iterator();

      while(lIter.hasNext()) {
         VirementAuthNode node = (VirementAuthNode)lIter.next();
         if(node.isAuthorisationRequired()) {
            answer.add(node);
         }
      }

      return answer;
   }

   public List convertToAuthNodes(Set seRefs) {
      ArrayList nodes = new ArrayList();
      Iterator seIter = seRefs.iterator();

      while(seIter.hasNext()) {
         StructureElementRefImpl structureElementRef = (StructureElementRefImpl)seIter.next();
         VirementAuthNode node = this.findNode(structureElementRef.getStructureElementPK().getStructureElementId());
         if(node == null) {
            throw new IllegalStateException("Failed to find VirementAuthNode for element:" + structureElementRef.getStructureElementPK().getStructureElementId());
         }

         nodes.add(node);
      }

      return nodes;
   }

   public List convertToAuthNodes(Collection virementLines) {
      ArrayList answer = new ArrayList();
      Iterator i = virementLines.iterator();

      while(i.hasNext()) {
         VirementLineImpl virementLine = (VirementLineImpl)i.next();
         VirementAuthNode node = this.findNode(virementLine.getBudgetLocationId());
         if(node == null) {
            throw new IllegalStateException("Failed to find VirementAuthNode for element:" + virementLine.getBudgetLocationId());
         }

         answer.add(node);
      }

      return answer;
   }

   public List convertAuthPointToAuthNodes(Collection authPoints) {
      ArrayList answer = new ArrayList();
      Iterator apIter = authPoints.iterator();

      while(apIter.hasNext()) {
         VirementAuthPointImpl authPoint = (VirementAuthPointImpl)apIter.next();
         answer.add(this.findNode(authPoint.getRAElement().getStructureElementPK().getStructureElementId()));
      }

      return answer;
   }

   public VirementAuthNode findHigherRA(Collection ras, Collection lines) {
      VirementAuthNode newRa;
      for(newRa = findSingleRaForUser((UserRefImpl)null, lines); newRa != null && !newRa.isParentOfAll(ras); newRa = newRa.findHigherRA()) {
         ;
      }

      return newRa;
   }

   public List getRequestLines() {
      return this.mRequestLines;
   }

   public void setRequestLines(List requestLines) {
      this.mRequestLines = requestLines;
   }
}
