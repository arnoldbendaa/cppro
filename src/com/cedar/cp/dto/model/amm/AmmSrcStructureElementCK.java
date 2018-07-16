/*     */ package com.cedar.cp.dto.model.amm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AmmSrcStructureElementCK extends AmmDimensionElementCK
/*     */   implements Serializable
/*     */ {
/*     */   protected AmmSrcStructureElementPK mAmmSrcStructureElementPK;
/*     */ 
/*     */   public AmmSrcStructureElementCK(AmmModelPK paramAmmModelPK, AmmDimensionPK paramAmmDimensionPK, AmmDimensionElementPK paramAmmDimensionElementPK, AmmSrcStructureElementPK paramAmmSrcStructureElementPK)
/*     */   {
/*  33 */     super(paramAmmModelPK, paramAmmDimensionPK, paramAmmDimensionElementPK);
/*     */ 
/*  38 */     this.mAmmSrcStructureElementPK = paramAmmSrcStructureElementPK;
/*     */   }
/*     */ 
/*     */   public AmmSrcStructureElementPK getAmmSrcStructureElementPK()
/*     */   {
/*  46 */     return this.mAmmSrcStructureElementPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  54 */     return this.mAmmSrcStructureElementPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  62 */     return this.mAmmSrcStructureElementPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  71 */     if ((obj instanceof AmmSrcStructureElementPK)) {
/*  72 */       return obj.equals(this);
/*     */     }
/*  74 */     if (!(obj instanceof AmmSrcStructureElementCK)) {
/*  75 */       return false;
/*     */     }
/*  77 */     AmmSrcStructureElementCK other = (AmmSrcStructureElementCK)obj;
/*  78 */     boolean eq = true;
/*     */ 
/*  80 */     eq = (eq) && (this.mAmmModelPK.equals(other.mAmmModelPK));
/*  81 */     eq = (eq) && (this.mAmmDimensionPK.equals(other.mAmmDimensionPK));
/*  82 */     eq = (eq) && (this.mAmmDimensionElementPK.equals(other.mAmmDimensionElementPK));
/*  83 */     eq = (eq) && (this.mAmmSrcStructureElementPK.equals(other.mAmmSrcStructureElementPK));
/*     */ 
/*  85 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  93 */     StringBuffer sb = new StringBuffer();
/*  94 */     sb.append(super.toString());
/*  95 */     sb.append("[");
/*  96 */     sb.append(this.mAmmSrcStructureElementPK);
/*  97 */     sb.append("]");
/*  98 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 106 */     StringBuffer sb = new StringBuffer();
/* 107 */     sb.append("AmmSrcStructureElementCK|");
/* 108 */     sb.append(super.getPK().toTokens());
/* 109 */     sb.append('|');
/* 110 */     sb.append(this.mAmmSrcStructureElementPK.toTokens());
/* 111 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static AmmModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 116 */     String[] token = extKey.split("[|]");
/* 117 */     int i = 0;
/* 118 */     checkExpected("AmmSrcStructureElementCK", token[(i++)]);
/* 119 */     checkExpected("AmmModelPK", token[(i++)]);
/* 120 */     i++;
/* 121 */     checkExpected("AmmDimensionPK", token[(i++)]);
/* 122 */     i++;
/* 123 */     checkExpected("AmmDimensionElementPK", token[(i++)]);
/* 124 */     i++;
/* 125 */     checkExpected("AmmSrcStructureElementPK", token[(i++)]);
/* 126 */     i = 1;
/* 127 */     return new AmmSrcStructureElementCK(AmmModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), AmmDimensionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), AmmDimensionElementPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), AmmSrcStructureElementPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 137 */     if (!expected.equals(found))
/* 138 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.amm.AmmSrcStructureElementCK
 * JD-Core Version:    0.6.0
 */