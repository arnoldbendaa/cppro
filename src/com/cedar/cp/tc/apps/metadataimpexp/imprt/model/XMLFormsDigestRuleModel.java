// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.imprt.model;

import com.cedar.cp.tc.apps.metadataimpexp.imprt.model.XMLFormDigestRuleModel;
import java.util.ArrayList;
import java.util.List;

public class XMLFormsDigestRuleModel {

   List<XMLFormDigestRuleModel> xmlFormList = new ArrayList();


   public List<XMLFormDigestRuleModel> getXmlFormList() {
      return this.xmlFormList;
   }

   public void setXmlFormList(List<XMLFormDigestRuleModel> xmlFormList) {
      this.xmlFormList = xmlFormList;
   }

   public void addXMLForm(XMLFormDigestRuleModel xmlFormModel) {
      if(this.xmlFormList != null) {
         this.xmlFormList.add(xmlFormModel);
      }

   }
}
