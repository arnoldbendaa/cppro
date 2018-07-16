/*     */ package com.cedar.cp.dto.user;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class UserRolePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mUserId;
/*     */   int mRoleId;
/*     */ 
/*     */   public UserRolePK(int newUserId, int newRoleId)
/*     */   {
/*  24 */     this.mUserId = newUserId;
/*  25 */     this.mRoleId = newRoleId;
/*     */   }
/*     */ 
/*     */   public int getUserId()
/*     */   {
/*  34 */     return this.mUserId;
/*     */   }
/*     */ 
/*     */   public int getRoleId()
/*     */   {
/*  41 */     return this.mRoleId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mUserId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mRoleId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     UserRolePK other = null;
/*     */ 
/*  65 */     if ((obj instanceof UserRoleCK)) {
/*  66 */       other = ((UserRoleCK)obj).getUserRolePK();
/*     */     }
/*  68 */     else if ((obj instanceof UserRolePK))
/*  69 */       other = (UserRolePK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mUserId == other.mUserId);
/*  76 */     eq = (eq) && (this.mRoleId == other.mRoleId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" UserId=");
/*  88 */     sb.append(this.mUserId);
/*  89 */     sb.append(",RoleId=");
/*  90 */     sb.append(this.mRoleId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mUserId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mRoleId);
/* 104 */     return "UserRolePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static UserRolePK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("UserRolePK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'UserRolePK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pUserId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pRoleId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new UserRolePK(pUserId, pRoleId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.user.UserRolePK
 * JD-Core Version:    0.6.0
 */