// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export.xmlform;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.XmlForm;
import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplicationState;
import com.cedar.cp.tc.apps.metadataimpexp.export.xmlform.AbstractXMLFormExportProcessing;
import com.cedar.cp.tc.apps.metadataimpexp.export.xmlform.XMLFormExportProcessingException;
import com.cedar.cp.tc.apps.metadataimpexp.util.XMLFormDefinitionConfigRuleSet;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.ImpExpXMLForm;
import java.io.IOException;
import java.io.StringReader;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

public class NonXMLFlatFormExportProcessing extends AbstractXMLFormExportProcessing {

   public ImpExpXMLForm process(XmlForm xmlForm) throws XMLFormExportProcessingException {
      try {
         return this.parsingXMLFormDefinition(xmlForm);
      } catch (ValidationException var3) {
         throw new XMLFormExportProcessingException(var3.getMessage());
      } catch (IOException var4) {
         throw new XMLFormExportProcessingException(var4.getMessage());
      } catch (SAXException var5) {
         throw new XMLFormExportProcessingException(var5.getMessage());
      }
   }

   private ImpExpXMLForm parsingXMLFormDefinition(XmlForm xmlForm) throws ValidationException, IOException, SAXException {
      String stdDef = "";

      try {
         stdDef = MetaDataImpExpApplicationState.getInstance().convertToStandardXMLFormDefinition(xmlForm.getDefinition());
      } catch (Exception var7) {
         throw new ValidationException(var7.getMessage());
      }

      ImpExpXMLForm impExpXMLForm = null;
      StringReader sDefinition = new StringReader(stdDef);
      Digester mConfigDigester = new Digester();
      mConfigDigester.setNamespaceAware(true);
      mConfigDigester.setValidating(false);
      mConfigDigester.setUseContextClassLoader(true);
      mConfigDigester.addRuleSet(new XMLFormDefinitionConfigRuleSet());
      impExpXMLForm = (ImpExpXMLForm)mConfigDigester.parse(sDefinition);
      impExpXMLForm.setVisId(xmlForm.getVisId());
      impExpXMLForm.setDescription(xmlForm.getDescription());
      impExpXMLForm.setType(xmlForm.getType());
      String fCubeVisId = MetaDataImpExpApplicationState.getInstance().getFCubeVisIdFromXmlForm(xmlForm.getVisId());
      impExpXMLForm.setFinanceCubeVisId(fCubeVisId);
      impExpXMLForm.setDefinition(stdDef);
      return impExpXMLForm;
   }
}
