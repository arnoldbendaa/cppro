// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorkbookPanel;
import com.cedar.cp.util.flatform.gui.WorkbookPanel$WorkbookTabPaneUI$1;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPopupMenu;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

class WorkbookPanel$WorkbookTabPaneUI extends BasicTabbedPaneUI {

   private int mPopupTabIndex;
   private MouseListener mMouseListener;
   private MouseListener mParentMouseListener;
   private JPopupMenu mPopupMenu;
   // $FF: synthetic field
   final WorkbookPanel this$0;


   WorkbookPanel$WorkbookTabPaneUI(WorkbookPanel var1) {
      this.this$0 = var1;
   }

   protected MouseListener createMouseListener() {
      this.mParentMouseListener = super.createMouseListener();
      this.mMouseListener = new WorkbookPanel$WorkbookTabPaneUI$1(this);
      return this.mMouseListener;
   }

   private void showPopup(MouseEvent e) {
      if(this.mPopupMenu == null) {
         this.mPopupMenu = this.createPopupMenu();
      }

      if(this.mPopupMenu != null && (this.mPopupTabIndex = this.getTabIndex(e.getX(), e.getY())) != -1) {
         this.mPopupMenu.show(this.tabPane, e.getX(), e.getY());
      }

   }

   private int getTabIndex(int x, int y) {
      for(int tabNo = 0; tabNo < this.this$0.getTabCount(); ++tabNo) {
         if(this.getTabBounds(this.tabPane, tabNo).contains(x, y)) {
            return tabNo;
         }
      }

      return -1;
   }

   JPopupMenu createPopupMenu() {
      if(this.this$0.getWorkbook() != null && this.this$0.getWorkbook().isDesignMode()) {
         JPopupMenu menu = new JPopupMenu();
         menu.add(this.this$0.getNewWorksheetAction());
         menu.add(this.this$0.getRemoveWorksheetAction());
         menu.add(this.this$0.getRenameWorksheetAction());
         menu.add(this.this$0.getReorderWorksheetsAction());
         return menu;
      } else {
         return null;
      }
   }

   public int getPopupTabIndex() {
      return this.mPopupTabIndex;
   }

   // $FF: synthetic method
   static MouseListener accessMethod000(WorkbookPanel$WorkbookTabPaneUI x0) {
      return x0.mParentMouseListener;
   }

   // $FF: synthetic method
   static void accessMethod100(WorkbookPanel$WorkbookTabPaneUI x0, MouseEvent x1) {
      x0.showPopup(x1);
   }
}
