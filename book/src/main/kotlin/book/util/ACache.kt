package book.util



import book.appCtx
import org.slf4j.LoggerFactory
import java.io.*
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.min

private val logger = LoggerFactory.getLogger(ACache::class.java)



/**
 * 本地缓存
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class ACache private constructor(cacheDir: File, max_size: Long, max_count: Int) {

    companion object {
        const val TIME_HOUR = 60 * 60
        const val TIME_DAY = TIME_HOUR * 24
        private const val MAX_SIZE = 1000 * 1000 * 50 // 50 mb
        private const val MAX_COUNT = Integer.MAX_VALUE // 不限制存放数据的数量
        private val mInstanceMap = HashMap<String, ACache>()

        @JvmOverloads
        fun get(
            cacheName: String = "ACache",
            maxSize: Long = MAX_SIZE.toLong(),
            maxCount: Int = MAX_COUNT
        ): ACache {
            val f = File(appCtx.cacheDir, cacheName)
            return get(f, maxSize, maxCount)
        }

        @JvmOverloads
        fun get(
            cacheDir: File,
            maxSize: Long = MAX_SIZE.toLong(),
            maxCount: Int = MAX_COUNT
        ): ACache {
            synchronized(this) {
                var manager = mInstanceMap[cacheDir.absoluteFile.toString()]
                if (manager == null) {
                    manager = ACache(cacheDir, maxSize, maxCount)
                    mInstanceMap[cacheDir.absolutePath] = manager
                }
                return manager
            }
        }
    }

    private var mCache: ACacheManager? = null

    init {
        try {
            if (!cacheDir.exists() && !cacheDir.mkdirs()) {
                logger.info("ACache can't make dirs in %s" + cacheDir.absolutePath)
            }
            mCache = ACacheManager(cacheDir, max_size, max_count)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // =======================================
    // ============ String数据 读写 ==============
    // =======================================

    /**
     * 保存 String数据 到 缓存中
     *
     * @param key   保存的key
     * @param value 保存的String数据
     */
    fun put(key: String, value: String) {
        mCache?.let { mCache ->
            try {
                val file = mCache.newFile(key)
                file.writeText(value)
                mCache.put(file)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 保存 String数据 到 缓存中
     *
     * @param key      保存的key
     * @param value    保存的String数据
     * @param saveTime 保存的时间，单位：秒
     */
    fun put(key: String, value: String, saveTime: Int) {
        put(key, Utils.newStringWithDateInfo(saveTime, value))
    }

    /**
     * 读取 String数据
     *
     * @return String 数据
     */
    fun getAsString(key: String): String? {
        mCache?.let { mCache ->
            val file = mCache[key]
            if (!file.exists())
                return null
            var removeFile = false
            try {
                val text = file.readText()
                if (!Utils.isDue(text)) {
                    return Utils.clearDateInfo(text)
                } else {
                    removeFile = true
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (removeFile)
                    remove(key)
            }
        }
        return null
    }

    fun getByHashCode(hashCode: String): String? {
        mCache?.let { mCache ->
            val file =  mCache.newFileFromHashCode(hashCode)

            if (!file.exists())
                return null
            var removeFile = false
            try {
                val text = file.readText()
                if (!Utils.isDue(text)) {
                    return Utils.clearDateInfo(text)
                } else {
                    removeFile = true
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (removeFile)
                    file.delete()
            }
        }
        return null
    }

    // =======================================
    // ========== JSONObject 数据 读写 =========
    // =======================================

    // /**
    //  * 保存 JSONObject数据 到 缓存中
    //  *
    //  * @param key   保存的key
    //  * @param value 保存的JSON数据
    //  */
    // fun put(key: String, value: JSONObject) {
    //     put(key, value.toString())
    // }

    // /**
    //  * 保存 JSONObject数据 到 缓存中
    //  *
    //  * @param key      保存的key
    //  * @param value    保存的JSONObject数据
    //  * @param saveTime 保存的时间，单位：秒
    //  */
    // fun put(key: String, value: JSONObject, saveTime: Int) {
    //     put(key, value.toString(), saveTime)
    // }

    // /**
    //  * 读取JSONObject数据
    //  *
    //  * @return JSONObject数据
    //  */
    // fun getAsJSONObject(key: String): JSONObject? {
    //     val json = getAsString(key) ?: return null
    //     return try {
    //         JSONObject(json)
    //     } catch (e: Exception) {
    //         null
    //     }
    // }

    // // =======================================
    // // ============ JSONArray 数据 读写 =============
    // // =======================================

    // /**
    //  * 保存 JSONArray数据 到 缓存中
    //  *
    //  * @param key   保存的key
    //  * @param value 保存的JSONArray数据
    //  */
    // fun put(key: String, value: JSONArray) {
    //     put(key, value.toString())
    // }

    // /**
    //  * 保存 JSONArray数据 到 缓存中
    //  *
    //  * @param key      保存的key
    //  * @param value    保存的JSONArray数据
    //  * @param saveTime 保存的时间，单位：秒
    //  */
    // fun put(key: String, value: JSONArray, saveTime: Int) {
    //     put(key, value.toString(), saveTime)
    // }

    // /**
    //  * 读取JSONArray数据
    //  *
    //  * @return JSONArray数据
    //  */
    // fun getAsJSONArray(key: String): JSONArray? {
    //     val json = getAsString(key)
    //     return try {
    //         JSONArray(json)
    //     } catch (e: Exception) {
    //         null
    //     }

    // }

    // =======================================
    // ============== byte 数据 读写 =============
    // =======================================

    /**
     * 保存 byte数据 到 缓存中
     *
     * @param key   保存的key
     * @param value 保存的数据
     */
    fun put(key: String, value: ByteArray) {
        mCache?.let { mCache ->
            val file = mCache.newFile(key)
            file.writeBytes(value)
            mCache.put(file)
        }
    }

    /**
     * 保存 byte数据 到 缓存中
     *
     * @param key      保存的key
     * @param value    保存的数据
     * @param saveTime 保存的时间，单位：秒
     */
    fun put(key: String, value: ByteArray, saveTime: Int) {
        put(key, Utils.newByteArrayWithDateInfo(saveTime, value))
    }

    /**
     * 获取 byte 数据
     *
     * @return byte 数据
     */
    fun getAsBinary(key: String): ByteArray? {
        mCache?.let { mCache ->
            var removeFile = false
            try {
                val file = mCache[key]
                if (!file.exists())
                    return null

                val byteArray = file.readBytes()
                return if (!Utils.isDue(byteArray)) {
                    Utils.clearDateInfo(byteArray)
                } else {
                    removeFile = true
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (removeFile)
                    remove(key)
            }
        }
        return null
    }

    /**
     * 保存 Serializable数据到 缓存中
     *
     * @param key      保存的key
     * @param value    保存的value
     * @param saveTime 保存的时间，单位：秒
     */
    @JvmOverloads
    fun put(key: String, value: Serializable, saveTime: Int = -1) {
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            ObjectOutputStream(byteArrayOutputStream).use { oos ->
                oos.writeObject(value)
                val data = byteArrayOutputStream.toByteArray()
                if (saveTime != -1) {
                    put(key, data, saveTime)
                } else {
                    put(key, data)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 读取 Serializable数据
     *
     * @return Serializable 数据
     */
    fun getAsObject(key: String): Any? {
        val data = getAsBinary(key)
        if (data != null) {
            var bis: ByteArrayInputStream? = null
            var ois: ObjectInputStream? = null
            try {
                bis = ByteArrayInputStream(data)
                ois = ObjectInputStream(bis)
                return ois.readObject()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    bis?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                try {
                    ois?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return null

    }

    // =======================================
    // ============== bitmap 数据 读写 =============
    // =======================================

    // /**
    //  * 保存 bitmap 到 缓存中
    //  *
    //  * @param key   保存的key
    //  * @param value 保存的bitmap数据
    //  */
    // fun put(key: String, value: Bitmap) {
    //     put(key, Utils.bitmap2Bytes(value))
    // }

    // /**
    //  * 保存 bitmap 到 缓存中
    //  *
    //  * @param key      保存的key
    //  * @param value    保存的 bitmap 数据
    //  * @param saveTime 保存的时间，单位：秒
    //  */
    // fun put(key: String, value: Bitmap, saveTime: Int) {
    //     put(key, Utils.bitmap2Bytes(value), saveTime)
    // }

    /**
     * 读取 bitmap 数据
     *
     * @return bitmap 数据
     */
    // fun getAsBitmap(key: String): Bitmap? {
    //     return if (getAsBinary(key) == null) {
    //         null
    //     } else Utils.bytes2Bitmap(getAsBinary(key)!!)
    // }

    // =======================================
    // ============= drawable 数据 读写 =============
    // =======================================

    // /**
    //  * 保存 drawable 到 缓存中
    //  *
    //  * @param key   保存的key
    //  * @param value 保存的drawable数据
    //  */
    // fun put(key: String, value: Drawable) {
    //     put(key, Utils.drawable2Bitmap(value))
    // }

    // /**
    //  * 保存 drawable 到 缓存中
    //  *
    //  * @param key      保存的key
    //  * @param value    保存的 drawable 数据
    //  * @param saveTime 保存的时间，单位：秒
    //  */
    // fun put(key: String, value: Drawable, saveTime: Int) {
    //     put(key, Utils.drawable2Bitmap(value), saveTime)
    // }

    /**
     * 读取 Drawable 数据
     *
     * @return Drawable 数据
     */
    // fun getAsDrawable(key: String): Drawable? {
    //     return if (getAsBinary(key) == null) {
    //         null
    //     } else Utils.bitmap2Drawable(
    //         Utils.bytes2Bitmap(
    //             getAsBinary(key)!!
    //         )
    //     )
    // }

    /**
     * 获取缓存文件
     *
     * @return value 缓存的文件
     */
    fun file(key: String): File? {
        mCache?.let { mCache ->
            try {
                val f = mCache.newFile(key)
                if (f.exists()) {
                    return f
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * 移除某个key
     *
     * @return 是否移除成功
     */
    fun remove(key: String): Boolean {
        return mCache?.remove(key) == true
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        mCache?.clear()
    }

    /**
     * @author 杨福海（michael） www.yangfuhai.com
     * @version 1.0
     * title 时间计算工具类
     */
    private object Utils {

        private const val mSeparator = ' '

        /**
         * 判断缓存的String数据是否到期
         *
         * @return true：到期了 false：还没有到期
         */
        fun isDue(str: String): Boolean {
            return isDue(str.toByteArray())
        }

        /**
         * 判断缓存的byte数据是否到期
         *
         * @return true：到期了 false：还没有到期
         */
        fun isDue(data: ByteArray): Boolean {
            try {
                val text = getDateInfoFromDate(data)
                if (text != null && text.size == 2) {
                    var saveTimeStr = text[0]
                    while (saveTimeStr.startsWith("0")) {
                        saveTimeStr = saveTimeStr
                            .substring(1)
                    }
                    val saveTime = java.lang.Long.valueOf(saveTimeStr)
                    val deleteAfter = java.lang.Long.valueOf(text[1])
                    if (System.currentTimeMillis() > saveTime + deleteAfter * 1000) {
                        return true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false
        }

        fun newStringWithDateInfo(second: Int, strInfo: String): String {
            return createDateInfo(second) + strInfo
        }

        fun newByteArrayWithDateInfo(second: Int, data2: ByteArray): ByteArray {
            val data1 = createDateInfo(second).toByteArray()
            val retData = ByteArray(data1.size + data2.size)
            System.arraycopy(data1, 0, retData, 0, data1.size)
            System.arraycopy(data2, 0, retData, data1.size, data2.size)
            return retData
        }

        fun clearDateInfo(strInfo: String?): String? {
            strInfo?.let {
                if (hasDateInfo(strInfo.toByteArray())) {
                    return strInfo.substring(strInfo.indexOf(mSeparator) + 1)
                }
            }
            return strInfo
        }

        fun clearDateInfo(data: ByteArray): ByteArray {
            return if (hasDateInfo(data)) {
                copyOfRange(
                    data, indexOf(data, mSeparator) + 1,
                    data.size
                )
            } else data
        }

        fun hasDateInfo(data: ByteArray?): Boolean {
            return (data != null && data.size > 15 && data[13] == '-'.code.toByte()
                    && indexOf(data, mSeparator) > 14)
        }

        fun getDateInfoFromDate(data: ByteArray): Array<String>? {
            if (hasDateInfo(data)) {
                val saveDate = String(copyOfRange(data, 0, 13))
                val deleteAfter = String(
                    copyOfRange(
                        data, 14,
                        indexOf(data, mSeparator)
                    )
                )
                return arrayOf(saveDate, deleteAfter)
            }
            return null
        }

        @Suppress("SameParameterValue")
        private fun indexOf(data: ByteArray, c: Char): Int {
            for (i in data.indices) {
                if (data[i] == c.code.toByte()) {
                    return i
                }
            }
            return -1
        }

        private fun copyOfRange(original: ByteArray, from: Int, to: Int): ByteArray {
            val newLength = to - from
            require(newLength >= 0) { "$from > $to" }
            val copy = ByteArray(newLength)
            System.arraycopy(
                original, from, copy, 0,
                min(original.size - from, newLength)
            )
            return copy
        }

        private fun createDateInfo(second: Int): String {
            val currentTime = StringBuilder(System.currentTimeMillis().toString() + "")
            while (currentTime.length < 13) {
                currentTime.insert(0, "0")
            }
            return "$currentTime-$second$mSeparator"
        }

        // /*
        //  * Bitmap → byte[]
        //  */
        // fun bitmap2Bytes(bm: Bitmap): ByteArray {
        //     val byteArrayOutputStream = ByteArrayOutputStream()
        //     bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        //     return byteArrayOutputStream.toByteArray()
        // }

        // /*
        //  * byte[] → Bitmap
        //  */
        // fun bytes2Bitmap(b: ByteArray): Bitmap? {
        //     return if (b.isEmpty()) {
        //         null
        //     } else BitmapFactory.decodeByteArray(b, 0, b.size)
        // }

        // /*
        //  * Drawable → Bitmap
        //  */
        // fun drawable2Bitmap(drawable: Drawable): Bitmap {
        //     // 取 drawable 的长宽
        //     val w = drawable.intrinsicWidth
        //     val h = drawable.intrinsicHeight
        //     // 取 drawable 的颜色格式
        //     @Suppress("DEPRECATION")
        //     val config = if (drawable.opacity != PixelFormat.OPAQUE)
        //         Bitmap.Config.ARGB_8888
        //     else
        //         Bitmap.Config.RGB_565
        //     // 建立对应 bitmap
        //     val bitmap = Bitmap.createBitmap(w, h, config)
        //     // 建立对应 bitmap 的画布
        //     val canvas = Canvas(bitmap)
        //     drawable.setBounds(0, 0, w, h)
        //     // 把 drawable 内容画到画布中
        //     drawable.draw(canvas)
        //     return bitmap
        // }

        // /*
        //  * Bitmap → Drawable
        //  */
        // fun bitmap2Drawable(bm: Bitmap?): Drawable? {
        //     return if (bm == null) {
        //         null
        //     } else BitmapDrawable(appCtx.resources, bm)
        // }
    }

    /**
     * @author 杨福海（michael） www.yangfuhai.com
     * @version 1.0
     * title 缓存管理器
     */
    open inner class ACacheManager(
        private var cacheDir: File,
        private val sizeLimit: Long,
        private val countLimit: Int
    ) {
        private val cacheSize: AtomicLong = AtomicLong()
        private val cacheCount: AtomicInteger = AtomicInteger()
        private val lastUsageDates = Collections
            .synchronizedMap(HashMap<File, Long>())

        init {
            calculateCacheSizeAndCacheCount()
        }

        /**
         * 计算 cacheSize和cacheCount
         */
        private fun calculateCacheSizeAndCacheCount() {
            Thread {

                try {
                    var size = 0
                    var count = 0
                    val cachedFiles = cacheDir.listFiles()
                    if (cachedFiles != null) {
                        for (cachedFile in cachedFiles) {
                            size += calculateSize(cachedFile).toInt()
                            count += 1
                            lastUsageDates[cachedFile] = cachedFile.lastModified()
                        }
                        cacheSize.set(size.toLong())
                        cacheCount.set(count)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }.start()
        }

        fun put(file: File) {

            try {
                var curCacheCount = cacheCount.get()
                while (curCacheCount + 1 > countLimit) {
                    val freedSize = removeNext()
                    cacheSize.addAndGet(-freedSize)

                    curCacheCount = cacheCount.addAndGet(-1)
                }
                cacheCount.addAndGet(1)

                val valueSize = calculateSize(file)
                var curCacheSize = cacheSize.get()
                while (curCacheSize + valueSize > sizeLimit) {
                    val freedSize = removeNext()
                    curCacheSize = cacheSize.addAndGet(-freedSize)
                }
                cacheSize.addAndGet(valueSize)

                val currentTime = System.currentTimeMillis()
                file.setLastModified(currentTime)
                lastUsageDates[file] = currentTime
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        operator fun get(key: String): File {
            val file = newFile(key)
            val currentTime = System.currentTimeMillis()
            file.setLastModified(currentTime)
            lastUsageDates[file] = currentTime

            return file
        }

        fun newFile(key: String): File {
            return File(cacheDir, key.hashCode().toString() + "")
        }

        fun newFileFromHashCode(hashCode: String): File {
            return File(cacheDir, hashCode)
        }

        fun remove(key: String): Boolean {
            val image = get(key)
            return image.delete()
        }

        fun clear() {
            try {
                lastUsageDates.clear()
                cacheSize.set(0)
                val files = cacheDir.listFiles()
                if (files != null) {
                    for (f in files) {
                        f.delete()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /**
         * 移除旧的文件
         */
        private fun removeNext(): Long {
            try {
                if (lastUsageDates.isEmpty()) {
                    return 0
                }

                var oldestUsage: Long? = null
                var mostLongUsedFile: File? = null
                val entries = lastUsageDates.entries
                synchronized(lastUsageDates) {
                    for ((key, lastValueUsage) in entries) {
                        if (mostLongUsedFile == null) {
                            mostLongUsedFile = key
                            oldestUsage = lastValueUsage
                        } else {
                            if (lastValueUsage < oldestUsage!!) {
                                oldestUsage = lastValueUsage
                                mostLongUsedFile = key
                            }
                        }
                    }
                }

                var fileSize: Long = 0
                if (mostLongUsedFile != null) {
                    fileSize = calculateSize(mostLongUsedFile!!)
                    if (mostLongUsedFile!!.delete()) {
                        lastUsageDates.remove(mostLongUsedFile)
                    }
                }
                return fileSize
            } catch (e: Exception) {
                e.printStackTrace()
                return 0
            }

        }

        private fun calculateSize(file: File): Long {
            return file.length()
        }
    }

}