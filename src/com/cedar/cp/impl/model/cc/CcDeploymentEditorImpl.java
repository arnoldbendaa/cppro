// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.cc;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementNode;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.cc.CcDeployment;
import com.cedar.cp.api.model.cc.CcDeploymentEditor;
import com.cedar.cp.api.model.cc.CcDeploymentEditorSession;
import com.cedar.cp.api.model.cc.CcDeploymentLine;
import com.cedar.cp.api.model.cc.CcDeploymentLineEditor;
import com.cedar.cp.api.model.cc.CcMappingLine;
import com.cedar.cp.api.model.cc.CcMappingLineEditor;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentEditorSessionSSO;
import com.cedar.cp.dto.model.cc.CcDeploymentImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentLineImpl;
import com.cedar.cp.dto.model.cc.CcDeploymentLinePK;
import com.cedar.cp.dto.model.cc.CcMappingLineImpl;
import com.cedar.cp.dto.model.cc.CcMappingLinePK;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.cc.CcDeploymentAdapter;
import com.cedar.cp.impl.model.cc.CcDeploymentEditorSessionImpl;
import com.cedar.cp.impl.model.cc.CcDeploymentLineEditorImpl;
import com.cedar.cp.impl.model.cc.CcMappingLineEditorImpl;
import com.cedar.cp.util.StringUtils;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class CcDeploymentEditorImpl extends BusinessEditorImpl implements CcDeploymentEditor, SubBusinessEditorOwner {

   private List<String> mFormFields;
   private List<DataTypeRef> mDataTypes;
   private static int mNextKey = -1;
   private CcDeploymentEditorSessionSSO mServerSessionData;
   private CcDeploymentImpl mEditorData;
   private CcDeploymentAdapter mEditorDataAdapter;


   public CcDeploymentEditorImpl(CcDeploymentEditorSessionImpl session, CcDeploymentEditorSessionSSO serverSessionData, CcDeploymentImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(CcDeploymentEditorSessionSSO serverSessionData, CcDeploymentImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setDimContextArray(Integer[] p) throws ValidationException {
      this.setDimContext0(p[0]);
      this.setDimContext1(p[1]);
      this.setDimContext2(p[2]);
      this.setDimContext3(p[3]);
      this.setDimContext4(p[4]);
      this.setDimContext5(p[5]);
      this.setDimContext6(p[6]);
      this.setDimContext7(p[7]);
      this.setDimContext8(p[8]);
      this.setDimContext9(p[9]);
   }

   public void setModelId(int newModelId) throws ValidationException {
      this.validateModelId(newModelId);
      if(this.mEditorData.getModelId() != newModelId) {
         this.setContentModified();
         this.mEditorData.setModelId(newModelId);
      }
   }

   public void setXmlformId(int newXmlformId) throws ValidationException {
      this.validateXmlformId(newXmlformId);
      if(this.mEditorData.getXmlformId() != newXmlformId) {
         this.setContentModified();
         this.mEditorData.setXmlformId(newXmlformId);
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

   public void setDimContext0(Integer newDimContext0) throws ValidationException {
      this.validateDimContext0(newDimContext0);
      if(this.mEditorData.getDimContext0() == null || !this.mEditorData.getDimContext0().equals(newDimContext0)) {
         this.dimensionContextUpdating();
         this.setContentModified();
         this.mEditorData.setDimContext0(newDimContext0);
      }
   }

   public void setDimContext1(Integer newDimContext1) throws ValidationException {
      this.validateDimContext1(newDimContext1);
      if(this.mEditorData.getDimContext1() == null || !this.mEditorData.getDimContext1().equals(newDimContext1)) {
         this.dimensionContextUpdating();
         this.setContentModified();
         this.mEditorData.setDimContext1(newDimContext1);
      }
   }

   public void setDimContext2(Integer newDimContext2) throws ValidationException {
      this.validateDimContext2(newDimContext2);
      if(this.mEditorData.getDimContext2() == null || !this.mEditorData.getDimContext2().equals(newDimContext2)) {
         this.dimensionContextUpdating();
         this.setContentModified();
         this.mEditorData.setDimContext2(newDimContext2);
      }
   }

   public void setDimContext3(Integer newDimContext3) throws ValidationException {
      this.validateDimContext3(newDimContext3);
      if(this.mEditorData.getDimContext3() == null || !this.mEditorData.getDimContext3().equals(newDimContext3)) {
         this.dimensionContextUpdating();
         this.setContentModified();
         this.mEditorData.setDimContext3(newDimContext3);
      }
   }

   public void setDimContext4(Integer newDimContext4) throws ValidationException {
      this.validateDimContext4(newDimContext4);
      if(this.mEditorData.getDimContext4() == null || !this.mEditorData.getDimContext4().equals(newDimContext4)) {
         this.dimensionContextUpdating();
         this.setContentModified();
         this.mEditorData.setDimContext4(newDimContext4);
      }
   }

   public void setDimContext5(Integer newDimContext5) throws ValidationException {
      this.validateDimContext5(newDimContext5);
      if(this.mEditorData.getDimContext5() == null || !this.mEditorData.getDimContext5().equals(newDimContext5)) {
         this.dimensionContextUpdating();
         this.setContentModified();
         this.mEditorData.setDimContext5(newDimContext5);
      }
   }

   public void setDimContext6(Integer newDimContext6) throws ValidationException {
      this.validateDimContext6(newDimContext6);
      if(this.mEditorData.getDimContext6() == null || !this.mEditorData.getDimContext6().equals(newDimContext6)) {
         this.dimensionContextUpdating();
         this.setContentModified();
         this.mEditorData.setDimContext6(newDimContext6);
      }
   }

   public void setDimContext7(Integer newDimContext7) throws ValidationException {
      this.validateDimContext7(newDimContext7);
      if(this.mEditorData.getDimContext7() == null || !this.mEditorData.getDimContext7().equals(newDimContext7)) {
         this.dimensionContextUpdating();
         this.setContentModified();
         this.mEditorData.setDimContext7(newDimContext7);
      }
   }

   public void setDimContext8(Integer newDimContext8) throws ValidationException {
      this.validateDimContext8(newDimContext8);
      if(this.mEditorData.getDimContext8() == null || !this.mEditorData.getDimContext8().equals(newDimContext8)) {
         this.dimensionContextUpdating();
         this.setContentModified();
         this.mEditorData.setDimContext8(newDimContext8);
      }
   }

   public void setDimContext9(Integer newDimContext9) throws ValidationException {
      this.validateDimContext9(newDimContext9);
      if(this.mEditorData.getDimContext9() == null || !this.mEditorData.getDimContext9().equals(newDimContext9)) {
         this.dimensionContextUpdating();
         this.setContentModified();
         this.mEditorData.setDimContext9(newDimContext9);
      }
   }

   public void validateModelId(int newModelId) throws ValidationException {}

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a CcDeployment");
      } else if(newVisId == null || newVisId.trim().length() == 0) {
         throw new ValidationException("A visual Id must be supplied.");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a CcDeployment");
      } else if(newDescription == null || newDescription.trim().length() == 0) {
         throw new ValidationException("A visual Id must be supplied.");
      }
   }

   public void validateXmlformId(int newXmlformId) throws ValidationException {
      this.queryXMLDetails(newXmlformId);
   }

   public void validateDimContext0(Integer newDimContext0) throws ValidationException {}

   public void validateDimContext1(Integer newDimContext1) throws ValidationException {}

   public void validateDimContext2(Integer newDimContext2) throws ValidationException {}

   public void validateDimContext3(Integer newDimContext3) throws ValidationException {}

   public void validateDimContext4(Integer newDimContext4) throws ValidationException {}

   public void validateDimContext5(Integer newDimContext5) throws ValidationException {}

   public void validateDimContext6(Integer newDimContext6) throws ValidationException {}

   public void validateDimContext7(Integer newDimContext7) throws ValidationException {}

   public void validateDimContext8(Integer newDimContext8) throws ValidationException {}

   public void validateDimContext9(Integer newDimContext9) throws ValidationException {}

   public void setModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var6) {
            throw new ValidationException(var6.getMessage());
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
      this.mEditorData.setModelId(((ModelRefImpl)actualRef).getModelPK().getModelId());
      DimensionRef[] dimensionRefs = this.getCcDeployment().getDimensionRefs();
      Integer[] contextDefaults = new Integer[10];

      for(int i = 0; i < dimensionRefs.length - 1; ++i) {
         contextDefaults[i] = Integer.valueOf(0);
      }

      this.mEditorData.setDimContextArray(contextDefaults);
   }

   public EntityList getOwnershipRefs() {
      return ((CcDeploymentEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public CcDeployment getCcDeployment() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new CcDeploymentAdapter((CcDeploymentEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      if(this.mEditorData.getVisId() != null && this.mEditorData.getVisId().trim().length() != 0) {
         if(this.mEditorData.getDescription() != null && this.mEditorData.getDescription().trim().length() != 0) {
            if(this.mEditorData.getDeploymentLines().size() == 0) {
               throw new ValidationException("At least one deployment line must be defined.");
            } else {
               this.streamLineDeploymentLines();
            }
         } else {
            throw new ValidationException("A description must be supplied");
         }
      } else {
         throw new ValidationException("A visual id must be supplied");
      }
   }

   private void streamLineDeploymentLines() {
      DimensionRef[] explicitDimensions = this.getExplicitMappingDimensionRefs();
      DimensionRef[] arr$ = explicitDimensions;
      int len$ = explicitDimensions.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         DimensionRef explicitDimensionRef = arr$[i$];
         this.streamLineDeploymentLines(explicitDimensionRef);
      }

   }

   private void streamLineDeploymentLines(DimensionRef explicitDimensionRef) {
      int index = this.getExplicitDimensionIndex(explicitDimensionRef);
      Iterator i$ = this.mEditorData.getDeploymentLines().iterator();

      while(i$.hasNext()) {
         CcDeploymentLine line = (CcDeploymentLine)i$.next();
         this.streamLineDeploymentLine((CcDeploymentLineImpl)line, explicitDimensionRef, index);
      }

   }

   private void streamLineDeploymentLine(CcDeploymentLineImpl line, DimensionRef dimensionRef, int index) {
      Map deploymentsMap = line.getDeploymentEntries();
      Map dimMap = (Map)deploymentsMap.get(dimensionRef);
      dimMap.clear();
      Iterator i$ = line.getMappingLines().iterator();

      while(i$.hasNext()) {
         CcMappingLine mappingLine = (CcMappingLine)i$.next();
         StructureElementRef seRef = (StructureElementRef)mappingLine.getEntries().get(index);
         dimMap.put(seRef, Boolean.valueOf(true));
      }

   }

   public CcDeploymentLineEditor getDeploymentLineEditor(Object key) throws ValidationException {
      CcDeploymentLineImpl line = null;
      if(key == null) {
         HashMap entries = new HashMap();
         DimensionRef[] arr$ = this.mEditorDataAdapter.getDimensionRefs();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            DimensionRef dimRef = arr$[i$];
            entries.put(dimRef, new HashMap());
         }

         line = new CcDeploymentLineImpl(this.mEditorData, new CcDeploymentLinePK(mNextKey--), 0, this.mEditorData.getDeploymentLines().size(), entries, new HashSet(), new ArrayList());
      } else {
         line = (CcDeploymentLineImpl)this.mEditorData.getDeploymentLine(key);
         if(line == null) {
            throw new ValidationException("Unable to locate deployment line for key:" + key);
         }
      }

      return new CcDeploymentLineEditorImpl((CcDeploymentEditorSession)this.getBusinessSession(), this, line);
   }

   private void dimensionContextUpdating() throws ValidationException {
      ArrayList allMappingLines = new ArrayList(this.mEditorData.getAllMappingLines());
      Iterator i$ = allMappingLines.iterator();

      while(i$.hasNext()) {
         CcMappingLine line = (CcMappingLine)i$.next();
         this.removeMappingLine(line.getKey());
      }

      this.mEditorData.resetExplictMappingDimensionRefs();
   }

   public void removeDeploymentLine(Object key) throws ValidationException {
      CcDeploymentLineImpl line = (CcDeploymentLineImpl)this.mEditorData.getDeploymentLine(key);
      if(line == null) {
         throw new ValidationException("Unable to locate deployment line for key:" + key);
      } else {
         if(this.mEditorData.getDeploymentLines().remove(line)) {
            this.setContentModified();
         }

      }
   }

   public void saveDeploymentLine(CcDeploymentLineImpl deploymentLine) {
      CcDeploymentLineImpl originalLine = (CcDeploymentLineImpl)this.mEditorData.getDeploymentLine(deploymentLine.getKey());
      int index;
      if(originalLine != null) {
         index = this.mEditorData.getDeploymentLines().indexOf(originalLine);
         this.mEditorData.getDeploymentLines().set(index, deploymentLine);
         deploymentLine.setIndex(index);
      } else {
         index = this.mEditorData.getDeploymentLines().size();
         this.mEditorData.getDeploymentLines().add(deploymentLine);
         deploymentLine.setIndex(index);
      }

      this.setContentModified();
   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {}

   public CcMappingLineEditor getMappingLineEditor(Object deploymentLineKey, Object mappingLineKey) throws ValidationException {
      CcDeploymentLineImpl deploymentLine = null;
      if(deploymentLineKey != null) {
         deploymentLine = (CcDeploymentLineImpl)this.mEditorData.getDeploymentLine(deploymentLineKey);
      }

      CcMappingLineImpl mappingLine = null;
      if(mappingLineKey == null) {
         if(deploymentLine == null) {
            throw new ValidationException("Unable to locate deployment line for key:" + deploymentLineKey);
         }

         ArrayList entries = new ArrayList();
         DimensionRef[] explictDimensionRefs = this.mEditorDataAdapter.getExplicitMappingDimensionRefs();

         for(int i = 0; i < explictDimensionRefs.length; ++i) {
            entries.add((Object)null);
         }

         mappingLine = new CcMappingLineImpl(new CcMappingLinePK(mNextKey--), (DataTypeRef)null, deploymentLine, (String)null, entries);
      } else {
         if(deploymentLine != null) {
            mappingLine = (CcMappingLineImpl)deploymentLine.getMappingLine(mappingLineKey);
         } else {
            mappingLine = (CcMappingLineImpl)this.mEditorData.findMappingLine(mappingLineKey);
         }

         if(mappingLine == null) {
            throw new ValidationException("Unable to locate mapping key for key:" + mappingLineKey);
         }
      }

      return new CcMappingLineEditorImpl(this.getBusinessSession(), this, mappingLine);
   }

   public void removeMappingLine(Object key) throws ValidationException {
      CcMappingLineImpl mappingLine = (CcMappingLineImpl)this.mEditorData.findMappingLine(key);
      if(mappingLine == null) {
         throw new ValidationException("Unable to locate mapping line for key:" + key);
      } else {
         mappingLine.getDeploymentLine().getMappingLines().remove(mappingLine);
         this.setContentModified();
      }
   }

   public void saveMappingLine(CcMappingLineImpl mappingLine) {
      CcMappingLineImpl originalLine = (CcMappingLineImpl)this.mEditorData.getMappingLine(mappingLine.getKey());
      if(originalLine != null) {
         int index = originalLine.getDeploymentLine().getMappingLines().indexOf(originalLine);
         originalLine.getDeploymentLine().getMappingLines().set(index, mappingLine);
      } else {
         mappingLine.getDeploymentLine().getMappingLines().add(mappingLine);
      }

      this.setContentModified();
   }

   private void queryXMLDetails(int xmlFormId) throws ValidationException {
      try {
         EntityList e = this.getConnection().getListHelper().getXMLFormDefinition(xmlFormId);
         String content = (String)e.getValueAt(0, "Definition");
         StringReader reader = new StringReader(content);
         DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
         docBuilderFactory.setValidating(false);
         docBuilderFactory.setNamespaceAware(true);
         DocumentBuilder db = docBuilderFactory.newDocumentBuilder();
         Document doc = db.parse(new InputSource(reader));
         Element root = doc.getDocumentElement();
         Element footer = (Element)root.getElementsByTagName("footer").item(0);
         NodeList nodelist = footer.getChildNodes();
         this.mFormFields = new ArrayList();

         for(int i = 0; i < nodelist.getLength(); ++i) {
            Node n = nodelist.item(i);
            if(n instanceof Element) {
               Element e1 = (Element)n;
               if(e1.getNodeName().equals("summary")) {
                  this.mFormFields.add(e1.getAttribute("id"));
               }
            }
         }

      } catch (Exception var14) {
         throw new ValidationException("Failed to parse form field list:" + var14.getMessage());
      }
   }

   public List<String> getXMLFormFields() throws ValidationException {
      if(this.mFormFields == null) {
         this.queryXMLDetails(this.mEditorData.getXmlformId());
      }

      return this.mFormFields;
   }

   public List<DataTypeRef> getDataTypes() throws ValidationException {
      if(this.mDataTypes == null) {
         EntityList dataTypeEntityList = this.getConnection().getListHelper().getAllDataTypesForModel(this.getCcDeployment().getModelId());
         ArrayList dataTypes = new ArrayList();

         for(int i = 0; i < dataTypeEntityList.getNumRows(); ++i) {
            dataTypes.add((DataTypeRef)dataTypeEntityList.getValueAt(i, "DataType"));
         }

         this.mDataTypes = dataTypes;
      }

      return this.mDataTypes;
   }

   public DimensionRef[] getExplicitMappingDimensionRefs() {
      return this.mEditorDataAdapter.getExplicitMappingDimensionRefs();
   }

   public int getExplicitDimensionIndex(DimensionRef dimensionRef) {
      DimensionRef[] explicitDimensions = this.mEditorDataAdapter.getExplicitMappingDimensionRefs();

      for(int i = 0; i < explicitDimensions.length; ++i) {
         if(explicitDimensions[i].equals(dimensionRef)) {
            return i;
         }
      }

      return -1;
   }

   private Object coerceElement(Object element) {
      if(element instanceof DefaultMutableTreeNode) {
         element = ((DefaultMutableTreeNode)element).getUserObject();
      }

      if(element instanceof StructureElementNode) {
         StructureElementNode sen = (StructureElementNode)element;
         element = sen.getStructureElementRef();
      }

      return element;
   }

   public String testDeployment(Object[] selections, boolean[] cascade) throws ValidationException {
      StructureElementRef[] refs = new StructureElementRef[selections.length];

      for(int i = 0; i < refs.length; ++i) {
         refs[i] = (StructureElementRef)this.coerceElement(selections[i]);
      }

      return ((CcDeploymentEditorSession)this.getBusinessSession()).testDeployment(refs, cascade);
   }

}
