/*     */ package com.cedar.cp.dto.perftestrun;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class PerfTestRunCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected PerfTestRunPK mPerfTestRunPK;
/*     */ 
/*     */   public PerfTestRunCK(PerfTestRunPK paramPerfTestRunPK)
/*     */   {
/*  26 */     this.mPerfTestRunPK = paramPerfTestRunPK;
/*     */   }
/*     */ 
/*     */   public PerfTestRunPK getPerfTestRunPK()
/*     */   {
/*  34 */     return this.mPerfTestRunPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mPerfTestRunPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mPerfTestRunPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof PerfTestRunPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof PerfTestRunCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     PerfTestRunCK other = (PerfTestRunCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mPerfTestRunPK.equals(other.mPerfTestRunPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mPerfTestRunPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("PerfTestRunCK|");
/*  92 */     sb.append(this.mPerfTestRunPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static PerfTestRunCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("PerfTestRunCK", token[(i++)]);
/* 101 */     checkExpected("PerfTestRunPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new PerfTestRunCK(PerfTestRunPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.perftestrun.PerfTestRunCK
 * JD-Core Version:    0.6.0
 */