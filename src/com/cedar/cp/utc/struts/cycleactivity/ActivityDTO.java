// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.cycleactivity;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class ActivityDTO {

   private int mActivityId;
   private int mModelId;
   private String mUserId;
   private String mFullName;
   private int mType;
   private String mDescription;
   private String mDetails;
   private Date mDate;
   private boolean mUndoEntry;
   private int mOwnerId;
   private static final String sTypeKey = "cp.budgetcycle.activity.type.key.";


   public int getActivityId() {
      return this.mActivityId;
   }

   public void setActivityId(int activityId) {
      this.mActivityId = activityId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public String getUserId() {
      return this.mUserId;
   }

   public void setUserId(String userId) {
      this.mUserId = userId;
   }

   public String getFullName() {
      return this.mFullName;
   }

   public void setFullName(String fullName) {
      this.mFullName = fullName;
   }

   public int getType() {
      return this.mType;
   }

   public void setType(int type) {
      this.mType = type;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public String getDetails() {
      return this.mDetails;
   }

   public void setDetails(String details) {
      this.mDetails = details;
   }

   public String getTransformDetails() {
      try {
         TransformerFactory e = TransformerFactory.newInstance();
         InputStream xslStream = this.getClass().getResourceAsStream("activity.xsl");
         Transformer transformer = e.newTransformer(new StreamSource(xslStream));
         StreamSource source = new StreamSource(new StringReader(this.mDetails));
         StringWriter writer = new StringWriter();
         transformer.transform(source, new StreamResult(writer));
         this.mDetails = writer.toString();
         writer.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      return this.mDetails;
   }

   public Date getDate() {
      return this.mDate;
   }

   public void setDate(Date date) {
      this.mDate = date;
   }

   public void setDate(long date) {
      this.mDate = new Date(date);
   }

   public String getTypeKey() {
      return "cp.budgetcycle.activity.type.key." + this.getType();
   }

   public boolean isUndoEntry() {
      return this.mUndoEntry;
   }

   public void setUndoEntry(boolean undoEntry) {
      this.mUndoEntry = undoEntry;
   }

   public int getOwnerId() {
      return this.mOwnerId;
   }

   public void setOwnerId(int ownerId) {
      this.mOwnerId = ownerId;
   }
}
