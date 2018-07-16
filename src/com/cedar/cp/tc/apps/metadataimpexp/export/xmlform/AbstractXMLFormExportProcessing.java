// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.export.xmlform;

import com.cedar.cp.api.xmlform.XmlForm;
import com.cedar.cp.tc.apps.metadataimpexp.export.xmlform.XMLFormExportProcessingException;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.ImpExpXMLForm;

public abstract class AbstractXMLFormExportProcessing {

   public abstract ImpExpXMLForm process(XmlForm var1) throws XMLFormExportProcessingException;
}
