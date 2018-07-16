package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.JmsConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.VersionValidationException;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.role.RoleRef;
import com.cedar.cp.api.systemproperty.SystemPropertyRef;
import com.cedar.cp.dto.base.AbstractELO;
import com.cedar.cp.dto.base.EntityEventMessage;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.passwordhistory.UserPasswordHistoryELO;
import com.cedar.cp.dto.role.AllRolesELO;
import com.cedar.cp.dto.role.RoleRefImpl;
import com.cedar.cp.dto.user.UserEditorSessionCSO;
import com.cedar.cp.dto.user.UserEditorSessionSSO;
import com.cedar.cp.dto.user.UserImpl;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserPreferenceImpl;
import com.cedar.cp.dto.user.UserRolePK;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.XmlFormRefImpl;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyAccessor;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyEVO;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.model.BudgetUserDAO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.SecurityGroupDAO;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryAccessor;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryEVO;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationUsersDAO;
import com.cedar.cp.ejb.impl.reset.ChallengeQuestionEVO;
import com.cedar.cp.ejb.impl.role.RoleAccessor;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormDAO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormUserLinkDAO;
import com.cedar.cp.util.Cryptography;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.common.JmsConnectionImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.tree.DefaultMutableTreeNode;

public class UserEditorSessionSEJB extends AbstractSession {
    private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<0><1><2><3><4><5>";
    private static final String DEPENDANTS_FOR_INSERT = "";
    private static final String DEPENDANTS_FOR_COPY = "<0><1><2><3><4><5>";
    private static final String DEPENDANTS_FOR_UPDATE = "<0><1><2><3><4><5>";
    private static final String DEPENDANTS_FOR_DELETE = "<0><1><2><3><4><5>";
    private int mNewId = 0;
    private transient ModelAccessor mModelAccessor;
    private transient DimensionAccessor mDimensionAccessor;
    private boolean isValidPassword = false;
    private AuthenticationPolicyEVO mActiveAuthenticationPolicy;
    private transient RoleAccessor mRoleAccessor;
    private transient Log mLog = new Log(getClass());
    private transient SessionContext mSessionContext;
    private transient UserAccessor mUserAccessor;
    private UserEditorSessionSSO mSSO;
    private UserPK mThisTableKey;
    private UserEVO mUserEVO;

    public UserEditorSessionSSO getItemData(int userId, Object paramKey) throws ValidationException, EJBException {
        setUserId(userId);

        if (mLog.isDebugEnabled()) {
            mLog.debug("getItemData", paramKey);
        }
        Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
        mThisTableKey = ((UserPK) paramKey);
        try {
            mUserEVO = getUserAccessor().getDetails(mThisTableKey, "<0><1><2><3><4><5>");

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
        mSSO = new UserEditorSessionSSO();

        UserImpl editorData = buildUserEditData(mThisTableKey);

        // Security - load data
        List assignments = new ArrayList();
        List<UserModelElementAssignment> existing = new BudgetUserDAO().getRespAreaAccess(mThisTableKey);
        for (UserModelElementAssignment element: existing) {
            Object[] userSecurityAssignment = new Object[3];
            userSecurityAssignment[0] = ((ModelPK) element.getModel().getPrimaryKey()).getModelId();
            userSecurityAssignment[1] = ((UserPK) element.getUser().getPrimaryKey()).getUserId();
            userSecurityAssignment[2] = element.getStructureElementRef();
            assignments.add(userSecurityAssignment);
        }
        editorData.setUserAssignments(assignments);

        // AdminApp - load data
        EntityList roles = getCPConnection().getListHelper().getAllHiddenRolesForUser(mThisTableKey.getUserId());
        List<RoleRef> adminAppRoles = new ArrayList<RoleRef>();
        for (Object data: roles.getValues("Role")) {
            adminAppRoles.add((RoleRef) data);
        }
        editorData.setUserAdminAppRoles(adminAppRoles);

        // XmlForms - load data
        EntityList existingXmlFormEntityList = (EntityList) new XmlFormDAO().getXcellXmlFormsForUser(mThisTableKey.getUserId());
        List<List<Object>> existingXmlFormList = existingXmlFormEntityList.getDataAsList();
        editorData.setUserXmlForms(existingXmlFormList);

        mSSO.setEditorData(editorData);
    }

    private UserImpl buildUserEditData(Object thisKey) throws Exception {
        UserImpl editorData = new UserImpl(thisKey);

        editorData.setName(mUserEVO.getName());
        editorData.setFullName(mUserEVO.getFullName());
        editorData.setEMailAddress(mUserEVO.getEMailAddress());
        editorData.setPasswordBytes(mUserEVO.getPasswordBytes());
        editorData.setPasswordDate(mUserEVO.getPasswordDate());
        editorData.setChangePassword(mUserEVO.getChangePassword());
        editorData.setResetStrikes(mUserEVO.getResetStrikes());
        editorData.setUserReadOnly(mUserEVO.getUserReadOnly());
        editorData.setUserDisabled(mUserEVO.getUserDisabled());
        editorData.setPasswordNeverExpires(mUserEVO.getPasswordNeverExpires());
        editorData.setExternalSystemUserName(mUserEVO.getExternalSystemUserName());
        editorData.setLogonAlias(mUserEVO.getLogonAlias());
        editorData.setNewFeaturesEnabled(mUserEVO.isNewFeaturesEnabled());
        editorData.setButtonsVisible(mUserEVO.areButtonsVisible());
        editorData.setShowBudgetActivity(mUserEVO.isShowBudgetActivity());
        editorData.setNewView(mUserEVO.isNewView());
        editorData.setVersionNum(mUserEVO.getVersionNum());
        editorData.setRoadMapAvailable(mUserEVO.getRoadMapAvailable());
        editorData.setMobilePIN(mUserEVO.getMobilePIN());
        editorData.setSalt(mUserEVO.getSalt());
        
        completeUserEditData(editorData);

        return editorData;
    }

    private void completeUserEditData(UserImpl editorData) throws Exception {
        String storedPassword = mUserEVO.getPasswordBytes();
        if (storedPassword != null) {
            String plainPassword = Cryptography.decrypt(storedPassword, "fc30");
            editorData.setPassword(plainPassword);
        }

        List roles = new ArrayList();
        RoleAccessor roleAccessor = new RoleAccessor(getInitialContext());
        EntityList userRoles = roleAccessor.getAllRolesForUser(mUserEVO.getUserId());
        Object[][] roleRefs = userRoles.getDataAsArray();
        for (Object[] role: roleRefs) {
            roles.add((RoleRef) role[0]);
        }
        editorData.setRoles(roles);

        Map userPreferences = new HashMap();
        for (UserPreferenceEVO upEvo: mUserEVO.getUserPreferencesMap().values()) {
            UserPreferenceImpl up = new UserPreferenceImpl(upEvo.getPK());
            up.setContentModified(false);
            up.setHelpId(upEvo.getHelpId());
            up.setPrefName(upEvo.getPrefName());
            up.setPrefType(upEvo.getPrefType());
            up.setPrefValue(upEvo.getPrefValue());
            up.setUserRef(mUserEVO.getEntityRef());
            up.setVersionNum(upEvo.getVersionNum());
            userPreferences.put(up.getPrefName(), up);
        }
        editorData.setUserPreferences(userPreferences);
    }

    public UserEditorSessionSSO getNewItemData(int userId) throws EJBException {
        mLog.debug("getNewItemData");

        setUserId(userId);
        Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
        try {
            mSSO = new UserEditorSessionSSO();

            UserImpl editorData = new UserImpl(null);

            // completeGetNewItemData(editorData);

            mSSO.setEditorData(editorData);

            return mSSO;
        } catch (EJBException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e.getMessage(), e);
        } finally {
            setUserId(0);
            if (timer != null)
                timer.logInfo("getNewItemData", "");
        }
    }

    // private void completeGetNewItemData(UserImpl editorData)
    // throws Exception
    // {
    // }

    public UserPK insert(UserEditorSessionCSO cso) throws ValidationException, EJBException {
        mLog.debug("insert");

        Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

        setUserId(cso.getUserId());

        UserImpl editorData = cso.getEditorData();
        try {
            AuthenticationPolicyEVO authenticationPolicy = getActiveAuthenticationPolicy();
            boolean isAuthenticateViaInternal = authenticationPolicy.getAuthenticationTechnique() == 1;

            mUserEVO = new UserEVO();
            String password;
            if (isAuthenticateViaInternal) {
                password = editorData.getPassword();
                if ((password != null) && (password.length() > 0)) {
                    validateNewPassword(mUserEVO, null, password, false);
                    mUserEVO.setPasswordBytes(editorData.getPasswordBytes());
                }
            }
            mUserEVO.setName(editorData.getName());
            mUserEVO.setFullName(editorData.getFullName());
            mUserEVO.setEMailAddress(editorData.getEMailAddress());
            mUserEVO.setPasswordBytes(editorData.getPasswordBytes());
            mUserEVO.setPasswordDate(editorData.getPasswordDate());
            mUserEVO.setChangePassword(editorData.isChangePassword());
            mUserEVO.setResetStrikes(editorData.getResetStrikes());
            mUserEVO.setUserReadOnly(editorData.isUserReadOnly());
            mUserEVO.setNewFeaturesEnabled(editorData.isNewFeaturesEnabled());
            mUserEVO.setButtonsVisible(editorData.areButtonsVisible());
            mUserEVO.setShowBudgetActivity(editorData.isShowBudgetActivity());
            mUserEVO.setNewView(editorData.isNewView());
            mUserEVO.setUserDisabled(editorData.isUserDisabled());
            mUserEVO.setPasswordNeverExpires(editorData.isPasswordNeverExpires());
            mUserEVO.setExternalSystemUserName(editorData.getExternalSystemUserName());
            mUserEVO.setLogonAlias(editorData.getLogonAlias());
            mUserEVO.setRoadMapAvailable(editorData.getRoadMapAvailable());
            mUserEVO.setMobilePIN(editorData.getMobilePIN());
            mUserEVO.setSalt(editorData.getSalt());

            updateUserRelationships(editorData);

            completeInsertSetup(editorData);

            validateInsert();

            mUserEVO = getUserAccessor().create(mUserEVO);

            insertIntoAdditionalTables(editorData, true);

            sendEntityEventMessage("User", mUserEVO.getPK(), 1);

            return mUserEVO.getPK();
        } catch (ValidationException ve) {
            throw new EJBException(ve);
        } catch (EJBException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e.getMessage(), e);
        } finally {
            setUserId(0);
            if (timer != null)
                timer.logInfo("insert", "");
        }
    }

    private void updateUserRelationships(UserImpl editorData) throws ValidationException {
    }

    private void completeInsertSetup(UserImpl editorData) throws Exception {
        if (editorData.getPasswordDate() == null) {
            Timestamp tstmp = new Timestamp(System.currentTimeMillis());
            mUserEVO.setPasswordDate(tstmp);
        }

        if (getActiveAuthenticationPolicy().getAuthenticationTechnique() == 1) {
            String password = editorData.getPassword();

            password = Cryptography.encrypt(password, "fc30");
            mUserEVO.setPasswordBytes(password);
        }
        adminAppPrepare(editorData);
        rolesUpdate(editorData, false);
        mUserEVO.setUserPreferences(editorData.getUserPreferences().entrySet());
    }

    private void insertIntoAdditionalTables(UserImpl editorData, boolean isInsert) throws Exception {
        saveSecurityAssignmentChanges(editorData, mUserEVO.getPK());
        saveXmlFormsChanges(editorData, mUserEVO.getPK());
    }

    private void validateInsert() throws ValidationException {
    }

    public UserPK copy(UserEditorSessionCSO cso) throws ValidationException, EJBException {
        mLog.debug("copy");

        Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

        setUserId(cso.getUserId());
        UserImpl editorData = cso.getEditorData();

        mThisTableKey = ((UserPK) editorData.getPrimaryKey());
        try {
            UserEVO origUser = getUserAccessor().getDetails(mThisTableKey, "<0><1><2><3><4><5>");

            mUserEVO = origUser.deepClone();

            mUserEVO.setName(editorData.getName());
            mUserEVO.setFullName(editorData.getFullName());
            mUserEVO.setEMailAddress(editorData.getEMailAddress());
            mUserEVO.setPasswordBytes(editorData.getPasswordBytes());
            mUserEVO.setPasswordDate(editorData.getPasswordDate());
            mUserEVO.setChangePassword(editorData.isChangePassword());
            mUserEVO.setResetStrikes(editorData.getResetStrikes());
            mUserEVO.setUserReadOnly(editorData.isUserReadOnly());
            mUserEVO.setNewFeaturesEnabled(editorData.isNewFeaturesEnabled());
            mUserEVO.setButtonsVisible(editorData.areButtonsVisible());
            mUserEVO.setShowBudgetActivity(editorData.isShowBudgetActivity());
            mUserEVO.setNewView(editorData.isNewView());
            mUserEVO.setUserDisabled(editorData.isUserDisabled());
            mUserEVO.setPasswordNeverExpires(editorData.isPasswordNeverExpires());
            mUserEVO.setExternalSystemUserName(editorData.getExternalSystemUserName());
            mUserEVO.setLogonAlias(editorData.getLogonAlias());
            mUserEVO.setVersionNum(0);
            mUserEVO.setRoadMapAvailable(editorData.getRoadMapAvailable());
            mUserEVO.setMobilePIN(editorData.getMobilePIN());
            mUserEVO.setSalt(editorData.getSalt());

            updateUserRelationships(editorData);

            completeCopySetup(editorData);

            validateCopy();

            mUserEVO.prepareForInsert();

            mUserEVO = getUserAccessor().create(mUserEVO);

            mThisTableKey = mUserEVO.getPK();

            insertIntoAdditionalTables(editorData, false);

            sendEntityEventMessage("User", mUserEVO.getPK(), 1);

            return mThisTableKey;
        } catch (ValidationException ve) {
            throw new EJBException(ve);
        } catch (EJBException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e);
        } finally {
            setUserId(0);
            if (timer != null)
                timer.logInfo("copy", mThisTableKey);
        }
    }

    private void validateCopy() throws ValidationException {
    }

    private void completeCopySetup(UserImpl editorData) throws Exception {
        Timestamp tstmp = new Timestamp(System.currentTimeMillis());
        mUserEVO.setPasswordDate(tstmp);

        String password = "password";
        password = Cryptography.encrypt(password, "fc30");
        mUserEVO.setPasswordBytes(password);

        adminAppPrepare(editorData);
        rolesUpdate(editorData, true);

        mUserEVO.setUserPreferences(new HashSet());
    }

    public void update(UserEditorSessionCSO cso) throws ValidationException, EJBException {
        mLog.debug("update");

        Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;

        setUserId(cso.getUserId());
        UserImpl editorData = cso.getEditorData();

        mThisTableKey = ((UserPK) editorData.getPrimaryKey());
        try {
            mUserEVO = getUserAccessor().getDetails(mThisTableKey, "<0><1><2><3><4><5>");

            preValidateUpdate(editorData);

            mUserEVO.setName(editorData.getName());
            mUserEVO.setFullName(editorData.getFullName());
            mUserEVO.setEMailAddress(editorData.getEMailAddress());
            mUserEVO.setPasswordBytes(editorData.getPasswordBytes());
            mUserEVO.setPasswordDate(editorData.getPasswordDate());
            mUserEVO.setChangePassword(editorData.isChangePassword());
            mUserEVO.setResetStrikes(editorData.getResetStrikes());
            mUserEVO.setUserReadOnly(editorData.isUserReadOnly());
            mUserEVO.setNewFeaturesEnabled(editorData.isNewFeaturesEnabled());
            mUserEVO.setButtonsVisible(editorData.areButtonsVisible());
            mUserEVO.setShowBudgetActivity(editorData.isShowBudgetActivity());
            mUserEVO.setNewView(editorData.isNewView());
            mUserEVO.setUserDisabled(editorData.isUserDisabled());
            mUserEVO.setPasswordNeverExpires(editorData.isPasswordNeverExpires());
            mUserEVO.setExternalSystemUserName(editorData.getExternalSystemUserName());
            mUserEVO.setLogonAlias(editorData.getLogonAlias());
            mUserEVO.setRoadMapAvailable(editorData.getRoadMapAvailable());
            mUserEVO.setMobilePIN(editorData.getMobilePIN());
            mUserEVO.setSalt(editorData.getSalt());

            if (editorData.getVersionNum() != mUserEVO.getVersionNum()) {
                throw new VersionValidationException(mThisTableKey + " expected:" + editorData.getVersionNum() + " found:" + mUserEVO.getVersionNum());
            }

            updateUserRelationships(editorData);

            completeUpdateSetup(editorData);

            postValidateUpdate();

            getUserAccessor().setDetails(mUserEVO);

            updateAdditionalTables(editorData);

            sendEntityEventMessage("User", mUserEVO.getPK(), 3);
        } catch (ValidationException ve) {
            throw new EJBException(ve);
        } catch (EJBException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e.getMessage(), e);
        } finally {
            setUserId(0);
            if (timer != null)
                timer.logInfo("update", mThisTableKey);
        }
    }

    private void preValidateUpdate(UserImpl editorData) throws ValidationException {
        String newPassword = editorData.getPassword();
        if ((editorData.isPasswordChanged()) && (newPassword != null) && (newPassword.length() > 0)) {
            String oldPassword = "";
            if ((mUserEVO.getPasswordBytes() != null) && (mUserEVO.getPasswordBytes().length() > 0))
                oldPassword = Cryptography.decrypt(mUserEVO.getPasswordBytes(), "fc30");
            validateNewPassword(mUserEVO, oldPassword, newPassword, true);
            isValidPassword = true;
        }
    }

    private void postValidateUpdate() throws ValidationException {
    }

    private void completeUpdateSetup(UserImpl editorData) throws Exception {
        String password = "";
        if (editorData.isPasswordChanged()) {
            password = editorData.getPassword();
            if ((password == null) || (password.length() <= 0)) {
                password = "password";
            }
            password = Cryptography.encrypt(password, "fc30");
            mUserEVO.setPasswordBytes(password);
            mUserEVO.setPasswordDate(new Timestamp(System.currentTimeMillis()));

            if (isValidPassword) {
                add2PasswordHistory(mUserEVO);
            }

        }

        if ((editorData.getUserChallenges() != null) && (editorData.getUserChallenges().size() > 0)) {
            challengesUpdate(editorData, false);
        }
        adminAppPrepare(editorData);
        rolesUpdate(editorData, false);
        userPreferencesUpdate(editorData, false);
    }

    private void updateAdditionalTables(UserImpl editorData) throws Exception {
        if ((isPortalEnabled()) && (editorData.isPasswordChanged())) {
            PortalUserDAO dao = new PortalUserDAO();
            String disabled = "N";

            if (editorData.isUserDisabled())
                disabled = "Y";
        }

        saveSecurityAssignmentChanges(editorData, mThisTableKey);
        saveXmlFormsChanges(editorData, mThisTableKey);
    }

    public void delete(int userId, Object paramKey) throws ValidationException, EJBException {
        if (mLog.isDebugEnabled()) {
            mLog.debug("delete", paramKey);
        }
        Timer timer = mLog.isInfoEnabled() ? new Timer(mLog) : null;
        System.out.println("df");
        setUserId(userId);
        mThisTableKey = ((UserPK) paramKey);
        try {
            mUserEVO = getUserAccessor().getDetails(mThisTableKey, "<0><1><2><3><4><5>");
            int id = mThisTableKey.getUserId();
            deleteDataFromOtherTables(id);
            validateDelete();
            mUserAccessor.remove(mThisTableKey);
            sendEntityEventMessage("User", mThisTableKey, 2);
        } catch (ValidationException ve) {
            throw ve;
        } catch (EJBException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e.getMessage(), e);
        } finally {
            setUserId(0);
            if (timer != null)
                timer.logInfo("delete", mThisTableKey);
        }
    }

    private void deleteDataFromOtherTables(int userId) throws Exception {
        BudgetUserDAO dao = new BudgetUserDAO();
        dao.deleteUserFromResponsibilityTable(userId);
        dao.deleteUserFromFinanceFormTable(userId);
        dao.deleteUserFromDataEntryProfileTable(userId);
        dao.deleteUserFromInternalDestinationTable(userId);
        dao.deleteUserRole(userId);
    }

    private void validateDelete() throws ValidationException, Exception {
        int id = mUserEVO.getUserId();
        BudgetUserDAO dao = new BudgetUserDAO();
        EntityList list = dao.getCheckUser(id);

        if (list.getNumRows() > 0) {
            throw new ValidationException("User is assigned budget responsibility");
        }
        XmlFormUserLinkDAO xmlLinkDao = new XmlFormUserLinkDAO();
        list = xmlLinkDao.getCheckXMLFormUserLink(id);

        if (list.getNumRows() > 0) {
            throw new ValidationException("User is assigned to a FinanceForm)");
        }
        InternalDestinationUsersDAO idDao = new InternalDestinationUsersDAO();
        list = idDao.getCheckInternalDestinationUsers(id);

        if (list.getNumRows() > 0) {
            throw new ValidationException("User is assigned to a internal destination group)");
        }
        SecurityGroupDAO sgDao = new SecurityGroupDAO();
        list = sgDao.getAllSecurityGroupsForUser(id);

        if (list.getNumRows() > 0)
            throw new ValidationException("User is assigned to a security group)");
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
            mLog.debug("update", "sending event message: " + em.toString());
            jms.send(em);
            jms.closeSession();
            jms.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AuthenticationPolicyEVO getActiveAuthenticationPolicy() throws ValidationException {
        if (mActiveAuthenticationPolicy == null) {
            AuthenticationPolicyAccessor policyAccessor = null;
            try {
                policyAccessor = new AuthenticationPolicyAccessor(getInitialContext());
            } catch (NamingException e) {
                throw new ValidationException("Cannot get Context");
            }
            try {
                mActiveAuthenticationPolicy = policyAccessor.getActiveAuthenticationPolicy();
            } catch (Exception e) {
                throw new ValidationException("Canot get authentication policy accessor ");
            }
        }

        return mActiveAuthenticationPolicy;
    }

    private UserPasswordHistoryELO getUserPasswordHistory(int userId) throws ValidationException {
        try {
            PasswordHistoryAccessor passwordHistoryAccessor = new PasswordHistoryAccessor(getInitialContext());
            return passwordHistoryAccessor.getUserPasswordHistory(userId);
        } catch (NamingException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    private void checkPasswordHistory(UserEVO userEvo, int passwordReuseDelta, String newPassword) throws ValidationException {
        if (passwordReuseDelta <= 0) {
            return;
        }

        UserPasswordHistoryELO passwordHistoryELO = getUserPasswordHistory(userEvo.getUserId());
        int numRow = passwordHistoryELO.getNumRows();
        if (numRow > 0) {
            int numOfCheck = numRow > passwordReuseDelta ? passwordReuseDelta : numRow;

            for (int i = 0; i < numOfCheck; i++) {
                String cipherPassword = (String) passwordHistoryELO.getValueAt(i, "PasswordBytes");
                String plainPassword = Cryptography.decrypt(cipherPassword, "fc30");
                if (newPassword.equals(plainPassword)) {
                    throw new ValidationException("New password is already in password history. Use other password.");
                }
            }
        }
    }

    private boolean checkPasswordMask(String passwordMask, String password) {
        Hashtable ht = new Hashtable();
        ht.put(Integer.valueOf(97), "[A-Za-z]");
        ht.put(Integer.valueOf(120), "[A-Za-z0-9]");
        ht.put(Integer.valueOf(110), "[0-9]");
        ht.put(Integer.valueOf(65), "[A-Za-z]");
        ht.put(Integer.valueOf(88), "[A-Za-z0-9]");
        ht.put(Integer.valueOf(78), "[0-9]");

        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < passwordMask.length(); i++) {
            sb.append(ht.get(Integer.valueOf(passwordMask.charAt(i))));
        }

        return password.matches(sb.toString());
    }

    private void checkMaximumRepetition(int maximumRepetition, String password) throws ValidationException {
        if (maximumRepetition <= 0) {
            return;
        }

        int passwordLength = password.length();
        int numOfRepeat = 0;
        for (int i = 0; i < passwordLength - 1; i++) {
            for (int j = i + 1; j < passwordLength; j++) {
                if (password.charAt(i) == password.charAt(j)) {
                    numOfRepeat++;
                }
            }
        }
        if (numOfRepeat > maximumRepetition) {
            throw new ValidationException("Repetition of characters in password must not be more than " + maximumRepetition);
        }
    }

    public void validateNewPassword(UserEVO userEvo, String oldPassword, String newPassword, boolean isUpdate) throws ValidationException {
        boolean isPasswordMaskChecked = false;
        AuthenticationPolicyEVO authenticationPolicy = getActiveAuthenticationPolicy();
        boolean isAuthenticateViaInternal = authenticationPolicy.getAuthenticationTechnique() == 1;

        if (isAuthenticateViaInternal) {
            String passwordMask = authenticationPolicy.getPasswordMask();
            if ((passwordMask != null) && (passwordMask.trim().length() > 0)) {
                isPasswordMaskChecked = checkPasswordMask(passwordMask, newPassword);
                if (!isPasswordMaskChecked) {
                    throw new ValidationException("Password must match Password mask:" + passwordMask);
                }
            } else {
                int minimumPasswordLength = authenticationPolicy.getMinimumPasswordLength();
                if (newPassword.length() < minimumPasswordLength) {
                    throw new ValidationException("Password's length must be at least " + minimumPasswordLength + " chars");
                }
            }

            int minimumChange = authenticationPolicy.getMinimumChanges();
            if ((oldPassword != null) && (oldPassword.trim().length() > 0)) {
                if (oldPassword.equals(newPassword)) {
                    throw new ValidationException("The new password is the same as the old password, please choose a different password");
                }
                if (minimumChange > 0) {
                    validatePasswordChange(newPassword, oldPassword, minimumChange);
                }
            }
            int minimumAlphas = authenticationPolicy.getMinimumAlphas();
            int minimumDigits = authenticationPolicy.getMinimumDigits();

            int passwordLength = newPassword.length();
            int numOfChar = 0;
            int numOfDigest = 0;
            boolean isUserPasswordDiffer = authenticationPolicy.getPasswordUseridDiffer();

            for (int i = 0; i < passwordLength; i++) {
                if (Character.isLetter(newPassword.charAt(i))) {
                    numOfChar++;
                } else if (Character.isDigit(newPassword.charAt(i))) {
                    numOfDigest++;
                }
            }

            if (numOfChar < minimumAlphas) {
                throw new ValidationException("Password must contain at least  " + minimumAlphas + " alphas chars");
            }

            if (numOfDigest < minimumDigits) {
                throw new ValidationException("Password must contain at least  " + minimumDigits + " numbers");
            }

            checkMaximumRepetition(authenticationPolicy.getMaximumRepetition(), newPassword);

            if (isUserPasswordDiffer) {
                if (newPassword.equals(userEvo.getName())) {
                    throw new ValidationException("New password must be different with Logon Id");
                }
            }

        }

        if (isUpdate) {
            int passwordReuseDelta = authenticationPolicy.getPasswordReuseDelta();
            checkPasswordHistory(userEvo, passwordReuseDelta, newPassword);
        }
    }

    private void validatePasswordChange(String newPasswd, String oldPasswd, int minimumPasswordChange) throws ValidationException {
        if ((newPasswd != null) && (oldPasswd != null) && (minimumPasswordChange > 0)) {
            int count = 0;
            for (int i = 0; i < newPasswd.length(); i++) {
                char c1 = newPasswd.charAt(i);
                if (i >= oldPasswd.length())
                    break;
                char c2 = oldPasswd.charAt(i);
                if (c1 != c2) {
                    count++;
                }
                if (count > minimumPasswordChange) {
                    break;
                }

            }

            if ((count < minimumPasswordChange) && (newPasswd.length() <= oldPasswd.length()))
                throw new ValidationException("The minimum number of character changes between passwords is " + minimumPasswordChange);
        }
    }

    private void add2PasswordHistory(UserEVO userEVO) throws EJBException {
        try {
            PasswordHistoryAccessor passwordHistoryAccessor = new PasswordHistoryAccessor(new InitialContext());

            PasswordHistoryEVO passwordHistoryEVO = new PasswordHistoryEVO();
            passwordHistoryEVO.setUserId(userEVO.getUserId());
            passwordHistoryEVO.setPasswordBytes(userEVO.getPasswordBytes());
            passwordHistoryEVO.setPasswordDate(userEVO.getPasswordDate());

            passwordHistoryAccessor.create(passwordHistoryEVO);

            mLog.debug("Save password history, OK");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new EJBException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e);
        }
    }

    private void rolesUpdate(UserImpl editorData, boolean isInsert) throws Exception {
        List userRoleEVOs = new ArrayList();
        if (mUserEVO.getUserRoles() != null) {
            userRoleEVOs.addAll(mUserEVO.getUserRoles());
        }
        Iterator rolesIter = editorData.getRoles().iterator();
        while (rolesIter.hasNext()) {
            RoleRefImpl rr = (RoleRefImpl) rolesIter.next();
            if (rr != null) {
                UserRolePK rolePK = new UserRolePK(mUserEVO.getUserId(), rr.getRolePK().getRoleId());

                UserRoleEVO userRoleEVO = mUserEVO.getUserRoles() != null ? mUserEVO.getUserRolesItem(rolePK) : null;

                if (userRoleEVO == null) {
                    userRoleEVO = new UserRoleEVO(mUserEVO.getUserId(), rr.getRolePK().getRoleId());

                    userRoleEVO.prepareForInsert(mUserEVO);
                    userRoleEVO.setInsertPending();
                    mUserEVO.addUserRolesItem(userRoleEVO);
                } else {
                    userRoleEVOs.remove(userRoleEVO);
                }
            }
        }
        for (int i = 0; i < userRoleEVOs.size(); i++) {
            UserRoleEVO userRoleEVO = (UserRoleEVO) userRoleEVOs.get(i);
            mUserEVO.deleteUserRolesItem(userRoleEVO.getPK());
        }
    }

    private void adminAppPrepare(UserImpl editorData) {
        EntityList rolesEntity = getCPConnection().getListHelper().getAllHiddenRolesForUser(mUserEVO.getUserId());
        List<List<Object>> existing = rolesEntity.getDataAsList(); //

        Iterator rolesIter;
        List editorRoles = editorData.getRoles();
        boolean exists;

        // Delete AdminAccess Roles if no hidden roles are chosen
        if ((editorData.getUserAdminAppRoles().size() == 1) && editorData.getUserAdminAppRoles().get(0).toString().equals("AdminAccess")) {
            editorData.getUserAdminAppRoles().remove(0);
        }

        // Delete not chosen
        for (List<Object> old: existing) {

            exists = false;
            rolesIter = editorData.getUserAdminAppRoles().iterator();
            while (rolesIter.hasNext()) {

                // Get RoleRefImpl from chosen roles
                RoleRefImpl rr = null;
                Object element = rolesIter.next();
                if (element instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) element;
                    if (dmtn.isLeaf()) {
                        rr = (RoleRefImpl) dmtn.getUserObject();
                    }
                } else if (element instanceof RoleRefImpl) {
                    rr = (RoleRefImpl) element;
                }

                if ((rr != null) && (old.get(0).equals(rr))) {
                    exists = true;
                }
            }

            // delete if not exist in new
            if (!exists) {
                UserRoleEVO userRoleEVO = new UserRoleEVO(mUserEVO.getUserId(), ((RoleRefImpl) old.get(0)).getRolePK().getRoleId());
                userRoleEVO.setDeletePending();
                mUserEVO.deleteUserRolesItem(userRoleEVO.getPK());
            } else {
                editorRoles.add((RoleRefImpl) old.get(0));
            }
        }

        // Insert chosen
        rolesIter = editorData.getUserAdminAppRoles().iterator();

        while (rolesIter.hasNext()) {

            // Get RoleRefImpl from chosen roles
            RoleRefImpl rr = null;
            Object element = rolesIter.next();
            if (element instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) element;
                if (dmtn.isLeaf()) {
                    rr = (RoleRefImpl) dmtn.getUserObject();
                }
            } else if (element instanceof RoleRefImpl) {
                rr = (RoleRefImpl) element;
            }

            if (rr != null) {
                exists = false;
                for (List<Object> old: existing) {
                    if (old.get(0).equals(rr)) {
                        exists = true;
                    }
                }

                // insert if new
                if (!exists) {
                    UserRoleEVO userRoleEVO = new UserRoleEVO(mUserEVO.getUserId(), rr.getRolePK().getRoleId());
                    userRoleEVO.setInsertPending();
                    mUserEVO.addUserRolesItem(userRoleEVO);
                    editorRoles.add(rr);
                }

            }
        }
        editorData.setRoles(editorRoles); // set checkbox in dataeditor
    }

    private void challengesUpdate(UserImpl editorData, boolean isInsert) {
        List challs = editorData.getUserChallenges();
        String question = "";
        String answer = "";
        boolean doRemove = true;

        Iterator challengeIter = mUserEVO.getChallengeQuestions().iterator();
        while (challengeIter.hasNext()) {
            ChallengeQuestionEVO challEVO = (ChallengeQuestionEVO) challengeIter.next();
            doRemove = true;
            for (Iterator i$ = challs.iterator(); i$.hasNext();) {
                Object chall = i$.next();

                Map questionset = (Map) chall;
                question = (String) questionset.get("question");
                answer = (String) questionset.get("answer");
                if ((challEVO.getQuestionText().equals(question)) && (challEVO.getQuestionAnswer().equals(answer))) {
                    challs.remove(questionset);
                    doRemove = false;
                    break;
                }
                if ((challEVO.getQuestionText().equals(question)) && (!challEVO.getQuestionAnswer().equals(answer))) {
                    challEVO.setQuestionAnswer(answer);
                    challs.remove(questionset);
                    doRemove = false;
                    break;
                }
            }

            if (doRemove) {
                mUserEVO.deleteChallengeQuestionsItem(challEVO.getPK());
            }

        }

        for (Iterator i$ = challs.iterator(); i$.hasNext();) {
            Object chall = i$.next();
            Map questionset = (Map) chall;

            ChallengeQuestionEVO evo = new ChallengeQuestionEVO();
            evo.setUserId(mUserEVO.getUserId());
            evo.setQuestionText((String) questionset.get("question"));
            evo.setQuestionAnswer((String) questionset.get("answer"));

            mUserEVO.addChallengeQuestionsItem(evo);
        }
    }

    private void userPreferencesUpdate(UserImpl editorData, boolean isInsert) {
        Map newValues = editorData.getUserPreferences();
        Set newKeys = new HashSet();
        newKeys.addAll(newValues.keySet());

        List userPreferencesEVOs = new ArrayList();
        if (mUserEVO.getUserPreferences() != null) {
            userPreferencesEVOs.addAll(mUserEVO.getUserPreferences());
        }
        Iterator upIter = mUserEVO.getUserPreferences().iterator();
        while (upIter.hasNext()) {
            UserPreferenceEVO upEvo = (UserPreferenceEVO) upIter.next();
            if (upEvo != null) {
                UserPreferenceImpl upi = (UserPreferenceImpl) newValues.get(upEvo.getPrefName());
                if (upi != null) {
                    upEvo.setPrefValue(upi.getPrefValue());

                    newKeys.remove(upEvo.getPrefName());
                }

            }

        }

        Iterator newUpIter = newKeys.iterator();
        while (newUpIter.hasNext()) {
            String key = (String) newUpIter.next();
            UserPreferenceEVO newEvo = new UserPreferenceEVO();
            newEvo.setUserId(mUserEVO.getUserId());
            newEvo.setInsertPending();
            UserPreferenceImpl upi = (UserPreferenceImpl) newValues.get(key);
            newEvo.setHelpId("user.pref.help.id");
            newEvo.setPrefName(upi.getPrefName());
            newEvo.setPrefType(upi.getPrefType());
            newEvo.setPrefValue(upi.getPrefValue());
            userPreferencesEVOs.add(newEvo);
        }
        mUserEVO.setUserPreferences(userPreferencesEVOs);
    }

    private Map queryAvailableRoles() throws Exception {
        AllRolesELO allRoles = getRoleAccessor().getAllRoles();
        Map m = new HashMap();
        for (int row = 0; row < allRoles.getNumRows(); row++) {
            RoleRefImpl rr = (RoleRefImpl) allRoles.getValueAt(row, "Role");
            m.put(rr.getNarrative(), rr);
        }
        return m;
    }

    private boolean isPortalEnabled() {
        String key = "SYS: Portal Integration";
        String eloKey = "";
        String value = "";

        SystemPropertyDAO dao = new SystemPropertyDAO();
        EntityList list = dao.getAllSystemPropertys();
        int rows = list.getNumRows();
        for (int i = 0; i < rows; i++) {
            eloKey = ((SystemPropertyRef) list.getValueAt(i, "SystemProperty")).getNarrative();
            if (eloKey.equals(key)) {
                value = (String) list.getValueAt(i, "Value");
                break;
            }
        }

        boolean result = Boolean.valueOf(value).booleanValue();

        return result;
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

    private RoleAccessor getRoleAccessor() throws Exception {
        if (mRoleAccessor == null) {
            mRoleAccessor = new RoleAccessor(getInitialContext());
        }
        return mRoleAccessor;
    }

    private void saveSecurityAssignmentChanges(UserImpl editorData, UserPK userPk) {
        List<Object[]> insert = new ArrayList<Object[]>();
        List<Object[]> delete = new ArrayList<Object[]>();
        BudgetUserDAO budgetUserDao = new BudgetUserDAO();
        List<UserModelElementAssignment> existingAssigns = budgetUserDao.getRespAreaAccess(userPk);
        // make unique
        HashSet nodes = new HashSet();
        nodes.addAll(existingAssigns);
        existingAssigns.clear();
        existingAssigns.addAll(nodes);
        List<Object[]> newAssigns = editorData.getUserAssignments();
        for (UserModelElementAssignment oldAssign: existingAssigns) {

            boolean found = false;
            for (Object[] newAssign: newAssigns) {
                if (newAssign[2] instanceof StructureElementRef) {
                    if (((StructureElementPK) oldAssign.getStructureElementRef().getPrimaryKey()).getStructureElementId() == ((StructureElementPK) ((StructureElementRef) newAssign[2]).getPrimaryKey()).getStructureElementId()) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                Object[] data = new Object[3];
                data[0] = ((ModelPK) ((ModelRef) oldAssign.getModel()).getPrimaryKey()).getModelId();
                data[1] = userPk.getUserId();
                data[2] = (((StructureElementPK) ((StructureElementRef) oldAssign.getStructureElementRef()).getPrimaryKey()).getStructureElementId());
                delete.add(data);
            }
        }
        for (Object[] newAssign: newAssigns) {
            if (newAssign[2] instanceof StructureElementRef) {
                boolean found = false;
                for (UserModelElementAssignment oldAssign: existingAssigns) {
                    if (((StructureElementPK) oldAssign.getStructureElementRef().getPrimaryKey()).getStructureElementId() == ((StructureElementPK) ((StructureElementRef) newAssign[2]).getPrimaryKey()).getStructureElementId()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    newAssign[1] = userPk.getUserId();
                    insert.add(newAssign);
                }
            }
        }
        if (delete.size() > 0)
            budgetUserDao.delete(delete);
        if (insert.size() > 0)
            budgetUserDao.insert(insert);
    }

    /**
     * Save XMLForms changes - check changes between current and added/deleted XMLForms in User->Users->XMLForms
     * 
     * @param editorData
     * @param userPk
     */
    private void saveXmlFormsChanges(UserImpl editorData, UserPK userPk) {
        List<Object[]> insert = new ArrayList<Object[]>();
        List<Object[]> delete = new ArrayList<Object[]>();
        XmlFormUserLinkDAO xmlFormUserLinkDAO = new XmlFormUserLinkDAO();
        XmlFormDAO xmlFormDAO = new XmlFormDAO();
        EntityList existingXmlFormEntityList = (EntityList) xmlFormDAO.getXcellXmlFormsForUser(userPk.getUserId());
        List<List<Object>> existingXmlFormList = existingXmlFormEntityList.getDataAsList();
        List<List<Object>> newXmlForm = editorData.getUserXmlForms();

        for (List<Object> oldXmlFormElem: existingXmlFormList) {
            boolean found = false;
        

       
            for (List<Object> newXmlFormElem: newXmlForm) {

                if (newXmlFormElem.get(4) ==(oldXmlFormElem.get(4))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                Object[] data = new Object[2];
                data[0] = ((XmlFormRefImpl) oldXmlFormElem.get(0)).getXmlFormPK().getXmlFormId();
                data[1] = userPk.getUserId();
                delete.add(data);
            }
        }

        for (List<Object> newXmlFormElem: newXmlForm) {
            boolean found = false;
            for (List<Object> oldXmlFormElem: existingXmlFormList) {
                if (newXmlFormElem.get(4) ==(oldXmlFormElem.get(4))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                Object[] data = new Object[2];
                data[0] = ((Integer) newXmlFormElem.get(4));
                data[1] = userPk.getUserId();
                insert.add(data);
            }
        }

        if (delete.size() > 0)
            xmlFormUserLinkDAO.delete(delete);
        if (insert.size() > 0)
            xmlFormUserLinkDAO.insert(insert);
    }

    public class XcellFormRow {
        private Object[] obj;

        public XcellFormRow(Object[] obj) {
            this.obj = obj;
        }

        public String toString() {
            return ((FinanceCubeRef) obj[1]).getNarrative();
        }

        public Object get(int index) {
            return obj[index];
        }
    }

}