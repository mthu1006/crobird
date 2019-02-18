package com.croteam.crobird.uitls;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

/**
 * Created by kien on 29-Aug-16.
 */
public class AppDialogManager {
    private DialogAcceptClickListener mClick;
    private static String titles;
    private static String content;
    private static String button;

    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        AppDialogManager.content = content;
    }

    public static void onCreate(Context context) {
        content = "";
//        titles = context.getString(R.string.txt_thongbao1);
//        button = context.getString(R.string.txt_profile10);
    }

//    public static void onCreateDialogConfirm(Context context,String content, final DialogAcceptClickListener mclick) {
//        final Dialog dialog = new Dialog(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);//chạm ở ngoài
//        dialog.setContentView(view);
//
//        ImageView mClose = (ImageView) view.findViewById(R.id.button_close);
//        TextView mTvContent = (TextView) view.findViewById(R.id.textView_content);
//        TextView mTvTitles = (TextView) view.findViewById(R.id.textView_titles);
//        Button mBtAccept = (Button) view.findViewById(R.id.btn_accept);
//        Button mBtDenice = (Button) view.findViewById(R.id.btn_denice);
//        mTvContent.setText(content);
//        mClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        mBtAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mclick.onAcceptClick(view);
//                dialog.dismiss();
//            }
//        });
//        mBtDenice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }

//    public static MaterialDialog onCreateDialogLoading(Context context) {
//        MaterialDialog.Builder mBuilder = new MaterialDialog.Builder(context);
//        mBuilder.content(R.string.txt_loading).progress(true, 0).cancelable(false);
//        MaterialDialog mDialog = mBuilder.build();
//        return mDialog;
//    }

    public static Dialog createLoadingDialog(Context context){
        ProgressDialog dialog = new ProgressDialog(context); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static Dialog onCreateCustomDialog(final Context context, int layoutId, final DialogAcceptClickListener mclick){
        final Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);//chạm ở ngoài
        dialog.setContentView(view);
//        Button btnDenice = (Button) view.findViewById(R.id.btn_cancel);
//        if(btnDenice!=null) {
//            btnDenice.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//        }

//        Button btn = (Button) view.findViewById(R.id.btn_accept);
//        ImageView img_close = (ImageView) view.findViewById(R.id.img_close);
//        if(img_close!=null)
//        img_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mclick.onCloseClick(v);
//                dialog.dismiss();
//            }
//        });
//        if(btn!=null)
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mclick.onAcceptClick(v);
//            }
//        });
        return dialog;
    }
}
