/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SecurityAccRngRelPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mSecurityAccessDefId;
/*     */   int mSecurityRangeId;
/*     */ 
/*     */   public SecurityAccRngRelPK(int newSecurityAccessDefId, int newSecurityRangeId)
/*     */   {
/*  24 */     this.mSecurityAccessDefId = newSecurityAccessDefId;
/*  25 */     this.mSecurityRangeId = newSecurityRangeId;
/*     */   }
/*     */ 
/*     */   public int getSecurityAccessDefId()
/*     */   {
/*  34 */     return this.mSecurityAccessDefId;
/*     */   }
/*     */ 
/*     */   public int getSecurityRangeId()
/*     */   {
/*  41 */     return this.mSecurityRangeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mSecurityAccessDefId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mSecurityRangeId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     SecurityAccRngRelPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof SecurityAccRngRelCK)) {
/*  66 */       other = ((SecurityAccRngRelCK)obj).getSecurityAccRngRelPK();
/*     */     }
/*  68 */     else if ((obj instanceof SecurityAccRngRelPK))
/*  69 */       other = (SecurityAccRngRelPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mSecurityAccessDefId == other.mSecurityAccessDefId);
/*  76 */     eq = (eq) && (this.mSecurityRangeId == other.mSecurityRangeId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" SecurityAccessDefId=");
/*  88 */     sb.append(this.mSecurityAccessDefId);
/*  89 */     sb.append(",SecurityRangeId=");
/*  90 */     sb.append(this.mSecurityRangeId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mSecurityAccessDefId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mSecurityRangeId);
/* 104 */     return "SecurityAccRngRelPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static SecurityAccRngRelPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("SecurityAccRngRelPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'SecurityAccRngRelPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pSecurityAccessDefId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pSecurityRangeId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new SecurityAccRngRelPK(pSecurityAccessDefId, pSecurityRangeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.SecurityAccRngRelPK
 * JD-Core Version:    0.6.0
 */