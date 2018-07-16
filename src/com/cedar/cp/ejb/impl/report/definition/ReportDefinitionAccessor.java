// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.definition;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.report.definition.AllPublicReportByTypeELO;
import com.cedar.cp.dto.report.definition.AllReportDefCalcByCCDeploymentIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefCalcByModelIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefCalcByReportTemplateIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefFormcByModelIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefFormcByReportTemplateIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefMappedExcelcByModelIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefMappedExcelcByReportTemplateIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefSummaryCalcByCCDeploymentIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefSummaryCalcByModelIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefSummaryCalcByReportTemplateIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefinitionsELO;
import com.cedar.cp.dto.report.definition.CheckFormIsUsedELO;
import com.cedar.cp.dto.report.definition.ReportDefinitionCK;
import com.cedar.cp.dto.report.definition.ReportDefinitionForVisIdELO;
import com.cedar.cp.dto.report.definition.ReportDefinitionPK;
import com.cedar.cp.ejb.impl.report.definition.ReportDefCalculatorDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefFormDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefMappedExcelDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefSummaryCalcDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionEVO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionLocal;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReportDefinitionAccessor implements Serializable {

   private ReportDefinitionLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_REPORT_FORM = "<0>";
   public static final String GET_REPORT_MAPPED_EXCEL = "<1>";
   public static final String GET_REPORT_CALCULATOR = "<2>";
   public static final String GET_SUMMARY_CALC_REPORT = "<3>";
   public static final String GET_ALL_DEPENDANTS = "<0><1><2><3>";


   public ReportDefinitionAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ReportDefinitionLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ReportDefinitionLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ReportDefinitionLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ReportDefinitionLocalHome", var2);
      }
   }

   private ReportDefinitionLocal getLocal(ReportDefinitionPK pk) throws Exception {
      ReportDefinitionLocal local = (ReportDefinitionLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public ReportDefinitionEVO create(ReportDefinitionEVO evo) throws Exception {
      ReportDefinitionLocal local = this.getLocalHome().create(evo);
      ReportDefinitionEVO newevo = local.getDetails("<UseLoadedEVOs>");
      ReportDefinitionPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(ReportDefinitionPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public ReportDefinitionEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof ReportDefinitionCK) {
         ReportDefinitionPK pk = ((ReportDefinitionCK)key).getReportDefinitionPK();
         return this.getLocal(pk).getDetails((ReportDefinitionCK)key, dependants);
      } else {
         return key instanceof ReportDefinitionPK?this.getLocal((ReportDefinitionPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(ReportDefinitionEVO evo) throws Exception {
      ReportDefinitionPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public ReportDefinitionEVO setAndGetDetails(ReportDefinitionEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ReportDefinitionPK generateKeys(ReportDefinitionPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllReportDefinitionsELO getAllReportDefinitions() {
      ReportDefinitionDAO dao = new ReportDefinitionDAO();
      return dao.getAllReportDefinitions();
   }

   public AllPublicReportByTypeELO getAllPublicReportByType(int param1) {
      ReportDefinitionDAO dao = new ReportDefinitionDAO();
      return dao.getAllPublicReportByType(param1);
   }

   public ReportDefinitionForVisIdELO getReportDefinitionForVisId(String param1) {
      ReportDefinitionDAO dao = new ReportDefinitionDAO();
      return dao.getReportDefinitionForVisId(param1);
   }

   public AllReportDefFormcByReportTemplateIdELO getAllReportDefFormcByReportTemplateId(int param1) {
      ReportDefFormDAO dao = new ReportDefFormDAO();
      return dao.getAllReportDefFormcByReportTemplateId(param1);
   }

   public AllReportDefFormcByModelIdELO getAllReportDefFormcByModelId(int param1) {
      ReportDefFormDAO dao = new ReportDefFormDAO();
      return dao.getAllReportDefFormcByModelId(param1);
   }

   public CheckFormIsUsedELO getCheckFormIsUsed(int param1) {
      ReportDefFormDAO dao = new ReportDefFormDAO();
      return dao.getCheckFormIsUsed(param1);
   }

   public AllReportDefMappedExcelcByReportTemplateIdELO getAllReportDefMappedExcelcByReportTemplateId(int param1) {
      ReportDefMappedExcelDAO dao = new ReportDefMappedExcelDAO();
      return dao.getAllReportDefMappedExcelcByReportTemplateId(param1);
   }

   public AllReportDefMappedExcelcByModelIdELO getAllReportDefMappedExcelcByModelId(int param1) {
      ReportDefMappedExcelDAO dao = new ReportDefMappedExcelDAO();
      return dao.getAllReportDefMappedExcelcByModelId(param1);
   }

   public AllReportDefCalcByCCDeploymentIdELO getAllReportDefCalcByCCDeploymentId(int param1) {
      ReportDefCalculatorDAO dao = new ReportDefCalculatorDAO();
      return dao.getAllReportDefCalcByCCDeploymentId(param1);
   }

   public AllReportDefCalcByReportTemplateIdELO getAllReportDefCalcByReportTemplateId(int param1) {
      ReportDefCalculatorDAO dao = new ReportDefCalculatorDAO();
      return dao.getAllReportDefCalcByReportTemplateId(param1);
   }

   public AllReportDefCalcByModelIdELO getAllReportDefCalcByModelId(int param1) {
      ReportDefCalculatorDAO dao = new ReportDefCalculatorDAO();
      return dao.getAllReportDefCalcByModelId(param1);
   }

   public AllReportDefSummaryCalcByCCDeploymentIdELO getAllReportDefSummaryCalcByCCDeploymentId(int param1) {
      ReportDefSummaryCalcDAO dao = new ReportDefSummaryCalcDAO();
      return dao.getAllReportDefSummaryCalcByCCDeploymentId(param1);
   }

   public AllReportDefSummaryCalcByReportTemplateIdELO getAllReportDefSummaryCalcByReportTemplateId(int param1) {
      ReportDefSummaryCalcDAO dao = new ReportDefSummaryCalcDAO();
      return dao.getAllReportDefSummaryCalcByReportTemplateId(param1);
   }

   public AllReportDefSummaryCalcByModelIdELO getAllReportDefSummaryCalcByModelId(int param1) {
      ReportDefSummaryCalcDAO dao = new ReportDefSummaryCalcDAO();
      return dao.getAllReportDefSummaryCalcByModelId(param1);
   }

	public AllReportDefinitionsELO getAllReportDefinitionsForLoggedUser(int userId) {
		ReportDefinitionDAO dao = new ReportDefinitionDAO();
	    return dao.getAllReportDefinitionsForLoggedUser(userId);
	}
}
