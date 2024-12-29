package web.Configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import web.Interceptor.LoginInterceptor


@Configuration
@EnableWebMvc
class WebMvcConfiguration : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        // 静态资源访问路径
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/public/static") //内置静态文件
            .addResourceLocations("static") //外置静态文件
            .addResourceLocations("upload")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor( LoginInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin/login")
    }


}