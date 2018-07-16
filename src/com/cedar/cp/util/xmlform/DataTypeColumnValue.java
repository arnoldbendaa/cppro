// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import javax.swing.tree.DefaultMutableTreeNode;

public class DataTypeColumnValue extends DefaultMutableTreeNode implements XMLWritable {

   private String mValue;
   private boolean mYtd;
   private int mPeriod;
   private String mPeriodVisId;
   private int mYear;
   private String mYearVisId;


   public DataTypeColumnValue() {
      super("DataTypeColumnValue");
   }

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }

   public boolean getYtd() {
      return this.mYtd;
   }

   public void setYtd(boolean flag) {
      this.mYtd = flag;
   }

   public int getPeriod() {
      return this.mPeriod;
   }

   public void setPeriod(int period) {
      this.mPeriod = period;
   }

   public String getPeriodVisId() {
      return this.mPeriodVisId;
   }

   public void setPeriodVisId(String visId) {
      this.mPeriodVisId = visId;
   }

   public int getYear() {
      return this.mYear;
   }

   public void setYear(int year) {
      this.mYear = year;
   }

   public String getYearVisId() {
      return this.mYearVisId;
   }

   public void setYearVisId(String yearVisId) {
      this.mYearVisId = yearVisId;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<dataTypeColumnValue ");
      out.write(" value=\"" + (this.mValue != null?this.mValue:"") + "\"");
      out.write(" ytd=\"" + this.mYtd + "\"");
      out.write(" year=\"" + this.mYear + "\"");
      out.write(" period=\"" + this.mPeriod + "\"");
      out.write(" periodVisId=\"" + (this.mPeriodVisId != null?XmlUtils.escapeStringForXML(this.mPeriodVisId):"") + "\"");
      out.write(" />");
   }
}
