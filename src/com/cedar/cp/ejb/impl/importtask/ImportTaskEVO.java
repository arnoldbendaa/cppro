package com.cedar.cp.ejb.impl.importtask;

import com.cedar.cp.api.importtask.ImportTaskRef;
import com.cedar.cp.dto.importtask.ImportTaskLinkPK;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.dto.importtask.ImportTaskRefImpl;
import com.cedar.cp.ejb.impl.importtask.ImportTaskLinkEVO;
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

public class ImportTaskEVO implements Serializable {

   private transient ImportTaskPK mPK;
   private int mImportTaskId;
   private String mVisId;
   private String mDescription;
   private int mExternalSystemId;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<ImportTaskLinkPK, ImportTaskLinkEVO> mImportTasksEvents;
   protected boolean mImportTasksEventsAllItemsLoaded;
   private boolean mModified;

   public ImportTaskEVO() {}

   public ImportTaskEVO(int newImportTaskId, String newVisId, String newDescription, int newExternalSystemId) {
      this.mImportTaskId = newImportTaskId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mExternalSystemId = newExternalSystemId;
   }

   public void setImportTasksEvents(Collection<ImportTaskLinkEVO> items) {
      if(items != null) {
         if(this.mImportTasksEvents == null) {
            this.mImportTasksEvents = new HashMap();
         } else {
            this.mImportTasksEvents.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ImportTaskLinkEVO child = (ImportTaskLinkEVO)i$.next();
            this.mImportTasksEvents.put(child.getPK(), child);
         }
      } else {
         this.mImportTasksEvents = null;
      }
   }

   public ImportTaskPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ImportTaskPK(this.mImportTaskId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getImportTaskId() {
      return this.mImportTaskId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }
   
	public int getExternalSystemId() {
		return this.mExternalSystemId;
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

   public void setImportTaskId(int newImportTaskId) {
      if(this.mImportTaskId != newImportTaskId) {
         this.mModified = true;
         this.mImportTaskId = newImportTaskId;
         this.mPK = null;
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
   
	public void setExternalSystem(int newExternalSystemId) {
		if (this.mExternalSystemId != newExternalSystemId) {
			this.mModified = true;
			this.mExternalSystemId = newExternalSystemId;
		}
	}
   
   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ImportTaskEVO newDetails) {
      this.setImportTaskId(newDetails.getImportTaskId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setExternalSystem(newDetails.getExternalSystemId());
   }

   public ImportTaskEVO deepClone() {
      ImportTaskEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ImportTaskEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mImportTaskId > 0) {
         newKey = true;
         this.mImportTaskId = 0;
      } else if(this.mImportTaskId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      ImportTaskLinkEVO item;
      if(this.mImportTasksEvents != null) {
         for(Iterator iter = (new ArrayList(this.mImportTasksEvents.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ImportTaskLinkEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mImportTaskId < 1) {
         returnCount = startCount + 1;
      }

      ImportTaskLinkEVO item;
      if(this.mImportTasksEvents != null) {
         for(Iterator iter = this.mImportTasksEvents.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ImportTaskLinkEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mImportTaskId < 1) {
         this.mImportTaskId = startKey;
         nextKey = startKey + 1;
      }

      ImportTaskLinkEVO item;
      if(this.mImportTasksEvents != null) {
         for(Iterator iter = (new ArrayList(this.mImportTasksEvents.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ImportTaskLinkEVO)iter.next();
            this.changeKey(item, this.mImportTaskId, item.getImportTaskLinkId());
         }
      }

      return nextKey;
   }

   public Collection<ImportTaskLinkEVO> getImportTasksEvents() {
      return this.mImportTasksEvents != null?this.mImportTasksEvents.values():null;
   }

   public Map<ImportTaskLinkPK, ImportTaskLinkEVO> getImportTasksEventsMap() {
      return this.mImportTasksEvents;
   }

   public void loadImportTasksEventsItem(ImportTaskLinkEVO newItem) {
      if(this.mImportTasksEvents == null) {
         this.mImportTasksEvents = new HashMap();
      }

      this.mImportTasksEvents.put(newItem.getPK(), newItem);
   }

   public void addImportTasksEventsItem(ImportTaskLinkEVO newItem) {
      if(this.mImportTasksEvents == null) {
         this.mImportTasksEvents = new HashMap();
      }

      ImportTaskLinkPK newPK = newItem.getPK();
      if(this.getImportTasksEventsItem(newPK) != null) {
         throw new RuntimeException("addImportTasksEventsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mImportTasksEvents.put(newPK, newItem);
      }
   }

   public void changeImportTasksEventsItem(ImportTaskLinkEVO changedItem) {
      if(this.mImportTasksEvents == null) {
         throw new RuntimeException("changeImportTasksEventsItem: no items in collection");
      } else {
         ImportTaskLinkPK changedPK = changedItem.getPK();
         ImportTaskLinkEVO listItem = this.getImportTasksEventsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeImportTasksEventsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteImportTasksEventsItem(ImportTaskLinkPK removePK) {
      ImportTaskLinkEVO listItem = this.getImportTasksEventsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeImportTasksEventsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ImportTaskLinkEVO getImportTasksEventsItem(ImportTaskLinkPK pk) {
      return (ImportTaskLinkEVO)this.mImportTasksEvents.get(pk);
   }

   public ImportTaskLinkEVO getImportTasksEventsItem() {
      if(this.mImportTasksEvents.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mImportTasksEvents.size());
      } else {
         Iterator iter = this.mImportTasksEvents.values().iterator();
         return (ImportTaskLinkEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public ImportTaskRef getEntityRef() {
      return new ImportTaskRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mImportTasksEventsAllItemsLoaded = true;
      if(this.mImportTasksEvents == null) {
         this.mImportTasksEvents = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ImportTaskId=");
      sb.append(String.valueOf(this.mImportTaskId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("ExternalSystemId=");
      sb.append(String.valueOf(this.mExternalSystemId));
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

      sb.append("ImportTask: ");
      sb.append(this.toString());
      if(this.mImportTasksEventsAllItemsLoaded || this.mImportTasksEvents != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ImportTasksEvents: allItemsLoaded=");
         sb.append(String.valueOf(this.mImportTasksEventsAllItemsLoaded));
         sb.append(" items=");
         if(this.mImportTasksEvents == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mImportTasksEvents.size()));
         }
      }

      if(this.mImportTasksEvents != null) {
         Iterator var5 = this.mImportTasksEvents.values().iterator();

         while(var5.hasNext()) {
            ImportTaskLinkEVO listItem = (ImportTaskLinkEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ImportTaskLinkEVO child, int newImportTaskId, int newImportTaskLinkId) {
      if(this.getImportTasksEventsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mImportTasksEvents.remove(child.getPK());
         child.setImportTaskId(newImportTaskId);
         child.setImportTaskLinkId(newImportTaskLinkId);
         this.mImportTasksEvents.put(child.getPK(), child);
      }
   }

   public void setImportTasksEventsAllItemsLoaded(boolean allItemsLoaded) {
      this.mImportTasksEventsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isImportTasksEventsAllItemsLoaded() {
      return this.mImportTasksEventsAllItemsLoaded;
   }
}
