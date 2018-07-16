// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.ImageLoader;
import com.cedar.cp.util.awt.FeedbackReceiver;
import com.cedar.cp.util.awt.ProcessingDialog$ProgressListener;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProcessingDialog extends JDialog implements FeedbackReceiver {

   private ImageIcon mProcessingIcon;
   private static final String TITLE = "Processing, please wait";
   private JProgressBar progressBar;
   private ProcessingDialog$ProgressListener progressListener;


   public ProcessingDialog(Component parent) {
      this(parent, "Processing, please wait");
   }

   public ProcessingDialog(Component parent, String title) {
      super((Frame)SwingUtilities.getWindowAncestor(parent), title, true);
      this.progressBar = new JProgressBar();
      this.build();
   }

   private void build() {
      this.setDefaultCloseOperation(0);
      this.setResizable(false);
      this.progressListener = new ProcessingDialog$ProgressListener(this, this.progressBar);
      this.progressBar.setIndeterminate(true);
      this.mProcessingIcon = ImageLoader.getImageIcon("processing.png");
      JLabel imageLabel = new JLabel(this.mProcessingIcon);
      this.getContentPane().add(imageLabel, "Center");
      this.getContentPane().add(this.progressBar, "South");
      this.pack();
      Dimension size = this.getSize();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      this.setLocation((int)(screenSize.getWidth() - size.getWidth()) / 2, (int)(screenSize.getHeight() - size.getHeight()) / 2);
   }

   public PropertyChangeListener getProgressListener() {
      return this.progressListener;
   }

   public void done() {
      this.dispose();
   }
}
