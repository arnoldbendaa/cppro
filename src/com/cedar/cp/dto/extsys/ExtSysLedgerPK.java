/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysLedgerPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 148 */   private int mHashCode = -2147483648;
/*     */   int mExternalSystemId;
/*     */   String mCompanyVisId;
/*     */   String mLedgerVisId;
/*     */ 
/*     */   public ExtSysLedgerPK(int newExternalSystemId, String newCompanyVisId, String newLedgerVisId)
/*     */   {
/*  25 */     this.mExternalSystemId = newExternalSystemId;
/*  26 */     this.mCompanyVisId = newCompanyVisId;
/*  27 */     this.mLedgerVisId = newLedgerVisId;
/*     */   }
/*     */ 
/*     */   public int getExternalSystemId()
/*     */   {
/*  36 */     return this.mExternalSystemId;
/*     */   }
/*     */ 
/*     */   public String getCompanyVisId()
/*     */   {
/*  43 */     return this.mCompanyVisId;
/*     */   }
/*     */ 
/*     */   public String getLedgerVisId()
/*     */   {
/*  50 */     return this.mLedgerVisId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  58 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  60 */       this.mHashCode += String.valueOf(this.mExternalSystemId).hashCode();
/*  61 */       this.mHashCode += this.mCompanyVisId.hashCode();
/*  62 */       this.mHashCode += this.mLedgerVisId.hashCode();
/*     */     }
/*     */ 
/*  65 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  73 */     ExtSysLedgerPK other = null;
/*     */ 
/*  75 */     if ((obj instanceof ExtSysLedgerCK)) {
/*  76 */       other = ((ExtSysLedgerCK)obj).getExtSysLedgerPK();
/*     */     }
/*  78 */     else if ((obj instanceof ExtSysLedgerPK))
/*  79 */       other = (ExtSysLedgerPK)obj;
/*     */     else {
/*  81 */       return false;
/*     */     }
/*  83 */     boolean eq = true;
/*     */ 
/*  85 */     eq = (eq) && (this.mExternalSystemId == other.mExternalSystemId);
/*  86 */     eq = (eq) && (this.mCompanyVisId.equals(other.mCompanyVisId));
/*  87 */     eq = (eq) && (this.mLedgerVisId.equals(other.mLedgerVisId));
/*     */ 
/*  89 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  97 */     StringBuffer sb = new StringBuffer();
/*  98 */     sb.append(" ExternalSystemId=");
/*  99 */     sb.append(this.mExternalSystemId);
/* 100 */     sb.append(",CompanyVisId=");
/* 101 */     sb.append(this.mCompanyVisId);
/* 102 */     sb.append(",LedgerVisId=");
/* 103 */     sb.append(this.mLedgerVisId);
/* 104 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 112 */     StringBuffer sb = new StringBuffer();
/* 113 */     sb.append(" ");
/* 114 */     sb.append(this.mExternalSystemId);
/* 115 */     sb.append(",");
/* 116 */     sb.append(this.mCompanyVisId);
/* 117 */     sb.append(",");
/* 118 */     sb.append(this.mLedgerVisId);
/* 119 */     return "ExtSysLedgerPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ExtSysLedgerPK getKeyFromTokens(String extKey)
/*     */   {
/* 124 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 126 */     if (extValues.length != 2) {
/* 127 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 129 */     if (!extValues[0].equals("ExtSysLedgerPK")) {
/* 130 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ExtSysLedgerPK|'");
/*     */     }
/* 132 */     extValues = extValues[1].split(",");
/*     */ 
/* 134 */     int i = 0;
/* 135 */     int pExternalSystemId = new Integer(extValues[(i++)]).intValue();
/* 136 */     String pCompanyVisId = new String(extValues[(i++)]);
/* 137 */     String pLedgerVisId = new String(extValues[(i++)]);
/* 138 */     return new ExtSysLedgerPK(pExternalSystemId, pCompanyVisId, pLedgerVisId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysLedgerPK
 * JD-Core Version:    0.6.0
 */