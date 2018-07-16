package com.cedar.cp.ejb.impl.budgetlocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.impl.model.BudgetUserDAO;
import com.cedar.cp.util.common.JmsConnectionImpl;
import com.cedar.cp.util.task.TaskMessageFactory;

public class BudgetUserActions {

	public static String update(InitialContext context, int maintainingUserId, PrimaryKey key, List<UserModelElementAssignment> updates, boolean isDeployForms, boolean replaceMode) throws Exception {
		return update(context, maintainingUserId, key, updates, isDeployForms, replaceMode, null);
	}

	public static String update(InitialContext context, int maintainingUserId, PrimaryKey key, List<UserModelElementAssignment> updates, boolean isDeployForms, boolean replaceMode, UserRef newUserRef) throws Exception {
		BudgetUserDAO dao = new BudgetUserDAO();
		List modelPKs = new ArrayList();

		int deleteCount = 0;
		int insertCount = 0;
		int updateCount = 0;

		List curr = dao.getRespAreaAccess(key);

		if (replaceMode) {
			for (int i = 0; i < curr.size(); i++) {
				UserModelElementAssignment ea = (UserModelElementAssignment) curr.get(i);
				UserModelElementAssignment foundea = find(updates, ea);
				if (foundea == null) {
					dao.delete((UserRef) ea.getUser(), (ModelRef) ea.getModel(), (StructureElementRef) ea.getStructureElementRef());
					deleteCount++;
					if (!modelPKs.contains(ea.getModel().getPrimaryKey())) {
						modelPKs.add((ModelPK) ea.getModel().getPrimaryKey());
					}
				}
			}
		}

		Map formDeployment = null;
		ModelRef currmodel = null;
		for (int i = 0; i < updates.size(); i++) {
			UserModelElementAssignment ea = (UserModelElementAssignment) updates.get(i);

			if ((currmodel == null) || (!currmodel.equals(ea.getModel()))) {
				if (currmodel != null) {
					issueFormdeploymentTask(maintainingUserId, isDeployForms, currmodel, formDeployment);
				}
				currmodel = (ModelRef) ea.getModel();
				formDeployment = new HashMap();
			}

			UserModelElementAssignment foundea = find(curr, ea);
			if (foundea == null) {
				if (newUserRef == null)
					dao.insert((UserRef) ea.getUser(), (ModelRef) ea.getModel(), (StructureElementRef) ea.getStructureElementRef(), ea.getReadOnly());
				else
					dao.insert(newUserRef, (ModelRef) ea.getModel(), (StructureElementRef) ea.getStructureElementRef(), ea.getReadOnly());
				insertCount++;
				if (!modelPKs.contains(ea.getModel().getPrimaryKey())) {
					modelPKs.add((ModelPK) ea.getModel().getPrimaryKey());
				}
				if (isDeployForms) {
					int updSeid = ((StructureElementPK) ea.getStructureElementRef().getPrimaryKey()).getStructureElementId();
					List l = (List) formDeployment.get(Integer.valueOf(updSeid));
					if (l == null) {
						l = new ArrayList();
					}
					l.add(Integer.valueOf(((UserPK) ea.getUser().getPrimaryKey()).getUserId()));
					formDeployment.put(Integer.valueOf(updSeid), l);
				}
			}

		}

		if (currmodel != null) {
			issueFormdeploymentTask(maintainingUserId, isDeployForms, currmodel, formDeployment);
		}

		for (int i = 0; i < updates.size(); i++) {
			UserModelElementAssignment ea = (UserModelElementAssignment) updates.get(i);
			UserModelElementAssignment foundea = find(curr, ea);
			if (foundea != null) {
				if (!ea.getReadOnly().equals(foundea.getReadOnly())) {
					dao.update((UserRef) ea.getUser(), (ModelRef) ea.getModel(), (StructureElementRef) ea.getStructureElementRef(), ea.getReadOnly());
					updateCount++;
					if (!modelPKs.contains(ea.getModel().getPrimaryKey())) {
						modelPKs.add((ModelPK) ea.getModel().getPrimaryKey());
					}
				}
			}
		}
		for (Object modelPK : modelPKs.toArray()) {
			sendEntityEventMessage("Model", (ModelPK) modelPK, 3, context);
		}
		return "deletes=" + deleteCount + ", inserts=" + insertCount + ", updates=" + updateCount;
	}

	private static void issueFormdeploymentTask(int maintainingUserId, boolean isDeployForms, ModelRef model, Map<Integer, List<Integer>> formDeployment) throws Exception {
		if ((isDeployForms) && (formDeployment.size() > 0)) {
			BRAFormDeploymentTaskRequest request = new BRAFormDeploymentTaskRequest();
			request.setModelRef(model);
			request.setNewUsers(formDeployment);
			TaskMessageFactory.issueNewTask(new InitialContext(), false, request, maintainingUserId);
		}
	}

	private static UserModelElementAssignment find(List<UserModelElementAssignment> el, UserModelElementAssignment targ) {
		for (int i = 0; i < el.size(); i++) {
			UserModelElementAssignment ea = (UserModelElementAssignment) el.get(i);
			if ((ea.getModel().equals(targ.getModel())) && (ea.getUser().equals(targ.getUser())) && (ea.getStructureElementRef().equals(targ.getStructureElementRef()))) {
				return ea;
			}
		}
		return null;
	}

	private static void sendEntityEventMessage(String tableName, PrimaryKey pk, int changeType, InitialContext context) {
		try {
			JmsConnection jms = new JmsConnectionImpl(context, 3, "entityEventTopic");
			jms.createSession();
			EntityEventMessage em = new EntityEventMessage(tableName, pk, changeType, BudgetUserActions.class.getName());
			jms.send(em);
			jms.closeSession();
			jms.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}