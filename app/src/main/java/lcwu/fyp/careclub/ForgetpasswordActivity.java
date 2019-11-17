package lcwu.fyp.careclub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class ForgetpasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button forgottonpass;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        email=findViewById(R.id.email);
        forgottonpass=findViewById(R.id.send);

        forgottonpass.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.send:{
                String stremail=email.getText().toString();
                if(stremail.length()<=6||!Patterns.EMAIL_ADDRESS.matcher(stremail).matches()){
                    email.setError("Enter a valid email");
                }
                else {
                    email.setError("Your varification code has been send on your email");
                }
                break;
            }
        }
    }
}
