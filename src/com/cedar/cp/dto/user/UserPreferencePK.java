/*     */ package com.cedar.cp.dto.user;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class UserPreferencePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mUserPrefId;
/*     */ 
/*     */   public UserPreferencePK(int newUserPrefId)
/*     */   {
/*  23 */     this.mUserPrefId = newUserPrefId;
/*     */   }
/*     */ 
/*     */   public int getUserPrefId()
/*     */   {
/*  32 */     return this.mUserPrefId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mUserPrefId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     UserPreferencePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof UserPreferenceCK)) {
/*  56 */       other = ((UserPreferenceCK)obj).getUserPreferencePK();
/*     */     }
/*  58 */     else if ((obj instanceof UserPreferencePK))
/*  59 */       other = (UserPreferencePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mUserPrefId == other.mUserPrefId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" UserPrefId=");
/*  77 */     sb.append(this.mUserPrefId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mUserPrefId);
/*  89 */     return "UserPreferencePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static UserPreferencePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("UserPreferencePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'UserPreferencePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pUserPrefId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new UserPreferencePK(pUserPrefId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.user.UserPreferencePK
 * JD-Core Version:    0.6.0
 */