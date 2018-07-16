/*     */ package com.cedar.cp.dto.xmlform.rebuild;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class FormRebuildPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mFormRebuildId;
/*     */ 
/*     */   public FormRebuildPK(int newFormRebuildId)
/*     */   {
/*  23 */     this.mFormRebuildId = newFormRebuildId;
/*     */   }
/*     */ 
/*     */   public int getFormRebuildId()
/*     */   {
/*  32 */     return this.mFormRebuildId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mFormRebuildId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     FormRebuildPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof FormRebuildCK)) {
/*  56 */       other = ((FormRebuildCK)obj).getFormRebuildPK();
/*     */     }
/*  58 */     else if ((obj instanceof FormRebuildPK))
/*  59 */       other = (FormRebuildPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mFormRebuildId == other.mFormRebuildId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" FormRebuildId=");
/*  77 */     sb.append(this.mFormRebuildId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mFormRebuildId);
/*  89 */     return "FormRebuildPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static FormRebuildPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("FormRebuildPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'FormRebuildPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pFormRebuildId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new FormRebuildPK(pFormRebuildId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.xmlform.rebuild.FormRebuildPK
 * JD-Core Version:    0.6.0
 */