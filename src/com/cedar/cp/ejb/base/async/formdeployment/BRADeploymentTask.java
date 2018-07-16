package com.cedar.cp.ejb.base.async.formdeployment;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.user.DataEntryProfileEditor;
import com.cedar.cp.api.user.DataEntryProfileEditorSession;
import com.cedar.cp.api.user.DataEntryProfilesProcess;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCycleLinkPK;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.UsersForModelAndElementELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.impl.budgetlocation.BRAFormDeploymentTaskRequest;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileEVO;
import com.cedar.cp.ejb.impl.user.UserEVO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.naming.InitialContext;

public class BRADeploymentTask extends BaseDeploymentTask {
	public String getEntityName() {
		return "BRADeploymentTask";
	}

	public void runUnitOfWork(InitialContext initialContext) throws Exception {
		setInitialContext(initialContext);
		BRAFormDeploymentTaskRequest request = (BRAFormDeploymentTaskRequest) getRequest();
		if (getCheckpoint() == null)
			buildProfiles(request);
	}

	private void buildProfiles(BRAFormDeploymentTaskRequest request) throws ValidationException {

		Map m = request.getNewUsers();
		for (Iterator i$ = m.keySet().iterator(); i$.hasNext();) {
			int structureElementId = ((Integer) i$.next()).intValue();
			
			ModelRef modelRef = (ModelRef) request.getModelRef();
			ModelPK modelPK = (ModelPK) modelRef.getPrimaryKey();
			int modelId = modelPK.getModelId();
			UsersForModelAndElementELO elo = getBudgetUserDAO().getUsersForModelAndElement(modelId, structureElementId);
			BudgetCycleDAO bcDao = new BudgetCycleDAO();
			
			while (elo.hasNext()) {
				elo.next();
				elo.getUserId();
				
				UserPK pk = new UserPK(elo.getUserId());
				UserEVO userEVO;
				Collection<DataEntryProfileEVO> existingDEProfiles = null;
				try {
					userEVO = getUserAccessor().getDetails(pk, "<2>");
					existingDEProfiles = userEVO.getDataEntryProfiles();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				boolean hasDesignModeSecurity = getCPConnection().getUserContext().hasSecurity("WEB_PROCESS.ViewDesignForms");
				Object[] bc = bcDao.getBudgetCyclesForModel(modelId).getValues("BudgetCycle");				
				
				for (Object budgetCycle : bc) {										
					int budgetCycleId = ((BudgetCycleCK) ((BudgetCycleRef) budgetCycle).getPrimaryKey()).getBudgetCyclePK().getBudgetCycleId();
					EntityList profiles = getCPConnection().getXmlFormsProcess().getAllFinanceXmlFormsForModelAndUser(modelId, budgetCycleId, elo.getUserId(), hasDesignModeSecurity);					
					DataEntryProfilesProcess process = getCPConnection().getDataEntryProfilesProcess();
					
					for (Object[] form : profiles.getDataAsArray()) {
						
						boolean doAdd = true;						
						XmlFormRef formRef = (XmlFormRef) form[0];
						int xmlFormId = ((XmlFormPK) formRef.getPrimaryKey()).getXmlFormId();
						String formDescription = ((XmlFormRef) form[0]).getNarrative();
						
						for (DataEntryProfileEVO existingProfile : existingDEProfiles) {									
							if (existingProfile.getXmlformId() == xmlFormId) {
								doAdd = false;
								break;
							}
						}
						
						if (doAdd) {							
							DataEntryProfileVO profile = new DataEntryProfileVO();
							DataEntryProfileEditorSession session = process.getDataEntryProfileEditorSession(profile.getPrimaryKey());
							DataEntryProfileEditor editor = session.getDataEntryProfileEditor();
							String formDataType = (String) form[4];
							
							editor.setDescription(formDescription);
							editor.setUserId(elo.getUserId());
							editor.setAutoOpenDepth(1);
							editor.setUserRef(new UserRefImpl(new UserPK(elo.getUserId()), ""));
							editor.setVisId(formRef.getDisplayText());
							editor.setXmlformId(xmlFormId);
							editor.setXmlFormRef(formRef);
							editor.setModelId(modelId);
							editor.setBudgetCycleId(budgetCycleId);
							editor.setDataType(formDataType);
							editor.commit();
							session.commit(false);
							process.terminateSession(session);
							
							log("    adding profile " + formDescription + " for user " + elo.getUserId());							
						} else {
							log("    profile " + formDescription + " for user " + elo.getUserId() + " already exists");
						}
					
					}
				}
			}
		}
	}
}