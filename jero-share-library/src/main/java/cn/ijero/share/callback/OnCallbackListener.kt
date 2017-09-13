package cn.ijero.share.callback

import cn.ijero.share.bean.Error
import cn.ijero.share.bean.QQLoginResult

/**
 * 分享或登录回调
 *
 * 参见 [OnCallbackListener.onCallback] 回调方法。
 *
 * Created by Jero on 2017/7/31.
 */
interface OnCallbackListener {
    /**
     * 1. type : 分享回调的类型，详见 [TypeValue.Companion.Type] 。（注意：微信分享的回调不在这里处理，请参考demo中的WXEntryActivity，进行您的应用的WXEntryActivity配置）
     *
     * 2. resultCode : 分享处理状态，参考 [ResultCode.Companion.ResultCode] 。
     *
     * 3. error : 分享出错时会把异常信息传递到此参数 ，但其他状态时此 [Error] 对象为null。
     *
     * 4. qqLoginResult : QQ登录的返回数据。其他操作时此为null
     *
     * Created by Jero on 2017/8/1.
     *
     */
    fun onCallback(type: Long, resultCode: Long, error: Error? = null, qqLoginResult: QQLoginResult? = null)
}