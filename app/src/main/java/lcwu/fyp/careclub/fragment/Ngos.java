package lcwu.fyp.careclub.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import lcwu.fyp.careclub.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ngos extends Fragment {
    ProgressBar pb;
    RecyclerView rv;
    LinearLayout mainlayout;
    LinearLayout emptylayout;

    public Ngos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_ngos, container, false);
        pb=root.findViewById(R.id.ngoprogress);
        rv=root.findViewById(R.id.ngos);


        return root;
    }

}
