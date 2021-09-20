package com.ominfo.app.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatEditText;

import com.ominfo.app.R;
import com.ominfo.app.basecontrol.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

  /*  @BindView(R.id.layLoginButton)
    RelativeLayout mLayLoginButton;
*/
/*
    @BindView(R.id.edtName)
    AppCompatEditText mEditTextName;

    @BindView(R.id.edtPassword)
    AppCompatEditText mEditTextPassword;
*/

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getDeps().inject(this);
        ButterKnife.bind(this);
        mContext = this;
    }

   /* *//*perform click actions*//*
    @OnClick({R.id.layLoginButton})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layLoginButton:
                if(isDetailsValid()) {
                    //launchScreen(mContext, UserListActivity.class);
                }
                break;

        }
    }*/

    /*check validations on field*/
    private boolean isDetailsValid() {
       /* if (TextUtils.isEmpty(mEditTextName.getText().toString().trim())) {
            //mEditTextName.setError(getString(R.string.val_msg_please_enter_email));
            return false;
        } else if (TextUtils.isEmpty(mEditTextPassword.getText().toString().trim())) {
           // mEditTextName.setError(getString(R.string.val_msg_please_enter_password));
            return false;
        } else if (mEditTextPassword.getEditableText().toString().trim().length() < 6) { //6
            //mEditTextName.setError(getString(R.string.val_msg_minimum_password));
            return false;
        }*/
        return true;
    }

}