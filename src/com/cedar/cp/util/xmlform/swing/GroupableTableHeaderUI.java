// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.xmlform.swing.GroupableTableHeader;
import com.cedar.cp.util.xmlform.swing.GroupableTableHeaderUI$MouseInputHandler;
import com.cedar.cp.util.xmlform.swing.GroupedColumn;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class GroupableTableHeaderUI extends BasicTableHeaderUI {

   private transient Log mLog = new Log(this.getClass());
   protected MouseInputListener mMouseInputListener;
   private static Cursor resizeCursor = Cursor.getPredefinedCursor(11);


   public void paint(Graphics g, JComponent c) {
      Rectangle clipBounds = g.getClipBounds();
      if(this.header.getColumnModel() != null) {
         int column = 0;
         Dimension size = this.header.getSize();
         Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
         Hashtable h = new Hashtable();

         for(Enumeration enumeration = this.header.getColumnModel().getColumns(); enumeration.hasMoreElements(); ++column) {
            cellRect.height = size.height;
            cellRect.y = 0;
            TableColumn aColumn = (TableColumn)enumeration.nextElement();
            Enumeration cGroups = ((GroupableTableHeader)this.header).getColumnGroupsEnumeration(aColumn);
            if(cGroups != null) {
               for(int groupHeight = 0; cGroups.hasMoreElements(); cellRect.y = groupHeight) {
                  GroupedColumn cGrouped = (GroupedColumn)cGroups.nextElement();
                  Rectangle groupRect = (Rectangle)h.get(cGrouped);
                  if(groupRect == null) {
                     groupRect = new Rectangle(cellRect);
                     Dimension d = cGrouped.getSize(this.header.getTable());
                     groupRect.width = d.width;
                     groupRect.height = d.height;
                     h.put(cGrouped, groupRect);
                  }

                  this.paintCell(g, groupRect, cGrouped);
                  groupHeight += groupRect.height;
                  cellRect.height = size.height - groupHeight;
               }
            }

            cellRect.width = aColumn.getWidth();
            if(cellRect.intersects(clipBounds)) {
               this.paintCell(g, cellRect, column);
            }

            cellRect.x += cellRect.width;
         }

      }
   }

   private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
      TableColumn aColumn = this.header.getColumnModel().getColumn(columnIndex);
      TableCellRenderer renderer = aColumn.getHeaderRenderer();
      if(renderer == null) {
         renderer = this.header.getDefaultRenderer();
      }

      Component component = renderer.getTableCellRendererComponent(this.header.getTable(), aColumn.getHeaderValue(), false, false, -1, columnIndex);
      this.rendererPane.add(component);
      this.rendererPane.paintComponent(g, component, this.header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
   }

   private void paintCell(Graphics g, Rectangle cellRect, GroupedColumn cGrouped) {
      TableCellRenderer renderer = cGrouped.getHeaderRenderer();
      Component component = renderer.getTableCellRendererComponent(this.header.getTable(), cGrouped.getHeaderValue(), false, false, -1, -1);
      this.rendererPane.add(component);
      this.rendererPane.paintComponent(g, component, this.header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
   }

   private int getHeaderHeight() {
      int height = 18;
      TableColumnModel columnModel = this.header.getColumnModel();

      for(int column = 0; column < columnModel.getColumnCount(); ++column) {
         TableColumn aColumn = columnModel.getColumn(column);
         TableCellRenderer renderer = aColumn.getHeaderRenderer();
         if(renderer == null) {
            renderer = this.header.getDefaultRenderer();
         }

         Component comp = renderer.getTableCellRendererComponent(this.header.getTable(), aColumn.getHeaderValue(), false, false, -1, column);
         int cHeight = comp.getPreferredSize().height;
         Enumeration enumer = ((GroupableTableHeader)this.header).getColumnGroupsEnumeration(aColumn);
         if(enumer != null) {
            while(enumer.hasMoreElements()) {
               GroupedColumn cGrouped = (GroupedColumn)enumer.nextElement();
               cHeight += cGrouped.getSize(this.header.getTable()).height;
            }
         }

         height = Math.max(height, cHeight);
      }

      return height;
   }

   private Dimension createHeaderSize(long width) {
      if(width > 2147483647L) {
         width = 2147483647L;
      }

      return new Dimension((int)width, this.getHeaderHeight());
   }

   public Dimension getPreferredSize(JComponent c) {
      long width = 0L;

      TableColumn aColumn;
      for(Enumeration enumeration = this.header.getColumnModel().getColumns(); enumeration.hasMoreElements(); width += (long)aColumn.getPreferredWidth()) {
         aColumn = (TableColumn)enumeration.nextElement();
      }

      return this.createHeaderSize(width);
   }

   protected MouseInputListener createMouseInputListener() {
      return new GroupableTableHeaderUI$MouseInputHandler(this);
   }

   public static ComponentUI createUI(JComponent h) {
      return new GroupableTableHeaderUI();
   }

   protected void installDefaults() {
      LookAndFeel.installColorsAndFont(this.header, "TableHeader.background", "TableHeader.foreground", "TableHeader.font");
      LookAndFeel.installProperty(this.header, "opaque", Boolean.TRUE);
   }

   protected void installListeners() {
      this.mMouseInputListener = this.createMouseInputListener();
      this.header.addMouseListener(this.mMouseInputListener);
      this.header.addMouseMotionListener(this.mMouseInputListener);
   }

   protected void uninstallListeners() {
      this.header.removeMouseListener(this.mMouseInputListener);
      this.header.removeMouseMotionListener(this.mMouseInputListener);
      this.mMouseInputListener = null;
   }

   private int viewIndexForColumn(TableColumn aColumn) {
      TableColumnModel cm = this.header.getColumnModel();

      for(int column = 0; column < cm.getColumnCount(); ++column) {
         if(cm.getColumn(column) == aColumn) {
            return column;
         }
      }

      return -1;
   }

   public Dimension getMinimumSize(JComponent c) {
      long width = 0L;

      TableColumn aColumn;
      for(Enumeration enumeration = this.header.getColumnModel().getColumns(); enumeration.hasMoreElements(); width += (long)aColumn.getMinWidth()) {
         aColumn = (TableColumn)enumeration.nextElement();
      }

      return this.createHeaderSize(width);
   }

   public Dimension getMaximumSize(JComponent c) {
      long width = 0L;

      TableColumn aColumn;
      for(Enumeration enumeration = this.header.getColumnModel().getColumns(); enumeration.hasMoreElements(); width += (long)aColumn.getMaxWidth()) {
         aColumn = (TableColumn)enumeration.nextElement();
      }

      return this.createHeaderSize(width);
   }

   // $FF: synthetic method
   static Cursor accessMethod000() {
      return resizeCursor;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod100(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod200(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod300(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod400(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod500(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod600(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod700(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod800(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod900(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod1000(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod1100(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod1200(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod1300(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod1400(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod1500(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod1600(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod1700(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod1800(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod1900(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod2000(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod2100(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod2200(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod2300(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod2400(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod2500(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static int accessMethod2600(GroupableTableHeaderUI x0, TableColumn x1) {
      return x0.viewIndexForColumn(x1);
   }

   // $FF: synthetic method
   static JTableHeader accessMethod2700(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod2800(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod2900(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod3000(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static Log accessMethod3100(GroupableTableHeaderUI x0) {
      return x0.mLog;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod3200(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod3300(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod3400(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod3500(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod3600(GroupableTableHeaderUI x0) {
      return x0.header;
   }

   // $FF: synthetic method
   static JTableHeader accessMethod3700(GroupableTableHeaderUI x0) {
      return x0.header;
   }

}
