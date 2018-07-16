// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:36
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.extsys.AllExternalSystemCompainesELO;
import com.cedar.cp.dto.extsys.AllExternalSystemsELO;
import com.cedar.cp.dto.extsys.AllGenericExternalSystemsELO;
import com.cedar.cp.dto.extsys.ExternalSystemCK;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyDAO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemLocal;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ExternalSystemAccessor implements Serializable {

   private ExternalSystemDAO mExternalSystemDAO;
   private ExternalSystemLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_EXT_SYS_COMPANIES = "<0>";
   public static final String GET_EXT_SYS_LEDGER = "<1>";
   public static final String GET_EXT_SYS_DIMENSIONS = "<2>";
   public static final String GET_EXT_SYS_DIM_ELEMENTS = "<3>";
   public static final String GET_EXT_SYS_HIERARCHIES = "<4>";
   public static final String GET_EXT_SYS_HIER_ELEMENTS = "<5>";
   public static final String GET_EXT_SYS_HIER_ELEM_FEEDS = "<6>";
   public static final String GET_EXT_SYS_VALUE_TYPES = "<7>";
   public static final String GET_EXT_SYS_CURRENCIES = "<8>";
   public static final String GET_EXT_SYS_CALENDAR_YEARS = "<9>";
   public static final String GET_EXT_SYS_CALENDAR_ELEMENTS = "<10>";
   public static final String GET_EXT_SYS_PROPERTIES = "<11>";
   public static final String GET_ALL_DEPENDANTS = "<0><1><2><3><4><5><6><7><8><9><10><11>";

   ExternalSystemEEJB externalSystemEjb = new ExternalSystemEEJB();
   public ExternalSystemAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ExternalSystemLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ExternalSystemLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ExternalSystemLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ExternalSystemLocalHome", var2);
      }
   }

   private ExternalSystemLocal getLocal(ExternalSystemPK pk) throws Exception {
      ExternalSystemLocal local = (ExternalSystemLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public ExternalSystemEVO create(ExternalSystemEVO evo) throws Exception {
//      ExternalSystemLocal local = this.getLocalHome().create(evo);
//      ExternalSystemEVO newevo = local.getDetails("<UseLoadedEVOs>");
//      ExternalSystemPK pk = newevo.getPK();
//      this.mLocals.put(pk, local);
      
      externalSystemEjb.ejbCreate(evo);
      ExternalSystemEVO newevo = externalSystemEjb.getDetails("<UseLoadedEVOs>");
      ExternalSystemPK pk = newevo.getPK();
      this.mLocals.put(pk, externalSystemEjb);
      return newevo;
   }

   public void remove(ExternalSystemPK pk) throws Exception {
//      this.getLocal(pk).remove();
	   externalSystemEjb.ejbRemove();
   }

   public ExternalSystemEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof ExternalSystemCK) {
         ExternalSystemPK pk = ((ExternalSystemCK)key).getExternalSystemPK();
         return this.getLocal(pk).getDetails((ExternalSystemCK)key, dependants);
      } else {
//         return key instanceof ExternalSystemPK?this.getLocal((ExternalSystemPK)key).getDetails(dependants):null;
    	  ExternalSystemPK pk = (ExternalSystemPK)key;
    	  ExternalSystemCK externalSystemCk = new ExternalSystemCK(pk);
    	  
    	  if(key instanceof ExternalSystemPK)
    		  return externalSystemEjb.getDetails(externalSystemCk,dependants);
    	  else
    		  return null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(ExternalSystemEVO evo) throws Exception {
      ExternalSystemPK pk = evo.getPK();
      externalSystemEjb.ejbFindByPrimaryKey(pk);
      externalSystemEjb.setDetails(evo);
      externalSystemEjb.ejbStore();
//      this.getLocal(pk).setDetails(evo);
   }

   public ExternalSystemEVO setAndGetDetails(ExternalSystemEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ExternalSystemPK generateKeys(ExternalSystemPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllExternalSystemsELO getAllExternalSystems() {
      ExternalSystemDAO dao = new ExternalSystemDAO();
      return dao.getAllExternalSystems();
   }

   public AllGenericExternalSystemsELO getAllGenericExternalSystems() {
      ExternalSystemDAO dao = new ExternalSystemDAO();
      return dao.getAllGenericExternalSystems();
   }

   public AllExternalSystemCompainesELO getAllExternalSystemCompaines() {
      ExtSysCompanyDAO dao = new ExtSysCompanyDAO();
      return dao.getAllExternalSystemCompaines();
   }

   public EntityList getCompanies(int systemId, int systemType) {
      return this.getExternalSystemDAO().getCompanies(systemId, systemType);
   }

   public EntityList getFinanceLedgers(int systemId, int systemType, String company) {
      return this.getExternalSystemDAO().getFinanceLedgers(systemId, systemType, company);
   }

   public EntityList getFinanceCalendarYears(int systemId, int systemType, String company) {
      return this.getExternalSystemDAO().getFinanceCalendarYears(systemId, systemType, company);
   }
   
   public EntityList getGlobalFinanceCalendarYears(int systemId, int systemType, List<String> companies) {
	   return this.getExternalSystemDAO().getGlobalFinanceCalendarYears(systemId, systemType, companies);
   }

   public EntityList getFinancePeriods(int systemId, int systemType, String company, int year) {
      return this.getExternalSystemDAO().getFinancePeriods(systemId, systemType, company, year);
   }

   public EntityList getFinanceDimensions(int systemId, int systemType, String company, String ledger) {
      return this.getExternalSystemDAO().getFinanceDimensions(systemId, systemType, company, ledger);
   }
   
   public EntityList getGlobalFinanceDimensions(int systemId, int systemType, List<String> companies, String ledger) {
      return this.getExternalSystemDAO().getGlobalFinanceDimensions(systemId, systemType, companies, ledger);
   }

   public EntityList getFinanceValueTypes(int systemId, int systemType, String company, String ledger, String dimCodes, int startYear, int cursorNum) {
      return this.getExternalSystemDAO().getFinanceValueTypes(systemId, systemType, company, ledger, dimCodes, startYear, cursorNum);
   }

   public EntityList getGlobalFinanceValueTypes(int systemId, int systemType, List<String> companies, String ledger, String dimCodes, int startYear, int cursorNum) {
	   return this.getExternalSystemDAO().getGlobalFinanceValueTypes(systemId, systemType, companies, ledger, dimCodes, startYear, cursorNum);
   }

   public EntityList getFinanceHierarchies(int systemId, int systemType, String company, String ledger, String extSysDimType, String dimCode) {
      return this.getExternalSystemDAO().getFinanceHierarchies(systemId, systemType, company, ledger, extSysDimType, dimCode);
   }
   
   public EntityList getGlobalFinanceHierarchies(int systemId, int systemType, List<String> companies, String ledger, String extSysDimType, String dimCode) {
      return this.getExternalSystemDAO().getGlobalFinanceHierarchies(systemId, systemType, companies, ledger, extSysDimType, dimCode);
   }
   
   public EntityList getFinanceDimElementGroups(int systemId, int systemType, String company, String ledger, String extSysDimType, String dimCode, int parentType, String parent, String accType) {
      return this.getExternalSystemDAO().getFinanceDimElementGroups(systemId, systemType, company, ledger, extSysDimType, dimCode, parentType, parent, accType);
   }

   public EntityList getGlobalFinanceDimElementGroups(int systemId, int systemType, List<String> companies, String ledger, String extSysDimType, String dimCode, int parentType, String parent, String accType) {
      return this.getExternalSystemDAO().getGlobalFinanceDimElementGroups(systemId, systemType, companies, ledger, extSysDimType, dimCode, parentType, parent, accType);
   }
   
   public EntityList getFinanceHierarchyElems(int systemId, int systemType, String company, String ledger, String extSysDimType, String dimCode, String hierName, String hierType, String parent) {
      return this.getExternalSystemDAO().getFinanceHierarchyElems(systemId, systemType, company, ledger, extSysDimType, dimCode, hierName, hierType, parent);
   }
   
   public EntityList getGlobalFinanceHierarchyElems(int systemId, int systemType, List<String> companies, String ledger, String extSysDimType, String dimCode, String hierName, String hierType, String parent) {
	   return this.getExternalSystemDAO().getGlobalFinanceHierarchyElems(systemId, systemType, companies, ledger, extSysDimType, dimCode, hierName, hierType, parent);
   }

   public String getSuggestedModelVisId(int systemId, int systemType, String company) {
      return this.getExternalSystemDAO().getSuggestedModelVisId(systemId, systemType, company);
   }

   private ExternalSystemDAO getExternalSystemDAO() {
      if(this.mExternalSystemDAO == null) {
         this.mExternalSystemDAO = new ExternalSystemDAO();
      }

      return this.mExternalSystemDAO;
   }
}
