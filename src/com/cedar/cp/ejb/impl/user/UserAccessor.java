// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.reset.AllChallengeQuestionsELO;
import com.cedar.cp.dto.reset.AllQuestionsAndAnswersByUserIDELO;
import com.cedar.cp.dto.reset.AllUserResetLinksELO;
import com.cedar.cp.dto.reset.LinkByUserIDELO;
import com.cedar.cp.dto.user.AllDashboardsForUserELO;
import com.cedar.cp.dto.user.AllDataEntryProfileHistorysELO;
import com.cedar.cp.dto.user.AllDataEntryProfilesELO;
import com.cedar.cp.dto.user.AllDataEntryProfilesForFormELO;
import com.cedar.cp.dto.user.AllDataEntryProfilesForUserELO;
import com.cedar.cp.dto.user.AllNonDisabledUsersELO;
import com.cedar.cp.dto.user.AllRevisionsELO;
import com.cedar.cp.dto.user.AllRolesForUsersELO;
import com.cedar.cp.dto.user.AllUserAttributesELO;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.AllUsersExportELO;
import com.cedar.cp.dto.user.AllUsersForDataEntryProfilesForModelELO;
import com.cedar.cp.dto.user.DefaultDataEntryProfileELO;
import com.cedar.cp.dto.user.FinanceSystemUserNameELO;
import com.cedar.cp.dto.user.SecurityStringsForUserELO;
import com.cedar.cp.dto.user.UserCK;
import com.cedar.cp.dto.user.UserMessageAttributesELO;
import com.cedar.cp.dto.user.UserMessageAttributesForIdELO;
import com.cedar.cp.dto.user.UserMessageAttributesForNameELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserPreferencesForUserELO;
import com.cedar.cp.dto.user.UsersWithSecurityStringELO;
import com.cedar.cp.ejb.impl.reset.ChallengeQuestionDAO;
import com.cedar.cp.ejb.impl.reset.UserResetLinkDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileDAO;
import com.cedar.cp.ejb.impl.user.DataEntryProfileHistoryDAO;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.ejb.impl.user.UserLocal;
import com.cedar.cp.ejb.impl.user.UserLocalHome;
import com.cedar.cp.ejb.impl.user.UserPreferenceDAO;
import com.cedar.cp.ejb.impl.user.UserRoleDAO;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class UserAccessor implements Serializable {

   private UserLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_USER_ROLES = "<0>";
   public static final String GET_USER_PREFERENCES = "<1>";
   public static final String GET_DATA_ENTRY_PROFILES = "<2>";
   public static final String GET_DATA_ENTRY_PROFILES_HISTORY = "<3>";
   public static final String GET_CHALLENGE_QUESTIONS = "<4>";
   public static final String GET_RESET_LINK = "<5>";
   public static final String GET_ALL_DEPENDANTS = "<0><1><2><3><4><5>";
   UserEEJB userEjb = new UserEEJB();

   public UserAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private UserLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (UserLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/UserLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up UserLocalHome", var2);
      }
   }

   private UserLocal getLocal(UserPK pk) throws Exception {
      UserLocal local = (UserLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public UserEVO create(UserEVO evo) throws Exception {
//      UserLocal local = this.getLocalHome().create(evo);
//      UserEVO newevo = local.getDetails("<UseLoadedEVOs>");
//      UserPK pk = newevo.getPK();
//      this.mLocals.put(pk, local);
//      return newevo;
	   userEjb.ejbCreate(evo);
	   UserEVO newevo = userEjb.getDetails("<UseLoadedEVOs>");
	   UserPK pk = newevo.getPK();
	   this.mLocals.put(pk, userEjb);
	   return newevo;
   }

   public void remove(UserPK pk) throws Exception {
//      this.getLocal(pk).remove();
	   userEjb.ejbRemove();
   }

   public UserEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof UserCK) {
    	  UserCK userCk = (UserCK)key;

         UserPK pk = ((UserCK)key).getUserPK();
         return userEjb.getDetails(userCk,dependants);
//         return this.getLocal(pk).getDetails((UserCK)key, dependants);
      } else {
    	  UserPK userPk = (UserPK)key;
    	  UserCK userCk = new UserCK(userPk);

//         return key instanceof UserPK?this.getLocal((UserPK)key).getDetails(dependants):null;
    	  if(key instanceof UserPK)
    		  return userEjb.getDetails(userCk,dependants);
    	  else
    		  return null;
    		  
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(UserEVO evo) throws Exception {
      UserPK pk = evo.getPK();
//      this.getLocal(pk).setDetails(evo);
      userEjb.ejbFindByPrimaryKey(pk);
      userEjb.setDetails(evo);
//      userEjb.ejbStore();
   }

   public UserEVO setAndGetDetails(UserEVO evo, String dependants) throws Exception {
//      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
      return userEjb.setAndGetDetails(evo, dependants);
   }

   public UserPK generateKeys(UserPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllUsersELO getAllUsers() {
      UserDAO dao = new UserDAO();
      return dao.getAllUsers();
   }
   
   public AllRevisionsELO getAllRevisions() {
       UserDAO dao = new UserDAO();
       return dao.getAllRevisions();
    }
   
   public AllDashboardsForUserELO getDashboardForms(Integer userId, boolean isAdmin) {
       UserDAO dao = new UserDAO();
       return dao.getDashboardForms(userId, isAdmin);
    }

   public SecurityStringsForUserELO getSecurityStringsForUser(int param1) {
      UserDAO dao = new UserDAO();
      return dao.getSecurityStringsForUser(param1);
   }

   public AllUsersExportELO getAllUsersExport() {
      UserDAO dao = new UserDAO();
      return dao.getAllUsersExport();
   }

   public AllUserAttributesELO getAllUserAttributes() {
      UserDAO dao = new UserDAO();
      return dao.getAllUserAttributes();
   }

   public AllNonDisabledUsersELO getAllNonDisabledUsers() {
      UserDAO dao = new UserDAO();
      return dao.getAllNonDisabledUsers();
   }

   public UserMessageAttributesELO getUserMessageAttributes() {
      UserDAO dao = new UserDAO();
      return dao.getUserMessageAttributes();
   }

   public UserMessageAttributesForIdELO getUserMessageAttributesForId(int param1) {
      UserDAO dao = new UserDAO();
      return dao.getUserMessageAttributesForId(param1);
   }

   public UserMessageAttributesForNameELO getUserMessageAttributesForName(String param1) {
      UserDAO dao = new UserDAO();
      return dao.getUserMessageAttributesForName(param1);
   }

   public FinanceSystemUserNameELO getFinanceSystemUserName(int param1) {
      UserDAO dao = new UserDAO();
      return dao.getFinanceSystemUserName(param1);
   }

   public UsersWithSecurityStringELO getUsersWithSecurityString(String param1) {
      UserDAO dao = new UserDAO();
      return dao.getUsersWithSecurityString(param1);
   }

   public AllRolesForUsersELO getAllRolesForUsers() {
      UserRoleDAO dao = new UserRoleDAO();
      return dao.getAllRolesForUsers();
   }

   public UserPreferencesForUserELO getUserPreferencesForUser(int param1) {
      UserPreferenceDAO dao = new UserPreferenceDAO();
      return dao.getUserPreferencesForUser(param1);
   }

   public AllDataEntryProfilesELO getAllDataEntryProfiles() {
      DataEntryProfileDAO dao = new DataEntryProfileDAO();
      return dao.getAllDataEntryProfiles();
   }

   public AllDataEntryProfilesForUserELO getAllDataEntryProfilesForUser(int param1, int param2, int budgetCycleId) {
      DataEntryProfileDAO dao = new DataEntryProfileDAO();
      return dao.getAllDataEntryProfilesForUser(param1, param2, budgetCycleId);
   }

   public AllUsersForDataEntryProfilesForModelELO getAllUsersForDataEntryProfilesForModel(int param1) {
      DataEntryProfileDAO dao = new DataEntryProfileDAO();
      return dao.getAllUsersForDataEntryProfilesForModel(param1);
   }

   public AllDataEntryProfilesForFormELO getAllDataEntryProfilesForForm(int param1) {
      DataEntryProfileDAO dao = new DataEntryProfileDAO();
      return dao.getAllDataEntryProfilesForForm(param1);
   }

   public DefaultDataEntryProfileELO getDefaultDataEntryProfile(int param1, int param2, int param3, int param4) {
      DataEntryProfileDAO dao = new DataEntryProfileDAO();
      return dao.getDefaultDataEntryProfile(param1, param2, param3, param4);
   }

   public AllDataEntryProfileHistorysELO getAllDataEntryProfileHistorys() {
      DataEntryProfileHistoryDAO dao = new DataEntryProfileHistoryDAO();
      return dao.getAllDataEntryProfileHistorys();
   }

   public UserPK findByUserID(String uid) {
      UserDAO dao = new UserDAO();
      return dao.findByUserID(uid);
   }

   public Map<UserPK, AllUsersELO> getMapOfAllUsers() {
      UserDAO dao = new UserDAO();
      return dao.getMapOfAllUsers();
   }
   
	public UserMessageAttributesForIdELO getUserMessageAttributesForMultiplIds(String[] params) {
		UserDAO dao = new UserDAO();
		return dao.getUserMessageAttributesForMultipleIds(params);
	}
	
	public AllChallengeQuestionsELO getAllChallengeQuestions() {
		ChallengeQuestionDAO dao = new ChallengeQuestionDAO();
		return dao.getAllChallengeQuestions();
	}
	
	public AllQuestionsAndAnswersByUserIDELO getAllQuestionsAndAnswersByUserID(int param1) {
		ChallengeQuestionDAO dao = new ChallengeQuestionDAO();
		return dao.getAllQuestionsAndAnswersByUserID(param1);
	}

	public AllUserResetLinksELO getAllUserResetLinks() {
		UserResetLinkDAO dao = new UserResetLinkDAO();
		return dao.getAllUserResetLinks();
	}

	public LinkByUserIDELO getLinkByUserID(int param1) {
		UserResetLinkDAO dao = new UserResetLinkDAO();
		return dao.getLinkByUserID(param1);
	}

	public AllQuestionsAndAnswersByUserIDELO getChallengeWord(int userId) {
		ChallengeQuestionDAO dao = new ChallengeQuestionDAO();
		return dao.getChallengeWord(userId);
	}

	public void setChallengeWord(int userId, String word) {
		ChallengeQuestionDAO dao = new ChallengeQuestionDAO();
		dao.setChallengeWord(userId, word);
	}
}
