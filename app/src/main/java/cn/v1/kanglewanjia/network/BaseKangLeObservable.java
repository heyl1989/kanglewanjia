package cn.v1.kanglewanjia.network;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

import cn.v1.kanglewanjia.App;
import cn.v1.kanglewanjia.data.Common;
import cn.v1.kanglewanjia.data.SPUtil;
import cn.v1.kanglewanjia.model.BaseData;
import cn.v1.kanglewanjia.network.exception.ParseThrowable;
import cn.v1.kanglewanjia.network.exception.SecurityThrowable;
import cn.v1.kanglewanjia.network.exception.ServiceErrorThrowable;
import cn.v1.kanglewanjia.network.exception.ThirdLoginErrorThrowable;
import cn.v1.kanglewanjia.network.exception.UserNoExitErrorThrowable;
import cn.v1.kanglewanjia.ui.LoginActivity;
import retrofit.mime.TypedString;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lawrence on 15/2/6.
 */
public class BaseKangLeObservable<T> implements Observable.OnSubscribe<T> {

    Type mType;
    Observable<TypedString> observable;

    /**
     * @param observable
     * @param mType      if is class input Class e.g. LoginSave.Class else if is Collection input Type e.g. new TypeToken<List<KaijiangData>>(){}.getType()
     */
    public BaseKangLeObservable(Observable<TypedString> observable, Type mType) {
        this.observable = observable;
        this.mType = mType;
    }

    @Override
    public void call(final Subscriber<? super T> subscriber) {

        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<TypedString>() {
            @Override
            public void call(TypedString typedString) {
                String responseStr = "";
                try {
                    responseStr = new String(typedString.getBytes());
                    responseStr = responseStr.replace("\"\"", "null");

                    BaseData baseT = new Gson().fromJson(responseStr, BaseData.class);
                    if (baseT.getCode().equals("0001")) {
                        Intent intent = new Intent(App.getInstance(), LoginActivity.class);
                        intent.putExtra("from", "noMain");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        App.getInstance().startActivity(intent);
                        SPUtil.put(App.getInstance(), Common.ISLOGIN, false);
                    } else {
                        Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(String.class, new TypeAdapter<String>() {

                            @Override
                            public void write(JsonWriter out, String value) throws IOException {
                                if (value == null) {
                                    // out.nullValue();
                                    out.value(""); // 序列化时将 null 转为 ""
                                } else {
                                    out.value(value);
                                }
                            }

                            @Override
                            public String read(JsonReader in) throws IOException {
                                if (in.peek() == JsonToken.NULL) {
                                    in.nextNull();
                                    return "";
                                }
                                // return in.nextString();
                                String str = in.nextString();
                                if (str.equals("")) { // 反序列化时将 "" 转为 null
                                    return "";
                                } else {
                                    return str;
                                }
                            }

                        }).create();
                        T t = gson.fromJson(responseStr, mType);
                        subscriber.onNext(t);
                    }
//                    if (baseT.getCode().equals("0000")) {
//                        Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(String.class, new TypeAdapter<String>() {
//
//                            @Override
//                            public void write(JsonWriter out, String value) throws IOException {
//                                if (value == null) {
//                                    // out.nullValue();
//                                    out.value(""); // 序列化时将 null 转为 ""
//                                } else {
//                                    out.value(value);
//                                }
//                            }
//
//                            @Override
//                            public String read(JsonReader in) throws IOException {
//                                if (in.peek() == JsonToken.NULL) {
//                                    in.nextNull();
//                                    return "";
//                                }
//                                // return in.nextString();
//                                String str = in.nextString();
//                                if (str.equals("")) { // 反序列化时将 "" 转为 null
//                                    return "";
//                                } else {
//                                    return str;
//                                }
//                            }
//
//                        }).create();
//                        T t = gson.fromJson(responseStr, mType);
//                        subscriber.onNext(t);
//                    } else if (baseT.getCode().equals("0001")) {
////                        EventBus.getDefault().post(new JumpLoginEvent(baseT.getMessage()));
//                        Intent intent = new Intent(App.getInstance(), LoginActivity.class);
//                        intent.putExtra("from", "noMain");
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                        App.getInstance().startActivity(intent);
//                        SPUtil.put(App.getInstance(), Common.ISLOGIN, false);
//                    } else if (baseT.getCode().equals("0100")) {
//                        subscriber.onError(new SecurityThrowable(baseT.getMessage()));
//                    } else if (baseT.getCode().equals("0205")) {
//                        subscriber.onError(new ThirdLoginErrorThrowable(responseStr));
//                    } else if (baseT.getCode().equals("0201")) {
//                        subscriber.onError(new UserNoExitErrorThrowable(responseStr));
//                    } else {
//                        subscriber.onError(new ServiceErrorThrowable(baseT.getMessage(), baseT.getCode()));
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(new ParseThrowable(responseStr));
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                subscriber.onError(throwable);
            }
        });
    }
}
