// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.awt;

import com.cedar.cp.utc.awt.ProgressDialog$1;
import com.cedar.cp.utc.awt.ProgressDialog$2;
import com.cedar.cp.utc.awt.ProgressDialog$3;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public abstract class ProgressDialog extends JDialog {

   private JButton mCancel;
   private boolean mCancelled;
   private Component mMessage;
   private JProgressBar mProgressBar;


   public ProgressDialog(Frame owner, String title, Component message) {
      super(owner, title, true);
      this.setDefaultCloseOperation(0);
      this.mMessage = message;
      this.buildControls();
   }

   public ProgressDialog(Dialog owner, String title, Component message) {
      super(owner, title, true);
      this.setDefaultCloseOperation(0);
      this.mMessage = message;
      this.buildControls();
   }

   public void buildControls() {
      JPanel buttons = new JPanel(new FlowLayout(2));
      this.mCancel = new JButton("Cancel");
      this.mCancel.addActionListener(new ProgressDialog$1(this));
      buttons.add(this.mCancel);
      JComponent content = (JComponent)this.getContentPane();
      content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      if(this.mMessage != null) {
         content.add(this.mMessage, "North");
         this.mProgressBar = new JProgressBar();
         this.mProgressBar.setIndeterminate(true);
         content.add(this.mProgressBar);
         content.add(buttons, "South");
      }

   }

   public void runAndShow() {
      this.runAndShow(true);
   }

   public void runAndShow(boolean modal) {
      this.setModal(modal);
      this.addWindowListener(new ProgressDialog$2(this));
      this.pack();
      this.setResizable(false);
      this.setLocationRelativeTo(this.getOwner());
      this.setVisible(true);
   }

   protected JProgressBar getProgressBar() {
      return this.mProgressBar;
   }

   protected boolean notCancelled() {
      return !this.mCancelled;
   }

   protected void showUserMessage(String msg) {
      try {
         SwingUtilities.invokeLater(new ProgressDialog$3(this, msg));
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public abstract void doWork() throws Exception;

   // $FF: synthetic method
   static boolean access$002(ProgressDialog x0, boolean x1) {
      return x0.mCancelled = x1;
   }
}
