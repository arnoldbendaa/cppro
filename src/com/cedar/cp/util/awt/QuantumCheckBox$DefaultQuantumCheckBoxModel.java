// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.QuantumStateModel;
import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import javax.swing.JToggleButton.ToggleButtonModel;

class QuantumCheckBox$DefaultQuantumCheckBoxModel extends ToggleButtonModel implements QuantumStateModel {

   private int mQuantumState = 0;
   private boolean mAllowIntermediateState;


   public int getQuantumState() {
      return this.mQuantumState;
   }

   public void setQuantumState(int newState) {
      if(this.getQuantumState() != newState) {
         this.mQuantumState = newState;
         this.fireStateChanged();
      }

   }

   public boolean isSelected() {
      return this.getQuantumState() == 1 || this.getQuantumState() == 2;
   }

   public void setSelected(boolean selected) {
      this.mQuantumState = selected?1:0;
      this.fireStateChanged();
   }

   public boolean isOn() {
      return this.mQuantumState == 1;
   }

   public boolean isBoth() {
      return this.mQuantumState == 2;
   }

   public boolean isOff() {
      return this.mQuantumState == 0;
   }

   public void setPressed(boolean b) {
      if(this.isPressed() != b && this.isEnabled()) {
         if(!b && this.isArmed()) {
            this.setSelected(!this.isSelected());
         }

         if(this.mQuantumState == 0) {
            if(this.isAllowIntermediateState()) {
               this.mQuantumState = 2;
            } else {
               this.mQuantumState = 1;
            }
         } else if(this.mQuantumState == 2) {
            this.mQuantumState = 1;
         } else {
            this.mQuantumState = 0;
         }

         this.fireStateChanged();
         if(!this.isPressed() && this.isArmed()) {
            int modifiers = 0;
            AWTEvent currentEvent = EventQueue.getCurrentEvent();
            if(currentEvent instanceof InputEvent) {
               modifiers = ((InputEvent)currentEvent).getModifiers();
            } else if(currentEvent instanceof ActionEvent) {
               modifiers = ((ActionEvent)currentEvent).getModifiers();
            }

            this.fireActionPerformed(new ActionEvent(this, 1001, this.getActionCommand(), EventQueue.getMostRecentEventTime(), modifiers));
         }

      }
   }

   public boolean isAllowIntermediateState() {
      return this.mAllowIntermediateState;
   }

   public void setAllowIntermediateState(boolean allowIntermediateState) {
      this.mAllowIntermediateState = allowIntermediateState;
   }
}
