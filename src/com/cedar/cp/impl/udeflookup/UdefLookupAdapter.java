// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.udeflookup;

import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.dto.udeflookup.UdefLookupImpl;
import com.cedar.cp.dto.udeflookup.UdefLookupPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.udeflookup.UdefLookupEditorSessionImpl;
import java.sql.Timestamp;
import java.util.List;

public class UdefLookupAdapter implements UdefLookup {

   private UdefLookupImpl mEditorData;
   private UdefLookupEditorSessionImpl mEditorSessionImpl;


   public UdefLookupAdapter(UdefLookupEditorSessionImpl e, UdefLookupImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected UdefLookupEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected UdefLookupImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(UdefLookupPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public String getGenTableName() {
      return this.mEditorData.getGenTableName();
   }

   public boolean isAutoSubmit() {
      return this.mEditorData.isAutoSubmit();
   }

   public boolean isScenario() {
      return this.mEditorData.isScenario();
   }

   public int getTimeLvl() {
      return this.mEditorData.getTimeLvl();
   }

   public int getYearStartMonth() {
      return this.mEditorData.getYearStartMonth();
   }

   public boolean isTimeRange() {
      return this.mEditorData.isTimeRange();
   }

   public Timestamp getLastSubmit() {
      return this.mEditorData.getLastSubmit();
   }

   public Timestamp getDataUpdated() {
      return this.mEditorData.getDataUpdated();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setGenTableName(String p) {
      this.mEditorData.setGenTableName(p);
   }

   public void setAutoSubmit(boolean p) {
      this.mEditorData.setAutoSubmit(p);
   }

   public void setScenario(boolean p) {
      this.mEditorData.setScenario(p);
   }

   public void setTimeLvl(int p) {
      this.mEditorData.setTimeLvl(p);
   }

   public void setYearStartMonth(int p) {
      this.mEditorData.setYearStartMonth(p);
   }

   public void setTimeRange(boolean p) {
      this.mEditorData.setTimeRange(p);
   }

   public void setLastSubmit(Timestamp p) {
      this.mEditorData.setLastSubmit(p);
   }

   public void setDataUpdated(Timestamp p) {
      this.mEditorData.setDataUpdated(p);
   }

   public List getColumnDef() {
      return this.mEditorData.getColumnDef();
   }

   public List getTableData() {
      if(this.mEditorData.getTableData() == null) {
         List data = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getLookupTableData(this.getGenTableName(), this.getColumnDef());
         this.mEditorData.setTableData(data);
      }

      return this.mEditorData.getTableData();
   }
}
