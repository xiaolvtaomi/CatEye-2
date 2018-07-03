package com.cicinnus.cateye.module;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import com.cicinnus.cateye.R;

/**
 * Created by lml on 18/6/13.
 */

public class RegistNewActivity extends FragmentActivity {

  private EditText et_account , et_pw ;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_regist_new);
  }

  public void clickLogin(View view){
    finish();
  }

  public void clickRegist(View view){
    Intent intent = new Intent();
    intent.putExtra("account", et_account.getText().toString());
    intent.putExtra("pw", et_pw.getText().toString());
    setResult(RESULT_OK, intent);
    finish();
  }


}
