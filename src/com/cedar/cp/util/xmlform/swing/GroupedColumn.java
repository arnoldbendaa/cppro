// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.xmlform.swing.GroupedColumn$1;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class GroupedColumn {

   protected TableCellRenderer mRenderer = getDefaultRenderer();
   protected Vector mElements;
   protected String mText;
   protected static DefaultTableCellRenderer sDefaultRenderer = new GroupedColumn$1();


   public static DefaultTableCellRenderer getDefaultRenderer() {
      return sDefaultRenderer;
   }

   public GroupedColumn(String text) {
      this.mText = text;
      this.mElements = new Vector();
   }

   public void add(Object obj) {
      if(obj != null) {
         this.mElements.addElement(obj);
      }
   }

   public Vector getColumnGroups() {
      return this.mElements;
   }

   public Vector getColumnGroups(TableColumn c, Vector g) {
      g.addElement(this);
      if(this.mElements.contains(c)) {
         return g;
      } else {
         Enumeration enumerate = this.mElements.elements();

         while(enumerate.hasMoreElements()) {
            Object obj = enumerate.nextElement();
            if(obj instanceof GroupedColumn) {
               Vector groups = ((GroupedColumn)obj).getColumnGroups(c, (Vector)g.clone());
               if(groups != null) {
                  return groups;
               }
            }
         }

         return null;
      }
   }

   public TableCellRenderer getHeaderRenderer() {
      return this.mRenderer;
   }

   public void setHeaderRenderer(TableCellRenderer renderer) {
      if(renderer != null) {
         this.mRenderer = renderer;
      }

   }

   public String getHeaderValue() {
      return this.mText;
   }

   public Dimension getSize(JTable table) {
      Component comp = this.mRenderer.getTableCellRendererComponent(table, this.getHeaderValue(), false, false, -1, -1);
      int height = comp.getPreferredSize().height;
      int width = 0;
      Enumeration enumerate = this.mElements.elements();

      while(enumerate.hasMoreElements()) {
         Object obj = enumerate.nextElement();
         if(obj instanceof TableColumn) {
            TableColumn aColumn = (TableColumn)obj;
            width += aColumn.getWidth();
         } else {
            width += ((GroupedColumn)obj).getSize(table).width;
         }
      }

      return new Dimension(width, height);
   }

   public int getMaxDepth() {
      int maxDepth = 2;
      Enumeration enumerate = this.mElements.elements();

      while(enumerate.hasMoreElements()) {
         Object obj = enumerate.nextElement();
         if(obj instanceof GroupedColumn) {
            int depth = 1 + ((GroupedColumn)obj).getMaxDepth();
            if(depth > maxDepth) {
               maxDepth = depth;
            }
         }
      }

      return maxDepth;
   }

   public int getStartCol() {
      if(this.mElements.size() == 0) {
         return -1;
      } else {
         Enumeration childGroups = this.mElements.elements();

         int col;
         do {
            if(!childGroups.hasMoreElements()) {
               return -1;
            }

            Object o = childGroups.nextElement();
            if(!(o instanceof GroupedColumn)) {
               TableColumn col1 = (TableColumn)o;
               return col1.getModelIndex();
            }

            col = ((GroupedColumn)o).getStartCol();
         } while(col < 0);

         return col;
      }
   }

   public int getEndCol() {
      if(this.mElements.isEmpty()) {
         return -1;
      } else {
         Object o = this.mElements.lastElement();
         if(o instanceof GroupedColumn) {
            int col1 = ((GroupedColumn)o).getEndCol();
            if(col1 == -1) {
               this.mElements.removeElement(o);
               return this.getEndCol();
            } else {
               return col1;
            }
         } else {
            TableColumn col = (TableColumn)o;
            return col.getModelIndex();
         }
      }
   }

}
