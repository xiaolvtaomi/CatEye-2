package com.cicinnus.cateye.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cicinnus.cateye.R;

/**
 * Created by lml on 18/5/31.
 */

public class TextEditLoginView extends LinearLayout {
    public TextEditLoginView(Context context) {
        super(context);
        this.mContext = context ;
        init(context);
    }

    public TextEditLoginView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context ;
        init(context);
        setParams(attrs);
    }

    public TextEditLoginView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context ;
        init(context);
        setParams(attrs);
    }
    
    private TextView tvLeft ;
    private TextView tvNesscery ;
    private EditText etRight ;
    private Context mContext ;
    
    private String strLeft ;
    private String strRightHint ;
    private String strRight ;
    private boolean nesscery = false ;
    
    private void init(Context context){
    
        inflate(context, R.layout.base_text_edit, this);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvNesscery = (TextView) findViewById(R.id.tv_nesscery);
        etRight = (EditText) findViewById(R.id.et_right);
    }
    
    private void setParams(AttributeSet attrs){
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.textedit);
        nesscery = a.getBoolean(R.styleable.textedit_nesscery, false);
        tvNesscery.setVisibility(nesscery?VISIBLE:INVISIBLE);
        strLeft = a.getString(R.styleable.textedit_left_text);
        tvLeft.setText(""+strLeft);
        strRight = a.getString(R.styleable.textedit_right_text);
        etRight.setText(TextUtils.isEmpty(strRight)?"":strRight);
        strRightHint = a.getString(R.styleable.textedit_right_hint);
        etRight.setHint(strRightHint);
//        String strInputType = a.getString(R.styleable.textedit_right_inputtype);
//        etRight.setInputType(TextUtils.isEmpty(strInputType)?InputType.TYPE_CLASS_TEXT:Integer.parseInt(strInputType));
    }
    
    public String getValue(){
        return etRight.getText().toString();
    }
    
}
