/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;

/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class FinanceCubePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   int mFinanceCubeId;
/*     */ 
/*     */   public FinanceCubePK(int newFinanceCubeId)
/*     */   {
/*  23 */     this.mFinanceCubeId = newFinanceCubeId;
/*     */   }
/*     */ 
/*     */   public int getFinanceCubeId()
/*     */   {
/*  32 */     return this.mFinanceCubeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += String.valueOf(this.mFinanceCubeId).hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     FinanceCubePK other = null;
/*     */ 
/*  55 */     if ((obj instanceof FinanceCubeCK)) {
/*  56 */       other = ((FinanceCubeCK)obj).getFinanceCubePK();
/*     */     }
/*  58 */     else if ((obj instanceof FinanceCubePK))
/*  59 */       other = (FinanceCubePK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mFinanceCubeId == other.mFinanceCubeId);
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" FinanceCubeId=");
/*  77 */     sb.append(this.mFinanceCubeId);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mFinanceCubeId);
/*  89 */     return "FinanceCubePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static FinanceCubePK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
                  int financeCubeId = new Integer(extKey).intValue();
                  return new FinanceCubePK(financeCubeId);
/*     */     }
/*  99 */     if (!extValues[0].equals("FinanceCubePK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'FinanceCubePK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     int pFinanceCubeId = new Integer(extValues[(i++)]).intValue();
/* 106 */     return new FinanceCubePK(pFinanceCubeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.FinanceCubePK
 * JD-Core Version:    0.6.0
 */