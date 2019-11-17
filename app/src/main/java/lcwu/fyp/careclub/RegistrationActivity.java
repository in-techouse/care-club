package lcwu.fyp.careclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
Button signup;
TextView gotologin;
EditText fname,lname,pass,cpass,email;
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

        signup.setOnClickListener(this);
        gotologin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
       case R.id.rgstrbtn:
       {
           String strfname=fname.getText().toString();
           String strlname=lname.getText().toString();
           String strpass=pass.getText().toString();
           String strcpass=cpass.getText().toString();
           String stremail=email.getText().toString();
           if(strfname.length()<3){
               fname.setError("enter a valid first name");

           }
           else {
               fname.setError(null);
           }
           if (strlname.length()<3){
               lname.setError("Enter valid last name");
           }
           else {
               lname.setError(null);
           }
           if (strpass.length()<6){
               pass.setError("Enter valid password");
           }
           else {
               pass.setError(null);
           }
           if(stremail.length()<3|| !Patterns.EMAIL_ADDRESS.matcher(stremail).matches()){
               email.setError("Enter a valid Email");
           }
           else {
               email.setError(null);
           }
           if (strcpass.length()<6||strcpass!=strpass){
               cpass.setError("Enter valid password");
           }
           else {
               cpass.setError(null);
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
}

