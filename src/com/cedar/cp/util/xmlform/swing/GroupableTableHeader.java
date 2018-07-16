// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;

import com.cedar.cp.util.xmlform.swing.GroupableTableHeaderUI;
import com.cedar.cp.util.xmlform.swing.GroupedColumn;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class GroupableTableHeader extends JTableHeader {

   private static final String uiClassID = "GroupableTableHeaderUI";
   protected Vector mColumnGroups = null;


   public GroupableTableHeader(TableColumnModel model) {
      super(model);
      this.setColumnModel(model);
      this.setReorderingAllowed(true);
   }

   public void updateUI() {
      this.setUI(new GroupableTableHeaderUI());
      this.resizeAndRepaint();
      this.invalidate();
   }

   public void removeColumnGroups() {
      this.mColumnGroups = null;
   }

   public void addColumnGroup(GroupedColumn g) {
      if(this.mColumnGroups == null) {
         this.mColumnGroups = new Vector();
      }

      this.mColumnGroups.addElement(g);
   }

   public Enumeration getColumnGroupsEnumeration(TableColumn col) {
      if(this.mColumnGroups == null) {
         return null;
      } else {
         Enumeration enumerate = this.mColumnGroups.elements();

         Vector v_ret;
         do {
            if(!enumerate.hasMoreElements()) {
               return null;
            }

            GroupedColumn cGrouped = (GroupedColumn)enumerate.nextElement();
            v_ret = cGrouped.getColumnGroups(col, new Vector());
         } while(v_ret == null);

         return v_ret.elements();
      }
   }

   public Vector getColumnGroups() {
      return this.mColumnGroups;
   }

   public int getMaxDepth() {
      int maxDepth = 1;
      if(this.mColumnGroups != null) {
         Enumeration enumerate = this.mColumnGroups.elements();

         while(enumerate.hasMoreElements()) {
            Object obj = enumerate.nextElement();
            if(obj instanceof GroupedColumn) {
               int depth = ((GroupedColumn)obj).getMaxDepth();
               if(depth > maxDepth) {
                  maxDepth = depth;
               }
            }
         }
      }

      return maxDepth;
   }
}
