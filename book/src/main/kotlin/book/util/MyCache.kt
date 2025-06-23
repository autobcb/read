package book.util

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MyCache(val num:Int) {
    private val  keys:MutableList<String> = mutableListOf()
    private val  map:HashMap<String,Any> = hashMapOf()
    private val  mutex = Mutex()

    fun clear()= runBlocking{
        mutex.withLock {
            keys.clear()
            map.clear()
        }
    }

    fun add(key:String, value:Any)= runBlocking{
        mutex.withLock {
            if(keys.contains(key)){
                keys.remove(key)
            }
            map[key] = value
            keys.add(key)
            runCatching {
                while(keys.size> num){
                    val k=keys[0]
                    keys.remove(k)
                    map.remove(k)
                }
            }
        }
    }

    fun contains(key:String)= runBlocking{
        var z=false;
        mutex.withLock {
            z=keys.contains(key)
        }
        z
    }

    fun remove(key:String)= runBlocking{
        mutex.withLock {
            map.remove(key)
            if(keys.contains(key)){
                keys.remove(key)
            }
        }
    }

    fun get(key:String):Any?{
        return map[key]
    }

}