// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.CreateImageDialog;
import com.cedar.cp.util.flatform.gui.ResourceBrowserDialog;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CreateImageDialog$2 implements ActionListener {

   // $FF: synthetic field
   final CreateImageDialog this$0;


   CreateImageDialog$2(CreateImageDialog var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      ResourceBrowserDialog browser = null;
      Window owner = this.this$0.getOwner();
      if(owner instanceof Frame) {
         browser = new ResourceBrowserDialog((Frame)this.this$0.getOwner(), CreateImageDialog.accessMethod100(this.this$0).getResourceHandler());
      } else {
         browser = new ResourceBrowserDialog((Dialog)this.this$0.getOwner(), CreateImageDialog.accessMethod100(this.this$0).getResourceHandler());
      }

      if(browser.doModal()) {
         int imageId = browser.getSelectedImageId();
         if(imageId > 0) {
            CreateImageDialog.accessMethod002(this.this$0, imageId);
            CreateImageDialog.accessMethod200(this.this$0).setText("");
            CreateImageDialog.accessMethod400(this.this$0).setText(browser.getSelectedImageName());
            CreateImageDialog.accessMethod500(this.this$0).setSelected(true);
         }
      }

   }
}
