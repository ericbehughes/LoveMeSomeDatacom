package android.lovemesomedatacom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RadioButton;

public class AcademicCalendar extends AppCompatActivity {

    private EditText etAcademicYear;
    private RadioButton rdioBtnFall;
    private RadioButton rdioBtnWinter;
    private WebView webViewAcademicCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_calendar);
        findViews();
//        webViewAcademicCalendar.getSettings().setJavaScriptEnabled(true);

        webViewAcademicCalendar.post(new Runnable() {
            @Override
            public void run() {
                webViewAcademicCalendar.loadUrl("https://www.dawsoncollege.qc.ca/registrar/fall-2017-day-division/");
            }
        });


    }

    private void findViews() {
//        etAcademicYear = (EditText)findViewById( R.id.etAcademicYear );
//        rdioBtnFall = (RadioButton)findViewById( R.id.rdioBtnFall );
//        rdioBtnWinter = (RadioButton)findViewById( R.id.rdioBtnWinter );
        webViewAcademicCalendar = (WebView) findViewById(R.id.webViewAcademicCalendar);


    }


}
