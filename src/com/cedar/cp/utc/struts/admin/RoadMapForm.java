package com.cedar.cp.utc.struts.admin;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;


public class RoadMapForm extends ActionForm {
    private ArrayList<RoadMapDTO> roadMapElements;

    public ArrayList<RoadMapDTO> getRoadMapElements() {
        return roadMapElements;
    }

    public void setRoadMapElements(ArrayList<RoadMapDTO> roadMapElements) {
        this.roadMapElements = roadMapElements;
    }
    
    public void addRoadMap(RoadMapDTO rm) {
        if (roadMapElements == null) {
            roadMapElements = new ArrayList<RoadMapDTO>();
        }
        roadMapElements.add(rm);
    }
}