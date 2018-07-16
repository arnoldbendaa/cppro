// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.xmlform.FormDeploymentData;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FormDeploymentDataImpl implements FormDeploymentData, Serializable, Cloneable {

   private Object mKey;
   private String mIdentifier;
   private String mDescription;
   private int mAutoExpandDepth;
   private boolean mFill;
   private boolean mBold;
   private boolean mHorz;
   private Integer mFinanceCubeId;
   private Integer mModelId;
   private Integer mBudgetCycleId;
   private Map mBusinessElements;
   private Map mSelection;
   private int mMailType;
   private String mMailContent;
   private String mAttachmentName;
   private byte[] mAttachment;
   private char mMobile;

   public Object getKey() {
      return this.mKey;
   }

   public void setKey(Object key) {
      this.mKey = key;
   }

   public String getIdentifier() {
      return this.mIdentifier;
   }
   
   public char getMobile() {
       return mMobile;
   }

   public void setMobile(char mMobile) {
        this.mMobile = mMobile;
    }

   public void setIdentifier(String identifier) {
      this.mIdentifier = identifier;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public int getAutoExpandDepth() {
      return this.mAutoExpandDepth;
   }

   public void setAutoExpandDepth(int autoExpandDepth) {
      this.mAutoExpandDepth = autoExpandDepth;
   }

   public boolean isFill() {
      return this.mFill;
   }

   public void setFill(boolean fill) {
      this.mFill = fill;
   }

   public boolean isBold() {
      return this.mBold;
   }

   public void setBold(boolean bold) {
      this.mBold = bold;
   }

   public boolean isHorz() {
      return this.mHorz;
   }

   public void setHorz(boolean horz) {
      this.mHorz = horz;
   }

   public Integer getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(Integer financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public Integer getModelId() {
      return this.mModelId;
   }

   public void setModelId(Integer modelId) {
      this.mModelId = modelId;
   }

	public Integer getBudgetCycleId() {
		return this.mBudgetCycleId;
	}

	public void setBudgetCycled(Integer budgetCycleId) {
		this.mBudgetCycleId = budgetCycleId;
	}
	   
   public Map getBusinessElements() {
      if(this.mBusinessElements == null) {
         this.mBusinessElements = new HashMap();
      }

      return this.mBusinessElements;
   }

   public void setBusinessElements(Map businessElements) {
      this.mBusinessElements = businessElements;
   }

   public Map getSelection() {
      return this.mSelection;
   }

   public void setSelection(Map selection) {
      this.mSelection = selection;
   }

   public int getMailType() {
      return this.mMailType;
   }

   public void setMailType(int mailType) {
      this.mMailType = mailType;
   }

   public String getMailContent() {
      return this.mMailContent;
   }

   public void setMailContent(String mailContent) {
      this.mMailContent = mailContent;
   }

   public String getAttachmentName() {
      return this.mAttachmentName;
   }

   public void setAttachmentName(String attachmentName) {
      this.mAttachmentName = attachmentName;
   }

   public byte[] getAttachment() {
      return this.mAttachment;
   }

   public void setAttachment(byte[] attachment) {
      this.mAttachment = attachment;
   }
}
