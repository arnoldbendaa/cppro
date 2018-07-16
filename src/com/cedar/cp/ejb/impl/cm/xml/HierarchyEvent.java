/*     */ package com.cedar.cp.ejb.impl.cm.xml;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name="hierarchyEvent")
/*     */ public class HierarchyEvent
/*     */ {
/*     */ 
/*     */   @XmlAttribute
/*     */   protected HierarchyActionType action;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected HierarchyEventType type;
/*     */ 
/*     */   @XmlAttribute(name="vis_id")
/*     */   protected String visId;
/*     */ 
/*     */   @XmlAttribute(name="orig_vis_id")
/*     */   protected String origVisId;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected String description;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected String parent;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected BigInteger index;
/*     */ 
/*     */   @XmlAttribute
/*     */   protected NullableBoolean credit;
/*     */ 
/*     */   @XmlAttribute(name="aug_credit")
/*     */   protected CrDrOverrideType augCredit;
/*     */ 
/*     */   public HierarchyActionType getAction()
/*     */   {
/*  75 */     return this.action;
/*     */   }
/*     */ 
/*     */   public void setAction(HierarchyActionType value)
/*     */   {
/*  87 */     this.action = value;
/*     */   }
/*     */ 
/*     */   public HierarchyEventType getType()
/*     */   {
/*  99 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setType(HierarchyEventType value)
/*     */   {
/* 111 */     this.type = value;
/*     */   }
/*     */ 
/*     */   public String getVisId()
/*     */   {
/* 123 */     return this.visId;
/*     */   }
/*     */ 
/*     */   public void setVisId(String value)
/*     */   {
/* 135 */     this.visId = value;
/*     */   }
/*     */ 
/*     */   public String getOrigVisId()
/*     */   {
/* 147 */     return this.origVisId;
/*     */   }
/*     */ 
/*     */   public void setOrigVisId(String value)
/*     */   {
/* 159 */     this.origVisId = value;
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 171 */     return this.description;
/*     */   }
/*     */ 
/*     */   public void setDescription(String value)
/*     */   {
/* 183 */     this.description = value;
/*     */   }
/*     */ 
/*     */   public String getParent()
/*     */   {
/* 195 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public void setParent(String value)
/*     */   {
/* 207 */     this.parent = value;
/*     */   }
/*     */ 
/*     */   public BigInteger getIndex()
/*     */   {
/* 219 */     return this.index;
/*     */   }
/*     */ 
/*     */   public void setIndex(BigInteger value)
/*     */   {
/* 231 */     this.index = value;
/*     */   }
/*     */ 
/*     */   public NullableBoolean getCredit()
/*     */   {
/* 243 */     return this.credit;
/*     */   }
/*     */ 
/*     */   public void setCredit(NullableBoolean value)
/*     */   {
/* 255 */     this.credit = value;
/*     */   }
/*     */ 
/*     */   public CrDrOverrideType getAugCredit()
/*     */   {
/* 267 */     return this.augCredit;
/*     */   }
/*     */ 
/*     */   public void setAugCredit(CrDrOverrideType value)
/*     */   {
/* 279 */     this.augCredit = value;
/*     */   }
/*     */ }

/* Location:           /home/oracle/coa/cp.ear/cp.ear_orginal/cp-server.jar
 * Qualified Name:     com.cedar.cp.ejb.impl.cm.xml.HierarchyEvent
 * JD-Core Version:    0.6.0
 */