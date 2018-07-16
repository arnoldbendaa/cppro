// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dataEntry;

import com.cedar.cp.dto.base.AbstractELO;
import com.cedar.cp.dto.dataEntry.CellAuditDetailELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CellAuditDetailHeaderELO extends AbstractELO implements Serializable {

   private transient String mId;
   private transient String mDescription;
   private transient CellAuditDetailELO mAudit;


   public CellAuditDetailHeaderELO() {
      super(new String[]{"VisId", "Description", "Audit"});
   }

   public void add(String id, String description, CellAuditDetailELO audit) {
      ArrayList l = new ArrayList();
      l.add(id);
      l.add(description);
      l.add(audit);
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
      int var4 = index + 1;
      this.mId = l.get(index).toString();
      this.mDescription = l.get(var4++).toString();
      this.mAudit = (CellAuditDetailELO)l.get(var4);
   }

   public String getId() {
      return this.mId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public CellAuditDetailELO getAudit() {
      return this.mAudit;
   }
}
