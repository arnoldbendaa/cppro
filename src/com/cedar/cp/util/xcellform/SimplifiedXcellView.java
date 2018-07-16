package com.cedar.cp.util.xcellform;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray.CellLink;
import com.jxcell.CellException;
import com.jxcell.CellFormat;
import com.jxcell.CellRef;
import com.jxcell.EndEditEvent;
import com.jxcell.EndEditListener;
import com.jxcell.RangeRef;
//import com.jxcell.RichModifiedEvent;
//import com.jxcell.RichModifiedListener;
import com.jxcell.StartEditEvent;
import com.jxcell.StartEditListener;
import com.jxcell.View;
//import com.jxcell.WorksheetModifiedEvent;
//import com.jxcell.WorksheetModifiedListener;
//import com.jxcell.ss.AD;
import com.softproideas.util.validation.MappingFunction;

/**
 * @author Jaroslaw Kaczarski
 * @company Softpro.pl Sp. z o.o.
 * 
 * Simplified XcellView is a wrapper over the View class which synchronize its
 * state with the Workbook.
 */
public class SimplifiedXcellView extends View {

	private static final long serialVersionUID = -5830787545624765334L;
	private Workbook workbook = null;

	public SimplifiedXcellView(View view) {
		super(view);
//		setfixedLineFormat(CellFormat.BorderNone, 0);
		synchronizeWorkbook();
	}

	/**
	 * Removes all IM and OM from the View
	 */
	private void clearMappings() {
		Worksheet worksheet = null;
		try {
			if(workbook==null)
				return;
			
			// Active sheet
			int activeSheet = this.getSheet();
			
			// Insert IM and OM to cells
			for (int sheetIdx = 0; sheetIdx < this.getSheetsCount(); sheetIdx++) {
				this.setSheet(sheetIdx);
				
				worksheet = this.workbook.getWorksheet(this.getSheetName(sheetIdx));

				Iterator<LinkedListSparse2DArray.CellLink<Cell>> cellIterator = worksheet.iterator();

				while (cellIterator.hasNext()) {
					CellLink<Cell> cellLink = cellIterator.next();
					Cell cell = cellLink.getData();
					if (cell != null) {
						this.setText(sheetIdx, cell.getRow(), cell.getColumn(), "");
					}
				}
			}
			
			// Set active sheet
			this.setSheet(activeSheet);
		} catch (CellException e) {
			e.printStackTrace();
		}
	}
	
	private void synchronizeWorkbook(){
		
		this.addStartEditListener(new StartEditListener() {
			
			@Override
			public void startEdit(StartEditEvent e) {
				try {
					String editSting = e.getEditString();
					CellRef cell=((View)e.getSource()).getActiveCell();
					Worksheet worksheet = workbook.getWorksheet(getSheetName(getSheet()));
					Cell workbookCell = worksheet.get(cell.getRow(), cell.getCol());
					if ((workbookCell == null) || (GeneralUtils.isEmptyOrNull(workbookCell.getOutputMapping()))) {
						cancelEdit();
					}

					//System.out.println("StartEditEvent: "+e.isCanceled()+" CellRef: row="+cell.getRow()+", col="+cell.getCol()+", pane="+cell.getPane());
				} catch (CellException e1) {
					e1.printStackTrace();
				}
			}
		});


//		this.addStartRichModifyListener(new RichModifiedListener() {
//			public void modified(RichModifiedEvent richModifiedEvent) {
//				RichModifiedEvent.ModificationAction type = richModifiedEvent.getModificationAction();
//				int dstSheetIndex = richModifiedEvent.getDstSheet();
//				int dstRow1 = richModifiedEvent.getDstRow1();
//				int dstCol1 = richModifiedEvent.getDstCol1();
//				int dstRow2 = richModifiedEvent.getDstRow2();
//				int dstCol2 = richModifiedEvent.getDstCol2();
//				int srcSheetIndex = richModifiedEvent.getSrcSheet();
//				int srcRow1 = richModifiedEvent.getSrcRow1();
//				int srcCol1 = richModifiedEvent.getSrcCol1();
//				int srcRow2 = richModifiedEvent.getSrcRow2();
//				int srcCol2 = richModifiedEvent.getSrcCol2();
//
//				if ( type.equals(RichModifiedEvent.ModificationAction.UNDO) || type.equals(RichModifiedEvent.ModificationAction.REDO) || type.equals(RichModifiedEvent.ModificationAction.RECALC)) {
//					// Permission to undo, redo and recalc
//				} 
//				else if (type.equals(RichModifiedEvent.ModificationAction.KEYEVENT_DELETE)) {
//					try {
//						Worksheet worksheet;
//						worksheet = workbook.getWorksheet(getSheetName(getSheet()));
//						Cell cell;
//						RangeRef range = getSelection();
//						for (int rowIdx = range.getRow1(); rowIdx <= range.getRow2(); rowIdx++) {
//							for (int colIdx = range.getCol1(); colIdx <= range.getCol2(); colIdx++) {
//								// Delete value if cell has output mapping
//								cell = worksheet.get(rowIdx, colIdx);
//								if ((cell != null) && !GeneralUtils.isEmptyOrNull(cell.getOutputMapping())) {
//									clearRange(rowIdx, colIdx, rowIdx, colIdx, (short) 2);
//									if (!cell.getCellText().equals("")) {
//										worksheet.setCellValue("", rowIdx, colIdx);
//									}
//								}
//							}
//						}
//					} catch (CellException e) {
//						e.printStackTrace();
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//					richModifiedEvent.setCanceled(true);
//				} 
//				else {
//					richModifiedEvent.setCanceled(true);
//				}
//			
////             	System.out.println(
////             			"richModified start fired: type" + type + " dstSheetIndex:" + dstSheetIndex + " dstRow1:" + dstRow1 + " dstCol1:" + dstCol1 + " dstRow2:" + dstRow2 + " dstCol2:" + dstCol2
////             			+ " srcSheetIndex:" + srcSheetIndex + " srcRow1:" + srcRow1 + " srcCol1:" + srcCol1 + " srcRow2:" + srcRow2 + " srcCol2:" + srcCol2);
//			}
//		});

//        this.addEndRichModifyListener(new RichModifiedListener() {
//        	public void modified(RichModifiedEvent richModifiedEvent) {
//        		RichModifiedEvent.ModificationAction type = richModifiedEvent.getModificationAction();
//        		int dstSheetIndex = richModifiedEvent.getDstSheet();
//        		int dstRow1 = richModifiedEvent.getDstRow1();
//        		int dstCol1 = richModifiedEvent.getDstCol1();
//        		int dstRow2 = richModifiedEvent.getDstRow2();
//        		int dstCol2 = richModifiedEvent.getDstCol2();
//        		int srcSheetIndex = richModifiedEvent.getSrcSheet();
//        		int srcRow1 = richModifiedEvent.getSrcRow1();
//        		int srcCol1 = richModifiedEvent.getSrcCol1();
//        		int srcRow2 = richModifiedEvent.getSrcRow2();
//        		int srcCol2 = richModifiedEvent.getSrcCol2();
//        		
//       			if (type.equals(RichModifiedEvent.ModificationAction.RECALC)) {
//       				//mUndoManager.undoableEditHappened( getUndoableEdit() );
//       				cellToWorkbook(dstSheetIndex, dstRow1, dstCol1);
//       			}
////        		System.out.println(
////        				"richModified end fired: type" + type + " dstSheetIndex:" + dstSheetIndex + " dstRow1:" + dstRow1 + " dstCol1:" + dstCol1 + " dstRow2:" + dstRow2 + " dstCol2:" + dstCol2
////        				+ " srcSheetIndex:" + srcSheetIndex + " srcRow1:" + srcRow1 + " srcCol1:" + srcCol1 + " srcRow2:" + srcRow2 + " srcCol2:" + srcCol2);
//        	}
//        });
	}
	
	/**
	 * Check that input/output mapping is in the specified area
	 * 
	 * @param sheet			sheet number
	 * @param row1			coordinate specifying the beginning row of the range.
	 * @param col1			coordinate specifying the beginning column of the range
	 * @param row2			Coordinate specifying the ending row of the range
	 * @param col2			Coordinate specifying the ending column of the range
	 * @return				true - in specified area are at least one cell with input/output mapping
	 */
	public boolean isCellsWorkbook(int sheet, int row1, int col1, int row2, int col2) {
		try {
			Worksheet worksheet = workbook.getWorksheet(getSheetName(sheet));
			setSheet(sheet);
			
			for (int rowIdx = row1; rowIdx <= row2; rowIdx++) {
				for (int colIdx = col1; colIdx <= col2; colIdx++) {
					Cell workbookCell = worksheet.get(rowIdx, colIdx);
					if (workbookCell != null) {
						if (!GeneralUtils.isEmptyOrNull(workbookCell.getInputMapping()) && !GeneralUtils.isEmptyOrNull(workbookCell.getOutputMapping())) {
							return true;
						}
					}
				}
			}
		} catch (CellException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * Initialize applet mode
	 */
	public void initAppletMode() {
		// Don`t show edit bar, grid lines
		setShowEditBar(false);
		setShowEditBarCellRef(false);
		setShowGridLines(false);
		
		// Paste -> CTRL+V
		ActionListener pasteAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					Worksheet worksheet = workbook.getWorksheet(getSheetName(getSheet()));
					Cell cell;
					RangeRef range = getSelection();
					String value;
					String pasteString = (String) (clipboard.getContents(this).getTransferData(DataFlavor.stringFlavor));

					String[] lines = pasteString.split("\n");
					for (int i = 0; i < lines.length; i++) {
						String[] cells = lines[i].split("\t");
						for (int j = 0; j < cells.length; j++) {
							// Set value, only in selected range
							if ((range.getRow1() + i <= range.getRow2()) && (range.getCol1() + j <= range.getCol2())) {
								cell = worksheet.get(range.getRow1() + i, range.getCol1() + j);
								// If cell has output mapping
								if ((cell != null) && !GeneralUtils.isEmptyOrNull(cell.getOutputMapping())) {
									setTextAsValue(range.getRow1() + i, range.getCol1() + j, cells[j]);
									if (!cell.getCellText().equals(cells[j])) {
										worksheet.setCellValue(cells[j], range.getRow1() + i, range.getCol1() + j);
									}
								}
							}
						}
					}
					//System.out.println("PASTE: Clipboard contains:\n" + pasteString );
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		this.registerKeyboardAction(pasteAction, KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK, false), 0);
		
		// Copy -> CTRL+C
		ActionListener copyAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					RangeRef range = getSelection();
					StringBuffer sbf = new StringBuffer();
					for (int rowIdx = range.getRow1(); rowIdx <= range.getRow2(); rowIdx++) {
						for (int colIdx = range.getCol1(); colIdx <= range.getCol2(); colIdx++) {
							sbf.append(getText(rowIdx, colIdx));
							if (colIdx < range.getCol2()) {
								sbf.append("\t");
							}
						}
						sbf.append("\n");
					}
					StringSelection stsel = new StringSelection(sbf.toString());
					clipboard.setContents(stsel, stsel);
					//System.out.println("COPY: Clipboard contains:\n" + sbf.toString() );
				} catch (CellException ex) {
					ex.printStackTrace();
				}
			}
		};
		this.registerKeyboardAction(copyAction, KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK, false), 0);
		
		// Cut -> CTRL+X
		ActionListener cutAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					Worksheet worksheet = workbook.getWorksheet(getSheetName(getSheet()));
					Cell cell;
					RangeRef range = getSelection();
					StringBuffer sbf = new StringBuffer();
					for (int rowIdx = range.getRow1(); rowIdx <= range.getRow2(); rowIdx++) {
						for (int colIdx = range.getCol1(); colIdx <= range.getCol2(); colIdx++) {
							// Copy
							sbf.append(getText(rowIdx, colIdx));
							if (colIdx < range.getCol2()) {
								sbf.append("\t");
							}
							// Delete value if cell has output mapping
							cell = worksheet.get(rowIdx, colIdx);
							if ((cell != null) && !GeneralUtils.isEmptyOrNull(cell.getOutputMapping())) {
								setTextAsValue(rowIdx, colIdx, "");
								if (!cell.getCellText().equals("")) {
									worksheet.setCellValue("", rowIdx, colIdx);
								}
							}
						}
						sbf.append("\n");
					}
					StringSelection stsel = new StringSelection(sbf.toString());
					clipboard.setContents(stsel, stsel);
					// System.out.println("CUT: Clipboard contains:\n" + sbf.toString() );
				} catch (CellException ex) {
					ex.printStackTrace();
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
			}
		};
		this.registerKeyboardAction(cutAction, KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK, false), 0);

	}
	
	/**
	 * Update input/output mapping with cell value
	 */
	public boolean updateMapping() {
		Worksheet worksheet = null;
		String text, temp;
		String[] tempTab;
		boolean found;
		boolean result = false;
		try {
			if (workbook == null)
				return false;

			// Insert IM and OM to cells
			for (int sheetIdx = 0; sheetIdx < this.getSheetsCount(); sheetIdx++) {
				
				worksheet = this.workbook.getWorksheet(this.getSheetName(sheetIdx));
				Iterator<LinkedListSparse2DArray.CellLink<Cell>> cellIterator = worksheet.iterator();
				
				while (cellIterator.hasNext()) {
					CellLink<Cell> cellLink = cellIterator.next();
					Cell cell = cellLink.getData();
					if (cell != null) {
						// Input mapping
						if (!GeneralUtils.isEmptyOrNull(cell.getInputMapping())) {
							found = false;
							text = cell.getInputMapping();
							// #Sheet1#A#1
							Pattern pattern=Pattern.compile("#[^#]*#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");	
							Matcher matcher = pattern.matcher(text);
							while (matcher.find()) {
								found = true;
								result = true;
								temp = matcher.group();
								tempTab = temp.split("#");
								text = text.replace(temp, getText(getSheetNumber(tempTab[1]), Integer.parseInt(tempTab[3])-1, this.columnNumber(tempTab[2]))); // replace found reference with cell value
							}
							// #A#1
							pattern=Pattern.compile("#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");	
							matcher = pattern.matcher(text);
							while (matcher.find()) {
								found = true;
								result = true;
								temp = matcher.group();
								tempTab = temp.split("#");
								text = text.replace(temp, getText(sheetIdx, Integer.parseInt(tempTab[2])-1, this.columnNumber(tempTab[1]))); // replace found reference with cell value
							}
							if (found){
								cell.setInputMapping(text);
							}
						}
						// Output mapping
						if (!GeneralUtils.isEmptyOrNull(cell.getOutputMapping())) {
							found = false;
							text = cell.getOutputMapping();
							// #Sheet1#A#1
							Pattern pattern=Pattern.compile("#[^#]*#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");	
							Matcher matcher = pattern.matcher(text);
							while (matcher.find()) {
								found = true;
								result = true;
								temp = matcher.group();
								tempTab = temp.split("#");
								text = text.replace(temp, getText(getSheetNumber(tempTab[1]), Integer.parseInt(tempTab[3])-1, this.columnNumber(tempTab[2]))); // replace found reference with cell value
							}
							// #A#1
							pattern=Pattern.compile("#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");	
							matcher = pattern.matcher(text);
							while (matcher.find()) {
								found = true;
								result = true;
								temp = matcher.group();
								tempTab = temp.split("#");
								text = text.replace(matcher.group(), getText(sheetIdx, Integer.parseInt(tempTab[2])-1, this.columnNumber(tempTab[1]))); // replace found reference with cell value
							}
							if (found){
								cell.setOutputMapping(text);
							}
						}
						// Text or formula
						if (!GeneralUtils.isEmptyOrNull(cell.getText())) {
							found = false;
							text = cell.getCellText();
							// #Sheet1#A#1
							Pattern pattern=Pattern.compile("#[^#]*#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");	
							Matcher matcher = pattern.matcher(text);
							while (matcher.find()) {
								found = true;
								result = true;
								temp = matcher.group();
								tempTab = temp.split("#");
								text = text.replace(temp, getText(getSheetNumber(tempTab[1]), Integer.parseInt(tempTab[3])-1, this.columnNumber(tempTab[2]))); // replace found reference with cell value
							}
							// #A#1
							pattern=Pattern.compile("#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");	
							matcher = pattern.matcher(text);
							while (matcher.find()) {
								found = true;
								result = true;
								temp = matcher.group();
								tempTab = temp.split("#");
								text = text.replace(matcher.group(), getText(sheetIdx, Integer.parseInt(tempTab[2])-1, this.columnNumber(tempTab[1]))); // replace found reference with cell value
							}
							
							if (found){
								cell.setFormulaText(text);
							}
						}
					}
				}
			}
		}
		catch (CellException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * Set cell values from workbook to view
	 */
	public void workbookToView() {
		Worksheet worksheet = null;
		try {
			if (workbook == null)
				return;

			// Insert IM and OM string to cells as text
			for (int sheetIdx = 0; sheetIdx < this.getSheetsCount(); sheetIdx++) {
				
				worksheet = this.workbook.getWorksheet(this.getSheetName(sheetIdx));

				Iterator<LinkedListSparse2DArray.CellLink<Cell>> cellIterator = worksheet.iterator();

				while (cellIterator.hasNext()) {
					CellLink<Cell> cellLink = cellIterator.next();
					Cell cell = cellLink.getData();
					if (cell != null) {
						com.jxcell.CellFormat format = this.getCellFormat(sheetIdx, cell.getRow(), cell.getColumn(), sheetIdx, cell.getRow(), cell.getColumn() );
						if (cell.getCellText().isEmpty() || cell.getCellText().equals("null")) { // if empty cell
							if (format != null) {
								if( format.getCustomFormat().equals("@") ){ // if empty cell with text format
									this.setTextAsValue(sheetIdx, cell.getRow(), cell.getColumn(), "");
								} else { // if empty cell with general/numeric format
									this.setTextAsValue(sheetIdx, cell.getRow(), cell.getColumn(), "0");
								}
							}
						} else {
						    String cellText = cell.getCellText();
						    if (cellText.startsWith("=")) {
						        cellText = cellText.replaceAll("\\$", "");
						        cellText = cellText.replaceFirst("=", "");
						        this.setFormula(sheetIdx, cell.getRow(), cell.getColumn(), cellText);
						    } else {
						        this.setTextAsValue(sheetIdx, cell.getRow(), cell.getColumn(), cellText);
						    }
							
						}
					}
				}
			}
		}
		catch (CellException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set cell values from view to workbook
	 */
	public void viewToWorkbook() {
		try {
			if (workbook == null)
				return;
						
			Worksheet worksheet = null;
			int initSheet = getSheet();
			String importText = "", input = "", output = "", tags = "", text = "";
			boolean correctString;
			
			for (int sheetIdx = 0; sheetIdx < this.getSheetsCount(); sheetIdx++) {
				
				setSheet(sheetIdx);
				worksheet = this.workbook.getWorksheet(this.getSheetName(sheetIdx));
				
				for (int rowIdx = 0; rowIdx <= this.getLastRow(); rowIdx++) {
					for (int colIdx = 0; colIdx <= this.getLastCol(); colIdx++) {
						setActiveCell(rowIdx, colIdx);
						importText = getText();
						
						if (checkImportString(importText)) {
							correctString = false;
							
							Cell cell = worksheet.get(rowIdx, colIdx);
							if (cell == null) {
								cell = worksheet.newCell(rowIdx, colIdx);
							}
							// Input mapping
							Pattern pattern=Pattern.compile("(input|INPUT|Input|in|IN|In)\\{\\{[^}}]*\\}\\}");	
							Matcher matcher = pattern.matcher(importText);
							while (matcher.find()) {
								correctString = true;
								input = matcher.group();
								input = input.substring(input.indexOf("{")+2, input.length()-2); // remove *{{ and }}
								cell.setInputMapping(input);
								this.setText("");
							}
							// Output mapping
							pattern=Pattern.compile("(output|OUTPUT|Output|out|OUT|Out)\\{\\{[^}}]*\\}\\}");	
							matcher = pattern.matcher(importText);
							while (matcher.find()) {
								correctString = true;
								output = matcher.group();
								output = output.substring(output.indexOf("{")+2, output.length()-2); // remove *{{ and }}
								cell.setOutputMapping(output);
								this.setText("");
							}
							// Tags
                            pattern=Pattern.compile("(tags|TAGS|Tags|tag|TAG|Tag)\\{\\{[^}}]*\\}\\}");    
                            matcher = pattern.matcher(importText);
                            while (matcher.find()) {
                                tags = matcher.group();
                                tags = tags.substring(tags.indexOf("{")+2, tags.length()-2); // remove *{{ and }}
                                String[] tagsArr = output.split(",");
                                for (String tag : tagsArr) {
                                    cell.addTag(tag.toLowerCase());
                                }
                                this.setText("");
                            }
							// Formula
							pattern=Pattern.compile("(formula|FORMULA|Formula)\\{\\{[^}}]*\\}\\}");	
							matcher = pattern.matcher(importText);
							while (matcher.find()) {
								correctString = true;
								text = matcher.group();
								text = text.substring(text.indexOf("{")+2, text.length()-2); // remove *{{ and }}
								if (checkFormulaMapping(text) || !GeneralUtils.isEmptyOrNull(cell.getOutputMapping()) || !GeneralUtils.isEmptyOrNull(cell.getInputMapping())) {
									if (text.startsWith("=")) {
										cell.setFormulaText(text);
									} else {
										cell.setFormulaText("="+text);
									}
								}
								if (text.startsWith("=")) {
									text = text.substring(1);
								}
								try {
									this.setFormula(text);
								} catch (CellException e) {
									//this.setErrorFormulaFormat(sheetIdx, rowIdx, colIdx);
									this.setText(text);
								}
							}
							// Text
							pattern=Pattern.compile("(text|TEXT|Text)\\{\\{[^}}]*\\}\\}");	
							matcher = pattern.matcher(importText);
							while (matcher.find()) {
								correctString = true;
								text = matcher.group();
								text = text.substring(text.indexOf("{")+2, text.length()-2); // remove *{{ and }}
								this.setText(text);
								if (!GeneralUtils.isEmptyOrNull(cell.getOutputMapping()) || !GeneralUtils.isEmptyOrNull(cell.getInputMapping())) {
									try {
										Double d = Double.parseDouble(text);
										text = d.toString();
										if (text.substring(text.length()-2).equals(".0")) {
											text = text.substring(0, text.length()-2);
									    }
									} catch (NumberFormatException nfe) { }
									cell.setText(text);
								}
							}
							
							if (!correctString) {
								//this.setErrorFormulaFormat(sheetIdx, rowIdx, colIdx);
							}
						}
						
					}
				}
			}
			setSheet(initSheet);
		} catch (CellException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set cell values from view to workbook
	 */
	public void cellToWorkbook(int sheet, int row, int column) {
		try {
			if (workbook == null)
				return;

			String cellText = getText(sheet, row, column);
			String cellFormattedText = getFormattedText(sheet, row, column);
			Worksheet worksheet = this.workbook.getWorksheet(this.getSheetName(sheet));
			Cell cell = worksheet.get(row, column);
			
			if (cell != null) {
				if (!GeneralUtils.isEmptyOrNull(cellText)) {
				    String value = "";
				    if (isProperDateFormat(cellFormattedText)) {
				        value = cellFormattedText;
				    } else {
				        value = cellText;
				    }
					
					if (!cell.getCellText().equals(value)) {
						worksheet.setCellValue(value, row, column);
					}
				} else {
					if (!cell.getCellText().equals("")) {
						worksheet.setCellValue("", row, column);
					}
				}
			}
		} catch (CellException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
    private Boolean isProperDateFormat(String value) {       
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        try {
            dateFormat.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
	
	/**
	 * Check if the given string contains input/output/formula/text
	 * 
	 * @param string        string to check
	 * @return 				true if the given string contains input/output/formula/text
	 */
	private boolean checkImportString(String string) {
		if (!GeneralUtils.isEmptyOrNull(string)) {
			if (string.toLowerCase().contains("input{{")) {
				return true;
			} else if (string.toLowerCase().contains("output{{")) {
				return true;
			} else if (string.toLowerCase().contains("formula{{")) {
				return true;
			} else if (string.toLowerCase().contains("text{{")) {
				return true;
			} else if (string.toLowerCase().contains("in{{")) {
				return true;
			} else if (string.toLowerCase().contains("out{{")) {
				return true;
			} else if (string.toLowerCase().contains("f{{")) {
				return true;
			} else if (string.toLowerCase().contains("t{{")) {
				return true;
			} else if (string.toLowerCase().contains("i{{")) {
				return true;
			} else if (string.toLowerCase().contains("o{{")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Rename sheet to new, unique name
	 * 
	 * @param sheetName
	 * @return
	 */
	private String newSheetName(String sheetName) {
		try {
			List<String> viewSheetList = new ArrayList<String>();
			for (int sheetIdx = 0; sheetIdx < this.getSheetsCount(); sheetIdx++) {
				viewSheetList.add(this.getSheetName(sheetIdx));
			}
			String[] tokens = sheetName.split(" ");
			if (tokens.length > 1) {
				try{
					int last = Integer.parseInt(tokens[tokens.length-1]);
					last++;
					for (int i = 0; i < tokens.length-1; i++) {
						sheetName = tokens[i];
					}
					sheetName = sheetName+" "+last;
				} catch(NumberFormatException e){
					sheetName = sheetName + " 1";
				}
			} else {
				sheetName = sheetName + " 1";
			}
			
			for (String x : viewSheetList) {
				if (x.toUpperCase().equals(sheetName.toUpperCase())) {
					return newSheetName(sheetName);
				}
			}
		} catch (CellException e) {
			e.printStackTrace();
		}
		return sheetName;
	}
	
	/**
	 * Repairs functions by coping text field to function field
	 */
	public void repairFunctions(JTextArea textArea) {
		try {
			int initSheet = getSheet();
			int initRow = getActiveRow();
			int initColumn = getActiveCol();
			int lastRow = 0;
			int lastColumn = 0;
			String textCell = "";
			
			for (int sheetIdx = 0; sheetIdx < this.getSheetsCount(); sheetIdx++) {
				if( textArea != null ) textArea.append("  [INFO] converting sheet no. "+(sheetIdx+1)+"\n" );
				
				setSheet(sheetIdx);
				lastRow = this.getLastRow();
				lastColumn = this.getLastCol();
				
				for (int rowIdx = 0; rowIdx <= lastRow; rowIdx++) {
					for (int colIdx = 0; colIdx <= lastColumn; colIdx++) {
						setActiveCell(rowIdx, colIdx);
						textCell = getText();
						
						if( textCell.startsWith("=") ) {
							editClear((short) 2 );
							
							//System.out.println( sheetIdx+" "+rowIdx+" "+colIdx+" "+textCell );
							String formula = textCell.substring(1,textCell.length() ).replace('"', '\"');
							if( formula.startsWith("+") )
								formula = formula.substring(1);
							
							if ( textCell.endsWith("%") ) {
								formula = formula.substring( 0, formula.length()-1 );
															
								com.jxcell.CellFormat format = getCellFormat(sheetIdx, rowIdx, colIdx, sheetIdx, rowIdx, colIdx);
								format.setCustomFormat("0.0%;(0.0%)");
								this.setCellFormat(format, sheetIdx, rowIdx, colIdx, sheetIdx, rowIdx, colIdx);
							}
							try {
								setFormula(formula);
							} catch (Exception e) {
								this.setText(formula);
								//this.setErrorFormulaFormat(sheetIdx, rowIdx, colIdx);
								if( textArea != null ) textArea.append("    [ERROR] Wrong formula in sheet="+(sheetIdx+1)+", row="+(rowIdx+1)+" column="+colIdx+"\n" );
							}
						}
						
					}
				}
			}
			setSheet(initSheet);
			setActiveCell(initRow, initColumn);
		} catch (CellException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check if the given formula string contains valid input mapping string
	 * 
	 * @param string   		string to check
	 * @return    			true if the given string contains input mapping string, false otherwise
	 */
	protected boolean checkFormulaMapping(String string) {	
		return MappingFunction.containsAnyMappingFunction(string);
	}
	
	/**
	 * Check if the given string is valid mapping string
	 * 
	 * @param string   		string to check
	 * @param typeMapping	0 = input 1 = output 2 = input or output
	 * @return    			valid string mapping or empty string if not valid
	 */
	protected String checkMapping(String string, int typeMapping) {	
		if ((typeMapping >= 0) && (typeMapping <= 2)) {
			boolean isInput = false;
			boolean isOutput = false;
			
			if (string.startsWith("cedar.cp.financeCube(") || string.startsWith("cedar.cp.dim0Identifier()") || string.startsWith("cedar.cp.dim0Description()") ||  string.startsWith("cedar.cp.dim1Identifier()") || string.startsWith("cedar.cp.dim1Description()") || string.startsWith("cedar.cp.dim2Identifier()") || string.startsWith("cedar.cp.dim2Description()") || string.startsWith("cedar.cp.param(") || string.startsWith("cedar.cp.structures(") || string.startsWith("cedar.cp.getVisId(") || string.startsWith("cedar.cp.getDescription(") || string.startsWith("cedar.cp.getLabel(") || string.startsWith("cedar.cp.formLink(")) {
				return string;
			} 
			if ((typeMapping == 0) || (typeMapping == 2)) {
				Pattern patternInputMapping = Pattern.compile("^cedar\\.cp\\.(getCell|getGlob|cell|getBaseVal|getQuantity|getCumBaseVal|getCumQuantity|getCurrencyLookup)\\(.*\\)$");
				Matcher matcher = patternInputMapping.matcher(string);
				isInput = matcher.find();
			}
			if ((typeMapping == 1) || (typeMapping == 2)) {
				Pattern patternInputMapping = Pattern.compile("^cedar\\.cp\\.(putCell|post)\\(.*\\)$");
				Matcher matcher = patternInputMapping.matcher(string);
				isOutput = matcher.find();
			}
			
			if (isInput || isOutput) {
				string = string.replaceAll("(\"|&quot;)", "");  // delete " or &quot;
				string = string.replaceAll("=", "=\""); 		// add " after =
				string = string.replaceAll(",", "\","); 		// add " before ,
				string = string.replaceAll("\\)$", "\"\\)"); 	// add " before last )
				return string;
			} else {
				return "";
			}
		}
		else {
			return "";
		}
	}
	

	/**
	 * Return dimension from input/output mapping
	 * 
	 * @param sheet				sheet number
	 * @param row				row number
	 * @param column			column number
	 * @param dimension			string defining the dimension to get ("dim0" or "dim1" or "dim2" or "dt")
	 * @param typeMapping		0 = input 1 = output 2 = input or output
	 * @param input				input mapping
	 * 
	 * @return    				valid string mapping or empty string if not valid
	 */
	public String getDimension(int sheet, int row, int column, String dimension, int typeMapping) { 
		return getDimension(sheet, row, column, dimension, typeMapping, "");
	}
	public String getDimension(int sheet, int row, int column, String dimension, int typeMapping, String input) {
		String checkedMappingString, dimToken, returnDimension = "";
		try {
			if (workbook == null)
				return returnDimension;

			Worksheet worksheet = this.workbook.getWorksheet(this.getSheetName(sheet));
			Cell workbookCell = worksheet.get(row, column);
			String inputMapping = workbookCell.getInputMapping();
			String outputMapping = workbookCell.getOutputMapping();
			if (!input.isEmpty())
				inputMapping = input;
				
			if ( (workbookCell != null) && (typeMapping >= 0) && (typeMapping <= 2) && (dimension.equals("dim0") || dimension.equals("dim1") || dimension.equals("dim2") || dimension.equals("dt"))  || dimension.equals("cmpy") || dimension.equals("company")) {
				
				if ( ((typeMapping == 0) || (typeMapping == 2)) && !GeneralUtils.isEmptyOrNull(inputMapping) ) {
					checkedMappingString = checkMapping(inputMapping, 0);
					if (!checkedMappingString.isEmpty()) {
						StringTokenizer stringTokenizer = new StringTokenizer(checkedMappingString, "(,)");
						stringTokenizer.nextToken(); // skip "cedar.cp.getCell"
						while(stringTokenizer.hasMoreTokens()) { 
							dimToken = stringTokenizer.nextToken();
							if (dimToken.startsWith(dimension)) {
								StringTokenizer stringTokenizer2 = new StringTokenizer(dimToken, "\"");
								stringTokenizer2.nextToken(); // skip "dimX="
								if( stringTokenizer2.hasMoreTokens() )
									returnDimension = stringTokenizer2.nextToken();
							}
						}
					}
				}
				if (( typeMapping == 1) || (typeMapping == 2) && !GeneralUtils.isEmptyOrNull(outputMapping) ) {
					checkedMappingString = checkMapping(outputMapping, 1);
					if (!checkedMappingString.isEmpty()) {
						StringTokenizer stringTokenizer = new StringTokenizer(checkedMappingString, "(,)"); 
						stringTokenizer.nextToken(); // skip "cedar.cp.putCell"
						while(stringTokenizer.hasMoreTokens()) { 
							dimToken = stringTokenizer.nextToken();
							if (dimToken.startsWith(dimension)) {
								StringTokenizer stringTokenizer2 = new StringTokenizer(dimToken, "\"");
								stringTokenizer2.nextToken(); // skip "dimX="
								returnDimension = stringTokenizer2.nextToken();
							}
						}
						
					}
				}
				
				// If don`t find in mapping, then check properties
				if (returnDimension.isEmpty()) {
					if (dimension.equals("dim0")) {
						String dim0 = worksheet.getProperties().get(WorkbookProperties.DIMENSION_0_VISID.toString());
						if (!GeneralUtils.isEmptyOrNull(dim0)) {
							returnDimension = dim0;
						}
					} else if  (dimension.equals("dim1")) {
						String dim1 = worksheet.getProperties().get(WorkbookProperties.DIMENSION_1_VISID.toString());
						if (!GeneralUtils.isEmptyOrNull(dim1)) {
							returnDimension = dim1;
						}
					}  else if  (dimension.equals("dim2")) {
						String dim2 = worksheet.getProperties().get(WorkbookProperties.DIMENSION_2_VISID.toString());
						if (!GeneralUtils.isEmptyOrNull(dim2)) {
							returnDimension = dim2;
						}
					}
				}
				
			}
		}
		catch (CellException e) {
			e.printStackTrace();
		}

		return returnDimension;
	}
	
	/**
	 * Retrieves the Workbook
	 * 
	 * @return
	 */
	public Workbook getWorkbook() {
		return workbook;
	}

	/**
	 * Sets new value to the Workbook
	 * 
	 * @param workbook
	 */
	public void setWorkbook(Workbook workbook) {
		workbook.setFormulasOn(false);
		this.workbook = workbook;
	}
	
	/**
	 * Clears values (including formulas), input mapping, output mapping and formats from all selected cells
	 */
	public void clearAll() {
		this.clearData((short) 3, false);
		this.clearMapping(2);
	}
	
	/**
	 * Clears values (including formulas) from all selected cells.
	 */
	public void clearData(){
		this.clearData((short) 2, false);
	}
	
	/**
	 * Clears format from all selected cells.
	 */
	public void clearFormat(){
		try {
			this.editClear((short) 1);
		} catch (CellException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Clears all selected cells.
	 * @param clearType	- Determines what is cleared: = 1 clears formats only / = 2 clears values only (including formulas) / = 7 clears values, formats, and objects
	 */
	public void clearData(short clearType, boolean repaint){
		int selectionCount = this.getSelectionCount();
		
		if (selectionCount > 0) { // if selection range of cells
			for (int i = 0; i < selectionCount; i++) {
				try {
					RangeRef selectionCells = this.getSelection(i);
					this.clearRange(selectionCells.getRow1(), selectionCells.getCol1(), selectionCells.getRow2(), selectionCells.getCol2(), clearType);
				} catch (CellException e) {
					e.printStackTrace();
				}
			}
		}
		else { // if selection one cell
			try {
				this.clearRange(this.getRow(), this.getCol(), this.getRow(), this.getCol(), clearType);
			} catch (CellException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Clears mapping from all selected cells
	 * 
	 * @param typeMapping	0 = input 1 = output 2 = input and output
	 */
	public void clearMapping(int typeMapping) {
		if ((typeMapping >= 0) && (typeMapping <= 2)) {
			try {
				Worksheet worksheet = this.workbook.getWorksheet(this.getSheetName(this.getSheet())); // get active worksheet from workbook	
				int selectionCount = this.getSelectionCount();
				
				if (selectionCount > 0) { // if selection range of cells
					for (int i = 0; i < selectionCount; i++) {
						RangeRef selectionCells = this.getSelection(i);
						int startRow = selectionCells.getRow1();
						int startCol = selectionCells.getCol1();
						int stopRow = selectionCells.getRow2();
						int stopCol = selectionCells.getCol2();
						
						while (startRow <= stopRow) { // checks all rows
							while (startCol <= stopCol) { // checks all columns
								Cell cell = worksheet.get(startRow, startCol);
								if (cell != null) {
									if ((typeMapping == 0) || (typeMapping == 2)) {
										cell.setInputMapping(""); // set input mapping empty
										if (checkFormulaMapping(cell.getCellText())) {
											cell.setFormulaText("");
										}
//										deleteCellImage(startRow, startCol);
									}
									if ((typeMapping == 1) || (typeMapping == 2)) {
										cell.setOutputMapping(""); // set output mapping empty
//										deleteCellImage(startRow, startCol);
									}
									// Clear cell
									this.clearRange(cell.getRow(), cell.getColumn(), cell.getRow(), cell.getColumn(), (short) 2);
								}
								startCol = startCol+1;
							}
							startCol = selectionCells.getCol1();
							startRow = startRow+1;						
						}
					}
				}
				else { // if selection one cell
					Cell cell = worksheet.get(this.getRow(), this.getCol());
					if (cell != null) {
						if ((typeMapping == 0) || (typeMapping == 2)) {
							cell.setInputMapping(""); // set input mapping empty
						}
						if ((typeMapping == 1) || (typeMapping == 2)) {
							cell.setOutputMapping(""); // set output mapping empty
						}
						// Clear cell
						this.clearRange(cell.getRow(), cell.getColumn(), cell.getRow(), cell.getColumn(), (short) 2);
					}
				}
			} catch (CellException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Copy input(output) mapping to output(input) mapping
	 * 
	 * @param typeCopy	0 = input to output 1 = output to input
	 */
	public void copyMapping(int typeCopy) {
		if ((typeCopy >= 0) && (typeCopy <= 1)) {
			try {
				Worksheet worksheet = this.workbook.getWorksheet(this.getSheetName(this.getSheet())); // get active worksheet from workbook	
				int selectionCount = this.getSelectionCount();
				
				if (selectionCount > 0) { // if selection range of cells
					for (int i = 0; i < selectionCount; i++) {
						RangeRef selectionCells = this.getSelection(i);
						int startRow = selectionCells.getRow1();
						int startCol = selectionCells.getCol1();
						int stopRow = selectionCells.getRow2();
						int stopCol = selectionCells.getCol2();
						
						while (startRow <= stopRow) { // checks all rows
							while (startCol <= stopCol) { // checks all columns
								Cell cell = worksheet.get(startRow, startCol);
								if (cell != null) {
									if ( (typeCopy == 0) && !GeneralUtils.isEmptyOrNull(cell.getInputMapping()) ) {
										cell.setOutputMapping(convertMapping(cell.getInputMapping(), 0)); // set output mapping value from input mapping
									} else if ( (typeCopy == 1) && !GeneralUtils.isEmptyOrNull(cell.getOutputMapping()) ) {
										cell.setInputMapping(convertMapping(cell.getOutputMapping(), 1)); // set input mapping value from output mapping
									}
								}
								startCol = startCol+1;
							}
							startCol = selectionCells.getCol1();
							startRow = startRow+1;						
						}
					}
				}
				else { // if selection one cell
					Cell cell = worksheet.get(this.getRow(), this.getCol());
					if (cell != null) {
						if ( (typeCopy == 0) && !GeneralUtils.isEmptyOrNull(cell.getInputMapping()) ) {
							cell.setOutputMapping(convertMapping(cell.getInputMapping(), 0)); // set output mapping value from input mapping
						} else if ( (typeCopy == 1) && !GeneralUtils.isEmptyOrNull(cell.getOutputMapping()) ) {
							cell.setInputMapping(convertMapping(cell.getOutputMapping(), 1)); // set input mapping value from output mapping
						}
					}
				}
			} catch (CellException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Convert input(output) mapping to output(input) mapping
	 * 
	 * @param string	string of mapping
	 * @param typeCopy	0 = input to output 1 = output to input
	 */
	private String convertMapping(String string, int typeConvert) {
		if (typeConvert == 0) {
			if (string.startsWith("cedar.cp.getCell")) {
				string = string.replaceFirst("cedar.cp.getCell", "cedar.cp.putCell");
			} else if (string.startsWith("cedar.cp.cell")) {
				string = string.replaceFirst("cedar.cp.cell", "cedar.cp.post");
				string = string.replaceFirst("M,", "");
				string = string.replaceFirst("B,", "");
			}
		} else if (typeConvert == 1) {
			if (string.startsWith("cedar.cp.putCell")) {
				string = string.replaceFirst("cedar.cp.putCell", "cedar.cp.getCell");
			} else if (string.startsWith("cedar.cp.post")) {
				string = string.replaceFirst("cedar.cp.post\\(", "cedar.cp.cell\\(M,");
			}
		}
		return string;
	}
	
	/**
	 * Set mapping cell format
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 */
	private void setMappingFormat(int sheet, int row, int column) {
		try {
			com.jxcell.CellFormat format = getCellFormat(sheet, row, column, sheet, row, column);
//			if (format.isUndefined((short) 13) || format.getPattern() == 0) {
//				format.setPattern((short) 1);
//			}
//			format.setPatternFG(0x0082ccf3);
			format.setFontName("SansSerif");
			format.setFontSize(10);
			format.setHorizontalAlignment((short) 1);
			format.setVerticalAlignment((short) 0);
			this.setCellFormat(format, sheet, row, column, sheet, row, column);
		} catch (CellException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set mapping cell format
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 */
	private void setErrorFormulaFormat(int sheet, int row, int column) {
		try {
			com.jxcell.CellFormat format = getCellFormat(sheet, row, column, sheet, row, column);
			if (format.isUndefined((short) 13) || format.getPattern() == 0) {
				format.setPattern((short) 1);
			}
			format.setPatternFG(0x00C20000);
			format.setFontName("SansSerif");
			format.setFontSize(10);
			format.setHorizontalAlignment((short) 1);
			format.setVerticalAlignment((short) 0);
			this.setCellFormat(format, sheet, row, column, sheet, row, column);
		} catch (CellException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes cells, rows, or columns from the specified range in all selected sheets. Rows are indexed from top to bottom beginning with 0; sheets and columns are indexed from left to right beginning with 0.
	 * 
	 * row1 - Coordinate of the beginning row of the range to delete
	 * col1 - Coordinate of the beginning column of the range
	 * row2 - Coordinate of the ending row of the range
	 * col2 - Coordinate of the ending column of the range
	 * shiftType - Constants determining how the delete should occur: ShiftHorizontal = 1 ShiftVertical = 2 shiftRows = 3 ShiftColumns = 4
	 */
	public void deleteRange(int row1, int col1, int row2, int col2, short shiftType) throws CellException {
		super.deleteRange(row1, col1, row2, col2, shiftType);
		
		// Update workbook
		Worksheet worksheet = this.workbook.getWorksheet(this.getSheetName(this.getSheet()));
		Iterator<LinkedListSparse2DArray.CellLink<Cell>> cellIterator = worksheet.iterator();

		while (cellIterator.hasNext()) {
			CellLink<Cell> cellLink = cellIterator.next();
			Cell cell = cellLink.getData();
			if (cell != null) {
				
				if (shiftType == 1) { // shift cells left
					if ((cell.getColumn() >= col1) && (cell.getRow() >= row1) && (cell.getRow() <= row2)) {
						if ((cell.getRow() >= row1) && (cell.getRow() <= row2) && (cell.getColumn() >= col1) && (cell.getColumn() <= col2)) {
							cell.setInputMapping("");
							cell.setOutputMapping("");
						} else if ((cell.getRow() >= row1) && (cell.getRow() <= row2)) {
							cell.setColumn(cell.getColumn()-((col2-col1)+1));
						}
					}
				} else if (shiftType == 2) { // shift cells up
					if ((cell.getRow() >= row1) && (cell.getRow() <= row2) && (cell.getColumn() >= col1) && (cell.getColumn() <= col2)) {
						cell.setInputMapping("");
						cell.setOutputMapping("");
					} else if ((cell.getRow() >= row1) && (cell.getColumn() >= col1) && (cell.getColumn() <= col2)) {
						cell.setRow(cell.getRow()-((row2-row1)+1));
					}
				} else if (shiftType == 3) { // entire row
					if ((cell.getRow() >= row1) && (cell.getRow() <= row2)) {
						cell.setInputMapping("");
						cell.setOutputMapping("");
					} else if (cell.getRow() > row2) {
						cell.setRow(cell.getRow()-((row2-row1)+1));
					}
				} else if (shiftType == 4) { // entire column
					if ((cell.getColumn() >= col1) && (cell.getColumn() <= col2)) {
						cell.setInputMapping("");
						cell.setOutputMapping("");
					} else if (cell.getColumn() > col2) {
						cell.setColumn(cell.getColumn()-((col2-col1)+1));
					}
				}
				
			}
		}
	}
	
	/**
	 * Moves the specified range in order to insert new cells, rows or columns. Rows are indexed from top to bottom beginning with 0; sheets and columns are indexed from left to right beginning with 0
	 * 
	 * row1 - range starting row
	 * col1 - range starting column
	 * row2 - range ending row
	 * col2 - range ending column
	 * shifttype - determines how the range cells should be shifted: ShiftHorizontal = 1; ShiftVertical = 2; ShiftRows = 3; ShiftColumns = 4;
	 */
	public void insertRange(int row1, int col1, int row2, int col2, short shifttype) throws CellException {
		super.insertRange(row1, col1, row2, col2, shifttype);
		this.shiftCells(row1, col1, row2, col2, shifttype);	 // shift cells in workbook
		this.editClear((short)1); // clear format
	}
	
	/**
	 * The same as insertRange() without cells format clear
	 */
	public void insertRangeClearOff(int row1, int col1, int row2, int col2, short shifttype) throws CellException {
		super.insertRange(row1, col1, row2, col2, shifttype);
		this.shiftCells(row1, col1, row2, col2, shifttype); // shift cells in workbook
	}

	/**
	 * Moves the selected range in all selected worksheets to insert new cells, rows, or columns
	 * 
	 * shift - A constant that determines how the insertion should occur. ShiftHorizontal = 1 ShiftVertical = 2 ShiftRows = 3 ShiftColumns = 4 FixupAppend = 32 FixupNormal = 0 FixupPrepend = 16
	 */
	public void editInsert(short shift) throws CellException {
		super.editInsert(shift);
		int col1 = this.getSelection().getCol1();
		int col2 = this.getSelection().getCol2();
		int row1 = this.getSelection().getRow1();
		int row2 = this.getSelection().getRow2();	
		this.shiftCells(row1, col1, row2, col2, shift); // shift cells in workbook
		this.editClear((short)1); // clear format
	}
	
	/**
	 * The same part of code using by insertRange(), insertRangeClearOff(), editInsert()
	 */
	private void shiftCells(int row1, int col1, int row2, int col2, short shifttype) throws CellException {
		// Update workbook
		Worksheet worksheet = this.workbook.getWorksheet(this.getSheetName(this.getSheet()));
		Iterator<LinkedListSparse2DArray.CellLink<Cell>> cellIterator = worksheet.iterator();

		while (cellIterator.hasNext()) {
			CellLink<Cell> cellLink = cellIterator.next();
			Cell cell = cellLink.getData();
			if (cell != null) {
				
				if (shifttype == 1) { // shift cells left
					if ((cell.getColumn() >= col1) && (cell.getRow() >= row1) && (cell.getRow() <= row2)) {
						cell.setColumn(cell.getColumn()+((col2-col1)+1));
					}
				} else if (shifttype == 2) { // shift cells up
					if ((cell.getRow() >= row1) && (cell.getColumn() >= col1) && (cell.getColumn() <= col2)) {
						cell.setRow(cell.getRow()+((row2-row1)+1));
					}
				} else if (shifttype == 3) { // entire row
					if (cell.getRow() >= row1) {
						cell.setRow(cell.getRow()+((row2-row1)+1));
					}
				} else if (shifttype == 4) { // entire column
					if (cell.getColumn() >= col1) {
						cell.setColumn(cell.getColumn()+((col2-col1)+1));
					}
				}

			}
		}
	}
	
	public void subtotal() {
		Object[] options = new Object[2];
		options[0] = "Up";
		options[1] = "Down";
		
		String s = (String)JOptionPane.showInputDialog(
				this,
				"Select row outline summary position:",
				"Subtotal",
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		if( s != null )
			this.subtotal( s.equals("Up") );
	}
	
	/**
	 * Create new outline group and add SUM() formula of selected cells
	 */
	public void subtotal(boolean upOrDown) {
		try {
			if( upOrDown && getSelStartRow() == 0 ) {
				JOptionPane.showMessageDialog(null, "Grouping not possible from first row!", "Group", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			int level = getRowOutlineLevel( getSelStartRow() );
			int i;
			for( i = getSelStartRow() ; i <= getSelEndRow() ; i++ )
				if( getRowOutlineLevel( i ) < level ) break;
			
			// impossible to create group when: is level down hole between first and last row OR first and last row are not on the same level
			if( i <= getSelEndRow() || getRowOutlineLevel( getSelStartRow() ) != getRowOutlineLevel( getSelEndRow() ) ) {
				JOptionPane.showMessageDialog(null, "Grouping not possible!", "Subtotal", JOptionPane.ERROR_MESSAGE);
			}
			else {
				// check if another subtotal exist in this row
				int rowSearch = isSummaryRowsBeforeDetail() ? getSelStartRow()-1 : getSelEndRow()+1 ;
				int rowLast = isSummaryRowsBeforeDetail() ? getSelStartRow() : getSelEndRow() ;
				boolean groupExist = false;
				if( rowSearch >= 0 && getRowOutlineLevel( rowSearch ) != getRowOutlineLevel( rowLast ) )
					groupExist = true;
				
				// if another subtotal exist don`t insert new row and column
				if( !groupExist ) {
					// get upper groups
					Map<Integer,Integer> rowsMap = new HashMap<Integer, Integer>();
					for( i = getSelStartRow() ; i <= getSelEndRow() ; i++ )
						rowsMap.put( i , getRowOutlineLevel( i ) );
					
					// set new group and others upper groups with one level up
//					for( Integer row : rowsMap.keySet() )
//						setRowOutlineLevel( row, row, rowsMap.get( row ) +1, false, upOrDown );
				}
				
				int firstSelectedColumn = getSelStartCol();
				int lastSelectedColumn = getSelEndCol();
				List<Integer> columnList = new ArrayList<Integer>();
				// searching in all selected columns cells with numbers values
				for( i = firstSelectedColumn ; i <= lastSelectedColumn ; i++ ) {
					int j;
					// check if cells has number values
					for( j = getSelStartRow() ; j <= getSelEndRow() ; j++ )
						// getText() return 0 when cell value is not number, number in other way
						if( getText( j, i ).equals("0") || getNumber( j, i ) != 0 ) break;
					// if has numbers add column to columnList
					if( j <= getSelEndRow() )
						columnList.add( i+( groupExist ? 0 : 1 ) );
				}
				
				// if any column to do
				if( columnList.size() != 0 ) {
					// get cells format
					com.jxcell.CellFormat format = getCellFormat( getSelStartRow(), getSelStartCol(), getSelEndRow(), getSelEndCol() );
					
					// groups from top
					if( upOrDown ) { //isSummaryRowsBeforeDetail()
						if( !groupExist ) {
							// add column
							insertRangeClearOff( getSelStartRow(), firstSelectedColumn, getSelStartRow(), firstSelectedColumn, (short) 4 );
							// add row at top
							insertRangeClearOff( getSelStartRow(), getSelStartCol(), getSelStartRow(), getSelStartCol(), (short) 3 );
							// set text "Total:"
							setText( getSelStartRow(), firstSelectedColumn, "Total:");
						}
						
						int firstRow = getSelStartRow() + ( groupExist ? 0 : 1 );
						int lastRow = getSelEndRow() + ( groupExist ? 0 : 1 );
						for( Integer col : columnList ) {
							setSelection( firstRow, col, lastRow, col );
							setFormula( firstRow-1, col, "SUM("+getSelectionLocal()+")" );
						}
						
						format.setBottomBorder((short) 2);
						format.setBottomBorderColor(0x00000000);
						setCellFormat( format, firstRow - 1 , firstSelectedColumn, firstRow - 1, columnList.get( columnList.size()-1 ) );
						
						// merge borders with other subtotals
						int column = firstSelectedColumn-1;
						while( groupExist && column > 0 ) {
							format = getCellFormat( firstRow - 1, column, firstRow - 1, column );
							if( format.getBottomBorder() == 2 )
								break;
							else {
								format.setBottomBorderColor(0x00000000);
								format.setBottomBorder((short) 2);
								setCellFormat( format, firstRow - 1, column, firstRow - 1, column );
							}
							column--;
						}
						
						setSelection(firstRow, columnList.get( 0 ), lastRow, columnList.get( columnList.size()-1 ) );
					}
					// groups from bottom
					else {
						if( !groupExist ) {
							// add column
							insertRangeClearOff( getSelStartRow(), firstSelectedColumn, getSelStartRow(), firstSelectedColumn, (short) 4 );
							// add row at bottom
							insertRangeClearOff( getSelEndRow()+1, getSelStartCol(), getSelEndRow()+1, getSelStartCol(), (short) 3 );
							// remove last extra row in group
							setRowOutlineLevel( getSelEndRow()+1, getSelEndRow()+1, getRowOutlineLevel( getSelEndRow()+1 )-1, false );
							// set text "Total:"
							setText( getSelEndRow()+1, firstSelectedColumn, "Total:");
						}

						// set formulas
						for( Integer col : columnList ) {
							setSelection( getSelStartRow(), col, getSelEndRow(), col );
							setFormula( getSelEndRow()+1, col, "SUM("+getSelectionLocal()+")" );
						}
						
						format.setTopBorder((short) 2);
						format.setTopBorderColor(0x00000000);
						setCellFormat( format, getSelEndRow()+1, firstSelectedColumn, getSelEndRow()+1, columnList.get( columnList.size()-1 ) );
						
						// merge borders with other subtotals
						int column = firstSelectedColumn-1;
						while( groupExist && column > 0 ) {
							format = getCellFormat( getSelEndRow()+1, column, getSelEndRow()+1, column );
							if( format.getTopBorder() == 2 )
								break;
							else {
								format.setTopBorderColor(0x00000000);
								format.setTopBorder((short) 2);
								setCellFormat( format, getSelEndRow()+1, column, getSelEndRow()+1, column );
							}
							column--;
						}
						
						setSelection( getSelStartRow(), columnList.get( 0 ), getSelEndRow(), columnList.get( columnList.size()-1 ) );
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
//			this.ungroupSubtotal();
//			JOptionPane.showMessageDialog(null, "Grouping not possible!\nPartial merged cell cannot be modified!", "Subtotal", JOptionPane.ERROR_MESSAGE);
		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	/**
	 * Remove group at first selected row
	 */
	public void ungroupSubtotal() {
		try {
			int level = getRowOutlineLevel( this.getActiveRow() );
			if( level > 0 ) {
				int firstRow = this.getActiveRow();
				int lastRow = this.getActiveRow();
				int i;
				
				// get first row of group
				for( i = firstRow ; i >= 0 ; i-- )
					if( getRowOutlineLevel( i ) < level ) break;
				firstRow = ++i;
				
				// get last row of group
				for( i = lastRow ; i < this.getMaxRow() ; i++ )
					if( getRowOutlineLevel( i ) < level ) break;
				lastRow = --i;
				
				// get upper groups than level
				Map<Integer,Integer> rowsMap = new HashMap<Integer, Integer>();
				for( i = firstRow ; i < lastRow ; i++ )
					rowsMap.put( i , getRowOutlineLevel( i ) );
				
				// remove group
				setRowOutlineLevel( firstRow, lastRow, level-1, false );
				
				// set old upper groups with one level down
				for( Integer row : rowsMap.keySet() )
					if( rowsMap.get( row ) > level )
						setRowOutlineLevel( row, row, rowsMap.get( row ) - 1, false );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void groupRows() {
		Object[] options = new Object[2];
		options[0] = "Up";
		options[1] = "Down";
		
		String s = (String)JOptionPane.showInputDialog(
				this,
				"Select row outline summary position:",
				"Group rows",
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		if( s != null )
			this.groupRows( s.equals("Up") );
	}
	
	/**
	 * Group rows
	 */
	public void groupRows(boolean upOrDown) {
		try {
			if( upOrDown && getSelStartRow() == 0 ) {
				JOptionPane.showMessageDialog(null, "Grouping not possible from first row!", "Group", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int level = getRowOutlineLevel(getSelStartRow());
			int i;
			for (i = getSelStartRow(); i <= getSelEndRow(); i++)
				if (getRowOutlineLevel(i) < level)
					break;

			// impossible to create group when: is level down hole between first
			// and last row OR first and last row are not on the same level
			if ( i <= getSelEndRow() || getRowOutlineLevel(getSelStartRow()) != getRowOutlineLevel(getSelEndRow()) ) {
				JOptionPane.showMessageDialog(null, "Grouping not possible!", "Group", JOptionPane.ERROR_MESSAGE);
			}
			else {
				Map<Integer,Integer> rowsMap = new HashMap<Integer, Integer>();
				for( i = getSelStartRow() ; i <= getSelEndRow() ; i++ )
					rowsMap.put( i , getRowOutlineLevel( i ) );
				
				// set new group and others upper groups with one level up
//				for( Integer row : rowsMap.keySet() )
//					setRowOutlineLevel( row, row, rowsMap.get( row ) +1, false, upOrDown );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Return sheet number by name (0 = first)
	 */
	public int getSheetNumber(String sheetName) {
		try {
			for (int sheetIdx = 0; sheetIdx < this.getSheetsCount(); sheetIdx++) {
				if (sheetName.toLowerCase().equals(this.getSheetName(sheetIdx).toLowerCase())) {
					return sheetIdx;
				}
			}
		} catch (CellException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Return excel column name. 0 = A
	 * 
	 * @param columnNumber 		number of column
	 * @return 					column name
	 */
	private static String columnName(int columnNumber) {
		int Base = 26;
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String colName = "";
		columnNumber++;
		
		while (columnNumber > 0) {
			int position = columnNumber % Base;
			colName = (position == 0 ? 'Z' : chars.charAt(position > 0 ? position - 1 : 0)) + colName;
			columnNumber = (columnNumber - 1) / Base;
		}
		return colName;
	}
	
	/**
	 * Return excel column number. A= 0
	 * 
	 * @param columnName 		column name
	 * @return					column number
	 */
	public static int columnNumber(String columnName) {
	    columnName = columnName.trim();
	    StringBuffer buff = new StringBuffer(columnName);
	    char chars[] = buff.reverse().toString().toLowerCase().toCharArray();
	    int retVal=0, multiplier=0;

	    for(int i = 0; i < chars.length;i++){
	        multiplier = (int)chars[i]-96;
	        retVal += multiplier * Math.pow(26, i);
	    }
	    return retVal-1;
	}
	
	private List<String> getMappingListFromFormula(String formula) {
		List<String> mappingList = new ArrayList<String>();

		// With cedar.cp.
		Pattern pattern = Pattern.compile("cedar\\.cp\\.(" + MappingFunction.getMappingsPattern() + ")\\([^)]*\\)");
		Matcher matcher = pattern.matcher(formula);
		while (matcher.find()) {
			mappingList.add(matcher.group());
			formula = formula.replace(matcher.group(), ""); // delete found mapping
		}
		// Without cedar.cp.
		pattern = Pattern.compile("(" + MappingFunction.getMappingsPattern() + ")\\([^)]*\\)");
		matcher = pattern.matcher(formula);
		while (matcher.find()) {
			mappingList.add("cedar.cp." + matcher.group());
		}

		return mappingList;
	}
}
