// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Hierarchy;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RepositorySelector;
import org.apache.log4j.spi.RootLogger;
import org.apache.log4j.xml.DOMConfigurator;
import org.w3c.dom.Document;

public class CPRepositorySelector implements RepositorySelector {

   private static boolean initialized = false;
   private static Object guard = LogManager.getRootLogger();
   private static Map repositories = new HashMap();
   private static LoggerRepository defaultRepository;


   public static synchronized void init(ServletContextEvent config) {
      if(!initialized) {
         defaultRepository = LogManager.getLoggerRepository();
         CPRepositorySelector hierarchy = new CPRepositorySelector();
         LogManager.setRepositorySelector(hierarchy, guard);
         initialized = true;
      }

      Hierarchy hierarchy1 = new Hierarchy(new RootLogger(Level.DEBUG));
      loadLog4JConfig(config, hierarchy1);
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      repositories.put(loader, hierarchy1);
   }

   public static synchronized void removeFromRepository() {
      repositories.remove(Thread.currentThread().getContextClassLoader());
   }

   private static void loadLog4JConfig(ServletContextEvent config, Hierarchy hierarchy) {
      try {
         String e = "/WEB-INF/classes/log4j.xml";
         InputStream log4JConfig = config.getServletContext().getResourceAsStream(e);
         Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(log4JConfig);
         DOMConfigurator conf = new DOMConfigurator();
         conf.doConfigure(doc.getDocumentElement(), hierarchy);
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   public LoggerRepository getLoggerRepository() {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      LoggerRepository repository = (LoggerRepository)repositories.get(loader);
      return repository == null?defaultRepository:repository;
   }

}
