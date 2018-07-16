// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.formdeployment;

import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.task.TaskCheckpoint;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.xmlform.FormDeploymentData;
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.message.MessageImpl;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.xmlform.FormDeploymentTaskRequest;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkPK;
import com.cedar.cp.ejb.api.message.MessageHelperServer;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.formdeployment.DeploymentTask$DeploymentCheckPoint;
import com.cedar.cp.ejb.impl.user.DataEntryProfileEVO;
import com.cedar.cp.ejb.impl.user.UserAccessor;
import com.cedar.cp.ejb.impl.user.UserEVO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormDAO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormEVO;
import com.cedar.cp.ejb.impl.xmlform.XmlFormUserLinkEVO;
import com.softproideas.common.models.FormDeploymentDataDTO;
import com.softproideas.common.models.StructureElementCoreDTO;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.InitialContext;

public class DeploymentTask extends AbstractTask {

    private transient UserAccessor mUserccessor;
    private transient InitialContext mInitialContext;

    public int getReportType() {
        return 0;
    }

    public String getEntityName() {
        return "DeploymentTask";
    }

    public void runUnitOfWork(InitialContext initialContext) throws Exception {
        this.setInitialContext(initialContext);
        FormDeploymentTaskRequest request = (FormDeploymentTaskRequest) this.getRequest();
        if (this.getCheckpoint() == null) {
            this.buildProfiles(request);
        } else {
            this.informUsers(request.getData());
        }

    }

    private void buildProfiles(FormDeploymentTaskRequest request) throws Exception {
        FormDeploymentDataDTO dto = request.getDto();
        if (dto.getMobile()) {
            buildMobileProfiles(dto);

        } else {
            buildWebProfiles(dto);
        }
        DeploymentTask$DeploymentCheckPoint cp = new DeploymentTask$DeploymentCheckPoint();

        // this.log("adding profile " + item.getVisId() + " for user " + userEVO.getName());
        // cp.addToMap(userEVO, item.getVisId() + " " + item.getDescription());
        this.setCheckpoint(cp);
    }

    private void buildWebProfiles(FormDeploymentDataDTO dto) {
        XmlFormDAO dao = new XmlFormDAO();
        ArrayList<DataEntryProfileEVO> dataEntryProfilesToInsert = new ArrayList<DataEntryProfileEVO>();
        StructureElementCoreDTO dim0 = dto.getStructureElements().get(0);
        ArrayList<Integer> usersIds = dao.getUsersId(dim0.getStructureId(), dim0.getStructureElementId(), dto.getFlatFormId());
        for (Integer userId: usersIds) {
            DataEntryProfileEVO dep = new DataEntryProfileEVO();
            dep.setUserId(userId);
            dep.setVisId(dto.getIdentifier());
            dep.setModelId(dto.getModelId().intValue());
            dep.setBudgetCycleId(dto.getBudgetCycleId());
            dep.setDescription(dto.getDescription());
            dep.setXmlformId(dto.getFlatFormId());
            dep.setMobile('N');
            dep.setDataType(dto.getDataType() != null ? dto.getDataType() : "");
            dataEntryProfilesToInsert.add(dep);
        }

        if (dataEntryProfilesToInsert.size() > 0) {
            dao.deployProfileForUsers(dataEntryProfilesToInsert);
        }

    }

    private void buildMobileProfiles(FormDeploymentDataDTO dto) {
        XmlFormDAO dao = new XmlFormDAO();

        ArrayList<DataEntryProfileEVO> dataEntryProfilesToInsert = new ArrayList<DataEntryProfileEVO>();

        StructureElementCoreDTO dim0 = dto.getStructureElements().get(0);
        StructureElementCoreDTO dim1 = dto.getStructureElements().get(1);
        StructureElementCoreDTO dim2 = dto.getStructureElements().get(2);        
        

        ArrayList<Integer> usersIds = dao.getUsersId(dim0.getStructureId(), dim0.getStructureElementId(), dto.getFlatFormId());

        for (Integer userId: usersIds) {
            ArrayList<StructureElementCoreDTO> structElems = dao.getStructuresElementsForUser(userId, dto.getModelId(),dim0.getStructureId(), dim0.getStructureElementId());

            for (StructureElementCoreDTO structElem: structElems) {

                DataEntryProfileEVO dep = new DataEntryProfileEVO();
                dep.setUserId(userId);
                dep.setVisId(dto.getIdentifier());
                dep.setModelId(dto.getModelId().intValue());
                dep.setBudgetCycleId(dto.getBudgetCycleId());
                dep.setDescription(dto.getDescription());
                dep.setXmlformId(dto.getFlatFormId());
                dep.setMobile('Y');

                dep.setStructureId0(structElem.getStructureId());
                dep.setStructureElementId0(structElem.getStructureElementId());
                dep.setElementLabel0(structElem.getStructureElementVisId());

                dep.setStructureId1(dim1 != null ? dim1.getStructureId() : 0);
                dep.setStructureElementId1(dim1 != null ? dim1.getStructureElementId() : 0);
                dep.setElementLabel1(dim1 != null ? dim1.getStructureElementVisId() : "");

                dep.setStructureId2(dim2 != null ? dim2.getStructureId() : 0);
                dep.setStructureElementId2(dim2 != null ? dim2.getStructureElementId() : 0);
                dep.setElementLabel2(dim2 != null ? dim2.getStructureElementVisId() : "");

                dep.setDataType(dto.getDataType() != null ? dto.getDataType() : "");

                dataEntryProfilesToInsert.add(dep);

            }

        }

        if (dataEntryProfilesToInsert.size() > 0) {
            dao.deployProfileForUsers(dataEntryProfilesToInsert);
        }

    }

    private void informUsers(FormDeploymentData data) throws Exception {
        MessageHelperServer server = new MessageHelperServer(this.getInitialContext(), false);
        Iterator i$ = this.getCheckpoint().getProfiles().keySet().iterator();

        while (i$.hasNext()) {
            UserEVO evo = (UserEVO) i$.next();
            MessageImpl msg = new MessageImpl((Object) null);
            msg.setSubject("New Form");
            msg.setMessageType(data.getMailType());
            String userId = "user:" + this.getUserId();
            msg.addFromUser(userId);
            if (data.getAttachment() != null && data.getAttachment().length > 0) {
                msg.addAttachment(new CPFileWrapper(data.getAttachment(), data.getAttachmentName()));
            }

            StringBuilder sb = new StringBuilder(data.getMailContent());
            sb.append("\n\n");
            Iterator i$1 = ((List) this.getCheckpoint().getProfiles().get(evo)).iterator();

            while (i$1.hasNext()) {
                String s = (String) i$1.next();
                sb.append("Form Id ").append(s).append(" has been created for you.").append("\n");
            }

            msg.setContent(sb.toString());
            msg.addToUser(evo.getName());
            this.log("sending message to " + evo.getName());
            server.createNewMessage(msg);
        }

        this.setCheckpoint((TaskCheckpoint) null);
    }

    private UserAccessor getUserAccessor() throws Exception {
        if (this.mUserccessor == null) {
            this.mUserccessor = new UserAccessor(this.getInitialContext());
        }

        return this.mUserccessor;
    }

    public InitialContext getInitialContext() {
        return this.mInitialContext;
    }

    public void setInitialContext(InitialContext initialContext) throws Exception {
        if (initialContext == null) {
            this.mInitialContext = new InitialContext();
        } else {
            this.mInitialContext = initialContext;
        }

    }

    public DeploymentTask$DeploymentCheckPoint getCheckpoint() {
        return (DeploymentTask$DeploymentCheckPoint) super.getCheckpoint();
    }
}
