// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import java.awt.BorderLayout;
import java.util.Arrays;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TimeDetailPanel extends JPanel {

   private String[] mFormats = new String[]{"13:30", "1:30 PM", "13:30:55", "1:30:55 PM", "30:55"};
   private String[] mPatterns = new String[]{"HH:mm", "hh:mm a", "HH:mm:ss", "hh:mm:ss a", "mm:ss"};
   private JList mFormatList;


   public TimeDetailPanel() {
      super(new BorderLayout(5, 5));
      this.mFormatList = new JList(this.mFormats);
      this.add(new JScrollPane(this.mFormatList), "Center");
   }

   public String getFormatPattern() {
      int index = this.mFormatList.getSelectedIndex();
      return index < 0?null:this.mPatterns[index];
   }

   public void selectPattern(String pattern) {
      if(pattern == null) {
         this.mFormatList.setSelectedIndex(-1);
      } else {
         int index = Arrays.asList(this.mPatterns).indexOf(pattern);
         if(index < 0) {
            index = 0;
         }

         this.mFormatList.setSelectedIndex(index);
      }

   }

   public void resetPanel() {
      this.mFormatList.setSelectedIndex(-1);
   }
}
