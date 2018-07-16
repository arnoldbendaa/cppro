// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.awt.LinesBorder;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import javax.swing.border.Border;

public interface CellFormat extends XMLWritable, Serializable {

   int FORMAT_TYPE_GENERAL = 0;
   int FORMAT_TYPE_NUMBER = 1;
   int FORMAT_TYPE_CURRENCY = 2;
   int FORMAT_TYPE_DATE = 3;
   int FORMAT_TYPE_TIME = 4;
   int FORMAT_TYPE_PERCENTAGE = 5;
   int FORMAT_TYPE_TEXT = 6;
   int FORMAT_TYPE_CUSTOM = 7;


   Color getBackgroundColor();

   void setBackgroundColor(Color var1);

   void setBackgroundColor(String var1);

   Color getInputableBackgroundColor();

   Color getTextColor();

   void setTextColor(Color var1);

   void setTextColor(String var1);

   Color getNegativeColor();

   void setNegativeColor(Color var1);

   void setNegativeColor(String var1);

   int getHorizontalAlignment();

   void setHorizontalAlignment(int var1);

   int getVerticalAlignment();

   void setVerticalAlignment(int var1);

   Font getFont();

   void setFont(Font var1);

   void setFontDetails(String var1, int var2, boolean var3, boolean var4);

   void setBoldFont(boolean var1);

   boolean isBoldFont();

   void setItalicFont(boolean var1);

   boolean isItalicFont();

   void setUnderlineFont(boolean var1);

   boolean isUnderlineFont();

   void setFontSize(int var1);

   int getFontSize();
   
   // Declare set outline level property
   void setOutlineLevel(int var1);
   
   // Declare get outline level property
   int getOutlineLevel();

   void setFontName(String var1);

   String getFontName();

   Border getBorder();

   void setBorder(LinesBorder var1);

   int getFormatType();

   void setFormatType(int var1);

   int getDecimalPlaces();

   void setDecimalPlaces(int var1);

   boolean isShowComma();

   void setShowComma(boolean var1);

   String getFormatPattern();

   void setFormatPattern(String var1);

   String getCurrencySymbol();

   void setCurrencySymbol(String var1);

   boolean isLocked();

   void setLocked(boolean var1);

   boolean isHidden();

   void setHidden(boolean var1);
}
