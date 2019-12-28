package lcwu.fyp.careclub.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class My_Profile extends Fragment {
    private Session session;
    private User user;
    private Helpers helpers;



    public My_Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my__profile, container, false);
        helpers=new Helpers();
        session=new Session(getActivity());
        user= session.getSession();




        return root;
    }


}
