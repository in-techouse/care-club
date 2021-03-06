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
import java.util.List;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.adapters.NgosAdapter;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.model.NGOs;

public class Ngos extends Fragment {
    private LinearLayout loading;
    private TextView noRecordFound;
    private RecyclerView ngos;
    private Helpers helpers;
    private List<NGOs> data;
    private NgosAdapter adapter;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("NGOS");

    public Ngos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ngos, container, false);
        loading = root.findViewById(R.id.loading);
        noRecordFound = root.findViewById(R.id.noRecordFound);
        ngos = root.findViewById(R.id.ngos);

        helpers = new Helpers();
        data = new ArrayList<>();

        adapter = new NgosAdapter(getActivity());

        ngos.setLayoutManager(new LinearLayoutManager(getActivity()));
        ngos.setAdapter(adapter);
        LoadNgos();

        return root;
    }

    private void LoadNgos() {
        if (!helpers.isConnected(getActivity())) {
            helpers.showError(getActivity(), "Error", "Error Occurr Due To Internet Connection");
            return;
        }
        loading.setVisibility(View.VISIBLE);
        noRecordFound.setVisibility(View.GONE);
        ngos.setVisibility(View.GONE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    NGOs ngos = d.getValue(NGOs.class);
                    if (ngos != null) {
                        data.add(ngos);
                    }
                }
                if (data.size() > 0) {
                    adapter.setData(data);
                    ngos.setVisibility(View.VISIBLE);
                    noRecordFound.setVisibility(View.GONE);
                } else {
                    ngos.setVisibility(View.GONE);
                    noRecordFound.setVisibility(View.VISIBLE);
                }
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
                ngos.setVisibility(View.VISIBLE);
                noRecordFound.setVisibility(View.GONE);
            }
        });
    }
}
