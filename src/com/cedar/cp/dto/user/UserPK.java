/*     */ package com.cedar.cp.dto.user;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class UserPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mUserId;
/*     */ 
/*     */   public UserPK(int newUserId)
/*     */   {
/*  23 */     this.mUserId = newUserId;
/*     */   }
/*     */ 
/*     */   public int getUserId()
/*     */   {
/*  32 */     return this.mUserId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mUserId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     UserPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof UserCK)) {
/*  56 */       other = ((UserCK)obj).getUserPK();
/*     */     }
/*  58 */     else if ((obj instanceof UserPK))
/*  59 */       other = (UserPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mUserId == other.mUserId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" UserId=");
/*  77 */     sb.append(this.mUserId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mUserId);
/*  89 */     return "UserPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static UserPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("UserPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'UserPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pUserId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new UserPK(pUserId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.user.UserPK
 * JD-Core Version:    0.6.0
 */