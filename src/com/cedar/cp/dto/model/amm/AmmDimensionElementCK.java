/*     */ package com.cedar.cp.dto.model.amm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AmmDimensionElementCK extends AmmDimensionCK
/*     */   implements Serializable
/*     */ {
/*     */   protected AmmDimensionElementPK mAmmDimensionElementPK;
/*     */ 
/*     */   public AmmDimensionElementCK(AmmModelPK paramAmmModelPK, AmmDimensionPK paramAmmDimensionPK, AmmDimensionElementPK paramAmmDimensionElementPK)
/*     */   {
/*  31 */     super(paramAmmModelPK, paramAmmDimensionPK);
/*     */ 
/*  35 */     this.mAmmDimensionElementPK = paramAmmDimensionElementPK;
/*     */   }
/*     */ 
/*     */   public AmmDimensionElementPK getAmmDimensionElementPK()
/*     */   {
/*  43 */     return this.mAmmDimensionElementPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mAmmDimensionElementPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mAmmDimensionElementPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof AmmDimensionElementPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof AmmDimensionElementCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     AmmDimensionElementCK other = (AmmDimensionElementCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mAmmModelPK.equals(other.mAmmModelPK));
/*  78 */     eq = (eq) && (this.mAmmDimensionPK.equals(other.mAmmDimensionPK));
/*  79 */     eq = (eq) && (this.mAmmDimensionElementPK.equals(other.mAmmDimensionElementPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mAmmDimensionElementPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("AmmDimensionElementCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mAmmDimensionElementPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static AmmModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("AmmDimensionElementCK", token[(i++)]);
/* 115 */     checkExpected("AmmModelPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("AmmDimensionPK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("AmmDimensionElementPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new AmmDimensionElementCK(AmmModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), AmmDimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), AmmDimensionElementPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.amm.AmmDimensionElementCK
 * JD-Core Version:    0.6.0
 */