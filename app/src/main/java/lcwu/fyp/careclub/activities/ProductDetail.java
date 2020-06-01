package lcwu.fyp.careclub.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
import lcwu.fyp.careclub.model.Product;

public class ProductDetail extends AppCompatActivity {
    private Product productDetail;
    private TextView pname, pcategory, pquantity, paddress;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        productDetail = (Product) b.getSerializable("product");
        if (productDetail == null) {
            finish();
            return;
        }

        pname = findViewById(R.id.pname);
        pcategory = findViewById(R.id.pcategory);
        pquantity = findViewById(R.id.pquantity);
        paddress = findViewById(R.id.paddress);


        pname.setText(productDetail.getName());
        pcategory.setText(productDetail.getCategory());
        pquantity.setText(productDetail.getQuantityOfProducts() + "");
        paddress.setText(productDetail.getAddress());
    }
}

