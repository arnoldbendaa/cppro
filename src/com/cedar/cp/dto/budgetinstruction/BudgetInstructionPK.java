/*     */ package com.cedar.cp.dto.budgetinstruction;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetInstructionPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mBudgetInstructionId;
/*     */ 
/*     */   public BudgetInstructionPK(int newBudgetInstructionId)
/*     */   {
/*  23 */     this.mBudgetInstructionId = newBudgetInstructionId;
/*     */   }
/*     */ 
/*     */   public int getBudgetInstructionId()
/*     */   {
/*  32 */     return this.mBudgetInstructionId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mBudgetInstructionId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     BudgetInstructionPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof BudgetInstructionCK)) {
/*  56 */       other = ((BudgetInstructionCK)obj).getBudgetInstructionPK();
/*     */     }
/*  58 */     else if ((obj instanceof BudgetInstructionPK))
/*  59 */       other = (BudgetInstructionPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mBudgetInstructionId == other.mBudgetInstructionId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" BudgetInstructionId=");
/*  77 */     sb.append(this.mBudgetInstructionId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mBudgetInstructionId);
/*  89 */     return "BudgetInstructionPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static BudgetInstructionPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("BudgetInstructionPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'BudgetInstructionPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pBudgetInstructionId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new BudgetInstructionPK(pBudgetInstructionId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK
 * JD-Core Version:    0.6.0
 */