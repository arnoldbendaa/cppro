// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.dimension.AllDisabledLeafELO;
import com.cedar.cp.dto.dimension.AllDisabledLeafandNotPlannableELO;
import com.cedar.cp.dto.dimension.AllLeafStructureElementsELO;
import com.cedar.cp.dto.dimension.AllNotPlannableELO;
import com.cedar.cp.dto.dimension.AllStructureElementsELO;
import com.cedar.cp.dto.dimension.BudgetHierarchyElementELO;
import com.cedar.cp.dto.dimension.BudgetLocationElementForModelELO;
import com.cedar.cp.dto.dimension.CheckLeafELO;
import com.cedar.cp.dto.dimension.ChildrenForParentELO;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.dimension.LeafPlannableBudgetLocationsForModelELO;
import com.cedar.cp.dto.dimension.LeavesForParentELO;
import com.cedar.cp.dto.dimension.MassStateImmediateChildrenELO;
import com.cedar.cp.dto.dimension.ReportChildrenForParentToRelativeDepthELO;
import com.cedar.cp.dto.dimension.ReportLeavesForParentELO;
import com.cedar.cp.dto.dimension.RespAreaImmediateChildrenELO;
import com.cedar.cp.dto.dimension.StructureElementByVisIdELO;
import com.cedar.cp.dto.dimension.StructureElementELO;
import com.cedar.cp.dto.dimension.StructureElementForIdsELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementParentsELO;
import com.cedar.cp.dto.dimension.StructureElementParentsFromVisIdELO;
import com.cedar.cp.dto.dimension.StructureElementParentsReversedELO;
import com.cedar.cp.dto.dimension.StructureElementValuesELO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementEVO;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;

public class StructureElementAccessor implements Serializable {

   private Hashtable mDAOs = new Hashtable();
   private transient InitialContext mInitialContext;


   public StructureElementAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private StructureElementDAO getDAO(StructureElementPK pk) throws Exception {
      StructureElementDAO dao = (StructureElementDAO)this.mDAOs.get(pk);
      if(dao == null) {
         dao = new StructureElementDAO();
         this.mDAOs.put(pk, dao);
      }

      return dao;
   }

   public StructureElementEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof StructureElementPK?this.getDAO((StructureElementPK)key).getDetails((StructureElementPK)key, dependants):null;
   }

   public AllStructureElementsELO getAllStructureElements(int param1) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getAllStructureElements(param1);
   }

   public AllLeafStructureElementsELO getAllLeafStructureElements(int param1) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getAllLeafStructureElements(param1);
   }

   public LeafPlannableBudgetLocationsForModelELO getLeafPlannableBudgetLocationsForModel(int param1) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getLeafPlannableBudgetLocationsForModel(param1);
   }

   public StructureElementParentsELO getStructureElementParents(int param1, int param2) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getStructureElementParents(param1, param2);
   }

   public StructureElementParentsReversedELO getStructureElementParentsReversed(int param1, int param2) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getStructureElementParentsReversed(param1, param2);
   }

   public StructureElementParentsFromVisIdELO getStructureElementParentsFromVisId(String param1, int param2) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getStructureElementParentsFromVisId(param1, param2);
   }

   public ImmediateChildrenELO getImmediateChildren(int param1, int param2) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getImmediateChildren(param1, param2);
   }

   public MassStateImmediateChildrenELO getMassStateImmediateChildren(int param1, int param2) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getMassStateImmediateChildren(param1, param2);
   }

   public StructureElementValuesELO getStructureElementValues(int param1, int param2) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getStructureElementValues(param1, param2);
   }

   public CheckLeafELO getCheckLeaf(int param1) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getCheckLeaf(param1);
   }

   public StructureElementELO getStructureElement(int param1) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getStructureElement(param1);
   }

   public StructureElementForIdsELO getStructureElementForIds(int param1, int param2) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getStructureElementForIds(param1, param2);
   }

   public StructureElementByVisIdELO getStructureElementByVisId(String param1, int param2) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getStructureElementByVisId(param1, param2);
   }

   public RespAreaImmediateChildrenELO getRespAreaImmediateChildren(int param1, int param2) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getRespAreaImmediateChildren(param1, param2);
   }

   public AllDisabledLeafELO getAllDisabledLeaf(int param1) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getAllDisabledLeaf(param1);
   }

   public AllNotPlannableELO getAllNotPlannable(int param1) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getAllNotPlannable(param1);
   }

   public AllDisabledLeafandNotPlannableELO getAllDisabledLeafandNotPlannable(int param1) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getAllDisabledLeafandNotPlannable(param1);
   }

   public LeavesForParentELO getLeavesForParent(int param1, int param2, int param3, int param4, int param5) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getLeavesForParent(param1, param2, param3, param4, param5);
   }

   public ChildrenForParentELO getChildrenForParent(int param1, int param2, int param3, int param4, int param5) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getChildrenForParent(param1, param2, param3, param4, param5);
   }

   public ReportLeavesForParentELO getReportLeavesForParent(int param1, int param2, int param3, int param4, int param5) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getReportLeavesForParent(param1, param2, param3, param4, param5);
   }

   public ReportChildrenForParentToRelativeDepthELO getReportChildrenForParentToRelativeDepth(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getReportChildrenForParentToRelativeDepth(param1, param2, param3, param4, param5, param6, param7, param8);
   }

   public BudgetHierarchyElementELO getBudgetHierarchyElement(int param1) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getBudgetHierarchyElement(param1);
   }

   public BudgetLocationElementForModelELO getBudgetLocationElementForModel(int param1, int param2) {
      StructureElementDAO dao = new StructureElementDAO();
      return dao.getBudgetLocationElementForModel(param1, param2);
   }
}
