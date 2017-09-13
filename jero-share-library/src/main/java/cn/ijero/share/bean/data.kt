package cn.ijero.share.bean

import java.io.Serializable

/**
 * 分享出错时的信息包装类。
 *
 * - code - 出错的code。
 * - message - 出错的信息。
 * - detail - 出错的详情。
 *
 * Created by Jero on 2017/7/31.
 */
data class Error(val code: Int = -1, val message: String? = null, val detail: String? = null) : Serializable {
    companion object {
        // =========QQ原有状态=======
        const val ERROR_IO = -2
        const val ERROR_URL = -3
        const val ERROR_JSON = -4
        const val ERROR_PARAM = -5
        const val ERROR_UNKNOWN = -6
        const val ERROR_CONNECTTIMEOUT = -7
        const val ERROR_SOCKETTIMEOUT = -8
        const val ERROR_HTTPSTATUS_ERROR = -9
        const val ERROR_NETWORK_UNAVAILABLE = -10
        const val ERROR_FILE_EXISTED = -11
        const val ERROR_NO_SDCARD = -12
        const val ERROR_LOCATION_TIMEOUT = -13
        const val ERROR_LOCATION_VERIFY_FAILED = -14
        const val ERROR_QQVERSION_LOW = -15
        // ========= 自定新增状态 ============
        const val ERROR_INVALID_IMAGE_URL = -16
        const val ERROR_INVALID_APPID = -17
        const val ERROR_WEIBO_ERROR = -18

        const val MSG_INVALID_IMAGE_URL = "图片url地址不正确"
        const val MSG_INVALID_APPID = "appid不正确"

    }
}

/**
 * 分享包装类。
 *
 * - title - 分享的标题，必须。
 * - summary - 分享的描述，非必须（可为null）。
 * - targetUrl - 目标url，必须。
 * - imageUrl - 分享的图片url，非必须（可为null）。
 *
 * Created by Jero on 2017/8/1.
 *
 */
data class ShareItem(val title: String, val summary: String? = null,
                     val targetUrl: String, val imageUrl: String? = null) : Serializable

data class QQLoginResult
@JvmOverloads
constructor(
        val ret: Int = 0,
        val pay_token: String?,
        val pf: String?,
        val expires_in: String?,
        val openid: String?,
        val pfkey: String?,
        val msg: String?,
        val access_token: String?
) : Serializable