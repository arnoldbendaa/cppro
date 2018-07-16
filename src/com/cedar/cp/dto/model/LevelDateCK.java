/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class LevelDateCK extends BudgetCycleCK
/*     */   implements Serializable
/*     */ {
/*     */   protected LevelDatePK mLevelDatePK;
/*     */ 
/*     */   public LevelDateCK(ModelPK paramModelPK, BudgetCyclePK paramBudgetCyclePK, LevelDatePK paramLevelDatePK)
/*     */   {
/*  31 */     super(paramModelPK, paramBudgetCyclePK);
/*     */ 
/*  35 */     this.mLevelDatePK = paramLevelDatePK;
/*     */   }
/*     */ 
/*     */   public LevelDatePK getLevelDatePK()
/*     */   {
/*  43 */     return this.mLevelDatePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mLevelDatePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mLevelDatePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof LevelDatePK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof LevelDateCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     LevelDateCK other = (LevelDateCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  78 */     eq = (eq) && (this.mBudgetCyclePK.equals(other.mBudgetCyclePK));
/*  79 */     eq = (eq) && (this.mLevelDatePK.equals(other.mLevelDatePK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mLevelDatePK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("LevelDateCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mLevelDatePK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("LevelDateCK", token[(i++)]);
/* 115 */     checkExpected("ModelPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("BudgetCyclePK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("LevelDatePK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new LevelDateCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), BudgetCyclePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), LevelDatePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.LevelDateCK
 * JD-Core Version:    0.6.0
 */