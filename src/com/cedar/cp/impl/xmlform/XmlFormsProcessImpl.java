// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlform;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.api.xmlform.FormDeploymentData;
import com.cedar.cp.api.xmlform.XmlFormEditorSession;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.api.xmlform.XmlFormsProcess;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.dto.xmlform.XmlFormCK;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.api.udeflookup.UdefLookupEditorSessionServer;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.xmlform.XmlFormEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

import java.util.HashSet;
import java.util.List;

public class XmlFormsProcessImpl extends BusinessProcessImpl implements XmlFormsProcess {

   private Log mLog = new Log(this.getClass());


   public XmlFormsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      XmlFormEditorSessionServer es = new XmlFormEditorSessionServer(this.getConnection());

      try {
         es.delete(primaryKey);
      } catch (ValidationException var5) {
         throw var5;
      } catch (CPException var6) {
         throw new RuntimeException("can\'t delete " + primaryKey, var6);
      }

      if(timer != null) {
         timer.logDebug("deleteObject", primaryKey);
      }

   }

   public XmlFormEditorSession getXmlFormEditorSession(Object key) throws ValidationException {
	  if (mActiveSessions == null) {
		  mActiveSessions = new HashSet();   
	  }
	  XmlFormEditorSessionImpl sess = new XmlFormEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllXmlForms() {
      try {
         return this.getConnection().getListHelper().getAllXmlForms();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllXmlForms", var2);
      }
   }
   
   public EntityList getAllXmlFormsForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllXmlFormsForLoggedUser();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllXmlFormsForLoggedUser", var2);
      }
   }

   public EntityList getAllFinXmlForms() {
      try {
         return this.getConnection().getListHelper().getAllFinXmlForms();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllFinXmlForms", var2);
      }
   }

   public EntityList getAllFFXmlForms() {
      try {
         return this.getConnection().getListHelper().getAllFFXmlForms();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllFFXmlForms", var2);
      }
   }

   public EntityList getAllFFXmlFormsForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllFFXmlFormsForLoggedUser();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllFFXmlFormsForLoggedUser", var2);
      }
   }
   
   public EntityList getAllXcellXmlForms() {
	   try {
		   return this.getConnection().getListHelper().getAllXcellXmlForms();
	   } catch (Exception var2) {
		   var2.printStackTrace();
		   throw new RuntimeException("can\'t get AllXmlForms", var2);
	   }
   }
   

   public EntityList getAllXcellXmlFormsForLoggedUser() {
	   try {
		   return this.getConnection().getListHelper().getAllXcellXmlFormsForLoggedUser();
	   } catch (Exception var2) {
		   var2.printStackTrace();
		   throw new RuntimeException("can\'t get AllXmlFormsForLoggedUser", var2);
	   }
   }
   
   
   public EntityList getXcellXmlFormsForUser(int userId) {
	   try {
		   return this.getConnection().getListHelper().getXcellXmlFormsForUser(userId);
	   } catch (Exception var2) {
		   var2.printStackTrace();
		   throw new RuntimeException("can\'t get AllXmlForms for user id:"+userId, var2);
	   }
   }

   public EntityList getAllMassVirementXmlForms() {
      try {
         return this.getConnection().getListHelper().getAllMassVirementXmlForms();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllMassVirementXmlForms", var2);
      }
   }

   public EntityList getAllMassVirementXmlFormsForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllMassVirementXmlFormsForLoggedUser();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllMassVirementXmlFormsFormsForLoggedUser", var2);
      }
   }

   public EntityList getAllFinanceXmlForms() {
      try {
         return this.getConnection().getListHelper().getAllFinanceXmlForms();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllFinanceXmlForms", var2);
      }
   }

   public EntityList getAllFinanceAndFlatForms() {
      try {
         return this.getConnection().getListHelper().getAllFinanceAndFlatForms();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllFinanceAndFlatForms", var2);
      }
   }

   public EntityList getAllFinanceXmlFormsForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllFinanceXmlFormsForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllFinanceXmlFormsForModel", var3);
      }
   }

   public EntityList getAllFinanceAndFlatFormsForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllFinanceAndFlatFormsForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllFinanceAndFlatFormsForModel", var3);
      }
   }

   public EntityList getAllFinanceXmlFormsAndDataTypesForModel(int param1) {
      try {
         return this.getConnection().getListHelper().getAllFinanceXmlFormsAndDataTypesForModel(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get AllFinanceXmlFormsAndDataTypesForModel", var3);
      }
   }
   
	public EntityList getAllXmlFormsForModel(int modelId) {
		try {
			return this.getConnection().getListHelper().getAllXmlFormsForModel(modelId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("can\'t get AllXmlFormsForModel", e);
		}
	}

   public EntityList getAllFixedXMLForms() {
      try {
         return this.getConnection().getListHelper().getAllFixedXMLForms();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllFixedXMLForms", var2);
      }
   }

   public EntityList getAllDynamicXMLForms() {
      try {
         return this.getConnection().getListHelper().getAllDynamicXMLForms();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllDynamicXMLForms", var2);
      }
   }

   public EntityList getAllFlatXMLForms() {
      try {
         return this.getConnection().getListHelper().getAllFlatXMLForms();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllFlatXMLForms", var2);
      }
   }

   public EntityList getXMLFormDefinition(int param1) {
      try {
         return this.getConnection().getListHelper().getXMLFormDefinition(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get XMLFormDefinition", var3);
      }
   }

   public EntityList getXMLFormCellPickerInfo(int param1) {
      try {
         return this.getConnection().getListHelper().getXMLFormCellPickerInfo(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get XMLFormCellPickerInfo", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing XmlForm";
      return ret;
   }

   protected int getProcessID() {
      return 44;
   }

   public EntityList getAllFinanceXmlFormsForModelAndUser(int param1, int budgetCycleId, int userId, boolean hasDesignModeSecurity) {
      try {
         return this.getConnection().getListHelper().getAllFinanceXmlFormsForModelAndUser(param1, budgetCycleId, userId, hasDesignModeSecurity);
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new RuntimeException("can\'t get AllFinanceXmlFormsForModelAndUser", var5);
      }
   }

   public void deleteFormAndProfiles(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      XmlFormEditorSessionServer es = new XmlFormEditorSessionServer(this.getConnection());

      try {
         es.deleteFormAndProfiles(primaryKey);
      } catch (ValidationException var5) {
         throw var5;
      } catch (CPException var6) {
         throw new RuntimeException("can\'t delete " + primaryKey, var6);
      }

      if(timer != null) {
         timer.logDebug("deleteFormAndProfiles", primaryKey);
      }

   }

	public void deleteFormProfiles(EntityRef er, String subject, String messageText) throws ValidationException {
		XmlFormEditorSessionServer es = new XmlFormEditorSessionServer(getConnection());
		try {
			es.deleteFormProfiles(er.getPrimaryKey(), subject, messageText);
		} catch (ValidationException e) {
			throw e;
		} catch (CPException e) {
			throw new RuntimeException("can't submit", e);
		}
	}
	
    public void deleteFormProfiles(EntityRef er, String subject, String messageText, Boolean mobile) throws ValidationException {
        XmlFormEditorSessionServer es = new XmlFormEditorSessionServer(getConnection());
        try {
            es.deleteFormProfiles(er.getPrimaryKey(), subject, messageText, mobile);
        } catch (ValidationException e) {
            throw e;
        } catch (CPException e) {
            throw new RuntimeException("can't submit", e);
        }
    }
    
   public int processFormDeployment(FormDeploymentData data) throws ValidationException {
      XmlFormEditorSessionServer es = new XmlFormEditorSessionServer(this.getConnection());

      try {
         return es.processFormDeployment(data);
      } catch (ValidationException var4) {
         throw var4;
      } catch (CPException var5) {
         throw new RuntimeException("can\'t submit " + data, var5);
      }
   }

   public int[] issueCellCalcRebuildTask(List<EntityRef> rebuildList) throws ValidationException {
      XmlFormEditorSessionServer es = new XmlFormEditorSessionServer(this.getConnection());

      try {
         return es.issueCellCalcRebuildTask(rebuildList);
      } catch (ValidationException var4) {
         throw var4;
      } catch (CPException var5) {
         throw new RuntimeException("can\'t submit", var5);
      }
   }

   public EntityList getAllXmlFormsAndProfiles(String param1, String param2, String param3) {
      try {
         return this.getConnection().getListHelper().getAllXmlFormsAndProfiles(param1, param2, param3);
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new RuntimeException("can\'t getAllXmlFormsAndProfiles", var5);
      }
   }

   public UdefLookup getUdefLookup(String visId) throws ValidationException {
      UdefLookupEditorSessionServer es = new UdefLookupEditorSessionServer(this.getConnection());

      try {
         return es.getUdefLookup(visId);
      } catch (ValidationException var4) {
         throw var4;
      } catch (CPException var5) {
         throw new RuntimeException("can\'t get udef lookup", var5);
      }
   }

   public EntityList getMassVirementFormsForFinanceCube(FinanceCubeRef targetFinanceCube) {
      EntityList allMassVirementForms = this.getConnection().getXmlFormsProcess().getAllMassVirementXmlForms();
      EntityListImpl filterList = new EntityListImpl(allMassVirementForms);

      for(int i = 0; i < filterList.getNumRows(); ++i) {
         FinanceCubeRef fcRef = (FinanceCubeRef)allMassVirementForms.getValueAt(i, "FinanceCube");
         if(fcRef != null && !fcRef.equals(targetFinanceCube)) {
            filterList.remove(i);
         }
      }

      return filterList;
   }

   public EntityList getXMLFormDefinition(Object key) {
      return this.getXMLFormDefinition(this.coerceXmlFormId(key));
   }

   private int coerceXmlFormId(Object key) {
      boolean id = false;
      if(key instanceof XmlFormRef) {
         key = ((XmlFormRef)key).getPrimaryKey();
      }

      if(key instanceof String) {
         key = XmlFormPK.getKeyFromTokens(String.valueOf(key));
      }

      if(key instanceof XmlFormCK) {
         key = ((XmlFormCK)key).getXmlFormPK();
      }

      if(key instanceof XmlFormPK) {
         key = Integer.valueOf(((XmlFormPK)key).getXmlFormId());
      }

      if(key instanceof Number) {
         return ((Number)key).intValue();
      } else {
         throw new IllegalArgumentException("Unexpeceted XmlForm key class:" + key);
      }
   }
   	
	@Override
	public Object[] getExcelFile(Object primaryKey) throws ValidationException {
	      XmlFormEditorSessionServer es = new XmlFormEditorSessionServer(this.getConnection());
	      try {
	         return es.getExcelFile(primaryKey);
	      } catch (ValidationException var4) {
	         throw var4;
	      } catch (CPException var5) {
	         throw new RuntimeException("can\'t fetch excel file ", var5);
	      }
	}

	@Override
	public boolean saveJsonForm(Object primaryKey, String json, int versionNumber, int userId) throws ValidationException {
	      XmlFormEditorSessionServer es = new XmlFormEditorSessionServer(this.getConnection());
	      try {
	         return es.saveJsonForm(primaryKey, json, versionNumber, userId);
	      } catch (ValidationException var4) {
	         throw var4;
	      } catch (CPException var5) {
	         throw new RuntimeException("can\'t save JSON form", var5);
	      }
	}
}
