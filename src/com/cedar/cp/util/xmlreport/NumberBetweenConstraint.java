// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlreport.Constraint;
import java.io.IOException;
import java.io.Writer;

public class NumberBetweenConstraint extends Constraint {

   private String mFromValue;
   private String mToValue;


   public String getFromValue() {
      return this.mFromValue;
   }

   public void setFromValue(String fromValue) {
      this.mFromValue = fromValue;
   }

   public String getToValue() {
      return this.mToValue;
   }

   public void setToValue(String toValue) {
      this.mToValue = toValue;
   }

   public String getSqlPredicate(String col) {
      StringBuffer sb = new StringBuffer();
      sb.append(this.isAndConstraint()?" and ":" or ");
      sb.append("(REPLACE(TRANSLATE(");
      sb.append(col);
      sb.append(",\'1234567890\',\'00000000000\'),\'0\',\'\') IS NULL and to_number(");
      sb.append(col);
      sb.append(") between ? and ? )");
      return sb.toString();
   }

   public String[] getBindVariables() {
      return new String[]{this.mFromValue, this.mToValue};
   }

   public String toString() {
      StringBuffer b = new StringBuffer();
      if(this.isAndConstraint()) {
         b.append("or ");
      }

      b.append("Number between \'");
      b.append(this.mFromValue);
      b.append("\' and \'");
      b.append(this.mToValue);
      b.append("\'");
      return b.toString();
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<numberBetweenConstraint ");
      out.write(" andConstraint=\"" + this.isAndConstraint() + "\" ");
      out.write(" fromValue=\"" + XmlUtils.escapeStringForXML(this.mFromValue) + "\" ");
      out.write(" toValue=\"" + XmlUtils.escapeStringForXML(this.mToValue) + "\" ");
      out.write(" />");
   }
}
