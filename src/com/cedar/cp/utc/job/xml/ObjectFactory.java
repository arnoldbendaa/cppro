// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.job.xml;

import com.cedar.cp.utc.job.xml.JobProperties;
import com.cedar.cp.utc.job.xml.JobProperty;
import com.cedar.cp.utc.job.xml.RootJob;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

   private static final QName _Job_QNAME = new QName("", "job");


   public JobProperty createJobProperty() {
      return new JobProperty();
   }

   public JobProperties createJobProperties() {
      return new JobProperties();
   }

   public RootJob createRootJob() {
      return new RootJob();
   }

   @XmlElementDecl(
      namespace = "",
      name = "job"
   )
   public JAXBElement<RootJob> createJob(RootJob value) {
      return new JAXBElement(_Job_QNAME, RootJob.class, (Class)null, value);
   }

}
