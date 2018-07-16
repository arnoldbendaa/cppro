package com.cedar.cp.dto.importtask;

import com.cedar.cp.api.importtask.ImportTaskLinkRef;
import com.cedar.cp.api.importtask.ImportTaskRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderedChildrenELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"TidyTaskLink", "ImportTask"};
   private transient ImportTaskLinkRef mImportTaskLinkEntityRef;
   private transient ImportTaskRef mImportTaskEntityRef;
   private transient int mType;
   private transient String mCmd;


   public OrderedChildrenELO() {
      super(new String[]{"TidyTaskLink", "ImportTask", "Type", "Cmd"});
   }

   public void add(ImportTaskLinkRef eRefImportTaskLink, ImportTaskRef eRefImportTask, int col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefImportTaskLink);
      l.add(eRefImportTask);
      l.add(new Integer(col1));
      l.add(col2);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mImportTaskLinkEntityRef = (ImportTaskLinkRef)l.get(index);
      this.mImportTaskEntityRef = (ImportTaskRef)l.get(var4++);
      this.mType = ((Integer)l.get(var4++)).intValue();
      this.mCmd = (String)l.get(var4++);
   }

   public ImportTaskLinkRef getImportTaskLinkEntityRef() {
      return this.mImportTaskLinkEntityRef;
   }

   public ImportTaskRef getImportTaskEntityRef() {
      return this.mImportTaskEntityRef;
   }

   public int getType() {
      return this.mType;
   }

   public String getCmd() {
      return this.mCmd;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
