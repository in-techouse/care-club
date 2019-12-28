package lcwu.fyp.careclub.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
    TextView fname,lname,email,phoneno;



    public My_Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my__profile, container, false);
        fname = root.findViewById(R.id.first_name);
        lname =root.findViewById(R.id.last_name);
        email=root.findViewById(R.id.email);
        phoneno=root.findViewById(R.id.phoneno);

        helpers=new Helpers();
        session=new Session(getActivity());
        user = session.getSession();
        Log.e("Profile", "First Name: "+ user.getFname());


        fname.setText(user.getFname());
        lname.setText(user.getLname());
        email.setText(user.getEmail());
        phoneno.setText(user.getPhone());

        return root;
    }


}
