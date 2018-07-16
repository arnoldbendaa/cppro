// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping.extsys;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.mapping.extsys.FinanceValueType;
import com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.List;

public class FinanceValueTypeOwnerImpl extends ExternalEntityImpl implements FinanceValueTypeOwner, Serializable {

   private List<FinanceValueType> mFinanceValueTypes;


   public FinanceValueTypeOwnerImpl(String vtoVisId, String descr) {
      this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(vtoVisId), descr));
   }

   public String getVisId() {
      return (String)((MappingKeyImpl)this.getEntityRef().getPrimaryKey()).get(0);
   }

   public FinanceValueTypeOwnerImpl(EntityRef entityRef, List<FinanceValueType> financeValueTypes) {
      super(entityRef);
      this.mFinanceValueTypes = financeValueTypes;
   }

   public List<FinanceValueType> getFinanceValueTypes() {
      return this.mFinanceValueTypes;
   }

   public void setFinanceValueTypes(List<FinanceValueType> financeValueTypes) {
      this.mFinanceValueTypes = financeValueTypes;
   }
}
