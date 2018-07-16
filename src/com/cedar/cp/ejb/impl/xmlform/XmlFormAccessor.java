// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:36
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlform;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.xmlform.AllDynamicXMLFormsELO;
import com.cedar.cp.dto.xmlform.AllFFXmlFormsELO;
import com.cedar.cp.dto.xmlform.AllFinXmlFormsELO;
import com.cedar.cp.dto.xmlform.AllFinanceAndFlatFormsELO;
import com.cedar.cp.dto.xmlform.AllFinanceAndFlatFormsForModelELO;
import com.cedar.cp.dto.xmlform.AllFinanceXmlFormsAndDataTypesForModelELO;
import com.cedar.cp.dto.xmlform.AllFinanceXmlFormsELO;
import com.cedar.cp.dto.xmlform.AllFinanceXmlFormsForModelELO;
import com.cedar.cp.dto.xmlform.AllFixedXMLFormsELO;
import com.cedar.cp.dto.xmlform.AllFlatXMLFormsELO;
import com.cedar.cp.dto.xmlform.AllMassVirementXmlFormsELO;
import com.cedar.cp.dto.xmlform.AllXMLFormUserLinkELO;
import com.cedar.cp.dto.xmlform.AllXcellXmlFormsELO;
import com.cedar.cp.dto.xmlform.AllXmlFormsAndProfilesELO;
import com.cedar.cp.dto.xmlform.AllXmlFormsELO;
import com.cedar.cp.dto.xmlform.CheckXMLFormUserLinkELO;
import com.cedar.cp.dto.xmlform.XMLFormCellPickerInfoELO;
import com.cedar.cp.dto.xmlform.XMLFormDefinitionELO;
import com.cedar.cp.dto.xmlform.XmlFormCK;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.impl.xmlform.XmlFormDAO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormEVO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormLocal;
import com.cedar.cp.ejb.impl.xmlform.XmlFormLocalHome;
import com.cedar.cp.ejb.impl.xmlform.XmlFormUserLinkDAO;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class XmlFormAccessor implements Serializable {

   private XmlFormLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_USER_LIST = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";
   XmlFormEEJB xmlFormEjb = new XmlFormEEJB();

   public XmlFormAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private XmlFormLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (XmlFormLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/XmlFormLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up XmlFormLocalHome", var2);
      }
   }

   private XmlFormLocal getLocal(XmlFormPK pk) throws Exception {
      XmlFormLocal local = (XmlFormLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public XmlFormEVO create(XmlFormEVO evo) throws Exception {
//      XmlFormLocal local = this.getLocalHome().create(evo);
//      XmlFormEVO newevo = local.getDetails("<UseLoadedEVOs>");
//      XmlFormPK pk = newevo.getPK();
//      this.mLocals.put(pk, local);
	   xmlFormEjb.ejbCreate(evo);
	   XmlFormEVO newevo = xmlFormEjb.getDetails("<UseLoadedEVOs>");
	   XmlFormPK pk = newevo.getPK();
	   this.mLocals.put(pk, xmlFormEjb);

      return newevo;
   }

   public void remove(XmlFormPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public XmlFormEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof XmlFormCK) {
         XmlFormPK pk = ((XmlFormCK)key).getXmlFormPK();
         return this.getLocal(pk).getDetails((XmlFormCK)key, dependants);
      } else {
//         return key instanceof XmlFormPK?this.getLocal((XmlFormPK)key).getDetails(dependants):null;
    	  XmlFormPK pk = (XmlFormPK)key;
    	  XmlFormCK ck = new XmlFormCK(pk);
    	  if(key instanceof XmlFormPK)
    		  return xmlFormEjb.getDetails(ck,dependants);
    	  else 
    		  return null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(XmlFormEVO evo) throws Exception {
      XmlFormPK pk = evo.getPK();
//      this.getLocal(pk).setDetails(evo);
//      XmlFormEEJB.ejbFindByPrimaryKey(pk);
      xmlFormEjb.ejbFindByPrimaryKey(pk);
      xmlFormEjb.setDetails(evo);
      xmlFormEjb.ejbStore();
   }
   

   public XmlFormEVO setAndGetDetails(XmlFormEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public XmlFormPK generateKeys(XmlFormPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllXmlFormsELO getAllXmlForms() {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllXmlForms();
   }

   public AllFinXmlFormsELO getAllFinXmlForms(int userId) {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllFinXmlForms(userId);
   }

   public AllFFXmlFormsELO getAllFFXmlForms() {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllFFXmlForms();
   }
   
   public AllXcellXmlFormsELO getAllXcellXmlForms() {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllXcellXmlForms();
   }
   
   public AllXmlFormsELO getXcellXmlFormsForUser(int userId) {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getXcellXmlFormsForUser(userId);
   }

   public AllMassVirementXmlFormsELO getAllMassVirementXmlForms() {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllMassVirementXmlForms();
   }

   public AllFinanceXmlFormsELO getAllFinanceXmlForms() {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllFinanceXmlForms();
   }

   public AllFinanceAndFlatFormsELO getAllFinanceAndFlatForms() {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllFinanceAndFlatForms();
   }

   public AllFinanceXmlFormsForModelELO getAllFinanceXmlFormsForModel(int param1) {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllFinanceXmlFormsForModel(param1);
   }

   public AllFinanceAndFlatFormsForModelELO getAllFinanceAndFlatFormsForModel(int param1) {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllFinanceAndFlatFormsForModel(param1);
   }

   public AllFinanceXmlFormsAndDataTypesForModelELO getAllFinanceXmlFormsAndDataTypesForModel(int param1) {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllFinanceXmlFormsAndDataTypesForModel(param1);
   }
   
	public AllXmlFormsELO getAllXmlFormsForModel(int modelId) {
		XmlFormDAO dao = new XmlFormDAO();
		return dao.getAllXmlFormsForModel(modelId);
	}

   public AllFixedXMLFormsELO getAllFixedXMLForms() {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllFixedXMLForms();
   }

   public AllDynamicXMLFormsELO getAllDynamicXMLForms() {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllDynamicXMLForms();
   }

   public AllFlatXMLFormsELO getAllFlatXMLForms() {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllFlatXMLForms();
   }

   public XMLFormDefinitionELO getXMLFormDefinition(int param1) {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getXMLFormDefinition(param1);
   }

   public XMLFormCellPickerInfoELO getXMLFormCellPickerInfo(int param1) {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getXMLFormCellPickerInfo(param1);
   }

   public AllXMLFormUserLinkELO getAllXMLFormUserLink() {
      XmlFormUserLinkDAO dao = new XmlFormUserLinkDAO();
      return dao.getAllXMLFormUserLink();
   }

   public CheckXMLFormUserLinkELO getCheckXMLFormUserLink(int param1) {
      XmlFormUserLinkDAO dao = new XmlFormUserLinkDAO();
      return dao.getCheckXMLFormUserLink(param1);
   }

   public AllFinanceXmlFormsForModelELO getAllFinanceXmlFormsForModelAndUser(int param1, int budgetCycleId, int userId, boolean hasDesignModeSecurity) {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllFinanceXmlFormsForModelAndUser(param1, budgetCycleId, userId, hasDesignModeSecurity);
   }

   public AllXmlFormsAndProfilesELO getAllXmlFormsAndProfiles(String param1, String param2, String param3) {
      XmlFormDAO dao = new XmlFormDAO();
      return dao.getAllXmlFormsAndProfiles(param1, param2, param3);
   }

	public AllXmlFormsELO getAllXmlFormsForLoggedUser(int userId) {
		XmlFormDAO dao = new XmlFormDAO();
	    return dao.getAllXmlFormsForLoggedUser(userId);
	}

	public AllFFXmlFormsELO getAllFFXmlFormsForLoggedUser(int userId) {
		XmlFormDAO dao = new XmlFormDAO();
	    return dao.getAllFFXmlFormsForLoggedUser(userId);
	}

	public AllXcellXmlFormsELO getAllXcellXmlFormsForLoggedUser(int userId) {
		XmlFormDAO dao = new XmlFormDAO();
	    return dao.getAllXcellXmlFormsForLoggedUser(userId);
	}

	public AllMassVirementXmlFormsELO getAllMassVirementXmlFormsForLoggedUser(int userId) {
		XmlFormDAO dao = new XmlFormDAO();
	    return dao.getAllMassVirementXmlFormsForLoggedUser(userId);
	}
}
