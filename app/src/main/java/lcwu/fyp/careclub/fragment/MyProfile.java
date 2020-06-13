package lcwu.fyp.careclub.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.activities.EditUserProfile;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends Fragment implements View.OnClickListener {
    public MyProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);
        getActivity().getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.care_club_bg));

        TextView name = root.findViewById(R.id.name);
        TextView email = root.findViewById(R.id.email);
        TextView phoneno = root.findViewById(R.id.phoneno);
        TextView edit = root.findViewById(R.id.edit);
        CircleImageView profile_image = root.findViewById(R.id.profile_image);
        edit.setOnClickListener(this);


        Session session = new Session(getActivity());
        User user = session.getSession();
        Log.e("Profile", "First Name: " + user.getFname());

        if (user.getImage() != null && user.getImage().length() > 0) {
            Glide.with(getActivity()).load(user.getImage()).into(profile_image);
        }

        name.setText(user.getFname() + " " + user.getLname());
        email.setText(user.getEmail());
        phoneno.setText(user.getPhone());
        return root;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.edit: {
                Intent it = new Intent(getActivity(), EditUserProfile.class);
                startActivity(it);
            }
        }
    }
}
