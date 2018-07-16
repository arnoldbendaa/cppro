// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.api.dataEntry.DataExtract;
import com.cedar.cp.api.datatype.DataTypeRef;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DataExtractImpl implements DataExtract, Serializable {

   private List mData;
   private List mNoteData;
   private int mUserId;
   private String mModelVisId;
   private String mFinanceCubeVisId;
   private boolean mReplaceMode;
   private int mTaskId;
   private List<String> mHierarchyList;
   private Map<String, DataTypeRef> mValidDataTypes;
   private int mRowCount;
   private List<String> mCalendarVisIds;
   private boolean mNoteFormat;


   public List getData() {
      return this.mData == null?Collections.EMPTY_LIST:this.mData;
   }

   public void setData(List data) {
      this.mData = data;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public void setUserId(int userId) {
      this.mUserId = userId;
   }

   public String getModelVisId() {
      return this.mModelVisId;
   }

   public void setModelVisId(String modelVisId) {
      this.mModelVisId = modelVisId;
   }

   public String getFinanceCubeVisId() {
      return this.mFinanceCubeVisId;
   }

   public void setFinanceCubeVisId(String financeCubeVisId) {
      this.mFinanceCubeVisId = financeCubeVisId;
   }

   public boolean isReplaceMode() {
      return this.mReplaceMode;
   }

   public void setReplaceMode(boolean replaceMode) {
      this.mReplaceMode = replaceMode;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public void setTaskId(int taskId) {
      this.mTaskId = taskId;
   }

   public List<String> getHierarchyList() {
      return this.mHierarchyList;
   }

   public void setHierarchyList(List<String> hierarchyList) {
      this.mHierarchyList = hierarchyList;
   }

   public Map<String, DataTypeRef> getValidDataTypes() {
      return this.mValidDataTypes == null?Collections.EMPTY_MAP:this.mValidDataTypes;
   }

   public void setValidDataTypes(Map<String, DataTypeRef> validDataTypes) {
      this.mValidDataTypes = validDataTypes;
   }

   public void setCalendarVisIds(List<String> calVisIds) {
      this.mCalendarVisIds = calVisIds;
   }

   public List<String> getCalendarVisIds() {
      return this.mCalendarVisIds;
   }

   public int getRowCount() {
      return this.mRowCount;
   }

   public void setRowCount(int rowCount) {
      this.mRowCount = rowCount;
   }

   public boolean isNoteFormat() {
      return this.mNoteFormat;
   }

   public void setNoteFormat(boolean noteFormat) {
      this.mNoteFormat = noteFormat;
   }

   public List getNoteData() {
      return this.mNoteData;
   }

   public void setNoteData(List noteData) {
      this.mNoteData = noteData;
   }
}
