package lcwu.fyp.careclub.activities;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import lcwu.fyp.careclub.R;
import lcwu.fyp.careclub.director.Session;
import lcwu.fyp.careclub.model.User;

public class SplashActivity extends AwesomeSplash {

    @Override
    public void initSplash(ConfigSplash configSplash) {
        /* you don't have to override every property */

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.textcolorWhite); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.logo2); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        //Customize Title
        configSplash.setTitleSplash("Care Club");
        configSplash.setTitleTextColor(R.color.colorAccent);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(1500);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
    }

    @Override
    public void animationsFinished() {
        Session session = new Session(SplashActivity.this);
        Intent it;
        if (session.getSession() == null) {
            it = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(it);
            finish();
        } else {
            User u = session.getSession();
            if (u.getRole() == 1) {
                it = new Intent(SplashActivity.this, RiderDashboard.class);
            } else {
                it = new Intent(SplashActivity.this, UserDashboard.class);

            }
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(it);
            finish();
        }
    }
}
