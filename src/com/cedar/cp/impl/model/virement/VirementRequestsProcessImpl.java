// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.virement.VirementQueryParams;
import com.cedar.cp.api.model.virement.VirementRequestEditorSession;
import com.cedar.cp.api.model.virement.VirementRequestsProcess;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.dimension.StructureElementCK;
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.virement.VirementRequestPK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.api.model.virement.VirementRequestEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.virement.VirementRequestEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VirementRequestsProcessImpl extends BusinessProcessImpl implements VirementRequestsProcess {

   private Log mLog = new Log(this.getClass());


   public VirementRequestsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      VirementRequestEditorSessionServer es = new VirementRequestEditorSessionServer(this.getConnection());

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

   public VirementRequestEditorSession getVirementRequestEditorSession(Object key) throws ValidationException {
      VirementRequestEditorSessionImpl sess = new VirementRequestEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllVirementRequests() {
      try {
         return this.getConnection().getListHelper().getAllVirementRequests();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllVirementRequests", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing VirementRequest";
      ret = "Budget Transfer Request";
      return ret;
   }

   protected int getProcessID() {
      return 73;
   }

   public int submitVirementRequest(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      VirementRequestEditorSessionServer es = new VirementRequestEditorSessionServer(this.getConnection());

      try {
         int e = es.submitVirementRequest(primaryKey);
         if(timer != null) {
            timer.logDebug("submitVirementRequest", primaryKey);
         }

         return e;
      } catch (ValidationException var5) {
         throw var5;
      } catch (CPException var6) {
         throw new RuntimeException("can\'t submit virement request: " + primaryKey, var6);
      }
   }

   public boolean haveVirementsWhichRequireAuthorisation() throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      VirementRequestEditorSessionServer es = new VirementRequestEditorSessionServer(this.getConnection());

      try {
         boolean e = es.haveVirementsWhichRequireAuthorisation();
         if(timer != null) {
            timer.logDebug("haveVirementsWhichRequireAuthorisation");
         }

         return e;
      } catch (CPException var4) {
         throw new RuntimeException("can\'t query virement auth status", var4);
      }
   }

   public EntityList queryVirementRequests(boolean includeChildRespAreaRequests) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      VirementRequestEditorSessionServer es = new VirementRequestEditorSessionServer(this.getConnection());

      try {
         EntityList e = es.queryVirementRequests(includeChildRespAreaRequests);
         if(timer != null) {
            timer.logDebug("queryVirementRequests");
         }

         return e;
      } catch (CPException var5) {
         throw new RuntimeException("can\'t query virement requests", var5);
      }
   }

   public List<DataTypeRef> queryDataTypes(Object financeCubeId) throws ValidationException {
      VirementRequestEditorSessionServer server = new VirementRequestEditorSessionServer(this.getConnection());
      return server.queryTransferDataTypes(this.queryFinanceCubeId(financeCubeId).intValue());
   }

   private Integer queryFinanceCubeId(Object financeCubeKey) throws ValidationException {
      if(financeCubeKey == null) {
         throw new ValidationException("Null supplied for finanec cube key");
      } else {
         if(financeCubeKey instanceof FinanceCubeRef) {
            financeCubeKey = ((FinanceCubeRef)financeCubeKey).getPrimaryKey();
         }

         if(financeCubeKey instanceof FinanceCubeCK) {
            financeCubeKey = ((FinanceCubeCK)financeCubeKey).getFinanceCubePK();
         }

         if(financeCubeKey instanceof FinanceCubePK) {
            financeCubeKey = Integer.valueOf(((FinanceCubePK)financeCubeKey).getFinanceCubeId());
         }

         if(financeCubeKey instanceof Integer) {
            return (Integer)financeCubeKey;
         } else {
            throw new ValidationException("Unexpected object type for finance cube key:" + financeCubeKey.getClass());
         }
      }
   }

   public VirementQueryParams getQueryParams(Object financeCubeId) throws ValidationException {
      VirementRequestEditorSessionServer server = new VirementRequestEditorSessionServer(this.getConnection());
      return server.getQueryParams(this.queryFinanceCubeId(financeCubeId).intValue());
   }

   public List<String> queryVirementRequests(int modelId, int numDims, Object creator, Object authoriser, Object virementId, Object status, List structureElements, Double minimumValue, Double maximumValue, Date fromDate, Date toDate) {
      VirementRequestEditorSessionServer server = new VirementRequestEditorSessionServer(this.getConnection());
      return server.queryVirementRequests(modelId, numDims, this.coerceUser(creator), this.coerceUser(authoriser), this.coerceVirementId(virementId), this.coerceVirementStatus(status), this.coerceElements(structureElements), minimumValue, maximumValue, fromDate, toDate);
   }

   public String queryVirementRequest(int requestId) {
      VirementRequestEditorSessionServer server = new VirementRequestEditorSessionServer(this.getConnection());
      return server.queryVirementRequest(requestId);
   }

   private Integer coerceVirementStatus(Object status) {
      if(status instanceof String) {
         status = Integer.valueOf(Integer.parseInt(String.valueOf(status)));
      }

      if(status instanceof Integer) {
         return (Integer)status;
      } else if(status != null) {
         throw new IllegalArgumentException("Unexpected virement status type:" + status);
      } else {
         return null;
      }
   }

   private Integer coerceUser(Object userId) {
      if(userId instanceof UserRef) {
         userId = ((UserRef)userId).getPrimaryKey();
      }

      if(userId instanceof String) {
         userId = UserPK.getKeyFromTokens(String.valueOf(userId));
      }

      return userId instanceof UserPK?Integer.valueOf(((UserPK)userId).getUserId()):null;
   }

   private List<StructureElementKey> coerceElements(List seIds) {
      ArrayList result = new ArrayList();

      for(int i = 0; i < seIds.size(); ++i) {
         result.add(this.coerceElement(seIds.get(i)));
      }

      return result;
   }

   private StructureElementKey coerceElement(Object element) {
      if(element instanceof int[]) {
         return new StructureElementKeyImpl(((int[])((int[])element))[0], ((int[])((int[])element))[1]);
      } else {
         if(element instanceof StructureElementRef) {
            element = ((StructureElementRef)element).getPrimaryKey();
         }

         if(element instanceof StructureElementCK) {
            return new StructureElementKeyImpl(((StructureElementPK)element).getStructureId(), ((StructureElementPK)element).getStructureElementId());
         } else {
            throw new IllegalArgumentException("Unknown type for StructureElementKey:" + element);
         }
      }
   }

   private Integer coerceVirementId(Object virementId) {
      if(virementId instanceof String) {
         virementId = Integer.valueOf(Integer.parseInt(String.valueOf(virementId)));
      }

      return virementId instanceof VirementRequestPK?Integer.valueOf(((VirementRequestPK)virementId).getRequestId()):(virementId instanceof Integer?(Integer)virementId:null);
   }
}
