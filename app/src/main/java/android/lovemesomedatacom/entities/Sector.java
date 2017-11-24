package android.lovemesomedatacom.entities;

/**
 * Created by 1331680 on 11/24/2017.
 */

public class Sector {
    private String sector;

    public Sector(){
        this("");
    }
    public Sector(String sector){
        this.sector = sector;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    @Override
    public String toString() {
        return "Sector{" +
                "sector='" + sector + '\'' +
                '}';
    }
}
