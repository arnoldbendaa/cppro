/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetStateHistoryPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 148 */   private int mHashCode = -2147483648;
/*     */   int mBudgetStateHistoryId;
/*     */   int mBudgetCycleId;
/*     */   int mStructureElementId;
/*     */ 
/*     */   public BudgetStateHistoryPK(int newBudgetStateHistoryId, int newBudgetCycleId, int newStructureElementId)
/*     */   {
/*  25 */     this.mBudgetStateHistoryId = newBudgetStateHistoryId;
/*  26 */     this.mBudgetCycleId = newBudgetCycleId;
/*  27 */     this.mStructureElementId = newStructureElementId;
/*     */   }
/*     */ 
/*     */   public int getBudgetStateHistoryId()
/*     */   {
/*  36 */     return this.mBudgetStateHistoryId;
/*     */   }
/*     */ 
/*     */   public int getBudgetCycleId()
/*     */   {
/*  43 */     return this.mBudgetCycleId;
/*     */   }
/*     */ 
/*     */   public int getStructureElementId()
/*     */   {
/*  50 */     return this.mStructureElementId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  58 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  60 */       this.mHashCode += String.valueOf(this.mBudgetStateHistoryId).hashCode();
/*  61 */       this.mHashCode += String.valueOf(this.mBudgetCycleId).hashCode();
/*  62 */       this.mHashCode += String.valueOf(this.mStructureElementId).hashCode();
/*     */     }
/*     */ 
/*  65 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  73 */     BudgetStateHistoryPK other = null;
/*     */ 
/*  75 */     if ((obj instanceof BudgetStateHistoryCK)) {
/*  76 */       other = ((BudgetStateHistoryCK)obj).getBudgetStateHistoryPK();
/*     */     }
/*  78 */     else if ((obj instanceof BudgetStateHistoryPK))
/*  79 */       other = (BudgetStateHistoryPK)obj;
/*     */     else {
/*  81 */       return false;
/*     */     }
/*  83 */     boolean eq = true;
/*     */ 
/*  85 */     eq = (eq) && (this.mBudgetStateHistoryId == other.mBudgetStateHistoryId);
/*  86 */     eq = (eq) && (this.mBudgetCycleId == other.mBudgetCycleId);
/*  87 */     eq = (eq) && (this.mStructureElementId == other.mStructureElementId);
/*     */ 
/*  89 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  97 */     StringBuffer sb = new StringBuffer();
/*  98 */     sb.append(" BudgetStateHistoryId=");
/*  99 */     sb.append(this.mBudgetStateHistoryId);
/* 100 */     sb.append(",BudgetCycleId=");
/* 101 */     sb.append(this.mBudgetCycleId);
/* 102 */     sb.append(",StructureElementId=");
/* 103 */     sb.append(this.mStructureElementId);
/* 104 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 112 */     StringBuffer sb = new StringBuffer();
/* 113 */     sb.append(" ");
/* 114 */     sb.append(this.mBudgetStateHistoryId);
/* 115 */     sb.append(",");
/* 116 */     sb.append(this.mBudgetCycleId);
/* 117 */     sb.append(",");
/* 118 */     sb.append(this.mStructureElementId);
/* 119 */     return "BudgetStateHistoryPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static BudgetStateHistoryPK getKeyFromTokens(String extKey)
/*     */   {
/* 124 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 126 */     if (extValues.length != 2) {
/* 127 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 129 */     if (!extValues[0].equals("BudgetStateHistoryPK")) {
/* 130 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'BudgetStateHistoryPK|'");
/*     */     }
/* 132 */     extValues = extValues[1].split(",");
/*     */ 
/* 134 */     int i = 0;
/* 135 */     int pBudgetStateHistoryId = new Integer(extValues[(i++)]).intValue();
/* 136 */     int pBudgetCycleId = new Integer(extValues[(i++)]).intValue();
/* 137 */     int pStructureElementId = new Integer(extValues[(i++)]).intValue();
/* 138 */     return new BudgetStateHistoryPK(pBudgetStateHistoryId, pBudgetCycleId, pStructureElementId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.BudgetStateHistoryPK
 * JD-Core Version:    0.6.0
 */