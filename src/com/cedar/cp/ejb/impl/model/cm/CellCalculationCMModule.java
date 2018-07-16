// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cm;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.ejb.impl.cm.event.RemovedDimensionElementEvent;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMMetaDataController;
import com.cedar.cp.ejb.impl.cm.frmwrk.CMUpdateAdapter;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG;
import com.cedar.cp.ejb.impl.model.CellCalcAssocDAO;
import com.cedar.cp.ejb.impl.model.CellCalculationDataDAO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentDAO;
import java.sql.SQLException;

public class CellCalculationCMModule extends CMUpdateAdapter {

   private CcDeploymentDAO mCcDeploymentDAO;
   private CellCalcAssocDAO mCellCalcAssocDAO;
   private CellCalculationDataDAO mCellCalculationDataDAO;
   private boolean mDimensionElementsRemoved;
   private boolean mAnyDimensionElementsRemoved;
   private CMMetaDataController mController;


   public CellCalculationCMModule(CMMetaDataController controller) {
      this.mController = controller;
   }

   public void initProcessing(ModelRef modelRef) {
      this.mDimensionElementsRemoved = false;
      this.mAnyDimensionElementsRemoved = false;
   }

   public void processEvent(RemovedDimensionElementEvent event) {
      this.mDimensionElementsRemoved = true;
      this.mAnyDimensionElementsRemoved = true;
   }

   public void terminateProcessing(DimensionDAG dimension) {
      if(this.mDimensionElementsRemoved) {
         try {
            EntityList e = this.mController.getFinanceCubes();

            for(int row = 0; row < e.getNumRows(); ++row) {
               FinanceCubeRefImpl fcRef = (FinanceCubeRefImpl)e.getValueAt(row, "FinanceCube");
               this.getCellCalculationDataDAO().dimensionElementsRemoved(fcRef.getFinanceCubePK().getFinanceCubeId(), this.mController.getDimensionIndex(dimension.getDimensionId()), dimension.getDimensionId());
            }
         } catch (SQLException var5) {
            var5.printStackTrace();
            throw new RuntimeException("Failed to tidy cell calculation data", var5);
         }
      }

   }

   public void terminateProcessing(ModelRef modelRef) {
      if(this.mAnyDimensionElementsRemoved) {
         try {
            this.getCcDeploymentDAO().tidyCellCalculationMetaData(this.mController.getModelEVO().getModelId(), this.mController.getDimensionCount());
         } catch (SQLException var3) {
            throw new CPException("Failed to tidy cell calcs", var3);
         }

         this.mAnyDimensionElementsRemoved = false;
      }

   }

   private CellCalcAssocDAO getCellCalcAssocDAO() {
      if(this.mCellCalcAssocDAO == null) {
         this.mCellCalcAssocDAO = new CellCalcAssocDAO();
      }

      return this.mCellCalcAssocDAO;
   }

   private CellCalculationDataDAO getCellCalculationDataDAO() {
      if(this.mCellCalculationDataDAO == null) {
         this.mCellCalculationDataDAO = new CellCalculationDataDAO();
      }

      return this.mCellCalculationDataDAO;
   }

   private CcDeploymentDAO getCcDeploymentDAO() {
      if(this.mCcDeploymentDAO == null) {
         this.mCcDeploymentDAO = new CcDeploymentDAO();
      }

      return this.mCcDeploymentDAO;
   }
}
