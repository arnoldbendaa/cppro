/*    */ package com.coa.portal.client;
/*    */ 
/*    */ import com.coa.portal.util.PortalEncoding;
/*    */ 
/*    */ public enum Application
/*    */ {
/* 14 */   PORTAL("00", "CedAr Enterprise Portal", PortalEncoding.AES, true), 
/* 15 */   EPROCUREMENT("01", "eProcurement", PortalEncoding.AES, true), 
/* 16 */   EFINANCIALS("02", "eFinancials", PortalEncoding.AES, true), 
/* 17 */   E5("03", "e5", PortalEncoding.AES, false), 
/* 18 */   EANALYSER("04", "eAnalyser", PortalEncoding.AES, true), 
/* 19 */   COA("05", "GUI Chart of Accounts", PortalEncoding.AES, true), 
/* 20 */   CP("06", "Collaborative Planning", PortalEncoding.AES, true), 
/* 21 */   E5_PORTLET("07", "e5 Portlet", PortalEncoding.AES, true), 
/* 22 */   E5_ADMIN_PORTLET("08", "e5 Admin Portlet", PortalEncoding.AES, true), 
/* 23 */   SPREADSHEET_UPLOADER("09", "Spreadsheet Uploader", PortalEncoding.AES, true), 
/* 24 */   EHR("10", "eHR", PortalEncoding.AES, true), 
/* 25 */   EHR_SELF_SERVICE("11", "eHR Self Service", PortalEncoding.AES, true), 
/* 26 */   EBIS("12", "eBIS", PortalEncoding.AES, true), 
/* 27 */   WEB_REPORT_RUNNER("13", "Web Report Runner", PortalEncoding.AES, false), 
/* 28 */   TOPAZ("14", "Topaz", PortalEncoding.AES, false), 
/* 29 */   BUSINESS_OBJECTS("15", "Business Objects", PortalEncoding.NONE, false), 
/* 30 */   ISS("16", "ISS", PortalEncoding.AES, true), 
/* 31 */   EEM("17", "EEM", PortalEncoding.AES, true), 
/* 32 */   CFF("18", "CFF", PortalEncoding.AES, true), 
/* 33 */   PCR("19", "PCR", PortalEncoding.AES, true), 
/* 34 */   AES_TEST("98", "AesTest", PortalEncoding.AES, true), 
/* 35 */   OTHER("99", "Other", PortalEncoding.AES, true);
/*    */ 
/*    */   private String mId;
/*    */   private String mDescription;
/*    */   private PortalEncoding mEncoding;
/*    */   private boolean mUseTimestamp;
/*    */ 
/* 44 */   private Application(String id, String description, PortalEncoding encoding, boolean useTimestamp) { this.mId = id;
/* 45 */     this.mDescription = description;
/* 46 */     this.mEncoding = encoding;
/* 47 */     this.mUseTimestamp = useTimestamp;
/*    */   }
/*    */ 
/*    */   public String getId()
/*    */   {
/* 56 */     return this.mId;
/*    */   }
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 65 */     return this.mDescription;
/*    */   }
/*    */ 
/*    */   public PortalEncoding getEncoding()
/*    */   {
/* 74 */     return this.mEncoding;
/*    */   }
/*    */ 
/*    */   public boolean isUseTimestamp()
/*    */   {
/* 84 */     return this.mUseTimestamp;
/*    */   }
/*    */ 
/*    */   public static Application getApplication(String id)
/*    */   {
/* 94 */     for (Application application : values())
/*    */     {
/* 96 */       if (application.getId().equals(id))
/* 97 */         return application;
/*    */     }
/* 99 */     throw new IllegalArgumentException("Unknown application id");
/*    */   }
/*    */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/portalClient.jar
 * Qualified Name:     com.coa.portal.client.Application
 * JD-Core Version:    0.6.0
 */