/*     */ package com.cedar.cp.dto.role;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RoleSecurityPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mRoleSecurityId;
/*     */ 
/*     */   public RoleSecurityPK(int newRoleSecurityId)
/*     */   {
/*  23 */     this.mRoleSecurityId = newRoleSecurityId;
/*     */   }
/*     */ 
/*     */   public int getRoleSecurityId()
/*     */   {
/*  32 */     return this.mRoleSecurityId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mRoleSecurityId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     RoleSecurityPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof RoleSecurityCK)) {
/*  56 */       other = ((RoleSecurityCK)obj).getRoleSecurityPK();
/*     */     }
/*  58 */     else if ((obj instanceof RoleSecurityPK))
/*  59 */       other = (RoleSecurityPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mRoleSecurityId == other.mRoleSecurityId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" RoleSecurityId=");
/*  77 */     sb.append(this.mRoleSecurityId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mRoleSecurityId);
/*  89 */     return "RoleSecurityPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static RoleSecurityPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("RoleSecurityPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'RoleSecurityPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pRoleSecurityId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new RoleSecurityPK(pRoleSecurityId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.role.RoleSecurityPK
 * JD-Core Version:    0.6.0
 */