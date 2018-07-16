// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.distribution;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.report.destination.external.ExternalDestinationRef;
import com.cedar.cp.api.report.destination.internal.InternalDestinationRef;
import com.cedar.cp.api.report.distribution.Distribution;
import com.cedar.cp.dto.report.destination.external.AllExternalDestinationsELO;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationsELO;
import com.cedar.cp.dto.report.distribution.DistributionImpl;
import com.cedar.cp.dto.report.distribution.DistributionPK;
import com.cedar.cp.impl.report.distribution.DistributionEditorSessionImpl;
import java.util.List;

public class DistributionAdapter implements Distribution {

   private DistributionImpl mEditorData;
   private DistributionEditorSessionImpl mEditorSessionImpl;


   public DistributionAdapter(DistributionEditorSessionImpl e, DistributionImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected DistributionEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected DistributionImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(DistributionPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public boolean isRaDistribution() {
      return this.mEditorData.isRaDistribution();
   }

   public String getDirRoot() {
      return this.mEditorData.getDirRoot();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setRaDistribution(boolean p) {
      this.mEditorData.setRaDistribution(p);
   }

   public void setDirRoot(String p) {
      this.mEditorData.setDirRoot(p);
   }

   public List getInternalDestinationList() {
      return this.mEditorData.getInternalDestinationList();
   }

   public List getExternalDestinationList() {
      return this.mEditorData.getExternalDestinationList();
   }

   public EntityList getAvailAbleInternalDestination() {
      EntityList list = this.mEditorSessionImpl.getConnection().getListHelper().getAllInternalDestinations();
      int size = list.getNumRows();
      AllInternalDestinationsELO avail = new AllInternalDestinationsELO();
      List selected = this.getInternalDestinationList();

      for(int i = 0; i < size; ++i) {
         InternalDestinationRef ref = (InternalDestinationRef)list.getValueAt(i, "InternalDestination");
         if(!selected.contains(ref)) {
            Object o = list.getValueAt(i, "Description");
            String name = "";
            if(o instanceof String) {
               name = o.toString();
            }

            avail.add(ref, name);
         }
      }

      return avail;
   }

   public EntityList getAvailAbleExternalDestination() {
      EntityList list = this.mEditorSessionImpl.getConnection().getListHelper().getAllExternalDestinations();
      int size = list.getNumRows();
      AllExternalDestinationsELO avail = new AllExternalDestinationsELO();
      List selected = this.getExternalDestinationList();

      for(int i = 0; i < size; ++i) {
         ExternalDestinationRef ref = (ExternalDestinationRef)list.getValueAt(i, "ExternalDestination");
         if(!selected.contains(ref)) {
            Object o = list.getValueAt(i, "Description");
            String name = "";
            if(o instanceof String) {
               name = o.toString();
            }

            avail.add(ref, name);
         }
      }

      return avail;
   }
}
