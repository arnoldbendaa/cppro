// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.SecurityRange;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.SecurityRangeImpl;
import com.cedar.cp.dto.dimension.SecurityRangePK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.dimension.SecurityRangeEditorSessionImpl;
import java.util.List;

public class SecurityRangeAdapter implements SecurityRange {

   private SecurityRangeImpl mEditorData;
   private SecurityRangeEditorSessionImpl mEditorSessionImpl;


   public SecurityRangeAdapter(SecurityRangeEditorSessionImpl e, SecurityRangeImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected SecurityRangeEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected SecurityRangeImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(SecurityRangePK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public DimensionRef getDimensionRef() {
      if(this.mEditorData.getDimensionRef() != null) {
         if(this.mEditorData.getDimensionRef().getNarrative() != null && this.mEditorData.getDimensionRef().getNarrative().length() > 0) {
            return this.mEditorData.getDimensionRef();
         } else {
            try {
               DimensionRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getDimensionEntityRef(this.mEditorData.getDimensionRef());
               this.mEditorData.setDimensionRef(e);
               return e;
            } catch (Exception var2) {
               throw new RuntimeException(var2.getMessage());
            }
         }
      } else {
         return null;
      }
   }

   public void setDimensionRef(DimensionRef ref) {
      this.mEditorData.setDimensionRef(ref);
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public ModelRef getModelRef() {
      return this.mEditorData.getModelRef();
   }

   public List getRanges() {
      return this.mEditorData.getRanges();
   }
}
