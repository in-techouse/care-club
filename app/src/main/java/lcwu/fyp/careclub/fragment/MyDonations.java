package lcwu.fyp.careclub.fragment;


import android.os.Bundle;
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
import java.util.Collections;
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.adapters.DonationAdapter;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.Donation;
import lcwu.fyp.careclub.model.NGOs;
import lcwu.fyp.careclub.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDonations extends Fragment {
    private LinearLayout loading;
    private TextView noRecordFound;
    private RecyclerView donations;
    private User user;
    private Helpers helpers;
    private List<Donation> data;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DonationAdapter adapter;
    private ValueEventListener listener;

    public MyDonations() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_donations, container, false);
        loading = root.findViewById(R.id.loading);
        noRecordFound = root.findViewById(R.id.noRecordFound);
        donations = root.findViewById(R.id.donations);

        Session session = new Session(getActivity());
        user = session.getSession();
        helpers = new Helpers();
        data = new ArrayList<>();
        donations.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DonationAdapter();
        donations.setAdapter(adapter);
        LoadDonations();
        return root;
    }

    private void LoadDonations() {
        if (!helpers.isConnected(getActivity())) {
            helpers.showError(getActivity(), "Error", "Error Occur Due To Internet Connection");
            loading.setVisibility(View.GONE);
            donations.setVisibility(View.VISIBLE);
            noRecordFound.setVisibility(View.GONE);
            return;
        }
        loading.setVisibility(View.VISIBLE);
        noRecordFound.setVisibility(View.GONE);
        donations.setVisibility(View.GONE);
        reference.child("Donations").orderByChild("userId").equalTo(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Donation donations = d.getValue(Donation.class);
                    if (donations != null) {
                        data.add(donations);
                    }
                }

                Collections.reverse(data);
                if (data.size() < 1) {
                    donations.setVisibility(View.GONE);
                    noRecordFound.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    adapter.setData(data);
                } else {
                    loadNgos();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
                donations.setVisibility(View.VISIBLE);
                noRecordFound.setVisibility(View.GONE);
            }
        });
    }

    private void loadNgos() {
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (listener != null)
                    reference.child("NGOS").addValueEventListener(listener);
                List<NGOs> ngOs = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    NGOs ngo = data.getValue(NGOs.class);
                    if (ngo != null) {
                        ngOs.add(ngo);
                    }
                }

                for (Donation d : data) {
                    for (NGOs n : ngOs) {
                        if (d.getNgoId().equals(n.getId())) {
                            d.setNgoCategory(n.getCategory());
                            d.setNgoName(n.getName());
                            d.setNgoEmail(n.getEmail());
                            d.setNgoContact(n.getPhone());
                        }
                    }
                }

                donations.setVisibility(View.VISIBLE);
                noRecordFound.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                adapter.setData(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (listener != null)
                    reference.child("NGOS").addValueEventListener(listener);
                loading.setVisibility(View.GONE);
                donations.setVisibility(View.VISIBLE);
                noRecordFound.setVisibility(View.GONE);
            }
        };

        reference.child("NGOS").addValueEventListener(listener);
    }
}
