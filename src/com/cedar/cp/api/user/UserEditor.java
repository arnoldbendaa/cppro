// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.user;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.Dimension;
import com.cedar.cp.api.role.RoleRef;
import com.cedar.cp.api.user.User;
import com.cedar.cp.api.user.UserRef;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

public interface UserEditor extends BusinessEditor {

    void setChangePassword(boolean var1) throws ValidationException;

    void setUserDisabled(boolean var1) throws ValidationException;

    void setUserReadOnly(boolean var1) throws ValidationException;

    void setPasswordNeverExpires(boolean var1) throws ValidationException;

    void setName(String var1) throws ValidationException;

    void setFullName(String var1) throws ValidationException;

    void setEMailAddress(String var1) throws ValidationException;

    void setPasswordBytes(String var1) throws ValidationException;

    void setPasswordDate(Timestamp var1) throws ValidationException;

    void setExternalSystemUserName(String var1) throws ValidationException;

    void setLogonAlias(String var1) throws ValidationException;

    User getUser();

    void setImageIcon(Icon var1) throws ValidationException;

    void setUserPreferences(Map var1) throws ValidationException;

    void setUserPreferenceValue(String var1, String var2) throws ValidationException;

    Map getModelDimensions() throws CPException;

    Dimension getDimension(Object var1) throws CPException;

    UserRef getRef();

    int getMinimumPasswordSize();

    boolean canChangePassword();

    void setPassword(String var1) throws ValidationException;

    void setPasswordChanged(boolean var1);

    void addRole(RoleRef var1);

    void removeRole(RoleRef var1);

    void removeRole(int var1);

    boolean isPasswordProtected();

    boolean isEmailAddressProtected();

    boolean isRoleAdminDisabled();

    String getRestrictedBannerText();

    void setUserChallenges(List challenges);

    EntityList getAllModels();

    EntityList getAllBudgetHierarchies();

    EntityList getImmediateChildren(Object pk);

    List<Object[]> getUserAssignments();

    void setUserAssignments(List dmtns);

    boolean hasUserAssignment(Object o);

    void removeUserAssignment(Object o);

    List getUserAdminAppRoles();

    void setUserAdminAppRoles(List roles);

    void setUserXmlForms(List<List<Object>> selected);

    List<List<Object>> getUserXmlForms();

    boolean isNewFeaturesEnabled();

    void setNewFeaturesEnabled(boolean bool);
    
    boolean areButtonsVisible();

    void setButtonsVisible(boolean bool);
    
    boolean isShowBudgetActivity();
    
    void setShowBudgetActivity(boolean bool);
    
    boolean isNewView();
    
    void setNewView(boolean bool);
    
    boolean getRoadMapAvailable();

    void setRoadMapAvailable(boolean bool);

}
