package com.cedar.cp.util.flatform.model;

import com.cedar.cp.util.DateUtils;
import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.flatform.model.CPChart;
import com.cedar.cp.util.flatform.model.CPChartFactory;
import com.cedar.cp.util.flatform.model.CPImage;
import com.cedar.cp.util.flatform.model.CPImageFactory;
import com.cedar.cp.util.flatform.model.CellErrorValue;
import com.cedar.cp.util.flatform.model.ExcelUtils;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.format.CellFormat;
import com.cedar.cp.util.flatform.model.format.CompositeCellFormat;
import com.cedar.cp.util.flatform.model.parser.CellRangeRef;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.Formula;
import com.cedar.cp.util.flatform.model.parser.ValueUtils;
import com.cedar.cp.util.xmlform.WorksheetEmbeddedObject;
import com.cedar.cp.util.xmlform.XMLWritable;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class Cell implements XMLWritable, Serializable {

    private Worksheet mWorksheet;
    private int mRow;
    private int mColumn;
    private String mText;
    private String mInputMapping;
    private String mOutputMapping;
    private ArrayList<String> mTags;
    private Formula mFormula;
    private Object mValue;
    private Set<CellRef> mRefs;
    private boolean mHidden;
    private boolean invertedValue;
    private boolean toEvaluate = true;
    private CPChartFactory mCPChartFactory;
    private CPImageFactory mCPImageFactory;
    private transient CellFormat mFormat;
    private transient String mFormattedText;
    public static final Double sZeroDouble = Double.valueOf(0.0D);
    private static final String sDefaultDateFormat = "dd/MM/yyyy";
    private static final String sDefaultDateTimeFormat = "dd/MM/yyyy HH:mm:ss";
    private static final String sDefaultTimeFormat = "HH:mm:ss";
    private static DecimalFormat sOutputNumberFormat = new DecimalFormat("#,##0.00;-#,##0.00");
    private static DecimalFormat sDefaultOutputNumberFormat = new DecimalFormat("##############.#########");
    public static DecimalFormat sInputNumberFormat = new DecimalFormat("#,##0.00;-#,##0.00");
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat();
    private static CompositeCellFormat sDefaultFormat = new CompositeCellFormat();
    
    private String[][] validationMessages;

    Cell(Worksheet worksheet) {
        this.mWorksheet = worksheet;
    }

    public Cell() {
        this((Worksheet) null);
    }

    Cell(Worksheet worksheet, int row, int column) {
        this(worksheet);
        this.mRow = row;
        this.mColumn = column;
    }

    public Worksheet getWorksheet() {
        return this.mWorksheet;
    }

    public void setWorksheet(Worksheet worksheet) {
        this.mWorksheet = worksheet;
    }

    public int getRow() {
        return this.mRow;
    }

    public void setRow(int row) {
        this.mRow = row;
    }

    public int getColumn() {
        return this.mColumn;
    }

    public void setColumn(int column) {
        this.mColumn = column;
    }

    public CellFormat getFormat() {
        if (this.mFormat == null) {
            this.mFormat = this.mWorksheet.getFormat(this.mRow, this.mColumn, this.mRow, this.mColumn);
            if (this.mFormat == null) {
                this.mFormat = sDefaultFormat;
            }
        }

        return this.mFormat;
    }

    public void resetCellFormat() {
        this.mFormat = null;
        this.mFormattedText = null;
    }

    public String getText() {
        return this.mText;
    }

    public void setText(String text) {
        if (text != null && text.trim().length() == 0) {
            text = null;
        }

        this.mValue = null;
        this.mText = text;
        this.mFormattedText = null;
    }

    public String getInputMapping() {
        return this.mInputMapping;
    }

    public void setInputMapping(String inputMapping) {
        this.mInputMapping = inputMapping;
    }

    public String getOutputMapping() {
        return this.mOutputMapping;
    }

    public void setOutputMapping(String outputMapping) {
        this.mOutputMapping = outputMapping;
    }

    public Formula getFormula() {
        return this.mFormula;
    }

    public void setFormula(Formula formula) {
        this.mFormula = formula;
        this.mFormattedText = null;
    }

    public void setFormulaText(String formula) {
        this.mText = formula;
        this.mFormattedText = null;
    }

    public String getFormulaText() {
        return this.mText;
    }

    public boolean isFormula() {
        return this.mFormula != null || this.mText != null && this.mText.length() > 0 && this.mText.charAt(0) == 61;
    }

    public void renameWorksheet(String oldName, String newName) {
        if (this.isFormula()) {
            this.mText = this.mText.replace(oldName, newName);
        }
    }

    public Object getValue() {
        return this.mValue;
    }

    public void setValue(Object value) {
        this.mValue = value;
        this.mFormattedText = null;
    }

    public boolean isHidden() {
        return this.mHidden;
    }

    public void setHidden(boolean hidden) {
        this.mHidden = hidden;
    }

    public Set<CellRef> getRefs() {
        return this.mRefs;
    }

    public void setRefs(Set<CellRef> refs) {
        this.mRefs = refs;
    }

    public String getAddress() {
        return this.getWorksheet().getColumnName(this.mColumn) + (this.mRow + 1);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.getWorksheet() != null) {
            sb.append(this.getWorksheet().getColumnName(this.mColumn)).append(this.mRow + 1).append(":").append(this.mText);
        } else {
            sb.append(this.mText);
        }

        return sb.toString();
    }

    public boolean isEmpty() {
        return this.mFormula == null ? this.mText == null || this.mText.isEmpty() : this.mValue == null || this.mValue instanceof String && ((String) this.mValue).isEmpty();
    }

    public String getStringValue() {
        return this.mFormula == null ? this.mText : (this.mValue != null ? String.valueOf(this.mValue) : null);
    }

    public boolean isDependencyTreeRoot() {
        return this.getFormula() == null && this.getRefs() != null && !this.getRefs().isEmpty() || this.getFormula() != null && this.getFormula().hasNoRefs() || this.getFormula() != null && !this.getFormula().hasNoRefs() && !this.refCellsExist();
    }

    private boolean refCellsExist() {
        if (this.getFormula() != null) {
            Iterator i$ = this.getFormula().getRefs().iterator();

            while (i$.hasNext()) {
                CellRangeRef formulaRef = (CellRangeRef) i$.next();
                if (!formulaRef.isSingleCell()) {
                    int startColumn1 = Math.min(formulaRef.getStartRef().getAbsoluteColumn(this.getColumn()), formulaRef.getEndRef().getAbsoluteColumn(this.getColumn()));
                    int endColumn = Math.max(formulaRef.getStartRef().getAbsoluteColumn(this.getColumn()), formulaRef.getEndRef().getAbsoluteColumn(this.getColumn()));
                    int startRow = Math.min(formulaRef.getStartRef().getAbsoluteRow(this.getRow()), formulaRef.getEndRef().getAbsoluteRow(this.getRow()));
                    int endRow = Math.max(formulaRef.getStartRef().getAbsoluteRow(this.getRow()), formulaRef.getEndRef().getAbsoluteRow(this.getRow()));
                    Worksheet refWorksheet = formulaRef.getStartRef().getWorksheet(this.getWorksheet());
                    return refWorksheet != null && refWorksheet.rangeIterator(startRow, startColumn1, endRow, endColumn).hasNext();
                }

                Cell startColumn = formulaRef.getStartRef().getCell(this.getWorksheet(), this.getRow(), this.getColumn());
                if (startColumn != null) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isError() {
        return this.mValue != null && this.mValue instanceof CellErrorValue;
    }

    public String getCellText() {
        if (this.mFormattedText == null) {
            if (this.mFormula == null) {
                this.mFormattedText = this.formatText(this.mText);
            } else if (this.mValue instanceof CellErrorValue) {
                this.mFormattedText = ((CellErrorValue) this.mValue).getText();
            } else {
                this.mFormattedText = this.formatText(this.mValue);
            }
        }

        return this.mFormattedText;
    }

    private String formatText(Object value) {
        String text = "";
        if (value != null) {
            int type = this.getFormat().getFormatType();
            if (type != 0 && type != 6) {
                if (type == 1) {
                    sOutputNumberFormat.setMinimumFractionDigits(this.getFormat().getDecimalPlaces());
                    sOutputNumberFormat.setMaximumFractionDigits(this.getFormat().getDecimalPlaces());
                    sOutputNumberFormat.setGroupingUsed(this.getFormat().isShowComma());

                    try {
                        text = sOutputNumberFormat.format(Double.parseDouble(value.toString()));
                    } catch (NumberFormatException var10) {
                        text = value.toString();
                    }
                } else if (type == 2) {
                    sOutputNumberFormat.setMinimumFractionDigits(this.getFormat().getDecimalPlaces());
                    sOutputNumberFormat.setMaximumFractionDigits(this.getFormat().getDecimalPlaces());
                    sOutputNumberFormat.setGroupingUsed(this.getFormat().isShowComma());

                    try {
                        text = this.getFormat().getCurrencySymbol() + sOutputNumberFormat.format(Double.parseDouble(value.toString()));
                    } catch (NumberFormatException var9) {
                        text = this.getFormat().getCurrencySymbol() + value.toString();
                    }
                } else if (type != 3 && type != 4) {
                    if (type == 5) {
                        sOutputNumberFormat.setMinimumFractionDigits(this.getFormat().getDecimalPlaces());
                        sOutputNumberFormat.setMaximumFractionDigits(this.getFormat().getDecimalPlaces());
                        sOutputNumberFormat.setGroupingUsed(false);

                        try {
                            text = sOutputNumberFormat.format(100.0D * Double.parseDouble(value.toString())) + "%";
                        } catch (NumberFormatException var8) {
                            text = value.toString() + "%";
                        }
                    } else if (type == 7) {
                        CellFormat format3 = this.getFormat();
                        if (format3 != null && format3.getFormatPattern() != null) {
                            sOutputNumberFormat.applyPattern(format3.getFormatPattern());
                        } else {
                            sOutputNumberFormat.applyPattern("##############.#########");
                        }

                        try {
                            text = sOutputNumberFormat.format(Double.parseDouble(value.toString()));
                        } catch (NumberFormatException var7) {
                            text = value.toString();
                        }
                    }
                } else if (value instanceof CellErrorValue) {
                    text = ((CellErrorValue) value).getText();
                } else if (value instanceof Number) {
                    double format = ((Number) value).doubleValue();
                    Date date = ExcelUtils.excel2JavaDate(format);
                    sDateFormat.applyPattern(this.getFormat().getFormatPattern());
                    text = sDateFormat.format(date);
                } else {
                    Date format1;
                    if (value instanceof Date) {
                        format1 = (Date) value;
                        sDateFormat.applyPattern(this.getFormat().getFormatPattern());
                        text = sDateFormat.format(format1);
                    } else if (value instanceof Time) {
                        Time format2 = (Time) value;
                        sDateFormat.applyPattern(this.getFormat().getFormatPattern());
                        text = sDateFormat.format(format2);
                    } else if (value instanceof String) {
                        String nfe;
                        if (type == 3) {
                            format1 = DateUtils.parseDateTime(sDateFormat, (String) value);
                            if (format1 == null) {
                                text = this.mText;
                            } else {
                                nfe = this.getFormat().getFormatPattern();
                                if (nfe == null) {
                                    nfe = "dd/MM/yyyy";
                                }

                                sDateFormat.applyPattern(nfe);
                                text = sDateFormat.format(format1);
                            }
                        } else {
                            format1 = DateUtils.parseTime(sDateFormat, (String) value);
                            if (format1 == null) {
                                text = this.mText;
                            } else {
                                nfe = this.getFormat().getFormatPattern();
                                if (nfe == null) {
                                    nfe = "HH:mm:ss";
                                }

                                sDateFormat.applyPattern(nfe);
                                text = sDateFormat.format(format1);
                            }
                        }
                    } else {
                        text = this.mText;
                    }
                }
            } else {
                if (!this.isFormula()) {
                    return value.toString();
                }

                if (!(value instanceof Number)) {
                    return String.valueOf(value);
                }

                text = sDefaultOutputNumberFormat.format(((Number) value).doubleValue());
            }
        }

        return text;
    }

    public void resetFormattedText() {
        this.mFormattedText = null;
    }

    public boolean isNegative() {
        if (this.mValue instanceof Number) {
            Number n = (Number) this.mValue;
            return n.doubleValue() < 0.0D;
        } else {
            return ValueUtils.isNumberChars(this.mText) ? this.mText.indexOf(45) == 0 : false;
        }
    }

    public Object getNumericValue() {
        if (this.mValue != null) {
            if (this.mValue instanceof Number) {
                return this.mValue;
            } else if (this.mValue instanceof String) {
                try {
                    return ((String) this.mValue).trim().length() > 0 ? Double.valueOf(sInputNumberFormat.parse((String) this.mValue).doubleValue()) : sZeroDouble;
                } catch (ParseException var2) {
                    return CellErrorValue.VALUE;
                }
            } else {
                return this.mValue instanceof CellErrorValue ? this.mValue : this.mValue;
            }
        } else if (this.mFormula == null) {
            try {
                if (ValueUtils.isNumberChars(this.mText)) {
                    return Double.valueOf(sInputNumberFormat.parse(this.mText.trim()).doubleValue());
                } else if (this.mText != null && this.mText.trim().length() != 0) {
                    Date e = ValueUtils.convert2Date(this.mText, false);
                    return e != null ? e : null;
                } else {
                    return Double.valueOf(0.0D);
                }
            } catch (ParseException var3) {
                return CellErrorValue.VALUE;
            }
        } else {
            return sZeroDouble;
        }
    }

    public Object getObjectValue() {
        return this.mFormula != null ? this.mValue : (this.mValue == null ? this.mText : this.mValue);
    }

    public Object getPostValue() {
        return this.mFormula != null ? (this.mValue == null ? sZeroDouble : this.mValue) : (this.mValue == null ? (this.mText == null ? "" : this.mText) : this.mValue);
    }

    public void setPostValue(Object value) {
        this.setText(String.valueOf(value));
    }

    public CPChartFactory getCPChartFactory() {
        return this.mCPChartFactory;
    }

    public void setCPChartFactory(CPChartFactory CPChartFactory) {
        this.mCPChartFactory = CPChartFactory;
    }

    public void createInternalChart() {
        if (this.mCPChartFactory != null) {
            CPChart chart = this.mCPChartFactory.createChart(this.mWorksheet);
            this.setValue(chart);
        }

        this.mCPChartFactory = null;
    }

    public CPImageFactory getCPImageFactory() {
        return this.mCPImageFactory;
    }

    public void setCPImageFactory(CPImageFactory CPImageFactory) {
        this.mCPImageFactory = CPImageFactory;
    }

    public void createInternalImage() {
        if (this.mCPImageFactory != null) {
            CPImage image = this.mCPImageFactory.createImage(this.mWorksheet);
            this.setValue(image);
        }

        this.mCPImageFactory = null;
    }

    public void writeXml(Writer out) throws IOException {
        if (!GeneralUtils.isEmptyOrNull(this.mText) || !GeneralUtils.isEmptyOrNull(this.mInputMapping) || !GeneralUtils.isEmptyOrNull(this.mOutputMapping) || this.mValue instanceof WorksheetEmbeddedObject) {
            out.write("<cell row=\"" + this.mRow + "\" column=\"" + this.mColumn + "\"");
            if (this.mText != null) {
                out.write(" text=\"" + XmlUtils.escapeStringForXML(this.mText) + "\"");
            }

            if (this.mInputMapping != null) {
                out.write(" inputMapping=\"" + XmlUtils.escapeStringForXML(this.mInputMapping) + "\"");
            }

            if (this.mOutputMapping != null) {
                out.write(" outputMapping=\"" + XmlUtils.escapeStringForXML(this.mOutputMapping) + "\"");
            }

            out.write(" >");
            if (this.mValue instanceof WorksheetEmbeddedObject) {
                ((WorksheetEmbeddedObject) this.mValue).writeXml(out);
            }

            out.write("</cell>");
        }
    }

    public String getColRow(Worksheet worksheet) {
        return worksheet.getColumnName(this.mColumn) + (this.mRow + 1);
    }

    public void postColumnInsert(Worksheet targetWorksheet, int startColumnIndex, int numColumns) {
        if (this.getFormula().isInsertColumnRelevant(this, targetWorksheet, startColumnIndex, numColumns)) {
            this.getFormula().getNewFormulaForColumnInsert(this, targetWorksheet, startColumnIndex, numColumns);
        }

    }

    public void postColumnRemoval(Worksheet targetWorksheet, int startColumnIndex, int numColumns) {
        if (this.getFormula().isRemoveColumnRelevant(this, targetWorksheet, startColumnIndex, numColumns)) {
            this.getFormula().getNewFormulaForColumnRemoval(this, targetWorksheet, startColumnIndex, numColumns);
        }

    }

    public void postRowInsert(Worksheet targetWorksheet, int startRowIndex, int numRows) {
        if (this.getFormula().isInsertRowRelevant(this, targetWorksheet, startRowIndex, numRows)) {
            this.getFormula().getNewFormulaForRowInsert(this, targetWorksheet, startRowIndex, numRows);
        }

    }

    public void postRowRemoval(Worksheet targetWorksheet, int startRowIndex, int numRows) {
        if (this.getFormula().isRemoveRowRelevant(this, targetWorksheet, startRowIndex, numRows)) {
            this.getFormula().getNewFormulaForRowRemoval(this, targetWorksheet, startRowIndex, numRows);
        }

    }

    public int hashCode() {
        return this.mWorksheet.hashCode() + this.mRow + this.mColumn;
    }

    public boolean isPostValueChanged() {
        Object originalValue = this.getWorksheet().queryOriginalCellValue(this);
        return originalValue != null && originalValue instanceof Number && this.getValue() instanceof Number ? ((Number) originalValue).doubleValue() != ((Number) this.getValue()).doubleValue() : GeneralUtils.isDifferent(originalValue, this.getValue());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Cell)) {
            return false;
        } else {
            Cell other = (Cell) obj;
            return this.mWorksheet == other.mWorksheet && this.mRow == other.mRow && this.mColumn == other.mColumn;
        }
    }

    public static CompositeCellFormat getDefaultFormat() {
        return sDefaultFormat;
    }

    public boolean isInvertedValue() {
        return invertedValue;
    }

    public void setInvertedValue(boolean invertedValue) {
        this.invertedValue = invertedValue;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }

    public void setTags(String mTags) {
        this.mTags = new ArrayList<String>();
        String[] tagsArray = mTags.split(",");
        for (String tag: tagsArray) {
            this.mTags.add(tag);
        }
    }

    public void addTag(String tag) {
        if (this.mTags == null) {
            this.mTags = new ArrayList<String>();
        }
        if (!this.mTags.contains(tag)) {
            this.mTags.add(tag);
        }
    }

    public void setTagsList(ArrayList<String> tagsList) {
        this.mTags = tagsList;
    }

    public boolean isToEvaluate() {
        return toEvaluate;
    }

    public void setToEvaluate(boolean toEvaluate) {
        this.toEvaluate = toEvaluate;
    }

    public String[][] getValidationMessages() {
        return validationMessages;
    }

    public void setValidationMessages(String[][] validationMessages) {
        this.validationMessages = validationMessages;
    }

}
