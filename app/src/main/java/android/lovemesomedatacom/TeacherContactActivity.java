package android.lovemesomedatacom;

import android.content.Intent;
import android.lovemesomedatacom.entities.Teacher;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 1331680 on 11/24/2017.
 */

public class TeacherContactActivity extends MenuActivity {
    private final String TAG = "TEACHER_CONTACT_ACT";
    private final String DAWSON_NUMBER = "(514) 931 8731";

    private TextView fullName, email, office, local, website, bio;
    private TextView[] departments, positions, sectors;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_teacher_contact);

        this.intent = this.getIntent();

        initializeViews();
    }

    @Override
    protected void onResume(){
        super.onResume();
        setViews();
    }

    private void initializeViews() {
        this.fullName = findViewById(R.id.full_name);
        this.email = findViewById(R.id.email);
        this.office = findViewById(R.id.office);
        this.local = findViewById(R.id.local);
        this.website = findViewById(R.id.website);
        this.bio = findViewById(R.id.bio);
    }

    private void setViews() {
        Teacher teacher = this.intent.getParcelableExtra("TEACHER");
        this.fullName.setText(getString(R.string.teacher_name)+ teacher.getFull_name());
        String formattedEmail = Html.fromHtml(teacher.getEmail()).toString();
        this.email.setText(getString(R.string.teacher_email) + formattedEmail);
        this.office.setText(getString(R.string.teacher_office) + teacher.getOffice());
        this.local.setText(getString(R.string.teacher_local) + teacher.getLocal());
        this.website.setText(getString(R.string.teacher_website) + teacher.getWebsite());
        this.bio.setText(getString(R.string.teacher_bio) + teacher.getBio());

    }
    public void launchEmailIntent(View view){
        TextView email = (TextView) view;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        String[] recipients = {email.getText().toString()};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.from)+getString(R.string.app_name));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void launchPhoneIntent(View view){
        Log.d(TAG, "PHONE INTENT INVOKED");
        TextView local = (TextView) view;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel://" + DAWSON_NUMBER+","+local.getText()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
