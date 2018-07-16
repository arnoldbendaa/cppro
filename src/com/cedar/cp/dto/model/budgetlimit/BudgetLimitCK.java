/*     */ package com.cedar.cp.dto.model.budgetlimit;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import com.cedar.cp.dto.model.FinanceCubeCK;
/*     */ import com.cedar.cp.dto.model.FinanceCubePK;
/*     */ import com.cedar.cp.dto.model.ModelCK;
/*     */ import com.cedar.cp.dto.model.ModelPK;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetLimitCK extends FinanceCubeCK
/*     */   implements Serializable
/*     */ {
/*     */   protected BudgetLimitPK mBudgetLimitPK;
/*     */ 
/*     */   public BudgetLimitCK(ModelPK paramModelPK, FinanceCubePK paramFinanceCubePK, BudgetLimitPK paramBudgetLimitPK)
/*     */   {
/*  32 */     super(paramModelPK, paramFinanceCubePK);
/*     */ 
/*  36 */     this.mBudgetLimitPK = paramBudgetLimitPK;
/*     */   }
/*     */ 
/*     */   public BudgetLimitPK getBudgetLimitPK()
/*     */   {
/*  44 */     return this.mBudgetLimitPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  52 */     return this.mBudgetLimitPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  60 */     return this.mBudgetLimitPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  69 */     if ((obj instanceof BudgetLimitPK)) {
/*  70 */       return obj.equals(this);
/*     */     }
/*  72 */     if (!(obj instanceof BudgetLimitCK)) {
/*  73 */       return false;
/*     */     }
/*  75 */     BudgetLimitCK other = (BudgetLimitCK)obj;
/*  76 */     boolean eq = true;
/*     */ 
/*  78 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  79 */     eq = (eq) && (this.mFinanceCubePK.equals(other.mFinanceCubePK));
/*  80 */     eq = (eq) && (this.mBudgetLimitPK.equals(other.mBudgetLimitPK));
/*     */ 
/*  82 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append(super.toString());
/*  92 */     sb.append("[");
/*  93 */     sb.append(this.mBudgetLimitPK);
/*  94 */     sb.append("]");
/*  95 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 103 */     StringBuffer sb = new StringBuffer();
/* 104 */     sb.append("BudgetLimitCK|");
/* 105 */     sb.append(super.getPK().toTokens());
/* 106 */     sb.append('|');
/* 107 */     sb.append(this.mBudgetLimitPK.toTokens());
/* 108 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 113 */     String[] token = extKey.split("[|]");
/* 114 */     int i = 0;
/* 115 */     checkExpected("BudgetLimitCK", token[(i++)]);
/* 116 */     checkExpected("ModelPK", token[(i++)]);
/* 117 */     i++;
/* 118 */     checkExpected("FinanceCubePK", token[(i++)]);
/* 119 */     i++;
/* 120 */     checkExpected("BudgetLimitPK", token[(i++)]);
/* 121 */     i = 1;
/* 122 */     return new BudgetLimitCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), FinanceCubePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), BudgetLimitPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 131 */     if (!expected.equals(found))
/* 132 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.budgetlimit.BudgetLimitCK
 * JD-Core Version:    0.6.0
 */