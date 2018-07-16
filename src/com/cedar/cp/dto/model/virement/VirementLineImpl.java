// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.virement.VirementLine;
import com.cedar.cp.api.model.virement.VirementLineSpread;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
import com.cedar.cp.dto.model.udwp.WeightingProfileRefImpl;
import com.cedar.cp.dto.model.virement.VirementLineSpreadImpl;
import com.cedar.cp.dto.model.virement.VirementRequestLinePK;
import com.cedar.cp.util.GeneralUtils;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VirementLineImpl implements VirementLine, Cloneable {

   private VirementRequestLinePK mPK;
   private long mTransferValue;
   private DataTypeRef mDataTypeRef;
   private boolean mTo;
   private boolean mSummaryLine;
   private List mAddress;
   private Object mSpreadProfileKey;
   private String mSpreadProfileVisId;
   private List<VirementLineSpread> mSpreadProfile;
   private Long mRepeatValue;
   private double mAllocationThreshold;


   public VirementLineImpl(double allocationThreshold) {
      this.mAddress = new ArrayList();
      this.mSpreadProfile = new ArrayList();
      this.setAllocationThreshold(allocationThreshold);
   }

   public VirementLineImpl(VirementRequestLinePK pk, double allocationThreshold) {
      this(allocationThreshold);
      this.mPK = pk;
   }

   public List getAddress() {
      return this.mAddress;
   }

   public void setAddress(List address) {
      this.mAddress = address;
   }

   public long getTransferValueAsLong() {
      return this.mTransferValue;
   }

   public double getTransferValue() {
      return GeneralUtils.convertDBToFinancialValue(this.mTransferValue);
   }

   public void setTransferValue(double transferValue) {
      this.mTransferValue = GeneralUtils.convertFinancialValueToDB(transferValue);
   }

   public void setTransferValue(long transferValue) {
      this.mTransferValue = transferValue;
   }

   public boolean isTo() {
      return this.mTo;
   }

   public boolean isFrom() {
      return !this.isTo();
   }

   public void setTo(boolean to) {
      this.mTo = to;
   }

   public Object clone() throws CloneNotSupportedException {
      VirementLineImpl cpy = (VirementLineImpl)super.clone();
      cpy.setPK(this.getPK());
      cpy.setTransferValue(this.getTransferValue());
      cpy.setDataTypeRef(this.getDataTypeRef());
      cpy.setTo(this.isTo());
      cpy.setAddress(this.getAddress());
      cpy.setSpreadProfile(this.cloneSpreadProfile());
      cpy.setSpreadProfileVisId(this.getSpreadProfileVisId());
      cpy.setAllocationThreshold(this.getAllocationThreshold());
      cpy.setRepeatValue(this.getRepeatValue());
      return cpy;
   }

   private List<VirementLineSpread> cloneSpreadProfile() {
      ArrayList copyProfile = new ArrayList();
      if(this.mSpreadProfile != null) {
         Iterator i$ = this.mSpreadProfile.iterator();

         while(i$.hasNext()) {
            VirementLineSpread spread = (VirementLineSpread)i$.next();

            try {
               copyProfile.add((VirementLineSpread)((VirementLineSpreadImpl)spread).clone());
            } catch (CloneNotSupportedException var5) {
               throw new IllegalStateException("Failed to clone virement line spread impl:", var5);
            }
         }
      }

      return copyProfile;
   }

   public void validate() throws ValidationException {
      if(this.mAddress != null && !this.mAddress.isEmpty()) {
         if(this.getDataTypeRef() == null) {
            throw new ValidationException("A data type reference must be defined");
         } else if(this.isSummaryLine() && this.isInvalidWeigthingProfile()) {
            throw new ValidationException("Weighting profile invalid - must allow allocation to at least one period");
         }
      } else {
         throw new ValidationException("Transfer line must have address defined");
      }
   }

   private boolean isInvalidWeigthingProfile() {
      if(this.mSpreadProfile != null) {
         Iterator i$ = this.mSpreadProfile.iterator();

         while(i$.hasNext()) {
            VirementLineSpread spread = (VirementLineSpread)i$.next();
            if(!spread.isHeld() && spread.getWeighting() != 0) {
               return false;
            }
         }
      }

      return true;
   }

   public String getAddressAsCSVString(boolean excludeCalendarDim) {
      return getAddressAsCSVString(this.getAddress(), excludeCalendarDim);
   }

   public String getAddressAsCSVString() {
      return getAddressAsCSVString(this.getAddress());
   }

   public int getBudgetLocationId() {
      return ((StructureElementRefImpl)this.mAddress.get(0)).getStructureElementPK().getStructureElementId();
   }

   public String getAddressVisIds() {
      return this.getAddressVisIds(",");
   }

   public String[] getAddressForCOATest() {
      String[] coaAddress = new String[this.getAddress().size()];

      for(int i = 0; i < this.getAddress().size() - 1; ++i) {
         StructureElementRef structureElementRef = (StructureElementRef)this.getAddress().get(i);
         coaAddress[i] = structureElementRef.getNarrative();
      }

      coaAddress[coaAddress.length - 1] = this.getDataTypeRef().getNarrative();
      return coaAddress;
   }

   public String getAddressVisIds(String delim) {
      StringBuffer sb = new StringBuffer();
      Iterator aIter = this.mAddress.iterator();

      while(aIter.hasNext()) {
         StructureElementRef structureElementRef = (StructureElementRef)aIter.next();
         sb.append(structureElementRef.getNarrative());
         if(aIter.hasNext()) {
            sb.append(delim);
         }
      }

      return sb.toString();
   }

   public static String getAddressAsCSVString(List address) {
      return getAddressAsCSVString(address, false);
   }

   public static String getAddressAsCSVString(List address, boolean excludeDate) {
      StringBuffer sb = new StringBuffer();
      int size = excludeDate?address.size() - 1:address.size();

      for(int i = 0; i < size; ++i) {
         StructureElementRef seRef = (StructureElementRef)address.get(i);
         sb.append(((StructureElementPK)((StructureElementPK)seRef.getPrimaryKey())).getStructureElementId());
         if(i < size - 1) {
            sb.append(",");
         }
      }

      return sb.toString();
   }

   public String getAddressAsCSVStringWithDataType(String dataType) {
      return getAddressAsCSVString(this.getAddress()) + ',' + dataType;
   }

   public static String getAddressAsCSVStringWithDataType(List address, String dataType) {
      return getAddressAsCSVString(address) + ',' + dataType;
   }

   public Object getKey() {
      return this.mPK;
   }

   public VirementRequestLinePK getPK() {
      return this.mPK;
   }

   public String getKeyAsText() {
      return this.mPK != null?this.mPK.toTokens():null;
   }

   public void setPK(VirementRequestLinePK key) {
      this.mPK = key;
   }

   public boolean equals(Object obj) {
      if(!(obj instanceof VirementLine)) {
         return false;
      } else {
         VirementLine vl = (VirementLine)obj;
         return (this.getKey() != null || vl.getKey() == null) && (this.getKey() == null || vl.getKey() != null) && (this.getKey() == null || vl.getKey() == null || this.getKey().equals(vl.getKey()));
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("\nVirementLineImpl");
      sb.append(" pK:" + this.mPK != null?this.mPK.toString():"null");
      sb.append(" dataType: " + this.mDataTypeRef != null?"null":this.mDataTypeRef);
      sb.append(" transferValue:");
      sb.append(String.valueOf(this.getTransferValue()));
      sb.append(" to:");
      sb.append(String.valueOf(this.mTo));
      if(this.mRepeatValue != null) {
         sb.append(" repeat:");
      }

      sb.append(this.mRepeatValue);
      sb.append("\naddress:");
      sb.append(this.mAddress);
      return sb.toString();
   }

   public boolean isSummaryLine() {
      return this.mSummaryLine;
   }

   public void setSummaryLine(boolean summaryLine) {
      this.mSummaryLine = summaryLine;
   }

   public List getSpreadProfile() {
      return this.mSpreadProfile;
   }

   public void setSpreadProfile(List spreadProfile) {
      this.mSpreadProfile = spreadProfile;
   }

   private void resetSpreadProfileValues() {
      Iterator profilesIter = this.mSpreadProfile.iterator();

      while(profilesIter.hasNext()) {
         VirementLineSpreadImpl lineSpread = (VirementLineSpreadImpl)profilesIter.next();
         lineSpread.setTransferValue(0.0D);
      }

   }

   public void spreadValueViaRatios() {
      this.resetSpreadProfileValues();
      List weightings = this.getSpreadProfile();
      if(this.getRepeatValue() != null) {
         this.setTransferValue(this.repeatValue(this.getRepeatValue().doubleValue(), weightings));
      } else {
         int sumOfWeightings = this.calculateSumOfWeightings();
         this.spreadValue(this.getTransferValue(), sumOfWeightings, weightings, this.getAllocationThreshold());
      }

   }

   private double repeatValue(double value, List<VirementLineSpreadImpl> weightings) {
      double transferValue = 0.0D;
      Iterator i$ = weightings.iterator();

      while(i$.hasNext()) {
         VirementLineSpreadImpl lineSpread = (VirementLineSpreadImpl)i$.next();
         if(!lineSpread.isHeld() && lineSpread.getWeighting() != 0) {
            lineSpread.setTransferValue(value);
            transferValue += value;
         } else {
            lineSpread.setTransferValue(0.0D);
         }
      }

      return transferValue;
   }

   private void spreadValue(double value, int sumOfWeightings, List<VirementLineSpreadImpl> weightings, double absAllocThreshold) {
      double allocated = 0.0D;
      double toAllocate = BigDecimal.valueOf(value).setScale(2, 4).doubleValue();
      byte spreadPrecision = 1;
      if(Math.abs(value) < absAllocThreshold) {
         spreadPrecision = 100;
      }

      int bucketCount = this.getBucketCount(weightings);
      boolean isSimpleEvenSplit = this.isSimpleEvenSplit(toAllocate, bucketCount, weightings);
      int i;
      if(sumOfWeightings != 0) {
         Iterator remainder = weightings.iterator();

         while(remainder.hasNext()) {
            VirementLineSpreadImpl lineSpread = (VirementLineSpreadImpl)remainder.next();
            i = lineSpread.getWeighting();
            boolean lineSpread1 = lineSpread.isHeld();
            if(!lineSpread1 && (double)i != 0.0D) {
               double alloc = 0.0D;
               if(isSimpleEvenSplit) {
                  alloc = BigDecimal.valueOf(toAllocate).divide(BigDecimal.valueOf((long)bucketCount)).doubleValue();
               } else if(Math.abs(allocated) < Math.abs(toAllocate)) {
                  double tmpVal = toAllocate * (double)spreadPrecision * (double)i / (double)sumOfWeightings;
                  long l = (long)(tmpVal > 0.0D?Math.floor(tmpVal):Math.ceil(tmpVal));
                  alloc = (double)l / (double)spreadPrecision;
               }

               lineSpread.setTransferValue(alloc);
               allocated += alloc;
            } else {
               lineSpread.setTransferValue(0.0D);
            }
         }
      }

      double var24 = toAllocate - allocated;
      if(var24 != 0.0D) {
         var24 = var24 * 100.0D / 100.0D;

         for(i = weightings.size() - 1; i >= 0; --i) {
            VirementLineSpreadImpl var25 = (VirementLineSpreadImpl)weightings.get(i);
            if(!var25.isHeld() && var25.getWeighting() != 0) {
               var25.setTransferValue(var25.getTransferValue() + var24);
               double var10000 = allocated + var24;
               break;
            }
         }
      }

   }

   public int calculateSumOfWeightings() {
      int sumOfWeightings = 0;
      List weightings = this.getSpreadProfile();
      Iterator profilesIter = weightings.iterator();

      while(profilesIter.hasNext()) {
         VirementLineSpreadImpl spreadLine = (VirementLineSpreadImpl)profilesIter.next();
         if(!spreadLine.isHeld()) {
            sumOfWeightings += spreadLine.getWeighting();
         }
      }

      return sumOfWeightings;
   }

   private boolean isSimpleEvenSplit(double toAllocate, int bucketCount, List<VirementLineSpreadImpl> weightings) {
      MathContext mc = new MathContext(4, toAllocate > 0.0D?RoundingMode.FLOOR:RoundingMode.CEILING);
      return this.isEvenSplit(weightings) && BigDecimal.valueOf(toAllocate).divide(BigDecimal.valueOf((long)bucketCount), mc).multiply(BigDecimal.valueOf((long)bucketCount), mc).equals(BigDecimal.valueOf(toAllocate));
   }

   private boolean isEvenSplit(List<VirementLineSpreadImpl> weightings) {
      int firstRatio = -1;
      Iterator profilesIter = weightings.iterator();

      while(profilesIter.hasNext()) {
         VirementLineSpreadImpl lineSpread = (VirementLineSpreadImpl)profilesIter.next();
         boolean held = lineSpread.isHeld();
         if(!held) {
            if(firstRatio == -1) {
               firstRatio = lineSpread.getWeighting();
            } else if(firstRatio != lineSpread.getWeighting()) {
               return false;
            }
         }
      }

      return firstRatio != -1;
   }

   private int getBucketCount(List<VirementLineSpreadImpl> weightings) {
      int bucketCount = 0;
      Iterator profilesIter = weightings.iterator();

      while(profilesIter.hasNext()) {
         VirementLineSpreadImpl lineSpread = (VirementLineSpreadImpl)profilesIter.next();
         boolean held = lineSpread.isHeld();
         if(!held) {
            ++bucketCount;
         }
      }

      return bucketCount;
   }

   public VirementLineSpreadImpl findLineSpread(Object key) {
      Iterator spIter = this.mSpreadProfile.iterator();

      VirementLineSpreadImpl virementLineSpread;
      do {
         if(!spIter.hasNext()) {
            return null;
         }

         virementLineSpread = (VirementLineSpreadImpl)spIter.next();
      } while(virementLineSpread.getKey() == null || key == null || !key.equals(virementLineSpread.getKey()));

      return virementLineSpread;
   }

   public StructureElementRef getCalendarElement() {
      return (StructureElementRef)this.mAddress.get(this.mAddress.size() - 1);
   }

   public Object getSpreadProfileKey() {
      return this.mSpreadProfileKey;
   }

   public void setSpreadProfileKey(Object profileKey) {
      this.mSpreadProfileKey = profileKey;
   }

   public String getSpreadProfileKeyAsText() {
      if(this.mSpreadProfileKey instanceof String) {
         return (String)this.mSpreadProfileKey;
      } else if(this.mSpreadProfileKey instanceof WeightingProfilePK) {
         return ((WeightingProfilePK)this.mSpreadProfileKey).toTokens();
      } else if(this.mSpreadProfileKey instanceof WeightingProfileRefImpl) {
         return ((WeightingProfileRefImpl)this.mSpreadProfileKey).getTokenizedKey();
      } else if(this.mSpreadProfileKey == null) {
         return null;
      } else {
         throw new IllegalStateException("Unknown key for spread profile:" + this.mSpreadProfileKey);
      }
   }

   public void setSpreadProfileVisId(String spreadProfileVisId) {
      this.mSpreadProfileVisId = spreadProfileVisId;
   }

   public String getSpreadProfileVisId() {
      return this.mSpreadProfileVisId;
   }

   public double getAllocationThreshold() {
      return this.mAllocationThreshold;
   }

   public void setAllocationThreshold(double allocationThreshold) {
      this.mAllocationThreshold = allocationThreshold;
   }

   public DataTypeRef getDataTypeRef() {
      return this.mDataTypeRef;
   }

   public void setDataTypeRef(DataTypeRef dataTypeRef) {
      this.mDataTypeRef = dataTypeRef;
   }

   public Long getRepeatValueAsLong() {
      return this.mRepeatValue;
   }

   public Double getRepeatValue() {
      return this.mRepeatValue == null?null:Double.valueOf(GeneralUtils.convertDBToFinancialValue(this.mRepeatValue.longValue()));
   }

   public void setRepeatValue(Long repeatValue) {
      this.mRepeatValue = repeatValue;
   }

   public void setRepeatValue(Double repeatValue) {
      if(repeatValue == null) {
         this.mRepeatValue = null;
      } else {
         this.mRepeatValue = Long.valueOf(GeneralUtils.convertFinancialValueToDB(repeatValue.doubleValue()));
      }

   }
}
