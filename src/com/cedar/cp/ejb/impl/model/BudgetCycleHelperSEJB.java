package com.cedar.cp.ejb.impl.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.cedar.cp.api.base.BudgetStatusException;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.facades.FlatForm2Extractor;
import com.cedar.cp.api.facades.FlatFormExtractor;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ChangeBudgetStateResult;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.OnDemandFinanceFormData;
import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.cedar.cp.api.model.ReviewBudgetSelection;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitCheck;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.datatype.DataTypeImpl;
import com.cedar.cp.dto.dimension.AllDimensionsForModelELO;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.dimension.MassStateImmediateChildrenELO;
import com.cedar.cp.dto.dimension.StructureElementELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementParentsELO;
import com.cedar.cp.dto.formnotes.AllFormNotesForFormAndBudgetLocationELO;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetStatePK;
import com.cedar.cp.dto.model.BudgetStateRebuildTaskRequest;
import com.cedar.cp.dto.model.BudgetStateTaskRequest;
import com.cedar.cp.dto.model.BudgetUserCK;
import com.cedar.cp.dto.model.BudgetUserPK;
import com.cedar.cp.dto.model.ChangeBudgetStateResultImpl;
import com.cedar.cp.dto.model.CheckIfHasStateELO;
import com.cedar.cp.dto.model.ModelCK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.OnDemandFinanceFormDataImpl;
import com.cedar.cp.dto.model.ReviewBudgetDetailsImpl;
import com.cedar.cp.dto.model.budgetlimit.BudgetLimitViolationImpl;
import com.cedar.cp.dto.performance.DataReviewPerformanceType;
import com.cedar.cp.dto.systemproperty.SystemPropertyELO;
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkPK;
import com.cedar.cp.ejb.base.async.XMLReportUtils;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dataentry.ContextInputFactory;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.ejb.impl.formnotes.FormNotesDAO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityEVO;
import com.cedar.cp.ejb.impl.model.act.BudgetActivityLinkEVO;
import com.cedar.cp.ejb.impl.model.budgetlimit.BudgetLimitDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDAO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileEVO;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormDAO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormEVO;
import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.NumberFormatter;
import com.cedar.cp.util.StringUtils;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.awt.ColorUtils;
import com.cedar.cp.util.db.DBAccessor;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.reader.XMLReader;
import com.cedar.cp.util.performance.GenericPerformanceType;
import com.cedar.cp.util.performance.PerformanceDatumImpl;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray.CellLink;
import com.cedar.cp.util.task.TaskMessageFactory;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.ColumnGroup;
import com.cedar.cp.util.xmlform.ColumnModelData;
import com.cedar.cp.util.xmlform.DataTypeColumnValue;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import com.cedar.cp.util.xmlform.FixedColumnValue;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.FormContext;
import com.cedar.cp.util.xmlform.Header;
import com.cedar.cp.util.xmlform.OnFormLoad;
import com.cedar.cp.util.xmlform.RowInput;
import com.cedar.cp.util.xmlform.StructureColumnValue;
import com.cedar.cp.util.xmlform.inputs.FormInputModel;
import com.cedar.cp.util.xmlform.inputs.SingleRowFormInputModel;
import com.jxcell.CellException;
import com.jxcell.View;
import com.softproideas.util.validation.MappingFunction;

public class BudgetCycleHelperSEJB extends AbstractSession implements SessionBean {

    private transient Log mLog = new Log(this.getClass());
    private transient DimensionElementDAO mDimensionElementDAO;
    private transient ModelDAO mModelDAO;
    private transient StructureElementDAO mStructureElementDAO;
    private transient ModelEVO mModelEVO;
    private transient BudgetCycleEVO mBudgetCycleEVO;
    private transient BudgetUserEVO mBudgetUserEVO;
    private transient ReviewBudgetDetailsImpl mReviewBudgetDetails;
    private transient DataEntryProfileDAO mDataEntryProfileDAO;
    private transient XmlFormDAO mXmlFormDAO;
    private transient DataEntryProfileEVO mDataEntryProfile;
    private transient XmlFormEVO mXmlFormEVO;
    private transient Map mContextVariables;
    private BudgetLimitCheck mBudgetLimitCheck;

    private void initMemberVariables() {
        this.mModelDAO = null;
        this.mDimensionElementDAO = null;
        this.mDataEntryProfileDAO = null;
        this.mStructureElementDAO = null;
        this.mModelEVO = null;
        this.mBudgetCycleEVO = null;
        this.mBudgetUserEVO = null;
        this.mReviewBudgetDetails = null;
        this.mXmlFormDAO = null;
        this.mDataEntryProfile = null;
        this.mXmlFormEVO = null;
        this.mContextVariables = new HashMap();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ReviewBudgetDetails getReviewBudgetDetails(int userId, String levelPrefix, int topNodeId, int modelId, int budgetCycleId, Object dataEntryProfilePrimaryKey, boolean hasDesignModeSecurity, Map userSelections, String dataType, Map contextVariables, CPConnection conn) {
        this.mLog.debug("getBudgetReviewDetails", "userId=" + userId + " levelPrefix=" + levelPrefix + " topNodeId=" + topNodeId + " modelId=" + modelId + " budgetCycleId=" + budgetCycleId + " dataEntryProfilePrimaryKey=" + dataEntryProfilePrimaryKey + " designModeSecurity=" + hasDesignModeSecurity + " dataType=" + dataType);
        this.setUserId(userId);
        this.initMemberVariables();
        PerformanceDatumImpl perf = null;

        ReviewBudgetDetailsImpl var36;
        try {
            perf = DataReviewPerformanceType.getInstance().createPerformanceDatum(String.valueOf(userId));
            long e = System.currentTimeMillis();
            this.getDataEntryProfileAndForm(dataEntryProfilePrimaryKey);
            if (this.mXmlFormEVO.getDesignMode() && !hasDesignModeSecurity) {
                throw new IllegalStateException("No security to view design mode forms");
            }

            this.mReviewBudgetDetails = new ReviewBudgetDetailsImpl();
            this.getModelAndBudgetCycle(modelId, budgetCycleId, false);
            long e1 = System.currentTimeMillis();
            perf.setAdditonalInformation("getModelAndBudgetCycle", new Double((double) (e1 - e)));
            DimensionDAO dao = new DimensionDAO();
            AllDimensionsForModelELO dimensions = dao.getAllDimensionsForModel(this.mModelEVO.getModelId());
            this.mReviewBudgetDetails.setDimensionsSelection(dimensions);
            int[] selections = new int[this.mModelEVO.getModelDimensionRels().size()];
            selections[0] = topNodeId;
            int[] profileSelections = this.mDataEntryProfile.getStructureElementIdArray();

            for (int iter = 1; iter < selections.length; ++iter) {
                selections[iter] = profileSelections[iter - 1];
            }

            Entry structureElementId;
            int periodId;
            for (Iterator var33 = userSelections.entrySet().iterator(); var33.hasNext(); selections[periodId] = ((Integer) structureElementId.getValue()).intValue()) {
                structureElementId = (Entry) var33.next();
                periodId = ((Integer) structureElementId.getKey()).intValue();
            }

            int var35 = selections[0];
            this.getBudgetUser(modelId, var35, userId);
            this.setModel();
            this.setBudgetCycle();
            periodId = selections[selections.length - 1];
            if (periodId == 0) {
                periodId = this.mBudgetCycleEVO.getPeriodIdTo();
                selections[selections.length - 1] = periodId;
            }

            this.mReviewBudgetDetails.setPeriod(periodId);
            if (dataType == null || dataType.trim().length() == 0) {
                String workbook = this.mDataEntryProfile.getDataType();
                if (workbook != null) {
                    dataType = workbook;
                } else {
                    dataType = this.mBudgetCycleEVO.getXmlFormDataType();
                }
            }

            this.mReviewBudgetDetails.setDataType(dataType);
            this.mContextVariables.putAll(contextVariables);
            this.mContextVariables.put(WorkbookProperties.DATA_TYPE.toString(), dataType);
            if (this.mContextVariables.get(WorkbookProperties.MODEL_ID.toString()) == null) {
                this.mContextVariables.put(WorkbookProperties.MODEL_ID.toString(), Integer.valueOf(modelId));
            }

            if (this.mContextVariables.get(WorkbookProperties.CALENDAR_INFO.toString() + modelId) == null) {
                this.mContextVariables.put(WorkbookProperties.CALENDAR_INFO.toString() + modelId, (new StructureElementDAO()).getCalendarInfoForModel(modelId));
            }

            this.loadFormData(modelId, perf, selections, periodId, dataType);
            this.primeOtherStructureElements(selections);
            this.mReviewBudgetDetails.setSelections(selections);
            this.mReviewBudgetDetails.setSelectionLabels(this.getSelectionLabels(selections));
            this.mReviewBudgetDetails.addContextVariables(this.mContextVariables);
            perf.setDatumPoint("Load Form Data");
            this.setReadOnlyDataTypes();
            this.setSecurityAccessDetails(Integer.valueOf(modelId));
            this.mReviewBudgetDetails.setFormType(this.mXmlFormEVO.getType());
            if (this.mXmlFormEVO.getType() == 3) {
                this.setBudgetLimitViolations(selections[0]);
                this.setCubeDeployments(modelId, this.mReviewBudgetDetails.getFinanceCubeId(), this.mReviewBudgetDetails.getStructureIds(), selections);
                this.setSystemPreferences();
                this.setSheetLevelNote(userId, selections[0]);
            } else if (this.mXmlFormEVO.getType() == 4) {
                com.cedar.cp.util.flatform.model.Workbook workbook1 = this.mReviewBudgetDetails.getWorkbook();
                CPFunctionsEvaluatorImpl engine = new CPFunctionsEvaluatorImpl();
                FlatFormExtractor extractor = new FlatFormExtractor(engine, workbook1);
                extractor.extract(this.mContextVariables.get(WorkbookProperties.DIMENSION_0_VISID.toString()).toString(), this.mContextVariables);
            } else if (this.mXmlFormEVO.getType() == 6) {
                com.cedar.cp.util.flatform.model.Workbook workbook = this.mReviewBudgetDetails.getWorkbook();
                workbook = updateMapping(this.mReviewBudgetDetails.getExcelFile(), workbook);
                FlatForm2Extractor extractor = new FlatForm2Extractor(conn, workbook);
                extractor.extract(this.mContextVariables.get(WorkbookProperties.DIMENSION_0_VISID.toString()).toString(), this.mContextVariables);
            }

            this.updateDataEntryProfileHistory(userId, modelId, budgetCycleId, topNodeId);
            var36 = this.mReviewBudgetDetails;
        } catch (EJBException var30) {
            throw var30;
        } catch (Exception var31) {
            throw new EJBException(var31);
        } finally {
            this.setUserId(-1);
            if (perf != null) {
                perf.completed();
            }

        }

        return var36;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ReviewBudgetDetails getReviewBudgetDetails(int userId, String levelPrefix, int topNodeId, int modelId, int budgetCycleId, Object dataEntryProfilePrimaryKey, boolean hasDesignModeSecurity, Map userSelections, String dataType, Map contextVariables) {
        this.mLog.debug("getBudgetReviewDetails", "userId=" + userId + " levelPrefix=" + levelPrefix + " topNodeId=" + topNodeId + " modelId=" + modelId + " budgetCycleId=" + budgetCycleId + " dataEntryProfilePrimaryKey=" + dataEntryProfilePrimaryKey + " designModeSecurity=" + hasDesignModeSecurity + " dataType=" + dataType);
        this.setUserId(userId);
        this.initMemberVariables();
        PerformanceDatumImpl perf = null;

        ReviewBudgetDetailsImpl var36;
        try {
            perf = DataReviewPerformanceType.getInstance().createPerformanceDatum(String.valueOf(userId));
            long e = System.currentTimeMillis();
            this.getDataEntryProfileAndForm(dataEntryProfilePrimaryKey);
            if (this.mXmlFormEVO.getDesignMode() && !hasDesignModeSecurity) {
                throw new IllegalStateException("No security to view design mode forms");
            }

            this.mReviewBudgetDetails = new ReviewBudgetDetailsImpl();
            this.getModelAndBudgetCycle(modelId, budgetCycleId, false);
            long e1 = System.currentTimeMillis();
            perf.setAdditonalInformation("getModelAndBudgetCycle", new Double((double) (e1 - e)));
            DimensionDAO dao = new DimensionDAO();
            AllDimensionsForModelELO dimensions = dao.getAllDimensionsForModel(this.mModelEVO.getModelId());
            this.mReviewBudgetDetails.setDimensionsSelection(dimensions);
            int[] selections = new int[this.mModelEVO.getModelDimensionRels().size()];
            selections[0] = topNodeId;
            int[] profileSelections = this.mDataEntryProfile.getStructureElementIdArray();

            for (int iter = 1; iter < selections.length; ++iter) {
                selections[iter] = profileSelections[iter - 1];
            }

            Entry structureElementId;
            int periodId;
            for (Iterator var33 = userSelections.entrySet().iterator(); var33.hasNext(); selections[periodId] = ((Integer) structureElementId.getValue()).intValue()) {
                structureElementId = (Entry) var33.next();
                periodId = ((Integer) structureElementId.getKey()).intValue();
            }

            int var35 = selections[0];
            this.getBudgetUser(modelId, var35, userId);
            this.setModel();
            this.setBudgetCycle();
            periodId = selections[selections.length - 1];
            if (periodId == 0) {
                periodId = this.mBudgetCycleEVO.getPeriodIdTo();
                selections[selections.length - 1] = periodId;
            }

            this.mReviewBudgetDetails.setPeriod(periodId);
            if (dataType == null || dataType.trim().length() == 0) {
                String workbook = this.mDataEntryProfile.getDataType();
                if (workbook != null) {
                    dataType = workbook;
                } else {
                    dataType = this.mBudgetCycleEVO.getXmlFormDataType();
                }
            }

            this.mReviewBudgetDetails.setDataType(dataType);
            this.mContextVariables.putAll(contextVariables);
            this.mContextVariables.put(WorkbookProperties.DATA_TYPE.toString(), dataType);
            if (this.mContextVariables.get(WorkbookProperties.MODEL_ID.toString()) == null) {
                this.mContextVariables.put(WorkbookProperties.MODEL_ID.toString(), Integer.valueOf(modelId));
            }

            if (this.mContextVariables.get(WorkbookProperties.CALENDAR_INFO.toString() + modelId) == null) {
                this.mContextVariables.put(WorkbookProperties.CALENDAR_INFO.toString() + modelId, (new StructureElementDAO()).getCalendarInfoForModel(modelId));
            }

            this.loadFormData(modelId, perf, selections, periodId, dataType);
            this.primeOtherStructureElements(selections);
            this.mReviewBudgetDetails.setSelections(selections);
            this.mReviewBudgetDetails.setSelectionLabels(this.getSelectionLabels(selections));
            this.mReviewBudgetDetails.addContextVariables(this.mContextVariables);
            perf.setDatumPoint("Load Form Data");
            this.setReadOnlyDataTypes();
            this.setSecurityAccessDetails(Integer.valueOf(modelId));
            this.mReviewBudgetDetails.setFormType(this.mXmlFormEVO.getType());
            if (this.mXmlFormEVO.getType() == 3) {
                this.setBudgetLimitViolations(selections[0]);
                this.setCubeDeployments(modelId, this.mReviewBudgetDetails.getFinanceCubeId(), this.mReviewBudgetDetails.getStructureIds(), selections);
                this.setSystemPreferences();
                this.setSheetLevelNote(userId, selections[0]);
            } else if (this.mXmlFormEVO.getType() == 4) {
                com.cedar.cp.util.flatform.model.Workbook workbook1 = this.mReviewBudgetDetails.getWorkbook();
                CPFunctionsEvaluatorImpl engine = new CPFunctionsEvaluatorImpl();
                FlatFormExtractor extractor = new FlatFormExtractor(engine, workbook1);
                extractor.extract(this.mContextVariables.get(WorkbookProperties.DIMENSION_0_VISID.toString()).toString(), this.mContextVariables);
            }

            this.updateDataEntryProfileHistory(userId, modelId, budgetCycleId, topNodeId);
            var36 = this.mReviewBudgetDetails;
        } catch (EJBException var30) {
            throw var30;
        } catch (Exception var31) {
            throw new EJBException(var31);
        } finally {
            this.setUserId(-1);
            if (perf != null) {
                perf.completed();
            }

        }

        return var36;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ReviewBudgetDetails getReviewBudgetDetails(int userId, String levelPrefix, int modelId, int budgetCycleId, Map userSelections, String dataType, int formId, Map contextVariables) {
        this.initMemberVariables();
        PerformanceDatumImpl perf = null;

        try {
            this.setUserId(userId);
            perf = DataReviewPerformanceType.getInstance().createPerformanceDatum(String.valueOf(userId));
            this.mReviewBudgetDetails = new ReviewBudgetDetailsImpl();
            this.getModelAndBudgetCycle(modelId, budgetCycleId, false);
            this.mXmlFormEVO = (new XmlFormDAO()).getDetails(new XmlFormPK(formId), "");
            DimensionDAO e = new DimensionDAO();
            AllDimensionsForModelELO dimensions = e.getAllDimensionsForModel(this.mModelEVO.getModelId());
            this.mReviewBudgetDetails.setDimensionsSelection(dimensions);
            int[] selections = new int[this.mModelEVO.getModelDimensionRels().size()];

            int periodId;
            int workbook;
            for (Iterator iter = userSelections.entrySet().iterator(); iter.hasNext(); selections[periodId] = workbook) {
                Entry structureElementId = (Entry) iter.next();
                periodId = ((Integer) structureElementId.getKey()).intValue();
                workbook = ((Integer) structureElementId.getValue()).intValue();
            }

            int structureElementId1 = selections[0];
            this.getBudgetUser(modelId, structureElementId1, userId);
            this.setModel();
            this.setBudgetCycle();
            periodId = selections[selections.length - 1];
            if (periodId == 0) {
                periodId = this.mBudgetCycleEVO.getPeriodId();
                selections[selections.length - 1] = periodId;
            }

            this.mReviewBudgetDetails.setPeriod(periodId);
            if (dataType == null || dataType.trim().length() == 0) {
                dataType = this.mBudgetCycleEVO.getXmlFormDataType();
            }

            this.mReviewBudgetDetails.setDataType(dataType);
            this.mContextVariables.putAll(contextVariables);
            this.mContextVariables.put(WorkbookProperties.DATA_TYPE.toString(), dataType);
            if (this.mContextVariables.get(WorkbookProperties.MODEL_ID.toString()) == null) {
                this.mContextVariables.put(WorkbookProperties.MODEL_ID.toString(), Integer.valueOf(modelId));
            }

            if (this.mContextVariables.get(WorkbookProperties.CALENDAR_INFO.toString() + modelId) == null) {
                this.mContextVariables.put(WorkbookProperties.CALENDAR_INFO.toString() + modelId, (new StructureElementDAO()).getCalendarInfoForModel(modelId));
            }

            this.loadFormData(modelId, perf, selections, periodId, dataType);
            this.primeOtherStructureElements(selections);
            this.mReviewBudgetDetails.setSelections(selections);
            this.mReviewBudgetDetails.setSelectionLabels(this.getSelectionLabels(selections));
            this.mReviewBudgetDetails.addContextVariables(this.mContextVariables);
            this.setReadOnlyDataTypes();
            this.setSecurityAccessDetails(Integer.valueOf(modelId));
            this.mReviewBudgetDetails.setFormType(this.mXmlFormEVO.getType());
            if (this.mXmlFormEVO.getType() == 3) {
                this.setCubeDeployments(modelId, this.mReviewBudgetDetails.getFinanceCubeId(), this.mReviewBudgetDetails.getStructureIds(), selections);
                this.setSystemPreferences();
                this.setSheetLevelNote(userId, selections[0]);
            } else if (this.mXmlFormEVO.getType() == 4) {
                com.cedar.cp.util.flatform.model.Workbook workbook1 = this.mReviewBudgetDetails.getWorkbook();
                CPFunctionsEvaluatorImpl engine = new CPFunctionsEvaluatorImpl();
                FlatFormExtractor extractor = new FlatFormExtractor(engine, workbook1);
                extractor.extract(this.mContextVariables.get(WorkbookProperties.DIMENSION_0_VISID.toString()).toString(), this.mContextVariables);
            } else if (this.mXmlFormEVO.getType() == 6) {
                com.cedar.cp.util.flatform.model.Workbook workbook1 = this.mReviewBudgetDetails.getWorkbook();
                CPFunctionsEvaluatorImpl engine = new CPFunctionsEvaluatorImpl();
                //XcellFormExtractor extractor = new XcellFormExtractor(this.getCPConnection(), workbook1);//old extractor
                FlatForm2Extractor extractor = new FlatForm2Extractor(this.getCPConnection(), workbook1);//new exrtactor
                extractor.extract(dimensions.getValueAt(0, "VisId").toString(), this.mContextVariables);
            }

        } catch (EJBException var23) {
            throw var23;
        } catch (Exception var24) {
            throw new EJBException(var24);
        } finally {
            this.setUserId(-1);
            if (perf != null) {
                perf.completed();
            }

        }

        return this.mReviewBudgetDetails;
    }

    public OnDemandFinanceFormData getFinanceFormDataRows(int userId, int modelId, int budgetCycleId, FinanceCubeInput config, int sheetProtectionLevel, int[] treeStructureIndexes, String contextDataType, int xAxisIndex, int[] structureElementIds, int childrenDepth, boolean secondaryStructure, boolean noData, int[] relevantCellCalcs, Map<String, DataType> dataTypes, Map<Integer, EntityList> securityAccessDetails, CalendarInfo calInfo) {
        this.setUserId(userId);
        ArrayList debugList = new ArrayList();
        int[] t = relevantCellCalcs;
        int data = relevantCellCalcs.length;

        for (int formContext = 0; formContext < data; ++formContext) {
            int e = t[formContext];
            debugList.add(Integer.valueOf(e));
        }

        this.mLog.debug("getFinanceFormDataRows", "Relevant Cell Calculations " + debugList.toString());
        Timer var41 = new Timer();
        this.mLog.debug("getFinanceFormDataRows", Thread.currentThread().getName() + " +++getFinanceFormDataRows");
        this.initMemberVariables();
        OnDemandFinanceFormDataImpl var40 = new OnDemandFinanceFormDataImpl();
        MyFormContext var42 = null;

        try {
            this.getModelAndBudgetCycle(modelId, 0, false);
            var42 = new MyFormContext();
            Object var43 = null;
            StructureElementDAO structureDao = new StructureElementDAO();
            Map seEvoMap = structureDao.getStructureElements(structureElementIds);
            Timer positions;
            if (noData) {
                positions = new Timer();
                Integer i = (Integer) config.getAxisDimensionIndexes().get(config.getAxisDimensionIndexes().size() - 1);
                if (xAxisIndex == i.intValue()) {
                    config.setModelId(modelId);
                    var43 = var42.getFinanceFormDataRows(userId, config, budgetCycleId, contextDataType, xAxisIndex, structureElementIds, -1, secondaryStructure, calInfo, dataTypes, securityAccessDetails);
                    var40.addPerformanceStat("FormLoadTimer elapsed time " + positions.stop());
                }

                if (var43 == null || ((FormInputModel) var43).getRowCount() == 0) {
                    StructureElementEVO evo = (StructureElementEVO) seEvoMap.get(Integer.valueOf(structureElementIds[xAxisIndex]));
                    var43 = new SingleRowFormInputModel(new BigDecimal(xAxisIndex), new BigDecimal(evo.getStructureElementId()), new BigDecimal(evo.getPosition()), evo.getVisId(), evo.getDescription(), new BigDecimal(evo.getDepth()), evo.getLeaf() ? "Y" : " ", evo.getIsCredit() ? "Y" : " ", evo.getDisabled() ? "Y" : " ", evo.getNotPlannable() ? "Y" : " ", new BigDecimal(0));
                    var40.addPerformanceStat("SingleRowFormInputModel elapsed time " + positions.stop());
                    Timer calcDeploymentsTimer = new Timer();
                    if (relevantCellCalcs.length == 0) {
                        var40.setNoCellCalcDeploymentsAllowed(true);
                    }

                    for (int deploymentDao = 0; deploymentDao < structureElementIds.length - 1; ++deploymentDao) {
                        if (deploymentDao != xAxisIndex) {
                            int selections = structureElementIds[deploymentDao];
                            if (!this.isStructureElementLeafElement(deploymentDao, selections)) {
                                var40.setNoCellCalcDeploymentsAllowed(true);
                                break;
                            }
                        }
                    }

                    if (!var40.isNoCellCalcDeploymentsAllowed() && xAxisIndex != structureElementIds.length - 1) {
                        CcDeploymentDAO var46 = new CcDeploymentDAO();
                        int[] var48 = new int[structureElementIds.length - 1];

                        for (int checkElements = 0; checkElements < var48.length; ++checkElements) {
                            var48[checkElements] = structureElementIds[checkElements];
                        }

                        int[] var47 = new int[((FormInputModel) var43).getRowCount()];

                        for (int cellDeployments = 0; cellDeployments < ((FormInputModel) var43).getRowCount(); ++cellDeployments) {
                            BigDecimal id = (BigDecimal) ((FormInputModel) var43).getValueAt(cellDeployments, 9);
                            var47[cellDeployments] = id.intValue();
                        }

                        List[] var49 = var46.getTargetCellCalculations(modelId, var48, var47, xAxisIndex, relevantCellCalcs);
                        var40.setCellCalculationDeployments(var49);
                        var40.addPerformanceStat("CalcDeploymentsTimer elapsed time " + calcDeploymentsTimer.stop());
                    }
                }
            } else {
                positions = new Timer();
                config.setModelId(modelId);
                var43 = var42.getFinanceFormDataRows(userId, config, budgetCycleId, contextDataType, xAxisIndex, structureElementIds, childrenDepth, secondaryStructure, calInfo, dataTypes, securityAccessDetails);
                var40.addPerformanceStat("FormLoadTimer elapsed time " + positions.stop());
            }

            var40.setFormInputModel((FormInputModel) var43);
            var40.setSheetProtectionLevel(((FormInputModel) var43).getSheetProtectionLevel());
            var40.setForSecondaryStructure(secondaryStructure);
            int[] var45 = new int[structureElementIds.length];

            for (int var44 = 0; var44 < var45.length; ++var44) {
                var45[var44] = ((StructureElementEVO) seEvoMap.get(Integer.valueOf(structureElementIds[var44]))).getPosition();
            }

            var40.setPositions(var45);
            if (relevantCellCalcs.length == 0) {
                var40.setNoCellCalcDeploymentsAllowed(true);
            }
        } catch (EJBException var37) {
            throw var37;
        } catch (Exception var38) {
            throw new EJBException(var38);
        } finally {
            if (var42 != null) {
                var42.close();
            }

        }

        var40.addPerformanceStat("Total elapsed time " + var41.stop());
        return var40;
    }

    private com.cedar.cp.util.flatform.model.Workbook loadWorkbookFromXML(String xml) throws Exception {
        XMLReader reader = new XMLReader();
        reader.init();
        StringReader sr = new StringReader(xml);
        reader.parseConfigFile(sr);
        return reader.getWorkbook();
    }

    private boolean isStructureElementLeafElement(int dimIndex, int structureElementId) throws Exception {
        DimensionElementDAO dimDao = this.getDimensionElementDAO();
        int dimId = this.getModelDimensionId(dimIndex);
        return dimDao.doesDimensionElementExist(dimId, structureElementId);
    }

    private int getModelDimensionId(int index) {
        Object[] dimRels = this.mModelEVO.getModelDimensionRels().toArray();

        for (int dim = 0; dim < dimRels.length; ++dim) {
            ModelDimensionRelEVO mdrelEvo = (ModelDimensionRelEVO) dimRels[dim];
            if (mdrelEvo.getDimensionSeqNum() == index) {
                return mdrelEvo.getDimensionId();
            }
        }

        return -1;
    }

    private void setSheetLevelNote(int currUserId, int cc) {
        FormNotesDAO dao = new FormNotesDAO();
        AllFormNotesForFormAndBudgetLocationELO elo = dao.getAllFormNotesForFormAndBudgetLocation(this.mXmlFormEVO.getXmlFormId(), cc);
        this.mReviewBudgetDetails.setSheetLevelNoteRead(false);
        if (elo.getNumRows() == 0) {
            this.mReviewBudgetDetails.setSheetLevelNoteRead(true);
        } else {
            UserRef userRef = (UserRef) elo.getValueAt(elo.getNumRows() - 1, "User");
            Timestamp var10000 = (Timestamp) elo.getValueAt(elo.getNumRows() - 1, "CreatedTime");
            String note = (String) elo.getValueAt(elo.getNumRows() - 1, "Note");
            int userId = ((UserPK) userRef.getPrimaryKey()).getUserId();
            String userName = userRef.getNarrative();
            if (userId == currUserId) {
                this.mReviewBudgetDetails.setSheetLevelNoteRead(true);
            }

            this.mReviewBudgetDetails.setSheetLevelNoteSummary(StringUtils.left(note, 100));
        }

    }

    private void setSystemPreferences() throws Exception {
        SystemPropertyDAO dao = new SystemPropertyDAO();
        SystemPropertyELO elo = dao.getSystemProperty("USR: Credit Colour");
        int creditColor = 16711680;
        if (elo.getNumRows() > 0) {
            String debitColor = (String) elo.getValueAt(0, "Value");

            try {
                creditColor = NumberFormatter.parseHexColor(debitColor);
            } catch (NumberFormatException var14) {
                this.mLog.warn("setSystemPreferences", "Can\'t parse credit color " + debitColor);
            }
        }

        this.mReviewBudgetDetails.setCreditColor(creditColor);
        int debitColor1 = 0;
        elo = dao.getSystemProperty("USR: Debit Colour");
        if (elo.getNumRows() > 0) {
            String negateCreditSign = (String) elo.getValueAt(0, "Value");

            try {
                debitColor1 = NumberFormatter.parseHexColor(negateCreditSign);
            } catch (NumberFormatException var13) {
                this.mLog.warn("setSystemPreferences", "Can\'t parse debit color " + negateCreditSign);
            }
        }

        this.mReviewBudgetDetails.setDebitColor(debitColor1);
        boolean negateCreditSign1 = true;
        elo = dao.getSystemProperty("USR: Negate Credit Sign");
        if (elo.getNumRows() > 0) {
            String allocThreshold = (String) elo.getValueAt(0, "Value");
            negateCreditSign1 = Boolean.valueOf(allocThreshold).booleanValue();
        }

        this.mReviewBudgetDetails.setNegateCreditSign(negateCreditSign1);
        elo = dao.getSystemProperty("ALLOC: Threshold");
        double allocThreshold1 = 0.0D;
        if (elo.getNumRows() > 0) {
            String popDialog = (String) elo.getValueAt(0, "Value");

            try {
                allocThreshold1 = NumberFormatter.parseDouble(popDialog);
            } catch (NumberFormatException var12) {
                this.mLog.warn("setSystemPreferences", "Can\'t parse ALLOC: Threshold " + popDialog);
            }
        }

        this.mReviewBudgetDetails.setSpreadPrecisionThreshold(allocThreshold1);
        elo = dao.getSystemProperty("WEB: DATA_ENTRY_PROFILE_MODE");
        boolean popDialog1 = true;
        if (elo.getNumRows() > 0) {
            String value = (String) elo.getValueAt(0, "Value");

            try {
                popDialog1 = value.equalsIgnoreCase("Pop dialog");
            } catch (NumberFormatException var11) {
                this.mLog.warn("setSystemPreferences", "Can\'t parse WEB: DATA_ENTRY_PROFILE_MODE " + value);
            }
        }

        this.mReviewBudgetDetails.setPopSpreadDialog(popDialog1);
    }

    private void getDataEntryProfileAndForm(Object key) throws Exception {
        DataEntryProfileCK primaryKey = (DataEntryProfileCK) key;
        this.mDataEntryProfileDAO = new DataEntryProfileDAO();
        this.mDataEntryProfile = this.mDataEntryProfileDAO.getDetails(primaryKey, "");
        this.mXmlFormEVO = (new XmlFormDAO()).getDetails(new XmlFormPK(this.mDataEntryProfile.getXmlformId()), "<0>");
        Map userMap = this.mXmlFormEVO.getUserListMap();
        if (userMap != null && !userMap.isEmpty()) {
            XmlFormUserLinkPK pk = new XmlFormUserLinkPK(this.mXmlFormEVO.getPK().getXmlFormId(), this.mDataEntryProfile.getUserId());
            if (!userMap.containsKey(pk)) {
                throw new ValidationException("No form level user security to access form");
            }
        }

    }

    public void updateDataEntryProfileHistory(int userId, int modelId, int budgetCycleId, int budgetLocationElementId) {
        this.mDataEntryProfileDAO.updateDataEntryProfileHistory(userId, modelId, budgetCycleId, budgetLocationElementId);
    }

    private void getModelAndBudgetCycle(int modelId, int budgetCycleId, boolean getStates) throws Exception {
        ModelPK mpk = new ModelPK(modelId);
        if (budgetCycleId > 0) {
            BudgetCyclePK bcpk = new BudgetCyclePK(budgetCycleId);
            BudgetCycleCK bcck = new BudgetCycleCK(mpk, bcpk);
            if (getStates) {
                this.mModelEVO = this.getModelDAO().getDetails((ModelCK) bcck, "<12>");
            } else {
                this.mModelEVO = this.getModelDAO().getDetails((ModelCK) bcck, "<9>");
            }

            this.mBudgetCycleEVO = this.mModelEVO.getBudgetCyclesItem(bcpk);
        } else {
            this.mModelEVO = this.getModelDAO().getDetails(mpk, "<9>");
        }

    }

    public void getBudgetUser(int modelId, int structureElementId, int userId) {
        BudgetUserDAO dao = new BudgetUserDAO();
        boolean structureId = false;
        int var14 = this.mModelEVO.getBudgetHierarchyId();
        StructureElementDAO strucDAO = new StructureElementDAO();
        StructureElementParentsELO list = strucDAO.getStructureElementParents(var14, structureElementId);

        for (int count = 0; this.mBudgetUserEVO == null && list.getNumRows() > count; ++count) {
            int checkStructureElementId = ((Integer) list.getValueAt(count, "StructureElementId")).intValue();
            BudgetUserPK bupk = new BudgetUserPK(modelId, checkStructureElementId, userId);
            BudgetUserCK buck = new BudgetUserCK(new ModelPK(modelId), bupk);

            try {
                this.mBudgetUserEVO = dao.getDetails(buck, "");
            } catch (Exception var13) {
                this.mBudgetUserEVO = null;
            }
        }

    }

    private void setModel() {
        this.mLog.debug("setModel", this.mModelEVO.getVisId());
        this.mReviewBudgetDetails.setModel(this.mModelEVO.getModelId(), this.mModelEVO.getVisId());
    }

    private void setBudgetCycle() {
        this.mLog.debug("setBudgetCycle", this.mModelEVO.getVisId());
        if (this.mBudgetCycleEVO != null) {
            if (this.mBudgetCycleEVO.getDescription() != null && this.mBudgetCycleEVO.getDescription().length() > 0) {
                this.mReviewBudgetDetails.setBudgetCycle(this.mBudgetCycleEVO.getBudgetCycleId(), this.mBudgetCycleEVO.getDescription());
            } else {
                this.mReviewBudgetDetails.setBudgetCycle(this.mBudgetCycleEVO.getBudgetCycleId(), this.mBudgetCycleEVO.getVisId());
            }

            this.mReviewBudgetDetails.setBudgetCyclePeriodId(this.mBudgetCycleEVO.getPeriodId());
        }
    }

    private StructureElementELO[] getSelectionLabels(int[] selections) {
        StructureElementDAO dao = new StructureElementDAO();
        StructureElementELO[] selectionLabels = new StructureElementELO[selections.length];
        int index = 0;

        for (int elo = 0; elo < selections.length; index = elo++) {
            if (selections[elo] > 0) {
                StructureElementELO elo1 = dao.getStructureElement(selections[elo]);
                selectionLabels[elo] = elo1;
                
                this.mContextVariables.put(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(elo)), elo1.getValueAt(0, "VisId"));
                this.mContextVariables.put(WorkbookProperties.DIMENSION_$_DESCRIPTION.toString().replace("$", String.valueOf(elo)), elo1.getValueAt(0, "Description"));
            }
        }

        if (selections[index] > 0) {
            StructureElementELO var7 = dao.getCalStructureElement(selections[index]);
            selectionLabels[index] = var7;
            this.mContextVariables.put(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(index)), var7.getValueAt(0, "VisId"));
            this.mContextVariables.put(WorkbookProperties.DIMENSION_$_DESCRIPTION.toString().replace("$", String.valueOf(index)), var7.getValueAt(0, "Description"));
        }

        return selectionLabels;
    }

    public StructureElementELO[] getCalendarDetailsLabels(int[] selections) {
        StructureElementDAO dao = new StructureElementDAO();
        StructureElementELO[] selectionLabels = new StructureElementELO[selections.length];

        for (int i = 0; i < selections.length; i++) {
            StructureElementELO var7 = dao.getCalStructureElement(selections[i]);
            selectionLabels[i] = var7;
        }

        return selectionLabels;
    }

    private void primeOtherStructureElements(int[] selections) throws Exception {
        Object[] dimRels = this.mModelEVO.getModelDimensionRels().toArray();
        DimensionDAO dimDao = new DimensionDAO();

        for (int dim = 0; dim < dimRels.length; ++dim) {
            ModelDimensionRelEVO mdrelEvo = (ModelDimensionRelEVO) dimRels[dim];
            int sequence = mdrelEvo.getDimensionSeqNum();
            if (sequence > 0 && sequence < dimRels.length - 1 && selections[sequence] == 0) {
                DimensionEVO dimensionEVO = dimDao.getDetails(new DimensionPK(mdrelEvo.getDimensionId()), "<3>");
                Collection hierarchies = dimensionEVO.getHierarchies();
                if (hierarchies == null || hierarchies.isEmpty()) {
                    throw new IllegalStateException("no hierarchy for dimension " + dimensionEVO);
                }

                HierarchyEVO hierarchyEVO = null;
                if (hierarchies.size() > 1) {
                    String hierChildren = null;
                    if (this.mReviewBudgetDetails.getTreeStructureIndexes() != null) {
                        for (int ser = 0; ser < this.mReviewBudgetDetails.getTreeStructureIndexes().size(); ++ser) {
                            if (((Integer) this.mReviewBudgetDetails.getTreeStructureIndexes().get(ser)).intValue() == sequence) {
                                hierChildren = (String) this.mReviewBudgetDetails.getTreeStructureVisIds().get(ser);
                            }
                        }
                    }

                    if (hierChildren != null) {
                        Iterator var15 = hierarchies.iterator();

                        while (var15.hasNext()) {
                            HierarchyEVO hierEvo = (HierarchyEVO) var15.next();
                            if (hierEvo.getVisId().equals(hierChildren)) {
                                hierarchyEVO = hierEvo;
                            }
                        }

                        if (hierarchyEVO == null) {
                            throw new RuntimeException("form hierarchy <" + hierChildren + "> not found");
                        }
                    }
                } else {
                    Iterator var14 = hierarchies.iterator();
                    hierarchyEVO = (HierarchyEVO) var14.next();
                }

                ImmediateChildrenELO var13 = this.getStructureElementDAO().getImmediateChildren(hierarchyEVO.getHierarchyId(), 0);
                if (var13.getNumRows() > 0) {
                    StructureElementRef var16 = (StructureElementRef) var13.getValueAt(0, "StructureElement");
                    selections[sequence] = ((StructureElementPK) var16.getPrimaryKey()).getStructureElementId();
                }
            }
        }

    }

    private void setSecurityAccessDetails(Integer modelId) {
        this.mReviewBudgetDetails.setSecurityAccessDetails(modelId, (new SecurityAccessDefDAO()).getSecurityAccessDetailsForModel(modelId.intValue()));
    }

    private void setReadOnlyDataTypes() {
        int financeCubeId = this.mReviewBudgetDetails.getFinanceCubeId();
        Map dataTypeEvos = (new DataTypeDAO()).getAllForFinanceCube(financeCubeId);
        HashMap dataTypes = new HashMap();
        Map rollUpRules = (new FinanceCubeDAO()).getRollUpRules(financeCubeId);
        Iterator i$ = dataTypeEvos.values().iterator();

        while (i$.hasNext()) {
            DataTypeEVO evo = (DataTypeEVO) i$.next();
            DataTypeImpl dto = new DataTypeImpl(evo.getPK());
            dto.setMeasureClass(evo.getMeasureClass());
            dto.setMeasureLength(evo.getMeasureLength());
            dto.setMeasureScale(evo.getMeasureScale());
            dto.setMeasureValidation(evo.getMeasureValidation());
            dto.setReadOnlyFlag(evo.getReadOnlyFlag());
            dto.setDescription(evo.getDescription());
            dto.setSubType(evo.getSubType());
            String visId = evo.getVisId();
            dto.setVisId(visId);
            dto.setPropagationRules((boolean[]) rollUpRules.get(visId));
            dataTypes.put(visId, dto);
            if (evo.getReadOnlyFlag() || dto.getSubType() == 2 || dto.getSubType() == 1) {
                this.mReviewBudgetDetails.addReadOnlyDataType(visId);
            }
        }

        this.mReviewBudgetDetails.setDataTypes(dataTypes);
    }

    private void setBudgetLimitViolations(int rootRA) {
        int financeCubeId = this.mReviewBudgetDetails.getFinanceCubeId();
        int numDims = this.mReviewBudgetDetails.getDimensionSelections().length;
        BudgetLimitDAO dao = new BudgetLimitDAO();
        List list = dao.getBudgetLimitsForBudgetLocation(numDims, financeCubeId, rootRA, true);

        int var20;
        for (int headings = 0; headings < list.size(); ++headings) {
            List selections = (List) list.get(headings);
            byte i = 0;
            var20 = i + 1;
            Integer blId = (Integer) selections.get(i);
            ++var20;
            ArrayList structureElements = new ArrayList();

            for (int dataType = 0; dataType < numDims; ++dataType) {
                String minimumValue = (String) selections.get(var20++);
                structureElements.add(minimumValue);
            }

            String var21 = (String) selections.get(var20++);
            BigDecimal var22 = (BigDecimal) selections.get(var20++);
            BigDecimal maximumValue = (BigDecimal) selections.get(var20++);
            BigDecimal currentValue = (BigDecimal) selections.get(var20++);
            BudgetLimitViolationImpl blvImpl = new BudgetLimitViolationImpl();
            blvImpl.setBudgetLimitId(blId);
            blvImpl.setStructureElements(structureElements);
            blvImpl.setDataType(var21);
            blvImpl.setMinimumValue(var22);
            blvImpl.setMaximumValue(maximumValue);
            blvImpl.setCurrentValue(currentValue);
            this.mReviewBudgetDetails.addBudgetLimitViolation(blvImpl);
        }

        if (!list.isEmpty()) {
            ArrayList var18 = new ArrayList();
            ReviewBudgetSelection[] var19 = this.mReviewBudgetDetails.getDimensionSelections();
            var18.add("Who Set Limit");
            System.out.println("selections size" + var19.length);

            for (var20 = 1; var20 < var19.length; ++var20) {
                var18.add(var19[var20].getLabel());
            }

            this.mReviewBudgetDetails.setBudgetLimitViolationStructureHeadings(var18);
        }

    }

    private void loadFormData(int modelId, PerformanceDatumImpl perf, int[] selections, int periodId, String dataType) throws Exception {
        this.mReviewBudgetDetails.setFormId(this.mXmlFormEVO.getPK().getXmlFormId());
        String definition = this.mXmlFormEVO.getDefinition();
        byte[] excelFile = this.mXmlFormEVO.getExcelFile();
        String jsonForm = this.mXmlFormEVO.getJsonForm();
        perf.setAdditonalInformation("XMLFormDefinition", definition);
        this.mLog.debug("loadFormData", "loaded form definition:\n" + definition);
        this.mReviewBudgetDetails.setAccountDimensionId(this.mModelEVO.getAccountId());
        if (this.mXmlFormEVO.getType() == 3) {
            this.loadFinanceFormData(modelId, perf, definition, selections, periodId, dataType);
        } else if ((this.mXmlFormEVO.getType() == 4) || (this.mXmlFormEVO.getType() == 6)) {
            this.loadFlatFormData(perf, definition, excelFile, jsonForm, selections, periodId, dataType);
        }

    }

    private void loadFinanceFormData(int modelId, PerformanceDatumImpl perf, String definition, int[] selections, int periodId, String dataType) throws Exception {
        MyFormContext formContext = null;

        try {
            com.cedar.cp.util.xmlform.reader.XMLReader reader = new com.cedar.cp.util.xmlform.reader.XMLReader();
            reader.init();
            StringReader sr = new StringReader(definition);
            reader.parseConfigFile(sr);
            FormConfig config = reader.getFormConfig();
            List columns = config.getBody().getColumns();
            this.mReviewBudgetDetails.setColumns(columns);
            Header header = config.getHeader();
            OnFormLoad formLoad = header.getOnFormLoad();
            if (formLoad != null) {
                this.mReviewBudgetDetails.setOnFormLoadFormula(formLoad.getFormula());
            }

            this.mReviewBudgetDetails.setDefaultFormatPattern(config.getBody().getDefaultFormat());
            this.mReviewBudgetDetails.setGradientDepth(config.getBody().getGradientDepth());
            this.mReviewBudgetDetails.setGradientColor(ColorUtils.getColorFromHexString(config.getBody().getGradientColor()));
            this.mReviewBudgetDetails.setEditBackground(ColorUtils.getColorFromHexString(config.getBody().getEditBackground()));
            this.mReviewBudgetDetails.setModifiedBackground(ColorUtils.getColorFromHexString(config.getBody().getModifiedBackground()));
            this.mReviewBudgetDetails.setEditForeground(ColorUtils.getColorFromHexString(config.getBody().getEditForeground()));
            this.mReviewBudgetDetails.setModifiedForeground(ColorUtils.getColorFromHexString(config.getBody().getModifiedForeground()));
            this.mReviewBudgetDetails.setRowHeaderColumnCount(config.getBody().getRowHeader().getColumns().size());
            this.mReviewBudgetDetails.setRowHeaderMaxDepth(config.getBody().getRowHeader().getMaxDepth());
            HashSet formDataTypes = new HashSet();
            this.mReviewBudgetDetails.setFormDataTypes(formDataTypes);
            formContext = new MyFormContext();
            ArrayList axesTreeIndexes = new ArrayList();
            ArrayList axesTreeHierVisIds = new ArrayList();
            int nonDataColumnValuesCount = 0;
            FinanceCubeInput fci = null;
            Iterator inputs = config.getInputs();

            while (inputs.hasNext()) {
                Object inputColumnIndex = inputs.next();
                if (inputColumnIndex instanceof FinanceCubeInput) {
                    fci = (FinanceCubeInput) inputColumnIndex;
                    Iterator start = fci.getColumnValues().iterator();

                    while (start.hasNext()) {
                        Object o2 = start.next();
                        int var44;
                        if (o2 instanceof FixedColumnValue) {
                            FixedColumnValue var42 = (FixedColumnValue) o2;
                            var44 = var42.getDim();
                            selections[var44] = this.getBusinessElementId(fci.getCubeVisId(), var44, var42.getDimValue());
                            ++nonDataColumnValuesCount;
                        } else if (o2 instanceof StructureColumnValue) {
                            StructureColumnValue var37 = (StructureColumnValue) o2;
                            var44 = var37.getDim();
                            axesTreeIndexes.add(new Integer(var44));
                            axesTreeHierVisIds.add(var37.getHier());
                            ++nonDataColumnValuesCount;
                        } else if (o2 instanceof DataTypeColumnValue) {
                            DataTypeColumnValue elapsed = (DataTypeColumnValue) o2;
                            String o = elapsed.getValue();
                            if (o != null && o.trim().length() > 0) {
                                formDataTypes.add(o);
                            } else {
                                formDataTypes.add(dataType);
                            }
                        }
                    }
                }
            }

            this.mReviewBudgetDetails.setTreeStructureIndexes(axesTreeIndexes);
            this.mReviewBudgetDetails.setTreeStructureVisIds(axesTreeHierVisIds);
            this.recursivelyProcessColumns(fci, columns, nonDataColumnValuesCount);
            long var38 = System.currentTimeMillis();

            for (int var43 = 0; var43 < selections.length; ++var43) {
                formContext.putVariable(WorkbookProperties.DIM_$.toString().replace("$", String.valueOf(var43)), new Integer(selections[var43]));
            }

            Iterator var45 = this.mContextVariables.entrySet().iterator();

            while (var45.hasNext()) {
                Object var39 = var45.next();
                Entry financeCubeId = (Entry) var39;
                formContext.putVariable(financeCubeId.getKey(), financeCubeId.getValue());
            }

            formContext.putVariable(WorkbookProperties.DATA_TYPE.toString(), dataType);
            formContext.putVariable(WorkbookProperties.BUDGET_CYCLE_PERIOD_ID.toString(), Integer.valueOf(periodId));
            config.buildVariables(perf, formContext);
            double var40 = (double) (System.currentTimeMillis() - var38) / 1000.0D;
            perf.setAdditonalInformation("BuildVariables", new Double(var40));
            int var41 = formContext.lookupFinanceCubeFromVisId(config.getFinanceCubeVisId());
            this.mReviewBudgetDetails.setFinanceCubeId(var41);
            ExternalSystemDAO extSysDao = new ExternalSystemDAO();
            ExternalSystemDAO.CubeExtSysInfo extSysInfo = extSysDao.getExtSysInfoForCube(var41);
            if (extSysInfo == null) {
                this.mReviewBudgetDetails.setServerSideChartOfAccountValidation(false);
            }

            this.mReviewBudgetDetails.setExternalSystemAvailable(extSysInfo != null);
            this.mReviewBudgetDetails.setContextVariables(config.getVariables());
            this.mReviewBudgetDetails.setSheetProtectionLevel(formContext.getSheetProtectionLevel());
            EntityList hierarchies = this.getModelDAO().getModelDimensionHierarchies(modelId);
            int[] hieararchyIds = new int[this.mReviewBudgetDetails.getDimensionCount()];

            for (int hierRow = 0; hierRow < hierarchies.getNumRows(); ++hierRow) {
                int dimIndex = ((Integer) hierarchies.getValueAt(hierRow, "dimension_seq_num")).intValue();
                if (dimIndex < hieararchyIds.length - 1) {
                    int treeStructureIndex = this.mReviewBudgetDetails.getTreeStructureIndexes().indexOf(Integer.valueOf(dimIndex));
                    String hierVisId = (String) hierarchies.getValueAt(hierRow, "vis_id");
                    if (treeStructureIndex != -1 && ((String) this.mReviewBudgetDetails.getTreeStructureVisIds().get(treeStructureIndex)).equals(hierVisId)) {
                        hieararchyIds[dimIndex] = ((Integer) hierarchies.getValueAt(hierRow, "hierarchy_id")).intValue();
                    }
                } else {
                    hieararchyIds[dimIndex] = ((Integer) hierarchies.getValueAt(hierRow, "hierarchy_id")).intValue();
                }
            }

            this.mReviewBudgetDetails.setStructureIds(hieararchyIds);
        } finally {
            if (formContext != null) {
                formContext.close();
            }

        }
    }

    private void loadFlatFormData(PerformanceDatumImpl perf, String definition, byte[] excelFile, String jsonForm, int[] selections, int periodId, String dataType) throws Exception {
        com.cedar.cp.util.flatform.model.Workbook workbook = this.loadWorkbookFromXML(definition);
        this.mReviewBudgetDetails.setWorkbook(workbook);
        this.mReviewBudgetDetails.setExcelFile(excelFile);
        this.mReviewBudgetDetails.setJsonForm(jsonForm);
        this.mReviewBudgetDetails.setContextVariables(new HashMap());
    }

    private int recursivelyProcessColumns(FinanceCubeInput fci, List columns, int inputColumnIndex) {
        for (int j = 0; j < columns.size(); ++j) {
            Object c = columns.get(j);
            if (c instanceof ColumnGroup) {
                ColumnGroup col = (ColumnGroup) c;
                inputColumnIndex = this.recursivelyProcessColumns(fci, col.getColumns(), inputColumnIndex);
            } else {
                Column var7 = (Column) c;
                if (var7.isTypeDataType()) {
                    this.setColumnModelData(var7, fci.getColumnValues().get(inputColumnIndex++));
                } else if (!var7.isTypeFormula() && !var7.isTypeStringFormula() && !var7.isTypeNumber()) {
                    if (var7.isTypeString()) {
                        if (var7.getPersists()) {
                            ++inputColumnIndex;
                        }
                    } else if (!var7.isTypeCellNote() && var7.isTypeTree()) {
                        ;
                    }
                } else if (var7.getPersists()) {
                    this.setColumnModelData(var7, fci.getColumnValues().get(inputColumnIndex++));
                }
            }
        }

        return inputColumnIndex;
    }

    private void setColumnModelData(Column column, Object columnValue) {
        ColumnModelData data = null;
        if (columnValue instanceof DataTypeColumnValue) {
            DataTypeColumnValue value = (DataTypeColumnValue) columnValue;
            data = new ColumnModelData(value.getPeriodVisId(), value.getYear(), value.getPeriod(), value.getYtd(), value.getValue());
        }

        column.setColumnModelData(data);
    }

    private int getBusinessElementId(String cubeVisId, int colDim, String elementVisId) {
        try {
            XmlFormDAO e = this.getXmlFormDAO();
            return e.lookupStructureElementFromVisId(cubeVisId, colDim, elementVisId);
        } catch (Exception var5) {
            this.mLog.debug("Can\'t get element for fixedColumnValue " + var5.getMessage());
            return 0;
        }
    }

    private void setCubeDeployments(int modelId, int financeCubeId, int[] structureIds, int[] selections) {
        CcDeploymentDAO deploymentDao = new CcDeploymentDAO();
        int[] newSelections = new int[selections.length - 1];

        for (int relevantCubeDeployments = 0; relevantCubeDeployments < newSelections.length; ++relevantCubeDeployments) {
            newSelections[relevantCubeDeployments] = selections[relevantCubeDeployments];
        }

        List var8 = deploymentDao.getRelevantCubeDeployments(modelId, financeCubeId, newSelections, this.mReviewBudgetDetails.getFormDataTypes(), this.mReviewBudgetDetails.getTreeStructureIndexes(), true);
        this.mReviewBudgetDetails.setCubeDeployments(var8);
    }

    public void setChildBudgetState(int pModelId, int pUserId, int pBudgetCycleId, int pStructureElementId, int pFromState, int pToState) {
        try {
            this.mLog.debug("setChildBudgetState", " modelId=" + pModelId + " budgetCycleId=" + pBudgetCycleId + " structureElementId=" + pStructureElementId + " fromState= " + pFromState + " toState= " + pToState);
            Timer e = new Timer(this.mLog);
            StructureElementDAO strucDAO = new StructureElementDAO();
            MassStateImmediateChildrenELO myChildren = strucDAO.getMassStateImmediateChildren(this.mModelEVO.getBudgetHierarchyId(), pStructureElementId);
            int childCount = myChildren.getNumRows();
            int activityCounter = -1;
            int historyCounter = -1;
            myChildren.reset();

            while (myChildren.hasNext()) {
                ArrayList links = new ArrayList();
                myChildren.next();
                if (!myChildren.getDisabled() && !myChildren.getNotPlannable()) {
                    BudgetStatePK pk = new BudgetStatePK(pBudgetCycleId, myChildren.getStructureElementId());
                    BudgetStateEVO evo = this.mBudgetCycleEVO.getBudgetCycleStatesItem(pk);
                    boolean wasNull = false;
                    if (pFromState == 10 || evo.getState() == pFromState) {
                        int oldState = 0;
                        if (evo != null) {
                            oldState = evo.getState();
                        } else {
                            evo = new BudgetStateEVO();
                            evo.setBudgetCycleId(pBudgetCycleId);
                            evo.setStructureElementId(myChildren.getStructureElementId());
                            wasNull = true;
                        }

                        boolean agreed = pToState == 4;
                        boolean rejected = pToState == 2 && (oldState == 4 || oldState == 3);
                        boolean submited = pToState == 3;
                        BudgetStateHistoryEVO historyEVO = new BudgetStateHistoryEVO();
                        historyEVO.setBudgetStateHistoryId(historyCounter--);
                        historyEVO.setBudgetCycleId(pBudgetCycleId);
                        historyEVO.setStructureElementId(myChildren.getStructureElementId());
                        historyEVO.setChangedTime(new Timestamp(System.currentTimeMillis()));
                        String stateText = "";
                        if (pToState == 1) {
                            stateText = "to preparing (started)";
                            evo.setState(2);
                            evo.setSubmitable(true);
                            historyEVO.setPreviousState(0);
                            historyEVO.setNewState(2);
                            evo.addBudgetCycleHistoryItem(historyEVO);
                            this.mBudgetCycleEVO.addBudgetCycleStatesItem(evo);
                        } else {
                            historyEVO.setNewState(pToState);
                            historyEVO.setPreviousState(oldState);
                            evo.setState(pToState);
                            evo.addBudgetCycleHistoryItem(historyEVO);
                            if (submited) {
                                evo.setRejectable(true);
                                evo.setSubmitable(false);
                            } else if (rejected) {
                                evo.setRejectable(false);
                                evo.setSubmitable(true);
                            } else if (agreed) {
                                evo.setSubmitable(false);
                                evo.setRejectable(false);
                            }

                            if (wasNull) {
                                this.mBudgetCycleEVO.addBudgetCycleStatesItem(evo);
                            } else {
                                this.mBudgetCycleEVO.changeBudgetCycleStatesItem(evo);
                            }
                        }

                        List var24 = this.addActivityLink(pBudgetCycleId, myChildren.getStructureElementId(), links);
                        this.addStateActivityRecord(pModelId, pUserId, stateText + " by Mass Change ", (String) null, var24, activityCounter--);
                    }
                }
            }

            e.logInfo("setChildBudgetState", "");
        } catch (Exception var23) {
            this.mLog.error("setChildBudgetState", " modelId=" + pModelId + " budgetCycleId=" + pBudgetCycleId + " structureElementId=" + pStructureElementId + " fromState= " + pFromState + " toState= " + pToState, var23);
            throw new CPException("error doing mass submit: " + var23.getMessage());
        }
    }

    public ChangeBudgetStateResult setBudgetState(int modelId, int userId, int budgetCycleId, int structureElementId, int state, BudgetLimitCheck check, Integer childStateFrom, Integer childStateTo) throws BudgetStatusException {
        ChangeBudgetStateResultImpl result = null;
        ArrayList ids = new ArrayList();
        Object links = new ArrayList();
        String setHistory = "Set History";
        String setStateHeader = "Set State Header";
        String checkLowestRA = "Check Lowest RA";
        String setParentage = "Set Parentage";
        ids.add(setHistory);
        ids.add(setStateHeader);
        ids.add(checkLowestRA);
        ids.add(setParentage);
        PerformanceDatumImpl perf = GenericPerformanceType.getInstance("SetBudgetState", ids, "Set the budget state").createPerformanceDatum("");

        ChangeBudgetStateResultImpl pk;
        try {
            this.mLog.debug("setBudgetState", " modelId=" + modelId + " budgetCycleId=" + budgetCycleId + " structureElementId=" + structureElementId + " state= " + state);
            Timer e = new Timer(this.mLog);
            result = new ChangeBudgetStateResultImpl();
            boolean valid = true;
            this.getModelAndBudgetCycle(modelId, budgetCycleId, true);
            if (childStateFrom != null && childStateFrom.intValue() != 0) {
                this.setChildBudgetState(modelId, userId, budgetCycleId, structureElementId, childStateFrom.intValue(), childStateTo.intValue());
            }

            if (state != 10) {
                BudgetStatePK var50 = new BudgetStatePK(budgetCycleId, structureElementId);
                BudgetStateEVO evo = this.mBudgetCycleEVO.getBudgetCycleStatesItem(var50);
                int oldState = 0;
                if (evo != null) {
                    oldState = evo.getState();
                }

                boolean agreed = state == 4;
                boolean rejected = state == 2 && (oldState == 4 || oldState == 3);
                boolean submited = state == 3;
                boolean forceMessage = false;
                List violations = Collections.EMPTY_LIST;
                if (check.isPerformCheck() && (submited || agreed)) {
                    BudgetLimitDAO spDAO = new BudgetLimitDAO();
                    violations = spDAO.getBudgetLimitsForBudgetLocation(modelId, structureElementId);
                }

                if (!violations.isEmpty()) {
                    result.setBudgetLimitViolations(violations);
                    result.setValid(false);
                    throw new BudgetStatusException(result);
                }

                SystemPropertyDAO var51 = new SystemPropertyDAO();
                SystemPropertyELO spList = var51.getSystemProperty("WEB: Status Change Message");
                if (spList.getNumRows() > 0) {
                    forceMessage = Boolean.valueOf(spList.getValueAt(0, "Value").toString()).booleanValue();
                }

                if (check.isPerformCheck() && forceMessage && (submited || rejected)) {
                    result.setBudgetLimitViolations(violations);
                    result.setValid(false);
                    result.setForceMessage(true);
                    throw new BudgetStatusException(result);
                }

                int orgStructureId = this.mModelEVO.getBudgetHierarchyId();
                BudgetStateHistoryEVO historyEVO = new BudgetStateHistoryEVO();
                historyEVO.setBudgetCycleId(budgetCycleId);
                historyEVO.setStructureElementId(structureElementId);
                historyEVO.setChangedTime(new Timestamp(System.currentTimeMillis()));
                perf.setDatumPoint(setHistory);
                String stateText = "";
                int fcDao;
                StructureElementDAO dao;
                StructureElementParentsELO cellNoteList;
                int childCount;
                if (state == 1) {
                    stateText = "to preparing (started)";
                    if (evo == null) {
                        evo = new BudgetStateEVO();
                        evo.setBudgetCycleId(budgetCycleId);
                        evo.setStructureElementId(structureElementId);
                        evo.setState(2);
                        perf.setDatumPoint(setStateHeader);
                        dao = new StructureElementDAO();
                        if (dao.isNodeSubmitable(budgetCycleId, orgStructureId, structureElementId)) {
                            evo.setSubmitable(true);
                        }

                        perf.setDatumPoint(checkLowestRA);
                        historyEVO.setPreviousState(0);
                        historyEVO.setNewState(2);
                        evo.addBudgetCycleHistoryItem(historyEVO);
                        this.mBudgetCycleEVO.addBudgetCycleStatesItem(evo);
                        cellNoteList = dao.getStructureElementParents(this.mModelEVO.getBudgetHierarchyId(), structureElementId);
                        fcDao = cellNoteList.getNumRows();
                        if (fcDao > 0) {
                            BudgetStateDAO parent_structure_element_id = new BudgetStateDAO();
                            CheckIfHasStateELO parentEvo = null;
                            boolean children = false;

                            for (childCount = 1; childCount < fcDao; ++childCount) {
                                boolean bsDao = false;
                                int var57 = ((Integer) cellNoteList.getValueAt(childCount, "StructureElementId")).intValue();
                                parentEvo = parent_structure_element_id.getCheckIfHasState(budgetCycleId, var57);
                                int var60 = parentEvo.getNumRows();
                                if (var60 == 0) {
                                    BudgetStateEVO agreedCount = new BudgetStateEVO();
                                    agreedCount.setBudgetCycleId(budgetCycleId);
                                    agreedCount.setStructureElementId(var57);
                                    agreedCount.setState(2);
                                    BudgetStateHistoryEVO parentHistoryEVO = new BudgetStateHistoryEVO();
                                    parentHistoryEVO.setBudgetCycleId(budgetCycleId);
                                    parentHistoryEVO.setStructureElementId(var57);
                                    parentHistoryEVO.setChangedTime(new Timestamp(System.currentTimeMillis()));
                                    parentHistoryEVO.setPreviousState(0);
                                    parentHistoryEVO.setNewState(2);
                                    agreedCount.addBudgetCycleHistoryItem(parentHistoryEVO);
                                    this.mBudgetCycleEVO.addBudgetCycleStatesItem(agreedCount);
                                    links = this.addActivityLink(budgetCycleId, var57, (List) links);
                                }
                            }
                        }

                        perf.setDatumPoint(setParentage);
                    }
                } else {
                    historyEVO.setNewState(state);
                    historyEVO.setPreviousState(oldState);
                    evo.setState(state);
                    evo.addBudgetCycleHistoryItem(historyEVO);
                    if (agreed || rejected || submited) {
                        dao = new StructureElementDAO();
                        cellNoteList = dao.getStructureElementParents(this.mModelEVO.getBudgetHierarchyId(), structureElementId);
                        fcDao = cellNoteList.getNumRows();
                        int var55 = 0;
                        BudgetStateEVO var54 = null;
                        if (fcDao > 1) {
                            var55 = ((Integer) cellNoteList.getValueAt(1, "StructureElementId")).intValue();
                            BudgetStatePK var59 = new BudgetStatePK(budgetCycleId, var55);
                            var54 = this.mBudgetCycleEVO.getBudgetCycleStatesItem(var59);
                        }

                        if (agreed) {
                            stateText = "to agreed";
                            ImmediateChildrenELO var58 = dao.getImmediateChildren(this.mModelEVO.getBudgetHierarchyId(), var55);
                            childCount = var58.getNumRows();
                            BudgetStateDAO var62 = new BudgetStateDAO();
                            int var61 = var62.getAgreeCount(this.mModelEVO.getBudgetHierarchyId(), var55, budgetCycleId);
                            evo.setRejectable(true);
                            if (var54 != null) {
                                var54.setRejectable(true);
                                if (childCount - 1 == var61) {
                                    var54.setSubmitable(true);
                                }

                                this.mBudgetCycleEVO.changeBudgetCycleStatesItem(var54);
                            }
                        } else if (rejected) {
                            if (oldState == 4) {
                                stateText = "to rejected from agreed";
                            } else if (oldState == 3) {
                                stateText = "to rejected from submitted";
                            }

                            if (var54 != null) {
                                var54.setSubmitable(false);
                                this.mBudgetCycleEVO.changeBudgetCycleStatesItem(var54);
                            }

                            evo.setRejectable(false);
                            valid = this.setChildrenRejectable(structureElementId, budgetCycleId, true);
                        } else if (submited) {
                            stateText = "to submitted";
                            evo.setRejectable(true);
                            valid = this.setChildrenRejectable(structureElementId, budgetCycleId, false);
                        }
                    }

                    this.mBudgetCycleEVO.changeBudgetCycleStatesItem(evo);
                    perf.setDatumPoint(setStateHeader);
                }

                List var49 = this.addActivityLink(budgetCycleId, structureElementId, (List) links);
                if (!valid) {
                    result.setValid(false);
                    result.setMessage("Error trying to set child nodes");
                    throw new BudgetStatusException(result);
                }

                if (!check.isPerformCheck() && check.getReason() != null && check.getReason().length() > 0) {
                    BudgetLimitDAO var53 = new BudgetLimitDAO();
                    List var52 = var53.getBudgetLimitViolationsForBudgetLocation(modelId, structureElementId);
                    FinanceCubeDAO var56 = new FinanceCubeDAO();
                    var56.insertCellNote(var52, check.getReason(), structureElementId, userId);
                    this.mBudgetLimitCheck = check;
                    this.addStateActivityRecord(modelId, userId, stateText, check.getReason(), var49);
                } else {
                    this.addStateActivityRecord(modelId, userId, stateText, (String) null, var49);
                }

                this.getModelDAO().setDetails(this.mModelEVO);
                this.getModelDAO().store();
                e.logInfo("setBudgetState", "");
                return result;
            }

            this.getModelDAO().setDetails(this.mModelEVO);
            this.getModelDAO().store();
            pk = result;
        } catch (BudgetStatusException var46) {
            throw var46;
        } catch (Exception var47) {
            this.mLog.error("setBudgetState", " modelId=" + modelId + " budgetCycleId=" + budgetCycleId + " structureElementId=" + structureElementId + " state= " + state, var47);
            result.setValid(false);
            result.setMessage("Error while trying to set state");
            result.setException(var47);
            throw new BudgetStatusException(result);
        } finally {
            perf.completed();
        }

        return pk;
    }

    private List addActivityLink(int budgetCycleId, int structureElementId, List links) {
        BudgetActivityLinkEVO balEvo = new BudgetActivityLinkEVO(-1, structureElementId, new Integer(budgetCycleId));
        links.add(balEvo);
        return links;
    }

    private void addStateActivityRecord(int modelId, int userId, String state, String reason, List links) {
        this.addStateActivityRecord(modelId, userId, state, reason, links, 0);
    }

    private void addStateActivityRecord(int modelId, int userId, String state, String reason, List links, int index) {
        BudgetActivityEVO baEvo = new BudgetActivityEVO();
        BigInteger balEvo;
        if (index != 0) {
            baEvo.setBudgetActivityId(index);
        } else {
            int report = 0;
            if (this.mModelEVO.getBudgetActivities() != null) {
                report = this.mModelEVO.getBudgetActivities().size();
            }

            balEvo = BigInteger.valueOf((long) (report + 1));
            baEvo.setBudgetActivityId(balEvo.negate().intValue());
        }

        baEvo.setModelId(modelId);

        try {
            UserDAO var17 = new UserDAO();
            UserEVO var16 = var17.getDetails(new UserPK(userId), "");
            baEvo.setUserId(var16.getName());
        } catch (Exception var14) {
            var14.printStackTrace();
            throw new CPException("No user present :" + var14.getMessage() + " for userid " + userId);
        }

        baEvo.setCreated(new Timestamp(System.currentTimeMillis()));
        baEvo.setActivityType(5);
        baEvo.setDescription("State Change");
        StateActivityWriter var15 = new StateActivityWriter();
        var15.addReport();
        var15.addParamSection("WHAT");
        var15.addParam("State changed", state);
        var15.addEndParamSection();
        int i;
        if (reason != null) {
            var15.addParamSection("WHY");
            var15.addParam("Reason for violation", reason);
            var15.addEndParamSection();
            var15.addMatrixSection("Budget Limit Violations");
            var15.addRow();
            var15.addCellHeading("Who set Limit");
            EntityList var18 = this.mBudgetLimitCheck.getViolationHeadings();
            if (var18 != null) {
                for (i = 1; i < var18.getNumRows(); ++i) {
                    var15.addCellHeading((String) var18.getValueAt(i, "VisId"));
                }
            }

            var15.addCellHeading("Data Type");
            var15.addCellHeading("Minimum Value");
            var15.addCellHeading("Maximum Value");
            var15.addCellHeading("Current Value");
            var15.addEndRow();
            List var20 = this.mBudgetLimitCheck.getViolations();

            for (int i1 = 0; i1 < var20.size(); ++i1) {
                var15.addRow();
                List structureElement = (List) var20.get(i1);

                for (int j = 2; j < structureElement.size(); ++j) {
                    if (structureElement.get(j) instanceof BigDecimal) {
                        var15.addCellNumber(NumberFormatter.format(((BigDecimal) structureElement.get(j)).doubleValue()));
                    } else if (structureElement.get(j) == null && (j == structureElement.size() - 1 || j == structureElement.size() - 2 || j == structureElement.size() - 3)) {
                        var15.addCellNumber("0.00");
                    } else {
                        var15.addCellText((String) structureElement.get(j));
                    }
                }

                var15.addEndRow();
            }

            var15.addEndMatrixSection();
        }

        var15.addEndReport();
        baEvo.setDetails(var15.getXMLTxt());
        balEvo = null;

        for (i = 0; i < links.size(); ++i) {
            BudgetActivityLinkEVO var19 = (BudgetActivityLinkEVO) links.get(i);
            baEvo.addActivityLinksItem(var19);
        }

        this.mModelEVO.addBudgetActivitiesItem(baEvo);
    }

    private boolean setChildrenRejectable(int parent_id, int budget_cycle_id, boolean rejectable) {
        boolean valid = true;
        StructureElementDAO strucDAO = new StructureElementDAO();
        ImmediateChildrenELO children = strucDAO.getImmediateChildren(this.mModelEVO.getBudgetHierarchyId(), parent_id);
        int child_count = children.getNumRows();

        for (int i = 0; i < child_count; ++i) {
            int child_elem_id = ((Integer) children.getValueAt(i, "StructureElementId")).intValue();
            BudgetStatePK childKey = new BudgetStatePK(budget_cycle_id, child_elem_id);
            BudgetStateEVO childEvo = this.mBudgetCycleEVO.getBudgetCycleStatesItem(childKey);
            if (childEvo == null) {
                valid = false;
                break;
            }

            childEvo.setRejectable(rejectable);
            this.mBudgetCycleEVO.changeBudgetCycleStatesItem(childEvo);
        }

        return valid;
    }

    public void ejbCreate() throws EJBException {
    }

    public void ejbRemove() {
    }

    public void setSessionContext(SessionContext context) {
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    private DimensionElementDAO getDimensionElementDAO() throws Exception {
        if (this.mDimensionElementDAO == null) {
            this.mDimensionElementDAO = new DimensionElementDAO();
        }

        return this.mDimensionElementDAO;
    }

    private ModelDAO getModelDAO() throws Exception {
        if (this.mModelDAO == null) {
            this.mModelDAO = new ModelDAO();
        }

        return this.mModelDAO;
    }

    private StructureElementDAO getStructureElementDAO() throws Exception {
        if (this.mStructureElementDAO == null) {
            this.mStructureElementDAO = new StructureElementDAO();
        }

        return this.mStructureElementDAO;
    }

    private XmlFormDAO getXmlFormDAO() throws Exception {
        if (this.mXmlFormDAO == null) {
            this.mXmlFormDAO = new XmlFormDAO();
        }

        return this.mXmlFormDAO;
    }

    public EntityList getCycleStateHistory(int bcId, int structure_id, int structure_elementid) {
        BudgetStateHistoryDAO dao = new BudgetStateHistoryDAO();
        return dao.getHistoryDetails(bcId, structure_id, structure_elementid);
    }

    public int issueBudgetStateTask(int daysBefore, int userId, int issuingTaskId) {
        byte var5;
        try {
            this.setUserId(userId);
            BudgetStateTaskRequest e = new BudgetStateTaskRequest();
            e.setDaysBefore(daysBefore);
            int var11 = TaskMessageFactory.issueNewTask(new InitialContext(), false, e, userId, issuingTaskId);
            return var11;
        } catch (Exception var9) {
            var9.printStackTrace();
            var5 = -1;
        } finally {
            this.setUserId(0);
        }

        return var5;
    }

    public int issueBudgetStateRebuildTask(EntityRef ref, int userId) {
        try {
            this.setUserId(userId);
            BudgetStateRebuildTaskRequest e = new BudgetStateRebuildTaskRequest();
            if (ref instanceof ModelRef) {
                ModelPK ck1 = (ModelPK) ref.getPrimaryKey();
                e.setModelId(Integer.valueOf(ck1.getModelId()));
            } else {
                if (!(ref instanceof BudgetCycleRef)) {
                    byte ck3 = 0;
                    return ck3;
                }

                BudgetCycleCK ck2 = (BudgetCycleCK) ref.getPrimaryKey();
                e.setModelId(Integer.valueOf(ck2.getModelPK().getModelId()));
                e.setBudgetCycleId(Integer.valueOf(ck2.getBudgetCyclePK().getBudgetCycleId()));
            }

            int ck4 = TaskMessageFactory.issueNewTask(new InitialContext(), false, e, userId);
            return ck4;
        } catch (Exception var8) {
            var8.printStackTrace();
            byte ck = -1;
            return ck;
        } finally {
            this.setUserId(0);
        }
    }

    /**
     * Update mapping with reference cell (ex: #A#1)
     */
    public com.cedar.cp.util.flatform.model.Workbook updateMapping(byte[] excelFile, com.cedar.cp.util.flatform.model.Workbook workbook) throws Exception {
        Workbook excelWorkbook = byteToWorkbook(excelFile);

        String text, temp;
        String[] tempTab;
        boolean found;

        // go through sheets in workbook
        for (Worksheet worksheet: workbook.getWorksheets()) {
            Sheet excelSheet = excelWorkbook.getSheet(worksheet.getName());

            Iterator cellLinkIterator = worksheet.iterator();
            Iterator<Row> excelRowIterator = excelSheet.iterator();
            int rowIndex = 0; // to start with A1
            Row row = excelRowIterator.next();

            CellStyle dateCellStyle = excelWorkbook.createCellStyle();
            short df = excelWorkbook.createDataFormat().getFormat("dd-MMM-yy");

            // iterate through cells in workbook
            while (cellLinkIterator.hasNext()) {
                // has info about cell
                CellLink cellLink = (CellLink) cellLinkIterator.next();

                // row by row in excel
                row: while (rowIndex < worksheet.getRowCount()) {
                    // match rows
                    if (rowIndex == cellLink.getRow()) {
                        int colIndex = 0;
                        Iterator<Cell> excelCellIterator = row.cellIterator();

                        // every column in row in excel
                        while (excelCellIterator.hasNext() && colIndex < worksheet.getColumnCount()) {
                            Cell excelCell = excelCellIterator.next();
                            colIndex = excelCell.getColumnIndex();

                            // match columns
                            if (colIndex == cellLink.getColumn()) {
                                // get cell data and set in excel
                                if (cellLink.getData() != null) {
                                    if (cellLink.getData() instanceof com.cedar.cp.util.flatform.model.Cell) {
                                        com.cedar.cp.util.flatform.model.Cell cell = (com.cedar.cp.util.flatform.model.Cell) cellLink.getData();

                                        if (cell != null) {
                                            // Input mapping
                                            if (!GeneralUtils.isEmptyOrNull(cell.getInputMapping())) {
                                                found = false;
                                                text = cell.getInputMapping();
                                                // #Sheet1#A#1
                                                Pattern pattern = Pattern.compile("#[^#]*#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
                                                Matcher matcher = pattern.matcher(text);
                                                while (matcher.find()) {
                                                    found = true;
                                                    temp = matcher.group();
                                                    tempTab = temp.split("#");
                                                    if (!isCellWithMapping(workbook, tempTab[1], tempTab[2] + tempTab[3])) {
                                                        text = text.replace(temp, getCellValue(excelWorkbook, tempTab[1], tempTab[2] + tempTab[3])); // replace found reference with cell value
                                                    }
                                                }
                                                // #A#1
                                                pattern = Pattern.compile("#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
                                                matcher = pattern.matcher(text);
                                                while (matcher.find()) {
                                                    found = true;
                                                    temp = matcher.group();
                                                    tempTab = temp.split("#");
                                                    if (!isCellWithMapping(workbook, worksheet.getName(), tempTab[1] + tempTab[2])) {
                                                        text = text.replace(temp, getCellValue(excelWorkbook, worksheet.getName(), tempTab[1] + tempTab[2]));
                                                    }
                                                }
                                                if (found) {
                                                    cell.setInputMapping(text);
                                                }
                                            }
                                            // Output mapping
                                            if (!GeneralUtils.isEmptyOrNull(cell.getOutputMapping())) {
                                                found = false;
                                                text = cell.getOutputMapping();
                                                // #Sheet1#A#1
                                                Pattern pattern = Pattern.compile("#[^#]*#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
                                                Matcher matcher = pattern.matcher(text);
                                                while (matcher.find()) {
                                                    found = true;
                                                    temp = matcher.group();
                                                    tempTab = temp.split("#");
                                                    if (!isCellWithMapping(workbook, tempTab[1], tempTab[2] + tempTab[3])) {
                                                        text = text.replace(temp, getCellValue(excelWorkbook, tempTab[1], tempTab[2] + tempTab[3])); // replace found reference with cell value
                                                    }
                                                }
                                                // #A#1
                                                pattern = Pattern.compile("#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
                                                matcher = pattern.matcher(text);
                                                while (matcher.find()) {
                                                    found = true;
                                                    temp = matcher.group();
                                                    tempTab = temp.split("#");
                                                    if (!isCellWithMapping(workbook, worksheet.getName(), tempTab[1] + tempTab[2])) {
                                                        text = text.replace(matcher.group(), getCellValue(excelWorkbook, worksheet.getName(), tempTab[1] + tempTab[2]));
                                                    }
                                                }
                                                if (found) {
                                                    cell.setOutputMapping(text);
                                                }
                                            }
                                            // Text or formula
                                            if (!GeneralUtils.isEmptyOrNull(cell.getText())) {
                                                found = false;
                                                text = cell.getCellText();
                                                // #Sheet1#A#1
                                                Pattern pattern = Pattern.compile("#[^#]*#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
                                                Matcher matcher = pattern.matcher(text);
                                                while (matcher.find()) {
                                                    found = true;
                                                    temp = matcher.group();
                                                    tempTab = temp.split("#");
                                                    if (!isCellWithMapping(workbook, tempTab[1], tempTab[2] + tempTab[3])) {
                                                        text = text.replace(temp, getCellValue(excelWorkbook, tempTab[1], tempTab[2] + tempTab[3])); // replace found reference with cell value
                                                    }
                                                }
                                                // #A#1
                                                pattern = Pattern.compile("#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
                                                matcher = pattern.matcher(text);
                                                while (matcher.find()) {
                                                    found = true;
                                                    temp = matcher.group();
                                                    tempTab = temp.split("#");
                                                    if (!isCellWithMapping(workbook, worksheet.getName(), tempTab[1] + tempTab[2])) {
                                                        text = text.replace(matcher.group(), getCellValue(excelWorkbook, worksheet.getName(), tempTab[1] + tempTab[2]));
                                                    }
                                                }
                                                if (found) {
                                                    cell.setFormulaText(text);
                                                }
                                            }
                                        }
                                        break row;
                                    }
                                }
                            }
                        }
                    } else {
                        row = excelRowIterator.next();
                        rowIndex = row.getRowNum();
                    }
                }
            }
        }

        return workbook;
    }

    private boolean isCellWithMapping(com.cedar.cp.util.flatform.model.Workbook workbook, String sheetName, String cellName) {
        CellReference cellReference = new CellReference(cellName);
        int rowNum = cellReference.getRow();
        int colNum = cellReference.getCol();

        List<Worksheet> worksheets = workbook.getWorksheets();

        for (Worksheet worksheet: worksheets) {
            if (sheetName.equals(worksheet.getName())) {
                com.cedar.cp.util.flatform.model.Cell cell = worksheet.get(rowNum, colNum);
                if (cell != null) {
                    if (!GeneralUtils.isEmptyOrNull(cell.getInputMapping())) {
                        return true;
                    }
                    if (!GeneralUtils.isEmptyOrNull(cell.getText()) && cell.getCellText().startsWith("=") && containsInputMapping(cell.getCellText())) {
                        return true;
                    }
                }
            } else {
                continue;
            }
        }

        return false;
    }

    /**
     * Check if the given string contains valid input mapping string
     */
    private boolean containsInputMapping(String string) {
       return MappingFunction.containsAnyMappingFunction(string);
    }

    private String getCellValue(Workbook workbook, String sheetName, String cellName) {
        String cellValue = "";
        Sheet sheet = workbook.getSheet(sheetName);
        CellReference cellReference = new CellReference(cellName);
        Row row = sheet.getRow(cellReference.getRow());
        Cell cell = row.getCell(cellReference.getCol());

        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;

                case Cell.CELL_TYPE_FORMULA:
                    cellValue = cell.getCellFormula();
                    break;

                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = cell.getDateCellValue().toString();
                    } else {
                        cellValue = Double.toString(cell.getNumericCellValue());
                    }
                    if (cellValue.substring(cellValue.length() - 2).equals(".0")) {
                        cellValue = cellValue.substring(0, cellValue.length() - 2);
                    }
                    break;

                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;

                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = Boolean.toString(cell.getBooleanCellValue());
                    break;

            }
        }
        return cellValue;
    }

    private Workbook byteToWorkbook(byte[] excelFile) throws Exception {
        try {
            InputStream is = new ByteArrayInputStream(excelFile);
            Workbook excelWorkbook = WorkbookFactory.create(is);
            is.close();
            return excelWorkbook;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Return sheet number by name (0 = first)
     */
    public int getSheetNumber(String sheetName, View view) {
        try {
            for (int sheetIdx = 0; sheetIdx < view.getSheetsCount(); sheetIdx++) {
                if (sheetName.toLowerCase().equals(view.getSheetName(sheetIdx).toLowerCase())) {
                    return sheetIdx;
                }
            }
        } catch (CellException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Return excel column number. A= 0
     */
    public static int columnNumber(String columnName) {
        columnName = columnName.trim();
        StringBuffer buff = new StringBuffer(columnName);
        char chars[] = buff.reverse().toString().toLowerCase().toCharArray();
        int retVal = 0, multiplier = 0;

        for (int i = 0; i < chars.length; i++) {
            multiplier = (int) chars[i] - 96;
            retVal += multiplier * Math.pow(26, i);
        }
        return retVal - 1;
    }

    public void updatePeriods(int bcId, int periodId, int fromTo) {
        BudgetCycleDAO dao = new BudgetCycleDAO();
        dao.updatePeriods(bcId, periodId, fromTo);
    }

    private static class MyFormContext extends DBAccessor implements FormContext {
        private int mElementId;
        private Map mVariables = new HashMap();
        private ContextInputFactory mWrappedFactory = new ContextInputFactory(this);
        private int mSheetProtectionLevel = 0;

        public FormInputModel getFormInputModel(PerformanceDatumImpl perf, RowInput config) {
            FormInputModel data = this.mWrappedFactory.getFormInputModel(perf, config);
            this.mSheetProtectionLevel = data.getSheetProtectionLevel();
            return data;
        }

        public Map getLookupInputs(PerformanceDatumImpl perf, List lookupInputs) {
            return this.mWrappedFactory.getLookupInputs(perf, lookupInputs, this.mVariables);
        }

        public FormInputModel getFinanceFormDataRows(int userId, FinanceCubeInput config, int budgetCycleId, String contextDataType, int xAxisIndex, int[] structureElementIds, int childrenDepth, boolean secondaryStructure, CalendarInfo calInfo, Map dataTypes, Map securityAccessDetails) throws Exception {
            FormInputModel data = this.mWrappedFactory.getFinanceFormDataRows3(userId, config, budgetCycleId, contextDataType, xAxisIndex, structureElementIds, childrenDepth, secondaryStructure, calInfo, dataTypes, securityAccessDetails);

            this.mSheetProtectionLevel = data.getSheetProtectionLevel();
            return data;
        }

        public Connection getSqlConnection() {
            return this.mCnx;
        }

        public void closeSqlConnection() {
        }

        public void putVariable(Object key, Object value) {
            this.mVariables.put(key, value);
        }

        public Map getVariables() {
            return this.mVariables;
        }

        public int lookupFinanceCubeFromVisId(String visId) {
            executePreparedQuery("select finance_cube_id from finance_cube where vis_id = ?", new Object[] { visId });

            return this.mElementId;
        }

        protected void processResultSet(ResultSet resultSet) throws SQLException {
            if (resultSet.next())
                this.mElementId = resultSet.getInt(1);
        }

        public int getSheetProtectionLevel() {
            return this.mSheetProtectionLevel;
        }

    }

    class StateActivityWriter extends XMLReportUtils {
        private StringBuffer mXML = new StringBuffer();

        StateActivityWriter() {
        }

        public void add(String txt) {
            this.mXML.append(txt);
        }

        public String getXMLTxt() {
            return this.mXML.toString();
        }

    }
}
