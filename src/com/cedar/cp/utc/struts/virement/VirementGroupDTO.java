// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.LazyList;
import com.cedar.cp.utc.struts.virement.VirementGroupDTO$1;
import com.cedar.cp.utc.struts.virement.VirementLineDTO;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class VirementGroupDTO implements Serializable {

   private String mKey;
   private long mRemainder;
   private String mNotes;
   private Collection mLines = new LazyList(new VirementGroupDTO$1(this));


   public long getRemainder() {
      return this.mRemainder;
   }

   public void setRemainder(long remainder) {
      this.mRemainder = remainder;
   }

   public Collection getLines() {
      return this.mLines;
   }

   public void setLines(Collection lines) {
      this.mLines = lines;
   }

   public void addRow(VirementLineDTO row) {
      this.mLines.add(row);
   }

   public void removeRow(VirementLineDTO row) {
      this.mLines.remove(row);
   }

   public String getNotes() {
      return this.mNotes;
   }

   public void setNotes(String notes) {
      this.mNotes = notes;
   }

   public int getNumRows() {
      return this.mLines.size();
   }

   public String getKey() {
      return this.mKey != null && this.mKey.trim().length() == 0?null:this.mKey;
   }

   public void setKey(String key) {
      this.mKey = key;
   }

   public VirementLineDTO findLine(Object key) {
      Iterator lIter = this.mLines.iterator();

      VirementLineDTO lineDTO;
      do {
         if(!lIter.hasNext()) {
            return null;
         }

         lineDTO = (VirementLineDTO)lIter.next();
      } while(lineDTO.getKey() == null || key == null || !lineDTO.getKey().equals(key));

      return lineDTO;
   }

   public void update(VirementGroupDTO srcGroup) {
      this.mKey = srcGroup.mKey;
      this.mRemainder = srcGroup.mRemainder;
      this.mNotes = srcGroup.mNotes;
      this.updateLines((List)srcGroup.getLines());
   }

   private void updateLines(List<VirementLineDTO> srcLines) {
      Iterator lIter = srcLines.iterator();

      while(lIter.hasNext()) {
         VirementLineDTO srcLineDTO = (VirementLineDTO)lIter.next();
         VirementLineDTO thisLineDTO = this.findLine(srcLineDTO.getKey());
         if(thisLineDTO != null) {
            thisLineDTO.update(srcLineDTO);
         } else if(srcLineDTO.getKey() == null || srcLineDTO.getKey().length() == 0) {
            thisLineDTO = (VirementLineDTO)((List)this.mLines).get(srcLines.indexOf(srcLineDTO));
            thisLineDTO.update(srcLineDTO);
         }
      }

   }
}
