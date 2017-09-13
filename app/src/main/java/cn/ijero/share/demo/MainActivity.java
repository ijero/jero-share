package cn.ijero.share.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.jetbrains.anko.ToastsKt;

import cn.ijero.share.JeroShare;
import cn.ijero.share.bean.Error;
import cn.ijero.share.bean.QQLoginResult;
import cn.ijero.share.bean.ShareItem;
import cn.ijero.share.callback.OnCallbackListener;
import cn.ijero.share.callback.ResultCode;
import cn.ijero.share.callback.TypeValue;

public class MainActivity extends AppCompatActivity implements OnCallbackListener {

    private String link = "https://github.com/ijero/jero-share";
    private String image = "https://raw.githubusercontent.com/ijero/jero-share/develop/imgs/logo.png";
    private String title = "JeroShare";
    private String summary = "《JeroShare》致力于为Android开发者实现简便的社会化分享";
    private JeroShare jeroShare;
    private ShareItem shareItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jeroShare = JeroShare.Companion.getInstance(this);
        shareItem = new ShareItem(title, summary, link, image);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public void sms(View view) {
        jeroShare.shareToSms("《JeroShare》致力于为Android开发者实现简便的社会化分享，详情请点击\n" + link);
    }

    public void weibo(View view) {
        jeroShare.share(TypeValue.TYPE_SHARE_WEI_BO, shareItem);
    }

    public void wechat_circle(View view) {
        jeroShare.share(TypeValue.TYPE_SHARE_WECHAT_CIRCLE, shareItem);
    }

    public void wechat(View view) {
        jeroShare.share(TypeValue.TYPE_SHARE_WECHAT, shareItem);
    }

    public void qzone(View view) {
        jeroShare.share(TypeValue.TYPE_SHARE_QQ_ZONE, shareItem);
    }

    public void qq(View view) {
        jeroShare.share(TypeValue.TYPE_SHARE_QQ, shareItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        jeroShare.handleResult(requestCode, resultCode, data, this);
    }


    @Override
    public void onCallback(long type, long resultCode, Error error, QQLoginResult qqLoginResult) {
        if (type == TypeValue.TYPE_SHARE_QQ) {
            handleQQShareResult(resultCode, error);
        } else if (type == TypeValue.TYPE_LOGIN_FOR_QQ) {
            handleQQLoginResult(resultCode, error, qqLoginResult);
        } else if (type == TypeValue.TYPE_SHARE_QQ_ZONE) {
            handleQQZoneShareResult(resultCode, error);
        }

    }

    private void handleQQZoneShareResult(long resultCode, Error error) {
        if (resultCode == ResultCode.RESULT_CODE_CANCEL) {
            ToastsKt.toast(this, "QQ空间分享取消");
        } else if (resultCode == ResultCode.RESULT_CODE_FAILED) {
            ToastsKt.longToast(this, "QQ空间分享出错，出错信息：" + error.toString());
        } else {
            ToastsKt.toast(this, "QQ空间分享成功");
        }
    }

    private void handleQQLoginResult(long resultCode, Error error, QQLoginResult qqLoginResult) {
        if (resultCode == ResultCode.RESULT_CODE_CANCEL) {
            ToastsKt.toast(this, "QQ登录取消");
        } else if (resultCode == ResultCode.RESULT_CODE_FAILED) {
            ToastsKt.longToast(this, "QQ登录出错，出错信息：" + error.toString());
        } else {
            ToastsKt.toast(this, "QQ登录成功" + qqLoginResult.toString());
        }
    }

    private void handleQQShareResult(long resultCode, Error error) {
        if (resultCode == ResultCode.RESULT_CODE_CANCEL) {
            ToastsKt.toast(this, "QQ分享取消");
        } else if (resultCode == ResultCode.RESULT_CODE_FAILED) {
            ToastsKt.longToast(this, "QQ分享出错，出错信息：" + error.toString());
        } else {
            ToastsKt.toast(this, "QQ分享成功");
        }
    }


    public void loginForQQ(View view) {
        jeroShare.login(TypeValue.TYPE_LOGIN_FOR_QQ);
    }

    public void loginForWechat(View view) {
        jeroShare.login(TypeValue.TYPE_LOGIN_FOR_WE_CHAT);

    }
}
