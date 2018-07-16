/*     */ package com.cedar.cp.dto.currency;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CurrencyPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mCurrencyId;
/*     */ 
/*     */   public CurrencyPK(int newCurrencyId)
/*     */   {
/*  23 */     this.mCurrencyId = newCurrencyId;
/*     */   }
/*     */ 
/*     */   public int getCurrencyId()
/*     */   {
/*  32 */     return this.mCurrencyId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mCurrencyId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     CurrencyPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof CurrencyCK)) {
/*  56 */       other = ((CurrencyCK)obj).getCurrencyPK();
/*     */     }
/*  58 */     else if ((obj instanceof CurrencyPK))
/*  59 */       other = (CurrencyPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mCurrencyId == other.mCurrencyId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" CurrencyId=");
/*  77 */     sb.append(this.mCurrencyId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mCurrencyId);
/*  89 */     return "CurrencyPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static CurrencyPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("CurrencyPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'CurrencyPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pCurrencyId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new CurrencyPK(pCurrencyId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.currency.CurrencyPK
 * JD-Core Version:    0.6.0
 */