// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export.xmlform;

import com.cedar.cp.api.xmlform.XmlForm;
import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplicationState;
import com.cedar.cp.tc.apps.metadataimpexp.export.xmlform.AbstractXMLFormExportProcessing;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.ImpExpXMLForm;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.reader.XMLReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class XMLFlatFormExportProcessing extends AbstractXMLFormExportProcessing {

   public ImpExpXMLForm process(XmlForm xmlForm) {
      ImpExpXMLForm impExpXMLForm = new ImpExpXMLForm();
      impExpXMLForm.setVisId(xmlForm.getVisId());
      impExpXMLForm.setDescription(xmlForm.getDescription());
      impExpXMLForm.setType(xmlForm.getType());
      String fCubeVisId = MetaDataImpExpApplicationState.getInstance().getFCubeVisIdFromXmlForm(xmlForm.getVisId());
      impExpXMLForm.setFinanceCubeVisId(fCubeVisId);

      try {
         String e = this.convertFlatFormDef(xmlForm.getDefinition());
         if(e != null) {
            impExpXMLForm.setDefinition(e);
         } else {
            impExpXMLForm.setDefinition(xmlForm.getDefinition());
         }
      } catch (Exception var5) {
         ;
      }

      return impExpXMLForm;
   }

   private String convertFlatFormDef(String xml) throws Exception {
      try {
         XMLReader e = new XMLReader();
         e.init();
         StringReader sr = new StringReader(xml);
         e.parseConfigFile(sr);
         Workbook workbook = e.getWorkbook();
         StringWriter writer = new StringWriter();
         workbook.writeXml(writer);
         return writer.toString();
      } catch (IOException var6) {
         return null;
      }
   }
}
