/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class BudgetUserPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 148 */   private int mHashCode = -2147483648;
/*     */   int mModelId;
/*     */   int mStructureElementId;
/*     */   int mUserId;
/*     */ 
/*     */   public BudgetUserPK(int newModelId, int newStructureElementId, int newUserId)
/*     */   {
/*  25 */     this.mModelId = newModelId;
/*  26 */     this.mStructureElementId = newStructureElementId;
/*  27 */     this.mUserId = newUserId;
/*     */   }
/*     */ 
/*     */   public int getModelId()
/*     */   {
/*  36 */     return this.mModelId;
/*     */   }
/*     */ 
/*     */   public int getStructureElementId()
/*     */   {
/*  43 */     return this.mStructureElementId;
/*     */   }
/*     */ 
/*     */   public int getUserId()
/*     */   {
/*  50 */     return this.mUserId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  58 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  60 */       this.mHashCode += String.valueOf(this.mModelId).hashCode();
/*  61 */       this.mHashCode += String.valueOf(this.mStructureElementId).hashCode();
/*  62 */       this.mHashCode += String.valueOf(this.mUserId).hashCode();
/*     */     }
/*     */ 
/*  65 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  73 */     BudgetUserPK other = null;
/*     */ 
/*  75 */     if ((obj instanceof BudgetUserCK)) {
/*  76 */       other = ((BudgetUserCK)obj).getBudgetUserPK();
/*     */     }
/*  78 */     else if ((obj instanceof BudgetUserPK))
/*  79 */       other = (BudgetUserPK)obj;
/*     */     else {
/*  81 */       return false;
/*     */     }
/*  83 */     boolean eq = true;
/*     */ 
/*  85 */     eq = (eq) && (this.mModelId == other.mModelId);
/*  86 */     eq = (eq) && (this.mStructureElementId == other.mStructureElementId);
/*  87 */     eq = (eq) && (this.mUserId == other.mUserId);
/*     */ 
/*  89 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  97 */     StringBuffer sb = new StringBuffer();
/*  98 */     sb.append(" ModelId=");
/*  99 */     sb.append(this.mModelId);
/* 100 */     sb.append(",StructureElementId=");
/* 101 */     sb.append(this.mStructureElementId);
/* 102 */     sb.append(",UserId=");
/* 103 */     sb.append(this.mUserId);
/* 104 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 112 */     StringBuffer sb = new StringBuffer();
/* 113 */     sb.append(" ");
/* 114 */     sb.append(this.mModelId);
/* 115 */     sb.append(",");
/* 116 */     sb.append(this.mStructureElementId);
/* 117 */     sb.append(",");
/* 118 */     sb.append(this.mUserId);
/* 119 */     return "BudgetUserPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static BudgetUserPK getKeyFromTokens(String extKey)
/*     */   {
/* 124 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 126 */     if (extValues.length != 2) {
/* 127 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 129 */     if (!extValues[0].equals("BudgetUserPK")) {
/* 130 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'BudgetUserPK|'");
/*     */     }
/* 132 */     extValues = extValues[1].split(",");
/*     */ 
/* 134 */     int i = 0;
/* 135 */     int pModelId = new Integer(extValues[(i++)]).intValue();
/* 136 */     int pStructureElementId = new Integer(extValues[(i++)]).intValue();
/* 137 */     int pUserId = new Integer(extValues[(i++)]).intValue();
/* 138 */     return new BudgetUserPK(pModelId, pStructureElementId, pUserId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.BudgetUserPK
 * JD-Core Version:    0.6.0
 */