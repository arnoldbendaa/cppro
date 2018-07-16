/*     */ package com.cedar.cp.dto.role;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RoleSecurityRelPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mRoleId;
/*     */   int mRoleSecurityId;
/*     */ 
/*     */   public RoleSecurityRelPK(int newRoleId, int newRoleSecurityId)
/*     */   {
/*  24 */     this.mRoleId = newRoleId;
/*  25 */     this.mRoleSecurityId = newRoleSecurityId;
/*     */   }
/*     */ 
/*     */   public int getRoleId()
/*     */   {
/*  34 */     return this.mRoleId;
/*     */   }
/*     */ 
/*     */   public int getRoleSecurityId()
/*     */   {
/*  41 */     return this.mRoleSecurityId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mRoleId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mRoleSecurityId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     RoleSecurityRelPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof RoleSecurityRelCK)) {
/*  66 */       other = ((RoleSecurityRelCK)obj).getRoleSecurityRelPK();
/*     */     }
/*  68 */     else if ((obj instanceof RoleSecurityRelPK))
/*  69 */       other = (RoleSecurityRelPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mRoleId == other.mRoleId);
/*  76 */     eq = (eq) && (this.mRoleSecurityId == other.mRoleSecurityId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" RoleId=");
/*  88 */     sb.append(this.mRoleId);
/*  89 */     sb.append(",RoleSecurityId=");
/*  90 */     sb.append(this.mRoleSecurityId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mRoleId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mRoleSecurityId);
/* 104 */     return "RoleSecurityRelPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static RoleSecurityRelPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("RoleSecurityRelPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'RoleSecurityRelPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pRoleId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pRoleSecurityId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new RoleSecurityRelPK(pRoleId, pRoleSecurityId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.role.RoleSecurityRelPK
 * JD-Core Version:    0.6.0
 */