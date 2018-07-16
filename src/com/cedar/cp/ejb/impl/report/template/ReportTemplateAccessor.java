// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.template;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.report.template.AllReportTemplatesELO;
import com.cedar.cp.dto.report.template.ReportTemplatePK;
import com.cedar.cp.ejb.impl.report.template.ReportTemplateDAO;
import com.cedar.cp.ejb.impl.report.template.ReportTemplateEVO;
import com.cedar.cp.ejb.impl.report.template.ReportTemplateLocal;
import com.cedar.cp.ejb.impl.report.template.ReportTemplateLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReportTemplateAccessor implements Serializable {

   private ReportTemplateLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;


   public ReportTemplateAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ReportTemplateLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ReportTemplateLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ReportTemplateLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ReportTemplateLocalHome", var2);
      }
   }

   private ReportTemplateLocal getLocal(ReportTemplatePK pk) throws Exception {
      ReportTemplateLocal local = (ReportTemplateLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public ReportTemplateEVO create(ReportTemplateEVO evo) throws Exception {
      ReportTemplateLocal local = this.getLocalHome().create(evo);
      ReportTemplateEVO newevo = local.getDetails("<UseLoadedEVOs>");
      ReportTemplatePK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(ReportTemplatePK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public ReportTemplateEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof ReportTemplatePK?this.getLocal((ReportTemplatePK)key).getDetails(dependants):null;
   }

   public void setDetails(ReportTemplateEVO evo) throws Exception {
      ReportTemplatePK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public ReportTemplateEVO setAndGetDetails(ReportTemplateEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ReportTemplatePK generateKeys(ReportTemplatePK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllReportTemplatesELO getAllReportTemplates() {
      ReportTemplateDAO dao = new ReportTemplateDAO();
      return dao.getAllReportTemplates();
   }
}
