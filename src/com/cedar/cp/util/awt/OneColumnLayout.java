// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;

public class OneColumnLayout implements LayoutManager2 {

   protected boolean mStretch;
   protected boolean mDirty;
   protected int mVGap;
   protected ArrayList mComponents;
   protected Dimension mSize;
   protected boolean mUseInsets;
   protected int[] mMinRowHeights;
   protected int[] mPreRowHeights;
   protected int[] mMaxRowHeights;
   protected int mCol1MinWidth;
   protected int mCol1PreWidth;
   protected int mCol1MaxWidth;
   protected int mMinHeight;
   protected int mPreHeight;
   protected int mMaxHeight;


   public OneColumnLayout() {
      this(0, true);
   }

   public OneColumnLayout(int vGap) {
      this(vGap, true);
   }

   public OneColumnLayout(int vGap, boolean useInsets) {
      this.mStretch = true;
      this.mDirty = false;
      this.mVGap = 5;
      this.mComponents = new ArrayList();
      this.mSize = new Dimension(0, 0);
      this.mUseInsets = false;
      this.mVGap = vGap;
      this.mUseInsets = useInsets;
   }

   public OneColumnLayout setStretchLastColumn(boolean stretch) {
      this.mStretch = stretch;
      return this;
   }

   public void addLayoutComponent(Component comp, Object position) {
      this.mComponents.add(comp);
      this.mDirty = true;
   }

   public void addLayoutComponent(String name, Component comp) {
      this.addLayoutComponent(comp, (Object)name);
   }

   public void removeLayoutComponent(Component comp) {
      this.mComponents.remove(comp);
      this.mDirty = true;
   }

   public Dimension minimumLayoutSize(Container target) {
      this.calculateSizes();
      Dimension size = new Dimension(this.mCol1MinWidth, this.mMinHeight);
      return this.addInsets(target, size);
   }

   public Dimension preferredLayoutSize(Container target) {
      this.calculateSizes();
      Dimension size = new Dimension(this.mCol1PreWidth, this.mPreHeight);
      return this.addInsets(target, size);
   }

   public Dimension maximumLayoutSize(Container target) {
      this.calculateSizes();
      Dimension size = new Dimension(this.mCol1MaxWidth, this.mMaxHeight);
      return this.addInsets(target, size);
   }

   public void invalidateLayout(Container container) {
      this.mDirty = true;
   }

   public float getLayoutAlignmentX(Container parent) {
      return 0.5F;
   }

   public float getLayoutAlignmentY(Container parent) {
      return 0.5F;
   }

   public void layoutContainer(Container container) {
      if(this.mDirty) {
         this.calculateSizes();
      }

      Insets ins = container.getInsets();
      int maxWidth = container.getSize().width;
      if(this.mUseInsets) {
         maxWidth -= ins.right;
      }

      int col1X = this.mUseInsets?ins.left:0;
      int rowY = this.mUseInsets?ins.top:0;
      int nRows = this.mComponents.size();

      for(int i = 0; i < nRows; ++i) {
         Component left = (Component)this.mComponents.get(i);
         int width = left.getPreferredSize().width;
         if(this.mStretch || col1X + width > maxWidth) {
            width = maxWidth - col1X;
         }

         if(width < 0) {
            width = 0;
         }

         int height = left.getPreferredSize().height;
         if(height > this.mPreRowHeights[i]) {
            height = this.mPreRowHeights[i];
         }

         left.setBounds(col1X, rowY, width, height);
         rowY += this.mPreRowHeights[i];
      }

   }

   private Dimension addInsets(Container container, Dimension size) {
      Insets i = container.getInsets();
      Dimension newSize = new Dimension(size.width + i.right, size.height + i.bottom);
      if(this.mUseInsets) {
         newSize.width += i.left;
         newSize.height += i.top;
      }

      return newSize;
   }

   private void calculateSizes() {
      if(this.mDirty) {
         this.mDirty = false;
         this.mSize.width = 0;
         this.mSize.height = 0;
         int nRows = this.mComponents.size();
         this.mMinRowHeights = new int[nRows];
         this.mPreRowHeights = new int[nRows];
         this.mMaxRowHeights = new int[nRows];
         this.mCol1MinWidth = 0;
         this.mCol1PreWidth = 0;
         this.mCol1MaxWidth = 0;

         int j;
         for(j = 0; j < nRows; ++j) {
            Component left = (Component)this.mComponents.get(j);
            Dimension leftSize = left.getMinimumSize();
            int col1Width = leftSize.width;
            if(col1Width > this.mCol1MinWidth) {
               this.mCol1MinWidth = col1Width;
            }

            int col1Height = leftSize.height + this.mVGap;
            this.mMinRowHeights[j] = col1Height;
            leftSize = left.getPreferredSize();
            col1Width = leftSize.width;
            if(col1Width > this.mCol1PreWidth) {
               this.mCol1PreWidth = col1Width;
            }

            col1Height = leftSize.height + this.mVGap;
            this.mPreRowHeights[j] = col1Height;
            leftSize = left.getMaximumSize();
            col1Width = leftSize.width;
            if(col1Width > this.mCol1MaxWidth) {
               this.mCol1MaxWidth = col1Width;
            }

            col1Height = leftSize.height + this.mVGap;
            this.mMaxRowHeights[j] = col1Height;
         }

         this.mMinHeight = this.mPreHeight = this.mMaxHeight = 0;

         for(j = 0; j < nRows; ++j) {
            this.mMinHeight += this.mMinRowHeights[j];
            this.mPreHeight += this.mPreRowHeights[j];
            this.mMaxHeight += this.mMaxRowHeights[j];
         }

      }
   }
}
