// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.awt.TwoColumnLayout;
import com.cedar.cp.util.flatform.gui.format.CustomDetailPanel$1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CustomDetailPanel extends JPanel {

   public static String[] CUSTOM_FORMATS = new String[]{"0", "0.00", "#,##0", "#,##0.00", "#,##0;-#,##0", "#,##0;(#,##0)", "#,##0.0;-#,##0.0", "#,##0.0;(#,##0.0)", "#,##0.00;-#,##0.00", "#,##0.00;(#,##0.00)", "£#,##0;-£#,##0", "£#,##0;(£#,##0)", "£#,##0.00;-£#,##0.00", "£#,##0.00;(£#,##0.00)", "0%", "0.00%"};
   private JList mFormatList;
   boolean mSetNegativeColor;
   Color mTextColor;
   private JPanel mText;
   private JLabel mExample;


   public CustomDetailPanel() {
      super(new BorderLayout(5, 5));
      this.mTextColor = Color.red;
      this.mExample = new JLabel("Example");
      this.mFormatList = new JList(CUSTOM_FORMATS);
      this.add(new JScrollPane(this.mFormatList), "Center");
      JPanel south = new JPanel(new TwoColumnLayout(5, 5));
      this.mText = new JPanel();
      this.mText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
      this.mText.add(this.mExample);
      this.mExample.setForeground(this.mTextColor);
      JButton negative = new JButton("Negative Color");
      negative.addActionListener(new CustomDetailPanel$1(this));
      south.add(negative);
      south.add(this.mText);
      this.add(south, "South");
   }

   public String getFormatPattern() {
      int index = this.mFormatList.getSelectedIndex();
      return index < 0?null:CUSTOM_FORMATS[index];
   }

   public void selectPattern(String pattern) {
      if(pattern == null) {
         this.mFormatList.setSelectedIndex(-1);
      } else {
         int index = Arrays.asList(CUSTOM_FORMATS).indexOf(pattern);
         if(index < 0) {
            index = 0;
         }

         this.mFormatList.setSelectedIndex(index);
      }

   }

   public void resetPanel() {
      this.mFormatList.setSelectedIndex(-1);
      this.mSetNegativeColor = false;
   }

   // $FF: synthetic method
   static JLabel accessMethod000(CustomDetailPanel x0) {
      return x0.mExample;
   }

}
