/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExternalSystemCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected ExternalSystemPK mExternalSystemPK;
/*     */ 
/*     */   public ExternalSystemCK(ExternalSystemPK paramExternalSystemPK)
/*     */   {
/*  26 */     this.mExternalSystemPK = paramExternalSystemPK;
/*     */   }
/*     */ 
/*     */   public ExternalSystemPK getExternalSystemPK()
/*     */   {
/*  34 */     return this.mExternalSystemPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mExternalSystemPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mExternalSystemPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof ExternalSystemPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof ExternalSystemCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     ExternalSystemCK other = (ExternalSystemCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mExternalSystemPK.equals(other.mExternalSystemPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mExternalSystemPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("ExternalSystemCK|");
/*  92 */     sb.append(this.mExternalSystemPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ExternalSystemCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("ExternalSystemCK", token[(i++)]);
/* 101 */     checkExpected("ExternalSystemPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new ExternalSystemCK(ExternalSystemPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExternalSystemCK
 * JD-Core Version:    0.6.0
 */