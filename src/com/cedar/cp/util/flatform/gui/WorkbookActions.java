// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.ImageLoader;
import com.cedar.cp.util.awt.ColoredIcon;
import com.cedar.cp.util.flatform.gui.WorkbookActionHandler;
import com.cedar.cp.util.flatform.gui.WorkbookActions$1;
import com.cedar.cp.util.flatform.gui.WorkbookActions$10;
import com.cedar.cp.util.flatform.gui.WorkbookActions$11;
import com.cedar.cp.util.flatform.gui.WorkbookActions$12;
import com.cedar.cp.util.flatform.gui.WorkbookActions$13;
import com.cedar.cp.util.flatform.gui.WorkbookActions$14;
import com.cedar.cp.util.flatform.gui.WorkbookActions$15;
import com.cedar.cp.util.flatform.gui.WorkbookActions$16;
import com.cedar.cp.util.flatform.gui.WorkbookActions$17;
import com.cedar.cp.util.flatform.gui.WorkbookActions$18;
import com.cedar.cp.util.flatform.gui.WorkbookActions$19;
import com.cedar.cp.util.flatform.gui.WorkbookActions$2;
import com.cedar.cp.util.flatform.gui.WorkbookActions$20;
import com.cedar.cp.util.flatform.gui.WorkbookActions$21;
import com.cedar.cp.util.flatform.gui.WorkbookActions$22;
import com.cedar.cp.util.flatform.gui.WorkbookActions$23;
import com.cedar.cp.util.flatform.gui.WorkbookActions$24;
import com.cedar.cp.util.flatform.gui.WorkbookActions$25;
import com.cedar.cp.util.flatform.gui.WorkbookActions$3;
import com.cedar.cp.util.flatform.gui.WorkbookActions$4;
import com.cedar.cp.util.flatform.gui.WorkbookActions$5;
import com.cedar.cp.util.flatform.gui.WorkbookActions$6;
import com.cedar.cp.util.flatform.gui.WorkbookActions$7;
import com.cedar.cp.util.flatform.gui.WorkbookActions$8;
import com.cedar.cp.util.flatform.gui.WorkbookActions$9;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class WorkbookActions {

   private boolean mProvidingFeedback = false;
   private WorkbookActionHandler mActionHandler;
   private AbstractAction mNewAction;
   private AbstractAction mOpenAction;
   private AbstractAction mSaveAction;
   private AbstractAction mExitAction;
   private JComboBox mFontFamilies;
   private JComboBox mFontSizes;
   private JComboBox mViewLayers;
   private AbstractAction mBoldAction;
   private AbstractAction mItalicAction;
   private AbstractAction mUnderlineAction;
   private AbstractAction mBackgroundColorAction;
   private ColoredIcon mBackgroundIcon = new ColoredIcon();
   private AbstractAction mBackgroundAction;
   private AbstractAction mTextColorAction;
   private ColoredIcon mTextIcon = new ColoredIcon();
   private AbstractAction mTextAction;
   private AbstractAction mGroupAction;
   private AbstractAction mSplitAction;
   private AbstractAction mBorderAction;
   private AbstractAction mToggleGridAction;
   private AbstractAction mAlignLeftAction;
   private AbstractAction mAlignCentreAction;
   private AbstractAction mAlignRightAction;
   private AbstractAction mTestAction;
   private AbstractAction mShowTestParamsAction;
   private AbstractAction mWorksheetPropertiesAction;
   private AbstractAction mChartAction;
   private AbstractAction mImageAction;
   private JButton mBoldButton;
   private JButton mItalicButton;
   private JButton mUnderlineButton;
   private JButton mGridButton;
   private JButton mTestButton;
   private JButton mTestParamsButton;
   private JButton mWorksheetPropertiesButton;


   public WorkbookActions(WorkbookActionHandler handler) {
      this.mActionHandler = handler;
      this.mNewAction = new WorkbookActions$1(this, "New");
      this.mOpenAction = new WorkbookActions$2(this, "Open");
      this.mSaveAction = new WorkbookActions$3(this, "Save");
      this.mExitAction = new WorkbookActions$4(this, "Exit");
      this.mBoldAction = new WorkbookActions$5(this, "", ImageLoader.getImageIcon("bold-off.png"));
      this.mBoldAction.putValue("ShortDescription", "Bold");
      this.mBoldAction.putValue("AcceleratorKey", KeyStroke.getKeyStroke(66, 2));
      this.mItalicAction = new WorkbookActions$6(this, "", ImageLoader.getImageIcon("italic-off.png"));
      this.mItalicAction.putValue("ShortDescription", "Italic");
      this.mItalicAction.putValue("AcceleratorKey", KeyStroke.getKeyStroke(73, 2));
      this.mUnderlineAction = new WorkbookActions$7(this, "", ImageLoader.getImageIcon("underline-off.png"));
      this.mUnderlineAction.putValue("ShortDescription", "Underline");
      this.mUnderlineAction.putValue("AcceleratorKey", KeyStroke.getKeyStroke(85, 2));
      this.mBackgroundIcon.setColor(Color.white);
      this.mBackgroundColorAction = new WorkbookActions$8(this, "", this.mBackgroundIcon);
      this.mBackgroundColorAction.putValue("ShortDescription", "Background Color");
      this.mBackgroundAction = new WorkbookActions$9(this, "", ImageLoader.getImageIcon("background-16.png"));
      this.mBackgroundAction.putValue("ShortDescription", "Background Color");
      this.mTextIcon.setColor(Color.black);
      this.mTextColorAction = new WorkbookActions$10(this, "", this.mTextIcon);
      this.mTextColorAction.putValue("ShortDescription", "Text Color");
      this.mTextAction = new WorkbookActions$11(this, "", ImageLoader.getImageIcon("label-foreground.png"));
      this.mTextAction.putValue("ShortDescription", "Font Color");
      this.mGroupAction = new WorkbookActions$12(this, "", ImageLoader.getImageIcon("cell-merge.png"));
      this.mGroupAction.putValue("ShortDescription", "Merge cells");
      this.mSplitAction = new WorkbookActions$13(this, "", ImageLoader.getImageIcon("cell-split.png"));
      this.mSplitAction.putValue("ShortDescription", "Split cells");
      this.mBorderAction = new WorkbookActions$14(this, "", ImageLoader.getImageIcon("border.png"));
      this.mBorderAction.putValue("ShortDescription", "Border");
      this.mToggleGridAction = new WorkbookActions$15(this, "", ImageLoader.getImageIcon("grid-on.png"));
      this.mToggleGridAction.putValue("ShortDescription", "Toggle Grid");
      this.mAlignLeftAction = new WorkbookActions$16(this, "", ImageLoader.getImageIcon("FormatAlignLeft16.png"));
      this.mAlignLeftAction.putValue("ShortDescription", "Align Left");
      this.mAlignCentreAction = new WorkbookActions$17(this, "", ImageLoader.getImageIcon("FormatAlignCenter16.png"));
      this.mAlignCentreAction.putValue("ShortDescription", "Centre");
      this.mAlignRightAction = new WorkbookActions$18(this, "", ImageLoader.getImageIcon("FormatAlignRight16.png"));
      this.mAlignRightAction.putValue("ShortDescription", "Align Right");
      this.mChartAction = new WorkbookActions$19(this, "", ImageLoader.getImageIcon("Chart16.png"));
      this.mChartAction.putValue("ShortDescription", "Chart");
      this.mImageAction = new WorkbookActions$20(this, "", ImageLoader.getImageIcon("image-16.png"));
      this.mImageAction.putValue("ShortDescription", "Image");
      this.mTestAction = new WorkbookActions$21(this, "", ImageLoader.getImageIcon("test.png"));
      this.mTestAction.putValue("ShortDescription", "Test");
      this.mShowTestParamsAction = new WorkbookActions$22(this, "", ImageLoader.getImageIcon("element-properties-16.png"));
      this.mShowTestParamsAction.putValue("ShortDescription", "Test Parameters");
      this.mWorksheetPropertiesAction = new WorkbookActions$23(this, "", ImageLoader.getImageIcon("properties.png"));
      this.mWorksheetPropertiesAction.putValue("ShortDescription", "Worksheet Properties");
   }

   public JMenuBar getMenuBar() {
      JMenuBar actions = new JMenuBar();
      JMenu fileMenu = new JMenu("File");
      fileMenu.add(this.mNewAction);
      fileMenu.add(this.mOpenAction);
      fileMenu.add(this.mSaveAction);
      fileMenu.addSeparator();
      fileMenu.add(this.mExitAction);
      actions.add(fileMenu);
      return actions;
   }

   public JToolBar getToolbar() {
      JToolBar tools = new JToolBar("Formatting", 0);
      tools.setRollover(true);
      tools.setFloatable(false);
      this.mFontFamilies = new JComboBox(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
      this.mFontFamilies.setPreferredSize(new Dimension(160, 20));
      this.mFontFamilies.setMaximumSize(new Dimension(160, 100));
      tools.add(this.mFontFamilies);
      tools.addSeparator();
      this.mFontSizes = new JComboBox(new Integer[]{Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(14), Integer.valueOf(16), Integer.valueOf(18), Integer.valueOf(20), Integer.valueOf(22), Integer.valueOf(24), Integer.valueOf(26), Integer.valueOf(28), Integer.valueOf(36), Integer.valueOf(48), Integer.valueOf(72)});
      this.mFontSizes.setPreferredSize(new Dimension(50, 20));
      this.mFontSizes.setMaximumSize(new Dimension(50, 100));
      tools.add(this.mFontSizes);
      WorkbookActions$24 fontListener = new WorkbookActions$24(this);
      this.mFontFamilies.addActionListener(fontListener);
      this.mFontSizes.addActionListener(fontListener);
      tools.addSeparator();
      this.mBoldButton = new JButton(this.mBoldAction);
      this.mBoldButton.setSelectedIcon(ImageLoader.getImageIcon("bold-on.png"));
      tools.add(this.mBoldButton);
      this.mItalicButton = new JButton(this.mItalicAction);
      this.mItalicButton.setSelectedIcon(ImageLoader.getImageIcon("italic-on.png"));
      tools.add(this.mItalicButton);
      this.mUnderlineButton = new JButton(this.mUnderlineAction);
      this.mUnderlineButton.setSelectedIcon(ImageLoader.getImageIcon("underline-off.png"));
      tools.add(this.mUnderlineButton);
      tools.addSeparator();
      tools.add(this.mBackgroundColorAction);
      tools.add(this.mBackgroundAction);
      tools.add(this.mTextColorAction);
      tools.add(this.mTextAction);
      tools.addSeparator();
      tools.add(this.mGroupAction);
      tools.add(this.mSplitAction);
      tools.addSeparator();
      tools.add(this.mBorderAction);
      tools.addSeparator();
      tools.add(this.mAlignLeftAction);
      tools.add(this.mAlignCentreAction);
      tools.add(this.mAlignRightAction);
      tools.addSeparator();
      this.mGridButton = new JButton(this.mToggleGridAction);
      this.mGridButton.setSelectedIcon(ImageLoader.getImageIcon("grid-off.png"));
      tools.add(this.mGridButton);
      tools.add(this.mChartAction);
      tools.add(this.mImageAction);
      tools.addSeparator();
      this.mTestButton = new JButton(this.mTestAction);
      tools.add(this.mTestButton);
      this.mTestParamsButton = new JButton(this.mShowTestParamsAction);
      tools.add(this.mTestParamsButton);
      this.mWorksheetPropertiesButton = new JButton(this.mWorksheetPropertiesAction);
      tools.add(this.mWorksheetPropertiesButton);
      this.mViewLayers = new JComboBox(new String[]{"Values", "Formulae", "Input Mappings", "Output Mappings"});
      this.mViewLayers.setPreferredSize(new Dimension(120, 20));
      this.mViewLayers.setMaximumSize(new Dimension(120, 100));
      WorkbookActions$25 layerListener = new WorkbookActions$25(this);
      this.mViewLayers.addActionListener(layerListener);
      tools.add(Box.createHorizontalGlue());
      tools.add(this.mViewLayers);
      return tools;
   }

   public void setViewLayer(int layer) {
      this.mProvidingFeedback = true;
      this.mViewLayers.setSelectedIndex(layer);
      this.mProvidingFeedback = false;
   }

   public void setCurrentFont(Font f) {
      this.mProvidingFeedback = true;
      if(f != null) {
         this.mBoldButton.setSelected(f.isBold());
         this.mItalicButton.setSelected(f.isItalic());
         this.mFontFamilies.setSelectedItem(f.getName());
         this.mFontSizes.setSelectedItem(Integer.valueOf(f.getSize()));
      } else {
         this.mBoldButton.setSelected(false);
         this.mItalicButton.setSelected(false);
         this.mFontFamilies.setSelectedItem("Arial");
         this.mFontSizes.setSelectedItem(Integer.valueOf(10));
      }

      this.mProvidingFeedback = false;
   }

   public void setCurrentBackgroundColor(Color color) {
      this.mBackgroundIcon.setColor(color);
   }

   public void setCurrentForegroundColor(Color color) {
      this.mTextIcon.setColor(color);
   }

   public void setGridOn(boolean gridOn) {
      this.mGridButton.setSelected(gridOn);
   }

   // $FF: synthetic method
   static boolean accessMethod000(WorkbookActions x0) {
      return x0.mProvidingFeedback;
   }

   // $FF: synthetic method
   static WorkbookActionHandler accessMethod100(WorkbookActions x0) {
      return x0.mActionHandler;
   }

   // $FF: synthetic method
   static ColoredIcon accessMethod200(WorkbookActions x0) {
      return x0.mBackgroundIcon;
   }

   // $FF: synthetic method
   static ColoredIcon accessMethod300(WorkbookActions x0) {
      return x0.mTextIcon;
   }

   // $FF: synthetic method
   static JComboBox accessMethod400(WorkbookActions x0) {
      return x0.mFontSizes;
   }

   // $FF: synthetic method
   static JComboBox accessMethod500(WorkbookActions x0) {
      return x0.mFontFamilies;
   }

   // $FF: synthetic method
   static JComboBox accessMethod600(WorkbookActions x0) {
      return x0.mViewLayers;
   }
   
   /**
    * Get action handler
    * 
    * @return
    */
	public WorkbookActionHandler getActionHandler() {
		return mActionHandler;
	}

}
