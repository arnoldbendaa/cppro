// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.utc.common.JNLPPostProcessor$1;
import com.cedar.cp.utc.common.JNLPPostProcessor$ByteArrayPrintWriter;
import com.cedar.cp.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JNLPPostProcessor implements Filter {

   private ServletContext mServletContext;
   private Log mLog = new Log(this.getClass());


   public void init(FilterConfig filterConfig) throws ServletException {
      this.mServletContext = filterConfig.getServletContext();
   }

   public String httpReqLine(HttpServletRequest req) {
      StringBuffer ret = req.getRequestURL();
      String query = req.getQueryString();
      if(query != null) {
         ret.append("?").append(query);
      }

      return ret.toString();
   }

   public String getHeaders(HttpServletRequest req) throws IOException {
      Enumeration en = req.getHeaderNames();
      StringBuffer sb = new StringBuffer();

      while(en.hasMoreElements()) {
         String name = (String)en.nextElement();
         sb.append(name).append(": ").append(req.getHeader(name)).append("\n");
      }

      return sb.toString();
   }

   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      HttpServletRequest hsr = (HttpServletRequest)servletRequest;
      HttpServletResponse resp = (HttpServletResponse)servletResponse;
      this.mLog.debug("doFilter", "Accessing filter for " + this.httpReqLine(hsr) + " " + hsr.getMethod());
      JNLPPostProcessor$ByteArrayPrintWriter pw = new JNLPPostProcessor$ByteArrayPrintWriter((JNLPPostProcessor$1)null);
      JNLPPostProcessor$1 wrappedResp = new JNLPPostProcessor$1(this, resp, pw, resp);
      filterChain.doFilter(servletRequest, wrappedResp);
      byte[] bytes = pw.toByteArray();
      this.mLog.debug("doFilter", "JNLPPostProcess::bytes in wrapped response = " + bytes.length);
      if(bytes != null && bytes.length != 0) {
         try {
            ByteArrayInputStream e = new ByteArrayInputStream(bytes);
            BufferedReader br = new BufferedReader(new InputStreamReader(e));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(baos));

            String line;
            while((line = br.readLine()) != null) {
               StringTokenizer bytesToWrite = new StringTokenizer(line, "${}", true);

               while(bytesToWrite.hasMoreTokens()) {
                  String token = bytesToWrite.nextToken();
                  if(token.equals("$") && bytesToWrite.hasMoreTokens()) {
                     token = bytesToWrite.nextToken();
                     if(token.equals("{") && bytesToWrite.hasMoreTokens()) {
                        token = bytesToWrite.nextToken();
                        String attribute = hsr.getParameter(token);
                        if("systemName".equals(token)) {
                           attribute = URLDecoder.decode(attribute, "UTF-8");
                        }

                        String value = attribute != null?attribute:token;
                        this.mLog.debug("doFilter", "JNLPPostProcessor::expanding user macro [" + token + "] to [" + value + "]");
                        bw.write(value);
                        token = bytesToWrite.nextToken();
                     } else {
                        bw.write("$");
                        bw.write(token);
                     }
                  } else {
                     bw.write(token);
                  }
               }
            }

            bw.flush();
            bw.close();
            this.mLog.debug("doFilter", "Converted file is [" + baos.toString() + "]");
            byte[] bytesToWrite1 = baos.toByteArray();
            resp.setContentLength(bytesToWrite1.length);
            resp.getOutputStream().write(bytesToWrite1);
            this.mLog.debug("doFilter", "JNLPPostProcessor::setContentLength = " + bytesToWrite1.length);
         } catch (Exception var18) {
            throw new ServletException("Unable to parse JNLP File", var18);
         }
      } else {
         this.mLog.debug("doFilter", "No content!");
      }

   }

   public void destroy() {
      this.mLog.debug("doFilter", "Destroying filter...");
   }
}
