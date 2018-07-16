/*     */ package com.cedar.cp.dto.extsys;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExternalSystemPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mExternalSystemId;
/*     */ 
/*     */   public ExternalSystemPK(int newExternalSystemId)
/*     */   {
/*  23 */     this.mExternalSystemId = newExternalSystemId;
/*     */   }
/*     */ 
/*     */   public int getExternalSystemId()
/*     */   {
/*  32 */     return this.mExternalSystemId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mExternalSystemId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ExternalSystemPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ExternalSystemCK)) {
/*  56 */       other = ((ExternalSystemCK)obj).getExternalSystemPK();
/*     */     }
/*  58 */     else if ((obj instanceof ExternalSystemPK))
/*  59 */       other = (ExternalSystemPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mExternalSystemId == other.mExternalSystemId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ExternalSystemId=");
/*  77 */     sb.append(this.mExternalSystemId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mExternalSystemId);
/*  89 */     return "ExternalSystemPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ExternalSystemPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("ExternalSystemPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ExternalSystemPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pExternalSystemId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ExternalSystemPK(pExternalSystemId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.extsys.ExternalSystemPK
 * JD-Core Version:    0.6.0
 */