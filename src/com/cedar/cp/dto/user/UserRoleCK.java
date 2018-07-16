/*     */ package com.cedar.cp.dto.user;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class UserRoleCK extends UserCK
/*     */   implements Serializable
/*     */ {
/*     */   protected UserRolePK mUserRolePK;
/*     */ 
/*     */   public UserRoleCK(UserPK paramUserPK, UserRolePK paramUserRolePK)
/*     */   {
/*  29 */     super(paramUserPK);
/*     */ 
/*  32 */     this.mUserRolePK = paramUserRolePK;
/*     */   }
/*     */ 
/*     */   public UserRolePK getUserRolePK()
/*     */   {
/*  40 */     return this.mUserRolePK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  48 */     return this.mUserRolePK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  56 */     return this.mUserRolePK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  65 */     if ((obj instanceof UserRolePK)) {
/*  66 */       return obj.equals(this);
/*     */     }
/*  68 */     if (!(obj instanceof UserRoleCK)) {
/*  69 */       return false;
/*     */     }
/*  71 */     UserRoleCK other = (UserRoleCK)obj;
/*  72 */     boolean eq = true;
/*     */ 
/*  74 */     eq = (eq) && (this.mUserPK.equals(other.mUserPK));
/*  75 */     eq = (eq) && (this.mUserRolePK.equals(other.mUserRolePK));
/*     */ 
/*  77 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     StringBuffer sb = new StringBuffer();
/*  86 */     sb.append(super.toString());
/*  87 */     sb.append("[");
/*  88 */     sb.append(this.mUserRolePK);
/*  89 */     sb.append("]");
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  98 */     StringBuffer sb = new StringBuffer();
/*  99 */     sb.append("UserRoleCK|");
/* 100 */     sb.append(super.getPK().toTokens());
/* 101 */     sb.append('|');
/* 102 */     sb.append(this.mUserRolePK.toTokens());
/* 103 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static UserCK getKeyFromTokens(String extKey)
/*     */   {
/* 108 */     String[] token = extKey.split("[|]");
/* 109 */     int i = 0;
/* 110 */     checkExpected("UserRoleCK", token[(i++)]);
/* 111 */     checkExpected("UserPK", token[(i++)]);
/* 112 */     i++;
/* 113 */     checkExpected("UserRolePK", token[(i++)]);
/* 114 */     i = 1;
/* 115 */     return new UserRoleCK(UserPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), UserRolePK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 123 */     if (!expected.equals(found))
/* 124 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.user.UserRoleCK
 * JD-Core Version:    0.6.0
 */