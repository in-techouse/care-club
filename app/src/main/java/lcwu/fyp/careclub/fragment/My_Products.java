package lcwu.fyp.careclub.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.Products;
import lcwu.fyp.careclub.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class My_Products extends Fragment {

private  LinearLayout loading;
private RecyclerView products;
private TextView noRecordFound;
private Session session;
private Helpers helpers;
private User user;
private List<Products> product;
private DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
    public My_Products() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_my__products, container, false);
        loading=root.findViewById(R.id.loading);
        products=root.findViewById(R.id.products);
        noRecordFound=root.findViewById(R.id.noRecordFound);
        session = new Session(getActivity());
        helpers = new Helpers();
        user=session.getSession();
        product = new ArrayList<>();
        loadProducts();
        return root;
    }
    private void loadProducts(){
        if (!helpers.isConnected(getActivity()))
        {
            helpers.showError(getActivity(),"Error","Error occured due to internet connection");
            return;
        }
        loading.setVisibility(View.VISIBLE);
        products.setVisibility(View.GONE);
        noRecordFound.setVisibility(View.GONE);
        reference.orderByChild("userid").equalTo(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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
