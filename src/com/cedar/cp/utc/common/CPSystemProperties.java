// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.common;

import com.cedar.cp.util.Log;
import com.cedar.cp.util.db.DBAccessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

public class CPSystemProperties {

    protected Log mLog = new Log(this.getClass());
    public static final String ATTRIBUTE_NAME = "cpSystemProperties";
    public static final String DISABLE_IE = "WEB: Disable Internet Explorer";
    public static final String DISABLE_FF = "WEB: Disable Firefox";
    public static final String DISABLE_CHROME = "WEB: Disable Chrome";
    public static final String SYS_NAME = "WEB: Environment Name";
    public static final String CONNECTION_URL = "WEB: Connection URL";
    public static final String REMOTE_CONNECTION_FLAG = "WEB: Remote Connection Flag";
    public static final String RMI_CONNECT_HTTP_FLAG = "WEB: RMI Connect over HTTP Flag";
    public static final String CP_CLIENT_CONNECT_FLAG = "WEB: Use CP ClientDispatcher";
    public static final String MAILTO_ADMINISTRATOR = "WEB: Contact Administrator";
    public static final String WEB_STATE = "WEB: System State";
    public static final String PATCH_STATUS = "SYS: Patch Status";
    public static final String CP_SUPPORT_CONTACT = "WEB: COA Solutions Support Address";
    public static final String SITE_ID = "WEB: Site Id";
    public static final String STATUS_ALERT = "WEB: Status System Alert";
    public static final String STATUS_ALERT_FROM_ADDRESS = "WEB: Alert from mail address";
    public static final String STATUS_ALERT_MESSAGE_TYPE = "WEB: Alert message type";
    public static final String STATUS_CHANGE_MESSAGE = "WEB: Status Change Message";
    public static final String CP_ROOT_URL = "WEB: Root URL";
    public static final String CYCLE_WARNING_DAYS = "WEB: Status Warning Days";
    public static final String LOGO_ID = "WEB: Logo Id";
    public static final String ALLOW_PASSTHRU = "WEB: Allow Passthru";
    public static final String SINGLE_ENTRY_POINT = "WEB: Single Entry Point";
    public static final String MAX_ATTACH_FILE_SIZE = "WEB: Max File Size";
    public static final String LOG_OFF_URL = "WEB: Logged off page";
    public static final String SESSION_TIMEOUT = "WEB: Session Timeout";
    public static final String PERFORMANCE_LOGGER = "SYS: Performance Log Level";
    public static final String RUN_LEVEL = "SYS: Run Level";
    public static final String RESET_METHOD = "RST: Reset Method";
    public static final String RESET_STRIKES_ALLOWED = "RST: Reset Strikes Allowed";
    public static final String RESET_LINK_VALIDITY = "RST: Reset Link validity";
    public static final String FORCE_SECURITY_QUESTIONS = "RST: Reset Force Questions";
    public static final String FULL_QUESTION_COUNT = "RST: Reset Full Question Count";
    public static final String VERIFY_QUESTION_COUNT = "RST: Reset Verify Question Count";
    public static final String DEVELOPMENT_NOTIFICATION = "WEB: Development Notification";
    
    private Properties mProperties = new Properties();
    private CPAuthenticationPolicy mActiveAuthenticationPolicy;
    private int mLogLevel = 20000;

    public CPSystemProperties() {
        init();
        CPPerformanceFilter.mPerformLogLevel = this.getPerformanceLogLevel();
    }

    public static CPSystemProperties getSystemProperties(Object o) {
        CPSystemProperties props = null;
        if ((o instanceof FilterConfig)) {
            FilterConfig config = (FilterConfig) o;
            props = (CPSystemProperties) config.getServletContext().getAttribute("cpSystemProperties");
        } else if ((o instanceof HttpServletRequest)) {
            HttpServletRequest request = (HttpServletRequest) o;
            HttpSession session = request.getSession();
            ServletContext servletContext = session.getServletContext();
            props = (CPSystemProperties) servletContext.getAttribute("cpSystemProperties");
        } else if ((o instanceof HttpSessionEvent)) {
            HttpSessionEvent event = (HttpSessionEvent) o;
            props = (CPSystemProperties) event.getSession().getServletContext().getAttribute("cpSystemProperties");
        }

        if (props != null) {
            props.needsLoading();
        }

        if (props == null) {
            props = setSystemProperties(o);
        }

        return props;
    }

    public static CPSystemProperties setSystemProperties(Object o) {
        CPSystemProperties props = new CPSystemProperties();
        if ((o instanceof FilterConfig)) {
            FilterConfig config = (FilterConfig) o;
            config.getServletContext().setAttribute("cpSystemProperties", props);
        } else if ((o instanceof HttpServletRequest)) {
            HttpServletRequest request = (HttpServletRequest) o;
            HttpSession session = request.getSession();
            ServletContext servletContext = session.getServletContext();
            servletContext.setAttribute("cpSystemProperties", props);
        } else if ((o instanceof HttpSessionEvent)) {
            HttpSessionEvent event = (HttpSessionEvent) o;
            event.getSession().getServletContext().setAttribute("cpSystemProperties", props);
        } else if ((o instanceof ServletContextEvent)) {
            ServletContextEvent event = (ServletContextEvent) o;
            event.getServletContext().setAttribute("cpSystemProperties", props);
        } else {
            throw new IllegalStateException("props not set");
        }

        return props;
    }

    private void init() {
        CPSystemProperties$DBHelper dbHelper = null;
        try {
            dbHelper = new CPSystemProperties$DBHelper(this);
            dbHelper.loadProperties();
            dbHelper.loadActiveAuthenticationPolicy();
        } catch (Exception e) {
            mLog.info("init", "Unable to load props");
        } finally {
            if (dbHelper != null)
                dbHelper.close();
        }
    }

    public String getSystemName() {
        return this.mProperties.getProperty("WEB: Environment Name", "System Name not set !!");
    }

    public String getPatchStatus() {
        return this.mProperties.getProperty("SYS: Patch Status", "");
    }

    public String getConnectionURL() {
        return this.mProperties.getProperty("WEB: Connection URL", "cpapi:jboss:localhost:1099");
    }

    public boolean getRemoteConnectionFlag() {
        String flag = this.mProperties.getProperty("WEB: Remote Connection Flag", "true").toLowerCase();
        this.mLog.debug("getRemoteConnectionFlag", "Remote EJB Connection flag is " + flag);
        return "true".equals(flag) || "yes".equals(flag);
    }

    public boolean getRMITunnelFlag() {
        String flag = this.mProperties.getProperty("WEB: RMI Connect over HTTP Flag", "true").toLowerCase();
        this.mLog.debug("getRMITunnelFlag", "RMI Http Tunnel flag is " + flag);
        return "true".equals(flag) || "yes".equals(flag);
    }

    public boolean getUseCPDispatcher() {
        String flag = this.mProperties.getProperty("WEB: Use CP ClientDispatcher", "false").toLowerCase();
        this.mLog.debug("getUseCPDispatcher", "Use CP Client Dispatcher flag is " + flag);
        return "true".equals(flag) || "yes".equals(flag);
    }

    public String getMailto() {
        return this.mProperties.getProperty("WEB: Contact Administrator", "");
    }

    public String getSystemState() {
        return this.mProperties.getProperty("WEB: System State", "production");
    }

    public boolean isProduction() {
        boolean result = true;
        if (!this.getSystemState().equalsIgnoreCase("production")) {
            result = false;
        }

        return result;
    }

    public boolean isDev() {
        boolean result = true;
        if (!this.getSystemState().equalsIgnoreCase("dev")) {
            result = false;
        }

        return result;
    }

    public String getCPSupportAddress() {
        return this.mProperties.getProperty("WEB: COA Solutions Support Address", "supportcp@cedar.com");
    }

    public String getSiteId() {
        return this.mProperties.getProperty("WEB: Site Id", "Test ID");
    }

    public boolean isStatusAlert() {
        String flag = this.getStatusAlert().toLowerCase();
        return "true".equals(flag) || "yes".equals(flag);
    }

    public String getStatusAlert() {
        return this.mProperties.getProperty("WEB: Status System Alert", "false");
    }

    public String getStatusAlertAddress() {
        return this.mProperties.getProperty("WEB: Alert from mail address", "cpsystem@cedar.com");
    }

    public String getStatusAlertMessageType() {
        return this.mProperties.getProperty("WEB: Alert message type", "0");
    }

    public int getIntStatusAlertMessageType() {
        return Integer.parseInt(this.mProperties.getProperty("WEB: Alert message type", "0"));
    }

    public int getSystemLogoId() {
        return Integer.parseInt(this.mProperties.getProperty("WEB: Logo Id", "0"));
    }

    public int getSessionTimeout() {
        return Integer.parseInt(this.mProperties.getProperty("WEB: Session Timeout", "120"));
    }

    public String getRootUrl() {
        return this.mProperties.getProperty("WEB: Root URL", "http://localhost/cp30");
    }

    public String getStatusChangeMessage() {
        return this.mProperties.getProperty("WEB: Status Change Message", "false");
    }

    public boolean isForceStateChangeMessage() {
        return Boolean.valueOf(this.getStatusChangeMessage()).booleanValue();
    }

    public String getRMIPort() {
        String value = this.getConnectionURL();
        value = value.substring(value.lastIndexOf(":") + 1);
        return value;
    }

    public boolean isInternalAuthentication() {
        return this.mActiveAuthenticationPolicy.isInternalTechnique();
    }

    public boolean isCosignSignonFilterEnabled() {
        return this.mActiveAuthenticationPolicy.isCosignTechnique();
    }

    public boolean isSSOSignonFilterEnabled() {
        return this.mActiveAuthenticationPolicy.isSSOTechnique();
    }

    public boolean isNtlmSignonFilterEnabled() {
        return this.mActiveAuthenticationPolicy.isNtlmTechnique();
    }

    public String getCosignConfigurationFile() {
        return this.mActiveAuthenticationPolicy.getCosignConfigurationFile();
    }

    public String getSingleEntryPoint() {
        return this.mProperties.getProperty("WEB: Single Entry Point");
    }

    public int getMaxUploadFileSize() {
        Integer result = Integer.valueOf(3000000);
        String propValue = this.mProperties.getProperty("WEB: Max File Size", "3000000");

        try {
            result = Integer.valueOf(Integer.parseInt(propValue));
        } catch (NumberFormatException var4) {
            ;
        }

        return result.intValue();
    }

    public boolean getAllowPassthru() {
        return Boolean.valueOf(this.mProperties.getProperty("WEB: Allow Passthru")).booleanValue();
    }

    public String getLogOffPage() {
        return this.mProperties.getProperty("WEB: Logged off page");
    }

    public int getPerformanceLogLevel() {
        try {
            return Integer.valueOf(this.mProperties.getProperty("SYS: Performance Log Level", "0")).intValue();
        } catch (NumberFormatException var2) {
            return 0;
        }
    }

    public int getStatusWarningDays() {
        String value = this.mProperties.getProperty("WEB: Status Warning Days", "5");

        int intValue;
        try {
            intValue = Integer.parseInt(value);
        } catch (NumberFormatException var4) {
            intValue = 5;
        }

        return intValue;
    }

    public Calendar getStatusWarningDate() {
        long millsPerDay = 86400000L;
        long daysBefor = millsPerDay * (long) this.getStatusWarningDays();
        Calendar cal = Calendar.getInstance();
        long checkDate = cal.getTimeInMillis() + daysBefor;
        cal.setTimeInMillis(checkDate);
        return cal;
    }

    public CPAuthenticationPolicy getActiveAuthenticationPolicy() {
        return this.mActiveAuthenticationPolicy;
    }

    public int getLogLevel() {
        return this.mLogLevel;
    }

    public void setLogLevel(int logLevel) {
        this.mLogLevel = logLevel;
    }

    public int getRunLevel() {
        return Integer.parseInt(mProperties.getProperty("SYS: Run Level", "0"));
    }

    public boolean isEmpty() {
        return mProperties.isEmpty();
    }

    public String getResetMethod() {
        return mProperties.getProperty("RST: Reset Method", "None");
    }

    public String getForceSecurityQuestions() {
        return mProperties.getProperty("RST: Reset Force Questions", "false");
    }

    public boolean isForceSecurityQuestions() {
        return Boolean.valueOf(getForceSecurityQuestions()).booleanValue();
    }

    public boolean isForceSecurityWord() {
        return getResetMethod().equals("Word") ? true : false;
    }

    public int getResetLinkValidity() {
        return Integer.parseInt(mProperties.getProperty("RST: Reset Link validity", "2"));
    }

    public int getResetStrikesAllowed() {
        return Integer.parseInt(mProperties.getProperty("RST: Reset Strikes Allowed", "0"));
    }

    public int getNumberOfChallengesForSetup() {
        return Integer.parseInt(mProperties.getProperty("RST: Reset Full Question Count", "5"));
    }

    public boolean isDisabledIE() {
        String flag = this.mProperties.getProperty(DISABLE_IE, "true").toLowerCase();
        return "true".equals(flag) || "True".equals(flag);
    }

    public boolean isDisabledFF() {
        String flag = this.mProperties.getProperty(DISABLE_FF, "true").toLowerCase();
        return "true".equals(flag) || "True".equals(flag);
    }

    public boolean isDisabledChrome() {
        String flag = this.mProperties.getProperty(DISABLE_CHROME, "false").toLowerCase();
        return "true".equals(flag) || "True".equals(flag);
    }

    public void needsLoading() {
        if (mProperties.isEmpty())
            init();
    }

    public int getNumberOfChallengeQuestions() {
        return Integer.parseInt(mProperties.getProperty("RST: Reset Verify Question Count", "2"));
    }
    
    public String getDevelopmentNotification() {
        return mProperties.getProperty("WEB: Development Notification", "");
    }

    // $FF: synthetic method
    static CPAuthenticationPolicy access$002(CPSystemProperties x0, CPAuthenticationPolicy x1) {
        return x0.mActiveAuthenticationPolicy = x1;
    }

    // $FF: synthetic method
    static Properties access$100(CPSystemProperties x0) {
        return x0.mProperties;
    }

    // $FF: synthetic method
    static CPAuthenticationPolicy access$000(CPSystemProperties x0) {
        return x0.mActiveAuthenticationPolicy;
    }
}
