// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.xmlreport.Constraint;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class InConstraint extends Constraint {

   private List mItems;


   public List getItems() {
      return this.mItems;
   }

   public void setItems(List items) {
      this.mItems = items;
   }

   public String getSqlPredicate(String col) {
      StringBuffer sb = new StringBuffer();
      sb.append(this.isAndConstraint()?" and ":" or ");
      sb.append(col);
      sb.append(" in ( ");

      for(int i = 0; i < this.mItems.size(); ++i) {
         if(i > 0) {
            sb.append(',');
         }

         sb.append('?');
      }

      sb.append(" ) ");
      return sb.toString();
   }

   public String[] getBindVariables() {
      return (String[])((String[])this.mItems.toArray(new String[this.mItems.size()]));
   }

   public String toString() {
      StringBuffer b = new StringBuffer();
      if(this.isAndConstraint()) {
         b.append("or ");
      }

      b.append("In \'");
      b.append(this.mItems);
      b.append("\'");
      return b.toString();
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<inConstraint");
      out.write(" andConstraint=\"" + this.isAndConstraint() + "\" ");
      out.write(" />");
   }
}
