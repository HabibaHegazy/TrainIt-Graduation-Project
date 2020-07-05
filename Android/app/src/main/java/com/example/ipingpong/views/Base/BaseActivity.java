package com.example.ipingpong.views.Base;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ipingpong.R;
import com.example.ipingpong.shared.datasource.local.SharedPrefManager;
import com.example.ipingpong.shared.entities.DialogEntities;
import com.example.ipingpong.util.CustomDialog;
import com.example.ipingpong.views.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.signOutBtn:
                // clear shared preferences and go to the login activity
                SharedPrefManager sharedPrefManager= new SharedPrefManager(getApplicationContext());
                sharedPrefManager.logout();

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case R.id.passwordSettingsBtn:
                CustomDialog customDialogPassword = new CustomDialog(DialogEntities.ChangePassword);
                customDialogPassword.show(getSupportFragmentManager(), "Change Password");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment fragment, String fragment_tag, Boolean animate){

        if(fragment != null){

            FragmentManager fm =  getSupportFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);

            if(animate){
                ft.setCustomAnimations(R.anim.right_enter, R.anim.left_out);
            }

            ft.replace(R.id.fragment_container, fragment, fragment_tag);
            getSupportActionBar().setTitle(fragment_tag);
            ft.commit();
        }
    }
}
