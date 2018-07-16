// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.xmlreport.SingleValueConstraint;

public class NotEqualConstraint extends SingleValueConstraint {

   String getConstraintText() {
      return "Not equal to";
   }

   public String getSqlPredicate(String col) {
      StringBuffer sb = new StringBuffer();
      sb.append(this.isAndConstraint()?" and ":" or ");
      sb.append(col);
      sb.append(" <> ? ");
      return sb.toString();
   }

   String getXmlElementName() {
      return "notEqualConstraint";
   }
}
