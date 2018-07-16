// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.authenticationpolicy;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyCK;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyDAO;
import com.cedar.cp.ejb.impl.authenticationpolicy.AuthenticationPolicyEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

public class AuthenticationPolicyEEJB implements EntityBean {

   protected EntityContext mEntityContext;
   private AuthenticationPolicyDAO mAuthenticationPolicyDAO;
   private transient Log mLog = new Log(this.getClass());


   public AuthenticationPolicyPK ejbCreate(AuthenticationPolicyEVO details) throws CreateException, EJBException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      AuthenticationPolicyPK var4;
      try {
         AuthenticationPolicyDAO e = this.getDAO();
         e.setDetails(details);
         var4 = e.create();
      } catch (DuplicateNameValidationException var9) {
         throw new EJBException(var9);
      } catch (ValidationException var10) {
         throw new EJBException(var10);
      } finally {
         if(timer != null) {
            timer.logDebug("ejbCreate", "");
         }

      }

      return var4;
   }

   public void ejbPostCreate(AuthenticationPolicyEVO details) throws CreateException, EJBException {}

   public void ejbRemove() throws RemoveException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         this.getDAO().remove();
      } catch (Exception var6) {
         throw new CPException("unable to remove " + this.getPK());
      } finally {
         if(timer != null) {
            timer.logDebug("ejbRemove", "");
         }

      }

   }

   public AuthenticationPolicyPK ejbFindByPrimaryKey(AuthenticationPolicyPK pk) throws FinderException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      AuthenticationPolicyPK ve;
      try {
         ve = this.getDAO().findByPrimaryKey(pk);
      } catch (ValidationException var7) {
         throw new CPException(var7.getMessage(), var7);
      } finally {
         if(timer != null) {
            timer.logDebug("ejbFindByPrimaryKey", pk.toString());
         }

      }

      return ve;
   }

   public void ejbLoad() {
      AuthenticationPolicyPK pk = (AuthenticationPolicyPK)this.mEntityContext.getPrimaryKey();
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         this.getDAO().load(pk);
      } catch (ValidationException var7) {
         throw new EJBException(var7);
      } finally {
         if(timer != null) {
            timer.logDebug("ejbLoad", pk.toString());
         }

      }

   }

   public void ejbStore() throws EJBException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         this.getDAO().store();
      } catch (EJBException var7) {
         this.mLog.error("ejbStore", "failed", var7);
         throw var7;
      } catch (Exception var8) {
         this.mLog.error("ejbStore", "failed", var8);
         throw new EJBException(var8);
      } finally {
//         if(timer != null) {
//            timer.logDebug("ejbStore", this.getPK().toString());
//         }

      }

   }

   private AuthenticationPolicyPK getPK() {
      return (AuthenticationPolicyPK)this.mEntityContext.getPrimaryKey();
   }

   public void ejbActivate() {}

   public void ejbPassivate() {
      this.mLog.debug("ejbPassivate", this.getPK().toString());
      this.mAuthenticationPolicyDAO = null;
   }

   public void setEntityContext(EntityContext entityContext) {
      this.mEntityContext = entityContext;
   }

   public void unsetEntityContext() {
      this.mEntityContext = null;
   }

   protected AuthenticationPolicyDAO getDAO() {
      if(this.mAuthenticationPolicyDAO == null) {
         this.mAuthenticationPolicyDAO = new AuthenticationPolicyDAO();
      }

      return this.mAuthenticationPolicyDAO;
   }

   public AuthenticationPolicyEVO getDetails(AuthenticationPolicyCK paramCK, String dependants) throws ValidationException {
      return this.getDAO().getDetails(paramCK, dependants);
   }

   public AuthenticationPolicyEVO getDetails(String dependants) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      AuthenticationPolicyEVO var3;
      try {
         var3 = this.getDAO().getDetails(dependants);
      } finally {
         if(timer != null) {
            timer.logDebug("getDetails", dependants);
         }

      }

      return var3;
   }

   public void setDetails(AuthenticationPolicyEVO details) {
      this.getDAO().setDetails(details);
   }

   public AuthenticationPolicyEVO setAndGetDetails(AuthenticationPolicyEVO evo, String dependants) {
      return this.getDAO().setAndGetDetails(evo, dependants);
   }

   public AuthenticationPolicyPK generateKeys() {
      return this.getDAO().generateKeys();
   }
}
