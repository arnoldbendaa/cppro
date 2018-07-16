// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import com.cedar.cp.util.xmlform.WorksheetEmbeddedObject;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.net.URL;
import javax.swing.ImageIcon;

public class CPImage extends ImageIcon implements Serializable, WorksheetEmbeddedObject {

   private int mImageId;
   private boolean mScale = true;


   public CPImage(URL location) {
      super(location);
   }

   public int getImageId() {
      return this.mImageId;
   }

   public void setImageId(int imageId) {
      this.mImageId = imageId;
   }

   public boolean isScale() {
      return this.mScale;
   }

   public void setScale(boolean scale) {
      this.mScale = scale;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<image imageId=\"" + this.mImageId + "\" scale=\"" + this.mScale + "\" />");
   }
}
