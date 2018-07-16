// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.formula;


public class FormulaDeployment$ElementReference {

   private String mStructure;
   private String mStartElement;
   private String mEndElement;


   public FormulaDeployment$ElementReference() {}

   public FormulaDeployment$ElementReference(String structure, String startElement, String endElement) {
      this.mStructure = structure;
      this.mStartElement = startElement;
      this.mEndElement = endElement;
   }

   public String getStructure() {
      return this.mStructure;
   }

   public void setStructure(String structure) {
      this.mStructure = structure;
   }

   public String getStartElement() {
      return this.mStartElement;
   }

   public void setStartElement(String startElement) {
      this.mStartElement = startElement;
   }

   public String getEndElement() {
      return this.mEndElement;
   }

   public void setEndElement(String endElement) {
      this.mEndElement = endElement;
   }
}
