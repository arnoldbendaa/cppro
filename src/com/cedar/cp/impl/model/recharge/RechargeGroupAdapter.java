// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.recharge;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.recharge.RechargeGroup;
import com.cedar.cp.dto.model.recharge.RechargeGroupImpl;
import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import com.cedar.cp.impl.model.recharge.RechargeGroupEditorSessionImpl;
import java.util.ArrayList;
import java.util.List;

public class RechargeGroupAdapter implements RechargeGroup {

   private RechargeGroupImpl mEditorData;
   private RechargeGroupEditorSessionImpl mEditorSessionImpl;


   public RechargeGroupAdapter(RechargeGroupEditorSessionImpl e, RechargeGroupImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected RechargeGroupEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected RechargeGroupImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(RechargeGroupPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public List getSelectedRecharge() {
      if(this.mEditorData.getSelectedRecharge() == null) {
         ArrayList selected = new ArrayList();
         this.mEditorData.setSelectedRecharge(selected);
      }

      return this.mEditorData.getSelectedRecharge();
   }

   public EntityList getAvailableRecharge() {
      EntityList list = null;
      CPConnection conn = this.mEditorSessionImpl.getConnection();
      if(this.mEditorData.getModelId() == 0) {
         list = conn.getListHelper().getAllRecharges();
      } else {
         list = conn.getListHelper().getAllRechargesWithModel(this.mEditorData.getModelId());
      }

      return list;
   }

   public int getModelId() {
      return this.mEditorData.getModelId();
   }
}
