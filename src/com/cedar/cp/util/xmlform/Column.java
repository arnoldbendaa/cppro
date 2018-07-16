// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.xmlform.ColumnFormat;
import com.cedar.cp.util.xmlform.ColumnModelData;
import com.cedar.cp.util.xmlform.TrafficLights;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.io.IOException;
import java.io.Writer;
import javax.swing.tree.DefaultMutableTreeNode;

public class Column extends DefaultMutableTreeNode implements XMLWritable {

   private String mId;
   private String mHeading;
   private String mType;
   private String mFormula;
   private boolean mProtected;
   private boolean mPersists;
   private boolean mHidden;
   private ColumnModelData mColumnModelData;
   private String mToolTipText;
   private boolean mAggregated;
   private String mLookup;
   private String mLinkColumn;
   private int mWidth;
   private String mLookupPartition;
   private boolean mRunningTotal;
   private ColumnFormat mColumnFormat = new ColumnFormat();
   private TrafficLights mTrafficLights;
   public static String TYPE_DATATYPE = "dataType";
   public static String TYPE_STRING = "string";
   public static String TYPE_FORMULA = "formula";
   public static String TYPE_STRING_FORMULA = "stringFormula";
   public static String TYPE_NUMBER = "number";
   public static String TYPE_TREE = "tree";
   public static String TYPE_LEVEL = "level";
   public static String TYPE_CELL_NOTE = "cellNote";


   public Column() {}

   public Column(Object userObject) {
      super(userObject);
   }

   public String getType() {
      return this.getPersists() && TYPE_NUMBER.equals(this.mType)?TYPE_DATATYPE:this.mType;
   }

   public void setType(String type) {
      if(this.mType == null || !this.mType.equals(type)) {
         this.mType = type;
         if(this.isTypeDataType()) {
            this.setPersists(true);
         }
      }

   }

   public String getId() {
      return this.mId;
   }

   public void setId(String id) {
      this.mId = id;
      this.setUserObject(this.mId + "," + this.mHeading);
   }

   public void setHeading(String heading) {
      this.mHeading = heading;
      this.setUserObject(this.mId + "," + this.mHeading);
   }

   public String getHeading() {
      return this.mHeading;
   }

   public String getHtmlHeading() {
      return XmlUtils.escapeStringForXML(this.mHeading, true);
   }

   public String getHeadingDisplay() {
      return this.mHeading.indexOf(60) >= 0?"<html><center>" + this.mHeading + "</center></html>":this.mHeading;
   }

   public String getDownloadDisplay() {
      return this.mHeading.indexOf(60) >= 0?this.mHeading.replaceAll("<[^>]*>", " "):this.mHeading;
   }

   public void setToolTipText(String toolTip) {
      this.mToolTipText = toolTip;
   }

   public String getToolTipText() {
      return this.mToolTipText;
   }

   public void setProtected(boolean flag) {
      this.mProtected = flag;
   }

   public boolean getIsProtected() {
      return this.mProtected;
   }

   public void setPersists(boolean flag) {
      if(this.mPersists != flag) {
         this.mPersists = flag;
         if(flag) {
            this.setAggregated(true);
            if(this.isTypeNumber()) {
               this.setType(TYPE_DATATYPE);
            }
         } else if(this.isTypeDataType()) {
            this.setType(TYPE_STRING);
         }
      }

   }

   public boolean getPersists() {
      return this.mPersists;
   }

   public void setFormula(String formula) {
      this.mFormula = formula;
      if(formula != null && formula.trim().length() > 0) {
         this.setProtected(true);
      }

   }

   public String getFormula() {
      return this.mFormula;
   }

   public ColumnModelData getColumnModelData() {
      return this.mColumnModelData;
   }

   public void setColumnModelData(ColumnModelData data) {
      this.mColumnModelData = data;
   }

   public boolean isTypeString() {
      return TYPE_STRING.equals(this.getType());
   }

   public boolean isTypeNumber() {
      return TYPE_NUMBER.equals(this.getType());
   }

   public boolean isTypeFormula() {
      return TYPE_FORMULA.equals(this.getType());
   }

   public boolean isTypeStringFormula() {
      return TYPE_STRING_FORMULA.equals(this.getType());
   }

   public boolean isTypeTree() {
      return TYPE_TREE.equals(this.getType());
   }

   public boolean isTypeCellNote() {
      return TYPE_CELL_NOTE.equals(this.getType());
   }

   public boolean isTypeLevel() {
      return TYPE_LEVEL.equals(this.getType());
   }

   public boolean isTypeDataType() {
      return TYPE_DATATYPE.equals(this.getType());
   }

   public boolean isHidden() {
      return this.mHidden;
   }

   public void setHidden(boolean state) {
      this.mHidden = state;
   }

   public boolean isBlankWhenZero() {
      return this.mColumnFormat.isBlankWhenZero();
   }

   public void setBlankWhenZero(boolean blankWhenZero) {
      this.mColumnFormat.setBlankWhenZero(blankWhenZero);
   }

   public String getFormat() {
      String format = this.mColumnFormat.getPattern();
      return format == null?"":format;
   }

   public void setFormat(String format) {
      this.mColumnFormat.setPattern(format);
   }

   public boolean isAggregated() {
      return !this.isTypeString() && !this.isTypeTree() && !this.isTypeStringFormula() && !this.isTypeCellNote()?(this.isTypeNumber()?true:(this.getPersists()?true:this.mAggregated)):false;
   }

   public void setAggregated(boolean isAggregated) {
      this.mAggregated = isAggregated;
   }

   public int getIntAlignment() {
      this.checkAlignment();
      return this.mColumnFormat.getAlignment();
   }

   private void checkAlignment() {
      int alignment = this.mColumnFormat.getAlignment();
      if(alignment < 0 || alignment >= ColumnFormat.sAlignments.length) {
         byte alignment1;
         if(!this.isTypeString() && !this.isTypeTree()) {
            alignment1 = 2;
         } else {
            alignment1 = 0;
         }

         this.mColumnFormat.setAlignment(alignment1);
      }

   }

   public void setAlignment(String alignment) {
      if(alignment != null && alignment.length() != 0) {
         if(alignment.equalsIgnoreCase("left")) {
            this.mColumnFormat.setAlignment(0);
         } else if(alignment.equalsIgnoreCase("center")) {
            this.mColumnFormat.setAlignment(1);
         } else {
            this.mColumnFormat.setAlignment(2);
         }
      } else {
         this.mColumnFormat.setAlignment(2);
      }

   }

   public String getAlignment() {
      this.checkAlignment();
      return ColumnFormat.sAlignments[this.mColumnFormat.getAlignment()];
   }

   public String getBackground() {
      return this.mColumnFormat.getBackground();
   }

   public void setBackground(String background) {
      this.mColumnFormat.setBackground(background);
   }

   public ColumnFormat getColumnFormat() {
      return this.mColumnFormat;
   }

   public String getLookup() {
      return this.mLookup;
   }

   public void setLookup(String lookup) {
      this.mLookup = lookup;
   }

   public int getWidth() {
      return this.mWidth;
   }

   public void setWidth(int width) {
      this.mWidth = width;
   }

   public String getLinkColumn() {
      return this.mLinkColumn;
   }

   public void setLinkColumn(String linkColumn) {
      this.mLinkColumn = linkColumn;
   }

   public String getLookupPartition() {
      return this.mLookupPartition;
   }

   public void setLookupPartition(String lookupPartition) {
      this.mLookupPartition = lookupPartition;
   }

   public TrafficLights getTrafficLights() {
      return this.mTrafficLights;
   }

   public void setTrafficLights(TrafficLights trafficLights) {
      this.mTrafficLights = trafficLights;
      super.add(trafficLights);
   }

   public void removeTrafficLights() {
      if(this.mTrafficLights != null) {
         super.remove(this.mTrafficLights);
         this.mTrafficLights = null;
      }

   }

   public boolean isRunningTotal() {
      return this.mRunningTotal;
   }

   public void setRunningTotal(boolean runningTotal) {
      this.mRunningTotal = runningTotal;
   }

   public void writeXml(Writer out) throws IOException {
      out.write("<column ");
      out.write(" id=\"" + this.mId + "\"");
      out.write(" type=\"" + this.getType() + "\"");
      out.write(" protected=\"" + this.mProtected + "\"");
      out.write(" persists=\"" + this.mPersists + "\"");
      out.write(" heading=\"" + XmlUtils.escapeStringForXML(this.mHeading) + "\"");
      if(this.mLookup != null && this.mLookup.trim().length() > 0) {
         out.write(" lookup=\"" + XmlUtils.escapeStringForXML(this.mLookup) + "\"");
      }

      if(this.mLookupPartition != null && this.mLookupPartition.trim().length() > 0) {
         out.write(" lookupPartition=\"" + XmlUtils.escapeStringForXML(this.mLookupPartition) + "\"");
      }

      if(this.mToolTipText != null && this.mToolTipText.trim().length() > 0) {
         out.write(" toolTipText=\"" + XmlUtils.escapeStringForXML(this.mToolTipText) + "\"");
      }

      String format = this.mColumnFormat.getPattern();
      if(format != null && format.trim().length() > 0) {
         out.write(" format=\"" + XmlUtils.escapeStringForXML(format) + "\"");
      }

      if(this.mLinkColumn != null && this.mLinkColumn.trim().length() > 0) {
         out.write(" linkColumn=\"" + XmlUtils.escapeStringForXML(this.mLinkColumn) + "\"");
      }

      String background = this.mColumnFormat.getBackground();
      if(background != null) {
         out.write(" background=\"" + background + "\"");
      }

      if(this.mWidth > 0) {
         out.write(" width=\"" + this.mWidth + "\"");
      }

      out.write(" blankWhenZero=\"" + this.mColumnFormat.isBlankWhenZero() + "\"");
      out.write(" aggregated=\"" + this.isAggregated() + "\"");
      out.write(" alignment=\"" + this.getAlignment() + "\"");
      out.write(" runningTotal=\"" + this.isRunningTotal() + "\"");
      out.write(" hidden=\"" + this.mHidden + "\">");
      if(this.mTrafficLights != null) {
         this.mTrafficLights.writeXml(out);
      }

      if(this.mFormula != null && this.mFormula.trim().length() > 0) {
         out.write("<formula><![CDATA[" + this.mFormula + "]]></formula>");
      }

      out.write("</column>");
   }

}
