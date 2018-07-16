/*     */ package com.cedar.cp.dto.perftest;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class PerfTestCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected PerfTestPK mPerfTestPK;
/*     */ 
/*     */   public PerfTestCK(PerfTestPK paramPerfTestPK)
/*     */   {
/*  26 */     this.mPerfTestPK = paramPerfTestPK;
/*     */   }
/*     */ 
/*     */   public PerfTestPK getPerfTestPK()
/*     */   {
/*  34 */     return this.mPerfTestPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mPerfTestPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mPerfTestPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof PerfTestPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof PerfTestCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     PerfTestCK other = (PerfTestCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mPerfTestPK.equals(other.mPerfTestPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mPerfTestPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("PerfTestCK|");
/*  92 */     sb.append(this.mPerfTestPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static PerfTestCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("PerfTestCK", token[(i++)]);
/* 101 */     checkExpected("PerfTestPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new PerfTestCK(PerfTestPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.perftest.PerfTestCK
 * JD-Core Version:    0.6.0
 */