// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.formrebuild;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.api.xmlform.rebuild.RebuildWrapper;
import com.cedar.cp.dto.dimension.StructureElementELO;
import com.cedar.cp.dto.model.BudgetCycleDetailedForIdELO;
import com.cedar.cp.dto.model.ModelDetailsWebELO;
import com.cedar.cp.dto.xmlform.XMLFormDefinitionELO;
import com.cedar.cp.dto.xmlform.rebuild.FormRebuildTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.formrebuild.RebuildTask$1;
import com.cedar.cp.ejb.base.async.formrebuild.RebuildTask$CubeXML;
import com.cedar.cp.ejb.base.async.formrebuild.RebuildTask$RebuildTaskCheckpoint;
import com.cedar.cp.ejb.base.common.util.SystemPropertyHelper;
import com.cedar.cp.ejb.base.cube.CubeUpdateEngine;
import com.cedar.cp.ejb.base.cube.flatform.FlatFormCubeUpdateEngine;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.model.BudgetCycleDAO;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormDAO;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.naming.InitialContext;

public class RebuildTask extends AbstractTask {

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      FormRebuildTaskRequest request = (FormRebuildTaskRequest)this.getRequest();
      if(this.getCheckpoint() == null) {
         this.expandParams(request);
         this.setCheckpoint(new RebuildTask$RebuildTaskCheckpoint());
      }

      this.getCheckpoint().setMaxBatchSize(SystemPropertyHelper.queryIntegerSystemProperty((Connection)null, "SYS: Form Rebuild batch size", 100));
      this.doWork(request);
      if(this.getCheckpoint().getListIndex() >= request.getRespArea().size() - 1) {
         this.setCheckpoint((TaskCheckpoint)null);
      }

   }

   public void doWork(FormRebuildTaskRequest request) throws Exception {
      BudgetCycleDAO bcDAO = new BudgetCycleDAO();
      BudgetCycleDetailedForIdELO bcELO = bcDAO.getBudgetCycleDetailedForId(request.getBudgetCycleId());
      bcELO.next();
      if(bcELO.getStatus() != 1) {
         this.log("Unable to process rebuild since BudgetCycle is not \'In Progress\' ");
      } else {
         Class wrapper = Class.forName("com.cedar.cp.utc.task.RebuildTaskRunner");
         Constructor wrapperConst = wrapper.getConstructor(new Class[]{CPConnection.class});
         Method m1 = wrapper.getMethod("doWork", new Class[]{Map.class, Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE});
         Method m2 = wrapper.getMethod("getWrapperResult", new Class[0]);
         Map selection = request.getSelectionCriteria();
         List respArea = request.getRespArea();
         RebuildTask$CubeXML data = new RebuildTask$CubeXML(this, (RebuildTask$1)null);

         int i;
         while((i = this.getCheckpoint().getNextIndex()) < respArea.size()) {
            selection.put(Integer.valueOf(0), respArea.get(i));
            this.log("Processing responsibility area " + respArea.get(i));
            Object engine = wrapperConst.newInstance(new Object[]{this.getCPConnection()});
            m1.invoke(engine, new Object[]{selection, Integer.valueOf(request.getModelId()), Integer.valueOf(request.getBudgetCycleId()), request.getDataType(), Integer.valueOf(request.getFormId())});
            RebuildWrapper result = (RebuildWrapper)m2.invoke(engine, new Object[0]);
            if(result.getXMLData() != null) {
               this.log("\tPosting deltas for " + result.getSelectionHeading());
               data.setXMLFormType(result.getFormType());
               data.addPostings(result.getXMLData());
            } else {
               this.log("\tNo Postings for " + result.getSelectionHeading());
            }

            if(this.getCheckpoint().shouldCheckpoint()) {
               break;
            }
         }

         if(data.getData().length() > 0) {
            String postingData;
            if(data.getXMLFormType() == 3) {
               CubeUpdateEngine engine2 = new CubeUpdateEngine();
               engine2.setTaskId(Integer.valueOf(this.getTaskId()));
               postingData = data.getPostingData();
               engine2.updateCube(new StringReader(postingData));
            } else if( (data.getXMLFormType() == 4) || (data.getXMLFormType() == 6)) {
               FlatFormCubeUpdateEngine engine1 = new FlatFormCubeUpdateEngine();
               engine1.setTaskId(this.getTaskId());
               postingData = data.getPostingData();
               engine1.updateCube(new StringReader(postingData));
            }
         }
      }

   }

   public void expandParams(FormRebuildTaskRequest request) {
      ModelDAO mDAO = new ModelDAO();
      ModelDetailsWebELO eList = mDAO.getModelDetailsWeb(request.getModelId());
      this.log("Model = " + eList.getValueAt(0, "VisId") + " " + eList.getValueAt(0, "Description"));
      BudgetCycleDAO bcDAO = new BudgetCycleDAO();
      BudgetCycleDetailedForIdELO eList1 = bcDAO.getBudgetCycleDetailedForId(request.getBudgetCycleId());
      this.log("BudgetCycle = " + eList1.getValueAt(0, "BudgetCycle") + " " + eList1.getValueAt(0, "Description"));
      XmlFormDAO formDAO = new XmlFormDAO();
      XMLFormDefinitionELO eList2 = formDAO.getXMLFormDefinition(request.getFormId());
      this.log("XMLForm = " + eList2.getValueAt(0, "XmlForm") + " " + eList2.getValueAt(0, "Description"));
      this.log("Rebuild is being run for " + request.getRespArea().size() + " budget locations");
      StructureElementDAO strucDAO = new StructureElementDAO();
      this.log("Selection criteria");
      Iterator i$ = request.getSelectionCriteria().keySet().iterator();

      while(i$.hasNext()) {
         Integer key = (Integer)i$.next();
         Integer value = (Integer)request.getSelectionCriteria().get(key);
         if(value == null) {
            this.log(key + " = null");
         } else {
            StructureElementELO eList3 = strucDAO.getStructureElement(value.intValue());
            this.log(key + " = " + eList3.getValueAt(0, "VisId") + " " + eList3.getValueAt(0, "Description"));
         }
      }

      this.log("Data type = " + request.getDataType());
   }

   public String getEntityName() {
      return "RebuildTask";
   }

   public int getReportType() {
      return 0;
   }

   public RebuildTask$RebuildTaskCheckpoint getCheckpoint() {
      return (RebuildTask$RebuildTaskCheckpoint)super.getCheckpoint();
   }
}
