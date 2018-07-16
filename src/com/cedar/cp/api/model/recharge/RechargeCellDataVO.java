// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.recharge;

import com.cedar.cp.api.base.EntityRef;
import java.io.Serializable;
import java.math.BigDecimal;

public class RechargeCellDataVO implements Serializable {

   private EntityRef[] mCellData;
   private Double mRatio;


   public RechargeCellDataVO() {}

   public RechargeCellDataVO(EntityRef[] cellData, BigDecimal ratio) {
      this.mCellData = cellData;
      if(ratio == null) {
         this.mRatio = null;
      } else {
         this.mRatio = Double.valueOf(ratio.doubleValue());
      }

   }

   public RechargeCellDataVO(EntityRef[] cellData) {
      this.mCellData = cellData;
   }

   public EntityRef[] getCellData() {
      return this.mCellData;
   }

   public void setCellData(EntityRef[] cellData) {
      this.mCellData = cellData;
   }

   public Double getRatio() {
      return this.mRatio;
   }

   public void setRatio(Object ratio) {
      if(ratio == null) {
         this.setRatio((Double)null);
      } else if(ratio instanceof Double) {
         this.setRatio((Double)ratio);
      }

   }

   public void setRatio(Double ratio) {
      this.mRatio = ratio;
   }
}
