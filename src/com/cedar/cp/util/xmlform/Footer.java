// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.Summary;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Footer extends Column {

   private int mCols = 1;
   private int mScrollHeight;
   private List mSummaries = new ArrayList();


   public Footer() {
      super("Footer");
   }

   public void addSummary(Summary s) {
      this.mSummaries.add(s);
      this.add(s);
   }

   public Iterator getSummaries() {
      return this.mSummaries.iterator();
   }

   public void removeSummary(Summary s) {
      this.mSummaries.remove(s);
      super.remove(s);
   }

   public int getCols() {
      return this.mCols;
   }

   public void setCols(int cols) {
      if(cols < 1) {
         cols = 1;
      }

      this.mCols = cols;
   }

   public int getScrollHeight() {
      return this.mScrollHeight;
   }

   public void setScrollHeight(int scrollHeight) {
      if(scrollHeight < 0) {
         scrollHeight = 0;
      }

      this.mScrollHeight = scrollHeight;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<footer");
      out.write(" cols=\"" + this.mCols + "\"");
      out.write(" scrollHeight=\"" + this.mScrollHeight + "\"");
      out.write(" >");
      Iterator iter = this.mSummaries.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</footer>");
   }
}
