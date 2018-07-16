// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.awt.table.MultiSpanCellTable;
import com.cedar.cp.util.flatform.gui.SparseTableModel;
import com.cedar.cp.util.flatform.model.Cell;
import java.awt.Rectangle;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartChangeEvent;

public class WrappedChartPanel extends ChartPanel {

   private MultiSpanCellTable mOwner;
   private SparseTableModel mSparseModel;
   private Cell mCell;


   public WrappedChartPanel(MultiSpanCellTable owner, JFreeChart chart, Cell cell) {
      super(chart);
      this.mOwner = owner;
      this.mSparseModel = (SparseTableModel)owner.getModel();
      this.mCell = cell;
   }

   public void chartChanged(ChartChangeEvent event) {
      super.chartChanged(event);
      this.mSparseModel.fireTableCellUpdated(this.mCell.getRow(), this.mCell.getColumn());
   }

   public int getWidth() {
      Rectangle rect = this.mOwner.getCellRect(this.mCell.getRow(), this.mCell.getColumn(), true);
      return rect.width;
   }

   public int getHeight() {
      Rectangle rect = this.mOwner.getCellRect(this.mCell.getRow(), this.mCell.getColumn(), true);
      return rect.height;
   }
}
