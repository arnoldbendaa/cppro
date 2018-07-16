// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.awt.CPUIManager;
import com.cedar.cp.util.awt.OkCancelDialog;
import com.cedar.cp.util.flatform.gui.WorksheetPanel;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog$1;
import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog$CategoryListModel;
import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog$CategoryListSelection;
import com.cedar.cp.util.flatform.model.parser.FlatformFunctionDialog$FunctionListSelection;
import com.cedar.cp.util.flatform.model.parser.WorksheetFormulaExecutor;
import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunction;
import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunctionCategory;
import com.cedar.cp.util.flatform.model.parser.xml.FlatformFunctionDetail;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class FlatformFunctionDialog extends OkCancelDialog {

   public static final String TITLE = "Insert Function";
   FlatformFunctionCategory mCurrentCategory = null;
   FlatformFunctionDetail mCurrentFunction = null;
   List<FlatformFunctionCategory> mCategories = null;
   JList mCategoryList = new JList();
   JList mFunctionList = new JList();
   JLabel mFunctionNameLabel = new JLabel();
   JLabel mFunctionDescLabel = new JLabel();
   WorksheetPanel mWorksheetPanel;
   Worksheet mWorksheet;
   int mRow;
   int mColumn;


   public FlatformFunctionDialog(Frame owner, WorksheetPanel worksheetPanel, Worksheet worksheet, int row, int column) {
      super(owner, "Insert Function");
      this.mWorksheetPanel = worksheetPanel;
      this.mWorksheet = worksheet;
      this.mRow = row;
      this.mColumn = column;
      this.init();
   }

   public FlatformFunctionDialog(Dialog owner, WorksheetPanel worksheetPanel, Worksheet worksheet, int row, int column) {
      super(owner, "Insert Function");
      this.mWorksheetPanel = worksheetPanel;
      this.mWorksheet = worksheet;
      this.mRow = row;
      this.mColumn = column;
      this.init();
   }

   public void showDialog() {
      this.setSize(new Dimension(520, 400));
      this.setVisible(true);
   }

   private void jbInit() throws Exception {
      JPanel masterPanel = new JPanel(new BorderLayout());
      masterPanel.add(this.buildSplitPanel(), "North");
      masterPanel.add(this.buildFunctionDescPanel(), "Center");
      this.getContentPane().add(masterPanel, "Center");
      this.pack();
      this.buildData();
   }

   private void buildData() {
      WorksheetFormulaExecutor formulaExecutor = new WorksheetFormulaExecutor();
      FlatformFunction flatFormFunction = formulaExecutor.getFlatFormFunction();
      this.mCategories = flatFormFunction.getFunctionCategories();
      FlatformFunctionCategory allCat = new FlatformFunctionCategory();
      allCat.setName("All");
      ArrayList funcList = new ArrayList();

      for(int catListModel = 0; catListModel < this.mCategories.size(); ++catListModel) {
         FlatformFunctionCategory cat = (FlatformFunctionCategory)this.mCategories.get(catListModel);
         if(cat.getFunctionList() != null) {
            funcList.addAll(cat.getFunctionList());
         }
      }

      if(funcList.size() > 0) {
         Collections.sort(funcList);
      }

      allCat.setFunctionList(funcList);
      this.mCategories.add(0, allCat);
      FlatformFunctionDialog$CategoryListModel var7 = new FlatformFunctionDialog$CategoryListModel(this, this.mCategories);
      this.mCategoryList.setModel(var7);
      this.mCategoryList.setSelectedIndex(0);
   }

   private JPanel buildFunctionDescPanel() {
      JPanel p = new JPanel();
      p.setLayout(new BoxLayout(p, 3));
      p.add(this.mFunctionNameLabel);
      Font f = this.mFunctionNameLabel.getFont().deriveFont(1);
      this.mFunctionNameLabel.setFont(f);
      p.add(this.mFunctionDescLabel);
      return p;
   }

   private JSplitPane buildSplitPanel() {
      JSplitPane splitPane = new JSplitPane(1, this.buildFunctionCategory(), this.buildFunctionName());
      splitPane.setPreferredSize(new Dimension(400, 260));
      splitPane.setDividerLocation(200);
      return splitPane;
   }

   private JPanel buildFunctionCategory() {
      JPanel p = new JPanel(new BorderLayout());
      p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Function category"));
      p.add(new JScrollPane(this.mCategoryList), "Center");
      this.mCategoryList.setSelectionMode(0);
      this.mCategoryList.addListSelectionListener(new FlatformFunctionDialog$CategoryListSelection(this, (FlatformFunctionDialog$1)null));
      return p;
   }

   private JPanel buildFunctionName() {
      JPanel p = new JPanel(new BorderLayout());
      p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Function name"));
      p.add(new JScrollPane(this.mFunctionList), "Center");
      this.mFunctionList.setSelectionMode(0);
      this.mFunctionList.addListSelectionListener(new FlatformFunctionDialog$FunctionListSelection(this, (FlatformFunctionDialog$1)null));
      return p;
   }

   protected void buildCenterPanel(Container center) {
      try {
         this.jbInit();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   protected boolean isOkAllowed() {
      return true;
   }

   protected void handleOk() {
      String func = "=" + this.mCurrentFunction.getName() + "()";
      Cell cell = new Cell();
      cell.setColumn(this.mColumn);
      cell.setRow(this.mRow);
      cell.setText(func);
      this.mWorksheet.addCell(cell);
      this.mWorksheetPanel.getTable().setValueAt(cell, this.mRow, this.mColumn);
      this.mWorksheetPanel.getTable().editCellAt(this.mRow, this.mColumn);
      super.handleOk();
   }

   public static void main(String[] argv) {
      CPUIManager.setLookAndFeel();

      try {
         SwingUtilities.invokeLater(new FlatformFunctionDialog$1());
      } catch (Throwable var2) {
         ;
      }

   }
}
