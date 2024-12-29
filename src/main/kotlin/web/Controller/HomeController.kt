package web.Controller


import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import java.io.IOException

@Controller
class HomeController {

    @RequestMapping("/")
    @Throws(IOException::class)
    fun home(resp: HttpServletResponse) {
        resp.sendRedirect("/index.html")
    }

}