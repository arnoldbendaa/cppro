// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.amm;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.amm.AmmDimensionMapping;
import com.cedar.cp.api.model.amm.AmmFinanceCubeMapping;
import com.cedar.cp.api.model.amm.AmmModel;
import com.cedar.cp.api.model.amm.AmmModelEditor;
import com.cedar.cp.api.model.amm.AmmModelEditorSession;
import com.cedar.cp.api.model.amm.AmmModelRef;
import com.cedar.cp.api.model.amm.DimensionElementDTO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.amm.AmmModelEditorSessionSSO;
import com.cedar.cp.dto.model.amm.AmmModelImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SwingUtils;
import com.cedar.cp.impl.model.amm.AmmModelAdapter;
import com.cedar.cp.impl.model.amm.AmmModelEditorSessionImpl;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

public class AmmModelEditorImpl extends BusinessEditorImpl implements AmmModelEditor {

   private AmmModelEditorSessionSSO mServerSessionData;
   private AmmModelImpl mEditorData;
   private AmmModelAdapter mEditorDataAdapter;


   public AmmModelEditorImpl(AmmModelEditorSessionImpl session, AmmModelEditorSessionSSO serverSessionData, AmmModelImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(AmmModelEditorSessionSSO serverSessionData, AmmModelImpl editorData) {
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

   public void setSrcModelId(int newSrcModelId) throws ValidationException {
      this.validateSrcModelId(newSrcModelId);
      if(this.mEditorData.getSrcModelId() != newSrcModelId) {
         this.setContentModified();
         this.mEditorData.setSrcModelId(newSrcModelId);
      }
   }

   public void setInvalidatedByTaskId(Integer newInvalidatedByTaskId) throws ValidationException {
      this.validateInvalidatedByTaskId(newInvalidatedByTaskId);
      if(this.mEditorData.getInvalidatedByTaskId() == null || !this.mEditorData.getInvalidatedByTaskId().equals(newInvalidatedByTaskId)) {
         this.setContentModified();
         this.mEditorData.setInvalidatedByTaskId(newInvalidatedByTaskId);
      }
   }

   public void validateModelId(int newModelId) throws ValidationException {}

   public void validateSrcModelId(int newSrcModelId) throws ValidationException {}

   public void validateInvalidatedByTaskId(Integer newInvalidatedByTaskId) throws ValidationException {}

   public void setTargetModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getTargetModelRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getTargetModelRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      if(actualRef != null) {
         ModelPK pk = (ModelPK)actualRef.getPrimaryKey();
         this.mEditorData.setModelId(pk.getModelId());
      }

      this.mEditorData.setTargetModelRef(actualRef);
      this.setContentModified();
   }

   public void setSourceModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getSourceModelRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getSourceModelRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      if(actualRef != null) {
         ModelPK pk = (ModelPK)actualRef.getPrimaryKey();
         this.mEditorData.setSrcModelId(pk.getModelId());
      }

      this.mEditorData.setSourceModelRef(actualRef);
      this.setContentModified();
   }

   public AmmModel getAmmModel() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new AmmModelAdapter((AmmModelEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public void setModelLocked(boolean b) {
      this.mEditorData.setModelLocked(b);
   }

   public void setDimsLocked(boolean b) {
      this.mEditorData.setDimsLocked(b);
   }

   public void addMappedDimMapping(DimensionRef ref, DimensionRef sourceDimensionRef, HierarchyRef sourceHierarchyRef) throws ValidationException {
      this.mEditorData.addMappedDimMapping(ref, sourceDimensionRef, sourceHierarchyRef);
   }

   public void addUnmappedDimMapping(DimensionRef ref) throws ValidationException {
      this.mEditorData.addUnmappedDimMapping(ref);
   }

   public void addUnmappedSourceDimMapping(DimensionRef ref, HierarchyRef hierRef) throws ValidationException {
      this.mEditorData.addUnmappedSourceDimMapping(ref, hierRef);
   }

   public void addCalDimMapping(DimensionRef ref, DimensionRef sourceDimensionRef, HierarchyRef sourceHierarchyRef) throws ValidationException {
      this.mEditorData.addCalDimMapping(ref, sourceDimensionRef, sourceHierarchyRef);
   }

   public void setFinanceCubeMappings(List<AmmFinanceCubeMapping> data) {
      this.mEditorData.setFinanceCubeMappings(data);
   }

   public void setSourceCalInfo(CalendarInfo info) {
      this.mEditorData.setSourceInfo(info);
   }

   public List<TreeNode> searchTree(int dimId, String text, TreeModel tm) {
      EntityList el = this.getConnection().getListHelper().doElementPickerSearch(dimId, text);
      return SwingUtils.locateNodesInTree(tm, el);
   }

   public Map<DimensionElementRef, List<StructureElementRef>> autoMap(List data, int sourceHierarchyId) {
      HashMap result = new HashMap();
      EntityList el = this.getConnection().getListHelper().getAllLeafStructureElements(sourceHierarchyId);
      Iterator i$ = data.iterator();

      while(i$.hasNext()) {
         Object dimObj = i$.next();
         DimensionElementDTO dimElementDTO = (DimensionElementDTO)dimObj;
         int size = el.getNumRows();

         for(int i = 0; i < size; ++i) {
            StructureElementRef element = (StructureElementRef)el.getValueAt(i, "StructureElement");
            if(dimElementDTO.getDimElement().getNarrative().equals(element.getNarrative())) {
               ArrayList elems = new ArrayList();
               elems.add(element);
               result.put(dimElementDTO.getDimElement(), elems);
               break;
            }
         }
      }

      return result;
   }

   public Map<DimensionElementRef, List<StructureElementRef>> copyFrom(EntityList selectedMapping, List data, int sourceDimensionId) {
      HashMap result = new HashMap();
      Map copyFromMap = null;
      AmmModelRef ref = (AmmModelRef)selectedMapping.getValueAt(0, "AmmModel");

      try {
         AmmModelEditorSession structureElem = this.getConnection().getAmmModelsProcess().getAmmModelEditorSession(ref.getPrimaryKey());
         AmmModel structSize = structureElem.getAmmModelEditor().getAmmModel();
         Iterator i = structSize.getDimMappings().iterator();

         while(i.hasNext()) {
            AmmDimensionMapping key = (AmmDimensionMapping)i.next();
            if(key.getType().intValue() == 0) {
               copyFromMap = key.getSelectedElementData();
               break;
            }
         }
      } catch (Exception var18) {
         var18.printStackTrace();
      }

      if(copyFromMap == null) {
         return result;
      } else {
         EntityList var19 = this.getConnection().getListHelper().getAllStructureElements(sourceDimensionId);
         int var20 = var19.getNumRows();
         int var21 = 0;

         while(var21 < data.size()) {
            DimensionElementDTO var22 = (DimensionElementDTO)data.get(var21);
            DimensionElementRef copyKey = null;
            Iterator values = copyFromMap.keySet().iterator();

            while(true) {
               if(values.hasNext()) {
                  DimensionElementRef copyValues = (DimensionElementRef)values.next();
                  if(!copyValues.getNarrative().equals(var22.getDimElement().getNarrative())) {
                     continue;
                  }

                  copyKey = copyValues;
               }

               if(copyKey != null) {
                  ArrayList var23 = new ArrayList();
                  List var24 = (List)copyFromMap.get(copyKey);
                  Iterator i$ = var24.iterator();

                  while(i$.hasNext()) {
                     StructureElementRef copyValueRef = (StructureElementRef)i$.next();

                     for(int j = 0; j < var20; ++j) {
                        StructureElementRef element = (StructureElementRef)var19.getValueAt(j, "StructureElement");
                        if(copyValueRef.getNarrative().equals(element.getNarrative())) {
                           var23.add(element);
                           break;
                        }
                     }
                  }

                  if(var23.size() > 0) {
                     result.put(var22.getDimElement(), var23);
                  }
               }

               ++var21;
               break;
            }
         }

         return result;
      }
   }

   public Map<CalendarElementNode, List<StructureElementRef>> autoMapCal(List data, CalendarInfo sourceInfo) {
      HashMap result = new HashMap();
      Iterator i$ = data.iterator();

      while(i$.hasNext()) {
         Object nodeObj = i$.next();
         CalendarElementNode targetNode = (CalendarElementNode)nodeObj;
         Enumeration e = sourceInfo.getRoot().postorderEnumeration();

         while(e.hasMoreElements()) {
            CalendarElementNode sourceNode = (CalendarElementNode)e.nextElement();
            if(sourceNode.isLeaf() && sourceNode.getFullPathVisId().equals(targetNode.getFullPathVisId())) {
               ArrayList elems = new ArrayList();
               elems.add((StructureElementRef)sourceNode.getStructureElementRef());
               result.put(targetNode, elems);
               break;
            }
         }
      }

      return result;
   }

   public Map<CalendarElementNode, List<StructureElementRef>> copyFromCal(EntityList selectedMapping, List data, CalendarInfo sourceInfo) {
      HashMap result = new HashMap();
      Map copyFromMap = null;
      AmmModelRef ref = (AmmModelRef)selectedMapping.getValueAt(0, "AmmModel");
      CalendarInfo copyFromValueInfo = null;

      try {
         AmmModelEditorSession i$ = this.getConnection().getAmmModelsProcess().getAmmModelEditorSession(ref.getPrimaryKey());
         AmmModel nodeObj = i$.getAmmModelEditor().getAmmModel();
         copyFromValueInfo = i$.getAmmModelEditor().getAmmModel().getSourceInfo();
         Iterator targetNode = nodeObj.getDimMappings().iterator();

         while(targetNode.hasNext()) {
            AmmDimensionMapping copyKey = (AmmDimensionMapping)targetNode.next();
            if(copyKey.getType().intValue() == 4) {
               copyFromMap = copyKey.getSelectedCalanderElementData();
               break;
            }
         }
      } catch (Exception var19) {
         var19.printStackTrace();
      }

      if(copyFromMap == null) {
         return result;
      } else {
         Iterator i$2 = data.iterator();

         while(i$2.hasNext()) {
            Object nodeObj1 = i$2.next();
            CalendarElementNode targetNode1 = (CalendarElementNode)nodeObj1;
            CalendarElementNode copyKey1 = null;
            Iterator values = copyFromMap.keySet().iterator();

            while(true) {
               if(values.hasNext()) {
                  CalendarElementNode copyValues = (CalendarElementNode)values.next();
                  if(!copyValues.getFullPathVisId().equals(targetNode1.getFullPathVisId())) {
                     continue;
                  }

                  copyKey1 = copyValues;
               }

               if(copyKey1 == null) {
                  break;
               }

               ArrayList values1 = new ArrayList();
               List copyValues1 = (List)copyFromMap.get(copyKey1);
               Iterator i$1 = copyValues1.iterator();

               while(i$1.hasNext()) {
                  StructureElementRef copyValueRef = (StructureElementRef)i$1.next();
                  CalendarElementNode nodeCheck = copyFromValueInfo.getById(copyValueRef);
                  Enumeration e = sourceInfo.getRoot().postorderEnumeration();

                  while(e.hasMoreElements()) {
                     CalendarElementNode sourceNode = (CalendarElementNode)e.nextElement();
                     if(sourceNode.getFullPathVisId().equals(nodeCheck.getFullPathVisId())) {
                        values1.add((StructureElementRef)sourceNode.getStructureElementRef());
                        break;
                     }
                  }
               }

               if(values1.size() > 0) {
                  result.put(targetNode1, values1);
               }
               break;
            }
         }

         return result;
      }
   }
}
