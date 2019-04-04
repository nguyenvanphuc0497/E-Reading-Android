package com.dtu.capstone2.ereading.ui.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.account.register.FragmentRegister;
import com.dtu.capstone2.ereading.ui.login.LoginFragment;


/**
 * Create by Nguyen Van Phuc on 4/3/19
 */
public class ManagerAccountContainerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_account_container);
    }

    @Override
    protected void onResume() {
        super.onResume();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.layoutManagerAccountContainerActivity, new LoginFragment());
        ft.commit();
    }
}
