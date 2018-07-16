/*     */ package com.cedar.cp.dto.user;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DataEntryProfileCK extends UserCK
/*     */   implements Serializable
/*     */ {
/*     */   protected DataEntryProfilePK mDataEntryProfilePK;
/*     */ 
/*     */   public DataEntryProfileCK(UserPK paramUserPK, DataEntryProfilePK paramDataEntryProfilePK)
/*     */   {
/*  29 */     super(paramUserPK);
/*     */ 
/*  32 */     this.mDataEntryProfilePK = paramDataEntryProfilePK;
/*     */   }
/*     */ 
/*     */   public DataEntryProfilePK getDataEntryProfilePK()
/*     */   {
/*  40 */     return this.mDataEntryProfilePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mDataEntryProfilePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mDataEntryProfilePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof DataEntryProfilePK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof DataEntryProfileCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     DataEntryProfileCK other = (DataEntryProfileCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mUserPK.equals(other.mUserPK));
/*  75 */     eq = (eq) && (this.mDataEntryProfilePK.equals(other.mDataEntryProfilePK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mDataEntryProfilePK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("DataEntryProfileCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mDataEntryProfilePK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static UserCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("DataEntryProfileCK", token[(i++)]);
/* 111 */     checkExpected("UserPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("DataEntryProfilePK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new DataEntryProfileCK(UserPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), DataEntryProfilePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.user.DataEntryProfileCK
 * JD-Core Version:    0.6.0
 */