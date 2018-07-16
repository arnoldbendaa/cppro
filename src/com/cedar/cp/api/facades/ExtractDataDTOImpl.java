// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.facades;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.facades.ExtractDataDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractDataDTOImpl implements ExtractDataDTO {

   private Map<String, Map<String, String>> mElementDescriptionMaps = new HashMap();
   private EntityList mCellData;
   private int mModelId;
   private int mFinanceCubeId;
   private int mNumDims;
   private int[] mHierarchyIds;
   private String[] mHierarchyVisIds;
   private List<String[]> mCellKeys;
   private String mCompany;


   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubId) {
      this.mFinanceCubeId = financeCubId;
   }

   public int getNumDims() {
      return this.mNumDims;
   }

   public void setNumDims(int numDims) {
      this.mNumDims = numDims;
   }

   public int[] getHierarchyIds() {
      return this.mHierarchyIds;
   }

   public void setHierarchyIds(int[] hierarchyIds) {
      this.mHierarchyIds = hierarchyIds;
   }

   public List<String[]> getCellKeys() {
      return this.mCellKeys;
   }

   public void setCellKeys(List<String[]> cellKeys) {
      this.mCellKeys = cellKeys;
   }

   public EntityList getCellData() {
      return this.mCellData;
   }

   public void setCellData(EntityList cellData) {
      this.mCellData = cellData;
   }

   public void addElementDescriptionLookup(String hierarchyVisId, String elementVisId) {
      Map hierarchyElementMap = (Map)this.mElementDescriptionMaps.get(hierarchyVisId);
      if(hierarchyElementMap == null) {
         hierarchyElementMap = new HashMap();
         this.mElementDescriptionMaps.put(hierarchyVisId, hierarchyElementMap);
      }

      ((Map)hierarchyElementMap).put(elementVisId, (Object)null);
   }

   public void setElementDescriptionLookup(String hierarchyVisId, String elementVisId, String description) {
      Map hierarchyElementMap = (Map)this.mElementDescriptionMaps.get(hierarchyVisId);
      if(hierarchyElementMap == null) {
         this.addElementDescriptionLookup(hierarchyVisId, elementVisId);
         hierarchyElementMap = (Map)this.mElementDescriptionMaps.get(hierarchyVisId);
      }

      hierarchyElementMap.put(elementVisId, description);
   }

   public String getElementDescriptionLookup(String hierarchyVisId, String elementVisId) {
      Map hierarchyElementMap = (Map)this.mElementDescriptionMaps.get(hierarchyVisId);
      return hierarchyElementMap != null?(String)hierarchyElementMap.get(elementVisId):null;
   }

   public boolean isElementDescriptionLookupRequired() {
      return !this.mElementDescriptionMaps.isEmpty();
   }

   public Map<String, String> getElementMapForHierarchy(String hierarchyVisId) {
      return (Map)this.mElementDescriptionMaps.get(hierarchyVisId);
   }

   public Map<String, Map<String, String>> getHierarchyElementMaps() {
      return this.mElementDescriptionMaps;
   }

   public String[] getHierarchyVisIds() {
      return this.mHierarchyVisIds;
   }

   public void setHierarchyVisIds(String[] hierarchyVisIds) {
      this.mHierarchyVisIds = hierarchyVisIds;
   }

	public String getCompany() {
		return mCompany;
	}
	
	public void setCompany(String mCompany) {
		this.mCompany = mCompany;
	}
}
