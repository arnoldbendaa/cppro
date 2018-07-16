// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:35
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserPreferenceImpl;
import com.cedar.cp.dto.user.UserPreferencePK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.ejb.impl.defaultuserpref.DefaultUserPrefDAO;
import com.cedar.cp.ejb.impl.defaultuserpref.DefaultUserPrefEVO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.ejb.impl.user.UserPreferenceEVO;
import com.cedar.cp.util.Log;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class UserPreferenceEditorSessionSEJB implements SessionBean {

   private static final String DEPENDANTS_FOR_GET_ITEM_DATA = "<1>";
   Collection mDefaultPreferences;
   private SessionContext mSessionContext;
   private transient InitialContext mInitialContext;
   private transient UserAccessor mUserAccessor;
   private UserEVO mUserEVO;
   private UserPreferenceEVO mUserPreferenceEVO;
   private transient Log mLog = new Log(this.getClass());


   public Collection getUserPreferences(int userID) throws EJBException {
      UserPK uPK = new UserPK(userID);

      try {
         this.mUserEVO = this.getUserAccessor().getDetails(uPK, "<1>");
         Object e = new Vector();
         Iterator iter = this.mUserEVO.getUserPreferences().iterator();

         while(iter.hasNext()) {
            UserPreferenceEVO aDefault = (UserPreferenceEVO)iter.next();
            UserPreferenceImpl name = new UserPreferenceImpl(aDefault.getPK());
            name.setPrefName(aDefault.getPrefName());
            name.setPrefValue(aDefault.getPrefValue());
            name.setPrefType(aDefault.getPrefType());
            name.setHelpId(aDefault.getHelpId());
            name.setVersionNum(aDefault.getVersionNum());
            name.setUserRef(new UserRefImpl(this.mUserEVO.getPK(), ""));
            ((Collection)e).add(name);
         }

         this.loadDefaultUserPreferences();
         if(((Collection)e).size() == 0) {
            e = this.mDefaultPreferences;
         } else if(((Collection)e).size() < this.mDefaultPreferences.size()) {
            iter = this.mDefaultPreferences.iterator();

            while(iter.hasNext()) {
               UserPreferenceImpl aDefault1 = (UserPreferenceImpl)iter.next();
               String name1 = aDefault1.getPrefName();
               Iterator iter2 = ((Collection)e).iterator();
               UserPreferenceImpl userPref = null;
               boolean addDefault = true;

               while(iter2.hasNext()) {
                  userPref = (UserPreferenceImpl)iter2.next();
                  if(name1.compareToIgnoreCase(userPref.getPrefName()) == 0) {
                     addDefault = false;
                     break;
                  }
               }

               if(addDefault) {
                  ((Collection)e).add(aDefault1);
               }
            }
         }

         return (Collection)e;
      } catch (Exception var10) {
         var10.printStackTrace();
         throw new EJBException(var10.getMessage());
      }
   }

   private void loadDefaultUserPreferences() {
      this.mDefaultPreferences = new Vector();
      DefaultUserPrefDAO dao = new DefaultUserPrefDAO();
      Iterator iter = dao.getAllDefaultUserPreferences().iterator();

      for(int count = -1; iter.hasNext(); --count) {
         DefaultUserPrefEVO current = (DefaultUserPrefEVO)iter.next();
         UserPreferenceImpl editorData = new UserPreferenceImpl(new UserPreferencePK(count));
         editorData.setPrefName(current.getName());
         editorData.setPrefValue(current.getValue());
         editorData.setPrefType(current.getType());
         editorData.setHelpId(current.getHelpId());
         editorData.setVersionNum(0);
         if(this.mUserEVO != null) {
            editorData.setUserRef(new UserRefImpl(this.mUserEVO.getPK(), ""));
         }

         this.mDefaultPreferences.add(editorData);
      }

   }

   public void updateUserPreferences(Collection prefs) throws EJBException {
      Iterator iter = prefs.iterator();
      UserRef uRef = null;

      UserPreferenceImpl e;
      while(iter.hasNext()) {
         e = (UserPreferenceImpl)iter.next();
         if(e.getPrefStorage().compareTo("server") == 0 && e.getContentModified()) {
            uRef = e.getUserRef();
         } else {
            iter.remove();
         }
      }

      if(uRef != null && prefs.size() > 0) {
         try {
            this.mUserEVO = this.getUserAccessor().getDetails((UserPK)uRef.getPrimaryKey(), "<1>");
            iter = prefs.iterator();

            while(iter.hasNext()) {
               e = (UserPreferenceImpl)iter.next();
               int id = ((UserPreferencePK)e.getPrimaryKey()).getUserPrefId();
               UserPreferenceEVO upevo;
               if(id > 0) {
                  upevo = this.mUserEVO.getUserPreferencesItem((UserPreferencePK)e.getPrimaryKey());
                  upevo.setPrefValue(e.getPrefValue());
               } else {
                  upevo = new UserPreferenceEVO();
                  upevo.setUserPrefId(((UserPreferencePK)e.getPrimaryKey()).getUserPrefId());
                  upevo.setUserId(this.mUserEVO.getUserId());
                  upevo.setPrefName(e.getPrefName());
                  upevo.setPrefValue(e.getPrefValue());
                  upevo.setPrefType(e.getPrefType());
                  upevo.setHelpId(e.getHelpId());
                  this.mUserEVO.addUserPreferencesItem(upevo);
               }
            }

            this.getUserAccessor().setDetails(this.mUserEVO);
         } catch (Exception var7) {
            var7.printStackTrace();
            throw new EJBException(var7.getMessage());
         }
      }

   }

   public Collection getDefaultUserPreferences() throws EJBException {
      this.loadDefaultUserPreferences();
      return this.mDefaultPreferences;
   }

   public void updateDefaultUserPreferences(Collection prefs) throws EJBException {
      this.mDefaultPreferences = new Vector();
      DefaultUserPrefDAO dao = new DefaultUserPrefDAO();
      this.mDefaultPreferences = dao.getAllDefaultUserPreferences();
      Iterator iter = prefs.iterator();

      while(iter.hasNext()) {
         UserPreferenceImpl pref = (UserPreferenceImpl)iter.next();
         if(pref.getContentModified()) {
            Iterator iter2 = this.mDefaultPreferences.iterator();

            while(iter2.hasNext()) {
               DefaultUserPrefEVO evo = (DefaultUserPrefEVO)iter2.next();
               if(pref.getPrefName().compareToIgnoreCase(evo.getName()) == 0) {
                  evo.setValue(pref.getPrefValue());
                  dao.setDetails(evo);

                  try {
                     dao.store();
                  } catch (Exception var8) {
                     this.mLog.debug("EXCEPTION  = " + var8.getMessage());
                     var8.printStackTrace();
                  }
                  break;
               }
            }
         }
      }

   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {
      this.mLog.debug("[debug] UserPreferenceEditorSessionSEJB activate");
   }

   public void ejbPassivate() {
      this.mLog.debug("[debug] UserPreferenceEditorSessionSEJB passivate");
   }

   private InitialContext getInitialContext() throws Exception {
      if(this.mInitialContext == null) {
         this.mInitialContext = new InitialContext();
      }

      return this.mInitialContext;
   }

   private UserAccessor getUserAccessor() throws Exception {
      if(this.mUserAccessor == null) {
         this.mUserAccessor = new UserAccessor(this.getInitialContext());
      }

      return this.mUserAccessor;
   }
}
