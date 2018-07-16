// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp;

import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplication$1;
import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpApplicationState;
//import com.cedar.cp.tc.apps.metadataimpexp.MetaDataImpExpDialog;
import com.cedar.cp.tc.apps.metadataimpexp.export.MetaDataExportApplication;
import com.cedar.cp.tc.apps.metadataimpexp.imprt.MetaDataImportApplication;
import com.cedar.cp.tc.apps.metadataimpexp.util.ImpExpActions;
import com.cedar.cp.tc.apps.metadataimpexp.view.ButtonsPanel;
import com.cedar.cp.tc.apps.metadataimpexp.view.CenterPanel;
import com.cedar.cp.tc.apps.metadataimpexp.view.HeaderPanel;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.util.Observable;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MetaDataImpExpApplication extends Observable implements ImpExpActions {

   private JFrame mOwnerMainFrame = null;
   private HeaderPanel mHeaderPanel = null;
   private CenterPanel mCenterPanel = null;
   private ButtonsPanel mButtonsPanel = null;
   private boolean mIsFinish = false;
   private boolean mIsFinishSucces = false;
   private JDialog mImpExptDialog = null;


   public MetaDataImpExpApplication(JFrame mainFrame) {
      this.mOwnerMainFrame = mainFrame;
   }

   public void doBack() {
      this.setChanged();
      this.notifyObservers(Integer.valueOf(1));
      this.clearChanged();
   }

   public void doCancel() {
      if(!this.mIsFinishSucces) {
         int ans = JOptionPane.showConfirmDialog(this.mImpExptDialog, "Do you want to quit?", "Meta Data Import Export", 0, 3);
         if(ans == 0) {
            this.mImpExptDialog.dispose();
            MetaDataImpExpApplicationState.getInstance().clearAllItem();
            MetaDataImpExpApplicationState.getInstance().destroyApplicationState();
         }
      } else {
         this.mImpExptDialog.dispose();
         MetaDataImpExpApplicationState.getInstance().clearAllItem();
         MetaDataImpExpApplicationState.getInstance().destroyApplicationState();
      }

   }

   public void doHelp() {}

   public void doNext() {
      this.setChanged();
      if(this.mIsFinish) {
         this.notifyObservers(Integer.valueOf(2));
      } else {
         this.notifyObservers(Integer.valueOf(0));
      }

      this.clearChanged();
   }

   public ButtonsPanel getButtonsPanel() {
      if(this.mButtonsPanel == null) {
         this.mButtonsPanel = new ButtonsPanel(this);
      }

      return this.mButtonsPanel;
   }

   public CenterPanel getCenterPanel() {
      if(this.mCenterPanel == null) {
         this.mCenterPanel = new CenterPanel();
      }

      return this.mCenterPanel;
   }

   public JFrame getMainFrame() {
      return this.mOwnerMainFrame;
   }

   public HeaderPanel getHeaderPanel() {
      if(this.mHeaderPanel == null) {
         this.mHeaderPanel = new HeaderPanel();
      }

      return this.mHeaderPanel;
   }

   public void setIsFirstStep() {
      this.getButtonsPanel().setIsFirstStep();
      this.mIsFinish = false;
   }

   public void setIsInMiddleStep() {
      this.getButtonsPanel().setIsInMiddleStep();
      this.mIsFinish = false;
   }

   public void setIsLastStep() {
      this.getButtonsPanel().setIsLastStep();
      this.mIsFinish = true;
   }

   public void showExportDialog() {
      MetaDataExportApplication exportApplication = new MetaDataExportApplication(this);
      this.addObserver(exportApplication);
      this.mImpExptDialog = this.getImpExpDialog("Meta Data Export");
      this.mImpExptDialog.setVisible(true);
   }

   public void showImportDialog() {
      MetaDataImportApplication importApplication = new MetaDataImportApplication(this);
      this.addObserver(importApplication);
      this.mImpExptDialog = this.getImpExpDialog("Meta Data Import");
      this.mImpExptDialog.setVisible(true);
   }

   private JDialog getImpExpDialog(String title) {
//      MetaDataImpExpDialog impExptDialog = new MetaDataImpExpDialog(this.mOwnerMainFrame, title);
//      impExptDialog.setDefaultCloseOperation(0);
//      impExptDialog.addWindowListener(new MetaDataImpExpApplication$1(this));
//      JPanel contentPane = (JPanel)impExptDialog.getContentPane();
//      contentPane.setLayout(new BorderLayout());
//      contentPane.add(this.getButtonsPanel(), "South");
//      contentPane.add(this.getCenterPanel(), "Center");
//      impExptDialog.setSize(new Dimension(700, 400));
//      return impExptDialog;//arnold all
      return null;
   }

   public void doingExport(int status) {
      this.mImpExptDialog.getContentPane().setCursor(Cursor.getPredefinedCursor(3));
      switch(status) {
      case 4:
         this.mIsFinishSucces = true;
         MetaDataImpExpApplicationState.getInstance().clearAllItem();
         MetaDataImpExpApplicationState.getInstance().destroyApplicationState();
         this.getButtonsPanel().setCompleteExportOperation(true);
         break;
      case 5:
         this.mIsFinishSucces = false;
         this.getButtonsPanel().setCompleteExportOperation(false);
         break;
      case 6:
         this.getButtonsPanel().setIsInExportOperation();
      }

      this.mImpExptDialog.getContentPane().setCursor(Cursor.getPredefinedCursor(0));
   }

   public void finish() {
      this.getButtonsPanel().setCompleteExportOperation(true);
   }

   public void showMessageDialog(String message, String title) {
      JOptionPane.showMessageDialog(this.mImpExptDialog, message, title, 1);
   }

   public void showErrorMessageDialog(String message, String title) {
      JOptionPane.showMessageDialog(this.mImpExptDialog, message, title, 0);
   }

   public JDialog getShowingDialog() {
      return this.mImpExptDialog;
   }
}
