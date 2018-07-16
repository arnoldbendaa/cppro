/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SecurityRangeCK extends DimensionCK
/*     */   implements Serializable
/*     */ {
/*     */   protected SecurityRangePK mSecurityRangePK;
/*     */ 
/*     */   public SecurityRangeCK(DimensionPK paramDimensionPK, SecurityRangePK paramSecurityRangePK)
/*     */   {
/*  29 */     super(paramDimensionPK);
/*     */ 
/*  32 */     this.mSecurityRangePK = paramSecurityRangePK;
/*     */   }
/*     */ 
/*     */   public SecurityRangePK getSecurityRangePK()
/*     */   {
/*  40 */     return this.mSecurityRangePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mSecurityRangePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mSecurityRangePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof SecurityRangePK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof SecurityRangeCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     SecurityRangeCK other = (SecurityRangeCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mDimensionPK.equals(other.mDimensionPK));
/*  75 */     eq = (eq) && (this.mSecurityRangePK.equals(other.mSecurityRangePK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mSecurityRangePK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("SecurityRangeCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mSecurityRangePK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static DimensionCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("SecurityRangeCK", token[(i++)]);
/* 111 */     checkExpected("DimensionPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("SecurityRangePK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new SecurityRangeCK(DimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), SecurityRangePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.SecurityRangeCK
 * JD-Core Version:    0.6.0
 */