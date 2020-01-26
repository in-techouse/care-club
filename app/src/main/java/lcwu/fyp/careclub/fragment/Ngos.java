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
import lcwu.fyp.careclub.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ngos extends Fragment {
    private LinearLayout loading;
    private TextView noRecordFound;
    private RecyclerView ngos;
    private Session session;
    private User  user;
    private Helpers helpers;
    private List<Ngos> Ngos;
    private DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("NGOS");



    public Ngos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_ngos, container, false);
        loading = root.findViewById(R.id.loading);
        noRecordFound = root.findViewById(R.id.noRecordFound);
        ngos = root.findViewById(R.id.ngos);

        session=new Session(getActivity());
        user=session.getSession();
        helpers=new Helpers();
        Ngos=new ArrayList<>();
        LoadNgos();



        return root;
    }
       private void LoadNgos(){
        if(!helpers.isConnected(getActivity())){
            helpers.showError(getActivity(),"Error","Error Occurr Due To Internet Connection");
            return;
           }
           loading.setVisibility(View.VISIBLE);
           noRecordFound.setVisibility(View.GONE);
           ngos.setVisibility(View.GONE);
           reference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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
