// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.user.loggedinusers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.dto.base.EntityListImpl;
import com.cedar.cp.util.common.ICPContext;

public class CPContextCache implements Serializable {

    private static Map<String, Object> sContexts = new ConcurrentHashMap<String, Object>();
    private static Map<Object, String> sIds = new ConcurrentHashMap<Object, String>();

    public static String getCPContextId(Object context) {
        String id = null;
        if (context != null) {
            id = (String) sIds.get(context);
        }

        if (id == null) {
            String id2 = (String) sIds.get(context);
            if (id2 == null) {
                id2 = "CP_" + System.currentTimeMillis();
                sIds.put(context, id2);
                sContexts.put(id2, context);
            }
        }
        return (String) sIds.get(context);
    }

    public static Object getCPContext(String id) {
        return id == null ? null : (Object) sContexts.get(id);
    }

    public static void remove(Object context) {
        String id = (String) sIds.get(context);
        if (id != null) {
            sIds.remove(context);
            sContexts.remove(id);
        }

    }

    public static void removeContextByUserName(List<String> userNames) {
        for (String userName: userNames) {
            for (Object cnx: sContexts.values()) {
                String userId = (String) ((ICPContext) cnx).getUserId();
                if (userName != null && userId != null && userName.equals(userId)) {
                    CPConnection cpcnx = ((ICPContext) cnx).getCPConnection();
                    if (cpcnx != null)
                        cpcnx.close();
                    String contextId = sIds.get(cnx);
                    sIds.remove(cnx);
                    sContexts.remove(contextId);
                    ((ICPContext) cnx).getSession().invalidate();
                    break;
                }
            }
        }
    }

    public static void removeContextByContextId(List<String> contextIds) {
        for (String contextId: contextIds) {
            ICPContext cnx = (ICPContext) sContexts.remove(contextId);
            sIds.remove(cnx);
        }
    }

    public static Map<String, Object> getContextSnapShot() {
        return new HashMap<String, Object>(sContexts);
    }

    public static EntityList getAllLoggedInUsers() {
        Set<Entry<String, Object>> entrySet = CPContextCache.getContextSnapShot().entrySet();
        Iterator<Entry<String, Object>> iterator = entrySet.iterator();

        String[] columns = new String[] { "IntUserId", "UserId", "UserName", "isAdmin", "ClientIP", "ClientHost", "CreationTime", "LastAccessedTime", "ContextId" };
        List<Object[]> rowList = new ArrayList<Object[]>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM kk:mm");
        while (iterator.hasNext()) {
            Entry<String, Object> entry = iterator.next();
            String contextId = entry.getKey();
            ICPContext cacheContext = (ICPContext) entry.getValue();

            if (cacheContext.getSession() != null) {
                Object[] row = new Object[9];
                row[0] = new Integer(cacheContext.getIntUserId());
                row[1] = new String(cacheContext.getUserId());
                row[2] = new String(cacheContext.getUserName());
                row[3] = new Boolean(cacheContext.getUserContext().isAdmin());
                row[4] = new String(cacheContext.getClientIP());
                row[5] = new String(cacheContext.getClientHost());
                row[6] = sdf.format(new Date(cacheContext.getSession().getCreationTime()));
                row[7] = sdf.format(new Date(cacheContext.getSession().getLastAccessedTime()));
                row[8] = contextId;
                rowList.add(row);
            }

        }
        Object[][] rows = new Object[rowList.size()][9];
        int i = 0;
        for (Object[] row: rowList) {
            rows[i] = row;
            i++;
        }
        return new EntityListImpl(columns, rows);
    }
}
