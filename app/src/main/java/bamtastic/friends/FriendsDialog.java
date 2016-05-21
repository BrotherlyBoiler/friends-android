package bamtastic.friends;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class FriendsDialog extends DialogFragment {

    private static final String TAG = FriendsDialog.class.getSimpleName();

    private LayoutInflater mLayoutInflater;
    public static final String DIALOG_TYPE = "command";
    public static String DELETE_RECORD = "delete_record";
    public static String DELETE_DATABASE = "delete_database";
    public static final String CONFIRM_EXIT = "confirm_exit";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mLayoutInflater = getActivity().getLayoutInflater();
        final View view = mLayoutInflater.inflate(R.layout.friend_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        String command = getArguments().getString(DIALOG_TYPE);
        if (command != null && command.equals(DELETE_RECORD)) {
            final int _id = getArguments().getInt(FriendsContract.Columns.FRIENDS_ID);
            String name = getArguments().getString(FriendsContract.Columns.FRIENDS_NAME);

            TextView message = (TextView) view.findViewById(R.id.popup_message);
            message.setText(getString(R.string.delete_friend, "\"" + name + "\""));

            builder.setPositiveButton(getString(R.string.dialog_delete),
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          if (which == Dialog.BUTTON_POSITIVE) {
                              ContentResolver contentResolver = getActivity().getContentResolver();
                              Uri uri = FriendsContract.Friends.buildFriendsUri(String.valueOf(_id));
                              contentResolver.delete(uri, null, null);
                              // Return to mainActivity after deletion
                              Intent intent = new Intent(getActivity().getApplicationContext(),
                                                              MainActivity.class);
                              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                              startActivity(intent);
                          }
                      }
                  });
            builder.setNegativeButton(getString(R.string.dialog_cancel),
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          if (which == Dialog.BUTTON_NEGATIVE) {
                              dialog.dismiss();
                          }
                      }
                  });
        } else if (command != null && command.equals(DELETE_DATABASE)) {
            TextView message = (TextView) view.findViewById(R.id.popup_message);
            message.setText(getString(R.string.delete_database));

            builder.setPositiveButton(getString(R.string.dialog_delete),
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          if (which == Dialog.BUTTON_POSITIVE) {
                              ContentResolver contentResolver = getActivity().getContentResolver();
                              Uri uri = FriendsContract.URI_TABLE;
                              contentResolver.delete(uri, null, null);
                              // Return to mainActivity after deletion
                              Intent intent = new Intent(getActivity().getApplicationContext(),
                                                              MainActivity.class);
                              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                              startActivity(intent);
                          }
                      }
                  });
            builder.setNegativeButton(getString(R.string.dialog_cancel),
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          if (which == Dialog.BUTTON_NEGATIVE) {
                              dialog.dismiss();
                          }
                      }
                  });
        } else if (command != null && command.equals(CONFIRM_EXIT)) {
            TextView message = (TextView) view.findViewById(R.id.popup_message);
            message.setText(getString(R.string.exit_without_save));

            builder.setPositiveButton(getString(R.string.dialog_discard),
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          if (which == Dialog.BUTTON_POSITIVE) {
                              getActivity().finish();
                          }
                      }
                  });
            builder.setNegativeButton(getString(R.string.dialog_cancel),
                  new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          if (which == Dialog.BUTTON_NEGATIVE) {
                              dialog.dismiss();
                          }
                      }
                  });
        } else {
            Log.d(TAG, "onCreateDialog: Invalid command");
        }
        return builder.create();
    }
}
