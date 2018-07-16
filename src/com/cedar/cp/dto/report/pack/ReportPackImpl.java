// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.pack;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.report.pack.ReportPack;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportPackImpl implements ReportPack, Serializable, Cloneable {

   public List mSelection;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private boolean mGroupAttachment;
   private String mParamExample;
   private int mVersionNum;


   public ReportPackImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mGroupAttachment = false;
      this.mParamExample = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (ReportPackPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean isGroupAttachment() {
      return this.mGroupAttachment;
   }

   public String getParamExample() {
      return this.mParamExample;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setGroupAttachment(boolean paramGroupAttachment) {
      this.mGroupAttachment = paramGroupAttachment;
   }

   public void setParamExample(String paramParamExample) {
      this.mParamExample = paramParamExample;
   }

   public List getSelection() {
      if(this.mSelection == null) {
         this.mSelection = new ArrayList();
      }

      return this.mSelection;
   }

   public void setSelection(List selection) {
      this.mSelection = selection;
   }

   public EntityList getAvailableDefinitions() {
      return null;
   }

   public EntityList getAvailableDistributions() {
      return null;
   }
}
