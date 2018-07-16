package com.cedar.cp.dto.model.globalmapping2.extsys;

import com.cedar.cp.api.model.globalmapping2.extsys.FinancePeriod;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.globalmapping2.extsys.ExternalEntityImpl;
import java.io.Serializable;

public class FinancePeriodImpl extends ExternalEntityImpl implements FinancePeriod, Serializable {

   private String mDescription;
   private int mPeriod;


   public FinancePeriodImpl() {}

   public FinancePeriodImpl(String periodVisId, String descr) {
      this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(periodVisId), descr));
   }

   public void setPeriod(int period) {
      this.mPeriod = period;
   }

   public int getPeriod() {
      return this.mPeriod;
   }

   public int hashCode() {
      return this.mPeriod;
   }

   public boolean equals(Object obj) {
      if(!(obj instanceof FinancePeriodImpl)) {
         return false;
      } else {
         FinancePeriodImpl other = (FinancePeriodImpl)obj;
         return super.equals(obj) && other.mPeriod == this.mPeriod;
      }
   }

   public String toString() {
      return this.getEntityRef().getNarrative();
   }
}
