/*     */ package com.cedar.cp.dto.model.amm;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class AmmSrcStructureElementPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mAmmSrcStructureElementId;
/*     */ 
/*     */   public AmmSrcStructureElementPK(int newAmmSrcStructureElementId)
/*     */   {
/*  23 */     this.mAmmSrcStructureElementId = newAmmSrcStructureElementId;
/*     */   }
/*     */ 
/*     */   public int getAmmSrcStructureElementId()
/*     */   {
/*  32 */     return this.mAmmSrcStructureElementId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mAmmSrcStructureElementId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     AmmSrcStructureElementPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof AmmSrcStructureElementCK)) {
/*  56 */       other = ((AmmSrcStructureElementCK)obj).getAmmSrcStructureElementPK();
/*     */     }
/*  58 */     else if ((obj instanceof AmmSrcStructureElementPK))
/*  59 */       other = (AmmSrcStructureElementPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mAmmSrcStructureElementId == other.mAmmSrcStructureElementId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" AmmSrcStructureElementId=");
/*  77 */     sb.append(this.mAmmSrcStructureElementId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mAmmSrcStructureElementId);
/*  89 */     return "AmmSrcStructureElementPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static AmmSrcStructureElementPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("AmmSrcStructureElementPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'AmmSrcStructureElementPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pAmmSrcStructureElementId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new AmmSrcStructureElementPK(pAmmSrcStructureElementId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.amm.AmmSrcStructureElementPK
 * JD-Core Version:    0.6.0
 */