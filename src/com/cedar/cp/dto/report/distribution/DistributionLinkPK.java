/*     */ package com.cedar.cp.dto.report.distribution;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DistributionLinkPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mDistributionId;
/*     */   int mDistributionLinkId;
/*     */ 
/*     */   public DistributionLinkPK(int newDistributionId, int newDistributionLinkId)
/*     */   {
/*  24 */     this.mDistributionId = newDistributionId;
/*  25 */     this.mDistributionLinkId = newDistributionLinkId;
/*     */   }
/*     */ 
/*     */   public int getDistributionId()
/*     */   {
/*  34 */     return this.mDistributionId;
/*     */   }
/*     */ 
/*     */   public int getDistributionLinkId()
/*     */   {
/*  41 */     return this.mDistributionLinkId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mDistributionId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mDistributionLinkId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     DistributionLinkPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof DistributionLinkCK)) {
/*  66 */       other = ((DistributionLinkCK)obj).getDistributionLinkPK();
/*     */     }
/*  68 */     else if ((obj instanceof DistributionLinkPK))
/*  69 */       other = (DistributionLinkPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mDistributionId == other.mDistributionId);
/*  76 */     eq = (eq) && (this.mDistributionLinkId == other.mDistributionLinkId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" DistributionId=");
/*  88 */     sb.append(this.mDistributionId);
/*  89 */     sb.append(",DistributionLinkId=");
/*  90 */     sb.append(this.mDistributionLinkId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mDistributionId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mDistributionLinkId);
/* 104 */     return "DistributionLinkPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static DistributionLinkPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("DistributionLinkPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'DistributionLinkPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pDistributionId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pDistributionLinkId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new DistributionLinkPK(pDistributionId, pDistributionLinkId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.report.distribution.DistributionLinkPK
 * JD-Core Version:    0.6.0
 */