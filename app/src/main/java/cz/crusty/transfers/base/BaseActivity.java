package cz.crusty.transfers.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Crusty. 03.09.2018
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected void addFragment(int containerId, Fragment fragment) {
        addFragment(containerId, fragment, null);
    }

    protected void addFragment(int containerId, Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFrag = fm.findFragmentById(containerId);
        if(currentFrag == null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(containerId, fragment, tag);
            ft.commit();
        }
    }

}
