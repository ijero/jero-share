package cn.ijero.share.callback

import android.support.annotation.IntDef

class ResultCode {
    companion object {
        /**
         *  - [RESULT_CODE_CANCEL] - 取消。
         *
         *  - [RESULT_CODE_FAILED] - 出错。
         *
         *  - [RESULT_CODE_SUCCESS] - 成功。
         *
         * Created by Jero on 2017/8/1.
         *
         */
        @IntDef(RESULT_CODE_SUCCESS, RESULT_CODE_FAILED, RESULT_CODE_CANCEL)
        @Retention(AnnotationRetention.SOURCE)
        @Target(AnnotationTarget.VALUE_PARAMETER)
        annotation class ResultCode

        const val RESULT_CODE_CANCEL = 0L
        const val RESULT_CODE_SUCCESS = 1L
        const val RESULT_CODE_FAILED = 2L

    }
}

