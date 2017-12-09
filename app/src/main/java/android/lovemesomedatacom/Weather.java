package android.lovemesomedatacom;

public class Weather {

    private String temperature, pressure, humidity, start, end;

    public Weather(){
        this.temperature = "";
        this.pressure = "";
        this.humidity = "";
        this.start = "";
        this.end = "";
    }

    public String getTemperature(){
        return temperature;
    }

    public void setTemperature(String temp){
        this.temperature = temp;
    }

    public String getPressure(){
        return pressure;
    }

    public void setPressure(String pressure){
        this.pressure = pressure;
    }

    public String getHumidity(){
        return humidity;
    }

    public void setHumidity(String humidity){
        this.humidity = humidity;
    }

    public String getStart(){
        return start;
    }

    public void setStart(String start){
        this.start = start;
    }

    public String getEnd(){
        return end;
    }

    public void setEnd(String end){
        this.end = end;
    }
}
