package cn.v1.kanglewanjia.ui.address_manage;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.v1.kanglewanjia.R;
import cn.v1.kanglewanjia.model.AddressListData;
import cn.v1.kanglewanjia.model.BaseData;
import cn.v1.kanglewanjia.model.DepartmentData;
import cn.v1.kanglewanjia.ui.BaseActivity;
import rx.functions.Action1;


public class AddressEditActivity extends BaseActivity {

    @Bind(R.id.et_contacts)
    EditText etContacts;
    @Bind(R.id.et_contact_number)
    EditText etContactNumber;
    @Bind(R.id.et_detail_address)
    EditText etDetailAddress;
    @Bind(R.id.tv_save)
    TextView tvSave;

    private AddressListData.DataData addressData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        ButterKnife.bind(this);
        initData();
    }

    @OnClick(R.id.tv_save)
    public void onClick() {
        String realName = etContacts.getText().toString().trim();
        String phoneNumber = etContactNumber.getText().toString().trim();
        String detailAddress = etDetailAddress.getText().toString().trim();
        if(TextUtils.isEmpty(realName)){
            showTost("请填写联系人");
            return;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            showTost("请填写联系电话");
            return;
        }
        if(TextUtils.isEmpty(detailAddress)){
            showTost("请填写详细地址");
            return;
        }
        if(getIntent().hasExtra("editAddress")){
            updateAddress(realName,phoneNumber,detailAddress);
        }else{
            saveAddress(realName,phoneNumber,detailAddress);
        }
    }

    private void initData() {
        if(getIntent().hasExtra("editAddress")){
            addressData = (AddressListData.DataData) getIntent().getSerializableExtra("editAddress");
            etContacts.setText(addressData.getUserRealName()+"");
            etContactNumber.setText(addressData.getMobile()+"");
            etDetailAddress.setText(addressData.getDetailAddress()+"");
        }
    }


    /**
     * 保存地址
     * @param realName
     * @param phoneNumber
     * @param detailAddress
     */
    private void saveAddress(String realName,String phoneNumber,String detailAddress) {
        showDialog();
        bindObservable(mAppClient.saveConsigneeAddress(realName, phoneNumber, detailAddress), new Action1<BaseData>() {
            @Override
            public void call(BaseData data) {
                if(TextUtils.equals("0000",data.getCode())){
                    showTost("添加成功");
                    finish();
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
     * 修改地址
     * @param realName
     * @param phoneNumber
     * @param detailAddress
     */
    private void updateAddress(String realName,String phoneNumber,String detailAddress) {
        showDialog();
        bindObservable(mAppClient.updateConsigeeAddress(addressData.getCaId()+"",realName, phoneNumber, detailAddress), new Action1<BaseData>() {
            @Override
            public void call(BaseData data) {
                if(TextUtils.equals("0000",data.getCode())){
                    showTost("修改成功");
                    finish();
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
}
