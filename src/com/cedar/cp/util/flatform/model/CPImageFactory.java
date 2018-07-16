// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import com.cedar.cp.util.flatform.model.CPImage;
import com.cedar.cp.util.flatform.model.ResourceStorage;
import com.cedar.cp.util.flatform.model.Worksheet;
import java.io.Serializable;

public class CPImageFactory implements Serializable {

   private int mImageId;
   private boolean mScale = true;
   private ResourceStorage mResourceHandler;


   public int getImageId() {
      return this.mImageId;
   }

   public void setImageId(int imageId) {
      this.mImageId = imageId;
   }

   public ResourceStorage getResourceHandler() {
      return this.mResourceHandler;
   }

   public boolean isScale() {
      return this.mScale;
   }

   public void setScale(boolean scale) {
      this.mScale = scale;
   }

   public void setResourceHandler(ResourceStorage resourceHandler) {
      this.mResourceHandler = resourceHandler;
   }

   public CPImage createImage(Worksheet worksheet) {
      this.mResourceHandler = worksheet.getWorkbook().getResourceHandler();
      return this.createImage();
   }

   public CPImage createImage() {
      CPImage image = null;
      image = new CPImage(this.mResourceHandler.getResourceURL(this.mImageId));
      image.setImageId(this.mImageId);
      image.setScale(this.mScale);
      return image;
   }
}
