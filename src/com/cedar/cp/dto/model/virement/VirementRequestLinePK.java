/*     */ package com.cedar.cp.dto.model.virement;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class VirementRequestLinePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mRequestLineId;
/*     */ 
/*     */   public VirementRequestLinePK(int newRequestLineId)
/*     */   {
/*  23 */     this.mRequestLineId = newRequestLineId;
/*     */   }
/*     */ 
/*     */   public int getRequestLineId()
/*     */   {
/*  32 */     return this.mRequestLineId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mRequestLineId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     VirementRequestLinePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof VirementRequestLineCK)) {
/*  56 */       other = ((VirementRequestLineCK)obj).getVirementRequestLinePK();
/*     */     }
/*  58 */     else if ((obj instanceof VirementRequestLinePK))
/*  59 */       other = (VirementRequestLinePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mRequestLineId == other.mRequestLineId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" RequestLineId=");
/*  77 */     sb.append(this.mRequestLineId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mRequestLineId);
/*  89 */     return "VirementRequestLinePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static VirementRequestLinePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("VirementRequestLinePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'VirementRequestLinePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pRequestLineId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new VirementRequestLinePK(pRequestLineId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.virement.VirementRequestLinePK
 * JD-Core Version:    0.6.0
 */