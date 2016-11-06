package com.goshopping.usx.goshopping.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.goshopping.usx.goshopping.R;
import com.goshopping.usx.goshopping.ui.fragment.HomePageMovieNotReFragment;
import com.goshopping.usx.goshopping.ui.fragment.HomePageMovieReFragment;


public class HomePageViewMovieActivity extends AppCompatActivity {

    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_movie);
        fm=getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.movie_content,new HomePageMovieReFragment());
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction transaction=fm.beginTransaction();



        switch (id) {
            case R.id.menu_setting:
                transaction.replace(R.id.movie_content,new HomePageMovieReFragment());

                break;
            case R.id.menu_location:
                transaction.replace(R.id.movie_content,new HomePageMovieNotReFragment());
                break;

        }
        transaction.commit();
        return true;
    }
}
