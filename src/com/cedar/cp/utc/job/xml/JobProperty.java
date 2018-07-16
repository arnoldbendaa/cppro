// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.job.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "jobProperty",
   propOrder = {"content"}
)
public class JobProperty {

   @XmlValue
   protected String content;
   @XmlAttribute(
      required = true
   )
   protected String name;
   @XmlAttribute(
      required = true
   )
   protected String value;


   public String getContent() {
      return this.content;
   }

   public void setContent(String value) {
      this.content = value;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String value) {
      this.name = value;
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }
}
