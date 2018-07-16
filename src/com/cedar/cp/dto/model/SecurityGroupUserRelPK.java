/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SecurityGroupUserRelPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mSecurityGroupId;
/*     */   int mUserId;
/*     */ 
/*     */   public SecurityGroupUserRelPK(int newSecurityGroupId, int newUserId)
/*     */   {
/*  24 */     this.mSecurityGroupId = newSecurityGroupId;
/*  25 */     this.mUserId = newUserId;
/*     */   }
/*     */ 
/*     */   public int getSecurityGroupId()
/*     */   {
/*  34 */     return this.mSecurityGroupId;
/*     */   }
/*     */ 
/*     */   public int getUserId()
/*     */   {
/*  41 */     return this.mUserId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mSecurityGroupId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mUserId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     SecurityGroupUserRelPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof SecurityGroupUserRelCK)) {
/*  66 */       other = ((SecurityGroupUserRelCK)obj).getSecurityGroupUserRelPK();
/*     */     }
/*  68 */     else if ((obj instanceof SecurityGroupUserRelPK))
/*  69 */       other = (SecurityGroupUserRelPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mSecurityGroupId == other.mSecurityGroupId);
/*  76 */     eq = (eq) && (this.mUserId == other.mUserId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" SecurityGroupId=");
/*  88 */     sb.append(this.mSecurityGroupId);
/*  89 */     sb.append(",UserId=");
/*  90 */     sb.append(this.mUserId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mSecurityGroupId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mUserId);
/* 104 */     return "SecurityGroupUserRelPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static SecurityGroupUserRelPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("SecurityGroupUserRelPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'SecurityGroupUserRelPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pSecurityGroupId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pUserId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new SecurityGroupUserRelPK(pSecurityGroupId, pUserId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.SecurityGroupUserRelPK
 * JD-Core Version:    0.6.0
 */