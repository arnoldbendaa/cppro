/*     */ package com.coa.portal.util;
/*     */ 
/*     */ import com.coa.portal.client.Application;
/*     */ 
/*     */ public enum PortalEncoding
/*     */ {
/*  14 */   USERSERVICE("00", "com.coa.portal.util.UserServiceDecoder"), 
/*  15 */   SQUIRREL("01", "com.coa.portal.util.SquirrelDecoder"), 
/*  16 */   USERSERVICE2("02", "com.coa.portal.util.UserService2Decoder"), 
/*  17 */   NONE("03", "com.coa.portal.util.NoneDecoder"), 
/*  18 */   AES("04", "com.coa.portal.util.AesDecoder");
/*     */ 
/*     */   private String mPrefix;
/*     */   private String mDecoderClassName;
/*     */ 
/*     */   private PortalEncoding(String prefix, String className)
/*     */   {
/*  30 */     this.mPrefix = prefix;
/*  31 */     this.mDecoderClassName = className;
/*     */   }
/*     */ 
/*     */   public String getPrefix()
/*     */   {
/*  40 */     return this.mPrefix;
/*     */   }
/*     */ 
/*     */   private String getDecoderClassName()
/*     */   {
/*  49 */     return this.mDecoderClassName;
/*     */   }
/*     */ 
/*     */   public static PortalEncoding getEncoding(String stream)
/*     */   {
/*  59 */     if ((stream == null) || (stream.length() < 2)) {
/*  60 */       throw new IllegalArgumentException("The stream does not have an application prefix");
/*     */     }
/*     */ 
/*  66 */     String applicationID = stream.substring(0, 2);
/*     */ 
/*  68 */     for (Application application : Application.values())
/*     */     {
/*  70 */       if (applicationID.equals(application.getId())) {
/*  71 */         return application.getEncoding();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  80 */     throw new IllegalArgumentException("Unknown prefix in stream");
/*     */   }
/*     */ 
/*     */   public PortalDecoderStrategy getDecoderStrategy()
/*     */     throws DecodeException
/*     */   {
/*     */     try
/*     */     {
/*  93 */       Class c = Class.forName(this.mDecoderClassName);
/*  94 */       Object instance = c.newInstance();
/*  95 */       if (!(instance instanceof PortalDecoderStrategy))
/*  96 */         throw new DecodeException("Invalid decoder class");
/*  97 */       return (PortalDecoderStrategy)instance;
/*     */     }
/*     */     catch (Exception e) {
/*     */     }
/* 101 */     throw new DecodeException("Can't load decoder class");
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/portalClient.jar
 * Qualified Name:     com.coa.portal.util.PortalEncoding
 * JD-Core Version:    0.6.0
 */