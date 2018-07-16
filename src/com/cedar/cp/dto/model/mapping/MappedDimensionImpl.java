// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping;

import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.mapping.MappedDimension;
import com.cedar.cp.api.model.mapping.MappedDimensionElement;
import com.cedar.cp.api.model.mapping.MappedHierarchy;
import com.cedar.cp.api.model.mapping.MappedModel;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.MappedDimensionPK;
import com.cedar.cp.dto.model.mapping.MappedHierarchyImpl;
import com.cedar.cp.dto.model.mapping.MappedModelImpl;
import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class MappedDimensionImpl implements MappedDimension, Serializable, Cloneable {

   private MappedDimensionPK mKey;
   private MappedModel mMappedModel;
   private DimensionRef mDimensionRef;
   private String mDimensionVisId;
   private String mDimensionDescription;
   private String mNullDimensionElementVisId;
   private String mNullDimensionElementDescription;
   private Integer mNullDimensionElementCreditDebit;
   private int mDimensionType;
   private String mPathVisId;
   private List<MappedHierarchy> mMappedHierarchies;
   private SortedSet<MappedDimensionElement> mMappedDimensionElements;
   private boolean mIsDisabledLeafNodesExcluded;
   private int mNextKey = -1;


   public MappedDimensionImpl(MappedDimensionPK key, MappedModel MappedModel) {
      this.mKey = key;
      this.mMappedModel = MappedModel;
      this.mMappedHierarchies = new ArrayList();
      this.mMappedDimensionElements = new TreeSet();
   }

   public MappedDimensionImpl(MappedDimensionPK key, MappedModel MappedModel, DimensionRef dimensionRef, String dimensionVisId, String dimensionDescription, int dimensionType, String pathVisId, List<MappedHierarchy> mappedHierarchies, SortedSet<MappedDimensionElement> mappedDimensionElements, boolean isDisabledLeafNodesExcluded, String nullDimensionElementVisId, String nullDimensionElementDescription, Integer nullDimensionElementCreditDebit) {
      this.mKey = key;
      this.mMappedModel = MappedModel;
      this.mDimensionRef = dimensionRef;
      this.mDimensionVisId = dimensionVisId;
      this.mDimensionDescription = dimensionDescription;
      this.mDimensionType = dimensionType;
      this.mPathVisId = pathVisId;
      this.mMappedHierarchies = mappedHierarchies;
      this.mMappedDimensionElements = mappedDimensionElements;
      this.mIsDisabledLeafNodesExcluded = isDisabledLeafNodesExcluded;
      this.mNullDimensionElementVisId = nullDimensionElementVisId;
      this.mNullDimensionElementDescription = nullDimensionElementDescription;
      this.mNullDimensionElementCreditDebit = nullDimensionElementCreditDebit;
   }

   public MappingKey getFinanceDimensionKey() {
      return this.mDimensionVisId == null?null:new MappingKeyImpl(this.mPathVisId);
   }

   public DimensionRef getDimension() {
      return this.mDimensionRef;
   }

   public String getDimensionVisId() {
      return this.mDimensionVisId;
   }

   public String getDimensionDescription() {
      return this.mDimensionDescription;
   }

   public int getDimensionType() {
      return this.mDimensionType;
   }

   public MappedModel getModelMapping() {
      return this.mMappedModel;
   }

   public String getPathVisId() {
      return this.mPathVisId;
   }

   public void setMappedModel(MappedModel MappedModel) {
      this.mMappedModel = MappedModel;
   }

   public void setDimensionRef(DimensionRef dimensionRef) {
      this.mDimensionRef = dimensionRef;
   }

   public void setDimensionVisId(String dimensionVisId) {
      this.mDimensionVisId = dimensionVisId;
   }

   public void setDimensionDescription(String dimensionDescription) {
      this.mDimensionDescription = dimensionDescription;
   }

   public void setDimensionType(int dimensionType) {
      this.mDimensionType = dimensionType;
   }

   public void setPathVisId(String pathVisId) {
      this.mPathVisId = pathVisId;
   }

   public MappedDimensionPK getKey() {
      return this.mKey;
   }

   public void setKey(MappedDimensionPK key) {
      this.mKey = key;
   }

   public List<MappedHierarchy> getMappedHierarchies() {
      return this.mMappedHierarchies;
   }

   public void setMappedHierarchies(List<MappedHierarchy> mappedHierarchies) {
      this.mMappedHierarchies = mappedHierarchies;
   }

   public Object clone() throws CloneNotSupportedException {
      MappedDimensionImpl copy = (MappedDimensionImpl)super.clone();
      copy.mKey = this.mKey;
      copy.mMappedModel = this.mMappedModel;
      copy.mDimensionRef = this.mDimensionRef;
      copy.mDimensionVisId = this.mDimensionVisId;
      copy.mDimensionDescription = this.mDimensionDescription;
      copy.mDimensionType = this.mDimensionType;
      copy.mPathVisId = this.mPathVisId;
      copy.mMappedHierarchies = new ArrayList(this.mMappedHierarchies);
      copy.mMappedDimensionElements = new TreeSet(this.mMappedDimensionElements);
      copy.mIsDisabledLeafNodesExcluded = this.mIsDisabledLeafNodesExcluded;
      return copy;
   }

   public MappedHierarchyImpl findMappedHierarchy(Object key) {
      if(this.mMappedHierarchies == null) {
         return null;
      } else {
         Iterator i$ = this.mMappedHierarchies.iterator();

         MappedHierarchy mh;
         do {
            if(!i$.hasNext()) {
               return null;
            }

            mh = (MappedHierarchy)i$.next();
         } while(!mh.getKey().equals(key));

         return (MappedHierarchyImpl)mh;
      }
   }

   public MappedHierarchyImpl findMappedHierarchy(MappingKey financeHierarchyKey) {
      if(this.mMappedHierarchies == null) {
         return null;
      } else {
         Iterator i$ = this.mMappedHierarchies.iterator();

         MappedHierarchy mh;
         do {
            if(!i$.hasNext()) {
               return null;
            }

            mh = (MappedHierarchy)i$.next();
         } while(!mh.getFinanceHierarchyKey().equals(financeHierarchyKey));

         return (MappedHierarchyImpl)mh;
      }
   }

   public MappedDimensionElement findMappedDimensionElement(Object key) {
      Iterator i$ = this.mMappedDimensionElements.iterator();

      MappedDimensionElement mde;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         mde = (MappedDimensionElement)i$.next();
      } while(!mde.getKey().equals(key));

      return mde;
   }

   public SortedSet<MappedDimensionElement> getMappedDimensionElements() {
      return this.mMappedDimensionElements;
   }

   public void setMappedDimensionElements(SortedSet<MappedDimensionElement> mappedDimensionElements) {
      this.mMappedDimensionElements = mappedDimensionElements;
   }

   public String toString() {
      return this.mDimensionDescription;
   }

   public boolean equals(Object obj) {
      return obj == this?true:(obj instanceof MappedDimension?((MappedDimension)obj).getKey().equals(this.mKey):false);
   }

   public int hashCode() {
      return this.mKey != null?this.mKey.hashCode():0;
   }

   public int getNextKey() {
      return ((MappedModelImpl)this.mMappedModel).getNextKey();
   }

   public boolean isResponsibilityAreaDimension() {
      Iterator i$ = this.mMappedHierarchies.iterator();

      MappedHierarchy mh;
      do {
         if(!i$.hasNext()) {
            return false;
         }

         mh = (MappedHierarchy)i$.next();
      } while(!mh.isResponsibilityAreaHierarchy());

      return true;
   }

   public void resetResponsibilityAreaHierarchyFlag() {
      Iterator i$ = this.mMappedHierarchies.iterator();

      while(i$.hasNext()) {
         MappedHierarchy mh = (MappedHierarchy)i$.next();
         if(mh.isResponsibilityAreaHierarchy()) {
            ((MappedHierarchyImpl)mh).setResponsibilityAreaHierarchy(false);
         }
      }

   }

   public void writeXml(Writer out) throws IOException {
      out.write(" <mappedDimension ");
      XmlUtils.outputAttribute(out, "dimensionVisId", this.mDimensionVisId);
      XmlUtils.outputAttribute(out, "dimensionDescription", this.mDimensionDescription);
      XmlUtils.outputAttribute(out, "dimensionType", Integer.valueOf(this.mDimensionType));
      XmlUtils.outputAttribute(out, "pathVisId", this.mPathVisId);
      XmlUtils.outputAttribute(out, "isDisabledLeafNodesExcluded", Boolean.valueOf(this.mIsDisabledLeafNodesExcluded));
      XmlUtils.outputAttribute(out, "nullDimensionElementVisId", this.mNullDimensionElementVisId);
      XmlUtils.outputAttribute(out, "nullDimensionElementDescription", this.mNullDimensionElementDescription);
      XmlUtils.outputAttribute(out, "nullDimensionElementCreditDebit", this.mNullDimensionElementCreditDebit);
      out.write(" >\n ");
      XmlUtils.outputElement(out, "mappedHierarchies", (Collection)this.mMappedHierarchies);
      XmlUtils.outputElement(out, "mappedDimensionElements", (Collection) this.mMappedDimensionElements);
      out.write(" </mappedDimension>\n");
   }

   public String getNullDimensionElementVisId() {
      return this.mNullDimensionElementVisId;
   }

   public void setNullDimensionElementVisId(String nullDimensionElementVisId) {
      this.mNullDimensionElementVisId = nullDimensionElementVisId;
   }

   public String getNullDimensionElementDescription() {
      return this.mNullDimensionElementDescription;
   }

   public void setNullDimensionElementDescription(String nullDimensionElementDescription) {
      this.mNullDimensionElementDescription = nullDimensionElementDescription;
   }

   public Integer getNullDimensionElementCreditDebit() {
      return this.mNullDimensionElementCreditDebit;
   }

   public void setNullDimensionElementCreditDebit(Integer nullDimensionElementCreditDebit) {
      this.mNullDimensionElementCreditDebit = nullDimensionElementCreditDebit;
   }

   public boolean isDisabledLeafNodesExcluded() {
      return this.mIsDisabledLeafNodesExcluded;
   }

   public void setDisabledLeafNodesExcluded(boolean disabledLeafNodesExcluded) {
      this.mIsDisabledLeafNodesExcluded = disabledLeafNodesExcluded;
   }
}
