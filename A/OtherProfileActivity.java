package hillfly.wifichat.activity;

import hillfly.wifichat.R;
import hillfly.wifichat.common.BaseActivity;
import hillfly.wifichat.model.Users;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OtherProfileActivity extends BaseActivity implements OnClickListener {

    // private LinearLayout mLayoutChat;// 对话

    private LinearLayout mLayoutGender;// 性别根布局
    private ImageView mIvGender;// 性别
    private TextView mHtvAge;// 年龄
    private TextView mHtvConstellation;// 星座
    private TextView mHtvTime;// 登陆时间
    private TextView mHtvIPaddress; // IP地址
    private TextView mHtvDevice; // 设备品牌型号

    private Users mPeople;// 用户实体

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherprofile);
        initViews();
        initEvents();
        init();
    }

    @Override
    protected void initViews() {
        // mLayoutChat = (LinearLayout) findViewById(R.id.otherprofile_bottom_layout_chat);
        mLayoutGender = (LinearLayout) findViewById(R.id.otherprofile_layout_gender);
        mIvGender = (ImageView) findViewById(R.id.otherprofile_iv_gender);
        mHtvAge = (TextView) findViewById(R.id.otherprofile_htv_age);
        mHtvConstellation = (TextView) findViewById(R.id.otherprofile_htv_constellation);
        mHtvTime = (TextView) findViewById(R.id.otherprofile_htv_time);
        mHtvIPaddress = (TextView) findViewById(R.id.otherprofile_htv_ipaddress);
        mHtvDevice = (TextView) findViewById(R.id.otherprofile_htv_device);

    }

    @Override
    protected void initEvents() {
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        // mLayoutChat.setOnClickListener(this);
    }

    private void init() {
        getProfile();
    }

    // actionBar的监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(OtherProfileActivity.this, ChatActivity.class);
        intent.putExtra(Users.ENTITY_PEOPLE, mPeople);
        startActivity(intent);
    }

    private void getProfile() {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showLoadingDialog(getString(R.string.dialog_loading));
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                Intent intent = getIntent();
                mPeople = intent.getParcelableExtra(Users.ENTITY_PEOPLE);
                if (mPeople == null) {
                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                dismissLoadingDialog();
                if (!result) {
                    showShortToast(R.string.dialog_loading_failue);
                }
                else {
                    initProfile();
                }
            }

        });
    }

    private void initProfile() {
        setTitle(mPeople.getNickname());
        mLayoutGender.setBackgroundResource(mPeople.getGenderBgId());
        mIvGender.setImageResource(mPeople.getGenderId());
        mHtvAge.setText(mPeople.getAge() + "");
        mHtvConstellation.setText(mPeople.getConstellation());
        mHtvTime.setText(mPeople.getLogintime());
        mHtvIPaddress.setText(mPeople.getIpaddress());
        mHtvDevice.setText(mPeople.getDevice());
    }

}
