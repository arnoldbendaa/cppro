// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.TableSorter;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class QThinTableHeaderRenderer extends DefaultTableCellRenderer {

   private Icon mAsc;
   private Icon mDesc;


   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      this.setText(value == null?" ":value.toString());
      this.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
      TableModel model = table.getModel();
      if(model instanceof TableSorter) {
         TableSorter sorter = (TableSorter)model;
         if(sorter.getSortingColumn() == column) {
            if(sorter.isAscending()) {
               this.setIcon(this.getUpIcon());
            } else {
               this.setIcon(this.getDownIcon());
            }
         } else {
            this.setIcon((Icon)null);
         }
      }

      return this;
   }

   private Icon getUpIcon() {
      if(this.mAsc == null) {
         this.mAsc = this.getIcon("sort_up.gif");
      }

      return this.mAsc;
   }

   private Icon getDownIcon() {
      if(this.mDesc == null) {
         this.mDesc = this.getIcon("sort_down.gif");
      }

      return this.mDesc;
   }

   private Icon getIcon(String name) {
      URL imageURL = this.getClass().getResource(name);
      Image img = Toolkit.getDefaultToolkit().getImage(imageURL);
      return new ImageIcon(img);
   }
}
