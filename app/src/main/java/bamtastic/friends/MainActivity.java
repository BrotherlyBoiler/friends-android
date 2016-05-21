package bamtastic.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_friend);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(android.R.id.content) == null) {
            FriendsListFragment friendsListFragment = new FriendsListFragment();
            fragmentManager.beginTransaction()
                  .add(android.R.id.content, friendsListFragment)
                  .commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                          .setAction("Action", null).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.add_record:
                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(addIntent);
                break;
            case R.id.delete_db:
                FriendsDialog dialog = new FriendsDialog();
                Bundle args = new Bundle();
                args.putString(FriendsDialog.DIALOG_TYPE, FriendsDialog.DELETE_DATABASE);
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "delete_database");
                break;
            case R.id.search_record:
                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
