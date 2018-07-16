/*     */ package com.cedar.cp.dto.user;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DataEntryProfileHistoryCK extends DataEntryProfileCK
/*     */   implements Serializable
/*     */ {
/*     */   protected DataEntryProfileHistoryPK mDataEntryProfileHistoryPK;
/*     */ 
/*     */   public DataEntryProfileHistoryCK(UserPK paramUserPK, DataEntryProfilePK paramDataEntryProfilePK, DataEntryProfileHistoryPK paramDataEntryProfileHistoryPK)
/*     */   {
/*  31 */     super(paramUserPK, paramDataEntryProfilePK);
/*     */ 
/*  35 */     this.mDataEntryProfileHistoryPK = paramDataEntryProfileHistoryPK;
/*     */   }
/*     */ 
/*     */   public DataEntryProfileHistoryPK getDataEntryProfileHistoryPK()
/*     */   {
/*  43 */     return this.mDataEntryProfileHistoryPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mDataEntryProfileHistoryPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mDataEntryProfileHistoryPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof DataEntryProfileHistoryPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof DataEntryProfileHistoryCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     DataEntryProfileHistoryCK other = (DataEntryProfileHistoryCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mUserPK.equals(other.mUserPK));
/*  78 */     eq = (eq) && (this.mDataEntryProfilePK.equals(other.mDataEntryProfilePK));
/*  79 */     eq = (eq) && (this.mDataEntryProfileHistoryPK.equals(other.mDataEntryProfileHistoryPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mDataEntryProfileHistoryPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("DataEntryProfileHistoryCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mDataEntryProfileHistoryPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static UserCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("DataEntryProfileHistoryCK", token[(i++)]);
/* 115 */     checkExpected("UserPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("DataEntryProfilePK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("DataEntryProfileHistoryPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new DataEntryProfileHistoryCK(UserPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), DataEntryProfilePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), DataEntryProfileHistoryPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.user.DataEntryProfileHistoryCK
 * JD-Core Version:    0.6.0
 */