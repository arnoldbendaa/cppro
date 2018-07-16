/*     */ package com.cedar.cp.dto.udeflookup;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class UdefLookupColumnDefCK extends UdefLookupCK
/*     */   implements Serializable
/*     */ {
/*     */   protected UdefLookupColumnDefPK mUdefLookupColumnDefPK;
/*     */ 
/*     */   public UdefLookupColumnDefCK(UdefLookupPK paramUdefLookupPK, UdefLookupColumnDefPK paramUdefLookupColumnDefPK)
/*     */   {
/*  29 */     super(paramUdefLookupPK);
/*     */ 
/*  32 */     this.mUdefLookupColumnDefPK = paramUdefLookupColumnDefPK;
/*     */   }
/*     */ 
/*     */   public UdefLookupColumnDefPK getUdefLookupColumnDefPK()
/*     */   {
/*  40 */     return this.mUdefLookupColumnDefPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mUdefLookupColumnDefPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mUdefLookupColumnDefPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof UdefLookupColumnDefPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof UdefLookupColumnDefCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     UdefLookupColumnDefCK other = (UdefLookupColumnDefCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mUdefLookupPK.equals(other.mUdefLookupPK));
/*  75 */     eq = (eq) && (this.mUdefLookupColumnDefPK.equals(other.mUdefLookupColumnDefPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mUdefLookupColumnDefPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("UdefLookupColumnDefCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mUdefLookupColumnDefPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static UdefLookupCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("UdefLookupColumnDefCK", token[(i++)]);
/* 111 */     checkExpected("UdefLookupPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("UdefLookupColumnDefPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new UdefLookupColumnDefCK(UdefLookupPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), UdefLookupColumnDefPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.udeflookup.UdefLookupColumnDefCK
 * JD-Core Version:    0.6.0
 */