package com.cedar.cp.util.flatform.model;

import com.cedar.cp.util.awt.ColorUtils;
import com.cedar.cp.util.flatform.model.CPChart;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.WorksheetDataset;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.util.TableOrder;

public class CPChartFactory implements Serializable {

   private int mType;
   private String mTitle;
   private Color mChartBackground;
   private Color mPlotBackground;
   private Color mTitleColor;
   private WorksheetDataset mDataset;
   private String mDomainLabel;
   private String mRangeLabel;
   private String mOrientation = "vertical";
   private Font mTitleFont;


   public int getType() {
      return this.mType;
   }

   public void setType(int type) {
      this.mType = type;
   }

   public String getTitle() {
      return this.mTitle;
   }

   public void setTitle(String title) {
      this.mTitle = title;
   }

   public Color getTitleColor() {
      return this.mTitleColor;
   }

   public void setTitleColor(Color titleColor) {
      this.mTitleColor = titleColor;
   }

   public void setTitleColor(String titleColor) {
      this.mTitleColor = ColorUtils.getColorFromHexString(titleColor);
   }

   public Font getTitleFont() {
      return this.mTitleFont;
   }

   public void setTitleFont(Font titleFont) {
      this.mTitleFont = titleFont;
   }

   public void setTitleFont(String name, int style, int size) {
      this.mTitleFont = new Font(name, style, size);
   }

   public Color getChartBackground() {
      return this.mChartBackground;
   }

   public void setChartBackground(Color chartBackground) {
      this.mChartBackground = chartBackground;
   }

   public void setChartBackground(String chartBackground) {
      this.mChartBackground = ColorUtils.getColorFromHexString(chartBackground);
   }

   public Color getPlotBackground() {
      return this.mPlotBackground;
   }

   public void setPlotBackground(Color plotBackground) {
      this.mPlotBackground = plotBackground;
   }

   public void setPlotBackground(String plotBackground) {
      this.mPlotBackground = ColorUtils.getColorFromHexString(plotBackground);
   }

   public WorksheetDataset getDataset() {
      return this.mDataset;
   }

   public void setDataset(WorksheetDataset dataset) {
      this.mDataset = dataset;
   }

   public String getDomainLabel() {
      return this.mDomainLabel;
   }

   public void setDomainLabel(String domainLabel) {
      this.mDomainLabel = domainLabel;
   }

   public String getRangeLabel() {
      return this.mRangeLabel;
   }

   public void setRangeLabel(String rangeLabel) {
      this.mRangeLabel = rangeLabel;
   }

   public String getOrientation() {
      return this.mOrientation;
   }

   public void setOrientation(String orientation) {
      this.mOrientation = orientation;
   }

   public CPChart createChart(Worksheet worksheet) {
      this.mDataset.setWorksheet(worksheet);
      return this.createChart();
   }

   public CPChart createChart() {
      PlotOrientation orientation = this.mOrientation.equals("vertical")?PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL;
      JFreeChart chart = null;
      switch(this.mType) {
      case 0:
         chart = ChartFactory.createAreaChart(this.mTitle, this.mDomainLabel, this.mRangeLabel, this.mDataset, orientation, true, true, false);
         break;
      case 1:
         chart = ChartFactory.createBarChart(this.mTitle, this.mDomainLabel, this.mRangeLabel, this.mDataset, orientation, true, true, false);
         break;
      case 2:
         chart = ChartFactory.createBarChart3D(this.mTitle, this.mDomainLabel, this.mRangeLabel, this.mDataset, orientation, true, true, false);
         break;
      case 3:
         chart = ChartFactory.createLineChart(this.mTitle, this.mDomainLabel, this.mRangeLabel, this.mDataset, orientation, true, true, false);
         break;
      case 4:
         chart = ChartFactory.createLineChart3D(this.mTitle, this.mDomainLabel, this.mRangeLabel, this.mDataset, orientation, true, true, false);
         break;
      case 5:
         chart = ChartFactory.createMultiplePieChart(this.mTitle, this.mDataset, TableOrder.BY_COLUMN, true, true, false);
         break;
      case 6:
         chart = ChartFactory.createMultiplePieChart3D(this.mTitle, this.mDataset, TableOrder.BY_COLUMN, true, true, false);
         break;
      case 7:
         chart = ChartFactory.createStackedBarChart(this.mTitle, this.mDomainLabel, this.mRangeLabel, this.mDataset, orientation, true, true, false);
         break;
      case 8:
         chart = ChartFactory.createStackedBarChart3D(this.mTitle, this.mDomainLabel, this.mRangeLabel, this.mDataset, orientation, true, true, false);
         break;
      case 9:
         chart = ChartFactory.createWaterfallChart(this.mTitle, this.mDomainLabel, this.mRangeLabel, this.mDataset, orientation, true, true, false);
      }

      if(chart != null) {
         TextTitle title;
         if(this.mTitleColor != null) {
            title = chart.getTitle();
            title.setPaint(this.mTitleColor);
         }

         if(this.mTitleFont != null) {
            title = chart.getTitle();
            title.setFont(this.mTitleFont);
         }

         chart.setBackgroundPaint(this.mChartBackground);
         chart.getPlot().setBackgroundPaint(this.mPlotBackground);
      }

      return new CPChart(chart, this.mType);
   }
}
