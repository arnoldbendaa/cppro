/*     */ package com.cedar.cp.dto.udeflookup;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class UdefLookupCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected UdefLookupPK mUdefLookupPK;
/*     */ 
/*     */   public UdefLookupCK(UdefLookupPK paramUdefLookupPK)
/*     */   {
/*  26 */     this.mUdefLookupPK = paramUdefLookupPK;
/*     */   }
/*     */ 
/*     */   public UdefLookupPK getUdefLookupPK()
/*     */   {
/*  34 */     return this.mUdefLookupPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mUdefLookupPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mUdefLookupPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof UdefLookupPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof UdefLookupCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     UdefLookupCK other = (UdefLookupCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mUdefLookupPK.equals(other.mUdefLookupPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mUdefLookupPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("UdefLookupCK|");
/*  92 */     sb.append(this.mUdefLookupPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static UdefLookupCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("UdefLookupCK", token[(i++)]);
/* 101 */     checkExpected("UdefLookupPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new UdefLookupCK(UdefLookupPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.udeflookup.UdefLookupCK
 * JD-Core Version:    0.6.0
 */