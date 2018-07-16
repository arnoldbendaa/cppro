// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorkbookPanel$WorkbookTabPaneUI;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class WorkbookPanel$WorkbookTabPaneUI$1 implements MouseListener {

   // $FF: synthetic field
   final WorkbookPanel$WorkbookTabPaneUI this$1;


   WorkbookPanel$WorkbookTabPaneUI$1(WorkbookPanel$WorkbookTabPaneUI var1) {
      this.this$1 = var1;
   }

   public void mousePressed(MouseEvent e) {
      WorkbookPanel$WorkbookTabPaneUI.accessMethod000(this.this$1).mousePressed(e);
      if(!e.isConsumed()) {
         this.popupTrigger(e);
      }

   }

   public void mouseReleased(MouseEvent e) {
      WorkbookPanel$WorkbookTabPaneUI.accessMethod000(this.this$1).mouseReleased(e);
      if(!e.isConsumed()) {
         this.popupTrigger(e);
      }

   }

   private void popupTrigger(MouseEvent e) {
      if(e.isPopupTrigger()) {
         WorkbookPanel$WorkbookTabPaneUI.accessMethod100(this.this$1, e);
      }

   }

   public void mouseClicked(MouseEvent e) {
      WorkbookPanel$WorkbookTabPaneUI.accessMethod000(this.this$1).mouseClicked(e);
   }

   public void mouseEntered(MouseEvent e) {
      WorkbookPanel$WorkbookTabPaneUI.accessMethod000(this.this$1).mouseEntered(e);
   }

   public void mouseExited(MouseEvent e) {
      WorkbookPanel$WorkbookTabPaneUI.accessMethod000(this.this$1).mouseExited(e);
   }
}
