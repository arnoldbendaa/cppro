// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.CommonElement;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.ImpExpInputs;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.ImpExpLookupInput;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ImpExpXMLForm extends CommonElement {

   private String mVisId;
   private String mDescription;
   private int mType;
   private String mFinanceCubeVisId;
   private String mDefFileName;
   private String mExcelFileName;
   private String mDefinition;
   private ImpExpInputs mInputs;
   private static short sFileId = 0;


   public ImpExpXMLForm() {
	  sFileId++;
      this.mDefFileName = sFileId+".xml";
      this.mExcelFileName = sFileId+".xlsx";
   }
   
   /**
    * Get excel file name
    */
	public String getExcelFileName() {
		return this.mExcelFileName;
	}
	
	/**
	 * Set excel file name
	 */
	public void setExcelFileName(String excelFileName) {
		this.mExcelFileName = excelFileName;
	}

   public String getDefFileName() {
      return this.mDefFileName;
   }

   public void setDefFileName(String defFileName) {
      this.mDefFileName = defFileName;
   }

   public ImpExpInputs getInputs() {
      return this.mInputs;
   }

   public void setInputs(ImpExpInputs inputs) {
      this.mInputs = inputs;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public String getFinanceCubeVisId() {
      return this.mFinanceCubeVisId;
   }

   public void setFinanceCubeVisId(String financeCubeVisId) {
      this.mFinanceCubeVisId = financeCubeVisId;
   }

   public int getType() {
      return this.mType;
   }
   
   /**
    * Set type form
    */
   public void setType(int type) {
      this.mType = type;
   }

   public String getDefinition() {
      return this.mDefinition;
   }

   public void setDefinition(String definition) {
      this.mDefinition = definition;
   }

   public Set<String> getLookupTableVisIds() {
      HashSet lookupTableVisIds = null;
      if(this.mInputs != null) {
         lookupTableVisIds = new HashSet();
         List listInputs = this.mInputs.getInputs();
         Iterator iterator = listInputs.iterator();

         while(iterator.hasNext()) {
            Object input = iterator.next();
            if(input instanceof ImpExpLookupInput) {
               ImpExpLookupInput lookupInput = (ImpExpLookupInput)input;
               String refTableVisId = lookupInput.getLookupName() != null?lookupInput.getLookupName():lookupInput.getTableName();
               lookupTableVisIds.add(refTableVisId);
            }
         }
      }

      return lookupTableVisIds;
   }

   public String toXML() {
      Set lookupTableVisIds = this.getLookupTableVisIds();
      StringBuffer strBuf = new StringBuffer();
      strBuf.append("<xmlForm>");
      strBuf.append("<visId>").append(this.replaceSpecialXMLCharacter(this.getVisId())).append("</visId>");
      strBuf.append("<description>").append(this.replaceSpecialXMLCharacter(this.getDescription())).append("</description>");
      strBuf.append("<type>").append(this.getType()).append("</type>");
      strBuf.append("<financeCubeVisId>");
      if(this.getFinanceCubeVisId() != null) {
         strBuf.append(this.getFinanceCubeVisId());
      }

      strBuf.append("</financeCubeVisId>");
      strBuf.append("<defFileName>").append(this.mDefFileName).append("</defFileName>");
      if (this.getType() == 6) {
    	  strBuf.append("<excelFileName>").append(this.mExcelFileName).append("</excelFileName>");
      }
      if(lookupTableVisIds != null && lookupTableVisIds.size() > 0) {
         strBuf.append("<lookupTable>");
         Iterator iterator = lookupTableVisIds.iterator();

         while(iterator.hasNext()) {
            strBuf.append("<lookupTableVisId>").append((String)iterator.next()).append("</lookupTableVisId>");
         }

         strBuf.append("</lookupTable>");
      }

      strBuf.append("</xmlForm>");
      return strBuf.toString();
   }

}
