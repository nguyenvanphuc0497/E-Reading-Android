package com.dtu.capstone2.ereading.ui.account;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.ui.account.login.LoginFragment;
import com.dtu.capstone2.ereading.ui.utils.BaseActivity;

/**
 * Create by Nguyen Van Phuc on 4/3/19
 */
public class ManagerAccountContainerActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_account_container);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layoutManagerAccountContainerActivity, new LoginFragment())
                .commit();
    }
}
