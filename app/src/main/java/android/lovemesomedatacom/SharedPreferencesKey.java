package android.lovemesomedatacom;
import android.content.SharedPreferences;


public enum SharedPreferencesKey {

    FIRST_NAME("FIRST_NAME"),
    LAST_NAME("LAST_NAME"),
    EMAIL_ADDRESS("EMAIL_ADDRESS"),
    PASSWORD("PASSWORD"),
    DATE_STAMP("DATE_STAMP"),
    MAIN_APP("MAIN_APP");

    private String key;

    SharedPreferencesKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}