// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllDataEntryCellCalcsELO extends AbstractELO implements Serializable {

   private transient Integer mAccount;
   private transient String mDataType;
   private transient String mFormField;
   private transient Integer mCellCalcId;
   private transient Integer mXMLFormId;
   private transient Boolean mSummaryPeriod;


   public AllDataEntryCellCalcsELO() {
      super(new String[]{"AccountId", "DataType", "FormFieid", "CellCalcId", "XMLFormId", "SummaryPeriod"});
   }

   public void add(int accountId, String datatype, String formField, int cellCalcId, int xmlFormId, boolean summaryPeriod) {
      ArrayList l = new ArrayList();
      l.add(new Integer(accountId));
      l.add(datatype);
      l.add(formField);
      l.add(new Integer(cellCalcId));
      l.add(new Integer(xmlFormId));
      l.add(new Boolean(summaryPeriod));
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mAccount = (Integer)l.get(index);
      this.mDataType = (String)l.get(var4++);
      this.mFormField = (String)l.get(var4++);
      this.mCellCalcId = (Integer)l.get(var4++);
      this.mXMLFormId = (Integer)l.get(var4++);
      this.mSummaryPeriod = (Boolean)l.get(var4++);
   }
}
