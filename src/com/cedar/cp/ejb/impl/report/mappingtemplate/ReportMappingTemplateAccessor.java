// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.mappingtemplate;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.report.mappingtemplate.AllReportMappingTemplatesELO;
import com.cedar.cp.dto.report.mappingtemplate.ReportMappingTemplatePK;
import com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateDAO;
import com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateEVO;
import com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateLocal;
import com.cedar.cp.ejb.impl.report.mappingtemplate.ReportMappingTemplateLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReportMappingTemplateAccessor implements Serializable {

   private ReportMappingTemplateLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;


   public ReportMappingTemplateAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ReportMappingTemplateLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ReportMappingTemplateLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ReportMappingTemplateLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ReportMappingTemplateLocalHome", var2);
      }
   }

   private ReportMappingTemplateLocal getLocal(ReportMappingTemplatePK pk) throws Exception {
      ReportMappingTemplateLocal local = (ReportMappingTemplateLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public ReportMappingTemplateEVO create(ReportMappingTemplateEVO evo) throws Exception {
      ReportMappingTemplateLocal local = this.getLocalHome().create(evo);
      ReportMappingTemplateEVO newevo = local.getDetails("<UseLoadedEVOs>");
      ReportMappingTemplatePK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(ReportMappingTemplatePK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public ReportMappingTemplateEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof ReportMappingTemplatePK?this.getLocal((ReportMappingTemplatePK)key).getDetails(dependants):null;
   }

   public void setDetails(ReportMappingTemplateEVO evo) throws Exception {
      ReportMappingTemplatePK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public ReportMappingTemplateEVO setAndGetDetails(ReportMappingTemplateEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ReportMappingTemplatePK generateKeys(ReportMappingTemplatePK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllReportMappingTemplatesELO getAllReportMappingTemplates() {
      ReportMappingTemplateDAO dao = new ReportMappingTemplateDAO();
      return dao.getAllReportMappingTemplates();
   }
}
