// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.awt.TwoColumnLayout;
import com.cedar.cp.util.flatform.gui.format.PercentageDetailPanel$1;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class PercentageDetailPanel extends JPanel {

   JSpinner mDecPlaces = new JSpinner();
   Color mTextColor;
   boolean mSetNegativeColor;
   private JPanel mText;
   private JLabel mExample;


   public PercentageDetailPanel() {
      super(new TwoColumnLayout(5, 5));
      this.mTextColor = Color.red;
      this.mExample = new JLabel("Example");
      this.add(new JLabel("Decimal places:"));
      this.mDecPlaces.setValue(Integer.valueOf(2));
      this.add(this.mDecPlaces);
      this.mText = new JPanel();
      this.mText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
      this.mText.add(this.mExample);
      this.mExample.setForeground(this.mTextColor);
      JButton negative = new JButton("Negative Color");
      negative.addActionListener(new PercentageDetailPanel$1(this));
      this.add(negative);
      this.add(this.mText);
   }

   public void resetPanel() {
      this.mDecPlaces.setValue(Integer.valueOf(-1));
      this.mSetNegativeColor = false;
   }

   // $FF: synthetic method
   static JLabel accessMethod000(PercentageDetailPanel x0) {
      return x0.mExample;
   }
}
