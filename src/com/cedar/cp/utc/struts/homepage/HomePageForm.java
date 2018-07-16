// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.homepage;

import com.cedar.cp.utc.common.CPForm;
import java.util.List;

public class HomePageForm extends CPForm {

    private List mModel;
    private List mMessages;
    private String refresh = "false";
    private boolean mVirementsToAuthorise;
    private boolean mOverdueItems;
    private boolean mDueItems;

    public List getModel() {
        return this.mModel;
    }

    public void setModel(List mModel) {
        this.mModel = mModel;
    }

    public List getMessages() {
        return this.mMessages;
    }

    public void setMessages(List mMessages) {
        this.mMessages = mMessages;
    }

    public String getRefresh() {
        return this.refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public boolean isVirementsToAuthorise() {
        return this.mVirementsToAuthorise;
    }

    public void setVirementsToAuthorise(boolean virementsToAuthorise) {
        this.mVirementsToAuthorise = virementsToAuthorise;
    }

    public boolean isOverdueItems() {
        return this.mOverdueItems;
    }

    public void setOverdueItems(boolean overdueItems) {
        this.mOverdueItems = overdueItems;
    }

    public boolean isDueItems() {
        return this.mDueItems;
    }

    public void setDueItems(boolean dueItems) {
        this.mDueItems = dueItems;
    }
}
