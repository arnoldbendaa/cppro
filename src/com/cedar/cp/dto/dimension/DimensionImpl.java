// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.api.dimension.Dimension;
import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.ListModelImpl;
import com.cedar.cp.dto.dimension.DimensionElementImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ListModel;

public class DimensionImpl implements Dimension, Serializable, Cloneable {

   private ModelRefImpl mModelRef;
   private boolean mChangeManagementRequestsPending;
   private boolean mSubmitChangeManagementRequest;
   private Map mPKMap;
   private Map mVisIdMap;
   private List mDimensionElements;
   private DimensionElement mNullElement;
   private ConnectionContext mConnectionContext;
   private transient ListModelImpl mListModel;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private int mType;
   private Integer mExternalSystemRef;
   private int mVersionNum;


   public DimensionImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mType = 0;
      this.mExternalSystemRef = null;
      this.mPKMap = new HashMap();
      this.mVisIdMap = new HashMap();
      this.mDimensionElements = new ArrayList();
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (DimensionPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getType() {
      return this.mType;
   }

   public Integer getExternalSystemRef() {
      return this.mExternalSystemRef;
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

   public void setType(int paramType) {
      this.mType = paramType;
   }

   public void setExternalSystemRef(Integer paramExternalSystemRef) {
      this.mExternalSystemRef = paramExternalSystemRef;
   }

   public void nullPrimaryKey() {
      this.mPrimaryKey = null;
   }

   public String toString() {
      return this.mVisId;
   }

   public void addDimensionElement(DimensionElementImpl de) throws ValidationException {
      if(this.getDimensionElement(de.getKey()) != null) {
         throw new ValidationException("Duplicate item add attempted");
      } else if(this.getDimensionElement(de.getVisId()) != null) {
         throw new ValidationException("Duplicate name add attempted");
      } else {
         this.mPKMap.put(de.getKey(), de);
         this.mVisIdMap.put(de.getVisId(), de);
         if(this.mListModel != null) {
            this.mListModel.add(de);
         } else {
            this.mDimensionElements.add(de);
         }

         if(de.isNullElement()) {
            this.setNullElement(de);
         }

      }
   }

   public void removeDimensionElement(DimensionElementImpl de) {
      this.mPKMap.remove(de.getKey());
      this.mVisIdMap.remove(de.getVisId());
      if(this.mListModel != null) {
         this.mListModel.remove(de);
      } else {
         this.mDimensionElements.remove(de);
      }

   }

   public DimensionElement getDimensionElement(Object key) {
      return (DimensionElementImpl)this.mPKMap.get(key);
   }

   public DimensionElement getDimensionElement(String visId) {
      return (DimensionElementImpl)this.mVisIdMap.get(visId);
   }

   public Collection getDimensionElements() {
      return this.mVisIdMap.values();
   }

   public ListModel getListModel() {
      if(this.mListModel == null) {
         this.mListModel = new ListModelImpl(this.mDimensionElements);
      }

      return this.mListModel;
   }

   public DimensionRef getEntityRef() {
      return new DimensionRefImpl((DimensionPK)this.mPrimaryKey, this.mVisId, this.mType);
   }

   public void setSubmitChangeManagementRequest(boolean b) {
      this.mSubmitChangeManagementRequest = b;
   }

   public boolean isSubmitChangeManagementRequest() {
      return this.mSubmitChangeManagementRequest;
   }

   public boolean changeManagementRequestsPending() {
      return this.mChangeManagementRequestsPending;
   }

   public void setChangeManagementRequestsPending(boolean changeManagementRequestsPending) {
      this.mChangeManagementRequestsPending = changeManagementRequestsPending;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRefImpl modelRef) {
      this.mModelRef = modelRef;
   }

   public DimensionElement getNullElement() {
      return this.mNullElement;
   }

   public void setNullElement(DimensionElement nullElement) {
      this.mNullElement = nullElement;
   }

   public ConnectionContext getConnectionContext() {
      return this.mConnectionContext;
   }

   public void setConnectionContext(ConnectionContext connectionContext) {
      this.mConnectionContext = connectionContext;
   }
}
