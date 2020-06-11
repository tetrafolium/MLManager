package com.javiersantos.mlmanager.async;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import com.afollestad.materialdialogs.MaterialDialog;
import com.javiersantos.mlmanager.R;
import com.javiersantos.mlmanager.utils.UtilsApp;
import com.javiersantos.mlmanager.utils.UtilsDialog;
import com.javiersantos.mlmanager.utils.UtilsRoot;

public class DeleteDataInBackground extends AsyncTask<Void, String, Boolean> {
  private Context context;
  private Activity activity;
  private MaterialDialog dialog;
  private String directory;
  private String successDescription;

  public DeleteDataInBackground(final Context context,
                                final MaterialDialog dialog,
                                final String directory,
                                final String successDescription) {
    this.context = context;
    this.activity = (Activity)context;
    this.dialog = dialog;
    this.directory = directory;
    this.successDescription = successDescription;
  }

  @Override
  protected Boolean doInBackground(final Void... voids) {
    Boolean status = false;

    if (UtilsApp.checkPermissions(activity)) {
      status = UtilsRoot.removeWithRootPermission(directory);
    }

    return status;
  }

  @Override
  protected void onPostExecute(final Boolean status) {
    super.onPostExecute(status);
    dialog.dismiss();
    if (status) {
      UtilsDialog.showSnackbar(activity, successDescription, null, null, 2)
          .show();
    } else {
      UtilsDialog.showTitleContent(
          context,
          context.getResources().getString(R.string.dialog_root_required),
          context.getResources().getString(
              R.string.dialog_root_required_description));
    }
  }
}
