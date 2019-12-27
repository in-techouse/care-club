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
public class My_Profile extends Fragment {


    public My_Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my__profile, container, false);
    }

}
