package cn.ijero.share

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import cn.ijero.share.bean.ShareError
import cn.ijero.share.bean.ShareItem
import cn.ijero.share.callback.OnShareCallbackListener
import cn.ijero.share.callback.RESULT_CODE_CANCEL
import cn.ijero.share.callback.RESULT_CODE_FAILED
import cn.ijero.share.callback.TYPE_NONE
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
    fun handleResult(requestCode: Int, resultCode: Long, data: Intent?, callbackListener: OnShareCallbackListener) {
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

    private fun handleResult(resultCode: Long, data: Intent, listener: OnShareCallbackListener) {
        val type = data.getLongExtra(EXTRA_SHARE_TYPE, TYPE_NONE)
        when (resultCode) {
            RESULT_CODE_CANCEL -> {
                // cancel
                listener.onCallback(type, resultCode)
            }
            RESULT_CODE_FAILED -> {
                // failed
                val error = data.getSerializableExtra(EXTRA_SHARE_ERROR) as ShareError
                listener.onCallback(type, resultCode, error)
            }
            else -> {
                // success
                listener.onCallback(type, resultCode)
            }
        }
    }

    fun share(shareTypeValue: Long, shareItem: ShareItem) {
        val intent = Intent(context, ShareActivity::class.java)
        intent.putExtra(EXTRA_SHARE_TYPE, shareTypeValue)
        intent.putExtra(EXTRA_SHARE_ITEM, shareItem)
        context.startActivityForResult(intent, REQUEST_CODE_SHARE)
    }

    fun shareToSms(content: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(Intent.EXTRA_TEXT, content)
        context.startActivity(Intent.createChooser(intent, "分享"))
    }

}