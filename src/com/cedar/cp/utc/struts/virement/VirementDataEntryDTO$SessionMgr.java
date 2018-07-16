// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

public class VirementDataEntryDTO$SessionMgr implements Serializable {

   private Map mMap = new HashMap();
   private static final String SESSION_MGR_KEY = "VirementDataEntryDTO.SessionMgr";


   public VirementDataEntryDTO$SessionMgr(HttpSession session) {
      session.setAttribute("VirementDataEntryDTO.SessionMgr", this);
   }

   public static void save(HttpSession session, VirementDataEntryDTO dto) {
      VirementDataEntryDTO$SessionMgr sessionMgr = querySessionMgr(session);
      sessionMgr.put(dto.getKey(), dto);
   }

   public static VirementDataEntryDTO load(HttpSession session, String key) {
      VirementDataEntryDTO$SessionMgr sessionMgr = querySessionMgr(session);
      return sessionMgr.get(key);
   }

   public static void remove(HttpSession session, String key) {
      VirementDataEntryDTO$SessionMgr sessionMgr = querySessionMgr(session);
      sessionMgr.remove(key);
   }

   private static VirementDataEntryDTO$SessionMgr querySessionMgr(HttpSession session) {
      VirementDataEntryDTO$SessionMgr mgr = (VirementDataEntryDTO$SessionMgr)session.getAttribute("VirementDataEntryDTO.SessionMgr");
      if(mgr == null) {
         mgr = new VirementDataEntryDTO$SessionMgr(session);
      }

      return mgr;
   }

   private void remove(String key) {
      this.mMap.remove(key);
   }

   private VirementDataEntryDTO get(String key) {
      return (VirementDataEntryDTO)this.mMap.get(key);
   }

   private void put(String key, VirementDataEntryDTO dto) {
      this.mMap.put(key, dto);
   }
}
