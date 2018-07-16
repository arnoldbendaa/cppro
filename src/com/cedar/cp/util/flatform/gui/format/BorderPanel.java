// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui.format;

import com.cedar.cp.util.DefaultValueMapping;
import com.cedar.cp.util.ValueMapping;
import com.cedar.cp.util.awt.LinesBorder;
import com.cedar.cp.util.awt.VerticalFlowLayout;
import com.cedar.cp.util.flatform.gui.format.BorderPanel$1;
import com.cedar.cp.util.flatform.gui.format.BorderPanel$2;
import com.cedar.cp.util.flatform.gui.format.BorderPanel$3;
import com.cedar.cp.util.flatform.gui.format.BorderPanel$4;
import com.cedar.cp.util.flatform.gui.format.BorderPanel$5;
import com.cedar.cp.util.flatform.gui.format.BorderPanel$6;
import com.cedar.cp.util.flatform.gui.format.BorderPanel$7;
import com.cedar.cp.util.flatform.gui.format.BorderPanel$8;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Collection;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class BorderPanel extends JPanel {

   private LinesBorder mLinesBorder;
   private JPanel mCenterPanel;
   private JComboBox mBottomSize;
   private JComboBox mEastSize;
   private JComboBox mWestSize;
   private JComboBox mTopSize;
   private boolean mSelectionUpdated;
   private ValueMapping sHorizontalBorderValuesMapping = new DefaultValueMapping(new String[]{"=", "None", "1", "2", "3", "4", "5"}, new Object[]{Integer.valueOf(-1), Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5)});
   private ValueMapping sVertcialBorderValuesMapping = new DefaultValueMapping(new String[]{"None", "1", "2", "3", "4", "5"}, new Object[]{Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5)});


   public BorderPanel() {
      super(new BorderLayout(5, 5));
      this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      this.mLinesBorder = new LinesBorder(Color.black, 0);
      this.mCenterPanel = new JPanel();
      this.mCenterPanel.setBackground(Color.white);
      this.mCenterPanel.setBorder(this.mLinesBorder);
      this.add(this.mCenterPanel, "Center");
      JPanel top = new JPanel(new FlowLayout(1, 5, 5));
      JButton topColor = new JButton("Color");
      topColor.addActionListener(new BorderPanel$1(this));
      top.add(topColor);
      this.mTopSize = new JComboBox(this.sHorizontalBorderValuesMapping.getLiterals().toArray(new String[0]));
      this.mTopSize.addActionListener(new BorderPanel$2(this));
      top.add(this.mTopSize);
      this.add(top, "North");
      JPanel west = new JPanel(new VerticalFlowLayout(1, 5, 5));
      JButton westColor = new JButton("Color");
      westColor.addActionListener(new BorderPanel$3(this));
      west.add(westColor);
      this.mWestSize = new JComboBox(this.sVertcialBorderValuesMapping.getLiterals().toArray(new String[0]));
      this.mWestSize.addActionListener(new BorderPanel$4(this));
      west.add(this.mWestSize);
      this.add(west, "West");
      JPanel east = new JPanel(new VerticalFlowLayout(1, 5, 5));
      JButton eastColor = new JButton("Color");
      eastColor.addActionListener(new BorderPanel$5(this));
      east.add(eastColor);
      this.mEastSize = new JComboBox(this.sVertcialBorderValuesMapping.getLiterals().toArray(new String[0]));
      this.mEastSize.addActionListener(new BorderPanel$6(this));
      east.add(this.mEastSize);
      this.add(east, "East");
      JPanel bottom = new JPanel(new FlowLayout(1, 5, 5));
      JButton bottomColor = new JButton("Color");
      bottomColor.addActionListener(new BorderPanel$7(this));
      bottom.add(bottomColor);
      this.mBottomSize = new JComboBox(this.sHorizontalBorderValuesMapping.getLiterals().toArray(new String[0]));
      this.mBottomSize.addActionListener(new BorderPanel$8(this));
      bottom.add(this.mBottomSize);
      this.add(bottom, "South");
   }

   public void populateFromCell(CellFormat format, Map<String, Collection<CellFormatEntry>> activeFormats) {
      if(CellFormatEntry.hasMultipleFormats(activeFormats, "border")) {
         this.mTopSize.setSelectedIndex(-1);
         this.mBottomSize.setSelectedIndex(-1);
         this.mWestSize.setSelectedIndex(-1);
         this.mEastSize.setSelectedIndex(-1);
      } else if(format.getBorder() != null) {
         LinesBorder border = (LinesBorder)format.getBorder();
         this.mLinesBorder.setThickness(border.getNorthThickness(), border.getWestThickness(), border.getSouthThickness(), border.getEastThickness());
         this.mLinesBorder.setColor(border.getNorthColor(), border.getWestColor(), border.getSouthColor(), border.getEastColor());
         this.mTopSize.setSelectedItem(this.sHorizontalBorderValuesMapping.getLiteral(Integer.valueOf(border.getNorthThickness())));
         this.mWestSize.setSelectedItem(this.sVertcialBorderValuesMapping.getLiteral(Integer.valueOf(border.getWestThickness())));
         this.mEastSize.setSelectedItem(this.sVertcialBorderValuesMapping.getLiteral(Integer.valueOf(border.getEastThickness())));
         this.mBottomSize.setSelectedItem(this.sHorizontalBorderValuesMapping.getLiteral(Integer.valueOf(border.getSouthThickness())));
      } else {
         this.mTopSize.setSelectedItem(this.sHorizontalBorderValuesMapping.getLiteral(Integer.valueOf(0)));
         this.mWestSize.setSelectedItem(this.sVertcialBorderValuesMapping.getLiteral(Integer.valueOf(0)));
         this.mEastSize.setSelectedItem(this.sVertcialBorderValuesMapping.getLiteral(Integer.valueOf(0)));
         this.mBottomSize.setSelectedItem(this.sHorizontalBorderValuesMapping.getLiteral(Integer.valueOf(0)));
      }

      this.mSelectionUpdated = false;
   }

   public void updateCell(CellFormat format) {
      if(this.mSelectionUpdated) {
         format.setBorder(this.mLinesBorder);
      }

   }

   // $FF: synthetic method
   static LinesBorder accessMethod000(BorderPanel x0) {
      return x0.mLinesBorder;
   }

   // $FF: synthetic method
   static boolean accessMethod102(BorderPanel x0, boolean x1) {
      return x0.mSelectionUpdated = x1;
   }

   // $FF: synthetic method
   static JComboBox accessMethod200(BorderPanel x0) {
      return x0.mTopSize;
   }

   // $FF: synthetic method
   static ValueMapping accessMethod300(BorderPanel x0) {
      return x0.sHorizontalBorderValuesMapping;
   }

   // $FF: synthetic method
   static JComboBox accessMethod400(BorderPanel x0) {
      return x0.mWestSize;
   }

   // $FF: synthetic method
   static ValueMapping accessMethod500(BorderPanel x0) {
      return x0.sVertcialBorderValuesMapping;
   }

   // $FF: synthetic method
   static JComboBox accessMethod600(BorderPanel x0) {
      return x0.mEastSize;
   }

   // $FF: synthetic method
   static JComboBox accessMethod700(BorderPanel x0) {
      return x0.mBottomSize;
   }
}
