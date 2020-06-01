package lcwu.fyp.careclub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.adapters.PaymentAdapter;
import lcwu.fyp.careclub.model.NGOs;
import lcwu.fyp.careclub.model.PaymentMethod;

public class NGODetail extends AppCompatActivity implements View.OnClickListener {
    private NGOs ngOs;
    private TextView ngoName, name, email, phne, address;
    private RecyclerView paymentMethod;
    private DatabaseReference refrence = FirebaseDatabase.getInstance().getReference().child("PaymentMethods");
    private ValueEventListener listner;
    private List<PaymentMethod> methods;
    private Button makeDonation, close;
    private PaymentAdapter adapter;
    private BottomSheetBehavior sheetBehavior;
    private LinearLayout mainSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngodetail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        Intent it = getIntent();
        if (it == null) {
            finish();
            return;
        }

        Bundle b = it.getExtras();
        if (b == null) {
            finish();
            return;
        }
        ngOs = (NGOs) b.getSerializable("NGO");
        if (ngOs == null) {
            finish();
            return;
        }

        ngoName = findViewById(R.id.ngoName);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phne = findViewById(R.id.phne);
        address = findViewById(R.id.address);
        paymentMethod = findViewById(R.id.paymentMethod);
        makeDonation = findViewById(R.id.makeDonation);
        close = findViewById(R.id.close);

        makeDonation.setOnClickListener(this);
        close.setOnClickListener(this);

        ngoName.setText(ngOs.getName());
        name.setText(ngOs.getName());
        email.setText(ngOs.getEmail());
        phne.setText(ngOs.getPhone());
        address.setText(ngOs.getAddress());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        paymentMethod.setLayoutManager(linearLayoutManager);
        methods = new ArrayList<>();
        adapter = new PaymentAdapter();
        paymentMethod.setAdapter(adapter);

        LinearLayout layoutBottomSheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setHideable(true);
        sheetBehavior.setPeekHeight(0);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                Log.e("NGODetail", "State is: " + i);
                if (i == 4 || i == 5) {
                    closeBottomSheet();
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        loadPaymentMethods();
    }

    private void loadPaymentMethods() {
        listner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                refrence.orderByChild("ngoId").equalTo(ngOs.getId()).removeEventListener(listner);
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    PaymentMethod m = data.getValue(PaymentMethod.class);
                    if (m != null) {
                        methods.add(m);
                    }
                }
                Log.e("NGODetail", "Methods Size: " + methods.size());
                adapter.setMethods(methods);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                refrence.orderByChild("ngoId").equalTo(ngOs.getId()).removeEventListener(listner);
            }
        };
        refrence.orderByChild("ngoId").equalTo(ngOs.getId()).addValueEventListener(listner);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.makeDonation: {
                showBottomSheet();
                break;
            }
            case R.id.close: {
                closeBottomSheet();
                break;
            }
        }
    }

    public void showBottomSheet() {
        sheetBehavior.setHideable(false);
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        makeDonation.setText("DONATE");
    }

    public void closeBottomSheet() {
        sheetBehavior.setHideable(true);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        makeDonation.setText("MAKE DONATION");
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
