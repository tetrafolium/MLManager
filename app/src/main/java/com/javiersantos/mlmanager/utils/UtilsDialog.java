package com.javiersantos.mlmanager.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.gc.materialdesign.widgets.SnackBar;
import com.javiersantos.mlmanager.AppInfo;
import com.javiersantos.mlmanager.MLManagerApplication;
import com.javiersantos.mlmanager.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import java.io.File;

public class UtilsDialog {

public static MaterialDialog showTitleContent(final Context context,
                                              final String title,
                                              final String content) {
	MaterialDialog.Builder materialBuilder =
		new MaterialDialog.Builder(context)
		.title(title)
		.content(content)
		.positiveText(context.getResources().getString(android.R.string.ok))
		.cancelable(true);
	return materialBuilder.show();
}

public static MaterialDialog
showTitleContentWithProgress(final Context context, final String title,
                             final String content) {
	MaterialDialog.Builder materialBuilder = new MaterialDialog.Builder(context)
	                                         .title(title)
	                                         .content(content)
	                                         .cancelable(false)
	                                         .progress(true, 0);
	return materialBuilder.show();
}

public static MaterialDialog.Builder showUninstall(final Context context) {
	return new MaterialDialog.Builder(context)
	       .title(context.getResources().getString(R.string.dialog_uninstall_root))
	       .content(context.getResources().getString(
				R.string.dialog_uninstall_root_description))
	       .positiveText(
		context.getResources().getString(R.string.button_uninstall))
	       .negativeText(context.getResources().getString(android.R.string.cancel))
	       .cancelable(false);
}

public static MaterialDialog.Builder showUninstalled(final Context context,
                                                     final AppInfo appInfo) {
	return new MaterialDialog.Builder(context)
	       .title(String.format(
			      context.getResources().getString(R.string.dialog_uninstalled_root),
			      appInfo.getName()))
	       .content(context.getResources().getString(
				R.string.dialog_uninstalled_root_description))
	       .positiveText(context.getResources().getString(R.string.button_reboot))
	       .negativeText(context.getResources().getString(R.string.button_later))
	       .cancelable(false);
}

/**
 * Show Snackbar
 * @param activity Activity
 * @param text Text of the Snackbar
 * @param buttonText Button text of the Snackbar
 * @param file File to remove if style == 1
 * @param style 1 for extracted APKs, 2 display without button and 3 for
 *     hidden apps
 * @return Snackbar to show
 */
public static SnackBar showSnackbar(final Activity activity,
                                    final String text,
                                    final @Nullable String buttonText,
                                    @Nullable final File file,
                                    final Integer style) {
	SnackBar snackBar;

	switch (style) {
	case 1:
		snackBar =
			new SnackBar(activity, text, buttonText, new View.OnClickListener() {
				@Override
				public void onClick(final View view) {
				        file.delete();
				}
			});
		break;
	case 2:
		snackBar = new SnackBar(activity, text, null, null);
		break;
	case 3:
		snackBar =
			new SnackBar(activity, text, buttonText, new View.OnClickListener() {
				@Override
				public void onClick(final View view) {
				        UtilsRoot.rebootSystem();
				}
			});
		break;
	default:
		snackBar = new SnackBar(activity, text, null, null);
		break;
	}

	return snackBar;
}

public static MaterialDialog showProFeatures(final Context context) {
	final MaterialSimpleListAdapter adapter =
		new MaterialSimpleListAdapter(context);
	adapter.add(
		new MaterialSimpleListItem.Builder(context)
		.content(context.getResources().getString(R.string.pro_feature_1))
		.icon(new IconicsDrawable(context)
		      .icon(GoogleMaterial.Icon.gmd_visibility_off)
		      .color(Color.GRAY)
		      .sizeDp(18))
		.backgroundColor(Color.WHITE)
		.build());
	adapter.add(
		new MaterialSimpleListItem.Builder(context)
		.content(context.getResources().getString(R.string.pro_feature_2))
		.icon(new IconicsDrawable(context)
		      .icon(GoogleMaterial.Icon.gmd_list)
		      .color(Color.GRAY)
		      .sizeDp(18))
		.backgroundColor(Color.WHITE)
		.build());
	adapter.add(
		new MaterialSimpleListItem.Builder(context)
		.content(context.getResources().getString(R.string.pro_feature_3))
		.icon(new IconicsDrawable(context)
		      .icon(GoogleMaterial.Icon.gmd_phonelink_erase)
		      .color(Color.GRAY)
		      .sizeDp(18))
		.backgroundColor(Color.WHITE)
		.build());
	adapter.add(
		new MaterialSimpleListItem.Builder(context)
		.content(context.getResources().getString(R.string.pro_feature_4))
		.icon(new IconicsDrawable(context)
		      .icon(GoogleMaterial.Icon.gmd_delete)
		      .color(Color.GRAY)
		      .sizeDp(18))
		.backgroundColor(Color.WHITE)
		.build());

	MaterialDialog.Builder materialBuilder =
		new MaterialDialog.Builder(context)
		.title(context.getResources().getString(R.string.action_buy) +
		       " (" +
		       context.getResources().getString(
			       R.string.action_buy_description) +
		       ")")
		.icon(ContextCompat.getDrawable(context, R.mipmap.ic_launcher_pro))
		.adapter(adapter,
		         new MaterialDialog.ListCallback() {
			@Override
			public void onSelection(
				final MaterialDialog materialDialog, final View view,
				final int i, final CharSequence charSequence) {
			}
		})
		.positiveText(
			context.getResources().getString(R.string.action_buy) +
			" ($1.43)")
		.negativeText(
			context.getResources().getString(R.string.button_later))
		.onPositive(new MaterialDialog.SingleButtonCallback() {
			@Override
			public void onClick(final @NonNull MaterialDialog dialog,
			                    final @NonNull DialogAction which) {
			        UtilsApp.goToGooglePlay(context,
			                                MLManagerApplication.getProPackage());
			}
		});

	return materialBuilder.show();
}
}
