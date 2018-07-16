// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import java.awt.Color;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

public class BenchmarkChartServlet extends HttpServlet {

   public static final String BENCHMARK_ATTRIBUTE_NAME = "benchmarkDataSet";
   private static Color sColor = new Color(15004926);


   public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
      HttpSession session = httpServletRequest.getSession();
      CategoryDataset dataset = (CategoryDataset)session.getAttribute("benchmarkDataSet");
      ServletOutputStream out = httpServletResponse.getOutputStream();

      try {
         JFreeChart t = ChartFactory.createLineChart("Benchmark Chart", "Test Run", "Elapsed Time", dataset, PlotOrientation.VERTICAL, true, true, false);
//         t.setBackgroundPaint(sColor);
         httpServletResponse.setContentType("image/png");
         ChartUtilities.writeChartAsPNG(out, t, 800, 600);
      } catch (Exception var11) {
         System.err.println(var11.toString());
      } catch (Throwable var12) {
         System.err.println(var12.toString());
      } finally {
         out.close();
      }

      session.removeAttribute("benchmarkDataSet");
   }

}
