// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.pack;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.pack.ReportPackCK;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import com.cedar.cp.ejb.impl.report.pack.ReportPackDAO;
import com.cedar.cp.ejb.impl.report.pack.ReportPackEVO;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

public class ReportPackEEJB implements EntityBean {

   protected EntityContext mEntityContext;
   private ReportPackDAO mReportPackDAO;
   private transient Log mLog = new Log(this.getClass());


   public ReportPackPK ejbCreate(ReportPackEVO details) throws CreateException, EJBException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      ReportPackPK var4;
      try {
         ReportPackDAO e = this.getDAO();
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

   public void ejbPostCreate(ReportPackEVO details) throws CreateException, EJBException {}

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

   public ReportPackPK ejbFindByPrimaryKey(ReportPackPK pk) throws FinderException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      ReportPackPK ve;
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
      ReportPackPK pk = (ReportPackPK)this.mEntityContext.getPrimaryKey();
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
         if(timer != null) {
            timer.logDebug("ejbStore", this.getPK().toString());
         }

      }

   }

   private ReportPackPK getPK() {
      return (ReportPackPK)this.mEntityContext.getPrimaryKey();
   }

   public void ejbActivate() {}

   public void ejbPassivate() {
      this.mLog.debug("ejbPassivate", this.getPK().toString());
      this.mReportPackDAO = null;
   }

   public void setEntityContext(EntityContext entityContext) {
      this.mEntityContext = entityContext;
   }

   public void unsetEntityContext() {
      this.mEntityContext = null;
   }

   protected ReportPackDAO getDAO() {
      if(this.mReportPackDAO == null) {
         this.mReportPackDAO = new ReportPackDAO();
      }

      return this.mReportPackDAO;
   }

   public ReportPackEVO getDetails(ReportPackCK paramCK, String dependants) throws ValidationException {
      return this.getDAO().getDetails(paramCK, dependants);
   }

   public ReportPackEVO getDetails(String dependants) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      ReportPackEVO var3;
      try {
         var3 = this.getDAO().getDetails(dependants);
      } finally {
         if(timer != null) {
            timer.logDebug("getDetails", dependants);
         }

      }

      return var3;
   }

   public ReportPackEVO getDetails(ReportPackCK paramCK) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      ReportPackEVO var3;
      try {
         var3 = this.getDAO().getDetails(paramCK, "");
      } finally {
         if(timer != null) {
            timer.logDebug("getDetails", paramCK.toString());
         }

      }

      return var3;
   }

   public void setDetails(ReportPackEVO details) {
      this.getDAO().setDetails(details);
   }

   public ReportPackEVO setAndGetDetails(ReportPackEVO evo, String dependants) {
      return this.getDAO().setAndGetDetails(evo, dependants);
   }

   public ReportPackPK generateKeys() {
      return this.getDAO().generateKeys();
   }
}