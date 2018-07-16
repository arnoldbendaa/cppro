/*     */ package com.cedar.cp.dto.model.amm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AmmFinanceCubeCK extends AmmModelCK
/*     */   implements Serializable
/*     */ {
/*     */   protected AmmFinanceCubePK mAmmFinanceCubePK;
/*     */ 
/*     */   public AmmFinanceCubeCK(AmmModelPK paramAmmModelPK, AmmFinanceCubePK paramAmmFinanceCubePK)
/*     */   {
/*  29 */     super(paramAmmModelPK);
/*     */ 
/*  32 */     this.mAmmFinanceCubePK = paramAmmFinanceCubePK;
/*     */   }
/*     */ 
/*     */   public AmmFinanceCubePK getAmmFinanceCubePK()
/*     */   {
/*  40 */     return this.mAmmFinanceCubePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mAmmFinanceCubePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mAmmFinanceCubePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof AmmFinanceCubePK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof AmmFinanceCubeCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     AmmFinanceCubeCK other = (AmmFinanceCubeCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mAmmModelPK.equals(other.mAmmModelPK));
/*  75 */     eq = (eq) && (this.mAmmFinanceCubePK.equals(other.mAmmFinanceCubePK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mAmmFinanceCubePK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("AmmFinanceCubeCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mAmmFinanceCubePK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static AmmModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("AmmFinanceCubeCK", token[(i++)]);
/* 111 */     checkExpected("AmmModelPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("AmmFinanceCubePK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new AmmFinanceCubeCK(AmmModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), AmmFinanceCubePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.amm.AmmFinanceCubeCK
 * JD-Core Version:    0.6.0
 */