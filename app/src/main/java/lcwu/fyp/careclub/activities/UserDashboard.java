package lcwu.fyp.careclub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Helpers;
import lcwu.fyp.careclub.director.NoSwipeableViewPager;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.fragment.MyDonations;
import lcwu.fyp.careclub.fragment.MyProducts;
import lcwu.fyp.careclub.fragment.MyProfile;
import lcwu.fyp.careclub.fragment.Ngos;
import lcwu.fyp.careclub.model.User;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private User user;
    private Session session;
    private Helpers helpers;
    private NoSwipeableViewPager pager;
    private PagerAdapter adapter;
    private Ngos ngos;
    private MyProducts myProducts;
    private MyDonations myDonations;
    private MyProfile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(UserDashboard.this, AddProduct.class);
                startActivity(it);
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ngos = new Ngos();
        myDonations = new MyDonations();
        myProducts = new MyProducts();
        myProfile = new MyProfile();

        pager = findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), 0);
        pager.setAdapter(adapter);

        session = new Session(getApplicationContext());
        user = session.getSession();
        helpers = new Helpers();
        View header = navigationView.getHeaderView(0);
        ImageView imageView = header.findViewById(R.id.imageView);
        TextView name = header.findViewById(R.id.name);
        TextView email = header.findViewById(R.id.email);
        TextView phone = header.findViewById(R.id.phone);

        name.setText(user.getFname() + " " + user.getLname());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Log.e("UserDashboard", "" + id);
        switch (id) {
            case R.id.nav_ngo: {
                pager.setCurrentItem(0);
                break;
            }
            case R.id.nav_myProduct: {
                pager.setCurrentItem(1);
                break;
            }
            case R.id.nav_myDonation: {
                pager.setCurrentItem(2);
                break;
            }
            case R.id.nav_profile: {
                pager.setCurrentItem(3);
                break;
            }
            case R.id.nav_logout: {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                session.destroySession();
                Intent it = new Intent(UserDashboard.this, LoginActivity.class);
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
                    return ngos;
                }
                case 1: {
                    return myProducts;
                }
                case 2: {
                    return myDonations;
                }
                default: {
                    return myProfile;
                }
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
