// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping.extsys;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.mapping.extsys.ExternalSystem;
import com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.mapping.extsys.FinanceCompany;
import com.cedar.cp.api.model.mapping.extsys.FinanceCurrency;
import com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimension;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup;
import com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy;
import com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement;
import com.cedar.cp.api.model.mapping.extsys.FinanceLedger;
import com.cedar.cp.api.model.mapping.extsys.FinanceValueType;
import com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.model.mapping.extsys.ExternalSystemImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinanceCalendarYearImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinanceCompanyImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinanceCurrencyGroupImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinanceCurrencyImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinanceDimensionElementGroupImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinanceDimensionElementImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinanceDimensionImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinanceHierarchyElementImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinanceHierarchyImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinanceLedgerImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinancePeriodImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinanceValueTypeImpl;
import com.cedar.cp.dto.model.mapping.extsys.FinanceValueTypeOwnerImpl;
import com.cedar.cp.ejb.api.extsys.ExternalSystemEditorSessionServer;
import com.cedar.cp.impl.base.CPConnectionImpl;
import com.cedar.cp.impl.model.mapping.extsys.AbstractExtSysProxy$CurrencyInfo;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public abstract class AbstractExtSysProxy implements InvocationHandler {

   private ExternalSystemEditorSessionServer mServer;
   private CPConnectionImpl mCPConnection;
   private Object mTarget;


   public abstract Object newInstance(CPConnectionImpl var1, Object var2);

   protected AbstractExtSysProxy(CPConnectionImpl connection, Object target) {
      this.mCPConnection = connection;
      this.mTarget = target;
   }

   public ExternalSystemEditorSessionServer getServer() {
      if(this.mServer == null) {
         this.mServer = new ExternalSystemEditorSessionServer(this.mCPConnection);
      }

      return this.mServer;
   }

   public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
      try {
         Object result;
         if(this.mTarget instanceof ExternalSystem) {
            result = this.dispatchExternalSystemCall(proxy, m, args);
         } else if(this.mTarget instanceof FinanceCompany) {
            result = this.dispatchFinanceCompanyCall(proxy, m, args);
         } else if(this.mTarget instanceof FinanceLedger) {
            result = this.dispatchFinanceLedgerCall(proxy, m, args);
         } else if(this.mTarget instanceof FinanceDimension) {
            result = this.dispatchFinanceDimensionCall(proxy, m, args);
         } else if(this.mTarget instanceof FinanceHierarchy) {
            result = this.dispatchFinanceHierarchyCall(proxy, m, args);
         } else if(this.mTarget instanceof FinanceHierarchyElement) {
            result = this.dispatchFinanceHierarchyElementCall(proxy, m, args);
         } else if(this.mTarget instanceof FinanceDimensionElement) {
            result = this.dispatchFinanceDimensionElementCall(proxy, m, args);
         } else if(this.mTarget instanceof FinanceDimensionElementGroup) {
            result = this.dispatchFinanceDimensionElementGroupCall(proxy, m, args);
         } else if(this.mTarget instanceof FinanceCalendarYear) {
            result = this.dispatchFinanceCalendarYearCall(proxy, m, args);
         } else if(this.mTarget instanceof FinanceValueType) {
            result = this.dispatchFinanceValueTypeCall(proxy, m, args);
         } else if(this.mTarget instanceof FinanceValueTypeOwner) {
            result = this.dispatchFinanceValueTypeOwnerCall(proxy, m, args);
         } else if(this.mTarget instanceof FinanceCurrencyGroup) {
            result = this.dispatchFinanceCurrencyGroupCall(proxy, m, args);
         } else {
            if(!(this.mTarget instanceof FinanceCurrency)) {
               throw new IllegalStateException("Unexpected target for AbstractExtSysProxy:" + this.mTarget.getClass());
            }

            result = this.dispatchFinanceCurrencyCall(proxy, m, args);
         }

         return result;
      } catch (InvocationTargetException var6) {
         throw var6.getTargetException();
      } catch (Exception var7) {
         var7.printStackTrace();
         throw new RuntimeException("unexpected invocation exception in " + this.mTarget.getClass().getName() + ": " + var7.getMessage());
      }
   }

   protected Object dispatchExternalSystemCall(Object proxy, Method m, Object[] args) throws Throwable {
      if(m.getName().equals("getCompanies")) {
         ExternalSystemImpl extSysImpl = (ExternalSystemImpl)this.getTarget();
         if(extSysImpl.getCompanies() == null) {
            this.getCompanies(extSysImpl);
         }
      }

      return m.invoke(this.getTarget(), args);
   }

   private void getCompanies(ExternalSystemImpl extSysImpl) throws ValidationException {
      ExternalSystemPK espk = (ExternalSystemPK)extSysImpl.getEntityRef().getPrimaryKey();
      EntityList extsysList = this.getServer().getCompanies(espk.getExternalSystemId(), extSysImpl.getSystemType());
      ArrayList myList = new ArrayList();

      for(int i = 0; i < extsysList.getNumRows(); ++i) {
         String company = (String)extsysList.getValueAt(i, "CMPY");
         String descr = (String)extsysList.getValueAt(i, "DESCR");
         boolean isDummy = extsysList.getValueAt(i, "IS_DUMMY").equals("Y");
         String accTypes = (String)extsysList.getValueAt(i, "ACCTYPES");
         String[] accTypesSplit = accTypes.split(":");
         HashMap accTypesMap = new HashMap();

         for(int hierTypes = 0; hierTypes < accTypesSplit.length; hierTypes += 2) {
            accTypesMap.put(accTypesSplit[hierTypes], accTypesSplit[hierTypes + 1]);
         }

         String var16 = (String)extsysList.getValueAt(i, "HIERTYPES");
         HashMap hierTypesMap = new HashMap();
         if(var16 != null) {
            String[] impl = var16.split(":");

            for(int j = 0; j < impl.length; j += 2) {
               hierTypesMap.put(impl[j], impl[j + 1]);
            }
         }

         FinanceCompanyImpl var17 = new FinanceCompanyImpl(company, descr, accTypesMap, hierTypesMap);
         var17.setDummy(isDummy);
         var17.setExternalSystem(extSysImpl);
         myList.add((FinanceCompany)this.newInstance(this.getCPConnection(), var17));
      }

      extSysImpl.setCompanies(myList);
   }

   protected Object dispatchFinanceCompanyCall(Object proxy, Method m, Object[] args) throws Throwable {
      FinanceCompanyImpl cmpyImpl = (FinanceCompanyImpl)this.getTarget();
      if(m.getName().equals("getFinanceCalendarYears")) {
         if(cmpyImpl.getFinanceCalendarYears() == null) {
            this.getFinanceCalendarYears(cmpyImpl);
         }
      } else if(m.getName().equals("getFinanceLedgers")) {
         if(cmpyImpl.getFinanceLedgers() == null) {
            this.getFinanceLedgers(cmpyImpl);
         }
      } else if(m.getName().equals("getSuggestedCPModelVisId")) {
         if(cmpyImpl.getSuggestedCPModelVisId() == null) {
            this.getSuggestedCPModelVisId(cmpyImpl);
         }
      } else if(m.getName().equals("toString")) {
         return cmpyImpl.getEntityRef().getNarrative();
      }

      return m.invoke(this.getTarget(), args);
   }

   private void getFinanceCalendarYears(FinanceCompanyImpl finCompany) throws ValidationException {
      ExternalSystem extSys = finCompany.getExternalSystem();
      ExternalSystemPK espk = (ExternalSystemPK)extSys.getEntityRef().getPrimaryKey();
      String company = finCompany.getCompanyVisId();
      EntityList extsysList = this.getServer().getFinanceCalendarYears(espk.getExternalSystemId(), extSys.getSystemType(), company);
      ArrayList myList = new ArrayList();

      for(int i = 0; i < extsysList.getNumRows(); ++i) {
         Integer year = (Integer)extsysList.getValueAt(i, "YEAR");
         String descr = (String)extsysList.getValueAt(i, "DESCR");
         FinanceCalendarYearImpl impl = new FinanceCalendarYearImpl(String.valueOf(year), descr);
         impl.setYear(year.intValue());
         impl.setFinanceCompany(finCompany);
         myList.add((FinanceCalendarYear)this.newInstance(this.getCPConnection(), impl));
      }

      finCompany.setFinanceCalendarYears(myList);
   }

   private void getSuggestedCPModelVisId(FinanceCompanyImpl finCompany) throws ValidationException {
      ExternalSystem extSys = finCompany.getExternalSystem();
      ExternalSystemPK espk = (ExternalSystemPK)extSys.getEntityRef().getPrimaryKey();
      String company = finCompany.getCompanyVisId();
      finCompany.setSuggestedCPModelVisId(this.getServer().getSuggestedModelVisId(espk.getExternalSystemId(), extSys.getSystemType(), company));
   }

   private void getFinanceLedgers(FinanceCompanyImpl finCompany) throws ValidationException {
      ExternalSystem extSys = finCompany.getExternalSystem();
      ExternalSystemPK espk = (ExternalSystemPK)extSys.getEntityRef().getPrimaryKey();
      String company = finCompany.getCompanyVisId();
      EntityList extsysList = this.getServer().getFinanceLedgers(espk.getExternalSystemId(), extSys.getSystemType(), company);
      ArrayList myList = new ArrayList();

      for(int i = 0; i < extsysList.getNumRows(); ++i) {
         String ledger = (String)extsysList.getValueAt(i, "LEDGER");
         String descr = (String)extsysList.getValueAt(i, "DESCR");
         ledger = ledger.trim();
         boolean isDummy = extsysList.getValueAt(i, "IS_DUMMY").equals("Y");
         FinanceLedgerImpl impl = new FinanceLedgerImpl(ledger, descr);
         impl.setDummy(isDummy);
         impl.setFinanceCompany(finCompany);
         myList.add((FinanceLedger)this.newInstance(this.getCPConnection(), impl));
      }

      finCompany.setFinanceLedgers(myList);
   }

   protected Object dispatchFinanceLedgerCall(Object proxy, Method m, Object[] args) throws Throwable {
      FinanceLedgerImpl ledgerImpl = (FinanceLedgerImpl)this.getTarget();
      if(!m.getName().equals("getFinanceDimensions") && !m.getName().equals("findFinanceDimensions")) {
         if(m.getName().equals("getFinanceValueTypes")) {
            if(ledgerImpl.getFinanceValueTypes() == null) {
               this.getFinanceValueTypes(ledgerImpl);
            }
         } else if(m.getName().equals("toString")) {
            return ledgerImpl.isDummy()?ledgerImpl.getFinanceCompany().getEntityRef().getNarrative():ledgerImpl.getEntityRef().getNarrative();
         }
      } else if(ledgerImpl.getFinanceDimensions() == null) {
         this.getFinanceDimensions(ledgerImpl);
      }

      return m.invoke(this.getTarget(), args);
   }

   private void getFinanceDimensions(FinanceLedgerImpl finLedger) throws ValidationException {
      FinanceCompany company = finLedger.getFinanceCompany();
      String companyCode = company.getCompanyVisId();
      ExternalSystem extSys = company.getExternalSystem();
      ExternalSystemPK espk = (ExternalSystemPK)extSys.getEntityRef().getPrimaryKey();
      EntityList extsysList = this.getServer().getFinanceDimensions(espk.getExternalSystemId(), extSys.getSystemType(), companyCode, finLedger.getLedgerVisId());
      ArrayList myList = new ArrayList();

      for(int i = 0; i < extsysList.getNumRows(); ++i) {
         String dim = (String)extsysList.getValueAt(i, "DIM");
         String descr = (String)extsysList.getValueAt(i, "DESCR");
         int dimType = ((Integer)extsysList.getValueAt(i, "DIM_TYPE")).intValue();
         String extSysDimType = (String)extsysList.getValueAt(i, "EXT_SYS_DIM_TYPE");
         String dimMask = (String)extsysList.getValueAt(i, "DIM_MASK");
         Integer dimMaskLen = (Integer)extsysList.getValueAt(i, "DIM_MASK_LEN");
         String suggestedCPVisId = ((String)extsysList.getValueAt(i, "SUGGESTED_CP_VIS_ID")).trim();
         boolean isMandatory = ((String)extsysList.getValueAt(i, "IS_MANDATORY")).equals("Y");
         if(dimMask != null) {
            while(dimMask.length() < dimMaskLen.intValue()) {
               dimMask = dimMask + " ";
            }

            dimMask = dimMask.substring(0, dimMaskLen.intValue());
         }

         boolean isDummy = extsysList.getValueAt(i, "IS_DUMMY").equals("Y");
         FinanceDimensionImpl impl = new FinanceDimensionImpl(dim, descr);
         impl.setDimensionType(dimType);
         impl.setExtSysDimensionType(extSysDimType);
         impl.setDimensionMask(dimMask);
         impl.setDummy(isDummy);
         impl.setFinanceLedger(finLedger);
         impl.setSuggestedCPVisId(suggestedCPVisId);
         impl.setIsMandatory(isMandatory);
         myList.add((FinanceDimension)this.newInstance(this.getCPConnection(), impl));
      }

      finLedger.setFinanceDimensions(myList);
   }

   private void getFinanceValueTypes(FinanceLedgerImpl finLedger) throws ValidationException {
      FinanceCompany company = finLedger.getFinanceCompany();
      String companyCode = company.getCompanyVisId();
      ExternalSystem extSys = company.getExternalSystem();
      ExternalSystemPK espk = (ExternalSystemPK)extSys.getEntityRef().getPrimaryKey();
      List selectedDimensions = finLedger.getSelectedDimensions();
      String dimCodes = "";

      FinanceDimension currMap;
      for(Iterator startYear = selectedDimensions.iterator(); startYear.hasNext(); dimCodes = dimCodes + currMap.getDimensionVisId()) {
         currMap = (FinanceDimension)startYear.next();
      }

      int var27 = finLedger.getFirstMappedYear();
      TreeMap var28 = new TreeMap();
      TreeMap usedCurrencies = null;
      ArrayList usedValueTypeList = new ArrayList();
      ArrayList unusedValueTypeList = new ArrayList();
      FinanceValueTypeImpl fvImpl = null;
      String prevValueType = "";
      boolean isCurrencyUsageSupplied = false;
      boolean isCurrencyApplicable = false;
      boolean isBalTypeApplicable = false;

      for(int actualUnusedValueTypeList = 0; actualUnusedValueTypeList < 2; ++actualUnusedValueTypeList) {
         EntityList fvto = this.getServer().getFinanceValueTypes(espk.getExternalSystemId(), extSys.getSystemType(), companyCode, finLedger.getLedgerVisId(), dimCodes, var27, actualUnusedValueTypeList);

         for(int unused = 0; unused < fvto.getNumRows(); ++unused) {
            int isUsed = ((Integer)fvto.getValueAt(unused, "RTYPE")).intValue();
            String i$ = (String)fvto.getValueAt(unused, "BAL");
            String used = (String)fvto.getValueAt(unused, "DESCR");
            String currency = (String)fvto.getValueAt(unused, "CURR");
            boolean isNonBase = ((Integer)fvto.getValueAt(unused, "IS_NON_BASE")).intValue() == 1;
            if(isUsed == 1) {
               var28.put(currency, new AbstractExtSysProxy$CurrencyInfo(this, used, isNonBase));
            } else if(isUsed == 2 || isUsed == 3) {
               if(!i$.equals(prevValueType)) {
                  this.finishOffValueTypeItem(isCurrencyUsageSupplied, fvImpl, isCurrencyApplicable, isBalTypeApplicable, var28, usedCurrencies);
                  prevValueType = i$;
                  isCurrencyUsageSupplied = ((Integer)fvto.getValueAt(unused, "IS_CURRENCY_USAGE_SUPPLIED")).intValue() == 1;
                  isCurrencyApplicable = ((Integer)fvto.getValueAt(unused, "IS_CURRENCY_APPLICABLE")).intValue() == 1;
                  isBalTypeApplicable = ((Integer)fvto.getValueAt(unused, "IS_BALTYPE_APPLICABLE")).intValue() == 1;
                  fvImpl = new FinanceValueTypeImpl(i$, used);
                  fvImpl.setFinanceCurrencies(new ArrayList());
                  fvImpl.setFinanceCurrencyGroups(new ArrayList());
                  if(isUsed == 2) {
                     usedValueTypeList.add((FinanceValueType)this.newInstance(this.getCPConnection(), fvImpl));
                  } else {
                     unusedValueTypeList.add((FinanceValueType)this.newInstance(this.getCPConnection(), fvImpl));
                  }

                  usedCurrencies = new TreeMap();
               }

               if(isUsed == 2 && isCurrencyUsageSupplied) {
                  if(currency == null) {
                     this.addNoCurrencyToValueType(fvImpl, currency);
                  } else if(!isNonBase) {
                     this.addBaseCurrencyToValueType(fvImpl, currency);
                  } else {
                     AbstractExtSysProxy$CurrencyInfo currInfo = (AbstractExtSysProxy$CurrencyInfo)var28.get(currency.trim());
                     if(currInfo != null) {
                        usedCurrencies.put(currency, currInfo);
                     }
                  }
               }
            }
         }
      }

      this.finishOffValueTypeItem(isCurrencyUsageSupplied, fvImpl, isCurrencyApplicable, isBalTypeApplicable, var28, usedCurrencies);
      finLedger.setFinanceValueTypes(usedValueTypeList);
      finLedger.setFinanceValueTypeOwners(new ArrayList());
      ArrayList var30 = new ArrayList();
      Iterator var29 = unusedValueTypeList.iterator();

      while(var29.hasNext()) {
         FinanceValueType var31 = (FinanceValueType)var29.next();
         boolean var35 = false;
         Iterator var33 = usedValueTypeList.iterator();

         while(var33.hasNext()) {
            FinanceValueType var34 = (FinanceValueType)var33.next();
            if(var34.getEntityRef().equals(var31.getEntityRef())) {
               var35 = true;
            }
         }

         if(!var35) {
            var30.add(var31);
         }
      }

      if(var30.size() > 0) {
         FinanceValueTypeOwnerImpl var32 = new FinanceValueTypeOwnerImpl("unused value types", "unused value types");
         var32.setFinanceValueTypes(var30);
         finLedger.getFinanceValueTypeOwners().add((FinanceValueTypeOwner)this.newInstance(this.getCPConnection(), var32));
      }

   }

   private void finishOffValueTypeItem(boolean isCurrencyUsageSupplied, FinanceValueTypeImpl fvImpl, boolean isCurrencyApplicable, boolean isBalTypeApplicable, Map<String, AbstractExtSysProxy$CurrencyInfo> currMap, Map<String, AbstractExtSysProxy$CurrencyInfo> usedCurrencies) {
      if(fvImpl != null) {
         FinanceCurrencyGroupImpl typeBGroup;
         FinanceCurrencyGroupImpl typeCGroup;
         if(isCurrencyUsageSupplied) {
            if(isCurrencyApplicable) {
               if(!isBalTypeApplicable) {
                  this.addUsedCurrenciesToValueType(fvImpl, " ", usedCurrencies);
                  typeBGroup = this.createCurrencyGroup("unused currencies", (FinanceCurrencyGroup)null);
                  this.addUnusedCurrenciesToGroup(fvImpl, typeBGroup, currMap, " ", usedCurrencies);
                  if(typeBGroup.getFinanceCurrencies().size() > 0) {
                     fvImpl.getFinanceCurrencyGroups().add((FinanceCurrencyGroup)this.newInstance(this.getCPConnection(), typeBGroup));
                  }
               } else {
                  typeBGroup = this.createCurrencyGroup("used currencies - base values", (FinanceCurrencyGroup)null);
                  this.addUsedCurrenciesToGroup(fvImpl, typeBGroup, "B", usedCurrencies);
                  if(typeBGroup.getFinanceCurrencies().size() > 0) {
                     fvImpl.getFinanceCurrencyGroups().add((FinanceCurrencyGroup)this.newInstance(this.getCPConnection(), typeBGroup));
                  }

                  typeCGroup = this.createCurrencyGroup("used currencies - currency values", (FinanceCurrencyGroup)null);
                  this.addUsedCurrenciesToGroup(fvImpl, typeCGroup, "C", usedCurrencies);
                  if(typeCGroup.getFinanceCurrencies().size() > 0) {
                     fvImpl.getFinanceCurrencyGroups().add((FinanceCurrencyGroup)this.newInstance(this.getCPConnection(), typeCGroup));
                  }

                  FinanceCurrencyGroupImpl currency = this.createCurrencyGroup("unused currencies", (FinanceCurrencyGroup)null);
                  FinanceCurrencyGroupImpl info = this.createCurrencyGroup("base values", (FinanceCurrencyGroup)null);
                  this.addUnusedCurrenciesToGroup(fvImpl, info, currMap, "B", usedCurrencies);
                  if(info.getFinanceCurrencies().size() > 0) {
                     currency.getFinanceCurrencyGroups().add((FinanceCurrencyGroup)this.newInstance(this.getCPConnection(), info));
                  }

                  FinanceCurrencyGroupImpl unusedTypeCGroup = this.createCurrencyGroup("currency values", (FinanceCurrencyGroup)null);
                  this.addUnusedCurrenciesToGroup(fvImpl, unusedTypeCGroup, currMap, "C", usedCurrencies);
                  if(unusedTypeCGroup.getFinanceCurrencies().size() > 0) {
                     currency.getFinanceCurrencyGroups().add((FinanceCurrencyGroup)this.newInstance(this.getCPConnection(), unusedTypeCGroup));
                  }

                  if(currency.getFinanceCurrencyGroups().size() > 0) {
                     fvImpl.getFinanceCurrencyGroups().add((FinanceCurrencyGroup)this.newInstance(this.getCPConnection(), currency));
                  }
               }
            }
         } else if(isCurrencyApplicable) {
            if(!isBalTypeApplicable) {
               typeBGroup = this.createCurrencyGroup("unused currencies", (FinanceCurrencyGroup)null);
               this.addAllCurrenciesToValueType(fvImpl, currMap, " ");
               if(typeBGroup.getFinanceCurrencies().size() > 0) {
                  fvImpl.getFinanceCurrencyGroups().add((FinanceCurrencyGroup)this.newInstance(this.getCPConnection(), typeBGroup));
               }
            } else {
               Iterator typeBGroup1 = currMap.entrySet().iterator();

               while(typeBGroup1.hasNext()) {
                  Entry typeCGroup1 = (Entry)typeBGroup1.next();
                  String currency1 = (String)typeCGroup1.getKey();
                  AbstractExtSysProxy$CurrencyInfo info1 = (AbstractExtSysProxy$CurrencyInfo)typeCGroup1.getValue();
                  if(!info1.isNonBase()) {
                     this.addBaseCurrencyToValueType(fvImpl, currency1);
                  }
               }

               typeBGroup = this.createCurrencyGroup("currencies - base values", (FinanceCurrencyGroup)null);
               this.addAllCurrenciesToGroup(fvImpl, typeBGroup, currMap, "B");
               fvImpl.getFinanceCurrencyGroups().add((FinanceCurrencyGroup)this.newInstance(this.getCPConnection(), typeBGroup));
               typeCGroup = this.createCurrencyGroup("currencies - currency values", (FinanceCurrencyGroup)null);
               this.addAllCurrenciesToGroup(fvImpl, typeCGroup, currMap, "C");
               fvImpl.getFinanceCurrencyGroups().add((FinanceCurrencyGroup)this.newInstance(this.getCPConnection(), typeCGroup));
            }
         } else {
            this.addNoCurrencyToValueType(fvImpl, (String)null);
         }

      }
   }

   private void addUsedCurrenciesToValueType(FinanceValueTypeImpl fvImpl, String balanceType, Map<String, AbstractExtSysProxy$CurrencyInfo> usedCurrencies) {
      fvImpl.getFinanceCurrencies().addAll(this.getUsedCurrencyList(fvImpl, usedCurrencies, balanceType));
   }

   private void addUsedCurrenciesToGroup(FinanceValueTypeImpl fvt, FinanceCurrencyGroupImpl group, String balanceType, Map<String, AbstractExtSysProxy$CurrencyInfo> usedCurrencies) {
      group.getFinanceCurrencies().addAll(this.getUsedCurrencyList(fvt, usedCurrencies, balanceType));
   }

   private List<FinanceCurrency> getUsedCurrencyList(FinanceValueTypeImpl fvImpl, Map<String, AbstractExtSysProxy$CurrencyInfo> usedCurrencies, String balanceType) {
      ArrayList returnList = new ArrayList();
      Iterator i$ = usedCurrencies.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         String currency = (String)entry.getKey();
         AbstractExtSysProxy$CurrencyInfo info = (AbstractExtSysProxy$CurrencyInfo)entry.getValue();
         FinanceCurrencyImpl currImpl = this.createCurrency(fvImpl, currency, info.getDescription(), balanceType);
         returnList.add((FinanceCurrency)this.newInstance(this.getCPConnection(), currImpl));
      }

      return returnList;
   }

   private void addAllCurrenciesToValueType(FinanceValueTypeImpl fvImpl, Map<String, AbstractExtSysProxy$CurrencyInfo> currMap, String balanceType) {
      fvImpl.getFinanceCurrencies().addAll(this.getAllCurrenciesList(fvImpl, currMap, balanceType));
   }

   private void addAllCurrenciesToGroup(FinanceValueType fvt, FinanceCurrencyGroupImpl group, Map<String, AbstractExtSysProxy$CurrencyInfo> currMap, String balanceType) {
      group.getFinanceCurrencies().addAll(this.getAllCurrenciesList(fvt, currMap, balanceType));
   }

   private List<FinanceCurrency> getAllCurrenciesList(FinanceValueType fvt, Map<String, AbstractExtSysProxy$CurrencyInfo> currMap, String balanceType) {
      ArrayList returnList = new ArrayList();
      Iterator i$ = currMap.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         String currency = (String)entry.getKey();
         AbstractExtSysProxy$CurrencyInfo info = (AbstractExtSysProxy$CurrencyInfo)entry.getValue();
         FinanceCurrencyImpl currImpl = this.createCurrency(fvt, currency, info.getDescription(), balanceType);
         returnList.add((FinanceCurrency)this.newInstance(this.getCPConnection(), currImpl));
      }

      return returnList;
   }

   private void addBaseCurrencyToValueType(FinanceValueTypeImpl fvImpl, String currency) {
      FinanceCurrencyImpl currImpl = this.createCurrency(fvImpl, (String)null, currency + " values for all currencies", "A");
      fvImpl.getFinanceCurrencies().add((FinanceCurrency)this.newInstance(this.getCPConnection(), currImpl));
   }

   private void addNoCurrencyToValueType(FinanceValueTypeImpl fvImpl, String currency) {
      FinanceCurrencyImpl currImpl = this.createCurrency(fvImpl, currency, "All Values", " ");
      fvImpl.getFinanceCurrencies().add((FinanceCurrency)this.newInstance(this.getCPConnection(), currImpl));
   }

   private void addUnusedCurrenciesToGroup(FinanceValueType fvt, FinanceCurrencyGroupImpl group, Map<String, AbstractExtSysProxy$CurrencyInfo> currMap, String balanceType, Map<String, AbstractExtSysProxy$CurrencyInfo> usedCurrencies) {
      Iterator i$ = currMap.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         String currency = (String)entry.getKey();
         AbstractExtSysProxy$CurrencyInfo info = (AbstractExtSysProxy$CurrencyInfo)entry.getValue();
         if(usedCurrencies.get(currency) == null) {
            FinanceCurrencyImpl currImpl = this.createCurrency(fvt, currency, info.getDescription(), balanceType);
            group.getFinanceCurrencies().add((FinanceCurrency)this.newInstance(this.getCPConnection(), currImpl));
         }
      }

   }

   private FinanceCurrencyGroupImpl createCurrencyGroup(String descr, FinanceCurrencyGroup owner) {
      FinanceCurrencyGroupImpl grp = new FinanceCurrencyGroupImpl(descr, descr);
      grp.setFinanceCurrencies(new ArrayList());
      grp.setFinanceCurrencyGroups(new ArrayList());
      if(owner != null) {
         owner.getFinanceCurrencyGroups().add((FinanceCurrencyGroup)this.newInstance(this.getCPConnection(), grp));
      }

      return grp;
   }

   private FinanceCurrencyImpl createCurrency(FinanceValueType fvt, String currency, String descr, String balanceType) {
      return new FinanceCurrencyImpl(fvt.getValueTypeVisId(), currency, balanceType, descr, fvt);
   }

   protected Object dispatchFinanceDimensionCall(Object proxy, Method m, Object[] args) throws Throwable {
      FinanceDimensionImpl dimensionImpl = (FinanceDimensionImpl)this.getTarget();
      if(m.getName().equals("getFinanceDimensionElementGroups")) {
         this.getFinanceDimensionElementGroups(dimensionImpl);
      } else if(m.getName().equals("getFinanceHierarchies")) {
         if(dimensionImpl.getFinanceHierarchies() == null) {
            this.getFinanceHierarchies(dimensionImpl);
         }
      } else if(m.getName().equals("toString")) {
         return dimensionImpl.getEntityRef().getNarrative();
      }

      return m.invoke(this.getTarget(), args);
   }

   private void getFinanceDimensionElementGroups(FinanceDimensionImpl finDimension) throws ValidationException {
      ArrayList groupList = new ArrayList();
      this.getDimElements(finDimension, (FinanceDimensionElementGroup)null, 0, (String)null, (String)null, (List)null, groupList);
      finDimension.setFinanceDimensionElementsGroups(groupList);
   }

   private void getFinanceDimensionElementGroups(FinanceDimensionElementGroupImpl finDimElemGroup) throws ValidationException {
      FinanceDimension finDimension = finDimElemGroup.getFinanceDimension();
      ArrayList groupList = new ArrayList();
      ArrayList elemList = new ArrayList();
      this.getDimElements(finDimension, finDimElemGroup, finDimElemGroup.getGroupType(), finDimElemGroup.getElem(), finDimElemGroup.getExtSysAccountType(), elemList, groupList);
      finDimElemGroup.setFinanceDimensionElements(elemList);
      finDimElemGroup.setFinanceDimensionElementGroups(groupList);
   }

   private void getDimElements(FinanceDimension finDimension, FinanceDimensionElementGroup finDimElemGroup, int parentGroupType, String parent, String parentType, List<FinanceDimensionElement> elemList, List<FinanceDimensionElementGroup> groupList) throws ValidationException {
      FinanceLedger ledger = finDimension.getFinanceLedger();
      String ledgerCode = ledger.getLedgerVisId();
      FinanceCompany company = ledger.getFinanceCompany();
      String companyCode = company.getCompanyVisId();
      ExternalSystem extSys = company.getExternalSystem();
      ExternalSystemPK espk = (ExternalSystemPK)extSys.getEntityRef().getPrimaryKey();
      EntityList extsysList = this.getServer().getFinanceDimElementGroups(espk.getExternalSystemId(), extSys.getSystemType(), companyCode, ledgerCode, finDimension.getExtSysDimensionType(), finDimension.getDimensionVisId(), parentGroupType, parent, parentType);

      for(int i = 0; i < extsysList.getNumRows(); ++i) {
         String elem = (String)extsysList.getValueAt(i, "ELEM");
         String descr = (String)extsysList.getValueAt(i, "DESCR");
         boolean isLeaf = extsysList.getValueAt(i, "IS_LEAF").equals("Y");
         String accType = (String)extsysList.getValueAt(i, "ACC_TYPE");
         Integer groupType = (Integer)extsysList.getValueAt(i, "NODE_TYPE");
         Integer mapType = (Integer)extsysList.getValueAt(i, "MAP_TYPE");
         if(isLeaf) {
            FinanceDimensionElementImpl impl = new FinanceDimensionElementImpl(elem, accType, descr);
            if(finDimElemGroup != null) {
               impl.setFinanceDimensionElementGroup(finDimElemGroup);
            }

            elemList.add((FinanceDimensionElement)this.newInstance(this.getCPConnection(), impl));
         } else {
            FinanceDimensionElementGroupImpl var23 = new FinanceDimensionElementGroupImpl(elem, accType, descr);
            if(finDimElemGroup != null) {
               var23.setFinanceDimensionElementGroup(finDimElemGroup);
            }

            var23.setFinanceDimension(finDimension);
            var23.setGroupType(groupType.intValue());
            var23.setMappingType(mapType);
            groupList.add((FinanceDimensionElementGroup)this.newInstance(this.getCPConnection(), var23));
         }
      }

   }

   private void getFinanceHierarchies(FinanceDimensionImpl finDimension) throws ValidationException {
      FinanceLedger ledger = finDimension.getFinanceLedger();
      String ledgerCode = ledger.getLedgerVisId();
      FinanceCompany company = ledger.getFinanceCompany();
      String companyCode = company.getCompanyVisId();
      ExternalSystem extSys = company.getExternalSystem();
      ExternalSystemPK espk = (ExternalSystemPK)extSys.getEntityRef().getPrimaryKey();
      EntityList extsysList = this.getServer().getFinanceHierarchies(espk.getExternalSystemId(), extSys.getSystemType(), companyCode, ledgerCode, finDimension.getExtSysDimensionType(), finDimension.getDimensionVisId());
      ArrayList myList = new ArrayList();
      FinanceHierarchyImpl dummyHier = null;

      for(int i = 0; i < extsysList.getNumRows(); ++i) {
         String hierarchyName = (String)extsysList.getValueAt(i, "HIERARCHY_NAME");
         String descr = (String)extsysList.getValueAt(i, "DESCR");
         String hierarchyType = String.valueOf(extsysList.getValueAt(i, "HIERARCHY_TYPE"));
         String suggestedCPVisId = ((String)extsysList.getValueAt(i, "SUGGESTED_CP_VIS_ID")).trim();
         boolean isDummy = ((Integer)extsysList.getValueAt(i, "IS_DUMMY")).intValue() == 1;
         FinanceHierarchyImpl impl = new FinanceHierarchyImpl(hierarchyName, hierarchyType, descr);
         impl.setSuggestedCPVisId(suggestedCPVisId.trim());
         impl.setFinanceDimension(finDimension);
         impl.setIsDummy(isDummy);
         impl.setIsMandatory(false);
         if(isDummy) {
            dummyHier = impl;
         } else {
            myList.add((FinanceHierarchy)this.newInstance(this.getCPConnection(), impl));
         }
      }

      if(dummyHier != null) {
         myList.add((FinanceHierarchy)this.newInstance(this.getCPConnection(), dummyHier));
      }

      finDimension.setFinanceHierarchies(myList);
   }

   protected Object dispatchFinanceHierarchyCall(Object proxy, Method m, Object[] args) throws Throwable {
      FinanceHierarchyImpl fh = (FinanceHierarchyImpl)this.getTarget();
      if(!m.getName().equals("getFinanceHierarchyElements") && !m.getName().equals("getFinanceDimensionElements")) {
         if(m.getName().equals("toString")) {
            return fh.getEntityRef().getNarrative();
         }
      } else if(fh.getFinanceHierarchyElements() == null || fh.getFinanceDimensionElements() == null) {
         this.getFinanceHierarchyElements(fh);
      }

      return m.invoke(this.getTarget(), args);
   }

   private void getFinanceHierarchyElements(FinanceHierarchyImpl fh) throws ValidationException {
      ArrayList groupList = new ArrayList();
      ArrayList elemList = new ArrayList();
      if(!fh.isDummy()) {
         this.getHierarchyElememts(fh, (String)null, elemList, groupList);
      }

      fh.setFinanceDimensionElements(elemList);
      fh.setFinanceHierarchyElements(groupList);
   }

   private void getFinanceHierarchyElements(FinanceHierarchyElementImpl fhe) throws ValidationException {
      ArrayList groupList = new ArrayList();
      ArrayList elemList = new ArrayList();
      this.getHierarchyElememts(fhe.getFinanceHierarchy(), fhe.getHierarchyElem(), elemList, groupList);
      fhe.setFinanceDimensionElements(elemList);
      fhe.setFinanceHierarchyElements(groupList);
   }

   private void getHierarchyElememts(FinanceHierarchy finHierarchy, String parent, List<FinanceDimensionElement> elemList, List<FinanceHierarchyElement> groupList) throws ValidationException {
      FinanceDimension dimension = finHierarchy.getFinanceDimension();
      FinanceLedger ledger = dimension.getFinanceLedger();
      String ledgerCode = ledger.getLedgerVisId();
      FinanceCompany company = ledger.getFinanceCompany();
      String companyCode = company.getCompanyVisId();
      ExternalSystem extSys = company.getExternalSystem();
      ExternalSystemPK espk = (ExternalSystemPK)extSys.getEntityRef().getPrimaryKey();
      EntityList extsysList = this.getServer().getFinanceHierarchyElems(espk.getExternalSystemId(), extSys.getSystemType(), companyCode, ledgerCode, dimension.getExtSysDimensionType(), dimension.getDimensionVisId(), finHierarchy.getHierarchyName(), finHierarchy.getHierarchyType(), parent);

      for(int i = 0; i < extsysList.getNumRows(); ++i) {
         String elem = (String)extsysList.getValueAt(i, "ELEM");
         String descr = (String)extsysList.getValueAt(i, "DESCR");
         boolean isLeaf = extsysList.getValueAt(i, "IS_LEAF").equals("Y");
         if(isLeaf) {
            FinanceDimensionElementImpl impl = new FinanceDimensionElementImpl(elem, (String)null, descr);
            impl.setFinanceDimension(dimension);
            elemList.add((FinanceDimensionElement)this.newInstance(this.getCPConnection(), impl));
         } else {
            FinanceHierarchyElementImpl var18 = new FinanceHierarchyElementImpl(elem, finHierarchy.getHierarchyName(), finHierarchy.getHierarchyType(), descr);
            var18.setFinanceHierarchy(finHierarchy);
            groupList.add((FinanceHierarchyElement)this.newInstance(this.getCPConnection(), var18));
         }
      }

   }

   protected Object dispatchFinanceHierarchyElementCall(Object proxy, Method m, Object[] args) throws Throwable {
      FinanceHierarchyElementImpl fhe = (FinanceHierarchyElementImpl)this.mTarget;
      if(!m.getName().equals("getFinanceHierarchyElements") && !m.getName().equals("getFinanceDimensionElements")) {
         if(m.getName().equals("toString")) {
            return fhe.getEntityRef().getNarrative();
         }
      } else if(fhe.getFinanceHierarchyElements() == null || fhe.getFinanceDimensionElements() == null) {
         this.getFinanceHierarchyElements(fhe);
      }

      return m.invoke(this.getTarget(), args);
   }

   protected Object dispatchFinanceDimensionElementGroupCall(Object proxy, Method m, Object[] args) throws Throwable {
      FinanceDimensionElementGroupImpl fdeg = (FinanceDimensionElementGroupImpl)this.getTarget();
      if(!m.getName().equals("getFinanceDimensionElements") && !m.getName().equals("getFinanceDimensionElementGroups")) {
         if(m.getName().equals("toString")) {
            return fdeg.getEntityRef().getNarrative();
         }
      } else if(fdeg.getFinanceDimensionElements() == null || fdeg.getFinanceDimensionElementGroups() == null) {
         this.getFinanceDimensionElementGroups(fdeg);
      }

      return m.invoke(this.getTarget(), args);
   }

   protected Object dispatchFinanceDimensionElementCall(Object proxy, Method m, Object[] args) throws Throwable {
      FinanceDimensionElementImpl fde = (FinanceDimensionElementImpl)this.getTarget();
      return m.getName().equals("toString")?fde.getEntityRef().getNarrative():m.invoke(this.getTarget(), args);
   }

   protected Object dispatchFinanceCalendarYearCall(Object proxy, Method m, Object[] args) throws Throwable {
      FinanceCalendarYearImpl fc = (FinanceCalendarYearImpl)this.getTarget();
      if(m.getName().equals("toString")) {
         return fc.getEntityRef().getNarrative();
      } else {
         if(m.getName().equals("getFinancePeriods") && fc.getFinancePeriods() == null) {
            this.getFinancePeriods(fc);
         }

         return m.invoke(this.getTarget(), args);
      }
   }

   private void getFinancePeriods(FinanceCalendarYearImpl calYear) throws ValidationException {
      FinanceCompany cmpy = calYear.getFinanceCompany();
      String companyCode = cmpy.getCompanyVisId();
      ExternalSystem extSys = cmpy.getExternalSystem();
      ExternalSystemPK espk = (ExternalSystemPK)extSys.getEntityRef().getPrimaryKey();
      EntityList extsysList = this.getServer().getFinancePeriods(espk.getExternalSystemId(), extSys.getSystemType(), companyCode, calYear.getYear());
      ArrayList myList = new ArrayList();

      for(int i = 0; i < extsysList.getNumRows(); ++i) {
         Integer period = (Integer)extsysList.getValueAt(i, "PERIOD");
         String descr = (String)extsysList.getValueAt(i, "DESCR");
         String visId = extsysList.getValueAt(i, "VIS_ID") + " - " + descr;
         FinancePeriodImpl impl = new FinancePeriodImpl(String.valueOf(period), descr);
         impl.setPeriod(period.intValue());
         myList.add(impl);
      }

      calYear.setFinancePeriods(myList);
   }

   protected Object dispatchFinanceValueTypeCall(Object proxy, Method m, Object[] args) throws Throwable {
      FinanceValueTypeImpl financeValueTypeImpl = (FinanceValueTypeImpl)this.getTarget();
      return m.getName().equals("toString")?financeValueTypeImpl.getEntityRef().getNarrative():m.invoke(this.getTarget(), args);
   }

   protected Object dispatchFinanceValueTypeOwnerCall(Object proxy, Method m, Object[] args) throws Exception {
      FinanceValueTypeOwnerImpl financeValueTypeOwnerImpl = (FinanceValueTypeOwnerImpl)this.getTarget();
      return m.getName().equals("toString")?financeValueTypeOwnerImpl.getEntityRef().getNarrative():m.invoke(this.getTarget(), args);
   }

   protected Object dispatchFinanceCurrencyGroupCall(Object proxy, Method m, Object[] args) throws Exception {
      FinanceCurrencyGroupImpl financeCurrencyGroupImpl = (FinanceCurrencyGroupImpl)this.getTarget();
      return m.getName().equals("toString")?financeCurrencyGroupImpl.getEntityRef().getNarrative():m.invoke(this.getTarget(), args);
   }

   private Object dispatchFinanceCurrencyCall(Object proxy, Method m, Object[] args) throws Throwable {
      FinanceCurrency fc = (FinanceCurrency)this.getTarget();
      return m.getName().equals("toString")?fc.getEntityRef().getNarrative():m.invoke(this.getTarget(), args);
   }

   public CPConnectionImpl getCPConnection() {
      return this.mCPConnection;
   }

   public Object getTarget() {
      return this.mTarget;
   }
}
