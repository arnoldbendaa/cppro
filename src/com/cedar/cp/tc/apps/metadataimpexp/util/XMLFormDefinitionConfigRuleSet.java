// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.ImpExpInputs;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.ImpExpLookupInput;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.ImpExpXMLForm;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.InputColumnKey;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class XMLFormDefinitionConfigRuleSet extends RuleSetBase {

   public void addRuleInstances(Digester digester) {
      digester.addObjectCreate("cp-form", ImpExpXMLForm.class);
      digester.addSetProperties("cp-form");
      digester.addObjectCreate("cp-form/inputs", ImpExpInputs.class);
      digester.addSetNext("cp-form/inputs", "setInputs");
      digester.addObjectCreate("cp-form/inputs/lookupInput", ImpExpLookupInput.class);
      digester.addSetProperties("cp-form/inputs/lookupInput");
      digester.addSetNext("cp-form/inputs/lookupInput", "addInput");
      digester.addObjectCreate("cp-form/inputs/lookupInput/inputColumnKey", InputColumnKey.class);
      digester.addSetProperties("cp-form/inputs/lookupInput/inputColumnKey");
      digester.addSetNext("cp-form/inputs/lookupInput/inputColumnKey", "setInputColumnKey");
   }
}
