// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extendedattachment;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.extendedattachment.AllExtendedAttachmentsELO;
import com.cedar.cp.dto.extendedattachment.AllImageExtendedAttachmentsELO;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentsForIdELO;
import com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentDAO;
import com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentEVO;
import com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentLocal;
import com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ExtendedAttachmentAccessor implements Serializable {

   private ExtendedAttachmentLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;


   public ExtendedAttachmentAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ExtendedAttachmentLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ExtendedAttachmentLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ExtendedAttachmentLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ExtendedAttachmentLocalHome", var2);
      }
   }

   private ExtendedAttachmentLocal getLocal(ExtendedAttachmentPK pk) throws Exception {
      ExtendedAttachmentLocal local = (ExtendedAttachmentLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public ExtendedAttachmentEVO create(ExtendedAttachmentEVO evo) throws Exception {
      ExtendedAttachmentLocal local = this.getLocalHome().create(evo);
      ExtendedAttachmentEVO newevo = local.getDetails("<UseLoadedEVOs>");
      ExtendedAttachmentPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(ExtendedAttachmentPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public ExtendedAttachmentEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof ExtendedAttachmentPK?this.getLocal((ExtendedAttachmentPK)key).getDetails(dependants):null;
   }

   public void setDetails(ExtendedAttachmentEVO evo) throws Exception {
      ExtendedAttachmentPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public ExtendedAttachmentEVO setAndGetDetails(ExtendedAttachmentEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ExtendedAttachmentPK generateKeys(ExtendedAttachmentPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllExtendedAttachmentsELO getAllExtendedAttachments() {
      ExtendedAttachmentDAO dao = new ExtendedAttachmentDAO();
      return dao.getAllExtendedAttachments();
   }

   public ExtendedAttachmentsForIdELO getExtendedAttachmentsForId(int param1) {
      ExtendedAttachmentDAO dao = new ExtendedAttachmentDAO();
      return dao.getExtendedAttachmentsForId(param1);
   }

   public AllImageExtendedAttachmentsELO getAllImageExtendedAttachments() {
      ExtendedAttachmentDAO dao = new ExtendedAttachmentDAO();
      return dao.getAllImageExtendedAttachments();
   }
}
