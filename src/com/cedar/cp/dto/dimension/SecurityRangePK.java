/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SecurityRangePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mSecurityRangeId;
/*     */ 
/*     */   public SecurityRangePK(int newSecurityRangeId)
/*     */   {
/*  23 */     this.mSecurityRangeId = newSecurityRangeId;
/*     */   }
/*     */ 
/*     */   public int getSecurityRangeId()
/*     */   {
/*  32 */     return this.mSecurityRangeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mSecurityRangeId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     SecurityRangePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof SecurityRangeCK)) {
/*  56 */       other = ((SecurityRangeCK)obj).getSecurityRangePK();
/*     */     }
/*  58 */     else if ((obj instanceof SecurityRangePK))
/*  59 */       other = (SecurityRangePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mSecurityRangeId == other.mSecurityRangeId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" SecurityRangeId=");
/*  77 */     sb.append(this.mSecurityRangeId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mSecurityRangeId);
/*  89 */     return "SecurityRangePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static SecurityRangePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("SecurityRangePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'SecurityRangePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pSecurityRangeId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new SecurityRangePK(pSecurityRangeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.SecurityRangePK
 * JD-Core Version:    0.6.0
 */