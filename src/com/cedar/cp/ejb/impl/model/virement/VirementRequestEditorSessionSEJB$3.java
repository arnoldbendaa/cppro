// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.virement.VirementLineSpreadImpl;
import com.cedar.cp.ejb.impl.dimension.StructureElementEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEditorSessionSEJB;
import java.util.Comparator;
import java.util.Map;

class VirementRequestEditorSessionSEJB$3 implements Comparator<VirementLineSpreadImpl> {

   // $FF: synthetic field
   final Map val$seMap;
   // $FF: synthetic field
   final VirementRequestEditorSessionSEJB this$0;


   VirementRequestEditorSessionSEJB$3(VirementRequestEditorSessionSEJB var1, Map var2) {
      this.this$0 = var1;
      this.val$seMap = var2;
   }

   public int compare(VirementLineSpreadImpl o1, VirementLineSpreadImpl o2) {
      int se1Id = ((StructureElementPK)o1.getStructureElementRef().getPrimaryKey()).getStructureElementId();
      StructureElementEVO se1 = (StructureElementEVO)this.val$seMap.get(Integer.valueOf(se1Id));
      int se2Id = ((StructureElementPK)o2.getStructureElementRef().getPrimaryKey()).getStructureElementId();
      StructureElementEVO se2 = (StructureElementEVO)this.val$seMap.get(Integer.valueOf(se2Id));
      return se1.getPosition() - se2.getPosition();
   }
}
