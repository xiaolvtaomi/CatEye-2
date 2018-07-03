package com.cicinnus.cateye.base;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.cicinnus.cateye.R;


/**
 * Created by lml on 16/5/17.
 */
public class UniLoadingDialog extends Dialog {


    private TextView mTv;
    private String info;

    public UniLoadingDialog(Context context, String text) {
        super(context, R.style.Translucent_NoTitle);
        info = text;
        init();
    }

    private void init() {
        setContentView(R.layout.uni_loadingdialog);

        mTv = (TextView) findViewById(R.id.loadingdialog_htv_text);
        mTv.setText(info);
    }

    public void setText(String text) {
        info = text;
        mTv.setText(info);
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

}
