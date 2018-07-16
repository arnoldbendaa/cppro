// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.Field;
import com.cedar.cp.util.xmlform.OnFormLoad;
import com.cedar.cp.util.xmlform.Text;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Header extends Column implements XMLWritable {

   private List mChildren = new ArrayList();
   private OnFormLoad mOnFormLoad;


   public Header() {
      super("Header");
   }

   public OnFormLoad getOnFormLoad() {
      return this.mOnFormLoad;
   }

   public void setOnFormLoad(OnFormLoad onFormLoad) {
      if(this.mOnFormLoad == null && onFormLoad != null) {
         this.mChildren.add(0, onFormLoad);
         this.insert(onFormLoad, 0);
         this.mOnFormLoad = onFormLoad;
      }

      if(this.mOnFormLoad != null && onFormLoad == null) {
         this.mChildren.remove(0);
         this.remove(0);
         this.mOnFormLoad = null;
      }

   }

   public void addText(Text text) {
      this.mChildren.add(text);
      this.add(text);
   }

   public void addField(Field field) {
      this.mChildren.add(field);
      this.add(field);
   }

   public Iterator getChildren() {
      return this.mChildren.iterator();
   }

   public void removeText(Text text) {
      this.mChildren.remove(text);
      super.remove(text);
   }

   public void removeField(Field field) {
      this.mChildren.remove(field);
      super.remove(field);
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<header>");
      Iterator iter = this.mChildren.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</header>");
   }
}
