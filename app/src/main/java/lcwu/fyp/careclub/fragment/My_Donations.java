package lcwu.fyp.careclub.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import lcwu.fyp.careclub.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class My_Donations extends Fragment {
    private LinearLayout loading;
    private TextView noRecordFound;
    private RecyclerView donations;

    public My_Donations() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_my__donations, container, false);
        loading = root.findViewById(R.id.loading);
        noRecordFound = root.findViewById(R.id.noRecordFound);
        donations = root.findViewById(R.id.donations);
        return root;
    }

}
