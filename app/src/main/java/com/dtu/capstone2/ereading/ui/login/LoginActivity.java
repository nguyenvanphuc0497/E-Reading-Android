package com.dtu.capstone2.ereading.ui.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dtu.capstone2.ereading.R;
import com.dtu.capstone2.ereading.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereading.network.request.DataLoginRequest;
import com.dtu.capstone2.ereading.ui.home.HomeActivity;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginviewmodel = new LoginViewModel();
    private String strToken;
    private Integer intIduser;
    private String strUserName;
    private String strPassword;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    final Context context = this;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
        edtPassword = findViewById(R.id.txtPassword);
        edtUsername = findViewById(R.id.txtUsername);

        btnLogin = findViewById(R.id.btnSignInAccount);
        /*
         * this is function event click button login*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserName = edtUsername.getText().toString();
                strPassword = edtPassword.getText().toString();

                loginviewmodel.GetDataLoginRequest(new AccountLoginRequest(strUserName, strPassword))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<DataLoginRequest>() {
                            @Override
                            public void accept(DataLoginRequest dataLoginRequest) throws Exception {
                                strToken = dataLoginRequest.getStringToken();
                                intIduser = dataLoginRequest.getIntId();
                                final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "",
                                        "Loading. Please wait...", true);
                                final Runnable runnable=new Runnable() {
                                    @Override
                                    public void run() {
                                        dialog.cancel();
                                    }
                                };
                                Handler handler=new Handler();
                                handler.postDelayed(runnable,2000);
                                Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                                dialog.setCancelable(false);
                                dialog.setTitle("Lỗi");
                                dialog.setMessage("Tên đăng nhập hoặc mật khẩu không chính xác" );
                                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Action for "Delete".
                                    }
                                });

                                final AlertDialog alert = dialog.create();
                                alert.show();
//                                Log.e("xxx", " mesage" + ((HttpException) throwable).message());
//                                Log.e("xxx", " code" + ((HttpException)throwable).code());
                            }
                        });

            }
        });


    }

//    private FirebaseAuth mAuth;
//    private static final int RC_SIGN_IN = 123;
//    private static final String TAG = LoginActivity.class.getSimpleName();
//    private GoogleSignInClient mGoogleSignInClient;
//    private SignInButton mSignIn;
//    private TextView mTvInfo;
//    private Button mSignOut;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        // Build a GoogleSignInClient with the options specified by gso.
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        mAuth = FirebaseAuth.getInstance();
//
//        mSignOut = findViewById(R.id.btnSignOut);
//        mSignOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        mTvInfo.setText("Hello Work");
//                        mSignOut.setVisibility(View.GONE);
//                        mSignIn.setVisibility(View.VISIBLE);
//                    }
//                });
//            }
//        });
//        mTvInfo = findViewById(R.id.tvInfo);
//        mSignIn = findViewById(R.id.btnSignIn);
//        mSignIn.setSize(SignInButton.SIZE_WIDE);
//        mSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//            }
//        });
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//
//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//                // ...
//            }
//        }
//    }
//
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            updateUI(null);
//                        }
//                    }
//                });
//    }
//
//    private void updateUI(UserInfo user) {
//        if (user != null) {
//            mSignOut.setVisibility(View.VISIBLE);
//            mSignIn.setVisibility(View.GONE);
//            mTvInfo.setText("Name: " + user.getDisplayName() + "; Email: " + user.getEmail() + "; SDT:" + user.getPhoneNumber());
//        }
//    }
}
