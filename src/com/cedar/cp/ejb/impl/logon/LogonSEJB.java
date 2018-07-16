// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.logon;

import com.cedar.cp.api.base.AdminOnlyException;
import com.cedar.cp.api.base.InvalidCredentialsException;
import com.cedar.cp.api.base.UserDisabledException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.logon.AuthenticationResult;
import com.cedar.cp.api.role.RoleRef;
import com.cedar.cp.dto.authenticationpolicy.ActiveAuthenticationPolicyForLogonELO;
import com.cedar.cp.dto.logon.AuthenticationResultImpl;
import com.cedar.cp.dto.role.AllHiddenRolesELO;
import com.cedar.cp.dto.role.AllRolesELO;
import com.cedar.cp.dto.role.AllRolesForUserELO;
import com.cedar.cp.dto.role.AllSecurityRolesForRoleELO;
import com.cedar.cp.dto.role.RolePK;
import com.cedar.cp.dto.role.RoleRefImpl;
import com.cedar.cp.dto.user.UserEditorSessionSSO;
import com.cedar.cp.dto.user.UserImpl;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.api.user.UserEditorSessionServer;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyDAO;
import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryDAO;
import com.cedar.cp.ejb.impl.logonhistory.LogonHistoryEVO;
import com.cedar.cp.ejb.impl.message.ServerSideUserMailer;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryDAO;
import com.cedar.cp.ejb.impl.passwordhistory.PasswordHistoryEVO;
import com.cedar.cp.ejb.impl.role.RoleDAO;
import com.cedar.cp.ejb.impl.role.RoleSecurityDAO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserDAO;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.ejb.impl.user.UserEditorSessionSEJB;
import com.cedar.cp.util.Cryptography;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import com.coa.idm.UserRepository;
import com.coa.lm.module.FixedCallbackHandler;
import com.coa.lm.principal.COAPrincipal;
import com.coa.portal.client.PortalPrincipal;
import edu.umich.auth.cosign.CosignPrincipal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import jcifs.smb.NtlmPasswordAuthentication;

public class LogonSEJB implements SessionBean {

   Log _log = new Log(this.getClass());
   private static String SSO_CP_ROLE_PREFIX = "adv.collaborative_planning.";
   private transient int mAuthSecurityLog = 1;
   private transient int mAuthenticationTechnique = 1;
   private transient int mAuthSecurityUserId = 0;
   private transient String mJassAuthNAme = "COA";
   private transient int mPasswordExpiryDays = 90;
   private transient int mMinPasswordSize = 8;
   private transient String mSystemName = "Unknown";
   private transient int mRunLevel = 0;
   private transient boolean mLoadedProps = false;
   private long M_SECONDS_IN_DAY = 806400000L;


   public AuthenticationResult authenticateUser(String usrid, String pwd, boolean trustAuthentication) throws ValidationException, EJBException {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      this.loadConfiguration();
      
      try {
         UserPK e = null;
         UserEVO userEVO = null;
         AuthenticationResultImpl result = new AuthenticationResultImpl();
         UserDAO userDAO = new UserDAO();
         if(!trustAuthentication && this.mAuthenticationTechnique == 2) {
            try {
               LoginContext storedPassword = new LoginContext(this.mJassAuthNAme, new FixedCallbackHandler(usrid, pwd));
               storedPassword.login();
               Subject subject = storedPassword.getSubject();
               Set principals = subject.getPrincipals(COAPrincipal.class);
               if(principals.size() != 1) {
                  throw new InvalidCredentialsException();
               }

               Iterator i$ = principals.iterator();

               while(i$.hasNext()) {
                  COAPrincipal principal = (COAPrincipal)i$.next();
                  this._log.debug("[debug] LogonSEJB external user is " + principal.getName());
                  e = userDAO.findByLogonAlias(principal.getName());
                  if(e == null) {
                     throw new ValidationException("Failed to locate user:" + principal.getName());
                  }

                  userEVO = userDAO.getDetails(e, "<0><1><2><3>");
                  result.setPrimaryKey(e);
                  if(userEVO.getUserDisabled()) {
                     throw new UserDisabledException();
                  }
               }
            } catch (Exception var14) {
               if(this._log.isDebugEnabled()) {
                  this._log.debug("authenticateUser", var14);
               }

               throw new InvalidCredentialsException();
            }
         }

         if(userEVO == null) {
            e = userDAO.findByUserID(usrid);
            if(e == null) {
               throw new InvalidCredentialsException(0);
            }

            userEVO = userDAO.getDetails(e, "<0><1><2><3>");
            result.setPrimaryKey(e);
            if(!trustAuthentication && userEVO.getUserDisabled()) {
               throw new UserDisabledException();
            }
         }

         if(!trustAuthentication) {
            if(this.mAuthenticationTechnique == 1) {
            	if(pwd.length() == 64){
            		// PIN authentication
            		String pin = userEVO.getMobilePIN();
    				if (pwd.compareTo(pin) != 0) {
            			throw new InvalidCredentialsException(1);
            		}
            	} else {
            		// Password authentication
            		String storedPassword1 = userEVO.getPasswordBytes();
            		storedPassword1 = Cryptography.decrypt(storedPassword1, "fc30");
            		if (pwd.compareTo(storedPassword1) != 0) {
            			throw new InvalidCredentialsException(1);
            		}
//            		if(!pwd.equals(storedPassword1)){
//            			throw new InvalidCredentialsException(1);
//            		}
            	}
            	userDAO.addUserLogHistory(usrid);
            	result.setResult(this.getResult(result, userEVO));
            } else {
               result.setResult(3);
            }

            if(result.getResult() <= 4 || result.getResult() == 8) {
				this.setValidUserResult(result, userEVO);
				this.add2LogonHistory(1, usrid);
            }
         } else {
				this.setValidUserResult(result, userEVO);
         }

         if(timer != null) {
            timer.logDebug("authenticateUser");
         }
         
		if ((mRunLevel == 1) && (!result.getUserSecurityStrings().contains("WEB_PROCESS.SystemAdmin"))) {
			throw new AdminOnlyException();
		}

         return result;
      } catch (ValidationException var15) {
         this.add2LogonHistory(2, usrid);
         if(this._log.isDebugEnabled()) {
            this._log.debug("authenticateUser", var15);
         }

         throw var15;
      } catch (Exception var16) {
         this.add2LogonHistory(2, usrid);
         var16.printStackTrace();
         this._log.debug("[debug] LogonSEJB authenticateUser() - " + var16.getClass() + " " + var16.getMessage());
         this._log.debug("[debug] LogonSEJB user : " + usrid + " does not exist");
         throw new InvalidCredentialsException();
      }
   }

   public AuthenticationResult authenticateUser(CosignPrincipal principal) throws ValidationException, EJBException {
      this.loadConfiguration();

      try {
         return this.authenticateExternalUser(principal.getName());
      } catch (ValidationException var3) {
         this.add2LogonHistory(2, principal.getName());
         if(this._log.isDebugEnabled()) {
            this._log.debug("authenticateUser", var3);
         }

         throw var3;
      } catch (Exception var4) {
         this.add2LogonHistory(2, principal.getName());
         var4.printStackTrace();
         this._log.debug("[debug] LogonSEJB authenticateUser() - " + var4.getClass() + " " + var4.getMessage());
         this._log.debug("[debug] LogonSEJB user : " + principal.getName() + " does not exist");
         throw new InvalidCredentialsException();
      }
   }

   public AuthenticationResult authenticateUser(UserRepository principal) throws ValidationException, EJBException {
      this.loadConfiguration();
      String userId = principal.getLogonIdentity();

      try {
         return this.authenticateExternalUser(userId);
      } catch (ValidationException var4) {
         this.add2LogonHistory(2, userId);
         if(this._log.isDebugEnabled()) {
            this._log.debug("authenticateUser", var4);
         }

         throw new InvalidCredentialsException();
      } catch (Exception var5) {
         this.add2LogonHistory(2, userId);
         var5.printStackTrace();
         this._log.debug("[debug] LogonSEJB authenticateUser() - " + var5.getClass() + " " + var5.getMessage());
         this._log.debug("[debug] LogonSEJB user : " + userId + " does not exist");
         throw new EJBException(var5);
      }
   }

   public String getAttribFromPrincipal(UserRepository principal, String key) {
      Map m = null;

      try {
         m = principal.getUserAttributes();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      if(m != null) {
         Object o = m.get(key);
         if(o != null) {
            String s = o.toString();
            return s.substring(1, s.length() - 1);
         }
      }

      return null;
   }

   public AuthenticationResult authenticateUser(PortalPrincipal principal) throws ValidationException, EJBException {
      this.loadConfiguration();

      try {
         return this.authenticateExternalUser(principal.getName());
      } catch (ValidationException var3) {
         this.add2LogonHistory(2, principal.getName());
         if(this._log.isDebugEnabled()) {
            this._log.debug("authenticateUser", var3);
         }

         throw var3;
      } catch (Exception var4) {
         this.add2LogonHistory(2, principal.getName());
         var4.printStackTrace();
         this._log.debug("[debug] LogonSEJB authenticateUser() - " + var4.getClass() + " " + var4.getMessage());
         this._log.debug("[debug] LogonSEJB user : " + principal.getName() + " does not exist");
         throw new InvalidCredentialsException();
      }
   }

   public AuthenticationResult authenticateUser(NtlmPasswordAuthentication principal) throws ValidationException, EJBException {
      this.loadConfiguration();

      try {
         return this.authenticateExternalUser(principal.getUsername());
      } catch (ValidationException var3) {
         this.add2LogonHistory(2, principal.getName());
         if(this._log.isDebugEnabled()) {
            this._log.debug("authenticateUser", var3);
         }

         throw var3;
      } catch (Exception var4) {
         this.add2LogonHistory(2, principal.getName());
         var4.printStackTrace();
         this._log.debug("[debug] LogonSEJB authenticateUser() - " + var4.getClass() + " " + var4.getMessage());
         this._log.debug("[debug] LogonSEJB user : " + principal.getName() + " does not exist");
         throw new InvalidCredentialsException();
      }
   }

   private AuthenticationResult authenticateExternalUser(String name) throws Exception {
      UserPK userPK = null;
      UserEVO userEVO = null;
      AuthenticationResultImpl result = new AuthenticationResultImpl();
      UserDAO userDAO = new UserDAO();
      userPK = userDAO.findByUserID(name.toLowerCase());
      if(userPK == null) {
         userPK = userDAO.findByLogonAlias(name.toLowerCase());
      }

      if(userPK == null) {
         throw new ValidationException("Failed to locate user for id:" + name);
      } else {
         userEVO = userDAO.getDetails(userPK, "<0><1><2><3>");
         result.setPrimaryKey(userPK);
         if(userEVO.getUserDisabled()) {
            throw new UserDisabledException();
         } else {
            result.setResult(3);
            this.setValidUserResult(result, userEVO);
            this.add2LogonHistory(1, name.toLowerCase());
            return result;
         }
      }
   }

   private void setValidUserResult(AuthenticationResultImpl result, UserEVO userEVO) throws Exception {
      result.setUserName(userEVO.getName());
      result.setFullUserName(userEVO.getFullName());
      this.loadSecuritySettings(result, userEVO.getUserId());
      result.setSystemName(this.loadSystemName());
      result.setLogonID(String.valueOf(userEVO.getUserId()));
      result.setMinimumPasswordSize(this.mMinPasswordSize);
      result.setNewFeaturesEnabled(userEVO.isNewFeaturesEnabled());
      result.setButtonsVisible(userEVO.areButtonsVisible());
      result.setShowBudgetActivity(userEVO.isShowBudgetActivity());
      result.setNewView(userEVO.isNewView());
      result.setRoadMapAvailable(userEVO.getRoadMapAvailable());
   }

   public void add2LogonHistory(int state, String username) {
      int logLevel = this.mAuthSecurityLog;
      if(username != null && username.trim().length() > 0) {
         if(state == 2) {
            this.mailUser(username);
         }

         if(logLevel != 1) {
            try {
               boolean e = false;
               if(logLevel == 4) {
                  e = true;
               } else if(logLevel == 2) {
                  e = state == 2;
               } else if(logLevel == 3) {
                  e = state == 1 || state == 2;
               }

               if(e) {
                  LogonHistoryEVO logonHistoryEVO = new LogonHistoryEVO();
                  logonHistoryEVO.setUserName(username);
                  logonHistoryEVO.setEventDate(new Timestamp(System.currentTimeMillis()));
                  logonHistoryEVO.setEventType(state);
                  LogonHistoryDAO dao = new LogonHistoryDAO();
                  dao.setDetails(logonHistoryEVO);
                  dao.create();
               }
            } catch (Exception var7) {
               var7.printStackTrace();
               throw new EJBException(var7);
            }
         }
      }

   }

   private void mailUser(String userName) {
      int userId = this.mAuthSecurityUserId;
      if(userId > 0) {
         ServerSideUserMailer mailer = new ServerSideUserMailer(userId, true);
         mailer.sendUserMailMessage(MessageFormat.format("Invalid logon for user {0}", new Object[]{userName}), "You have been flagged as the receiver of invalid Collaborative Planning Logon attempts\nThe name of the system being accessed is " + this.mSystemName + " at time " + new Date());
      }

   }

   public AuthenticationResult changePassword(String logonId, String newPassword) throws EJBException {
      this.loadConfiguration();
      AuthenticationResultImpl result = new AuthenticationResultImpl();
      if(this.mAuthenticationTechnique != 1) {
         throw new IllegalStateException("Can\'t change password when external authentication");
      } else {
         try {
            UserAccessor e = new UserAccessor(new InitialContext());
            UserPK userPK = null;
            UserEVO userEVO = null;
            userPK = e.findByUserID(logonId);
            if(userPK == null) {
               throw new ValidationException("Failed to locate user:" + logonId);
            } else {
               userEVO = e.getDetails(userPK, "");
               String storedPassword = userEVO.getPasswordBytes();
               storedPassword = Cryptography.decrypt(storedPassword, "fc30");
               if(newPassword.equals(storedPassword)) {
                  result.setResult(13);
                  return result;
               } else {
                  UserEditorSessionSEJB userSEjb = new UserEditorSessionSEJB();
                  userSEjb.validateNewPassword(userEVO, storedPassword, newPassword, true);
                  String encrypted = Cryptography.encrypt(newPassword, "fc30");
                  userEVO.setPasswordBytes(encrypted);
                  Timestamp now = new Timestamp(System.currentTimeMillis());
                  userEVO.setPasswordDate(now);
                  userEVO.setChangePassword(false);
                  e.setDetails(userEVO);
                  PasswordHistoryDAO passwordHistoryDAO = new PasswordHistoryDAO();
                  PasswordHistoryEVO passwordHistoryEVO = new PasswordHistoryEVO();
                  passwordHistoryEVO.setUserId(userPK.getUserId());
                  passwordHistoryEVO.setPasswordBytes(encrypted);
                  passwordHistoryEVO.setPasswordDate(now);
                  passwordHistoryEVO.setUpdatedByUserId(userPK.getUserId());
                  passwordHistoryDAO.setDetails(passwordHistoryEVO);
                  passwordHistoryDAO.create();
                  result.setResult(11);
                  return result;
               }
            }
         } catch (Exception var13) {
            var13.printStackTrace();
            result.setResult(12);
            result.setValidationErrorMsg(var13.getMessage());
            this._log.debug("Error in change password");
            return result;
         }
      }
   }

   public void disableUser(String logonId) throws EJBException {
      this.loadConfiguration();

      try {
         UserAccessor e = new UserAccessor(new InitialContext());
         UserPK userPK = e.findByUserID(logonId);
         if(userPK == null) {
            throw new ValidationException("Failed to locate user:" + logonId);
         }

         UserEVO userEVO = e.getDetails(userPK, "");
         userEVO.setUserDisabled(true);
         e.setDetails(userEVO);
      } catch (Exception var5) {
         this._log.debug("Error in disable user");
      }

   }

   public void syncCPUserDetails(UserRepository ssoPrincipal) throws EJBException, ValidationException {
      try {
         UserEditorSessionServer e = new UserEditorSessionServer(new InitialContext(), false);
         String userId = ssoPrincipal.getLogonIdentity();
         UserDAO userDAO = new UserDAO();
         UserPK userPK = null;
         userPK = userDAO.findByUserID(userId.toLowerCase());
         if(userPK == null) {
            userPK = userDAO.findByLogonAlias(userId.toLowerCase());
         }

         if(userPK == null) {
            throw new ValidationException("Failed to locate user for id:" + userId);
         } else {
            UserEditorSessionSSO userEditorSessionSSO = e.getItemData(userPK);
            UserImpl userImpl = userEditorSessionSSO.getEditorData();
            List allRoles = this.getAllRoles();
            Set ssoRoles = ssoPrincipal.getGroupMemberships(SSO_CP_ROLE_PREFIX);
            HashSet existinRoles = new HashSet(userImpl.getRoles());
            boolean rolesChanged = false;
            Iterator userAttributesChanged = ssoRoles.iterator();

            String eMailAddress;
            while(userAttributesChanged.hasNext()) {
               eMailAddress = (String)userAttributesChanged.next();
               RoleRefImpl roleRefImpl = this.findRole(allRoles, eMailAddress);
               if(roleRefImpl != null) {
                  if(!existinRoles.contains(roleRefImpl)) {
                     userImpl.getRoles().add(roleRefImpl);
                     rolesChanged = true;
                  }

                  existinRoles.remove(roleRefImpl);
               }
            }

            if(!existinRoles.isEmpty()) {
               for(userAttributesChanged = existinRoles.iterator(); userAttributesChanged.hasNext(); rolesChanged = true) {
                  RoleRefImpl eMailAddress1 = (RoleRefImpl)userAttributesChanged.next();
                  userImpl.getRoles().remove(eMailAddress1);
               }
            }

            boolean userAttributesChanged1 = false;
            eMailAddress = this.getSSOAttribute(ssoPrincipal, "mail");
            if(eMailAddress == null && userImpl.getEMailAddress() != null || eMailAddress != null && userImpl.getEMailAddress() == null || eMailAddress != null && userImpl.getEMailAddress() != null && eMailAddress.compareTo(userImpl.getEMailAddress()) != 0) {
               userImpl.setEMailAddress(eMailAddress);
               userAttributesChanged1 = true;
            }

            if(rolesChanged || userAttributesChanged1) {
               e.update(userImpl);
            }

         }
      } catch (NamingException var15) {
         throw new EJBException("Failed to access UserEditorSessionServer", var15);
      } catch (Exception var16) {
         throw new EJBException("Failed to create CP User", var16);
      }
   }

   private RoleRefImpl findRole(Collection<RoleRefImpl> roles, String name) {
      Iterator i$ = roles.iterator();

      RoleRefImpl roleRefImpl;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         roleRefImpl = (RoleRefImpl)i$.next();
      } while(!roleRefImpl.getNarrative().equals(name));

      return roleRefImpl;
   }

   private String getSSOAttribute(UserRepository ssoPrincipal, String attributeName) throws Exception {
      if(ssoPrincipal.getUserAttributes().get(attributeName) != null) {
         Set sset = (Set)ssoPrincipal.getUserAttributes().get(attributeName);
         return sset.size() > 0?(String)sset.iterator().next():null;
      } else {
         return null;
      }
   }

   public AuthenticationResult createUser(UserRepository ssoPrincipal) throws EJBException, ValidationException {
      try {
         UserEditorSessionServer e = new UserEditorSessionServer(new InitialContext(), false);
         UserEditorSessionSSO userEditorSessionSSO = e.getNewItemData();
         UserImpl userImpl = userEditorSessionSSO.getEditorData();
         String userId = ssoPrincipal.getLogonIdentity();
         userImpl.setName(userId.toLowerCase());
         if(ssoPrincipal.getFullName() != null) {
            userImpl.setFullName(ssoPrincipal.getFullName());
         } else {
            userImpl.setFullName(userId);
         }

         List allRoles = this.getAllRoles();
         Set ssoRoles = ssoPrincipal.getGroupMemberships(SSO_CP_ROLE_PREFIX);
         Iterator eMailAddress = allRoles.iterator();

         while(eMailAddress.hasNext()) {
            RoleRefImpl roleRefImpl = (RoleRefImpl)eMailAddress.next();
            String cpRoleName = roleRefImpl.getNarrative();
            if(ssoRoles.contains(cpRoleName)) {
               userImpl.getRoles().add(roleRefImpl);
            }
         }

         String eMailAddress1 = this.getSSOAttribute(ssoPrincipal, "mail");
         userImpl.setEMailAddress(eMailAddress1);
         e.insert(userImpl);
         return this.authenticateUser(ssoPrincipal);
      } catch (NamingException var11) {
         throw new EJBException("Failed to access UserEditorSessionServer", var11);
      } catch (Exception var12) {
         throw new EJBException("Failed to create CP User", var12);
      }
   }

   private List<RoleRefImpl> getAllRoles() {
      RoleDAO roleDAO = new RoleDAO();
      AllRolesELO allRolesELO = roleDAO.getAllRoles();
      ArrayList allRoles = new ArrayList();

      for(int i = 0; i < allRolesELO.getNumRows(); ++i) {
         allRoles.add((RoleRefImpl)allRolesELO.getValueAt(i, "Role"));
      }

      return allRoles;
   }

   private long calculateExpiry(UserEVO userEvo) {
      this.loadConfiguration();
      long userExpiryTime = userEvo.getPasswordDate().getTime();
      userExpiryTime += (long)this.mPasswordExpiryDays * this.M_SECONDS_IN_DAY;
      long timeNow = System.currentTimeMillis();
      return (userExpiryTime - timeNow) / this.M_SECONDS_IN_DAY;
   }

   private int getResult(AuthenticationResultImpl result, UserEVO userEVO) {
      this.loadConfiguration();
      if(userEVO.getChangePassword()) {
         return 4;
      } else if(!userEVO.getPasswordNeverExpires()) {
         long expiryDays = this.calculateExpiry(userEVO);
         if(expiryDays < 0L) {
            result.setExpiryDays(expiryDays);
            return 4;
         } else if(expiryDays <= 7L) {
            result.setExpiryDays(expiryDays);
            return 2;
         } else {
            result.setExpiryDays((long)this.mPasswordExpiryDays);
            return 1;
         }
      } else {
         result.setExpiryDays((long)this.mPasswordExpiryDays);
         return 1;
      }
   }

   private void loadSecuritySettings(AuthenticationResultImpl result, int userId) throws Exception {
      Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;
      HashMap security = new HashMap();
      HashMap roles = new HashMap();
      RoleDAO roleDao = new RoleDAO();
      RoleSecurityDAO roleSecurityDAO = new RoleSecurityDAO();
      AllRolesForUserELO roleStrings = roleDao.getAllRolesForUser(userId);
      AllRolesForUserELO hiddenRoleStrings = roleDao.getAllHiddenRolesForUser(userId);

      for(int i = 0; i < roleStrings.getNumRows(); ++i) {
         RoleRef roleRef = (RoleRef)roleStrings.getValueAt(i, "Role");
         String roleVisId = (String)roleStrings.getValueAt(i, "VisId");
         RolePK rolePK = (RolePK)roleRef.getPrimaryKey();
         AllSecurityRolesForRoleELO roleSecurities = roleSecurityDAO.getAllSecurityRolesForRole(rolePK.getRoleId());
         if(roleSecurities.getNumRows() > 0) {
            String[] securityStrings = (String[])((String[])roleSecurities.getValues("SecurityString"));
            security.put(roleVisId, new HashSet(Arrays.asList(securityStrings)));
            roles.put(roleVisId, (String)roleStrings.getValueAt(i, "Description"));
         }
      }
      
      for(int i = 0; i < hiddenRoleStrings.getNumRows(); ++i) {
          RoleRef roleRef = (RoleRef)hiddenRoleStrings.getValueAt(i, "Role");
          String roleVisId = (String)hiddenRoleStrings.getValueAt(i, "VisId");
          RolePK rolePK = (RolePK)roleRef.getPrimaryKey();
          AllSecurityRolesForRoleELO roleSecurities = roleSecurityDAO.getAllSecurityRolesForRole(rolePK.getRoleId());
          if(roleSecurities.getNumRows() > 0) {
             String[] securityStrings = (String[])((String[])roleSecurities.getValues("SecurityString"));
             security.put(roleVisId, new HashSet(Arrays.asList(securityStrings)));
             roles.put(roleVisId, (String)hiddenRoleStrings.getValueAt(i, "Description"));
          } else {
              security.put(roleVisId, new HashSet());
              roles.put(roleVisId, (String)hiddenRoleStrings.getValueAt(i, "Description"));
          }
       }

      result.setUserRoleSecurityStrings(security);
      result.setRoleDescriptions(roles);
      if(timer != null) {
         timer.logDebug("loadSecuritySettings");
      }

   }

   private void loadConfiguration() {
      this.mSystemName = SystemPropertyHelper.queryStringSystemProperty((Connection)null, "WEB: Environment Name", "Unknown");
      
      mRunLevel = SystemPropertyHelper.queryIntegerSystemProperty(null, "SYS: Run Level", 0);
      
      if(!this.mLoadedProps) {
         Timer timer = this._log.isDebugEnabled()?new Timer(this._log):null;

         try {
            AuthenticationPolicyDAO e = new AuthenticationPolicyDAO();
            ActiveAuthenticationPolicyForLogonELO aELO = e.getActiveAuthenticationPolicyForLogon();
            if(aELO.hasNext()) {
               aELO.next();
               this.mAuthenticationTechnique = aELO.getAuthenticationTechnique();
               this.mAuthSecurityLog = aELO.getSecurityLog();
               this.mAuthSecurityUserId = aELO.getSecurityAdministrator();
               this.mMinPasswordSize = aELO.getMinimumPasswordLength();
               this.mPasswordExpiryDays = aELO.getPasswordExpiry();
               if(this.mAuthenticationTechnique == 2) {
                  this.mJassAuthNAme = aELO.getJaasEntryName();
               }
            }
         } catch (Exception var4) {
            this._log.warn("loadConfiguration", var4);
            var4.printStackTrace();
         }

         this.mLoadedProps = true;
         if(timer != null) {
            timer.logDebug("loadConfiguration");
         }

      }
   }

   private String loadSystemName() {
      this.loadConfiguration();
      return this.mSystemName;
   }

   public void ejbRemove() {}

   public void ejbActivate() {}

   public void ejbPassivate() {}

   public void ejbCreate() throws EJBException {}

   public void setSessionContext(SessionContext sc) {}

}
