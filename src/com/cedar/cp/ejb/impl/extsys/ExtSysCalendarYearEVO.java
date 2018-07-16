// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:31
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys;

import com.cedar.cp.api.extsys.ExtSysCalendarYearRef;
import com.cedar.cp.dto.extsys.ExtSysCalElementPK;
import com.cedar.cp.dto.extsys.ExtSysCalendarYearCK;
import com.cedar.cp.dto.extsys.ExtSysCalendarYearPK;
import com.cedar.cp.dto.extsys.ExtSysCalendarYearRefImpl;
import com.cedar.cp.ejb.impl.extsys.ExtSysCalElementEVO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyEVO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExtSysCalendarYearEVO implements Serializable {

   private transient ExtSysCalendarYearPK mPK;
   private int mExternalSystemId;
   private String mCompanyVisId;
   private String mCalendarYearVisId;
   private int mYear;
   private Map<ExtSysCalElementPK, ExtSysCalElementEVO> mExtSysCalendarElements;
   protected boolean mExtSysCalendarElementsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ExtSysCalendarYearEVO() {}

   public ExtSysCalendarYearEVO(int newExternalSystemId, String newCompanyVisId, String newCalendarYearVisId, int newYear, Collection newExtSysCalendarElements) {
      this.mExternalSystemId = newExternalSystemId;
      this.mCompanyVisId = newCompanyVisId;
      this.mCalendarYearVisId = newCalendarYearVisId;
      this.mYear = newYear;
      this.setExtSysCalendarElements(newExtSysCalendarElements);
   }

   public void setExtSysCalendarElements(Collection<ExtSysCalElementEVO> items) {
      if(items != null) {
         if(this.mExtSysCalendarElements == null) {
            this.mExtSysCalendarElements = new HashMap();
         } else {
            this.mExtSysCalendarElements.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ExtSysCalElementEVO child = (ExtSysCalElementEVO)i$.next();
            this.mExtSysCalendarElements.put(child.getPK(), child);
         }
      } else {
         this.mExtSysCalendarElements = null;
      }

   }

   public ExtSysCalendarYearPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ExtSysCalendarYearPK(this.mExternalSystemId, this.mCompanyVisId, this.mCalendarYearVisId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getExternalSystemId() {
      return this.mExternalSystemId;
   }

   public String getCompanyVisId() {
      return this.mCompanyVisId;
   }

   public String getCalendarYearVisId() {
      return this.mCalendarYearVisId;
   }

   public int getYear() {
      return this.mYear;
   }

   public void setExternalSystemId(int newExternalSystemId) {
      if(this.mExternalSystemId != newExternalSystemId) {
         this.mModified = true;
         this.mExternalSystemId = newExternalSystemId;
         this.mPK = null;
      }
   }

   public void setYear(int newYear) {
      if(this.mYear != newYear) {
         this.mModified = true;
         this.mYear = newYear;
      }
   }

   public void setCompanyVisId(String newCompanyVisId) {
      if(this.mCompanyVisId != null && newCompanyVisId == null || this.mCompanyVisId == null && newCompanyVisId != null || this.mCompanyVisId != null && newCompanyVisId != null && !this.mCompanyVisId.equals(newCompanyVisId)) {
         this.mCompanyVisId = newCompanyVisId;
         this.mModified = true;
      }

   }

   public void setCalendarYearVisId(String newCalendarYearVisId) {
      if(this.mCalendarYearVisId != null && newCalendarYearVisId == null || this.mCalendarYearVisId == null && newCalendarYearVisId != null || this.mCalendarYearVisId != null && newCalendarYearVisId != null && !this.mCalendarYearVisId.equals(newCalendarYearVisId)) {
         this.mCalendarYearVisId = newCalendarYearVisId;
         this.mModified = true;
      }

   }

   public void setDetails(ExtSysCalendarYearEVO newDetails) {
      this.setExternalSystemId(newDetails.getExternalSystemId());
      this.setCompanyVisId(newDetails.getCompanyVisId());
      this.setCalendarYearVisId(newDetails.getCalendarYearVisId());
      this.setYear(newDetails.getYear());
   }

   public ExtSysCalendarYearEVO deepClone() {
      ExtSysCalendarYearEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ExtSysCalendarYearEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ExtSysCompanyEVO parent) {
      boolean newKey = this.insertPending();
      ExtSysCalElementEVO item;
      if(this.mExtSysCalendarElements != null) {
         for(Iterator iter = (new ArrayList(this.mExtSysCalendarElements.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ExtSysCalElementEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      ExtSysCalElementEVO item;
      if(this.mExtSysCalendarElements != null) {
         for(Iterator iter = this.mExtSysCalendarElements.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ExtSysCalElementEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ExtSysCompanyEVO parent, int startKey) {
      int nextKey = startKey;
      ExtSysCalElementEVO item;
      if(this.mExtSysCalendarElements != null) {
         for(Iterator iter = (new ArrayList(this.mExtSysCalendarElements.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ExtSysCalElementEVO)iter.next();
            this.changeKey(item, this.mExternalSystemId, this.mCompanyVisId, this.mCalendarYearVisId, item.getCalElementVisId());
         }
      }

      return nextKey;
   }

   public Collection<ExtSysCalElementEVO> getExtSysCalendarElements() {
      return this.mExtSysCalendarElements != null?this.mExtSysCalendarElements.values():null;
   }

   public Map<ExtSysCalElementPK, ExtSysCalElementEVO> getExtSysCalendarElementsMap() {
      return this.mExtSysCalendarElements;
   }

   public void loadExtSysCalendarElementsItem(ExtSysCalElementEVO newItem) {
      if(this.mExtSysCalendarElements == null) {
         this.mExtSysCalendarElements = new HashMap();
      }

      this.mExtSysCalendarElements.put(newItem.getPK(), newItem);
   }

   public void addExtSysCalendarElementsItem(ExtSysCalElementEVO newItem) {
      if(this.mExtSysCalendarElements == null) {
         this.mExtSysCalendarElements = new HashMap();
      }

      ExtSysCalElementPK newPK = newItem.getPK();
      if(this.getExtSysCalendarElementsItem(newPK) != null) {
         throw new RuntimeException("addExtSysCalendarElementsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mExtSysCalendarElements.put(newPK, newItem);
      }
   }

   public void changeExtSysCalendarElementsItem(ExtSysCalElementEVO changedItem) {
      if(this.mExtSysCalendarElements == null) {
         throw new RuntimeException("changeExtSysCalendarElementsItem: no items in collection");
      } else {
         ExtSysCalElementPK changedPK = changedItem.getPK();
         ExtSysCalElementEVO listItem = this.getExtSysCalendarElementsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeExtSysCalendarElementsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteExtSysCalendarElementsItem(ExtSysCalElementPK removePK) {
      ExtSysCalElementEVO listItem = this.getExtSysCalendarElementsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeExtSysCalendarElementsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ExtSysCalElementEVO getExtSysCalendarElementsItem(ExtSysCalElementPK pk) {
      return (ExtSysCalElementEVO)this.mExtSysCalendarElements.get(pk);
   }

   public ExtSysCalElementEVO getExtSysCalendarElementsItem() {
      if(this.mExtSysCalendarElements.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mExtSysCalendarElements.size());
      } else {
         Iterator iter = this.mExtSysCalendarElements.values().iterator();
         return (ExtSysCalElementEVO)iter.next();
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

   public ExtSysCalendarYearRef getEntityRef(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany, String entityText) {
      return new ExtSysCalendarYearRefImpl(new ExtSysCalendarYearCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), this.getPK()), entityText);
   }

   public ExtSysCalendarYearCK getCK(ExternalSystemEVO evoExternalSystem, ExtSysCompanyEVO evoExtSysCompany) {
      return new ExtSysCalendarYearCK(evoExternalSystem.getPK(), evoExtSysCompany.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mExtSysCalendarElementsAllItemsLoaded = true;
      if(this.mExtSysCalendarElements == null) {
         this.mExtSysCalendarElements = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ExternalSystemId=");
      sb.append(String.valueOf(this.mExternalSystemId));
      sb.append(' ');
      sb.append("CompanyVisId=");
      sb.append(String.valueOf(this.mCompanyVisId));
      sb.append(' ');
      sb.append("CalendarYearVisId=");
      sb.append(String.valueOf(this.mCalendarYearVisId));
      sb.append(' ');
      sb.append("Year=");
      sb.append(String.valueOf(this.mYear));
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

      sb.append("ExtSysCalendarYear: ");
      sb.append(this.toString());
      if(this.mExtSysCalendarElementsAllItemsLoaded || this.mExtSysCalendarElements != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ExtSysCalendarElements: allItemsLoaded=");
         sb.append(String.valueOf(this.mExtSysCalendarElementsAllItemsLoaded));
         sb.append(" items=");
         if(this.mExtSysCalendarElements == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mExtSysCalendarElements.size()));
         }
      }

      if(this.mExtSysCalendarElements != null) {
         Iterator var5 = this.mExtSysCalendarElements.values().iterator();

         while(var5.hasNext()) {
            ExtSysCalElementEVO listItem = (ExtSysCalElementEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ExtSysCalElementEVO child, int newExternalSystemId, String newCompanyVisId, String newCalendarYearVisId, String newCalElementVisId) {
      if(this.getExtSysCalendarElementsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mExtSysCalendarElements.remove(child.getPK());
         child.setExternalSystemId(newExternalSystemId);
         child.setCompanyVisId(newCompanyVisId);
         child.setCalendarYearVisId(newCalendarYearVisId);
         child.setCalElementVisId(newCalElementVisId);
         this.mExtSysCalendarElements.put(child.getPK(), child);
      }
   }

   public void setExtSysCalendarElementsAllItemsLoaded(boolean allItemsLoaded) {
      this.mExtSysCalendarElementsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isExtSysCalendarElementsAllItemsLoaded() {
      return this.mExtSysCalendarElementsAllItemsLoaded;
   }
}
