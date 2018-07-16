// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public class ColumnGroup extends DefaultMutableTreeNode implements XMLWritable {

   private String mHeading = "ColumnGroup";
   private List mColumns = new ArrayList();


   public ColumnGroup() {
      this.setUserObject(this.mHeading);
   }

   public String getHeading() {
      return this.mHeading;
   }

   public String getHtmlHeading() {
      return XmlUtils.escapeStringForXML(this.mHeading, true);
   }

   public String getHeadingDisplay() {
      return this.mHeading.indexOf(60) >= 0?"<html><center>" + this.mHeading + "</center></html>":this.mHeading;
   }

   public void setHeading(String heading) {
      this.mHeading = heading;
      this.setUserObject(this.mHeading);
   }

   public void addColumn(Column column) {
      this.mColumns.add(column);
      super.add(column);
   }

   public void addColumn(int index, Column column) {
      this.mColumns.add(index, column);
      super.insert(column, index);
   }

   public void addColumnGroup(ColumnGroup group) {
      this.mColumns.add(group);
      super.add(group);
   }

   public List getColumns() {
      return this.mColumns;
   }

   public Column getColumnById(String columnId) {
      Iterator i$ = this.mColumns.iterator();

      while(i$.hasNext()) {
         Object o = i$.next();
         if(o instanceof Column) {
            Column column = (Column)o;
            if(column.getId().equals(columnId)) {
               return column;
            }
         }
      }

      return null;
   }

   public void removeColumn(Column col) {
      this.mColumns.remove(col);
      super.remove(col);
   }

   public void removeColumnGroup(ColumnGroup group) {
      this.mColumns.remove(group);
      super.remove(group);
   }

   public void addColumnAtIndex(int index, Column column) {
      this.mColumns.add(index, column);
      super.insert(column, index);
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<columnGroup ");
      if(this.mHeading != null) {
         out.write(" heading=\"" + XmlUtils.escapeStringForXML(this.mHeading) + "\"");
      }

      out.write(">");
      Iterator iter = this.mColumns.iterator();

      while(iter.hasNext()) {
         ((XMLWritable)iter.next()).writeXml(out);
      }

      out.write("</columnGroup>");
   }
}
