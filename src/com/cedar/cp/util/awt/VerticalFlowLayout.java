// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class VerticalFlowLayout implements LayoutManager {

   public static final int TOP = 0;
   public static final int MIDDLE = 1;
   public static final int BOTTOM = 2;
   int newAlign;
   int vgap;
   int hgap;


   public VerticalFlowLayout() {
      this(1, 5, 5);
   }

   public VerticalFlowLayout(int align) {
      this(align, 5, 5);
   }

   public VerticalFlowLayout(int align, int vgap, int hgap) {
      this.hgap = hgap;
      this.vgap = vgap;
      this.setAlignment(align);
   }

   public int getAlignment() {
      return this.newAlign;
   }

   public void setAlignment(int align) {
      this.newAlign = align;
   }

   public int getHgap() {
      return this.hgap;
   }

   public void setHgap(int hgap) {
      this.hgap = hgap;
   }

   public int getVgap() {
      return this.vgap;
   }

   public void setVgap(int vgap) {
      this.vgap = vgap;
   }

   public void addLayoutComponent(String name, Component comp) {}

   public void removeLayoutComponent(Component comp) {}

   public Dimension preferredLayoutSize(Container target) {
      synchronized(target.getTreeLock()) {
         Dimension dim = new Dimension(0, 0);
         int nmembers = target.getComponentCount();
         boolean firstVisibleComponent = true;

         for(int insets = 0; insets < nmembers; ++insets) {
            Component m = target.getComponent(insets);
            if(m.isVisible()) {
               Dimension d = m.getPreferredSize();
               dim.width = Math.max(dim.width, d.width);
               if(firstVisibleComponent) {
                  firstVisibleComponent = false;
               } else {
                  dim.height += this.vgap;
               }

               dim.height += d.height;
            }
         }

         Insets var11 = target.getInsets();
         dim.width += var11.left + var11.right + this.hgap * 2;
         dim.height += var11.top + var11.bottom + this.vgap * 2;
         return dim;
      }
   }

   public Dimension minimumLayoutSize(Container target) {
      synchronized(target.getTreeLock()) {
         Dimension dim = new Dimension(0, 0);
         int nmembers = target.getComponentCount();

         for(int insets = 0; insets < nmembers; ++insets) {
            Component m = target.getComponent(insets);
            if(m.isVisible()) {
               Dimension d = m.getMinimumSize();
               dim.width = Math.max(dim.width, d.width);
               if(insets > 0) {
                  dim.height += this.vgap;
               }

               dim.height += d.height;
            }
         }

         Insets var10 = target.getInsets();
         dim.width += var10.left + var10.right + this.hgap * 2;
         dim.height += var10.top + var10.bottom + this.vgap * 2;
         return dim;
      }
   }

   private void moveComponents(Container target, int x, int y, int width, int height, int colStart, int colEnd) {
      synchronized(target.getTreeLock()) {
         switch(this.newAlign) {
         case 0:
            y = 0;
            break;
         case 1:
            y = height / 2;
            break;
         case 2:
            y = height;
         }

         for(int i = colStart; i < colEnd; ++i) {
            Component m = target.getComponent(i);
            if(m.isVisible()) {
               m.setLocation(x, y);
               y += m.getHeight() + this.vgap;
            }
         }

      }
   }

   public void layoutContainer(Container target) {
      synchronized(target.getTreeLock()) {
         Insets insets = target.getInsets();
         int maxheight = target.getHeight() - (insets.top + insets.bottom + this.vgap * 2);
         int nmembers = target.getComponentCount();
         int x = insets.left + this.hgap;
         int y = 0;
         int colw = 0;
         int start = 0;

         for(int i = 0; i < nmembers; ++i) {
            Component m = target.getComponent(i);
            if(m.isVisible()) {
               Dimension d = m.getPreferredSize();
               m.setSize(d.width, d.height);
               if(y != 0 && y + d.height > maxheight) {
                  this.moveComponents(target, x, this.vgap, colw, maxheight - y, start, i);
                  y = d.height;
                  x += this.hgap + colw;
                  colw = d.width;
                  start = i;
               } else {
                  if(y > 0) {
                     y += this.vgap;
                  }

                  y += d.height;
                  if(d.width > colw) {
                     colw = d.width;

                     for(int j = i - 1; j >= start; --j) {
                        Component temp = target.getComponent(j);
                        temp.setSize(colw, temp.getHeight());
                     }
                  } else {
                     m.setSize(colw, d.height);
                  }
               }
            }
         }

         this.moveComponents(target, x, this.vgap, colw, maxheight - y, start, nmembers);
      }
   }

   public String toString() {
      String str = "";
      switch(this.newAlign) {
      case 0:
         str = ",align=TOP";
         break;
      case 1:
         str = ",align=MIDDLE";
         break;
      case 2:
         str = ",align=BOTTOM";
      }

      return this.getClass().getName() + "[hgap=" + this.hgap + ",vgap=" + this.vgap + str + "]";
   }
}
