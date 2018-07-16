// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.ImageLoader;
import com.cedar.cp.util.MaximizeToolbar$1;
import com.cedar.cp.util.MaximizeToolbar$MaxActionLinstener;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JToolBar;

public class MaximizeToolbar {

   public static final String MAXIMIZE = "Maximize";
   public static final String RESTORE_DOWN = "Restore Down";
   protected JButton mMaximiseButtonToolbar = new JButton();
   protected JButton mMaximiseButton = new JButton("Maximize");
   private Dimension mDialogSize = new Dimension();
   private boolean mIsMaximize = false;
   private Point mDialogLocation = new Point();
   private JDialog mDialog = null;
   private JToolBar mToolBar = new JToolBar();
   private MaximizeToolbar$MaxActionLinstener maxActionLinstener = new MaximizeToolbar$MaxActionLinstener(this, (MaximizeToolbar$1)null);


   public MaximizeToolbar(JDialog dialog) {
      this.mDialog = dialog;
      this.mDialog.addComponentListener(new MaximizeToolbar$1(this));
      this.mMaximiseButtonToolbar.setIcon(ImageLoader.getImageIcon("Maximise16.png"));
      this.mMaximiseButtonToolbar.setToolTipText("Maximize");
      this.mMaximiseButtonToolbar.addActionListener(this.maxActionLinstener);
      this.mMaximiseButton.addActionListener(this.maxActionLinstener);
      this.mToolBar.setFloatable(false);
      this.mToolBar.add(Box.createHorizontalGlue());
      this.mToolBar.add(this.mMaximiseButtonToolbar);
   }

   private void fireMaximizeAction(ActionEvent e) {
      if(e.getSource() == this.mMaximiseButtonToolbar) {
         this.mMaximiseButtonToolbar.setIcon(ImageLoader.getImageIcon("restore-16.png"));
      }

      this.mMaximiseButton.setText("Restore Down");
      this.mMaximiseButton.setToolTipText("Restore Down");
      this.mMaximiseButtonToolbar.setToolTipText("Restore Down");
   }

   private void fireRestoreDownAction(ActionEvent e) {
      if(e.getSource() == this.mMaximiseButtonToolbar) {
         this.mMaximiseButtonToolbar.setIcon(ImageLoader.getImageIcon("Maximise16.png"));
      }

      this.mMaximiseButton.setText("Maximize");
      this.mMaximiseButton.setToolTipText("Maximize");
      this.mMaximiseButtonToolbar.setToolTipText("Maximize");
   }

   public JButton getMaximiseButton() {
      return this.mMaximiseButton;
   }

   public JToolBar getToolBar() {
      return this.mToolBar;
   }

   public JButton getMaximiseButtonToolbar() {
      return this.mMaximiseButtonToolbar;
   }

   public static void maximise(JDialog dialog) {
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
   static boolean accessMethod000(MaximizeToolbar x0) {
      return x0.mIsMaximize;
   }

   // $FF: synthetic method
   static Dimension accessMethod100(MaximizeToolbar x0) {
      return x0.mDialogSize;
   }

   // $FF: synthetic method
   static JDialog accessMethod200(MaximizeToolbar x0) {
      return x0.mDialog;
   }

   // $FF: synthetic method
   static Point accessMethod300(MaximizeToolbar x0) {
      return x0.mDialogLocation;
   }

   // $FF: synthetic method
   static boolean accessMethod002(MaximizeToolbar x0, boolean x1) {
      return x0.mIsMaximize = x1;
   }

   // $FF: synthetic method
   static void accessMethod400(MaximizeToolbar x0, ActionEvent x1) {
      x0.fireRestoreDownAction(x1);
   }

   // $FF: synthetic method
   static void accessMethod500(MaximizeToolbar x0, ActionEvent x1) {
      x0.fireMaximizeAction(x1);
   }

   // $FF: synthetic method
   static Point accessMethod302(MaximizeToolbar x0, Point x1) {
      return x0.mDialogLocation = x1;
   }

   // $FF: synthetic method
   static Dimension accessMethod102(MaximizeToolbar x0, Dimension x1) {
      return x0.mDialogSize = x1;
   }
}
