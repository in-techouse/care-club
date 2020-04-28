package lcwu.fyp.careclub.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

public class My_Products extends Fragment {
    private LinearLayout loading;
    private RecyclerView products;
    private TextView noRecordFound;
    private Session session;
    private Helpers helpers;
    private User user;
    private List<Product> data;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
    private ProductsAdapter adapter;

    public My_Products() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my__products, container, false);
        loading = root.findViewById(R.id.loading);
        products = root.findViewById(R.id.products);
        noRecordFound = root.findViewById(R.id.noRecordFound);
        session = new Session(getActivity());
        helpers = new Helpers();
        user = session.getSession();
        data = new ArrayList<>();
        adapter = new ProductsAdapter(getActivity());
        products.setLayoutManager(new LinearLayoutManager(getActivity()));
        products.setAdapter(adapter);
        loadProducts();
        return root;
    }

    private void loadProducts() {
        if (!helpers.isConnected(getActivity())) {
            helpers.showError(getActivity(), "Error", "Error occured due to internet connection");
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
