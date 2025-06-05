package web


import org.noear.solon.Solon
import org.noear.solon.annotation.SolonMain
import org.noear.solon.scheduling.annotation.EnableScheduling
import org.noear.solon.web.cors.CrossFilter
import org.noear.solon.web.staticfiles.StaticMappings
import org.noear.solon.web.staticfiles.repository.FileStaticRepository


@SolonMain
@EnableScheduling
class App


fun main(args: Array<String>) {
    Solon.start(App::class.java, args) { app ->
        app.enableSessionState(true)
        app.enableWebSocket(true);
        app.filter(CrossFilter().pathPatterns("/assets/covers/**").allowedOrigins("*"))
        app.filter(CrossFilter().pathPatterns("/assets/codes/**").allowedOrigins("*"))
        StaticMappings.add("/assets/",  FileStaticRepository("storage/assets/"));
        app.get("/") { ctx -> ctx.forward("/index.html"); }
    }
}