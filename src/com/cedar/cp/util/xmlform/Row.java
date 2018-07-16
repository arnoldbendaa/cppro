// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.xmlform.InputColumnValue;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public class Row extends DefaultMutableTreeNode implements XMLWritable, Cloneable {

   private List mColumnValues = new ArrayList();


   public Row() {
      super("Row");
   }

   public List getInputColumnValues() {
      return this.mColumnValues;
   }

   public void addInputColumnValue(InputColumnValue value) {
      this.mColumnValues.add(value);
      this.add(value);
   }

   public void removeColumnValue(InputColumnValue value) {
      this.mColumnValues.remove(value);
      this.remove(value);
   }

   public void removeInputColumnValueAtIndex(int index) {
      this.mColumnValues.remove(index);
      this.remove(index);
   }

   public void addInputColumnValueAtIndex(int index, InputColumnValue value) {
      this.mColumnValues.add(index, value);
      this.insert(value, index);
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<row>");
      Iterator iter = this.mColumnValues.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</row>");
   }

   public Object clone() {
      Row newRow = new Row();
      Iterator iter = this.mColumnValues.iterator();

      while(iter.hasNext()) {
         InputColumnValue node = (InputColumnValue)iter.next();
         newRow.addInputColumnValue((InputColumnValue)node.clone());
      }

      return newRow;
   }
}
