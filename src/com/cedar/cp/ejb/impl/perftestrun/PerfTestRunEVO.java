// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftestrun;

import com.cedar.cp.api.perftestrun.PerfTestRunRef;
import com.cedar.cp.dto.perftestrun.PerfTestRunPK;
import com.cedar.cp.dto.perftestrun.PerfTestRunRefImpl;
import com.cedar.cp.dto.perftestrun.PerfTestRunResultPK;
import com.cedar.cp.ejb.impl.perftestrun.PerfTestRunResultEVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PerfTestRunEVO implements Serializable {

   private transient PerfTestRunPK mPK;
   private int mPerfTestRunId;
   private String mVisId;
   private String mDescription;
   private boolean mShipped;
   private Timestamp mWhenRan;
   private Map<PerfTestRunResultPK, PerfTestRunResultEVO> mPerfTestRunResults;
   protected boolean mPerfTestRunResultsAllItemsLoaded;
   private boolean mModified;


   public PerfTestRunEVO() {}

   public PerfTestRunEVO(int newPerfTestRunId, String newVisId, String newDescription, boolean newShipped, Timestamp newWhenRan, Collection newPerfTestRunResults) {
      this.mPerfTestRunId = newPerfTestRunId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mShipped = newShipped;
      this.mWhenRan = newWhenRan;
      this.setPerfTestRunResults(newPerfTestRunResults);
   }

   public void setPerfTestRunResults(Collection<PerfTestRunResultEVO> items) {
      if(items != null) {
         if(this.mPerfTestRunResults == null) {
            this.mPerfTestRunResults = new HashMap();
         } else {
            this.mPerfTestRunResults.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            PerfTestRunResultEVO child = (PerfTestRunResultEVO)i$.next();
            this.mPerfTestRunResults.put(child.getPK(), child);
         }
      } else {
         this.mPerfTestRunResults = null;
      }

   }

   public PerfTestRunPK getPK() {
      if(this.mPK == null) {
         this.mPK = new PerfTestRunPK(this.mPerfTestRunId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getPerfTestRunId() {
      return this.mPerfTestRunId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean getShipped() {
      return this.mShipped;
   }

   public Timestamp getWhenRan() {
      return this.mWhenRan;
   }

   public void setPerfTestRunId(int newPerfTestRunId) {
      if(this.mPerfTestRunId != newPerfTestRunId) {
         this.mModified = true;
         this.mPerfTestRunId = newPerfTestRunId;
         this.mPK = null;
      }
   }

   public void setShipped(boolean newShipped) {
      if(this.mShipped != newShipped) {
         this.mModified = true;
         this.mShipped = newShipped;
      }
   }

   public void setVisId(String newVisId) {
      if(this.mVisId != null && newVisId == null || this.mVisId == null && newVisId != null || this.mVisId != null && newVisId != null && !this.mVisId.equals(newVisId)) {
         this.mVisId = newVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setWhenRan(Timestamp newWhenRan) {
      if(this.mWhenRan != null && newWhenRan == null || this.mWhenRan == null && newWhenRan != null || this.mWhenRan != null && newWhenRan != null && !this.mWhenRan.equals(newWhenRan)) {
         this.mWhenRan = newWhenRan;
         this.mModified = true;
      }

   }

   public void setDetails(PerfTestRunEVO newDetails) {
      this.setPerfTestRunId(newDetails.getPerfTestRunId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setShipped(newDetails.getShipped());
      this.setWhenRan(newDetails.getWhenRan());
   }

   public PerfTestRunEVO deepClone() {
      PerfTestRunEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (PerfTestRunEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mPerfTestRunId > 0) {
         newKey = true;
         this.mPerfTestRunId = 0;
      } else if(this.mPerfTestRunId < 1) {
         newKey = true;
      }

      PerfTestRunResultEVO item;
      if(this.mPerfTestRunResults != null) {
         for(Iterator iter = (new ArrayList(this.mPerfTestRunResults.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (PerfTestRunResultEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mPerfTestRunId < 1) {
         returnCount = startCount + 1;
      }

      PerfTestRunResultEVO item;
      if(this.mPerfTestRunResults != null) {
         for(Iterator iter = this.mPerfTestRunResults.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (PerfTestRunResultEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mPerfTestRunId < 1) {
         this.mPerfTestRunId = startKey;
         nextKey = startKey + 1;
      }

      PerfTestRunResultEVO item;
      if(this.mPerfTestRunResults != null) {
         for(Iterator iter = (new ArrayList(this.mPerfTestRunResults.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (PerfTestRunResultEVO)iter.next();
            item.setPerfTestRunId(this.mPerfTestRunId);
         }
      }

      return nextKey;
   }

   public Collection<PerfTestRunResultEVO> getPerfTestRunResults() {
      return this.mPerfTestRunResults != null?this.mPerfTestRunResults.values():null;
   }

   public Map<PerfTestRunResultPK, PerfTestRunResultEVO> getPerfTestRunResultsMap() {
      return this.mPerfTestRunResults;
   }

   public void loadPerfTestRunResultsItem(PerfTestRunResultEVO newItem) {
      if(this.mPerfTestRunResults == null) {
         this.mPerfTestRunResults = new HashMap();
      }

      this.mPerfTestRunResults.put(newItem.getPK(), newItem);
   }

   public void addPerfTestRunResultsItem(PerfTestRunResultEVO newItem) {
      if(this.mPerfTestRunResults == null) {
         this.mPerfTestRunResults = new HashMap();
      }

      PerfTestRunResultPK newPK = newItem.getPK();
      if(this.getPerfTestRunResultsItem(newPK) != null) {
         throw new RuntimeException("addPerfTestRunResultsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mPerfTestRunResults.put(newPK, newItem);
      }
   }

   public void changePerfTestRunResultsItem(PerfTestRunResultEVO changedItem) {
      if(this.mPerfTestRunResults == null) {
         throw new RuntimeException("changePerfTestRunResultsItem: no items in collection");
      } else {
         PerfTestRunResultPK changedPK = changedItem.getPK();
         PerfTestRunResultEVO listItem = this.getPerfTestRunResultsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changePerfTestRunResultsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deletePerfTestRunResultsItem(PerfTestRunResultPK removePK) {
      PerfTestRunResultEVO listItem = this.getPerfTestRunResultsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removePerfTestRunResultsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public PerfTestRunResultEVO getPerfTestRunResultsItem(PerfTestRunResultPK pk) {
      return (PerfTestRunResultEVO)this.mPerfTestRunResults.get(pk);
   }

   public PerfTestRunResultEVO getPerfTestRunResultsItem() {
      if(this.mPerfTestRunResults.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mPerfTestRunResults.size());
      } else {
         Iterator iter = this.mPerfTestRunResults.values().iterator();
         return (PerfTestRunResultEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public PerfTestRunRef getEntityRef(String entityText) {
      return new PerfTestRunRefImpl(this.getPK(), entityText);
   }

   public void postCreateInit() {
      this.mPerfTestRunResultsAllItemsLoaded = true;
      if(this.mPerfTestRunResults == null) {
         this.mPerfTestRunResults = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("PerfTestRunId=");
      sb.append(String.valueOf(this.mPerfTestRunId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("Shipped=");
      sb.append(String.valueOf(this.mShipped));
      sb.append(' ');
      sb.append("WhenRan=");
      sb.append(String.valueOf(this.mWhenRan));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i$ = 0; i$ < indent; ++i$) {
         sb.append(' ');
      }

      sb.append("PerfTestRun: ");
      sb.append(this.toString());
      if(this.mPerfTestRunResultsAllItemsLoaded || this.mPerfTestRunResults != null) {
         sb.delete(indent, sb.length());
         sb.append(" - PerfTestRunResults: allItemsLoaded=");
         sb.append(String.valueOf(this.mPerfTestRunResultsAllItemsLoaded));
         sb.append(" items=");
         if(this.mPerfTestRunResults == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mPerfTestRunResults.size()));
         }
      }

      if(this.mPerfTestRunResults != null) {
         Iterator var5 = this.mPerfTestRunResults.values().iterator();

         while(var5.hasNext()) {
            PerfTestRunResultEVO listItem = (PerfTestRunResultEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(PerfTestRunResultEVO child, int newPerfTestRunResultId) {
      if(this.getPerfTestRunResultsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mPerfTestRunResults.remove(child.getPK());
         child.setPerfTestRunResultId(newPerfTestRunResultId);
         this.mPerfTestRunResults.put(child.getPK(), child);
      }
   }

   public void setPerfTestRunResultsAllItemsLoaded(boolean allItemsLoaded) {
      this.mPerfTestRunResultsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isPerfTestRunResultsAllItemsLoaded() {
      return this.mPerfTestRunResultsAllItemsLoaded;
   }
}
