// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.tld;

import com.cedar.cp.util.XmlUtils;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.taglib.AbstractTag;
import java.io.Writer;

public class CPDecoratorProperty extends AbstractTag {

   private String propertyName;
   private String defaultValue;
   private boolean writeEntireProperty = false;


   public void setProperty(String propertyName) {
      this.propertyName = propertyName;
   }

   protected String getProperty() {
      return this.propertyName;
   }

   public void setDefault(String defaultValue) {
      this.defaultValue = defaultValue;
   }

   public final void setWriteEntireProperty(String writeEntireProperty) {
      if(writeEntireProperty != null && writeEntireProperty.trim().length() != 0) {
         switch(writeEntireProperty.charAt(0)) {
         case 49:
         case 84:
         case 89:
         case 116:
         case 121:
            this.writeEntireProperty = true;
            break;
         default:
            this.writeEntireProperty = false;
         }

      }
   }

   public int doEndTag() {
      try {
         Page e = this.getPage();
         String propertyValue = e.getProperty(this.propertyName);
         propertyValue = XmlUtils.escapeXMLStringForHTML(propertyValue);
         if(propertyValue == null || propertyValue.trim().length() == 0) {
            propertyValue = this.defaultValue;
         }

         if(propertyValue != null) {
            Writer out = this.getOut();
            if(this.writeEntireProperty) {
               out.write(" ");
               out.write(this.propertyName.substring(this.propertyName.lastIndexOf(46) + 1));
               out.write("=\"");
               out.write(propertyValue);
               out.write("\"");
            } else {
               out.write(propertyValue);
            }
         }
      } catch (Exception var4) {
         trace(var4);
      }

      return 6;
   }
}
