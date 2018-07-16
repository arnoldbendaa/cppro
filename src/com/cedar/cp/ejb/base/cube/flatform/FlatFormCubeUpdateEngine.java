// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.flatform;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.ejb.base.cube.flatform.DimensionLookup;
import com.cedar.cp.ejb.base.cube.flatform.WorkbookUpdate;
import com.cedar.cp.ejb.base.cube.flatform.WorkbookUpdateXMLReader;
import com.cedar.cp.ejb.base.cube.flatform.WorksheetCellUpdate;
import com.cedar.cp.ejb.base.cube.flatform.WorksheetUpdate;
import com.cedar.cp.ejb.impl.dataentry.DataEntryDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.util.DateUtils;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.StringUtils;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;

import java.io.Reader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FlatFormCubeUpdateEngine {

    private static final String sCubeUpdateXML = "<CubeUpdate>\n<UserId>{0}</UserId>\n<FinanceCubeId>{1}</FinanceCubeId>\n<AbsoluteValues>true</AbsoluteValues>\n<UpdateType>0</UpdateType>\n<BudgetCycleId>{2}</BudgetCycleId>\n<Cells>\n{3}{4}</Cells>\n</CubeUpdate>";
    private static final String sFinanceCellLineXML = "<Cell addr=\"{0}\" dataType=\"{1}\" delta=\"{2}\"/>\n";
    private static final String sNumberCellLineXML = "<Cell addr=\"{0}\" dataType=\"{1}\"       numberValue=\"{2}\" />\n";
    private static final String sStringCellLineXML = "<Cell addr=\"{0}\" dataType=\"{1}\"       stringValue=\"{2}\" />\n";
    private static final String sTimeCellLineXML = "<Cell addr=\"{0}\" dataType=\"{1}\"       timeValue=\"{2}\" />\n";
    private static final String sDateCellLineXML = "<Cell addr=\"{0}\" dataType=\"{1}\"       dateValue=\"{2}\" />\n";
    private static final String sDateTimeCellLineXML = "<Cell addr=\"{0}\" dataType=\"{1}\"       dateTimeValue=\"{2}\" />\n";
    private static final String sBooleanCellLineXML = "<Cell addr=\"{0}\" dataType=\"{1}\"       booleanValue=\"{2}\" />\n";
    private static final String sCellNoteLineXML = "<CellNote addr=\"{0}\" dataType=\"{1}\" userName=\"{2}\"><![CDATA[{3}]]></CellNote>";
    private int mTaskId;
    private SimpleDateFormat mInputDateFormatter;
    private SimpleDateFormat mTimeFormatter;
    private SimpleDateFormat mDateFormatter;
    private SimpleDateFormat mDateTimeFormatter;
    private transient Log mLog = new Log(this.getClass());

    public String getEntityName() {
        return "FlatFormCubeUpdateEngine";
    }

    public void updateCube(StringReader xmlReader) throws Exception {
        long start = System.currentTimeMillis();
        WorkbookUpdate workbookUpdate = this.parseXMLDocument(xmlReader);
        long end = System.currentTimeMillis();
        this.mLog.info("flatformCubeUpdateEngine", "Parsed XML document in " + (end - start) + " milliseconds");
        start = System.currentTimeMillis();
        workbookUpdate.replaceParameterMacros();
        workbookUpdate.insertContextVariables();
        end = System.currentTimeMillis();
        this.mLog.info("flatformCubeUpdateEngine", "replaced parameter macros and context variables in " + (end - start) + " milliseconds");
        start = System.currentTimeMillis();
        Map dataTypeMap = this.queryDataTypeInfo(workbookUpdate);
        end = System.currentTimeMillis();
        this.mLog.info("flatformCubeUpdateEngine", "lookup data types " + (end - start) + " milliseconds");
        start = System.currentTimeMillis();
        Map lookupInfo = workbookUpdate.queryLookupInfo();
        end = System.currentTimeMillis();
        this.mLog.info("flatformCubeUpdateEngine", "Calculated required internal lookup set in " + (end - start) + " milliseconds");
        start = System.currentTimeMillis();
        (new StructureElementDAO()).queryVisualIds(lookupInfo);
        end = System.currentTimeMillis();
        this.mLog.info("flatformCubeUpdateEngine", "Queried internal ids in " + (end - start) + " milliseconds");
        start = System.currentTimeMillis();
        Iterator<WorksheetUpdate> i$ = workbookUpdate.getWorksheetUpdates().iterator();

        while (i$.hasNext()) {
            WorksheetUpdate worksheetUpdate = (WorksheetUpdate) i$.next();
            this.updateCube(workbookUpdate, worksheetUpdate, lookupInfo, dataTypeMap);
        }

        end = System.currentTimeMillis();
        this.mLog.info("flatformCubeUpdateEngine", "Performed cube update(s) in " + (end - start) + " milliseconds");
    }

    private WorkbookUpdate parseXMLDocument(StringReader xmlReader) throws Exception {
        WorkbookUpdateXMLReader reader = new WorkbookUpdateXMLReader();
        reader.initConfigDigester();
        reader.parseConfigFile(xmlReader);
        return reader.getWorkbookUpdate();
    }

    private void updateCube(WorkbookUpdate workbookUpdate, WorksheetUpdate worksheetUpdate, Map<String, DimensionLookup> lookupInfo, Map<String, DataTypeEVO> dataTypeInfo) throws Exception {
        int financeCubeId = worksheetUpdate.getFinanceCubeId();
        if (financeCubeId < 0) {
            throw new ValidationException("Worksheet [" + worksheetUpdate.getName() + "] must define a finance cube id in its properties");
        } else {
            //@formatter:off
			String xml = MessageFormat.format(
			        "<CubeUpdate>\n"
			        + "<UserId>{0}</UserId>\n"
			        + "<FinanceCubeId>{1}</FinanceCubeId>\n"
			        + "<AbsoluteValues>true</AbsoluteValues>\n"
			        + "<UpdateType>0</UpdateType>\n"
			        + "<BudgetCycleId>{2}</BudgetCycleId>\n"
			        + "<INVERT_NUMBERS_VALUE>{3}</INVERT_NUMBERS_VALUE>\n"
			        + "<EXCLUDE_DATA_TYPES>{4}</EXCLUDE_DATA_TYPES>\n"
			        + "<Cells>\n{5}{6}</Cells>\n"
			        + "</CubeUpdate>",
					new Object[] { String.valueOf(workbookUpdate.getUserId()),
			                String.valueOf(financeCubeId),
			                String.valueOf(workbookUpdate.getBudgetCycleId()),
			                Boolean.valueOf(workbookUpdate.getParameters().get(WorkbookProperties.INVERT_NUMBERS_VALUE.toString())),
			                String.valueOf(workbookUpdate.getParameters().get(WorkbookProperties.EXCLUDE_DATA_TYPES.toString())),
			                this.generateCellsXML(workbookUpdate, worksheetUpdate, worksheetUpdate.queryDimensionAndHierarchyVisIds(), worksheetUpdate.getDimensionLookup(lookupInfo), dataTypeInfo),
			                ""
			                });
			//@formatter:on

            CubeUpdateEngine cubeUpdateEngine = new CubeUpdateEngine();
            cubeUpdateEngine.setTaskId(Integer.valueOf(this.getTaskId()));
            cubeUpdateEngine.updateCube((Reader) (new StringReader(xml)), true);
        }
    }

    private String generateCellsXML(WorkbookUpdate workbookUpdate, WorksheetUpdate worksheetUpdate, Pair<String, String>[] dimAndHiers, DimensionLookup[] lookupInfo, Map<String, DataTypeEVO> dataTypeInfo) throws Exception {
        StringBuilder sb = new StringBuilder();
        int[] cellIds = new int[dimAndHiers.length];
        Map rollUpRulesMap = null;
        Iterator<WorksheetCellUpdate> i$ = worksheetUpdate.getWorksheetUpdateCells().iterator();

        while (i$.hasNext()) {
            WorksheetCellUpdate cellUpdate = i$.next();
            String[] cellVisIds = cellUpdate.getAddressElements();
            if (cellVisIds.length != dimAndHiers.length + 1) {
                throw new ValidationException("Sheet:[" + worksheetUpdate.getName() + "]" + " row:[" + cellUpdate.getRow() + "]" + " col:[" + cellUpdate.getCol() + "]" + " wrong number of parameters and no context for cost centre.");
            }

            DataTypeEVO dataTypeEVO = (DataTypeEVO) dataTypeInfo.get(cellUpdate.getDataType());
            if (dataTypeEVO == null) {
                throw new ValidationException("Sheet:[" + worksheetUpdate.getName() + "]" + " row:[" + cellUpdate.getRow() + "]" + " col:[" + cellUpdate.getCol() + "]" + " dataType:[" + cellUpdate.getDataType() + "]" + " data type not found.");
            }

            for (int i = 0; i < cellVisIds.length - 1; ++i) {
                cellIds[i] = lookupInfo[i].getLeafStructureElementId((String) dimAndHiers[i].getChild2(), cellVisIds[i]);
                if (cellIds[i] == -1) {
                    throw new ValidationException("Sheet:[" + worksheetUpdate.getName() + "]" + " row:[" + cellUpdate.getRow() + "]" + " col:[" + cellUpdate.getCol() + "]" + " element:[" + cellVisIds[i] + "]" + " not found in hierarchy " + (String) dimAndHiers[i].getChild2());
                }

                if (cellIds[i] == -2) {
                    boolean valid = true;
                    if (dataTypeEVO.isMeasure() || workbookUpdate.getFormType() == 6) {
                        if (dataTypeEVO.isMeasureNumeric()) {
                            if (rollUpRulesMap == null) {
                                FinanceCubeDAO financeCubeDAO = new FinanceCubeDAO();
                                rollUpRulesMap = financeCubeDAO.getRollUpRules(worksheetUpdate.getFinanceCubeId());
                            }

                            valid = !((boolean[]) rollUpRulesMap.get(dataTypeEVO.getVisId()))[i];
                        }
                    } else {
                        valid = false;
                    }

                    if (!valid) {
                        throw new ValidationException("Sheet:[" + worksheetUpdate.getName() + "]" + " row:[" + cellUpdate.getRow() + "]" + " col:[" + cellUpdate.getCol() + "]" + " element:[" + cellVisIds[i] + "]" + " is not a leaf level element in hierarchy " + (String) dimAndHiers[i].getChild2());
                    }

                    cellIds[i] = lookupInfo[i].getStructureElementId((String) dimAndHiers[i].getChild2(), cellVisIds[i]);
                    if (cellIds[i] == -1) {
                        throw new ValidationException("Sheet:[" + worksheetUpdate.getName() + "]" + " row:[" + cellUpdate.getRow() + "]" + " col:[" + cellUpdate.getCol() + "]" + " element:[" + cellVisIds[i] + "]" + " not found in hierarchy " + (String) dimAndHiers[i].getChild2());
                    }
                }
            }

            if (dataTypeEVO.isFinanceValue()) {
                // Global Model Mappings v2 (new argument - company)
                if ((cellUpdate.getAddressElements().length > 4) && (cellUpdate.getAddressElements()[4] != null)) {
                    sb.append(MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\" delta=\"{2}\" company=\"{3}\"/>\n", new Object[] { this.buildAddress(cellIds), cellVisIds[3], cellUpdate.getValue(), cellUpdate.getAddressElements()[4] }));
                } else {
                    sb.append(MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\" delta=\"{2}\"/>\n", new Object[] { this.buildAddress(cellIds), cellVisIds[3], cellUpdate.getValue() }));
                }
            } else if (dataTypeEVO.isMeasure()) {
                if (dataTypeEVO.isMeasureNumeric()) {
                    sb.append(MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\"       numberValue=\"{2}\" />\n", new Object[] { this.buildAddress(cellIds), cellVisIds[cellVisIds.length - 1], cellUpdate.getValue() }));
                } else if (dataTypeEVO.isMeasureString()) {
                    sb.append(MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\"       stringValue=\"{2}\" />\n", new Object[] { this.buildAddress(cellIds), cellVisIds[cellVisIds.length - 1], cellUpdate.getValue() }));
                } else {
                    Date var16;
                    if (dataTypeEVO.isMeasureDate()) {
                        var16 = DateUtils.parseDate(this.getInputDateFormatter(), cellUpdate.getValue());
                        if (var16 == null) {
                            var16 = DateUtils.parseDateTime(this.getInputDateFormatter(), cellUpdate.getValue());
                            if (var16 == null) {
                                throw new ValidationException("Sheet:[" + worksheetUpdate.getName() + "]" + " row:[" + cellUpdate.getRow() + "]" + " col:[" + cellUpdate.getCol() + "]" + " value:[" + cellUpdate.getValue() + "]" + " is not a valid date.");
                            }
                        }

                        sb.append(MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\"       dateValue=\"{2}\" />\n", new Object[] { this.buildAddress(cellIds), cellVisIds[cellVisIds.length - 1], this.getDateFormmatter().format(var16) }));
                    } else if (dataTypeEVO.isMeasureDateTime()) {
                        var16 = DateUtils.parseDateTime(this.getInputDateFormatter(), cellUpdate.getValue());
                        if (var16 == null) {
                            throw new ValidationException("Sheet:[" + worksheetUpdate.getName() + "]" + " row:[" + cellUpdate.getRow() + "]" + " col:[" + cellUpdate.getCol() + "]" + " value:[" + cellUpdate.getValue() + "]" + " is not a valid date time.");
                        }

                        sb.append(MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\"       dateTimeValue=\"{2}\" />\n", new Object[] { this.buildAddress(cellIds), cellVisIds[cellVisIds.length - 1], this.getDateTimeFormatter().format(var16) }));
                    } else if (dataTypeEVO.isMeasureTime()) {
                        var16 = DateUtils.parseTime(this.getInputDateFormatter(), cellUpdate.getValue());
                        if (var16 == null) {
                            throw new ValidationException("Sheet:[" + worksheetUpdate.getName() + "]" + " row:[" + cellUpdate.getRow() + "]" + " col:[" + cellUpdate.getCol() + "]" + " value:[" + cellUpdate.getValue() + "]" + " is not a valid time.");
                        }

                        sb.append(MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\"       timeValue=\"{2}\" />\n", new Object[] { this.buildAddress(cellIds), cellVisIds[cellVisIds.length - 1], this.getTimeFormatter().format(var16) }));
                    } else if (dataTypeEVO.isMeasureBoolean()) {
                        Boolean var17 = StringUtils.parseBoolean(cellUpdate.getValue());
                        if (var17 == null) {
                            throw new ValidationException("Sheet:[" + worksheetUpdate.getName() + "]" + " row:[" + cellUpdate.getRow() + "]" + " col:[" + cellUpdate.getCol() + "]" + " value:[" + cellUpdate.getValue() + "]" + " is not a valid boolean value.");
                        }

                        sb.append(MessageFormat.format("<Cell addr=\"{0}\" dataType=\"{1}\"       booleanValue=\"{2}\" />\n", new Object[] { this.buildAddress(cellIds), cellVisIds[cellVisIds.length - 1], var17.toString() }));
                    }
                }
            }
        }

        return sb.toString();
    }

    private String buildAddress(int[] cellAddress) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < cellAddress.length; ++i) {
            sb.append(String.valueOf(cellAddress[i]));
            if (i < cellAddress.length - 1) {
                sb.append(',');
            }
        }

        return sb.toString();
    }

    private Map<String, DataTypeEVO> queryDataTypeInfo(WorkbookUpdate workbookUpdate) {
        Set dataTypes = workbookUpdate.queryDataTypes();
        if (dataTypes.isEmpty()) {
            return new HashMap();
        } else {
            DataTypeDAO dataTypeDAO = new DataTypeDAO();
            return dataTypeDAO.getDataTypeEVOMap(dataTypes);
        }
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public void setTaskId(int taskId) {
        this.mTaskId = taskId;
    }

    private SimpleDateFormat getTimeFormatter() {
        if (this.mTimeFormatter == null) {
            this.mTimeFormatter = new SimpleDateFormat("HH:mm:ss");
        }

        return this.mTimeFormatter;
    }

    private SimpleDateFormat getDateFormmatter() {
        if (this.mDateFormatter == null) {
            this.mDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        }

        return this.mDateFormatter;
    }

    private SimpleDateFormat getDateTimeFormatter() {
        if (this.mDateTimeFormatter == null) {
            this.mDateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        }

        return this.mDateTimeFormatter;
    }

    private SimpleDateFormat getInputDateFormatter() {
        if (this.mInputDateFormatter == null) {
            this.mInputDateFormatter = new SimpleDateFormat();
        }

        return this.mInputDateFormatter;
    }
}
