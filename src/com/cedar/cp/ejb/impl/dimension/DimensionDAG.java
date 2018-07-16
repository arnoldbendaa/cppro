// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.dimension;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractDAG;
import com.cedar.cp.ejb.impl.dimension.CalendarSpecDAG;
import com.cedar.cp.ejb.impl.dimension.CalendarSpecEVO;
import com.cedar.cp.ejb.impl.dimension.CalendarYearSpecDAG;
import com.cedar.cp.ejb.impl.dimension.CalendarYearSpecEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionDAG$1;
import com.cedar.cp.ejb.impl.dimension.DimensionEVO;
import com.cedar.cp.ejb.impl.dimension.DimensionElementDAG;
import com.cedar.cp.ejb.impl.dimension.DimensionElementEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAG;
import com.cedar.cp.ejb.impl.dimension.HierarchyEVO;
import com.cedar.cp.ejb.impl.dimension.HierarchyNodeDAG;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DimensionDAG extends AbstractDAG {

   private String mVisId;
   private String mDescription;
   private int mDimensionId;
   private int mType;
   private Integer mExternalSystemRef;
   private int mVersionNum;
   private transient DimensionRefImpl mEntityRef;
   private transient DimensionPK mPK;
   private Map<DimensionElementPK, DimensionElementDAG> mPKMap;
   private Map<String, DimensionElementDAG> mVisIdMap;
   private Map<HierarchyRef, HierarchyDAG> mHierarchies;
   private List<CalendarYearSpecDAG> mYearSpecs;
   private CalendarSpecDAG mCalendarSpec;
   private DimensionElementDAG mNullElement;
   private Log mLog = new Log(this.getClass());


   public DimensionDAG(DAGContext context, int type) {
      super(context, true);
      this.mVisId = "";
      this.mDescription = "";
      this.mType = type;
      this.mDimensionId = -1;
      this.mVersionNum = -1;
      this.mPKMap = new HashMap();
      this.mVisIdMap = new HashMap();
      this.mYearSpecs = new ArrayList();
      this.mExternalSystemRef = null;
   }

   public DimensionDAG(DAGContext context, DimensionEVO dimEVO) throws ValidationException {
      super(context, false);
      Timer timer = new Timer(this.mLog);
      this.mVisId = dimEVO.getVisId();
      this.mDescription = dimEVO.getDescription();
      this.mDimensionId = dimEVO.getDimensionId();
      this.mType = dimEVO.getType();
      this.mVersionNum = dimEVO.getVersionNum();
      this.mPKMap = new HashMap();
      this.mVisIdMap = new HashMap();
      this.mYearSpecs = new ArrayList();
      this.mHierarchies = new HashMap();
      this.mExternalSystemRef = dimEVO.getExternalSystemRef();
      Iterator i;
      if(dimEVO.getElements() != null) {
         i = dimEVO.getElements().iterator();

         while(i.hasNext()) {
            DimensionElementEVO hierEVO = (DimensionElementEVO)i.next();
            DimensionElementDAG hierDAG = new DimensionElementDAG(context, hierEVO);
            hierDAG.setDimension(this);
            this.mPKMap.put(hierDAG.getPK(), hierDAG);
            if(this.mType != 3) {
               this.mVisIdMap.put(hierDAG.getVisId(), hierDAG);
            }
         }
      }

      if(this.mType == 3) {
         if(dimEVO.getCalendarSpec() != null && !dimEVO.getCalendarSpec().isEmpty()) {
            CalendarSpecEVO i2 = dimEVO.getCalendarSpecItem();
            CalendarSpecDAG hierEVO1 = new CalendarSpecDAG(this.getContext(), this, i2.getYearStartMonth(), i2.getYearVisIdFormat(), i2.getHalfYearVisIdFormat(), i2.getQuarterVisIdFormat(), i2.getMonthVisIdFormat(), i2.getWeekVisIdFormat(), i2.getDayVisIdFormat(), i2.getYearDescrFormat(), i2.getHalfYearDescrFormat(), i2.getQuarterDescrFormat(), i2.getMonthDescrFormat(), i2.getWeekDescrFormat(), i2.getDayDescrFormat(), i2.getOpenVisIdFormat(), i2.getOpenDescrFormat(), i2.getAdjVisIdFormat(), i2.getAdjDescrFormat(), i2.getP13VisIdFormat(), i2.getP13DescrFormat(), i2.getP14VisIdFormat(), i2.getP14DescrFormat());
            this.setCalendarSpec(hierEVO1);
         } else {
            CalendarSpecDAG i1 = new CalendarSpecDAG(this.getContext(), this);
            this.setCalendarSpec(i1);
         }

         if(dimEVO.getCalendarYearSpecs() != null) {
            i = dimEVO.getCalendarYearSpecs().iterator();

            while(i.hasNext()) {
               CalendarYearSpecEVO hierEVO3 = (CalendarYearSpecEVO)i.next();
               this.mYearSpecs.add(new CalendarYearSpecDAG(context, this, hierEVO3));
            }

            if(!this.mYearSpecs.isEmpty()) {
               Collections.sort(this.mYearSpecs, new DimensionDAG$1(this));
            }
         }
      }

      if(dimEVO.getHierarchies() != null) {
         i = dimEVO.getHierarchies().iterator();

         while(i.hasNext()) {
            HierarchyEVO hierEVO2 = (HierarchyEVO)i.next();
            HierarchyDAG hierDAG1 = new HierarchyDAG(context, this, hierEVO2);
            this.mHierarchies.put(hierDAG1.getEntityRef(), hierDAG1);
         }
      }

      if(dimEVO.getNullElementId() != null) {
         this.mNullElement = (DimensionElementDAG)this.mPKMap.get(new DimensionElementPK(dimEVO.getNullElementId().intValue()));
         this.mNullElement.setNullElement(true);
      }

      context.getCache().put(new DimensionPK(this.mDimensionId), this);
      timer.logInfo("<init>", "");
   }

   public CalendarYearSpecDAG getCalendarYearSpec(int year) {
      Iterator i$ = this.mYearSpecs.iterator();

      CalendarYearSpecDAG ys;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         ys = (CalendarYearSpecDAG)i$.next();
      } while(ys.getCalendarYear() != year);

      return ys;
   }

   public DimensionImpl createLightweightDAG() {
      return this.createLightweightDAG(new DimensionImpl(new DimensionPK(this.mDimensionId)));
   }

   public DimensionImpl createLightweightDAG(DimensionImpl dimImpl) {
      dimImpl.setVisId(this.mVisId);
      dimImpl.setDescription(this.mDescription);
      dimImpl.setType(this.mType);
      dimImpl.setVersionNum(this.mVersionNum);
      dimImpl.setExternalSystemRef(this.mExternalSystemRef);
      Iterator i = this.getDimensionElements().iterator();

      while(i.hasNext()) {
         DimensionElementDAG deDAG = (DimensionElementDAG)i.next();

         try {
            dimImpl.addDimensionElement(deDAG.createLightweightDAG(dimImpl));
         } catch (ValidationException var5) {
            throw new IllegalStateException("Dimension element constraints violated");
         }
      }

      return dimImpl;
   }

   public DimensionEVO createEVO() {
      DimensionEVO devo = new DimensionEVO(this.mDimensionId, this.mVisId, this.mDescription, this.mType, this.mExternalSystemRef, this.mNullElement != null?Integer.valueOf(this.mNullElement.getId()):null, this.mVersionNum, new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList());
      Iterator i = this.getDimensionElements().iterator();

      while(i.hasNext()) {
         DimensionElementDAG i$ = (DimensionElementDAG)i.next();
         DimensionElementEVO spec = i$.createEVO();
         devo.addElementsItem(spec);
      }

      if(this.mType == 3) {
         if(this.mCalendarSpec != null) {
            devo.addCalendarSpecItem(this.mCalendarSpec.createEVO());
         }

         Iterator i$1 = this.mYearSpecs.iterator();

         while(i$1.hasNext()) {
            CalendarYearSpecDAG spec1 = (CalendarYearSpecDAG)i$1.next();
            devo.addCalendarYearSpecsItem(spec1.createEVO());
         }
      }

      return devo;
   }

   public void updateEVO(DimensionEVO dEVO) {
      dEVO.setVisId(this.getVisId());
      dEVO.setDescription(this.getDescription());
      dEVO.setType(this.getType());
      dEVO.setExternalSystemRef(this.getExternalSystemRef());
      dEVO.setNullElementId(this.getNullElement() != null?Integer.valueOf(this.getNullElement().getId()):null);
      ArrayList itemsToDelete;
      Iterator i;
      if(dEVO.getType() == 3) {
         if(dEVO.getCalendarSpec() != null && !dEVO.getCalendarSpec().isEmpty()) {
            CalendarSpecEVO itemsToDelete1 = (CalendarSpecEVO)dEVO.getCalendarSpec().iterator().next();
            this.getCalendarSpec().updateEVO(itemsToDelete1);
         } else {
            itemsToDelete = new ArrayList();
            itemsToDelete.add(this.getCalendarSpec().createEVO());
         }

         if(dEVO.getCalendarYearSpecs() != null) {
            itemsToDelete = new ArrayList(dEVO.getCalendarYearSpecs());
         } else {
            itemsToDelete = new ArrayList();
         }

         i = this.getYearSpecs().iterator();

         while(i.hasNext()) {
            CalendarYearSpecDAG i$ = (CalendarYearSpecDAG)i.next();
            if(i$.getCalendarYearSpecId() < 1) {
               dEVO.addCalendarYearSpecsItem(i$.createEVO());
            } else {
               CalendarYearSpecEVO hDAG = dEVO.getCalendarYearSpecsItem(i$.getPK());
               if(i$.isDirty()) {
                  i$.updateEVO(hDAG);
                  dEVO.changeCalendarYearSpecsItem(hDAG);
               }

               itemsToDelete.remove(hDAG);
            }
         }

         i = itemsToDelete.iterator();

         while(i.hasNext()) {
            CalendarYearSpecEVO i$1 = (CalendarYearSpecEVO)i.next();
            dEVO.deleteCalendarYearSpecsItem(i$1.getPK());
         }
      }

      itemsToDelete = new ArrayList((Collection)(dEVO.getElements() != null?dEVO.getElements():Collections.EMPTY_LIST));
      i = this.getDimensionElements().iterator();

      while(i.hasNext()) {
         DimensionElementDAG i$2 = (DimensionElementDAG)i.next();
         if(i$2.getId() < 1) {
            dEVO.addElementsItem(i$2.createEVO());
         } else {
            DimensionElementEVO hDAG1 = dEVO.getElementsItem(i$2.getPK());
            if(i$2.isDirty()) {
               i$2.updateEVO(hDAG1);
               dEVO.changeElementsItem(hDAG1);
            }

            itemsToDelete.remove(hDAG1);
         }
      }

      i = itemsToDelete.iterator();

      while(i.hasNext()) {
         DimensionElementEVO i$4 = (DimensionElementEVO)i.next();
         dEVO.deleteElementsItem(i$4.getPK());
      }

      if(this.getHierarchies() != null) {
         Iterator i$3 = this.getHierarchies().values().iterator();

         while(i$3.hasNext()) {
            HierarchyDAG hDAG2 = (HierarchyDAG)i$3.next();
            if(hDAG2.isRequiresRebuild()) {
               HierarchyEVO hEVO = dEVO.getHierarchiesItem(new HierarchyPK(hDAG2.getHierarchyId()));
               if(hEVO != null) {
                  hEVO.setModified(true);
               }
            }
         }
      }

   }

   public List findHierarchyNodes(String visId) {
      if(this.mHierarchies == null) {
         return Collections.EMPTY_LIST;
      } else {
         ArrayList result = null;
         Iterator hIter = this.mHierarchies.values().iterator();

         while(hIter.hasNext()) {
            HierarchyDAG hierDAG = (HierarchyDAG)hIter.next();
            HierarchyNodeDAG node = hierDAG.findElement(visId);
            if(node != null) {
               if(result == null) {
                  result = new ArrayList();
               }

               result.add(node);
            }
         }

         return (List)(result == null?Collections.EMPTY_LIST:result);
      }
   }

   public DimensionElementDAG findElement(String visId) {
      if(this.mType == 3) {
         throw new IllegalStateException("Find by visual id not supported for calendar elements");
      } else {
         return (DimensionElementDAG)this.mVisIdMap.get(visId);
      }
   }

   public DimensionElementDAG findElement(int id) {
      return (DimensionElementDAG)this.mPKMap.get(new DimensionElementPK(id));
   }

   public DimensionElementDAG findElement(DimensionElementPK pk) {
      return (DimensionElementDAG)this.mPKMap.get(pk);
   }

   public void addDimensionElement(DimensionElementDAG element) throws ValidationException {
      if(this.mPKMap.get(element.getPK()) != null) {
         throw new ValidationException("Already added " + element.getVisId());
      } else if(this.mType != 3 && this.mVisIdMap.get(element.getVisId()) != null) {
         throw new ValidationException("Duplicate visual id:" + element.getVisId());
      } else {
         this.mPKMap.put(element.getPK(), element);
         if(this.mType != 3) {
            this.mVisIdMap.put(element.getVisId(), element);
         }

         this.setDirty();
      }
   }

   public void removeDimensionElement(DimensionElementDAG element) {
      this.mPKMap.remove(element.getPK());
      if(this.mType != 3) {
         this.mVisIdMap.remove(element.getVisId());
      }

      this.setDirty();
   }

   public void updateDimensionElementVisId(DimensionElementDAG element, String oldVisId, String newVisId) {
      if(this.mType == 3) {
         element.setVisId(newVisId);
      } else {
         this.mVisIdMap.remove(oldVisId);
         element.setVisId(newVisId);
         this.mVisIdMap.put(newVisId, element);
      }

   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
      this.setDirty();
   }

   public int getDimensionId() {
      return this.mDimensionId;
   }

   public int getType() {
      return this.mType;
   }

   public Integer getExternalSystemRef() {
      return this.mExternalSystemRef;
   }

   public void setExternalSystemRef(Integer externalSystemRef) {
      if(externalSystemRef != null && this.mExternalSystemRef == null || externalSystemRef == null && this.mExternalSystemRef != null || externalSystemRef != null && this.mExternalSystemRef != null && !externalSystemRef.equals(this.mExternalSystemRef)) {
         this.mExternalSystemRef = externalSystemRef;
         this.setDirty();
      }

   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public boolean equals(DimensionDAG anotherDim) {
      return anotherDim == null?false:this.mDimensionId == anotherDim.mDimensionId;
   }

   public DimensionRef getEntityRef() {
      if(this.mEntityRef == null) {
         this.mEntityRef = new DimensionRefImpl(new DimensionPK(this.mDimensionId), this.mVisId, this.mType);
      }

      return this.mEntityRef;
   }

   public DimensionPK getPK() {
      if(this.mPK == null) {
         this.mPK = new DimensionPK(this.mDimensionId);
      }

      return this.mPK;
   }

   public Map getPKMap() {
      return this.mPKMap;
   }

   public Map getVisIdMap() {
      return this.mVisIdMap;
   }

   public Collection getDimensionElements() {
      return this.mPKMap.values();
   }

   public List<CalendarYearSpecDAG> getYearSpecs() {
      return this.mYearSpecs;
   }

   public void setYearSpecs(List<CalendarYearSpecDAG> yearSpecs) {
      this.mYearSpecs = yearSpecs;
   }

   public CalendarSpecDAG getCalendarSpec() {
      return this.mCalendarSpec;
   }

   public void setCalendarSpec(CalendarSpecDAG calendarSpec) {
      this.mCalendarSpec = calendarSpec;
   }

   public Map<HierarchyRef, HierarchyDAG> getHierarchies() {
      return this.mHierarchies;
   }

   public DimensionElementDAG getNullElement() {
      return this.mNullElement;
   }

   public void setNullElement(DimensionElementDAG nullElement) {
      if(this.mNullElement != nullElement) {
         this.mNullElement = nullElement;
         this.setDirty();
      }

   }
}
