// Decompiled by: Fernflower v0.8.6
// Date: 12.08.2012 13:26:27
// Copyright: 2008-2012, Stiver
// Home page: http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingDeploymentEditor;
import com.cedar.cp.api.model.udwp.WeightingDeploymentEditorSession;
import com.cedar.cp.api.model.udwp.WeightingDeploymentsProcess;
import com.cedar.cp.api.model.udwp.WeightingProfile;
import com.cedar.cp.api.model.udwp.WeightingProfileEditor;
import com.cedar.cp.api.model.udwp.WeightingProfileEditorSession;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.api.model.udwp.WeightingProfilesProcess;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.currency.CurrencyCK;
import com.cedar.cp.dto.currency.CurrencyPK;
import com.cedar.cp.dto.currency.CurrencyRefImpl;
import com.cedar.cp.dto.dimension.AllDimensionsELO;
import com.cedar.cp.dto.dimension.DimensionCK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.CubeAdminTaskRequest;
import com.cedar.cp.dto.model.FinanceCubesForModelELO;
import com.cedar.cp.dto.model.ImportDataModelTaskRequest;
import com.cedar.cp.dto.model.ModelDimensionRelPK;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.cedar.cp.dto.model.ModelEditorSessionCSO;
import com.cedar.cp.dto.model.ModelEditorSessionSSO;
import com.cedar.cp.dto.model.ModelImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.udwp.WeightingProfileCK;
import com.cedar.cp.dto.report.definition.AllReportDefCalcByModelIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefFormcByModelIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefMappedExcelcByModelIdELO;
import com.cedar.cp.dto.report.definition.AllReportDefSummaryCalcByModelIdELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.xmlform.AllXmlFormsELO;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionAssignmentDAO;
import com.cedar.cp.ejb.impl.currency.CurrencyAccessor;
import com.cedar.cp.ejb.impl.currency.CurrencyEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.model.CellCalcDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDimensionRelEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.ModelPropertyEVO;
import com.cedar.cp.ejb.impl.model.amm.AmmModelDAO;
import com.cedar.cp.ejb.impl.report.definition.ReportDefinitionAccessor;
import com.cedar.cp.ejb.impl.task.group.TaskRIChecker;
import com.cedar.cp.ejb.impl.user.DataEntryProfileDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileEVO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class ModelEditorSessionSEJB extends AbstractSession {

	private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<10><0><1><9>";
	private static final String DEPENDANTS_FOR_INSERT = "";
	private static final String DEPENDANTS_FOR_COPY = "<0><1><2><3><4><5><6><7><8><9><10><11><12><13><14><15><16><17><18><19><20><21><22><23><24><25><26><27><28><29><30><31><32><33><34><35><36><37><38><39><40><41><42><43><44><45><46><47><48>";
	private static final String DEPENDANTS_FOR_UPDATE = "<10><9>";
	private static final String DEPENDANTS_FOR_DELETE = "";
	private transient Log mLog = new Log(this.getClass());
	private transient SessionContext mSessionContext;
	private transient ModelAccessor mModelAccessor;
	private transient CurrencyAccessor mCurrencyAccessor;
	private transient DimensionAccessor mDimensionAccessor;
	private ModelEditorSessionSSO mSSO;
	private ModelPK mThisTableKey;
	private ModelEVO mModelEVO;

	public ModelEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
		this.setUserId(userId);
		if (this.mLog.isDebugEnabled()) {
			this.mLog.debug("getItemData", paramKey);
		}

		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.mThisTableKey = (ModelPK) paramKey;

		ModelEditorSessionSSO e;
		try {
			this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<10><0><1><9>");
			this.makeItemData();
			e = this.mSSO;
		} catch (ValidationException var10) {
			throw var10;
		} catch (EJBException var11) {
			if (var11.getCause() instanceof ValidationException) {
				throw (ValidationException) var11.getCause();
			}

			throw var11;
		} catch (Exception var12) {
			var12.printStackTrace();
			throw new EJBException(var12.getMessage(), var12);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("getItemData", this.mThisTableKey);
			}

		}

		return e;
	}

	private void makeItemData() throws Exception {
		this.mSSO = new ModelEditorSessionSSO();
		ModelImpl editorData = this.buildModelEditData(this.mThisTableKey);
		this.completeGetItemData(editorData);
		this.mSSO.setEditorData(editorData);
	}

	private void completeGetItemData(ModelImpl editorData) throws Exception {
	}

	private ModelImpl buildModelEditData(Object thisKey) throws Exception {
		ModelImpl editorData = new ModelImpl(thisKey);
		editorData.setVisId(this.mModelEVO.getVisId());
		editorData.setDescription(this.mModelEVO.getDescription());
		editorData.setCurrencyInUse(this.mModelEVO.getCurrencyInUse());
		editorData.setLocked(this.mModelEVO.getLocked());
		editorData.setVirementEntryEnabled(this.mModelEVO.getVirementEntryEnabled());
		editorData.setVersionNum(this.mModelEVO.getVersionNum());
		CurrencyPK key = null;
		if (this.mModelEVO.getCurrencyId() != 0) {
			key = new CurrencyPK(this.mModelEVO.getCurrencyId());
		}

		if (key != null) {
			CurrencyEVO ck = this.getCurrencyAccessor().getDetails(key, "");
			editorData.setCurrencyRef(new CurrencyRefImpl(ck.getPK(), ck.getVisId()));
		}

		DimensionPK key1 = null;
		if (this.mModelEVO.getAccountId() != 0) {
			key1 = new DimensionPK(this.mModelEVO.getAccountId());
		}

		DimensionEVO ck1;
		if (key1 != null) {
			ck1 = this.getDimensionAccessor().getDetails(key1, "");
			editorData.setAccountRef(new DimensionRefImpl(ck1.getPK(), ck1.getVisId(), ck1.getType()));
		}

		key1 = null;
		if (this.mModelEVO.getCalendarId() != 0) {
			key1 = new DimensionPK(this.mModelEVO.getCalendarId());
		}

		if (key1 != null) {
			ck1 = this.getDimensionAccessor().getDetails(key1, "");
			editorData.setCalendarRef(new DimensionRefImpl(ck1.getPK(), ck1.getVisId(), ck1.getType()));
		}

		HierarchyPK key2 = null;
		if (this.mModelEVO.getBudgetHierarchyId() != 0) {
			key2 = new HierarchyPK(this.mModelEVO.getBudgetHierarchyId());
		}

		if (key2 != null) {
			HierarchyCK ck2 = (HierarchyCK) this.getDimensionAccessor().getCKForDependantPK(key2);
			DimensionEVO evoDimension = this.getDimensionAccessor().getDetails(ck2, "");
			HierarchyEVO evoHierarchy = evoDimension.getHierarchiesItem(ck2.getHierarchyPK());
			editorData.setBudgetHierarchyRef(new HierarchyRefImpl(evoHierarchy.getPK(), evoHierarchy.getVisId()));
		}

		this.completeModelEditData(editorData);
		return editorData;
	}

	private void completeModelEditData(ModelImpl editorData) throws Exception {
		AllDimensionsELO allDimensionsELO = this.getDimensionAccessor().getAllDimensions();
		Iterator iter = this.mModelEVO.getModelDimensionRels().iterator();

		while (iter.hasNext()) {
			ModelDimensionRelEVO amendAcc = (ModelDimensionRelEVO) iter.next();
			int amendCal = amendAcc.getDimensionId();
			DimensionPK entityRefList = new DimensionPK(amendCal);
			allDimensionsELO.reset();

			while (allDimensionsELO.hasNext()) {
				allDimensionsELO.next();
				if (allDimensionsELO.getType() == 2) {
					DimensionPK iterator = (DimensionPK) allDimensionsELO.getDimensionEntityRef().getPrimaryKey();
					if (iterator.equals(entityRefList)) {
						editorData.addSelectedDimensionRef(allDimensionsELO.getDimensionEntityRef());
						break;
					}
				}
			}
		}

		boolean amendAcc1 = this.isDimensionAmendable((DimensionPK) editorData.getAccountRef().getPrimaryKey());
		boolean amendCal1 = this.isDimensionAmendable((DimensionPK) editorData.getCalendarRef().getPrimaryKey());
		this.mSSO.setAccountAmendable(amendAcc1);
		this.mSSO.setCalendarAmendable(amendCal1);
		ArrayList entityRefList1 = new ArrayList();
		Iterator iterator1 = editorData.getSelectedDimensionRefs().iterator();

		while (iterator1.hasNext()) {
			EntityRef entityRef = (EntityRef) iterator1.next();
			if (!this.isDimensionAmendable((DimensionPK) entityRef.getPrimaryKey())) {
				entityRefList1.add(entityRef);
			}
		}

		if (!entityRefList1.isEmpty()) {
			this.mSSO.setImmutableBusinessDimensions(entityRefList1);
		}

		this.loadModelProperties(this.mModelEVO, editorData);
	}

	public ModelEditorSessionSSO getNewItemData(int userId) throws EJBException {
		this.mLog.debug("getNewItemData");
		this.setUserId(userId);
		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;

		ModelEditorSessionSSO var4;
		try {
			this.mSSO = new ModelEditorSessionSSO();
			ModelImpl e = new ModelImpl((Object) null);
			this.completeGetNewItemData(e);
			this.mSSO.setEditorData(e);
			var4 = this.mSSO;
		} catch (EJBException var9) {
			throw var9;
		} catch (Exception var10) {
			var10.printStackTrace();
			throw new EJBException(var10.getMessage(), var10);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("getNewItemData", "");
			}

		}

		return var4;
	}

	private void completeGetNewItemData(ModelImpl editorData) throws Exception {
		editorData.setProperties(new HashMap());
	}

	public ModelPK insert(ModelEditorSessionCSO cso) throws ValidationException, EJBException {
		this.mLog.debug("insert");
		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.setUserId(cso.getUserId());
		ModelImpl editorData = cso.getEditorData();

		ModelPK e;
		try {
			this.mModelEVO = new ModelEVO();
			this.mModelEVO.setVisId(editorData.getVisId());
			this.mModelEVO.setDescription(editorData.getDescription());
			this.mModelEVO.setCurrencyInUse(editorData.isCurrencyInUse());
			this.mModelEVO.setLocked(editorData.isLocked());
			this.mModelEVO.setVirementEntryEnabled(editorData.isVirementEntryEnabled());
			this.updateModelRelationships(editorData);
			this.completeInsertSetup(editorData);
			this.validateInsert();
			this.mModelEVO = this.getModelAccessor().create(this.mModelEVO);
			this.insertIntoAdditionalTables(editorData, true);
			this.sendEntityEventMessage("Model", this.mModelEVO.getPK(), 1);
			e = this.mModelEVO.getPK();
		} catch (ValidationException var10) {
			throw new EJBException(var10);
		} catch (EJBException var11) {
			throw var11;
		} catch (Exception var12) {
			var12.printStackTrace();
			throw new EJBException(var12.getMessage(), var12);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("insert", "");
			}

		}

		return e;
	}

	private void updateModelRelationships(ModelImpl editorData) throws ValidationException {
		Object key;
		if (editorData.getCurrencyRef() != null) {
			key = editorData.getCurrencyRef().getPrimaryKey();
			if (key instanceof CurrencyPK) {
				this.mModelEVO.setCurrencyId(((CurrencyPK) key).getCurrencyId());
			} else {
				this.mModelEVO.setCurrencyId(((CurrencyCK) key).getCurrencyPK().getCurrencyId());
			}

			try {
				this.getCurrencyAccessor().getDetails(key, "");
			} catch (Exception var7) {
				var7.printStackTrace();
				throw new ValidationException(editorData.getCurrencyRef() + " no longer exists");
			}
		} else {
			this.mModelEVO.setCurrencyId(0);
		}

		if (editorData.getAccountRef() != null) {
			key = editorData.getAccountRef().getPrimaryKey();
			if (key instanceof DimensionPK) {
				this.mModelEVO.setAccountId(((DimensionPK) key).getDimensionId());
			} else {
				this.mModelEVO.setAccountId(((DimensionCK) key).getDimensionPK().getDimensionId());
			}

			try {
				this.getDimensionAccessor().getDetails(key, "");
			} catch (Exception var6) {
				var6.printStackTrace();
				throw new ValidationException(editorData.getAccountRef() + " no longer exists");
			}
		} else {
			this.mModelEVO.setAccountId(0);
		}

		if (editorData.getCalendarRef() != null) {
			key = editorData.getCalendarRef().getPrimaryKey();
			if (key instanceof DimensionPK) {
				this.mModelEVO.setCalendarId(((DimensionPK) key).getDimensionId());
			} else {
				this.mModelEVO.setCalendarId(((DimensionCK) key).getDimensionPK().getDimensionId());
			}

			try {
				this.getDimensionAccessor().getDetails(key, "");
			} catch (Exception var5) {
				var5.printStackTrace();
				throw new ValidationException(editorData.getCalendarRef() + " no longer exists");
			}
		} else {
			this.mModelEVO.setCalendarId(0);
		}

		if (editorData.getBudgetHierarchyRef() != null) {
			key = editorData.getBudgetHierarchyRef().getPrimaryKey();
			if (key instanceof HierarchyPK) {
				this.mModelEVO.setBudgetHierarchyId(((HierarchyPK) key).getHierarchyId());
			} else {
				this.mModelEVO.setBudgetHierarchyId(((HierarchyCK) key).getHierarchyPK().getHierarchyId());
			}

			try {
				this.getDimensionAccessor().getDetails(key, "");
			} catch (Exception var4) {
				var4.printStackTrace();
				throw new ValidationException(editorData.getBudgetHierarchyRef() + " no longer exists");
			}
		} else {
			this.mModelEVO.setBudgetHierarchyId(0);
		}

	}

	private void completeInsertSetup(ModelImpl editorData) throws Exception {
		int calendarId = ((DimensionPK) editorData.getCalendarRef().getPrimaryKey()).getDimensionId();
		this.mModelEVO.setCalendarId(calendarId);
		int accountId = ((DimensionPK) editorData.getAccountRef().getPrimaryKey()).getDimensionId();
		this.mModelEVO.setAccountId(accountId);
		int seqNum = 0;
		Iterator iter = editorData.getSelectedDimensionRefs().iterator();

		ModelDimensionRelEVO mdre;
		while (iter.hasNext()) {
			mdre = new ModelDimensionRelEVO(0, ((DimensionPK) ((EntityRef) iter.next()).getPrimaryKey()).getDimensionId(), 2, seqNum++);
			this.mModelEVO.addModelDimensionRelsItem(mdre);
		}

		mdre = new ModelDimensionRelEVO(0, ((DimensionPK) editorData.getAccountRef().getPrimaryKey()).getDimensionId(), 1, seqNum++);
		this.mModelEVO.addModelDimensionRelsItem(mdre);
		mdre = new ModelDimensionRelEVO(0, ((DimensionPK) editorData.getCalendarRef().getPrimaryKey()).getDimensionId(), 3, seqNum++);
		this.mModelEVO.addModelDimensionRelsItem(mdre);
		this.updateModelPropertyEVOs(editorData, this.mModelEVO);
	}

	private void insertIntoAdditionalTables(ModelImpl editorData, boolean isInsert) throws Exception {
		if (isInsert) {
			this.createDefaultWeightingProfiles(editorData);
		}
		saveSecurityAssignment(editorData);
	}

	private void saveSecurityAssignment(ModelImpl editorData) {
		BudgetUserDAO budgetUserDao = new BudgetUserDAO();
		
		Object[] newAssign = new Object[3];
		newAssign[0] = this.mModelEVO.getModelId();
		newAssign[1] = this.getUserId();
		newAssign[2] = ((HierarchyCK)editorData.getBudgetHierarchyRef().getPrimaryKey()).getHierarchyPK().getHierarchyId();
		
		budgetUserDao.insertAfterNewModel(newAssign);
	}

	private void validateInsert() throws ValidationException {
	}

	public ModelPK copy(ModelEditorSessionCSO cso) throws ValidationException, EJBException {
		this.mLog.debug("copy");
		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.setUserId(cso.getUserId());
		ModelImpl editorData = cso.getEditorData();
		this.mThisTableKey = (ModelPK) editorData.getPrimaryKey();

		ModelPK var5;
		try {
			ModelEVO e = this.getModelAccessor().getDetails(this.mThisTableKey, "<0><1><2><3><4><5><6><7><8><9><10><11><12><13><14><15><16><17><18><19><20><21><22><23><24><25><26><27><28><29><30><31><32><33><34><35><36><37><38><39><40><41><42><43><44><45><46><47><48>");
			this.mModelEVO = e.deepClone();
			this.mModelEVO.setVisId(editorData.getVisId());
			this.mModelEVO.setDescription(editorData.getDescription());
			this.mModelEVO.setCurrencyInUse(editorData.isCurrencyInUse());
			this.mModelEVO.setLocked(editorData.isLocked());
			this.mModelEVO.setVirementEntryEnabled(editorData.isVirementEntryEnabled());
			this.mModelEVO.setVersionNum(0);
			this.updateModelRelationships(editorData);
			this.completeCopySetup(editorData);
			this.validateCopy();
			this.mModelEVO.prepareForInsert();
			this.mModelEVO = this.getModelAccessor().create(this.mModelEVO);
			this.mThisTableKey = this.mModelEVO.getPK();
			this.insertIntoAdditionalTables(editorData, false);
			this.sendEntityEventMessage("Model", this.mModelEVO.getPK(), 1);
			var5 = this.mThisTableKey;
		} catch (ValidationException var11) {
			throw new EJBException(var11);
		} catch (EJBException var12) {
			throw var12;
		} catch (Exception var13) {
			var13.printStackTrace();
			throw new EJBException(var13);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("copy", this.mThisTableKey);
			}

		}

		return var5;
	}

	private void validateCopy() throws ValidationException {
	}

	private void completeCopySetup(ModelImpl editorData) throws Exception {
		this.mModelEVO.setModelDimensionRels(new ArrayList());
		this.completeInsertSetup(editorData);
	}

	public void update(ModelEditorSessionCSO cso) throws ValidationException, EJBException {
		this.mLog.debug("update");
		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.setUserId(cso.getUserId());
		ModelImpl editorData = cso.getEditorData();
		this.mThisTableKey = (ModelPK) editorData.getPrimaryKey();

		try {
			this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "<10><9>");
			this.preValidateUpdate(editorData);
			this.mModelEVO.setVisId(editorData.getVisId());
			this.mModelEVO.setDescription(editorData.getDescription());
			this.mModelEVO.setCurrencyInUse(editorData.isCurrencyInUse());
			this.mModelEVO.setLocked(editorData.isLocked());
			this.mModelEVO.setVirementEntryEnabled(editorData.isVirementEntryEnabled());
			if (editorData.getVersionNum() != this.mModelEVO.getVersionNum()) {
				throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mModelEVO.getVersionNum());
			}

			this.updateModelRelationships(editorData);
			this.completeUpdateSetup(editorData);
			this.postValidateUpdate();
			this.getModelAccessor().setDetails(this.mModelEVO);
			this.updateAdditionalTables(editorData);
			this.sendEntityEventMessage("Model", this.mModelEVO.getPK(), 3);
		} catch (ValidationException var10) {
			throw new EJBException(var10);
		} catch (EJBException var11) {
			throw var11;
		} catch (Exception var12) {
			var12.printStackTrace();
			throw new EJBException(var12.getMessage(), var12);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("update", this.mThisTableKey);
			}

		}

	}

	private void preValidateUpdate(ModelImpl editorData) throws ValidationException {
	}

	private void postValidateUpdate() throws ValidationException {
	}

	private void completeUpdateSetup(ModelImpl editorData) throws Exception {
		int calendarId = ((DimensionPK) editorData.getCalendarRef().getPrimaryKey()).getDimensionId();
		this.mModelEVO.setCalendarId(calendarId);
		int accountId = ((DimensionPK) editorData.getAccountRef().getPrimaryKey()).getDimensionId();
		this.mModelEVO.setAccountId(accountId);
		ArrayList selectedDims = new ArrayList();
		selectedDims.addAll(editorData.getSelectedDimensionRefs());
		selectedDims.add(editorData.getAccountRef());
		selectedDims.add(editorData.getCalendarRef());
		int seqNum = 0;
		ArrayList newOnes = new ArrayList();

		Iterator iter;
		for (iter = selectedDims.iterator(); iter.hasNext(); ++seqNum) {
			int mdre = ((DimensionPK) ((EntityRef) iter.next()).getPrimaryKey()).getDimensionId();
			ModelDimensionRelEVO found = new ModelDimensionRelEVO(this.mModelEVO.getModelId(), mdre, 2, seqNum);
			newOnes.add(found);
			ModelDimensionRelEVO j = this.mModelEVO.getModelDimensionRelsItem(new ModelDimensionRelPK(this.mModelEVO.getModelId(), mdre));
			if (j == null) {
				if (mdre == accountId) {
					j = new ModelDimensionRelEVO(this.mModelEVO.getModelId(), mdre, 1, seqNum);
				} else if (mdre == calendarId) {
					j = new ModelDimensionRelEVO(this.mModelEVO.getModelId(), mdre, 3, seqNum);
				} else {
					j = new ModelDimensionRelEVO(this.mModelEVO.getModelId(), mdre, 2, seqNum);
				}

				this.mModelEVO.addModelDimensionRelsItem(j);
			} else {
				j.setDimensionSeqNum(seqNum);
				this.mModelEVO.changeModelDimensionRelsItem(j);
			}
		}

		iter = this.mModelEVO.getModelDimensionRels().iterator();

		while (iter.hasNext()) {
			ModelDimensionRelEVO var12 = (ModelDimensionRelEVO) iter.next();
			boolean var13 = false;
			int var14 = 0;

			while (true) {
				if (var14 < newOnes.size()) {
					ModelDimensionRelEVO newMdre = (ModelDimensionRelEVO) newOnes.get(var14);
					if (var12.getModelId() != newMdre.getModelId() || var12.getDimensionId() != newMdre.getDimensionId()) {
						++var14;
						continue;
					}

					var13 = true;
				}

				if (!var13) {
					this.mModelEVO.deleteModelDimensionRelsItem(new ModelDimensionRelPK(this.mModelEVO.getModelId(), var12.getDimensionId()));
				}
				break;
			}
		}

		this.updateModelPropertyEVOs(editorData, this.mModelEVO);
	}

	private void updateAdditionalTables(ModelImpl editorData) throws Exception {
	}

	public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
		if (this.mLog.isDebugEnabled()) {
			this.mLog.debug("delete", paramKey);
		}

		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.setUserId(userId);
		this.mThisTableKey = (ModelPK) paramKey;

		try {
			this.mModelEVO = this.getModelAccessor().getDetails(this.mThisTableKey, "");
			this.validateDelete();
			this.deleteDataFromOtherTables();
			this.mModelAccessor.remove(this.mThisTableKey);
			this.sendEntityEventMessage("Model", this.mThisTableKey, 2);
		} catch (ValidationException var10) {
			throw var10;
		} catch (EJBException var11) {
			throw var11;
		} catch (Exception var12) {
			var12.printStackTrace();
			throw new EJBException(var12.getMessage(), var12);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("delete", this.mThisTableKey);
			}

		}
	}
	
	public Object[][] checkForms(int modelId) {
		AllXmlFormsELO forms = new XmlFormDAO().getAllXmlFormsForModel(modelId);
		if (forms.getNumRows() > 0) {
			return forms.getDataAsArray();
		} else return null;
	}

	private void deleteDataFromOtherTables() throws Exception {
		
		FinanceCubesForModelELO fcs = new FinanceCubeDAO().getFinanceCubesForModel(mThisTableKey.getModelId());
		for ( Object[] fc : fcs.getDataAsArray()) {
			issueCubeAdminTask((Integer)fc[2], fc[0].toString(), fc[3].toString());
		}

		EntityList userEl = new DataEntryProfileDAO().getAllUsersForDataEntryProfilesForModel(mThisTableKey.getModelId());
		UserAccessor userAccessor = new UserAccessor(getInitialContext());
		for (int i = 0; i < userEl.getNumRows(); i++)
		{
			UserPK userPk = (UserPK) ((EntityRef) userEl.getValueAt(i, "User")).getPrimaryKey();
			UserEVO userevo = userAccessor.getDetails(userPk, "<2><3>");
			for (DataEntryProfileEVO depevo : userevo.getDataEntryProfiles())
			{
				if (depevo.getModelId() == mThisTableKey.getModelId())
				{
					depevo.setDeletePending();
					break;
				}
			}
			userAccessor.setAndGetDetails(userevo, "");
		}
		// remove dimensions and hierarchies with dependencies
		DimensionDAO dao = new DimensionDAO();
		EntityList mappedDims = dao.getAllDimensionsForModel(mModelEVO.getPK().getModelId());

		for (int i = 0; i < mappedDims.getNumRows(); i++) {
			int dimId = (Integer) mappedDims.getValueAt(i, "DimensionId");
			dao.doRemove(dimId);
		}
		
		// XML forms
		AllXmlFormsELO forms = new XmlFormDAO().getAllXmlFormsForModel(mModelEVO.getPK().getModelId());
		if (forms.getNumRows() > 0) {
			XmlFormDAO xDao = new XmlFormDAO();
			xDao.deleteXMLForms(forms.getDataAsArray());
			DataEntryProfileDAO depDao = new DataEntryProfileDAO();
			depDao.deleteDataEntryProfiles(forms.getDataAsArray(), mModelEVO.getPK().getModelId());
		}
	}

	private void validateDelete() throws ValidationException, Exception {
		EntityList list = null;
		int modelId = mModelEVO.getPK().getModelId();
		// EntityList mappedModels = new MappedModelDAO().getAllMappedModels();
		// checkIfInUse(mappedModels, "Model", "Model Mapping");
		EntityList ammMappedModels = new AmmModelDAO().getAllAmmModels();
		for (int i = 0; i < ammMappedModels.getNumRows(); i++)
		{
			Integer targetModel = (Integer) ammMappedModels.getValueAt(i, "ModelId");
			Integer sourceModel = (Integer) ammMappedModels.getValueAt(i, "SrcModelId");
			if (targetModel.intValue() == modelId)
				throw new ValidationException("Model is an aggregated model target");
			if (sourceModel.intValue() == modelId) {
				throw new ValidationException("Model is an aggregated model source");
			}
		}
		BudgetInstructionAssignmentDAO biaDao = new BudgetInstructionAssignmentDAO();
		list = biaDao.getAllBudgetInstructionAssignments();
		checkIfInUse(list, "ModelId", "Budget Instruction");

		BudgetCycleDAO bcDao = new BudgetCycleDAO();
		list = bcDao.getAllBudgetCycles();

		checkIfInUse(list, "Model", "Budget Cycle");

		CellCalcDAO ccdao = new CellCalcDAO();
		list = ccdao.getCellCalcIntegrity();

		checkIfInUse(list, "Model", "Cell Calculation");
		try
		{
			TaskRIChecker.isInUseTaskGroup(getCPConnection(), mModelEVO.getPK(), 3);
		} catch (ValidationException e)
		{
			throw new ValidationException("Model " + e.getMessage() + " is in use in TaskGroup ");
		}

		ReportDefinitionAccessor reportDefAccessor = new ReportDefinitionAccessor(getInitialContext());
		AllReportDefCalcByModelIdELO calclist = reportDefAccessor.getAllReportDefCalcByModelId(modelId);
		AllReportDefFormcByModelIdELO formlist = reportDefAccessor.getAllReportDefFormcByModelId(modelId);
		AllReportDefSummaryCalcByModelIdELO summarylist = reportDefAccessor.getAllReportDefSummaryCalcByModelId(modelId);
		AllReportDefMappedExcelcByModelIdELO mappedlist = reportDefAccessor.getAllReportDefMappedExcelcByModelId(modelId);

		if (((calclist != null) && (calclist.getNumRows() > 0)) || ((formlist != null) && (formlist.getNumRows() > 0)) || ((summarylist != null) && (summarylist.getNumRows() > 0)) || ((mappedlist != null) && (summarylist.getNumRows() > 0)))
		{
			throw new ValidationException("Model is in use in Report Definition");
		}
	}

	public void ejbCreate() throws EJBException {
	}

	public void ejbRemove() {
	}

	public void setSessionContext(SessionContext context) {
		this.mSessionContext = context;
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	private ModelAccessor getModelAccessor() throws Exception {
		if (this.mModelAccessor == null) {
			this.mModelAccessor = new ModelAccessor(this.getInitialContext());
		}

		return this.mModelAccessor;
	}

	private CurrencyAccessor getCurrencyAccessor() throws Exception {
		if (this.mCurrencyAccessor == null) {
			this.mCurrencyAccessor = new CurrencyAccessor(this.getInitialContext());
		}

		return this.mCurrencyAccessor;
	}

	private DimensionAccessor getDimensionAccessor() throws Exception {
		if (this.mDimensionAccessor == null) {
			this.mDimensionAccessor = new DimensionAccessor(this.getInitialContext());
		}

		return this.mDimensionAccessor;
	}

	private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
		try {
			JmsConnectionImpl e = new JmsConnectionImpl(this.getInitialContext(), 3, "entityEventTopic");
			e.createSession();
			EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, this.getClass().getName());
			this.mLog.debug("update", "sending event message: " + em.toString());
			e.send(em);
			e.closeSession();
			e.closeConnection();
		} catch (Exception var6) {
			var6.printStackTrace();
		}

	}

	private boolean isDimensionAmendable(DimensionPK dimPK) {
		return true;
	}

	private void checkIfInUse(EntityList list, String key, String check) throws ValidationException {
		String message = "Model is in use by " + check;
		int numRows = list.getNumRows();

		for (int i = 0; i < numRows; ++i) {
			Object o = list.getValueAt(i, key);
			if (o instanceof Integer) {
				Integer id = (Integer) o;
				if (this.mModelEVO.getPK().getModelId() == id.intValue()) {
					throw new ValidationException(message);
				}
			} else if (o instanceof ModelRef) {
				ModelRef var9 = (ModelRef) o;
				if (this.mModelEVO.getPK().equals(var9.getPrimaryKey())) {
					throw new ValidationException(message);
				}
			}
		}

	}

	private EntityRef[] findEntityRefs(EntityList list, String[] colName, String[] value) {
		EntityRef[] ans = new EntityRef[colName.length];
		int row = 0;

		while (row < list.getNumRows()) {
			int i = 0;

			while (true) {
				if (i < colName.length) {
					EntityRef ref = (EntityRef) list.getValueAt(row, colName[i]);
					if (ref.getNarrative().equals(value[i])) {
						ans[i] = ref;
						if (i == colName.length - 1) {
							return ans;
						}

						++i;
						continue;
					}
				}

				++row;
				break;
			}
		}

		return null;
	}

	private void createDefaultWeightingProfiles(ModelImpl editorData) throws Exception, ValidationException {
		String visId = "Even Split";
		this.createDefaultWeightingProfile(visId);
		this.getModelAccessor().flush(new ModelPK(this.mModelEVO.getModelId()));
		this.createWeightingDeployment(this.mModelEVO.getVisId(), visId);
	}

	private void createWeightingDeployment(String modelVisId, String profileVisId) throws ValidationException, Exception {
		WeightingDeploymentsProcess wdProcess = this.getCPConnection().getWeightingDeploymentsProcess();
		WeightingDeploymentEditorSession wdSession = wdProcess.getWeightingDeploymentEditorSession((Object) null);
		WeightingDeploymentEditor wdEditor = wdSession.getWeightingDeploymentEditor();
		EntityList profiles = wdEditor.getOwnershipRefs();
		EntityRef[] refs = this.findEntityRefs(profiles, new String[] { "Model", "WeightingProfile" }, new String[] { modelVisId, profileVisId });
		if (refs == null) {
			throw new IllegalStateException("Unable to locate model for UDWP deployment insert:" + modelVisId + " " + profileVisId);
		} else {
			ModelRef modelRef = (ModelRef) refs[0];
			wdEditor.setModelRef(modelRef);
			WeightingProfileRef profileRef = (WeightingProfileRef) refs[1];
			if (profileRef == null) {
				throw new IllegalStateException("Unable to locate profile for UDWP deployment insert:" + modelVisId);
			} else {
				wdEditor.setWeightingProfileRef(profileRef);
				wdEditor.setAnyAccount(true);
				wdEditor.setAnyBusiness(true);
				wdEditor.setAnyDataType(true);
				wdEditor.commit();
				wdSession.commit(false);
				wdProcess.terminateSession(wdSession);
				this.closeCPConnection();
			}
		}
	}

	private WeightingProfileCK createDefaultWeightingProfile(String visId) throws ValidationException, Exception {
		WeightingProfilesProcess process = this.getCPConnection().getWeightingProfilesProcess();
		WeightingProfileEditorSession session = process.getWeightingProfileEditorSession((Object) null);
		WeightingProfileEditor editor = session.getWeightingProfileEditor();
		EntityList models = editor.getOwnershipRefs();
		EntityRef[] refs = this.findEntityRefs(models, new String[] { "Model" }, new String[] { this.mModelEVO.getVisId() });
		if (refs == null) {
			throw new IllegalStateException("Unable to locate model for udwp insert:" + this.mModelEVO.getVisId());
		} else {
			ModelRef modelRef = (ModelRef) refs[0];
			editor.setModelRef(modelRef);
			editor.setVisId(visId);
			editor.setDescription("Even Split");
			WeightingProfile wp = editor.getWeightingProfile();

			for (int wpKey = 0; wpKey < wp.getNumWeightingRows(); ++wpKey) {
				editor.setWeighting(wpKey, 1);
			}

			editor.commit();
			WeightingProfileCK var10 = (WeightingProfileCK) session.commit(false);
			process.terminateSession(session);
			this.closeCPConnection();
			return var10;
		}
	}

	private void loadModelProperties(ModelEVO modelEVO, ModelImpl modelImpl) {
		if (modelEVO.getModelProperties() != null) {
			Iterator i$ = modelEVO.getModelProperties().iterator();

			while (i$.hasNext()) {
				ModelPropertyEVO modelPropertyEVO = (ModelPropertyEVO) i$.next();
				modelImpl.getProperties().put(modelPropertyEVO.getPropertyName(), modelPropertyEVO.getPropertyValue());
			}
		}

	}

	private void updateModelPropertyEVOs(ModelImpl modelImpl, ModelEVO modelEVO) {
		Map newProperties = modelImpl.getProperties();
		HashMap existingEVOs = new HashMap();
		Iterator i$;
		if (modelEVO.getModelProperties() != null) {
			i$ = modelEVO.getModelProperties().iterator();

			ModelPropertyEVO entry;
			while (i$.hasNext()) {
				entry = (ModelPropertyEVO) i$.next();
				existingEVOs.put(entry.getPropertyName(), entry);
			}

			i$ = modelEVO.getModelProperties().iterator();

			while (i$.hasNext()) {
				entry = (ModelPropertyEVO) i$.next();
				String modelPropertyEVO = entry.getPropertyName();
				String value = (String) newProperties.get(modelPropertyEVO);
				if (value != null) {
					entry.setPropertyValue(value);
					newProperties.remove(modelPropertyEVO);
				} else {
					modelEVO.deleteModelPropertiesItem(entry.getPK());
				}
			}
		}

		i$ = newProperties.entrySet().iterator();

		while (i$.hasNext()) {
			Entry entry1 = (Entry) i$.next();
			ModelPropertyEVO modelPropertyEVO1 = new ModelPropertyEVO(modelEVO.getModelId(), (String) entry1.getKey(), (String) entry1.getValue());
			modelEVO.addModelPropertiesItem(modelPropertyEVO1);
		}

	}

    private int issueCubeAdminTask(int financeCubeId, String visId, String description) throws Exception {
        ModelDimensionsELO modelDimensions = this.getModelAccessor().getModelDimensions(this.mModelEVO.getModelId());

        try {
            CubeAdminTaskRequest request = new CubeAdminTaskRequest(financeCubeId, visId, description, 0, 1);
            return TaskMessageFactory.issueNewTask(this.getInitialContext(), true, request, this.getUserId());
        } catch (Exception e) {
            throw new EJBException(e);
        }
    }

    public int issueImportDataModelTask(List<ModelRef> models, List<DataTypeRef> dataTypes, int globalModelId, int userId, int issuingTaskId) throws EJBException {
        try {
            ImportDataModelTaskRequest request = new ImportDataModelTaskRequest(models, dataTypes, globalModelId, userId);
            int taskId = TaskMessageFactory.issueNewTask(new InitialContext(), true, request, userId);
            this.mLog.debug("ImportDataModelTask", "taskId=" + taskId);
            return taskId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e);
        }
    }
}
