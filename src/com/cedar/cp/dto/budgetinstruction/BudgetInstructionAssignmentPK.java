/*     */ package com.cedar.cp.dto.budgetinstruction;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetInstructionAssignmentPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mAssignmentId;
/*     */ 
/*     */   public BudgetInstructionAssignmentPK(int newAssignmentId)
/*     */   {
/*  23 */     this.mAssignmentId = newAssignmentId;
/*     */   }
/*     */ 
/*     */   public int getAssignmentId()
/*     */   {
/*  32 */     return this.mAssignmentId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mAssignmentId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     BudgetInstructionAssignmentPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof BudgetInstructionAssignmentCK)) {
/*  56 */       other = ((BudgetInstructionAssignmentCK)obj).getBudgetInstructionAssignmentPK();
/*     */     }
/*  58 */     else if ((obj instanceof BudgetInstructionAssignmentPK))
/*  59 */       other = (BudgetInstructionAssignmentPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mAssignmentId == other.mAssignmentId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" AssignmentId=");
/*  77 */     sb.append(this.mAssignmentId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mAssignmentId);
/*  89 */     return "BudgetInstructionAssignmentPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static BudgetInstructionAssignmentPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("BudgetInstructionAssignmentPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'BudgetInstructionAssignmentPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pAssignmentId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new BudgetInstructionAssignmentPK(pAssignmentId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentPK
 * JD-Core Version:    0.6.0
 */