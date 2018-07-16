/*     */ package com.cedar.cp.dto.currency;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CurrencyCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected CurrencyPK mCurrencyPK;
/*     */ 
/*     */   public CurrencyCK(CurrencyPK paramCurrencyPK)
/*     */   {
/*  26 */     this.mCurrencyPK = paramCurrencyPK;
/*     */   }
/*     */ 
/*     */   public CurrencyPK getCurrencyPK()
/*     */   {
/*  34 */     return this.mCurrencyPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mCurrencyPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mCurrencyPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof CurrencyPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof CurrencyCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     CurrencyCK other = (CurrencyCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mCurrencyPK.equals(other.mCurrencyPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mCurrencyPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("CurrencyCK|");
/*  92 */     sb.append(this.mCurrencyPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static CurrencyCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("CurrencyCK", token[(i++)]);
/* 101 */     checkExpected("CurrencyPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new CurrencyCK(CurrencyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.currency.CurrencyCK
 * JD-Core Version:    0.6.0
 */