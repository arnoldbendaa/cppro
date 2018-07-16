package com.cedar.cp.api.notes;

import java.util.ArrayList;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;

public interface NotesProcess extends BusinessProcess {
   
   EntityList getAllFinanceCubesForLoggedUser();
   
   EntityList getHierarchiesForModel(int var1);
   
   ArrayList<Object[]> getNotesForCostCenters(ArrayList<Integer> costCenters, int financeCubeId, String fromDate, String toDate);
}
