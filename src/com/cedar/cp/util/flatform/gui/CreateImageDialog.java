// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.awt.OkCancelDialog;
import com.cedar.cp.util.awt.TwoColumnLayout;
import com.cedar.cp.util.flatform.gui.CreateImageDialog$1;
import com.cedar.cp.util.flatform.gui.CreateImageDialog$2;
import com.cedar.cp.util.flatform.model.CPImage;
import com.cedar.cp.util.flatform.model.CPImageFactory;
import com.cedar.cp.util.flatform.model.Workbook;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class CreateImageDialog extends OkCancelDialog {

   private JRadioButton mExisting;
   private JRadioButton mNew;
   private ButtonGroup mButtonGroup = new ButtonGroup();
   private Workbook mWorkbook;
   private JButton mImageBrowse;
   private JButton mFileBrowse;
   private JTextField mImageName;
   private JTextField mFileName;
   private JCheckBox mScale;
   private int mImageId = -1;


   public CreateImageDialog(Frame owner, Workbook workbook) throws HeadlessException {
      super(owner, "Create Image");
      this.mWorkbook = workbook;
      this.init();
   }

   public CreateImageDialog(Dialog owner, Workbook workbook) throws HeadlessException {
      super(owner, "Create Image");
      this.mWorkbook = workbook;
      this.init();
   }

   protected boolean isOkAllowed() {
      return true;
   }

   protected void buildCenterPanel(Container center) {
      JPanel top = new JPanel(new TwoColumnLayout(5, 5));
      this.mExisting = new JRadioButton("Existing Image:");
      top.add(this.mExisting);
      this.mButtonGroup.add(this.mExisting);
      JPanel imageBrowse = new JPanel(new BorderLayout(5, 5));
      this.mImageName = new JTextField(10);
      this.mImageName.setEditable(false);
      imageBrowse.add(this.mImageName, "Center");
      this.mImageBrowse = new JButton("Browse");
      imageBrowse.add(this.mImageBrowse, "East");
      top.add(imageBrowse);
      this.mNew = new JRadioButton("Image File To Load:");
      top.add(this.mNew);
      this.mButtonGroup.add(this.mNew);
      JPanel fileBrowse = new JPanel(new BorderLayout(5, 5));
      this.mFileName = new JTextField(10);
      this.mFileName.setEditable(false);
      fileBrowse.add(this.mFileName, "Center");
      this.mFileBrowse = new JButton("Browse");
      fileBrowse.add(this.mFileBrowse, "East");
      top.add(fileBrowse);
      this.mScale = new JCheckBox("Scale Image", true);
      top.add(this.mScale);
      top.add(new JLabel(""));
      center.add(top, "North");
      this.addListeners();
   }

   private void addListeners() {
      this.mFileBrowse.addActionListener(new CreateImageDialog$1(this));
      this.mImageBrowse.addActionListener(new CreateImageDialog$2(this));
   }

   public static Set<String> unique(String[] strings) {
      HashSet set = new HashSet();
      String[] arr$ = strings;
      int len$ = strings.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String string = arr$[i$];
         String name = string.toLowerCase();
         set.add(name);
      }

      return set;
   }

   public CPImage createImage() {
      CPImageFactory imageFactory = new CPImageFactory();
      imageFactory.setResourceHandler(this.mWorkbook.getResourceHandler());
      imageFactory.setImageId(this.mImageId);
      imageFactory.setScale(this.mScale.isSelected());
      return imageFactory.createImage();
   }

   // $FF: synthetic method
   static int accessMethod002(CreateImageDialog x0, int x1) {
      return x0.mImageId = x1;
   }

   // $FF: synthetic method
   static Workbook accessMethod100(CreateImageDialog x0) {
      return x0.mWorkbook;
   }

   // $FF: synthetic method
   static JTextField accessMethod200(CreateImageDialog x0) {
      return x0.mFileName;
   }

   // $FF: synthetic method
   static JRadioButton accessMethod300(CreateImageDialog x0) {
      return x0.mNew;
   }

   // $FF: synthetic method
   static JTextField accessMethod400(CreateImageDialog x0) {
      return x0.mImageName;
   }

   // $FF: synthetic method
   static JRadioButton accessMethod500(CreateImageDialog x0) {
      return x0.mExisting;
   }
}
