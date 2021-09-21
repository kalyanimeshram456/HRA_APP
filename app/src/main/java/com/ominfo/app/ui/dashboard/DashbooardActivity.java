package com.ominfo.app.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.ominfo.app.R;
import com.ominfo.app.basecontrol.BaseActivity;
import com.ominfo.app.ui.kata_chithi.KataChithiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashbooardActivity extends BaseActivity {

    @BindView(R.id.cardKataChithi)
    CardView mCardKataChithi;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbooard);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        ButterKnife.bind(this);

    }

    //perform click actions
    @OnClick({R.id.cardKataChithi})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.cardKataChithi:
                launchScreen(mContext, KataChithiActivity.class);
                break;

        }
    }
}