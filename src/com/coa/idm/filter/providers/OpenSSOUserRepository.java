// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:31:44
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.coa.idm.filter.providers;

import com.coa.idm.UserRepository;
import com.coa.idm.UserRepositoryException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class OpenSSOUserRepository implements UserRepository {

//   private SSOToken mSSOToken;
   private Principal mCurrentUser;
   private String mLogonIdentity;
   private Map<String, Set<String>> mMemberships = new HashMap();


   public OpenSSOUserRepository(String logonIdentity) {
      this.mLogonIdentity = logonIdentity;
   }

   public String getLogonIdentity() {
      return this.mLogonIdentity;
   }

   public String getLastName() throws Exception {
      return this.findUserAttribute("sn");
   }

   public String getFirstName() throws Exception {
      return this.findUserAttribute("givenname");
   }

   public String getFullName() throws Exception {
      return this.findUserAttribute("cn");
   }

   public String getEMail() throws Exception {
      return this.findUserAttribute("mail");
   }

   public String getTelephoneNumber() throws Exception {
      return this.findUserAttribute("telephonenumber");
   }

   void createSSOToken(HttpServletRequest servletRequest) throws Exception {
//      SSOTokenManager manager = SSOTokenManager.getInstance();
//      this.mSSOToken = manager.createSSOToken(servletRequest);
//      this.setCurrentUser(this.mSSOToken.getPrincipal());
      this.mMemberships = new HashMap();
   }

   void createSSOToken(String token) throws Exception {
//      SSOTokenManager manager = SSOTokenManager.getInstance();
//      this.mSSOToken = manager.createSSOToken(token);
//      this.setCurrentUser(this.mSSOToken.getPrincipal());
      this.mMemberships = new HashMap();
   }

   private String findUserAttribute(String key) throws Exception {
      Map attributes = this.getUserAttributes();
      if(attributes != null) {
         Set setAttrs = (Set)attributes.get(key);
         if(setAttrs != null && !setAttrs.isEmpty()) {
            return (String)setAttrs.iterator().next();
         }
      }

      return null;
   }

   public void setCurrentUser(Principal user) {
      this.mCurrentUser = user;
   }

   public Principal getCurrentUser() throws UserRepositoryException {
      return this.mCurrentUser;
   }

@Override
public Map<String, Set<String>> getUserAttributes() throws Exception {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setUserAttributes(Map<String, Set<String>> var1) throws Exception {
	// TODO Auto-generated method stub
	
}

@Override
public Set<String> getGroupMemberships(String var1) throws Exception {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void refreshSession() throws Exception {
	// TODO Auto-generated method stub
	
}

@Override
public boolean isStillValid() throws Exception {
	// TODO Auto-generated method stub
	return false;
}

//   public Set<String> getGroupMemberships(String prefixFilter) throws Exception {
//      if(this.mSSOToken == null) {
//         return Collections.emptySet();
//      } else {
//         if(prefixFilter != null) {
//            Set userIdentity = (Set)this.mMemberships.get(prefixFilter);
//            if(userIdentity != null) {
//               return userIdentity;
//            }
//         }
//
//         AMIdentity userIdentity1 = IdUtils.getIdentity(this.mSSOToken);
//         Set groups = userIdentity1.getMemberships(IdType.GROUP);
//         HashSet groupIds = new HashSet();
//         StringBuffer matchingFormat = new StringBuffer("id=");
//         if(prefixFilter != null) {
//            matchingFormat.append(prefixFilter);
//         }
//
//         matchingFormat.append("([^,]*),.*");
//         Pattern pattern = Pattern.compile(matchingFormat.toString());
//         Iterator i$ = groups.iterator();
//
//         while(i$.hasNext()) {
//            AMIdentity identity = (AMIdentity)i$.next();
//            Matcher matcher = pattern.matcher(identity.getUniversalId());
//            if(matcher.matches()) {
//               String match = matcher.groupCount() > 0?matcher.group(1):matcher.group();
//               groupIds.add(match);
//            }
//         }
//
//         if(prefixFilter != null) {
//            this.mMemberships.put(prefixFilter, groupIds);
//         }
//
//         return groupIds;
//      }
//   }
//
//   public Map<String, Set<String>> getUserAttributes() throws Exception {
//      if(this.mSSOToken == null) {
//         return null;
//      } else {
//         AMIdentity userIdentity = IdUtils.getIdentity(this.mSSOToken);
//         return userIdentity.getAttributes();
//      }
//   }

//   public void setUserAttributes(Map<String, Set<String>> attributes) throws Exception {
//      if(this.mSSOToken == null) {
//         throw new IllegalStateException("No connection to repository");
//      } else {
//         AMIdentity userIdentity = IdUtils.getIdentity(this.mSSOToken);
//         userIdentity.setAttributes(attributes);
//         userIdentity.store();
//      }
//   }
//
//   public void refreshSession() throws Exception {
//      SSOTokenManager.getInstance().refreshSession(this.mSSOToken);
//   }
//
//   public boolean isStillValid() throws Exception {
//      SSOTokenManager manager = SSOTokenManager.getInstance();
//      return manager.isValidToken(this.mSSOToken);
//   }
}
