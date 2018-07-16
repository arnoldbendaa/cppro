// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.udwp.Profile;
import com.cedar.cp.api.model.virement.VirementLine;
import com.cedar.cp.api.model.virement.VirementLineEditor;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
import com.cedar.cp.dto.model.virement.VirementLineImpl;
import com.cedar.cp.dto.model.virement.VirementLineSpreadImpl;
import com.cedar.cp.dto.model.virement.VirementLineSpreadPK;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.virement.VirementGroupEditorImpl;
import com.cedar.cp.impl.model.virement.VirementRequestEditorSessionImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class VirementLineEditorImpl extends SubBusinessEditorImpl implements VirementLineEditor {

   private static final WeightingProfilePK sNullProfileKey = new WeightingProfilePK(0);
   private VirementLineImpl mVirementLine;
   private VirementLineImpl mOriginalLine;


   public VirementLineEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner, VirementLineImpl virementLine) {
      super(sess, owner);
      this.mOriginalLine = virementLine;

      try {
         if(virementLine != null) {
            this.mVirementLine = (VirementLineImpl)virementLine.clone();
         } else {
            EntityList e = this.getConnection().getSystemPropertysProcess().getSystemProperty("ALLOC: Threshold");
            double allocationThreshold = 100.0D;
            if(e.getNumRows() > 0) {
               try {
                  allocationThreshold = Double.parseDouble(String.valueOf(e.getValueAt(0, "Value")));
               } catch (NumberFormatException var8) {
                  System.err.println("Failed to parse system property:ALLOC: Threshold value:" + e.getValueAt(0, "Value"));
               }
            }

            this.mVirementLine = new VirementLineImpl(allocationThreshold);
         }

      } catch (CloneNotSupportedException var9) {
         throw new CPException("Failed to clone VirementLineImpl");
      }
   }

   private VirementGroupEditorImpl getVirementGroupEditor() {
      return (VirementGroupEditorImpl)this.getOwner();
   }

   protected void saveModifications() throws ValidationException {
      if(this.mVirementLine.getDataTypeRef() == null) {
         throw new ValidationException("A data type must be selected");
      } else {
         List l = this.getVirementGroupEditor().getVirementGroup().getRows();
         int index;
         if(this.mOriginalLine != null && (index = l.indexOf(this.mOriginalLine)) != -1) {
            l.remove(index);
            l.add(index, this.mVirementLine);
         } else {
            l.add(this.mVirementLine);
         }

      }
   }

   protected void undoModifications() throws CPException {}

   public VirementLine getVirementLine() {
      return this.mVirementLine;
   }

   public void setTransferValue(double value) {
      if(this.mVirementLine.getTransferValue() != value) {
         this.mVirementLine.setTransferValue(value);
         this.setContentModified();
      }

   }

   public void setDataTypeRef(DataTypeRef dataTypeRef) {
      if(this.fieldHasChanged(this.mVirementLine.getDataTypeRef(), dataTypeRef)) {
         this.mVirementLine.setDataTypeRef(dataTypeRef);
         this.setContentModified();
      }

   }

   public void setTo(boolean t) {
      if(this.mVirementLine.isTo() != t) {
         this.mVirementLine.setTo(t);
         this.setContentModified();
      }

   }

   public void setSpreadProfileKey(Object key) throws ValidationException {
      if(key != null && key.equals(sNullProfileKey) | sNullProfileKey.toTokens().equals(key)) {
         key = null;
      }

      if(this.mVirementLine.getSpreadProfileKey() == null && key != null || this.mVirementLine.getSpreadProfileKey() != null && key == null || this.mVirementLine.getSpreadProfileKey() != null && key != null && !this.mVirementLine.getSpreadProfileKey().equals(key)) {
         this.mVirementLine.setSpreadProfileKey(key);
         if(this.mVirementLine.isSummaryLine()) {
            if(key != null) {
               this.applyUserDefinedWeightingProfile(key);
            } else {
               this.mVirementLine.setSpreadProfileVisId((String)null);
            }
         }

         this.setContentModified();
      }

   }

   public void setAddress(List address) throws ValidationException {
      if(this.mVirementLine.getAddress() == null && address != null || this.mVirementLine.getAddress() != null && address == null || this.mVirementLine.getAddress() != null && address != null && !this.mVirementLine.getAddress().equals(address)) {
         if(address != null && address.get(0) instanceof Object[]) {
            for(int dateRef = 0; dateRef < address.size(); ++dateRef) {
               Object[] connection = (Object[])((Object[])address.get(dateRef));
               Integer seDetails = (Integer)connection[0];
               Integer id = (Integer)connection[1];
               String visId = (String)connection[2];
               address.set(dateRef, new StructureElementRefImpl(new StructureElementPK(seDetails.intValue(), id.intValue()), visId));
            }
         }

         this.mVirementLine.setAddress(address);
         this.setContentModified();
      }

      StructureElementRefImpl var7 = this.getDateStructureElement();
      CPConnection var8 = this.getRequestSession().getConnection();
      EntityList var9 = var8.getListHelper().getStructureElementValues(var7);
      this.mVirementLine.setSummaryLine(!((Boolean)var9.getValueAt(0, "Leaf")).booleanValue());
      if(this.mVirementLine.isSummaryLine()) {
         this.populateWeightingProfile((Object)null);
         this.mVirementLine.spreadValueViaRatios();
      } else {
         this.mVirementLine.setSpreadProfile(new ArrayList());
      }

   }

   private void populateWeightingProfile(Object profileKey) throws ValidationException {
      StructureElementRefImpl dateRef = this.getDateStructureElement();
      CPConnection connection = this.getRequestSession().getConnection();
      int sId = dateRef.getStructureElementPK().getStructureId();
      int seId = dateRef.getStructureElementPK().getStructureElementId();
      EntityList leaves = connection.getListHelper().getLeavesForParent(sId, sId, seId, sId, seId);
      List weightings = this.mVirementLine.getSpreadProfile();
      int numRows = leaves.getNumRows();

      for(int i = 0; i < numRows; ++i) {
         StructureElementRefImpl ref = (StructureElementRefImpl)leaves.getValueAt(i, "StructureElement");
         if(weightings.size() <= i) {
            weightings.add(new VirementLineSpreadImpl((VirementLineSpreadPK)null, ref, i, false, 1, 0L));
         }
      }

      this.applyUserDefinedWeightingProfile(profileKey);
      this.mVirementLine.setSpreadProfileKey(profileKey);
   }

   private void applyUserDefinedWeightingProfile(Object profileKey) throws ValidationException {
      List weightings = this.mVirementLine.getSpreadProfile();
      Profile p = null;
      Object profile = null;
      int i;
      if(profileKey != null && this.mVirementLine.getDataTypeRef() != null) {
         i = this.getRequestSession().getVirementRequestEditor().getVirementRequest().getFinanceCubeId();
         int impl = this.getRequestSession().getVirementRequestEditor().getVirementRequest().getModelId();
         Object[] addresses = new Object[this.getVirementLine().getAddress().size() * 2];

         for(int i1 = 0; i1 < addresses.length; i1 += 2) {
            StructureElementRef sef = (StructureElementRef)this.getVirementLine().getAddress().get(i1 / 2);
            StructureElementPK sePK = (StructureElementPK)sef.getPrimaryKey();
            addresses[i1] = Integer.valueOf(sePK.getStructureId());
            addresses[i1 + 1] = Integer.valueOf(sePK.getStructureElementId());
         }

         p = this.getConnection().getWeightingProfilesProcess().getProfileDetail(Integer.valueOf(i), profileKey, addresses, this.mVirementLine.getDataTypeRef().getNarrative());
      }

      int[] var11;
      if(p != null && p.getWeightings() != null) {
         var11 = p.getWeightings();
         this.mVirementLine.setSpreadProfileVisId(p.getRef().getNarrative());
      } else {
         var11 = new int[weightings.size()];
         Arrays.fill(var11, 1);
      }

      for(i = 0; i < weightings.size() && i < var11.length; ++i) {
         VirementLineSpreadImpl var12 = (VirementLineSpreadImpl)weightings.get(i);
         var12.setWeighting(var11[i]);
      }

      this.mVirementLine.spreadValueViaRatios();
   }

   public void setSpreadProfile(StructureElementRef element, boolean held, int weighting) throws ValidationException {
      Iterator spIter = this.mVirementLine.getSpreadProfile().iterator();

      VirementLineSpreadImpl profile;
      do {
         if(!spIter.hasNext()) {
            throw new ValidationException("Element " + element + " not found in spread profile");
         }

         profile = (VirementLineSpreadImpl)spIter.next();
      } while(!profile.getStructureElementRef().equals(element));

      profile.setHeld(held);
      profile.setWeighting(weighting);
      this.mVirementLine.spreadValueViaRatios();
      this.setContentModified();
   }

   public void setSpreadProfile(String elementKey, boolean held, int weighting) throws ValidationException {
      StructureElementPK sePK = StructureElementPK.getKeyFromTokens(elementKey);
      Iterator spIter = this.mVirementLine.getSpreadProfile().iterator();

      VirementLineSpreadImpl profile;
      do {
         if(!spIter.hasNext()) {
            throw new ValidationException("Element key " + elementKey + " not found in spread profile");
         }

         profile = (VirementLineSpreadImpl)spIter.next();
      } while(!profile.getStructureElementRef().getPrimaryKey().equals(sePK));

      profile.setHeld(held);
      profile.setWeighting(weighting);
      this.mVirementLine.spreadValueViaRatios();
      this.setContentModified();
   }

   private StructureElementRefImpl getDateStructureElement() {
      int dateIndex = this.getVirementLine().getAddress().size() - 1;
      return (StructureElementRefImpl)this.getVirementLine().getAddress().get(dateIndex);
   }

   private VirementRequestEditorSessionImpl getRequestSession() {
      return (VirementRequestEditorSessionImpl)this.getBusinessSession();
   }

   public void setRepeatValue(Double repeatValue) {
      this.mVirementLine.setRepeatValue(repeatValue);
   }

}
