// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.currency.CurrencyRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.ModelEditor;
import com.cedar.cp.api.model.ModelEditorSession;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.model.ModelEditorSessionSSO;
import com.cedar.cp.dto.model.ModelImpl;
import com.cedar.cp.ejb.api.model.ModelEditorSessionServer;
import com.cedar.cp.ejb.impl.model.ModelEditorSessionSEJB;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.ModelEditorImpl;
import com.cedar.cp.impl.model.ModelsProcessImpl;
import com.cedar.cp.util.Log;

import cppro.utils.DBUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModelEditorSessionImpl extends BusinessSessionImpl implements ModelEditorSession {

   protected ModelEditorSessionSSO mServerSessionData;
   protected ModelImpl mEditorData;
   protected ModelEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());

   public static ModelEditorSessionSEJB server = new ModelEditorSessionSEJB();
   public ModelEditorSessionImpl(ModelsProcessImpl process, Object key) throws ValidationException {
      super(process);

      try {
         if(key == null) {
            this.mServerSessionData = server.getNewItemData(DBUtils.userId);
         } else {
//            this.mServerSessionData = this.getSessionServer().getItemData(key);
        	 this.mServerSessionData = server.getItemData(DBUtils.userId, key);
         }
      } catch (ValidationException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new RuntimeException("Can\'t get Model", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected ModelEditorSessionServer getSessionServer() throws CPException {
      return new ModelEditorSessionServer(this.getConnection());
   }

   public ModelEditor getModelEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new ModelEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public CurrencyRef[] getAvailableCurrencyRefs() {
      try {
         return this.getSessionServer().getAvailableCurrencyRefs();
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
   }

   public DimensionRef[] getAvailableAccountRefs() {
      DimensionRef[] avail = this.getDimensionsByType(1);
      if(this.mEditorData.getAccountRef() == null) {
         return avail;
      } else {
         this.mLog.debug("getavailableAccountRefs", this.mEditorData.getAccountRef());
         DimensionRef[] avail2 = new DimensionRef[1 + avail.length];
         avail2[0] = this.getConnection().getListHelper().getDimensionEntityRef(this.mEditorData.getAccountRef());

         int i;
         for(i = 0; i < avail.length; ++i) {
            avail2[i + 1] = avail[i];
         }

         for(i = 0; i < avail2.length; ++i) {
            this.mLog.debug("getavailableAccountRefs", avail2[i]);
         }

         return avail2;
      }
   }

   public DimensionRef[] getAvailableCalendarRefs() {
      DimensionRef[] avail = this.getDimensionsByType(3);
      if(this.mEditorData.getCalendarRef() == null) {
         return avail;
      } else {
         DimensionRef[] avail2 = new DimensionRef[1 + avail.length];
         avail2[0] = this.getConnection().getListHelper().getDimensionEntityRef(this.mEditorData.getCalendarRef());

         for(int i = 0; i < avail.length; ++i) {
            avail2[i + 1] = avail[i];
         }

         return avail2;
      }
   }

   public HierarchyRef[] getAvailableBudgetHierarchyRefs() {
      try {
         HierarchyRef[] e = this.getSessionServer().getAvailableBudgetHierarchyRefs();
         List drefs = this.mEditorData.getSelectedDimensionRefs();
         ArrayList availList = new ArrayList();

         for(int i = 0; i < e.length; ++i) {
            HierarchyCK hck = (HierarchyCK)e[i].getPrimaryKey();
            Iterator iter = drefs.iterator();

            while(iter.hasNext()) {
               DimensionRef dr = (DimensionRef)iter.next();
               DimensionPK dpk = (DimensionPK)dr.getPrimaryKey();
               if(hck.getDimensionPK().equals(dpk)) {
                  availList.add(e[i]);
               }
            }
         }

         return (HierarchyRef[])((HierarchyRef[])availList.toArray(new HierarchyRef[0]));
      } catch (Exception var9) {
         throw new RuntimeException("unexpected exceptio", var9);
      }
   }

   public Object persistModifications(boolean cloneOnSave) throws CPException, ValidationException {
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

   public void terminate() {}

   public DimensionRef[] getAvailableBusinessDimensionRefs() {
      DimensionRef[] avail = this.getDimensionsByType(2);
      return avail;
   }

   private DimensionRef[] getDimensionsByType(int reqdType) {
      try {
         EntityList e = this.getConnection().getListHelper().getAvailableDimensions();
         ArrayList l = new ArrayList();

         for(int data = 0; data < e.getNumRows(); ++data) {
            DimensionRefImpl i = (DimensionRefImpl)e.getValueAt(data, "Dimension");
            int type = i.getType();
            if(type == reqdType) {
               l.add(i);
            }
         }

         DimensionRef[] var8 = new DimensionRef[l.size()];

         for(int var9 = 0; var9 < var8.length; ++var9) {
            var8[var9] = (DimensionRef)l.get(var9);
         }

         return var8;
      } catch (Exception var7) {
         throw new RuntimeException(var7.getMessage());
      }
   }
}
