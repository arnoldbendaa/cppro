// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:38
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlform;

import com.cedar.cp.api.xmlform.XmlFormUserLinkRef;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkCK;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkPK;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkRefImpl;
import com.cedar.cp.ejb.impl.xmlform.XmlFormEVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class XmlFormUserLinkEVO implements Serializable {

   private transient XmlFormUserLinkPK mPK;
   private int mXmlFormId;
   private int mUserId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public XmlFormUserLinkEVO() {}

   public XmlFormUserLinkEVO(int newXmlFormId, int newUserId) {
      this.mXmlFormId = newXmlFormId;
      this.mUserId = newUserId;
   }

   public XmlFormUserLinkPK getPK() {
      if(this.mPK == null) {
         this.mPK = new XmlFormUserLinkPK(this.mXmlFormId, this.mUserId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getXmlFormId() {
      return this.mXmlFormId;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public int getUpdatedByUserId() {
      return this.mUpdatedByUserId;
   }

   public Timestamp getUpdatedTime() {
      return this.mUpdatedTime;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public void setXmlFormId(int newXmlFormId) {
      if(this.mXmlFormId != newXmlFormId) {
         this.mModified = true;
         this.mXmlFormId = newXmlFormId;
         this.mPK = null;
      }
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
         this.mPK = null;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(XmlFormUserLinkEVO newDetails) {
      this.setXmlFormId(newDetails.getXmlFormId());
      this.setUserId(newDetails.getUserId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public XmlFormUserLinkEVO deepClone() {
      XmlFormUserLinkEVO cloned = new XmlFormUserLinkEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mXmlFormId = this.mXmlFormId;
      cloned.mUserId = this.mUserId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(XmlFormEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(XmlFormEVO parent, int startKey) {
      return startKey;
   }

   public void setInsertPending() {
      this.mInsertPending = true;
   }

   public boolean insertPending() {
      return this.mInsertPending;
   }

   public void setDeletePending() {
      this.mDeletePending = true;
   }

   public boolean deletePending() {
      return this.mDeletePending;
   }

   protected void reset() {
      this.mModified = false;
      this.mInsertPending = false;
   }

   public XmlFormUserLinkRef getEntityRef(XmlFormEVO evoXmlForm, String entityText) {
      return new XmlFormUserLinkRefImpl(new XmlFormUserLinkCK(evoXmlForm.getPK(), this.getPK()), entityText);
   }

   public XmlFormUserLinkCK getCK(XmlFormEVO evoXmlForm) {
      return new XmlFormUserLinkCK(evoXmlForm.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("XmlFormId=");
      sb.append(String.valueOf(this.mXmlFormId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      sb.append("UpdatedByUserId=");
      sb.append(String.valueOf(this.mUpdatedByUserId));
      sb.append(' ');
      sb.append("UpdatedTime=");
      sb.append(String.valueOf(this.mUpdatedTime));
      sb.append(' ');
      sb.append("CreatedTime=");
      sb.append(String.valueOf(this.mCreatedTime));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      if(this.mInsertPending) {
         sb.append("insertPending ");
      }

      if(this.mDeletePending) {
         sb.append("deletePending ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("XmlFormUserLink: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
