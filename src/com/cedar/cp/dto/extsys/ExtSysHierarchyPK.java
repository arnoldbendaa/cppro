/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysHierarchyPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 182 */   private int mHashCode = -2147483648;
/*     */   int mExternalSystemId;
/*     */   String mCompanyVisId;
/*     */   String mLedgerVisId;
/*     */   String mDimensionVisId;
/*     */   String mHierarchyVisId;
/*     */ 
/*     */   public ExtSysHierarchyPK(int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId, String newHierarchyVisId)
/*     */   {
/*  27 */     this.mExternalSystemId = newExternalSystemId;
/*  28 */     this.mCompanyVisId = newCompanyVisId;
/*  29 */     this.mLedgerVisId = newLedgerVisId;
/*  30 */     this.mDimensionVisId = newDimensionVisId;
/*  31 */     this.mHierarchyVisId = newHierarchyVisId;
/*     */   }
/*     */ 
/*     */   public int getExternalSystemId()
/*     */   {
/*  40 */     return this.mExternalSystemId;
/*     */   }
/*     */ 
/*     */   public String getCompanyVisId()
/*     */   {
/*  47 */     return this.mCompanyVisId;
/*     */   }
/*     */ 
/*     */   public String getLedgerVisId()
/*     */   {
/*  54 */     return this.mLedgerVisId;
/*     */   }
/*     */ 
/*     */   public String getDimensionVisId()
/*     */   {
/*  61 */     return this.mDimensionVisId;
/*     */   }
/*     */ 
/*     */   public String getHierarchyVisId()
/*     */   {
/*  68 */     return this.mHierarchyVisId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  76 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  78 */       this.mHashCode += String.valueOf(this.mExternalSystemId).hashCode();
/*  79 */       this.mHashCode += this.mCompanyVisId.hashCode();
/*  80 */       this.mHashCode += this.mLedgerVisId.hashCode();
/*  81 */       this.mHashCode += this.mDimensionVisId.hashCode();
/*  82 */       this.mHashCode += this.mHierarchyVisId.hashCode();
/*     */     }
/*     */ 
/*  85 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  93 */     ExtSysHierarchyPK other = null;
/*     */ 
/*  95 */     if ((obj instanceof ExtSysHierarchyCK)) {
/*  96 */       other = ((ExtSysHierarchyCK)obj).getExtSysHierarchyPK();
/*     */     }
/*  98 */     else if ((obj instanceof ExtSysHierarchyPK))
/*  99 */       other = (ExtSysHierarchyPK)obj;
/*     */     else {
/* 101 */       return false;
/*     */     }
/* 103 */     boolean eq = true;
/*     */ 
/* 105 */     eq = (eq) && (this.mExternalSystemId == other.mExternalSystemId);
/* 106 */     eq = (eq) && (this.mCompanyVisId.equals(other.mCompanyVisId));
/* 107 */     eq = (eq) && (this.mLedgerVisId.equals(other.mLedgerVisId));
/* 108 */     eq = (eq) && (this.mDimensionVisId.equals(other.mDimensionVisId));
/* 109 */     eq = (eq) && (this.mHierarchyVisId.equals(other.mHierarchyVisId));
/*     */ 
/* 111 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 119 */     StringBuffer sb = new StringBuffer();
/* 120 */     sb.append(" ExternalSystemId=");
/* 121 */     sb.append(this.mExternalSystemId);
/* 122 */     sb.append(",CompanyVisId=");
/* 123 */     sb.append(this.mCompanyVisId);
/* 124 */     sb.append(",LedgerVisId=");
/* 125 */     sb.append(this.mLedgerVisId);
/* 126 */     sb.append(",DimensionVisId=");
/* 127 */     sb.append(this.mDimensionVisId);
/* 128 */     sb.append(",HierarchyVisId=");
/* 129 */     sb.append(this.mHierarchyVisId);
/* 130 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 138 */     StringBuffer sb = new StringBuffer();
/* 139 */     sb.append(" ");
/* 140 */     sb.append(this.mExternalSystemId);
/* 141 */     sb.append(",");
/* 142 */     sb.append(this.mCompanyVisId);
/* 143 */     sb.append(",");
/* 144 */     sb.append(this.mLedgerVisId);
/* 145 */     sb.append(",");
/* 146 */     sb.append(this.mDimensionVisId);
/* 147 */     sb.append(",");
/* 148 */     sb.append(this.mHierarchyVisId);
/* 149 */     return "ExtSysHierarchyPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ExtSysHierarchyPK getKeyFromTokens(String extKey)
/*     */   {
/* 154 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 156 */     if (extValues.length != 2) {
/* 157 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 159 */     if (!extValues[0].equals("ExtSysHierarchyPK")) {
/* 160 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ExtSysHierarchyPK|'");
/*     */     }
/* 162 */     extValues = extValues[1].split(",");
/*     */ 
/* 164 */     int i = 0;
/* 165 */     int pExternalSystemId = new Integer(extValues[(i++)]).intValue();
/* 166 */     String pCompanyVisId = new String(extValues[(i++)]);
/* 167 */     String pLedgerVisId = new String(extValues[(i++)]);
/* 168 */     String pDimensionVisId = new String(extValues[(i++)]);
/* 169 */     String pHierarchyVisId = new String(extValues[(i++)]);
/* 170 */     return new ExtSysHierarchyPK(pExternalSystemId, pCompanyVisId, pLedgerVisId, pDimensionVisId, pHierarchyVisId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysHierarchyPK
 * JD-Core Version:    0.6.0
 */