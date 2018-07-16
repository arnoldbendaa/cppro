// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.awt.TwoColumnLayout;
import com.cedar.cp.util.flatform.gui.format.CurrencyDetailPanel$1;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class CurrencyDetailPanel extends JPanel {

   JSpinner mDecPlaces = new JSpinner();
   JComboBox mSymbol;
   Color mTextColor;
   boolean mSetNegativeColor;
   private JPanel mText;
   private JLabel mExample;


   public CurrencyDetailPanel() {
      super(new TwoColumnLayout(5, 5));
      this.mTextColor = Color.red;
      this.mExample = new JLabel("Example");
      this.add(new JLabel("Decimal places:"));
      this.mDecPlaces.setValue(Integer.valueOf(2));
      this.add(this.mDecPlaces);
      this.add(new JLabel("Currency Symbol"));
      this.mSymbol = new JComboBox(new Object[]{"", "£ ", "$ ", "€ ", "AED ", "AFN ", "ALL ", "AMD ", "ANG ", "AOA ", "ARS ", "AUD ", "AWG ", "AZN ", "BAM ", "BBD ", "BDT ", "BGN ", "BHD ", "BIF ", "BMD ", "BND ", "BOB ", "BRL ", "BSD ", "BTN ", "BWP ", "BYR ", "BZD ", "CAD ", "CDF ", "CHF ", "CLP ", "CNY ", "COP ", "CRC ", "CUP ", "CVE ", "CYP ", "CZK ", "DJF ", "DKK ", "DOP ", "DZD ", "EEK ", "EGP ", "ERN ", "ETB ", "EUR ", "FJD ", "FKP ", "GBP ", "GEL ", "GGP ", "GHS ", "GIP ", "GMD ", "GNF ", "GTQ ", "GYD ", "HKD ", "HNL ", "HRK ", "HTG ", "HUF ", "IDR ", "ILS ", "IMP ", "INR ", "IQD ", "IRR ", "ISK ", "JEP ", "JMD ", "JOD ", "JPY ", "KES ", "KGS ", "KHR ", "KMF ", "KPW ", "KRW ", "KWD ", "KYD ", "KZT ", "LAK ", "LBP ", "LKR ", "LRD ", "LSL ", "LTL ", "LVL ", "LYD ", "MAD ", "MDL ", "MGA ", "MKD ", "MMK ", "MNT ", "MOP ", "MRO ", "MTL ", "MUR ", "MVR ", "MWK ", "MXN ", "MYR ", "MZN ", "NAD ", "NGN ", "NIO ", "NOK ", "NPR ", "NZD ", "OMR ", "PAB ", "PEN ", "PGK ", "PHP ", "PKR ", "PLN ", "PYG ", "QAR ", "RON ", "RSD ", "RUB ", "RWF ", "SAR ", "SBD ", "SCR ", "SDG ", "SEK ", "SGD ", "SHP ", "SKK ", "SLL ", "SOS ", "SPL ", "SRD ", "STD ", "SVC ", "SYP ", "SZL ", "THB ", "TJS ", "TMM ", "TND ", "TOP ", "TRY ", "TTD ", "TVD ", "TWD ", "TZS ", "UAH ", "UGX ", "USD ", "UYU ", "UZS ", "VEB ", "VEF ", "VND ", "VUV ", "WST ", "XAF ", "XAG ", "XAU ", "XCD ", "XDR ", "XOF ", "XPD ", "XPF ", "XPT ", "YER ", "ZAR ", "ZMK ", "ZWD "});
      this.add(this.mSymbol);
      this.mText = new JPanel();
      this.mText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
      this.mText.add(this.mExample);
      this.mExample.setForeground(this.mTextColor);
      JButton negative = new JButton("Negative Color");
      negative.addActionListener(new CurrencyDetailPanel$1(this));
      this.add(negative);
      this.add(this.mText);
   }

   public void resetPanel() {
      this.mDecPlaces.setValue(Integer.valueOf(-1));
      this.mSetNegativeColor = false;
      this.mSymbol.setSelectedIndex(-1);
   }

   // $FF: synthetic method
   static JLabel accessMethod000(CurrencyDetailPanel x0) {
      return x0.mExample;
   }
}
