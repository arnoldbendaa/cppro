// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.mapping.extsys;

import com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement;
import com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy;
import com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.extsys.ExternalEntityImpl;
import java.io.Serializable;
import java.util.List;

public class FinanceHierarchyElementImpl extends ExternalEntityImpl implements FinanceHierarchyElement, Serializable {

   private List<FinanceHierarchyElement> mFinanceHierarchyElements;
   private List<FinanceDimensionElement> mFinanceDimensionElements;
   private FinanceHierarchy mFinanceHierarchy;


   public FinanceHierarchyElementImpl(String elem, String hierName, String hierType, String descr) {
      this.setEntityRef(new EntityRefImpl(new MappingKeyImpl(elem, hierName, hierType), descr));
   }

   public String getHierarchyElem() {
      return (String)((MappingKeyImpl)this.getEntityRef().getPrimaryKey()).get(0);
   }

   public List<FinanceHierarchyElement> getFinanceHierarchyElements() {
      return this.mFinanceHierarchyElements;
   }

   public List<FinanceDimensionElement> getFinanceDimensionElements() {
      return this.mFinanceDimensionElements;
   }

   public void setFinanceHierarchyElements(List<FinanceHierarchyElement> financeHierarchyElements) {
      this.mFinanceHierarchyElements = financeHierarchyElements;
   }

   public void setFinanceDimensionElements(List<FinanceDimensionElement> financeDimensionElements) {
      this.mFinanceDimensionElements = financeDimensionElements;
   }

   public FinanceHierarchy getFinanceHierarchy() {
      return this.mFinanceHierarchy;
   }

   public void setFinanceHierarchy(FinanceHierarchy financeHierarchy) {
      this.mFinanceHierarchy = financeHierarchy;
   }
}
