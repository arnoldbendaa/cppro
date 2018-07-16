/*     */ package com.cedar.cp.dto.report.distribution;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DistributionCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected DistributionPK mDistributionPK;
/*     */ 
/*     */   public DistributionCK(DistributionPK paramDistributionPK)
/*     */   {
/*  26 */     this.mDistributionPK = paramDistributionPK;
/*     */   }
/*     */ 
/*     */   public DistributionPK getDistributionPK()
/*     */   {
/*  34 */     return this.mDistributionPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mDistributionPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mDistributionPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof DistributionPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof DistributionCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     DistributionCK other = (DistributionCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mDistributionPK.equals(other.mDistributionPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mDistributionPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("DistributionCK|");
/*  92 */     sb.append(this.mDistributionPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static DistributionCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("DistributionCK", token[(i++)]);
/* 101 */     checkExpected("DistributionPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new DistributionCK(DistributionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.distribution.DistributionCK
 * JD-Core Version:    0.6.0
 */