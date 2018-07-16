/*     */ package com.cedar.cp.dto.authenticationpolicy;
/*     */ 
/*     */ import com.cedar.cp.dto.base.CompositeKey;
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AuthenticationPolicyCK extends CompositeKey
/*     */   implements Serializable
/*     */ {
/*     */   protected AuthenticationPolicyPK mAuthenticationPolicyPK;
/*     */ 
/*     */   public AuthenticationPolicyCK(AuthenticationPolicyPK paramAuthenticationPolicyPK)
/*     */   {
/*  26 */     this.mAuthenticationPolicyPK = paramAuthenticationPolicyPK;
/*     */   }
/*     */ 
/*     */   public AuthenticationPolicyPK getAuthenticationPolicyPK()
/*     */   {
/*  34 */     return this.mAuthenticationPolicyPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  42 */     return this.mAuthenticationPolicyPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  50 */     return this.mAuthenticationPolicyPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  59 */     if ((obj instanceof AuthenticationPolicyPK)) {
/*  60 */       return obj.equals(this);
/*     */     }
/*  62 */     if (!(obj instanceof AuthenticationPolicyCK)) {
/*  63 */       return false;
/*     */     }
/*  65 */     AuthenticationPolicyCK other = (AuthenticationPolicyCK)obj;
/*  66 */     boolean eq = true;
/*     */ 
/*  68 */     eq = (eq) && (this.mAuthenticationPolicyPK.equals(other.mAuthenticationPolicyPK));
/*     */ 
/*  70 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     sb.append("[");
/*  80 */     sb.append(this.mAuthenticationPolicyPK);
/*  81 */     sb.append("]");
/*  82 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  90 */     StringBuffer sb = new StringBuffer();
/*  91 */     sb.append("AuthenticationPolicyCK|");
/*  92 */     sb.append(this.mAuthenticationPolicyPK.toTokens());
/*  93 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static AuthenticationPolicyCK getKeyFromTokens(String extKey)
/*     */   {
/*  98 */     String[] token = extKey.split("[|]");
/*  99 */     int i = 0;
/* 100 */     checkExpected("AuthenticationPolicyCK", token[(i++)]);
/* 101 */     checkExpected("AuthenticationPolicyPK", token[(i++)]);
/* 102 */     i = 1;
/* 103 */     return new AuthenticationPolicyCK(AuthenticationPolicyPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 110 */     if (!expected.equals(found))
/* 111 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyCK
 * JD-Core Version:    0.6.0
 */