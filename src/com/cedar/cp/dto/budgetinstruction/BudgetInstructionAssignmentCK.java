/*     */ package com.cedar.cp.dto.budgetinstruction;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetInstructionAssignmentCK extends BudgetInstructionCK
/*     */   implements Serializable
/*     */ {
/*     */   protected BudgetInstructionAssignmentPK mBudgetInstructionAssignmentPK;
/*     */ 
/*     */   public BudgetInstructionAssignmentCK(BudgetInstructionPK paramBudgetInstructionPK, BudgetInstructionAssignmentPK paramBudgetInstructionAssignmentPK)
/*     */   {
/*  29 */     super(paramBudgetInstructionPK);
/*     */ 
/*  32 */     this.mBudgetInstructionAssignmentPK = paramBudgetInstructionAssignmentPK;
/*     */   }
/*     */ 
/*     */   public BudgetInstructionAssignmentPK getBudgetInstructionAssignmentPK()
/*     */   {
/*  40 */     return this.mBudgetInstructionAssignmentPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mBudgetInstructionAssignmentPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mBudgetInstructionAssignmentPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof BudgetInstructionAssignmentPK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof BudgetInstructionAssignmentCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     BudgetInstructionAssignmentCK other = (BudgetInstructionAssignmentCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mBudgetInstructionPK.equals(other.mBudgetInstructionPK));
/*  75 */     eq = (eq) && (this.mBudgetInstructionAssignmentPK.equals(other.mBudgetInstructionAssignmentPK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mBudgetInstructionAssignmentPK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("BudgetInstructionAssignmentCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mBudgetInstructionAssignmentPK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static BudgetInstructionCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("BudgetInstructionAssignmentCK", token[(i++)]);
/* 111 */     checkExpected("BudgetInstructionPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("BudgetInstructionAssignmentPK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new BudgetInstructionAssignmentCK(BudgetInstructionPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), BudgetInstructionAssignmentPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentCK
 * JD-Core Version:    0.6.0
 */