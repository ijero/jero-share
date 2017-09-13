package cn.ijero.share.demo.wxapi;

import android.support.annotation.NonNull;

import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.jetbrains.anko.ToastsKt;
import org.jetbrains.annotations.NotNull;

import cn.ijero.share.callback.TypeValue;
import cn.ijero.share.wechat.WechatHandlerActivity;


/**
 * Created by Jero on 2017/8/2.
 */

public class WXEntryActivity extends WechatHandlerActivity {

    @Override
    public void onSuccess(long type, @NonNull BaseResp resp) {
        if (type == TypeValue.TYPE_SHARE_WECHAT) {
            ToastsKt.toast(this, "微信分享成功");
        }else if (type == TypeValue.TYPE_LOGIN_FOR_WE_CHAT){
            ToastsKt.toast(this, "微信登录成功");
        }else if (type == TypeValue.TYPE_SHARE_WECHAT_CIRCLE){
            ToastsKt.toast(this, "微信朋友圈分享成功");
        }
    }

    @Override
    public void onFailed(long type, int errorCode, String errorStr) {
        if (type == TypeValue.TYPE_SHARE_WECHAT) {
            ToastsKt.longToast(this, "微信分享出错 : " + errorCode + " , " + errorStr);
        }else if (type == TypeValue.TYPE_LOGIN_FOR_WE_CHAT){
            ToastsKt.longToast(this, "微信登录出错 : " + errorCode + " , " + errorStr);
        }else if (type == TypeValue.TYPE_SHARE_WECHAT_CIRCLE){
            ToastsKt.longToast(this, "微信朋友圈分享出错 : " + errorCode + " , " + errorStr);
        }
    }

    @Override
    public void onCancel(long type) {
        if (type == TypeValue.TYPE_SHARE_WECHAT) {
            ToastsKt.toast(this, "微信分享取消");
        }else if (type == TypeValue.TYPE_LOGIN_FOR_WE_CHAT){
            ToastsKt.toast(this, "微信登录取消");
        }else if (type == TypeValue.TYPE_SHARE_WECHAT_CIRCLE){
            ToastsKt.toast(this, "微信朋友圈分享取消");
        }
    }

    @NotNull
    @Override
    public String getLoggerTag() {
        return this.getClass().getCanonicalName();
    }
}
