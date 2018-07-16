// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.extsys.ExternalSystem;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalSystemImpl implements ExternalSystem, Serializable, Cloneable {

   private Map<Object, List> mAvailableColumnCacheList = new HashMap();
   private Map<Object, List> mSelectedColumnCacheList = new HashMap();
   private Map<String, String> mProperties = new HashMap();
   private Object mPrimaryKey;
   private int mSystemType;
   private String mVisId;
   private String mDescription;
   private String mLocation;
   private String mConnectorClass;
   private String mImportSource;
   private String mExportTarget;
   private boolean mEnabled;
   private int mVersionNum;


   public ExternalSystemImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mSystemType = 0;
      this.mVisId = "";
      this.mDescription = "";
      this.mLocation = "";
      this.mConnectorClass = "";
      this.mImportSource = "";
      this.mExportTarget = "";
      this.mEnabled = false;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (ExternalSystemPK)paramKey;
   }

   public int getSystemType() {
      return this.mSystemType;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getLocation() {
      return this.mLocation;
   }

   public String getConnectorClass() {
      return this.mConnectorClass;
   }

   public String getImportSource() {
      return this.mImportSource;
   }

   public String getExportTarget() {
      return this.mExportTarget;
   }

   public boolean isEnabled() {
      return this.mEnabled;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setSystemType(int paramSystemType) {
      this.mSystemType = paramSystemType;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setLocation(String paramLocation) {
      this.mLocation = paramLocation;
   }

   public void setConnectorClass(String paramConnectorClass) {
      this.mConnectorClass = paramConnectorClass;
   }

   public void setImportSource(String paramImportSource) {
      this.mImportSource = paramImportSource;
   }

   public void setExportTarget(String paramExportTarget) {
      this.mExportTarget = paramExportTarget;
   }

   public void setEnabled(boolean paramEnabled) {
      this.mEnabled = paramEnabled;
   }

   public Map<String, String> getProperties() {
      return this.mProperties;
   }

   public void setProperties(Map<String, String> properties) {
      this.mProperties = properties;
   }

   public void addAvailableColumns(int subsystemId, List col) {
      this.mAvailableColumnCacheList.put(Integer.valueOf(subsystemId), col);
   }

   public void addSelectedColumns(int subsystemId, List col) {
      this.mSelectedColumnCacheList.put(Integer.valueOf(subsystemId), col);
   }

   public List getAvailableColumnList(int subsystemId) {
      return this.mAvailableColumnCacheList.containsKey(Integer.valueOf(subsystemId))?(List)this.mAvailableColumnCacheList.get(Integer.valueOf(subsystemId)):null;
   }

   public List getSelectedColumnList(int subsystemId) {
      return this.mSelectedColumnCacheList.containsKey(Integer.valueOf(subsystemId))?(List)this.mSelectedColumnCacheList.get(Integer.valueOf(subsystemId)):null;
   }

   public Map<Object, List> getAllSelectedColumns() {
      return this.mSelectedColumnCacheList;
   }
}
