// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JToolTip;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.UIResource;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartPanel;

import com.cedar.cp.util.RTree;
import com.cedar.cp.util.awt.JMultiLineToolTip;
import com.cedar.cp.util.awt.table.MultiSpanCellTable;
import com.cedar.cp.util.flatform.model.CPChart;
import com.cedar.cp.util.flatform.model.CPImage;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.CellSelectionListener;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.WorksheetListener;
import com.cedar.cp.util.flatform.model.event.WorksheetColumnEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetFormatEvent;
import com.cedar.cp.util.flatform.model.event.WorksheetRowEvent;

public class WorksheetTable extends MultiSpanCellTable implements WorksheetListener {
   private static final long serialVersionUID = 624322318337821280L;
   
   private int mViewingLayer = 0;
   private WorksheetRenderer mWorksheetRenderer;
   private WorksheetEditor mWorksheetEditor;
   private boolean mShowHeaders = true;


   public void setViewLayer(int layer) {
      this.mViewingLayer = layer;
      this.mWorksheetRenderer.setViewLayer(this.mViewingLayer);
      this.mWorksheetEditor.setViewLayer(this.mViewingLayer);
      Worksheet sheet = this.getWorksheet();
      if(sheet != null) {
         sheet.setViewLayer(this.mViewingLayer);
      }

      this.repaint();
   }

   public int getViewLayer() {
      return this.mViewingLayer;
   }

   public WorksheetTable(SparseTableModel model, boolean showHeaders) {
      super(model);
      this.mShowHeaders = showHeaders;
      this.setGridColor(new Color(204, 204, 204));
      this.setShowVerticalLines(true);
      this.setShowHorizontalLines(true);
      this.setCellSelectionEnabled(true);
      this.mWorksheetRenderer = new WorksheetRenderer(this.getWorksheet().getWorkbook().isDesignMode());
      this.setDefaultRenderer(Cell.class, this.mWorksheetRenderer);
      this.mWorksheetEditor = new WorksheetEditor(this);
      this.setDefaultEditor(Cell.class, this.mWorksheetEditor);
      
      ActionListener pasteAction = new ActionListener() {
    	   public void actionPerformed(ActionEvent e) {
    	      performPasteAction();
    	   }
    	};
      this.registerKeyboardAction(pasteAction, KeyStroke.getKeyStroke("ctrl V"), 0);
      
	  ActionListener copyAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performCopyAction();
			}
		};
      this.registerKeyboardAction(copyAction, KeyStroke.getKeyStroke("ctrl C"), 0);
      
      ListSelectionListener cellSelectionListener = new ListSelectionListener() {
    	   public void valueChanged(ListSelectionEvent e) {
    	      if(!e.getValueIsAdjusting()) {
    	         int selectedRow = getSelectedRow();
    	         int selectedColumn = getSelectedColumn();
    	         fireCellSelectionChanged(selectedRow, selectedColumn);
    	      }
    	   }
    	};
      this.getTableHeader().getColumnModel().getSelectionModel().addListSelectionListener(cellSelectionListener);
      this.getSelectionModel().addListSelectionListener(cellSelectionListener);
      model.getWorksheet().addWorksheetListener(this);
   }

   protected void configureEnclosingScrollPane() {
      Container p = this.getParent();
      if(p instanceof JViewport) {
         Container gp = p.getParent();
         if(gp instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane)gp;
            JViewport viewport = scrollPane.getViewport();
            if(viewport == null || viewport.getView() != this) {
               return;
            }

            if(this.mShowHeaders) {
               scrollPane.setColumnHeaderView(this.getTableHeader());
            }

            Border border = scrollPane.getBorder();
            if(border == null || border instanceof UIResource) {
               scrollPane.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
            }
         }
      }

   }

   public Worksheet getWorksheet() {
      Worksheet sheet = null;
      TableModel model = this.getModel();
      if(model instanceof SparseTableModel) {
         SparseTableModel stm = (SparseTableModel)model;
         sheet = stm.getWorksheet();
      }

      return sheet;
   }

   public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
      Rectangle sRect = super.getCellRect(row, column, includeSpacing);
      if(row >= 0 && column >= 0 && this.getRowCount() > row && this.getColumnCount() > column) {
         int[] n = new int[]{1, 1};
         RTree.Rect mergeRect = this.getWorksheet().queryCellMergeRect(row, column);
         if(mergeRect != null) {
            n[0] = mergeRect.mEndRow - mergeRect.getStartRow() + 1;
            n[1] = mergeRect.mEndColumn - mergeRect.getStartColumn() + 1;
            row = mergeRect.mStartRow;
            column = mergeRect.mStartColumn;
         }

         int index = 0;
         Rectangle cellFrame = new Rectangle();
         int aCellHeight = this.rowHeight + this.rowMargin;
         cellFrame.y = row * aCellHeight;
         cellFrame.height = n[0] * aCellHeight;

         Enumeration enumeration;
         for(enumeration = this.getColumnModel().getColumns(); enumeration.hasMoreElements(); ++index) {
            TableColumn spacing = (TableColumn)enumeration.nextElement();
            cellFrame.width = spacing.getWidth();
            if(index == column) {
               break;
            }

            cellFrame.x += cellFrame.width;
         }

         for(int var13 = 0; var13 < n[1] - 1 && enumeration.hasMoreElements(); ++var13) {
            TableColumn aColumn = (TableColumn)enumeration.nextElement();
            cellFrame.width += aColumn.getWidth();
         }

         if(!includeSpacing) {
            Dimension var14 = this.getIntercellSpacing();
            cellFrame.setBounds(cellFrame.x + var14.width / 2, cellFrame.y + var14.height / 2, cellFrame.width - var14.width, cellFrame.height - var14.height);
         }

         return cellFrame;
      } else {
         return sRect;
      }
   }

   private int[] rowColumnAtPoint(Point point) {
      int[] retValue = new int[]{-1, -1};
      int row = point.y / (this.rowHeight + this.rowMargin);
      if(row >= 0 && this.getRowCount() > row) {
         int column = this.getColumnModel().getColumnIndexAtX(point.x);
         RTree.Rect mergeRect = this.getWorksheet().queryCellMergeRect(row, column);
         if(mergeRect == null) {
            retValue[0] = row;
            retValue[1] = column;
         } else {
            retValue[0] = mergeRect.mStartRow;
            retValue[1] = mergeRect.mStartColumn;
         }

         return retValue;
      } else {
         return retValue;
      }
   }

   public int rowAtPoint(Point point) {
      return this.rowColumnAtPoint(point)[0];
   }

   public int columnAtPoint(Point point) {
      return this.rowColumnAtPoint(point)[1];
   }

   public void mergeSelectedCells() {
      int[] columns = this.getSelectedColumns();
      int[] rows = this.getSelectedRows();
      if(rows != null && columns != null) {
         if(rows.length >= 1 && columns.length >= 1) {
            int rowSpan = rows.length;
            int columnSpan = columns.length;
            int startRow = rows[0];
            int startColumn = columns[0];
            this.getWorksheet().mergeCells(startRow, startColumn, startRow + rowSpan - 1, startColumn + columnSpan - 1);
            this.revalidate();
         }
      }
   }

   public void splitSelectedCells() {
      int[] columns = this.getSelectedColumns();
      int[] rows = this.getSelectedRows();
      if(rows != null && columns != null) {
         if(rows.length >= 1 && columns.length >= 1) {
            int startRow = rows[0];
            int startColumn = columns[0];
            this.getWorksheet().splitCells(startRow, startColumn, startRow + rows.length - 1, startColumn + columns.length - 1);
            this.revalidate();
         }
      }
   }

   public void insertColumn(int columnIndex) {
      SparseTableModel sparseModel = (SparseTableModel)this.getModel();
      sparseModel.insertColumn(columnIndex);
   }

   private void postInsertColumn(int columnIndex) {
      int oldIndex = columnIndex + 1;
      JTableHeader header = this.getTableHeader();
      TableColumnModel tcm = header.getColumnModel();

      for(int col = this.getColumnCount() - 1; col > oldIndex; --col) {
         TableColumn c = tcm.getColumn(col);
         c.setPreferredWidth(tcm.getColumn(col - 1).getPreferredWidth());
      }

   }

   public void deleteColumn(int columnIndex) {
      SparseTableModel sparseModel = (SparseTableModel)this.getModel();
      sparseModel.deleteColumn(columnIndex);
   }

   private void preDeleteColumn(int columnIndex) {
      JTableHeader header = this.getTableHeader();
      TableColumnModel tcm = header.getColumnModel();

      for(int col = columnIndex; col < this.getColumnCount() - 2; ++col) {
         TableColumn c = tcm.getColumn(col);
         c.setPreferredWidth(tcm.getColumn(col + 1).getPreferredWidth());
      }

   }

   public void insertRow(int rowIndex) {
      SparseTableModel sparseModel = (SparseTableModel)this.getModel();
      sparseModel.insertRow(rowIndex);
   }

   private void postInsertRow(int rowIndex) {}

   public void deleteRow(int rowIndex) {
      SparseTableModel sparseModel = (SparseTableModel)this.getModel();
      sparseModel.deleteRow(rowIndex);
   }

   private void preDeleteRow(int rowIndex) {}

   public void worksheetStructureChange(WorksheetEvent e) {
      if(e instanceof WorksheetColumnEvent) {
         WorksheetColumnEvent wre = (WorksheetColumnEvent)e;
         if(wre.getType() == 1) {
            this.preDeleteColumn(wre.getColumn());
         }
      } else if(e instanceof WorksheetRowEvent) {
         WorksheetRowEvent wre1 = (WorksheetRowEvent)e;
         if(wre1.getType() == 1) {
            this.preDeleteRow(wre1.getRow());
         }
      }

   }

   public void worksheetStructureChanged(WorksheetEvent e) {
      if(e instanceof WorksheetColumnEvent) {
         WorksheetColumnEvent wre = (WorksheetColumnEvent)e;
         if(wre.getType() == 0) {
            this.postInsertColumn(wre.getColumn());
         }
      } else if(e instanceof WorksheetRowEvent) {
         WorksheetRowEvent wre1 = (WorksheetRowEvent)e;
         if(wre1.getType() == 0) {
            this.postInsertRow(wre1.getRow());
         }
      }

   }

   public void worksheetFormatChanged(WorksheetFormatEvent e) {}

   public TableCellRenderer getCellRenderer(int row, int column) {
      Object value = this.getValueAt(row, column);
      if(value instanceof Cell) {
         TableCellRenderer renderer = this.getWrappedObjectRenderer((Cell)value);
         return renderer != null?renderer:this.getDefaultRenderer(value.getClass());
      } else {
         return super.getCellRenderer(row, column);
      }
   }

   private TableCellRenderer getWrappedObjectRenderer(Cell cell) {
      Object value = cell.getValue();
      if(value == null) {
         return null;
      } else {
    	  TableCellRenderer renderer;
         if(value instanceof CPChart) {
            renderer = (TableCellRenderer)this.mFixedRenderers.get(value);
            if(renderer == null && this.getModel() instanceof SparseTableModel) {
               WrappedChartPanel imageLabel1 = new WrappedChartPanel(this, ((CPChart)value).getChart(), cell);
               renderer = new WrappedJComponentTableRenderer(imageLabel1);
               this.mFixedRenderers.put(value, renderer);
            }

            return (TableCellRenderer)renderer;
         } else if(value instanceof CPImage) {
            renderer = (TableCellRenderer)this.mFixedRenderers.get(value);
            if(renderer == null && this.getModel() instanceof SparseTableModel) {
               WrappedImage imageLabel = new WrappedImage((CPImage)value);
               renderer = new WrappedJComponentTableRenderer(imageLabel);
               this.mFixedRenderers.put(value, renderer);
            }

            return (TableCellRenderer)renderer;
         } else {
            return null;
         }
      }
   }

   public ChartPanel getChartPanel(CPChart chart) {
      ChartPanel panel = null;
      TableCellRenderer renderer = (TableCellRenderer)this.mFixedRenderers.get(chart);
      if(renderer instanceof WrappedJComponentTableRenderer) {
         WrappedJComponentTableRenderer wrapper = (WrappedJComponentTableRenderer)renderer;
         JComponent comp = wrapper.getWrappedComponenet();
         if(comp instanceof ChartPanel) {
            panel = (ChartPanel)comp;
         }
      }

      return panel;
   }

   public void performCutAction() {
      Worksheet worksheet = this.getWorksheet();
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      if(worksheet != null && clipboard != null) {
         int startRow = this.getSelectedRow();
         int startColumn = this.getSelectedColumn();
         worksheet.cutToClipboard(clipboard, startRow, startColumn, startRow + this.getSelectedRowCount() - 1, startColumn + this.getSelectedColumnCount() - 1);
      }

   }

   public void performCopyAction() {
      Worksheet worksheet = this.getWorksheet();
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      if(worksheet != null && clipboard != null) {
         int startRow = this.getSelectedRow();
         int startColumn = this.getSelectedColumn();
         worksheet.copyToClipboard(clipboard, startRow, startColumn, startRow + this.getSelectedRowCount() - 1, startColumn + this.getSelectedColumnCount() - 1);
      }

   }

   public void performPasteAction() {
      Worksheet worksheet = this.getWorksheet();
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      if(worksheet != null && clipboard != null) {
         int startRow = this.getSelectedRow();
         int startColumn = this.getSelectedColumn();
         worksheet.pasteFromClipboard(clipboard, startRow, startColumn, startRow + this.getSelectedRowCount() - 1, startColumn + this.getSelectedColumnCount() - 1);
      }

   }

   public JToolTip createToolTip() {
      return new JMultiLineToolTip();
   }

   public void addCellSelectionListener(CellSelectionListener l) {
      this.listenerList.add(CellSelectionListener.class, l);
   }

   public void removeCellSelectionListenerListener(CellSelectionListener l) {
      this.listenerList.remove(CellSelectionListener.class, l);
   }

   public void fireCellSelectionChanged(int row, int column) {
      this.fireCellSelectionChanged(new CellSelectionEvent(this, row, column, this.getRowCount(), this.getColumnCount()));
   }

   public void fireCellSelectionChanged(CellSelectionEvent e) {
      Object[] listeners = this.listenerList.getListenerList();

      for(int i = listeners.length - 2; i >= 0; i -= 2) {
         if(listeners[i] == CellSelectionListener.class) {
            ((CellSelectionListener)listeners[i + 1]).cellSelectionChanged(e);
         }
      }

   }
}
