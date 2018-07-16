// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:38
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.dto.extsys.ExtSysCompanyPK;
import com.cedar.cp.dto.extsys.ExtSysPropertyPK;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.extsys.ExternalSystemRefImpl;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysPropertyEVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExternalSystemEVO implements Serializable {

   private transient ExternalSystemPK mPK;
   private int mExternalSystemId;
   private int mSystemType;
   private String mVisId;
   private String mDescription;
   private String mLocation;
   private String mConnectorClass;
   private String mImportSource;
   private String mExportTarget;
   private boolean mEnabled;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<ExtSysCompanyPK, ExtSysCompanyEVO> mExtSysCompanies;
   protected boolean mExtSysCompaniesAllItemsLoaded;
   private Map<ExtSysPropertyPK, ExtSysPropertyEVO> mExtSysProperties;
   protected boolean mExtSysPropertiesAllItemsLoaded;
   private boolean mModified;
   public static final int TYPE_E5 = 5;
   public static final int TYPE_EFINANCIALS = 3;
   public static final int TYPE_OPEN_ACCOUNTS = 10;
   public static final int TYPE_GENERIC = 20;


   public ExternalSystemEVO() {}

   public ExternalSystemEVO(int newExternalSystemId, int newSystemType, String newVisId, String newDescription, String newLocation, String newConnectorClass, String newImportSource, String newExportTarget, boolean newEnabled, int newVersionNum, Collection newExtSysCompanies, Collection newExtSysProperties) {
      this.mExternalSystemId = newExternalSystemId;
      this.mSystemType = newSystemType;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mLocation = newLocation;
      this.mConnectorClass = newConnectorClass;
      this.mImportSource = newImportSource;
      this.mExportTarget = newExportTarget;
      this.mEnabled = newEnabled;
      this.mVersionNum = newVersionNum;
      this.setExtSysCompanies(newExtSysCompanies);
      this.setExtSysProperties(newExtSysProperties);
   }

   public void setExtSysCompanies(Collection<ExtSysCompanyEVO> items) {
      if(items != null) {
         if(this.mExtSysCompanies == null) {
            this.mExtSysCompanies = new HashMap();
         } else {
            this.mExtSysCompanies.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExtSysCompanyEVO child = (ExtSysCompanyEVO)i$.next();
            this.mExtSysCompanies.put(child.getPK(), child);
         }
      } else {
         this.mExtSysCompanies = null;
      }

   }

   public void setExtSysProperties(Collection<ExtSysPropertyEVO> items) {
      if(items != null) {
         if(this.mExtSysProperties == null) {
            this.mExtSysProperties = new HashMap();
         } else {
            this.mExtSysProperties.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExtSysPropertyEVO child = (ExtSysPropertyEVO)i$.next();
            this.mExtSysProperties.put(child.getPK(), child);
         }
      } else {
         this.mExtSysProperties = null;
      }

   }

   public ExternalSystemPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExternalSystemPK(this.mExternalSystemId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getExternalSystemId() {
      return this.mExternalSystemId;
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

   public boolean getEnabled() {
      return this.mEnabled;
   }

   public int getVersionNum() {
      return this.mVersionNum;
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

   public void setExternalSystemId(int newExternalSystemId) {
      if(this.mExternalSystemId != newExternalSystemId) {
         this.mModified = true;
         this.mExternalSystemId = newExternalSystemId;
         this.mPK = null;
      }
   }

   public void setSystemType(int newSystemType) {
      if(this.mSystemType != newSystemType) {
         this.mModified = true;
         this.mSystemType = newSystemType;
      }
   }

   public void setEnabled(boolean newEnabled) {
      if(this.mEnabled != newEnabled) {
         this.mModified = true;
         this.mEnabled = newEnabled;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setVisId(String newVisId) {
      if(this.mVisId != null && newVisId == null || this.mVisId == null && newVisId != null || this.mVisId != null && newVisId != null && !this.mVisId.equals(newVisId)) {
         this.mVisId = newVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setLocation(String newLocation) {
      if(this.mLocation != null && newLocation == null || this.mLocation == null && newLocation != null || this.mLocation != null && newLocation != null && !this.mLocation.equals(newLocation)) {
         this.mLocation = newLocation;
         this.mModified = true;
      }

   }

   public void setConnectorClass(String newConnectorClass) {
      if(this.mConnectorClass != null && newConnectorClass == null || this.mConnectorClass == null && newConnectorClass != null || this.mConnectorClass != null && newConnectorClass != null && !this.mConnectorClass.equals(newConnectorClass)) {
         this.mConnectorClass = newConnectorClass;
         this.mModified = true;
      }

   }

   public void setImportSource(String newImportSource) {
      if(this.mImportSource != null && newImportSource == null || this.mImportSource == null && newImportSource != null || this.mImportSource != null && newImportSource != null && !this.mImportSource.equals(newImportSource)) {
         this.mImportSource = newImportSource;
         this.mModified = true;
      }

   }

   public void setExportTarget(String newExportTarget) {
      if(this.mExportTarget != null && newExportTarget == null || this.mExportTarget == null && newExportTarget != null || this.mExportTarget != null && newExportTarget != null && !this.mExportTarget.equals(newExportTarget)) {
         this.mExportTarget = newExportTarget;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ExternalSystemEVO newDetails) {
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setSystemType(newDetails.getSystemType());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setLocation(newDetails.getLocation());
      this.setConnectorClass(newDetails.getConnectorClass());
      this.setImportSource(newDetails.getImportSource());
      this.setExportTarget(newDetails.getExportTarget());
      this.setEnabled(newDetails.getEnabled());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ExternalSystemEVO deepClone() {
      ExternalSystemEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ExternalSystemEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mExternalSystemId > 0) {
         newKey = true;
         this.mExternalSystemId = 0;
      } else if(this.mExternalSystemId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      Iterator iter;
      ExtSysCompanyEVO item;
      if(this.mExtSysCompanies != null) {
         for(iter = (new ArrayList(this.mExtSysCompanies.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ExtSysCompanyEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      ExtSysPropertyEVO item1;
      if(this.mExtSysProperties != null) {
         for(iter = (new ArrayList(this.mExtSysProperties.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (ExtSysPropertyEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mExternalSystemId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      ExtSysCompanyEVO item;
      if(this.mExtSysCompanies != null) {
         for(iter = this.mExtSysCompanies.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ExtSysCompanyEVO)iter.next();
         }
      }

      ExtSysPropertyEVO item1;
      if(this.mExtSysProperties != null) {
         for(iter = this.mExtSysProperties.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (ExtSysPropertyEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mExternalSystemId < 1) {
         this.mExternalSystemId = startKey;
         nextKey = startKey + 1;
      }

      Iterator iter;
      ExtSysCompanyEVO item;
      if(this.mExtSysCompanies != null) {
         for(iter = (new ArrayList(this.mExtSysCompanies.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ExtSysCompanyEVO)iter.next();
            this.changeKey(item, this.mExternalSystemId, item.getCompanyVisId());
         }
      }

      ExtSysPropertyEVO item1;
      if(this.mExtSysProperties != null) {
         for(iter = (new ArrayList(this.mExtSysProperties.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (ExtSysPropertyEVO)iter.next();
            this.changeKey(item1, this.mExternalSystemId, item1.getPropertyName());
         }
      }

      return nextKey;
   }

   public Collection<ExtSysCompanyEVO> getExtSysCompanies() {
      return this.mExtSysCompanies != null?this.mExtSysCompanies.values():null;
   }

   public Map<ExtSysCompanyPK, ExtSysCompanyEVO> getExtSysCompaniesMap() {
      return this.mExtSysCompanies;
   }

   public void loadExtSysCompaniesItem(ExtSysCompanyEVO newItem) {
      if(this.mExtSysCompanies == null) {
         this.mExtSysCompanies = new HashMap();
      }

      this.mExtSysCompanies.put(newItem.getPK(), newItem);
   }

   public void addExtSysCompaniesItem(ExtSysCompanyEVO newItem) {
      if(this.mExtSysCompanies == null) {
         this.mExtSysCompanies = new HashMap();
      }

      ExtSysCompanyPK newPK = newItem.getPK();
      if(this.getExtSysCompaniesItem(newPK) != null) {
         throw new RuntimeException("addExtSysCompaniesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExtSysCompanies.put(newPK, newItem);
      }
   }

   public void changeExtSysCompaniesItem(ExtSysCompanyEVO changedItem) {
      if(this.mExtSysCompanies == null) {
         throw new RuntimeException("changeExtSysCompaniesItem: no items in collection");
      } else {
         ExtSysCompanyPK changedPK = changedItem.getPK();
         ExtSysCompanyEVO listItem = this.getExtSysCompaniesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExtSysCompaniesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExtSysCompaniesItem(ExtSysCompanyPK removePK) {
      ExtSysCompanyEVO listItem = this.getExtSysCompaniesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExtSysCompaniesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExtSysCompanyEVO getExtSysCompaniesItem(ExtSysCompanyPK pk) {
      return (ExtSysCompanyEVO)this.mExtSysCompanies.get(pk);
   }

   public ExtSysCompanyEVO getExtSysCompaniesItem() {
      if(this.mExtSysCompanies.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExtSysCompanies.size());
      } else {
         Iterator iter = this.mExtSysCompanies.values().iterator();
         return (ExtSysCompanyEVO)iter.next();
      }
   }

   public Collection<ExtSysPropertyEVO> getExtSysProperties() {
      return this.mExtSysProperties != null?this.mExtSysProperties.values():null;
   }

   public Map<ExtSysPropertyPK, ExtSysPropertyEVO> getExtSysPropertiesMap() {
      return this.mExtSysProperties;
   }

   public void loadExtSysPropertiesItem(ExtSysPropertyEVO newItem) {
      if(this.mExtSysProperties == null) {
         this.mExtSysProperties = new HashMap();
      }

      this.mExtSysProperties.put(newItem.getPK(), newItem);
   }

   public void addExtSysPropertiesItem(ExtSysPropertyEVO newItem) {
      if(this.mExtSysProperties == null) {
         this.mExtSysProperties = new HashMap();
      }

      ExtSysPropertyPK newPK = newItem.getPK();
      if(this.getExtSysPropertiesItem(newPK) != null) {
         throw new RuntimeException("addExtSysPropertiesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExtSysProperties.put(newPK, newItem);
      }
   }

   public void changeExtSysPropertiesItem(ExtSysPropertyEVO changedItem) {
      if(this.mExtSysProperties == null) {
         throw new RuntimeException("changeExtSysPropertiesItem: no items in collection");
      } else {
         ExtSysPropertyPK changedPK = changedItem.getPK();
         ExtSysPropertyEVO listItem = this.getExtSysPropertiesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExtSysPropertiesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExtSysPropertiesItem(ExtSysPropertyPK removePK) {
      ExtSysPropertyEVO listItem = this.getExtSysPropertiesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExtSysPropertiesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExtSysPropertyEVO getExtSysPropertiesItem(ExtSysPropertyPK pk) {
      return (ExtSysPropertyEVO)this.mExtSysProperties.get(pk);
   }

   public ExtSysPropertyEVO getExtSysPropertiesItem() {
      if(this.mExtSysProperties.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExtSysProperties.size());
      } else {
         Iterator iter = this.mExtSysProperties.values().iterator();
         return (ExtSysPropertyEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public ExternalSystemRef getEntityRef() {
      return new ExternalSystemRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mExtSysCompaniesAllItemsLoaded = true;
      if(this.mExtSysCompanies == null) {
         this.mExtSysCompanies = new HashMap();
      } else {
         Iterator i$ = this.mExtSysCompanies.values().iterator();

         while(i$.hasNext()) {
            ExtSysCompanyEVO child = (ExtSysCompanyEVO)i$.next();
            child.postCreateInit();
         }
      }

      this.mExtSysPropertiesAllItemsLoaded = true;
      if(this.mExtSysProperties == null) {
         this.mExtSysProperties = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ExternalSystemId=");
      sb.append(String.valueOf(this.mExternalSystemId));
      sb.append(' ');
      sb.append("SystemType=");
      sb.append(String.valueOf(this.mSystemType));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("Location=");
      sb.append(String.valueOf(this.mLocation));
      sb.append(' ');
      sb.append("ConnectorClass=");
      sb.append(String.valueOf(this.mConnectorClass));
      sb.append(' ');
      sb.append("ImportSource=");
      sb.append(String.valueOf(this.mImportSource));
      sb.append(' ');
      sb.append("ExportTarget=");
      sb.append(String.valueOf(this.mExportTarget));
      sb.append(' ');
      sb.append("Enabled=");
      sb.append(String.valueOf(this.mEnabled));
      sb.append(' ');
      sb.append("VersionNum=");
      sb.append(String.valueOf(this.mVersionNum));
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

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i$ = 0; i$ < indent; ++i$) {
         sb.append(' ');
      }

      sb.append("ExternalSystem: ");
      sb.append(this.toString());
      if(this.mExtSysCompaniesAllItemsLoaded || this.mExtSysCompanies != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExtSysCompanies: allItemsLoaded=");
         sb.append(String.valueOf(this.mExtSysCompaniesAllItemsLoaded));
         sb.append(" items=");
         if(this.mExtSysCompanies == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExtSysCompanies.size()));
         }
      }

      if(this.mExtSysPropertiesAllItemsLoaded || this.mExtSysProperties != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExtSysProperties: allItemsLoaded=");
         sb.append(String.valueOf(this.mExtSysPropertiesAllItemsLoaded));
         sb.append(" items=");
         if(this.mExtSysProperties == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExtSysProperties.size()));
         }
      }

      Iterator var5;
      if(this.mExtSysCompanies != null) {
         var5 = this.mExtSysCompanies.values().iterator();

         while(var5.hasNext()) {
            ExtSysCompanyEVO listItem = (ExtSysCompanyEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mExtSysProperties != null) {
         var5 = this.mExtSysProperties.values().iterator();

         while(var5.hasNext()) {
            ExtSysPropertyEVO var6 = (ExtSysPropertyEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ExtSysCompanyEVO child, int newExternalSystemId, String newCompanyVisId) {
      if(this.getExtSysCompaniesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExtSysCompanies.remove(child.getPK());
         child.setExternalSystemId(newExternalSystemId);
         child.setCompanyVisId(newCompanyVisId);
         this.mExtSysCompanies.put(child.getPK(), child);
      }
   }

   public void changeKey(ExtSysPropertyEVO child, int newExternalSystemId, String newPropertyName) {
      if(this.getExtSysPropertiesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExtSysProperties.remove(child.getPK());
         child.setExternalSystemId(newExternalSystemId);
         child.setPropertyName(newPropertyName);
         this.mExtSysProperties.put(child.getPK(), child);
      }
   }

   public void setExtSysCompaniesAllItemsLoaded(boolean allItemsLoaded) {
      this.mExtSysCompaniesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExtSysCompaniesAllItemsLoaded() {
      return this.mExtSysCompaniesAllItemsLoaded;
   }

   public void setExtSysPropertiesAllItemsLoaded(boolean allItemsLoaded) {
      this.mExtSysPropertiesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExtSysPropertiesAllItemsLoaded() {
      return this.mExtSysPropertiesAllItemsLoaded;
   }
}
