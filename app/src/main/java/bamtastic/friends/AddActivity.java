package bamtastic.friends;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends FragmentActivity {

    private static final String TAG = AddActivity.class.getSimpleName();
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

        mContentResolver = AddActivity.this.getContentResolver();

        mNameTextView = (TextView) findViewById(R.id.friend_name);
        mEmailTextView = (TextView) findViewById(R.id.friend_email);
        mPhoneTextView = (TextView) findViewById(R.id.friend_phone);

        mButton = (Button) findViewById(R.id.save_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    ContentValues values = new ContentValues();
                    values.put(FriendsContract.Columns.FRIENDS_NAME, mNameTextView.getText().toString());
                    values.put(FriendsContract.Columns.FRIENDS_EMAIL, mEmailTextView.getText().toString());
                    values.put(FriendsContract.Columns.FRIENDS_PHONE, mPhoneTextView.getText().toString());
                    Uri returned = mContentResolver.insert(FriendsContract.URI_TABLE, values);
                    Log.d(TAG, "onClick: Record id returned is " + returned);

                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddActivity.this, "No valid data available.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValid() {
        return !(mNameTextView.getText().toString().length() == 0 ||
                       mPhoneTextView.getText().toString().length() == 0 ||
                       mEmailTextView.getText().toString().length() == 0);
    }

    private boolean dataEntered() {
        return (mNameTextView.getText().toString().length() > 0 ||
                      mPhoneTextView.getText().toString().length() > 0 ||
                      mEmailTextView.getText().toString().length() > 0);
    }

    @Override
    public void onBackPressed() {
        if (dataEntered()) {
            FriendsDialog dialog = new FriendsDialog();
            Bundle args = new Bundle();
            args.putString(FriendsDialog.DIALOG_TYPE, FriendsDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "confirm-exit");
        } else {
            super.onBackPressed();
        }
    }
}
