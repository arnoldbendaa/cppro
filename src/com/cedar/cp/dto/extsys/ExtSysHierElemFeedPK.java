/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysHierElemFeedPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 199 */   private int mHashCode = -2147483648;
/*     */   int mExternalSystemId;
/*     */   String mCompanyVisId;
/*     */   String mLedgerVisId;
/*     */   String mDimensionVisId;
/*     */   String mHierElementVisId;
/*     */   String mDimElementVisId;
/*     */ 
/*     */   public ExtSysHierElemFeedPK(int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId, String newHierElementVisId, String newDimElementVisId)
/*     */   {
/*  28 */     this.mExternalSystemId = newExternalSystemId;
/*  29 */     this.mCompanyVisId = newCompanyVisId;
/*  30 */     this.mLedgerVisId = newLedgerVisId;
/*  31 */     this.mDimensionVisId = newDimensionVisId;
/*  32 */     this.mHierElementVisId = newHierElementVisId;
/*  33 */     this.mDimElementVisId = newDimElementVisId;
/*     */   }
/*     */ 
/*     */   public int getExternalSystemId()
/*     */   {
/*  42 */     return this.mExternalSystemId;
/*     */   }
/*     */ 
/*     */   public String getCompanyVisId()
/*     */   {
/*  49 */     return this.mCompanyVisId;
/*     */   }
/*     */ 
/*     */   public String getLedgerVisId()
/*     */   {
/*  56 */     return this.mLedgerVisId;
/*     */   }
/*     */ 
/*     */   public String getDimensionVisId()
/*     */   {
/*  63 */     return this.mDimensionVisId;
/*     */   }
/*     */ 
/*     */   public String getHierElementVisId()
/*     */   {
/*  70 */     return this.mHierElementVisId;
/*     */   }
/*     */ 
/*     */   public String getDimElementVisId()
/*     */   {
/*  77 */     return this.mDimElementVisId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  85 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  87 */       this.mHashCode += String.valueOf(this.mExternalSystemId).hashCode();
/*  88 */       this.mHashCode += this.mCompanyVisId.hashCode();
/*  89 */       this.mHashCode += this.mLedgerVisId.hashCode();
/*  90 */       this.mHashCode += this.mDimensionVisId.hashCode();
/*  91 */       this.mHashCode += this.mHierElementVisId.hashCode();
/*  92 */       this.mHashCode += this.mDimElementVisId.hashCode();
/*     */     }
/*     */ 
/*  95 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 103 */     ExtSysHierElemFeedPK other = null;
/*     */ 
/* 105 */     if ((obj instanceof ExtSysHierElemFeedCK)) {
/* 106 */       other = ((ExtSysHierElemFeedCK)obj).getExtSysHierElemFeedPK();
/*     */     }
/* 108 */     else if ((obj instanceof ExtSysHierElemFeedPK))
/* 109 */       other = (ExtSysHierElemFeedPK)obj;
/*     */     else {
/* 111 */       return false;
/*     */     }
/* 113 */     boolean eq = true;
/*     */ 
/* 115 */     eq = (eq) && (this.mExternalSystemId == other.mExternalSystemId);
/* 116 */     eq = (eq) && (this.mCompanyVisId.equals(other.mCompanyVisId));
/* 117 */     eq = (eq) && (this.mLedgerVisId.equals(other.mLedgerVisId));
/* 118 */     eq = (eq) && (this.mDimensionVisId.equals(other.mDimensionVisId));
/* 119 */     eq = (eq) && (this.mHierElementVisId.equals(other.mHierElementVisId));
/* 120 */     eq = (eq) && (this.mDimElementVisId.equals(other.mDimElementVisId));
/*     */ 
/* 122 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 130 */     StringBuffer sb = new StringBuffer();
/* 131 */     sb.append(" ExternalSystemId=");
/* 132 */     sb.append(this.mExternalSystemId);
/* 133 */     sb.append(",CompanyVisId=");
/* 134 */     sb.append(this.mCompanyVisId);
/* 135 */     sb.append(",LedgerVisId=");
/* 136 */     sb.append(this.mLedgerVisId);
/* 137 */     sb.append(",DimensionVisId=");
/* 138 */     sb.append(this.mDimensionVisId);
/* 139 */     sb.append(",HierElementVisId=");
/* 140 */     sb.append(this.mHierElementVisId);
/* 141 */     sb.append(",DimElementVisId=");
/* 142 */     sb.append(this.mDimElementVisId);
/* 143 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 151 */     StringBuffer sb = new StringBuffer();
/* 152 */     sb.append(" ");
/* 153 */     sb.append(this.mExternalSystemId);
/* 154 */     sb.append(",");
/* 155 */     sb.append(this.mCompanyVisId);
/* 156 */     sb.append(",");
/* 157 */     sb.append(this.mLedgerVisId);
/* 158 */     sb.append(",");
/* 159 */     sb.append(this.mDimensionVisId);
/* 160 */     sb.append(",");
/* 161 */     sb.append(this.mHierElementVisId);
/* 162 */     sb.append(",");
/* 163 */     sb.append(this.mDimElementVisId);
/* 164 */     return "ExtSysHierElemFeedPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ExtSysHierElemFeedPK getKeyFromTokens(String extKey)
/*     */   {
/* 169 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 171 */     if (extValues.length != 2) {
/* 172 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 174 */     if (!extValues[0].equals("ExtSysHierElemFeedPK")) {
/* 175 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ExtSysHierElemFeedPK|'");
/*     */     }
/* 177 */     extValues = extValues[1].split(",");
/*     */ 
/* 179 */     int i = 0;
/* 180 */     int pExternalSystemId = new Integer(extValues[(i++)]).intValue();
/* 181 */     String pCompanyVisId = new String(extValues[(i++)]);
/* 182 */     String pLedgerVisId = new String(extValues[(i++)]);
/* 183 */     String pDimensionVisId = new String(extValues[(i++)]);
/* 184 */     String pHierElementVisId = new String(extValues[(i++)]);
/* 185 */     String pDimElementVisId = new String(extValues[(i++)]);
/* 186 */     return new ExtSysHierElemFeedPK(pExternalSystemId, pCompanyVisId, pLedgerVisId, pDimensionVisId, pHierElementVisId, pDimElementVisId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysHierElemFeedPK
 * JD-Core Version:    0.6.0
 */