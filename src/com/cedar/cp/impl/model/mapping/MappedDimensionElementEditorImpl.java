// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.mapping.MappedDimensionElement;
import com.cedar.cp.api.model.mapping.MappedDimensionElementEditor;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.MappedDimensionElementImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.mapping.MappedDimensionEditorImpl;
import com.cedar.cp.util.StringUtils;

public class MappedDimensionElementEditorImpl extends SubBusinessEditorImpl implements MappedDimensionElementEditor {

   private MappedDimensionElementImpl mMappedDimensionElement;


   public MappedDimensionElementEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner, MappedDimensionElementImpl mappedDimensionElement, boolean newMapping) {
      super(sess, owner);

      try {
         this.mMappedDimensionElement = (MappedDimensionElementImpl)mappedDimensionElement.clone();
      } catch (CloneNotSupportedException var6) {
         throw new CPException("Failed to clone MappedDimensionElementImpl", var6);
      }

      if(newMapping) {
         this.setContentModified();
      }

   }

   protected void undoModifications() throws CPException {}

   protected void saveModifications() throws ValidationException {
      ((MappedDimensionEditorImpl)this.getOwner()).updateMappedDimensionElement(this.mMappedDimensionElement);
   }

   public MappedDimensionElement getMappedDimensionElement() {
      return this.mMappedDimensionElement;
   }

   public void setMappingType(int mappingType) throws ValidationException {
      if(mappingType != 0 && mappingType != 1 && mappingType != 2 && mappingType != 3) {
         throw new ValidationException("Not a valid mapping type:" + mappingType);
      } else {
         if(this.mMappedDimensionElement.getMappingType() != mappingType) {
            this.mMappedDimensionElement.setMappingType(mappingType);
            this.setContentModified();
         }

      }
   }

   public void setVisId1(String visId1) throws ValidationException {
      this.validateFieldPresent(visId1);
      if(StringUtils.differ(visId1, this.mMappedDimensionElement.getVisId1())) {
         this.mMappedDimensionElement.setVisId1(visId1);
         this.setContentModified();
      }

   }

   public void setVisId2(String visId2) throws ValidationException {
      if(StringUtils.differ(visId2, this.mMappedDimensionElement.getVisId2())) {
         this.mMappedDimensionElement.setVisId2(visId2);
         this.setContentModified();
      }

   }

   public void setFinanceMappingKey(MappingKey key) throws ValidationException {
      MappingKeyImpl mk = (MappingKeyImpl)key;
      if(mk.length() != 3) {
         throw new ValidationException("Invalid mapping key - expected three key segments:" + key);
      } else {
         this.setVisId1((String)mk.get(0));
         this.setVisId2((String)mk.get(1));
         this.setVisId3((String)mk.get(2));
      }
   }

   private void setVisId3(String visId3) throws ValidationException {
      if(StringUtils.differ(visId3, this.mMappedDimensionElement.getVisId3())) {
         this.mMappedDimensionElement.setVisId3(visId3);
         this.setContentModified();
      }

   }
}
