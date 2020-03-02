package com.offgrid.coupler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.offgrid.coupler.model.Info;


public class MockActivity extends AppCompatActivity {


    private Info info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        info = Info.getInstance(getIntent().getExtras());

        setContentView(R.layout.activity_mock);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(info.getTitle());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });

        final TextView textView = findViewById(R.id.text);
        textView.setText(info.getText());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (info.getAction().equals(Info.Action.chat_room)) {
            getMenuInflater().inflate(R.menu.menu_mock_with_settings, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_mock, menu);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_done) {
            setResult(RESULT_OK, new Intent());
            finish();
            return true;
        }

        if (id == R.id.action_settings) {
            Toast.makeText(MockActivity.this, "Called action_settings " + item.getItemId(), Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

