// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:51
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.awt;

import java.io.Serializable;

public class XYConstraints implements Cloneable, Serializable {

   int x;
   int y;
   int width;
   int height;


   public XYConstraints() {
      this(0, 0, 0, 0);
   }

   public XYConstraints(int x, int y) {
      this(x, y, 0, 0);
   }

   public XYConstraints(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public int getX() {
      return this.x;
   }

   public void setX(int x) {
      this.x = x;
   }

   public int getY() {
      return this.y;
   }

   public void setY(int y) {
      this.y = y;
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

   public int hashCode() {
      return this.x ^ this.y * 37 ^ this.width * 43 ^ this.height * 47;
   }

   public boolean equals(Object that) {
      if(!(that instanceof XYConstraints)) {
         return false;
      } else {
         XYConstraints other = (XYConstraints)that;
         return other.x == this.x && other.y == this.y && other.width == this.width && other.height == this.height;
      }
   }

   public Object clone() {
      return new XYConstraints(this.x, this.y, this.width, this.height);
   }

   public String toString() {
      return String.valueOf((new StringBuffer("XYConstraints[")).append(this.x).append(",").append(this.y).append(",").append(this.width).append(",").append(this.height).append("]"));
   }
}
