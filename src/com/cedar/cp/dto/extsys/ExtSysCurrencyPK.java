/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExtSysCurrencyPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 165 */   private int mHashCode = -2147483648;
/*     */   int mExternalSystemId;
/*     */   String mCompanyVisId;
/*     */   String mLedgerVisId;
/*     */   String mCurrencyVisId;
/*     */ 
/*     */   public ExtSysCurrencyPK(int newExternalSystemId, String newCompanyVisId, String newLedgerVisId, String newCurrencyVisId)
/*     */   {
/*  26 */     this.mExternalSystemId = newExternalSystemId;
/*  27 */     this.mCompanyVisId = newCompanyVisId;
/*  28 */     this.mLedgerVisId = newLedgerVisId;
/*  29 */     this.mCurrencyVisId = newCurrencyVisId;
/*     */   }
/*     */ 
/*     */   public int getExternalSystemId()
/*     */   {
/*  38 */     return this.mExternalSystemId;
/*     */   }
/*     */ 
/*     */   public String getCompanyVisId()
/*     */   {
/*  45 */     return this.mCompanyVisId;
/*     */   }
/*     */ 
/*     */   public String getLedgerVisId()
/*     */   {
/*  52 */     return this.mLedgerVisId;
/*     */   }
/*     */ 
/*     */   public String getCurrencyVisId()
/*     */   {
/*  59 */     return this.mCurrencyVisId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  67 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  69 */       this.mHashCode += String.valueOf(this.mExternalSystemId).hashCode();
/*  70 */       this.mHashCode += this.mCompanyVisId.hashCode();
/*  71 */       this.mHashCode += this.mLedgerVisId.hashCode();
/*  72 */       this.mHashCode += this.mCurrencyVisId.hashCode();
/*     */     }
/*     */ 
/*  75 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  83 */     ExtSysCurrencyPK other = null;
/*     */ 
/*  85 */     if ((obj instanceof ExtSysCurrencyCK)) {
/*  86 */       other = ((ExtSysCurrencyCK)obj).getExtSysCurrencyPK();
/*     */     }
/*  88 */     else if ((obj instanceof ExtSysCurrencyPK))
/*  89 */       other = (ExtSysCurrencyPK)obj;
/*     */     else {
/*  91 */       return false;
/*     */     }
/*  93 */     boolean eq = true;
/*     */ 
/*  95 */     eq = (eq) && (this.mExternalSystemId == other.mExternalSystemId);
/*  96 */     eq = (eq) && (this.mCompanyVisId.equals(other.mCompanyVisId));
/*  97 */     eq = (eq) && (this.mLedgerVisId.equals(other.mLedgerVisId));
/*  98 */     eq = (eq) && (this.mCurrencyVisId.equals(other.mCurrencyVisId));
/*     */ 
/* 100 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 108 */     StringBuffer sb = new StringBuffer();
/* 109 */     sb.append(" ExternalSystemId=");
/* 110 */     sb.append(this.mExternalSystemId);
/* 111 */     sb.append(",CompanyVisId=");
/* 112 */     sb.append(this.mCompanyVisId);
/* 113 */     sb.append(",LedgerVisId=");
/* 114 */     sb.append(this.mLedgerVisId);
/* 115 */     sb.append(",CurrencyVisId=");
/* 116 */     sb.append(this.mCurrencyVisId);
/* 117 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 125 */     StringBuffer sb = new StringBuffer();
/* 126 */     sb.append(" ");
/* 127 */     sb.append(this.mExternalSystemId);
/* 128 */     sb.append(",");
/* 129 */     sb.append(this.mCompanyVisId);
/* 130 */     sb.append(",");
/* 131 */     sb.append(this.mLedgerVisId);
/* 132 */     sb.append(",");
/* 133 */     sb.append(this.mCurrencyVisId);
/* 134 */     return "ExtSysCurrencyPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ExtSysCurrencyPK getKeyFromTokens(String extKey)
/*     */   {
/* 139 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 141 */     if (extValues.length != 2) {
/* 142 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 144 */     if (!extValues[0].equals("ExtSysCurrencyPK")) {
/* 145 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ExtSysCurrencyPK|'");
/*     */     }
/* 147 */     extValues = extValues[1].split(",");
/*     */ 
/* 149 */     int i = 0;
/* 150 */     int pExternalSystemId = new Integer(extValues[(i++)]).intValue();
/* 151 */     String pCompanyVisId = new String(extValues[(i++)]);
/* 152 */     String pLedgerVisId = new String(extValues[(i++)]);
/* 153 */     String pCurrencyVisId = new String(extValues[(i++)]);
/* 154 */     return new ExtSysCurrencyPK(pExternalSystemId, pCompanyVisId, pLedgerVisId, pCurrencyVisId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExtSysCurrencyPK
 * JD-Core Version:    0.6.0
 */