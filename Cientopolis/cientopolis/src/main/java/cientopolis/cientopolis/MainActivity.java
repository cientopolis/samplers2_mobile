package cientopolis.cientopolis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import cientopolis.cientopolis.activities.LoginActivity;
import cientopolis.cientopolis.fragments.MainFragment;
import cientopolis.cientopolis.fragments.WorkflowsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int LOGIN_ACTIVITY_SUCCESS = 1;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.nav_user);
        TextView nav_email = (TextView)hView.findViewById(R.id.nav_email);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        Menu nav_Menu = navigationView.getMenu();
        SharedPreferences sharedPref = this.getSharedPreferences("Profile", Context.MODE_PRIVATE);
        String defaultValue = "";
        String uid = sharedPref.getString("id", defaultValue);
        if (uid != defaultValue){
            nav_Menu.findItem(R.id.login).setVisible(false);
            nav_Menu.findItem(R.id.logout).setVisible(true);
            nav_user.setText(sharedPref.getString("username", defaultValue));
            nav_email.setText(sharedPref.getString("email", defaultValue));
        } else {
            nav_Menu.findItem(R.id.login).setVisible(true);
            nav_Menu.findItem(R.id.logout).setVisible(false);
        }
        Fragment fragment = new MainFragment();
        goToFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LOGIN_ACTIVITY_SUCCESS) {
            navigationView.getMenu().getItem(0).setChecked(true);
            View hView =  navigationView.getHeaderView(0);
            TextView nav_user = (TextView)hView.findViewById(R.id.nav_user);
            TextView nav_email = (TextView)hView.findViewById(R.id.nav_email);
            SharedPreferences sharedPref = this.getSharedPreferences("Profile", Context.MODE_PRIVATE);
            nav_user.setText(sharedPref.getString("username", ""));
            nav_email.setText(sharedPref.getString("email", ""));
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.login).setVisible(false);
            nav_Menu.findItem(R.id.logout).setVisible(true);
            Fragment fragment = new MainFragment();
            goToFragment(fragment);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (id == R.id.workflows) {
            Fragment fragment = new WorkflowsFragment();
            goToFragment(fragment);

        } else if (id == R.id.logout) {
            SharedPreferences sharedPref = getSharedPreferences("Profile", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("id", "");
            LoginManager.getInstance().logOut();
            editor.commit();
            finish();
            startActivity(getIntent());

        } else if (id == R.id.login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_ACTIVITY_SUCCESS);

        } else if (id == R.id.home){
            Fragment fragment = new MainFragment();
            goToFragment(fragment);
        }

        return true;
    }

    public void goToFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_main, fragment);
        ft.commit();
    }
}
