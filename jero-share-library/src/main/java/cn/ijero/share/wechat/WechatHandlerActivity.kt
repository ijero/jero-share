package cn.ijero.share.wechat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import cn.ijero.share.R
import cn.ijero.share.callback.TYPE_WECHAT
import cn.ijero.share.common.Config
import cn.ijero.share.common.Key
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import java.io.IOException
import java.util.*

/**
 * Created by Jero on 2017/8/4.
 */
abstract class WechatHandlerActivity : Activity(), IWXAPIEventHandler {

    abstract fun onSuccess(type: Long)

    abstract fun onFailed(type: Long, errorCode: Int, errorStr: String?)

    abstract fun onCancel(type: Long)

    override fun onResp(resp: BaseResp) {
        val type = resp.transaction.toLong()
        val errorCode = resp.errCode
        when (errorCode) {
            BaseResp.ErrCode.ERR_OK -> {
                onSuccess(type)
            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {
                onCancel(type)
            }
            else -> {
                onFailed(type, errorCode, resp.errStr)
            }
        }
        finish()
    }

    override fun onReq(req: BaseReq?) {

    }

    private var iwxapi: IWXAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.NoAnimationStyle)

        var wechatAppid: String? = null
        try {
            val prop = Properties()
            prop.load(assets.open(Config.PROP_FILENAME))
            wechatAppid = prop.getProperty(Key.WECHAT_APPID)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (TextUtils.isEmpty(wechatAppid)) {
            Log.e("WechatHandlerActivity", "${Config.PROP_FILENAME} 文件中未找到正确的 ${Key.WECHAT_APPID}")
            return
        }

        iwxapi = WXAPIFactory.createWXAPI(this, wechatAppid)
        iwxapi!!.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
        iwxapi?.handleIntent(intent, this)
    }


}