// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.xmlreport.XMLWritable;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public abstract class Constraint implements XMLWritable, Serializable, Cloneable {

   private boolean mAndConstraint;


   public boolean isAndConstraint() {
      return this.mAndConstraint;
   }

   public void setAndConstraint(boolean andConstraint) {
      this.mAndConstraint = andConstraint;
   }

   public Constraint getCopy() {
      try {
         return (Constraint)super.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   public abstract void writeXml(Writer var1) throws IOException;

   public abstract String getSqlPredicate(String var1);

   public abstract String[] getBindVariables();
}
