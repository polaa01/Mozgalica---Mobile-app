package com.example.mozgalica;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mozgalica.db.DBHelper;


public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //LocaleHelper.loadLocale(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(getString(R.string.app_name));
        //myToolbar.getContext().setTheme(R.style.ToolbarTheme);

        btnLogin = findViewById(R.id.btn_login);
        usernameText = findViewById(R.id.editTextText);

        DBHelper db = new DBHelper(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString().trim();

                if(username.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.enter_username), Toast.LENGTH_SHORT).show();
                }
               else {
                    if (!db.doesUserExists(username)) {
                        boolean result = db.insertUser(username);

                        if (result) {
                            Toast.makeText(getApplicationContext(), getString(R.string.user_created) , Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.err), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.welcome_back), Toast.LENGTH_SHORT).show();
                    }

                    //SharedPreferences prefs = getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);

                    //SharedPreferences.Editor editor = prefs.edit();
                    //editor.putString("username", username);
                    //editor.apply();

                    //LocaleHelper.loadLocale(getApplicationContext());

                    Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}