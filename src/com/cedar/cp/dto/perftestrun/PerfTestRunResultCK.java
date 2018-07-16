/*     */ package com.cedar.cp.dto.perftestrun;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class PerfTestRunResultCK extends PerfTestRunCK
/*     */   implements Serializable
/*     */ {
/*     */   protected PerfTestRunResultPK mPerfTestRunResultPK;
/*     */ 
/*     */   public PerfTestRunResultCK(PerfTestRunPK paramPerfTestRunPK, PerfTestRunResultPK paramPerfTestRunResultPK)
/*     */   {
/*  29 */     super(paramPerfTestRunPK);
/*     */ 
/*  32 */     this.mPerfTestRunResultPK = paramPerfTestRunResultPK;
/*     */   }
/*     */ 
/*     */   public PerfTestRunResultPK getPerfTestRunResultPK()
/*     */   {
/*  40 */     return this.mPerfTestRunResultPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mPerfTestRunResultPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mPerfTestRunResultPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof PerfTestRunResultPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof PerfTestRunResultCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     PerfTestRunResultCK other = (PerfTestRunResultCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mPerfTestRunPK.equals(other.mPerfTestRunPK));
/*  75 */     eq = (eq) && (this.mPerfTestRunResultPK.equals(other.mPerfTestRunResultPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mPerfTestRunResultPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("PerfTestRunResultCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mPerfTestRunResultPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static PerfTestRunCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("PerfTestRunResultCK", token[(i++)]);
/* 111 */     checkExpected("PerfTestRunPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("PerfTestRunResultPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new PerfTestRunResultCK(PerfTestRunPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), PerfTestRunResultPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.perftestrun.PerfTestRunResultCK
 * JD-Core Version:    0.6.0
 */