// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.UserContext;
import com.cedar.cp.api.model.BudgetUsersProcess;
import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.utc.struts.approver.BudgetCycleStatusForm;
import com.cedar.cp.utc.struts.approver.CrumbDTO;
import com.cedar.cp.utc.struts.homepage.BLChildDTO;
import com.cedar.cp.utc.struts.homepage.BudgetCycleDTO;
import com.cedar.cp.utc.struts.homepage.BudgetLocationDTO;
import com.cedar.cp.utc.struts.homepage.ModelDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BudgetCycleStatusSetupAction extends CPAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BudgetCycleStatusForm myForm = (BudgetCycleStatusForm) form;
        CPContext context = this.getCPContext(request);
        CPConnection cnx = context.getCPConnection();
        UserContext userCnx = cnx.getUserContext();
        myForm.setController(userCnx.hasSecurity("WEB_PROCESS.RootNode"));
        myForm.setApprover(true);
        myForm.setHolder(true);
        ArrayList modelList = null;
        String locationIdentifier = "";
        String description = "";
        if (myForm.getRefresh().equals("false") || modelList == null) {
            modelList = new ArrayList();
            BudgetUsersProcess process = cnx.getBudgetUsersProcess();
            int userId = userCnx.getUserId();
            EntityList cycles = process.getBudgetDetailsForUser(userId, true, myForm.getStructureElementId(), myForm.getBudgetCycleId());
            int rows = cycles.getNumRows();

            for (int i = 0; i < rows; ++i) {
                ModelDTO modelDTO = new ModelDTO();
                Object modelref = cycles.getValueAt(i, "Model");
                modelDTO.setName(modelref);
                modelDTO.setModelId(((Integer) cycles.getValueAt(i, "ModelId")).intValue());

                EntityList budgetCycleEntityList = (EntityList) cycles.getValueAt(i, "BudgetCycles");
                int noCycles = budgetCycleEntityList.getNumRows();
                ArrayList budgetCycleList = new ArrayList();

                for (int j = 0; j < noCycles; ++j) {
                    BudgetCycleDTO bcDTO = new BudgetCycleDTO();
                    bcDTO.setBudgetCycle(budgetCycleEntityList.getValueAt(j, "BudgetCycle"));
                    bcDTO.setModelId(((Integer) budgetCycleEntityList.getValueAt(j, "ModelId")).intValue());
                    bcDTO.setBudgetCycleId(((Integer) budgetCycleEntityList.getValueAt(j, "BudgetCycleId")).intValue());
                    bcDTO.setHierachyId(((Integer) budgetCycleEntityList.getValueAt(j, "HierarchyId")).intValue());
                    bcDTO.setCategory((String) budgetCycleEntityList.getValueAt(j, "Category"));
                    EntityList budgetLocationEntityList = (EntityList) budgetCycleEntityList.getValueAt(j, "BudgetLocations");
                    int noLocations = budgetLocationEntityList.getNumRows();
                    ArrayList budgetLocationList = new ArrayList();

                    for (int k = 0; k < noLocations; ++k) {
                        BudgetLocationDTO blDTO = new BudgetLocationDTO();
                        boolean massSubmit = false;
                        boolean massReject = false;
                        boolean allChildrenLeafs = true;
                        Object blState = budgetLocationEntityList.getValueAt(k, "State");
                        int blStateValue = 0;
                        if (blState != null && blState instanceof Integer) {
                            blStateValue = ((Integer) blState).intValue();
                        }

                        blDTO.setState(blStateValue);
                        blDTO.setStructureElementId(((Integer) budgetLocationEntityList.getValueAt(k, "StructureElementId")).intValue());
                        locationIdentifier = (String) budgetLocationEntityList.getValueAt(k, "VisId");
                        blDTO.setIdentifier(locationIdentifier);
                        description = (String) budgetLocationEntityList.getValueAt(k, "Description");
                        blDTO.setDescription(description);
                        blDTO.setDepth(((Integer) budgetLocationEntityList.getValueAt(k, "Depth")).intValue() + 1);
                        blDTO.setEndDate((Timestamp) budgetLocationEntityList.getValueAt(k, "EndDate"));

                        blDTO.setFullRights(((Boolean) budgetLocationEntityList.getValueAt(k, "FullRights")).booleanValue());

                        blDTO.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
                        EntityList children = (EntityList) budgetLocationEntityList.getValueAt(k, "ChildLocations");
                        int noChildren = children.getNumRows();
                        ArrayList budgetLocationChildList = new ArrayList();

                        for (int l = 0; l < noChildren; ++l) {
                            BLChildDTO childDTO = new BLChildDTO();
                            Object childState = children.getValueAt(l, "State");
                            int childStateValue = 0;
                            if (childState != null && childState instanceof Integer) {
                                childStateValue = ((Integer) childState).intValue();
                            }

                            if ((myForm.getStateFilter() != 1 || childStateValue == 0) && (myForm.getStateFilter() != 2 || childStateValue == 2) && (myForm.getStateFilter() != 3 || childStateValue == 3) && (myForm.getStateFilter() != 4 || childStateValue == 4)) {
                                childDTO.setState(childStateValue);
                                childDTO.setStructureElementId(((Integer) children.getValueAt(l, "StructureElementId")).intValue());
                                childDTO.setIdentifier((String) children.getValueAt(l, "ElementVisId"));
                                childDTO.setDescription((String) children.getValueAt(l, "Description"));
                                childDTO.setUserCount(((Integer) children.getValueAt(l, "UserCount")).intValue());
                                childDTO.setOtherUserCount(((Integer) children.getValueAt(l, "OtherUserCount")).intValue());
                                childDTO.setSubmitable(((Boolean) children.getValueAt(l, "Submitable")).booleanValue());
                                childDTO.setRejectable(((Boolean) children.getValueAt(l, "Rejectable")).booleanValue());
                                childDTO.setLastUpdateById(((Integer) children.getValueAt(i, "LastUpdateById")).intValue());
                                childDTO.setEndDate((Timestamp) children.getValueAt(l, "EndDate"));
                                childDTO.setFullRights(((Boolean) children.getValueAt(l, "FullRights")).booleanValue());
                                childDTO.setLateDate(this.getCPSystemProperties(request).getStatusWarningDate());
                                childDTO.setParent(blDTO);
                                boolean leaf = ((Boolean) children.getValueAt(l, "Leaf")).booleanValue();
                                if (!leaf) {
                                    allChildrenLeafs = false;
                                }

                                if (!massSubmit && leaf) {
                                    massSubmit = true;
                                }

                                if (!massReject && childDTO.isRejectable()) {
                                    massReject = true;
                                }

                                budgetLocationChildList.add(childDTO);
                            }
                        }

                        blDTO.setChildren(budgetLocationChildList);
                        blDTO.setUserCount(((Integer) budgetLocationEntityList.getValueAt(k, "UserCount")).intValue());
                        blDTO.setController(myForm.isController());
                        blDTO.setSubmitable(((Boolean) budgetLocationEntityList.getValueAt(k, "Submitable")).booleanValue());
                        blDTO.setRejectable(((Boolean) budgetLocationEntityList.getValueAt(k, "Rejectable")).booleanValue());
                        blDTO.setLastUpdatedById(((Integer) budgetLocationEntityList.getValueAt(k, "LastUpdatedById")).intValue());
                        if (blDTO.getState() != 2) {
                            blDTO.setMassSubmitable(false);
                        } else {
                            blDTO.setMassSubmitable(allChildrenLeafs && massSubmit);
                        }

                        blDTO.setMassRejectable(allChildrenLeafs && massReject);
                        if (myForm.getOldUserCount() == 0 && blDTO.getUserCount() > 0) {
                            myForm.setOldUserCount(blDTO.getUserCount());
                        } else if (myForm.getOldUserCount() > 0) {
                            blDTO.setUserCount(1);
                            blDTO.setAgreeable(myForm.getOldDepth() > 0);
                        }

                        budgetLocationList.add(blDTO);
                    }

                    bcDTO.setBudgetLocations(budgetLocationList);
                    budgetCycleList.add(bcDTO);
                }

                modelDTO.setBudgetCycle(budgetCycleList);
                modelList.add(modelDTO);
            }
        }

        myForm.setModel(modelList);
        this.doCrumbs(form, locationIdentifier, description);
        return mapping.findForward("success");
    }

    public void doCrumbs(ActionForm form, String locationIdentifier, String description) {
       BudgetCycleStatusForm myForm = (BudgetCycleStatusForm)form;
       List crumbs;
       String delim = "**,**";
       if(myForm.getCrumbSize() == 0){
           crumbs = new ArrayList();
       }else {
           crumbs = myForm.getCrumbs();
       }
       String visIdList = myForm.getVisIdList();
       String structureIdList = myForm.getStructureElementList();
       String descriptionList = myForm.getDescriptionList();
       
       StringTokenizer visIdTokens = new StringTokenizer(visIdList, delim); 
       StringTokenizer structureIdsTokens = new StringTokenizer(structureIdList, delim);
       StringTokenizer descriptionTokens = new StringTokenizer(descriptionList, delim);
       String finalVisId;
       String finalStructureId;
       String finalDescription;
       StringBuffer bufferVisId = new StringBuffer();
       StringBuffer bufferStrutureId = new StringBuffer();
       StringBuffer bufferDescription = new StringBuffer();
       int i = 0;
       while(visIdTokens.hasMoreTokens() && i < 2) {
           finalVisId =  visIdTokens.nextToken();
           if(!finalVisId.equals(locationIdentifier))
          {
              finalStructureId = structureIdsTokens.nextToken();
              finalDescription = descriptionTokens.nextToken();
              bufferVisId.append(finalVisId + delim);
              bufferStrutureId.append(finalStructureId + delim);
              bufferDescription.append(finalDescription + delim);
              CrumbDTO dto1 = new CrumbDTO(finalVisId, finalStructureId, "1", "0", finalDescription);
              crumbs.add(dto1);
              i++;
           }else{
               break;
           }
       }
       String vlToken = locationIdentifier;
       String seToken = String.valueOf(myForm.getStructureElementId());
       String odToken = "1";
       String oucToken = "0";
       String dToken = description;
       
       CrumbDTO dto1 = new CrumbDTO(vlToken, seToken, odToken, oucToken, dToken);
       crumbs.add(dto1);
       myForm.setCrumbs(crumbs);
       
       bufferVisId.append(vlToken);
       bufferStrutureId.append(seToken);
       bufferDescription.append(dToken);
       
       myForm.setVisIdList(bufferVisId.toString());
       myForm.setStructureElementList(bufferStrutureId.toString());
       myForm.setDescriptionList(bufferDescription.toString());
   }

    public void doCrumbsOld(ActionForm form, String locationIdentifier, String description) {
        if (form instanceof BudgetCycleStatusForm) {
            BudgetCycleStatusForm myForm = (BudgetCycleStatusForm) form;
            ArrayList crumbs = new ArrayList();
            StringBuffer sel = new StringBuffer();
            StringBuffer vl = new StringBuffer();
            StringBuffer odl = new StringBuffer();
            StringBuffer oucl = new StringBuffer();
            StringBuffer dl = new StringBuffer();
            String delim = "**,**";
            StringTokenizer seTokens;
            StringTokenizer vlTokens;
            StringTokenizer odTokens;
            StringTokenizer oucTokens;
            StringTokenizer dTokens;
            String vlToken;
            String seToken;
            String oucToken;
            String odToken;
            String dToken;
            if (myForm.getAddId() != null && myForm.getAddId().length() > 0) {
                sel.append(myForm.getStructureElementList());
                vl.append(myForm.getVisIdList());
                odl.append(myForm.getOldDepthList());
                oucl.append(myForm.getOldUserCountList());
                dl.append(myForm.getDescriptionList());
                if (sel.length() > 0) {
                    sel.append(delim);
                    vl.append(delim);
                    odl.append(delim);
                    oucl.append(delim);
                    dl.append(delim);
                }

                sel.append(myForm.getStructureElementId());
                vl.append(locationIdentifier);
                odl.append(myForm.getOldDepth());
                oucl.append(myForm.getOldUserCount());
                dl.append(description);
            } else if (myForm.getOldId() != null && myForm.getOldId().length() > 0) {
                String dto = myForm.getOldId();
                boolean add = true;
                seTokens = new StringTokenizer(myForm.getStructureElementList(), delim);
                vlTokens = new StringTokenizer(myForm.getVisIdList(), delim);
                odTokens = new StringTokenizer(myForm.getOldDepthList(), delim);
                oucTokens = new StringTokenizer(myForm.getOldUserCountList(), delim);
                dTokens = new StringTokenizer(myForm.getDescriptionList(), delim);

                while (seTokens.hasMoreTokens() && add) {
                    seToken = seTokens.nextToken();
                    if (seToken.equals(dto)) {
                        add = false;
                    }

                    vlToken = vlTokens.nextToken();
                    odToken = odTokens.nextToken();
                    oucToken = oucTokens.nextToken();
                    dToken = dTokens.nextToken();
                    if (sel.length() > 0) {
                        sel.append(delim);
                        vl.append(delim);
                        odl.append(delim);
                        oucl.append(delim);
                        dl.append(delim);
                    }

                    sel.append(seToken);
                    vl.append(vlToken);
                    odl.append(odToken);
                    oucl.append(oucToken);
                    dl.append(dToken);
                }
            } else {
                sel.append(myForm.getStructureElementList());
                vl.append(myForm.getVisIdList());
                odl.append(myForm.getOldDepthList());
                oucl.append(myForm.getOldUserCountList());
                dl.append(myForm.getDescriptionList());
            }

            myForm.setStructureElementList(sel.toString());
            myForm.setVisIdList(vl.toString());
            myForm.setOldDepthList(odl.toString());
            myForm.setOldUserCountList(oucl.toString());
            myForm.setDescriptionList(dl.toString());
            seTokens = new StringTokenizer(myForm.getStructureElementList(), delim);
            vlTokens = new StringTokenizer(myForm.getVisIdList(), delim);
            odTokens = new StringTokenizer(myForm.getOldDepthList(), delim);
            oucTokens = new StringTokenizer(myForm.getOldUserCountList(), delim);
            dTokens = new StringTokenizer(myForm.getDescriptionList(), delim);

            while (seTokens.hasMoreTokens()) {
                seToken = seTokens.nextToken();
                if (vlTokens.hasMoreElements()) {
                    vlToken = vlTokens.nextToken();
                } else {
                    vlToken = "No Vis Id";
                }

                odToken = oucTokens.nextToken();
                oucToken = odTokens.nextToken();
                if (dTokens.hasMoreElements()) {
                    dToken = dTokens.nextToken();
                } else {
                    dToken = "No Description";
                }

                CrumbDTO dto1 = new CrumbDTO(vlToken, seToken, odToken, oucToken, dToken);
                crumbs.add(dto1);
            }

            myForm.setCrumbs(crumbs);
        }

    }
}
