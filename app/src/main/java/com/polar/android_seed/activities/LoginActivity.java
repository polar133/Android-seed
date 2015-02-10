package com.polar.android_seed.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.polar.android_seed.R;
import com.polar.android_seed.models.services.UsuarioService;

import java.util.HashMap;


public class LoginActivity extends Activity {

    SharedPreferences preferencias;
    private UserLoginTask mAuthTask = null;
    // UI references.
    private EditText mUserView, mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferencias = getSharedPreferences("AppPref", Context.MODE_PRIVATE);

        if (preferencias.getBoolean("loggedIn", false)) {
            iniciarMain();
        }

        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUserView = (EditText) findViewById(R.id.login_user);

        mPasswordView = (EditText) findViewById(R.id.login_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login_user || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        String usuario = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(usuario)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        } else if (!isUsuarioValid(usuario)) {
            mUserView.setError(getString(R.string.error_usuario));
            focusView = mUserView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(usuario, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUsuarioValid(String usuario) {
        return usuario.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void iniciarMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    public class UserLoginTask extends AsyncTask<Void, Void, HashMap<String, Object>> {

        private final String mUsuario;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mUsuario = email;
            mPassword = password;
        }

        @Override
        protected HashMap<String, Object> doInBackground(Void... params) {
            return UsuarioService.getInstance().cargarLogin(getApplicationContext(), mUsuario, mPassword);
        }

        @Override
        protected void onPostExecute(final HashMap<String, Object> success) {
            mAuthTask = null;
            showProgress(false);

            if (success.keySet().contains("token")) {
                SharedPreferences.Editor prefEditor = preferencias.edit();
                prefEditor.putString("username", mUsuario);
                prefEditor.putBoolean("loggedIn", true);
                prefEditor.putString("token", success.get("token").toString());
                prefEditor.apply();

                iniciarMain();
            } else {
                Toast.makeText(getApplicationContext(), (CharSequence) success.get("error"), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


}



