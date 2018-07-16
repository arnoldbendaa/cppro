/*     */ package com.cedar.cp.dto.report.destination.external;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExternalDestinationCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected ExternalDestinationPK mExternalDestinationPK;
/*     */ 
/*     */   public ExternalDestinationCK(ExternalDestinationPK paramExternalDestinationPK)
/*     */   {
/*  26 */     this.mExternalDestinationPK = paramExternalDestinationPK;
/*     */   }
/*     */ 
/*     */   public ExternalDestinationPK getExternalDestinationPK()
/*     */   {
/*  34 */     return this.mExternalDestinationPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mExternalDestinationPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mExternalDestinationPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof ExternalDestinationPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof ExternalDestinationCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     ExternalDestinationCK other = (ExternalDestinationCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mExternalDestinationPK.equals(other.mExternalDestinationPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mExternalDestinationPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("ExternalDestinationCK|");
/*  92 */     sb.append(this.mExternalDestinationPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalDestinationCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("ExternalDestinationCK", token[(i++)]);
/* 101 */     checkExpected("ExternalDestinationPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new ExternalDestinationCK(ExternalDestinationPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.destination.external.ExternalDestinationCK
 * JD-Core Version:    0.6.0
 */