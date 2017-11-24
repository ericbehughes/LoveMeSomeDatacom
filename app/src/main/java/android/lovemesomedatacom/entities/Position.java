package android.lovemesomedatacom.entities;

/**
 * Created by 1331680 on 11/24/2017.
 */

public class Position {
    private String position;

    public Position(){
        this("");
    }

    public Position(String position){
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Position{" +
                "position='" + position + '\'' +
                '}';
    }
}
