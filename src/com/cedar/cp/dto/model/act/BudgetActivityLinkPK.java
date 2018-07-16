/*     */ package com.cedar.cp.dto.model.act;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetActivityLinkPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mBudgetActivityId;
/*     */   int mStructureElementId;
/*     */ 
/*     */   public BudgetActivityLinkPK(int newBudgetActivityId, int newStructureElementId)
/*     */   {
/*  24 */     this.mBudgetActivityId = newBudgetActivityId;
/*  25 */     this.mStructureElementId = newStructureElementId;
/*     */   }
/*     */ 
/*     */   public int getBudgetActivityId()
/*     */   {
/*  34 */     return this.mBudgetActivityId;
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
/*  51 */       this.mHashCode += String.valueOf(this.mBudgetActivityId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mStructureElementId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     BudgetActivityLinkPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof BudgetActivityLinkCK)) {
/*  66 */       other = ((BudgetActivityLinkCK)obj).getBudgetActivityLinkPK();
/*     */     }
/*  68 */     else if ((obj instanceof BudgetActivityLinkPK))
/*  69 */       other = (BudgetActivityLinkPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mBudgetActivityId == other.mBudgetActivityId);
/*  76 */     eq = (eq) && (this.mStructureElementId == other.mStructureElementId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" BudgetActivityId=");
/*  88 */     sb.append(this.mBudgetActivityId);
/*  89 */     sb.append(",StructureElementId=");
/*  90 */     sb.append(this.mStructureElementId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mBudgetActivityId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mStructureElementId);
/* 104 */     return "BudgetActivityLinkPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static BudgetActivityLinkPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("BudgetActivityLinkPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'BudgetActivityLinkPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pBudgetActivityId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pStructureElementId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new BudgetActivityLinkPK(pBudgetActivityId, pStructureElementId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.act.BudgetActivityLinkPK
 * JD-Core Version:    0.6.0
 */