package com.cedar.cp.ejb.base.async.importtask;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.naming.InitialContext;

import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.importtask.ImportTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import com.cedar.cp.ejb.impl.extsys.AdAccessorDAO;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.util.Log;

public class ImportTask extends AbstractTask {

    private transient InitialContext mInitialContext;
    private transient Log mLog;

    public ImportTask() {
        mLog = new Log(getClass());
    }

    public int getReportType() {
        return 8;
    }

    public ImportCheckpoint getCheckpoint() {
        return (ImportCheckpoint) super.getCheckpoint();
    }

    public String getEntityName() {
        return "ImportTask";
    }

    public void runUnitOfWork(InitialContext initialContext) throws Exception {
        mInitialContext = initialContext;

        if (!(getRequest() instanceof ImportTaskRequest)) {
            return;
        }
        TaskRequest tr = getRequest();
        String taskName = tr.getIdentifier();

        if (taskName.equals("OA_PCTRANS")) {
            ImportCheckpoint checkpoint = getCheckpoint();
            if (checkpoint == null) {
                checkpoint = new ImportCheckpoint();
                deleteOaPctransIndexes();
                importOaPctrans();
                setCheckpoint(checkpoint);
            } else {
                if (checkpoint.getCheckpointNumber() == 1) {
                    buildOaPcTransIndexes();
                } else if (checkpoint.getCheckpointNumber() == 2) {
                    calculateOaPctransCums();
                } else if (checkpoint.getCheckpointNumber() == 3) {
                    calculateOaPctransTotals();
                } else if (checkpoint.getCheckpointNumber() == 4) {
                    buildOaPcTransTotalsIndexes();
                    setCheckpoint((TaskCheckpoint) null);
                }

            }
        } else if (taskName.equals("a3")) {
            importA3();
        }
    }

    private void importOaPctrans() throws SQLException {
        int added = 0;
        ExternalSystemDAO dao = new ExternalSystemDAO();
        List<Integer> companyList = dao.getCompanyList();
        this.log("Start importing... ");

        for (int i = 0; i < companyList.size(); i++) {
            int company = companyList.get(i);
            this.log("Importing for company " + company);
            dao.deleteFromOaPctrans(company); // delete old data
            added = dao.importOaPctrans(company); // import new data
            this.log("   added " + added + " leaves");
            this.log("   end for company " + company);
        }
        this.log("End importing.");
    }
    
    private void calculateOaPctransCums() throws SQLException {
        ExternalSystemDAO dao = new ExternalSystemDAO();
        List<Integer> companyList = dao.getCompanyList();
        this.log("Start calculate cumulatives... ");

        for (int i = 0; i < companyList.size(); i++) {
            int company = companyList.get(i);
            this.log("Calculate for company " + company);
            dao.buildOaPctransCums(company); // calculate totals
            this.log("   end for company " + company);
        }
        this.log("End calculate cumulatives.");
    }
    
    
    private void calculateOaPctransTotals() throws SQLException {
        ExternalSystemDAO dao = new ExternalSystemDAO();
        List<Integer> companyList = dao.getCompanyList();
        this.log("Start calculate totals... ");

        for (int i = 0; i < companyList.size(); i++) {
            int company = companyList.get(i);
            this.log("Calculate for company " + company);
            dao.buildOaPctransTotals(company); // calculate totals
            this.log("   end for company " + company);
        }
        this.log("End calculate totals.");
    }

    private void deleteOaPctransIndexes() {
        ExternalSystemDAO dao = new ExternalSystemDAO();
        dao.deleteOaPctransIndexes();
    }
    private void buildOaPcTransIndexes() {
        this.log("Start building indexes for oa_pctrans...");
        ExternalSystemDAO dao = new ExternalSystemDAO();
        dao.buildOaPctransIndexes();
        this.log("End building indexes for oa_pctrans.");
    }

    private void buildOaPcTransTotalsIndexes() {
        this.log("Start building indexes for oa_pctrans_totals...");
        ExternalSystemDAO dao = new ExternalSystemDAO();
        dao.buildOaPctransTotalsIndexes();
        this.log("End building indexes for oa_pctrans_totals.");
    }

    private void importA3() throws SQLException {
        mLog.debug("runUnitOfWork", "Starting task");
        setCheckpoint(new ImportCheckpoint());

        int added = 0;
        ExternalSystemDAO dao = new ExternalSystemDAO();
        AdAccessorDAO acessor = dao.getAdAccessorDAO();
        this.log("Start importing... ");

        added = acessor.importSaleLotCancel();// ok 70 000
        this.log("End for SaleLotCancel table (added " + added + " rows)");

        added = acessor.importSale(); // 20 000
        this.log("End for Sale table (added " + added + " rows)");

        added = acessor.importSaleLot(); // 4 000 000
        this.log("End for SaleLot table (added " + added + " rows)");

        added = acessor.importSaleItem(); // 5 500 000
        this.log("End for SaleItem table (added " + added + " rows)");

        added = acessor.importSaleBid(); // 3 000 000
        this.log("End for SaleBid table (added " + added + " rows)");

        added = acessor.importSaleLotA();// ok 4 000 000
        this.log("End for SaleLotA table (added " + added + " rows)");

        acessor.insertIntoAuctionLookup();

        mLog.debug("runUnitOfWork", "Ending  task");
        setCheckpoint(null);
    }

    public void runClass(String className) {
        mLog.info("runClass", className);
        Class cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            mLog.info("runClass", className);
            log(new StringBuilder().append("CLASS NOT FOUND EXCEPTION: ").append(e.getMessage()).toString());
            return;
        }

        Constructor constructor = null;
        for (Constructor ct: cls.getDeclaredConstructors()) {
            if (ct.getParameterTypes().length == 0) {
                constructor = ct;
                break;
            }
        }

        Method setTaskIdMethod = null;
        Method runMethod = null;
        Method getMessagesMethod = null;
        for (Method m: cls.getDeclaredMethods()) {
            if ((m.getName().equals("setTaskId")) && (m.getParameterTypes().length == 1) && (m.getParameterTypes()[0].getName().equals("java.lang.Integer"))) {
                setTaskIdMethod = m;
            } else if ((m.getName().equals("run")) && (m.getParameterTypes().length == 0)) {
                runMethod = m;
            } else if ((m.getName().equals("getMessages")) && (m.getParameterTypes().length == 0) && (m.getReturnType().getName().equals("java.util.List"))) {
                getMessagesMethod = m;
            }
        }
        if (runMethod == null) {
            mLog.info("runClass", "run method not found");
            return;
        }
        if (setTaskIdMethod == null) {
            mLog.info("runClass", "setTaskId method not found");
        }
        if (getMessagesMethod == null) {
            mLog.info("runClass", "getMessages method not found");
        }

        if (constructor == null) {
            mLog.info("runClass", "couldn't find appropriate constructor");
            log(new StringBuilder().append("couldn't find appropriate constructor in ").append(className).toString());
            return;
        }

        Object instance = null;
        try {
            instance = cls.newInstance();
            Object[] argList = { new Integer(getTaskId()) };
            if (setTaskIdMethod != null)
                setTaskIdMethod.invoke(instance, argList);
            runMethod.invoke(instance, new Object[0]);
            if (getMessagesMethod != null) {
                List messages = (List) getMessagesMethod.invoke(instance, new Object[0]);
                log(messages);
            }
        } catch (Exception e) {
            log(new StringBuilder().append(e.getClass().getName()).append(" ").append(e.getMessage()).toString());

            if (e.getCause() != null)
                e.getCause().printStackTrace();
            else
                e.printStackTrace();
        }
    }

    public InitialContext getInitialContext() {
        return mInitialContext;
    }

    public boolean mustComplete() {
        return false;
    }

    static class ImportCheckpoint extends AbstractTaskCheckpoint {
        public List toDisplay() {
            return Arrays.asList(new String[] { "Import Task Checkpoint number:" + getCheckpointNumber() });
        }
    }
}