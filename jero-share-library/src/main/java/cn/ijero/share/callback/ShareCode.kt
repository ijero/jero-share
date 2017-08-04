@file:JvmName("ShareResultCodeValue")
@file:JvmMultifileClass
package cn.ijero.share.callback

import android.support.annotation.IntDef


/**
 *  - [RESULT_CODE_CANCEL] - 分享取消。
 *
 *  - [RESULT_CODE_FAILED] - 分享出错。
 *
 *  - [RESULT_CODE_SUCCESS] - 分享成功。
 *
 * Created by Jero on 2017/8/1.
 *
 */
@IntDef(RESULT_CODE_SUCCESS, RESULT_CODE_FAILED, RESULT_CODE_CANCEL)
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class ShareResultCode

const val RESULT_CODE_CANCEL = 0L
const val RESULT_CODE_SUCCESS = 1L
const val RESULT_CODE_FAILED = 2L
