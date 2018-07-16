// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.ListModels$MyCheckpoint;
import com.cedar.cp.ejb.impl.dimension.DimensionAccessor;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelAccessor;
import com.cedar.cp.ejb.impl.model.ModelDimensionRelEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.util.Log;
import java.util.Iterator;
import javax.naming.InitialContext;

public class ListModels extends AbstractTask {

   private transient InitialContext mInitialContext;
   private transient ModelAccessor mModelAccessor;
   private transient DimensionAccessor mDimensionAccessor;
   private transient Log _log = new Log(this.getClass());


   public void runUnitOfWork(InitialContext context) throws Exception {
      this.mInitialContext = context;
      if(this.getCheckpoint() == null) {
         this.firstTime();
      }

      this.doWork(10);
   }

   public int getReportType() {
      return 0;
   }

   private void firstTime() throws Exception {
      this.setCheckpoint(new ListModels$MyCheckpoint());
      this.getCheckpoint().setAllModels(this.getModelAccessor().getAllModels());
      AllModelsELO elo = this.getCheckpoint().getAllModels();
      elo.reset();

      while(elo.hasNext()) {
         elo.next();
         ModelRef er = elo.getModelEntityRef();
      }

   }

   private void doWork(int checkpointLimit) throws Exception {
      int currentKey = 0;
      AllModelsELO elo = this.getCheckpoint().getAllModels();
      elo.reset();
      if(this.getCheckpoint().getLastKey() > 0) {
         while(currentKey < this.getCheckpoint().getLastKey()) {
            elo.next();
            ++currentKey;
         }
      }

      boolean i = false;

      int var7;
      for(var7 = 0; var7 < checkpointLimit && elo.hasNext(); ++var7) {
         elo.next();
         ModelRef er = elo.getModelEntityRef();
         ModelPK pk = (ModelPK)er.getPrimaryKey();
         this.printModel(pk);
         ++currentKey;
      }

      if(var7 == checkpointLimit) {
         this.getCheckpoint().setLastKey(currentKey);
      } else {
         this.setCheckpoint((TaskCheckpoint)null);
      }

   }

   private void printModel(ModelPK pk) throws Exception {
      ModelEVO model = this.getModelAccessor().getDetails(pk, "<0><1><2><3><4><5><6><7><8><9><10><11><12><13><14><15><16><17><18><19><20><21><22><23><24><25><26><27><28><29><30><31><32><33><34><35><36><37><38><39><40><41><42><43><44><45><46><47><48>");
      this._log.info("printModel", model);
      DimensionEVO accountEVO = this.getDimensionAccessor().getDetails(new DimensionPK(model.getAccountId()), "");
      DimensionEVO calendarEVO = this.getDimensionAccessor().getDetails(new DimensionPK(model.getCalendarId()), "");
      int dimIndex = 0;
      Iterator i;
      if(model.getModelDimensionRels() != null) {
         i = model.getModelDimensionRels().iterator();

         while(i.hasNext()) {
            ModelDimensionRelEVO fincubeEVO = (ModelDimensionRelEVO)i.next();
            DimensionEVO businessEVO = this.getDimensionAccessor().getDetails(new DimensionPK(fincubeEVO.getDimensionId()), "");
            if(businessEVO.getType() == 2) {
               this._log.info("printModel", "    dim" + dimIndex++ + "    bus=" + businessEVO.getVisId());
            }
         }
      }

      this._log.info("printModel", "    dim" + dimIndex++ + "    acc=" + accountEVO.getVisId());
      this._log.info("printModel", "    dim" + dimIndex++ + "    cal=" + calendarEVO.getVisId());
      if(model.getFinanceCubes() != null) {
         i = model.getFinanceCubes().iterator();

         while(i.hasNext()) {
            FinanceCubeEVO var9 = (FinanceCubeEVO)i.next();
            this._log.info("printModel", "    cube=" + var9);
         }
      }

   }

   private ModelAccessor getModelAccessor() throws Exception {
      if(this.mModelAccessor == null) {
         this.mModelAccessor = new ModelAccessor(this.mInitialContext);
      }

      return this.mModelAccessor;
   }

   private DimensionAccessor getDimensionAccessor() throws Exception {
      if(this.mDimensionAccessor == null) {
         this.mDimensionAccessor = new DimensionAccessor(this.mInitialContext);
      }

      return this.mDimensionAccessor;
   }

   public ListModels$MyCheckpoint getCheckpoint() {
      return (ListModels$MyCheckpoint)super.getCheckpoint();
   }

   public String getEntityName() {
      return "ListModels";
   }
}
