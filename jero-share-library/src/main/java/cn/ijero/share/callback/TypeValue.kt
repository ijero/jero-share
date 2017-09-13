package cn.ijero.share.callback

import android.support.annotation.IntDef

/**
 * 分享或者登录的TYPE，参考：[cn.ijero.share.callback.TypeValue.Companion.Type]
 *
 * Created by Jero on 2017/9/13.
 *
 */
class TypeValue {
    companion object {

        /**
         *
         * 分享的类型：
         *
         * - [TYPE_NONE] - 表示没有分享类型。
         * - [TYPE_SHARE_QQ] - QQ分享。
         * - [TYPE_SHARE_QQ_ZONE] - QQ空间分享。
         * - [TYPE_SHARE_WECHAT] - 微信分享。
         * - [TYPE_SHARE_WECHAT_CIRCLE] - 微信朋友圈分享。
         * - [TYPE_SHARE_WEI_BO] - 微博分享。
         * - [TYPE_SHARE_SMS] - 短信分享。
         * - [TYPE_LOGIN_FOR_WE_CHAT] - 微信登录。
         * - [TYPE_LOGIN_FOR_QQ] - QQ登录。
         *
         * Created by Jero on 2017/7/31.
         */
        @IntDef(TYPE_NONE, TYPE_SHARE_QQ, TYPE_SHARE_QQ_ZONE, TYPE_SHARE_WECHAT, TYPE_SHARE_WECHAT_CIRCLE, TYPE_SHARE_WEI_BO, TYPE_SHARE_SMS, TYPE_LOGIN_FOR_WE_CHAT, TYPE_LOGIN_FOR_QQ)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Type

        /**
         * 参考：[Type]
         *
         * Created by Jero on 2017/9/13.
         *
         */
        const val TYPE_NONE = 0L
        /**
         * 参考：[Type]
         *
         * Created by Jero on 2017/9/13.
         *
         */
        const val TYPE_SHARE_QQ = 1L
        /**
         * 参考：[Type]
         *
         * Created by Jero on 2017/9/13.
         *
         */
        const val TYPE_SHARE_QQ_ZONE = 2L
        /**
         * 参考：[Type]
         *
         * Created by Jero on 2017/9/13.
         *
         */
        const val TYPE_SHARE_WECHAT = 3L
        /**
         * 参考：[Type]
         *
         * Created by Jero on 2017/9/13.
         *
         */
        const val TYPE_SHARE_WECHAT_CIRCLE = 4L
        /**
         * 参考：[Type]
         *
         * Created by Jero on 2017/9/13.
         *
         */
        const val TYPE_SHARE_WEI_BO = 5L
        /**
         * 参考：[Type]
         *
         * Created by Jero on 2017/9/13.
         *
         */
        const val TYPE_SHARE_SMS = 6L
        /**
         * 参考：[Type]
         *
         * Created by Jero on 2017/9/13.
         *
         */
        const val TYPE_LOGIN_FOR_WE_CHAT = 7L
        /**
         * 参考：[Type]
         *
         * Created by Jero on 2017/9/13.
         *
         */
        const val TYPE_LOGIN_FOR_QQ = 8L


    }
}
