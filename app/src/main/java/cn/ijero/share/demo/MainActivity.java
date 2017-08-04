package cn.ijero.share.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.jetbrains.anko.ToastsKt;

import cn.ijero.share.JeroShare;
import cn.ijero.share.bean.ShareError;
import cn.ijero.share.bean.ShareItem;
import cn.ijero.share.callback.OnShareCallbackListener;
import cn.ijero.share.callback.ShareResultCodeValue;
import cn.ijero.share.callback.ShareTypedValue;

public class MainActivity extends AppCompatActivity implements OnShareCallbackListener {

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
        jeroShare.share(ShareTypedValue.TYPE_WEI_BO, shareItem);
    }

    public void wechat_circle(View view) {
        jeroShare.share(ShareTypedValue.TYPE_WECHAT_CIRCLE, shareItem);
    }

    public void wechat(View view) {
        jeroShare.share(ShareTypedValue.TYPE_WECHAT, shareItem);
    }

    public void qzone(View view) {
        jeroShare.share(ShareTypedValue.TYPE_QQ_ZONE, shareItem);
    }

    public void qq(View view) {
        jeroShare.share(ShareTypedValue.TYPE_QQ, shareItem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        jeroShare.handleResult(requestCode, resultCode, data, this);
    }

    @Override
    public void onCallback(long type, long resultCode, ShareError shareError) {
        if (type == ShareTypedValue.TYPE_QQ) {
            handleQQResult(resultCode, shareError);
        } else if (type == ShareTypedValue.TYPE_QQ_ZONE) {
            handleQZoneResult(resultCode, shareError);
        } else if (type == ShareTypedValue.TYPE_WEI_BO) {
            handleWeiBo(resultCode, shareError);
        } else if (type == ShareTypedValue.TYPE_SMS) {
            handleSms(resultCode, shareError);
        }
    }

    private void handleSms(long resultCode, ShareError shareError) {
        if (resultCode == ShareResultCodeValue.RESULT_CODE_CANCEL) {
            ToastsKt.toast(this, "短信分享取消");
        } else if (resultCode == ShareResultCodeValue.RESULT_CODE_FAILED) {
            ToastsKt.toast(this, "短信分享出错！\n" + shareError);
        } else {
            ToastsKt.toast(this, "短信分享成功");
        }
    }

    private void handleWeiBo(long resultCode, ShareError shareError) {
        if (resultCode == ShareResultCodeValue.RESULT_CODE_CANCEL) {
            ToastsKt.toast(this, "微博分享取消");
        } else if (resultCode == ShareResultCodeValue.RESULT_CODE_FAILED) {
            ToastsKt.toast(this, "微博分享出错！\n" + shareError);
        } else {
            ToastsKt.toast(this, "微博分享成功");
        }
    }

    private void handleQQResult(long resultCode, ShareError shareError) {
        if (resultCode == ShareResultCodeValue.RESULT_CODE_CANCEL) {
            ToastsKt.toast(this, "qq分享取消");
        } else if (resultCode == ShareResultCodeValue.RESULT_CODE_FAILED) {
            ToastsKt.toast(this, "qq分享出错！\n" + shareError);
        } else {
            ToastsKt.toast(this, "qq分享成功");
        }
    }

    private void handleQZoneResult(long resultCode, ShareError shareError) {
        if (resultCode == ShareResultCodeValue.RESULT_CODE_CANCEL) {
            ToastsKt.toast(this, "qq空间分享取消");
        } else if (resultCode == ShareResultCodeValue.RESULT_CODE_FAILED) {
            ToastsKt.toast(this, "qq空间分享出错！\n" + shareError);
        } else {
            ToastsKt.toast(this, "qq空间分享成功");
        }
    }


}
