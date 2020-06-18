package lcwu.fyp.careclub.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.adapters.ProductsAdapter;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.Product;
import lcwu.fyp.careclub.model.User;

public class MyAssignedProducts extends AppCompatActivity {
    private LinearLayout loading;
    private RecyclerView products;
    private TextView noRecordFound;
    private Session session;
    private Helpers helpers;
    private User user;
    private List<Product> data;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
    private ProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_assigned_products);
        loading = findViewById(R.id.loading);
        products = findViewById(R.id.products);
        noRecordFound = findViewById(R.id.noRecordFound);
        session = new Session(MyAssignedProducts.this);
        helpers = new Helpers();
        user = session.getSession();
        data = new ArrayList<>();
        adapter = new ProductsAdapter(MyAssignedProducts.this, user.getRole());
        products.setLayoutManager(new LinearLayoutManager(MyAssignedProducts.this));
        products.setAdapter(adapter);
        loadProducts();
    }

    private void loadProducts() {
        if (!helpers.isConnected(MyAssignedProducts.this)) {
            helpers.showError(MyAssignedProducts.this, "Error", "Error occured due to internet connection");
            return;
        }
        loading.setVisibility(View.VISIBLE);
        products.setVisibility(View.GONE);
        noRecordFound.setVisibility(View.GONE);
        reference.orderByChild("userId").equalTo(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get children from datasnapshot
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Product product = d.getValue(Product.class);
                    if (product != null) {
                        data.add(product);
                    }
                }
                Log.e("Products", "Products list size: " + data.size());

                if (data.size() > 0) {
                    adapter.setData(data);
                    products.setVisibility(View.VISIBLE);
                    noRecordFound.setVisibility(View.GONE);
                } else {
                    products.setVisibility(View.GONE);
                    noRecordFound.setVisibility(View.VISIBLE);

                }
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
                products.setVisibility(View.VISIBLE);
                noRecordFound.setVisibility(View.GONE);
            }
        });

    }
}
