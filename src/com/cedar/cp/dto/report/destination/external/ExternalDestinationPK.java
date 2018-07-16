/*     */ package com.cedar.cp.dto.report.destination.external;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ExternalDestinationPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mExternalDestinationId;
/*     */ 
/*     */   public ExternalDestinationPK(int newExternalDestinationId)
/*     */   {
/*  23 */     this.mExternalDestinationId = newExternalDestinationId;
/*     */   }
/*     */ 
/*     */   public int getExternalDestinationId()
/*     */   {
/*  32 */     return this.mExternalDestinationId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mExternalDestinationId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ExternalDestinationPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ExternalDestinationCK)) {
/*  56 */       other = ((ExternalDestinationCK)obj).getExternalDestinationPK();
/*     */     }
/*  58 */     else if ((obj instanceof ExternalDestinationPK))
/*  59 */       other = (ExternalDestinationPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mExternalDestinationId == other.mExternalDestinationId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ExternalDestinationId=");
/*  77 */     sb.append(this.mExternalDestinationId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mExternalDestinationId);
/*  89 */     return "ExternalDestinationPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ExternalDestinationPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("ExternalDestinationPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ExternalDestinationPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pExternalDestinationId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ExternalDestinationPK(pExternalDestinationId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.destination.external.ExternalDestinationPK
 * JD-Core Version:    0.6.0
 */