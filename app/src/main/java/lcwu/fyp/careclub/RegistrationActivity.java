package lcwu.fyp.careclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
Button signup;
ProgressBar signupprogress;
TextView gotologin;
EditText fname,lname,pass,cpass,email;
String strfname,strlname,strpass,strcpass,stremail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        signup=findViewById(R.id.rgstrbtn);
        gotologin=findViewById(R.id.gotologin);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        pass=findViewById(R.id.pass);
        cpass=findViewById(R.id.cpass);
        email=findViewById(R.id.email);
        signupprogress=findViewById(R.id.signupprogress);

        signup.setOnClickListener(this);
        gotologin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
       case R.id.rgstrbtn:
       {
            strfname=fname.getText().toString();
            strlname=lname.getText().toString();
            strpass=pass.getText().toString();
            strcpass=cpass.getText().toString();
            stremail=email.getText().toString();

           boolean flag=isValid();
           if(flag) {
               signupprogress.setVisibility(View.VISIBLE);
               signup.setVisibility(View.GONE);
                //Firebase
               Log.e("Registeration","Gooning to start");

               FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(stremail,strcpass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                signupprogress.setVisibility(View.GONE);
                                signup.setVisibility(View.VISIBLE);
                                Log.e("Registeration","success");

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                signupprogress.setVisibility(View.GONE);
                                signup.setVisibility(View.VISIBLE);
                                Log.e("Registeration","Failure"+e.getMessage());
                            }
                        });

           }
           break;
       }
            case R.id.gotologin:
            {
                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
                break;
            }
        }}
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

