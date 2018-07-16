// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import com.cedar.cp.util.xmlform.inputs.FormInputModel;
import java.math.BigDecimal;

public class SingleRowFormInputModel implements FormInputModel {

   private String mVisId;
   private String mLabel;
   private BigDecimal mDepth;
   private String mLeaf;
   private String mIsCredit;
   private String mDisabled;
   private String mNotPlannable;
   private BigDecimal mSecurityState;
   private Object mId;
   private BigDecimal mPosition;
   private BigDecimal mState;
   private BigDecimal mAxisDimIndex;


   public SingleRowFormInputModel(BigDecimal axisDimIndex, Object id, BigDecimal position, String visId, String label, BigDecimal depth, String leaf, String credit, String disabled, String notPlannable, BigDecimal securityState) {
      this.mVisId = visId;
      this.mLabel = label;
      this.mDepth = depth;
      this.mLeaf = leaf;
      this.mIsCredit = credit;
      this.mDisabled = disabled;
      this.mNotPlannable = notPlannable;
      this.mSecurityState = securityState;
      this.mId = id;
      this.mPosition = position;
      this.mState = new BigDecimal(0);
      this.mAxisDimIndex = axisDimIndex;
   }

   public int getRowCount() {
      return 1;
   }

   public int getColumnCount() {
      return 11;
   }

   public Object getValueAt(int row, int col) {
      switch(col) {
      case 0:
         return this.mVisId;
      case 1:
         return this.mLabel;
      case 2:
         return this.mState;
      case 3:
         return this.mDepth;
      case 4:
         return this.mLeaf;
      case 5:
         return this.mIsCredit;
      case 6:
         return this.mDisabled;
      case 7:
         return this.mNotPlannable;
      case 8:
         return this.mSecurityState;
      case 9:
         return this.mId;
      case 10:
         return this.mPosition;
      case 11:
         return this.mAxisDimIndex;
      default:
         return null;
      }
   }

   public void setValueAt(Object value, int row, int col) {
      switch(col) {
      case 0:
         this.mVisId = (String)value;
         break;
      case 1:
         this.mLabel = (String)value;
         break;
      case 2:
         this.mState = (BigDecimal)value;
         break;
      case 3:
         this.mDepth = (BigDecimal)value;
         break;
      case 4:
         this.mLeaf = (String)value;
         break;
      case 5:
         this.mIsCredit = (String)value;
         break;
      case 6:
         this.mDisabled = (String)value;
         break;
      case 7:
         this.mNotPlannable = (String)value;
         break;
      case 8:
         this.mSecurityState = (BigDecimal)value;
         break;
      case 9:
         this.mId = value;
         break;
      case 10:
         this.mPosition = (BigDecimal)value;
         break;
      case 11:
         this.mAxisDimIndex = (BigDecimal)value;
      }

   }

   public int getSheetProtectionLevel() {
      return 0;
   }
}
