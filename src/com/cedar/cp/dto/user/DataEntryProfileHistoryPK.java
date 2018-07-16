/*     */ package com.cedar.cp.dto.user;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DataEntryProfileHistoryPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 148 */   private int mHashCode = -2147483648;
/*     */   int mUserId;
/*     */   int mModelId;
/*     */   int mBudgetLocationElementId;
			int mBudgetCycleId;
/*     */ 
/*     */   public DataEntryProfileHistoryPK(int newUserId, int newModelId, int newBudgetLocationElementId, int newBudgetCycleId)
/*     */   {
/*  25 */     this.mUserId = newUserId;
/*  26 */     this.mModelId = newModelId;
/*  27 */     this.mBudgetLocationElementId = newBudgetLocationElementId;
			  this.mBudgetCycleId = newBudgetCycleId;
/*     */   }
/*     */ 
/*     */   public int getUserId()
/*     */   {
/*  36 */     return this.mUserId;
/*     */   }
/*     */ 
/*     */   public int getModelId()
/*     */   {
/*  43 */     return this.mModelId;
/*     */   }
/*     */ 
/*     */   public int getBudgetLocationElementId()
/*     */   {
/*  50 */     return this.mBudgetLocationElementId;
/*     */   }

			public int getBudgetCycleId() {
				return this.mBudgetCycleId;
			}
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  58 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  60 */       this.mHashCode += String.valueOf(this.mUserId).hashCode();
/*  61 */       this.mHashCode += String.valueOf(this.mModelId).hashCode();
/*  62 */       this.mHashCode += String.valueOf(this.mBudgetLocationElementId).hashCode();
				this.mHashCode += String.valueOf(this.mBudgetCycleId).hashCode();
/*     */     }
/*     */ 
/*  65 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  73 */     DataEntryProfileHistoryPK other = null;
/*     */ 
/*  75 */     if ((obj instanceof DataEntryProfileHistoryCK)) {
/*  76 */       other = ((DataEntryProfileHistoryCK)obj).getDataEntryProfileHistoryPK();
/*     */     }
/*  78 */     else if ((obj instanceof DataEntryProfileHistoryPK))
/*  79 */       other = (DataEntryProfileHistoryPK)obj;
/*     */     else {
/*  81 */       return false;
/*     */     }
/*  83 */     boolean eq = true;
/*     */ 
/*  85 */     eq = (eq) && (this.mUserId == other.mUserId);
/*  86 */     eq = (eq) && (this.mModelId == other.mModelId);
/*  87 */     eq = (eq) && (this.mBudgetLocationElementId == other.mBudgetLocationElementId);
			  eq = (eq) && (this.mBudgetCycleId == other.mBudgetCycleId);
/*     */ 
/*  89 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  97 */     StringBuffer sb = new StringBuffer();
/*  98 */     sb.append(" UserId=");
/*  99 */     sb.append(this.mUserId);
/* 100 */     sb.append(",ModelId=");
/* 101 */     sb.append(this.mModelId);
/* 102 */     sb.append(",BudgetLocationElementId=");
/* 103 */     sb.append(this.mBudgetLocationElementId);
			  sb.append(",BudgetCycleId=");
			  sb.append(this.mBudgetCycleId);
/* 104 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 112 */     StringBuffer sb = new StringBuffer();
/* 113 */     sb.append(" ");
/* 114 */     sb.append(this.mUserId);
/* 115 */     sb.append(",");
/* 116 */     sb.append(this.mModelId);
/* 117 */     sb.append(",");
/* 118 */     sb.append(this.mBudgetLocationElementId);
			  sb.append(",");
			  sb.append(this.mBudgetCycleId);
/* 119 */     return "DataEntryProfileHistoryPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static DataEntryProfileHistoryPK getKeyFromTokens(String extKey)
/*     */   {
/* 124 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 126 */     if (extValues.length != 2) {
/* 127 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 129 */     if (!extValues[0].equals("DataEntryProfileHistoryPK")) {
/* 130 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'DataEntryProfileHistoryPK|'");
/*     */     }
/* 132 */     extValues = extValues[1].split(",");
/*     */ 
/* 134 */     int i = 0;
/* 135 */     int pUserId = new Integer(extValues[(i++)]).intValue();
/* 136 */     int pModelId = new Integer(extValues[(i++)]).intValue();
/* 137 */     int pBudgetLocationElementId = new Integer(extValues[(i++)]).intValue();
			  int pBudgetCycleId = new Integer(extValues[(i++)]).intValue();
/* 138 */     return new DataEntryProfileHistoryPK(pUserId, pModelId, pBudgetLocationElementId, pBudgetCycleId);
/*     */   }
/*     */ }