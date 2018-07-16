// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;

public class TwoColumnLayout implements LayoutManager2 {

   protected boolean mStretch;
   protected boolean mDirty;
   protected int mHGap;
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
   protected int mCol2MinWidth;
   protected int mCol2PreWidth;
   protected int mCol2MaxWidth;
   protected int mMinHeight;
   protected int mPreHeight;
   protected int mMaxHeight;


   public TwoColumnLayout() {
      this(5, 5, true);
   }

   public TwoColumnLayout(int hGap) {
      this(hGap, 5, true);
   }

   public TwoColumnLayout(int hGap, int vGap) {
      this(hGap, vGap, true);
   }

   public TwoColumnLayout(int hGap, int vGap, boolean useInsets, boolean strechLastColumn) {
      this(hGap, vGap, useInsets);
      this.setStretchLastColumn(strechLastColumn);
   }

   public TwoColumnLayout(int hGap, int vGap, boolean useInsets) {
      this.mStretch = true;
      this.mDirty = false;
      this.mHGap = 5;
      this.mVGap = 5;
      this.mComponents = new ArrayList();
      this.mSize = new Dimension(0, 0);
      this.mUseInsets = false;
      this.mHGap = hGap;
      this.mVGap = vGap;
      this.mUseInsets = useInsets;
   }

   public TwoColumnLayout setStretchLastColumn(boolean stretch) {
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
      Dimension size = new Dimension(this.mCol1MinWidth + this.mCol2MinWidth, this.mMinHeight);
      return this.addInsets(target, size);
   }

   public Dimension preferredLayoutSize(Container target) {
      this.calculateSizes();
      Dimension size = new Dimension(this.mCol1PreWidth + this.mCol2PreWidth, this.mPreHeight);
      return this.addInsets(target, size);
   }

   public Dimension maximumLayoutSize(Container target) {
      this.calculateSizes();
      Dimension size = new Dimension(this.mCol1MaxWidth + this.mCol2MaxWidth, this.mMaxHeight);
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
      int col2X = this.mUseInsets?ins.left + this.mCol1PreWidth:this.mCol1PreWidth;
      int rowY = this.mUseInsets?ins.top:0;
      int nRows = (this.mComponents.size() + 1) / 2;

      for(int i = 0; i < nRows; ++i) {
         Component left = (Component)this.mComponents.get(i * 2);
         Component right = i * 2 + 1 >= this.mComponents.size()?null:(Component)this.mComponents.get(i * 2 + 1);
         left.setBounds(col1X, rowY, left.getPreferredSize().width, left.getPreferredSize().height);
         if(right != null) {
            int width = right.getPreferredSize().width;
            if(this.mStretch || col2X + width > maxWidth) {
               width = maxWidth - col2X;
            }

            if(width < 0) {
               width = 0;
            }

            int height = right.getPreferredSize().height;
            if(height > this.mPreRowHeights[i]) {
               height = this.mPreRowHeights[i];
            }

            right.setBounds(col2X, rowY, width, height);
         }

         rowY += this.mPreRowHeights[i] + this.mVGap;
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
         int nRows = (this.mComponents.size() + 1) / 2;
         this.mMinRowHeights = new int[nRows];
         this.mPreRowHeights = new int[nRows];
         this.mMaxRowHeights = new int[nRows];
         this.mCol1MinWidth = this.mHGap;
         this.mCol2MinWidth = this.mVGap;
         this.mCol1PreWidth = this.mHGap;
         this.mCol2PreWidth = this.mVGap;
         this.mCol1MaxWidth = this.mHGap;
         this.mCol2MaxWidth = this.mVGap;

         int j;
         for(j = 0; j < nRows; ++j) {
            Component left = (Component)this.mComponents.get(j * 2);
            Component right = j * 2 + 1 >= this.mComponents.size()?null:(Component)this.mComponents.get(j * 2 + 1);
            Dimension leftSize = left.getMinimumSize();
            Dimension rightSize = right == null?null:right.getMinimumSize();
            int col1Width = leftSize.width + this.mHGap;
            int col2Width = rightSize == null?0:rightSize.width;
            if(col1Width > this.mCol1MinWidth) {
               this.mCol1MinWidth = col1Width;
            }

            if(col2Width > this.mCol2MinWidth) {
               this.mCol2MinWidth = col2Width;
            }

            int col1Height = leftSize.height;
            int col2Height = rightSize == null?0:rightSize.height;
            this.mMinRowHeights[j] = Math.max(col1Height, col2Height);
            leftSize = left.getPreferredSize();
            rightSize = right == null?null:right.getPreferredSize();
            col1Width = leftSize.width + this.mHGap;
            col2Width = rightSize == null?0:rightSize.width;
            if(col1Width > this.mCol1PreWidth) {
               this.mCol1PreWidth = col1Width;
            }

            if(col2Width > this.mCol2PreWidth) {
               this.mCol2PreWidth = col2Width;
            }

            col1Height = leftSize.height;
            col2Height = rightSize == null?0:rightSize.height;
            this.mPreRowHeights[j] = Math.max(col1Height, col2Height);
            if(this.mPreRowHeights[j] > 90) {
               this.mPreRowHeights[j] = 90;
            }

            leftSize = left.getMaximumSize();
            rightSize = right == null?null:right.getMaximumSize();
            col1Width = leftSize.width + this.mHGap;
            col2Width = rightSize == null?0:rightSize.width;
            if(col1Width > this.mCol1MaxWidth) {
               this.mCol1MaxWidth = col1Width;
            }

            if(col2Width > this.mCol2MaxWidth) {
               this.mCol2MaxWidth = col2Width;
            }

            col1Height = leftSize.height;
            col2Height = rightSize == null?0:rightSize.height;
            this.mMaxRowHeights[j] = Math.max(col1Height, col2Height);
         }

         this.mMinHeight = this.mPreHeight = this.mMaxHeight = 0;

         for(j = 0; j < nRows; ++j) {
            this.mMinHeight += this.mMinRowHeights[j];
            if(j != 0) {
               this.mMinHeight += this.mVGap;
            }

            this.mPreHeight += this.mPreRowHeights[j];
            if(j != 0) {
               this.mPreHeight += this.mVGap;
            }

            this.mMaxHeight += this.mMaxRowHeights[j];
            if(j != 0) {
               this.mMaxHeight += this.mVGap;
            }
         }

      }
   }

   public int getHGap() {
      return this.mHGap;
   }

   public void setHGap(int HGap) {
      this.mHGap = HGap;
   }

   public int getVGap() {
      return this.mVGap;
   }

   public void setVGap(int VGap) {
      this.mVGap = VGap;
   }
}
