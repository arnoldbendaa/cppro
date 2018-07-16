// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import com.cedar.cp.util.MaximizeToolbar;
import com.cedar.cp.util.awt.OkCancelDialog$1;
import com.cedar.cp.util.awt.OkCancelDialog$2;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class OkCancelDialog extends JDialog {

   protected AbstractAction mOkAction;
   protected AbstractAction mCancelAction;
   protected JPanel mButtonPanel;
   protected boolean mYesNoStyle;
   protected JButton mMaximiseButton;
   private boolean mCancelled = true;
   private MaximizeToolbar mMaximizeToolbar;


   public OkCancelDialog(Frame owner, String title) throws HeadlessException {
      super(owner, title, true);
      this.commonInit();
   }

   public OkCancelDialog(Dialog owner, String title) throws HeadlessException {
      super(owner, title, true);
      this.commonInit();
   }

   private void commonInit() {
      this.setDefaultCloseOperation(2);
      this.mMaximizeToolbar = new MaximizeToolbar(this);
      this.mMaximiseButton = this.mMaximizeToolbar.getMaximiseButton();
      this.mMaximiseButton.setVisible(false);
   }

   public static Window getWindowForComponent(Component parentComponent) throws HeadlessException {
      return (Window)(parentComponent == null?JOptionPane.getRootFrame():(!(parentComponent instanceof Frame) && !(parentComponent instanceof Dialog)?getWindowForComponent(parentComponent.getParent()):(Window)parentComponent));
   }

   protected void init() {
      this.init(false);
   }

   protected void init(boolean yesNoStyle) {
      this.mYesNoStyle = yesNoStyle;
      this.buildControls();
   }

   protected void handleOk() {
      this.mCancelled = false;
      this.dispose();
   }

   protected void handleCancel() {
      this.dispose();
   }

   public void buildToolbar() {
      this.getContentPane().add(this.getMaximizeToolbar().getToolBar(), "North");
   }

   protected void buildControls() {
      Container c = this.getContentPane();
      if(c instanceof JComponent) {
         ((JComponent)c).setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
      }

      this.mButtonPanel = new JPanel(new FlowLayout(1));
      this.mOkAction = new OkCancelDialog$1(this, this.mYesNoStyle?"Yes":"Ok");
      this.mCancelAction = new OkCancelDialog$2(this, this.mYesNoStyle?"No":"Cancel");
      this.mButtonPanel.add(this.mMaximiseButton);
      this.mButtonPanel.add(new JButton(this.mOkAction));
      this.mButtonPanel.add(new JButton(this.mCancelAction));
      this.getContentPane().add(this.mButtonPanel, "South");
      this.buildCenterPanel(this.getCenterContainer());
   }

   protected Container getCenterContainer() {
      return this.getContentPane();
   }

   protected Container getButtonPanel() {
      return this.mButtonPanel;
   }

   public boolean doModal() {
      this.mCancelled = true;
      this.pack();
      this.setLocationRelativeTo(this.getOwner());
      this.setInitialFocusComponent();
      this.setVisible(true);
      return !this.mCancelled;
   }

   public boolean doModal(Dimension size) {
      this.mCancelled = true;
      this.pack();
      this.setSize(size);
      this.setLocationRelativeTo(this.getOwner());
      this.setInitialFocusComponent();
      this.setVisible(true);
      return !this.mCancelled;
   }

   protected void setInitialFocusComponent() {
      this.getContentPane().requestFocusInWindow();
   }

   protected void fireOkAction() {
      int modifiers = 0;
      AWTEvent currentEvent = EventQueue.getCurrentEvent();
      if(currentEvent instanceof InputEvent) {
         modifiers = ((InputEvent)currentEvent).getModifiers();
      } else if(currentEvent instanceof ActionEvent) {
         modifiers = ((ActionEvent)currentEvent).getModifiers();
      }

      this.mOkAction.actionPerformed(new ActionEvent(this, 1001, (String)null, EventQueue.getMostRecentEventTime(), modifiers));
   }

   protected abstract boolean isOkAllowed();

   protected abstract void buildCenterPanel(Container var1);

   public MaximizeToolbar getMaximizeToolbar() {
      if(this.mMaximizeToolbar == null) {
         this.mMaximizeToolbar = new MaximizeToolbar(this);
      }

      return this.mMaximizeToolbar;
   }
}
