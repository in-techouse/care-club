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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import de.hdodenhof.circleimageview.CircleImageView;
import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.NoSwipeableViewPager;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.fragment.MyDonations;
import lcwu.fyp.careclub.fragment.MyProducts;
import lcwu.fyp.careclub.fragment.MyProfile;
import lcwu.fyp.careclub.fragment.Ngos;
import lcwu.fyp.careclub.model.User;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Session session;
    private NoSwipeableViewPager pager;
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
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), 0);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        session = new Session(getApplicationContext());
        User user = session.getSession();
        View header = navigationView.getHeaderView(0);
        CircleImageView imageView = header.findViewById(R.id.imageView);
        if (user.getImage() != null && user.getImage().length() > 0) {
            Glide.with(getApplicationContext()).load(user.getImage()).into(imageView);
        }
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
                MaterialDialog mDialog = new MaterialDialog.Builder(UserDashboard.this)
                        .setTitle("Are You Sure?".toUpperCase())
                        .setMessage("Do you want to logout from the CARE CLUB?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", R.drawable.ic_action_okay, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                auth.signOut();
                                dialogInterface.dismiss();
                                session.destroySession();
                                Intent it = new Intent(UserDashboard.this, LoginActivity.class);
                                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(it);
                                finish();
                            }
                        })
                        .setNegativeButton("NO!", R.drawable.ic_action_close, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .build();

                // Show Dialog
                mDialog.show();
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
