/*     */ package com.cedar.cp.dto.model.amm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AmmDimensionCK extends AmmModelCK
/*     */   implements Serializable
/*     */ {
/*     */   protected AmmDimensionPK mAmmDimensionPK;
/*     */ 
/*     */   public AmmDimensionCK(AmmModelPK paramAmmModelPK, AmmDimensionPK paramAmmDimensionPK)
/*     */   {
/*  29 */     super(paramAmmModelPK);
/*     */ 
/*  32 */     this.mAmmDimensionPK = paramAmmDimensionPK;
/*     */   }
/*     */ 
/*     */   public AmmDimensionPK getAmmDimensionPK()
/*     */   {
/*  40 */     return this.mAmmDimensionPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mAmmDimensionPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mAmmDimensionPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof AmmDimensionPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof AmmDimensionCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     AmmDimensionCK other = (AmmDimensionCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mAmmModelPK.equals(other.mAmmModelPK));
/*  75 */     eq = (eq) && (this.mAmmDimensionPK.equals(other.mAmmDimensionPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mAmmDimensionPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("AmmDimensionCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mAmmDimensionPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static AmmModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("AmmDimensionCK", token[(i++)]);
/* 111 */     checkExpected("AmmModelPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("AmmDimensionPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new AmmDimensionCK(AmmModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), AmmDimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.amm.AmmDimensionCK
 * JD-Core Version:    0.6.0
 */