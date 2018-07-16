/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SecurityGroupPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mSecurityGroupId;
/*     */ 
/*     */   public SecurityGroupPK(int newSecurityGroupId)
/*     */   {
/*  23 */     this.mSecurityGroupId = newSecurityGroupId;
/*     */   }
/*     */ 
/*     */   public int getSecurityGroupId()
/*     */   {
/*  32 */     return this.mSecurityGroupId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mSecurityGroupId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     SecurityGroupPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof SecurityGroupCK)) {
/*  56 */       other = ((SecurityGroupCK)obj).getSecurityGroupPK();
/*     */     }
/*  58 */     else if ((obj instanceof SecurityGroupPK))
/*  59 */       other = (SecurityGroupPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mSecurityGroupId == other.mSecurityGroupId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" SecurityGroupId=");
/*  77 */     sb.append(this.mSecurityGroupId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mSecurityGroupId);
/*  89 */     return "SecurityGroupPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static SecurityGroupPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("SecurityGroupPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'SecurityGroupPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pSecurityGroupId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new SecurityGroupPK(pSecurityGroupId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.SecurityGroupPK
 * JD-Core Version:    0.6.0
 */