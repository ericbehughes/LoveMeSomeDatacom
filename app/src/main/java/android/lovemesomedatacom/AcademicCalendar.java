package android.lovemesomedatacom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AcademicCalendar extends AppCompatActivity {

    private final String TAG = "AcademicCalendar";
    private EditText etAcademicYear;
    private RadioButton rdioBtnFall;
    private RadioButton rdioBtnWinter;
    private RadioGroup semesterRdioGroup;
    private WebView webViewAcademicCalendar;
    private String season;
    private String year;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_calendar);
        findViews();
        this.setTitle(R.string.academic_calendar_activity_title);


        /**
         * load default Academic Calendar on separate thread
         */
        Log.d(TAG, "start default academic calendar webview with fall 2017");
        webViewAcademicCalendar.post(new Runnable() {
            @Override
            public void run() {
                webViewAcademicCalendar.loadUrl("https://www.dawsoncollege.qc.ca/registrar/fall-2017-day-division/");
            }
        });

        /**
         * listener on the search/enter button of the keyboard and grabs the value in the
         * edit text field and laods url on new Runnable thread
         */
        etAcademicYear.setOnEditorActionListener(new TextView.OnEditorActionListener() {


            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    int selectedRadio = semesterRdioGroup.getCheckedRadioButtonId();

                    if (rdioBtnFall.getId() == selectedRadio){
                        season = "fall";
                    }
                    else if (rdioBtnWinter.getId() == selectedRadio){
                        season = "winter";
                    }

                    year = etAcademicYear.getText().toString();
                    webViewAcademicCalendar.post(new Runnable() {
                        @Override
                        public void run() {

                            webViewAcademicCalendar.loadUrl("https://www.dawsoncollege.qc.ca/registrar/"+ season+ "-" + year+"-day-division/");
                        }
                    });
                    return true;
                }
                return false;
            }
        });


    }

    private void findViews() {
        etAcademicYear = (EditText)findViewById( R.id.etAcademicYear );
        rdioBtnFall = (RadioButton)findViewById( R.id.rdioBtnFall );
        rdioBtnWinter = (RadioButton)findViewById( R.id.rdioBtnWinter );
        semesterRdioGroup = (RadioGroup) findViewById(R.id.semesterRdioGroup);
        webViewAcademicCalendar = (WebView) findViewById(R.id.webViewAcademicCalendar);


    }


}
