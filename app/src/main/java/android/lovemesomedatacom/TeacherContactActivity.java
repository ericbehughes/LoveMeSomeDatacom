package android.lovemesomedatacom;

import android.content.Intent;
import android.lovemesomedatacom.entities.Teacher;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 1331680 on 11/24/2017.
 */

public class TeacherContactActivity extends MenuActivity {
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
    protected void onStart(){
        super.onStart();
        setViews();
    }

    private void initializeViews() {
        this.fullName = findViewById(R.id.full_name);
        this.email = findViewById(R.id.email);
        this.office = findViewById(R.id.office);
        this.local = findViewById(R.id.website);
        this.website = findViewById(R.id.website);
        this.bio = findViewById(R.id.bio);
    }

    private void setViews() {
        Teacher teacher = this.intent.getParcelableExtra("TEACHER");
        this.fullName.setText(teacher.getFull_name());
        this.email.setText(teacher.getEmail());
        this.office.setText(teacher.getOffice());
        this.local.setText(teacher.getLocal());
        this.website.setText(teacher.getWebsite());
        this.bio.setText(teacher.getBio());

    }
    public void launchEmailIntent(View view){

    }
    public void launchPhoneIntent(View view){

    }

}
