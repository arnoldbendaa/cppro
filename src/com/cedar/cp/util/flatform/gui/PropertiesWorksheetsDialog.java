// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.awt.OkCancelDialog;
import com.cedar.cp.util.flatform.gui.PropertiesWorksheetsDialogView;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

public class PropertiesWorksheetsDialog extends OkCancelDialog {

   private static final long serialVersionUID = 1L;
   private PropertiesWorksheetsDialogView mView;
   private Workbook mWorkbook;
   private Worksheet mWorksheet;


   public PropertiesWorksheetsDialog(Frame owner, Workbook workbook, Worksheet worksheet) throws HeadlessException {
      super(owner, "Worksheet Properties");
      this.mWorkbook = workbook;
      this.mWorksheet = worksheet;
      this.init();
   }

   public PropertiesWorksheetsDialog(Dialog owner, Workbook workbook, Worksheet worksheet) throws HeadlessException {
      super(owner, "Worksheet Properties");
      this.mWorkbook = workbook;
      this.mWorksheet = worksheet;
      this.init();
   }

   protected boolean isOkAllowed() {
      return true;
   }

   protected void buildCenterPanel(Container center) {
      center.add(this.mView = new PropertiesWorksheetsDialogView(), "Center");
      this.setSheetName(this.mWorksheet.getName());
      this.setHidden(this.mWorksheet.isHidden());
      this.mWorkbook.beginGroupEdit();
   }

   protected void handleOk() {
      String newSheetName = this.getSheetName();
      if(newSheetName != null) {
         if(newSheetName.trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "A sheet must have a name with one or more non whitespace characters.");
         } else if(!this.mWorkbook.renameWorksheet(this.mWorksheet.getName(), newSheetName, this.isHidden())) {
            JOptionPane.showMessageDialog(this, "A sheet of this name is already present in the workbook. Please select another name.");
         } else {
            this.mWorksheet.setHidden(this.isHidden());
            this.mWorkbook.endGroupEdit();
            super.handleOk();
         }
      }

   }

   protected void handleCancel() {
      this.mWorkbook.endGroupEdit();
      super.handleCancel();
   }

   public String getSheetName() {
      return this.mView.mNewName.getText();
   }

   private void setSheetName(String sheetName) {
      this.mView.mNewName.setText(sheetName);
   }

   public boolean isHidden() {
      return this.mView.mHidden.isOn();
   }

   private void setHidden(boolean hidden) {
      this.mView.mHidden.setSelected(hidden);
   }
}
