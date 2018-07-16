/*     */ package com.cedar.cp.ejb.impl.cm.xml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlSchemaType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import javax.xml.datatype.XMLGregorianCalendar;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="changeManagementType", propOrder={"event"})
/*     */ @XmlRootElement
/*     */ public class ChangeManagementType
/*     */ {
/*     */ 
/*     */   @XmlElement(required=true)
/*     */   protected List<ChangeManagementEvent> event;
/*     */ 
/*     */   @XmlAttribute(name="extract-date-time", required=true)
/*     */   @XmlSchemaType(name="dateTime")
/*     */   protected XMLGregorianCalendar extractDateTime;
/*     */ 
/*     */   @XmlAttribute(name="source-system-name", required=true)
/*     */   protected String sourceSystemName;
/*     */ 
/*     */   public List<ChangeManagementEvent> getEvent()
/*     */   {
/*  77 */     if (this.event == null) {
/*  78 */       this.event = new ArrayList();
/*     */     }
/*  80 */     return this.event;
/*     */   }
/*     */ 
/*     */   public XMLGregorianCalendar getExtractDateTime()
/*     */   {
/*  92 */     return this.extractDateTime;
/*     */   }
/*     */ 
/*     */   public void setExtractDateTime(XMLGregorianCalendar value)
/*     */   {
/* 104 */     this.extractDateTime = value;
/*     */   }
/*     */ 
/*     */   public String getSourceSystemName()
/*     */   {
/* 116 */     return this.sourceSystemName;
/*     */   }
/*     */ 
/*     */   public void setSourceSystemName(String value)
/*     */   {
/* 128 */     this.sourceSystemName = value;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.ChangeManagementType
 * JD-Core Version:    0.6.0
 */