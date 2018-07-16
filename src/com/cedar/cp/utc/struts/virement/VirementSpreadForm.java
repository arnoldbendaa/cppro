// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.VirementDataEntryForm;
import com.cedar.cp.utc.struts.virement.VirementLineDTO;
import java.util.ArrayList;
import java.util.List;

public class VirementSpreadForm extends VirementDataEntryForm {

   private List mProfiles = new ArrayList();
   private VirementLineDTO mLine = null;
   private int mNumRows;
   private String mParentPage;


   public VirementLineDTO getLine() {
      if(this.mLine == null) {
         this.mLine = new VirementLineDTO();
      }

      return this.mLine;
   }

   public void setLine(VirementLineDTO line) {
      this.mLine = line;
   }

   public int getNumRows() {
      return this.mNumRows;
   }

   public void setNumRows(int numRows) {
      this.mNumRows = numRows;
   }

   public String getParentPage() {
      return this.mParentPage;
   }

   public void setParentPage(String parentPage) {
      this.mParentPage = parentPage;
   }

   public List getProfiles() {
      return this.mProfiles;
   }

   public void setProfiles(List profiles) {
      this.mProfiles = profiles;
   }
}
