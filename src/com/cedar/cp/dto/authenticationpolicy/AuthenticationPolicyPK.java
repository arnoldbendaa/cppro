/*     */ package com.cedar.cp.dto.authenticationpolicy;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AuthenticationPolicyPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mAuthenticationPolicyId;
/*     */ 
/*     */   public AuthenticationPolicyPK(int newAuthenticationPolicyId)
/*     */   {
/*  23 */     this.mAuthenticationPolicyId = newAuthenticationPolicyId;
/*     */   }
/*     */ 
/*     */   public int getAuthenticationPolicyId()
/*     */   {
/*  32 */     return this.mAuthenticationPolicyId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mAuthenticationPolicyId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     AuthenticationPolicyPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof AuthenticationPolicyCK)) {
/*  56 */       other = ((AuthenticationPolicyCK)obj).getAuthenticationPolicyPK();
/*     */     }
/*  58 */     else if ((obj instanceof AuthenticationPolicyPK))
/*  59 */       other = (AuthenticationPolicyPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mAuthenticationPolicyId == other.mAuthenticationPolicyId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" AuthenticationPolicyId=");
/*  77 */     sb.append(this.mAuthenticationPolicyId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mAuthenticationPolicyId);
/*  89 */     return "AuthenticationPolicyPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static AuthenticationPolicyPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("AuthenticationPolicyPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'AuthenticationPolicyPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pAuthenticationPolicyId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new AuthenticationPolicyPK(pAuthenticationPolicyId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK
 * JD-Core Version:    0.6.0
 */