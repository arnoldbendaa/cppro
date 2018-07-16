// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.SparseTableModel;
import com.cedar.cp.util.flatform.gui.WorksheetTable;
import com.cedar.cp.util.flatform.model.Cell;
import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class WorksheetEditor extends DefaultCellEditor {

   private int mViewingLayer = 0;
   private WorksheetTable mWorksheetTable;
   private int mRow;
   private int mColumn;


   public WorksheetEditor(WorksheetTable worksheetTable) {
      super(new JTextField());
      this.mWorksheetTable = worksheetTable;
      this.getComponent().setName("WorksheetEditor");
   }

   public void setViewLayer(int layer) {
      this.mViewingLayer = layer;
   }

   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      ((JComponent)this.getComponent()).setBorder(new LineBorder(Color.black));
      Cell c;
      if(this.mViewingLayer == 0 && value instanceof Cell) {
         c = (Cell)value;
         value = c.getText();
      } else if(this.mViewingLayer == 2 && value instanceof Cell) {
         c = (Cell)value;
         value = c.getInputMapping();
      } else if(this.mViewingLayer == 3 && value instanceof Cell) {
         c = (Cell)value;
         value = c.getOutputMapping();
      } else if(this.mViewingLayer == 1 && value instanceof Cell) {
         c = (Cell)value;
         value = c.getFormulaText();
      }

      this.mRow = row;
      this.mColumn = column;
      Component c1 = super.getTableCellEditorComponent(table, value, isSelected, row, column);
      ((JTextField)this.editorComponent).selectAll();
      return c1;
   }

   public boolean shouldSelectCell(EventObject anEvent) {
      ((JTextField)this.editorComponent).selectAll();
      return true;
   }

   public boolean stopCellEditing() {
      try {
         ((SparseTableModel)this.mWorksheetTable.getModel()).validateCellText(this.mRow, this.mColumn, this.getTextField().getText());
      } catch (ParseException var2) {
         JOptionPane.showMessageDialog(this.getTextField(), var2.getMessage(), "Input validation", 0);
         if(var2.getErrorOffset() >= 0 && var2.getErrorOffset() < this.getTextField().getText().length()) {
            this.getTextField().setCaretPosition(var2.getErrorOffset());
         }

         return false;
      } catch (Throwable var3) {
         JOptionPane.showMessageDialog(this.getTextField(), var3.getMessage(), "Input validation", 0);
         return false;
      }

      return super.stopCellEditing();
   }

   private JTextField getTextField() {
      return (JTextField)this.editorComponent;
   }
}
