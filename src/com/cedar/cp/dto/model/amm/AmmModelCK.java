/*     */ package com.cedar.cp.dto.model.amm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AmmModelCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected AmmModelPK mAmmModelPK;
/*     */ 
/*     */   public AmmModelCK(AmmModelPK paramAmmModelPK)
/*     */   {
/*  26 */     this.mAmmModelPK = paramAmmModelPK;
/*     */   }
/*     */ 
/*     */   public AmmModelPK getAmmModelPK()
/*     */   {
/*  34 */     return this.mAmmModelPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mAmmModelPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mAmmModelPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof AmmModelPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof AmmModelCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     AmmModelCK other = (AmmModelCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mAmmModelPK.equals(other.mAmmModelPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mAmmModelPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("AmmModelCK|");
/*  92 */     sb.append(this.mAmmModelPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static AmmModelCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("AmmModelCK", token[(i++)]);
/* 101 */     checkExpected("AmmModelPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new AmmModelCK(AmmModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.amm.AmmModelCK
 * JD-Core Version:    0.6.0
 */