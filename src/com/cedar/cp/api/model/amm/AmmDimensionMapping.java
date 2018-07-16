// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.amm;

import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmmDimensionMapping implements Serializable, Comparable {

   public static final int sACC_DIM = 0;
   public static final int sBUSS_DIM = 1;
   public static final int sUnMapped_TARGET = 2;
   public static final int sUnMapped_SOURCE = 3;
   public static final int sCAL_DIM = 4;
   private int mType;
   private DimensionRef mDimensionRef;
   private Integer mDimensionId;
   private DimensionRef mSourceDimensionRef;
   private Integer mSourceDimensionId;
   private HierarchyRef mSourceHierarchyRef;
   private Integer mSourcehierarchyId;
   private Map<DimensionElementRef, List<StructureElementRef>> mSelectedElementData;
   private DimensionElementRef mUnmappedDimensionRef;
   private List<StructureElementRef> mSourceElementData;
   private Map<CalendarElementNode, List<StructureElementRef>> mSelectedCalanderElementData;


   public AmmDimensionMapping(DimensionRef dimensionRef, int dimensionId) {
      this.mDimensionRef = dimensionRef;
      this.mDimensionId = Integer.valueOf(dimensionId);
      switch(dimensionRef.getType()) {
      case 1:
         this.mType = 0;
         break;
      case 2:
         this.mType = 1;
         break;
      default:
         this.mType = 4;
      }

   }

   public AmmDimensionMapping(DimensionRef dimensionRef, int dimensionId, int type) {
      this.mDimensionRef = dimensionRef;
      this.mDimensionId = Integer.valueOf(dimensionId);
      this.mType = type;
   }

   public AmmDimensionMapping(DimensionRef dimensionRef, int dimensionId, HierarchyRef sourceHierarchyRef, int sourcehierarchyId, int type) {
      this.mSourceDimensionRef = dimensionRef;
      this.mSourceDimensionId = Integer.valueOf(dimensionId);
      this.mSourceHierarchyRef = sourceHierarchyRef;
      this.mSourcehierarchyId = Integer.valueOf(sourcehierarchyId);
      this.mType = type;
   }

   public AmmDimensionMapping(DimensionRef dimensionRef, int dimensionId, DimensionRef sourceDimensionRef, int sourceDimensionId, HierarchyRef sourceHierarchyRef, int sourcehierarchyId) {
      this(dimensionRef, dimensionId);
      this.mSourceDimensionRef = sourceDimensionRef;
      this.mSourceDimensionId = Integer.valueOf(sourceDimensionId);
      this.mSourceHierarchyRef = sourceHierarchyRef;
      this.mSourcehierarchyId = Integer.valueOf(sourcehierarchyId);
   }

   public DimensionRef getDimensionRef() {
      return this.mDimensionRef;
   }

   public DimensionRef getSourceDimensionRef() {
      return this.mSourceDimensionRef;
   }

   public HierarchyRef getSourceHierarchyRef() {
      return this.mSourceHierarchyRef;
   }

   public Integer getDimensionId() {
      return this.mDimensionId;
   }

   public Integer getSourceDimensionId() {
      return this.mSourceDimensionId;
   }

   public Integer getSourcehierarchyId() {
      return this.mSourcehierarchyId;
   }

   public Map<DimensionElementRef, List<StructureElementRef>> getSelectedElementData() {
      if(this.mSelectedElementData == null) {
         this.mSelectedElementData = new HashMap();
      }

      return this.mSelectedElementData;
   }

   public void setSelectedElementData(Map<DimensionElementRef, List<StructureElementRef>> selectedElementData) {
      this.mSelectedElementData = selectedElementData;
   }

   public DimensionElementRef getUnmappedDimensionRef() {
      return this.mUnmappedDimensionRef;
   }

   public void setUnmappedDimensionRef(DimensionElementRef unmappedDimensionRef) {
      this.mUnmappedDimensionRef = unmappedDimensionRef;
   }

   public Integer getType() {
      return Integer.valueOf(this.mType);
   }

   public void setType(int type) {
      this.mType = type;
   }

   public List<StructureElementRef> getSourceElementData() {
      return this.mSourceElementData;
   }

   public void setSourceElementData(List<StructureElementRef> sourceElementData) {
      this.mSourceElementData = sourceElementData;
   }

   public Map<CalendarElementNode, List<StructureElementRef>> getSelectedCalanderElementData() {
      if(this.mSelectedCalanderElementData == null) {
         this.mSelectedCalanderElementData = new HashMap();
      }

      return this.mSelectedCalanderElementData;
   }

   public void setSelectedCalanderElementData(Map<CalendarElementNode, List<StructureElementRef>> selectedCalanderElementData) {
      this.mSelectedCalanderElementData = selectedCalanderElementData;
   }

   public int compareTo(Object o) {
      AmmDimensionMapping that = (AmmDimensionMapping)o;
      return this.getType() != that.getType()?this.getType().compareTo(that.getType()):(this.getDimensionId() != null?this.getDimensionId().compareTo(that.getDimensionId()):(this.getSourceDimensionId() != null?this.getSourceDimensionId().compareTo(that.getSourceDimensionId()):0));
   }

   public boolean equals(Object o) {
      if(this == o) {
         return true;
      } else if(o != null && this.getClass() == o.getClass()) {
         AmmDimensionMapping that = (AmmDimensionMapping)o;
         if(this.mType != that.mType) {
            return false;
         } else {
            label134: {
               if(this.mDimensionId != null) {
                  if(this.mDimensionId.equals(that.mDimensionId)) {
                     break label134;
                  }
               } else if(that.mDimensionId == null) {
                  break label134;
               }

               return false;
            }

            if(this.mDimensionRef != null) {
               if(!this.mDimensionRef.equals(that.mDimensionRef)) {
                  return false;
               }
            } else if(that.mDimensionRef != null) {
               return false;
            }

            label120: {
               if(this.mSelectedCalanderElementData != null) {
                  if(this.mSelectedCalanderElementData.equals(that.mSelectedCalanderElementData)) {
                     break label120;
                  }
               } else if(that.mSelectedCalanderElementData == null) {
                  break label120;
               }

               return false;
            }

            if(this.mSelectedElementData != null) {
               if(!this.mSelectedElementData.equals(that.mSelectedElementData)) {
                  return false;
               }
            } else if(that.mSelectedElementData != null) {
               return false;
            }

            if(this.mSourceDimensionId != null) {
               if(!this.mSourceDimensionId.equals(that.mSourceDimensionId)) {
                  return false;
               }
            } else if(that.mSourceDimensionId != null) {
               return false;
            }

            if(this.mSourceDimensionRef != null) {
               if(!this.mSourceDimensionRef.equals(that.mSourceDimensionRef)) {
                  return false;
               }
            } else if(that.mSourceDimensionRef != null) {
               return false;
            }

            label92: {
               if(this.mSourceElementData != null) {
                  if(this.mSourceElementData.equals(that.mSourceElementData)) {
                     break label92;
                  }
               } else if(that.mSourceElementData == null) {
                  break label92;
               }

               return false;
            }

            label85: {
               if(this.mSourceHierarchyRef != null) {
                  if(this.mSourceHierarchyRef.equals(that.mSourceHierarchyRef)) {
                     break label85;
                  }
               } else if(that.mSourceHierarchyRef == null) {
                  break label85;
               }

               return false;
            }

            if(this.mSourcehierarchyId != null) {
               if(!this.mSourcehierarchyId.equals(that.mSourcehierarchyId)) {
                  return false;
               }
            } else if(that.mSourcehierarchyId != null) {
               return false;
            }

            boolean var10000;
            label174: {
               if(this.mUnmappedDimensionRef != null) {
                  if(!this.mUnmappedDimensionRef.equals(that.mUnmappedDimensionRef)) {
                     break label174;
                  }
               } else if(that.mUnmappedDimensionRef != null) {
                  break label174;
               }

               var10000 = true;
               return var10000;
            }

            var10000 = false;
            return var10000;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.mType;
      result = 29 * result + (this.mDimensionRef != null?this.mDimensionRef.hashCode():0);
      result = 29 * result + (this.mDimensionId != null?this.mDimensionId.hashCode():0);
      result = 29 * result + (this.mSourceDimensionRef != null?this.mSourceDimensionRef.hashCode():0);
      result = 29 * result + (this.mSourceDimensionId != null?this.mSourceDimensionId.hashCode():0);
      result = 29 * result + (this.mSourceHierarchyRef != null?this.mSourceHierarchyRef.hashCode():0);
      result = 29 * result + (this.mSourcehierarchyId != null?this.mSourcehierarchyId.hashCode():0);
      result = 29 * result + (this.mSelectedElementData != null?this.mSelectedElementData.hashCode():0);
      result = 29 * result + (this.mUnmappedDimensionRef != null?this.mUnmappedDimensionRef.hashCode():0);
      result = 29 * result + (this.mSourceElementData != null?this.mSourceElementData.hashCode():0);
      result = 29 * result + (this.mSelectedCalanderElementData != null?this.mSelectedCalanderElementData.hashCode():0);
      return result;
   }
}
