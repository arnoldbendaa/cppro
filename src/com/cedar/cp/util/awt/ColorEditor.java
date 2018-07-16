// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ColorEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

   Color currentColor;
   JButton button = new JButton();
   JColorChooser colorChooser;
   JDialog dialog;
   protected static final String EDIT = "edit";


   public ColorEditor() {
      this.button.setActionCommand("edit");
      this.button.addActionListener(this);
      this.button.setBorderPainted(false);
      this.colorChooser = new JColorChooser();
      this.dialog = JColorChooser.createDialog(this.button, "Pick a Color", true, this.colorChooser, this, (ActionListener)null);
   }

   public void actionPerformed(ActionEvent e) {
      if("edit".equals(e.getActionCommand())) {
         this.button.setBackground(this.currentColor);
         this.colorChooser.setColor(this.currentColor);
         this.dialog.setVisible(true);
         this.fireEditingStopped();
      } else {
         this.currentColor = this.colorChooser.getColor();
      }

   }

   public Object getCellEditorValue() {
      return this.currentColor;
   }

   public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      this.currentColor = (Color)value;
      return this.button;
   }
}
