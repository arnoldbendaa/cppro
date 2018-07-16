// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.udwp.Profile;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.api.model.udwp.WeightingProfilesProcess;
import com.cedar.cp.api.model.virement.VirementAuthPoint;
import com.cedar.cp.api.model.virement.VirementGroup;
import com.cedar.cp.api.model.virement.VirementGroupEditor;
import com.cedar.cp.api.model.virement.VirementLine;
import com.cedar.cp.api.model.virement.VirementLineEditor;
import com.cedar.cp.api.model.virement.VirementLineSpread;
import com.cedar.cp.api.model.virement.VirementRequest;
import com.cedar.cp.api.model.virement.VirementRequestEditor;
import com.cedar.cp.api.model.virement.VirementRequestEditorSession;
import com.cedar.cp.api.model.virement.VirementRequestsProcess;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPRoleSecurityException;
import com.cedar.cp.utc.picker.ElementDTO;
import com.cedar.cp.utc.struts.virement.DataTypeDTO;
import com.cedar.cp.utc.struts.virement.SpreadProfileDTO;
import com.cedar.cp.utc.struts.virement.UserDTO;
import com.cedar.cp.utc.struts.virement.VirementAuthPointDTO;
import com.cedar.cp.utc.struts.virement.VirementBaseAction$1;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO;
import com.cedar.cp.utc.struts.virement.VirementDataEntryForm;
import com.cedar.cp.utc.struts.virement.VirementGroupDTO;
import com.cedar.cp.utc.struts.virement.VirementLineDTO;
import com.cedar.cp.utc.struts.virement.VirementLineSpreadDTO;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

public class VirementBaseAction extends CPAction {

   private NumberFormat mNumberFormat;


   protected Object saveRequest(VirementDataEntryDTO dto, HttpServletRequest httpServletRequest) throws ValidationException, CPRoleSecurityException {
      CPConnection con = this.getCPContext(httpServletRequest).getCPConnection();
      VirementRequestsProcess process = con.getVirementRequestsProcess();
      Object key = !dto.isNewKey() && dto.getKey() != null && dto.getKey().length() > 0?con.getEntityKeyFactory().getKeyFromTokens(dto.getKey()):null;
      VirementRequestEditorSession session = process.getVirementRequestEditorSession(key);

      try {
         VirementRequestEditor editor = session.getVirementRequestEditor();
         VirementRequest request = editor.getVirementRequest();
         editor.setOwningUserRef(con.getUserContext().getUserId(), con.getUserContext().getUserName());
         editor.setReason(dto.getReason());
         editor.setReference(dto.getReference());
         editor.setModelRef(dto.getModelId(), dto.getModelVisId());
         editor.setFinanceCubeRef(dto.getFinanceCubeId(), dto.getFinanceCubeVisId());
         editor.setBudgetCycleRef(dto.getBudgetCycleId(), dto.getBudgetCycleVisId());
         ArrayList groupsToRemove = new ArrayList(editor.getVirementRequest().getVirementGroups());
         Iterator gIter = dto.getGroups().iterator();

         while(gIter.hasNext()) {
            VirementGroupDTO group = (VirementGroupDTO)gIter.next();
            Object groupKey = editor.decodeGroupKey(group.getKey());
            VirementGroupEditor groupEditor = editor.getVirementRequestGroupEditor(groupKey);
            groupsToRemove.remove(groupEditor.getVirementGroup());
            groupEditor.setNotes(group.getNotes());
            ArrayList linesToRemove = new ArrayList(groupEditor.getVirementGroup().getRows());
            Iterator lIter = group.getLines().iterator();

            while(lIter.hasNext()) {
               VirementLineDTO line = (VirementLineDTO)lIter.next();
               Object lineKey = groupEditor.decodeLineKey(line.getKey());
               VirementLineEditor lineEditor = groupEditor.getEditor(lineKey);
               linesToRemove.remove(lineEditor.getVirementLine());
               ArrayList address = new ArrayList();
               Iterator eIter = line.getCells().iterator();

               while(eIter.hasNext()) {
                  ElementDTO profileKey = (ElementDTO)eIter.next();
                  address.add(new Object[]{new Integer(profileKey.getStructureId()), new Integer(profileKey.getId()), profileKey.getIdentifier()});
               }

               lineEditor.setAddress(address);
               lineEditor.setTo(line.isTo());
               lineEditor.setTransferValue(line.getTransferValueAsDouble());
               lineEditor.setDataTypeRef(this.lookupDataTypeRef(editor.getTransferDataTypes(), line.getDataTypeId()));
               String profileKey1 = line.getSpreadProfileId();
               if(profileKey1 != null && profileKey1.trim().length() == 0) {
                  profileKey1 = null;
               }

               lineEditor.setSpreadProfileKey(profileKey1);
               Iterator spIter = line.getSpreadProfile().iterator();

               while(spIter.hasNext()) {
                  VirementLineSpreadDTO spreadDTO = (VirementLineSpreadDTO)spIter.next();
                  lineEditor.setSpreadProfile(spreadDTO.getStructureElementKey(), spreadDTO.isHeld(), spreadDTO.getWeightingAsInt());
               }

               lineEditor.commit();
            }

            lIter = linesToRemove.iterator();

            while(lIter.hasNext()) {
               VirementLine line1 = (VirementLine)lIter.next();
               groupEditor.remove(request, line1.getKey());
            }

            groupEditor.commit();
         }

         gIter = groupsToRemove.iterator();

         while(gIter.hasNext()) {
            VirementGroup group1 = (VirementGroup)gIter.next();
            editor.remove(group1);
         }

         Object group2 = session.commit(false);
         return group2;
      } finally {
         if(process != null && session != null) {
            process.terminateSession(session);
         }

      }
   }

   private DataTypeRef lookupDataTypeRef(List<DataTypeRef> dataTypes, String dataTypeVisId) {
      Iterator i$ = dataTypes.iterator();

      DataTypeRef dtRef;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         dtRef = (DataTypeRef)i$.next();
      } while(!dtRef.getTokenizedKey().equals(dataTypeVisId));

      return dtRef;
   }

   protected VirementDataEntryDTO loadRequest(String requestIdStr, HttpServletRequest httpServletRequest) throws ValidationException, CPRoleSecurityException {
      CPConnection connection = this.getCPContext(httpServletRequest).getCPConnection();
      Object key = connection.getEntityKeyFactory().getKeyFromTokens(requestIdStr);
      return this.loadRequest(key, httpServletRequest);
   }

   protected VirementDataEntryDTO loadRequest(Object key, HttpServletRequest httpServletRequest) throws ValidationException, CPRoleSecurityException {
      CPConnection connection = this.getCPContext(httpServletRequest).getCPConnection();
      VirementRequestsProcess process = connection.getVirementRequestsProcess();
      VirementRequestEditorSession session = process.getVirementRequestEditorSession(key);

      VirementDataEntryDTO apIter2;
      try {
         VirementRequestEditor editor = session.getVirementRequestEditor();
         VirementRequest req = editor.getVirementRequest();
         CalendarInfo calendarInfo = connection.getHierarchysProcess().getCalendarInfoForModel(Integer.valueOf(req.getModelId()));
         VirementDataEntryDTO dto = new VirementDataEntryDTO();
         dto.setOwner(req.getOwningUserRef().getNarrative());
         dto.setTransferId(req.getRequestId());
         dto.setModelId(req.getModelId());
         dto.setModelVisId(req.getModelRef().getNarrative());
         dto.setFinanceCubeId(req.getFinanceCubeId());
         dto.setFinanceCubeVisId(req.getFinanceCubeRef().getNarrative());
         dto.setBudgetCycleId(req.getBudgetCycleId());
         dto.setBudgetCycleVisId(req.getBudgetCycleRef().getNarrative());
         dto.setReason(req.getReason());
         dto.setReference(req.getReference());
         dto.setKey(req.getRequestRef().getTokenizedKey());
         dto.setDataTypes(this.convertToDataTypeDTOs(editor.getTransferDataTypes()));
         Iterator currentUser = req.getVirementGroups().iterator();

         while(currentUser.hasNext()) {
            VirementGroup apIter = (VirementGroup)currentUser.next();
            VirementGroupDTO authPoint = new VirementGroupDTO();
            authPoint.setNotes(apIter.getNotes());
            authPoint.setKey(apIter.getKeyAsText());
            Iterator authPointDTO = apIter.getRows().iterator();

            while(authPointDTO.hasNext()) {
               VirementLine line = (VirementLine)authPointDTO.next();
               VirementLineDTO lineDTO = new VirementLineDTO();
               lineDTO.setAllocationThreshold(String.valueOf(line.getAllocationThreshold()));
               lineDTO.setKey(line.getKeyAsText());
               ArrayList address = new ArrayList();

               Iterator spIter;
               ElementDTO spreadDTO;
               for(spIter = line.getAddress().iterator(); spIter.hasNext(); address.add(spreadDTO)) {
                  StructureElementRef spread = (StructureElementRef)spIter.next();
                  spreadDTO = new ElementDTO();
                  spreadDTO.setKey(spread.getPrimaryKey());
                  if(!spIter.hasNext()) {
                     CalendarElementNode cen = calendarInfo.getById(spread);
                     if(cen != null) {
                        spreadDTO.setIdentifier(cen.getFullPathVisId());
                     } else {
                        spreadDTO.setIdentifier(spread.getNarrative());
                     }
                  } else {
                     spreadDTO.setIdentifier(spread.getNarrative());
                  }
               }

               lineDTO.setSpreadProfileId(line.getSpreadProfileKeyAsText());
               lineDTO.setSpreadProfileVisId(line.getSpreadProfileVisId());
               lineDTO.setSummaryLine(line.isSummaryLine());
               lineDTO.setCells(address);
               lineDTO.setTo(line.isTo());
               lineDTO.setTransferValue(this.getNumberFormatter().format(line.getTransferValue()));
               lineDTO.setDataTypeVisId(line.getDataTypeRef().getNarrative());
               lineDTO.setDataTypeId(line.getDataTypeRef().getTokenizedKey());
               authPoint.getLines().add(lineDTO);
               spIter = line.getSpreadProfile().iterator();

               while(spIter.hasNext()) {
                  VirementLineSpread spread1 = (VirementLineSpread)spIter.next();
                  VirementLineSpreadDTO spreadDTO1 = new VirementLineSpreadDTO(spread1.getKeyAsText(), spread1.getIndex(), spread1.isHeld(), String.valueOf(spread1.getWeighting()), spread1.getStructureElementRef().getTokenizedKey(), spread1.getStructureElementRef().getNarrative(), this.getNumberFormatter().format(spread1.getTransferValue()));
                  lineDTO.getSpreadProfile().add(spreadDTO1);
               }
            }

            dto.getGroups().add(authPoint);
         }

         UserRef currentUser1 = connection.getUserContext().getUserRef();
         Iterator apIter1 = req.getVirementAuthPoints().values().iterator();

         while(apIter1.hasNext()) {
            VirementAuthPoint authPoint1 = (VirementAuthPoint)apIter1.next();
            VirementAuthPointDTO authPointDTO1 = new VirementAuthPointDTO(new UserDTO(authPoint1.getAuthUser()), this.convertToUserDTOs(authPoint1.getAvailableAuthorisers()), new ElementDTO(authPoint1.getStructureElementRef()), authPoint1.getKey(), authPoint1.getKeyAsText(), this.convertToElementDTOs(authPoint1.getLines()), authPoint1.getNotes(), authPoint1.getStatus());
            authPointDTO1.setUserCanAuth(authPoint1.isCanUserAuth());
            dto.getAuthPoints().add(authPointDTO1);
         }

         dto.setDimensionHeaders(this.queryDimensionHeaders(httpServletRequest, dto.getModelId()));
         apIter2 = dto;
      } finally {
         if(process != null && session != null) {
            process.terminateSession(session);
         }

      }

      return apIter2;
   }

   private List convertToDataTypeDTOs(List<DataTypeRef> dataTypes) {
      ArrayList result = new ArrayList();
      Iterator i$ = dataTypes.iterator();

      while(i$.hasNext()) {
         DataTypeRef dtRef = (DataTypeRef)i$.next();
         result.add(new DataTypeDTO(dtRef));
      }

      Collections.sort(result, new VirementBaseAction$1(this));
      return result;
   }

   private List convertToUserDTOs(Collection userRefs) {
      ArrayList result = new ArrayList();
      Iterator uIter = userRefs.iterator();

      while(uIter.hasNext()) {
         result.add(new UserDTO((UserRef)uIter.next()));
      }

      return result;
   }

   private List convertToElementDTOs(Collection virementLines) {
      ArrayList result = new ArrayList();
      Iterator lIter = virementLines.iterator();

      while(lIter.hasNext()) {
         VirementLine line = (VirementLine)lIter.next();
         StructureElementRef raElement = (StructureElementRef)line.getAddress().get(0);
         result.add(new ElementDTO(raElement));
      }

      return result;
   }

   protected boolean updateAuthPoint(HttpServletRequest httpServletRequest, String requestIdStr, String authPointIdStr, String notes, boolean auth) throws CPRoleSecurityException, ValidationException {
      CPConnection connection = this.getCPContext(httpServletRequest).getCPConnection();
      Object key = connection.getEntityKeyFactory().getKeyFromTokens(requestIdStr);
      VirementRequestsProcess process = connection.getVirementRequestsProcess();
      VirementRequestEditorSession session = process.getVirementRequestEditorSession(key);

      boolean var12;
      try {
         VirementRequestEditor editor = session.getVirementRequestEditor();
         boolean result = false;
         if(auth) {
            result = editor.authorise(authPointIdStr, notes);
         } else {
            editor.reject(authPointIdStr, notes);
         }

         editor.commit();
         session.commit(false);
         var12 = result;
      } finally {
         if(process != null && session != null) {
            process.terminateSession(session);
         }

      }

      return var12;
   }

   protected List queryDimensionHeaders(HttpServletRequest httpServletRequest, int modelId) throws CPRoleSecurityException {
      CPConnection con = this.getCPContext(httpServletRequest).getCPConnection();
      EntityList dims = con.getListHelper().getAllDimensionsForModel(modelId);
      ArrayList dimHeaders = new ArrayList();

      for(int i = 0; i < dims.getNumRows(); ++i) {
         dimHeaders.add(dims.getValueAt(i, "Dimension"));
      }

      return dimHeaders;
   }

   protected boolean validateStructureElements(CPConnection con, HttpServletRequest request, VirementDataEntryForm form) {
      try {
         EntityList e = con.getListHelper().getAllDimensionsForModel(form.getData().getModelId());
         CalendarInfo var28 = con.getHierarchysProcess().getCalendarInfoForModel(Integer.valueOf(form.getData().getModelId()));
         EntityList systemProperty = con.getSystemPropertysProcess().getSystemProperty("VIRE: RESTRICT_BY_RA");
         boolean vireRestrictByRA = systemProperty.getNumRows() == 1 && Boolean.parseBoolean(systemProperty.getValueAt(0, "Value").toString());
         Iterator gIter = form.getGroups().iterator();

         while(gIter.hasNext()) {
            VirementGroupDTO group = (VirementGroupDTO)gIter.next();
            Iterator lIter = group.getLines().iterator();

            while(lIter.hasNext()) {
               VirementLineDTO line = (VirementLineDTO)lIter.next();
               int dimNo = 0;
               boolean anyElementChanged = false;

               for(Iterator cIter = line.getCells().iterator(); cIter.hasNext(); ++dimNo) {
                  ElementDTO dto = (ElementDTO)cIter.next();
                  int dimId = ((Integer)e.getValueAt(dimNo, "DimensionId")).intValue();
                  int structureElementId;
                  if(cIter.hasNext()) {
                     EntityList profiles = con.getListHelper().getStructureElementByVisId(dto.getIdentifier(), dimId);
                     if(profiles.getNumRows() == 0) {
                        DimensionRef profile = (DimensionRef)form.getData().getDimensionHeaders().get(dimNo);
                        throw new ValidationException("Element \'" + dto.getIdentifier() + "\' not found in dimension " + profile.getNarrative());
                     }

                     int structureId = ((Integer)profiles.getValueAt(0, "StructureId")).intValue();
                     structureElementId = ((Integer)profiles.getValueAt(0, "StructureElementId")).intValue();
                     StructureElementRef leaves = (StructureElementRef)profiles.getValueAt(0, "StructureElement");
                     if(dto.getId() != 0 && dto.getId() != structureElementId) {
                        anyElementChanged = true;
                     }

                     dto.setKey(leaves.getPrimaryKey());
                     dto.setId(structureElementId);
                     dto.setStructureId(structureId);
                     dto.setDescription((String)profiles.getValueAt(0, "Description"));
                     dto.setLeaf(((Boolean)profiles.getValueAt(0, "Leaf")).booleanValue());
                  } else {
                     CalendarElementNode var29 = var28.findElement(dto.getIdentifier());
                     if(var29 == null) {
                        throw new ValidationException("Unable to locate calendar element : " + dto.getIdentifier());
                     }

                     if(dto.getId() != 0 && dto.getId() != var29.getStructureElementId()) {
                        anyElementChanged = true;
                     }

                     dto.setId(structureElementId = var29.getStructureElementId());
                     dto.setIdentifier(var29.getFullPathVisId());
                     dto.setKey(var29.getKey());
                     dto.setDescription(var29.getDescription());
                     dto.setLeaf(var29.isLeaf());
                     dto.setStructureId(var28.getCalendarId());
                  }

                  if(dimNo == 0 && vireRestrictByRA && !con.getListHelper().hasUserAccessToRespArea(con.getUserContext().getUserId(), form.getData().getModelId(), structureElementId)) {
                     DimensionRef var31 = (DimensionRef)form.getData().getDimensionHeaders().get(dimNo);
                     String var35 = MessageFormat.format("You do not have access to responsibility area [{0} - {1}] in [{2}]", new Object[]{dto.getIdentifier(), dto.getDescription(), var31.getNarrative()});
                     throw new ValidationException(var35);
                  }

                  if(!cIter.hasNext()) {
                     line.setSummaryLine(!dto.isLeaf());
                     if(line.isSummaryLine() && (line.getSpreadProfile().isEmpty() || anyElementChanged)) {
                        EntityList var32 = con.getListHelper().getLeavesForParent(dto.getStructureId(), dto.getStructureId(), dto.getId(), dto.getStructureId(), dto.getId());
                        line.getSpreadProfile().clear();

                        for(int var30 = 0; var30 < var32.getNumRows(); ++var30) {
                           StructureElementRef var36 = (StructureElementRef)var32.getValueAt(var30, "StructureElement");
                           VirementLineSpreadDTO p = new VirementLineSpreadDTO((String)null, var30, false, "1", var36.getTokenizedKey(), var36.getNarrative(), "0.00");
                           line.getSpreadProfile().add(p);
                        }

                        if(line.getSpreadProfileId() == null || line.getSpreadProfileId().trim().length() == 0 || anyElementChanged) {
                           List var34 = this.queryWeightingProfiles(con, form.getData(), line, false);
                           if(var34.size() > 0) {
                              SpreadProfileDTO var37 = (SpreadProfileDTO)var34.get(0);
                              line.setSpreadProfileId(var37.getWeightingProfileRef().getTokenizedKey());
                              line.setSpreadProfileVisId(var37.getWeightingProfileRef().getNarrative());
                              Profile var33 = this.getWeightingProfile(con, form.getData(), line);
                              if(var33 != null) {
                                 int[] weightings = var33.getWeightings();
                                 List lineProfile = (List)line.getSpreadProfile();
                                 if(weightings != null) {
                                    for(int i = 0; i < weightings.length && i < lineProfile.size(); ++i) {
                                       VirementLineSpreadDTO spreadDTO = (VirementLineSpreadDTO)lineProfile.get(i);
                                       spreadDTO.setWeighting(String.valueOf(weightings[i]));
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return true;
      } catch (ValidationException var27) {
         ActionErrors errors = new ActionErrors();
         errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.virement.validation.error", var27.getMessage()));
         this.addErrors(request, errors);
         return false;
      }
   }

   protected List queryWeightingProfiles(CPConnection connection, VirementDataEntryDTO mainDTO, VirementLineDTO line, boolean addInDummy) throws ValidationException {
      List cells = (List)line.getCells();
      ElementDTO bCell = (ElementDTO)cells.get(0);
      ElementDTO aCell = (ElementDTO)cells.get(cells.size() - 2);
      ElementDTO cCell = (ElementDTO)cells.get(cells.size() - 1);
      WeightingProfilesProcess process = connection.getWeightingProfilesProcess();
      Object dataTypeKey = line.getDataTypeId();
      if(dataTypeKey == null) {
         dataTypeKey = new Integer(-1);
      }

      EntityList profiles = process.queryProfiles(Integer.valueOf(mainDTO.getModelId()), dataTypeKey, Integer.valueOf(aCell.getStructureId()), Integer.valueOf(aCell.getId()), Integer.valueOf(bCell.getStructureId()), Integer.valueOf(bCell.getId()), Integer.valueOf(cCell.getStructureId()), Integer.valueOf(cCell.getId()));
      ArrayList result = new ArrayList();

      for(int wpRef = 0; wpRef < profiles.getNumRows(); ++wpRef) {
         WeightingProfileRef wpRef1 = (WeightingProfileRef)profiles.getValueAt(wpRef, "WeightingProfile");
         String global = (String)profiles.getValueAt(wpRef, "Global");
         int profileType = ((Integer)profiles.getValueAt(wpRef, "ProfileType")).intValue();
         int startLevel = ((Integer)profiles.getValueAt(wpRef, "StartLevel")).intValue();
         int leafLevel = ((Integer)profiles.getValueAt(wpRef, "LeafLevel")).intValue();
         int dynamicOffset = ((Integer)profiles.getValueAt(wpRef, "DynamicOffset")).intValue();
         String dynamicDataType = (String)profiles.getValueAt(wpRef, "DynamicDataType");
         result.add(new SpreadProfileDTO(wpRef1, global.equalsIgnoreCase("Y"), profileType, startLevel, leafLevel, dynamicOffset, dynamicDataType));
      }

      if(addInDummy) {
         WeightingProfileRef var21 = connection.getWeightingProfilesProcess().getCustomWeightingProfile();
         result.add(new SpreadProfileDTO(var21, false, -1, -1, -1, -1, (String)null));
      }

      return result;
   }

   protected Profile getWeightingProfile(CPConnection connection, VirementDataEntryDTO virement, VirementLineDTO line) throws ValidationException {
      ArrayList addresses = new ArrayList();
      Iterator i$ = ((List)line.getCells()).iterator();

      while(i$.hasNext()) {
         ElementDTO eDTO = (ElementDTO)i$.next();
         addresses.add(Integer.valueOf(eDTO.getStructureId()));
         addresses.add(Integer.valueOf(eDTO.getId()));
      }

      return line.getSpreadProfileId() != null && line.getSpreadProfileId().trim().length() != 0?connection.getWeightingProfilesProcess().getProfileDetail(Integer.valueOf(virement.getFinanceCubeId()), line.getSpreadProfileId(), addresses.toArray(), line.getDataTypeVisId()):null;
   }

   protected double queryAllocationThreshold(HttpServletRequest httpServletRequest) throws Exception {
      CPConnection connection = this.getCPContext(httpServletRequest).getCPConnection();
      EntityList list = connection.getSystemPropertysProcess().getSystemProperty("ALLOC: Threshold");
      double allocationThreshold = 100.0D;
      if(list.getNumRows() > 0) {
         try {
            allocationThreshold = Double.parseDouble(String.valueOf(list.getValueAt(0, "Value")));
         } catch (NumberFormatException var7) {
            this.mLog.warn("queryAllocationThreshold", "Unable to paser system property:ALLOC: Threshold");
         }
      }

      return allocationThreshold;
   }

   protected void queryVirementDataTypes(HttpServletRequest httpServletRequest, VirementDataEntryDTO virementDTO) throws Exception {
      CPConnection connection = this.getCPContext(httpServletRequest).getCPConnection();
      List dataTypes = connection.getVirementRequestsProcess().queryDataTypes(Integer.valueOf(virementDTO.getFinanceCubeId()));
      virementDTO.getDataTypes().clear();
      virementDTO.getDataTypes().addAll(this.convertToDataTypeDTOs(dataTypes));
   }

   protected NumberFormat getNumberFormatter() {
      if(this.mNumberFormat == null) {
         this.mNumberFormat = new DecimalFormat();
         this.mNumberFormat.setMaximumFractionDigits(2);
         this.mNumberFormat.setMinimumFractionDigits(2);
      }

      return this.mNumberFormat;
   }
}
