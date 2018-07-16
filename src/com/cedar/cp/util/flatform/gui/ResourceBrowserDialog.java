// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.awt.OkCancelDialog;
import com.cedar.cp.util.flatform.gui.ResourceBrowserDialog$1;
import com.cedar.cp.util.flatform.gui.ResourceBrowserDialog$2;
import com.cedar.cp.util.flatform.gui.ResourceBrowserDialog$ResourceListModel;
import com.cedar.cp.util.flatform.gui.WrappedImage;
import com.cedar.cp.util.flatform.model.CPImage;
import com.cedar.cp.util.flatform.model.ResourceStorage;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;

public class ResourceBrowserDialog extends OkCancelDialog {

   private ResourceStorage mResourceHandler;
   private JLabel mPreview;
   private TableModel mResourceTableModel;
   private ResourceBrowserDialog$ResourceListModel mResources;
   private JList mImageList;
   private JCheckBox mScale;
   private Map<Integer, CPImage> mImageCache = new HashMap();


   public ResourceBrowserDialog(Frame owner, ResourceStorage handler) throws HeadlessException {
      super(owner, "Image Browser");
      this.mResourceHandler = handler;
      this.init();
   }

   public ResourceBrowserDialog(Dialog owner, ResourceStorage handler) throws HeadlessException {
      super(owner, "Image Browser");
      this.mResourceHandler = handler;
      this.init();
   }

   public int getSelectedImageId() {
      int index = this.mImageList.getSelectedIndex();
      return index < 0?-1:this.mResources.getId(index);
   }

   public String getSelectedImageName() {
      int index = this.mImageList.getSelectedIndex();
      return index < 0?"":this.mResources.getElementAt(index).toString();
   }

   protected boolean isOkAllowed() {
      return this.mImageList.getSelectedIndex() >= 0;
   }

   protected void buildCenterPanel(Container center) {
      JPanel grid = new JPanel(new BorderLayout(5, 5));
      JPanel left = new JPanel(new BorderLayout(5, 5));
      left.add(new JLabel("Choose an image:"), "North");
      this.mResourceTableModel = this.mResourceHandler.getResources();
      this.mResources = new ResourceBrowserDialog$ResourceListModel(this, this.mResourceTableModel);
      this.mImageList = new JList(this.mResources);
      this.mImageList.setSelectionMode(0);
      left.add(new JScrollPane(this.mImageList), "Center");
      this.mScale = new JCheckBox("Scale:", true);
      left.add(this.mScale, "South");
      grid.add(left, "West");
      JPanel right = new JPanel(new BorderLayout(5, 5));
      right.add(new JLabel("Preview:"), "North");
      this.mPreview = new WrappedImage((CPImage)null);
      this.mPreview.setPreferredSize(new Dimension(300, 300));
      right.add(this.mPreview, "Center");
      grid.add(right, "Center");
      center.add(grid, "Center");
      this.addListeners();
   }

   private void addListeners() {
      this.mImageList.addListSelectionListener(new ResourceBrowserDialog$1(this));
      this.mScale.addItemListener(new ResourceBrowserDialog$2(this));
   }

   private ImageIcon getImageIcon(int index) {
      CPImage icon = (CPImage)this.mImageCache.get(Integer.valueOf(index));
      if(icon == null) {
         if(index < 0) {
            return null;
         }

         URL url = this.mResourceHandler.getResourceURL(this.mResources.getId(index));
         if(url == null) {
            return null;
         }

         icon = new CPImage(url);
         icon.setScale(this.mScale.isSelected());
         this.mImageCache.put(Integer.valueOf(index), icon);
      }

      return icon;
   }

   // $FF: synthetic method
   static JList accessMethod000(ResourceBrowserDialog x0) {
      return x0.mImageList;
   }

   // $FF: synthetic method
   static ImageIcon accessMethod100(ResourceBrowserDialog x0, int x1) {
      return x0.getImageIcon(x1);
   }

   // $FF: synthetic method
   static JLabel accessMethod200(ResourceBrowserDialog x0) {
      return x0.mPreview;
   }

   // $FF: synthetic method
   static JCheckBox accessMethod300(ResourceBrowserDialog x0) {
      return x0.mScale;
   }
}
