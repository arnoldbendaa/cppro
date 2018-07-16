// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.xmlreport.XMLWritable;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public class DisplayStructureElement implements XMLWritable, Serializable {

   private int mId;
   private String mText;


   public int getId() {
      return this.mId;
   }

   public void setId(int id) {
      this.mId = id;
   }

   public String getText() {
      return this.mText;
   }

   public void setText(String text) {
      this.mText = text;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<displayStructureElement ");
      if(this.mId != 0) {
         out.write("id=\"" + this.mId + "\"");
      }

      if(this.mText != null) {
         out.write("text=\"" + this.mText + "\"");
      }

      out.write(" />");
   }
}
