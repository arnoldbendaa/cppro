// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$1;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$10;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$11;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$12;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$13;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$14;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$15;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$2;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$3;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$4;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$5;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$6;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$7;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$8;
import com.cedar.cp.util.flatform.gui.WorksheetPopups$9;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

public class WorksheetPopups {

   private WorksheetPanel mWorksheetPanel;
   private MouseEvent mLastMouseEvent;
   private int mCurrentColumn;
   private int mCurrentRow;
   private AbstractAction mInsertColumnAction;
   private AbstractAction mDeleteColumnAction;
   private AbstractAction mInsertRowAction;
   private AbstractAction mDeleteRowAction;
   private AbstractAction mImageClipAction;
   private AbstractAction mImageScaleAction;
   private AbstractAction mClearAllContentsAction;
   private AbstractAction mClearDataAction;
   private AbstractAction mClearInputMappingAction;
   private AbstractAction mClearOutputMappingAction;
   private AbstractAction mCopyInput2OutputMappingAction;
   private AbstractAction mCopyOutput2InputMappingAction;
   private AbstractAction mCellFormatAction;
   private AbstractAction mResetCellFormatAction;


   WorksheetPopups(WorksheetPanel worksheet) {
      this.mWorksheetPanel = worksheet;
      this.mInsertColumnAction = new WorksheetPopups$1(this, "Insert");
      this.mDeleteColumnAction = new WorksheetPopups$2(this, "Delete");
      this.mInsertRowAction = new WorksheetPopups$3(this, "Insert");
      this.mDeleteRowAction = new WorksheetPopups$4(this, "Delete");
      this.mCellFormatAction = new WorksheetPopups$5(this, "Format Cells...");
      this.mResetCellFormatAction = new WorksheetPopups$6(this, "Reset Cell Format");
      this.mImageClipAction = new WorksheetPopups$7(this, "Clip");
      this.mImageScaleAction = new WorksheetPopups$8(this, "Scale");
      this.mClearAllContentsAction = new WorksheetPopups$9(this, "Clear All");
      this.mClearDataAction = new WorksheetPopups$10(this, "Clear Data");
      this.mClearInputMappingAction = new WorksheetPopups$11(this, "Clear Input Mapping");
      this.mClearOutputMappingAction = new WorksheetPopups$12(this, "Clear Output Mapping");
      this.mCopyInput2OutputMappingAction = new WorksheetPopups$13(this, "Copy Input To Output Mapping");
      this.mCopyOutput2InputMappingAction = new WorksheetPopups$14(this, "Copy Output To Input Mapping");
   }

   JPopupMenu getTopLeftPopup(MouseEvent me) {
      this.mLastMouseEvent = me;
      JPopupMenu menu = new JPopupMenu("Column Popup");
      if(this.isDesignMode()) {
         menu.add(this.mClearAllContentsAction);
         menu.addSeparator();
         menu.add(this.mCellFormatAction);
         menu.add(this.mResetCellFormatAction);
      }

      return menu;
   }

   JPopupMenu getColumnPopup(MouseEvent me, int colIndex) {
      this.mLastMouseEvent = me;
      this.mCurrentColumn = colIndex;
      JPopupMenu menu = new JPopupMenu("Column Popup");
      if(this.isDesignMode()) {
         menu.add(this.mInsertColumnAction);
         menu.add(this.mDeleteColumnAction);
         menu.add(this.mClearAllContentsAction);
         menu.addSeparator();
         menu.add(this.mCellFormatAction);
         menu.add(this.mResetCellFormatAction);
      }

      return menu;
   }

   JPopupMenu getRowPopup(MouseEvent me, int rowIndex) {
      this.mLastMouseEvent = me;
      this.mCurrentRow = rowIndex;
      JPopupMenu menu = new JPopupMenu("Row Popup");
      if(this.isDesignMode()) {
         menu.add(this.mInsertRowAction);
         menu.add(this.mDeleteRowAction);
         menu.add(this.mClearAllContentsAction);
         menu.addSeparator();
         menu.add(this.mCellFormatAction);
         menu.add(this.mResetCellFormatAction);
      }

      return menu;
   }

   JPopupMenu getCellPopup(MouseEvent me) {
      this.mLastMouseEvent = me;
      if(this.isDesignMode()) {
         JPopupMenu menu = new JPopupMenu("Cell Popup");
         JMenu clearContents;
         if(this.mWorksheetPanel.isImageCellSelected()) {
            clearContents = new JMenu("Image");
            clearContents.add(this.mImageClipAction);
            clearContents.add(this.mImageScaleAction);
            menu.add(clearContents);
         }

         clearContents = new JMenu("Clear Contents");
         clearContents.add(this.mClearAllContentsAction);
         clearContents.add(this.mClearDataAction);
         clearContents.add(this.mClearInputMappingAction);
         clearContents.add(this.mClearOutputMappingAction);
         menu.add(clearContents);
         menu.add(this.mCopyInput2OutputMappingAction);
         menu.add(this.mCopyOutput2InputMappingAction);
         menu.addSeparator();
         menu.add(this.mCellFormatAction);
         if(this.mWorksheetPanel.getCellPickerActionListener() != null) {
            WorksheetPopups$15 cellPicker = new WorksheetPopups$15(this, "Cell Picker...");
            menu.addSeparator();
            menu.add(cellPicker);
         }

         return menu;
      } else {
         return null;
      }
   }

   private boolean isDesignMode() {
      return this.mWorksheetPanel.isDesignMode();
   }

   // $FF: synthetic method
   static int accessMethod000(WorksheetPopups x0) {
      return x0.mCurrentColumn;
   }

   // $FF: synthetic method
   static WorksheetPanel accessMethod100(WorksheetPopups x0) {
      return x0.mWorksheetPanel;
   }

   // $FF: synthetic method
   static int accessMethod200(WorksheetPopups x0) {
      return x0.mCurrentRow;
   }

   // $FF: synthetic method
   static MouseEvent accessMethod300(WorksheetPopups x0) {
      return x0.mLastMouseEvent;
   }
}
