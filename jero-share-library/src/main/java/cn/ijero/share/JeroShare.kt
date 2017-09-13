package cn.ijero.share

import android.app.Activity
import android.content.Intent
import cn.ijero.share.bean.Error
import cn.ijero.share.bean.QQLoginResult
import cn.ijero.share.bean.ShareItem
import cn.ijero.share.callback.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error


/**
 *
 * Created by Jero on 2017/7/31.
 */
class JeroShare(private val context: Activity) : AnkoLogger {
    companion object : AnkoLogger {
        const val REQUEST_CODE_SHARE = 1000
        const val EXTRA_SHARE_TYPE = "cn.ijero.sharesdk.JeroShare.Companion.EXTRA_SHARE_TYPE"
        const val EXTRA_SHARE_ITEM = "cn.ijero.sharesdk.JeroShare.Companion.EXTRA_SHARE_ITEM"
        const val EXTRA_SHARE_ERROR = "cn.ijero.sharesdk.JeroShare.Companion.EXTRA_SHARE_ERROR"
        const val EXTRA_LOGINED_QQ = "cn.ijero.sharesdk.JeroShare.Companion.EXTRA_LOGINED_QQ"
        private var jeroShare: JeroShare? = null

        @Synchronized
        fun getInstance(activity: Activity): JeroShare {
            if (jeroShare == null) {
                synchronized(JeroShare::class.java) {
                    if (jeroShare == null) {
                        jeroShare = JeroShare(activity)
                    }
                }
            }
            return jeroShare!!
        }

//        @Synchronized
//        fun getInstance(fragment: Fragment) = getInstance(fragment.activity)

    }

    // 根据分享的回调结果处理出来并返回给回调接口
    fun handleResult(requestCode: Int, resultCode: Long, data: Intent?, callbackListener: OnCallbackListener) {
        if (data == null) {
            return
        }
        when (requestCode) {
            REQUEST_CODE_SHARE -> {
                handleResult(resultCode, data, callbackListener)
            }
            else -> {
                // nothing
            }
        }

        error { "$requestCode , $resultCode , $data" }
    }

    private fun handleResult(resultCode: Long, data: Intent, listener: OnCallbackListener) {
        val type = data.getLongExtra(EXTRA_SHARE_TYPE, TypeValue.TYPE_NONE)
        when (resultCode) {
            ResultCode.RESULT_CODE_CANCEL -> {
                // cancel
                listener.onCallback(type, resultCode)
            }
            ResultCode.RESULT_CODE_FAILED -> {
                // failed
                val error = data.getSerializableExtra(EXTRA_SHARE_ERROR) as Error
                listener.onCallback(type, resultCode, error)
            }
            else -> {
                // success
                val result = data.getSerializableExtra(JeroShare.EXTRA_LOGINED_QQ) as QQLoginResult
                listener.onCallback(type, resultCode, qqLoginResult = result)
            }
        }
    }

    /**
     *
     * 参考 [TypeValue.Companion.Type]
     *
     * Created by Jero on 2017/8/24.
     *
     */
    fun share(typeValue: Long, shareItem: ShareItem) {
        val intent = Intent(context, ProxyActivity::class.java)
        intent.putExtra(EXTRA_SHARE_TYPE, typeValue)
        intent.putExtra(EXTRA_SHARE_ITEM, shareItem)
        context.startActivityForResult(intent, REQUEST_CODE_SHARE)
    }

    /**
     * 分享到短信
     *
     * Created by Jero on 2017/9/13.
     *
     */
    fun shareToSms(content: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(Intent.EXTRA_TEXT, content)
        context.startActivity(Intent.createChooser(intent, "分享"))
    }

    /**
     * 登录方法，通过指定type进行不同类型登录，取值：[TypeValue.TYPE_LOGIN_FOR_QQ]和[TypeValue.TYPE_LOGIN_FOR_WE_CHAT]
     *
     * Created by Jero on 2017/9/13.
     *
     */
    fun login(typeValue: Long) {
        val intent = Intent(context, ProxyActivity::class.java)
        intent.putExtra(EXTRA_SHARE_TYPE, typeValue)
        context.startActivityForResult(intent, REQUEST_CODE_SHARE)
    }

}