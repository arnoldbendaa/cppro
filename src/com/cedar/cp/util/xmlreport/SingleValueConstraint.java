// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlreport.Constraint;
import java.io.IOException;
import java.io.Writer;

public abstract class SingleValueConstraint extends Constraint {

   private String mValue;


   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }

   public abstract String getSqlPredicate(String var1);

   public String[] getBindVariables() {
      return new String[]{this.mValue};
   }

   public String toString() {
      StringBuffer b = new StringBuffer();
      if(this.isAndConstraint()) {
         b.append("AND ");
      }

      b.append(this.getConstraintText());
      b.append(" \'");
      b.append(this.mValue);
      b.append("\'");
      return b.toString();
   }

   abstract String getConstraintText();

   abstract String getXmlElementName();

   public void writeXml(Writer out) throws IOException {
      out.write("<" + this.getXmlElementName());
      out.write(" andConstraint=\"" + this.isAndConstraint() + "\" ");
      out.write(" value=\"" + XmlUtils.escapeStringForXML(this.mValue) + "\" ");
      out.write(" />");
   }
}
