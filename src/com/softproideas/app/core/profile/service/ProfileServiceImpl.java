/*******************************************************************************
 * Copyright Â©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
package com.softproideas.app.core.profile.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.user.DataEntryProfileEditor;
import com.cedar.cp.api.user.DataEntryProfileEditorSession;
import com.cedar.cp.api.user.DataEntryProfilesProcess;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.de.profiles.DataEntryProfileVO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
//import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
import com.softproideas.app.core.form.mapper.FormMapper;
import com.softproideas.app.core.form.model.FormDTO;
import com.softproideas.app.core.profile.dao.ProfileDao;
import com.softproideas.app.core.profile.dao.ProfileDaoImpl;
import com.softproideas.app.core.profile.mapper.ProfileMapper;
import com.softproideas.app.core.profile.model.ProfileDTO;
import com.softproideas.app.core.profile.model.UserIdXmlForm;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;

/**
 * <p>TODO: Brakuje opisu do klasy</p>
 * 
 * @author Å�ukasz PuÅ‚a, Szymon Walczak
 * @email lukasz.pula@softproideas.com, szymon.walczak@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

    private static Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;
    public ProfileServiceImpl(){
    }
    public void Init(CPContext context){
    	cpContextHolder = new CPContextHolder();
    	cpContextHolder.init(context);
    }
    @Autowired
    ProfileDao profileDao;

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.core.profile.service.ProfileService#browseProfiles(int, int, int)
     */
    @Override
    public List<ProfileDTO> browseProfiles(int modelId, int budgetCycleId, int selectedStructureElementId) throws ServiceException {
        try {
            EntityList profilesList = cpContextHolder.getDataEntryProfilesProcess().getAllDataEntryProfilesForUser(cpContextHolder.getUserId(), modelId, budgetCycleId);
            EntityList formsList = browseXmlFormsForUser(modelId, budgetCycleId);

            // if we haven't got any profiles, create profiles from default xml forms
            if (profilesList == null || profilesList.getNumRows() < 1) {
                createProfilesFromXmlForms(modelId, budgetCycleId, formsList);
                profilesList = cpContextHolder.getDataEntryProfilesProcess().getAllDataEntryProfilesForUser(cpContextHolder.getUserId(), modelId, budgetCycleId);
                if (profilesList == null || profilesList.getNumRows() < 1) {
                    // user hasn't got any access to any profile
                    return new ArrayList<ProfileDTO>();
                }
            }

            List<ProfileDTO> profiles = ProfileMapper.mapToDTO(profilesList);
            List<FormDTO> forms = FormMapper.mapToDTO(formsList);

            // find default form
            int defaultFormId = -1;
            for (FormDTO form: forms) {
                if (form.isDefaultForm()) {
                    defaultFormId = form.getFormId();
                    break;
                }
            }

            boolean haveDefaultProfile = false;
            for (ProfileDTO profileDTO: profiles) {
                if (profileDTO.getFormId() == defaultFormId) {
                    profileDTO.setDefaultProfile(true);
                    haveDefaultProfile = true;
                    break;
                }
            }

            // user has got created default profile or hasn't got permission to create that profile
            if (haveDefaultProfile || (!haveDefaultProfile && defaultFormId == -1))
                return profiles;

            // if user has got permission to xml form, but profile (related to this xml form) was not created earlier.
            createProfileFromXmlForms(modelId, budgetCycleId, formsList, defaultFormId);
            return browseProfiles(modelId, budgetCycleId, selectedStructureElementId);
        } catch (Exception e) {
            logger.error("Error during fetching profiles!", e);
            throw new ServiceException("Error during fetching profiles!", e);
        }
    }

    /**
     * Create profile related to xml form (with formId)
     */
    private void createProfileFromXmlForms(int modelId, int budgetCycleId, EntityList xmlForms, int defaultFormId) throws Exception {
        DataEntryProfileVO profile = new DataEntryProfileVO();
        profile = addBasicPropertiesToDataEntryProfileVO(profile, modelId, budgetCycleId);

        // commit profiles, update only some fields
        for (Object[] xmlForm: xmlForms.getDataAsArray()) {
            XmlFormRef xmlFormRef = (XmlFormRef) xmlForm[0];
            int formId = ((XmlFormPK) xmlFormRef.getPrimaryKey()).getXmlFormId();
            if (formId == defaultFormId) {
                ProfileMapper.mapFormPropertiesToVO(profile, xmlForm);
                profile.setDataType((String) xmlForm[4]);
                profile.setAutoOpenDepth(1);
                commitNewDataEntryProfile(profile);
                break;
            }
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.softproideas.app.core.profile.service.ProfileService#browseFormList(int, int)
     */
    @Override
    public List<FormDTO> browseFormList(int modelId, int budgetCycleId) throws ServiceException {
        try {
            EntityList xmlForms = browseXmlFormsForUser(modelId, budgetCycleId);
            return FormMapper.mapToDTO(xmlForms);
        } catch (Exception e) {
            logger.error("Error during fetching profiles!", e);
            throw new ServiceException("Error during fetching profiles!", e);
        }
    }

    /* (non-Javadoc)
     * @see com.softproideas.app.core.profile.service.ProfileService#deleteProfile(int, int)
     */
    @Override
    public void deleteProfilesForForm(int flatFormId, List<Integer> userId, boolean isInUserTable) throws ServiceException {
        try {
            profileDao.deleteProfilesForForm(flatFormId, userId, isInUserTable);
        } catch (Exception e) {
            logger.error("Error during deleting profiles!", e);
            throw new ServiceException("Error during deleting profiles!", e);
        }
    }

    /* (non-Javadoc)
     * @see com.softproideas.app.core.profile.service.ProfileService#insertProfile(int, java.util.List)
     */
    @Override
    public void insertProfileForForm(int flatFormId, List<Integer> userIdList, boolean isInUserTable) throws ServiceException {
        try {
            profileDao.insertProfileForForm(flatFormId, userIdList, isInUserTable);
        } catch (DaoException e) {
            logger.error("Error during creating profile!", e);
            throw new ServiceException("Error during creating profile!", e);
        }
    }

    @Override
    public void deleteProfiles(List<Integer> modelId, List<Integer> userId) throws ServiceException {
        try {
            profileDao.deleteProfiles(modelId, userId);
        } catch (Exception e) {
            logger.error("Error during deleting profiles!", e);
            throw new ServiceException("Error during deleting profiles!", e);
        }
    }
    
    /**
     * Browse all available xml forms for logged user
     */
    private EntityList browseXmlFormsForUser(int modelId, int budgetCycleId) throws Exception {
        boolean hasDesignModeSecurity = cpContextHolder.getUserContext().hasSecurity("WEB_PROCESS.ViewDesignForms");
        EntityList xmlForms = cpContextHolder.getXmlFormsProcess().getAllFinanceXmlFormsForModelAndUser(modelId, budgetCycleId, cpContextHolder.getUserId(), hasDesignModeSecurity);
        return xmlForms;
    }

    /**
     * Creates profiles related to passed xml forms
     */
    private void createProfilesFromXmlForms(int modelId, int budgetCycleId, EntityList xmlForms) throws Exception {
        DataEntryProfileVO profile = new DataEntryProfileVO();
        profile = addBasicPropertiesToDataEntryProfileVO(profile, modelId, budgetCycleId);

        // commit profiles, update only some fields
        for (Object[] xmlForm: xmlForms.getDataAsArray()) {
            ProfileMapper.mapFormPropertiesToVO(profile, xmlForm);
            profile.setDataType((String) xmlForm[4]);
            profile.setAutoOpenDepth(1);
            commitNewDataEntryProfile(profile);
        }
    }

    /**
     * Sets basic properties for profileVO
     */
    private DataEntryProfileVO addBasicPropertiesToDataEntryProfileVO(DataEntryProfileVO profileVO, int modelId, int budgetCycleId) {
        if (profileVO == null)
            profileVO = new DataEntryProfileVO();

        profileVO.setModelId(modelId);
        profileVO.setBudgetCycleId(budgetCycleId);
        profileVO.setUserId(cpContextHolder.getUserId());
        profileVO.setUserRef(cpContextHolder.getUserContext().getUserRef());
        return profileVO;
    }

    /**
     * Commit a new profile to DB
     */
    private void commitNewDataEntryProfile(DataEntryProfileVO profile) throws Exception {
        DataEntryProfilesProcess process = cpContextHolder.getDataEntryProfilesProcess();
        DataEntryProfileEditorSession session = process.getDataEntryProfileEditorSession(profile.getPrimaryKey());
        DataEntryProfileEditor editor = session.getDataEntryProfileEditor();

        ProfileMapper.mapToEditor(editor, profile);

        editor.commit();
        session.commit(false);
        process.terminateSession(session);
    }
    @Override
    public void createProfiles(Integer modelId, Integer userId) throws SQLException, ServiceException{
        List<UserIdXmlForm> userIdXmlForms = new ArrayList<UserIdXmlForm>();        
        try {
            userIdXmlForms = profileDao.getXmlFormsForCreateMissingProfiles(modelId, userId);            
            profileDao.createProfilesByModel(userIdXmlForms, modelId);
        } catch (DaoException e) {
            logger.error("Error during deploy profiles by modelId!", e);
            throw new ServiceException("Error during deploy profiles by modelId!", e);
        } catch (SQLException e) {
            logger.error("Error during deploy profiles by modelId!", e);
            throw new ServiceException("Error during deploy profiles by modelId!", e);
        }
    }

    /* (non-Javadoc)
     * @see com.softproideas.app.core.profile.service.ProfileService#deleteProfilesForUser(java.util.List, int)
     */
    @Override
    public void deleteProfilesForUser(List<Integer> flatFormIdList, int userId) throws ServiceException {
        try {
            profileDao.deleteProfilesForUser(flatFormIdList, userId);
        } catch (Exception e) {
            logger.error("Error during deleting profiles!", e);
            throw new ServiceException("Error during deleting profiles!", e);
        }
    }

    /* (non-Javadoc)
     * @see com.softproideas.app.core.profile.service.ProfileService#insertProfileForUser(java.util.List, int)
     */
    @Override
    public void insertProfileForUser(List<Integer> flatFormIdList, int userId, List<Integer> modelsIdsFlatFormsInsertedBefore) throws ServiceException {
        try {
            profileDao.insertProfileForUser(flatFormIdList, userId, modelsIdsFlatFormsInsertedBefore);
        } catch (DaoException e) {
            logger.error("Error during creating profile!", e);
            throw new ServiceException("Error during creating profile!", e);
        }
    }

    /* (non-Javadoc)
     * @see com.softproideas.app.core.profile.service.ProfileService#deleteProfilesForBudgetCycle(java.util.List)
     */
    @Override
    public void deleteProfilesForBudgetCycle(List<Integer> flatFormIdList, int budgetCycleId) throws ServiceException {
        try {
            profileDao.deleteProfilesForBudgetCycle(flatFormIdList, budgetCycleId);
        } catch (Exception e) {
            logger.error("Error during deleting profiles!", e);
            throw new ServiceException("Error during deleting profiles!", e);
        }
    }

    /* (non-Javadoc)
     * @see com.softproideas.app.core.profile.service.ProfileService#insertProfileForBudgetCycle(java.util.List, int)
     */
    @Override
    public void insertProfileForBudgetCycle(List<Integer> flatFormIdList, BudgetCycleDetailsDTO budgetCycle) throws ServiceException {
        try {
            profileDao.insertProfileForBudgetCycle(flatFormIdList, budgetCycle);
        } catch (DaoException e) {
            logger.error("Error during creating profile!", e);
            throw new ServiceException("Error during creating profile!", e);
        }
    }

	@Override
	public void deleteAllProfilesForUser(int userId) throws ServiceException {
        try {
        	profileDao.deleteAllProfilesForUser(userId);
        } catch (Exception e) {
            logger.error("Error during deleting profiles!", e);
            throw new ServiceException("Error during deleting profiles!", e);
        }
	}

    @Override
    public void deleteAllProfilesForBudgetCycle(int budgetCycleId) throws ServiceException {
        try {
            profileDao.deleteAllProfilesForBudgetCycle(budgetCycleId);
        } catch (Exception e) {
            logger.error("Error during deleting profiles!", e);
            throw new ServiceException("Error during deleting profiles!", e);
        }
    }
}
