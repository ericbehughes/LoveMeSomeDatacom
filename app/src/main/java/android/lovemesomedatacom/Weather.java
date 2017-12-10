package android.lovemesomedatacom;

public class Weather {

    private String temperature, pressure, humidity, clouds, windDirection, windSpeed, symbol, precipitation, start, end;

    public Weather(){
        this.temperature = "";
        this.pressure = "";
        this.humidity = "";
        this.start = "";
        this.end = "";
        this.clouds = "";
        this.precipitation = "";
        this.windDirection = "";
        this.windSpeed = "";
        this.symbol = "";
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

    public String getClouds(){
        return clouds;
    }

    public void setClouds(String clouds){
        this.clouds = clouds;
    }

    public String getPrecipitation(){
        return precipitation;
    }

    public void setPrecipitation(String precipitation){
        this.precipitation = precipitation;
    }

    public String getWindSpeed(){
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed){
        this.windSpeed = windSpeed;
    }

    public String getWindDirection(){
        return windDirection;
    }

    public void setWindDirection(String windDirection){
        this.windDirection = windDirection;
    }

    public String getSymbol(){
        return symbol;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }
}
