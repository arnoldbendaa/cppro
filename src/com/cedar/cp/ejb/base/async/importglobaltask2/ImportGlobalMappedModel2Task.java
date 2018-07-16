package com.cedar.cp.ejb.base.async.importglobaltask2;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.cm.ChangeManagementTaskRequest;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import com.cedar.cp.dto.model.globalmapping2.ImportGlobalMappedModel2TaskRequest;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint;
import com.cedar.cp.ejb.impl.cm.ChangeMgmtEngine;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementActionType;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEvent;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementEventType;
import com.cedar.cp.ejb.impl.cm.xml.ChangeManagementType;
import com.cedar.cp.ejb.impl.datatype.DataTypeAccessor;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemAccessor;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.globalmapping2.MappedCalendarElementEVO;
import com.cedar.cp.ejb.impl.model.globalmapping2.MappedCalendarYearEVO;
import com.cedar.cp.ejb.impl.model.globalmapping2.MappedDataTypeEVO;
import com.cedar.cp.ejb.impl.model.globalmapping2.MappedDimensionEVO;
import com.cedar.cp.ejb.impl.model.globalmapping2.MappedDimensionElementEVO;
import com.cedar.cp.ejb.impl.model.globalmapping2.MappedFinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.globalmapping2.MappedHierarchyEVO;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2Accessor;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2EVO;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.xml.datatype.DatatypeFactory;

public class ImportGlobalMappedModel2Task extends AbstractTask {

    private transient InitialContext mInitialContext;
    private GlobalMappedModel2Accessor mMappedModelAccessor;
    private ExternalSystemAccessor mExternalSystemAccessor;
    private ModelAccessor mModelAccessor;
    private DimensionAccessor mDimensionAccessor;
    private DataTypeAccessor mDataTypeAccessor;
    private ArrayList<Integer> mAllCompanies = null;
    private int company;
    private int financeCubeID;

    public void runUnitOfWork(InitialContext context) throws Exception {
        this.mInitialContext = context;
        if (this.getCheckpoint() == null) {
            this.firstTime();
        } else if (this.getCheckpoint().getCMCheckpoint() != null) {
            this.doChangeManagement();
        }

    }

    public boolean mustComplete() {
        return this.getCheckpoint() == null ? false : this.getCheckpoint().getCMCheckpoint() != null;
    }

    public void tidyActions() {
        super.tidyActions();
        this._log.info("tidyActions", "deleting change managements for task " + this.getTaskId());
        String l = "delete from CHANGE_MGMT where TASK_ID = ?";
        PreparedStatement i$ = null;

        try {
            i$ = this.getConnection().prepareStatement(l);
            i$.setInt(1, this.getTaskId());
            int cubePK = i$.executeUpdate();
            if (cubePK > 0) {
                this._log.info("tidyActions", "deleted changeMgmts=" + cubePK);
            }
        } catch (SQLException var8) {
            throw new RuntimeException("unable to delete change managements for task", var8);
        } finally {
            this.closeStatement(i$);
            this.closeConnection();
        }

        if (this.getCheckpoint() != null && this.getCheckpoint().getCMCheckpoint() != null) {
            List l1 = this.getCheckpoint().getCMCheckpoint().getImportCubes();
            Iterator i$1 = l1.iterator();

            while (i$1.hasNext()) {
                FinanceCubePK cubePK1 = (FinanceCubePK) i$1.next();
                ExternalSystemDAO dao = new ExternalSystemDAO();
                int extSysType = dao.getExtSysTypeForCube(cubePK1.getFinanceCubeId());
                this._log.info("tidyActions", "import_utils tidy (type=" + extSysType);
                dao.importValues(extSysType, 3, this.getTaskId(), cubePK1.getFinanceCubeId());
            }
        }

    }

    private void firstTime() throws Exception {
        this.setCheckpoint(new ImportGlobalMappedModel2Task$MyCheckpoint());
        this.log("Import Details:");
        ModelEVO modelEVO = null;

        int dimensionCount;
        for (dimensionCount = 0; dimensionCount < ((ImportGlobalMappedModel2TaskRequest) this.getRequest()).getMappedModelIds().length; ++dimensionCount) {
            int cmRequestPKs = ((ImportGlobalMappedModel2TaskRequest) this.getRequest()).getMappedModelIds()[dimensionCount];
            GlobalMappedModel2EVO globalMappedModelEVO = this.getMappedModelAccessor().getDetails(new GlobalMappedModel2PK(cmRequestPKs), "<0><1><2><3><4><5><6>");
            if (globalMappedModelEVO.getCompanyVisId() != null && this.mAllCompanies == null) {
                this.mAllCompanies = new ArrayList<Integer>();
                String[] str = globalMappedModelEVO.getCompanyVisId().split(",");
                for (int i = 0; i < str.length; i++) {
                    if (!str[i].trim().isEmpty()) {
                        this.mAllCompanies.add(Integer.parseInt(str[i].trim()));
                    }
                }
                if (this.company == 0) {
                    this.company = this.mAllCompanies.get(0);
                }
            }
            this.log(" ");
            this.log(" - - - - - Start import for company = " + this.company + " - - - - - ");
            this.log(" ");

            ExternalSystemEVO externalSystemEVO = this.getExternalSystemAccessor().getDetails(new ExternalSystemPK(globalMappedModelEVO.getExternalSystemId()), "");
            this.getCheckpoint().setExternalSystemType(externalSystemEVO.getSystemType());
            ChangeManagementType changeManagementType = null;

            try {
                DatatypeFactory engine = DatatypeFactory.newInstance();
                changeManagementType = new ChangeManagementType();
                changeManagementType.setExtractDateTime(engine.newXMLGregorianCalendar(new GregorianCalendar()));
                changeManagementType.setSourceSystemName(externalSystemEVO.getVisId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            modelEVO = this.getModelAccessor().getDetails(new ModelPK(globalMappedModelEVO.getModelId()), "<0><9>");
            this.log("  Model=" + modelEVO.getVisId() + " (id=" + modelEVO.getModelId() + ")");
            this.log("    System=" + externalSystemEVO.getVisId());
            this.log("    Location=<" + externalSystemEVO.getLocation() + ">");
            this.log("   " + (globalMappedModelEVO.getCompanyVisId() == null ? "" : " Company=" + this.company/* cmtr.getCompanyVisId() */) + " Ledger=" + globalMappedModelEVO.getLedgerVisId());

            // Mapped Years

            ArrayList mappedYears = new ArrayList();
            mappedYears.addAll(globalMappedModelEVO.getMappedCalendarYears());
            Collections.sort(mappedYears, new Comparator<MappedCalendarYearEVO>() {
                public int compare(final MappedCalendarYearEVO o1, final MappedCalendarYearEVO o2) {
                    return o1.getYear() - o2.getYear();
                }
            });
            this.log("    Years/Periods:");
            StringBuffer sb = new StringBuffer();
            Iterator mappedYearsIterator = mappedYears.iterator();

            while (mappedYearsIterator.hasNext()) {
                MappedCalendarYearEVO mappedCalendarYearEvo = (MappedCalendarYearEVO) mappedYearsIterator.next();
                int periodSum = 0;
                Iterator periodIterator = mappedCalendarYearEvo.getMappedCalendarElements().iterator();

                while (periodIterator.hasNext()) {
                    MappedCalendarElementEVO periodEVO = (MappedCalendarElementEVO) periodIterator.next();
                    if (periodEVO.getDimensionElementId() != null) {
                        ++periodSum;
                    }
                }

                sb.append('[');
                sb.append(String.valueOf(mappedCalendarYearEvo.getYear()));
                sb.append('/');
                sb.append(String.valueOf(periodSum));
                sb.append("] ");
            }

            // Mapped Dimensions

            this.log("      " + sb.toString());
            ArrayList mappedDimensions = new ArrayList();
            mappedDimensions.addAll(globalMappedModelEVO.getMappedDimensions());
            Collections.sort(mappedDimensions, new Comparator<MappedDimensionEVO>() {
                public int compare(MappedDimensionEVO o1, MappedDimensionEVO o2) {
                    return o1.getPathVisId().compareTo(o2.getPathVisId());
                }
            });
            Iterator mappedDimensionsIterator = mappedDimensions.iterator();
            
            // Mapped Hierarchies
            
            ArrayList mappedHierarchies;
            Iterator mappedHierarchiesIterator;
            while (mappedDimensionsIterator.hasNext()) {
                MappedDimensionEVO mappedDimensionEVO = (MappedDimensionEVO) mappedDimensionsIterator.next();
                DimensionEVO dimensionEVO = this.getDimensionAccessor().getDetails(new DimensionPK(mappedDimensionEVO.getDimensionId()), "<3>");
                this.log("    Dimension=" + dimensionEVO.getVisId() + " (id=" + dimensionEVO.getDimensionId() + ") excludeDisabledLeafs=" + mappedDimensionEVO.getExcludeDisabledLeafNodes());
                ChangeManagementEvent cmEventDimension = new ChangeManagementEvent();
                cmEventDimension.setAction(ChangeManagementActionType.fromValue("amend"));
                cmEventDimension.setType(ChangeManagementEventType.fromValue("dimension"));
                cmEventDimension.setVisId(dimensionEVO.getVisId());
                changeManagementType.getEvent().add(cmEventDimension);
                mappedHierarchies = new ArrayList();
                mappedHierarchies.addAll(mappedDimensionEVO.getMappedHierarchys());
                Collections.sort(mappedHierarchies, new Comparator<MappedHierarchyEVO>() {
                    public int compare(MappedHierarchyEVO o1, MappedHierarchyEVO o2) {
                        return (o1.getHierarchyVisId1() + o1.getHierarchyVisId2()).compareTo(o2.getHierarchyVisId1() + o2.getHierarchyVisId2());
                    }
                });
                mappedHierarchiesIterator = mappedHierarchies.iterator();

                while (mappedHierarchiesIterator.hasNext()) {
                    MappedHierarchyEVO mftEvo = (MappedHierarchyEVO) mappedHierarchiesIterator.next();
                    HierarchyEVO hierarchyEVO = dimensionEVO.getHierarchiesItem(new HierarchyPK(mftEvo.getHierarchyId()));
                    if (hierarchyEVO != null) {
                        this.log("      Hierarchy=" + hierarchyEVO.getVisId() + " (id=" + hierarchyEVO.getHierarchyId() + ")");
                    }
                }
                
                // Mapped Dimension Elements
                
                this.log("      Dimension Element mappings:");
                ArrayList mappedDimensionElements = new ArrayList();
                mappedDimensionElements.addAll(mappedDimensionEVO.getMappedDimensionElements());
                Collections.sort(mappedDimensionElements, new Comparator<MappedDimensionElementEVO>() {
                    public int compare(MappedDimensionElementEVO o1, MappedDimensionElementEVO o2) {
                        return (o1.getMappingType() + o1.getElementVisId1() + o1.getElementVisId2() + o1.getElementVisId3()).compareTo(o2.getMappingType() + o2.getElementVisId1() + o2.getElementVisId2() + o2.getElementVisId3());
                    }
                });
                this.displayMappedDimElems(dimensionEVO, mappedDimensionElements, 0);
                this.displayMappedDimElems(dimensionEVO, mappedDimensionElements, 1);
                this.displayMappedDimElems(dimensionEVO, mappedDimensionElements, 2);
                this.displayMappedDimElems(dimensionEVO, mappedDimensionElements, 3);
            }

            mappedDimensionsIterator = globalMappedModelEVO.getMappedFinanceCubes().iterator();
            
            // Mapped Finance Cube
            ArrayList mappedDataTypes;
            Iterator mappedDataTypesIterator;
            
            while (mappedDimensionsIterator.hasNext()) {
                MappedFinanceCubeEVO mappedFinanceCubeEVO = (MappedFinanceCubeEVO) mappedDimensionsIterator.next();
                // if ( mappedFinanceCubeEVO.getCompany() != null && mappedFinanceCubeEVO.getCompany().equals( this.company.get("companyId").toString() ) ) {
                FinanceCubeEVO financeCubeEVO = modelEVO.getFinanceCubesItem(new FinanceCubePK(mappedFinanceCubeEVO.getFinanceCubeId()));
                this.log("    FinanceCube= _ (id=" + financeCubeEVO.getFinanceCubeId() + ")");

                this.financeCubeID = financeCubeEVO.getFinanceCubeId();

                boolean availableForImport = false;
                mappedDataTypes = new ArrayList();
                mappedDataTypes.addAll(mappedFinanceCubeEVO.getMappedDataTypes());
                Collections.sort(mappedDataTypes, new Comparator<MappedDataTypeEVO>() {
                    public int compare(MappedDataTypeEVO o1, MappedDataTypeEVO o2) {
                        return (o1.getValueTypeVisId1() + o1.getValueTypeVisId2() + o1.getValueTypeVisId3()).compareTo(o2.getValueTypeVisId1() + o2.getValueTypeVisId2() + o2.getValueTypeVisId3());
                    }
                });
                mappedDataTypesIterator = mappedDataTypes.iterator();

                while (mappedDataTypesIterator.hasNext()) {
                    MappedDataTypeEVO mappedDataTypeEVO = (MappedDataTypeEVO) mappedDataTypesIterator.next();
                    DataTypeEVO dataTypeEVO = this.getDataTypeAccessor().getDetails(new DataTypePK((short) mappedDataTypeEVO.getDataTypeId()), "");
                    this.log("      valueType=" + mappedDataTypeEVO.getValueTypeVisId1() + (mappedDataTypeEVO.getValueTypeVisId2() == null ? "" : "/" + mappedDataTypeEVO.getValueTypeVisId2()) + (mappedDataTypeEVO.getValueTypeVisId3() == null ? "" : "/" + mappedDataTypeEVO.getValueTypeVisId3()) + " dataType=" + dataTypeEVO.getVisId() + " importable=" + dataTypeEVO.getAvailableForImport() + " impExpStatus=" + (mappedDataTypeEVO.getImpExpStatus() == 1 ? " export_only" : (mappedDataTypeEVO.getImpExpStatus() == 2 ? " import_export" : (mappedDataTypeEVO.getImpExpStatus() == 0 ? " import_only" : "?"))));
                    this.log("        yearOffsets: import from=" + mappedDataTypeEVO.getImpStartYearOffset() + " to=" + mappedDataTypeEVO.getImpEndYearOffset() + " export from=" + mappedDataTypeEVO.getExpStartYearOffset() + " to=" + mappedDataTypeEVO.getExpEndYearOffset());
                    if (dataTypeEVO.getAvailableForImport() && mappedDataTypeEVO.getImpExpStatus() != 1) {
                        availableForImport = true;
                    }
                }

                if (availableForImport) {
                    ChangeManagementEvent cmEventFinanceCube = new ChangeManagementEvent();
                    cmEventFinanceCube.setAction(ChangeManagementActionType.fromValue("import"));
                    cmEventFinanceCube.setType(ChangeManagementEventType.fromValue("import-values"));
                    cmEventFinanceCube.setVisId(String.valueOf(financeCubeEVO.getFinanceCubeId()));
                    changeManagementType.getEvent().add(cmEventFinanceCube);
                }
                // }
            }

            if (((ImportGlobalMappedModel2TaskRequest) this.getRequest()).isSafeMode() && !this.checkUnmappedDimElements(globalMappedModelEVO, mappedDimensions)) {
                this.setCompletionExceptionMessage("Import terminated (no data has been changed).");
                this.setCheckpoint((TaskCheckpoint) null);
                return;
            }

            ChangeMgmtEngine cmEngine = new ChangeMgmtEngine(this.mInitialContext, (ChangeManagementTaskRequest) null, (ChangeManagementCheckPoint) null, this, this);
            cmEngine.registerUpdateRequest(changeManagementType);
            cmEngine.setChangeMgmtTaskIds(ChangeMgmtEngine.queryChangeRequestPKs(modelEVO.getEntityRef()), this.getTaskId());
        }

        dimensionCount = modelEVO.getModelDimensionRels().size();
        List var20 = ChangeMgmtEngine.queryChangeRequestPKs(modelEVO.getEntityRef());
        ChangeManagementTaskRequest var21 = new ChangeManagementTaskRequest(modelEVO.getEntityRef(), dimensionCount, var20);
        ChangeManagementCheckPoint var22 = new ChangeManagementCheckPoint();
        ChangeMgmtEngine var23 = new ChangeMgmtEngine(this.mInitialContext, var21, var22, this, this);
        var23.setTaskId(this.getTaskId());
        var23.setUserId(this.getUserId());
        var23.setChangeMgmtTaskIds(var20, this.getTaskId());
        this.getCheckpoint().setCMRequest(var21);
        this.getCheckpoint().setCMCheckpoint(var22);
    }

    private boolean checkUnmappedDimElements(GlobalMappedModel2EVO mmEvo, List<MappedDimensionEVO> dimRows) throws Exception {
        if (this.company != this.mAllCompanies.get(0)) {
            return true;
        }
        String warning = null;
        Iterator i$ = dimRows.iterator();

        while (i$.hasNext()) {
            MappedDimensionEVO mdEvo = (MappedDimensionEVO) i$.next();
            DimensionEVO dimEvo = this.getDimensionAccessor().getDetails(new DimensionPK(mdEvo.getDimensionId()), "<3>");
            this.log(" ");
            this.log("checking removed elements in dimension " + dimEvo.getVisId());
            ExternalSystemDAO xsdao = new ExternalSystemDAO();
            EntityList diffs = xsdao.importDimension(this.getTaskId(), this.getCheckpoint().getExternalSystemType(), mdEvo.getDimensionId(), this.company);
            int unmappedCount = 0;
            int batchCount = 0;
            PreparedStatement batchStmt = null;

            try {
                batchStmt = this.getConnection().prepareStatement("insert into FS_DE\n(VIS_ID, FS_DESCR, FS_IS_CREDIT, FS_NOT_PLANNABLE, FS_DISABLED)\nvalues\n(?, nvl(?,\' \'), nvl(?,\' \'), nvl(?,\' \'), nvl(?,\' \'))");

                for (int stmt = 0; stmt < diffs.getNumRows(); ++stmt) {
                    String sqle = (String) diffs.getValueAt(stmt, "TYPE");
                    String action = (String) diffs.getValueAt(stmt, "ACTION");
                    String visId = (String) diffs.getValueAt(stmt, "VIS_ID");
                    String descr = (String) diffs.getValueAt(stmt, "DESCR");
                    String notPlannableStr = (String) diffs.getValueAt(stmt, "NOT_PLANNABLE");
                    String disabledStr = (String) diffs.getValueAt(stmt, "DISABLED");
                    String isCreditStr = (String) diffs.getValueAt(stmt, "IS_CREDIT");
                    if (sqle.equals("dimension-element") && action.equals("delete")) {
                        batchStmt.setString(1, visId);
                        batchStmt.setString(2, descr);
                        batchStmt.setString(3, isCreditStr);
                        batchStmt.setString(4, notPlannableStr);
                        batchStmt.setString(5, disabledStr);
                        batchStmt.addBatch();
                        ++unmappedCount;
                        ++batchCount;
                        if (batchCount > 100) {
                            batchStmt.executeBatch();
                            batchCount = 0;
                        }
                    }
                }

                if (batchCount > 0) {
                    batchStmt.executeBatch();
                }
            } catch (SQLException var32) {
                throw new RuntimeException(this.getEntityName() + " checkUnmappedElements", var32);
            } finally {
                this.closeStatement(batchStmt);
                this.closeConnection();
            }

            if (unmappedCount != 0) {
                CallableStatement var34 = null;

                try {
                    var34 = this.getConnection().prepareCall("begin import_utils.checkUnmappedDimElems(?,?,?,?); end;");
                    var34.setInt(1, this.getTaskId());
                    var34.setInt(2, mmEvo.getModelId());
                    var34.setInt(3, mdEvo.getDimensionId());
                    var34.registerOutParameter(4, 12);
                    var34.execute();
                    if (var34.getString(4) != null && var34.getString(4).equals("Y")) {
                        warning = "Y";
                    }
                } catch (SQLException var30) {
                    throw new RuntimeException(this.getEntityName() + " checkUnmappedElements", var30);
                } finally {
                    this.closeStatement(var34);
                    this.closeConnection();
                }
            }
        }

        if (warning != null && warning.equals("Y")) {
            this.log(" ");
            this.logInfo("Non-readonly data would be lost due to unmapped dimension elements.");
            this.log(" ");
            this.logInfo("Either:");
            this.logInfo("  (a) change the mapping to include the unmapped dimension elements");
            this.logInfo("  (b) to import regardless, use the Import (skip validation) menu option");
            return false;
        } else {
            return true;
        }
    }

    private void displayMappedDimElems(DimensionEVO dimEvo, List<MappedDimensionElementEVO> deRows, int mappingType) {
        StringBuffer sb = new StringBuffer();
        boolean firstOfType = true;
        Iterator i$ = deRows.iterator();

        while (i$.hasNext()) {
            MappedDimensionElementEVO mdeEvo = (MappedDimensionElementEVO) i$.next();
            if (mdeEvo.getMappingType() == mappingType) {
                if (firstOfType) {
                    if (mappingType == 0) {
                        this.log("        specific names [Name" + (dimEvo.getType() == 1 ? ",Type" : "") + "]:");
                    } else if (mappingType == 1) {
                        this.log("        prefixes [Prefix" + (dimEvo.getType() == 1 ? ",Type" : "") + "]:");
                    } else if (mappingType == 2) {
                        this.log("        ranges [From,To" + (dimEvo.getType() == 1 ? ",Type" : "") + "]:");
                    } else {
                        this.log("        hierarchy elements: {HierarchyElement,Hierarchy,HierarchyType]");
                    }
                }

                firstOfType = false;
                if (sb.length() > 60) {
                    this.log("        " + sb.toString());
                    sb.setLength(0);
                }

                sb.append('[');
                sb.append(mdeEvo.getElementVisId1());
                if (mdeEvo.getMappingType() == 2 || mdeEvo.getMappingType() == 3) {
                    sb.append(',');
                    sb.append(mdeEvo.getElementVisId2());
                }

                if (mdeEvo.getElementVisId3() != null && !mdeEvo.getElementVisId3().equals(" ")) {
                    sb.append(',');
                    sb.append(mdeEvo.getElementVisId3());
                }

                sb.append("] ");
            }
        }

        if (sb.length() > 0) {
            this.log("        " + sb.toString());
        }

    }

    private void createCMUpdateRequest(ExternalSystemEVO xsEvo, ModelEVO modelEvo, Object event) throws ValidationException {
        ChangeManagementType myCm = null;

        try {
            DatatypeFactory factory = DatatypeFactory.newInstance();
            myCm = new ChangeManagementType();
            myCm.setExtractDateTime(factory.newXMLGregorianCalendar(new GregorianCalendar()));
            myCm.setSourceSystemName(xsEvo.getVisId());
            myCm.getEvent().add((ChangeManagementEvent) event);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ChangeMgmtEngine engine = new ChangeMgmtEngine(this.mInitialContext, (ChangeManagementTaskRequest) null, (ChangeManagementCheckPoint) null, this, this);
        engine.registerUpdateRequest(myCm);
        engine.setChangeMgmtTaskIds(ChangeMgmtEngine.queryChangeRequestPKs(modelEvo.getEntityRef()), this.getTaskId());
    }

    private void doChangeManagement() throws Exception {
        ChangeMgmtEngine engine = new ChangeMgmtEngine(this.mInitialContext, this.getCheckpoint().getCMRequest(), this.getCheckpoint().getCMCheckpoint(), this, this);
        engine.setTaskId(this.getTaskId());
        engine.setUserId(this.getUserId());
        // ChangeManagementCheckPoint cmCheckpoint = engine.runChangeManagement();
        // int mappedModelId = ((ImportGlobalMappedModel2TaskRequest)this.getRequest()).getMappedModelIds()[ 0 ];
        ChangeManagementCheckPoint cmCheckpoint = engine.runGlobal2ChangeManagement(this.company);
        if (cmCheckpoint != null) {
            this.getCheckpoint().setCMCheckpoint(cmCheckpoint);
        } else {
            this.getCheckpoint().setCMRequest((ChangeManagementTaskRequest) null);
            this.getCheckpoint().setCMCheckpoint((ChangeManagementCheckPoint) null);
            this.setCheckpoint((TaskCheckpoint) null);
        }

    }

    public int getReportType() {
        return 5;
    }

    public ImportGlobalMappedModel2Task$MyCheckpoint getCheckpoint() {
        return (ImportGlobalMappedModel2Task$MyCheckpoint) super.getCheckpoint();
    }

    public String getEntityName() {
        return "ImportMappedModelTask";
    }

    private static String decodeNullableYNValue(String value) {
        return value != null ? (value.equalsIgnoreCase("Y") ? "true" : "false") : null;
    }

    private GlobalMappedModel2Accessor getMappedModelAccessor() {
        if (this.mMappedModelAccessor == null) {
            this.mMappedModelAccessor = new GlobalMappedModel2Accessor(this.mInitialContext);
        }

        return this.mMappedModelAccessor;
    }

    private ExternalSystemAccessor getExternalSystemAccessor() {
        if (this.mExternalSystemAccessor == null) {
            this.mExternalSystemAccessor = new ExternalSystemAccessor(this.mInitialContext);
        }

        return this.mExternalSystemAccessor;
    }

    private ModelAccessor getModelAccessor() {
        if (this.mModelAccessor == null) {
            this.mModelAccessor = new ModelAccessor(this.mInitialContext);
        }

        return this.mModelAccessor;
    }

    private DimensionAccessor getDimensionAccessor() {
        if (this.mDimensionAccessor == null) {
            this.mDimensionAccessor = new DimensionAccessor(this.mInitialContext);
        }

        return this.mDimensionAccessor;
    }

    private DataTypeAccessor getDataTypeAccessor() {
        if (this.mDataTypeAccessor == null) {
            this.mDataTypeAccessor = new DataTypeAccessor(this.mInitialContext);
        }

        return this.mDataTypeAccessor;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public ArrayList<Integer> getAllCompanies() {
        return this.mAllCompanies;
    }

    public int getFinanceCubeID() {
        return this.financeCubeID;
    }
}
