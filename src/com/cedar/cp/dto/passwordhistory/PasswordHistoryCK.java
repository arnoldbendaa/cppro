/*     */ package com.cedar.cp.dto.passwordhistory;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class PasswordHistoryCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected PasswordHistoryPK mPasswordHistoryPK;
/*     */ 
/*     */   public PasswordHistoryCK(PasswordHistoryPK paramPasswordHistoryPK)
/*     */   {
/*  26 */     this.mPasswordHistoryPK = paramPasswordHistoryPK;
/*     */   }
/*     */ 
/*     */   public PasswordHistoryPK getPasswordHistoryPK()
/*     */   {
/*  34 */     return this.mPasswordHistoryPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mPasswordHistoryPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mPasswordHistoryPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof PasswordHistoryPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof PasswordHistoryCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     PasswordHistoryCK other = (PasswordHistoryCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mPasswordHistoryPK.equals(other.mPasswordHistoryPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mPasswordHistoryPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("PasswordHistoryCK|");
/*  92 */     sb.append(this.mPasswordHistoryPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static PasswordHistoryCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("PasswordHistoryCK", token[(i++)]);
/* 101 */     checkExpected("PasswordHistoryPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new PasswordHistoryCK(PasswordHistoryPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.passwordhistory.PasswordHistoryCK
 * JD-Core Version:    0.6.0
 */