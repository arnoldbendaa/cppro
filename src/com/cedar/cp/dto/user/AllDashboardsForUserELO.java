package com.cedar.cp.dto.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.dto.base.AbstractELO;

public class AllDashboardsForUserELO extends AbstractELO {

    public AllDashboardsForUserELO() {
        super(new String[] { "dashboard_uuid", "dashboard_title", "dashboard_type" });
    }

    public void add(String dashboardUUID, String dashboardName, String dashboardType) {
        ArrayList l = new ArrayList();
        l.add(dashboardUUID);
        l.add(dashboardName);// dashboard title
        l.add(dashboardType);
        this.mCollection.add(l);
    }

    public void next() {
        if (this.mIterator == null) {
            this.reset();
        }

        ++this.mCurrRowIndex;
        List l = (List) this.mIterator.next();
        byte index = 0;
        int var4 = index + 1;
    }

}
