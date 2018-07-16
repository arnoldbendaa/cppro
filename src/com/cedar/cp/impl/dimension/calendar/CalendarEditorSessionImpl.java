// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension.calendar;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.calendar.CalendarEditor;
import com.cedar.cp.api.dimension.calendar.CalendarEditorSession;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorSessionSSO;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.ejb.api.dimension.calendar.CalendarEditorSessionServer;
import com.cedar.cp.ejb.impl.dimension.calendar.CalendarEditorSessionSEJB;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.dimension.calendar.CalendarEditorImpl;
import com.cedar.cp.util.Log;

import cppro.utils.DBUtils;

public class CalendarEditorSessionImpl extends BusinessSessionImpl implements CalendarEditorSession {

   protected CalendarEditorSessionSSO mServerSessionData;
   private CalendarEditorSessionServer mEditorSessionServer = new CalendarEditorSessionServer(this.getConnection());
   protected CalendarImpl mEditorData;
   protected CalendarEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());
   CalendarEditorSessionSEJB server = new CalendarEditorSessionSEJB();

   public CalendarEditorSessionImpl(BusinessProcessImpl process, Object key) {
      super(process);

      try {
         if(key == null) {
            this.mServerSessionData = server.getNewItemData(DBUtils.userId);
         } else {
            this.mServerSessionData = server.getItemData(DBUtils.userId,key);
         }
      } catch (ValidationException var4) {
         throw new CPException(var4.getMessage(), var4);
      } catch (Exception var5) {
         throw new RuntimeException("Can\'t get Hierarchy", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected CalendarEditorSessionServer getSessionServer() throws CPException {
      return this.mEditorSessionServer;
   }

   protected Object persistModifications(boolean cloneOnSave) throws ValidationException, CPException {
      if(this.mClientEditor != null) {
         this.mClientEditor.saveModifications();
      }

      if(this.mEditorData.getPrimaryKey() == null) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().insert(this.mEditorData));
      } else if(cloneOnSave) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().copy(this.mEditorData));
      } else {
         this.getSessionServer().update(this.mEditorData);
      }

      return this.mEditorData.getPrimaryKey();
   }

   public void terminate() {
      try {
         if(this.mEditorSessionServer != null) {
            this.mEditorSessionServer.removeSession();
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public CalendarEditor getCalendarEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new CalendarEditorImpl(this, this.mEditorData);
      }

      return this.mClientEditor;
   }
}
