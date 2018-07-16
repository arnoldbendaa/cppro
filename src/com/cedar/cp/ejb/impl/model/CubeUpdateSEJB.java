// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.ejb.base.cube.flatform.FlatFormCubeUpdateEngine;
import com.cedar.cp.ejb.impl.currency.CurrencyAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.util.Log;
import java.io.StringReader;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;

public class CubeUpdateSEJB implements SessionBean {

   private transient Log mLog = new Log(this.getClass());
   private SessionContext mSessionContext;
   private transient InitialContext mInitialContext;
   private transient ModelAccessor mModelAccessor;
   private transient CurrencyAccessor mCurrencyAccessor;
   private transient DimensionAccessor mDimensionAccessor;


   public void executeCubeUpdate(String xmlUpdate) throws EJBException {
      try {
         CubeUpdateEngine e = new CubeUpdateEngine();
         e.updateCube(new StringReader(xmlUpdate));
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new EJBException("Cube update failed:" + var3.getMessage(), var3);
      }
   }

   public void executeFlatFormUpdate(String xmlUpdate) throws EJBException {
      try {
         FlatFormCubeUpdateEngine e = new FlatFormCubeUpdateEngine();
         e.updateCube(new StringReader(xmlUpdate));
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new EJBException("Cube update failed:" + var3.getMessage(), var3);
      }
   }

   public void ejbCreate() throws EJBException {}

   public void ejbRemove() {}

   public void setSessionContext(SessionContext context) {
      this.mSessionContext = context;
   }

   public void ejbActivate() {}

   public void ejbPassivate() {}

   private InitialContext getInitialContext() throws Exception {
      if(this.mInitialContext == null) {
         this.mInitialContext = new InitialContext();
      }

      return this.mInitialContext;
   }

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.getInitialContext());
      }

      return this.mModelAccessor;
   }

   private CurrencyAccessor getCurrencyAccessor() throws Exception {
      if(this.mCurrencyAccessor == null) {
         this.mCurrencyAccessor = new CurrencyAccessor(this.getInitialContext());
      }

      return this.mCurrencyAccessor;
   }

   private DimensionAccessor getDimensionAccessor() throws Exception {
      if(this.mDimensionAccessor == null) {
         this.mDimensionAccessor = new DimensionAccessor(this.getInitialContext());
      }

      return this.mDimensionAccessor;
   }
}
