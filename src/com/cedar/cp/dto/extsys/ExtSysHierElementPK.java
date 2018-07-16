/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysHierElementPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 199 */   private int mHashCode = -2147483648;
/*     */   int mExternalSystemId;
/*     */   String mCompanyVisId;
/*     */   String mLedgerVisId;
/*     */   String mDimensionVisId;
/*     */   String mHierarchyVisId;
/*     */   String mHierElementVisId;
/*     */ 
/*     */   public ExtSysHierElementPK(int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newDimensionVisId, String newHierarchyVisId, String newHierElementVisId)
/*     */   {
/*  28 */     this.mExternalSystemId = newExternalSystemId;
/*  29 */     this.mCompanyVisId = newCompanyVisId;
/*  30 */     this.mLedgerVisId = newLedgerVisId;
/*  31 */     this.mDimensionVisId = newDimensionVisId;
/*  32 */     this.mHierarchyVisId = newHierarchyVisId;
/*  33 */     this.mHierElementVisId = newHierElementVisId;
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
/*     */   public String getHierarchyVisId()
/*     */   {
/*  70 */     return this.mHierarchyVisId;
/*     */   }
/*     */ 
/*     */   public String getHierElementVisId()
/*     */   {
/*  77 */     return this.mHierElementVisId;
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
/*  91 */       this.mHashCode += this.mHierarchyVisId.hashCode();
/*  92 */       this.mHashCode += this.mHierElementVisId.hashCode();
/*     */     }
/*     */ 
/*  95 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 103 */     ExtSysHierElementPK other = null;
/*     */ 
/* 105 */     if ((obj instanceof ExtSysHierElementCK)) {
/* 106 */       other = ((ExtSysHierElementCK)obj).getExtSysHierElementPK();
/*     */     }
/* 108 */     else if ((obj instanceof ExtSysHierElementPK))
/* 109 */       other = (ExtSysHierElementPK)obj;
/*     */     else {
/* 111 */       return false;
/*     */     }
/* 113 */     boolean eq = true;
/*     */ 
/* 115 */     eq = (eq) && (this.mExternalSystemId == other.mExternalSystemId);
/* 116 */     eq = (eq) && (this.mCompanyVisId.equals(other.mCompanyVisId));
/* 117 */     eq = (eq) && (this.mLedgerVisId.equals(other.mLedgerVisId));
/* 118 */     eq = (eq) && (this.mDimensionVisId.equals(other.mDimensionVisId));
/* 119 */     eq = (eq) && (this.mHierarchyVisId.equals(other.mHierarchyVisId));
/* 120 */     eq = (eq) && (this.mHierElementVisId.equals(other.mHierElementVisId));
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
/* 139 */     sb.append(",HierarchyVisId=");
/* 140 */     sb.append(this.mHierarchyVisId);
/* 141 */     sb.append(",HierElementVisId=");
/* 142 */     sb.append(this.mHierElementVisId);
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
/* 161 */     sb.append(this.mHierarchyVisId);
/* 162 */     sb.append(",");
/* 163 */     sb.append(this.mHierElementVisId);
/* 164 */     return "ExtSysHierElementPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ExtSysHierElementPK getKeyFromTokens(String extKey)
/*     */   {
/* 169 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 171 */     if (extValues.length != 2) {
/* 172 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 174 */     if (!extValues[0].equals("ExtSysHierElementPK")) {
/* 175 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ExtSysHierElementPK|'");
/*     */     }
/* 177 */     extValues = extValues[1].split(",");
/*     */ 
/* 179 */     int i = 0;
/* 180 */     int pExternalSystemId = new Integer(extValues[(i++)]).intValue();
/* 181 */     String pCompanyVisId = new String(extValues[(i++)]);
/* 182 */     String pLedgerVisId = new String(extValues[(i++)]);
/* 183 */     String pDimensionVisId = new String(extValues[(i++)]);
/* 184 */     String pHierarchyVisId = new String(extValues[(i++)]);
/* 185 */     String pHierElementVisId = new String(extValues[(i++)]);
/* 186 */     return new ExtSysHierElementPK(pExternalSystemId, pCompanyVisId, pLedgerVisId, pDimensionVisId, pHierarchyVisId, pHierElementVisId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysHierElementPK
 * JD-Core Version:    0.6.0
 */