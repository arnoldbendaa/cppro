/*     */ package com.cedar.cp.dto.impexp;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ImpExpHdrCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected ImpExpHdrPK mImpExpHdrPK;
/*     */ 
/*     */   public ImpExpHdrCK(ImpExpHdrPK paramImpExpHdrPK)
/*     */   {
/*  26 */     this.mImpExpHdrPK = paramImpExpHdrPK;
/*     */   }
/*     */ 
/*     */   public ImpExpHdrPK getImpExpHdrPK()
/*     */   {
/*  34 */     return this.mImpExpHdrPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mImpExpHdrPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mImpExpHdrPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof ImpExpHdrPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof ImpExpHdrCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     ImpExpHdrCK other = (ImpExpHdrCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mImpExpHdrPK.equals(other.mImpExpHdrPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mImpExpHdrPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("ImpExpHdrCK|");
/*  92 */     sb.append(this.mImpExpHdrPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ImpExpHdrCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("ImpExpHdrCK", token[(i++)]);
/* 101 */     checkExpected("ImpExpHdrPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new ImpExpHdrCK(ImpExpHdrPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.impexp.ImpExpHdrCK
 * JD-Core Version:    0.6.0
 */