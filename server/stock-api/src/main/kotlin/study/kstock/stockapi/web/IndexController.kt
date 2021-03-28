package study.kstock.stockapi.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import study.kstock.stockapi.config.auth.dto.SessionUser
import javax.servlet.http.HttpSession

@Controller
class IndexController(private var httpSession: HttpSession) {

    @GetMapping("/")
    fun index(model: Model): String {
        var user = httpSession.getAttribute("user")
        if (user != null) {
            user = user as SessionUser
            model.addAttribute("userName", user.name)
        }
        return "index"
    }
}