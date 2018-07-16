// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import com.cedar.cp.util.awt.OkCancelDialog;
import com.cedar.cp.util.awt.TwoColumnLayout;
import com.cedar.cp.util.flatform.gui.CreateChartDialog$1;
import com.cedar.cp.util.flatform.model.CPChart;
import com.cedar.cp.util.flatform.model.CPChartFactory;
import com.cedar.cp.util.flatform.model.WorksheetDataset;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class CreateChartDialog extends OkCancelDialog {

   private JTextField mChartTitle;
   private JTextField mRangeLabel;
   private JTextField mDomainLabel;
   private JTextField mSpecificCell;
   private JSpinner mRowCount;
   private JSpinner mColumnCount;
   private JComboBox mChartType;
   private String[] mChartTypes = new String[]{"Area", "Bar", "Bar 3D", "Line", "Line 3D", "Multiple Pie", "Multiple Pie 3D", "Stacked Bar", "Stacked Bar 3D", "Waterfall"};
   private JComboBox mPlacement;
   private String[] mPlacementTypes = new String[]{"Above Data", "Left of Data", "Below Data", "Right of Data", "Specific Cell"};
   public static final int PLACEMENT_ABOVE = 0;
   public static final int PLACEMENT_LEFT = 1;
   public static final int PLACEMENT_BELOW = 2;
   public static final int PLACEMENT_RIGHT = 3;
   public static final int PLACEMENT_LOCATION = 4;


   public CreateChartDialog(Frame owner) throws HeadlessException {
      super(owner, "Create Chart");
      this.init();
   }

   public CreateChartDialog(Dialog owner) throws HeadlessException {
      super(owner, "Create Chart");
      this.init();
   }

   protected boolean isOkAllowed() {
      return true;
   }

   protected void buildCenterPanel(Container center) {
      JPanel top = new JPanel(new TwoColumnLayout(5, 5));
      this.mChartTitle = new JTextField("Title");
      this.mDomainLabel = new JTextField("X Axis");
      this.mRangeLabel = new JTextField("Y Axis");
      this.mChartType = new JComboBox(this.mChartTypes);
      top.add(new JLabel("Chart Title:"));
      top.add(this.mChartTitle);
      top.add(new JLabel("Domain Label (x-axis):"));
      top.add(this.mDomainLabel);
      top.add(new JLabel("Range Label (y-axis):"));
      top.add(this.mRangeLabel);
      top.add(new JLabel("Chart Type"));
      top.add(this.mChartType);
      top.add(new JLabel("Placement of Chart:"));
      this.mPlacement = new JComboBox(this.mPlacementTypes);
      top.add(this.mPlacement);
      top.add(new JLabel("Specific Target Cell:"));
      this.mSpecificCell = new JTextField();
      top.add(this.mSpecificCell);
      top.add(new JLabel("Number of Rows For Chart:"));
      this.mRowCount = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
      top.add(this.mRowCount);
      top.add(new JLabel("Number of Columns For Chart:"));
      this.mColumnCount = new JSpinner(new SpinnerNumberModel(8, 1, 100, 1));
      top.add(this.mColumnCount);
      center.add(top, "North");
      this.mChartType.setSelectedIndex(2);
      this.mPlacement.setSelectedIndex(2);
      this.mSpecificCell.setEnabled(false);
      this.addListeners();
   }

   private void addListeners() {
      this.mPlacement.addActionListener(new CreateChartDialog$1(this));
   }

   public CPChart createChart(WorksheetDataset dataset) {
      String chartTitle = this.mChartTitle.getText();
      String categoryLabel = this.mDomainLabel.getText();
      String valueLabel = this.mRangeLabel.getText();
      CPChartFactory chartFactory = new CPChartFactory();
      chartFactory.setType(this.mChartType.getSelectedIndex());
      chartFactory.setTitle(chartTitle);
      chartFactory.setDomainLabel(categoryLabel);
      chartFactory.setRangeLabel(valueLabel);
      chartFactory.setDataset(dataset);
      return chartFactory.createChart();
   }

   public int getPlacementLocation() {
      return this.mPlacement.getSelectedIndex();
   }

   public String getSpecificLocation() {
      return this.mSpecificCell.getText();
   }

   public int getRowCount() {
      return ((Integer)this.mRowCount.getValue()).intValue();
   }

   public int getColumnCount() {
      return ((Integer)this.mColumnCount.getValue()).intValue();
   }

   // $FF: synthetic method
   static JComboBox accessMethod000(CreateChartDialog x0) {
      return x0.mPlacement;
   }

   // $FF: synthetic method
   static JTextField accessMethod100(CreateChartDialog x0) {
      return x0.mSpecificCell;
   }
}
