// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.model.CPImage;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class WrappedImage extends JLabel {

   public WrappedImage(CPImage image) {
      super((String)null, image, 0);
   }

   public void paint(Graphics g) {
      CPImage cpImage = (CPImage)this.getIcon();
      if(cpImage != null) {
         int width;
         int height;
         if(cpImage.isScale()) {
            width = this.getWidth();
            height = this.getHeight();
         } else {
            Image image = cpImage.getImage();
            width = image.getWidth((ImageObserver)null);
            height = image.getHeight((ImageObserver)null);
         }

         g.drawImage(((ImageIcon)this.getIcon()).getImage(), 0, 0, width, height, (ImageObserver)null);
      }

      this.paintBorder(g);
   }
}
