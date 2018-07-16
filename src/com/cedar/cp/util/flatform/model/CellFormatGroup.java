// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import com.cedar.cp.util.RTree;
import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.awt.LinesBorder;
import com.cedar.cp.util.flatform.model.format.BackgroundColorProperty;
import com.cedar.cp.util.flatform.model.format.BoldFontProperty;
import com.cedar.cp.util.flatform.model.format.BorderProperty;
import com.cedar.cp.util.flatform.model.format.CurrencySymbolProperty;
import com.cedar.cp.util.flatform.model.format.DecimalPlacesProperty;
import com.cedar.cp.util.flatform.model.format.FontNameProperty;
import com.cedar.cp.util.flatform.model.format.FontSizeProperty;
import com.cedar.cp.util.flatform.model.format.FormatPatternProperty;
import com.cedar.cp.util.flatform.model.format.FormatProperty;
import com.cedar.cp.util.flatform.model.format.FormatTypeProperty;
import com.cedar.cp.util.flatform.model.format.HiddenProperty;
import com.cedar.cp.util.flatform.model.format.HorizontalAlignmentProperty;
import com.cedar.cp.util.flatform.model.format.ItalicFontProperty;
import com.cedar.cp.util.flatform.model.format.LockedProperty;
import com.cedar.cp.util.flatform.model.format.NegativeColorProperty;
import com.cedar.cp.util.flatform.model.format.OutlineLevelProperty;
import com.cedar.cp.util.flatform.model.format.ShowCommaProperty;
import com.cedar.cp.util.flatform.model.format.TextColorProperty;
import com.cedar.cp.util.flatform.model.format.UnderlineFontProperty;
import com.cedar.cp.util.flatform.model.format.VerticalAlignmentProperty;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class CellFormatGroup extends HashMap<String, FormatProperty> implements XMLWritable, Serializable {

   private RTree.Rect mRect;
   private int mVersion;


   public CellFormatGroup() {}

   public CellFormatGroup(RTree.Rect rect, Collection<FormatProperty> props, int version) {
      this.mRect = rect;
      this.mVersion = version;
      Iterator i$ = props.iterator();

      while(i$.hasNext()) {
         FormatProperty fp = (FormatProperty)i$.next();
         this.put(fp.getName(), fp);
      }

   }

   public RTree.Rect getRect() {
      return this.mRect;
   }

   public void setRect(RTree.Rect rect) {
      this.mRect = rect;
   }

   public int getVersion() {
      return this.mVersion;
   }

   public void setVersion(int version) {
      this.mVersion = version;
   }

   public void setBackgroundColor(String color) {
      this.setFormatProperty(new BackgroundColorProperty(color));
   }

   public void setTextColor(String color) {
      this.setFormatProperty(new TextColorProperty(color));
   }

   public void setHorizontalAlignment(int horizontalAlignment) {
      this.setFormatProperty(new HorizontalAlignmentProperty(horizontalAlignment));
   }

   public void setVerticalAlignment(int horizontalAlignment) {
      this.setFormatProperty(new VerticalAlignmentProperty(horizontalAlignment));
   }

   public void setBoldFont(boolean boldFont) {
      this.setFormatProperty(new BoldFontProperty(boldFont));
   }

   public void setItalicFont(boolean italicFont) {
      this.setFormatProperty(new ItalicFontProperty(italicFont));
   }

   public void setUnderlineFont(boolean underlineFont) {
      this.setFormatProperty(new UnderlineFontProperty(underlineFont));
   }

   public void setFontName(String fontName) {
      this.setFormatProperty(new FontNameProperty(fontName));
   }

   public void setFontSize(int fontSize) {
      this.setFormatProperty(new FontSizeProperty(fontSize));
   }
   
//   // Set outline level property
//   public void setOutlineLevel(int outlineLevel) {
//	  this.setFormatProperty(new OutlineLevelProperty(outlineLevel));
//   }

   public void setFormatPattern(String formatPattern) {
      this.setFormatProperty(new FormatPatternProperty(formatPattern));
   }

   public void setFormatType(int formatType) {
      this.setFormatProperty(new FormatTypeProperty(formatType));
   }

   public void setBorder(LinesBorder linesBorder) {
      this.setFormatProperty(new BorderProperty(linesBorder));
   }

   private void setFormatProperty(FormatProperty formatProperty) {
      this.put(formatProperty.getName(), formatProperty);
   }

   public void setLocked(boolean locked) {
      this.setFormatProperty(new LockedProperty(locked));
   }

   public void setHidden(boolean hidden) {
      this.setFormatProperty(new HiddenProperty(hidden));
   }

   public void setNegativeColor(String negativeColor) {
      this.setFormatProperty(new NegativeColorProperty(negativeColor));
   }

   public void setDecimalPlaces(int decimalPlaces) {
      this.setFormatProperty(new DecimalPlacesProperty(decimalPlaces));
   }

   public void setShowComma(boolean showComma) {
      this.setFormatProperty(new ShowCommaProperty(showComma));
   }

   public void setCurrencySymbol(String currencySymbol) {
      this.setFormatProperty(new CurrencySymbolProperty(currencySymbol));
   }

   public void writeXml(Writer out) throws IOException {
      if(!this.values().isEmpty()) {
         out.write("<cellFormatGroup");
         XmlUtils.outputAttribute(out, "version", Integer.valueOf(this.mVersion));
         Iterator i$ = this.values().iterator();

         FormatProperty prop;
         while(i$.hasNext()) {
            prop = (FormatProperty)i$.next();
            if(prop.isXmlAttribute()) {
               prop.writeXml(out);
            }
         }

         out.write(">");
         out.write("<rect ");
         XmlUtils.outputAttribute(out, "startColumn", Integer.valueOf(this.mRect.mStartColumn));
         XmlUtils.outputAttribute(out, "startRow", Integer.valueOf(this.mRect.mStartRow));
         XmlUtils.outputAttribute(out, "endColumn", Integer.valueOf(this.mRect.mEndColumn));
         XmlUtils.outputAttribute(out, "endRow", Integer.valueOf(this.mRect.mEndRow));
         out.write("/>");
         i$ = this.values().iterator();

         while(i$.hasNext()) {
            prop = (FormatProperty)i$.next();
            if(!prop.isXmlAttribute()) {
               prop.writeXml(out);
            }
         }

         out.write("</cellFormatGroup>");
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      if(this.mRect != null) {
         sb.append("mRect:" + this.mRect.toString());
      }

      sb.append(super.toString());
      return sb.toString();
   }
}
