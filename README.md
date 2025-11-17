# 轻阅读
一个全移动端可用的阅读app,不管是手机还是大屏设备都适配了ui，这个仓库是轻阅读app服务端源码，可以在全端同步进度。开源阅读不方便同步轻阅读进度，并且开源阅读过于复杂，移植多端难度较大，通过同一个后端的方法，可以做到多端体验基本一致。

<details><summary>免责声明（Disclaimer）</summary>
轻阅读是一款提供网络文学搜索的工具，为广大网络文学爱好者提供一种方便、快捷舒适的试读体验。

当您搜索一本书的时，轻阅读会将该书的书名以关键词的形式提交到各个第三方网络文学网站。各第三方网站返回的内容与轻阅读无关，轻阅读对其概不负责，亦不承担任何法律责任。任何通过使用轻阅读而链接到的第三方网页均系他人制作或提供，您可能从第三方网页上获得其他服务，轻阅读对其合法性概不负责，亦不承担任何法律责任。第三方搜索引擎结果根据您提交的书名自动搜索获得并提供试读，不代表轻阅读赞成或被搜索链接到的第三方网页上的内容或立场。您应该对使用搜索引擎的结果自行承担风险。

轻阅读不做任何形式的保证：不保证第三方搜索引擎的搜索结果满足您的要求，不保证搜索服务不中断，不保证搜索结果的安全性、正确性、及时性、合法性。因网络状况、通讯线路、第三方网站等任何原因而导致您不能正常使用轻阅读，轻阅读不承担任何法律责任。轻阅读尊重并保护所有使用轻阅读用户的个人隐私权，您注册的用户名、电子邮件地址等个人资料，非经您亲自许可或根据相关法律、法规的强制性规定，轻阅读不会主动地泄露给第三方。

轻阅读致力于最大程度地减少网络文学轻阅读者在自行搜寻过程中的无意义的时间浪费，通过专业搜索展示不同网站中网络文学的最新章节。轻阅读在为广大小说爱好者提供方便、快捷舒适的试读体验的同时，也使优秀网络文学得以迅速、更广泛的传播，从而达到了在一定程度促进网络文学充分繁荣发展之目的。轻阅读鼓励广大小说爱好者通过轻阅读发现优秀网络小说及其提供商，并建议阅读正版图书。任何单位或个人认为通过轻阅读搜索链接到的第三方网页内容可能涉嫌侵犯其信息网络传播权，应该及时向轻阅读提出书面权力通知，并提供身份证明、权属证明及详细侵权情况证明。轻阅读在收到上述法律文件后，将会依法尽快断开相关链接内容。
</details>

# 官网
http://www.qread.xyz/

# 后台
http://ip:8080/admin(轻阅读后台)

http://ip:8080/(web阅读)

web端不支持cookie的保存，不支持webview，漫画或者图片或者听书链接回因资源服务器禁止跨域而无法显示，app版才支持cookie的保存

# 演示后端
https://qread.zeabur.app/     
https://qread.zeabur.app/admin

管理后台账号: admin 密码:adminadmin    
演示后端随时可能停用或者还原 仅供演示请勿储存数据

# 注意
所以后端都为用户自行搭建，开发者不提供任何后端。如果需要开通权限请联系后端管理员！

# 打赏
[ 如果你觉得这个项目帮助到了你，你可以帮作者买一杯果汁表示鼓励 🍹
](http://app.qread.xyz/pay)

# 本地书仓
需要自行导入本地书仓书源，导入后需要自行修改书源url中的路径（/Users/q9uo11/Downloads/book）改为你本地书仓的路径（如果用的容器需要用容器内的路径），如你的后端不是监听的8080端口，需要修改书源中127.0.0.1:8080中的8080为你监听的端口，
为了安全本地书仓的接口仅允许本地访问。

# 自定义包名
自定义包名可以免填写后端，将直接使用在后台记录的后端地址，自定义的包名请不要以com.q9uo11开头。修改方法均已测试，请自行研究不提供技术支持。

# 安卓自定义包名
直接下载mt管理器修改，具体不说了百度一堆

# ios自定义包名
可以使用轻松签，全能签等，具体不说了百度一堆,[或者可以通过mac电脑修改.](https://blog.csdn.net/jxfcwys/article/details/126937538)

# 鸿蒙自定义包名
我会将编译好flutter部分的ohos文件夹压缩，你们下载解压后用鸿蒙官方ide(DevEco-Studio)打开,然后再修改entry/src/mian/resources/rawfile/flutter_assets/assets/images/package.txt文件(在txt文件中填写你的自定义包名)，即可编译，由于没有证书编译会失败但会生成未签名版本在entry\build\default\outputs\default文件夹下。

# windows自定义包名
修改data/flutter_assets/assets/images/package.txt文件(在txt文件中填写你的自定义包名)

# macos自定义包名
macos代码是支持修改资源文件assets/images/package.txt从而修改包名，但mac资源文件被打包了需要特定软件才能解包，过于复杂。mac也支持修改包名，但没有什么简便方法，如果有伙伴研究出来了可以发我，我加上。

# 代理
代理功能为轻阅读独属，开启后可将get post等http请求从服务器发送改为从手机发送（仅支持调用系统函数时，如果书源直接自己调用java类而非自有函数时可能会不支持）。书源页开启的为全局代理，也可以使用下面的函数进行单url代理，
当然也可以这样写搜索链接 http://127.0.0.1,{“usePhone”:true}。      
代理功能是为了服务器的ip与手机ip不一致导致的盾的问题，或者是将需要vpn才能访问的资源让手机请求。

# 独有函数
````
java.qread() //永久返回1 ，用来判断是否为轻阅读
java.getWebViewUANEW()   //获取机型真正的ua用以判断型号为了兼容性getWebViewUA函数值返回安卓ua。  
java.getusePhone(urlStr: String, headers: Map<String, String>) //用法和java.get一致，但此函数会使用代理功能    
java.headusePhone(urlStr: String, headers: Map<String, String>) //用法和java.head一致，但此函数会使用代理功能       
java.postusePhone(urlStr: String, body: String, headers: Map<String, String>) //用法和java.post一致，但此函数会使用代理功能。    
java.startBrowserDp(url: String, title: String） //段评专用接口，优化显示
````

# 与开源阅读进度同步   
https://github.com/autobcb/qreadwebdav

# 书源权限
书源可以直接调用java代码，所以给予书源权限时一定要小心！

# 未支持函数
以下函数还未支持，有些调用了安卓接口无法支持，有些我觉得用不上，有些我也不知道是干啥，如果有轻阅读3支持的书源，轻阅读不支持，可以在issues里发给我。
````

fun un7zFile(zipPath: String): String 
fun unrarFile(zipPath: String): String
fun unArchiveFile(zipPath: String): String
fun  getRarStringContent(url: String, path: String): String
fun  getRarStringContent(url: String, path: String, charsetName: String): String
fun get7zStringContent(url: String, path: String): String
fun get7zStringContent(url: String, path: String, charsetName: String): String 
fun getRarByteArrayContent(url: String, path: String): ByteArray?
fun get7zByteArrayContent(url: String, path: String): ByteArray? 
fun getGlideUrl(): GlideUrl
fun getMediaItem(): MediaItem 
````
# 热键(web端和windows支持)
esc键返回 左右键翻页
F11 无边框 F12恢复边框(仅windows支持)
F10 最小话 F9 显示app(windows 和macos 支持)


# 快速部署
将后端文件（下载web*.zip）上传到root目录，确保root/read/read.jar存在，确保root/read/conf.yml存在（conf.yml中可修改后台管理的账号密码），然后安装docker,
网上有一键安装脚本可自行百度，docker一键部署命令如下，如需用mysql数据库请自行修改配置文件。
````
docker run -tid  -e TZ=Asia/Shanghai --name read  -v /root/read:/app --net=host --restart=always docker.1ms.run/openjdk:22-rc-oracle java -jar /app/read.jar
````
如需使用其他端口可修改启动命令为 java  -Dserver.port=端口  -jar /app/read.jar 。当然/root/read可以换成其他路径，只要确保这个路径下有read.jar和conf.yml。
需要需要使用代理可将启动命令修改为
````
 java  -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=1080 -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=1080 -jar /app/read.jar
````
# Docker Compose 部署方式（感谢Lin-max1032制作的镜像）
你也可以使用 docker-compose 更方便地管理和启动服务。请在你的服务器上新建一个 docker-compose.yml 文件，内容如下：
````
version: '3.8'

services:

  qread:
    image: linmax/read:latest
    container_name: qread
    restart: unless-stopped
    ports:
      - "8080:8080"                     #根据需求设置
    volumes:
      - ./appdata:/app
    environment:
      TZ: Asia/Shanghai                 #设置时区为上海

      # =========== 数据库设置 ===========
      DB_TYPE: sqlite                   #设置为mysql时数据库设置生效，设置为sqlite时数据库设置不生效
      DB_JDBCURL: jdbc:mysql://127.0.0.1:3306/数据库名?characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=UTC
      DB_USERNAME: 用户名
      DB_PASSWORD: 密码

      # =========== admin 用户设置 ===========
      #ADMIN_GONGGAO: 这是一个公告        #填写公告后 app 每次开启时都会弹出
      ADMIN_USERNAME: admin             #后台管理员账户
      ADMIN_PASSWORD: adminadmin        #后台管理员密码
      #3.1.0更新配置信息
      #ADMIN_CODE:                       # 需要填写app管理后台生成的密钥

      # =========== user 设置 ===========
      USER_ALLOWCHANGE: "true "         #是否允许自助修改权限 允许 true 不允许 false
      USER_ALLOWUPTXT: "false"          #是否允许上传txt 允许 true 不允许 false
      USER_ALLOWCACHE: "false"          #是否允许添加缓存 允许 true 不允许 false
      USER_ALLOWIMG: "false"            #是否使用图片解密 允许 true 不允许 false
      USER_ALLOWCHECK: "false"          #是否允许检验书源 允许 true 不允许 false
      USER_SOURCE: "0"                  # 0: 不允许修改书源, 1: 允许后台修改, 2: 独立书源
      USER_MAXSOURCE: "0"               # 最大书源数量，0 表示不限制
      USER_TIMEOUT: "0"                 #10分钟内除发多少次timeout禁用书源 0 为不限制
      #3.2.0新增配置信息
      USER_PROXYPNG: "false"             #是否代理封面

      #===========  SMTP 邮件设置 ===========
      #SMTP_HOST:                       #smtp邮箱host
      #SMTP_PROTOCOLS: TLSv1.2          #通讯协议
      #SMTP_PORT:                       #端口
      #SMTP_ACCOUNT:                    #邮箱账号
      #SMTP_PASSWORD:                   #邮箱密码，部分邮箱是安全码
      #SMTP_PERSONAL:                   #发送人的昵称
      #SMTP_CODESUBJECT: 验证码          #验证码邮件的主题
      # =========== 默认设置 ===========
      #DEFAULT_TTS:                     #默认tts链接
      #DEFAULT_RULE:                    #默认净化链接
      # =========== HTTP 线程设置 ===========
      SERVER_HTTP_CORETHREADS: x5       #默认线程
      SERVER_HTTP_MAXTHREADS: x10       #最大线程
      # =========== 日志输出 ===========
      #如果不想日志输出到文件请把下面配置去除注解(3.3.0新增配置信息)
      #DISABLE_LOG_TO_FILE: true
      # =========== 启动命令（必填：二选一）===========
      JAVA_CMD: java -jar /app/read.jar  #不使用代理启动
      #JAVA_CMD:  java  -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=1080 -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=1080 -jar /app/read.jar       #使用代理启动
````
使用步骤     
安装 docker-compose（大多数新版本 Docker 已自带）。    
将上面的内容保存为 docker-compose.yml。   
在该目录下执行：   
docker-compose up -d   
查看日志：   
docker-compose logs -f   
停止服务：   
docker-compose down

# 反向代理
如果需要使用nginx反向代理后端必须要注意websocket配置，目前websocket有四个：    
/api/接口版本号/ws        
/api/接口版本号/debug       
/api/接口版本号/rssdebug     
/api/接口版本号/checkdebug      

# 已知问题
RSA加密，RSA加密安卓端和JAVA端加密标准不同，所以有可能安卓版本轻阅读能解密的web版本不能解密，如果安卓端能解密的web不行请修改代码
````
Cipher.getInstance("RSA",BouncyCastleProvider());
````
如果svg图片中文显示为方框请在docker或者服务器安装中文字体
````
sudo apt install fonts-wqy-microhei fonts-wqy-zenhei xfonts-wqy
````

web端如果想要和安卓端解密一样一定要修改代码，BouncyCastleProvider在org.bouncycastle.jce.provider.BouncyCastleProvider
示范一个完整版的RSA解码，这段代码在已测试通过的书源中摘抄出来的
````
var javaImport = new JavaImporter();
javaImport.importPackage(
Packages.java.lang,
Packages.java.security,
Packages.org.bouncycastle.jce.provider,
Packages.java.security.interfaces,
Packages.java.security.spec,
Packages.java.io,      
Packages.javax.crypto,
Packages.javax.crypto.spec
);
with(javaImport){
function Decode(str){
       jmkey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjYFYoMbA0uW8by6+YIghxxsvibS9YW4yKVSulykAzZZwZ/+dNTkZ4inY7Pj08aksm6RCGKS6+WfvVQo/EdkcS5p2LY2/76qVzapyHsyQf/Pud6ATPKnwxNt/DaqjL35Z9K0NI/RF9x732RdIEOTKXppfRdzCa/1Ctm/5ZFilY8UmZsppkjDd3XkuPr3n3wVC8WFvqmdJ1N55prRlnaRaO+mIOXo3OsOzIxE5EdcE0TLT9OFZ3Wlbi3E0iI0v/ZsrWoL57YvLwo7BsARp7BansDCx8NZg6ObGQN/tNrE/nKqQTXeJjnFWXdLfhI7xivPPphkj5fNpiufIsIUEd7eXBwIDAQAB";
       publicKey = 	KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(jmkey, 0)));

       instance = Cipher.getInstance("RSA",new BouncyCastleProvider());
        instance.init(2, publicKey);
        decode = Base64.decode(str,0);
        blockSize = instance.getBlockSize();
        byteArrayOutputStream = new ByteArrayOutputStream(64);
         i = 0;
        while (true) {
             i2 = i * blockSize;
            if (decode.length - i2 > 0) {
                byteArrayOutputStream.write(instance.doFinal(decode, i2, blockSize));
                i++;
            } else {
                decode = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return new String(decode);
            }
        }
    }
}
````

ios端如果需要过一些盾如cf盾需要设置ua为ios，其他平台也可能回遇到验真ua的cf盾
````
Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1
````

Android TTS 本身不支持暂停功能，因此我们实施了一种解决方法。我们利用本机方法来确定调用时的 start 索引。我们使用该索引在下次调用时创建新文本。由于使用 ，暂停适用于 >= 26 的 SDK 版本。此外，如果使用 和 offsets 在 of 内，则需要跟踪它们，因为在暂停后调用时，一旦创建新文本，它们就会更新。已知部分系统的引擎不会适配这个接口所以部分安卓暂停后可能会重新读本句或者直接读下一句。


# mysql数据库
后端支持修改为mysql数据库，部署好数据库后参考mysqlconf.yml文件来修改conf.yml，默认的sqlite文件数据库性能弱不适合高强度使用。

# 常见问题
# 首页有红色感叹号，点击后提示未成功连接到服务器，怎么办？
这个一般都是由于 websocket的问题，如果你使用了反向代理，需要配置 nginx 让其支持 websocket。如果还不行请检查是否开启了 ssl，部分 ssl 证书 ws 会出错（原因未知）。再看看 docker 是否为 host 模式，旧版本 docker 用端口转发 ws 会出问题。

# httptts 读完一段有停顿，怎么办？
请检查是否开启了在线播放，关闭在线播放后会自动缓存下一段。

# 为啥我的 app 没有输入后端地址的地方？
请勿修改 app 包名，修改包名后默认为自定义包名模式，需要再 app 后台添加站点并绑定包名。

# 目前新增了 app 管理，我是否需要去 app 管理注册并添加站点？
app 管理仅提供主域名和备用域名，并可以使用别名当做后端。而且如果在后台登记了包名，你修改包名后就不会显示后端地址了，当你需要使用到这些功能时或者需要用到 api 时才需要去这里注册，不然正常填写后端正常使用即可。

# 为啥我将后端穿透出来后就无法使用了？
如果要使用穿透必须选择 tcp 穿透，http 穿透不一定会支持 ws。


## 感谢

- 项目初期参考了 [reader](https://github.com/hectorqin/reader)
- [阅读3.0](https://github.com/gedoor/legado)


![Image text](https://github.com/autobcb/read/blob/main/png/home.png?raw=true)
![Image text](https://github.com/autobcb/read/blob/main/png/book.png?raw=true)
![Image text](https://github.com/autobcb/read/blob/main/png/login.png?raw=true)
![Image text](https://github.com/autobcb/read/blob/main/png/home2.png?raw=true)
![Image text](https://github.com/autobcb/read/blob/main/png/faxian.png?raw=true)
![Image text](https://github.com/autobcb/read/blob/main/png/search.png?raw=true)
![Image text](https://github.com/autobcb/read/blob/main/png/read.png?raw=true)

![Image text](https://github.com/autobcb/read/blob/main/png/home3.jpg?raw=true)
![Image text](https://github.com/autobcb/read/blob/main/png/found.jpg?raw=true)
![Image text](https://github.com/autobcb/read/blob/main/png/my.jpg?raw=true)
![Image text](https://github.com/autobcb/read/blob/main/png/editsource.jpg?raw=true)
![Image text](https://github.com/autobcb/read/blob/main/png/source.jpg?raw=true)
![Image text](https://github.com/autobcb/read/blob/main/png/sourcelogin.jpg?raw=true)

