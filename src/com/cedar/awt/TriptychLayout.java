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

public class TriptychLayout implements LayoutManager2, Serializable {

   private int mHGap;
   private int mVGap;
   private Component mWest;
   private Component mCenter;
   private Component mEast;
   public static final String WEST = "WEST";
   public static final String CENTER = "CENTER";
   public static final String EAST = "EAST";
   private int mOrientation;


   public int getOrientation() {
      return this.mOrientation;
   }

   public void setOrientation(int orientation) {
      this.mOrientation = orientation;
   }

   public TriptychLayout() {
      this(0);
   }

   public TriptychLayout(int hgap) {
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
            } else {
               throw new IllegalArgumentException("cannot add to layout: too many constraints");
            }
         } else {
            if(name.equals("WEST")) {
               this.mWest = comp;
            } else if(name.equals("CENTER")) {
               this.mCenter = comp;
            } else {
               if(!name.equals("EAST")) {
                  throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + name);
               }

               this.mEast = comp;
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
         }

      }
   }

   public Dimension minimumLayoutSize(Container target) {
      synchronized(target.getTreeLock()) {
         Dimension dimWest = this.mWest != null?this.mWest.getMinimumSize():new Dimension(0, 0);
         Dimension dimCenter = this.mCenter != null?this.mCenter.getMinimumSize():new Dimension(0, 0);
         Dimension dimEast = this.mEast != null?this.mEast.getMinimumSize():new Dimension(0, 0);
         Dimension dim = new Dimension(0, 0);
         if(this.getOrientation() == 1) {
            dim.width = dimWest.width + this.mHGap + dimCenter.width + this.mHGap + dimEast.width;
            dim.height = Math.max(dimWest.height, Math.max(dimCenter.height, dimEast.height));
         } else {
            dim.width = Math.max(dimWest.width, Math.max(dimCenter.width, dimEast.width));
            dim.height = dimWest.height + this.mVGap + dimCenter.height + this.mVGap + dimEast.height;
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
         Dimension dim = new Dimension(0, 0);
         if(this.getOrientation() == 1) {
            dim.width = dimWest.width + this.mHGap + dimCenter.width + this.mHGap + dimEast.width;
            dim.height = Math.max(dimWest.height, Math.max(dimCenter.height, dimEast.height));
         } else {
            dim.width = Math.max(dimWest.width, Math.max(dimCenter.width, dimEast.width));
            dim.height = dimWest.height + this.mVGap + dimCenter.height + this.mVGap + dimEast.height;
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
         int width;
         int height;
         int middle;
         int middleHeight;
         int topBottom;
         int bottomTop;
         if(this.getOrientation() == 1) {
            width = right - left;
            height = bottom - top;
            middle = left + width / 2;
            middleHeight = 0;
            topBottom = middle - this.mHGap;
            bottomTop = middle + this.mHGap;
            if(this.mCenter != null) {
               middleHeight = this.mCenter.getPreferredSize().width;
               topBottom -= middleHeight / 2;
               bottomTop += middleHeight / 2;
            }

            if(this.mWest != null) {
               this.mWest.setBounds(left, top, topBottom - left, height);
            }

            if(this.mCenter != null) {
               this.mCenter.setBounds(topBottom + this.mHGap, top, middleHeight, height);
            }

            if(this.mEast != null) {
               this.mEast.setBounds(topBottom + this.mHGap + middleHeight + this.mHGap, top, right - bottomTop, height);
            }
         } else {
            width = right - left;
            height = bottom - top;
            middle = top + height / 2;
            middleHeight = 0;
            topBottom = middle - this.mVGap;
            bottomTop = middle + this.mVGap;
            if(this.mCenter != null) {
               middleHeight = this.mCenter.getPreferredSize().height;
               topBottom -= middleHeight / 2;
               bottomTop += middleHeight / 2;
            }

            if(this.mWest != null) {
               this.mWest.setBounds(left, top, right, height - bottomTop);
            }

            if(this.mCenter != null) {
               this.mCenter.setBounds(left, topBottom + this.mVGap, width, middleHeight);
            }

            if(this.mEast != null) {
               this.mEast.setBounds(left, topBottom + this.mVGap + middleHeight + this.mHGap, width, height - bottomTop);
            }
         }

      }
   }

   public String toString() {
      return this.getClass().getName() + "[hgap=" + this.mHGap + "][vgap=" + this.mVGap + "]";
   }
}
