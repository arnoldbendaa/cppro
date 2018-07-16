// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;

public class AutoPopulate extends Column implements XMLWritable {

   private boolean mAddAllowed = true;
   private boolean mDeleteAddedRowsAllowed = false;
   private boolean mDeleteAllowed = true;
   private boolean mAutoDelete = false;
   private String mColumn;


   public AutoPopulate() {
      this.setId("autoPopulate");
      this.setHeading("");
      this.setType("number");
      this.setProtected(true);
      this.setPersists(false);
      this.setUserObject("Auto Populate");
      this.setWidth(20);
   }

   public boolean isAddAllowed() {
      return this.mAddAllowed;
   }

   public void setAddAllowed(boolean addAllowed) {
      this.mAddAllowed = addAllowed;
   }

   public boolean isDeleteAddedRowsAllowed() {
      return this.mDeleteAddedRowsAllowed;
   }

   public void setDeleteAddedRowsAllowed(boolean deleteAddedRowsAllowed) {
      this.mDeleteAddedRowsAllowed = deleteAddedRowsAllowed;
   }

   public boolean isDeleteAllowed() {
      return this.mDeleteAllowed;
   }

   public void setDeleteAllowed(boolean deleteAllowed) {
      this.mDeleteAllowed = deleteAllowed;
   }

   public boolean isAutoDelete() {
      return this.mAutoDelete;
   }

   public void setAutoDelete(boolean autoDelete) {
      this.mAutoDelete = autoDelete;
   }

   public String getColumn() {
      return this.mColumn;
   }

   public void setColumn(String column) {
      this.mColumn = column;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<autoPopulate ");
      out.write(" lookup=\"" + this.getLookup() + "\"");
      out.write(" addAllowed=\"" + this.isAddAllowed() + "\"");
      out.write(" deleteAddedRowsAllowed=\"" + this.isDeleteAddedRowsAllowed() + "\"");
      out.write(" deleteAllowed=\"" + this.isDeleteAllowed() + "\"");
      out.write(" autoDelete=\"" + this.isAutoDelete() + "\"");
      out.write(" column=\"" + this.mColumn + "\"");
      out.write(" width=\"" + this.getWidth() + "\"");
      out.write(" />");
   }
}
