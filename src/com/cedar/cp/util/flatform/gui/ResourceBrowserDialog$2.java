// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.flatform.gui.ResourceBrowserDialog;
import com.cedar.cp.util.flatform.model.CPImage;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.Icon;

class ResourceBrowserDialog$2 implements ItemListener {

   // $FF: synthetic field
   final ResourceBrowserDialog this$0;


   ResourceBrowserDialog$2(ResourceBrowserDialog var1) {
      this.this$0 = var1;
   }

   public void itemStateChanged(ItemEvent e) {
      Icon icon = ResourceBrowserDialog.accessMethod200(this.this$0).getIcon();
      if(icon instanceof CPImage) {
         CPImage image = (CPImage)icon;
         image.setScale(ResourceBrowserDialog.accessMethod300(this.this$0).isSelected());
         ResourceBrowserDialog.accessMethod200(this.this$0).repaint();
      }

   }
}
