package com.hakodzhak.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);

        TextView title = new TextView(this);
        title.setText("⚙️ Settings");
        title.setTextSize(20);
        layout.addView(title);

        SharedPreferences prefs = getSharedPreferences("config", MODE_PRIVATE);

        EditText tokenInput = new EditText(this);
        tokenInput.setHint("Bot Token");
        tokenInput.setText(prefs.getString("botToken", ""));
        layout.addView(tokenInput);

        EditText chatIdInput = new EditText(this);
        chatIdInput.setHint("Chat ID");
        chatIdInput.setText(prefs.getString("chatId", ""));
        layout.addView(chatIdInput);

        Button saveBtn = new Button(this);
        saveBtn.setText("حفظ");
        layout.addView(saveBtn);

        setContentView(layout);

        saveBtn.setOnClickListener(v -> {
            prefs.edit()
                .putString("botToken", tokenInput.getText().toString())
                .putString("chatId", chatIdInput.getText().toString())
                .apply();
            Toast.makeText(this, "تم الحفظ", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
