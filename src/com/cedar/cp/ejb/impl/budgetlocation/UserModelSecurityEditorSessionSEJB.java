package com.cedar.cp.ejb.impl.budgetlocation;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.budgetlocation.UserModelElementAssignmentImpl;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityEditorSessionCSO;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityEditorSessionSSO;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
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
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;

public class UserModelSecurityEditorSessionSEJB extends AbstractSession {

	private transient Log mLog = new Log(getClass());
	private SessionContext mSessionContext;
	private DAGContext mDAGContext;
	private transient ModelAccessor mModelAccessor;
	private transient DimensionAccessor mDimensionAccessor;
	private transient UserAccessor mUserAccessor;
	private transient UserModelSecurityEditorSessionSSO mSSO;
	private transient ModelEVO mModelEVO;
	private transient DimensionRef mDimensionRef;
	private UserPK mThisTableKey;

	public UserModelSecurityEditorSessionSEJB() {
		try {
			mDAGContext = new DAGContext(getInitialContext());
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}

	public UserModelSecurityEditorSessionSSO getItemData(Object paramKey) throws ValidationException, EJBException {
		Timer timer = new Timer(mLog);
		mThisTableKey = ((UserPK) paramKey);
		mLog.debug("getItemData", mThisTableKey == null ? "All" : mThisTableKey);
		try {
			UserModelSecurityImpl editorData = new UserModelSecurityImpl(mThisTableKey);
			UserEVO userevo;
			if (mThisTableKey != null) {
				userevo = getUserAccessor().getDetails(mThisTableKey, "");
				editorData.setUserRef(userevo.getEntityRef());
				editorData.setUserDescription(userevo.getFullName());
			}
			editorData.setUserModelElementAccess(new BudgetUserDAO().getRespAreaAccess(mThisTableKey));
			editorData.setModelsAndRAHierarchies(new BudgetUserDAO().getModelsAndRAHierarchies());

			mSSO = new UserModelSecurityEditorSessionSSO();
			mSSO.setEditorData(editorData);

			return mSSO;
		} catch (EJBException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		} finally {
			timer.logInfo("getItemData", mThisTableKey == null ? "All" : mThisTableKey);
		}
	}

	public void update(UserModelSecurityEditorSessionCSO cso) throws ValidationException, EJBException {
		setUserId(cso.getUserId());
		Object pk = cso.getEditorData().getPrimaryKey();
		mLog.debug("update", pk == null ? "All" : pk);

		Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
		UserModelSecurityImpl editorData = cso.getEditorData();
		String counts = "";
		try {
			counts = BudgetUserActions.update(getInitialContext(), getUserId(), (PrimaryKey) editorData.getPrimaryKey(), editorData.getUserModelElementAccess(), editorData.isDeployForms(), true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e.getMessage());
		} finally {
			timer.logInfo("update", new StringBuilder().append(pk == null ? "All" : pk).append(" ").append(counts).toString());
		}
	}

	public void doImport(List<String[]> list) throws ValidationException {
		StringBuilder message = new StringBuilder();
		int errorCount = 0;
		boolean errorFlg = false;
		BudgetUserDAO dao = new BudgetUserDAO();
		StructureElementDAO sedao = new StructureElementDAO();
		UserDAO userdao = new UserDAO();
		EntityList modelInfo = dao.getModelsAndRAHierarchies();
		EntityList userInfo = userdao.getAllUsers();
		try {
			String entityType = null;
			String entityVisId = null;
			String entityIntent = null;
			EntityRef entityRef = null;
			List updates = new ArrayList();
			for (int row = 0; row < list.size(); row++) {
				String[] object = (String[]) list.get(row);
				if (object != null) {
					if (object.length != 0) {
						if (object.length == 3) {
							if (entityType != null) {
								String counts = BudgetUserActions.update(getInitialContext(), getUserId(), (PrimaryKey) entityRef.getPrimaryKey(), updates, false, entityIntent.equalsIgnoreCase("Replace"));
								mLog.info("doImport", new StringBuilder().append(entityType).append(" ").append(entityVisId).append(" ").append(entityIntent).append(": ").append(counts).toString());
							}

							entityType = object[0];
							entityVisId = object[1];
							entityIntent = object[2];
							updates = new ArrayList();

							if (entityType.equalsIgnoreCase("model")) {
								entityRef = findModel(entityVisId, modelInfo);
								if (entityRef == null) {
									message.append(new StringBuilder().append("row ").append(row + 1).append(" - model ").append(entityVisId).append(" does not exist").append("\n").toString());
									errorCount++;
									errorFlg = true;
								}

							} else {
								entityRef = findUser(entityVisId, userInfo);
								if (entityRef == null) {
									message.append(new StringBuilder().append("row ").append(row + 1).append(" - user ").append(entityVisId).append(" does not exist").append("\n").toString());
									errorCount++;
									errorFlg = true;
								}
							}

						} else {
							String detailVisId = object[1];
							String seVisId = object[2];
							String readOnly = object[3];
							EntityRef thisEntityRef = null;

							if (!entityType.equalsIgnoreCase("model")) {
								thisEntityRef = findModel(detailVisId, modelInfo);
								if (thisEntityRef == null) {
									message.append(new StringBuilder().append("row ").append(row + 1).append(" - model ").append(detailVisId).append(" does not exist").append("\n").toString());
									errorCount++;
									errorFlg = true;
								}

							} else {
								thisEntityRef = findUser(detailVisId, userInfo);
								if (thisEntityRef == null) {
									message.append(new StringBuilder().append("row ").append(row + 1).append(" - user ").append(detailVisId).append(" does not exist").toString());
									errorCount++;
									errorFlg = true;
								}

							}

							int dimensionId = ((DimensionPK) mDimensionRef.getPrimaryKey()).getDimensionId();
							EntityList elements = sedao.getSearchTree(dimensionId, seVisId, new Integer(0));
							if (elements.getNumRows() == 0) {
								message.append(new StringBuilder().append("row ").append(row + 1).append(" - responsibility area ").append(seVisId).append(" does not exist").append("\n").toString());
								errorCount++;
								errorFlg = true;
							}

							if (errorCount == 10) {
								message.append("validation terminated due to number of errors\n");
								throw new ValidationException(message.toString());
							}

							if (!errorFlg) {
								UserModelElementAssignment ea = new UserModelElementAssignmentImpl(entityType.equalsIgnoreCase("model") ? entityRef : thisEntityRef, entityType.equalsIgnoreCase("model") ? thisEntityRef : entityRef, (StructureElementRef) elements.getValueAt(0, "StructureElement"), "", Boolean.valueOf(readOnly.equalsIgnoreCase("Y")));

								updates.add(ea);
							}
						}
					}
				}
			}
			if (entityType != null) {
				String counts = BudgetUserActions.update(getInitialContext(), getUserId(), (PrimaryKey) entityRef.getPrimaryKey(), updates, false, entityIntent.equalsIgnoreCase("Replace"));
				mLog.info("doImport", new StringBuilder().append(entityType).append(" ").append(entityVisId).append(" ").append(entityIntent).append(": ").append(counts).toString());
			}

		} catch (ValidationException ve) {
			throw ve;
		} catch (Exception e) {
			message.append("An error occured during import: ");
			message.append(" because : ");
			message.append(e.getMessage());
			message.append("\n");
			errorCount++;
		}

		if (errorCount > 0)
			throw new ValidationException(message.toString());
	}

	private void valdateImport() {
	}

	private ModelRef findModel(String visId, EntityList modelInfo) {
		for (int i = 0; i < modelInfo.getNumRows(); i++) {
			ModelRef modelRef = (ModelRef) modelInfo.getValueAt(i, "Model");
			if (visId.equals(modelRef.getNarrative())) {
				mDimensionRef = ((DimensionRef) modelInfo.getValueAt(i, "Dimension"));
				return modelRef;
			}
		}
		return null;
	}

	private UserRef findUser(String visId, EntityList userInfo) {
		for (int i = 0; i < userInfo.getNumRows(); i++) {
			UserRef userRef = (UserRef) userInfo.getValueAt(i, "User");
			if (visId.equals(userRef.getNarrative())) {
				return userRef;
			}
		}
		return null;
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

	private UserAccessor getUserAccessor() throws Exception {
		if (mUserAccessor == null) {
			mUserAccessor = new UserAccessor(getInitialContext());
		}
		return mUserAccessor;
	}

	private void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType) {
		try {
			JmsConnection jms = new JmsConnectionImpl(getInitialContext(), 3, "entityEventTopic");
			jms.createSession();
			EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, getClass().getName());
			mLog.debug("update", new StringBuilder().append("sending event message: ").append(em.toString()).toString());
			jms.send(em);
			jms.closeSession();
			jms.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}