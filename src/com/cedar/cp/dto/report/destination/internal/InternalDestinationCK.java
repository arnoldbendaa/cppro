/*     */ package com.cedar.cp.dto.report.destination.internal;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class InternalDestinationCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected InternalDestinationPK mInternalDestinationPK;
/*     */ 
/*     */   public InternalDestinationCK(InternalDestinationPK paramInternalDestinationPK)
/*     */   {
/*  26 */     this.mInternalDestinationPK = paramInternalDestinationPK;
/*     */   }
/*     */ 
/*     */   public InternalDestinationPK getInternalDestinationPK()
/*     */   {
/*  34 */     return this.mInternalDestinationPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mInternalDestinationPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mInternalDestinationPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof InternalDestinationPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof InternalDestinationCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     InternalDestinationCK other = (InternalDestinationCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mInternalDestinationPK.equals(other.mInternalDestinationPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mInternalDestinationPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("InternalDestinationCK|");
/*  92 */     sb.append(this.mInternalDestinationPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static InternalDestinationCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("InternalDestinationCK", token[(i++)]);
/* 101 */     checkExpected("InternalDestinationPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new InternalDestinationCK(InternalDestinationPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.destination.internal.InternalDestinationCK
 * JD-Core Version:    0.6.0
 */