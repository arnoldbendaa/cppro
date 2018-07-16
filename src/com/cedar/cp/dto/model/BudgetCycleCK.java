/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetCycleCK extends ModelCK
/*     */   implements Serializable
/*     */ {
/*     */   protected BudgetCyclePK mBudgetCyclePK;
/*     */ 
/*     */   public BudgetCycleCK(ModelPK paramModelPK, BudgetCyclePK paramBudgetCyclePK)
/*     */   {
/*  29 */     super(paramModelPK);
/*     */ 
/*  32 */     this.mBudgetCyclePK = paramBudgetCyclePK;
/*     */   }
/*     */ 
/*     */   public BudgetCyclePK getBudgetCyclePK()
/*     */   {
/*  40 */     return this.mBudgetCyclePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mBudgetCyclePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mBudgetCyclePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof BudgetCyclePK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof BudgetCycleCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     BudgetCycleCK other = (BudgetCycleCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  75 */     eq = (eq) && (this.mBudgetCyclePK.equals(other.mBudgetCyclePK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mBudgetCyclePK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("BudgetCycleCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mBudgetCyclePK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("BudgetCycleCK", token[(i++)]);
/* 111 */     checkExpected("ModelPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("BudgetCyclePK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new BudgetCycleCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), BudgetCyclePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.BudgetCycleCK
 * JD-Core Version:    0.6.0
 */