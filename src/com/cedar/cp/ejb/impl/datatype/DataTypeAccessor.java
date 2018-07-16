// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.datatype;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.datatype.AllDataTypeForFinanceCubeELO;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.datatype.AllDataTypesForModelELO;
import com.cedar.cp.dto.datatype.AllDataTypesWebELO;
import com.cedar.cp.dto.datatype.DataTypeCK;
import com.cedar.cp.dto.datatype.DataTypeDependenciesELO;
import com.cedar.cp.dto.datatype.DataTypeDetailsForVisIDELO;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypesByTypeELO;
import com.cedar.cp.dto.datatype.DataTypesByTypeWriteableELO;
import com.cedar.cp.dto.datatype.DataTypesForImpExpELO;
import com.cedar.cp.ejb.impl.datatype.DataTypeDAO;
import com.cedar.cp.ejb.impl.datatype.DataTypeEVO;
import com.cedar.cp.ejb.impl.datatype.DataTypeLocal;
import com.cedar.cp.ejb.impl.datatype.DataTypeLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DataTypeAccessor implements Serializable {

   private DataTypeLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_DATA_TYPE_DEPENDENCIES = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";
   DataTypeEEJB dataTypeEjb = new DataTypeEEJB();
   
   public DataTypeAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private DataTypeLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (DataTypeLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/DataTypeLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up DataTypeLocalHome", var2);
      }
   }

   private DataTypeEEJB getLocal(DataTypePK pk) throws Exception {
	   DataTypeEEJB local = (DataTypeEEJB)this.mLocals.get(pk);
      if(local == null) {
////         local = this.getLocalHome().findByPrimaryKey(pk);
//    	  local = (DataTypeEEJB) dataTypeEjb.ejbFindByPrimaryKey(pk);
//         this.mLocals.put(pk, local);
      }

      return local;
   }

   public DataTypeEVO create(DataTypeEVO evo) throws Exception {
	   dataTypeEjb.ejbCreate(evo);
//      DataTypeLocal local = this.getLocalHome().create(evo);
      DataTypeEVO newevo = dataTypeEjb.getDetails("<UseLoadedEVOs>");
      DataTypePK pk = newevo.getPK();
      this.mLocals.put(pk, dataTypeEjb);
      return newevo;
   }

   public void remove(DataTypePK pk) throws Exception {
      //this.getLocal(pk).ejbRemove();
      dataTypeEjb.ejbRemove();
   }

   public DataTypeEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof DataTypeCK) {
         DataTypePK pk = ((DataTypeCK)key).getDataTypePK();
         return this.getLocal(pk).getDetails((DataTypeCK)key, dependants);
      } else {
    	
//         return key instanceof DataTypePK?this.getLocal((DataTypePK)key).getDetails(dependants):null;
    	  DataTypePK dataTypePk =  (DataTypePK)key;
    	  DataTypeCK dataTypeCk = new DataTypeCK(dataTypePk);
         if (key instanceof DataTypePK)
			return dataTypeEjb.getDetails(dataTypeCk,dependants);
		else
			return null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(DataTypeEVO evo) throws Exception {
      DataTypePK pk = evo.getPK();
//      this.getLocal(pk).setDetails(evo);
      dataTypeEjb.ejbFindByPrimaryKey(pk);
      dataTypeEjb.setDetails(evo);
      dataTypeEjb.ejbStore();
   }

   public DataTypeEVO setAndGetDetails(DataTypeEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public DataTypePK generateKeys(DataTypePK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllDataTypesELO getAllDataTypes() {
      DataTypeDAO dao = new DataTypeDAO();
      return dao.getAllDataTypes();
   }

   public AllDataTypesWebELO getAllDataTypesWeb() {
      DataTypeDAO dao = new DataTypeDAO();
      return dao.getAllDataTypesWeb();
   }

   public AllDataTypeForFinanceCubeELO getAllDataTypeForFinanceCube(int param1) {
      DataTypeDAO dao = new DataTypeDAO();
      return dao.getAllDataTypeForFinanceCube(param1);
   }

   public AllDataTypesForModelELO getAllDataTypesForModel(int param1) {
      DataTypeDAO dao = new DataTypeDAO();
      return dao.getAllDataTypesForModel(param1);
   }

   public DataTypesByTypeELO getDataTypesByType(int param1) {
      DataTypeDAO dao = new DataTypeDAO();
      return dao.getDataTypesByType(param1);
   }

   public DataTypesByTypeWriteableELO getDataTypesByTypeWriteable(int param1) {
      DataTypeDAO dao = new DataTypeDAO();
      return dao.getDataTypesByTypeWriteable(param1);
   }

   public DataTypeDependenciesELO getDataTypeDependencies(short param1) {
      DataTypeDAO dao = new DataTypeDAO();
      return dao.getDataTypeDependencies(param1);
   }

   public DataTypesForImpExpELO getDataTypesForImpExp() {
      DataTypeDAO dao = new DataTypeDAO();
      return dao.getDataTypesForImpExp();
   }

   public DataTypeDetailsForVisIDELO getDataTypeDetailsForVisID(String param1) {
      DataTypeDAO dao = new DataTypeDAO();
      return dao.getDataTypeDetailsForVisID(param1);
   }
}
