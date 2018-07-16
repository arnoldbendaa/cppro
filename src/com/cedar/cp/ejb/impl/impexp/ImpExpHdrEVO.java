// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.impexp;

import com.cedar.cp.api.impexp.ImpExpHdrRef;
import com.cedar.cp.dto.impexp.ImpExpHdrPK;
import com.cedar.cp.dto.impexp.ImpExpHdrRefImpl;
import com.cedar.cp.dto.impexp.ImpExpRowPK;
import com.cedar.cp.ejb.impl.impexp.ImpExpRowEVO;
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

public class ImpExpHdrEVO implements Serializable {

   private transient ImpExpHdrPK mPK;
   private int mBatchId;
   private Timestamp mBatchTs;
   private int mFinanceCubeId;
   private int mBatchType;
   private Map<ImpExpRowPK, ImpExpRowEVO> mIMP_EXP_ROW;
   protected boolean mIMP_EXP_ROWAllItemsLoaded;
   private boolean mModified;
   public static final int IMPORT = 0;
   public static final int EXPORT = 1;


   public ImpExpHdrEVO() {}

   public ImpExpHdrEVO(int newBatchId, Timestamp newBatchTs, int newFinanceCubeId, int newBatchType, Collection newIMP_EXP_ROW) {
      this.mBatchId = newBatchId;
      this.mBatchTs = newBatchTs;
      this.mFinanceCubeId = newFinanceCubeId;
      this.mBatchType = newBatchType;
      this.setIMP_EXP_ROW(newIMP_EXP_ROW);
   }

   public void setIMP_EXP_ROW(Collection<ImpExpRowEVO> items) {
      if(items != null) {
         if(this.mIMP_EXP_ROW == null) {
            this.mIMP_EXP_ROW = new HashMap();
         } else {
            this.mIMP_EXP_ROW.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ImpExpRowEVO child = (ImpExpRowEVO)i$.next();
            this.mIMP_EXP_ROW.put(child.getPK(), child);
         }
      } else {
         this.mIMP_EXP_ROW = null;
      }

   }

   public ImpExpHdrPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ImpExpHdrPK(this.mBatchId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getBatchId() {
      return this.mBatchId;
   }

   public Timestamp getBatchTs() {
      return this.mBatchTs;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getBatchType() {
      return this.mBatchType;
   }

   public void setBatchId(int newBatchId) {
      if(this.mBatchId != newBatchId) {
         this.mModified = true;
         this.mBatchId = newBatchId;
         this.mPK = null;
      }
   }

   public void setFinanceCubeId(int newFinanceCubeId) {
      if(this.mFinanceCubeId != newFinanceCubeId) {
         this.mModified = true;
         this.mFinanceCubeId = newFinanceCubeId;
      }
   }

   public void setBatchType(int newBatchType) {
      if(this.mBatchType != newBatchType) {
         this.mModified = true;
         this.mBatchType = newBatchType;
      }
   }

   public void setBatchTs(Timestamp newBatchTs) {
      if(this.mBatchTs != null && newBatchTs == null || this.mBatchTs == null && newBatchTs != null || this.mBatchTs != null && newBatchTs != null && !this.mBatchTs.equals(newBatchTs)) {
         this.mBatchTs = newBatchTs;
         this.mModified = true;
      }

   }

   public void setDetails(ImpExpHdrEVO newDetails) {
      this.setBatchId(newDetails.getBatchId());
      this.setBatchTs(newDetails.getBatchTs());
      this.setFinanceCubeId(newDetails.getFinanceCubeId());
      this.setBatchType(newDetails.getBatchType());
   }

   public ImpExpHdrEVO deepClone() {
      ImpExpHdrEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ImpExpHdrEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mBatchId > 0) {
         newKey = true;
         this.mBatchId = 0;
      } else if(this.mBatchId < 1) {
         newKey = true;
      }

      ImpExpRowEVO item;
      if(this.mIMP_EXP_ROW != null) {
         for(Iterator iter = (new ArrayList(this.mIMP_EXP_ROW.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ImpExpRowEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mBatchId < 1) {
         returnCount = startCount + 1;
      }

      ImpExpRowEVO item;
      if(this.mIMP_EXP_ROW != null) {
         for(Iterator iter = this.mIMP_EXP_ROW.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ImpExpRowEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mBatchId < 1) {
         this.mBatchId = startKey;
         nextKey = startKey + 1;
      }

      ImpExpRowEVO item;
      if(this.mIMP_EXP_ROW != null) {
         for(Iterator iter = (new ArrayList(this.mIMP_EXP_ROW.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ImpExpRowEVO)iter.next();
            this.changeKey(item, this.mBatchId, item.getRowNo());
         }
      }

      return nextKey;
   }

   public Collection<ImpExpRowEVO> getIMP_EXP_ROW() {
      return this.mIMP_EXP_ROW != null?this.mIMP_EXP_ROW.values():null;
   }

   public Map<ImpExpRowPK, ImpExpRowEVO> getIMP_EXP_ROWMap() {
      return this.mIMP_EXP_ROW;
   }

   public void loadIMP_EXP_ROWItem(ImpExpRowEVO newItem) {
      if(this.mIMP_EXP_ROW == null) {
         this.mIMP_EXP_ROW = new HashMap();
      }

      this.mIMP_EXP_ROW.put(newItem.getPK(), newItem);
   }

   public void addIMP_EXP_ROWItem(ImpExpRowEVO newItem) {
      if(this.mIMP_EXP_ROW == null) {
         this.mIMP_EXP_ROW = new HashMap();
      }

      ImpExpRowPK newPK = newItem.getPK();
      if(this.getIMP_EXP_ROWItem(newPK) != null) {
         throw new RuntimeException("addIMP_EXP_ROWItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mIMP_EXP_ROW.put(newPK, newItem);
      }
   }

   public void changeIMP_EXP_ROWItem(ImpExpRowEVO changedItem) {
      if(this.mIMP_EXP_ROW == null) {
         throw new RuntimeException("changeIMP_EXP_ROWItem: no items in collection");
      } else {
         ImpExpRowPK changedPK = changedItem.getPK();
         ImpExpRowEVO listItem = this.getIMP_EXP_ROWItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeIMP_EXP_ROWItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteIMP_EXP_ROWItem(ImpExpRowPK removePK) {
      ImpExpRowEVO listItem = this.getIMP_EXP_ROWItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeIMP_EXP_ROWItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ImpExpRowEVO getIMP_EXP_ROWItem(ImpExpRowPK pk) {
      return (ImpExpRowEVO)this.mIMP_EXP_ROW.get(pk);
   }

   public ImpExpRowEVO getIMP_EXP_ROWItem() {
      if(this.mIMP_EXP_ROW.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mIMP_EXP_ROW.size());
      } else {
         Iterator iter = this.mIMP_EXP_ROW.values().iterator();
         return (ImpExpRowEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public ImpExpHdrRef getEntityRef(String entityText) {
      return new ImpExpHdrRefImpl(this.getPK(), entityText);
   }

   public void postCreateInit() {
      this.mIMP_EXP_ROWAllItemsLoaded = true;
      if(this.mIMP_EXP_ROW == null) {
         this.mIMP_EXP_ROW = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("BatchId=");
      sb.append(String.valueOf(this.mBatchId));
      sb.append(' ');
      sb.append("BatchTs=");
      sb.append(String.valueOf(this.mBatchTs));
      sb.append(' ');
      sb.append("FinanceCubeId=");
      sb.append(String.valueOf(this.mFinanceCubeId));
      sb.append(' ');
      sb.append("BatchType=");
      sb.append(String.valueOf(this.mBatchType));
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

      sb.append("ImpExpHdr: ");
      sb.append(this.toString());
      if(this.mIMP_EXP_ROWAllItemsLoaded || this.mIMP_EXP_ROW != null) {
         sb.delete(indent, sb.length());
         sb.append(" - IMP_EXP_ROW: allItemsLoaded=");
         sb.append(String.valueOf(this.mIMP_EXP_ROWAllItemsLoaded));
         sb.append(" items=");
         if(this.mIMP_EXP_ROW == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mIMP_EXP_ROW.size()));
         }
      }

      if(this.mIMP_EXP_ROW != null) {
         Iterator var5 = this.mIMP_EXP_ROW.values().iterator();

         while(var5.hasNext()) {
            ImpExpRowEVO listItem = (ImpExpRowEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ImpExpRowEVO child, int newBatchId, int newRowNo) {
      if(this.getIMP_EXP_ROWItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mIMP_EXP_ROW.remove(child.getPK());
         child.setBatchId(newBatchId);
         child.setRowNo(newRowNo);
         this.mIMP_EXP_ROW.put(child.getPK(), child);
      }
   }

   public void setIMP_EXP_ROWAllItemsLoaded(boolean allItemsLoaded) {
      this.mIMP_EXP_ROWAllItemsLoaded = allItemsLoaded;
   }

   public boolean isIMP_EXP_ROWAllItemsLoaded() {
      return this.mIMP_EXP_ROWAllItemsLoaded;
   }
}
