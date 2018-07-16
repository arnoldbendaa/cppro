/*     */ package com.cedar.cp.dto.defaultuserpref;
/*     */ 
/*     */ import com.cedar.cp.dto.base.PrimaryKey;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class DefaultUserPrefPK extends PrimaryKey
/*     */   implements Serializable
/*     */ {
/* 114 */   private int mHashCode = -2147483648;
/*     */   String mName;
/*     */ 
/*     */   public DefaultUserPrefPK(String newName)
/*     */   {
/*  23 */     this.mName = newName;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  32 */     return this.mName;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  40 */     if (this.mHashCode == -2147483648)
/*     */     {
/*  42 */       this.mHashCode += this.mName.hashCode();
/*     */     }
/*     */ 
/*  45 */     return this.mHashCode;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  53 */     DefaultUserPrefPK other = null;
/*     */ 
/*  55 */     if ((obj instanceof DefaultUserPrefCK)) {
/*  56 */       other = ((DefaultUserPrefCK)obj).getDefaultUserPrefPK();
/*     */     }
/*  58 */     else if ((obj instanceof DefaultUserPrefPK))
/*  59 */       other = (DefaultUserPrefPK)obj;
/*     */     else {
/*  61 */       return false;
/*     */     }
/*  63 */     boolean eq = true;
/*     */ 
/*  65 */     eq = (eq) && (this.mName.equals(other.mName));
/*     */ 
/*  67 */     return eq;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     sb.append(" Name=");
/*  77 */     sb.append(this.mName);
/*  78 */     return sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public String toTokens()
/*     */   {
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     sb.append(" ");
/*  88 */     sb.append(this.mName);
/*  89 */     return "DefaultUserPrefPK|" + sb.toString().substring(1);
/*     */   }
/*     */ 
/*     */   public static DefaultUserPrefPK getKeyFromTokens(String extKey)
/*     */   {
/*  94 */     String[] extValues = extKey.split("[|]");
/*     */ 
/*  96 */     if (extValues.length != 2) {
/*  97 */       throw new IllegalStateException(extKey + ": format incorrect");
/*     */     }
/*  99 */     if (!extValues[0].equals("DefaultUserPrefPK")) {
/* 100 */       throw new IllegalStateException(extKey + ": format incorrect - must start with 'DefaultUserPrefPK|'");
/*     */     }
/* 102 */     extValues = extValues[1].split(",");
/*     */ 
/* 104 */     int i = 0;
/* 105 */     String pName = new String(extValues[(i++)]);
/* 106 */     return new DefaultUserPrefPK(pName);
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp-extracted/utc.war/cp-common.jar
 * Qualified Name:     com.cedar.cp.dto.defaultuserpref.DefaultUserPrefPK
 * JD-Core Version:    0.6.0
 */