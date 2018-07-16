// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class DefaultQTableCellRenderer extends JPanel implements TableCellRenderer {

   public static final int JCHECKBOX = 0;
   public static final int JRADIOBUTTON = 1;
   public static final int JCOMBOBOX = 2;
   protected Border noFocusBorder;
   private Color unselectedForeground;
   private Color unselectedBackground;
   private JComponent mComponent;


   public DefaultQTableCellRenderer() {
      this(0);
   }

   public DefaultQTableCellRenderer(int type) {
      this.noFocusBorder = new EmptyBorder(1, 1, 1, 1);
      this.setOpaque(true);
      this.setBorder(this.noFocusBorder);
      this.setLayout(new FlowLayout(1, 0, 0));
      this.mComponent = this.createComponent(type);
      this.add(this.mComponent);
      this.mComponent.setBorder(new EmptyBorder(0, 0, 0, 0));
   }

   public JComponent createComponent(int type) {
      return (JComponent)(type == 0?new JCheckBox():(type == 1?new JRadioButton():(type == 2?new JComboBox():null)));
   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      this.mComponent.setEnabled(this.isValid(table, value, isSelected, hasFocus, row, column));
      if(isSelected) {
         super.setForeground(table.getSelectionForeground());
         super.setBackground(table.getSelectionBackground());
      } else {
         super.setForeground(this.unselectedForeground != null?this.unselectedForeground:table.getForeground());
         super.setBackground(this.unselectedBackground != null?this.unselectedBackground:table.getBackground());
      }

      if(hasFocus) {
         this.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
         if(table.isCellEditable(row, column)) {
            super.setForeground(UIManager.getColor("Table.focusCellForeground"));
            super.setBackground(UIManager.getColor("Table.focusCellBackground"));
         }
      } else {
         this.setBorder(this.noFocusBorder);
      }

      this.setValue(value);
      Color back = this.getBackground();
      boolean colorMatch = back != null && back.equals(table.getBackground()) && table.isOpaque();
      this.setOpaque(!colorMatch);
      return this;
   }

   public void setForeground(Color c) {
      super.setForeground(c);
      this.unselectedForeground = c;
   }

   public void setBackground(Color c) {
      super.setBackground(c);
      this.unselectedBackground = c;
   }

   public void updateUI() {
      super.updateUI();
      this.setForeground((Color)null);
      this.setBackground((Color)null);
   }

   protected void setValue(Object value) {
      Boolean val;
      if(this.mComponent instanceof JCheckBox) {
         val = (Boolean)value;
         ((JCheckBox)this.mComponent).setSelected(val.booleanValue());
      }

      if(this.mComponent instanceof JRadioButton) {
         val = (Boolean)value;
         ((JRadioButton)this.mComponent).setSelected(val.booleanValue());
      }

      if(this.mComponent instanceof JComboBox) {
         ((JComboBox)this.mComponent).setSelectedItem(value);
      }

   }

   public boolean isValid(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      return true;
   }
}
