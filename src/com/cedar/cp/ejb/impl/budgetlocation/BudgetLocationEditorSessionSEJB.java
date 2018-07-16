package com.cedar.cp.ejb.impl.budgetlocation;

import java.util.Iterator;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.budgetlocation.BudgetLocationEditorSessionCSO;
import com.cedar.cp.dto.budgetlocation.BudgetLocationEditorSessionSSO;
import com.cedar.cp.dto.budgetlocation.BudgetLocationImpl;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.BudgetUserDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;

public class BudgetLocationEditorSessionSEJB extends AbstractSession {

	private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "";
	private static final String DEPENDANTS_FOR_UPDATE = "<18>";
	private static final String DEPENDANTS_FOR_DELETE = "<15><16><18>";
	private transient Log mLog = new Log(getClass());
	private SessionContext mSessionContext;
	private DAGContext mDAGContext;
	private transient ModelAccessor mModelAccessor;
	private transient DimensionAccessor mDimensionAccessor;
	private transient UserAccessor mUserAccessor;
	private transient BudgetLocationEditorSessionSSO mSSO;
	private transient ModelEVO mModelEVO;
	private ModelPK mThisTableKey;

	public BudgetLocationEditorSessionSEJB() {
		try {
			mDAGContext = new DAGContext(getInitialContext());
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}

	public BudgetLocationEditorSessionSSO getItemData(Object paramKey) throws ValidationException, EJBException {
		Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
		mThisTableKey = ((ModelPK) paramKey);
		mLog.debug("getItemData", paramKey);
		try {
			mSSO = new BudgetLocationEditorSessionSSO();
			BudgetLocationImpl editorData = new BudgetLocationImpl(mThisTableKey);
			mSSO.setEditorData(editorData);

			editorData.setPrimaryKey(mThisTableKey);
			editorData.setModelUserElementAccess(new BudgetUserDAO().getRespAreaAccess(mThisTableKey));
			editorData.setAllUsers(new UserDAO().getAllUsers());
			EntityList modelsEl = new BudgetUserDAO().getModelsAndRAHierarchies();

			boolean modelFound = false;
			for (int i = 0; i < modelsEl.getNumRows(); i++) {
				ModelRef model = (ModelRef) modelsEl.getValueAt(i, "Model");
				DimensionRef dim = (DimensionRef) modelsEl.getValueAt(i, "Dimension");
				String dimdescr = (String) modelsEl.getValueAt(i, "DimensionDescription");
				int hierarchyId = ((Integer) modelsEl.getValueAt(i, "HierarchyId")).intValue();
				HierarchyRef hier = (HierarchyRef) modelsEl.getValueAt(i, "Hierarchy");
				StructureElementRef rootse = (StructureElementRef) modelsEl.getValueAt(i, "StructureElement");
				String rootdescr = (String) modelsEl.getValueAt(i, "StructureElementDescription");

				if (model.getPrimaryKey().equals(mThisTableKey)) {
					modelFound = true;

					editorData.setModelRef(model);
					editorData.setDimensionRef(dim);
					editorData.setHierarchyRef(hier);
					editorData.setRootElementEntityRef(rootse);

					StructureElementPK sepk = (StructureElementPK) rootse.getPrimaryKey();
					ImmediateChildrenELO rootChildren = new StructureElementDAO().getImmediateChildren(sepk.getStructureId(), sepk.getStructureElementId());
					mLog.debug("root element children=" + rootChildren.size());
					editorData.setRootChildren(rootChildren);
				}
			}

			return mSSO;
		} catch (EJBException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		} finally {
			if (timer != null)
				timer.logInfo("getItemData", mThisTableKey);
		}
	}

	private void makeItemData() throws Exception {
	}

	public void update(BudgetLocationEditorSessionCSO cso) throws ValidationException, EJBException {
		setUserId(cso.getUserId());
		mLog.debug("update " + cso.getEditorData().getPrimaryKey());

		Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
		BudgetLocationImpl editorData = cso.getEditorData();
		try {
			BudgetUserActions.update(getInitialContext(), getUserId(), (PrimaryKey) editorData.getPrimaryKey(), editorData.getModelUserElementAccess(), editorData.isDeployForms(), true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		} finally {
			if (timer != null)
				timer.logInfo("update", mThisTableKey);
		}
	}

	public void delete(Object paramKey) throws ValidationException, EJBException {
		if (mLog.isDebugEnabled()) {
			mLog.debug("delete", paramKey);
		}
		Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
		mThisTableKey = ((ModelPK) paramKey);
		try {
			mModelEVO = getModelAccessor().getDetails(mThisTableKey, "<15><16><18>");

			validateDelete();

			deleteDataFromOtherTables();

			getModelAccessor().setDetails(mModelEVO);

			sendEntityEventMessage("Model", mThisTableKey, 2);
			sendEntityEventMessage("BudgetUser", mThisTableKey, 2);
		} catch (ValidationException ve) {
			throw ve;
		} catch (EJBException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		} finally {
			if (timer != null)
				timer.logInfo("delete", mThisTableKey);
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
		mSessionContext = context;
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	private ModelAccessor getModelAccessor() throws Exception {
		if (mModelAccessor == null) {
			mModelAccessor = new ModelAccessor(getInitialContext());
		}
		return mModelAccessor;
	}

	private DimensionAccessor getDimensionAccessor() throws Exception {
		if (mDimensionAccessor == null) {
			mDimensionAccessor = new DimensionAccessor(getInitialContext());
		}
		return mDimensionAccessor;
	}

	private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
		try {
			JmsConnection jms = new JmsConnectionImpl(getInitialContext(), 3, "entityEventTopic");
			jms.createSession();
			EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, getClass().getName());
			mLog.debug("update", "sending event message: " + em.toString());
			jms.send(em);
			jms.closeSession();
			jms.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void copyAssignments(int userId, Object fromUserKey, List<Object> toUserKeys) throws ValidationException, EJBException {
		setUserId(userId);
		try {
			UserPK fromUserPK;
			List fromUsersList;
			Iterator i$;
			fromUserPK = (UserPK) ((EntityRef) fromUserKey).getPrimaryKey();
			BudgetUserDAO buDAO = new BudgetUserDAO();
			fromUsersList = buDAO.getRespAreaAccess(fromUserPK);
			for (i$ = toUserKeys.iterator(); i$.hasNext();) {
				Object toUser = i$.next();

				UserRef toUserRef = (UserRef) toUser;
				UserPK toUserPK = (UserPK) toUserRef.getPrimaryKey();

				if (!fromUserPK.equals(toUserPK)) {
					BudgetUserActions.update(getInitialContext(), getUserId(), toUserPK, fromUsersList, false, false, toUserRef);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		} finally {
			setUserId(0);
		}
	}
}