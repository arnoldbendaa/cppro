package com.cedar.cp.dto.importtask;

import com.cedar.cp.api.importtask.ImportTaskLinkRef;
import com.cedar.cp.dto.importtask.ImportTaskLinkCK;
import com.cedar.cp.dto.importtask.ImportTaskLinkPK;
import com.cedar.cp.dto.base.EntityRefImpl;
import java.io.Serializable;

public class ImportTaskLinkRefImpl extends EntityRefImpl implements ImportTaskLinkRef, Serializable {

   public ImportTaskLinkRefImpl(ImportTaskLinkCK key, String narrative) {
      super(key, narrative);
   }

   public ImportTaskLinkRefImpl(ImportTaskLinkPK key, String narrative) {
      super(key, narrative);
   }

   public ImportTaskLinkPK getImportTaskLinkPK() {
      return this.mKey instanceof ImportTaskLinkCK?((ImportTaskLinkCK)this.mKey).getImportTaskLinkPK():(ImportTaskLinkPK)this.mKey;
   }
}
