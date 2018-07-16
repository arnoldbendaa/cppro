// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.io.Serializable;

public class CrossLayout implements LayoutManager2, Serializable {

   private int mHGap;
   private int mVGap;
   private Component mUL;
   private Component mUR;
   private Component mLL;
   private Component mLR;
   public static final String UPPER_LEFT = "UL";
   public static final String UPPER_RIGHT = "UR";
   public static final String LOWER_LEFT = "LL";
   public static final String LOWER_RIGHT = "LR";


   public CrossLayout() {
      this(0, 0);
   }

   public CrossLayout(int hgap, int vgap) {
      this.mHGap = hgap;
      this.mVGap = vgap;
   }

   public int getHGap() {
      return this.mHGap;
   }

   public void setHgap(int hgap) {
      this.mHGap = hgap;
   }

   public int getVGap() {
      return this.mVGap;
   }

   public void setVGap(int vgap) {
      this.mVGap = vgap;
   }

   public void addLayoutComponent(String constraint, Component comp) {
      this.addLayoutComponent(comp, (Object)constraint);
   }

   public void addLayoutComponent(Component comp, Object constraints) {
      synchronized(comp.getTreeLock()) {
         String name = null;
         if(constraints == null) {
            name = "UL";
         } else {
            name = constraints.toString();
         }

         if(name.equals("UL")) {
            this.mUL = comp;
         } else if(name.equals("UR")) {
            this.mUR = comp;
         } else if(name.equals("LL")) {
            this.mLL = comp;
         } else {
            if(!name.equals("LR")) {
               throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + name);
            }

            this.mLR = comp;
         }

      }
   }

   public void removeLayoutComponent(Component comp) {
      synchronized(comp.getTreeLock()) {
         if(comp == this.mUL) {
            this.mUL = null;
         } else if(comp == this.mUR) {
            this.mUR = null;
         } else if(comp == this.mLL) {
            this.mLL = null;
         } else if(comp == this.mLR) {
            this.mLR = null;
         }

      }
   }

   public Dimension minimumLayoutSize(Container target) {
      synchronized(target.getTreeLock()) {
         Dimension dimUL = this.mUL != null?this.mUL.getMinimumSize():new Dimension(0, 0);
         Dimension dimUR = this.mUR != null?this.mUR.getMinimumSize():new Dimension(0, 0);
         Dimension dimLL = this.mLL != null?this.mLL.getMinimumSize():new Dimension(0, 0);
         Dimension dimLR = this.mLR != null?this.mLR.getMinimumSize():new Dimension(0, 0);
         Dimension dim = new Dimension(0, 0);
         dim.width = this.mHGap + Math.max(dimUL.width, dimLL.width) + Math.max(dimUR.width, dimLR.width);
         dim.height = this.mVGap + Math.max(dimUL.height, dimUR.height) + Math.max(dimLL.height, dimLR.height);
         Insets insets = target.getInsets();
         dim.width += insets.left + insets.right;
         dim.height += insets.top + insets.bottom;
         return dim;
      }
   }

   public Dimension preferredLayoutSize(Container target) {
      synchronized(target.getTreeLock()) {
         Dimension dimUL = this.mUL != null?this.mUL.getPreferredSize():new Dimension(0, 0);
         Dimension dimUR = this.mUR != null?this.mUR.getPreferredSize():new Dimension(0, 0);
         Dimension dimLL = this.mLL != null?this.mLL.getPreferredSize():new Dimension(0, 0);
         Dimension dimLR = this.mLR != null?this.mLR.getPreferredSize():new Dimension(0, 0);
         Dimension dim = new Dimension(0, 0);
         dim.width = this.mHGap + Math.max(dimUL.width, dimLL.width) + Math.max(dimUR.width, dimLR.width);
         dim.height = this.mVGap + Math.max(dimUL.height, dimUR.height) + Math.max(dimLL.height, dimLR.height);
         Insets insets = target.getInsets();
         dim.width += insets.left + insets.right;
         dim.height += insets.top + insets.bottom;
         return dim;
      }
   }

   public Dimension maximumLayoutSize(Container target) {
      return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
   }

   public float getLayoutAlignmentX(Container parent) {
      return 0.5F;
   }

   public float getLayoutAlignmentY(Container parent) {
      return 0.5F;
   }

   public void invalidateLayout(Container target) {}

   public void layoutContainer(Container target) {
      synchronized(target.getTreeLock()) {
         Insets insets = target.getInsets();
         int top = insets.top;
         int bottom = target.getHeight() - insets.bottom;
         int left = insets.left;
         int right = target.getWidth() - insets.right;
         int width = right - left;
         int height = bottom - top;
         int x = 0;
         if(this.mUL != null) {
            x = this.mUL.getPreferredSize().width;
         } else if(this.mLL != null) {
            x = this.mLL.getPreferredSize().width;
         }

         if(x > width) {
            x = width;
         }

         int xRemaining = width - x - this.mHGap;
         if(xRemaining < 0) {
            xRemaining = 0;
         }

         int y = 0;
         if(this.mUL != null) {
            y = this.mUL.getPreferredSize().height;
         } else if(this.mUR != null) {
            y = this.mUR.getPreferredSize().height;
         }

         if(y > height) {
            y = height;
         }

         int yRemaining = height - y - this.mVGap;
         if(yRemaining < 0) {
            yRemaining = 0;
         }

         if(this.mUL != null) {
            this.mUL.setBounds(left, top, x, y);
         }

         if(this.mUR != null) {
            this.mUR.setBounds(left + x + this.mHGap, top, xRemaining, y);
         }

         if(this.mLL != null) {
            this.mLL.setBounds(left, top + y + this.mVGap, x, yRemaining);
         }

         if(this.mLR != null) {
            this.mLR.setBounds(left + x + this.mHGap, top + y + this.mVGap, xRemaining, yRemaining);
         }

      }
   }

   public String toString() {
      return this.getClass().getName() + "[hgap=" + this.mHGap + ",vgap=" + this.mVGap + "]";
   }
}
