/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SecurityRangeRowCK extends SecurityRangeCK
/*     */   implements Serializable
/*     */ {
/*     */   protected SecurityRangeRowPK mSecurityRangeRowPK;
/*     */ 
/*     */   public SecurityRangeRowCK(DimensionPK paramDimensionPK, SecurityRangePK paramSecurityRangePK, SecurityRangeRowPK paramSecurityRangeRowPK)
/*     */   {
/*  31 */     super(paramDimensionPK, paramSecurityRangePK);
/*     */ 
/*  35 */     this.mSecurityRangeRowPK = paramSecurityRangeRowPK;
/*     */   }
/*     */ 
/*     */   public SecurityRangeRowPK getSecurityRangeRowPK()
/*     */   {
/*  43 */     return this.mSecurityRangeRowPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mSecurityRangeRowPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mSecurityRangeRowPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof SecurityRangeRowPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof SecurityRangeRowCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     SecurityRangeRowCK other = (SecurityRangeRowCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mDimensionPK.equals(other.mDimensionPK));
/*  78 */     eq = (eq) && (this.mSecurityRangePK.equals(other.mSecurityRangePK));
/*  79 */     eq = (eq) && (this.mSecurityRangeRowPK.equals(other.mSecurityRangeRowPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mSecurityRangeRowPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("SecurityRangeRowCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mSecurityRangeRowPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static DimensionCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("SecurityRangeRowCK", token[(i++)]);
/* 115 */     checkExpected("DimensionPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("SecurityRangePK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("SecurityRangeRowPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new SecurityRangeRowCK(DimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), SecurityRangePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), SecurityRangeRowPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.SecurityRangeRowCK
 * JD-Core Version:    0.6.0
 */