package android.lovemesomedatacom;

import android.content.Intent;
import android.lovemesomedatacom.entities.Teacher;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * The TeacherContactActivity is responsible for properly displaying a single's Teachers information.
 * It display the field of a single Teacher object gotten through the intent's extra method. This
 * activity also ensures that when the email field is clicked, an implicit intent requiring the
 * device's mailing app to be called. It also fires an intent for the phone's dial when the local
 * field is clicked.
 *
 * @author Sebastian Ramirez
 */

public class TeacherContactActivity extends MenuActivity {
    private final String TAG = TeacherContactActivity.class.getSimpleName();

    //Common phone number to reach Dawson College
    private final String DAWSON_NUMBER = "15149318731";
    //Views
    private TextView fullName, email, office, local, website, bio;
    private TextView[] departments, positions, sectors;

    private Intent intent;
    private Teacher teacher;
    private String formattedEmail;

    /**
     * The onCreate is fired when the Activity is started. It takes a Bundle as parameter in order
     * to preserve data between activities or when the viewport changes. It inflates the specified layout
     * resource and initializes several attributes required by public and private methods that need initialization.
     * onCreate will also initialize all the views that need to be changed through code by invoking
     * the instantiateViews method.
     *
     * @param savedInstance
     */
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_teacher_contact);
        this.intent = this.getIntent();
        this.teacher = this.intent.getParcelableExtra("TEACHER");
        instantiateViews();
    }

    /**
     * The overriden onResume method makes sure the TextView views are modified only after they have
     * been inflated for sure by calling the setViews method.
     */
    @Override
    protected void onResume() {
        super.onResume();
        setViews();
    }

    /**
     * The instantiateViews method is a simple method that will instantiate all the views that need
     * to be accessed and/or changed by the activity and its methods.
     */
    private void instantiateViews() {
        this.fullName = findViewById(R.id.full_name);
        this.email = findViewById(R.id.email);
        this.office = findViewById(R.id.office);
        this.local = findViewById(R.id.local);
        this.website = findViewById(R.id.website);
        this.bio = findViewById(R.id.bio);
    }

    /**
     * The setViews method sets the text for all the necessary TextViews in the activity.
     */
    private void setViews() {
        this.fullName.setText(getString(R.string.teacher_name) + this.teacher.getFull_name());
        this.formattedEmail = Html.fromHtml(this.teacher.getEmail()).toString();
        this.email.setText(getString(R.string.teacher_email) + this.formattedEmail);
        this.office.setText(getString(R.string.teacher_office) + this.teacher.getOffice());
        this.local.setText(getString(R.string.teacher_local) + this.teacher.getLocal());
        this.website.setText(getString(R.string.teacher_website) + this.teacher.getWebsite());
        this.bio.setText(getString(R.string.teacher_bio) + this.teacher.getBio());

    }

    /**
     * The launchEmailIntent method fires an implicit intent calling for the device's mailing app.
     * It will set the recipient according to the Teacher's object email. It needs to be passed as
     * an array as per the putExtra method signature. It will also set the subject of the email as
     * the app's name.
     *
     * @param view the view that invoked this method
     */
    public void launchEmailIntent(View view) throws UnsupportedEncodingException {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        String[] recipients = {this.formattedEmail};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.from) + getString(R.string.app_name));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * The launchPhoneIntent method fires an implicit intent calling for the device's dialing service.
     * It will set the number to be dialed as the common Dawsons College number plus the local number
     * of the Teacher's object.
     *
     * @param view the view that invoked this method
     */
    public void launchPhoneIntent(View view) {
        Log.d(TAG, "PHONE INTENT INVOKED");
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String number = "tel:" + DAWSON_NUMBER + "," + teacher.getLocal();
        Log.d(TAG, "NUMBER: " + number);
        intent.setData(Uri.parse(number));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
