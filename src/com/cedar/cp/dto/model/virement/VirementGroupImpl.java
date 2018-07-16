// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.virement.VirementGroup;
import com.cedar.cp.dto.model.virement.VirementLineImpl;
import com.cedar.cp.dto.model.virement.VirementRequestGroupPK;
import com.cedar.cp.dto.model.virement.VirementRequestImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VirementGroupImpl implements VirementGroup, Cloneable {

   private VirementRequestGroupPK mKey;
   private Map<DataTypeRef, Long> mRemainders = new HashMap();
   private String mNotes;
   private List mRows = new ArrayList();


   public Object getKey() {
      return this.mKey;
   }

   public void setPK(VirementRequestGroupPK pk) {
      this.mKey = pk;
   }

   public VirementRequestGroupPK getPK() {
      return this.mKey;
   }

   public String getNotes() {
      return this.mNotes;
   }

   public void setNotes(String notes) {
      this.mNotes = notes;
   }

   public List getRows() {
      return this.mRows;
   }

   public void setRows(List rows) {
      this.mRows = rows;
   }

   public VirementLineImpl findLine(Object key) {
      for(int row = 0; row < this.mRows.size(); ++row) {
         VirementLineImpl line = (VirementLineImpl)this.mRows.get(row);
         if(line.getKey() != null && line.getKey().equals(key)) {
            return line;
         }
      }

      return null;
   }

   public void removeLine(VirementLineImpl line) {
      this.mRows.remove(line);
   }

   public Object clone() throws CloneNotSupportedException {
      VirementGroupImpl cpy = (VirementGroupImpl)super.clone();
      cpy.setPK(this.getPK());
      cpy.setNotes(this.getNotes());
      ArrayList lines = new ArrayList();

      for(int i = 0; i < this.mRows.size(); ++i) {
         lines.add(((VirementLineImpl)this.mRows.get(i)).clone());
      }

      this.calculateRemainders();
      return cpy;
   }

   public void validate(VirementRequestImpl request) throws ValidationException {
      if(this.mRows != null && !this.mRows.isEmpty()) {
         this.calculateRemainders();
         Iterator i = this.mRemainders.keySet().iterator();

         while(i.hasNext()) {
            DataTypeRef line = (DataTypeRef)i.next();
            if(Math.abs(this.getRemainder(line)) > 99L) {
               throw new ValidationException("Budget transfer remainder must be zero for data type : " + line + " - " + line.getDescription() + " in group " + (request.getGroupIdx(this) + 1));
            }
         }

         i = this.mRows.iterator();

         while(i.hasNext()) {
            VirementLineImpl line1 = (VirementLineImpl)i.next();

            try {
               line1.validate();
            } catch (ValidationException var5) {
               throw new ValidationException(var5.getMessage() + " for line " + line1.getAddressVisIds(",") + " in group " + (request.getGroupIdx(this) + 1));
            }
         }

      } else {
         throw new ValidationException("Transfer group contains no transfer details");
      }
   }

   private void calculateRemainders() {
      this.mRemainders.clear();
      Iterator i = this.mRows.iterator();

      while(i.hasNext()) {
         VirementLineImpl line = (VirementLineImpl)i.next();
         long remainder = line.isTo()?-line.getTransferValueAsLong():line.getTransferValueAsLong();
         this.mRemainders.put(line.getDataTypeRef(), Long.valueOf(this.getRemainder(line.getDataTypeRef()) + remainder));
      }

   }

   public List getLinesForBudgetLocation(int seId) {
      ArrayList l = new ArrayList();
      Iterator lIter = this.mRows.iterator();

      while(lIter.hasNext()) {
         VirementLineImpl line = (VirementLineImpl)lIter.next();
         if(line.getBudgetLocationId() == seId) {
            l.add(line);
         }
      }

      return l;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("\nVirementGroupImpl");
      sb.append(" key:" + this.mKey != null?this.mKey.toString():"null");
      Iterator lIter = this.mRemainders.keySet().iterator();

      while(lIter.hasNext()) {
         DataTypeRef line = (DataTypeRef)lIter.next();
         sb.append(" Remainder for data type " + line + " is " + this.getRemainder(line));
      }

      sb.append(" notes:");
      sb.append(this.mNotes);
      lIter = this.mRows.iterator();

      while(lIter.hasNext()) {
         VirementLineImpl line1 = (VirementLineImpl)lIter.next();
         sb.append(line1.toString());
      }

      return sb.toString();
   }

   public boolean equals(Object obj) {
      if(!(obj instanceof VirementGroup)) {
         return false;
      } else {
         VirementGroup vg = (VirementGroup)obj;
         return (this.getKey() != null || vg.getKey() == null) && (this.getKey() == null || vg.getKey() != null) && (this.getKey() == null || vg.getKey() == null || this.getKey().equals(vg.getKey()));
      }
   }

   public String getKeyAsText() {
      return this.mKey != null?this.mKey.toTokens():null;
   }

   public long getRemainder(DataTypeRef dataTypeRef) {
      return this.mRemainders.get(dataTypeRef) != null?((Long)this.mRemainders.get(dataTypeRef)).longValue():0L;
   }
}
