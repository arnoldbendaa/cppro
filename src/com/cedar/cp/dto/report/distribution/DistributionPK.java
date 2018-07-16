/*     */ package com.cedar.cp.dto.report.distribution;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DistributionPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mDistributionId;
/*     */ 
/*     */   public DistributionPK(int newDistributionId)
/*     */   {
/*  23 */     this.mDistributionId = newDistributionId;
/*     */   }
/*     */ 
/*     */   public int getDistributionId()
/*     */   {
/*  32 */     return this.mDistributionId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mDistributionId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     DistributionPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof DistributionCK)) {
/*  56 */       other = ((DistributionCK)obj).getDistributionPK();
/*     */     }
/*  58 */     else if ((obj instanceof DistributionPK))
/*  59 */       other = (DistributionPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mDistributionId == other.mDistributionId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" DistributionId=");
/*  77 */     sb.append(this.mDistributionId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mDistributionId);
/*  89 */     return "DistributionPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static DistributionPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("DistributionPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'DistributionPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pDistributionId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new DistributionPK(pDistributionId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.distribution.DistributionPK
 * JD-Core Version:    0.6.0
 */