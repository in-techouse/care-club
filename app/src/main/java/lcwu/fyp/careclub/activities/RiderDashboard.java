package lcwu.fyp.careclub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.NoSwipeableViewPager;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.fragment.MyProfile;
import lcwu.fyp.careclub.fragment.PickedProducts;
import lcwu.fyp.careclub.fragment.Rider_Product;
import lcwu.fyp.careclub.model.NGOs;
import lcwu.fyp.careclub.model.User;

public class RiderDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("NGOS");
    private ValueEventListener listener;
    private DrawerLayout drawer;
    private Session session;
    private NoSwipeableViewPager pager;
    private Rider_Product myProducts;
    private MyProfile myProfile;
    private PickedProducts pickedProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        myProducts = new Rider_Product();
        myProfile = new MyProfile();
        pickedProducts = new PickedProducts();

        pager = findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 0);
        pager.setAdapter(adapter);

        session = new Session(getApplicationContext());
        User user = session.getSession();
        View header = navigationView.getHeaderView(0);

        CircleImageView imageView = header.findViewById(R.id.imageView);
        if (user.getImage() != null && user.getImage().length() > 0) {
            Glide.with(getApplicationContext()).load(user.getImage()).into(imageView);
        }
        TextView name = header.findViewById(R.id.name);
        TextView email = header.findViewById(R.id.email);
        TextView ngos = header.findViewById(R.id.ngos);
        TextView phone = header.findViewById(R.id.phone);

        NGOs ngo = session.getNgo();
        if (ngo == null) {
            listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (listener != null)
                        reference.child(user.getNgoId()).removeEventListener(listener);
                    if (dataSnapshot.exists()) {
                        NGOs n = dataSnapshot.getValue(NGOs.class);
                        if (n != null) {
                            session.setNGO(n);
                            ngos.setText(n.getName());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    if (listener != null)
                        reference.child(user.getNgoId()).removeEventListener(listener);
                }
            };
            reference.child(user.getNgoId()).addValueEventListener(listener);
        } else {
            ngos.setText(ngo.getName());
        }

        name.setText(user.getFname() + " " + user.getLname());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Log.e("UserDashboard", "" + id);
        switch (id) {
            case R.id.nav_products: {
                pager.setCurrentItem(0);
                break;
            }
            case R.id.nav_picked_products: {
                pager.setCurrentItem(1);
                break;
            }
            case R.id.nav_profile: {
                pager.setCurrentItem(2);
                break;
            }
            case R.id.nav_logout: {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                session.destroySession();
                Intent it = new Intent(RiderDashboard.this, LoginActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(it);
                finish();
                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return myProducts;
                }
                case 1: {
                    return pickedProducts;
                }
                case 2: {
                    return myProfile;
                }
                default: {
                    return myProfile;
                }
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
