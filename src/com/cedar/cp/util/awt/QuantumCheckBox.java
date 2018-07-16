// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.QuantumCheckBox$1;
import com.cedar.cp.util.awt.QuantumCheckBox$DefaultQuantumCheckBoxModel;
import com.cedar.cp.util.awt.QuantumCheckBox$QuantumCheckBoxUI;
import com.cedar.cp.util.awt.QuantumStateModel;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class QuantumCheckBox extends JCheckBox {

   private static final String uiClassID = "QuantumCheckBoxUI";


   public QuantumCheckBox() {
      this.setModel(new QuantumCheckBox$DefaultQuantumCheckBoxModel());
   }

   public QuantumCheckBox(String label) {
      super(label);
      this.setModel(new QuantumCheckBox$DefaultQuantumCheckBoxModel());
   }

   public int getQuantumState() {
      return ((QuantumStateModel)this.getModel()).getQuantumState();
   }

   public void setQuantumState(int state) {
      ((QuantumStateModel)this.getModel()).setQuantumState(state);
   }

   public boolean isOn() {
      return ((QuantumStateModel)this.getModel()).isOn();
   }

   public boolean isOff() {
      return ((QuantumStateModel)this.getModel()).isOff();
   }

   public boolean isBoth() {
      return ((QuantumStateModel)this.getModel()).isBoth();
   }

   public boolean isAllowIntermediateState() {
      return ((QuantumStateModel)this.getModel()).isAllowIntermediateState();
   }

   public void setAllowIntermediateState(boolean state) {
      ((QuantumStateModel)this.getModel()).setAllowIntermediateState(state);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new QuantumCheckBox$1());
   }

   public void setUI(QuantumCheckBox$QuantumCheckBoxUI ui) {
      super.setUI(ui);
   }

   public void updateUI() {
      if(UIManager.get(this.getUIClassID()) != null) {
         this.setUI((QuantumCheckBox$QuantumCheckBoxUI)UIManager.getUI(this));
      } else {
         this.setUI(QuantumCheckBox$QuantumCheckBoxUI.createUI(this));
      }

   }

   public QuantumCheckBox$QuantumCheckBoxUI getUI() {
      return (QuantumCheckBox$QuantumCheckBoxUI)this.ui;
   }

   public String getUIClassID() {
      return "QuantumCheckBoxUI";
   }
}
