# MCrashNotifyDD
Android crash 信息收集以及实时通知给钉钉群，减少crash 率

#使用方法
1，在根build.gradle(吐槽下，为什么不叫root.gradle，这样一目了然是哪个gradle文件)下加入：maven { url 'https://jitpack.io' }
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
2，在要使用的moudle/build.gradle中加入依赖
	dependencies {
	        implementation 'com.github.mayuhai:MCrashNotifyDD:1.0.1'
	}
  
3, 在项目中初始化(建议在application中)
 a,初始化Crash监听
``` 
TraceLog.getInstance()
//                .setCacheSize(10 * 1024)//支持设置缓存大小，超出后清空
                .setLogDir(this,
                getFilesDir() + File.separator + TraceLog.LOG_DRI + File.separator)
                .setWifiOnly(false)//设置只在Wifi状态下上传，设置为false为Wifi和移动网络都上传
                .setWifiOnly(false)//设置只在Wifi状态下上传，设置为false为Wifi和移动网络都上传
                .setLogSaver(new CrashWriter(this))//支持自定义保存崩溃信息的样式
                .setLogDebugModel(true) //设置是否显示日志信息
                .setLogContent(TraceLog.LOG_LEVEL_INFO)
//                .setEncryption(new Base64Encode()) //支持日志到AES加密、DES加密和Base64编码，默认不开启
//                .setDecode(true)//解密已加密的数据
                .setCombineInfoCrash(false)//是否合并info和crash
                .setUploadType(new RetrofitUploadTrace(this))
                .init(this);
 ``` 
 b，初始化发送钉钉消息监听,填入钉钉机器人的Webhook
```
PerformanceReportManager.getInstance()
                .setContext(this)
                .setReportOfflineUrl("https://oapi.dingtalk.com/robot/send?access_token=fe747815e640beba0f24408e2c68d1f503c14258c4741146b4e0bdca54fbad78");
``` 

大功告成，消息实时提醒，特别好用，比bugly好用多了
 
