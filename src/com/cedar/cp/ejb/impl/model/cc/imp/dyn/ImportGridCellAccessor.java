// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:42
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCellPK;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ImportGridCellDAO;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ImportGridCellEVO;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;

public class ImportGridCellAccessor implements Serializable {

   private Hashtable mDAOs = new Hashtable();
   private transient InitialContext mInitialContext;


   public ImportGridCellAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ImportGridCellDAO getDAO(ImportGridCellPK pk) throws Exception {
      ImportGridCellDAO dao = (ImportGridCellDAO)this.mDAOs.get(pk);
      if(dao == null) {
         dao = new ImportGridCellDAO();
         this.mDAOs.put(pk, dao);
      }

      return dao;
   }

   public ImportGridCellEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      return key instanceof ImportGridCellPK?this.getDAO((ImportGridCellPK)key).getDetails((ImportGridCellPK)key, dependants):null;
   }
}
