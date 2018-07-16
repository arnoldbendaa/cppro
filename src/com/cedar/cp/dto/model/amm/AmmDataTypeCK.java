/*     */ package com.cedar.cp.dto.model.amm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AmmDataTypeCK extends AmmFinanceCubeCK
/*     */   implements Serializable
/*     */ {
/*     */   protected AmmDataTypePK mAmmDataTypePK;
/*     */ 
/*     */   public AmmDataTypeCK(AmmModelPK paramAmmModelPK, AmmFinanceCubePK paramAmmFinanceCubePK, AmmDataTypePK paramAmmDataTypePK)
/*     */   {
/*  31 */     super(paramAmmModelPK, paramAmmFinanceCubePK);
/*     */ 
/*  35 */     this.mAmmDataTypePK = paramAmmDataTypePK;
/*     */   }
/*     */ 
/*     */   public AmmDataTypePK getAmmDataTypePK()
/*     */   {
/*  43 */     return this.mAmmDataTypePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mAmmDataTypePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mAmmDataTypePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof AmmDataTypePK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof AmmDataTypeCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     AmmDataTypeCK other = (AmmDataTypeCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mAmmModelPK.equals(other.mAmmModelPK));
/*  78 */     eq = (eq) && (this.mAmmFinanceCubePK.equals(other.mAmmFinanceCubePK));
/*  79 */     eq = (eq) && (this.mAmmDataTypePK.equals(other.mAmmDataTypePK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mAmmDataTypePK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("AmmDataTypeCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mAmmDataTypePK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static AmmModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("AmmDataTypeCK", token[(i++)]);
/* 115 */     checkExpected("AmmModelPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("AmmFinanceCubePK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("AmmDataTypePK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new AmmDataTypeCK(AmmModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), AmmFinanceCubePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), AmmDataTypePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.amm.AmmDataTypeCK
 * JD-Core Version:    0.6.0
 */