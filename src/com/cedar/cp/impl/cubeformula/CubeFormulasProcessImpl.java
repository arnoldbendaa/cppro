// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.cubeformula;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cubeformula.CubeFormulaEditorSession;
import com.cedar.cp.api.cubeformula.CubeFormulaRef;
import com.cedar.cp.api.cubeformula.CubeFormulasProcess;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cubeformula.CubeFormulaRefImpl;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.ejb.api.cubeformula.CubeFormulaEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.cubeformula.CubeFormulaEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CubeFormulasProcessImpl extends BusinessProcessImpl implements CubeFormulasProcess {

   private Log mLog = new Log(this.getClass());


   public CubeFormulasProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      CubeFormulaEditorSessionServer es = new CubeFormulaEditorSessionServer(this.getConnection());

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

   public CubeFormulaEditorSession getCubeFormulaEditorSession(Object key) throws ValidationException {
      CubeFormulaEditorSessionImpl sess = new CubeFormulaEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllCubeFormulas() {
      try {
         return this.getConnection().getListHelper().getAllCubeFormulas();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllCubeFormulas", var2);
      }
   }
   
   
   public EntityList getAllCubeFormulasForLoggedUser() {
      try {
         return this.getConnection().getListHelper().getAllCubeFormulasForLoggedUser();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllCubeFormulasForLoggedUser", var2);
      }
   }

   public EntityList getCubeFormulaeForFinanceCube(int param1) {
      try {
         return this.getConnection().getListHelper().getCubeFormulaeForFinanceCube(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get CubeFormulaeForFinanceCube", var3);
      }
   }

   public String getProcessName() {
      String ret = "Processing CubeFormula";
      return ret;
   }

   protected int getProcessID() {
      return 97;
   }

   public int issueCubeFormulaRebuildTask(ModelRef modelRef, FinanceCubeRef financeCubeRef, List<CubeFormulaRef> cubeFormula) throws ValidationException {
      int fcId = ((FinanceCubeRefImpl)financeCubeRef).getFinanceCubePK().getFinanceCubeId();
      EntityList fcDetails = this.getConnection().getFinanceCubesProcess().getFinanceCubeDetails(fcId);
      if(fcDetails.getNumRows() != 1) {
         throw new ValidationException("Unable to locate finance cube id : " + fcId);
      } else {
         Boolean cubeFormulaEnabled = (Boolean)fcDetails.getValueAt(0, "CubeFormulaEnabled");
         if(!cubeFormulaEnabled.booleanValue()) {
            throw new ValidationException("Cube formula are not enabled in finance cube:" + financeCubeRef);
         } else {
            CubeFormulaEditorSessionServer es = new CubeFormulaEditorSessionServer(this.getConnection());
            ArrayList rebuildFormulas = new ArrayList();
            Iterator i$ = cubeFormula.iterator();

            while(i$.hasNext()) {
               CubeFormulaRef cfr = (CubeFormulaRef)i$.next();
               rebuildFormulas.add((CubeFormulaRefImpl)cfr);
            }

            return es.issueFormulaRebuildTask(modelRef, financeCubeRef, rebuildFormulas);
         }
      }
   }
}
