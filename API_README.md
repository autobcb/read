# API README

本文件整理了项目内所有 API 路由、**参数列表**以及**返回内容**，来源于服务端控制器方法签名与返回值。除非特别标注，`@Mapping` 默认不限制 HTTP 方法（可用 GET/POST 等）。

> 说明
> - `Context ctx` 为框架注入的请求上下文，一般无需客户端传参。
> - `@Body` 参数表示 **JSON 请求体**。
> - `UploadedFile` 参数表示 **multipart/form-data 文件上传**。
> - 版本路由：`/api/{v}`，其中 `v` 为接口版本号（`BaseController.apiversion = 5`）。
> - **返回结构**：大多数接口返回 `JsonResponse`（或 `/qapi` 返回 `QJsonResponse`）。
>   - `JsonResponse` 常见字段：`isSuccess`, `errorMsg`, `data`（或 `Data(...)`）
>   - `QJsonResponse` 常见字段：`success`, `msg`, `data`

---

## WebSocket

| 路径 | 说明 | 参数 | 返回 |
| --- | --- | --- | --- |
| `/api/{v}/ws` | 主 WebSocket | `id`（accessToken，query 参数） | WebSocket 消息（JSON 文本） |
| `/api/{v}/debug` | 调试 WebSocket | - | WebSocket 消息 |
| `/api/{v}/rssdebug` | RSS 调试 WebSocket | - | WebSocket 消息 |
| `/api/{v}/checkdebug` | 书源检测调试 WebSocket | - | WebSocket 消息 |

---

## 核心业务 API（`/api/{v}`）

### UserController

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/appversion` | 无 | `JsonResponse`（Data: 版本信息/字符串） |
| `/changeSourcePermission` | `accessToken: String?`, `permission: Int` | `JsonResponse`（Data: 无） |
| `/login` | `username: String?`, `password: String?`, `model: String?`, `v: Int (Path)` | `JsonResponse`（Data: token / 用户信息） |
| `/getUserInfo` | `accessToken: String?` | `JsonResponse`（Data: 用户信息） |
| `/changepass` | `accessToken: String?`, `password: String?`, `oldpassword: String` | `JsonResponse`（Data: 无） |
| `/getalltocken` | `accessToken: String?` | `JsonResponse`（Data: token 列表） |

### ItemController

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/getitem` | `accessToken: String?`, `name: String?` | `JsonResponse`（Data: item 值） |
| `/setitem` | `accessToken: String?`, `name: String?`, `value: String?` | `JsonResponse`（Data: 无） |

### BookshelfController

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/getBookshelfPage` | `accessToken: String?` | `JsonResponse`（Data: 书架分页数据） |
| `/getBookshelfNew` | `accessToken: String?`, `md5: String?`, `page: String` | `JsonResponse`（Data: 书架列表/增量） |
| `/getgroupNew` | `accessToken: String?`, `md5: String?` | `JsonResponse`（Data: 分组列表/增量） |
| `/addreadchapter` | `accessToken: String?`, `readchapter: String?`, `url: String?` | `JsonResponse`（Data: 无） |

### BookGroupController

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/getgroup` | `accessToken: String?` | `JsonResponse`（Data: 分组列表） |
| `/addgroup` | `accessToken: String?`, `name: String?` | `JsonResponse`（Data: 无） |
| `/delgroup` | `accessToken: String?`, `name: String?` | `JsonResponse`（Data: 无） |
| `/editgroup` | `accessToken: String?`, `oldname: String?`, `newname: String?` | `JsonResponse`（Data: 无） |
| `/setgroup` | `accessToken: String?`, `name: String?`, `url: String?` | `JsonResponse`（Data: 无） |
| `/setgroups` | `accessToken: String?`, `name: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/ordergroup` | `accessToken: String?`, `groups: String` | `JsonResponse`（Data: 无） |

### GroundController

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/getallgroundPage` | `accessToken: String?` | `JsonResponse`（Data: 背景分页） |
| `/getallgroundNew` | `accessToken: String?`, `md5: String?`, `page: String` | `JsonResponse`（Data: 背景列表/增量） |
| `/addground` | `accessToken: String?`, `ground: BackGround` (`@Body`) | `JsonResponse`（Data: 无） |
| `/delground` | `accessToken: String?`, `ground: BackGround` (`@Body`) | `JsonResponse`（Data: 无） |
| `/getallground` | `accessToken: String?` | `JsonResponse`（Data: 背景列表） |
| `/importground` | `accessToken: String?`, `file: UploadedFile?` | `JsonResponse`（Data: 导入结果） |

### BookMarkController

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/addbookmark` | `accessToken: String?`, `url: String`, `name: String`, `index: Int`, `pos: Double` | `JsonResponse`（Data: 无） |
| `/getbookmark` | `accessToken: String?`, `url: String` | `JsonResponse`（Data: 书签列表） |
| `/delbookmark` | `accessToken: String?`, `id: String` | `JsonResponse`（Data: 无） |

### ReplaceRuleController

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/getdefaultrule` | `accessToken: String?` | `JsonResponse`（Data: 默认规则） |
| `/getReplaceRulesPage` | `accessToken: String?` | `JsonResponse`（Data: 规则分页） |
| `/getReplaceRulesNew` | `accessToken: String?`, `md5: String?`, `page: String` | `JsonResponse`（Data: 规则列表/增量） |
| `/addReplaceRule` | `accessToken: String?`, `rule: ReplaceRule` (`@Body`) | `JsonResponse`（Data: 无） |
| `/topReplaceRule` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: 无） |
| `/delReplaceRule` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: 无） |
| `/delReplaceRules` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/stopReplaceRules` | `accessToken: String?`, `id: String?`, `st: String?` | `JsonResponse`（Data: 无） |
| `/stopReplaceRulesbyIds` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/startReplaceRulesbyIds` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/saverules` | `accessToken: String?`, `content: String` (`@Body`) | `JsonResponse`（Data: 无） |
| `/saverule` | `accessToken: String?`, `content: String` (`@Body`) | `JsonResponse`（Data: 无） |

### BookController

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/searchBook` | `accessToken: String?`, `bookSourceUrl: String?`, `page: Int?`, `key: String?` | `JsonResponse`（Data: 搜索结果列表） |
| `/exploreBook` | `accessToken: String?`, `bookSourceUrl: String?`, `page: Int?`, `ruleFindUrl: String?` | `JsonResponse`（Data: 探索结果列表） |
| `/saveBookInfo` | `accessToken: String?`, `book: SearchBook` | `JsonResponse`（Data: 保存结果） |
| `/urlsaveBook` | `accessToken: String?`, `url: String` | `JsonResponse`（Data: 保存结果） |
| `/saveBook` | `accessToken: String?`, `book: SearchBook`, `useReplaceRule: Int` | `JsonResponse`（Data: 保存结果） |
| `/saveBooks` | `accessToken: String?`, `content: String` (`@Body`) | `JsonResponse`（Data: 保存结果） |
| `/refreshBook` | `accessToken: String?`, `bookurl: String?` | `JsonResponse`（Data: 刷新结果） |
| `/getBookinfo` | `accessToken: String?`, `book: SearchBook?` | `JsonResponse`（Data: 书籍详情） |
| `/getBookinfo2` | `accessToken: String?`, `url: String?` | `JsonResponse`（Data: 书籍详情） |
| `/deleteBook` | `accessToken: String?`, `book: SearchBook` | `JsonResponse`（Data: 无） |
| `/deleteBooks` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/updateuseReplaceRule` | `accessToken: String?`, `url: String`, `useReplaceRule: Int?` | `JsonResponse`（Data: 无） |
| `/getcancache` | `accessToken: String?`, `url: String` | `JsonResponse`（Data: 是否可缓存） |
| `/getcancachelist` | `accessToken: String?` | `JsonResponse`（Data: 可缓存列表） |
| `/addCache` | `accessToken: String?`, `url: String` | `JsonResponse`（Data: 无） |
| `/delCache` | `accessToken: String?`, `id: String` | `JsonResponse`（Data: 无） |
| `/saveCookies` | `accessToken: String?`, `url: String`, `cookie: String`, `html: String?`, `id: String?` | `JsonResponse`（Data: 无） |
| `/getCookies` | `accessToken: String?`, `url: String?` | `JsonResponse`（Data: Cookie 内容） |
| `/savehtml` | `accessToken: String?`, `html: String?`, `id: String?` | `JsonResponse`（Data: 无） |
| `/cleancookies` | `accessToken: String?` | `JsonResponse`（Data: 无） |
| `/cleancaches` | `accessToken: String?` | `JsonResponse`（Data: 无） |
| `/noCookies` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: 无） |

### ReadController

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/getChapterList` | `accessToken: String?`, `bookSourceUrl: String?`, `url: String?` | `JsonResponse`（Data: 章节列表） |
| `/getBookContent` | `accessToken: String?`, `bookSourceUrl: String?`, `url: String?`, `index: Int?`, `type: Int?` | `JsonResponse`（Data: 章节正文） |
| `/getBookContentNew` | `accessToken: String?`, `bookSourceUrl: String?`, `url: String?`, `index: Int?`, `type: Int?`, `bookname: String?`, `useReplaceRule: Int?` | `JsonResponse`（Data: `{ rules, text }`） |
| `/getChapterListNew` | `accessToken: String?`, `bookSourceUrl: String?`, `url: String?`, `bookname: String?`, `useReplaceRule: Int?`, `needRefresh: Int?` | `JsonResponse`（Data: 章节列表） |
| `/fetchBookContent` | `accessToken: String?`, `url: String?`, `index: Int?` | `JsonResponse`（Data: 无） |
| `/fetchBook` | `accessToken: String?`, `url: String?` | `JsonResponse`（Data: 无） |
| `/saveBookProgress` | `accessToken: String?`, `pos: Double?`, `url: String?`, `title: String?`, `index: Int?` | `JsonResponse`（Data: 阅读进度字符串） |
| `/getBookread` | `accessToken: String?`, `url: String?` | `JsonResponse`（Data: 阅读章节索引串） |
| `/setBookSource` | `accessToken: String?`, `bookUrl: String?`, `newUrl: String?`, `bookSourceUrl: String?` | `JsonResponse`（Data: 书籍信息） |
| `/getBookshelf` | `accessToken: String?`, `version: String?`, `name: String?`, `v: Int (Path)` | `JsonResponse`（Data: 书架列表） |
| `/getSourcesloginui` | `accessToken: String?`, `url: String` | `JsonResponse`（Data: loginUi JSON） |
| `/getBookSources` | `accessToken: String?`, `isall: String?`, `v: Int (Path)` | `JsonResponse`（Data: 书源列表） |
| `/getBookSourcesExploreUrl` | `accessToken: String?`, `bookSourceUrl: String?`, `need: String?` | `JsonResponse`（Data: `{ checkKeyWord, found, loginUrl, loginUi }`） |
| `/getopenurl` | `accessToken: String?`, `bookSourceUrl: String?`, `url: String?` | `JsonResponse`（Data: 解析后的 url） |
| `/svgtopng` | `accessToken: String?`, `svg: String?`（`ctx` 由框架注入） | **二进制 PNG** |
| `/imageDecode` | `accessToken: String?`, `bookSourceUrl: String?`, `book: String?`, `url: String?`, `header: String?`（`ctx` 由框架注入） | **二进制图片** / `JsonResponse` 错误 |
| `/getLoginInfo` | `accessToken: String?`, `bookSourceUrl: String?` | `JsonResponse`（Data: 登录信息 JSON 字符串） |
| `/getVariable` | `accessToken: String?`, `bookSourceUrl: String?` | `JsonResponse`（Data: 变量 JSON） |
| `/setVariable` | `accessToken: String?`, `bookSourceUrl: String?`, `info: String?` | `JsonResponse`（Data: 无） |
| `/getbookVariable` | `accessToken: String?`, `bookurl: String?` | `JsonResponse`（Data: 自定义变量 JSON） |
| `/setbookVariable` | `accessToken: String?`, `bookurl: String?`, `info: String?` | `JsonResponse`（Data: 无） |
| `/putLoginInfo` | `accessToken: String?`, `bookSourceUrl: String?`, `info: String?` | `JsonResponse`（Data: 无） |
| `/action` | `accessToken: String?`, `bookSourceUrl: String?`, `action: String?` | `JsonResponse`（Data: 无） |
| `/payAction` | `accessToken: String?`, `url: String?`, `index: Int` | `JsonResponse`（Data: 无） |
| `/changebooktype` | `accessToken: String?`, `bookUrl: String?`, `type: Int?` | `JsonResponse`（Data: 无） |
| `/proxypng` | `url: String?`（`ctx` 由框架注入） | **二进制图片** |

### SourceController

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/getBookSourcesPage` | `accessToken: String?` | `JsonResponse`（Data: 书源分页） |
| `/getBookSourcesNew` | `accessToken: String?`, `md5: String?`, `page: String` | `JsonResponse`（Data: 书源列表/增量） |
| `/getcansource` | `accessToken: String?` | `JsonResponse`（Data: 是否允许修改书源） |
| `/saveBookSources` | `accessToken: String?`, `content: String` (`@Body`) | `JsonResponse`（Data: 保存结果） |
| `/saveBookSourcesv2` | `accessToken: String?`, `source: String`, `urls: String` | `JsonResponse`（Data: 保存结果） |
| `/saveBookSource` | `accessToken: String?`, `content: String` (`@Body`) | `JsonResponse`（Data: 保存结果） |
| `/topSource` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: 无） |
| `/bottomSource` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: 无） |
| `/delbookSource` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: 无） |
| `/topallSource` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/bottomallSource` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/editsourcegroup` | `accessToken: String?`, `st: String`, `group: String`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/delbookSources` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/getbookSources` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: 单个书源） |
| `/editbookSources` | `accessToken: String?`, `content: EditMsg` (`@Body`) | `JsonResponse`（Data: 无） |
| `/stopbookSource` | `accessToken: String?`, `id: String?`, `st: String?` | `JsonResponse`（Data: 无） |
| `/stopbookSources` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/startbookSources` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/stopbookSourceExplores` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/startbookSourceExplores` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/getbookSourcejson` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 书源 JSON） |

### RssController

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/getRssSourcessPage` | `accessToken: String?` | `JsonResponse`（Data: RSS 源分页） |
| `/getRssSourcessNew` | `accessToken: String?`, `md5: String?`, `page: String` | `JsonResponse`（Data: RSS 源列表/增量） |
| `/getRssSourcess` | `accessToken: String?` | `JsonResponse`（Data: RSS 源列表） |
| `/rssshouldOverrideUrlLoading` | `accessToken: String?`, `id: String?`, `url: String?` | `JsonResponse`（Data: 是否拦截/处理） |
| `/getRssSources` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: RSS 源信息） |
| `/editRssSources` | `accessToken: String?`, `content: EditMsg` (`@Body`) | `JsonResponse`（Data: 无） |
| `/topRssSource` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: 无） |
| `/bottomallrssSource` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/topallrssSource` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/bottomRssSource` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: 无） |
| `/editrsssourcegroup` | `accessToken: String?`, `st: String`, `group: String`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/delRssSource` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: 无） |
| `/stopRssSource` | `accessToken: String?`, `id: String?`, `st: String?` | `JsonResponse`（Data: 无） |
| `/startRssSources` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/stopRssSources` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/delRssSources` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/getRssSourcejson` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: RSS 源 JSON） |
| `/saveRssSources` | `accessToken: String?`, `source: String`, `urls: String` | `JsonResponse`（Data: 保存结果） |
| `/getRssSourcesloginui` | `accessToken: String?`, `url: String` | `JsonResponse`（Data: loginUi JSON） |
| `/getRssType` | `accessToken: String?`, `id: String` | `JsonResponse`（Data: RSS 类型） |
| `/getArticles` | `accessToken: String?`, `id: String`, `sortUrl: String`, `sortName: String`, `page: Int` | `JsonResponse`（Data: 文章列表） |
| `/getRsssortUrls` | `accessToken: String?`, `id: String` | `JsonResponse`（Data: 分类 URL 列表） |
| `/getRssContent` | `accessToken: String?`, `id: String`, `article: String` | `JsonResponse`（Data: 文章内容） |
| `/getRssLoginInfo` | `accessToken: String?`, `id: String` | `JsonResponse`（Data: 登录信息 JSON） |
| `/putRssLoginInfo` | `accessToken: String?`, `id: String`, `info: String?` | `JsonResponse`（Data: 无） |
| `/rssaction` | `accessToken: String?`, `id: String`, `action: String?` | `JsonResponse`（Data: 无） |
| `/getRssVariable` | `accessToken: String?`, `id: String` | `JsonResponse`（Data: 变量 JSON） |
| `/setRssVariable` | `accessToken: String?`, `id: String`, `info: String?` | `JsonResponse`（Data: 无） |
| `/getRssContenthtml` | `id: String`（`ctx` 由框架注入） | HTML 文本输出 |

### TTsController

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/getallttsPage` | `accessToken: String?` | `JsonResponse`（Data: TTS 分页） |
| `/getallttsNew` | `accessToken: String?`, `md5: String?`, `page: String` | `JsonResponse`（Data: TTS 列表/增量） |
| `/getalltts` | `accessToken: String?` | `JsonResponse`（Data: TTS 列表） |
| `/getdefaulttts` | `accessToken: String?` | `JsonResponse`（Data: 默认 TTS） |
| `/addtts` | `accessToken: String?`, `tts: HttpTts` (`@Body`) | `JsonResponse`（Data: 无） |
| `/deltts` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: 无） |
| `/delttss` | `accessToken: String?`, `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/savettss` | `accessToken: String?`, `content: String` (`@Body`) | `JsonResponse`（Data: 无） |
| `/tts` | `accessToken: String?`, `id: String?`, `speakText: String?`, `speechRate: Double?`（`ctx` 由框架注入） | **二进制音频** |
| `/getttsLoginInfo` | `accessToken: String?`, `id: String?` | `JsonResponse`（Data: 登录信息 JSON） |
| `/putttsLoginInfo` | `accessToken: String?`, `id: String?`, `info: String?` | `JsonResponse`（Data: 无） |
| `/ttsaction` | `accessToken: String?`, `id: String?`, `action: String?` | `JsonResponse`（Data: 无） |
| `/upjson` | `accessToken: String?`, `content: String` (`@Body`) | `JsonResponse`（Data: 无） |

### LocalBookController（API 版本）

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/importBookPreview` | `accessToken: String?`, `file: UploadedFile?` | `JsonResponse`（Data: 预览结果） |
| `/uploadimage` | `accessToken: String?`, `file: UploadedFile?` | `JsonResponse`（Data: 上传结果） |

---

## 站点与注册相关 API

### QApiController（`/qapi`）

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/qapi/check` | `key: String?` | `QJsonResponse`（Data: true/false） |
| `/qapi/getcode` | `key: String?`, `isalltime: Int?` | `QJsonResponse`（Data: code 字符串） |
| `/qapi/login` | `md5: String?`, `username: String`, `time: Int` | HTML 页面（`ylogin.html`） |
| `/qapi/changeSourcePermission` | `key: String?`, `username: String`, `permission: Int` | `QJsonResponse`（Data: 无） |
| `/qapi/changePermission` | `key: String?`, `username: String`, `permission: Int`, `allow: Boolean` | `QJsonResponse`（Data: 无） |
| `/qapi/regester` | `key: String?`, `username: String?`, `password: String?`, `phone: String?`, `email: String?` | `QJsonResponse`（Data: 无） |
| `/qapi/searchByEmail` | `key: String?`, `email: String?` | `QJsonResponse`（Data: 用户名列表） |

### HomeController（站点公共接口）

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `GET /regester` | 无 | HTML 页面（`regester.html`） |
| `POST /regester` | `username: String?`, `password: String?`, `phone: String?`, `email: String?`, `code: String?` | `JsonResponse`（Data: 无） |
| `/needcode` | 无 | `JsonResponse`（Data: Boolean） |
| `/sendResetCode` | `email: String?` | `JsonResponse`（Data: 无） |
| `/resetPassword` | `password: String?`, `code: String?`, `email: String?` | `JsonResponse`（Data: 无） |
| `/ua` | 无（`ctx` 由框架注入） | HTML 文本输出 |

---

## 本地书仓（仅本机访问）

> 这些接口在 `LocalBookController`（非 `/api/{v}` 版本）中，且要求请求来源 `127.0.0.1`。

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/searchlocalbook` | `path: String?`, `key: String?`（`ctx` 由框架注入） | `JsonResponse`（Data: 本地书籍列表） |
| `/searchlocalbook2` | `path: String?`, `key: String?`, `page: Int?`（`ctx` 由框架注入） | `JsonResponse`（Data: 本地书籍列表） |
| `/getlocalbookinfo` | `path: String?`（`ctx` 由框架注入） | `JsonResponse`（Data: 书籍信息） |
| `/getlocalbookinfoChapterList` | `path: String?`（`ctx` 由框架注入） | `JsonResponse`（Data: 章节列表） |
| `/getlocalbookinfoContent` | `path: String?`, `index: Int?`（`ctx` 由框架注入） | `JsonResponse`（Data: 章节内容） |

---

## 管理后台 API（`/admin`）

### 后台页面路由

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/admin` | 无 | HTML 页面 |
| `/admin/adduser` | 无 | HTML 页面 |
| `/admin/addcookie` | 无 | HTML 页面 |
| `/admin/login` | 无 | HTML 页面 |
| `/admin/?` | 无 | HTML 页面 |
| `/admin/book` | 无 | HTML 页面 |
| `/admin/rss` | 无 | HTML 页面 |
| `/admin/code` | 无 | HTML 页面 |

### 登录与用户管理

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/admin/login` | `username: String?`, `password: String?`（`ctx` 由框架注入） | `JsonResponse`/HTML 重定向 |
| `/admin/logout` | 无（`ctx` 由框架注入） | 退出结果 |
| `/admin/adduser` | `user: Users` | `JsonResponse`（Data: 新用户） |
| `/admin/getuser` | `id: String?` | `JsonResponse`（Data: 用户信息） |
| `/admin/seachusers` | `where: String?`, `order: String?`, `page: Int (default 1)`, `limit: Int (default 20)` | `JsonResponse`（Data: 用户分页） |
| `/admin/deluser` | `id: String?` | `JsonResponse`（Data: 无） |
| `/admin/delusers` | `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |

### 书源管理

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/admin/seachbookSource` | `where: String?`, `order: String?`, `page: Int (default 1)`, `limit: Int (default 20)` | `JsonResponse`（Data: 书源分页） |
| `/admin/uploadSource` | `file: UploadedFile` | `JsonResponse`（Data: 导入结果） |
| `/admin/delbookSource` | `id: String?` | `JsonResponse`（Data: 无） |
| `/admin/delbookSources` | `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/admin/stopbookSource` | `id: String?`, `st: String?` | `JsonResponse`（Data: 无） |
| `/admin/stopbookSourceExplore` | `id: String?`, `st: String?` | `JsonResponse`（Data: 无） |
| `/admin/topSource` | `id: String?` | `JsonResponse`（Data: 无） |

### RSS 书源管理

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/admin/seachrssSource` | `where: String?`, `order: String?`, `page: Int (default 1)`, `limit: Int (default 20)` | `JsonResponse`（Data: RSS 源分页） |
| `/admin/delRssSource` | `id: String?` | `JsonResponse`（Data: 无） |
| `/admin/delRssSources` | `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |
| `/admin/stopRssSource` | `id: String?`, `st: String?` | `JsonResponse`（Data: 无） |
| `/admin/topRssSource` | `id: String?` | `JsonResponse`（Data: 无） |
| `/admin/uploadRssSource` | `file: UploadedFile` | `JsonResponse`（Data: 导入结果） |

### 邀请码/注册码管理

| 路径 | 参数 | 返回 |
| --- | --- | --- |
| `/admin/seachcode` | `where: String?`, `order: String?`, `page: Int (default 1)`, `limit: Int (default 20)` | `JsonResponse`（Data: Code 分页） |
| `/admin/addcode` | `num: Int (default 1)` | `JsonResponse`（Data: 新 code 列表） |
| `/admin/delcode` | `code: String?` | `JsonResponse`（Data: 无） |
| `/admin/delcodes` | `ids: List<String>?` (`@Body`) | `JsonResponse`（Data: 无） |

---

## 源码参考

- `src/main/kotlin/web/controller/api/*.kt`
- `src/main/kotlin/web/controller/*.kt`
- `src/main/kotlin/web/controller/admin/*.kt`
