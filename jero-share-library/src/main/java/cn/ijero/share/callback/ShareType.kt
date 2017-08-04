@file:JvmName("ShareTypedValue")
@file:JvmMultifileClass
package cn.ijero.share.callback

import android.support.annotation.IntDef

/**
 *
 * 分享的类型：
 *
 * - [TYPE_NONE] - 表示没有分享类型。
 * - [TYPE_QQ] - QQ分享。
 * - [TYPE_QQ_ZONE] - QQ空间分享。
 * - [TYPE_WECHAT] - 微信分享。
 * - [TYPE_WECHAT_CIRCLE] - 微信朋友圈分享。
 * - [TYPE_WEI_BO] - 微博分享。
 * - [TYPE_SMS] - 短信分享。
 *
 * Created by Jero on 2017/7/31.
 */
@IntDef(TYPE_NONE, TYPE_QQ, TYPE_QQ_ZONE, TYPE_WECHAT, TYPE_WECHAT_CIRCLE, TYPE_WEI_BO, TYPE_SMS)
@Retention(AnnotationRetention.SOURCE)
annotation class ShareType

const val TYPE_NONE = 0L
const val TYPE_QQ = 1L
const val TYPE_QQ_ZONE = 2L
const val TYPE_WECHAT = 3L
const val TYPE_WECHAT_CIRCLE = 4L
const val TYPE_WEI_BO = 5L
const val TYPE_SMS = 6L