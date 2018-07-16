package cppro.beans;
 
import java.util.ArrayList;
 
import cppro.chart.form.ChartData;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
public class GetChartData {
 
    public String  GenerateJSonChartDataYearWisePassPercentage(String jsonp) throws  JSONException
    {
 
    String chartType=jsonp;
 
    JSONObject  finalJSonObj=new JSONObject();
 
    ChartData cd;
 
    ArrayList <ChartData> chartDataArray=new ArrayList<ChartData>(); 
 
    cd=new ChartData();
    cd.setYear(2010);
    cd.setBoys_pass_percentage(70);
    cd.setGirls_pass_percentage(80);
    chartDataArray.add(cd);
 
    cd=new ChartData();
    cd.setYear(2011);
    cd.setBoys_pass_percentage(75);
    cd.setGirls_pass_percentage(80);
    chartDataArray.add(cd);
 
    cd=new ChartData();
    cd.setYear(2012);
    cd.setBoys_pass_percentage(65);
    cd.setGirls_pass_percentage(90);
    chartDataArray.add(cd);
 
    cd=new ChartData();
    cd.setYear(2013);
    cd.setBoys_pass_percentage(90);
    cd.setGirls_pass_percentage(85);
    chartDataArray.add(cd);
 
    cd=new ChartData();
    cd.setYear(2014);
    cd.setBoys_pass_percentage(85);
    cd.setGirls_pass_percentage(90);
    chartDataArray.add(cd);
 
    JSONArray chartData=new JSONArray();
    JSONArray xaxisArr =new JSONArray();
 
     JSONObject xaxisObj = new JSONObject();
 
     JSONObject dataObj = new JSONObject();
 
     JSONArray boys_pass_percentage =new JSONArray();
     JSONArray girls_pass_percentage =new JSONArray();
 
     for (int i=0; i<chartDataArray.size();i++)
     {
 
         System.out.println("Json data "+i);
 
         xaxisArr.put(chartDataArray.get(i).getYear());
 
         boys_pass_percentage.put(chartDataArray.get(i).getBoys_pass_percentage());
         girls_pass_percentage.put(chartDataArray.get(i).getGirls_pass_percentage());
 
     }
 
    xaxisObj.put("category", xaxisArr);
 
        chartData.put(xaxisObj);
 
        dataObj = new JSONObject();
        dataObj.put("name","Pass % of Boys");
        dataObj.put("color","#FF0000");
        dataObj.put("data",boys_pass_percentage);
        chartData.put(dataObj);
 
        dataObj = new JSONObject();
        dataObj.put("name","Pass % of Girls");
        dataObj.put("color","#008000");
        dataObj.put("data",girls_pass_percentage);
        chartData.put(dataObj);
 
        System.out.println("Json data "+ chartData); 
 
        finalJSonObj.put(chartType, chartData);
 
        String  tempStr=jsonp+"("+finalJSonObj.toString()+")";
 
        return tempStr;
}
 
}