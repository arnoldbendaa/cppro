/*     */ package com.cedar.cp.dto.model.cc.imp.dyn;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ImportGridPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 131 */   private int mHashCode = -2147483648;
/*     */   int mModelId;
/*     */   int mGridId;
/*     */ 
/*     */   public ImportGridPK(int newModelId, int newGridId)
/*     */   {
/*  24 */     this.mModelId = newModelId;
/*  25 */     this.mGridId = newGridId;
/*     */   }
/*     */ 
/*     */   public int getModelId()
/*     */   {
/*  34 */     return this.mModelId;
/*     */   }
/*     */ 
/*     */   public int getGridId()
/*     */   {
/*  41 */     return this.mGridId;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  49 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  51 */       this.mHashCode += String.valueOf(this.mModelId).hashCode();
/*  52 */       this.mHashCode += String.valueOf(this.mGridId).hashCode();
/*     */     }
/*     */ 
/*  55 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     ImportGridPK other = null;
/*     */ 
/*  65 */     if ((obj instanceof ImportGridCK)) {
/*  66 */       other = ((ImportGridCK)obj).getImportGridPK();
/*     */     }
/*  68 */     else if ((obj instanceof ImportGridPK))
/*  69 */       other = (ImportGridPK)obj;
/*     */     else {
/*  71 */       return false;
/*     */     }
/*  73 */     boolean eq = true;
/*     */ 
/*  75 */     eq = (eq) && (this.mModelId == other.mModelId);
/*  76 */     eq = (eq) && (this.mGridId == other.mGridId);
/*     */ 
/*  78 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ModelId=");
/*  88 */     sb.append(this.mModelId);
/*  89 */     sb.append(",GridId=");
/*  90 */     sb.append(this.mGridId);
/*  91 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  99 */     StringBuffer sb = new StringBuffer();
/* 100 */     sb.append(" ");
/* 101 */     sb.append(this.mModelId);
/* 102 */     sb.append(",");
/* 103 */     sb.append(this.mGridId);
/* 104 */     return "ImportGridPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static ImportGridPK getKeyFromTokens(String extKey)
/*     */   {
/* 109 */     String[] extValues = extKey.split("[|]");
/*     */ 
/* 111 */     if (extValues.length != 2) {
/* 112 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/* 114 */     if (!extValues[0].equals("ImportGridPK")) {
/* 115 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'ImportGridPK|'");
/*     */     }
/* 117 */     extValues = extValues[1].split(",");
/*     */ 
/* 119 */     int i = 0;
/* 120 */     int pModelId = new Integer(extValues[(i++)]).intValue();
/* 121 */     int pGridId = new Integer(extValues[(i++)]).intValue();
/* 122 */     return new ImportGridPK(pModelId, pGridId);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.model.cc.imp.dyn.ImportGridPK
 * JD-Core Version:    0.6.0
 */