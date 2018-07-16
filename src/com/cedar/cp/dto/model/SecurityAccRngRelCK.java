/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SecurityAccRngRelCK extends SecurityAccessDefCK
/*     */   implements Serializable
/*     */ {
/*     */   protected SecurityAccRngRelPK mSecurityAccRngRelPK;
/*     */ 
/*     */   public SecurityAccRngRelCK(ModelPK paramModelPK, SecurityAccessDefPK paramSecurityAccessDefPK, SecurityAccRngRelPK paramSecurityAccRngRelPK)
/*     */   {
/*  31 */     super(paramModelPK, paramSecurityAccessDefPK);
/*     */ 
/*  35 */     this.mSecurityAccRngRelPK = paramSecurityAccRngRelPK;
/*     */   }
/*     */ 
/*     */   public SecurityAccRngRelPK getSecurityAccRngRelPK()
/*     */   {
/*  43 */     return this.mSecurityAccRngRelPK;
/*     */   }
/*     */ 
/*     */   public PrimaryKey getPK()
/*     */   {
/*  51 */     return this.mSecurityAccRngRelPK;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  59 */     return this.mSecurityAccRngRelPK.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  68 */     if ((obj instanceof SecurityAccRngRelPK)) {
/*  69 */       return obj.equals(this);
/*     */     }
/*  71 */     if (!(obj instanceof SecurityAccRngRelCK)) {
/*  72 */       return false;
/*     */     }
/*  74 */     SecurityAccRngRelCK other = (SecurityAccRngRelCK)obj;
/*  75 */     boolean eq = true;
/*     */ 
/*  77 */     eq = (eq) && (this.mModelPK.equals(other.mModelPK));
/*  78 */     eq = (eq) && (this.mSecurityAccessDefPK.equals(other.mSecurityAccessDefPK));
/*  79 */     eq = (eq) && (this.mSecurityAccRngRelPK.equals(other.mSecurityAccRngRelPK));
/*     */ 
/*  81 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     sb.append(super.toString());
/*  91 */     sb.append("[");
/*  92 */     sb.append(this.mSecurityAccRngRelPK);
/*  93 */     sb.append("]");
/*  94 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/* 102 */     StringBuffer sb = new StringBuffer();
/* 103 */     sb.append("SecurityAccRngRelCK|");
/* 104 */     sb.append(super.getPK().toTokens());
/* 105 */     sb.append('|');
/* 106 */     sb.append(this.mSecurityAccRngRelPK.toTokens());
/* 107 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static ModelCK getKeyFromTokens(String extKey)
/*     */   {
/* 112 */     String[] token = extKey.split("[|]");
/* 113 */     int i = 0;
/* 114 */     checkExpected("SecurityAccRngRelCK", token[(i++)]);
/* 115 */     checkExpected("ModelPK", token[(i++)]);
/* 116 */     i++;
/* 117 */     checkExpected("SecurityAccessDefPK", token[(i++)]);
/* 118 */     i++;
/* 119 */     checkExpected("SecurityAccRngRelPK", token[(i++)]);
/* 120 */     i = 1;
/* 121 */     return new SecurityAccRngRelCK(ModelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), SecurityAccessDefPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), SecurityAccRngRelPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
/*     */   }
/*     */ 
/*     */   private static void checkExpected(String expected, String found)
/*     */   {
/* 130 */     if (!expected.equals(found))
/* 131 */       throw new IllegalArgumentException("expected=" + expected + " found=" + found);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.SecurityAccRngRelCK
 * JD-Core Version:    0.6.0
 */