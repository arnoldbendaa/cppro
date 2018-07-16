// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dataentry;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.CellCalculationDetails;
import com.cedar.cp.api.dataEntry.CellContents;
import com.cedar.cp.api.dataEntry.DataExtract;
import com.cedar.cp.api.dataEntry.FinanceSystemCellData;
import com.cedar.cp.api.dataEntry.MassUpdateParameters;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.facades.ExtractDataDTO;
import com.cedar.cp.api.financesystem.FinanceSystemCheckDetail;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.dataEntry.AvailableStructureElementELO;
import com.cedar.cp.dto.dataEntry.AvailableStructureElementImpl;
import com.cedar.cp.dto.dataEntry.CellContentsImpl;
import com.cedar.cp.dto.dataEntry.DataExtractImpl;
import com.cedar.cp.dto.dataEntry.MassUpdateTaskRequest;
import com.cedar.cp.dto.dataEntry.RechargeTaskRequest;
import com.cedar.cp.dto.datatype.AllDataTypeForFinanceCubeELO;
import com.cedar.cp.dto.datatype.PickerDataTypesFinCubeELO;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.calendar.CalendarInfoImpl;
import com.cedar.cp.dto.extsys.ExtSysTransactionQueryParams;
import com.cedar.cp.dto.financesystem.FinanceSystemCheckDetailDTO;
import com.cedar.cp.dto.impexp.CubeImportTaskRequest;
import com.cedar.cp.dto.impexp.ImpExpHdrPK;
import com.cedar.cp.dto.model.AllAttachedDataTypesForFinanceCubeELO;
import com.cedar.cp.dto.model.AllRootsForModelELO;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubeDetailsELO;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.NodesForUserAndModelELO;
import com.cedar.cp.dto.systemproperty.SystemPropertyELO;
import com.cedar.cp.ejb.api.dataentry.DataEntryServer;
import com.cedar.cp.ejb.base.cube.CellNote;
import com.cedar.cp.ejb.impl.dataentry.DataEntryContextDAO;
import com.cedar.cp.ejb.impl.dataentry.DataEntryDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.ejb.impl.formnotes.FormNotesDAO;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrDAO;
import com.cedar.cp.ejb.impl.impexp.ImpExpHdrEVO;
import com.cedar.cp.ejb.impl.model.BudgetStateDAO;
import com.cedar.cp.ejb.impl.model.BudgetUserDAO;
import com.cedar.cp.ejb.impl.model.ExtractDataHelper;
import com.cedar.cp.ejb.impl.model.FinanceCubeDAO;
import com.cedar.cp.ejb.impl.model.FinanceCubeDataTypeDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.model.ModelDimensionRelEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.util.DefaultValueMapping;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.ValueMapping;
import com.cedar.cp.util.performance.PerformanceDatumImpl;
import com.cedar.cp.util.task.TaskMessageFactory;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;
import com.cedar.cp.util.xmlform.inputs.StructureElementReference;
import com.cedar.cp.util.xmlform.reader.XMLReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DataEntrySEJB implements SessionBean {

	private int mCurrentIndex;
	private SessionContext mContext;
	private transient Log mLog = new Log(this.getClass());
	private static int s3DFormCheck = 0;
	private static int s4DFormCheck = 1;
	DataEntryDAO dao = new DataEntryDAO();
	private ValueMapping getDataTypes(int financeCubeId) {
		FinanceCubeDataTypeDAO dataTypeDao = new FinanceCubeDataTypeDAO();
		AllAttachedDataTypesForFinanceCubeELO list = dataTypeDao.getAllAttachedDataTypesForFinanceCube(financeCubeId);
		String[] literals = new String[list.getNumRows()];
		String[] values = new String[list.getNumRows()];

		for (int i = 0; i < list.getNumRows(); ++i) {
			String visId = (String) list.getValueAt(i, "VisId");
			String descr = (String) list.getValueAt(i, "Description");
			literals[i] = visId + " - " + descr;
			values[i] = visId;
		}

		return new DefaultValueMapping(literals, values);
	}

	public boolean doesCellMatchChartOfAccounts(int modelId, int financeCubeId, int[] cellReference, String[] cellVisIds, int userId, int budgetCycleId) throws EJBException {
		try {
			boolean e = (new ExternalSystemDAO()).checkCOA(modelId, financeCubeId, cellVisIds, userId);
			if ((new BudgetStateDAO()).getCurrentState(budgetCycleId, cellReference[0]) > 2) {
				throw new ValidationException("Responsibility Area is not in a Preparing state");
			} else {
				return e;
			}
		} catch (ValidationException var8) {
			throw new EJBException(var8.getMessage(), var8);
		} catch (SQLException var9) {
			throw new EJBException("can\'t check COA", var9);
		}
	}

	public DataExtract dataImportPrep(Object key) throws EJBException, ValidationException {
		DataExtractImpl dataImpl = new DataExtractImpl();
		DataTypeDAO dao = new DataTypeDAO();
		if (key instanceof FinanceCubeRefImpl) {
			FinanceCubeRef dataTypeMap = (FinanceCubeRef) key;
			key = dataTypeMap.getPrimaryKey();
		}

		PickerDataTypesFinCubeELO eList;
		if (key instanceof FinanceCubeCK) {
			eList = dao.getPickerDataTypesWeb(((FinanceCubeCK) key).getFinanceCubePK().getFinanceCubeId(), new int[] { 0, 4 }, true);
		} else {
			eList = dao.getPickerDataTypesWeb(((FinanceCubePK) key).getFinanceCubeId(), new int[] { 0, 4 }, true);
		}

		HashMap var8 = new HashMap();

		for (int i = 0; i < eList.getNumRows(); ++i) {
			DataTypeRef value = (DataTypeRef) eList.getValueAt(i, "DataType");
			var8.put(value.getNarrative(), value);
		}

		dataImpl.setValidDataTypes(var8);
		return dataImpl;
	}

	public DataExtract dataImport(DataExtract dataExtract) throws EJBException, ValidationException {
		DataExtractImpl dataImpl = (DataExtractImpl) dataExtract;

		try {
			int e = this.validateDataImport(dataExtract);
			CubeImportTaskRequest request = new CubeImportTaskRequest(e, dataImpl);
			int taskId = TaskMessageFactory.issueNewTask(new InitialContext(), true, request, dataImpl.getUserId());
			dataImpl.setTaskId(taskId);
			return dataImpl;
		} catch (ValidationException var6) {
			var6.printStackTrace();
			throw var6;
		} catch (NamingException var7) {
			throw new EJBException("Can\'t submit import", var7);
		} catch (Exception var8) {
			var8.printStackTrace();
			throw new EJBException(var8);
		}
	}

	private int validateDataImport(DataExtract dataExtract) throws ValidationException {
		if (dataExtract.getData().isEmpty()) {
			throw new ValidationException("Import rows cannot be empty");
		} else {
			//DataEntryDAO dao = new DataEntryDAO();
			int financeCubeId = dao.getFinanceCubeIdFromVisId(dataExtract.getModelVisId(), dataExtract.getFinanceCubeVisId());
			if (financeCubeId < 1) {
				throw new ValidationException("Finance cube cannot be found \'" + dataExtract.getFinanceCubeVisId() + "\'");
			} else {
				List validMappings = dao.getAllElems(financeCubeId, dataExtract.getHierarchyList());
				HashMap dataTypes = new HashMap();
				HashMap measureScales = new HashMap();
				DataTypeDAO dtDAO = new DataTypeDAO();
				AllDataTypeForFinanceCubeELO dataTypesList = dtDAO.getAllDataTypeForFinanceCube(financeCubeId);

				for (int fcDAO = 0; fcDAO < dataTypesList.getNumRows(); ++fcDAO) {
					DataTypeRef rollUpRules = (DataTypeRef) dataTypesList.getValueAt(fcDAO, "DataType");
					Boolean firstRow = (Boolean) dataTypesList.getValueAt(fcDAO, "ReadOnlyFlag");
					if (rollUpRules.getSubType() != 3 && rollUpRules.getSubType() != 2 && rollUpRules.getSubType() != 1 && (firstRow == null || !firstRow.booleanValue())) {
						String nDims = rollUpRules.getNarrative();
						dataTypes.put(nDims, rollUpRules);
						Integer iter = (Integer) dataTypesList.getValueAt(fcDAO, "MeasureScale");
						measureScales.put(nDims, iter);
					}
				}

				FinanceCubeDAO var19 = new FinanceCubeDAO();
				Map var21 = var19.getRollUpRules(financeCubeId);
				int n;
				Object[] row;
				DataTypeRef ref;
				Integer o;
				Object[] var20;
				Iterator var23;
				int var22;
				int var25;
				if (!dataExtract.getData().isEmpty()) {
					var20 = (Object[]) ((Object[]) dataExtract.getData().get(0));
					var22 = var20.length - 2;
					if (var22 < 1) {
						throw new ValidationException("Not enough data in the import rows");
					}

					var23 = dataExtract.getData().iterator();
					n = 0;

					while (var23.hasNext()) {
						++n;
						row = (Object[]) ((Object[]) var23.next());
						ref = (DataTypeRef) dataTypes.get(row[row.length - 2]);
						if (ref == null) {
							throw new ValidationException("Invalid datatype " + row[row.length - 2]);
						}

						if (row.length != var22 + 2) {
							throw new ValidationException("Row " + n + " does not have the same amount of data as the rest");
						}

						if (ref.getMeasureClass().intValue() == 3 || ref.getMeasureClass().intValue() == 4 || ref.getMeasureClass().intValue() == 2) {
							Object[] i = (Object[]) ((Object[]) row[row.length - 1]);
							if (i[2] == null) {
								var23.remove();
								continue;
							}
						}

						for (var25 = 0; var25 < var20.length - 2; ++var25) {
							o = this.getElementId(validMappings, var21, ref, var25, (String) row[var25]);
							if (o == null) {
								throw new ValidationException("Invalid key \'" + row[var25] + "\' on row " + n);
							}

							row[var25] = o;
						}

						Integer var24 = (Integer) measureScales.get(row[row.length - 2]);
						Object[] var26 = (Object[]) ((Object[]) row[row.length - 1]);
						if (ref.getSubType() == 0) {
							var26[0] = Double.valueOf(((Double) var26[0]).doubleValue() * 10000.0D);
						} else if (ref.getSubType() == 4 && (ref.getSubType() == 4 && ref.getMeasureClass().intValue() == 4 || ref.getMeasureClass().intValue() == 3 || ref.getMeasureClass().intValue() == 2)) {
							var26[2] = new Timestamp(((Date) var26[2]).getTime());
						}
					}
				}

				if (dataExtract.getNoteData() != null && !dataExtract.getNoteData().isEmpty()) {
					var20 = (Object[]) ((Object[]) dataExtract.getNoteData().get(0));
					var22 = var20.length - 2;
					if (var22 < 1) {
						throw new ValidationException("Not enough data in the import rows");
					}

					var23 = dataExtract.getNoteData().iterator();
					n = 0;

					while (var23.hasNext()) {
						++n;
						row = (Object[]) ((Object[]) var23.next());
						ref = (DataTypeRef) dataTypes.get(row[row.length - 2]);
						if (ref == null) {
							throw new ValidationException("Invalid datatype " + row[row.length - 2]);
						}

						if (row.length != var22 + 2) {
							throw new ValidationException("Row " + n + " does not have the same amount of data as the rest");
						}

						for (var25 = 0; var25 < var20.length - 2; ++var25) {
							o = this.getElementId(validMappings, var21, ref, var25, (String) row[var25]);
							if (o == null) {
								throw new ValidationException("Invalid key \'" + row[var25] + "\' on row " + n);
							}

							row[var25] = o;
						}
					}
				}

				return financeCubeId;
			}
		}
	}

	private Integer getElementId(List<Map<String, Integer>[]> allAndLeafElems, Map<String, boolean[]> rollupRules, DataTypeRef dtRef, int dim, String visId) {
		Map correctMap;
		if (dtRef.getSubType() == 0) {
			correctMap = ((Map[]) allAndLeafElems.get(dim))[1];
		} else if (dtRef.getMeasureClass().intValue() == 1) {
			if (((boolean[]) rollupRules.get(dtRef.getNarrative()))[dim]) {
				correctMap = ((Map[]) allAndLeafElems.get(dim))[1];
			} else {
				correctMap = ((Map[]) allAndLeafElems.get(dim))[0];
			}
		} else {
			correctMap = ((Map[]) allAndLeafElems.get(dim))[0];
		}

		return (Integer) correctMap.get(visId);
	}

	private ImpExpHdrPK createNewImportBatchHeader(int financeCubeId) {
		ImpExpHdrDAO dao = new ImpExpHdrDAO();
		ImpExpHdrEVO evo = new ImpExpHdrEVO();
		evo.setFinanceCubeId(financeCubeId);
		evo.setBatchTs(new Timestamp(System.currentTimeMillis()));
		evo.setBatchType(0);
		dao.setDetails(evo);
		ImpExpHdrPK pk = null;

		try {
			pk = dao.create();
			return pk;
		} catch (Exception var6) {
			throw new CPException(var6.getMessage(), var6);
		}
	}

	public DataExtract getDataExtract(EntityRef financeCubeRef, List businessDims, List accountIds, List periodIds, List dataTypes, boolean fullIntersection, boolean count) throws EJBException {
		DataExtractImpl result = new DataExtractImpl();

		try {
			FinanceCubeCK e = (FinanceCubeCK) financeCubeRef.getPrimaryKey();
			FinanceCubePK fpk = (FinanceCubePK) e.getPK();
			int financeCubeId = fpk.getFinanceCubeId();
			int modelId = e.getModelPK().getModelId();
			//DataEntryDAO dao = new DataEntryDAO();
			List[] extractLists;
			if (count) {
				extractLists = dao.getDataExtract(financeCubeId, businessDims, accountIds, periodIds, dataTypes, fullIntersection, true, (Integer) null);
				BigDecimal var29 = (BigDecimal) ((Object[]) ((Object[]) extractLists[1].get(0)))[0];
				result.setRowCount(var29.intValue());
				return result;
			} else {
				extractLists = dao.getDataExtract(financeCubeId, businessDims, accountIds, periodIds, dataTypes, fullIntersection, count, Integer.valueOf('\ufffa'));
				result.setCalendarVisIds(extractLists[0]);
				result.setData(extractLists[1]);
				FinanceCubeDAO fcDAO = new FinanceCubeDAO();
				FinanceCubeDetailsELO fcDetails = fcDAO.getFinanceCubeDetails(financeCubeId);
				if (fcDetails.hasNext()) {
					fcDetails.next();
					result.setModelVisId(fcDetails.getModelEntityRef().getNarrative());
					result.setFinanceCubeVisId(fcDetails.getFinanceCubeEntityRef().getNarrative());
				}

				HashMap hierarchyMap = new HashMap();
				ModelDAO mDAO = new ModelDAO();
				AllRootsForModelELO rootELO = mDAO.getAllRootsForModel(modelId);

				while (rootELO.hasNext()) {
					rootELO.next();
					hierarchyMap.put(Integer.valueOf(rootELO.getStructureId()), rootELO.getHierarchyEntityRef().getNarrative());
				}

				ArrayList hierarchyList = new ArrayList();

				int i;
				for (i = 0; i < businessDims.size(); ++i) {
					ArrayList sRef = (ArrayList) businessDims.get(i);

					for (int sPK = 0; sPK < sRef.size(); ++sPK) {
						StructureElementRef s = (StructureElementRef) sRef.get(sPK);
						StructureElementPK sPK1 = (StructureElementPK) s.getPrimaryKey();
						String s1 = (String) hierarchyMap.get(Integer.valueOf(sPK1.getStructureId()));
						hierarchyList.add(s1);
					}
				}

				for (i = 0; i < accountIds.size(); ++i) {
					StructureElementRef var30 = (StructureElementRef) accountIds.get(i);
					StructureElementPK var31 = (StructureElementPK) var30.getPrimaryKey();
					String var32 = (String) hierarchyMap.get(Integer.valueOf(var31.getStructureId()));
					hierarchyList.add(var32);
				}

				result.setHierarchyList(hierarchyList);
				return result;
			}
		} catch (EJBException var27) {
			var27.printStackTrace();
			throw var27;
		} catch (Exception var28) {
			var28.printStackTrace();
			throw new EJBException(var28);
		}
	}

	public List getFinanceCubeDataSlice(String modelVisId, String financeCubeVisId, String aggregationRule, int[] rowHeadings, int[] columnHeadings, int[] filterHeadings, List rows, List columns, List filters) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		int financeCubeId = dao.getFinanceCubeIdFromVisId(modelVisId, financeCubeVisId);
		if (financeCubeId < 1) {
			throw new CPException("Finance cube cannot be found \'" + financeCubeVisId + "\'");
		} else {
			return dao.getFinanceCubeDataSlice(financeCubeId, aggregationRule, rowHeadings, columnHeadings, filterHeadings, rows, columns, filters);
		}
	}

	public List getAllStructureElementsForFinanceCube(int userId, String modelVisId, String fcVisId) throws EJBException {
		ArrayList results = new ArrayList();
		//DataEntryDAO dao = new DataEntryDAO();
		int fcId = dao.getFinanceCubeIdFromVisId(modelVisId, fcVisId);
		Object dimensionIds = null;
		Object hierarchyIds = null;
		String[] hierarchyVisIds = null;
		FinanceCubePK fcPk = new FinanceCubePK(fcId);

		int[] var25;
		int[] var26;
		int var37;
		try {
			InitialContext fDao = new InitialContext();
			DimensionAccessor dataTypes = new DimensionAccessor(fDao);
			ModelAccessor i = new ModelAccessor(fDao);
			ModelEVO rootedAt = i.getDetails(fcPk, "<9>");
			Collection children = rootedAt.getModelDimensionRels();
			var25 = new int[children.size()];
			var26 = new int[children.size()];
			hierarchyVisIds = new String[children.size()];
			Iterator list = children.iterator();

			while (list.hasNext()) {
				ModelDimensionRelEVO ck = (ModelDimensionRelEVO) list.next();
				var25[ck.getDimensionSeqNum()] = ck.getDimensionId();
				DimensionEVO modelPk = dataTypes.getDetails(new DimensionPK(ck.getDimensionId()), "<3>");
				Collection modelId = modelPk.getHierarchies();
				HierarchyEVO nodes;
				Iterator buDao;
				if (ck.getDimensionSeqNum() == 0) {
					buDao = modelId.iterator();

					while (buDao.hasNext()) {
						nodes = (HierarchyEVO) buDao.next();
						if (nodes.getHierarchyId() == rootedAt.getBudgetHierarchyId()) {
							var26[0] = nodes.getHierarchyId();
							hierarchyVisIds[0] = nodes.getVisId();
							break;
						}
					}
				} else {
					if (modelId == null || modelId.isEmpty()) {
						throw new IllegalStateException("no hierarchy for dimension " + modelPk);
					}

					buDao = modelId.iterator();
					nodes = (HierarchyEVO) buDao.next();
					var26[ck.getDimensionSeqNum()] = nodes.getHierarchyId();
					hierarchyVisIds[ck.getDimensionSeqNum()] = nodes.getVisId();
				}
			}

			results.add(var26);
			int[] var36 = new int[var26.length];

			for (int var39 = 0; var39 < var26.length; ++var39) {
				var37 = dao.getMaxDepthForHierarchy(var26[var39]);
				var36[var39] = var37;
			}

			results.add(var36);
		} catch (Exception var24) {
			throw new EJBException("Can\'t get details", var24);
		}

		FinanceCubeDataTypeDAO var27 = new FinanceCubeDataTypeDAO();
		AllAttachedDataTypesForFinanceCubeELO var28 = var27.getAllAttachedDataTypesForFinanceCube(fcId);
		results.add(var28);

		for (int var29 = 0; var29 < var25.length; ++var29) {
			byte var31 = 0;
			ArrayList var30 = new ArrayList();
			if (var29 == 0) {
				ModelDAO var34 = new ModelDAO();
				FinanceCubeCK var35 = var34.getFinanceCubeCK(fcPk);
				ModelPK var38 = var35.getModelPK();
				var37 = var38.getModelId();
				BudgetUserDAO var40 = new BudgetUserDAO();
				NodesForUserAndModelELO var41 = var40.getNodesForUserAndModel(var37, userId);
				if (var41.getNumRows() < 1) {
					throw new IllegalStateException("No budget locations are accessible by user identified by id " + userId);
				}

				for (int r = 0; r < var41.getNumRows(); ++r) {
					int var32 = ((Integer) var41.getValueAt(r, "StructureElementId")).intValue();
					AvailableStructureElementELO list1 = dao.getRootedStructureElement(var32, var25[var29], var26[var29]);
					var30.add(this.checkStructureElementsAgainstFinanceSystem(Collections.EMPTY_SET, list1).getList().get(0));
				}
			} else {
				AvailableStructureElementELO var33 = dao.getRootedStructureElement(var31, var25[var29], var26[var29]);
				var30.add(this.checkStructureElementsAgainstFinanceSystem(Collections.EMPTY_SET, var33).getList().get(0));
			}

			this.mLog.debug("For dimension " + var29 + " we have element count " + var30.size());
			results.add(var30);
		}

		return results;
	}

	public EntityList getImmediateChildren(int userId, Object primaryKey) throws EJBException {
		StructureElementDAO dao = new StructureElementDAO();
		if (primaryKey instanceof StructureElementRef) {
			primaryKey = ((StructureElementRef) primaryKey).getPrimaryKey();
		}

		int structureId = ((StructureElementPK) primaryKey).getStructureId();
		int elementId = ((StructureElementPK) primaryKey).getStructureElementId();
		ImmediateChildrenELO children = dao.getImmediateChildren(structureId, elementId);
		return children;
	}

    public EntityList getImmediateChildrenLimitedByPermission(int dimensionType, int userId, int budgetCycleId, Object primaryKey) throws EJBException {
        StructureElementDAO dao = new StructureElementDAO();
        if (primaryKey instanceof StructureElementRef) {
            primaryKey = ((StructureElementRef) primaryKey).getPrimaryKey();
        }

        int structureId = ((StructureElementPK) primaryKey).getStructureId();
        int parentId = ((StructureElementPK) primaryKey).getStructureElementId();
        ImmediateChildrenELO children;
        if (dimensionType == 0) {
            // if account dimension, limited by budget responsibility permission
            children = dao.getImmediateAccountChildrenLimitedByPermission(structureId, parentId, userId, budgetCycleId);

        } else if (dimensionType == 1) {
            // if bussiness dimension, no need for limitations
            children = dao.getImmediateChildren(structureId, parentId);

        } else if (dimensionType == 2) {
            // if calendar dimension, limited by the range from the budget cycle
            children = dao.getImmediateCalendarChildrenLimitedByPermission(structureId, parentId, budgetCycleId);

        } else {
            throw new IllegalStateException("Unexpected value for the parametr 'dimensionType' (" + dimensionType + ")");
        }
        return children;
    }

	public EntityList getElementsForConstraints(boolean raDimension, boolean calDimension, int userId, String financeCubeVisId, int hierarchyId, List constraints) throws EJBException {
		if (hierarchyId < 0) {
			DataTypeDAO dao1 = new DataTypeDAO();
			return dao1.getDataTypesForConstraints(financeCubeVisId, constraints);
		} else {
			StructureElementDAO dao = new StructureElementDAO();
			return dao.getStructureElementsForConstraints(raDimension, calDimension, financeCubeVisId, userId, hierarchyId, constraints);
		}
	}

	public Map getFinanceCubeDataForExtract(int fcId, int[] structureElementIds, String dataType) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getFinanceCubeDataForExtract(fcId, structureElementIds, dataType);
	}

	public Object getSingleCellValue(int fcId, int[] structureElementIds, String dataType) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getSingleCellValue(fcId, structureElementIds, dataType);
	}

	private FinanceSystemCheckDetail checkStructureElementsAgainstFinanceSystem(Set financeElements, EntityList elements) {
		return this.checkStructureElementsAgainstFinanceSystem(financeElements, elements, (FinanceSystemCheckDetail) null);
	}

	private FinanceSystemCheckDetail checkStructureElementsAgainstFinanceSystem(Set financeElements, EntityList elements, FinanceSystemCheckDetail checkDetail) {
		if (checkDetail == null) {
			checkDetail = new FinanceSystemCheckDetailDTO();
		}

		int rowCount = elements.getNumRows();
		Object results = new ArrayList();
		if (!financeElements.isEmpty()) {
			Integer i = (Integer) elements.getValueAt(0, "Level");
			this.mCurrentIndex = 0;
			results = this.processRowWithLevel(i.intValue(), elements, financeElements);
		} else {
			for (int var19 = 0; var19 < rowCount; ++var19) {
				Integer level = (Integer) elements.getValueAt(var19, "Level");
				Object key = elements.getValueAt(var19, "Key");
				Integer id = (Integer) elements.getValueAt(var19, "Id");
				String vis = (String) elements.getValueAt(var19, "VisId");
				String isCredit = (String) elements.getValueAt(var19, "Credit");
				String disabled = (String) elements.getValueAt(var19, "Disabled");
				String notPlannable = (String) elements.getValueAt(var19, "NotPlannable");
				String label = (String) elements.getValueAt(var19, "Label");
				String isLeaf = (String) elements.getValueAt(var19, "Leaf");
				Integer position = (Integer) elements.getValueAt(var19, "Position");
				StructureElementReference ref = new StructureElementReference(id, label, isCredit, disabled, notPlannable, position.intValue());
				ref.setKey(key);
				ref.setIsLeaf(isLeaf);
				ref.setVisId(vis);
				AvailableStructureElementImpl row = new AvailableStructureElementImpl(level, ref);
				((List) results).add(row);
			}
		}

		((FinanceSystemCheckDetailDTO) checkDetail).setValidList((List) results);
		return (FinanceSystemCheckDetail) checkDetail;
	}

	private List processRowWithLevel(int level, EntityList nominals, Set validNominals) {
		AvailableStructureElementImpl lastRow = null;
		ArrayList ourEntries = new ArrayList();

		for (int ourLevel = level; this.mCurrentIndex < nominals.getNumRows(); ++this.mCurrentIndex) {
			int lvl = ((Integer) nominals.getValueAt(this.mCurrentIndex, "Level")).intValue();
			if (lvl > ourLevel) {
				List vis = this.processRowWithLevel(lvl, nominals, validNominals);
				if (!vis.isEmpty()) {
					if (lastRow != null) {
						ourEntries.add(lastRow);
					}

					ourEntries.addAll(vis);
				}
			}

			if (lvl == ourLevel) {
				String var19 = (String) nominals.getValueAt(this.mCurrentIndex, "VisId");
				String leaf = (String) nominals.getValueAt(this.mCurrentIndex, "Leaf");
				Integer l;
				Integer id;
				String isCredit;
				String disabled;
				String notPlannable;
				String label;
				StructureElementReference ref;
				Integer position;
				if (!leaf.equalsIgnoreCase("Y")) {
					l = (Integer) nominals.getValueAt(this.mCurrentIndex, "Level");
					id = (Integer) nominals.getValueAt(this.mCurrentIndex, "Id");
					isCredit = (String) nominals.getValueAt(this.mCurrentIndex, "Credit");
					disabled = (String) nominals.getValueAt(this.mCurrentIndex, "Disabled");
					notPlannable = (String) nominals.getValueAt(this.mCurrentIndex, "NotPlannable");
					label = (String) nominals.getValueAt(this.mCurrentIndex, "Label");
					position = (Integer) nominals.getValueAt(this.mCurrentIndex, "Position");
					ref = new StructureElementReference(id, label, isCredit, disabled, notPlannable, position.intValue());
					ref.setVisId(var19);
					lastRow = new AvailableStructureElementImpl(l, ref);
				} else if (validNominals.contains(var19)) {
					l = (Integer) nominals.getValueAt(this.mCurrentIndex, "Level");
					id = (Integer) nominals.getValueAt(this.mCurrentIndex, "Id");
					isCredit = (String) nominals.getValueAt(this.mCurrentIndex, "Credit");
					disabled = (String) nominals.getValueAt(this.mCurrentIndex, "Disabled");
					notPlannable = (String) nominals.getValueAt(this.mCurrentIndex, "NotPlannable");
					label = (String) nominals.getValueAt(this.mCurrentIndex, "Label");
					position = (Integer) nominals.getValueAt(this.mCurrentIndex, "Position");
					ref = new StructureElementReference(id, label, isCredit, disabled, notPlannable, position.intValue());
					ref.setVisId(var19);
					AvailableStructureElementImpl row = new AvailableStructureElementImpl(l, ref);
					ourEntries.add(row);
				}
			}

			if (lvl < ourLevel) {
				--this.mCurrentIndex;
				return ourEntries;
			}
		}

		return ourEntries;
	}

	public CellContents getFinanceCubeCell(String modelVisId, String financeCubeVisId, boolean ytd, String[] structureVisIds, String[] elementVisIds, String dataType) {
		CellContentsImpl cell = null;
		//DataEntryDAO dao = new DataEntryDAO();
		BigDecimal result = dao.getFinanceCubeCell(modelVisId, financeCubeVisId, ytd, structureVisIds, elementVisIds, dataType);
		if (result != null) {
			cell = new CellContentsImpl((String) null, result);
		}

		return cell;
	}

	public EntityList getCellValues(int fcId, int numDims, String[] structureVisIds, List<String[]> cellKeys, String company) {
		return dao.getCellValues(fcId, numDims, structureVisIds, cellKeys, company);
	}

	public EntityList getCellValues(int fcId, int numDims, int[] hierIds, List<String[]> cellKeys, String company) {
		return dao.getCellValues(fcId, numDims, hierIds, cellKeys, company);
	}

	public EntityList getCellValues(int fcId, int numDims, String[] structureVisIds, List<String[]> cellKeys) {
		return dao.getCellValues(fcId, numDims, structureVisIds, cellKeys);
	}

	public EntityList getCellValues(int fcId, int numDims, int[] hierIds, List<String[]> cellKeys) {
		return dao.getCellValues(fcId, numDims, hierIds, cellKeys);
	}

	public EntityList getOACellValues(int company, List<String[]> cellKeys) {
		return dao.getOACellValues(company, cellKeys);
	}

    public EntityList getCurrencyCellValues(List<String[]> cellKeys) {
        return dao.getCurrencyCellValues(cellKeys);
    }
    
    public EntityList getParameterCellValues(List<String[]> cellKeys) {
        return dao.getParameterCellValues(cellKeys);
    }

    public EntityList getAuctionCellValues(List<String[]> cellKeys) {
        return dao.getAuctionCellValues(cellKeys);
    }

	public ExtractDataDTO getExtractData(ExtractDataDTO extractDataDTO) {
		return ExtractDataHelper.getExtractData(extractDataDTO);
	}

	public CellContents getFinanceCubeCell(String modelVisId, int financeCubeId, boolean ytd, int[] structureVisIds, String[] elementVisIds, String dataType) {
		CellContentsImpl cell = null;
		//DataEntryDAO dao = new DataEntryDAO();
		BigDecimal result = dao.getFinanceCubeCell(modelVisId, financeCubeId, ytd, structureVisIds, elementVisIds, dataType);
		if (result != null) {
			cell = new CellContentsImpl((String) null, result);
		}

		return cell;
	}

	public EntityList getCellNote(int financeCubeId, String cellPk, int userId) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getCellNote(financeCubeId, cellPk, userId);
	}

	public EntityList getAllNotes(int financeCubeId, int userId) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getAllNotes(financeCubeId, userId);
	}
	
	public EntityList getLastNotes(int financeCubeId) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getLastNotes(financeCubeId);
	}

	public EntityList getCellNote(int financeCubeId, String cellPk, int userId, String company) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getCellNote(financeCubeId, cellPk, userId, company);
	}

	public EntityList getCellAudit(int financeCubeId, String cellPk, int userId) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getCellAudit(financeCubeId, cellPk, userId);
	}

	public EntityList queryExtSysInfoForCube(int financeCubeId) throws ValidationException, EJBException {
		return (new ExternalSystemDAO()).queryExtSysInfoForCube(financeCubeId);
	}

	public ExtSysTransactionQueryParams queryExtSysParams(int financeCubeId, String cellPk, String ytd) {
		return (new ExternalSystemDAO()).queryExtSysParams(financeCubeId, cellPk, ytd);
	}

	public FinanceSystemCellData getFinanceSystemCellData(int financeCubeId, String cellPk, String ytd, int userId, int cmpy) throws EJBException, ValidationException {
		ExternalSystemDAO dao = new ExternalSystemDAO();
		return dao.getFinanceTransactions(financeCubeId, cellPk, ytd, userId, cmpy);
	}
	
	public String[] getPropertiesForInvoice() {
		SystemPropertyDAO dao = new SystemPropertyDAO();
		String[] properties = new String[3];
		properties[0] = dao.getSystemProperty("WEB: Invoice Server URL").getDataAsArray()[0][1].toString();
		properties[1] = dao.getSystemProperty("WEB: Invoice User").getDataAsArray()[0][1].toString();
		properties[2] = dao.getSystemProperty("WEB: Invoice Password").getDataAsArray()[0][1].toString();
		return properties;
	}

	public EntityList getSheetNote(int formId, int elemId) throws EJBException {
		return (new FormNotesDAO()).getAllFormNotesForFormAndBudgetLocation(formId, elemId);
	}

	public boolean getXMLFormSecurityAccessFlag(int formId) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getXMLFormReadOnlySecurityFlag(formId);
	}

	public int getXMLFormIdFromCellCalcId(int financeCubeId, int cellCalcId) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getDefinitionIdFromCellCalcId(cellCalcId);
	}

	public CellCalculationDetails getCellCalculationDetails(int financeCubeId, int formDefinitionId, Map contextVariables, int cellCalcShortId) throws EJBException {
		CellCalculationDetails result = new CellCalculationDetails();
		//DataEntryDAO dao = new DataEntryDAO();
		result.setFormConfig(this.getXMLFormConfig(contextVariables, formDefinitionId));
		result.setPreviousData(dao.getCalculationPreviousData(result.getFormConfig(), financeCubeId, cellCalcShortId));
		result.setSummaryData(dao.getCalculationSummaryData(result.getFormConfig(), financeCubeId, cellCalcShortId));
		return result;
	}

	public List<Integer> getCellCalcShortIdsForRA(int financeCubeId, int cellCalcDeploymentId, int budgetLocation) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getCellCalcShortIdsForRA(financeCubeId, cellCalcDeploymentId, budgetLocation);
	}

	public List<Integer> getCellCalcShortIdsForRA(int financeCubeId, int cellCalcDeploymentId, int budgetLocation, CalendarElementNode rootCalNode) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		ArrayList calIds = new ArrayList();
		Enumeration calnodes = rootCalNode.depthFirstEnumeration();

		while (calnodes.hasMoreElements()) {
			CalendarElementNode node = (CalendarElementNode) calnodes.nextElement();
			calIds.add(Integer.valueOf(node.getStructureElementId()));
		}

		return dao.getCellCalcShortIdsForRA(financeCubeId, cellCalcDeploymentId, budgetLocation, calIds);
	}

	public List<Object> getCellCalculationContext(int financeCubeId, int cellCalcShortId) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getCellCalculationContext(financeCubeId, cellCalcShortId);
	}

	public FormConfig getXMLFormConfig(Map contextVariables, int formId) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		String[] config = dao.getXMLFormConfig(formId);
		String visId = config[0];
		String definition = config[1];
		this.mLog.debug("getXMLFormConfig", "loaded xml_form_id=" + formId + " visId=" + visId);
		FormConfig result = null;

		try {
			XMLReader e = new XMLReader();
			e.init();
			StringReader sr = new StringReader(definition);
			e.parseConfigFile(sr);
			DataEntryContextDAO formContext = new DataEntryContextDAO(contextVariables);
			result = e.getFormConfig();
			result.buildVariables((PerformanceDatumImpl) null, formContext);
			return result;
		} catch (Exception var11) {
			this.mLog.error("getXMLFormConfig", "unable to get xml_form_id=" + formId + " visId=" + visId, var11);
			throw new EJBException(var11);
		}
	}

	public FormDataInputModel getCalculationPreviousData(FormConfig formConfig, int financeCubeId, int cellCalcShortId) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getCalculationPreviousData(formConfig, financeCubeId, cellCalcShortId);
	}

	public Map getCalculationSummaryData(FormConfig formConfig, int financeCubeId, int cellCalcShortId) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.getCalculationSummaryData(formConfig, financeCubeId, cellCalcShortId);
	}

	public int issueMassUpdate(int userId, MassUpdateParameters mup) throws EJBException {
		MassUpdateTaskRequest request = new MassUpdateTaskRequest(new ModelPK(mup.getModelId()));
		request.setModelId(mup.getModelId());
		request.setModelVisId(mup.getModelVisId());
		request.setFinanceCubeId(mup.getFinanceCubeId());
		request.setFinanceCubeVisId(mup.getFinanceCubeVisId());
		request.setReason(mup.getReason());
		request.setReference(mup.getReference());
		request.setChangeCells(mup.getChangeCells());
		request.setCurrentValue(mup.getCurrentValue());
		request.setChangeType(mup.getChangeType());
		request.setChangePercent(mup.getChangePercent());
		request.setChangeBy(mup.getChangeBy());
		request.setChangeTo(mup.getChangeTo());
		request.setCalId(mup.getCalId());
		request.setDataTypeId(mup.getDataTypeId());
		request.setDataTypeVisId(mup.getDataTypeVisId());
		request.setRoundUnits(mup.getRoundUnits());
		request.setHoldNegative(mup.isHoldNegative());
		request.setHoldPositive(mup.isHoldPositive());
		request.setHoldCells(mup.getHoldCells());
		request.setCellPosting(mup.isCellPosting());
		request.setReport(mup.isReport());

		try {
			int e = TaskMessageFactory.issueNewTask(new InitialContext(), false, request, userId);
			this.mLog.debug("issueMassUpdate", "taskId=" + e);
			return e;
		} catch (Exception var5) {
			var5.printStackTrace();
			throw new EJBException(var5);
		}
	}

	public String validateMassUpdate(int financeCubeId, int srcCalElem, int targetCalElem) throws EJBException {
		//DataEntryDAO dao = new DataEntryDAO();
		return dao.validateMassUpdate(financeCubeId, srcCalElem, targetCalElem);
	}

	public int issueRecharge(int userId, int financeCubeId, int calendarStructureElementId, boolean report, boolean source, boolean target, int[] rechargeIds) throws EJBException {
		try {
			ModelAccessor e = new ModelAccessor(new InitialContext());
			ModelEVO modelEvo = e.getDetails(new FinanceCubePK(financeCubeId), "");
			RechargeTaskRequest rtr = new RechargeTaskRequest(new ModelPK(modelEvo.getModelId()));
			rtr.setModelId(modelEvo.getModelId());
			rtr.setModelVisId(modelEvo.getVisId());
			rtr.setFinanceCubeId(financeCubeId);
			rtr.setFinanceCubeVisId(modelEvo.getFinanceCubesItem(new FinanceCubePK(financeCubeId)).getVisId());
			rtr.setCalendarStructureElementId(calendarStructureElementId);
			rtr.setReportOnly(report);
			rtr.setReportSourceCells(source);
			rtr.setReportTargetCells(target);
			rtr.setRechargeIds(rechargeIds);
			int taskId = TaskMessageFactory.issueNewTask(new InitialContext(), false, rtr, userId);
			this.mLog.debug("issueCharge", "taskId=" + taskId);
			return taskId;
		} catch (Exception var12) {
			var12.printStackTrace();
			throw new EJBException(var12);
		}
	}

	public CalendarInfo getCalendarInfoForModel(String modelVisId) throws EJBException {
		StructureElementDAO structureDao = new StructureElementDAO();
		CalendarInfoImpl calInfo = structureDao.getCalendarInfoForModelVisId(modelVisId);
		return calInfo;
	}

	public int getCalYearMonthId(String visId, String calVisIdPrefix, int dimId) throws EJBException {
		StructureElementDAO structureDao = new StructureElementDAO();
		return structureDao.getCalYearMonthId(visId, calVisIdPrefix, dimId);
	}

	public List<Map<String, String>> getStructureElements(List<Integer> dimsList) throws EJBException {
		StructureElementDAO structureDao = new StructureElementDAO();
		return structureDao.getStructureElements(dimsList);
	}

	public Map<String, String> getCalendarRangeDetails(int id) throws EJBException {
		StructureElementDAO structureDao = new StructureElementDAO();
		return structureDao.getCalendarRangeDetails(id);
	}

	public void ejbCreate() {
	}

	public void ejbActivate() throws EJBException, RemoteException {
	}

	public void ejbPassivate() throws EJBException, RemoteException {
	}

	public void ejbRemove() throws EJBException, RemoteException {
	}

	public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
		this.mContext = sessionContext;
	}

	public int getFinanceCubeIdFromVisId(String modelVisId, String cubeVisId) throws ValidationException, EJBException {
		return new DataEntryDAO().getFinanceCubeIdFromVisId(modelVisId, cubeVisId);
	}
	
	public boolean getUserReadOnlyAccess(int modelId, int structureElementId, int userId) throws EJBException, ValidationException {
		return new BudgetUserDAO().getUserReadOnlyAccess(modelId, structureElementId, userId);
	}
	
	public void saveCellNoteChange(Object cellNote, int financeCubeId, int userId, int budgetCycleId, String action) throws EJBException, ValidationException {
		if (action.equals("UPDATE")) {
			new DataEntryDAO().updateCellNote((CellNote) cellNote, financeCubeId);
		} else if (action.equals("INSERT")) {
			new DataEntryDAO().insertCellNote((CellNote) cellNote, financeCubeId, userId, budgetCycleId);
		} else if (action.equals("DELETE")) {
			new DataEntryDAO().deleteCellNote((CellNote) cellNote, financeCubeId);
		} else {
			throw new ValidationException("Unrecognized action.");
		}
	}
}
