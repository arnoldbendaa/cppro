// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.xmlform.XmlFormEditorImpl;
import com.cedar.cp.impl.xmlform.XmlFormEditorSessionImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.naming.Context;

public abstract class BusinessSessionImpl implements BusinessSession {

   protected Context mContext;
   protected BusinessProcessImpl mProcess;
   protected Set mActiveEditors;


   public BusinessSessionImpl(BusinessProcessImpl process) {
      this.mProcess = process;
      this.mContext = process.getContext();
      this.mActiveEditors = new HashSet();
   }

   public BusinessProcess getBusinessProcess() {
      return this.mProcess;
   }

   public CPConnection getConnection() {
      return this.mProcess.getConnection();
   }

   public boolean hasSecurity(String actionToTest) {
      return this.mProcess.hasSecurity(actionToTest);
   }

   public boolean isContentModified() {
      Iterator iter = this.mActiveEditors.iterator();

      BusinessEditor editor;
      do {
         if(!iter.hasNext()) {
            return false;
         }

         editor = (BusinessEditor)iter.next();
      } while(!editor.isContentModified());

      return true;
   }

   public boolean isReadOnly() {
      return true;
   }

   public Object commit(boolean cloneOnSave) throws ValidationException {
      try {
         this.mProcess.getConnection().fireNetworkActivity(true);
         Iterator e = this.mActiveEditors.iterator();
         //System.out.println("----- BusinessSessionImpl.commit(): mActiveEditors.size: "+this.mActiveEditors.size());
         while(e.hasNext()) {
            BusinessEditor editor = (BusinessEditor)e.next();
            editor.commit();
         }

         Object editor1 = this.persistModifications(cloneOnSave);
         return editor1;
      } catch (ValidationException var8) {
         throw var8;
      } catch (CPException var9) {
         var9.printStackTrace();
         throw new RuntimeException("unable to persist");
      } finally {
         this.mProcess.getConnection().fireNetworkActivity(false);
      }
   }

   public Collection getActiveEditors() {
      return Collections.unmodifiableSet(this.mActiveEditors);
   }

   protected abstract Object persistModifications(boolean var1) throws ValidationException, CPException;

   public Context getContext() {
      return this.mContext;
   }

   public abstract void terminate();
}
