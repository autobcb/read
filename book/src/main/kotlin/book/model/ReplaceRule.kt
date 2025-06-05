package book.model

import book.util.TextUtils
import book.webBook.exception.NoStackTraceException
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

data class ReplaceRule(
    var id: Long = System.currentTimeMillis(),
    //名称
    var name: String = "",
    //分组
    var group: String? = null,
    //替换内容
    var pattern: String = "",
    //替换为
    var replacement: String = "",
    //作用范围
    var scope: String? = null,
    //作用于标题
    var scopeTitle: Boolean = false,
    //作用于正文
    var scopeContent: Boolean = true,
    //排除范围
    var excludeScope: String? = null,
    //是否启用
    var isEnabled: Boolean = true,
    //是否正则
    var isRegex: Boolean = true,
    //超时时间
    var timeoutMillisecond: Long = 3000L,
    //排序
    var order: Int = Int.MIN_VALUE,
)  {

    override fun equals(other: Any?): Boolean {
        if (other is ReplaceRule) {
            return other.id == id
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    @delegate:Transient
    val regex: Regex by lazy {
        pattern.toRegex()
    }

    fun getDisplayNameGroup(): String {
        return if (group.isNullOrBlank()) {
            name
        } else {
            String.format("%s (%s)", name, group)
        }
    }

    fun isValid(): Boolean {
        if (TextUtils.isEmpty(pattern)) {
            return false
        }
        //判断正则表达式是否正确
        if (isRegex) {
            try {
                Pattern.compile(pattern)
            } catch (ex: PatternSyntaxException) {
                 println("正则语法错误或不支持：${ex.localizedMessage}")
                return false
            }
            // Pattern.compile测试通过，但是部分情况下会替换超时，报错，一般发生在修改表达式时漏删了
            if (pattern.endsWith('|') && !pattern.endsWith("\\|")) {
                return false
            }
        }
        return true
    }

    @Throws(NoStackTraceException::class)
    fun checkValid() {
        if (!isValid()) {
            throw NoStackTraceException("替换规则为空或者不满足正则表达式要求")
        }
    }

    fun getValidTimeoutMillisecond(): Long {
        if (timeoutMillisecond <= 0) {
            return 3000L
        }
        return timeoutMillisecond
    }
}