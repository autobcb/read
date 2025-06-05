package web.config

import org.noear.solon.annotation.Bean
import org.noear.solon.annotation.Configuration
import org.noear.solon.annotation.Inject
import org.noear.solon.data.cache.CacheService
import org.noear.solon.data.cache.CacheServiceSupplier


@Configuration
class CacheConfig {

    @Bean
    fun cahce(@Inject("\${cache}") supplier: CacheServiceSupplier): CacheService {
        return supplier.get()
    }

}