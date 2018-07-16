package com.cedar.cp.impl.model.globalmapping2;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.globalmapping2.MappedDimension;
import com.cedar.cp.api.model.globalmapping2.MappedDimensionEditor;
import com.cedar.cp.api.model.globalmapping2.MappedDimensionElement;
import com.cedar.cp.api.model.globalmapping2.MappedDimensionElementEditor;
import com.cedar.cp.api.model.globalmapping2.MappedHierarchy;
import com.cedar.cp.api.model.globalmapping2.MappedHierarchyEditor;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedDimensionElementImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedDimensionElementPK;
import com.cedar.cp.dto.model.globalmapping2.MappedDimensionImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedHierarchyImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedHierarchyPK;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.globalmapping2.MappedDimensionElementEditorImpl;
import com.cedar.cp.impl.model.globalmapping2.MappedHierarchyEditorImpl;
import com.cedar.cp.impl.model.globalmapping2.GlobalMappedModel2EditorImpl;
import java.util.Iterator;
import java.util.List;

public class MappedDimensionEditorImpl extends SubBusinessEditorImpl implements MappedDimensionEditor, SubBusinessEditorOwner {

   private MappedDimensionImpl mDimensionMapping;


   public MappedDimensionEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner, MappedDimensionImpl dimensionMapping) {
      super(sess, owner);

      try {
         this.mDimensionMapping = (MappedDimensionImpl)dimensionMapping.clone();
      } catch (CloneNotSupportedException var5) {
         throw new CPException("Failed to clone MappedDimensionImpl:" + var5.getMessage());
      }
   }

   public void setDisabledLeafNodesExcluded(boolean exclude) {
      this.mDimensionMapping.setDisabledLeafNodesExcluded(exclude);
      this.setContentModified();
   }

   public void setFinanceDimensionKey(MappingKey key) throws ValidationException {
      MappingKeyImpl mk = (MappingKeyImpl)key;
      this.mDimensionMapping.setPathVisId((String)mk.get(0));
   }

   public void setDimensionVisId(String visId) throws ValidationException {
      if(this.fieldHasChanged(visId, this.mDimensionMapping.getDimensionVisId())) {
//         if(!this.getMappedModelEditor().getMappedModel().isNew()) {
//            throw new ValidationException("Update of dimension visid and description only supported on a new mapped model");
//         }

         this.mDimensionMapping.setDimensionVisId(visId);
         this.setContentModified();
      }

   }

   public void setDimensionDescription(String descr) throws ValidationException {
      if(this.fieldHasChanged(descr, this.mDimensionMapping.getDimensionDescription())) {
//         if(!this.getMappedModelEditor().getMappedModel().isNew()) {
//            throw new ValidationException("Update of dimension visid and description only supported on a new mapped model");
//         }

         this.mDimensionMapping.setDimensionDescription(descr);
         this.setContentModified();
      }

   }

   public void setDimensionType(int type) throws ValidationException {
      if(this.fieldHasChanged(Integer.valueOf(type), Integer.valueOf(this.mDimensionMapping.getDimensionType()))) {
         if(type != 1 && type != 2 && type != 3) {
            throw new ValidationException("Not a valid dimension type:" + type);
         }

//         if(!this.getMappedModelEditor().getMappedModel().isNew()) {
//            throw new ValidationException("Update of dimension type only supported on a new mapped model");
//         }

         this.mDimensionMapping.setDimensionType(type);
         this.setContentModified();
      }

   }

   public void setPathVisId(String visId) throws ValidationException {
      if(this.fieldHasChanged(visId, this.mDimensionMapping.getPathVisId())) {
//         if(!this.getMappedModelEditor().getMappedModel().isNew()) {
//            throw new ValidationException("Update of dimension mapping only supported on a new mapped model");
//         }

         if(visId == null || visId.trim().length() == 0) {
            throw new ValidationException("A path visual id must be supplied.");
         }

         this.mDimensionMapping.setPathVisId(visId);
         this.setContentModified();
      }

   }

   public void setNullElementVisId(String visId) throws ValidationException {
      if(visId != null) {
         visId = visId.trim();
      }

      if(this.fieldHasChanged(visId, this.mDimensionMapping.getNullDimensionElementVisId())) {
         this.mDimensionMapping.setNullDimensionElementVisId(visId);
         this.setContentModified();
      }

   }

   public void setNullElementDescription(String description) throws ValidationException {
      if(description != null) {
         description = description.trim();
      }

      if(this.fieldHasChanged(description, this.mDimensionMapping.getNullDimensionElementDescription())) {
         this.mDimensionMapping.setNullDimensionElementDescription(description);
         this.setContentModified();
      }

   }

   public void setNullElementCrDrFlag(Integer crdr) throws ValidationException {
      if(this.fieldHasChanged(crdr, this.mDimensionMapping.getNullDimensionElementCreditDebit())) {
         this.mDimensionMapping.setNullDimensionElementCreditDebit(crdr);
         this.setContentModified();
      }

   }

   public void removeMappedHierarchy(Object key) throws ValidationException {
      Iterator i$ = this.mDimensionMapping.getMappedHierarchies().iterator();

      MappedHierarchy mh;
      do {
         if(!i$.hasNext()) {
            throw new ValidationException("Unable to locate mapped hierarchy with key:" + key);
         }

         mh = (MappedHierarchy)i$.next();
      } while(!mh.getKey().equals(key));

      if(mh.isResponsibilityAreaHierarchy() && !this.getMappedModelEditor().getMappedModel().isNew()) {
         throw new ValidationException("Removal of the primary business hierarchy is not supported");
      } else {
         this.mDimensionMapping.getMappedHierarchies().remove(mh);
         this.setContentModified();
      }
   }

   public MappedHierarchyEditor getMappedHierarchyEditor(Object key) throws ValidationException {
      MappedHierarchyImpl mh;
      if(key != null) {
         mh = this.mDimensionMapping.findMappedHierarchy(key);
      } else {
         mh = new MappedHierarchyImpl(new MappedHierarchyPK(this.getNextKey()), (HierarchyRef)null, "", "", "", "", false);
      }

      if(mh == null) {
         throw new ValidationException("Unable to locate mapped hierarchy with key:" + key);
      } else {
         this.setContentModified();
         return new MappedHierarchyEditorImpl(this.getBusinessSession(), this, mh);
      }
   }

   void updateMappedHierarchy(MappedHierarchyImpl mappedHier) {
      MappedHierarchyImpl oldItem = this.mDimensionMapping.findMappedHierarchy(mappedHier.getKey());
      if(oldItem != null) {
         oldItem.setHierarchyRef(mappedHier.getHierarchyRef());
         oldItem.setHierarchyVisId1(mappedHier.getHierarchyVisId1());
         oldItem.setHierarchyVisId2(mappedHier.getHierarchyVisId2());
         oldItem.setNewHierarchyDescription(mappedHier.getNewHierarchyDescription());
         oldItem.setNewHierarchyVisId(mappedHier.getNewHierarchyVisId());
         oldItem.setResponsibilityAreaHierarchy(mappedHier.isResponsibilityAreaHierarchy());
      } else {
         this.mDimensionMapping.getMappedHierarchies().add(mappedHier);
      }

      if(mappedHier.isResponsibilityAreaHierarchy()) {
         Iterator i$ = this.mDimensionMapping.getMappedHierarchies().iterator();

         while(i$.hasNext()) {
            MappedHierarchy mhi = (MappedHierarchy)i$.next();
            if(mhi.isResponsibilityAreaHierarchy() && !mhi.getKey().equals(mappedHier.getKey())) {
               ((MappedHierarchyImpl)mhi).setResponsibilityAreaHierarchy(false);
            }
         }
      }

      this.setContentModified();
   }

   public MappedHierarchy addMappedHierarchy(String visId1, String visId2, String newVisId, String newDescription) throws ValidationException {
      MappedHierarchyImpl mh = new MappedHierarchyImpl(new MappedHierarchyPK(this.getNextKey()), (HierarchyRef)null, visId1, visId2, newVisId, newDescription, false);
      this.mDimensionMapping.getMappedHierarchies().add(mh);
      this.setContentModified();
      return mh;
   }

   protected void undoModifications() throws CPException {}

   protected void saveModifications() throws ValidationException {
      this.getMappedModelEditor().updateMappedDimension(this.mDimensionMapping);
   }

   public GlobalMappedModel2EditorImpl getMappedModelEditor() {
      return (GlobalMappedModel2EditorImpl)this.getOwner();
   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {}

   public MappedDimensionElementEditor getMappedDimensionElementEditor(Object key) throws ValidationException {
      MappedDimensionElementImpl element = (MappedDimensionElementImpl)this.mDimensionMapping.findMappedDimensionElement(key);
      boolean newMapping = false;
      if(element == null) {
         element = new MappedDimensionElementImpl(new MappedDimensionElementPK(this.getNextKey()), 2, "A", "Z", (String)null);
         newMapping = true;
      }

      return new MappedDimensionElementEditorImpl(this.getBusinessSession(), this, element, newMapping);
   }

   public void removeMappedDimensionElement(Object key) throws ValidationException {
      MappedDimensionElement element = this.mDimensionMapping.findMappedDimensionElement(key);
      if(element == null) {
         throw new ValidationException("Unable to find mapped dimension element with key:" + key);
      } else {
         this.mDimensionMapping.getMappedDimensionElements().remove(element);
         this.setContentModified();
      }
   }

   public void updateMappedDimensionElement(MappedDimensionElementImpl mappedDimensionElement) {
      MappedDimensionElementImpl orig = (MappedDimensionElementImpl)this.mDimensionMapping.findMappedDimensionElement(mappedDimensionElement.getKey());
      if(orig != null) {
         orig.setMappingType(mappedDimensionElement.getMappingType());
         orig.setVisId1(mappedDimensionElement.getVisId1());
         orig.setVisId2(mappedDimensionElement.getVisId2());
         orig.setVisId3(mappedDimensionElement.getVisId3());
      } else {
         this.mDimensionMapping.getMappedDimensionElements().add(mappedDimensionElement);
      }

      this.setContentModified();
   }

   public MappedDimension getDimensionMapping() {
      return this.mDimensionMapping;
   }

   private int getNextKey() {
      return this.mDimensionMapping.getNextKey();
   }
   
   public void setSelectedCompanies(List<FinanceCompany> companies) {
	   this.mDimensionMapping.setSelectedCompanies(companies);
   }
   
   public void setSelectedCompany(String companyVisId, Boolean value) {
	   this.mDimensionMapping.setSelectedCompany(companyVisId, value);
   }
}
