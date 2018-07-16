// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.udwp;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingProfile;
import com.cedar.cp.api.model.udwp.WeightingProfileEditor;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorUtils;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.udwp.WeightingProfileEditorSessionSSO;
import com.cedar.cp.dto.model.udwp.WeightingProfileImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.model.udwp.WeightingProfileAdapter;
import com.cedar.cp.impl.model.udwp.WeightingProfileEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightingProfileEditorImpl extends BusinessEditorImpl implements WeightingProfileEditor {

   private CalendarEditorUtils mCalendarEditorUtils = new CalendarEditorUtils();
   private static Map<Integer, int[]> sValidMapStates = new HashMap();
   private List<DataTypeRef> mAvailableDataTypes;
   private WeightingProfileEditorSessionSSO mServerSessionData;
   private WeightingProfileImpl mEditorData;
   private WeightingProfileAdapter mEditorDataAdapter;


   public WeightingProfileEditorImpl(WeightingProfileEditorSessionImpl session, WeightingProfileEditorSessionSSO serverSessionData, WeightingProfileImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(WeightingProfileEditorSessionSSO serverSessionData, WeightingProfileImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setModelId(int newModelId) throws ValidationException {
      this.validateModelId(newModelId);
      if(this.mEditorData.getModelId() != newModelId) {
         this.setContentModified();
         this.mEditorData.setModelId(newModelId);
      }
   }

   public void setStartLevel(int newStartLevel) throws ValidationException {
      this.validateStartLevel(newStartLevel);
      if(this.mEditorData.getStartLevel() != newStartLevel) {
         this.createWeightingRows(newStartLevel, this.mEditorData.getLeafLevel());
         this.setContentModified();
         this.mEditorData.setStartLevel(newStartLevel);
      }
   }

   public void setLeafLevel(int newLeafLevel) throws ValidationException {
      this.validateLeafLevel(newLeafLevel);
      if(this.mEditorData.getLeafLevel() != newLeafLevel) {
         this.createWeightingRows(this.mEditorData.getStartLevel(), newLeafLevel);
         this.setContentModified();
         this.mEditorData.setLeafLevel(newLeafLevel);
      }
   }

   public void setProfileType(int newProfileType) throws ValidationException {
      this.validateProfileType(newProfileType);
      if(this.mEditorData.getProfileType() != newProfileType) {
         this.setContentModified();
         this.mEditorData.setProfileType(newProfileType);
      }
   }

   public void setDynamicOffset(int newDynamicOffset) throws ValidationException {
      this.validateDynamicOffset(newDynamicOffset);
      if(this.mEditorData.getDynamicOffset() != newDynamicOffset) {
         this.setContentModified();
         this.mEditorData.setDynamicOffset(newDynamicOffset);
      }
   }

   public void setVisId(String newVisId) throws ValidationException {
      if(newVisId != null) {
         newVisId = StringUtils.rtrim(newVisId);
      }

      this.validateVisId(newVisId);
      if(this.mEditorData.getVisId() == null || !this.mEditorData.getVisId().equals(newVisId)) {
         this.setContentModified();
         this.mEditorData.setVisId(newVisId);
      }
   }

   public void setDescription(String newDescription) throws ValidationException {
      if(newDescription != null) {
         newDescription = StringUtils.rtrim(newDescription);
      }

      this.validateDescription(newDescription);
      if(this.mEditorData.getDescription() == null || !this.mEditorData.getDescription().equals(newDescription)) {
         this.setContentModified();
         this.mEditorData.setDescription(newDescription);
      }
   }

   public void setDynamicDataTypeId(Integer newDynamicDataTypeId) throws ValidationException {
      this.validateDynamicDataTypeId(newDynamicDataTypeId);
      if(this.mEditorData.getDynamicDataTypeId() == null || !this.mEditorData.getDynamicDataTypeId().equals(newDynamicDataTypeId)) {
         this.setContentModified();
         this.mEditorData.setDynamicDataTypeId(newDynamicDataTypeId);
      }
   }

   public void setDynamicEsIfWfbtoz(Boolean newDynamicEsIfWfbtoz) throws ValidationException {
      this.validateDynamicEsIfWfbtoz(newDynamicEsIfWfbtoz);
      if(this.mEditorData.getDynamicEsIfWfbtoz() == null || !this.mEditorData.getDynamicEsIfWfbtoz().equals(newDynamicEsIfWfbtoz)) {
         this.setContentModified();
         this.mEditorData.setDynamicEsIfWfbtoz(newDynamicEsIfWfbtoz);
      }
   }

   public void validateModelId(int newModelId) throws ValidationException {}

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a WeightingProfile");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a WeightingProfile");
      }
   }

   public void validateStartLevel(int newStartLevel) throws ValidationException {
      if(newStartLevel != 100 && newStartLevel != 0 && newStartLevel != 1 && newStartLevel != 2 && newStartLevel != 3 && newStartLevel != 4) {
         throw new ValidationException("Invalid calendar start level:" + newStartLevel);
      }
   }

   public void validateLeafLevel(int newLeafLevel) throws ValidationException {
      if(newLeafLevel != 0 && newLeafLevel != 1 && newLeafLevel != 2 && newLeafLevel != 3 && newLeafLevel != 4 && newLeafLevel != 5) {
         throw new ValidationException("Invalid calendar leaf level:" + newLeafLevel);
      }
   }

   public void validateProfileType(int newProfileType) throws ValidationException {
      if(newProfileType != 1 && newProfileType != 0 && newProfileType != 2 && newProfileType != 3) {
         throw new ValidationException("Invalid profile type:" + newProfileType);
      }
   }

   public void validateDynamicOffset(int newDynamicOffset) throws ValidationException {}

   public void validateDynamicDataTypeId(Integer newDynamicDataTypeId) throws ValidationException {}

   public void validateDynamicEsIfWfbtoz(Boolean newDynamicEsIfWfbtoz) throws ValidationException {}

   public void setModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getModelRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getModelRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setModelRef(actualRef);
      this.setContentModified();
      EntityList calSpecForModel = this.getConnection().getListHelper().getCalendarSpecForModel(((ModelPK)actualRef.getPrimaryKey()).getModelId());
      if(calSpecForModel.getNumRows() != 1) {
         throw new ValidationException("Unable to locate calendar spec from model:" + actualRef);
      } else {
         this.mEditorData.setYearStartMonth(((Integer)calSpecForModel.getValueAt(0, "YearStartMonth")).intValue());
         this.createWeightingRows(this.mEditorData.getStartLevel(), this.mEditorData.getLeafLevel());
      }
   }

   public EntityList getOwnershipRefs() {
      return ((WeightingProfileEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public WeightingProfile getWeightingProfile() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new WeightingProfileAdapter((WeightingProfileEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      boolean found = false;
      int[] leafs = this.getValidLeaves(this.mEditorData.getStartLevel());
      int[] arr$ = leafs;
      int len$ = leafs.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int leaf = arr$[i$];
         if(leaf == this.mEditorData.getLeafLevel()) {
            found = true;
            break;
         }
      }

      if(!found) {
         throw new ValidationException("Invalid start level and leaf selection.");
      }
   }

   public void setWeighting(int index, int value) throws ValidationException {
      int currentValue = this.mEditorData.getWeighting(index);
      if(currentValue != value) {
         this.mEditorData.setWeighting(index, value);
         this.setContentModified();
      }

   }

   private void createWeightingRows(int startLevel, int leafLevel) throws ValidationException {
      List leaves = this.mCalendarEditorUtils.queryWeightingElements(startLevel, leafLevel, this.mEditorData.getYearStartMonth());
      Object[][] weightingData = new Object[leaves.size()][3];

      for(int row = 0; row < leaves.size(); ++row) {
         HierarchyElementImpl leaf = (HierarchyElementImpl)leaves.get(row);
         weightingData[row][0] = leaf.getVisId();
         weightingData[row][1] = leaf.getDescription();
         weightingData[row][2] = Integer.valueOf(0);
      }

      this.mEditorData.setWeightingInfo(weightingData);
   }

   public void setDynamicDataType(DataTypeRef dataTypeRef) throws ValidationException {
      this.mEditorData.setDynamicDataType(dataTypeRef);
      this.setContentModified();
   }

   public List<DataTypeRef> getAvailableDataTypes() {
      if(this.mAvailableDataTypes == null) {
         this.mAvailableDataTypes = new ArrayList();
         EntityList dataTypes = this.getConnection().getListHelper().getAllDataTypes();

         for(int row = 0; row < dataTypes.getNumRows(); ++row) {
            this.mAvailableDataTypes.add((DataTypeRef)dataTypes.getValueAt(row, "DataType"));
         }
      }

      return this.mAvailableDataTypes;
   }

   public int[] getValidLeaves(int startElement) {
      return (int[])sValidMapStates.get(Integer.valueOf(startElement));
   }

   static {
      sValidMapStates.put(Integer.valueOf(0), new int[]{1, 2, 3, 4, 5});
      sValidMapStates.put(Integer.valueOf(1), new int[]{2, 3, 5});
      sValidMapStates.put(Integer.valueOf(2), new int[]{3, 5});
      sValidMapStates.put(Integer.valueOf(3), new int[]{5});
      sValidMapStates.put(Integer.valueOf(4), new int[]{5});
   }
}
