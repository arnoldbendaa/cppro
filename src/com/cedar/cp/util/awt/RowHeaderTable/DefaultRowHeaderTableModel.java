// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import com.cedar.cp.util.awt.RowHeaderTable.InternalTableModel;
import com.cedar.cp.util.awt.RowHeaderTable.RowHeaderTableModel;
import com.cedar.cp.util.awt.TreeTable.TreeTableModel;
import java.awt.Color;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class DefaultRowHeaderTableModel implements RowHeaderTableModel, Serializable {

   protected String mCornerName;
   protected TreeTableModel mRowModel;
   protected Set mTreeModelsChangedListeners = new HashSet();
   protected InternalTableModel mInternalTableModel;
   protected int mGradientDepth;
   protected Color mGradientColor;
   protected Color mEditBackground;
   protected Color mEditForeground;
   protected Color mModifiedBackground;
   protected Color mModifiedForeground;


   public DefaultRowHeaderTableModel(TreeTableModel rowModel, String cornerName, int gradientDepth, Color gradientColor, Color editBackground, Color modifiedBackground, Color editForeground, Color modifiedForeground) {
      this.mRowModel = rowModel;
      this.mCornerName = cornerName;
      this.mGradientDepth = gradientDepth;
      this.mGradientColor = gradientColor;
      this.mEditBackground = editBackground;
      this.mEditForeground = editForeground;
      this.mModifiedBackground = modifiedBackground;
      this.mModifiedForeground = modifiedForeground;
   }

   public TreeTableModel getRowTreeTableModel() {
      return this.mRowModel;
   }

   public String getCornerName() {
      return this.mCornerName;
   }

   public boolean isCellEditable(Object row, int column) {
      return false;
   }

   public abstract Object getValueAt(Object var1, int var2);

   public abstract void setValueAt(Object var1, Object var2, int var3);

   public void setInternalTableModel(InternalTableModel model) {
      this.mInternalTableModel = model;
   }

   public InternalTableModel getInternalTableModel() {
      return this.mInternalTableModel;
   }

   public int getGradientDepth() {
      return this.mGradientDepth;
   }

   public void setGradientDepth(int gradientDepth) {
      this.mGradientDepth = gradientDepth;
   }

   public Color getGradientColor() {
      return this.mGradientColor;
   }

   public void setGradientColor(Color gradientColor) {
      this.mGradientColor = gradientColor;
   }

   public Color getEditBackground() {
      return this.mEditBackground;
   }

   public void setEditBackground(Color editBackground) {
      this.mEditBackground = editBackground;
   }

   public Color getEditForeground() {
      return this.mEditForeground;
   }

   public void setEditForeground(Color editForeground) {
      this.mEditForeground = editForeground;
   }

   public Color getModifiedBackground() {
      return this.mModifiedBackground;
   }

   public void setModifiedBackground(Color modifiedBackground) {
      this.mModifiedBackground = modifiedBackground;
   }

   public Color getModifiedForeground() {
      return this.mModifiedForeground;
   }

   public void setModifiedForeground(Color modifiedForeground) {
      this.mModifiedForeground = modifiedForeground;
   }
}
