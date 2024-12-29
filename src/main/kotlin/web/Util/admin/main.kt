package web.Util.admin

import jakarta.servlet.http.HttpSession

fun loginok(username:String, session: HttpSession){
    session.setAttribute("username", username)
}

fun logout(session: HttpSession) {
    session.removeAttribute("username")
}

fun islogin(session: HttpSession) :Boolean =run{
    if(session.getAttribute("username") != null && session.getAttribute("username").toString().isNotBlank()){
        true
    }else{
        false
    }
}

