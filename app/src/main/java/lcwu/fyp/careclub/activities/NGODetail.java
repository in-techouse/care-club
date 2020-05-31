package lcwu.fyp.careclub.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.model.NGOs;
import lcwu.fyp.careclub.model.PaymentMethod;

public class NGODetail extends AppCompatActivity {
    private NGOs ngOs;
    private TextView name, email, phne, address;
    private RecyclerView paymentMethod;
    private DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("PaymentMethods");
    private ValueEventListener listner;
    private List<PaymentMethod> methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngodetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent it = getIntent();
        if (it == null) {
            fileList();
            return;
        }

        Bundle b = it.getExtras();
        if (b == null) {
            fileList();
            return;
        }
        ngOs = (NGOs) b.getSerializable("NGO");
        if (ngOs == null) {
            fileList();
            return;
        }

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phne = findViewById(R.id.phne);
        address = findViewById(R.id.address);
        paymentMethod = findViewById(R.id.paymentMethod);

        name.setText(ngOs.getName());
        email.setText(ngOs.getEmail());
        phne.setText(ngOs.getPhone());
        address.setText(ngOs.getAddress());
        paymentMethod.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        methods= new ArrayList<>();
        loadPaymentMethods();

    }

    private void loadPaymentMethods() {
        listner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                refrence.orderByChild("ngoId").equalTo(ngOs.getId()).removeEventListener(listner);
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    PaymentMethod m = data.getValue(PaymentMethod.class);
                    if (m!=null){
                        methods.add(m);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                refrence.orderByChild("ngoId").equalTo(ngOs.getId()).removeEventListener(listner);
            }
        };
        refrence.orderByChild("ngoId").equalTo(ngOs.getId()).addValueEventListener(listner);
    }

}
