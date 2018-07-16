// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.HierarchysProcess;
import com.cedar.cp.api.model.FinanceCube;
import com.cedar.cp.api.model.FinanceCubeEditor;
import com.cedar.cp.api.model.FinanceCubeEditorSession;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.FinanceCubesProcess;
import com.cedar.cp.api.model.Model;
import com.cedar.cp.api.model.ModelEditor;
import com.cedar.cp.api.model.ModelEditorSession;
import com.cedar.cp.api.model.ModelsProcess;
import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.api.udeflookup.UdefLookupEditor;
import com.cedar.cp.api.udeflookup.UdefLookupEditorSession;
import com.cedar.cp.api.udeflookup.UdefLookupRef;
import com.cedar.cp.api.udeflookup.UdefLookupsProcess;
import com.cedar.cp.api.xmlform.XmlForm;
import com.cedar.cp.api.xmlform.XmlFormEditor;
import com.cedar.cp.api.xmlform.XmlFormEditorSession;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.api.xmlform.XmlFormsProcess;
import com.cedar.cp.tc.apps.metadataimpexp.util.CommonImpExpItem;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.FormConfigFactory;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MetaDataImpExpApplicationState {

   private static MetaDataImpExpApplicationState instance = null;
   private HashMap mItemList = new HashMap();
   private Collection mTaskList = new ArrayList();
   private CPConnection mConnection;


   public CPConnection getConnection() {
      return this.mConnection;
   }

   public static MetaDataImpExpApplicationState getInstance() {
      if(instance == null) {
         instance = new MetaDataImpExpApplicationState();
      }

      return instance;
   }

   public Collection getItemList(int type) {
      if(this.mItemList.get(Integer.valueOf(type)) == null) {
         switch(type) {
         case 0:
            this.mItemList.put(Integer.valueOf(type), this.getListXMLForms());
            break;
         case 1:
            this.mItemList.put(Integer.valueOf(type), this.getListLookupTables());
            break;
         case 2:
            this.mItemList.put(Integer.valueOf(type), this.getTypeOfExportItem());
            break;
         default:
            return null;
         }
      }

      return (Collection)this.mItemList.get(Integer.valueOf(type));
   }

   public UdefLookup getUdefLookup(String visId) throws ValidationException {
      Object key = this.queryUdefLookupPK(visId);
      if(key == null) {
         return null;
      } else {
         UdefLookupsProcess process = this.mConnection.getUdefLookupsProcess();
         UdefLookupEditorSession session = process.getUdefLookupEditorSession(key);
         UdefLookupEditor editor = session.getUdefLookupEditor();
         return editor.getUdefLookup();
      }
   }

   public XmlForm getXmlForm(String visId) throws ValidationException {
      Object key = this.queryXmlFormPK(visId);
      if(key == null) {
         return null;
      } else {
         XmlFormsProcess process = this.mConnection.getXmlFormsProcess();
         XmlFormEditorSession session = process.getXmlFormEditorSession(key);
         XmlFormEditor mEditor = session.getXmlFormEditor();
         return mEditor.getXmlForm();
      }
   }

   public void setConnection(CPConnection connection) {
      this.mConnection = connection;
   }

   private Collection getTypeOfExportItem() {
      ArrayList typeOfExportItem = new ArrayList();
      CommonImpExpItem item = new CommonImpExpItem();
      item.setId(0);
      item.setItemName("XML Forms (including associated lookup tables)");
      typeOfExportItem.add(item);
      item = new CommonImpExpItem();
      item.setId(1);
      item.setItemName("Lookup Tables");
      typeOfExportItem.add(item);
      return typeOfExportItem;
   }

   public String convertToStandardXMLFormDefinition(String xmlFormDef) throws Exception {
      FormConfig config = FormConfigFactory.createStandardForm(xmlFormDef);
      StringWriter writer = new StringWriter();
      config.writeSchemaXml(writer, this.getSchemaAttribute());
      return writer.toString();
   }

   private Collection getListLookupTables() {
      UdefLookupsProcess process = this.mConnection.getUdefLookupsProcess();
      EntityList udefLookups = process.getAllUdefLookups();
      Object[] listVisID = udefLookups.getValues("UdefLookup");
      Object[] listDescription = udefLookups.getValues("Description");
      if(listVisID == null) {
         return null;
      } else {
         ArrayList exportItemList = new ArrayList();
         CommonImpExpItem item = null;

         for(int i = 0; i < listVisID.length; ++i) {
            item = new CommonImpExpItem();
            item.setTreeNodeType(1);
            item.setItemName(listVisID[i].toString());
            item.setDescription((String)listDescription[i]);
            exportItemList.add(item);
         }

         return exportItemList;
      }
   }

   private List getListXMLForms() {
      ArrayList exportItemList = new ArrayList();
      XmlFormsProcess process = this.mConnection.getXmlFormsProcess();
      EntityList listXmlForm = process.getAllXmlForms();
      Object[] listVisID = listXmlForm.getValues("XmlForm");
      Object[] listDescription = listXmlForm.getValues("Description");
      if(listVisID == null) {
         return exportItemList;
      } else {
         CommonImpExpItem item = null;

         for(int i = 0; i < listVisID.length; ++i) {
            item = new CommonImpExpItem();
            item.setTreeNodeType(2);
            item.setItemName(listVisID[i].toString());
            item.setDescription((String)listDescription[i]);
            exportItemList.add(item);
         }

         return exportItemList;
      }
   }

   public String getSchemaAttribute() {
      StringBuffer sb = new StringBuffer();
      sb.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"");
      EntityList list = this.mConnection.getListHelper().getSystemProperty("WEB: Root URL");
      sb.append((String)list.getValueAt(0, "Value"));
      if(sb.lastIndexOf("/") != sb.length()) {
         sb.append("/");
      }

      sb.append("schema/XMLForm.xsd");
      sb.append("\"");
      return sb.toString();
   }

   public Object queryUdefLookupPK(String visid) {
      UdefLookupsProcess process = this.mConnection.getUdefLookupsProcess();
      EntityList udefLookups = process.getAllUdefLookups();
      Object[] refs = udefLookups.getValues("UdefLookup");

      for(int i = 0; i < refs.length; ++i) {
         UdefLookupRef ref = (UdefLookupRef)refs[i];
         if(ref.getNarrative().equals(visid)) {
            return ref.getPrimaryKey();
         }
      }

      return null;
   }

   public Object queryXmlFormPK(String visId) {
      XmlFormsProcess process = this.mConnection.getXmlFormsProcess();
      EntityList listXmlForm = process.getAllXmlForms();
      Object[] refs = listXmlForm.getValues("XmlForm");

      for(int i = 0; i < refs.length; ++i) {
         XmlFormRef ref = (XmlFormRef)refs[i];
         if(ref.getNarrative().equals(visId)) {
            return ref.getPrimaryKey();
         }
      }

      return null;
   }

   public Collection getSelectedExportItemList(int type) {
      Collection expItemList = this.getItemList(type);
      ArrayList selectedExpItemList = new ArrayList();
      Iterator item = expItemList.iterator();

      while(item.hasNext()) {
         CommonImpExpItem temp = (CommonImpExpItem)item.next();
         if(temp.isSelected()) {
            selectedExpItemList.add(temp);
         }
      }

      return selectedExpItemList;
   }

   public Collection<CommonImpExpItem> getDuplicatedItems(List<CommonImpExpItem> items) {
      if(items != null && items.size() > 0) {
         Collection lookupDBList = this.getListLookupTables();
         List formDBList = this.getListXMLForms();
         ArrayList duplicatedList = new ArrayList();
         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CommonImpExpItem selectedItem = (CommonImpExpItem)i$.next();
            if(lookupDBList != null && lookupDBList.contains(selectedItem) || formDBList != null && formDBList.contains(selectedItem)) {
               duplicatedList.add(selectedItem);
            }
         }

         return duplicatedList;
      } else {
         return null;
      }
   }

   public Collection getAllTaskList() {
      this.mTaskList.clear();
      this.mTaskList.addAll(this.getSelectedExportItemList(0));
      this.mTaskList.addAll(this.getSelectedExportItemList(1));
      return this.mTaskList;
   }

   public String getFCubeVisIdFromXmlForm(String formVisId) {
      int fCubePK = this.queryFCubePKByXmlFormVisID(formVisId);
      if(fCubePK == -1) {
         return null;
      } else {
         EntityList fcubes = this.mConnection.getListHelper().getAllSimpleFinanceCubes();
         int size = fcubes.getNumRows();

         for(int i = 0; i < size; ++i) {
            Object id = fcubes.getValueAt(i, "FinanceCubeId");
            if(id instanceof Integer && ((Integer)id).intValue() == fCubePK) {
               return fcubes.getValueAt(i, "FinanceCube").toString();
            }
         }

         return null;
      }
   }

   private int queryFCubePKByXmlFormVisID(String formVisId) {
      XmlFormsProcess process = this.mConnection.getXmlFormsProcess();
      EntityList xmlForms = process.getAllFinanceAndFlatForms();
      Object[] refs = xmlForms.getValues("XmlForm");
      Object[] refsFinanceCube = xmlForms.getValues("FinanceCubeId");

      for(int i = 0; i < refs.length; ++i) {
         XmlFormRef ref = (XmlFormRef)refs[i];
         ref.getPrimaryKey();
         if(ref.getNarrative().equals(formVisId)) {
            Object pk = refsFinanceCube[i];
            if(pk instanceof Integer) {
               return ((Integer)pk).intValue();
            }
         }
      }
      
      // Search in XcellForms if not found in the FinanceAndFlatForm
      xmlForms = process.getAllXcellXmlForms();
      refs = xmlForms.getValues("XmlForm");
      refsFinanceCube = xmlForms.getValues("FinanceCubeId");
      
      for(int i = 0; i < refs.length; ++i) {
         XmlFormRef ref = (XmlFormRef)refs[i];
         ref.getPrimaryKey();
         if(ref.getNarrative().equals(formVisId)) {
            Object pk = refsFinanceCube[i];
            if(pk instanceof Integer) {
               return ((Integer)pk).intValue();
            }
         }
      }
      
      return -1;
   }

   public FinanceCube getFinanceCube(Object key) throws ValidationException {
      FinanceCubesProcess process = this.mConnection.getFinanceCubesProcess();
      FinanceCubeEditorSession session = process.getFinanceCubeEditorSession(key);
      FinanceCubeEditor editor = session.getFinanceCubeEditor();
      return editor.getFinanceCube();
   }

   private FinanceCubeRef[] getAllFinanceCubeRef() {
      FinanceCubesProcess process = this.mConnection.getFinanceCubesProcess();
      EntityList list = process.getAllFinanceCubes();
      FinanceCubeRef[] financeCubes = new FinanceCubeRef[list.getNumRows()];

      for(int i = 0; i < financeCubes.length; ++i) {
         financeCubes[i] = (FinanceCubeRef)list.getValueAt(i, "FinanceCube");
      }

      return financeCubes;
   }

   public Object getFinanceCubePK(String visId) {
      FinanceCubeRef[] refs = this.getAllFinanceCubeRef();
      if(refs != null) {
         for(int i = 0; i < refs.length; ++i) {
            if(refs[i].getNarrative().equals(visId)) {
               return refs[i].getPrimaryKey();
            }
         }
      }

      return null;
   }

   public String[] getAllFCubeVisId() {
      FinanceCubeRef[] refs = this.getAllFinanceCubeRef();
      if(refs == null) {
         return null;
      } else {
         String[] visIds = new String[refs.length];

         for(int i = 0; i < refs.length; ++i) {
            visIds[i] = refs[i].getNarrative();
         }

         return visIds;
      }
   }

   public boolean isFinanceCubeExists(String fcVisId) {
      return this.getFinanceCubePK(fcVisId) != null;
   }

   public Model getFinanceCubeModel(String fCubeVisID) throws ValidationException {
      Object key = this.getFinanceCubePK(fCubeVisID);
      FinanceCube fCube = this.getFinanceCube(key);
      Object modelKey = fCube.getModelRef().getPrimaryKey();
      Model model = this.getFinCubeModel(modelKey);
      return model;
   }

   private Model getFinCubeModel(Object key) throws ValidationException {
      ModelsProcess process = this.getConnection().getModelsProcess();
      ModelEditorSession session = process.getModelEditorSession(key);
      ModelEditor editor = session.getModelEditor();
      return editor.getModel();
   }

   public void clearAllItem() {
      this.mItemList.clear();
      this.mTaskList.clear();
   }

   public void getHierarcyDetailsFromDimId(int dimId) {
      HierarchysProcess process = this.getConnection().getHierarchysProcess();
      EntityList entityList = process.getHierarcyDetailsFromDimId(dimId);
      String[] d = entityList.getHeadings();
   }

   public String[] getDimVisIdListForFinancecCube(String fCubeVisId) {
      try {
         Model e = this.getFinanceCubeModel(fCubeVisId);
         ModelsProcess modelProcess = this.getConnection().getModelsProcess();
         EntityList hierList = modelProcess.getAllHierarchiesForModel(e.getPrimaryKey());
         Object[] d = hierList.getValues("Dimension");
         String[] dimVisIdList = new String[d.length];

         for(int i = 0; i < d.length; ++i) {
            DimensionRef dim = (DimensionRef)d[i];
            dimVisIdList[i] = dim.getNarrative();
         }

         return dimVisIdList;
      } catch (ValidationException var9) {
         return null;
      }
   }

   public String[] getAllHierVisIdForFinancecCube(String fCubeVisId) {
      try {
         Model e = this.getFinanceCubeModel(fCubeVisId);
         ModelsProcess modelProcess = this.getConnection().getModelsProcess();
         EntityList hierList = modelProcess.getAllHierarchiesForModel(e.getPrimaryKey());
         Object[] s = hierList.getValues("Hierarchy");
         String[] hierVisId = new String[s.length];

         for(int i = 0; i < s.length; ++i) {
            HierarchyRef hier = (HierarchyRef)s[i];
            hierVisId[i] = hier.getNarrative();
         }

         return hierVisId;
      } catch (ValidationException var9) {
         return null;
      }
   }

   public void destroyApplicationState() {
      instance = null;
   }

}
