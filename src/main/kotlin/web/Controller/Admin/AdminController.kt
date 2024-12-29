package web.Controller.Admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RequestMapping("/admin")
annotation class AdminController

@RestController
@RequestMapping("/admin")
annotation class AdminApiController