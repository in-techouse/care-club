package lcwu.fyp.careclub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.User;

public class EditUserProfile extends AppCompatActivity implements View.OnClickListener {
    private Session session;
    private Helpers helpers;
    private User user;
    EditText fname, lname, email, phoneno;
    String strfname, strlname, strphoneno;
    Button update;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.care_club_bg));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        phoneno = findViewById(R.id.phoneno);
        update = findViewById(R.id.updatebtn);
        pb = findViewById(R.id.progressbar);

        update.setOnClickListener(this);

        helpers = new Helpers();
        session = new Session(EditUserProfile.this);
        user = session.getSession();


        fname.setText(user.getFname());
        lname.setText(user.getLname());
        email.setText(user.getEmail());
        phoneno.setText(user.getPhone());


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.updatebtn: {
                boolean isConn = helpers.isConnected(EditUserProfile.this);
                if (!isConn) {
                    helpers.showError(EditUserProfile.this, "Internet Error", "Internet Error");
                    return;
                }
                strfname = fname.getText().toString();
                strlname = lname.getText().toString();
                strphoneno = phoneno.getText().toString();

                boolean flag = isValid();
                if (flag) {
                    //Show progress bar
                    pb.setVisibility(View.VISIBLE);
                    update.setVisibility(View.GONE);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    user.setFname(strfname);
                    user.setLname(strlname);
                    user.setPhone(strphoneno);
                    reference.child("Users").child(user.getId()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            session.setSession(user);
                            //Start dashboard activity
                            Intent intent = new Intent(EditUserProfile.this, UserDashboard.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pb.setVisibility(View.GONE);
                            update.setVisibility(View.VISIBLE);
                            helpers.showError(EditUserProfile.this, "Registration Failed", e.getMessage());
                        }
                    });

                }
            }


        }
    }

    private boolean isValid() {
        boolean flag = true;
        if (strfname.length() < 3) {
            fname.setError("enter a valid first name");
            flag = false;

        } else {
            fname.setError(null);
        }
        if (strlname.length() < 3) {
            lname.setError("Enter valid last name");
            flag = false;
        } else {
            lname.setError(null);
        }
        if (strphoneno.length() < 11) {
            phoneno.setError("Enter valid phone number");
            flag = false;
        } else {
            phoneno.setError(null);
        }
        return flag;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return true;
    }
}