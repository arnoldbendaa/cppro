// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:34:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.api.base.AdminOnlyException;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.DriverManager;
import com.cedar.cp.api.base.InvalidCredentialsException;
import com.cedar.cp.api.base.UserContext;
import com.cedar.cp.api.base.UserDisabledException;
import com.cedar.cp.api.base.UserLicenseException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.utc.common.CPSystemProperties;
import com.cedar.cp.utc.common.SingleEntryPointMismatchException;
import com.cedar.cp.util.common.ICPContext;
import com.coa.idm.UserRepository;
import com.coa.portal.client.PortalPrincipal;

import edu.umich.auth.cosign.CosignPrincipal;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import jcifs.smb.NtlmPasswordAuthentication;

public class CPContext implements HttpSessionBindingListener, Serializable, ICPContext {

    public static final String ATTRIBUTE_NAME = "cpContext";
    private boolean mLoggedOn = true;
    private transient CPConnection mCPConnection;
    private String mUserName;
    private String mConnectionUrl;
    private String mUserId;
    private String mPassword;
    private boolean mUseRMITunnel;
    private boolean mMobileAPIUser = false;
    private boolean mSingleSignon = false;
    private boolean mPortalUser = false;
    private boolean mCosignSignon = false;
    private boolean mPortalSignon = false;
    private boolean mNtlmSignon = false;
    private boolean mSSOSignon = false;
    private boolean mJvmInit = false;
    private String mClientIP;
    private String mClientHost;
    private transient HttpSession mSession;
    private boolean mForceQuestions;
    private boolean mForceWord;
    private boolean mNewFeaturesEnabled;
    private boolean mButtonsVisible;
    private boolean mShowBudgetActivity;
    private boolean mNewView;
    private boolean mRoadMapAvailable;
    private boolean mDashboardAvailable;

    private CPContext(CPConnection connection, String url, String userid, String password, boolean useRMITunnel, HttpServletRequest request) {
        this.mCPConnection = connection;
        this.mUserName = this.mCPConnection.getUserContext().getUserName();
        this.setNewFeaturesEnabled(this.mCPConnection.getUserContext().isNewFeaturesEnabled());
        this.setShowBudgetActivity(this.mCPConnection.getUserContext().isShowBudgetActivity());
        this.setNewView(this.mCPConnection.getUserContext().isNewView());
        this.setRoadMapAvailable(this.mCPConnection.getUserContext().getRoadMapAvailable());        
        this.setDashboardAvailable(showDashboardIcon(this.mCPConnection.getUserContext().getUserRoles()));
        this.mConnectionUrl = url;
        this.mUserId = userid;
        this.mPassword = password;
        this.mUseRMITunnel = useRMITunnel;
        if (request != null) {
            this.mClientIP = request.getRemoteAddr();
            this.mClientHost = request.getRemoteHost();
            this.mSession = request.getSession();
        }

    }

    private boolean showDashboardIcon(Set<String> dashboardRoles) {
        if (dashboardRoles.contains("Auction Open") || dashboardRoles.contains("Dashboard Free Form Open") || dashboardRoles.contains("Dashboard Hierarchy Open")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getIsUserAdministrator() {
        return this.getUserContext().isAdmin();
    }

    public Boolean isSystemAdministrator(){
        return this.getUserContext().getUserRoles().contains("SystemAdministrator");
    }

    public static CPContext logon(CPSystemProperties sysProps, HttpServletRequest request, CosignPrincipal cosignPrincipal) throws UserLicenseException, InvalidCredentialsException, UserDisabledException, SingleEntryPointMismatchException {
        CPConnection connection;
        try {
            checkSingleEntryPoint(sysProps, request);
            connection = DriverManager.getConnection(sysProps.getConnectionURL(), cosignPrincipal, sysProps.getRemoteConnectionFlag(), sysProps.getRMITunnelFlag(), ConnectionContext.INTERACTIVE_WEB);
        } catch (InvalidCredentialsException var5) {
            throw var5;
        } catch (UserLicenseException var6) {
            throw var6;
        } catch (UserDisabledException var7) {
            throw var7;
        } catch (SingleEntryPointMismatchException var8) {
            throw var8;
        } catch (ValidationException var9) {
            throw new RuntimeException("Cant get cp connection", var9);
        }

        connection.startEntityEventWatcher();
        UserContext userContext = connection.getUserContext();
        return new CPContext(connection, sysProps.getConnectionURL(), cosignPrincipal.getName(), userContext.getPassword(), sysProps.getRMITunnelFlag(), request);
    }

    public static CPContext logon(CPSystemProperties sysProps, HttpServletRequest request, UserRepository ssoPrincipal) throws UserLicenseException, InvalidCredentialsException, UserDisabledException, SingleEntryPointMismatchException {
        CPConnection connection;
        try {
            checkSingleEntryPoint(sysProps, request);
            connection = DriverManager.getConnection(sysProps.getConnectionURL(), ssoPrincipal, sysProps.getRemoteConnectionFlag(), sysProps.getRMITunnelFlag(), ConnectionContext.INTERACTIVE_WEB);
        } catch (InvalidCredentialsException var6) {
            throw var6;
        } catch (UserLicenseException var7) {
            throw var7;
        } catch (UserDisabledException var8) {
            throw var8;
        } catch (SingleEntryPointMismatchException var9) {
            throw var9;
        } catch (ValidationException var10) {
            throw new RuntimeException("Cant get cp connection", var10);
        }

        connection.startEntityEventWatcher();
        UserContext userContext = connection.getUserContext();
        return new CPContext(connection, sysProps.getConnectionURL(), userContext.getLogonString(), userContext.getPassword(), sysProps.getRMITunnelFlag(), request);
    }

    public static CPContext logon(CPSystemProperties sysProps, HttpServletRequest request, PortalPrincipal portalPrincipal) throws UserLicenseException, InvalidCredentialsException, UserDisabledException, SingleEntryPointMismatchException {
        CPConnection connection;
        try {
            checkSingleEntryPoint(sysProps, request);
            connection = DriverManager.getConnection(sysProps.getConnectionURL(), portalPrincipal, sysProps.getRemoteConnectionFlag(), sysProps.getRMITunnelFlag(), ConnectionContext.INTERACTIVE_WEB);
        } catch (InvalidCredentialsException var5) {
            throw var5;
        } catch (UserLicenseException var6) {
            throw var6;
        } catch (UserDisabledException var7) {
            throw var7;
        } catch (SingleEntryPointMismatchException var8) {
            throw var8;
        } catch (ValidationException var9) {
            throw new RuntimeException("Cant get cp connection", var9);
        } catch (Throwable var10) {
            throw new RuntimeException("Cant get cp connection", var10);
        }

        connection.startEntityEventWatcher();
        UserContext userContext = connection.getUserContext();
        return new CPContext(connection, sysProps.getConnectionURL(), portalPrincipal.getName(), userContext.getPassword(), sysProps.getRMITunnelFlag(), request);
    }

    public static CPContext logon(CPSystemProperties sysProps, HttpServletRequest request, NtlmPasswordAuthentication ntlmPrincipal) throws UserLicenseException, InvalidCredentialsException, UserDisabledException, SingleEntryPointMismatchException {
        CPConnection connection;
        try {
            checkSingleEntryPoint(sysProps, request);
            connection = DriverManager.getConnection(sysProps.getConnectionURL(), ntlmPrincipal, sysProps.getRemoteConnectionFlag(), sysProps.getRMITunnelFlag(), ConnectionContext.INTERACTIVE_WEB);
        } catch (InvalidCredentialsException var5) {
            throw var5;
        } catch (UserLicenseException var6) {
            throw var6;
        } catch (UserDisabledException var7) {
            throw var7;
        } catch (SingleEntryPointMismatchException var8) {
            throw var8;
        } catch (ValidationException var9) {
            throw new RuntimeException("Cant get cp connection", var9);
        }

        connection.startEntityEventWatcher();
        UserContext userContext = connection.getUserContext();
        return new CPContext(connection, sysProps.getConnectionURL(), ntlmPrincipal.getUsername(), userContext.getPassword(), sysProps.getRMITunnelFlag(), request);
    }

    public static CPContext logon(CPSystemProperties sysProps, HttpServletRequest request, String userId, String password) throws UserLicenseException, InvalidCredentialsException, UserDisabledException, SingleEntryPointMismatchException, AdminOnlyException {
        checkSingleEntryPoint(sysProps, request);
        return logon(sysProps.getConnectionURL(), userId, password, sysProps.getRemoteConnectionFlag(), sysProps.getRMITunnelFlag(), true, request);
    }

    private static void checkSingleEntryPoint(CPSystemProperties sysProps, HttpServletRequest request) throws SingleEntryPointMismatchException {
        if (sysProps.getSingleEntryPoint() != null && sysProps.getSingleEntryPoint().trim().length() > 0) {
            HttpSession session = request.getSession();
            Object attr = session.getAttribute("cp30.initial.request");
            if (attr == null) {
                throw new SingleEntryPointMismatchException();
            }
        }

    }

    public static CPContext logon(String url, String userId, String password, boolean remote, boolean useRMIHttpTunnel, boolean startWatcher, HttpServletRequest request) throws UserLicenseException, InvalidCredentialsException, UserDisabledException, AdminOnlyException {
        CPConnection connection;
        try {
            connection = DriverManager.getConnection(url, userId, password, remote, useRMIHttpTunnel, ConnectionContext.INTERACTIVE_WEB);
        } catch (InvalidCredentialsException var9) {
            throw var9;
        } catch (UserLicenseException var10) {
            throw var10;
        } catch (UserDisabledException var11) {
            throw var11;
        } catch (AdminOnlyException aoe) {
            throw aoe;
        } catch (ValidationException var12) {
            throw new RuntimeException("Cant get cp connection", var12);
        }

        if (startWatcher) {
            connection.startEntityEventWatcher();
        }

        return new CPContext(connection, url, userId, password, useRMIHttpTunnel, request);
    }

    public void logoff() {
        synchronized ("cpContext") {
            if (this.mLoggedOn) {
                if (this.mCPConnection != null) {
                    this.mCPConnection.add2LogonHistory(-1, this.mUserId);
                    this.mCPConnection.close();
                }

                this.mCPConnection = null;
                this.mLoggedOn = false;
                this.mSession = null;
            }

        }
    }

    public void mobileLogoff() {
        synchronized ("cpContext") {
            if (this.mLoggedOn) {
                if (this.mCPConnection != null) {
                    this.mCPConnection.add2LogonHistory(-1, this.mUserId + "_mobile");
                    this.mCPConnection.close();
                }

                this.mCPConnection = null;
                this.mLoggedOn = false;
                this.mSession = null;
            }

        }
    }

    private void destroy() {
        if (this.mLoggedOn) {
            this.logoff();
        }

    }

    public String getUserName() {
        return this.mUserName;
    }

    public void setUserName(String name) {
        this.mUserName = name;
    }

    public UserContext getUserContext() {
        return this.mCPConnection.getUserContext();
    }

    public String getUserId() {
        return this.mUserId;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public String getConnectionURL() {
        return this.mConnectionUrl;
    }

    public CPConnection getCPConnection() {
        return this.mCPConnection;
    }

    public void valueBound(HttpSessionBindingEvent event) {
    }

    public void valueUnbound(HttpSessionBindingEvent event) {
        this.destroy();
    }

    public boolean isMustChangePassword() {
        return !this.isSingleSignon() && !this.isCosignSignon() && !this.isNtlmSignon() && !this.isSSOSignon() ? this.getUserContext().userMustChangePassword() : false;
    }

    public void setPassword(String pPassword) {
        this.mPassword = pPassword;
    }

    public boolean isUseRMITunnel() {
        return this.mUseRMITunnel;
    }

    public String getUseRMITunnel() {
        return this.mUseRMITunnel ? "true" : "false";
    }

    public void setUseRMITunnel(boolean useRMITunnel) {
        this.mUseRMITunnel = useRMITunnel;
    }

    public boolean isPortalUser() {
        return this.mPortalUser;
    }

    public void setPortalUser(boolean portalUser) {
        this.mPortalUser = portalUser;
        this.setSingleSignon(portalUser);
    }

    public boolean isMobileAPIUser() {
        return mMobileAPIUser;
    }

    public void setMobileAPIUser(boolean mobileAPIUser) {
        this.mMobileAPIUser = mobileAPIUser;
    }

    public boolean isSingleSignon() {
        return this.mSingleSignon;
    }

    public boolean isCosignSignon() {
        return this.mCosignSignon;
    }

    public void setCosignSignon(boolean cosignSignon) {
        this.mCosignSignon = cosignSignon;
    }

    public String getCosignSignon() {
        return this.mCosignSignon ? "true" : "false";
    }

    public boolean isPortalSignon() {
        return this.mPortalSignon;
    }

    public void setPortalSignon(boolean portalSignon) {
        this.mPortalSignon = portalSignon;
    }

    public String getPortalSignon() {
        return this.mPortalSignon ? "true" : "false";
    }

    public boolean isNtlmSignon() {
        return this.mNtlmSignon;
    }

    public void setNtlmSignon(boolean ntlmSignon) {
        this.mNtlmSignon = ntlmSignon;
    }

    public String getNtlmSignon() {
        return this.mNtlmSignon ? "true" : "false";
    }

    public void setSingleSignon(boolean singleSignon) {
        this.mSingleSignon = singleSignon;
    }

    public boolean isSSOSignon() {
        return this.mSSOSignon;
    }

    public void setSSOSignon(boolean SSOSignon) {
        this.mSSOSignon = SSOSignon;
    }

    public String getSSOSignon() {
        return this.isSSOSignon() ? "true" : "false";
    }

    public boolean isJvmInit() {
        return this.mJvmInit;
    }

    public void setJvmInit(boolean jvmInit) {
        this.mJvmInit = jvmInit;
    }

    public int getIntUserId() {
        return this.getUserContext() != null ? this.getUserContext().getUserId() : 0;
    }

    public HttpSession getSession() {
        return this.mSession;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CPContext");
        if (this.mClientHost != null || this.mClientIP != null) {
            sb.append("{");
            if (this.mClientHost != null) {
                sb.append("clientHost=\'").append(this.mClientHost).append('\'');
            }

            if (this.mClientIP != null) {
                sb.append(", clientIP=\'").append(this.mClientIP).append('\'');
            }

            sb.append('}');
        }

        sb.append("{");
        sb.append("mUserName=\'").append(this.mUserName).append('\'');
        sb.append(", mUserId=\'").append(this.mUserId).append('\'');
        sb.append(", mLoggedOn=").append(this.mLoggedOn);
        sb.append(", mPortalUser=").append(this.mPortalUser);
        sb.append(", mMobileAPIUser=").append(this.mMobileAPIUser);
        sb.append(", mSingleSignon=").append(this.mSingleSignon);
        sb.append(", mCosignSignon=").append(this.mCosignSignon);
        sb.append(", mPortalSignon=").append(this.mPortalSignon);
        sb.append(", mNtlmSignon=").append(this.mNtlmSignon);
        sb.append(", roadMapAvailable=").append(this.mRoadMapAvailable);
        sb.append(", dashboardAvailable=").append(this.mDashboardAvailable);
        sb.append('}');
        return sb.toString();
    }

    public String getClientIP() {
        return this.mClientIP;
    }

    public String getClientHost() {
        return this.mClientHost;
    }

    public String getUserPreference(String name) {
        UserContext userContext = this.mCPConnection.getUserContext();
        Map preferences = userContext.getUserPreferenceValues();
        String result = (String) preferences.get(name);
        if (result == null) {
            result = "";
        }

        return result;
    }

    public void clearCache() {
        if (this.getCPConnection() != null) {
            this.getCPConnection().getClientCache().clear();
        }

    }

    public boolean isForceQuestions() {
        return mForceQuestions;
    }

    public void setForceQuestions(boolean forceQuestions) {
        mForceQuestions = forceQuestions;
    }

    public boolean isForceWord() {
        return mForceWord;
    }

    public void setForceWord(boolean mForceWord) {
        this.mForceWord = mForceWord;
    }

    public boolean isNewFeaturesEnabled() {
        return mNewFeaturesEnabled;
    }

    public void setNewFeaturesEnabled(boolean mNewFeaturesEnabled) {
        this.mNewFeaturesEnabled = mNewFeaturesEnabled;
    }

    public boolean areButtonsVisible() {
        return mButtonsVisible;
    }

    public void setButtonsVisible(boolean mButtonsVisible) {
        this.mButtonsVisible = mButtonsVisible;
    }

    public boolean isShowBudgetActivity() {
        return mShowBudgetActivity;
    }

    public void setShowBudgetActivity(boolean mShowBudgetActivity) {
        this.mShowBudgetActivity = mShowBudgetActivity;
    }

    public boolean isNewView() {
        return mNewView;
    }

    public void setNewView(boolean mNewView) {
        this.mNewView = mNewView;
    }

    public boolean getRoadMapAvailable() {
        return mRoadMapAvailable;
    }

    public void setRoadMapAvailable(boolean value) {
        this.mRoadMapAvailable = value;
    }

    public boolean getDashboardAvailable() {
        return mDashboardAvailable;
    }

    public void setDashboardAvailable(boolean mDashboardAvailable) {
        this.mDashboardAvailable = mDashboardAvailable;
    }

}
