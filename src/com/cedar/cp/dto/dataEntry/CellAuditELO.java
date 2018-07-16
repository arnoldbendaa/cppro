// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.dto.base.AbstractELO;
import com.cedar.cp.dto.dataEntry.CellAuditDetailHeaderELO;
import com.cedar.cp.dto.dataEntry.CellAuditHeaderELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CellAuditELO extends AbstractELO implements Serializable {

   private transient CellAuditHeaderELO mHeader;
   private transient CellAuditDetailHeaderELO mDetail;


   public CellAuditELO() {
      super(new String[]{"Header", "Detail"});
   }

   public void add(CellAuditHeaderELO header, CellAuditDetailHeaderELO detail) {
      ArrayList l = new ArrayList();
      l.add(header);
      l.add(detail);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.mCurrRowIndex = -1;
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      byte var10002 = index;
      int index1 = index + 1;
      this.mHeader = (CellAuditHeaderELO)l.get(var10002);
      this.mDetail = (CellAuditDetailHeaderELO)l.get(index1);
   }

   public CellAuditHeaderELO getHeader() {
      return this.mHeader;
   }

   public CellAuditDetailHeaderELO getDetail() {
      return this.mDetail;
   }
}
