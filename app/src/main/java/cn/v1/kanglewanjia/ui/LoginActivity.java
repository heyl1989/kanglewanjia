package cn.v1.kanglewanjia.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.data.Common;
import cn.v1.kanglewanjia.data.SPUtil;
import cn.v1.kanglewanjia.model.AuthCodeData;
import cn.v1.kanglewanjia.model.BaseData;
import cn.v1.kanglewanjia.model.LoginData;
import rx.functions.Action1;


public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_phone_number)
    EditText etPhoneNumber;
    @Bind(R.id.et_verification_code)
    EditText etVerificationCode;
    @Bind(R.id.tv_send)
    TextView tvSend;
    @Bind(R.id.tv_login)
    TextView tvLogin;

    private String from;
    private boolean runningThree;
    private String codeId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        String phoneNumber = (String) SPUtil.get(context,Common.USER_PHONE_NUMBER,"");
        etPhoneNumber.setText(phoneNumber);
    }

    private void initData() {
        if(getIntent().hasExtra("from")){
            from = getIntent().getStringExtra("from");
        }

    }

    @OnClick({R.id.tv_send, R.id.tv_login})
    public void onClick(View view) {
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_send:
                if(phoneNumber.length() < 11){
                    showTost("请输入正确的手机号码");
                    return;
                }
                if(runningThree){}else{
                    getAuthCode(phoneNumber);
                    downTimer.start();
                }
                break;
            case R.id.tv_login:
                if(phoneNumber.length() < 11){
                    showTost("请输入正确的手机号码");
                    return;
                }
                String authCode = etVerificationCode.getText().toString().trim();
                if(TextUtils.isEmpty(authCode)){
                    showTost("验证码不能为空");
                    return;
                }
                login(phoneNumber,authCode);
                break;
        }
    }

    /**
     * 登录注册接口
     * @param phoneNumber
     * @param authCode
     */
    private void login(final String phoneNumber, String authCode) {
        showDialog();
        bindObservable(mAppClient.login(phoneNumber,codeId,authCode), new Action1<LoginData>() {
            @Override
            public void call(LoginData data) {
                if(TextUtils.equals("0000",data.getCode())){
                    SPUtil.put(context, Common.USER_TOKEN,data.getData().getToken());
                    SPUtil.put(context,Common.USER_PHONE_NUMBER,phoneNumber+"");
                    if(TextUtils.equals("noMain",from)){
                        finish();
                        login();
                    }else {
                        finish();
                        goNewActivity(MainActivity.class);
                        login();
                    }
                }else{
                    showTost(data.getMessage()+"");
                }
                closeDialog();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                closeDialog();
            }
        });
    }

    /**
     * 获取验证码
     * @param phoneNumber
     */
    private void getAuthCode(String phoneNumber) {
        bindObservable(mAppClient.getAuthCode(phoneNumber), new Action1<AuthCodeData>() {
            @Override
            public void call(AuthCodeData data) {
                if(TextUtils.equals("0000",data.getCode())){
                    codeId = data.getData().getCodeId();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            tvSend.setText((l / 1000) + "s");
        }

        @Override
        public void onFinish() {
            runningThree = false;
            tvSend.setText("重新发送");
        }
    };
}
