package bamtastic.friends;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditActivity extends FragmentActivity {

    private static final String TAG = EditActivity.class.getSimpleName();
    private TextView mNameTextView;
    private TextView mEmailTextView;
    private TextView mPhoneTextView;
    private Button mButton;
    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mContentResolver = EditActivity.this.getContentResolver();

        mNameTextView = (TextView) findViewById(R.id.friend_name);
        mEmailTextView = (TextView) findViewById(R.id.friend_email);
        mPhoneTextView = (TextView) findViewById(R.id.friend_phone);

        Intent intent = getIntent();
        final String _id = intent.getStringExtra(FriendsContract.Columns.FRIENDS_ID);
        String name = intent.getStringExtra(FriendsContract.Columns.FRIENDS_NAME);
        String email = intent.getStringExtra(FriendsContract.Columns.FRIENDS_EMAIL);
        String phone = intent.getStringExtra(FriendsContract.Columns.FRIENDS_PHONE);

        mNameTextView.setText(name);
        mEmailTextView.setText(email);
        mPhoneTextView.setText(phone);

        mButton = (Button) findViewById(R.id.save_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(FriendsContract.Columns.FRIENDS_NAME, mNameTextView.getText().toString());
                values.put(FriendsContract.Columns.FRIENDS_EMAIL, mEmailTextView.getText().toString());
                values.put(FriendsContract.Columns.FRIENDS_PHONE, mPhoneTextView.getText().toString());
                Uri uri = FriendsContract.Friends.buildFriendsUri(_id);
                int recordsUpdated = mContentResolver.update(uri, values, null, null);
                Log.d(TAG, "onClick: Number of records updated " + recordsUpdated);

                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }
}
