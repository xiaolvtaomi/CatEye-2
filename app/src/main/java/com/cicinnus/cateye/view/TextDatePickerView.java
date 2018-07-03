package com.cicinnus.cateye.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cicinnus.cateye.R;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lml on 18/5/31.
 */

public class TextDatePickerView extends LinearLayout {
    public TextDatePickerView(Context context) {
        super(context);
        this.mContext = context ;
        init(context);
    }

    public TextDatePickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context ;
        init(context);
        setParams(attrs);
    }

    public TextDatePickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context ;
        init(context);
        setParams(attrs);
    }
    
    private TextView tvLeft ;
    private TextView tvNesscery ;
    private TextView tvRight;
    private Context mContext ;
    
    private String strLeft ;
    private String strRightHint ;
    private String strRight ;
    private boolean nesscery = false ;
    
    private void init(Context context){
    
        inflate(context, R.layout.base_text_text, this);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvNesscery = (TextView) findViewById(R.id.tv_nesscery);
        tvRight = (TextView) findViewById(R.id.et_right);
    }
    
    private void setParams(AttributeSet attrs){
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.textedit);
        nesscery = a.getBoolean(R.styleable.textedit_nesscery, false);
        tvNesscery.setVisibility(nesscery?VISIBLE:INVISIBLE);
        strLeft = a.getString(R.styleable.textedit_left_text);
        tvLeft.setText(""+strLeft);
        strRight = a.getString(R.styleable.textedit_right_text);
        tvRight.setText(TextUtils.isEmpty(strRight)?"":strRight);
        strRightHint = a.getString(R.styleable.textedit_right_hint);
        tvRight.setHint(strRightHint);
//        String strInputType = a.getString(R.styleable.textedit_right_inputtype);
//        tvRight.setInputType(TextUtils.isEmpty(strInputType)?InputType.TYPE_CLASS_TEXT:Integer.parseInt(strInputType));


        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickDialog dialog = new DatePickDialog(getContext());
                //设置上下年分限制
                dialog.setYearLimt(2);
                //设置标题
                dialog.setTitle("选择时间");
                //设置类型
                dialog.setType(DateType.TYPE_YMDH);
                //设置消息体的显示格式，日期格式
                dialog.setMessageFormat("yyyy-MM-dd HH:mm");
                //设置选择回调
                dialog.setOnChangeLisener(null);
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        tvRight.setText(sdf.format(date));
                    }
                });
                dialog.show();
            }
        });
    }
    
    public String getValue(){
        return tvRight.getText().toString();
    }
    
}
