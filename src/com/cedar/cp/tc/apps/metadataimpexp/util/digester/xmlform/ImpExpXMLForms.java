// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.ImpExpXMLForm;
import java.util.ArrayList;
import java.util.Iterator;

public class ImpExpXMLForms extends CommonElement {

   private ArrayList<ImpExpXMLForm> mParsedXMLForm = new ArrayList();


   public void addImpExpXMLForm(ImpExpXMLForm impExpXMLForm) {
      this.mParsedXMLForm.add(impExpXMLForm);
   }

   public ArrayList<ImpExpXMLForm> getParsedXMLFormList() {
      return this.mParsedXMLForm;
   }

   public String toXML() {
      StringBuffer strBuf = new StringBuffer();
      strBuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      strBuf.append("<xmlForms>");
      Iterator form = this.mParsedXMLForm.iterator();

      while(form.hasNext()) {
         strBuf.append(((ImpExpXMLForm)form.next()).toXML());
      }

      strBuf.append("</xmlForms>");
      return strBuf.toString();
   }
}
