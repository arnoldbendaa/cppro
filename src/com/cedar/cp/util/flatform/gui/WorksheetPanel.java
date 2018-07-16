// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jfree.chart.ChartPanel;

import com.cedar.cp.util.awt.LinesBorder;
import com.cedar.cp.util.awt.OkCancelDialog;
import com.cedar.cp.util.flatform.gui.format.CellFormatDialog;
import com.cedar.cp.util.flatform.model.CPChart;
import com.cedar.cp.util.flatform.model.CPImage;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.CellSelectionListener;
import com.cedar.cp.util.flatform.model.ColumnFormat;
import com.cedar.cp.util.flatform.model.OutlineHead;
import com.cedar.cp.util.flatform.model.OutlineHeadStatusChangeListener;
import com.cedar.cp.util.flatform.model.OutlineLevelModelChangeEvent;
import com.cedar.cp.util.flatform.model.OutlineLevelModelStatusChangeListener;
import com.cedar.cp.util.flatform.model.OutlineHead.OutlineState;
import com.cedar.cp.util.flatform.model.OutlineLevelModelChangeEvent.ChangeType;
import com.cedar.cp.util.flatform.model.OutlineLevelModel;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.WorksheetDataset;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.CellFormatEntry;
import com.cedar.cp.util.flatform.model.format.CompositeCellFormat;
import com.cedar.cp.util.flatform.model.format.DefaultCellFormat;
import com.cedar.cp.util.xmlreport.Purpose;

public class WorksheetPanel extends JPanel implements CellFactory, CellSelectionListener {
	private static final long serialVersionUID = -8127263093472250703L;

	/*      */private static enum VertBorderType
	/*      */{
		/* 867 */TOP, MIDDLE, BOTTOM, TOP_BOTTOM;
		/*      */
	}

	/*      */
	/*      */private static enum HorizBorderType
	/*      */{
		/* 860 */LEFT, CENTRE, RIGHT, LEFT_RIGHT;
		/*      */
	}

	private SparseTableModel mModel;
	private WorksheetTable mTable;
	private JList mRowHeader;
	private Font mCurrentFont = null;
	private JLabel mCellAddress;
	private JLabel mFeedback;
	private WorksheetPopups mPopupActions;
	private CellSelectionListener mChainedSelectionListener;
	private ActionListener mCellPickerActionListener;
	// private TopLeftPanel mTopLeft;
	private JPanel mTopLeft;
	private static final DefaultCellFormat sDefaultCellFormat = new DefaultCellFormat();

	// // Outline level model
	// private OutlineLevelModel outlineLevelModel = new OutlineLevelModel();
	// // Outline level panel
	// private OutlineLevelPanel mOutlineLevelPanel;

	// public WorksheetPanel(Worksheet worksheet, CellSelectionListener chainedSelectionlistener, Boolean showHeaders) {
	// initWorksheetPanel(worksheet, chainedSelectionlistener, showHeaders);
	// }
	//
	// private void initWorksheetPanel(Worksheet worksheet,
	// CellSelectionListener chainedSelectionlistener, Boolean showHeaders) {
	// if (showHeaders == null)
	// showHeaders = false;
	//
	// this.mOutlineLevelPanel = new OutlineLevelPanel(outlineLevelModel);
	// this.mTopLeft = new TopLeftPanel(outlineLevelModel);
	// this.setLayout(new BorderLayout());
	// this.buildControls(worksheet, showHeaders);
	//
	// // Example how to use OutlineLevelModelStatusChangeListener
	// outlineLevelModel
	// .addOutlineLevelModelStatusChangeListener(new OutlineLevelModelStatusChangeListener() {
	// @Override
	// public void stateChanged(ChangeEvent e) {
	// OutlineLevelModelChangeEvent event = (OutlineLevelModelChangeEvent) e;
	// OutlineHead head = event.getHead();
	// ChangeType changeType = event.getChangeType();
	// System.out
	// .println("OutlineLevelModelStatusChangeListener - "
	// + "Model has changed its status: "
	// + changeType + " " + head);
	// mOutlineLevelPanel.setPreferredSize();
	// mOutlineLevelPanel.repaint();
	// mTopLeft.buildComponents();
	// mTopLeft.repaint();
	// }
	// });
	//
	// // Example how to use OutlineHeadStatusChangeListener
	// outlineLevelModel
	// .addOutlineHeadStatusChangeListener(new OutlineHeadStatusChangeListener(worksheet) {
	// @Override
	// public void stateChanged(ChangeEvent e) {
	// OutlineHead head = (OutlineHead) e.getSource();
	// System.out.println("OutlineHeadStatusChangeListener - "
	// + "Head has changed its status: " + head);
	//
	// // Collapse/Expand rows
	// if(head.getOutlineState()==OutlineState.COLLAPSED){
	// ((Worksheet)getParam(0)).collapseRows(1, 5);
	// } else {
	// ((Worksheet)getParam(0)).expandRows(1, 5);
	// }
	// mTable.repaint();
	//
	// // Repaint the OutlineLevelPanel
	// mOutlineLevelPanel.repaint();
	// }
	// });
	//
	//
	// OutlineHead headL1R1E10 = new OutlineHead(1, 10, OutlineState.EXPANDED);
	// OutlineHead headL2R5E15 = new OutlineHead(5, 10, OutlineState.EXPANDED);
	// OutlineHead headL3R8E20 = new OutlineHead(8, 10, OutlineState.EXPANDED);
	// OutlineHead headL3R9E10 = new OutlineHead(9, 10, OutlineState.EXPANDED);
	// OutlineHead headL3R10E10 = new OutlineHead(10, 10,OutlineState.EXPANDED);
	//
	// outlineLevelModel.addHead(headL1R1E10, null);
	// // outlineLevelModel.addHead(headL2R5E15, headL1R1E10);
	// // outlineLevelModel.addHead(headL3R8E20, headL2R5E15);
	// // outlineLevelModel.addHead(headL3R9E10, headL3R8E20);
	// // outlineLevelModel.addHead(headL3R10E10, headL3R9E10);
	//
	// this.mPopupActions = new WorksheetPopups(this);
	// TableColumnModel tcm = this.mTable.getTableHeader().getColumnModel();
	// Iterator boldAction = worksheet.getColumnFormats().iterator();
	//
	// while (boldAction.hasNext()) {
	// ColumnFormat italicAction = (ColumnFormat) boldAction.next();
	// if (italicAction.getColumn() < tcm.getColumnCount()) {
	// TableColumn underlineAction = tcm.getColumn(italicAction
	// .getColumn());
	// underlineAction.setPreferredWidth(italicAction.getWidth());
	// }
	// }
	//
	// this.mChainedSelectionListener = chainedSelectionlistener;
	// this.mTable.addCellSelectionListener(this);
	// WorksheetPanel$1 boldAction1 = new WorksheetPanel$1(this);
	// this.mTable.registerKeyboardAction(boldAction1,
	// KeyStroke.getKeyStroke("ctrl B"), 0);
	// WorksheetPanel$2 italicAction1 = new WorksheetPanel$2(this);
	// this.mTable.registerKeyboardAction(italicAction1,
	// KeyStroke.getKeyStroke("ctrl I"), 0);
	// WorksheetPanel$3 underlineAction1 = new WorksheetPanel$3(this);
	// this.mTable.registerKeyboardAction(underlineAction1,
	// KeyStroke.getKeyStroke("ctrl U"), 0);
	// this.mTable.addMouseListener(new WorksheetPanel$4(this));
	// this.mTable.getTableHeader().addMouseListener(
	// new WorksheetPanel$5(this));
	// this.mTopLeft.addMouseListener(new WorksheetPanel$6(this));
	// this.mRowHeader.addMouseListener(new WorksheetPanel$7(this));
	// }

	public WorksheetPanel(Worksheet worksheet, CellSelectionListener chainedSelectionlistener, boolean showHeaders) {
		this.setLayout(new BorderLayout());
		this.buildControls(worksheet, showHeaders);
		this.mPopupActions = new WorksheetPopups(this);
		TableColumnModel tcm = this.mTable.getTableHeader().getColumnModel();
		Iterator boldAction = worksheet.getColumnFormats().iterator();

		while (boldAction.hasNext()) {
			ColumnFormat italicAction = (ColumnFormat) boldAction.next();
			if (italicAction.getColumn() < tcm.getColumnCount()) {
				TableColumn underlineAction = tcm.getColumn(italicAction.getColumn());
				underlineAction.setPreferredWidth(italicAction.getWidth());
			}
		}

		this.mChainedSelectionListener = chainedSelectionlistener;
		this.mTable.addCellSelectionListener(this);
		WorksheetPanel$1 boldAction1 = new WorksheetPanel$1(this);
		this.mTable.registerKeyboardAction(boldAction1, KeyStroke.getKeyStroke("ctrl B"), 0);
		WorksheetPanel$2 italicAction1 = new WorksheetPanel$2(this);
		this.mTable.registerKeyboardAction(italicAction1, KeyStroke.getKeyStroke("ctrl I"), 0);
		WorksheetPanel$3 underlineAction1 = new WorksheetPanel$3(this);
		this.mTable.registerKeyboardAction(underlineAction1, KeyStroke.getKeyStroke("ctrl U"), 0);
		this.mTable.addMouseListener(new WorksheetPanel$4(this));
		this.mTable.getTableHeader().addMouseListener(new WorksheetPanel$5(this));
		this.mTopLeft.addMouseListener(new WorksheetPanel$6(this));
		this.mRowHeader.addMouseListener(new WorksheetPanel$7(this));
	}

	private void showColumnPopup(MouseEvent me) {
		JTableHeader header = this.mTable.getTableHeader();
		int colIndex = header.getColumnModel().getColumnIndexAtX(me.getPoint().x);
		if (colIndex >= 0 && colIndex < this.mTable.getColumnCount()) {
			this.selectColumn(colIndex);
			JPopupMenu menu = this.mPopupActions.getColumnPopup(me, colIndex);
			menu.show(me.getComponent(), me.getPoint().x, me.getPoint().y);
		}

	}

	private void showTopLeftPopup(MouseEvent me) {
		this.mTable.selectAll();
		JPopupMenu menu = this.mPopupActions.getTopLeftPopup(me);
		menu.show(me.getComponent(), me.getPoint().x, me.getPoint().y);
	}

	private void showRowPopup(MouseEvent me) {
		int rowIndex = this.mRowHeader.locationToIndex(me.getPoint());
		if (rowIndex >= 0 && rowIndex < this.mTable.getRowCount()) {
			this.selectRow(rowIndex);
			JPopupMenu menu = this.mPopupActions.getRowPopup(me, rowIndex);
			menu.show(me.getComponent(), me.getPoint().x, me.getPoint().y);
		}

	}

	private void showCellPopup(MouseEvent me) {
		this.ensureCellSelected(me);
		JPopupMenu menu = this.mPopupActions.getCellPopup(me);
		JPopupMenu chartMenu = null;
		if (this.isDesignMode() && this.isChartCellSelected()) {
			chartMenu = this.getChartPopupMenu();
			if (chartMenu != null) {
				menu = chartMenu;
			}
		}

		if (menu != null) {
			menu.show(me.getComponent(), me.getPoint().x, me.getPoint().y);
		}

	}

	private void ensureCellSelected(MouseEvent e) {
		int row = this.mTable.rowAtPoint(e.getPoint());
		int col = this.mTable.columnAtPoint(e.getPoint());
		int[] selectedRows = this.mTable.getSelectedRows();
		int[] selectedColumns = this.mTable.getSelectedColumns();
		int r = 0;

		int c;
		for (c = 0; r < selectedRows.length && selectedRows[r] != row; ++r) {
			;
		}

		while (c < selectedColumns.length && selectedColumns[c] != col) {
			++c;
		}

		if (r >= selectedRows.length || c >= selectedColumns.length) {
			this.mTable.getSelectionModel().setSelectionInterval(row, row);
			this.mTable.getColumnModel().getSelectionModel().setSelectionInterval(col, col);
		}

	}

	public boolean isImageCellSelected() {
		int[] selectedRows = this.mTable.getSelectedRows();
		int[] selectedColumns = this.mTable.getSelectedColumns();
		if (selectedRows.length == 1 && selectedColumns.length == 1) {
			Cell cell = (Cell) this.mTable.getValueAt(selectedRows[0], selectedColumns[0]);
			if (cell != null) {
				Object value = cell.getValue();
				return value instanceof CPImage;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean isChartCellSelected() {
		int[] selectedRows = this.mTable.getSelectedRows();
		int[] selectedColumns = this.mTable.getSelectedColumns();
		if (selectedRows.length == 1 && selectedColumns.length == 1) {
			Cell cell = (Cell) this.mTable.getValueAt(selectedRows[0], selectedColumns[0]);
			if (cell != null) {
				Object value = cell.getValue();
				return value instanceof CPChart;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private JPopupMenu getChartPopupMenu() {
		int[] selectedRows = this.mTable.getSelectedRows();
		int[] selectedColumns = this.mTable.getSelectedColumns();
		if (selectedRows.length == 1 && selectedColumns.length == 1) {
			Cell cell = (Cell) this.mTable.getValueAt(selectedRows[0], selectedColumns[0]);
			if (cell != null) {
				Object value = cell.getValue();
				if (value instanceof CPChart) {
					ChartPanel panel = this.mTable.getChartPanel((CPChart) value);
					return panel.getPopupMenu();
				}
			}

			return null;
		} else {
			return null;
		}
	}

	private void selectColumn(int colIndex) {
		this.mTable.getSelectionModel().setSelectionInterval(0, this.mTable.getRowCount() - 1);
		this.mTable.getColumnModel().getSelectionModel().setSelectionInterval(colIndex, colIndex);
	}

	private void selectRow(int rowIndex) {
		this.mTable.getSelectionModel().setSelectionInterval(rowIndex, rowIndex);
		this.mTable.getColumnModel().getSelectionModel().setSelectionInterval(0, this.mTable.getColumnCount() - 1);
	}

	void insertColumn(int columnIndex) {
		this.mTable.insertColumn(columnIndex);
	}

	void deleteColumn(int columnIndex) {
		this.mTable.deleteColumn(columnIndex);
	}

	void insertRow(int rowIndex) {
		this.mTable.insertRow(rowIndex);
	}

	void deleteRow(int rowIndex) {
		this.mTable.deleteRow(rowIndex);
	}

	void showCellFormatDialog(MouseEvent e) {
		CellFormatDialog dialog = null;
		Window owner = OkCancelDialog.getWindowForComponent(this.mTable);
		int[] selection = this.getSelectionRect(this.mTable.getSelectedRows(), this.mTable.getSelectedColumns());
		int startRow = selection[0];
		int startColumn = selection[1];
		int endRow = selection[2];
		int endColumn = selection[3];
		if (startRow != Integer.MAX_VALUE && endRow >= 0 && startColumn != Integer.MAX_VALUE && endColumn >= 0) {
			if (owner instanceof Frame) {
				dialog = new CellFormatDialog((Frame) owner, "Cell Format", Purpose.XML_FORM);
			} else {
				dialog = new CellFormatDialog((Dialog) owner, "Cell Format", Purpose.XML_FORM);
			}

			Map activeFormatMap = this.getWorksheet().queryFormatProperties(startRow, startColumn, endRow, endColumn);
			CompositeCellFormat primeCellFormat = new CompositeCellFormat();
			Iterator updateCellFormat = activeFormatMap.values().iterator();

			while (updateCellFormat.hasNext()) {
				Collection formats = (Collection) updateCellFormat.next();
				CellFormatEntry msFormat = CellFormatEntry.selectMostSignificantFormat(formats);
				msFormat.getFormatProperty().updateFormat(primeCellFormat);
			}

			dialog.populateFromFormat(primeCellFormat, activeFormatMap);
			if (dialog.doModal()) {
				CompositeCellFormat updateCellFormat1 = new CompositeCellFormat(activeFormatMap);
				dialog.updateCell(updateCellFormat1);
				this.getWorksheet().setCellFormat(startRow, startColumn, endRow, endColumn, updateCellFormat1.queryFormatProperties());
			}

		} else {
			JOptionPane.showMessageDialog(owner, "Select one or more cells before selecting the format dialog");
		}
	}

	private void applyFormat(int[] selectedRows, int[] selectedColumns, CompositeCellFormat cellFormat) {
		int[] selectionRect = this.getSelectionRect(selectedRows, selectedColumns);
		this.applyFormat(selectionRect[0], selectionRect[1], selectionRect[2], selectionRect[3], cellFormat);
	}

	private int[] getSelectionRect(int[] selectedRows, int[] selectedColumns) {
		int startRow = Integer.MAX_VALUE;
		int endRow = -1;
		int[] startColumn = selectedRows;
		int endColumn = selectedRows.length;

		int len$;
		for (int result = 0; result < endColumn; ++result) {
			len$ = startColumn[result];
			if (len$ < startRow) {
				startRow = len$;
			}

			if (len$ > endRow) {
				endRow = len$;
			}
		}

		int var11 = Integer.MAX_VALUE;
		endColumn = -1;
		int[] var12 = selectedColumns;
		len$ = selectedColumns.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			int selectedColumn = var12[i$];
			if (selectedColumn < var11) {
				var11 = selectedColumn;
			}

			if (selectedColumn > endColumn) {
				endColumn = selectedColumn;
			}
		}

		var12 = new int[] { startRow, var11, endRow, endColumn };
		return var12;
	}

	private void applyFormat(int startRow, int startColumn, int endRow, int endColumn, CompositeCellFormat cellFormat) {
		Worksheet worksheet = this.mTable.getWorksheet();
		worksheet.setCellFormat(startRow, startColumn, endRow, endColumn, cellFormat.queryFormatProperties());
	}

	private CellFormat queryCellFormat(int row, int column) {
		CellFormat cellFormat = this.mTable.getWorksheet().getFormat(row, column, row, column);
		return (CellFormat) (cellFormat != null ? cellFormat : sDefaultCellFormat);
	}

	void resetCellFormat() {
		this.applyFormat(this.mTable.getSelectedRows(), this.mTable.getSelectedColumns(), new CompositeCellFormat());
	}

	void clearContents(int which) {
		int[] selectedRows = this.mTable.getSelectedRows();
		int[] selectedColumns = this.mTable.getSelectedColumns();
		int[] arr$ = selectedRows;
		int len$ = selectedRows.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			int selectedRow = arr$[i$];
			int[] arr$1 = selectedColumns;
			int len$1 = selectedColumns.length;

			for (int i$1 = 0; i$1 < len$1; ++i$1) {
				int selectedColumn = arr$1[i$1];
				if (this.mTable.isCellEditable(selectedRow, selectedColumn)) {
					Cell cell = (Cell) this.mTable.getValueAt(selectedRow, selectedColumn);
					if (cell != null) {
						if ((which & 1) > 0) {
							cell.setText((String) null);
							cell.setValue((Object) null);
						}

						if ((which & 2) > 0) {
							cell.setInputMapping((String) null);
						}

						if ((which & 4) > 0) {
							cell.setOutputMapping((String) null);
						}

						this.getWorksheet().getWorkbook().recalcDependencyTree(cell);
					}
				}
			}
		}

		this.mTable.repaint();
	}

	void scaleSelectedImages(boolean scale) {
		int[] selectedRows = this.mTable.getSelectedRows();
		int[] selectedColumns = this.mTable.getSelectedColumns();
		int[] arr$ = selectedRows;
		int len$ = selectedRows.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			int selectedRow = arr$[i$];
			int[] arr$1 = selectedColumns;
			int len$1 = selectedColumns.length;

			for (int i$1 = 0; i$1 < len$1; ++i$1) {
				int selectedColumn = arr$1[i$1];
				if (this.mTable.isCellEditable(selectedRow, selectedColumn)) {
					Cell cell = (Cell) this.mTable.getValueAt(selectedRow, selectedColumn);
					if (cell != null) {
						Object value = cell.getValue();
						if (value instanceof CPImage) {
							CPImage image = (CPImage) value;
							image.setScale(scale);
							this.repaint();
						}
					}
				}
			}
		}

	}

	void copyInput2OutputMapping() {
		int[] selectedRows = this.mTable.getSelectedRows();
		int[] selectedColumns = this.mTable.getSelectedColumns();
		int[] arr$ = selectedRows;
		int len$ = selectedRows.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			int selectedRow = arr$[i$];
			int[] arr$1 = selectedColumns;
			int len$1 = selectedColumns.length;

			for (int i$1 = 0; i$1 < len$1; ++i$1) {
				int selectedColumn = arr$1[i$1];
				if (this.mTable.isCellEditable(selectedRow, selectedColumn)) {
					Cell cell = (Cell) this.mTable.getValueAt(selectedRow, selectedColumn);
					if (cell != null) {
						String inputMapping = cell.getInputMapping();
						if (inputMapping != null && inputMapping.length() > 0) {
							String outputMapping = inputMapping.replaceFirst("cedar.cp.cell", "cedar.cp.post");
							outputMapping = outputMapping.replaceFirst("cedar.cp.getCell", "cedar.cp.putCell");
							outputMapping = outputMapping.replaceFirst("M,", "");
							outputMapping = outputMapping.replaceFirst("B,", "");
							cell.setOutputMapping(outputMapping);
						} else {
							cell.setOutputMapping((String) null);
						}
					}
				}
			}
		}

		this.mTable.repaint();
	}

	void copyOutput2InputMapping() {
		int[] selectedRows = this.mTable.getSelectedRows();
		int[] selectedColumns = this.mTable.getSelectedColumns();
		int[] arr$ = selectedRows;
		int len$ = selectedRows.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			int selectedRow = arr$[i$];
			int[] arr$1 = selectedColumns;
			int len$1 = selectedColumns.length;

			for (int i$1 = 0; i$1 < len$1; ++i$1) {
				int selectedColumn = arr$1[i$1];
				if (this.mTable.isCellEditable(selectedRow, selectedColumn)) {
					Cell cell = (Cell) this.mTable.getValueAt(selectedRow, selectedColumn);
					if (cell != null) {
						String outputMapping = cell.getOutputMapping();
						if (outputMapping != null && outputMapping.length() > 0) {
							String inputMapping = outputMapping.replaceFirst("cedar.cp.post\\(", "cedar.cp.cell\\(M,");
							inputMapping = inputMapping.replaceFirst("cedar.cp.putCell", "cedar.cp.getCell");
							cell.setInputMapping(inputMapping);
						} else {
							cell.setInputMapping((String) null);
						}
					}
				}
			}
		}

		this.mTable.repaint();
	}

	// private void buildControls(Worksheet worksheet, boolean showHeaders) {
	// if(worksheet.isDesignMode()) {
	// this.mModel = new SparseTableModel(worksheet, worksheet.getMaxRows(), worksheet.getMaxColumns(), this, !showHeaders);
	// } else {
	// this.mModel = new SparseTableModel(worksheet, worksheet.getRowCount(), worksheet.getColumnCount(), this, !showHeaders);
	// }
	//
	// this.mTable = new WorksheetTable(this.mModel, showHeaders);
	// this.mTable.setAutoResizeMode(0);
	// this.mTable.getTableHeader().setReorderingAllowed(false);
	// this.mTable.setCellSelectionEnabled(true);
	// this.mTable.setSelectionMode(1);
	// this.mTable.getTableHeader().setDefaultRenderer(new TableHeaderRenderer());
	// this.mTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	// this.mRowHeader = new JList(new RowHeaderListModel(this.mModel));
	// this.mRowHeader.setBackground(this.getBackground());
	// this.mRowHeader.setFixedCellWidth(40);
	// this.mRowHeader.setFixedCellHeight(this.mTable.getRowHeight() + this.mTable.getRowMargin());
	// this.mRowHeader.setCellRenderer(new RowHeaderRenderer(this.mTable));
	// this.mRowHeader.setSelectionMode(0);
	// this.mRowHeader.getSelectionModel().addListSelectionListener(new WorksheetPanel$8(this));
	//
	//
	// // sidePanel contains outline level panel and row header
	// JPanel sidePanel = new JPanel();
	// sidePanel.setBorder(null);
	// GroupLayout layout = new GroupLayout(sidePanel);
	// layout.setAutoCreateGaps(false);
	// layout.setAutoCreateContainerGaps(false);
	// sidePanel.setLayout(layout);
	// layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	// .addGroup(layout.createSequentialGroup()
	// .addComponent(mOutlineLevelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	// .addGap(0, 0, 0)
	// .addComponent(mRowHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	// );
	// layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	// .addComponent(mOutlineLevelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	// .addComponent(mRowHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	// );
	//
	// // JScrollPane which contains table in the center and sidePanel at the left
	// JScrollPane scroller = new JScrollPane(this.mTable);
	// if(showHeaders) {
	// scroller.setRowHeaderView(sidePanel);
	// }
	//
	// scroller.setAutoscrolls(true);
	// this.add(scroller, "Center");
	// this.setShowGrid(worksheet.isShowGrid());
	// Color textColor = UIManager.getColor("TabbedPane.foreground");
	// Color background = UIManager.getColor("TabbedPane.selected");
	// JPanel p = new JPanel(new FlowLayout(0, 4, 0));
	// p.setBackground(background);
	// this.mCellAddress = new JLabel(" ");
	// this.mCellAddress.setForeground(textColor);
	// p.add(this.mCellAddress);
	// JLabel equals = new JLabel(":");
	// equals.setForeground(textColor);
	// p.add(equals);
	// this.mFeedback = new JLabel(" ");
	// this.mFeedback.setForeground(textColor);
	// p.add(this.mFeedback);
	// if(showHeaders) {
	// this.add(p, "North");
	// }
	//
	// this.mTopLeft.setBackground(background);
	// this.mTopLeft.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(202,205,224)));
	// scroller.setCorner("UPPER_LEFT_CORNER", this.mTopLeft);
	// }

	private void buildControls(Worksheet worksheet, boolean showHeaders) {
		if (worksheet.isDesignMode()) {
			this.mModel = new SparseTableModel(worksheet, worksheet.getMaxRows(), worksheet.getMaxColumns(), this, !showHeaders);
		} else {
			this.mModel = new SparseTableModel(worksheet, worksheet.getRowCount(), worksheet.getColumnCount(), this, !showHeaders);
		}

		this.mTable = new WorksheetTable(this.mModel, showHeaders);
		this.mTable.setAutoResizeMode(0);
		this.mTable.getTableHeader().setReorderingAllowed(false);
		this.mTable.setCellSelectionEnabled(true);
		this.mTable.setSelectionMode(1);
		this.mTable.getTableHeader().setDefaultRenderer(new TableHeaderRenderer());
		this.mTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		this.mRowHeader = new JList(new RowHeaderListModel(this.mModel));
		this.mRowHeader.setBackground(this.getBackground());
		this.mRowHeader.setFixedCellWidth(40);
		this.mRowHeader.setFixedCellHeight(this.mTable.getRowHeight() + this.mTable.getRowMargin());
		this.mRowHeader.setCellRenderer(new RowHeaderRenderer(this.mTable));
		this.mRowHeader.setSelectionMode(0);
		this.mRowHeader.getSelectionModel().addListSelectionListener(new WorksheetPanel$8(this));
		JScrollPane scroller = new JScrollPane(this.mTable);
		if (showHeaders) {
			scroller.setRowHeaderView(this.mRowHeader);
		}

		scroller.setAutoscrolls(true);
		this.add(scroller, "Center");
		this.setShowGrid(worksheet.isShowGrid());
		Color textColor = UIManager.getColor("TabbedPane.foreground");
		Color background = UIManager.getColor("TabbedPane.selected");
		JPanel p = new JPanel(new FlowLayout(0, 4, 0));
		p.setBackground(background);
		this.mCellAddress = new JLabel(" ");
		this.mCellAddress.setForeground(textColor);
		p.add(this.mCellAddress);
		JLabel equals = new JLabel(":");
		equals.setForeground(textColor);
		p.add(equals);
		this.mFeedback = new JLabel(" ");
		this.mFeedback.setForeground(textColor);
		p.add(this.mFeedback);
		if (showHeaders) {
			this.add(p, "North");
		}

		this.mTopLeft = new JPanel();
		this.mTopLeft.setBackground(background);
		scroller.setCorner("UPPER_LEFT_CORNER", this.mTopLeft);
	}

	public void setFormatBackground(Color color) {
		this.applyFormatToSelectedCells(new WorksheetPanel$9(this, color));
	}

	public void setFormatForeground(Color color) {
		this.applyFormatToSelectedCells(new WorksheetPanel$10(this, color));
	}

	public void setHorizontalAlignment(int alignment) {
		this.applyFormatToSelectedCells(new WorksheetPanel$11(this, alignment));
	}

	public void toggleFormatBoldFont() {
		this.applyFormatToSelectedCells(new WorksheetPanel$12(this));
	}

	public void toggleFormatItalicFont() {
		this.applyFormatToSelectedCells(new WorksheetPanel$13(this));
	}

	public void toggleFormatUnderlineFont() {
		this.applyFormatToSelectedCells(new WorksheetPanel$14(this));
	}

	public void setFormatFont(String name, Integer size) {
		this.mCurrentFont = null;
		this.applyFormatToSelectedCells(new WorksheetPanel$15(this, name, size));
	}

	public void mergeSelectedCells() {
		this.mTable.mergeSelectedCells();
	}

	public void splitSelectedCells() {
		this.mTable.splitSelectedCells();
	}

	public void toggleGrid() {
		Worksheet worksheet = this.mTable.getWorksheet();
		worksheet.setShowGrid(!worksheet.isShowGrid());
		this.setShowGrid(worksheet.isShowGrid());
	}

	private void setShowGrid(boolean showGrid) {
		if (showGrid) {
			this.mTable.setShowGrid(true);
			this.mTable.setIntercellSpacing(new Dimension(1, 1));
		} else {
			this.mTable.setShowGrid(false);
			this.mTable.setIntercellSpacing(new Dimension(0, 0));
		}

		this.mRowHeader.setFixedCellHeight(this.mTable.getRowHeight() + this.mTable.getRowMargin());
	}

	public void setViewLayer(int layer) {
		this.mTable.setViewLayer(layer);
	}

	public int getViewLayer() {
		return this.mTable.getViewLayer();
	}

	public void setBoxBorder() {
		this.getWorksheet().getWorkbook().beginGroupEdit();
		int[] columns = this.mTable.getSelectedColumns();
		int[] rows = this.mTable.getSelectedRows();
		if (rows != null && columns != null) {
			if (rows.length >= 1 && columns.length >= 1) {
				int startRow = rows[0];
				int endRow = startRow + rows.length - 1;
				int startColumn = columns[0];
				int endColumn = startColumn + columns.length - 1;
				if (columns.length > 2) {
					this.setBoxBorder(startColumn, startColumn, startRow, endRow, HorizBorderType.LEFT);
					this.setBoxBorder(startColumn + 1, endColumn - 1, startRow, endRow, HorizBorderType.CENTRE);
					this.setBoxBorder(endColumn, endColumn, startRow, endRow, HorizBorderType.RIGHT);
				} else if (columns.length == 2) {
					this.setBoxBorder(startColumn, startColumn, startRow, endRow, HorizBorderType.LEFT);
					this.setBoxBorder(endColumn, endColumn, startRow, endRow, HorizBorderType.RIGHT);
				} else {
					this.setBoxBorder(endColumn, endColumn, startRow, endRow, HorizBorderType.LEFT_RIGHT);
				}

				this.getWorksheet().getWorkbook().endGroupEdit();
			}
		}
	}

	public void addChart() {
		int[] columns = this.mTable.getSelectedColumns();
		int[] rows = this.mTable.getSelectedRows();
		if (rows != null && columns != null) {
			if (rows.length >= 1 && columns.length >= 1) {
				int startRow = rows[0];
				int endRow = startRow + rows.length - 1;
				int startColumn = columns[0];
				int endColumn = startColumn + columns.length - 1;
				if (startRow != Integer.MAX_VALUE && endRow >= 0 && startColumn != Integer.MAX_VALUE && endColumn >= 0) {
					Component parent = SwingUtilities.getRoot(this);
					CreateChartDialog chartDialog = null;
					if (parent instanceof JFrame) {
						chartDialog = new CreateChartDialog((JFrame) parent);
					} else if (parent instanceof JDialog) {
						chartDialog = new CreateChartDialog((JDialog) parent);
					}

					if (chartDialog.doModal()) {
						try {
							int pe = endRow + 1;
							int chartStartColumn = startColumn;
							switch (chartDialog.getPlacementLocation()) {
							case 0:
								pe = startRow - chartDialog.getRowCount();
								break;
							case 1:
								pe = startRow;
								chartStartColumn = startColumn - chartDialog.getColumnCount();
							case 2:
							default:
								break;
							case 3:
								pe = startRow;
								chartStartColumn = endColumn + 1;
								break;
							case 4:
								try {
									String dataset = chartDialog.getSpecificLocation();
									int[] chart = this.getWorksheet().getCellCoordinates(dataset.toUpperCase());
									pe = chart[0];
									chartStartColumn = chart[1];
								} catch (ParseException var15) {
									JOptionPane.showMessageDialog(this, "Unable to parse cell reference", "Location Error", 0);
								}
							}

							if (pe >= 0 && chartStartColumn >= 0) {
								WorksheetDataset dataset1 = new WorksheetDataset(this.getWorksheet(), startRow, startColumn, endRow, endColumn);
								CPChart chart1 = chartDialog.createChart(dataset1);
								this.getWorksheet().setCellValue((Object) chart1, pe, chartStartColumn);
								int chartEndRow = pe + chartDialog.getRowCount() - 1;
								int chartEndColumn = chartStartColumn + chartDialog.getColumnCount() - 1;
								this.getWorksheet().mergeCells(pe, chartStartColumn, chartEndRow, chartEndColumn);
							} else {
								JOptionPane.showMessageDialog(this, "Unable to place chart outside sheet", "Location Error", 0);
							}
						} catch (ParseException var16) {
							var16.printStackTrace();
						}
					}

				}
			}
		}
	}

	public void addImage() {
		int[] columns = this.mTable.getSelectedColumns();
		int[] rows = this.mTable.getSelectedRows();
		if (rows != null && columns != null) {
			if (rows.length >= 1 && columns.length >= 1) {
				int startRow = rows[0];
				int endRow = startRow + rows.length - 1;
				int startColumn = columns[0];
				int endColumn = startColumn + columns.length - 1;
				if (startRow != Integer.MAX_VALUE && endRow >= 0 && startColumn != Integer.MAX_VALUE && endColumn >= 0) {
					Component parent = SwingUtilities.getRoot(this);
					CreateImageDialog imageDialog = null;
					if (parent instanceof JFrame) {
						imageDialog = new CreateImageDialog((JFrame) parent, this.getWorksheet().getWorkbook());
					} else if (parent instanceof JDialog) {
						imageDialog = new CreateImageDialog((JDialog) parent, this.getWorksheet().getWorkbook());
					}

					if (imageDialog.doModal()) {
						try {
							CPImage pe = imageDialog.createImage();
							this.getWorksheet().setCellValue((Object) pe, startRow, startColumn);
							if (startRow != endRow || startColumn != endColumn) {
								this.getWorksheet().mergeCells(startRow, startColumn, endRow, endColumn);
							}
						} catch (ParseException var10) {
							var10.printStackTrace();
						}
					}

				}
			}
		}
	}

	private void setBoxBorder(int startColumn, int endColumn, int startRow, int endRow, HorizBorderType horizBorderType) {
		int rowCount = endRow - startRow + 1;
		if (rowCount > 2) {
			this.setBoxBorder(startColumn, endColumn, startRow, startRow, horizBorderType, VertBorderType.TOP);
			this.setBoxBorder(startColumn, endColumn, startRow + 1, endRow - 1, horizBorderType, VertBorderType.MIDDLE);
			this.setBoxBorder(startColumn, endColumn, endRow, endRow, horizBorderType, VertBorderType.BOTTOM);
		} else if (rowCount == 2) {
			this.setBoxBorder(startColumn, endColumn, startRow, startRow, horizBorderType, VertBorderType.TOP);
			this.setBoxBorder(startColumn, endColumn, endRow, endRow, horizBorderType, VertBorderType.BOTTOM);
		} else {
			this.setBoxBorder(startColumn, endColumn, endRow, endRow, horizBorderType, VertBorderType.TOP_BOTTOM);
		}

	}

	private void setBoxBorder(int startColumn, int endColumn, int startRow, int endRow, HorizBorderType horizBorderType, VertBorderType vertBorderType) {
		CompositeCellFormat cellFormat = (new CompositeCellFormat()).cloneFormat(this.queryCellFormat(startRow, startColumn));
		cellFormat.setBorder(new LinesBorder(Color.darkGray, 0));
		LinesBorder linesBorder = (LinesBorder) cellFormat.getBorder();
		if (horizBorderType == HorizBorderType.LEFT || horizBorderType == HorizBorderType.LEFT_RIGHT) {
			linesBorder.setWestThickness(1);
		}

		if (horizBorderType == HorizBorderType.RIGHT || horizBorderType == HorizBorderType.LEFT_RIGHT) {
			linesBorder.setEastThickness(1);
		}

		if (vertBorderType == VertBorderType.TOP || vertBorderType == VertBorderType.TOP_BOTTOM) {
			linesBorder.setNorthThickness(1);
		}

		if (vertBorderType == VertBorderType.BOTTOM || vertBorderType == VertBorderType.TOP_BOTTOM) {
			linesBorder.setSouthThickness(1);
		}

		this.applyFormat(startRow, startColumn, endRow, endColumn, cellFormat);
	}

	private void applyFormatToSelectedCells(WorksheetPanel$FormatEditor editor) {
		int[] selection = this.getSelectionRect(this.mTable.getSelectedRows(), this.mTable.getSelectedColumns());
		int startRow = selection[0];
		int startColumn = selection[1];
		int endRow = selection[2];
		int endColumn = selection[3];
		if (startRow != Integer.MAX_VALUE && endRow >= 0 && startColumn != Integer.MAX_VALUE && endColumn >= 0) {
			Map existingFormatProperties = this.getWorksheet().queryFormatProperties(startRow, startColumn, endRow, endColumn);
			HashMap newFormatProperties = new HashMap();
			editor.updateProps(existingFormatProperties, newFormatProperties);
			this.getWorksheet().setCellFormat(startRow, startColumn, endRow, endColumn, newFormatProperties.values());
		}
	}

	public Cell createCell(int row, int column) {
		Cell cell = this.mModel.newCell(row, column);
		if (this.mCurrentFont != null) {
			cell.getFormat().setFont(this.mCurrentFont);
		}

		this.mModel.setValueAt(cell, row, column);
		return cell;
	}

	public WorksheetTable getTable() {
		return this.mTable;
	}

	public void setTable(WorksheetTable table) {
		this.mTable = table;
	}

	public SparseTableModel getModel() {
		return this.mModel;
	}

	public void setModel(SparseTableModel model) {
		this.mModel = model;
	}

	public void cellSelectionChanged(CellSelectionEvent e) {
		String text = null;
		String addr = null;
		this.mCellAddress.setText(" ");
		this.mFeedback.setText(" ");
		int row = e.getFirstRow();
		int col = e.getFirstColumn();
		if (row >= 0 && col >= 0) {
			addr = this.mTable.getColumnName(col) + (row + 1);
			Object o = this.mTable.getValueAt(row, col);
			if (o instanceof Cell) {
				Cell cell = (Cell) o;
				text = cell.getText();
				e.setCell(cell);
			}
		}

		this.mCellAddress.setText(addr != null ? addr : " ");
		this.mFeedback.setText(text != null ? text : " ");
		if (this.mChainedSelectionListener != null) {
			this.mChainedSelectionListener.cellSelectionChanged(e);
		}

	}

	public void performCutAction() {
		this.mTable.performCutAction();
	}

	public void performCopyAction() {
		this.mTable.performCopyAction();
	}

	public void performPasteAction() {
		this.mTable.performPasteAction();
	}

	Worksheet getWorksheet() {
		return this.mModel.getWorksheet();
	}

	public boolean isDesignMode() {
		return this.mModel.getWorksheet().getWorkbook().isDesignMode();
	}

	public ActionListener getCellPickerActionListener() {
		return this.mCellPickerActionListener;
	}

	public boolean isGridOn() {
		return this.getWorksheet().isShowGrid();
	}

	public void setCellPickerActionListener(ActionListener cellPickerActionListener) {
		this.mCellPickerActionListener = cellPickerActionListener;
	}

	// $FF: synthetic method
	static void accessMethod000(WorksheetPanel x0, MouseEvent x1) {
		x0.showCellPopup(x1);
	}

	// $FF: synthetic method
	static void accessMethod100(WorksheetPanel x0, MouseEvent x1) {
		x0.showColumnPopup(x1);
	}

	// $FF: synthetic method
	static void accessMethod200(WorksheetPanel x0, MouseEvent x1) {
		x0.showTopLeftPopup(x1);
	}

	// $FF: synthetic method
	static void accessMethod300(WorksheetPanel x0, MouseEvent x1) {
		x0.showRowPopup(x1);
	}

	// $FF: synthetic method
	static WorksheetTable accessMethod400(WorksheetPanel x0) {
		return x0.mTable;
	}

}
