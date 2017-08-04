package cn.ijero.share.demo.wxapi;

import org.jetbrains.anko.ToastsKt;

import cn.ijero.share.callback.ShareTypedValue;
import cn.ijero.share.wechat.WechatHandlerActivity;


/**
 * Created by Jero on 2017/8/2.
 */

public class WXEntryActivity extends WechatHandlerActivity {

    @Override
    public void onSuccess(long type) {
        if (type == ShareTypedValue.TYPE_WECHAT) {
            ToastsKt.toast(this, "微信分享成功");
        } else {
            ToastsKt.toast(this, "微信朋友圈分享成功");
        }
        finish();
    }

    @Override
    public void onFailed(long type, int errorCode, String errorStr) {
        if (type == ShareTypedValue.TYPE_WECHAT) {
            ToastsKt.toast(this, "微信分享失败");
        } else {
            ToastsKt.toast(this, "微信朋友圈分享失败");
        }
        finish();
    }

    @Override
    public void onCancel(long type) {
        if (type == ShareTypedValue.TYPE_WECHAT) {
            ToastsKt.toast(this, "微信分享取消");
        } else {
            ToastsKt.toast(this, "微信朋友圈分享取消");
        }
        finish();
    }
}
