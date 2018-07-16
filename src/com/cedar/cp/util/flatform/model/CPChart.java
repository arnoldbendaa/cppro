// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.awt.ColorUtils;
import com.cedar.cp.util.xmlform.WorksheetEmbeddedObject;
import com.cedar.cp.util.xmlform.XMLWritable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;

public class CPChart implements Serializable, WorksheetEmbeddedObject {

   private int mType;
   private JFreeChart mChart;


   public CPChart(JFreeChart chart, int type) {
      this.mChart = chart;
      this.mType = type;
   }

   public JFreeChart getChart() {
      return this.mChart;
   }

   public void setChart(JFreeChart chart) {
      this.mChart = chart;
   }

   public int getType() {
      return this.mType;
   }

   public void setType(int type) {
      this.mType = type;
   }

   public void writeXml(Writer out) throws IOException {
      Paint bp = this.mChart.getBackgroundPaint();
      Color bgColor = (Color)bp;
      out.write("<chart type=\"" + this.mType + "\"");
      if(bgColor != null) {
         out.write(" background=\"" + ColorUtils.getHexStringFromColor(bgColor) + "\"");
      }

      out.write(" >");
      this.writeChartTitle(out);
      this.writePlot(out);
      this.writeDataset(out);
      out.write("</chart>");
   }

   private void writeChartTitle(Writer out) throws IOException {
      TextTitle title = this.mChart.getTitle();
      if(title != null) {
         Color color = Color.black;
         Paint p = title.getPaint();
         if(p instanceof Color) {
            color = (Color)p;
         }

         out.write("<chartTitle text=\"" + XmlUtils.escapeStringForXML(title.getText()) + "\" color=\"" + ColorUtils.getHexStringFromColor(color) + "\" >");
         Font f = title.getFont();
         if(f != null) {
            out.write("<font name=\"" + f.getName() + "\" style=\"" + f.getStyle() + "\" size=\"" + f.getSize() + "\" />");
         }

         out.write("</chartTitle>");
      }

   }

   private void writePlot(Writer out) throws IOException {
      Plot plot = this.mChart.getPlot();
      if(plot instanceof CategoryPlot) {
         CategoryPlot piePlot = (CategoryPlot)plot;
         CategoryAxis p = piePlot.getDomainAxis();
         String bgColor = p.getLabel();
         ValueAxis rangeAxis = piePlot.getRangeAxis();
         String rangeLabel = rangeAxis.getLabel();
         Paint p1 = plot.getBackgroundPaint();
         Color bgColor1 = (Color)p1;
         out.write("<plot domainLabel=\"" + XmlUtils.escapeStringForXML(bgColor) + "\" rangeLabel=\"" + XmlUtils.escapeStringForXML(rangeLabel) + "\"");
         if(bgColor1 != null) {
            out.write(" background=\"" + ColorUtils.getHexStringFromColor(bgColor1) + "\"");
         }

         if(piePlot.getOrientation() == PlotOrientation.HORIZONTAL) {
            out.write(" orientation=\"horizontal\"");
         } else {
            out.write(" orientation=\"vertical\"");
         }

         out.write(" />");
      } else if(plot instanceof MultiplePiePlot) {
         MultiplePiePlot piePlot1 = (MultiplePiePlot)plot;
         Paint p2 = plot.getBackgroundPaint();
         Color bgColor2 = (Color)p2;
         out.write("<plot");
         if(bgColor2 != null) {
            out.write(" background=\"" + ColorUtils.getHexStringFromColor(bgColor2) + "\"");
         }

         out.write(" />");
      }

   }

   private void writeDataset(Writer out) throws IOException {
      Plot plot = this.mChart.getPlot();
      CategoryDataset dataset = null;
      if(plot instanceof CategoryPlot) {
         dataset = ((CategoryPlot)plot).getDataset();
      } else if(plot instanceof MultiplePiePlot) {
         dataset = ((MultiplePiePlot)plot).getDataset();
      }

      if(dataset instanceof XMLWritable) {
         ((XMLWritable)dataset).writeXml(out);
      }

   }
}
