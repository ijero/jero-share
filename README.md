
![logo](/imgs/logo.png)

# JeroShare
集中封装的分享组件，实现更便捷的分享方式。

目前支持的社区：

1. 微信（及朋友圈）
2. QQ（及QQ空间）
3. 微博
4. 短信
5. 后续将增加更多社区分享、自定义模块及一键分享UI。

使用步骤：

	- 请在分享平台上确认您的应用程序的包名、签名等信息填写正确。
	- 导入本项目作为您的项目的library。
	- 参照Demo实现分享的回调处理（由开发者自由处理回调）。

导入library：




配置：

- 在项目的aasets文件夹下，新建share.properties文件，内容如下

		qq_appid=您的qq开发平台的appid
		wechat_appid=您的微信开发平台的appid
		weibo_appkey=您的微博开发平台的appKey


- 微信(不使用微信分享就跳过该步骤)： 在自己的包名下手动创建wxapi包，新建WXEntryActivity并继承自WechatHandlerActivity，并配置AndroidManifest.xml。

		public class WXEntryActivity extends WechatHandlerActivity {
		    @Override
		    public void onSuccess(long type) {
		        if (type == ShareTypedValue.TYPE_WECHAT) {
		            ToastsKt.toast(this, "微信分享成功");
		        } else {
		            ToastsKt.toast(this, "微信朋友圈分享成功");
		        }
		        finish();
		    }
		
		    @Override
		    public void onFailed(long type, int errorCode, String errorStr) {
		        if (type == ShareTypedValue.TYPE_WECHAT) {
		            ToastsKt.toast(this, "微信分享失败");
		        } else {
		            ToastsKt.toast(this, "微信朋友圈分享失败");
		        }
		        finish();
		    }
		
		    @Override
		    public void onCancel(long type) {
		        if (type == ShareTypedValue.TYPE_WECHAT) {
		            ToastsKt.toast(this, "微信分享取消");
		        } else {
		            ToastsKt.toast(this, "微信朋友圈分享取消");
		        }
		        finish();
		    }
		}

	AndroidManifest.xml:
	
		<!--微信回调-->
	    <activity android:name="cn.ijero.share.demo.wxapi.WXEntryActivity"
	        android:launchMode="singleTop"
	        android:exported="true">
	        <intent-filter>
	            <action android:name="android.intent.action.VIEW"/>
	            <category android:name="android.intent.category.DEFAULT"/>
	            <data android:scheme="jeroshare"/>
	        </intent-filter>
	    </activity>


- QQ（不使用QQ就跳过此步骤）：只需要配置xml即可（注意：appid前要加tencent）

		<!--qq回调页面-->
        <activity
            android:name="com.tencent.tauth.AuthActivity"
            android:noHistory="true"
            android:launchMode="singleTask" >
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data android:scheme="tencent您的appid" />
            </intent-filter>
        </activity>

        <activity
            android:name="com.tencent.connect.common.AssistActivity"
            android:screenOrientation="behind"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"
            android:configChanges="orientation|keyboardHidden">
        </activity>


- 在分享之前，先实例化分享组件

	    jeroShare = JeroShare.Companion.getInstance(this);
	    
    
- 分享数据
		
		shareItem = new ShareItem(title, summary, link, image); // 构建分享内容
		
		jeroShare.share(ShareTypedValue.TYPE_QQ, shareItem); // QQ
	
		jeroShare.share(ShareTypedValue.TYPE_QQ_ZONE, shareItem); // QQ空间
	
		jeroShare.share(ShareTypedValue.TYPE_WECHAT, shareItem); // 微信
	
		jeroShare.share(ShareTypedValue.TYPE_WECHAT_CIRCLE, shareItem); // 微信朋友圈
	
		jeroShare.share(ShareTypedValue.TYPE_WEI_BO, shareItem); // 微博
	
		jeroShare.shareToSms("《JeroShare》致力于为Android开发者实现简便的社会化分享，详情请点击\n" + link); //短信
    
- 实现回调（短信、微信除外），微信回调参考1中的demo源码。

	在Activity的onActivityResult方法中，添加handleResult()方法，并在当前Activity实现OnShareCallbackListener接口即可：

	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        jeroShare.handleResult(requestCode, resultCode, data, this);
	    }
    
	回调结果在onCallback方法中（微信除外，短信不支持回调）：

	    @Override
	    public void onCallback(long type, long resultCode, ShareError shareError) {
	        if (type == ShareTypedValue.TYPE_QQ) {
	            handleQQResult(resultCode, shareError);
	        } else if (type == ShareTypedValue.TYPE_QQ_ZONE) {
	            handleQZoneResult(resultCode, shareError);
	        } else if (type == ShareTypedValue.TYPE_WEI_BO) {
	            handleWeiBo(resultCode, shareError);
	        } else if (type == ShareTypedValue.TYPE_SMS) {
	            handleSms(resultCode, shareError);
	        }
	    }

额外：

1. 分享内容包装类。

		ShareItem(val title: String, val summary: String? = null, val targetUrl: String, val imageUrl: String? = null)
		
		title - 分享的标题，必须。
		summary - 分享的描述，非必须（可为null）。
		targetUrl - 目标url，必须。
		imageUrl - 分享的图片url，非必须（可为null）。

2. 错误信息包装类。

		ShareError(val code: Int = -1, val message: String? = null, val detail: String? = null)

		code - 出错的code。
		message - 出错的信息。
		detail - 出错的详情。