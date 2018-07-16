// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicArrowButton;

public class ReorderWorksheetsDialogView extends JPanel {

   JTable mTable = new JTable();
   JScrollPane mScrollPane;
   BasicArrowButton mNorth;
   BasicArrowButton mSouth;


   public ReorderWorksheetsDialogView() {
      this.mScrollPane = new JScrollPane(this.mTable);
      this.mNorth = new BasicArrowButton(1);
      this.mSouth = new BasicArrowButton(5);
      this.setLayout(new BorderLayout(5, 5));
      this.mScrollPane.setPreferredSize(new Dimension(130, 400));
      this.add(this.mScrollPane, "Center");
      this.add(this.createButtonsPanel(), "East");
   }

   private JPanel createButtonsPanel() {
      JPanel p = new JPanel(new GridLayout(2, 1));
      p.add(this.mNorth);
      p.add(this.mSouth);
      return p;
   }
}
