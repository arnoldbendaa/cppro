// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.DialogUtils$1;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

public class DialogUtils implements ActionListener {

   private JDialog mDialog = null;
   private Dimension dialogSize = new Dimension();
   private boolean isMaximize = false;
   private Point dialogLocation = new Point();


   public void registerDialog(JDialog component) {
      this.mDialog = component;
      this.mDialog.addComponentListener(new DialogUtils$1(this));
   }

   public void actionPerformed(ActionEvent e) {
      if(this.isMaximize) {
         this.mDialog.setSize(this.dialogSize);
         this.mDialog.setLocation(this.dialogLocation);
         this.isMaximize = false;
      } else {
         this.maximise(this.mDialog);
         this.isMaximize = true;
      }

   }

   protected void handleMoved() {
      this.dialogLocation = this.mDialog.getLocation();
   }

   protected void handleResized() {
      this.dialogSize = this.mDialog.getSize();
   }

   private void maximise(JDialog dialog) {
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice gd = ge.getDefaultScreenDevice();
      Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gd.getDefaultConfiguration());
      dialog.setLocation(insets.left, insets.top);
      dialog.setSize(dim.width - insets.left - insets.right, dim.height - insets.top - insets.bottom);
      dialog.doLayout();
      dialog.invalidate();
      dialog.validate();
   }

   // $FF: synthetic method
   static boolean accessMethod000(DialogUtils x0) {
      return x0.isMaximize;
   }
}
