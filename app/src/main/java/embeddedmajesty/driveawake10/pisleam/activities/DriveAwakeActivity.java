package embeddedmajesty.driveawake10.pisleam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import embeddedmajesty.driveawake10.pisleam.R;
import embeddedmajesty.driveawake10.pisleam.fragments.AboutUsFragment;
import embeddedmajesty.driveawake10.pisleam.fragments.DashboardFragment;
import embeddedmajesty.driveawake10.pisleam.fragments.SensitizationFragment;
import embeddedmajesty.driveawake10.pisleam.fragments.SettingsFragment;

public class DriveAwakeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Toolbar appbar;
    ImageView bluetoothimg;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_awake);
        bottomNavigationView = findViewById(R.id.bottomNav);
        appbar =findViewById(R.id.app_bar);
       // setSupportActionBar(appbar);
        appbar.inflateMenu(R.menu.menuappbar);
//        appbar.setCollapseIcon(R.id.connect);
        bluetoothimg = findViewById(R.id.connect);


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new DashboardFragment()).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.Dashboard:
                        fragment =new DashboardFragment();
                        break;
                    case R.id.sensitization:
                        fragment =new SensitizationFragment();
                        break;
                    case R.id.settings:
                        fragment =new SettingsFragment();
                        break;
                    case R.id.about:
                        fragment =new AboutUsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
                return true;
            }
        });
        appbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.profile)
                { Intent login = new Intent(DriveAwakeActivity.this, ProfileActivity.class);
                    startActivity(login);
                }
                else if(item.getItemId()== R.id.logout)
                {
                    Intent login = new Intent(DriveAwakeActivity.this, MainActivity.class);
                    startActivity(login);
                }


                return false;
            }
        });
        bluetoothimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(DriveAwakeActivity.this,
                        BluetoothActivity.class);
                startActivity(mainIntent);
            }
        });

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuappbar, menu);
        return true;    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                /* DO EDIT */
         /*       return true;
            case R.id.logout:
                Intent login = new Intent(DriveAwakeActivity.this, MainActivity.class);
                startActivity(login);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

}
