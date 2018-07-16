// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.LookupTableDigestRuleModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.LookupTablesDigestRuleModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormDigestRuleModel;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormsDigestRuleModel;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.metadata.MetaData;
import de.schlichtherle.truezip.file.TFile;
import de.schlichtherle.truezip.file.TFileInputStream;
import java.io.IOException;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

public class MetaDataXMLDigesterUtils {

   public static XMLFormsDigestRuleModel parseXMLForm(TFile zipFile) throws IOException, SAXException {
      String formMetaFile = zipFile.getAbsolutePath() + "/" + "xmlforms.xml";
      TFile xmlFile = new TFile(formMetaFile);
      if(xmlFile != null && xmlFile.exists() && xmlFile.isFile()) {
         Digester digester = new Digester();
         digester.setValidating(false);
         digester.addObjectCreate("xmlForms", XMLFormsDigestRuleModel.class);
         digester.addObjectCreate("xmlForms/xmlForm", XMLFormDigestRuleModel.class);
         digester.addBeanPropertySetter("xmlForms/xmlForm/visId", "visId");
         digester.addBeanPropertySetter("xmlForms/xmlForm/description", "description");
         digester.addBeanPropertySetter("xmlForms/xmlForm/type", "type");
         digester.addBeanPropertySetter("xmlForms/xmlForm/financeCubeVisId", "financeCubeVisId");
         digester.addBeanPropertySetter("xmlForms/xmlForm/defFileName", "defFileName");
         digester.addBeanPropertySetter("xmlForms/xmlForm/excelFileName", "excelFileName");
         digester.addCallMethod("xmlForms/xmlForm/lookupTable/lookupTableVisId", "addLookupTable", 1);
         digester.addCallParam("xmlForms/xmlForm/lookupTable/lookupTableVisId", 0);
         digester.addSetNext("xmlForms/xmlForm", "addXMLForm");
         TFileInputStream inputStream = new TFileInputStream(xmlFile);
         XMLFormsDigestRuleModel xmlFormsDigestRuleModel = (XMLFormsDigestRuleModel)digester.parse(inputStream);
         inputStream.close();
         return xmlFormsDigestRuleModel;
      } else {
         return null;
      }
   }

   public static MetaData parseMetaData(TFile zipFile) throws IOException, SAXException {
      Digester digester = new Digester();
      digester.setValidating(false);
      digester.addObjectCreate("meta-data", MetaData.class);
      digester.addBeanPropertySetter("meta-data/xmlForm", "xmlFormFileName");
      digester.addBeanPropertySetter("meta-data/lookupTable", "lookupTableName");
      digester.addCallMethod("meta-data/xmlFormDef", "addXmlFormDefFileName", 1);
      digester.addCallParam("meta-data/xmlFormDef", 0);
      digester.addCallMethod("meta-data/lookupTableExportExcelFile", "addLookupTableExportFileName", 1);
      digester.addCallParam("meta-data/lookupTableExportExcelFile", 0);
      String filePath = zipFile.getAbsolutePath() + "/" + "META-DATA.xml";
      TFile file = new TFile(filePath);
      TFileInputStream inputStream = new TFileInputStream(file);
      MetaData metaData = (MetaData)digester.parse(inputStream);
      inputStream.close();
      return metaData;
   }

   public static LookupTablesDigestRuleModel parseLookupTable(TFile zipFile) throws IOException, SAXException {
      String lookupMetaFile = zipFile.getAbsolutePath() + "/" + "lokuptbl.xml";
      TFile xmlFile = new TFile(lookupMetaFile);
      if(xmlFile != null && xmlFile.exists() && xmlFile.isFile()) {
         Digester digester = new Digester();
         digester.setValidating(false);
         digester.addObjectCreate("lookupTables", LookupTablesDigestRuleModel.class);
         digester.addObjectCreate("lookupTables/lookupTable", LookupTableDigestRuleModel.class);
         digester.addBeanPropertySetter("lookupTables/lookupTable/visId", "visId");
         digester.addBeanPropertySetter("lookupTables/lookupTable/description", "description");
         digester.addBeanPropertySetter("lookupTables/lookupTable/genTableName", "genTableName");
         digester.addBeanPropertySetter("lookupTables/lookupTable/autoSubmit", "autoSubmit");
         digester.addBeanPropertySetter("lookupTables/lookupTable/exportExcelFile", "exportExcelFile");
         digester.addSetNext("lookupTables/lookupTable", "addLookupTable");
         TFileInputStream inputStream = new TFileInputStream(xmlFile);
         LookupTablesDigestRuleModel lookupTablesDigestRuleModel = (LookupTablesDigestRuleModel)digester.parse(inputStream);
         inputStream.close();
         return lookupTablesDigestRuleModel;
      } else {
         return null;
      }
   }

   public static void main(String[] args) {}
}
