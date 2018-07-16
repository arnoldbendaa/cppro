/*    */ package com.coa.idm.filter;
/*    */ 
/*    */ public enum SSOProviderType
/*    */ {
/* 12 */   NONE("com.coa.idm.filter.providers.NoneProvider"), 
/* 13 */   OPENSSO("com.coa.idm.filter.providers.OpenSSOProvider"), 
/* 14 */   COSIGN("com.coa.idm.filter.providers.CoSignProvider");
/*    */ 
/*    */   private String mProviderClass;
/*    */ 
/*    */   private SSOProviderType(String className) {
/* 20 */     this.mProviderClass = className;
/*    */   }
/*    */ 
/*    */   public String getProviderClass()
/*    */   {
/* 25 */     return this.mProviderClass;
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/IdentityManager.jar
 * Qualified Name:     com.coa.idm.filter.SSOProviderType
 * JD-Core Version:    0.6.0
 */