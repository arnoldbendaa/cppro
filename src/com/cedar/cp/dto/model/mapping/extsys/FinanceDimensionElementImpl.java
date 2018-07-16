// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping.extsys;

import com.cedar.cp.api.model.mapping.extsys.FinanceDimension;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement;
import com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup;
import com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.extsys.ExternalEntityImpl;
import java.io.Serializable;

public class FinanceDimensionElementImpl extends ExternalEntityImpl implements FinanceDimensionElement, Serializable {

   private FinanceHierarchyElement mFinanceHierarchyElement;
   private FinanceDimension mFinanceDimension;
   private FinanceDimensionElementGroup mFinanceDimensionElementGroup;


   public FinanceDimensionElementImpl(String elem, String accType, String descr) {
      this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(elem, (Object)null, accType), descr));
   }

   public FinanceDimensionElementGroup getFinanceDimensionElementGroup() {
      return this.mFinanceDimensionElementGroup;
   }

   public void setFinanceDimensionElementGroup(FinanceDimensionElementGroup financeDimensionElementGroup) {
      this.mFinanceDimensionElementGroup = financeDimensionElementGroup;
   }

   public FinanceDimension getFinanceDimension() {
      return this.mFinanceDimension == null?(this.mFinanceDimensionElementGroup != null?this.mFinanceDimensionElementGroup.getFinanceDimension():this.mFinanceHierarchyElement.getFinanceHierarchy().getFinanceDimension()):this.mFinanceDimension;
   }

   public void setFinanceDimension(FinanceDimension financeDimension) {
      this.mFinanceDimension = financeDimension;
   }

   public FinanceHierarchyElement getFinanceHierarchyElement() {
      return this.mFinanceHierarchyElement;
   }

   public void setFinanceHierarchyElement(FinanceHierarchyElement financeHierarchyElement) {
      this.mFinanceHierarchyElement = financeHierarchyElement;
   }
}
