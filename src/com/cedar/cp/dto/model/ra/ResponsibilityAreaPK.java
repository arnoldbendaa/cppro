/*     */ package com.cedar.cp.dto.model.ra;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ResponsibilityAreaPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mResponsibilityAreaId;
/*     */ 
/*     */   public ResponsibilityAreaPK(int newResponsibilityAreaId)
/*     */   {
/*  23 */     this.mResponsibilityAreaId = newResponsibilityAreaId;
/*     */   }
/*     */ 
/*     */   public int getResponsibilityAreaId()
/*     */   {
/*  32 */     return this.mResponsibilityAreaId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mResponsibilityAreaId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     ResponsibilityAreaPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof ResponsibilityAreaCK)) {
/*  56 */       other = ((ResponsibilityAreaCK)obj).getResponsibilityAreaPK();
/*     */     }
/*  58 */     else if ((obj instanceof ResponsibilityAreaPK))
/*  59 */       other = (ResponsibilityAreaPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mResponsibilityAreaId == other.mResponsibilityAreaId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" ResponsibilityAreaId=");
/*  77 */     sb.append(this.mResponsibilityAreaId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mResponsibilityAreaId);
/*  89 */     return "ResponsibilityAreaPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ResponsibilityAreaPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("ResponsibilityAreaPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ResponsibilityAreaPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pResponsibilityAreaId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new ResponsibilityAreaPK(pResponsibilityAreaId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.ra.ResponsibilityAreaPK
 * JD-Core Version:    0.6.0
 */