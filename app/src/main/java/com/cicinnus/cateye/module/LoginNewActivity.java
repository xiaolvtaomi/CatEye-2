package com.cicinnus.cateye.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.cicinnus.cateye.R;
import com.cicinnus.cateye.base.BaseActivity;
import com.cicinnus.cateye.tools.JumpActUtil;
import com.cicinnus.cateye.tools.PreferencesUtil;
import com.cicinnus.cateye.tools.ToastUtil;

/**
 * Created by lml on 18/6/17.
 */

public class LoginNewActivity extends FragmentActivity {

  private EditText etAccount , etPw ;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_new);
    etAccount = (EditText) findViewById(R.id.email);
    etPw = (EditText) findViewById(R.id.password);

    findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(TextUtils.isEmpty(etAccount.getText().toString())){
          ToastUtil.showToast("请输入帐号");
        }else if(TextUtils.isEmpty(etPw.getText().toString())){
          ToastUtil.showToast("请输入密码");
        }else{
          if((TextUtils.equals("410325199910052020",etAccount.getText().toString())
              && TextUtils.equals("123456",etPw.getText().toString())) ||
              (TextUtils.equals("test",etAccount.getText().toString())
                  && TextUtils.equals("123456",etPw.getText().toString()))
              ){
            PreferencesUtil.setYouke(false);
            JumpActUtil.go2MainActivity(LoginNewActivity.this);
            finish();
          }else{
            ToastUtil.showToast("测试410325199910052020，密码123456\n测试test，密码123456");
          }
        }
      }
    });
    findViewById(R.id.tv_regist).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        JumpActUtil.go2RegistActivity(LoginNewActivity.this);
      }
    });
    findViewById(R.id.tv_youke).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        PreferencesUtil.setYouke(true);
        JumpActUtil.go2MainActivity(LoginNewActivity.this);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(RESULT_OK == resultCode && requestCode == 123){
      etAccount.setText(data.getStringExtra("account"));
      etPw.setText(data.getStringExtra("pw"));
    }
  }
}
