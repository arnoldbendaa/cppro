package cppro.chart.form;
 
public class ChartData {
 
    int year =0;
    float boys_pass_percentage=0;
    float girls_pass_percentage=0;
    float total=0;
 
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public float getBoys_pass_percentage() {
        return boys_pass_percentage;
    }
    public void setBoys_pass_percentage(float boys_pass_percentage) {
        this.boys_pass_percentage = boys_pass_percentage;
    }
    public float getGirls_pass_percentage() {
        return girls_pass_percentage;
    }
    public void setGirls_pass_percentage(float girls_pass_percentage) {
        this.girls_pass_percentage = girls_pass_percentage;
    }
    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
    }
 
}