package lcwu.fyp.careclub.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.model.User;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
AppCompatButton signup;
ProgressBar signupprogress;
TextView gotologin;
EditText fname,lname,pass,cpass,email,phoneno;
String strfname,strlname,strpass,strcpass,stremail, strphoneno;
Helpers helpers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        signup=findViewById(R.id.rgstrbtn);
        gotologin=findViewById(R.id.gotologin);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        phoneno=findViewById(R.id.phoneno);
        pass=findViewById(R.id.pass);
        cpass=findViewById(R.id.cpass);
        email=findViewById(R.id.email);
        signupprogress=findViewById(R.id.signupprogress);

        signup.setOnClickListener(this);
        gotologin.setOnClickListener(this);

        helpers=new Helpers();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rgstrbtn: {
                boolean isConn = helpers.isConnected(RegistrationActivity.this);
                if (!isConn) {
                    helpers.showError(RegistrationActivity.this, "Internet Error", "Internet Error");
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
                        Log.e("Registeration", "Gooning to start");

                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.createUserWithEmailAndPassword(stremail, strcpass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                        User user = new User();
//                                        reference.child("Users").setValue()

//                                signupprogress.setVisibility(View.GONE);
//                                signup.setVisibility(View.VISIBLE);
//                                Log.e("Registeration","success");
//                                Intent it=new Intent(RegistrationActivity.this,Dashboard.class);
//                                startActivity(it);
//                                finish();


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                helpers.showError(RegistrationActivity.this,"Title",e.getMessage());
                                signupprogress.setVisibility(View.GONE);
                                signup.setVisibility(View.VISIBLE);
                                Log.e("Registeration", "Failure" + e.getMessage());
                                helpers.showError(RegistrationActivity.this, "Registration Failed", e.getMessage());
                            }
                        });

                    }
                    break;
                }
                case R.id.gotologin: {
                    Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(i);
                    break;
                }
            }
        }

        private boolean isValid()
        {
            boolean flag=true;
            if(strfname.length()<3){
                fname.setError("enter a valid first name");
                flag=false;

            }
            else {
                fname.setError(null);
            }
            if (strlname.length()<3){
                lname.setError("Enter valid last name");
                flag=false;
            }
            else {
                lname.setError(null);
            }
            if (strphoneno.length()<12){
                phoneno.setError("Enter valid last name");
                flag=false;
            }
            else {
                lname.setError(null);
            }
            if (strpass.length()<6){
                pass.setError("Enter valid password");
                flag=false;
            }
            else {
                pass.setError(null);
            }
            if(stremail.length()<3|| !Patterns.EMAIL_ADDRESS.matcher(stremail).matches()){
                email.setError("Enter a valid Email");
                flag=false;
            }
            else {
                email.setError(null);
            }
            if (strcpass.length()<6){
                cpass.setError("Enter valid password");
                flag=false;
            }
            else {
                cpass.setError(null);
            }
            return flag;
        }
}
