package com.example.gigatlon.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gigatlon.MyApplication;
import com.example.gigatlon.MyPreferences;
import com.example.gigatlon.R;
import com.example.gigatlon.databinding.ActivityLoginBinding;
import com.example.gigatlon.databinding.ActivityMainBinding;
import com.example.gigatlon.repository.UserRepository;
import com.example.gigatlon.ui.home.HomeFragment;
import com.example.gigatlon.ui.logIn.fragment_logIn;
import com.example.gigatlon.viewmodel.RepositoryViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity   {

    private AppBarConfiguration mAppBarConfiguration;
    private UserRepository repository;
    private MyApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         application = (MyApplication)getApplication();
        if(application.getPreferences().getAuthToken() != null){
            setContentView(R.layout.activity_main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            build();


        }
        else{
            setContentView(R.layout.activity_login);
            Toolbar toolbar = findViewById(R.id.toolbar2);
            setSupportActionBar(toolbar);

        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void onLoggedIn() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        build();


    }

    private void onLoggedOut(){
       /* Intent intent = getIntent();
        finish();
        startActivity(intent);*/
        recreate();
        /*
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);*/
    }

    private void build(){

        if(findViewById(R.id.drawer_layout) instanceof DrawerLayout) {
            //Log.d("UI", findViewById(R.id.drawer_layout).getClass().toString() );
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);

            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_account, R.id.nav_routines, R.id.nav_favorites, R.id.nav_progress)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        }else{
            NavigationView navigationView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_account, R.id.nav_routines, R.id.nav_favorites, R.id.nav_progress)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        }
        findViewById(R.id.button_log_out).setOnClickListener(V ->{
            repository = application.getUserRepository();
            repository.logout().observe(this, resource ->{
                switch (resource.status) {
                    case LOADING:

                        break;
                    case SUCCESS:
                        application.getPreferences().removeToken();

                        onLoggedOut();
                        break;
                    case ERROR:

                        Toast.makeText(application, resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            });

           // Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
        });
    }


}