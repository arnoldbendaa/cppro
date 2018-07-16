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

public class DateDetailPanel extends JPanel {

   private String[] mFormats = new String[]{"14/03/1998", "14-Mar-98", "3/14", "3/14/98", "03/14/98", "14-Mar", "Mar-98", "March-98", "March 14, 1998", "3/14/98 1:30 PM", "3/14/98 13:30", "14/3/98 1:30 PM", "14/3/98 13:30", "3/14/1998", "14-Mar-1998"};
   private String[] mPatterns = new String[]{"dd/MM/yyyy", "dd-MMM-yy", "M/dd", "M/dd/yy", "MM/dd/yy", "dd-MMM", "MMM-yy", "MMMMM-yy", "MMMMM dd, yyyy", "M/dd/yy hh:mm a", "M/dd/yy HH:mm", "dd/M/yy hh:mm a", "dd/M/yy HH:mm", "M/dd/yyyy", "dd-MMM-yyyy"};
   private JList mFormatList;


   public DateDetailPanel() {
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
