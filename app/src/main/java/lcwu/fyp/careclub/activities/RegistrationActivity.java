package lcwu.fyp.careclub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.User;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton signup;
    private ProgressBar signupprogress;
    private EditText fname, lname, pass, cpass, email, phoneno;
    private String strfname, strlname, strpass, strcpass, stremail, strphoneno;
    private Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.care_club_bg));

        signup = findViewById(R.id.rgstrbtn);
        TextView gotologin = findViewById(R.id.gotologin);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        phoneno = findViewById(R.id.phoneno);
        pass = findViewById(R.id.pass);
        cpass = findViewById(R.id.cpass);
        email = findViewById(R.id.email);
        signupprogress = findViewById(R.id.signupprogress);

        signup.setOnClickListener(this);
        gotologin.setOnClickListener(this);

        helpers = new Helpers();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rgstrbtn: {
                boolean isConn = helpers.isConnected(RegistrationActivity.this);
                if (!isConn) {
                    helpers.showError(RegistrationActivity.this, "ERROR", "No internet connection found.\nConnect to a network and try again.");
                    return;
                }
                strfname = fname.getText().toString();
                strlname = lname.getText().toString();
                strpass = pass.getText().toString();
                strcpass = cpass.getText().toString();
                stremail = email.getText().toString();
                strphoneno = phoneno.getText().toString();

                boolean flag = isValid();
                if (flag) {
                    signupprogress.setVisibility(View.VISIBLE);
                    signup.setVisibility(View.GONE);
                    //Firebase

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(stremail, strpass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                    final User user = new User();
                                    user.setFname(strfname);
                                    user.setLname(strlname);
                                    user.setPhone(strphoneno);
                                    user.setEmail(stremail);
                                    String id = stremail.replace("@", "-");
                                    id = id.replace(".", "_");
                                    user.setId(id);
                                    user.setRole(0);
                                    reference.child("Users").child(id).setValue(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Session session = new Session(RegistrationActivity.this);
                                                    session.setSession(user);
                                                    //Start dashboard activity'
                                                    Intent intent = new Intent(RegistrationActivity.this, UserDashboard.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    signupprogress.setVisibility(View.GONE);
                                                    signup.setVisibility(View.VISIBLE);
                                                    helpers.showError(RegistrationActivity.this, "Registration Failed!".toUpperCase(), "Something went wrong.\nPlease try again later.");
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            signupprogress.setVisibility(View.GONE);
                            signup.setVisibility(View.VISIBLE);
                            helpers.showError(RegistrationActivity.this, "Registration Failed!".toUpperCase(), e.getMessage());
                        }
                    });

                }
                break;
            }
            case R.id.gotologin: {
                finish();
                break;
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
        if (strpass.length() < 6) {
            pass.setError("Enter valid password");
            flag = false;
        } else {
            pass.setError(null);
        }
        if (stremail.length() < 3 || !Patterns.EMAIL_ADDRESS.matcher(stremail).matches()) {
            email.setError("Enter a valid Email");
            flag = false;
        } else {
            email.setError(null);
        }
        if (strcpass.length() < 6) {
            cpass.setError("Enter valid password");
            flag = false;
        } else {
            cpass.setError(null);
        }
        if (strpass.length() > 5 && strcpass.length() > 5 && !strcpass.equals(strpass)) {
            cpass.setError("Password doesn't match");
            flag = false;
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

