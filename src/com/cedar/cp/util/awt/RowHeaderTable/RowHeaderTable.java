// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt.RowHeaderTable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;

import com.cedar.cp.util.awt.StatusListener;
import com.cedar.cp.util.awt.TreeTable.TreeTable;
import com.cedar.cp.util.awt.TreeTable.TreeTableModel;

public class RowHeaderTable extends JScrollPane {

   private boolean mProcessingSelectionEvent;
   private TreeTable mRowTreeTable;
   private InternalTable mTable;
   private InternalTableModel mTableModel;
   protected RowHeaderTableModel mRowHeaderTableModel;
   protected TableCellRenderer mHeaderCellRenderer;
   protected StatusListener mStatusListener;
   protected TreeExpansionListener mTreeExpansionListener;
   protected TreeModelListener mTreeModelListener;
   protected static final int PREFERRED_MARGIN = 4;
   protected static final String FIRST_COL = "Nominals";
   private static final int HEADER_MARGIN = 5;


   public RowHeaderTable(RowHeaderTableModel model) {
      this.setModel(model);
      // Expand vertically listener
      ActionListener expandAction = new ActionListener() {
    	   public void actionPerformed(ActionEvent e) {
    	      expandVertically();
    	   }
    	};
      this.getInternalTable().registerKeyboardAction(expandAction, KeyStroke.getKeyStroke("alt V"), 0);
   }

   public StatusListener getStatusListener() {
      return this.mStatusListener;
   }

   public void setStatusListener(StatusListener statusListener) {
      this.mStatusListener = statusListener;
      if(this.mTable != null) {
         this.mTable.setStatusListener(statusListener);
      }

   }

   public void setModel(RowHeaderTableModel model) {
      this.mRowHeaderTableModel = model;
      if(this.mRowTreeTable == null) {
         this.mRowTreeTable = this.createRowTreeTable(this.mRowHeaderTableModel.getRowTreeTableModel());
         this.mRowTreeTable.setShowVerticalLines(true);
         this.mRowTreeTable.setShowHorizontalLines(true);
         this.mRowTreeTable.setIntercellSpacing(new Dimension(1, 1));
         
         // Change height of row
         this.mRowTreeTable.addPropertyChangeListener("rowHeight", new PropertyChangeListener() {
        	   public void propertyChange(PropertyChangeEvent evt) {
        	      if(mRowTreeTable != null) {
        	         mRowTreeTable.setRowHeight(mTable.getRowHeight());
        	      }
        	   }
        	});
         this.mTreeExpansionListener = new RowHeaderTable$3(this);
         this.mRowTreeTable.getTree().addTreeExpansionListener(this.mTreeExpansionListener);
      } else {
         this.mRowTreeTable.setModel(this.mRowHeaderTableModel.getRowTreeTableModel());
      }

      this.mTableModel = this.createInternalTableModel(model, this.mRowTreeTable);
      if(this.mTable == null) {
         this.mTable = this.createInternalTable(this.mTableModel);
         this.mTable.setAutoResizeMode(0);
         this.mTable.setCellSelectionEnabled(true);
         this.mTable.setStatusListener(this.mStatusListener);
         this.setViewportView(this.mTable);
         this.setRowHeaderView(new RowHeader(this.mRowTreeTable));
         this.keepSelectionsInSync();
         this.setCorner("LOWER_LEFT_CORNER", this.createLowerLeftCorner());
         this.setCorner("UPPER_LEFT_CORNER", this.createUpperLeftCorner());
         this.setCorner("UPPER_RIGHT_CORNER", this.createUpperRightCorner());
         this.mTable.getTableHeader().addMouseListener(new RowHeaderTable$4(this));
         
         // Right mouse button click
         this.mTable.addMouseListener(new MouseAdapter() {
        	   public void mouseClicked(MouseEvent e) {
        		   if(e.getClickCount() == 1 && SwingUtilities.isRightMouseButton(e)) {
        		         int row = mTable.rowAtPoint(e.getPoint());
        		         int col = mTable.columnAtPoint(e.getPoint());
        		         if(row != -1 && col != -1) {
        		            mTable.getSelectionModel().setSelectionInterval(row, row);
        		            mTable.getColumnModel().getSelectionModel().setSelectionInterval(col, col);
        		            showCellPopup(e, false, row, col);
        		         }
        		      }
        	   }
        	});
         
         this.mRowTreeTable.addMouseListener(new RowHeaderTable$6(this));
      } else {
         this.mTable.setModel(this.mTableModel);
      }

      this.setGradientDetails();
      this.mRowTreeTable.setRowHeight(this.mTable.getRowHeight());
      this.mTreeModelListener = new RowHeaderTable$7(this);
      this.mRowHeaderTableModel.getRowTreeTableModel().addTreeModelListener(this.mTreeModelListener);
   }

   public void setDisableTreeListeners(boolean disableTreeListeners) {
      if(disableTreeListeners) {
         this.mRowTreeTable.getTree().removeTreeExpansionListener(this.mTreeExpansionListener);
         this.mRowHeaderTableModel.getRowTreeTableModel().removeTreeModelListener(this.mTreeModelListener);
      } else {
         this.mRowTreeTable.getTree().addTreeExpansionListener(this.mTreeExpansionListener);
         this.mRowHeaderTableModel.getRowTreeTableModel().addTreeModelListener(this.mTreeModelListener);
      }

      this.mRowTreeTable.setDisableTreeListeners(disableTreeListeners);
      this.mTableModel.setDisableTreeListeners(disableTreeListeners);
   }

   protected void setGradientDetails() {
      new Random();
      Color gradientColor = this.mRowHeaderTableModel.getGradientColor();
      if(gradientColor == null) {
         this.mRowTreeTable.setBackground(this.mTable.getTableHeader().getBackground());
         this.mRowTreeTable.setOpaque(true);
         Color c = new Color(228, 244, 254);
         this.mTable.setBackground(c);
      } else {
         this.mRowTreeTable.setBackground(gradientColor);
         this.mRowTreeTable.setOpaque(false);
         this.mTable.setBackground(gradientColor);
      }

      Component c1 = this.getCorner("UPPER_LEFT_CORNER");
      if(c1 != null) {
         c1.setBackground(this.mTable.getTableHeader().getBackground());
      }

   }

   public void setDisplayFont(Font newFont) {
      Graphics2D graphics = (Graphics2D)this.getGraphics();
      this.mRowTreeTable.setDisplayFont(newFont);
      if(graphics != null) {
         FontMetrics fm = graphics.getFontMetrics(newFont);
         this.mTable.setRowHeight(fm.getHeight() + this.mTable.getRowMargin() + this.mTable.getIntercellSpacing().height);
      }

      this.mRowTreeTable.setRowHeight(this.mTable.getRowHeight());
      this.mTable.setFont(newFont);
      this.mTable.getTableHeader().setFont(newFont);
   }

   private void showCellPopup(MouseEvent e, boolean inRowHeader, int row, int col) {
      JPopupMenu popup = this.buildCellPopupMenu(inRowHeader, row, col);
      if(popup != null) {
         popup.show(e.getComponent(), e.getX(), e.getY());
      }

   }

   protected JPopupMenu buildCellPopupMenu(boolean inRowHeader, int row, int col) {
      return null;
   }

   protected void delayedFireTableDataChanged() {
      SwingUtilities.invokeLater(new RowHeaderTable$8(this));
   }

   protected void delayedFireTableRowChanged(int row) {
      if(row != -1) {
         SwingUtilities.invokeLater(new RowHeaderTable$9(this, row));
      }

   }

   protected int getHitColumn(Point p, boolean insideMargin) {
      if(this.mTable == null) {
         return -1;
      } else {
         JTableHeader header = this.mTable.getTableHeader();
         int col = header.columnAtPoint(p);
         if(col != -1) {
            Point leftP = new Point(p.x - 5, p.y);
            int leftCol = header.columnAtPoint(leftP);
            if(insideMargin && leftCol != col) {
               return col;
            } else {
               Point rightP = new Point(p.x + 5, p.y);
               int rightCol = header.columnAtPoint(rightP);
               return insideMargin && rightCol != col?col:(insideMargin?-1:(col == leftCol && col == rightCol?col:-1));
            }
         } else {
            return -1;
         }
      }
   }

   public void updateUI() {
      super.updateUI();
      this.getViewport().setBackground(Color.white);
   }

   public JTree getRowTree() {
      return this.mRowTreeTable.getTree();
   }

   public void setRootVisible(boolean visible) {
      this.mRowTreeTable.getTree().setRootVisible(visible);
   }

   public void setShowsRootHandles(boolean visible) {
      this.mRowTreeTable.getTree().setShowsRootHandles(visible);
   }

   public void setGridColor(Color c) {
      this.mRowTreeTable.setGridColor(c);
      this.mTable.setGridColor(c);
   }

   public void setIntercellSpacing(Dimension d) {
      this.mRowTreeTable.setIntercellSpacing(d);
      this.mTable.setIntercellSpacing(d);
   }

   public int getRowCount() {
      return this.mRowTreeTable.getRowCount();
   }

   public void expandRow(int r) {
      this.mRowTreeTable.getTree().expandRow(r);
   }

   public TreeNode getTreeNodeForRow(int r) {
      if(r < 0) {
         return null;
      } else {
         Object value = this.mRowTreeTable.getValueAt(r, 0);
         return value instanceof TreeNode?(TreeNode)value:null;
      }
   }

   public void setDefaultRenderer(Class clazz, TableCellRenderer renderer) {
      this.mRowTreeTable.setDefaultRenderer(clazz, renderer);
      this.mTable.setDefaultRenderer(clazz, renderer);
   }

   public void setTreeCellRenderer(TreeCellRenderer renderer) {
      this.mRowTreeTable.getTree().setCellRenderer(renderer);
   }

   public TreeTable getRowHeaderTreeTable() {
      return this.mRowTreeTable;
   }

   public InternalTable getInternalTable() {
      return this.mTable;
   }

   public void setSelectionMode(int newMode) {
      this.mRowTreeTable.setSelectionMode(newMode);
      this.mTable.setSelectionMode(newMode);
   }

   public void expandRows() {
      if(this.mRowTreeTable != null) {
         for(int row = 0; row < this.mRowTreeTable.getTree().getRowCount(); ++row) {
            if(!this.mRowTreeTable.getTree().isExpanded(row)) {
               this.mRowTreeTable.getTree().expandRow(row);
            }
         }
      }

   }

   protected void expandVertically() {
      int row = this.mTable.getSelectedRow();
      int column = this.mTable.getSelectedColumn();
      if(row != -1) {
         if(this.mRowTreeTable.getTree().isExpanded(row)) {
            this.mRowTreeTable.getTree().collapseRow(row);
         } else {
            this.mRowTreeTable.getTree().expandRow(row);
         }

         this.mTable.setRowSelectionInterval(row, row);
         this.mTable.setColumnSelectionInterval(column, column);
      }

   }

   protected Component createLowerLeftCorner() {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createBevelBorder(0));
      return panel;
   }

   protected Component createUpperLeftCorner() {
      JPanel topLeft = new JPanel(new BorderLayout());
      topLeft.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.gray));
      RowHeaderTableCorner cornerInfo = new RowHeaderTableCorner(this.mRowHeaderTableModel.getCornerName());
      topLeft.add(cornerInfo, "Center");
      return topLeft;
   }

   protected Component createUpperRightCorner() {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createBevelBorder(0));
      return panel;
   }

   protected TreeTable createRowTreeTable(TreeTableModel model) {
      TreeTable tree = new TreeTable(model);
      ToolTipManager.sharedInstance().registerComponent(tree);
      return tree;
   }

   protected InternalTableModel createInternalTableModel(RowHeaderTableModel model, TreeTable rowTreeTable) {
      return new InternalTableModel(model, rowTreeTable);
   }

   protected InternalTable createInternalTable(InternalTableModel model) {
      return new InternalTable(this, model);
   }

   protected void initTableColumnSizes(JTable table) {
      long s = System.currentTimeMillis();

      for(int e = 0; e < table.getColumnCount(); ++e) {
         this.initTableColumnSize(table, e);
      }

      long var6 = System.currentTimeMillis();
   }

   protected void initTableColumnSize(JTable table, int col) {
      TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
      TableColumn column = table.getColumnModel().getColumn(col);
      TableCellRenderer renderer = column.getCellRenderer();
      if(renderer == null) {
         renderer = headerRenderer;
      }

      int prefWidth = 2 + this.getColumnPreferredWidth(table, renderer, col);
      column.setPreferredWidth(prefWidth);
      column.sizeWidthToFit();
   }

   protected int getColumnPreferredWidth(JTable jTable, TableCellRenderer headerRenderer, int colIndex) {
      TableColumn column = jTable.getColumnModel().getColumn(colIndex);
      Component comp = headerRenderer.getTableCellRendererComponent((JTable)null, column.getHeaderValue(), false, false, 0, colIndex);
      int minWidth = 4 + comp.getPreferredSize().width + 4;

      for(int r = 0; r < jTable.getRowCount(); ++r) {
         int cellWidth = this.getCellPreferredWidth(jTable, r, colIndex);
         if(cellWidth > minWidth) {
            minWidth = cellWidth;
         }
      }

      if(column.getHeaderValue().equals("Nominals")) {
         minWidth += 25;
      }

      return minWidth;
   }

   protected int getCellPreferredWidth(JTable table, int row, int col) {
      int cellWidth = 0;
      Object cellValue = table.getValueAt(row, col);
      if(cellValue != null) {
         TableCellRenderer defaultRenderer = table.getDefaultRenderer(table.getModel().getColumnClass(col));
         if(defaultRenderer != null) {
            Component comp = defaultRenderer.getTableCellRendererComponent(table, cellValue, false, false, row, col);
            cellWidth = 4 + comp.getPreferredSize().width;
         }
      }

      return cellWidth;
   }

   public RowHeaderTableModel getModel() {
      return this.mRowHeaderTableModel;
   }

   public InternalTable getTable() {
      return this.mTable;
   }

   public void setHeaderCellRenderer(TableCellRenderer renderer) {
      this.mHeaderCellRenderer = renderer;
      this.mTable.createDefaultColumnsFromModel();
   }

   public TableCellRenderer getHeaderCellRenderer() {
      return this.mHeaderCellRenderer;
   }

   private void keepSelectionsInSync() {
      this.mRowTreeTable.getTree().addTreeSelectionListener(new RowHeaderTable$10(this));
      this.mTable.getSelectionModel().addListSelectionListener(new RowHeaderTable$11(this));
   }

   // $FF: synthetic method
   static TreeTable accessMethodmRowTreeTable(RowHeaderTable x0) {
      return x0.mRowTreeTable;
   }

   // $FF: synthetic method
   static InternalTable accessMethodmTable(RowHeaderTable x0) {
      return x0.mTable;
   }

   // $FF: synthetic method
   static void accessMethodshowCellPopup(RowHeaderTable x0, MouseEvent x1, boolean x2, int x3, int x4) {
      x0.showCellPopup(x1, x2, x3, x4);
   }

   // $FF: synthetic method
   static InternalTableModel accessMethodmTableModel(RowHeaderTable x0) {
      return x0.mTableModel;
   }

   // $FF: synthetic method
   static boolean accessMethodmProcessingSelectionEvent(RowHeaderTable x0) {
      return x0.mProcessingSelectionEvent;
   }

   // $FF: synthetic method
   static boolean accessMethodmProcessingSelectionEvent(RowHeaderTable x0, boolean x1) {
      return x0.mProcessingSelectionEvent = x1;
   }
}
