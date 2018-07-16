// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:37
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc;

import com.cedar.cp.api.model.cc.CcDeploymentRef;
import com.cedar.cp.dto.model.cc.CcDeploymentCK;
import com.cedar.cp.dto.model.cc.CcDeploymentLinePK;
import com.cedar.cp.dto.model.cc.CcDeploymentPK;
import com.cedar.cp.dto.model.cc.CcDeploymentRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentLineEVO;
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

public class CcDeploymentEVO implements Serializable {

   private transient CcDeploymentPK mPK;
   private int mCcDeploymentId;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private int mXmlformId;
   private Integer mDimContext0;
   private Integer mDimContext1;
   private Integer mDimContext2;
   private Integer mDimContext3;
   private Integer mDimContext4;
   private Integer mDimContext5;
   private Integer mDimContext6;
   private Integer mDimContext7;
   private Integer mDimContext8;
   private Integer mDimContext9;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<CcDeploymentLinePK, CcDeploymentLineEVO> mCCDeploymentLines;
   protected boolean mCCDeploymentLinesAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int CONTEXTUAL = 0;
   public static final int EXPLICIT = 1;
   public static final int EMBEDDED = 2;


   public CcDeploymentEVO() {}

   public CcDeploymentEVO(int newCcDeploymentId, int newModelId, String newVisId, String newDescription, int newXmlformId, Integer newDimContext0, Integer newDimContext1, Integer newDimContext2, Integer newDimContext3, Integer newDimContext4, Integer newDimContext5, Integer newDimContext6, Integer newDimContext7, Integer newDimContext8, Integer newDimContext9, int newVersionNum, Collection newCCDeploymentLines) {
      this.mCcDeploymentId = newCcDeploymentId;
      this.mModelId = newModelId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mXmlformId = newXmlformId;
      this.mDimContext0 = newDimContext0;
      this.mDimContext1 = newDimContext1;
      this.mDimContext2 = newDimContext2;
      this.mDimContext3 = newDimContext3;
      this.mDimContext4 = newDimContext4;
      this.mDimContext5 = newDimContext5;
      this.mDimContext6 = newDimContext6;
      this.mDimContext7 = newDimContext7;
      this.mDimContext8 = newDimContext8;
      this.mDimContext9 = newDimContext9;
      this.mVersionNum = newVersionNum;
      this.setCCDeploymentLines(newCCDeploymentLines);
   }

   public void setCCDeploymentLines(Collection<CcDeploymentLineEVO> items) {
      if(items != null) {
         if(this.mCCDeploymentLines == null) {
            this.mCCDeploymentLines = new HashMap();
         } else {
            this.mCCDeploymentLines.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            CcDeploymentLineEVO child = (CcDeploymentLineEVO)i$.next();
            this.mCCDeploymentLines.put(child.getPK(), child);
         }
      } else {
         this.mCCDeploymentLines = null;
      }

   }

   public CcDeploymentPK getPK() {
      if(this.mPK == null) {
         this.mPK = new CcDeploymentPK(this.mCcDeploymentId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getCcDeploymentId() {
      return this.mCcDeploymentId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getXmlformId() {
      return this.mXmlformId;
   }

   public Integer[] getDimContextArray() {
      return new Integer[]{this.getDimContext0(), this.getDimContext1(), this.getDimContext2(), this.getDimContext3(), this.getDimContext4(), this.getDimContext5(), this.getDimContext6(), this.getDimContext7(), this.getDimContext8(), this.getDimContext9()};
   }

   public void setDimContextArray(Integer[] p) {
      this.setDimContext0(p[0]);
      this.setDimContext1(p[1]);
      this.setDimContext2(p[2]);
      this.setDimContext3(p[3]);
      this.setDimContext4(p[4]);
      this.setDimContext5(p[5]);
      this.setDimContext6(p[6]);
      this.setDimContext7(p[7]);
      this.setDimContext8(p[8]);
      this.setDimContext9(p[9]);
   }

   public Integer getDimContext0() {
      return this.mDimContext0;
   }

   public Integer getDimContext1() {
      return this.mDimContext1;
   }

   public Integer getDimContext2() {
      return this.mDimContext2;
   }

   public Integer getDimContext3() {
      return this.mDimContext3;
   }

   public Integer getDimContext4() {
      return this.mDimContext4;
   }

   public Integer getDimContext5() {
      return this.mDimContext5;
   }

   public Integer getDimContext6() {
      return this.mDimContext6;
   }

   public Integer getDimContext7() {
      return this.mDimContext7;
   }

   public Integer getDimContext8() {
      return this.mDimContext8;
   }

   public Integer getDimContext9() {
      return this.mDimContext9;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public int getUpdatedByUserId() {
      return this.mUpdatedByUserId;
   }

   public Timestamp getUpdatedTime() {
      return this.mUpdatedTime;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public void setCcDeploymentId(int newCcDeploymentId) {
      if(this.mCcDeploymentId != newCcDeploymentId) {
         this.mModified = true;
         this.mCcDeploymentId = newCcDeploymentId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setXmlformId(int newXmlformId) {
      if(this.mXmlformId != newXmlformId) {
         this.mModified = true;
         this.mXmlformId = newXmlformId;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
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

   public void setDimContext0(Integer newDimContext0) {
      if(this.mDimContext0 != null && newDimContext0 == null || this.mDimContext0 == null && newDimContext0 != null || this.mDimContext0 != null && newDimContext0 != null && !this.mDimContext0.equals(newDimContext0)) {
         this.mDimContext0 = newDimContext0;
         this.mModified = true;
      }

   }

   public void setDimContext1(Integer newDimContext1) {
      if(this.mDimContext1 != null && newDimContext1 == null || this.mDimContext1 == null && newDimContext1 != null || this.mDimContext1 != null && newDimContext1 != null && !this.mDimContext1.equals(newDimContext1)) {
         this.mDimContext1 = newDimContext1;
         this.mModified = true;
      }

   }

   public void setDimContext2(Integer newDimContext2) {
      if(this.mDimContext2 != null && newDimContext2 == null || this.mDimContext2 == null && newDimContext2 != null || this.mDimContext2 != null && newDimContext2 != null && !this.mDimContext2.equals(newDimContext2)) {
         this.mDimContext2 = newDimContext2;
         this.mModified = true;
      }

   }

   public void setDimContext3(Integer newDimContext3) {
      if(this.mDimContext3 != null && newDimContext3 == null || this.mDimContext3 == null && newDimContext3 != null || this.mDimContext3 != null && newDimContext3 != null && !this.mDimContext3.equals(newDimContext3)) {
         this.mDimContext3 = newDimContext3;
         this.mModified = true;
      }

   }

   public void setDimContext4(Integer newDimContext4) {
      if(this.mDimContext4 != null && newDimContext4 == null || this.mDimContext4 == null && newDimContext4 != null || this.mDimContext4 != null && newDimContext4 != null && !this.mDimContext4.equals(newDimContext4)) {
         this.mDimContext4 = newDimContext4;
         this.mModified = true;
      }

   }

   public void setDimContext5(Integer newDimContext5) {
      if(this.mDimContext5 != null && newDimContext5 == null || this.mDimContext5 == null && newDimContext5 != null || this.mDimContext5 != null && newDimContext5 != null && !this.mDimContext5.equals(newDimContext5)) {
         this.mDimContext5 = newDimContext5;
         this.mModified = true;
      }

   }

   public void setDimContext6(Integer newDimContext6) {
      if(this.mDimContext6 != null && newDimContext6 == null || this.mDimContext6 == null && newDimContext6 != null || this.mDimContext6 != null && newDimContext6 != null && !this.mDimContext6.equals(newDimContext6)) {
         this.mDimContext6 = newDimContext6;
         this.mModified = true;
      }

   }

   public void setDimContext7(Integer newDimContext7) {
      if(this.mDimContext7 != null && newDimContext7 == null || this.mDimContext7 == null && newDimContext7 != null || this.mDimContext7 != null && newDimContext7 != null && !this.mDimContext7.equals(newDimContext7)) {
         this.mDimContext7 = newDimContext7;
         this.mModified = true;
      }

   }

   public void setDimContext8(Integer newDimContext8) {
      if(this.mDimContext8 != null && newDimContext8 == null || this.mDimContext8 == null && newDimContext8 != null || this.mDimContext8 != null && newDimContext8 != null && !this.mDimContext8.equals(newDimContext8)) {
         this.mDimContext8 = newDimContext8;
         this.mModified = true;
      }

   }

   public void setDimContext9(Integer newDimContext9) {
      if(this.mDimContext9 != null && newDimContext9 == null || this.mDimContext9 == null && newDimContext9 != null || this.mDimContext9 != null && newDimContext9 != null && !this.mDimContext9.equals(newDimContext9)) {
         this.mDimContext9 = newDimContext9;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(CcDeploymentEVO newDetails) {
      this.setCcDeploymentId(newDetails.getCcDeploymentId());
      this.setModelId(newDetails.getModelId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setXmlformId(newDetails.getXmlformId());
      this.setDimContext0(newDetails.getDimContext0());
      this.setDimContext1(newDetails.getDimContext1());
      this.setDimContext2(newDetails.getDimContext2());
      this.setDimContext3(newDetails.getDimContext3());
      this.setDimContext4(newDetails.getDimContext4());
      this.setDimContext5(newDetails.getDimContext5());
      this.setDimContext6(newDetails.getDimContext6());
      this.setDimContext7(newDetails.getDimContext7());
      this.setDimContext8(newDetails.getDimContext8());
      this.setDimContext9(newDetails.getDimContext9());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public CcDeploymentEVO deepClone() {
      CcDeploymentEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (CcDeploymentEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mCcDeploymentId > 0) {
         newKey = true;
         if(parent == null) {
            this.setCcDeploymentId(-this.mCcDeploymentId);
         } else {
            parent.changeKey(this, -this.mCcDeploymentId);
         }
      } else if(this.mCcDeploymentId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      CcDeploymentLineEVO item;
      if(this.mCCDeploymentLines != null) {
         for(Iterator iter = (new ArrayList(this.mCCDeploymentLines.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (CcDeploymentLineEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mCcDeploymentId < 1) {
         returnCount = startCount + 1;
      }

      CcDeploymentLineEVO item;
      if(this.mCCDeploymentLines != null) {
         for(Iterator iter = this.mCCDeploymentLines.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (CcDeploymentLineEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mCcDeploymentId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      CcDeploymentLineEVO item;
      if(this.mCCDeploymentLines != null) {
         for(Iterator iter = (new ArrayList(this.mCCDeploymentLines.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (CcDeploymentLineEVO)iter.next();
            item.setCcDeploymentId(this.mCcDeploymentId);
         }
      }

      return nextKey;
   }

   public Collection<CcDeploymentLineEVO> getCCDeploymentLines() {
      return this.mCCDeploymentLines != null?this.mCCDeploymentLines.values():null;
   }

   public Map<CcDeploymentLinePK, CcDeploymentLineEVO> getCCDeploymentLinesMap() {
      return this.mCCDeploymentLines;
   }

   public void loadCCDeploymentLinesItem(CcDeploymentLineEVO newItem) {
      if(this.mCCDeploymentLines == null) {
         this.mCCDeploymentLines = new HashMap();
      }

      this.mCCDeploymentLines.put(newItem.getPK(), newItem);
   }

   public void addCCDeploymentLinesItem(CcDeploymentLineEVO newItem) {
      if(this.mCCDeploymentLines == null) {
         this.mCCDeploymentLines = new HashMap();
      }

      CcDeploymentLinePK newPK = newItem.getPK();
      if(this.getCCDeploymentLinesItem(newPK) != null) {
         throw new RuntimeException("addCCDeploymentLinesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mCCDeploymentLines.put(newPK, newItem);
      }
   }

   public void changeCCDeploymentLinesItem(CcDeploymentLineEVO changedItem) {
      if(this.mCCDeploymentLines == null) {
         throw new RuntimeException("changeCCDeploymentLinesItem: no items in collection");
      } else {
         CcDeploymentLinePK changedPK = changedItem.getPK();
         CcDeploymentLineEVO listItem = this.getCCDeploymentLinesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeCCDeploymentLinesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteCCDeploymentLinesItem(CcDeploymentLinePK removePK) {
      CcDeploymentLineEVO listItem = this.getCCDeploymentLinesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeCCDeploymentLinesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public CcDeploymentLineEVO getCCDeploymentLinesItem(CcDeploymentLinePK pk) {
      return (CcDeploymentLineEVO)this.mCCDeploymentLines.get(pk);
   }

   public CcDeploymentLineEVO getCCDeploymentLinesItem() {
      if(this.mCCDeploymentLines.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mCCDeploymentLines.size());
      } else {
         Iterator iter = this.mCCDeploymentLines.values().iterator();
         return (CcDeploymentLineEVO)iter.next();
      }
   }

   public void setInsertPending() {
      this.mInsertPending = true;
   }

   public boolean insertPending() {
      return this.mInsertPending;
   }

   public void setDeletePending() {
      this.mDeletePending = true;
   }

   public boolean deletePending() {
      return this.mDeletePending;
   }

   protected void reset() {
      this.mModified = false;
      this.mInsertPending = false;
   }

   public CcDeploymentRef getEntityRef(ModelEVO evoModel) {
      return new CcDeploymentRefImpl(new CcDeploymentCK(evoModel.getPK(), this.getPK()), this.mVisId);
   }

   public CcDeploymentCK getCK(ModelEVO evoModel) {
      return new CcDeploymentCK(evoModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mCCDeploymentLinesAllItemsLoaded = true;
      if(this.mCCDeploymentLines == null) {
         this.mCCDeploymentLines = new HashMap();
      } else {
         Iterator i$ = this.mCCDeploymentLines.values().iterator();

         while(i$.hasNext()) {
            CcDeploymentLineEVO child = (CcDeploymentLineEVO)i$.next();
            child.postCreateInit();
         }
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("CcDeploymentId=");
      sb.append(String.valueOf(this.mCcDeploymentId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("XmlformId=");
      sb.append(String.valueOf(this.mXmlformId));
      sb.append(' ');
      sb.append("DimContext0=");
      sb.append(String.valueOf(this.mDimContext0));
      sb.append(' ');
      sb.append("DimContext1=");
      sb.append(String.valueOf(this.mDimContext1));
      sb.append(' ');
      sb.append("DimContext2=");
      sb.append(String.valueOf(this.mDimContext2));
      sb.append(' ');
      sb.append("DimContext3=");
      sb.append(String.valueOf(this.mDimContext3));
      sb.append(' ');
      sb.append("DimContext4=");
      sb.append(String.valueOf(this.mDimContext4));
      sb.append(' ');
      sb.append("DimContext5=");
      sb.append(String.valueOf(this.mDimContext5));
      sb.append(' ');
      sb.append("DimContext6=");
      sb.append(String.valueOf(this.mDimContext6));
      sb.append(' ');
      sb.append("DimContext7=");
      sb.append(String.valueOf(this.mDimContext7));
      sb.append(' ');
      sb.append("DimContext8=");
      sb.append(String.valueOf(this.mDimContext8));
      sb.append(' ');
      sb.append("DimContext9=");
      sb.append(String.valueOf(this.mDimContext9));
      sb.append(' ');
      sb.append("VersionNum=");
      sb.append(String.valueOf(this.mVersionNum));
      sb.append(' ');
      sb.append("UpdatedByUserId=");
      sb.append(String.valueOf(this.mUpdatedByUserId));
      sb.append(' ');
      sb.append("UpdatedTime=");
      sb.append(String.valueOf(this.mUpdatedTime));
      sb.append(' ');
      sb.append("CreatedTime=");
      sb.append(String.valueOf(this.mCreatedTime));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      if(this.mInsertPending) {
         sb.append("insertPending ");
      }

      if(this.mDeletePending) {
         sb.append("deletePending ");
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

      sb.append("CcDeployment: ");
      sb.append(this.toString());
      if(this.mCCDeploymentLinesAllItemsLoaded || this.mCCDeploymentLines != null) {
         sb.delete(indent, sb.length());
         sb.append(" - CCDeploymentLines: allItemsLoaded=");
         sb.append(String.valueOf(this.mCCDeploymentLinesAllItemsLoaded));
         sb.append(" items=");
         if(this.mCCDeploymentLines == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mCCDeploymentLines.size()));
         }
      }

      if(this.mCCDeploymentLines != null) {
         Iterator var5 = this.mCCDeploymentLines.values().iterator();

         while(var5.hasNext()) {
            CcDeploymentLineEVO listItem = (CcDeploymentLineEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(CcDeploymentLineEVO child, int newCcDeploymentLineId) {
      if(this.getCCDeploymentLinesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mCCDeploymentLines.remove(child.getPK());
         child.setCcDeploymentLineId(newCcDeploymentLineId);
         this.mCCDeploymentLines.put(child.getPK(), child);
      }
   }

   public void setCCDeploymentLinesAllItemsLoaded(boolean allItemsLoaded) {
      this.mCCDeploymentLinesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isCCDeploymentLinesAllItemsLoaded() {
      return this.mCCDeploymentLinesAllItemsLoaded;
   }
}
