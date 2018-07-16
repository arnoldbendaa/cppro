// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.FileUtils;
import com.cedar.cp.util.awt.FCFileFilter;
import com.cedar.cp.util.flatform.gui.CreateImageDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class CreateImageDialog$1 implements ActionListener {

   // $FF: synthetic field
   final CreateImageDialog this$0;


   CreateImageDialog$1(CreateImageDialog var1) {
      this.this$0 = var1;
   }

   public void actionPerformed(ActionEvent e) {
      JFileChooser browser = new JFileChooser();
      FCFileFilter fileFilter = new FCFileFilter();
      String[] formatNames = ImageIO.getReaderFormatNames();
      String[] returnVal = new String[]{"gif", "jpg", "jpeg", "png"};
      int selectedFile = returnVal.length;

      for(int ioe = 0; ioe < selectedFile; ++ioe) {
         String extension = returnVal[ioe];
         fileFilter.addExtension(extension);
      }

      fileFilter.setDescription("Image file types");
      browser.setFileFilter(fileFilter);
      int var10 = browser.showOpenDialog(this.this$0.getParent());
      if(var10 == 0) {
         File var11 = browser.getSelectedFile();
         if(var11 != null && var11.isFile()) {
            try {
               byte[] var12 = FileUtils.getFileAsBytes(var11);
               CreateImageDialog.accessMethod002(this.this$0, CreateImageDialog.accessMethod100(this.this$0).storeResource(var11.getName(), var12));
               CreateImageDialog.accessMethod200(this.this$0).setText(var11.getName());
               CreateImageDialog.accessMethod300(this.this$0).setSelected(true);
               CreateImageDialog.accessMethod400(this.this$0).setText("");
            } catch (IOException var9) {
               JOptionPane.showMessageDialog(this.this$0.getParent(), "Unable to open file\n" + var9.getMessage(), "Open Error", 0);
               return;
            }
         }
      }

   }
}
