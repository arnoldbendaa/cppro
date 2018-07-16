/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SecurityAccessDefPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mSecurityAccessDefId;
/*     */ 
/*     */   public SecurityAccessDefPK(int newSecurityAccessDefId)
/*     */   {
/*  23 */     this.mSecurityAccessDefId = newSecurityAccessDefId;
/*     */   }
/*     */ 
/*     */   public int getSecurityAccessDefId()
/*     */   {
/*  32 */     return this.mSecurityAccessDefId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mSecurityAccessDefId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     SecurityAccessDefPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof SecurityAccessDefCK)) {
/*  56 */       other = ((SecurityAccessDefCK)obj).getSecurityAccessDefPK();
/*     */     }
/*  58 */     else if ((obj instanceof SecurityAccessDefPK))
/*  59 */       other = (SecurityAccessDefPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mSecurityAccessDefId == other.mSecurityAccessDefId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" SecurityAccessDefId=");
/*  77 */     sb.append(this.mSecurityAccessDefId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mSecurityAccessDefId);
/*  89 */     return "SecurityAccessDefPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static SecurityAccessDefPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("SecurityAccessDefPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'SecurityAccessDefPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pSecurityAccessDefId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new SecurityAccessDefPK(pSecurityAccessDefId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.SecurityAccessDefPK
 * JD-Core Version:    0.6.0
 */