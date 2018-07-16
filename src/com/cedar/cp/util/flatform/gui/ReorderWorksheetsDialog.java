// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.awt.OkCancelDialog;
import com.cedar.cp.util.flatform.gui.ReorderWorksheetsDialog$1;
import com.cedar.cp.util.flatform.gui.ReorderWorksheetsDialog$2;
import com.cedar.cp.util.flatform.gui.ReorderWorksheetsDialog$3;
import com.cedar.cp.util.flatform.gui.ReorderWorksheetsDialog$WorksheetOrderTableModel;
import com.cedar.cp.util.flatform.gui.ReorderWorksheetsDialogView;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;

public class ReorderWorksheetsDialog extends OkCancelDialog {

   private ReorderWorksheetsDialog$WorksheetOrderTableModel mTableModel;
   private ReorderWorksheetsDialogView mView;
   private Workbook mWorkbook;


   public ReorderWorksheetsDialog(Frame owner, Workbook workbook) throws HeadlessException {
      super(owner, "Reorder worksheets");
      this.mWorkbook = workbook;
      this.init();
   }

   public ReorderWorksheetsDialog(Dialog owner, Workbook workbook) throws HeadlessException {
      super(owner, "Reorder worksheets");
      this.mWorkbook = workbook;
      this.init();
   }

   protected boolean isOkAllowed() {
      return true;
   }

   protected void buildCenterPanel(Container center) {
      center.add(this.mView = new ReorderWorksheetsDialogView(), "Center");
      this.mView.mTable.setModel(this.mTableModel = new ReorderWorksheetsDialog$WorksheetOrderTableModel(this, (ReorderWorksheetsDialog$1)null));
      this.mView.mTable.setSelectionMode(0);
      this.mView.mNorth.addActionListener(new ReorderWorksheetsDialog$1(this));
      this.mView.mSouth.addActionListener(new ReorderWorksheetsDialog$2(this));
      this.mView.mTable.getSelectionModel().addListSelectionListener(new ReorderWorksheetsDialog$3(this));
      if(!this.mTableModel.getData().isEmpty()) {
         this.mView.mTable.setRowSelectionInterval(0, 0);
      } else {
         this.updateUIState();
      }

      this.mWorkbook.beginGroupEdit();
   }

   protected void handleOk() {
      this.mWorkbook.endGroupEdit();
      super.handleOk();
   }

   protected void handleCancel() {
      this.mWorkbook.endGroupEdit();
      super.handleCancel();
   }

   private void moveNorth() {
      int selectedRow = this.mView.mTable.getSelectedRow();
      if(selectedRow != -1 && this.mWorkbook.moveWorksheet((Worksheet)this.mWorkbook.getWorksheets().get(selectedRow), selectedRow - 1)) {
         this.mTableModel.moveNorth(selectedRow);
      }

      this.updateUIState();
   }

   private void moveSouth() {
      int selectedRow = this.mView.mTable.getSelectedRow();
      if(selectedRow != -1 && this.mWorkbook.moveWorksheet((Worksheet)this.mWorkbook.getWorksheets().get(selectedRow), selectedRow + 1)) {
         this.mTableModel.moveSouth(selectedRow);
      }

      this.updateUIState();
   }

   private void updateUIState() {
      int selectedRow = this.mView.mTable.getSelectedRow();
      if(selectedRow != -1) {
         this.mView.mNorth.setEnabled(selectedRow > 0);
         this.mView.mSouth.setEnabled(selectedRow < this.mTableModel.getData().size() - 1);
      } else {
         this.mView.mNorth.setEnabled(false);
         this.mView.mSouth.setEnabled(false);
      }

   }

   // $FF: synthetic method
   static void accessMethod100(ReorderWorksheetsDialog x0) {
      x0.moveNorth();
   }

   // $FF: synthetic method
   static void accessMethod200(ReorderWorksheetsDialog x0) {
      x0.moveSouth();
   }

   // $FF: synthetic method
   static void accessMethod300(ReorderWorksheetsDialog x0) {
      x0.updateUIState();
   }

   // $FF: synthetic method
   static Workbook accessMethod400(ReorderWorksheetsDialog x0) {
      return x0.mWorkbook;
   }

   // $FF: synthetic method
   static ReorderWorksheetsDialogView accessMethod500(ReorderWorksheetsDialog x0) {
      return x0.mView;
   }
}
