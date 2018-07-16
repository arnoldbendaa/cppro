// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.Spread;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public class SummarySpreads extends DefaultMutableTreeNode implements XMLWritable {

   private List mSpreads = new ArrayList();


   public SummarySpreads() {
      this.setUserObject("summarySpreads");
   }

   public void addSpread(Spread spread) {
      this.mSpreads.add(spread);
      super.add(spread);
   }

   public List getSpreads() {
      return this.mSpreads;
   }

   public void removeSpread(Spread spread) {
      this.mSpreads.remove(spread);
      super.remove(spread);
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<summarySpreads>");
      Iterator iter = this.mSpreads.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</summarySpreads>");
   }
}
