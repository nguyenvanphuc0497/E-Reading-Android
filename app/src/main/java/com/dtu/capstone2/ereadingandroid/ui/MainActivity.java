package com.dtu.capstone2.ereadingandroid.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.dtu.capstone2.ereadingandroid.R;
import com.dtu.capstone2.ereadingandroid.datasource.repository.EReadingRepository;
import com.dtu.capstone2.ereadingandroid.network.request.AccountLoginRequest;
import com.dtu.capstone2.ereadingandroid.network.response.Token;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private EReadingRepository localRepository = new EReadingRepository();
    private String tk = "";
    private TextView tvTest;
    private String text;

   //test text selection action
    // Tracks current contextual action mode
    private ActionMode currentActionMode;
    // Define the callback when ActionMode is activated
    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Actions");
            mode.getMenuInflater().inflate(R.menu.actions_textview, menu);
            return true;
        }

        // Called each time the action mode is shown.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int min = 0;
            int max = tvTest.getText().length();
            if (tvTest.isFocused()) {
                final int selStart = tvTest.getSelectionStart();
                final int selEnd = tvTest.getSelectionEnd();

                min = Math.max(0, Math.min(selStart, selEnd));
                max = Math.max(0, Math.max(selStart, selEnd));
            }
            // Perform your definition lookup with the selected text
            final CharSequence selectedText = tvTest.getText().subSequence(min, max);
            // Finish and close the ActionMode
            switch (item.getItemId()) {
                case R.id.menu_choose:
                    Toast.makeText(MainActivity.this, "Chọn!" + selectedText, Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                case R.id.menu_add:
                    // Trigger the deletion here
                    mode.finish();
                    Toast.makeText(MainActivity.this, "Add!"+ selectedText, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_request:
                    mode.finish();
                    Toast.makeText(MainActivity.this, "Request!"+ selectedText, Toast.LENGTH_SHORT).show();
                    return true;

                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            currentActionMode = null; // Clear current action mode
        }
    };
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        tvTest = findViewById(R.id.tvTest);
        tvTest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (currentActionMode != null) { return false; }
                startActionMode(modeCallBack);
                v.setSelected(true);
                return true;
            }
        });

        localRepository.login(new AccountLoginRequest("admin", "admin123456"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Token>() {
                    @Override
                    public void accept(Token token) throws Exception {
                        tk = token.getToken();
                        Log.e("xxx", "accept: " + token);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }
}
