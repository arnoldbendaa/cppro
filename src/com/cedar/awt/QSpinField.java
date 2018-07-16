// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:50
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.LittleButton;
import com.cedar.awt.NumberField;
import com.cedar.awt.QSpinField$1;
import com.cedar.awt.QSpinField$2;
import com.cedar.awt.QSpinField$3;
import java.awt.BorderLayout;
import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.basic.BasicArrowButton;

public class QSpinField extends JPanel implements ItemSelectable {

   private BorderLayout borderLayout1;
   private JTextField mField;
   private BasicArrowButton mUpButton;
   private BasicArrowButton mDownButton;
   private Box box1;
   private int mValue;
   private int mMinValue;
   private int mMaxValue;
   private EventListenerList mListenerList;


   public QSpinField() {
      this(1, Integer.MIN_VALUE, Integer.MAX_VALUE);
   }

   public QSpinField(int value) {
      this(value, Integer.MIN_VALUE, Integer.MAX_VALUE);
   }

   public QSpinField(int value, int minValue, int maxValue) {
      this.borderLayout1 = new BorderLayout();
      this.mField = new NumberField();
      this.mUpButton = new LittleButton(1);
      this.mDownButton = new LittleButton(5);
      this.mListenerList = new EventListenerList();
      if(minValue > maxValue) {
         minValue = maxValue;
      }

      this.mMinValue = minValue;
      this.mMaxValue = maxValue;
      if(value < this.mMinValue) {
         value = this.mMinValue;
      }

      if(value > this.mMaxValue) {
         value = this.mMaxValue;
      }

      this.ctor(value);
   }

   private void ctor(int value) {
      try {
         this.jbInit();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      this.mValue = value;
      this.valueToScreen();
   }

   private void jbInit() throws Exception {
      this.box1 = Box.createVerticalBox();
      this.setLayout(this.borderLayout1);
      this.add(this.mField, "Center");
      this.add(this.box1, "East");
      this.box1.add(this.mUpButton, (Object)null);
      this.box1.add(this.mDownButton, (Object)null);
      this.enableButtons();
      this.mUpButton.addActionListener(new QSpinField$1(this));
      this.mDownButton.addActionListener(new QSpinField$2(this));
      this.mField.getDocument().addDocumentListener(new QSpinField$3(this));
   }

   public void enableButtons() {
      this.mUpButton.setEnabled(this.mValue < this.mMaxValue);
      this.mDownButton.setEnabled(this.mValue > this.mMinValue);
   }

   private void valueToScreen() {
      this.mField.setText(Integer.toString(this.mValue));
   }

   private void screenToValue() {
      int value;
      if(this.mField.getText().trim().length() == 0) {
         value = 0;
      } else {
         value = Integer.decode(this.mField.getText()).intValue();
      }

      if(value != this.mValue) {
         this.mValue = value;
         this.fireItemStateChanged(1, new Integer(this.mValue));
      }

   }

   private void fieldChanged() {
      this.screenToValue();
   }

   protected void fireItemStateChanged(int stateChange, Object item) {
      Object[] listeners = this.mListenerList.getListenerList();
      ItemEvent e = null;

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == ItemListener.class) {
            if(e == null) {
               e = new ItemEvent(this, 701, item, stateChange);
            }

            ((ItemListener)listeners[i + 1]).itemStateChanged(e);
         }
      }

   }

   public void setValue(int value) {
      if(this.mValue != value) {
         this.mValue = value;
         this.valueToScreen();
         this.fireItemStateChanged(1, new Integer(this.mValue));
      }

   }

   public int getValue() {
      return this.mValue;
   }

   public void addItemListener(ItemListener l) {
      this.mListenerList.add(ItemListener.class, l);
   }

   public void removeItemListener(ItemListener l) {
      this.mListenerList.remove(ItemListener.class, l);
   }

   public Object[] getSelectedObjects() {
      return null;
   }

   public void setEnabled(boolean b) {
      super.setEnabled(b);
      this.mField.setEnabled(b);
      this.mUpButton.setEnabled(b);
      this.mDownButton.setEnabled(b);
   }

   // $FF: synthetic method
   static int accessMethod008(QSpinField x0) {
      return x0.mValue++;
   }

   // $FF: synthetic method
   static void accessMethod100(QSpinField x0) {
      x0.valueToScreen();
   }

   // $FF: synthetic method
   static int accessMethod000(QSpinField x0) {
      return x0.mValue;
   }

   // $FF: synthetic method
   static int accessMethod010(QSpinField x0) {
      return x0.mValue--;
   }

   // $FF: synthetic method
   static void accessMethod200(QSpinField x0) {
      x0.fieldChanged();
   }
}
