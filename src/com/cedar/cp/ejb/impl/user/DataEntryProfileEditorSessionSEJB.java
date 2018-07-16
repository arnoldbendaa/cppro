// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.DataEntryProfileEditorSessionCSO;
import com.cedar.cp.dto.user.DataEntryProfileEditorSessionSSO;
import com.cedar.cp.dto.user.DataEntryProfileImpl;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.dto.xmlform.XmlFormCK;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.XmlFormRefImpl;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.user.DataEntryProfileEVO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormAccessor;
import com.cedar.cp.ejb.impl.xmlform.XmlFormEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class DataEntryProfileEditorSessionSEJB extends AbstractSession {

	private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<3>";
	private static final String DEPENDANTS_FOR_INSERT = "";
	private static final String DEPENDANTS_FOR_COPY = "<3>";
	private static final String DEPENDANTS_FOR_UPDATE = "<3>";
	private static final String DEPENDANTS_FOR_DELETE = "<3>";
	private transient Log mLog = new Log(this.getClass());
	private transient SessionContext mSessionContext;
	private transient UserAccessor mUserAccessor;
	private transient XmlFormAccessor mXmlFormAccessor;
	private DataEntryProfileEditorSessionSSO mSSO;
	private DataEntryProfileCK mThisTableKey;
	private UserEVO mUserEVO;
	private DataEntryProfileEVO mDataEntryProfileEVO;

	public DataEntryProfileEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
		setUserId(userId);

		if (mLog.isDebugEnabled()) {
			mLog.debug("getItemData", paramKey);
		}
		Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
		mThisTableKey = ((DataEntryProfileCK) paramKey);
		try {
			mUserEVO = getUserAccessor().getDetails(mThisTableKey, "<3>");

			mDataEntryProfileEVO = mUserEVO.getDataEntryProfilesItem(mThisTableKey.getDataEntryProfilePK());

			makeItemData();

			return mSSO;
		} catch (ValidationException e) {
			throw e;
		} catch (EJBException e) {
			if ((e.getCause() instanceof ValidationException))
				throw ((ValidationException) e.getCause());
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage(), e);
		} finally {
			setUserId(0);
			if (timer != null)
				timer.logInfo("getItemData", mThisTableKey);
		}
	}

	private void makeItemData() throws Exception {
		this.mSSO = new DataEntryProfileEditorSessionSSO();
		DataEntryProfileImpl editorData = this.buildDataEntryProfileEditData(this.mThisTableKey);
		this.completeGetItemData(editorData);
		this.mSSO.setEditorData(editorData);
	}

	private void completeGetItemData(DataEntryProfileImpl editorData) throws Exception {
	}

	private DataEntryProfileImpl buildDataEntryProfileEditData(Object thisKey) throws Exception {
		DataEntryProfileImpl editorData = new DataEntryProfileImpl(thisKey);
		editorData.setVisId(this.mDataEntryProfileEVO.getVisId());
		editorData.setUserId(this.mDataEntryProfileEVO.getUserId());
		editorData.setModelId(this.mDataEntryProfileEVO.getModelId());
		editorData.setBudgetCycleId(this.mDataEntryProfileEVO.getBudgetCycleId());
		editorData.setAutoOpenDepth(this.mDataEntryProfileEVO.getAutoOpenDepth());
		editorData.setDescription(this.mDataEntryProfileEVO.getDescription());
		editorData.setXmlformId(this.mDataEntryProfileEVO.getXmlformId());
		editorData.setFillDisplayArea(this.mDataEntryProfileEVO.getFillDisplayArea());
		editorData.setShowBoldSummaries(this.mDataEntryProfileEVO.getShowBoldSummaries());
		editorData.setShowHorizontalLines(this.mDataEntryProfileEVO.getShowHorizontalLines());
		editorData.setStructureId0(this.mDataEntryProfileEVO.getStructureId0());
		editorData.setStructureId1(this.mDataEntryProfileEVO.getStructureId1());
		editorData.setStructureId2(this.mDataEntryProfileEVO.getStructureId2());
		editorData.setStructureId3(this.mDataEntryProfileEVO.getStructureId3());
		editorData.setStructureId4(this.mDataEntryProfileEVO.getStructureId4());
		editorData.setStructureId5(this.mDataEntryProfileEVO.getStructureId5());
		editorData.setStructureId6(this.mDataEntryProfileEVO.getStructureId6());
		editorData.setStructureId7(this.mDataEntryProfileEVO.getStructureId7());
		editorData.setStructureId8(this.mDataEntryProfileEVO.getStructureId8());
		editorData.setStructureElementId0(this.mDataEntryProfileEVO.getStructureElementId0());
		editorData.setStructureElementId1(this.mDataEntryProfileEVO.getStructureElementId1());
		editorData.setStructureElementId2(this.mDataEntryProfileEVO.getStructureElementId2());
		editorData.setStructureElementId3(this.mDataEntryProfileEVO.getStructureElementId3());
		editorData.setStructureElementId4(this.mDataEntryProfileEVO.getStructureElementId4());
		editorData.setStructureElementId5(this.mDataEntryProfileEVO.getStructureElementId5());
		editorData.setStructureElementId6(this.mDataEntryProfileEVO.getStructureElementId6());
		editorData.setStructureElementId7(this.mDataEntryProfileEVO.getStructureElementId7());
		editorData.setStructureElementId8(this.mDataEntryProfileEVO.getStructureElementId8());
		editorData.setElementLabel0(this.mDataEntryProfileEVO.getElementLabel0());
		editorData.setElementLabel1(this.mDataEntryProfileEVO.getElementLabel1());
		editorData.setElementLabel2(this.mDataEntryProfileEVO.getElementLabel2());
		editorData.setElementLabel3(this.mDataEntryProfileEVO.getElementLabel3());
		editorData.setElementLabel4(this.mDataEntryProfileEVO.getElementLabel4());
		editorData.setElementLabel5(this.mDataEntryProfileEVO.getElementLabel5());
		editorData.setElementLabel6(this.mDataEntryProfileEVO.getElementLabel6());
		editorData.setElementLabel7(this.mDataEntryProfileEVO.getElementLabel7());
		editorData.setElementLabel8(this.mDataEntryProfileEVO.getElementLabel8());
		editorData.setDataType(this.mDataEntryProfileEVO.getDataType());
		editorData.setVersionNum(this.mDataEntryProfileEVO.getVersionNum());
		editorData.setUserRef(new UserRefImpl(this.mUserEVO.getPK(), this.mUserEVO.getName()));
		XmlFormPK key = null;
		if (this.mDataEntryProfileEVO.getXmlformId() != 0) {
			key = new XmlFormPK(this.mDataEntryProfileEVO.getXmlformId());
		}

		if (key != null) {
			editorData.setXmlFormRef(new XmlFormRefImpl(key, this.mDataEntryProfileEVO.getVisId()));
			// XmlFormEVO evoXmlForm = this.getXmlFormAccessor().getDetails(key, "");
			// editorData.setXmlFormRef(new XmlFormRefImpl(evoXmlForm.getPK(), evoXmlForm.getVisId()));
		}

		this.completeDataEntryProfileEditData(editorData);
		return editorData;
	}

	private void completeDataEntryProfileEditData(DataEntryProfileImpl editorData) throws Exception {
	}

	public DataEntryProfileEditorSessionSSO getNewItemData(int userId) throws EJBException {
		this.mLog.debug("getNewItemData");
		this.setUserId(userId);
		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;

		DataEntryProfileEditorSessionSSO var4;
		try {
			this.mSSO = new DataEntryProfileEditorSessionSSO();
			DataEntryProfileImpl e = new DataEntryProfileImpl((Object) null);
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

	private void completeGetNewItemData(DataEntryProfileImpl editorData) throws Exception {
	}

	public DataEntryProfileCK insert(DataEntryProfileEditorSessionCSO cso) throws ValidationException, EJBException {
		this.mLog.debug("insert");
		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.setUserId(cso.getUserId());
		DataEntryProfileImpl editorData = cso.getEditorData();

		try {
			this.mUserEVO = this.getUserAccessor().getDetails(editorData.getUserRef(), "");
			this.mDataEntryProfileEVO = new DataEntryProfileEVO();
			this.mDataEntryProfileEVO.setVisId(editorData.getVisId());
			this.mDataEntryProfileEVO.setUserId(editorData.getUserId());
			this.mDataEntryProfileEVO.setModelId(editorData.getModelId());
			this.mDataEntryProfileEVO.setBudgetCycleId(editorData.getBudgetCycleId());
			this.mDataEntryProfileEVO.setAutoOpenDepth(editorData.getAutoOpenDepth());
			this.mDataEntryProfileEVO.setDescription(editorData.getDescription());
			this.mDataEntryProfileEVO.setXmlformId(editorData.getXmlformId());
			this.mDataEntryProfileEVO.setFillDisplayArea(editorData.isFillDisplayArea());
			this.mDataEntryProfileEVO.setShowBoldSummaries(editorData.isShowBoldSummaries());
			this.mDataEntryProfileEVO.setShowHorizontalLines(editorData.isShowHorizontalLines());
			this.mDataEntryProfileEVO.setStructureId0(editorData.getStructureId0());
			this.mDataEntryProfileEVO.setStructureId1(editorData.getStructureId1());
			this.mDataEntryProfileEVO.setStructureId2(editorData.getStructureId2());
			this.mDataEntryProfileEVO.setStructureId3(editorData.getStructureId3());
			this.mDataEntryProfileEVO.setStructureId4(editorData.getStructureId4());
			this.mDataEntryProfileEVO.setStructureId5(editorData.getStructureId5());
			this.mDataEntryProfileEVO.setStructureId6(editorData.getStructureId6());
			this.mDataEntryProfileEVO.setStructureId7(editorData.getStructureId7());
			this.mDataEntryProfileEVO.setStructureId8(editorData.getStructureId8());
			this.mDataEntryProfileEVO.setStructureElementId0(editorData.getStructureElementId0());
			this.mDataEntryProfileEVO.setStructureElementId1(editorData.getStructureElementId1());
			this.mDataEntryProfileEVO.setStructureElementId2(editorData.getStructureElementId2());
			this.mDataEntryProfileEVO.setStructureElementId3(editorData.getStructureElementId3());
			this.mDataEntryProfileEVO.setStructureElementId4(editorData.getStructureElementId4());
			this.mDataEntryProfileEVO.setStructureElementId5(editorData.getStructureElementId5());
			this.mDataEntryProfileEVO.setStructureElementId6(editorData.getStructureElementId6());
			this.mDataEntryProfileEVO.setStructureElementId7(editorData.getStructureElementId7());
			this.mDataEntryProfileEVO.setStructureElementId8(editorData.getStructureElementId8());
			this.mDataEntryProfileEVO.setElementLabel0(editorData.getElementLabel0());
			this.mDataEntryProfileEVO.setElementLabel1(editorData.getElementLabel1());
			this.mDataEntryProfileEVO.setElementLabel2(editorData.getElementLabel2());
			this.mDataEntryProfileEVO.setElementLabel3(editorData.getElementLabel3());
			this.mDataEntryProfileEVO.setElementLabel4(editorData.getElementLabel4());
			this.mDataEntryProfileEVO.setElementLabel5(editorData.getElementLabel5());
			this.mDataEntryProfileEVO.setElementLabel6(editorData.getElementLabel6());
			this.mDataEntryProfileEVO.setElementLabel7(editorData.getElementLabel7());
			this.mDataEntryProfileEVO.setElementLabel8(editorData.getElementLabel8());
			this.mDataEntryProfileEVO.setDataType(editorData.getDataType());
			this.updateDataEntryProfileRelationships(editorData);
			this.completeInsertSetup(editorData);
			this.validateInsert();
			this.mUserEVO.addDataEntryProfilesItem(this.mDataEntryProfileEVO);
			this.mUserEVO = this.getUserAccessor().setAndGetDetails(this.mUserEVO, "<2>");
			Iterator e = this.mUserEVO.getDataEntryProfiles().iterator();

			while (true) {
				if (e.hasNext()) {
					this.mDataEntryProfileEVO = (DataEntryProfileEVO) e.next();
					if (!this.mDataEntryProfileEVO.insertPending()) {
						continue;
					}
				}

				this.insertIntoAdditionalTables(editorData, true);
				this.sendEntityEventMessage("DataEntryProfile", this.mDataEntryProfileEVO.getPK(), 1);
				DataEntryProfileCK var5 = new DataEntryProfileCK(this.mUserEVO.getPK(), this.mDataEntryProfileEVO.getPK());
				return var5;
			}
		} catch (ValidationException var11) {
			throw new EJBException(var11);
		} catch (EJBException var12) {
			throw var12;
		} catch (Exception var13) {
			var13.printStackTrace();
			throw new EJBException(var13.getMessage(), var13);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("insert", "");
			}

		}
	}

	private void updateDataEntryProfileRelationships(DataEntryProfileImpl editorData) throws ValidationException {
		if (editorData.getXmlFormRef() != null) {
			Object key = editorData.getXmlFormRef().getPrimaryKey();
			if (key instanceof XmlFormPK) {
				this.mDataEntryProfileEVO.setXmlformId(((XmlFormPK) key).getXmlFormId());
			} else {
				this.mDataEntryProfileEVO.setXmlformId(((XmlFormCK) key).getXmlFormPK().getXmlFormId());
			}

			try {
				this.getXmlFormAccessor().getDetails(key, "");
			} catch (Exception var4) {
				var4.printStackTrace();
				throw new ValidationException(editorData.getXmlFormRef() + " no longer exists");
			}
		} else {
			this.mDataEntryProfileEVO.setXmlformId(0);
		}

	}

	private void completeInsertSetup(DataEntryProfileImpl editorData) throws Exception {
	}

	private void insertIntoAdditionalTables(DataEntryProfileImpl editorData, boolean isInsert) throws Exception {
	}

	private void validateInsert() throws ValidationException {
	}

	public DataEntryProfileCK copy(DataEntryProfileEditorSessionCSO cso) throws ValidationException, EJBException {
		this.mLog.debug("copy");
		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.setUserId(cso.getUserId());
		DataEntryProfileImpl editorData = cso.getEditorData();
		this.mThisTableKey = (DataEntryProfileCK) editorData.getPrimaryKey();

		try {
			this.mUserEVO = this.getUserAccessor().getDetails(this.mThisTableKey, "<3>");
			DataEntryProfileEVO e = this.mUserEVO.getDataEntryProfilesItem(this.mThisTableKey.getDataEntryProfilePK());
			this.mDataEntryProfileEVO = e.deepClone();
			this.mDataEntryProfileEVO.setVisId(editorData.getVisId());
			this.mDataEntryProfileEVO.setUserId(editorData.getUserId());
			this.mDataEntryProfileEVO.setModelId(editorData.getModelId());
			this.mDataEntryProfileEVO.setBudgetCycleId(editorData.getBudgetCycleId());
			this.mDataEntryProfileEVO.setAutoOpenDepth(editorData.getAutoOpenDepth());
			this.mDataEntryProfileEVO.setDescription(editorData.getDescription());
			this.mDataEntryProfileEVO.setXmlformId(editorData.getXmlformId());
			this.mDataEntryProfileEVO.setFillDisplayArea(editorData.isFillDisplayArea());
			this.mDataEntryProfileEVO.setShowBoldSummaries(editorData.isShowBoldSummaries());
			this.mDataEntryProfileEVO.setShowHorizontalLines(editorData.isShowHorizontalLines());
			this.mDataEntryProfileEVO.setStructureId0(editorData.getStructureId0());
			this.mDataEntryProfileEVO.setStructureId1(editorData.getStructureId1());
			this.mDataEntryProfileEVO.setStructureId2(editorData.getStructureId2());
			this.mDataEntryProfileEVO.setStructureId3(editorData.getStructureId3());
			this.mDataEntryProfileEVO.setStructureId4(editorData.getStructureId4());
			this.mDataEntryProfileEVO.setStructureId5(editorData.getStructureId5());
			this.mDataEntryProfileEVO.setStructureId6(editorData.getStructureId6());
			this.mDataEntryProfileEVO.setStructureId7(editorData.getStructureId7());
			this.mDataEntryProfileEVO.setStructureId8(editorData.getStructureId8());
			this.mDataEntryProfileEVO.setStructureElementId0(editorData.getStructureElementId0());
			this.mDataEntryProfileEVO.setStructureElementId1(editorData.getStructureElementId1());
			this.mDataEntryProfileEVO.setStructureElementId2(editorData.getStructureElementId2());
			this.mDataEntryProfileEVO.setStructureElementId3(editorData.getStructureElementId3());
			this.mDataEntryProfileEVO.setStructureElementId4(editorData.getStructureElementId4());
			this.mDataEntryProfileEVO.setStructureElementId5(editorData.getStructureElementId5());
			this.mDataEntryProfileEVO.setStructureElementId6(editorData.getStructureElementId6());
			this.mDataEntryProfileEVO.setStructureElementId7(editorData.getStructureElementId7());
			this.mDataEntryProfileEVO.setStructureElementId8(editorData.getStructureElementId8());
			this.mDataEntryProfileEVO.setElementLabel0(editorData.getElementLabel0());
			this.mDataEntryProfileEVO.setElementLabel1(editorData.getElementLabel1());
			this.mDataEntryProfileEVO.setElementLabel2(editorData.getElementLabel2());
			this.mDataEntryProfileEVO.setElementLabel3(editorData.getElementLabel3());
			this.mDataEntryProfileEVO.setElementLabel4(editorData.getElementLabel4());
			this.mDataEntryProfileEVO.setElementLabel5(editorData.getElementLabel5());
			this.mDataEntryProfileEVO.setElementLabel6(editorData.getElementLabel6());
			this.mDataEntryProfileEVO.setElementLabel7(editorData.getElementLabel7());
			this.mDataEntryProfileEVO.setElementLabel8(editorData.getElementLabel8());
			this.mDataEntryProfileEVO.setDataType(editorData.getDataType());
			this.mDataEntryProfileEVO.setVersionNum(0);
			this.updateDataEntryProfileRelationships(editorData);
			this.completeCopySetup(editorData);
			this.validateCopy();
			UserPK parentKey = (UserPK) editorData.getUserRef().getPrimaryKey();
			if (!parentKey.equals(this.mUserEVO.getPK())) {
				this.mUserEVO = this.getUserAccessor().getDetails(parentKey, "");
			}

			this.mDataEntryProfileEVO.prepareForInsert((UserEVO) null);
			this.mUserEVO.addDataEntryProfilesItem(this.mDataEntryProfileEVO);
			this.mUserEVO = this.getUserAccessor().setAndGetDetails(this.mUserEVO, "<2><3>");
			Iterator iter = this.mUserEVO.getDataEntryProfiles().iterator();

			while (true) {
				if (iter.hasNext()) {
					this.mDataEntryProfileEVO = (DataEntryProfileEVO) iter.next();
					if (!this.mDataEntryProfileEVO.insertPending()) {
						continue;
					}
				}

				this.mThisTableKey = new DataEntryProfileCK(this.mUserEVO.getPK(), this.mDataEntryProfileEVO.getPK());
				this.insertIntoAdditionalTables(editorData, false);
				this.sendEntityEventMessage("DataEntryProfile", this.mDataEntryProfileEVO.getPK(), 1);
				DataEntryProfileCK var7 = this.mThisTableKey;
				return var7;
			}
		} catch (ValidationException var13) {
			throw new EJBException(var13);
		} catch (EJBException var14) {
			throw var14;
		} catch (Exception var15) {
			var15.printStackTrace();
			throw new EJBException(var15);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("copy", this.mThisTableKey);
			}

		}
	}

	private void validateCopy() throws ValidationException {
	}

	private void completeCopySetup(DataEntryProfileImpl editorData) throws Exception {
	}

	public void update(DataEntryProfileEditorSessionCSO cso) throws ValidationException, EJBException {
		this.mLog.debug("update");
		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.setUserId(cso.getUserId());
		DataEntryProfileImpl editorData = cso.getEditorData();
		this.mThisTableKey = (DataEntryProfileCK) editorData.getPrimaryKey();

		try {
			this.mUserEVO = this.getUserAccessor().getDetails(this.mThisTableKey, "<3>");
			this.mDataEntryProfileEVO = this.mUserEVO.getDataEntryProfilesItem(this.mThisTableKey.getDataEntryProfilePK());
			this.preValidateUpdate(editorData);
			this.mDataEntryProfileEVO.setVisId(editorData.getVisId());
			this.mDataEntryProfileEVO.setUserId(editorData.getUserId());
			this.mDataEntryProfileEVO.setModelId(editorData.getModelId());
			this.mDataEntryProfileEVO.setBudgetCycleId(editorData.getBudgetCycleId());
			this.mDataEntryProfileEVO.setAutoOpenDepth(editorData.getAutoOpenDepth());
			this.mDataEntryProfileEVO.setDescription(editorData.getDescription());
			this.mDataEntryProfileEVO.setXmlformId(editorData.getXmlformId());
			this.mDataEntryProfileEVO.setFillDisplayArea(editorData.isFillDisplayArea());
			this.mDataEntryProfileEVO.setShowBoldSummaries(editorData.isShowBoldSummaries());
			this.mDataEntryProfileEVO.setShowHorizontalLines(editorData.isShowHorizontalLines());
			this.mDataEntryProfileEVO.setStructureId0(editorData.getStructureId0());
			this.mDataEntryProfileEVO.setStructureId1(editorData.getStructureId1());
			this.mDataEntryProfileEVO.setStructureId2(editorData.getStructureId2());
			this.mDataEntryProfileEVO.setStructureId3(editorData.getStructureId3());
			this.mDataEntryProfileEVO.setStructureId4(editorData.getStructureId4());
			this.mDataEntryProfileEVO.setStructureId5(editorData.getStructureId5());
			this.mDataEntryProfileEVO.setStructureId6(editorData.getStructureId6());
			this.mDataEntryProfileEVO.setStructureId7(editorData.getStructureId7());
			this.mDataEntryProfileEVO.setStructureId8(editorData.getStructureId8());
			this.mDataEntryProfileEVO.setStructureElementId0(editorData.getStructureElementId0());
			this.mDataEntryProfileEVO.setStructureElementId1(editorData.getStructureElementId1());
			this.mDataEntryProfileEVO.setStructureElementId2(editorData.getStructureElementId2());
			this.mDataEntryProfileEVO.setStructureElementId3(editorData.getStructureElementId3());
			this.mDataEntryProfileEVO.setStructureElementId4(editorData.getStructureElementId4());
			this.mDataEntryProfileEVO.setStructureElementId5(editorData.getStructureElementId5());
			this.mDataEntryProfileEVO.setStructureElementId6(editorData.getStructureElementId6());
			this.mDataEntryProfileEVO.setStructureElementId7(editorData.getStructureElementId7());
			this.mDataEntryProfileEVO.setStructureElementId8(editorData.getStructureElementId8());
			this.mDataEntryProfileEVO.setElementLabel0(editorData.getElementLabel0());
			this.mDataEntryProfileEVO.setElementLabel1(editorData.getElementLabel1());
			this.mDataEntryProfileEVO.setElementLabel2(editorData.getElementLabel2());
			this.mDataEntryProfileEVO.setElementLabel3(editorData.getElementLabel3());
			this.mDataEntryProfileEVO.setElementLabel4(editorData.getElementLabel4());
			this.mDataEntryProfileEVO.setElementLabel5(editorData.getElementLabel5());
			this.mDataEntryProfileEVO.setElementLabel6(editorData.getElementLabel6());
			this.mDataEntryProfileEVO.setElementLabel7(editorData.getElementLabel7());
			this.mDataEntryProfileEVO.setElementLabel8(editorData.getElementLabel8());
			this.mDataEntryProfileEVO.setDataType(editorData.getDataType());
			if (editorData.getVersionNum() != this.mDataEntryProfileEVO.getVersionNum()) {
				throw new VersionValidationException(this.mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + this.mDataEntryProfileEVO.getVersionNum());
			}

			this.updateDataEntryProfileRelationships(editorData);
			this.completeUpdateSetup(editorData);
			this.postValidateUpdate();
			this.getUserAccessor().setDetails(this.mUserEVO);
			this.updateAdditionalTables(editorData);
			this.sendEntityEventMessage("DataEntryProfile", this.mDataEntryProfileEVO.getPK(), 3);
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

	private void preValidateUpdate(DataEntryProfileImpl editorData) throws ValidationException {
	}

	private void postValidateUpdate() throws ValidationException {
	}

	private void completeUpdateSetup(DataEntryProfileImpl editorData) throws Exception {
	}

	public EntityList getOwnershipData(int userId, Object paramKey) throws EJBException {
		this.mLog.debug("getOwnershipData");
		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.setUserId(userId);
		this.mThisTableKey = (DataEntryProfileCK) paramKey;

		AllUsersELO e;
		try {
			e = this.getUserAccessor().getAllUsers();
		} catch (Exception var8) {
			var8.printStackTrace();
			throw new EJBException(var8.getMessage(), var8);
		} finally {
			this.setUserId(0);
			if (timer != null) {
				timer.logInfo("getOwnershipData", "");
			}

		}

		return e;
	}

	private void updateAdditionalTables(DataEntryProfileImpl editorData) throws Exception {
	}

	public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
		if (this.mLog.isDebugEnabled()) {
			this.mLog.debug("delete", paramKey);
		}

		Timer timer = this.mLog.isInfoEnabled() ? new Timer(this.mLog) : null;
		this.setUserId(userId);
		this.mThisTableKey = (DataEntryProfileCK) paramKey;

		try {
			this.mUserEVO = this.getUserAccessor().getDetails(this.mThisTableKey, "<3>");
			this.mDataEntryProfileEVO = this.mUserEVO.getDataEntryProfilesItem(this.mThisTableKey.getDataEntryProfilePK());
			this.validateDelete();
			this.deleteDataFromOtherTables();
			this.mUserEVO.deleteDataEntryProfilesItem(this.mThisTableKey.getDataEntryProfilePK());
			this.getUserAccessor().setDetails(this.mUserEVO);
			this.sendEntityEventMessage("DataEntryProfile", this.mThisTableKey.getDataEntryProfilePK(), 2);
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

	private void deleteDataFromOtherTables() throws Exception {
	}

	private void validateDelete() throws ValidationException, Exception {
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

	private UserAccessor getUserAccessor() throws Exception {
		if (this.mUserAccessor == null) {
			this.mUserAccessor = new UserAccessor(this.getInitialContext());
		}

		return this.mUserAccessor;
	}

	private XmlFormAccessor getXmlFormAccessor() throws Exception {
		if (this.mXmlFormAccessor == null) {
			this.mXmlFormAccessor = new XmlFormAccessor(this.getInitialContext());
		}

		return this.mXmlFormAccessor;
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
}
