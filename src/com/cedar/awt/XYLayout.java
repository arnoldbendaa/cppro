// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import com.cedar.awt.XYConstraints;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Hashtable;

public class XYLayout implements LayoutManager2, Serializable, Cloneable {

   int width;
   int height;
   Hashtable info = new Hashtable();
   static final XYConstraints defaultConstraints = new XYConstraints();


   public XYLayout() {}

   public XYLayout(int width, int height) {
      this.width = width;
      this.height = height;
   }

   public int getWidth() {
      return this.width;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public String toString() {
      return String.valueOf((new StringBuffer("XYLayout[width=")).append(this.width).append(",height=").append(this.height).append("]"));
   }

   public void addLayoutComponent(String s, Component component1) {}

   public void removeLayoutComponent(Component component) {
      this.info.remove(component);
   }

   public Dimension preferredLayoutSize(Container target) {
      return this.getLayoutSize(target, true);
   }

   public Dimension minimumLayoutSize(Container target) {
      return this.getLayoutSize(target, false);
   }

   public void layoutContainer(Container target) {
      Insets insets = target.getInsets();
      int count = target.getComponentCount();

      for(int i = 0; i < count; ++i) {
         Component component = target.getComponent(i);
         if(component.isVisible()) {
            Rectangle r = this.getComponentBounds(component, true);
            component.setBounds(insets.left + r.x, insets.top + r.y, r.width, r.height);
         }
      }

   }

   public void addLayoutComponent(Component component, Object constraints) {
      if(constraints instanceof XYConstraints) {
         this.info.put(component, constraints);
      }

   }

   public Dimension maximumLayoutSize(Container target) {
      return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
   }

   public float getLayoutAlignmentX(Container target) {
      return 0.5F;
   }

   public float getLayoutAlignmentY(Container target) {
      return 0.5F;
   }

   public void invalidateLayout(Container container) {}

   Rectangle getComponentBounds(Component component, boolean doPreferred) {
      XYConstraints constraints = (XYConstraints)this.info.get(component);
      if(constraints == null) {
         constraints = defaultConstraints;
      }

      Rectangle r = new Rectangle(constraints.x, constraints.y, constraints.width, constraints.height);
      if(r.width <= 0 || r.height <= 0) {
         Dimension d = doPreferred?component.getPreferredSize():component.getMinimumSize();
         if(r.width <= 0) {
            r.width = d.width;
         }

         if(r.height <= 0) {
            r.height = d.height;
         }
      }

      return r;
   }

   Dimension getLayoutSize(Container target, boolean doPreferred) {
      Dimension dim = new Dimension(0, 0);
      if(this.width <= 0 || this.height <= 0) {
         int insets = target.getComponentCount();

         for(int i = 0; i < insets; ++i) {
            Component component = target.getComponent(i);
            if(component.isVisible()) {
               Rectangle r = this.getComponentBounds(component, doPreferred);
               dim.width = Math.max(dim.width, r.x + r.width);
               dim.height = Math.max(dim.height, r.y + r.height);
            }
         }
      }

      if(this.width > 0) {
         dim.width = this.width;
      }

      if(this.height > 0) {
         dim.height = this.height;
      }

      Insets var8 = target.getInsets();
      dim.width += var8.left + var8.right;
      dim.height += var8.top + var8.bottom;
      return dim;
   }

}
