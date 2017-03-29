##React-native 如何使用 code-push 热更新
使用前须知

* #####Q: “苹果应用商店和android应用商店允不允许使用热更新？” 

   A: “都允许。”

	>苹果允许使用热更新Apple's developer agreement, 但是规定不能弹框提示用户更新，影响用户体验。 Google Play也允许热更新，但必须弹框告知用户更新。在中国的android市场发布时，都必须关闭更新弹框，否则会在审核应用时以“请上传最新版本的二进制应用包”驳回应用。
	
* #####Q: “react-native 开发环境更新模式是否可以直接用在生产环境下？”

	A: “不能。”

* #####Q: “code-push使用复杂么？”

	A: “不复杂。很多网上的文章说复杂，是因为作者没有仔细理解官方文档，而且认为踩坑了。”

* #####Q: “为什么推荐code-push？”

	A: ”非常好。除了满足基本更新功能外，还有统计，hash计算容错和补丁更新功能。微软的项目，大公司技术有保障，而且开源。近几年微软在拥抱开源方面，让大家也是刮目相看。“

##1.安装依赖包
1.1. **react-native-cli** react-native命令行工具，安装后可以在终端使用react-native命令

```shell
$ npm install react-native-cli@latest -g
```
1.2. **code-push-cli** 连接微软云端，管理发布更新版本命令行工具，安装后可以在终端使用code-push命令

```shell
$ npm install code-push-cli@latest -g 
```

1.3. **react-native-code-push** 集成到react-native项目，按照以下步骤安装并修改配置既可集成

```shell
$ react-native init CodePushDemo #初始化一个react-native项目
$ cd CodePushDemo
$ npm install --save react-native-code-push  #安装react-native-code-push
$ react-native link react-native-code-push  #连接到项目中，提示输入配置可以先行忽略
```
	
1.4. **code-push-server** 微软云服务在中国太慢，可以用它搭建自己的服务端。具体配置参考该项目

```shell
$ npm install code-push-server -g
$ code-push-server-db init --dbhost localhost --dbuser root --dbpassword #初始化数据库
$ code-push-server #启动服务 浏览器中打开 http://127.0.0.1:3000
```


##2.创建服务端应用

基于code-push-server服务
  
```shell
$ code-push login http://127.0.0.1:3000  #浏览器中登录获取token，用户名:admin, 密码:123456
$ code-push app add CodePushDemo-ios #创建iOS版, 获取Production DeploymentKey
$ code-push app add CodePushDemo-android #创建android版，获取获取Production DeploymentKey
```
>例如：创建android版，获取获取Production DeploymentKey
>>D:\ CodePushDemo> code-push app add CodePushDemo-android
Successfully added the "CodePushDemo-android" app, along with the following default deployments:

```shell
┌────────────┬───────────────────────────────────────┐
│ Name       │ Deployment Key                        │
├────────────┼───────────────────────────────────────┤
│ Production │ TVa4zdjrzOxTRYTftQyziTLn3D4f4ksvOXqog │
├────────────┼───────────────────────────────────────┤
│ Staging    │ 9EoBW0EXP5ZmD8K49WmJNjy2bNjp4ksvOXqog │
└────────────┴───────────────────────────────────────┘
```



##配置CodePushDemo react-native项目

###Android端配置

1.**编辑MainApplication.java**

1.1 YourKey替换成CodePushDemo-android的Production DeploymentKey值

1.2 YourCodePushServerUrl值设置为code-push-server服务地址 [**http://127.0.0.1:3000/**](#) 不在同一台机器的时候，请将127.0.0.1改成外网ip或者域名地址。

1.3 将默认版本号1.0改成三位1.0.0

1.4 android模拟器和code-push-server在同一台机器上时，需要额外运行命令adb reverse tcp:3000 tcp:3000 代理端口，否则无法访问127.0.0.1:3000端口

```java
@Override
protected List<ReactPackage> getPackages() {
  return Arrays.<ReactPackage>asList(
      new MainReactPackage(),
      new CodePush(
         "YourKey",
         MainApplication.this,
         BuildConfig.DEBUG,
         "YourCodePushServerUrl" 
      )
  );
}
```

2.**添加更新检查**
可以参考[demo.js](https://github.com/lisong/code-push-demo-app/blob/master/demo.js)
可以在入口componentDidMount添加，也可以参考下面的形式添加：


```javascript
import CodePush from "react-native-code-push";
import MainProvider from './src/ui_layer/containers/MainProvider';
let codePushOptions = { checkFrequency: CodePush.CheckFrequency.ON_APP_RESUME, installMode: CodePush.InstallMode.ON_NEXT_RESTART}; ##配置codepush参数

class DashengChefuMerchant extends Component {
  render() {
    return (
        <MainProvider />
    );
  }
}

DashengChefuMerchant = CodePush(codePushOptions)(DashengChefuMerchant);
AppRegistry.registerComponent('DashengChefuMerchant', () => DashengChefuMerchant);
 
```

***
---

## 运行CodePushDemo react-native项目

### android
```shell
$ cd ~/CodePushDemo
$ cd android
$ ./gradlew assembleRelease
$ cd app/build/outputs/apk  #将打好的包app-release.apk安装到您的手机上
```


## 发布更新到服务上

iOS和android要分开发布，所以创建了`CodePushDemo-ios`和`CodePushDemo-android`应用

```shell
$ cd ~/CodePushDemo
$ code-push release-react CodePushDemo-ios ios -d Production #iOS版
$ code-push release-react CodePushDemo-android android -d Production #android版
```

##参考：更多CodePush命令：

```Shell
CodePush is a service that enables you to deploy mobile app updates directly to your users' devices.

Usage: code-push <command>

命令：
  access-key       View and manage the access keys associated with your account
  app              View and manage your CodePush apps
  collaborator     View and manage app collaborators
  debug            View the CodePush debug logs for a running app
  deployment       View and manage your app deployments
  link             Link an additional authentication provider (e.g. GitHub) to an existing CodePush account
  login            Authenticate with the CodePush server in order to begin managing your apps
  logout           Log out of the current session
  patch            Update the metadata for an existing release
  promote          Promote the latest release from one app deployment to another
  register         Register a new CodePush account
  release          Release an update to an app deployment
  release-cordova  Release a Cordova update to an app deployment
  release-react    Release a React Native update to an app deployment
  rollback         Rollback the latest release for an app deployment
  session          View and manage the current login sessions associated with your account
  whoami           Display the account info for the current login session

选项：
  -v, --version  显示版本号  [布尔]
```

## 注意事项

- 苹果允许使用热更新[Apple's developer agreement](https://developer.apple.com/programs/ios/information/iOS_Program_Information_4_3_15.pdf), 但是规定不能弹框提示用户更新，影响用户体验。 而Google Play恰好相反，必须弹框告知用户更新。然而中国的android市场都必须关闭更新弹框，否则会在审核应用时以“请上传最新版本的二进制应用包”驳回应用。
- react-native 不同平台bundle包不一样，在使用code-push-server的时候必须创建不同的应用来区分(eg. CodePushDemo-ios 和 CodePushDemo-android)
- react-native-code-push只更新资源文件,不会更新java和Objective C，所以npm升级依赖包版本的时候，如果依赖包使用的本地化实现, 这时候必须更改应用版本号(ios修改Info.plist中的CFBundleShortVersionString, android修改build.gradle中的versionName), 然后重新编译app发布到应用商店。
- 推荐使用code-push release-react 命令发布应用，该命令合并了打包和发布命令(eg. code-push release-react CodePushDemo-ios ios -d Production)

## 例子

[code-push-demo-app](https://github.com/lisong/code-push-demo-app)














