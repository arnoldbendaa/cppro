// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.ImageLoader;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.awt.StatusBar$1;
import com.cedar.cp.util.awt.StatusBar$2;
import com.cedar.cp.util.awt.StatusBar$3;
import com.cedar.cp.util.awt.StatusBar$4;
import com.cedar.cp.util.awt.StatusBar$5;
import com.cedar.cp.util.awt.StatusBar$6;
import com.cedar.cp.util.awt.StatusBar$7;
import com.cedar.cp.util.awt.StatusBar$8;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.CompoundBorder;

public class StatusBar extends JPanel {

   private Timer mClearMessageTimer;
   private JLabel mLabel;
   private JLabel mMessage;
   private JLabel mInfo;
   private JLabel mMemoryWatcher;
   private JLabel mSystemName;
   private JLabel mModified;
   private JLabel mNetworkActivity;
   private JLabel mSecure;
   private Icon mNoNetworkIcon;
   private Icon mNetworkIcon;
   private boolean mReadOnly;
   private transient Log mLog = new Log(this.getClass());


   public StatusBar() {
      super(new BorderLayout());
      CompoundBorder sunken = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(1), BorderFactory.createEmptyBorder(2, 2, 2, 2));
      JPanel center = new JPanel(new GridLayout(1, 2, 0, 0));
      JPanel right = new JPanel(new FlowLayout(0, 0, 0));
      this.mLabel = new JLabel();
      this.mLabel.setBorder(sunken);
      center.add(this.mLabel);
      this.mMessage = new JLabel();
      this.mMessage.setBorder(sunken);
      center.add(this.mMessage);
      this.add(center, "Center");
      this.add(right, "East");
      this.mInfo = new JLabel(" ");
      this.mInfo.setBorder(sunken);
      right.add(this.mInfo);
      this.mMemoryWatcher = new JLabel("Memory");
      this.mMemoryWatcher.setBorder(sunken);
      right.add(this.mMemoryWatcher);
      this.mSystemName = new JLabel("System Name");
      this.mSystemName.setBorder(sunken);
      right.add(this.mSystemName);
      this.mModified = new JLabel("Modified");
      this.mModified.setBorder(sunken);
      this.mModified.setVisible(false);
      right.add(this.mModified);
      this.mNetworkIcon = ImageLoader.getImageIcon("network-activity.png");
      this.mNoNetworkIcon = ImageLoader.getImageIcon("network-activity-busy.png");
      this.mNetworkActivity = new JLabel(this.mNoNetworkIcon);
      this.mNetworkActivity.setBorder(sunken);
      right.add(this.mNetworkActivity);
      this.mSecure = new JLabel(ImageLoader.getImageIcon("SecurityUnlock16.png"));
      this.mSecure.setBorder(sunken);
      right.add(this.mSecure);
      Dimension prefSize = this.mModified.getPreferredSize();
      prefSize.width = this.mNetworkActivity.getPreferredSize().width;
      this.mNetworkActivity.setPreferredSize(prefSize);
      prefSize.width = this.mSecure.getPreferredSize().width;
      this.mSecure.setPreferredSize(prefSize);
      this.mMemoryWatcher.setToolTipText("The amount of used memory");
      this.mSystemName.setToolTipText("The name of the connected server system");
      this.mModified.setToolTipText("Indicates if the data has been modified");
      this.mNetworkActivity.setToolTipText("Indicates network activity");
      this.mSecure.setToolTipText("When locked the connections are through SSL");
      this.setModified(false);
   }

   public void addNotify() {
      super.addNotify();
      if(this.isDisplayable()) {
         this.startMemoryWatcher();
         this.mMemoryWatcher.addMouseListener(new StatusBar$1(this));
      }

   }

   private void startMemoryWatcher() {
      StatusBar$2 memoryWatcher = new StatusBar$2(this);
      memoryWatcher.start();
   }

   public void setReadOnly() {
      this.mModified.setText("Read Only");
      this.mModified.setVisible(true);
      this.mReadOnly = true;
   }

   public void setSystemName(String name) {
      this.mSystemName.setText(name);
   }

   public void setModified(boolean state) {
      if(!this.mReadOnly) {
         this.mModified.setText(state?"Modified":"");
         this.mModified.setVisible(state);
      }

   }

   public void setNetworkActivity(boolean state) {
      if(EventQueue.isDispatchThread()) {
         this.mNetworkActivity.setIcon(state?this.mNetworkIcon:this.mNoNetworkIcon);
      } else {
         SwingUtilities.invokeLater(new StatusBar$3(this, state));
      }

   }

   public void setSecureConnection(boolean secure) {
      this.mSecure.setIcon(ImageLoader.getImageIcon(secure?"SecurityLock16.png":"SecurityUnlock16.png"));
   }

   public void showBusy(String msg) {
      if(EventQueue.isDispatchThread()) {
         this.mLabel.setText(msg);
      } else {
         SwingUtilities.invokeLater(new StatusBar$4(this, msg));
      }

   }

   public void setStatusMessage(String msg) {
      this.mLabel.setText(msg);
   }

   public void showNotBusy() {
      if(EventQueue.isDispatchThread()) {
         this.setStatusMessage("Ready");
      } else {
         SwingUtilities.invokeLater(new StatusBar$5(this));
      }

   }

   public void setMessage(String msg) {
      this.setMessage(msg, -1);
   }

   public void setMessage(String msg, int timeout) {
      if(EventQueue.isDispatchThread()) {
         this.stopClearMessageTimer();
         this.mMessage.setText(msg);
         if(timeout > 0) {
            this.startClearMessageTimer(timeout);
         }
      } else {
         SwingUtilities.invokeLater(new StatusBar$6(this, msg, timeout));
      }

   }

   public void setStatusMessage(String msg, int timeout) {}

   private synchronized void startClearMessageTimer(int timeout) {
      Timer t = this.getClearMessageTimer();
      if(t.isRunning()) {
         t.stop();
      }

      t.setDelay(timeout);
      t.setInitialDelay(timeout);
      t.start();
   }

   private synchronized void stopClearMessageTimer() {
      if(this.getClearMessageTimer().isRunning()) {
         this.getClearMessageTimer().stop();
      }

   }

   private Timer getClearMessageTimer() {
      if(this.mClearMessageTimer == null) {
         this.mClearMessageTimer = new Timer(3000, new StatusBar$7(this));
      }

      return this.mClearMessageTimer;
   }

   public void setInfo(String msg) {
      if(EventQueue.isDispatchThread()) {
         this.mInfo.setText(msg);
      } else {
         SwingUtilities.invokeLater(new StatusBar$8(this, msg));
      }

   }

   public void setInfoToolTipText(String text) {
      if(this.mInfo != null) {
         this.mInfo.setToolTipText(text);
      }

   }

   // $FF: synthetic method
   static Log accessMethod000(StatusBar x0) {
      return x0.mLog;
   }

   // $FF: synthetic method
   static JLabel accessMethod100(StatusBar x0) {
      return x0.mMemoryWatcher;
   }

   // $FF: synthetic method
   static Icon accessMethod200(StatusBar x0) {
      return x0.mNetworkIcon;
   }

   // $FF: synthetic method
   static Icon accessMethod300(StatusBar x0) {
      return x0.mNoNetworkIcon;
   }

   // $FF: synthetic method
   static JLabel accessMethod400(StatusBar x0) {
      return x0.mNetworkActivity;
   }

   // $FF: synthetic method
   static JLabel accessMethod500(StatusBar x0) {
      return x0.mLabel;
   }

   // $FF: synthetic method
   static void accessMethod600(StatusBar x0) {
      x0.stopClearMessageTimer();
   }

   // $FF: synthetic method
   static JLabel accessMethod700(StatusBar x0) {
      return x0.mMessage;
   }

   // $FF: synthetic method
   static void accessMethod800(StatusBar x0, int x1) {
      x0.startClearMessageTimer(x1);
   }

   // $FF: synthetic method
   static JLabel accessMethod900(StatusBar x0) {
      return x0.mInfo;
   }
}
