/*     */ package com.cedar.cp.dto.report.destination.internal;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class InternalDestinationPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mInternalDestinationId;
/*     */ 
/*     */   public InternalDestinationPK(int newInternalDestinationId)
/*     */   {
/*  23 */     this.mInternalDestinationId = newInternalDestinationId;
/*     */   }
/*     */ 
/*     */   public int getInternalDestinationId()
/*     */   {
/*  32 */     return this.mInternalDestinationId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mInternalDestinationId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     InternalDestinationPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof InternalDestinationCK)) {
/*  56 */       other = ((InternalDestinationCK)obj).getInternalDestinationPK();
/*     */     }
/*  58 */     else if ((obj instanceof InternalDestinationPK))
/*  59 */       other = (InternalDestinationPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mInternalDestinationId == other.mInternalDestinationId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" InternalDestinationId=");
/*  77 */     sb.append(this.mInternalDestinationId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mInternalDestinationId);
/*  89 */     return "InternalDestinationPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static InternalDestinationPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("InternalDestinationPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'InternalDestinationPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pInternalDestinationId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new InternalDestinationPK(pInternalDestinationId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.destination.internal.InternalDestinationPK
 * JD-Core Version:    0.6.0
 */