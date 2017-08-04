package cn.ijero.share.callback

import cn.ijero.share.bean.ShareError

/**
 *
 * Created by Jero on 2017/7/31.
 */
interface OnShareCallbackListener {
    /**
     * 1. 分享回调的 [type] 类型，详见 [ShareType] 。（注意：微信分享的回调不在这里处理，请参考demo中的WXEntryActivity，进行您的应用的WXEntryActivity配置）
     *
     * 2. 分享出错时会把异常信息传递给 [shareError] ，但分享成功时此 [ShareError] 对象为null。
     *
     * 3. 分享处理状态 [resultCode] ，参考 [ShareResultCode] 。
     *
     * Created by Jero on 2017/8/1.
     *
     */

    fun onCallback(type: Long, resultCode: Long, shareError: ShareError? = null)
}