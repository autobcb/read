package web.util.mail

import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object  Mail {

    private  fun getmailSession():Session{
        val conf=MailConf.get()
        val account = conf.account
        val password = conf.password

        val props = mapOf(
            "mail.smtp.auth" to "true",
            "mail.smtp.host" to conf.host,
            "mail.smtp.ssl.enable" to "true",
            "mail.smtp.ssl.protocols" to conf.protocols,
            "mail.smtp.port" to conf.port,
        )

        val properties = Properties().apply { putAll(props) }

        val authenticator = object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(account, password)
            }
        }
        val mailSession = Session.getInstance(properties, authenticator)
        return mailSession
    }



    fun sendCode(code:String,to:String){
        val conf=MailConf.get()
        val account = conf.account
        val personal = conf.personal
        val subject = conf.codesubject
        val text = conf.codetext.replaceFirst("\$code", code)
        val textMessage = MimeMessage(getmailSession()).apply {
            setFrom(InternetAddress(account, personal, "UTF-8"))
            setRecipient(Message.RecipientType.TO, InternetAddress(to))
            setSubject(subject)
            setText(text)
        }

        Transport.send(textMessage)
    }
}