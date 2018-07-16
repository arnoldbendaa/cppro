// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.cubeformula;

import com.cedar.cp.api.cubeformula.CubeFormulaRef;
import com.cedar.cp.dto.cubeformula.CubeFormulaCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaPK;
import com.cedar.cp.dto.cubeformula.CubeFormulaRefImpl;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLinePK;
import com.cedar.cp.ejb.impl.cubeformula.FormulaDeploymentLineEVO;
import com.cedar.cp.ejb.impl.model.FinanceCubeEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
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

public class CubeFormulaEVO implements Serializable {

   private transient CubeFormulaPK mPK;
   private int mCubeFormulaId;
   private int mFinanceCubeId;
   private String mVisId;
   private String mDescription;
   private String mFormulaText;
   private boolean mDeploymentInd;
   private int mFormulaType;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<FormulaDeploymentLinePK, FormulaDeploymentLineEVO> mDeployments;
   protected boolean mDeploymentsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int AUTOMATIC = 0;
   public static final int MANUAL = 1;


   public CubeFormulaEVO() {}

   public CubeFormulaEVO(int newCubeFormulaId, int newFinanceCubeId, String newVisId, String newDescription, String newFormulaText, boolean newDeploymentInd, int newFormulaType, Collection newDeployments) {
      this.mCubeFormulaId = newCubeFormulaId;
      this.mFinanceCubeId = newFinanceCubeId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mFormulaText = newFormulaText;
      this.mDeploymentInd = newDeploymentInd;
      this.mFormulaType = newFormulaType;
      this.setDeployments(newDeployments);
   }

   public void setDeployments(Collection<FormulaDeploymentLineEVO> items) {
      if(items != null) {
         if(this.mDeployments == null) {
            this.mDeployments = new HashMap();
         } else {
            this.mDeployments.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            FormulaDeploymentLineEVO child = (FormulaDeploymentLineEVO)i$.next();
            this.mDeployments.put(child.getPK(), child);
         }
      } else {
         this.mDeployments = null;
      }

   }

   public CubeFormulaPK getPK() {
      if(this.mPK == null) {
         this.mPK = new CubeFormulaPK(this.mCubeFormulaId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getCubeFormulaId() {
      return this.mCubeFormulaId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getFormulaText() {
      return this.mFormulaText;
   }

   public boolean getDeploymentInd() {
      return this.mDeploymentInd;
   }

   public int getFormulaType() {
      return this.mFormulaType;
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

   public void setCubeFormulaId(int newCubeFormulaId) {
      if(this.mCubeFormulaId != newCubeFormulaId) {
         this.mModified = true;
         this.mCubeFormulaId = newCubeFormulaId;
         this.mPK = null;
      }
   }

   public void setFinanceCubeId(int newFinanceCubeId) {
      if(this.mFinanceCubeId != newFinanceCubeId) {
         this.mModified = true;
         this.mFinanceCubeId = newFinanceCubeId;
      }
   }

   public void setDeploymentInd(boolean newDeploymentInd) {
      if(this.mDeploymentInd != newDeploymentInd) {
         this.mModified = true;
         this.mDeploymentInd = newDeploymentInd;
      }
   }

   public void setFormulaType(int newFormulaType) {
      if(this.mFormulaType != newFormulaType) {
         this.mModified = true;
         this.mFormulaType = newFormulaType;
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

   public void setFormulaText(String newFormulaText) {
      if(this.mFormulaText != null && newFormulaText == null || this.mFormulaText == null && newFormulaText != null || this.mFormulaText != null && newFormulaText != null && !this.mFormulaText.equals(newFormulaText)) {
         this.mFormulaText = newFormulaText;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(CubeFormulaEVO newDetails) {
      this.setCubeFormulaId(newDetails.getCubeFormulaId());
      this.setFinanceCubeId(newDetails.getFinanceCubeId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setFormulaText(newDetails.getFormulaText());
      this.setDeploymentInd(newDetails.getDeploymentInd());
      this.setFormulaType(newDetails.getFormulaType());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public CubeFormulaEVO deepClone() {
      CubeFormulaEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (CubeFormulaEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(FinanceCubeEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mCubeFormulaId > 0) {
         newKey = true;
         if(parent == null) {
            this.setCubeFormulaId(-this.mCubeFormulaId);
         } else {
            parent.changeKey(this, -this.mCubeFormulaId);
         }
      } else if(this.mCubeFormulaId < 1) {
         newKey = true;
      }

      FormulaDeploymentLineEVO item;
      if(this.mDeployments != null) {
         for(Iterator iter = (new ArrayList(this.mDeployments.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (FormulaDeploymentLineEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mCubeFormulaId < 1) {
         returnCount = startCount + 1;
      }

      FormulaDeploymentLineEVO item;
      if(this.mDeployments != null) {
         for(Iterator iter = this.mDeployments.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (FormulaDeploymentLineEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(FinanceCubeEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mCubeFormulaId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      FormulaDeploymentLineEVO item;
      if(this.mDeployments != null) {
         for(Iterator iter = (new ArrayList(this.mDeployments.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (FormulaDeploymentLineEVO)iter.next();
            item.setCubeFormulaId(this.mCubeFormulaId);
         }
      }

      return nextKey;
   }

   public Collection<FormulaDeploymentLineEVO> getDeployments() {
      return this.mDeployments != null?this.mDeployments.values():null;
   }

   public Map<FormulaDeploymentLinePK, FormulaDeploymentLineEVO> getDeploymentsMap() {
      return this.mDeployments;
   }

   public void loadDeploymentsItem(FormulaDeploymentLineEVO newItem) {
      if(this.mDeployments == null) {
         this.mDeployments = new HashMap();
      }

      this.mDeployments.put(newItem.getPK(), newItem);
   }

   public void addDeploymentsItem(FormulaDeploymentLineEVO newItem) {
      if(this.mDeployments == null) {
         this.mDeployments = new HashMap();
      }

      FormulaDeploymentLinePK newPK = newItem.getPK();
      if(this.getDeploymentsItem(newPK) != null) {
         throw new RuntimeException("addDeploymentsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mDeployments.put(newPK, newItem);
      }
   }

   public void changeDeploymentsItem(FormulaDeploymentLineEVO changedItem) {
      if(this.mDeployments == null) {
         throw new RuntimeException("changeDeploymentsItem: no items in collection");
      } else {
         FormulaDeploymentLinePK changedPK = changedItem.getPK();
         FormulaDeploymentLineEVO listItem = this.getDeploymentsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeDeploymentsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteDeploymentsItem(FormulaDeploymentLinePK removePK) {
      FormulaDeploymentLineEVO listItem = this.getDeploymentsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeDeploymentsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public FormulaDeploymentLineEVO getDeploymentsItem(FormulaDeploymentLinePK pk) {
      return (FormulaDeploymentLineEVO)this.mDeployments.get(pk);
   }

   public FormulaDeploymentLineEVO getDeploymentsItem() {
      if(this.mDeployments.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mDeployments.size());
      } else {
         Iterator iter = this.mDeployments.values().iterator();
         return (FormulaDeploymentLineEVO)iter.next();
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

   public CubeFormulaRef getEntityRef(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube) {
      return new CubeFormulaRefImpl(new CubeFormulaCK(evoModel.getPK(), evoFinanceCube.getPK(), this.getPK()), this.mVisId);
   }

   public CubeFormulaCK getCK(ModelEVO evoModel, FinanceCubeEVO evoFinanceCube) {
      return new CubeFormulaCK(evoModel.getPK(), evoFinanceCube.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mDeploymentsAllItemsLoaded = true;
      if(this.mDeployments == null) {
         this.mDeployments = new HashMap();
      } else {
         Iterator i$ = this.mDeployments.values().iterator();

         while(i$.hasNext()) {
            FormulaDeploymentLineEVO child = (FormulaDeploymentLineEVO)i$.next();
            child.postCreateInit();
         }
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("CubeFormulaId=");
      sb.append(String.valueOf(this.mCubeFormulaId));
      sb.append(' ');
      sb.append("FinanceCubeId=");
      sb.append(String.valueOf(this.mFinanceCubeId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("FormulaText=");
      sb.append(String.valueOf(this.mFormulaText));
      sb.append(' ');
      sb.append("DeploymentInd=");
      sb.append(String.valueOf(this.mDeploymentInd));
      sb.append(' ');
      sb.append("FormulaType=");
      sb.append(String.valueOf(this.mFormulaType));
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

      sb.append("CubeFormula: ");
      sb.append(this.toString());
      if(this.mDeploymentsAllItemsLoaded || this.mDeployments != null) {
         sb.delete(indent, sb.length());
         sb.append(" - Deployments: allItemsLoaded=");
         sb.append(String.valueOf(this.mDeploymentsAllItemsLoaded));
         sb.append(" items=");
         if(this.mDeployments == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mDeployments.size()));
         }
      }

      if(this.mDeployments != null) {
         Iterator var5 = this.mDeployments.values().iterator();

         while(var5.hasNext()) {
            FormulaDeploymentLineEVO listItem = (FormulaDeploymentLineEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(FormulaDeploymentLineEVO child, int newFormulaDeploymentLineId) {
      if(this.getDeploymentsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mDeployments.remove(child.getPK());
         child.setFormulaDeploymentLineId(newFormulaDeploymentLineId);
         this.mDeployments.put(child.getPK(), child);
      }
   }

   public void setDeploymentsAllItemsLoaded(boolean allItemsLoaded) {
      this.mDeploymentsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isDeploymentsAllItemsLoaded() {
      return this.mDeploymentsAllItemsLoaded;
   }
}
