package lcwu.fyp.careclub.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lcwu.fyp.careclub.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ngos extends Fragment {


    public Ngos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_ngos, container, false);
        return root;
    }

}
