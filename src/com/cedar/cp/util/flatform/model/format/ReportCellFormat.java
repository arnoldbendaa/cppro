package com.cedar.cp.util.flatform.model.format;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.awt.ColorUtils;
import java.io.IOException;
import java.io.Writer;

public class ReportCellFormat extends DefaultCellFormat
{
  private boolean mIsDefaultRowFormatting;

  public ReportCellFormat()
  {
    mIsDefaultRowFormatting = true;
  }

  public ReportCellFormat(ReportCellFormat cellFormat) {
    setBackgroundColor(cellFormat.getBackgroundColor());
    setTextColor(cellFormat.getTextColor());
    setNegativeColor(cellFormat.getNegativeColor());
    setHorizontalAlignment(cellFormat.getHorizontalAlignment());
    setVerticalAlignment(cellFormat.getVerticalAlignment());
    setDecimalPlaces(cellFormat.getDecimalPlaces());
    setShowComma(cellFormat.isShowComma());
    setFormatPattern(cellFormat.getFormatPattern());
    setCurrencySymbol(cellFormat.getCurrencySymbol());
    setFormatType(cellFormat.getFormatType());
    setFontName(cellFormat.getFontName());
    setFontSize(cellFormat.getFontSize());
    setBoldFont(cellFormat.isBoldFont());
    setItalicFont(cellFormat.isItalicFont());
    setUnderlineFont(cellFormat.isUnderlineFont());
    setDefaultRowFormatting(cellFormat.isDefaultRowFormatting());
  }

  public void setRepBackgroundColor(String color)
  {
    setBackgroundColor(ColorUtils.getColorFromHexString(color));
  }

  public void setRepTextColor(String color)
  {
    setTextColor(ColorUtils.getColorFromHexString(color));
  }

  public void setRepNegativeColor(String color)
  {
    setNegativeColor(ColorUtils.getColorFromHexString(color));
  }

  public void setDefaultRowFormatting(boolean defaultBackGroundFormatting) {
    mIsDefaultRowFormatting = defaultBackGroundFormatting;
  }

  public boolean isDefaultRowFormatting() {
    return mIsDefaultRowFormatting;
  }

  public void writeXml(Writer out) throws IOException {
    out.write("<reportcellformat");
    XmlUtils.outputAttribute(out, "repBackgroundColor", ColorUtils.getHexStringFromColor(getBackgroundColor()));
    XmlUtils.outputAttribute(out, "repTextColor", ColorUtils.getHexStringFromColor(getTextColor()));
    XmlUtils.outputAttribute(out, "repNegativeColor", ColorUtils.getHexStringFromColor(getNegativeColor()));
    XmlUtils.outputAttribute(out, "horizontalAlignment", Integer.toString(getHorizontalAlignment()));
    XmlUtils.outputAttribute(out, "verticalAlignment", Integer.toString(getVerticalAlignment()));
    XmlUtils.outputAttribute(out, "decimalPlaces", Integer.toString(getDecimalPlaces()));
    XmlUtils.outputAttribute(out, "showComma", Boolean.toString(isShowComma()));
    if ((getFormatPattern() != null) && (getFormatPattern().trim().length() > 0)) {
      XmlUtils.outputAttribute(out, "formatPattern", getFormatPattern());
    }
    if (getCurrencySymbol() != null) {
      XmlUtils.outputAttribute(out, "currencySymbol", getCurrencySymbol());
    }
    XmlUtils.outputAttribute(out, "formatType", Integer.toString(getFormatType()));
    XmlUtils.outputAttribute(out, "fontName", getFontName());
    XmlUtils.outputAttribute(out, "fontSize", Integer.toString(getFontSize()));
    XmlUtils.outputAttribute(out, "boldFont", Boolean.toString(isBoldFont()));
    XmlUtils.outputAttribute(out, "italicFont", Boolean.toString(isItalicFont()));
    XmlUtils.outputAttribute(out, "underlineFont", Boolean.toString(isUnderlineFont()));
    XmlUtils.outputAttribute(out, "defaultRowFormatting", Boolean.toString(isDefaultRowFormatting()));
    out.write("/>");
  }
}