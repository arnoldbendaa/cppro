// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.io.Serializable;

public class TriptychUpDownLayout implements LayoutManager2, Serializable {

   private static final long serialVersionUID = 1L;
   private int mHGap;
   private int mVGap;
   private Component mWest;
   private Component mCenter;
   private Component mEast;
   private Component mMargin;
   public static final String WEST = "WEST";
   public static final String CENTER = "CENTER";
   public static final String EAST = "EAST";
   public static final String MARGIN = "MARGIN";
   private int mOrientation;


   public int getOrientation() {
      return this.mOrientation;
   }

   public void setOrientation(int orientation) {
      this.mOrientation = orientation;
   }

   public TriptychUpDownLayout() {
      this(0);
   }

   public TriptychUpDownLayout(int hgap) {
      this.mOrientation = 1;
      this.mHGap = hgap;
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

   public void setVGap(int VGap) {
      this.mVGap = VGap;
   }

   public void addLayoutComponent(Component comp, Object constraints) {
      synchronized(comp.getTreeLock()) {
         if(constraints != null && !(constraints instanceof String)) {
            throw new IllegalArgumentException("cannot add to layout: constraint must be a string (or null)");
         } else {
            this.addLayoutComponent((String)constraints, comp);
         }
      }
   }

   public void addLayoutComponent(String name, Component comp) {
      synchronized(comp.getTreeLock()) {
         if(name == null) {
            if(this.mWest == null) {
               this.mWest = comp;
            } else if(this.mCenter == null) {
               this.mCenter = comp;
            } else if(this.mEast == null) {
               this.mEast = comp;
            } else if(this.mMargin == null) {
               this.mMargin = comp;
            } else {
               throw new IllegalArgumentException("cannot add to layout: too many constraints");
            }
         } else {
            if(name.equals("WEST")) {
               this.mWest = comp;
            } else if(name.equals("CENTER")) {
               this.mCenter = comp;
            } else if(name.equals("EAST")) {
               this.mEast = comp;
            } else {
               if(!name.equals("MARGIN")) {
                  throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + name);
               }

               this.mMargin = comp;
            }

         }
      }
   }

   public void removeLayoutComponent(Component comp) {
      synchronized(comp.getTreeLock()) {
         if(comp == this.mWest) {
            this.mWest = null;
         } else if(comp == this.mCenter) {
            this.mCenter = null;
         } else if(comp == this.mEast) {
            this.mEast = null;
         } else if(comp == this.mMargin) {
            this.mMargin = null;
         }

      }
   }

   public Dimension minimumLayoutSize(Container target) {
      synchronized(target.getTreeLock()) {
         Dimension dimWest = this.mWest != null?this.mWest.getMinimumSize():new Dimension(0, 0);
         Dimension dimCenter = this.mCenter != null?this.mCenter.getMinimumSize():new Dimension(0, 0);
         Dimension dimEast = this.mEast != null?this.mEast.getMinimumSize():new Dimension(0, 0);
         Dimension dimMargin = this.mMargin != null?this.mMargin.getMinimumSize():new Dimension(0, 0);
         Dimension dim = new Dimension(0, 0);
         if(this.getOrientation() == 1) {
            dim.width = dimWest.width + this.mHGap + dimCenter.width + this.mHGap + dimEast.width + dimMargin.width;
            dim.height = Math.max(dimWest.height, Math.max(dimCenter.height, dimEast.height));
         } else {
            dim.width = Math.max(dimWest.width, Math.max(dimCenter.width, dimEast.width));
            dim.height = dimWest.height + this.mVGap + dimCenter.height + this.mVGap + dimEast.height + dimMargin.height;
         }

         Insets insets = target.getInsets();
         dim.width += insets.left + insets.right;
         dim.height += insets.top + insets.bottom;
         return dim;
      }
   }

   public Dimension preferredLayoutSize(Container target) {
      synchronized(target.getTreeLock()) {
         Dimension dimWest = this.mWest != null?this.mWest.getPreferredSize():new Dimension(0, 0);
         Dimension dimCenter = this.mCenter != null?this.mCenter.getPreferredSize():new Dimension(0, 0);
         Dimension dimEast = this.mEast != null?this.mEast.getPreferredSize():new Dimension(0, 0);
         Dimension dimMargin = this.mMargin != null?this.mMargin.getPreferredSize():new Dimension(0, 0);
         Dimension dim = new Dimension(0, 0);
         if(this.getOrientation() == 1) {
            dim.width = dimWest.width + this.mHGap + dimCenter.width + this.mHGap + dimEast.width + dimMargin.width;
            dim.height = Math.max(dimWest.height, Math.max(dimCenter.height, dimEast.height));
         } else {
            dim.width = Math.max(dimWest.width, Math.max(dimCenter.width, dimEast.width));
            dim.height = dimWest.height + this.mVGap + dimCenter.height + this.mVGap + dimEast.height + dimMargin.height;
         }

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
         if(this.getOrientation() == 1) {
            int width = right - left;
            int height = bottom - top;
            int middle = left + width / 2;
            int middleWidth = 0;
            int marginWidth = 0;
            int leftTable = middle - this.mHGap;
            int rightTable = middle + this.mHGap;
            if(this.mCenter != null) {
               middleWidth = this.mCenter.getPreferredSize().width;
               leftTable -= middleWidth / 2;
               rightTable -= middleWidth / 2;
            }

            if(this.mMargin != null) {
               marginWidth = this.mMargin.getPreferredSize().width;
               leftTable -= marginWidth / 2;
               rightTable -= marginWidth / 2;
            }

            if(this.mWest != null) {
               this.mWest.setBounds(left, top, leftTable - left, height);
            }

            if(this.mCenter != null) {
               this.mCenter.setBounds(leftTable + this.mHGap, top, middleWidth, height);
            }

            if(this.mEast != null) {
               this.mEast.setBounds(leftTable + this.mHGap + middleWidth + this.mHGap, top, rightTable, height);
            }

            if(this.mMargin != null) {
               this.mMargin.setBounds(leftTable + this.mHGap + middleWidth + this.mHGap + rightTable, top, marginWidth, height);
            }
         }

      }
   }

   public String toString() {
      return this.getClass().getName() + "[hgap=" + this.mHGap + "][vgap=" + this.mVGap + "]";
   }
}
