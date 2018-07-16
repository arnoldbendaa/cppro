/*     */ package com.cedar.cp.dto.role;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class RolePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mRoleId;
/*     */ 
/*     */   public RolePK(int newRoleId)
/*     */   {
/*  23 */     this.mRoleId = newRoleId;
/*     */   }
/*     */ 
/*     */   public int getRoleId()
/*     */   {
/*  32 */     return this.mRoleId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mRoleId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     RolePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof RoleCK)) {
/*  56 */       other = ((RoleCK)obj).getRolePK();
/*     */     }
/*  58 */     else if ((obj instanceof RolePK))
/*  59 */       other = (RolePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mRoleId == other.mRoleId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" RoleId=");
/*  77 */     sb.append(this.mRoleId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mRoleId);
/*  89 */     return "RolePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static RolePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("RolePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'RolePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pRoleId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new RolePK(pRoleId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.role.RolePK
 * JD-Core Version:    0.6.0
 */