// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.awt.CalcButton;
import com.cedar.cp.util.awt.CalculatorDialog$1;
import com.cedar.cp.util.awt.CalculatorDialog$2;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

public class CalculatorDialog extends JDialog implements ActionListener {

   private static final String UPDATE = "Update";
   private static final String CANCEL = "Cancel";
   private static final String X_SQUARED = "x²";
   protected static CalculatorDialog sCalc = null;
   private JTextField mDisplay;
   private JLabel mHasMemory;
   private double mRegister1;
   private double mRegister2;
   private double mMemory;
   private char mOperand;
   private boolean mIsClear;
   private JTextField mField;


   public static CalculatorDialog getCalculator(Frame owner) {
      if(sCalc == null) {
         sCalc = new CalculatorDialog(owner);
      }

      return sCalc;
   }

   public static CalculatorDialog getCalculator(Dialog owner) {
      if(sCalc == null) {
         sCalc = new CalculatorDialog(owner);
      }

      return sCalc;
   }

   public CalculatorDialog(Frame owner) {
      super(owner, "CP Calculator", true);
      this.layoutPanel();
      this.setResizable(false);
      this.resetCalculator();
   }

   public CalculatorDialog(Dialog owner) {
      super(owner, "CP Calculator", true);
      this.layoutPanel();
      this.setResizable(false);
      this.resetCalculator();
   }

   public void showCalculator(JTextField field) {
      this.mField = field;
      this.resetCalculator();
      if(field != null && field.getText() != null && field.getText().trim().length() != 0) {
         this.mDisplay.setText(field.getText());
         this.mIsClear = true;
      }

      this.setLocationRelativeTo((Component)null);
      this.setVisible(true);
   }

   private void layoutPanel() {
      Container cp = this.getContentPane();
      cp.setLayout(new BorderLayout());
      this.mDisplay = new JTextField(16);
      this.mDisplay.setEditable(false);
      this.mDisplay.setHorizontalAlignment(4);
      this.mDisplay.setText("0");
      this.mDisplay.setFocusable(true);
      this.mDisplay.addKeyListener(new CalculatorDialog$1(this));
      JPanel displayPanel = new JPanel(new FlowLayout());
      displayPanel.setBorder(new TitledBorder("Display"));
      displayPanel.add(this.mDisplay);
      JPanel keysPanel = new JPanel(new GridLayout(6, 5, 3, 3));
      keysPanel.setBorder(new EmptyBorder(0, 3, 5, 3));
      keysPanel.add(this.mHasMemory = new JLabel());
      keysPanel.add(new JLabel(""));
      keysPanel.add(new JLabel(""));
      keysPanel.add(new CalcButton("C/CE", this));
      keysPanel.add(new CalcButton("AC", this));
      keysPanel.add(new CalcButton("MC", this));
      keysPanel.add(new CalcButton("x²", this));
      keysPanel.add(new CalcButton("sqrt", this));
      keysPanel.add(new CalcButton("%", this));
      keysPanel.add(new CalcButton("1/x", this));
      keysPanel.add(new CalcButton("MR", this));
      keysPanel.add(new CalcButton("7", this));
      keysPanel.add(new CalcButton("8", this));
      keysPanel.add(new CalcButton("9", this));
      keysPanel.add(new CalcButton("/", this));
      keysPanel.add(new CalcButton("M+", this));
      keysPanel.add(new CalcButton("4", this));
      keysPanel.add(new CalcButton("5", this));
      keysPanel.add(new CalcButton("6", this));
      keysPanel.add(new CalcButton("*", this));
      keysPanel.add(new CalcButton("M-", this));
      keysPanel.add(new CalcButton("1", this));
      keysPanel.add(new CalcButton("2", this));
      keysPanel.add(new CalcButton("3", this));
      keysPanel.add(new CalcButton("-", this));
      keysPanel.add(new CalcButton("+/-", this));
      keysPanel.add(new CalcButton(".", this));
      keysPanel.add(new CalcButton("0", this));
      keysPanel.add(new CalcButton("=", this));
      keysPanel.add(new CalcButton("+", this));
      JPanel buttonPanel = new JPanel(new FlowLayout());
      buttonPanel.setBorder(new MatteBorder(1, 0, 0, 0, (new TitledBorder("")).getTitleColor()));
      JButton update = new JButton("Update");
      update.addActionListener(this);
      JButton cancel = new JButton("Cancel");
      cancel.addActionListener(this);
      buttonPanel.add(update);
      buttonPanel.add(cancel);
      cp.add(displayPanel, "North");
      cp.add(keysPanel, "Center");
      cp.add(buttonPanel, "South");
      this.pack();
      this.addWindowListener(new CalculatorDialog$2(this));
   }

   private void resetCalculator() {
      this.mRegister1 = 0.0D;
      this.mRegister2 = 0.0D;
      this.mMemory = 0.0D;
      this.mHasMemory.setText("");
      this.mIsClear = true;
      this.mOperand = 61;
      this.mDisplay.setText("0.0");
   }

   public void actionPerformed(ActionEvent ae) {
      if(ae.getActionCommand().equals("AC")) {
         this.resetCalculator();
      } else if(ae.getActionCommand().equals("C/CE")) {
         if(this.mRegister2 != 0.0D) {
            this.mRegister2 = 0.0D;
         } else {
            this.mRegister1 = 0.0D;
         }
      } else if(!ae.getActionCommand().equals("0") && !ae.getActionCommand().equals("1") && !ae.getActionCommand().equals("2") && !ae.getActionCommand().equals("3") && !ae.getActionCommand().equals("4") && !ae.getActionCommand().equals("5") && !ae.getActionCommand().equals("6") && !ae.getActionCommand().equals("7") && !ae.getActionCommand().equals("8") && !ae.getActionCommand().equals("9")) {
         if(ae.getActionCommand().equals(".")) {
            if(this.mDisplay.getText().indexOf(".") == -1) {
               this.mDisplay.setText(this.mIsClear?"0.":this.mDisplay.getText() + ".");
            } else {
               this.mDisplay.setText("0.");
            }

            this.mIsClear = false;
         } else if(!ae.getActionCommand().equals("+") && !ae.getActionCommand().equals("-") && !ae.getActionCommand().equals("*") && !ae.getActionCommand().equals("/") && !ae.getActionCommand().equals("=")) {
            if(ae.getActionCommand().equals("+/-")) {
               if(this.mDisplay.getText().startsWith("-")) {
                  this.mDisplay.setText(this.mDisplay.getText().substring(1));
               } else {
                  this.mDisplay.setText("-" + this.mDisplay.getText());
               }
            } else if(ae.getActionCommand().equals("sqrt")) {
               this.mRegister2 = Double.valueOf(this.mDisplay.getText()).doubleValue();
               this.mRegister1 = Math.sqrt(this.mRegister2);
               this.mDisplay.setText(String.valueOf(this.mRegister1));
               this.mOperand = 61;
               this.mIsClear = true;
            } else if(ae.getActionCommand().equals("x²")) {
               this.mRegister2 = Double.valueOf(this.mDisplay.getText()).doubleValue();
               this.mRegister1 = this.mRegister2 * this.mRegister2;
               this.mDisplay.setText(String.valueOf(this.mRegister1));
               this.mOperand = 61;
               this.mIsClear = true;
            } else if(ae.getActionCommand().equals("%")) {
               this.mRegister2 = Double.valueOf(this.mDisplay.getText()).doubleValue();
               this.mRegister2 = this.mRegister1 * this.mRegister2 / 100.0D;
               this.mDisplay.setText(String.valueOf(this.mRegister2));
               this.mIsClear = true;
            } else if(ae.getActionCommand().equals("1/x")) {
               this.mRegister2 = Double.valueOf(this.mDisplay.getText()).doubleValue();
               this.mRegister2 = 1.0D / this.mRegister2;
               this.mDisplay.setText(String.valueOf(this.mRegister2));
               this.mIsClear = true;
            } else if(ae.getActionCommand().equals("M+")) {
               this.mHasMemory.setText("M");
               this.mMemory += Double.valueOf(this.mDisplay.getText()).doubleValue();
               this.mIsClear = true;
            } else if(ae.getActionCommand().equals("M-")) {
               this.mHasMemory.setText("M");
               this.mMemory -= Double.valueOf(this.mDisplay.getText()).doubleValue();
            } else if(ae.getActionCommand().equals("MR")) {
               this.mDisplay.setText(String.valueOf(this.mMemory));
               this.mIsClear = true;
            } else if(ae.getActionCommand().equals("MC")) {
               this.mMemory = 0.0D;
               this.mHasMemory.setText("");
            } else if(ae.getActionCommand().equals("Cancel")) {
               this.setVisible(false);
            } else if(ae.getActionCommand().equals("Update")) {
               this.mField.setText(this.mDisplay.getText());
               this.setVisible(false);
            }
         } else {
            this.mRegister2 = Double.valueOf(this.mDisplay.getText()).doubleValue();
            this.doCalculation();
            this.mOperand = ae.getActionCommand().charAt(0);
            this.mDisplay.setText(String.valueOf(this.mRegister1));
            this.mIsClear = true;
         }
      } else {
         String digit = ae.getActionCommand();
         this.mDisplay.setText(this.mIsClear?digit:this.mDisplay.getText() + digit);
         this.mIsClear = false;
      }

      this.mDisplay.requestFocus();
   }

   private void doCalculation() {
      switch(this.mOperand) {
      case 42:
         this.mRegister1 *= this.mRegister2;
         break;
      case 43:
         this.mRegister1 += this.mRegister2;
         break;
      case 44:
      case 46:
      default:
         this.mRegister1 = this.mRegister2;
         break;
      case 45:
         this.mRegister1 -= this.mRegister2;
         break;
      case 47:
         this.mRegister1 /= this.mRegister2;
      }

   }

   // $FF: synthetic method
   static JTextField accessMethod000(CalculatorDialog x0) {
      return x0.mDisplay;
   }

   // $FF: synthetic method
   static JTextField accessMethod100(CalculatorDialog x0) {
      return x0.mField;
   }

}
