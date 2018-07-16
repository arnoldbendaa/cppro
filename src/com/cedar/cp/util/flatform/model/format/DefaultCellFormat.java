// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.awt.ColorUtils;
import com.cedar.cp.util.awt.LinesBorder;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.Writer;
import javax.swing.border.Border;

public class DefaultCellFormat implements CellFormat {

   private Color mBackgroundColor;
   private Color mInputableBackgroundColor;
   private Color mTextColor;
   private Color mNegativeColor;
   private int mHorizontalAlignment;
   private int mVerticalAlignment;
   private LinesBorder mBorder;
   private int mFormatType;
   private int mDecimalPlaces;
   private boolean mShowComma;
   private String mFormatPattern;
   private String mCurrencySymbol;
   private boolean mLocked;
   private boolean mHidden;
   private Font mFont;
   private boolean mBoldFont;
   private boolean mItalicFont;
   private boolean mUnderlineFont;
   private int mFontSize;
   private int mOutlineLevel;
   private String mFontName;


   public DefaultCellFormat() {
      this.mBackgroundColor = Color.WHITE;
      this.mInputableBackgroundColor = new Color(16777164);
      this.mTextColor = Color.BLACK;
      this.mNegativeColor = Color.red;
      this.mHorizontalAlignment = 4;
      this.mVerticalAlignment = 0;
      this.mFormatType = 0;
      this.mDecimalPlaces = 2;
      this.mShowComma = true;
      this.mLocked = true;
      this.mFontSize = 10;
      // Default outline levels property is 0 (no group)
      this.mOutlineLevel = 0;
      this.mFontName = "Arial";
   }

   public Color getInputableBackgroundColor() {
      return this.mInputableBackgroundColor;
   }

   public Color getBackgroundColor() {
      return this.mBackgroundColor;
   }

   public void setBackgroundColor(Color backgroundColor) {
      this.mBackgroundColor = backgroundColor;
   }

   public void setBackgroundColor(String color) {
      this.setBackgroundColor(ColorUtils.getColorFromHexString(color));
   }

   public Color getTextColor() {
      return this.mTextColor;
   }

   public void setTextColor(Color textColor) {
      this.mTextColor = textColor;
   }

   public void setTextColor(String color) {
      this.setTextColor(ColorUtils.getColorFromHexString(color));
   }

   public Color getNegativeColor() {
      return this.mNegativeColor;
   }

   public void setNegativeColor(Color negativeColor) {
      this.mNegativeColor = negativeColor;
   }

   public void setNegativeColor(String color) {
      this.setNegativeColor(ColorUtils.getColorFromHexString(color));
   }

   public int getHorizontalAlignment() {
      return this.mHorizontalAlignment;
   }

   public void setHorizontalAlignment(int horizontalAlignment) {
      this.mHorizontalAlignment = horizontalAlignment;
   }

   public int getVerticalAlignment() {
      return this.mVerticalAlignment;
   }

   public void setVerticalAlignment(int verticalAlignment) {
      this.mVerticalAlignment = verticalAlignment;
   }

   public Font getFont() {
      if(this.mFont == null && this.mFontName != null) {
         int style = this.isBoldFont()?1:0;
         if(this.isItalicFont()) {
            style |= 2;
         }

         Font f = new Font(this.mFontName, style, this.mFontSize);
         this.setFont(f);
      }

      return this.mFont;
   }

   public void setFont(Font font) {
      this.mFont = font;
      if(font == null) {
         this.mFontName = null;
         this.mFontSize = 0;
         this.mBoldFont = false;
         this.mItalicFont = false;
      } else {
         this.mFontName = font.getName();
         this.mFontSize = font.getSize();
         this.mBoldFont = font.isBold();
         this.mItalicFont = font.isItalic();
      }

   }

   public void setFontDetails(String name, int size, boolean bold, boolean italic) {
      if(name != null) {
         this.mFontSize = size;
         this.mBoldFont = bold;
         this.mItalicFont = italic;
         this.mFontName = name;
      }

   }

   public boolean isBoldFont() {
      return this.mBoldFont;
   }

   public void setBoldFont(boolean boldFont) {
      this.mBoldFont = boldFont;
   }

   public boolean isItalicFont() {
      return this.mItalicFont;
   }

   public void setItalicFont(boolean italicFont) {
      this.mItalicFont = italicFont;
   }

   public boolean isUnderlineFont() {
      return this.mUnderlineFont;
   }

   public void setUnderlineFont(boolean underlineFont) {
      this.mUnderlineFont = underlineFont;
   }

   public int getFontSize() {
      return this.mFontSize;
   }

   public void setFontSize(int fontSize) {
      this.mFontSize = fontSize;
   }
   
   // Get outline level property of default cell
   public int getOutlineLevel() {
	  return this.mOutlineLevel;
   }
   
   // Set outline level property of default cell
   public void setOutlineLevel(int outlineLevel) {
	  this.mOutlineLevel = outlineLevel;
	}

   public String getFontName() {
      return this.mFontName;
   }

   public void setFontName(String fontName) {
      this.mFontName = fontName;
   }

   public Border getBorder() {
      return this.mBorder;
   }

   public void setBorder(LinesBorder border) {
      this.mBorder = border;
   }

   public int getFormatType() {
      return this.mFormatType;
   }

   public void setFormatType(int formatType) {
      this.mFormatType = formatType;
   }

   public int getDecimalPlaces() {
      return this.mDecimalPlaces;
   }

   public void setDecimalPlaces(int decimalPlaces) {
      this.mDecimalPlaces = decimalPlaces;
   }

   public boolean isShowComma() {
      return this.mShowComma;
   }

   public void setShowComma(boolean showComma) {
      this.mShowComma = showComma;
   }

   public String getFormatPattern() {
      return this.mFormatPattern;
   }

   public void setFormatPattern(String formatPattern) {
      this.mFormatPattern = formatPattern;
   }

   public String getCurrencySymbol() {
      return this.mCurrencySymbol;
   }

   public void setCurrencySymbol(String currencySymbol) {
      this.mCurrencySymbol = currencySymbol;
   }

   public boolean isLocked() {
      return this.mLocked;
   }

   public void setLocked(boolean locked) {
      this.mLocked = locked;
   }

   public boolean isHidden() {
      return this.mHidden;
   }

   public void setHidden(boolean hidden) {
      this.mHidden = hidden;
   }

   public void writeXml(Writer out) throws IOException {}
}
