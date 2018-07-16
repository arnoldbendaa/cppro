/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetStatePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mBudgetCycleId;
/*     */   int mStructureElementId;
/*     */ 
/*     */   public BudgetStatePK(int newBudgetCycleId, int newStructureElementId)
/*     */   {
/*  24 */     this.mBudgetCycleId = newBudgetCycleId;
/*  25 */     this.mStructureElementId = newStructureElementId;
/*     */   }
/*     */ 
/*     */   public int getBudgetCycleId()
/*     */   {
/*  34 */     return this.mBudgetCycleId;
/*     */   }
/*     */ 
/*     */   public int getStructureElementId()
/*     */   {
/*  41 */     return this.mStructureElementId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mBudgetCycleId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mStructureElementId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     BudgetStatePK other = null;
/*     */ 
/*  65 */     if ((obj instanceof BudgetStateCK)) {
/*  66 */       other = ((BudgetStateCK)obj).getBudgetStatePK();
/*     */     }
/*  68 */     else if ((obj instanceof BudgetStatePK))
/*  69 */       other = (BudgetStatePK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mBudgetCycleId == other.mBudgetCycleId);
/*  76 */     eq = (eq) && (this.mStructureElementId == other.mStructureElementId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" BudgetCycleId=");
/*  88 */     sb.append(this.mBudgetCycleId);
/*  89 */     sb.append(",StructureElementId=");
/*  90 */     sb.append(this.mStructureElementId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mBudgetCycleId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mStructureElementId);
/* 104 */     return "BudgetStatePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static BudgetStatePK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("BudgetStatePK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'BudgetStatePK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pBudgetCycleId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pStructureElementId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new BudgetStatePK(pBudgetCycleId, pStructureElementId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.BudgetStatePK
 * JD-Core Version:    0.6.0
 */