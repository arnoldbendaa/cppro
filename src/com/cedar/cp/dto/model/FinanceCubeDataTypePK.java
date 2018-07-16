/*     */ package com.cedar.cp.dto.model;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class FinanceCubeDataTypePK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mFinanceCubeId;
/*     */   short mDataTypeId;
/*     */ 
/*     */   public FinanceCubeDataTypePK(int newFinanceCubeId, short newDataTypeId)
/*     */   {
/*  24 */     this.mFinanceCubeId = newFinanceCubeId;
/*  25 */     this.mDataTypeId = newDataTypeId;
/*     */   }
/*     */ 
/*     */   public int getFinanceCubeId()
/*     */   {
/*  34 */     return this.mFinanceCubeId;
/*     */   }
/*     */ 
/*     */   public short getDataTypeId()
/*     */   {
/*  41 */     return this.mDataTypeId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mFinanceCubeId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mDataTypeId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     FinanceCubeDataTypePK other = null;
/*     */ 
/*  65 */     if ((obj instanceof FinanceCubeDataTypeCK)) {
/*  66 */       other = ((FinanceCubeDataTypeCK)obj).getFinanceCubeDataTypePK();
/*     */     }
/*  68 */     else if ((obj instanceof FinanceCubeDataTypePK))
/*  69 */       other = (FinanceCubeDataTypePK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mFinanceCubeId == other.mFinanceCubeId);
/*  76 */     eq = (eq) && (this.mDataTypeId == other.mDataTypeId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" FinanceCubeId=");
/*  88 */     sb.append(this.mFinanceCubeId);
/*  89 */     sb.append(",DataTypeId=");
/*  90 */     sb.append(this.mDataTypeId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mFinanceCubeId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mDataTypeId);
/* 104 */     return "FinanceCubeDataTypePK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static FinanceCubeDataTypePK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("FinanceCubeDataTypePK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'FinanceCubeDataTypePK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pFinanceCubeId = new Integer(extValues[(i++)]).intValue();
/* 121 */     short pDataTypeId = new Short(extValues[(i++)]).shortValue();
/* 122 */     return new FinanceCubeDataTypePK(pFinanceCubeId, pDataTypeId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.FinanceCubeDataTypePK
 * JD-Core Version:    0.6.0
 */