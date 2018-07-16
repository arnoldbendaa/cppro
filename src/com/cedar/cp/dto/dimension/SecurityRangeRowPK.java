/*     */ package com.cedar.cp.dto.dimension;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class SecurityRangeRowPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mSecurityRangeRowId;
/*     */ 
/*     */   public SecurityRangeRowPK(int newSecurityRangeRowId)
/*     */   {
/*  23 */     this.mSecurityRangeRowId = newSecurityRangeRowId;
/*     */   }
/*     */ 
/*     */   public int getSecurityRangeRowId()
/*     */   {
/*  32 */     return this.mSecurityRangeRowId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mSecurityRangeRowId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     SecurityRangeRowPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof SecurityRangeRowCK)) {
/*  56 */       other = ((SecurityRangeRowCK)obj).getSecurityRangeRowPK();
/*     */     }
/*  58 */     else if ((obj instanceof SecurityRangeRowPK))
/*  59 */       other = (SecurityRangeRowPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mSecurityRangeRowId == other.mSecurityRangeRowId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" SecurityRangeRowId=");
/*  77 */     sb.append(this.mSecurityRangeRowId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mSecurityRangeRowId);
/*  89 */     return "SecurityRangeRowPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static SecurityRangeRowPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("SecurityRangeRowPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'SecurityRangeRowPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pSecurityRangeRowId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new SecurityRangeRowPK(pSecurityRangeRowId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.dimension.SecurityRangeRowPK
 * JD-Core Version:    0.6.0
 */