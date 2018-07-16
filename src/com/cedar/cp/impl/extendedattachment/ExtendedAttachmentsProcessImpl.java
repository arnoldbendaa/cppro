// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.extendedattachment;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentEditorSession;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentsProcess;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import com.cedar.cp.ejb.api.extendedattachment.ExtendedAttachmentEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.extendedattachment.ExtendedAttachmentEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class ExtendedAttachmentsProcessImpl extends BusinessProcessImpl implements ExtendedAttachmentsProcess {

   private Log mLog = new Log(this.getClass());


   public ExtendedAttachmentsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      ExtendedAttachmentEditorSessionServer es = new ExtendedAttachmentEditorSessionServer(this.getConnection());

      try {
         es.delete(primaryKey);
      } catch (ValidationException var5) {
         throw var5;
      } catch (CPException var6) {
         throw new RuntimeException("can\'t delete " + primaryKey, var6);
      }

      if(timer != null) {
         timer.logDebug("deleteObject", primaryKey);
      }

   }

   public ExtendedAttachmentEditorSession getExtendedAttachmentEditorSession(Object key) throws ValidationException {
      ExtendedAttachmentEditorSessionImpl sess = new ExtendedAttachmentEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllExtendedAttachments() {
      try {
         return this.getConnection().getListHelper().getAllExtendedAttachments();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllExtendedAttachments", var2);
      }
   }

   public EntityList getExtendedAttachmentsForId(int param1) {
      try {
         return this.getConnection().getListHelper().getExtendedAttachmentsForId(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get ExtendedAttachmentsForId", var3);
      }
   }

   public EntityList getAllImageExtendedAttachments() {
      try {
         return this.getConnection().getListHelper().getAllImageExtendedAttachments();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllImageExtendedAttachments", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing ExtendedAttachment";
      return ret;
   }

   protected int getProcessID() {
      return 87;
   }

   public ExtendedAttachmentEditorSession getExtendedAttachmentEditorSessionForId(Integer id) throws ValidationException {
      return id == null?this.getExtendedAttachmentEditorSession((Object)null):this.getExtendedAttachmentEditorSession(new ExtendedAttachmentPK(id.intValue()));
   }
}
