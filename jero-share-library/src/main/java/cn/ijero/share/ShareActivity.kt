package cn.ijero.share

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import cn.ijero.share.bean.ShareError
import cn.ijero.share.bean.ShareItem
import cn.ijero.share.callback.*
import cn.ijero.share.common.Config
import cn.ijero.share.common.Key
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.api.WebpageObject
import com.sina.weibo.sdk.api.WeiboMultiMessage
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.share.WbShareCallback
import com.sina.weibo.sdk.share.WbShareHandler
import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzoneShare
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.net.URL
import java.util.*
import kotlin.concurrent.thread


class ShareActivity : Activity(), IUiListener, AnkoLogger, WbShareCallback {


    companion object {
        const val EXTRA_WECHAT_RESULT = "cn.ijero.share.ShareActivity.Companion,EXTRA_WECHAT_RESULT"
    }

    override fun onWbShareFail() {
        setError(ShareError(ShareError.ERROR_WEIBO_ERROR, getString(R.string.str_weibo_share_failed), getString(R.string.str_weibo_share_failed)))
    }

    override fun onWbShareCancel() {
        setCancel()
    }

    override fun onWbShareSuccess() {
        setSuccess()
    }

    private var curType: Long = TYPE_NONE
    private var mTencent: Tencent? = null
    private var iwxapi: IWXAPI? = null
    private var wbShareHandler: WbShareHandler? = null
    override fun onComplete(p0: Any?) {
        setSuccess()
    }

    private fun setSuccess() {
        val intent = buildIntent()
        intent.putExtra(JeroShare.EXTRA_SHARE_TYPE, curType)
        setResult(RESULT_CODE_SUCCESS.toInt(), intent)
        finish()
    }

    override fun onCancel() {
        setCancel()
    }

    private fun setCancel() {
        val intent = buildIntent()
        intent.putExtra(JeroShare.EXTRA_SHARE_TYPE, curType)
        setResult(RESULT_CODE_CANCEL.toInt(), intent)
        finish()
    }

    override fun onError(p0: UiError) {
        val error = ShareError(p0.errorCode, p0.errorMessage, p0.errorDetail)
        setError(error)
    }

    private fun setError(error: ShareError) {
        val intent = buildIntent()
        intent.putExtra(JeroShare.EXTRA_SHARE_ERROR, error)
        intent.putExtra(JeroShare.EXTRA_SHARE_TYPE, curType)
        setResult(RESULT_CODE_FAILED.toInt(), intent)
        finish()
    }

    private fun buildIntent(): Intent {
        val intent = Intent()
        intent.putExtra(JeroShare.EXTRA_SHARE_TYPE, curType)
        return intent
    }

    private fun loadConfig() {
        val prop = Properties()
        try {
            prop.load(assets.open(Config.PROP_FILENAME))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val qqAppid = initTencentApi(prop)
        val wxAppid = initWXApi(prop)
        val weiboAppKey = initWeibo(prop)

        error {
            "qqAppid: $qqAppid , wxAppid : $wxAppid , weiboAppKey: $weiboAppKey"
        }

    }

    private fun initWeibo(prop: Properties): String? {
        val weiboAppKey = prop.getProperty(Key.WEIBO_APPKEY)
        if (!weiboAppKey.isNullOrEmpty()) {
            WbSdk.install(this, AuthInfo(this, weiboAppKey, Config.WEIBO_CALLBACK, null))
            wbShareHandler = WbShareHandler(this)
            wbShareHandler!!.registerApp()
        }
        return weiboAppKey
    }

    private fun initTencentApi(prop: Properties): String? {
        val qqAppid = prop.getProperty(Key.QQ_APPID)
        if (!qqAppid.isNullOrEmpty()) {
            mTencent = Tencent.createInstance(qqAppid, this)
        }
        return qqAppid
    }

    private fun initWXApi(prop: Properties): String? {
        val wechatAppId = prop.getProperty(Key.WECHAT_APPID)
        if (!wechatAppId.isNullOrEmpty()) {
            iwxapi = WXAPIFactory.createWXAPI(this, wechatAppId)
            iwxapi!!.registerApp(wechatAppId)
        }
        return wechatAppId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadConfig()
        val type = intent.getLongExtra(JeroShare.EXTRA_SHARE_TYPE, TYPE_NONE)
        val item = intent.getSerializableExtra(JeroShare.EXTRA_SHARE_ITEM) as ShareItem

        when (type) {
            TYPE_QQ -> {
                shareToQQ(item)
            }
            TYPE_QQ_ZONE -> {
                shareToQQZone(item)
            }
            TYPE_WEI_BO -> {
                shareToWeiBo(item)
            }
            TYPE_WECHAT -> {
                shareToWeChat(item)
            }
            TYPE_WECHAT_CIRCLE -> {
                shareToWeChat(item, false)
            }
        }
    }

    private fun shareToQQ(item: ShareItem) {
        curType = TYPE_QQ
        if (mTencent == null) {
            error { getString(R.string.str_qq_appid_notnull) }
            val error = ShareError(ShareError.ERROR_INVALID_APPID, ShareError.MSG_INVALID_APPID, getString(R.string.str_qq_appid_notnull))
            setError(error)
            return
        }
        val params = Bundle()
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
        params.putString(QQShare.SHARE_TO_QQ_TITLE, item.title)
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, item.targetUrl)
        if (item.summary != null)
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, item.summary)
        if (item.imageUrl != null)
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, item.imageUrl)
        mTencent!!.shareToQQ(this, params, null)
    }

    fun shareToQQZone(item: ShareItem) {
        curType = TYPE_QQ_ZONE
        if (mTencent == null) {
            error { getString(R.string.str_qq_appid_notnull) }
            val error = ShareError(ShareError.ERROR_INVALID_APPID, ShareError.MSG_INVALID_APPID, getString(R.string.str_qq_appid_notnull))
            setError(error)
            return
        }
        val params = Bundle()
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT)
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, item.title)
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, item.targetUrl)
        if (item.summary != null)
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, item.summary)
        if (item.imageUrl != null)
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, arrayListOf(item.imageUrl))
        mTencent!!.shareToQzone(this, params, null)
    }


    private fun shareToWeiBo(item: ShareItem) {
        curType = TYPE_WEI_BO
        if (wbShareHandler == null) {
            error { "微博appkey不能为空" }
            val error = ShareError(ShareError.ERROR_INVALID_APPID, ShareError.MSG_INVALID_APPID, "微博appkey不能为空")
            setError(error)
            return
        }
        thread(isDaemon = true) {
            val message = WeiboMultiMessage()
            val webpageObject = WebpageObject()

            if (item.imageUrl != null) {
                try {
                    val bytes = URL(item.imageUrl).readBytes()
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    if (bitmap == null) {
                        error { getString(R.string.str_load_image_error) }
                        val error = ShareError(ShareError.ERROR_INVALID_IMAGE_URL, ShareError.MSG_INVALID_IMAGE_URL, getString(R.string.str_load_image_error))
                        setError(error)
                        return@thread
                    }
                    webpageObject.setThumbImage(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            webpageObject.title = item.title
            webpageObject.description = item.summary
            webpageObject.actionUrl = item.targetUrl
            message.mediaObject = webpageObject
            wbShareHandler!!.shareMessage(message, false)
        }
    }

    private fun shareToWeChat(item: ShareItem, isToChat: Boolean = true) {
        curType = if (isToChat) TYPE_WECHAT else TYPE_WECHAT_CIRCLE
        when (iwxapi) {
            null -> {
                error { getString(R.string.str_wechat_appid_notnnull) }
                val error = ShareError(ShareError.ERROR_INVALID_APPID,
                        ShareError.MSG_INVALID_APPID,
                        getString(R.string.str_wechat_appid_notnnull))
                setError(error)
                return
            }
            else -> thread(isDaemon = true) {
                shareToWechatWithBitmap(item, isToChat)
            }
        }

    }

    private fun shareToWechatWithBitmap(item: ShareItem, isToChat: Boolean) {
        val webpageObject = WXWebpageObject(item.targetUrl)
        val msg = WXMediaMessage(webpageObject)
        if (item.imageUrl != null) {
            try {
                val bytes = URL(item.imageUrl).readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                if (bitmap == null) {
                    error { getString(R.string.str_load_image_error) }
                    val error = ShareError(ShareError.ERROR_INVALID_IMAGE_URL,
                            ShareError.MSG_INVALID_IMAGE_URL,
                            getString(R.string.str_load_image_error))
                    setError(error)
                    return
                }
                msg.setThumbImage(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        msg.title = item.title
        msg.description = item.summary
        val req = SendMessageToWX.Req()
        if (isToChat)
            req.transaction = "$TYPE_WECHAT"
        else
            req.transaction = "$TYPE_WECHAT_CIRCLE"
        req.message = msg
        req.scene = if (isToChat) SendMessageToWX.Req.WXSceneSession else SendMessageToWX.Req.WXSceneTimeline
        iwxapi!!.sendReq(req)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 处理QQ分享回调
        Tencent.onActivityResultData(requestCode, resultCode, data, this)
    }

    override fun onNewIntent(intent: Intent?) {
        // 微博分享回调
        wbShareHandler?.doResultIntent(intent, this)
    }

}
