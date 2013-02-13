package org.kevoree.web

import java.util.HashMap
import org.kevoree.library.javase.webserver.URLHandlerScala
import org.kevoree.library.javase.webserver.AbstractPage
import org.kevoree.library.javase.webserver.KevoreeHttpRequest
import org.kevoree.library.javase.webserver.KevoreeHttpResponse
import scala.Some
import scala.None
import java.io.InputStream
import java.io.File
import java.io.FileInputStream
import java.io.BufferedReader
import java.io.InputStreamReader

/*
* Author : Gregory Nain (developer.name@uni.lu)
* Date : 13/02/13
* (c) 2013 University of Luxembourg – Interdisciplinary Centre for Security Reliability and Trust (SnT)
* All rights reserved
*/

class PageRenderer(val devmod : Boolean, val folder: java.io.File?) {

    val handler = URLHandlerScala()

    fun checkForTemplateRequest(index: String, origin: AbstractPage, request: KevoreeHttpRequest, response: KevoreeHttpResponse) : Boolean {
        if(origin.getDictionary() == null) {
            throw NullPointerException("UrlPattern property of component "+origin.getName()+" can not be null")
        } else {
            val urlPattern = origin.getDictionary()!!.get("urlpattern").toString()
            val lastParam = handler.getLastParam(request.getUrl(), urlPattern)
            if(lastParam != null  && lastParam.isDefined()) {
                if (lastParam.get() == "" || lastParam.get() == null || lastParam.get() == "/") {
                    response.setContent(krender(index, "/", HashMap<String, String>(), urlPattern))
                    return true
                } else {
                    return false
                }
            } else {
                return false
            }
        }
    }


    fun krender(name: String, currentURL: String, vars: Map<String, String>, pattern: String): String {
        val sb = StringBuffer()
        sb.append(kheader())
        sb.append(MenuRenderer.getMenuHtml())

        // sb.append("<div class=\"hero-unit\">\n    <div class=\"row\">\n        <div class=\"span5\">\n            <p><img src=\"img/kevoree-logo.png\"/></p>\n        </div>\n        <div class=\"span5\">\n            <p>Kevoree project aims at enabling distributed reconfigurable software development. Build around a component model, Kevoree leverage model@runtime approach to offer tools to build, adapt and synchronize distributed systems.\n                Extensible, this project already offer runtime for Standard Java Virtual Machine, Android, Arduino but also for virtualization management such as VirtualBox.\n                In short Kevoree helping you to develop your adaptable software from Cloud stack to embedded devices !\n            </p>\n        </div>\n    </div>\n</div>")
        sb.append("<div class=\"wrapper\">")
        sb.append("<div id=\"main\" class=\"container clear-top\">")
        sb.append(replaceVariable(renderHtml(name), vars))
        sb.append("</div>")
        sb.append("<div class=\"push\"><!--//--></div>")
        sb.append("</div>")
        sb.append(footerScript())
        sb.append("<div class=\"footer\" /><footer>\n<h3>&copy; Kevoree.org 2013</h3>\n</footer>\n\n</div>")
        sb.append("</body></html>")

        var patternCleaned = pattern
        if (patternCleaned.endsWith("**")) {
            patternCleaned = patternCleaned.replace("**", "")
        }
        if (!patternCleaned.endsWith("/")) {
            patternCleaned = patternCleaned + "/"
        }
        return sb.toString().replace("{urlpattern}", patternCleaned)
    }

    fun renderContent(content: String, pattern: String): String {
        val sb = StringBuffer()
        sb.append(kheader())
        sb.append(MenuRenderer.getMenuHtml())

        // sb.append("<div class=\"hero-unit\">\n    <div class=\"row\">\n        <div class=\"span5\">\n            <p><img src=\"img/kevoree-logo.png\"/></p>\n        </div>\n        <div class=\"span5\">\n            <p>Kevoree project aims at enabling distributed reconfigurable software development. Build around a component model, Kevoree leverage model@runtime approach to offer tools to build, adapt and synchronize distributed systems.\n                Extensible, this project already offer runtime for Standard Java Virtual Machine, Android, Arduino but also for virtualization management such as VirtualBox.\n                In short Kevoree helping you to develop your adaptable software from Cloud stack to embedded devices !\n            </p>\n        </div>\n    </div>\n</div>")
        sb.append("<div class=\"wrapper\">")
        sb.append("<div id=\"main\" class=\"container clear-top\">")
        sb.append(content)
        sb.append("</div>")
        sb.append("<div class=\"push\"><!--//--></div>")
        sb.append("</div>")
        sb.append(footerScript())
        sb.append("<div class=\"footer\" /><footer>\n<h3>&copy; Kevoree.org 2013</h3>\n</footer>\n\n</div>")
        sb.append("</body></html>")

        var patternCleaned = pattern
        if (patternCleaned.endsWith("**")) {
            patternCleaned = patternCleaned.replace("**", "")
        }
        if (!patternCleaned.endsWith("/")) {
            patternCleaned = patternCleaned + "/"
        }
        return sb.toString().replace("{urlpattern}", patternCleaned)
    }

    fun renderHtml(name: String) : String  {
        //Source.fromFile(new File(getClass.getClassLoader.getResource("templates/../").getPath+"../../src/main/resources/templates/html/" + name)).getLines().mkString("\n")

        var st: InputStream?
        if (devmod) {
            st = FileInputStream(File(folder!!.getAbsolutePath() + java.io.File.separator + "templates" + java.io.File.separator + "html" + java.io.File.separator + name))
        } else {
            st = this.javaClass.getClassLoader()!!.getResourceAsStream("templates/html/" + name)
        }

        if (st != null) {
            val sb = StringBuffer()
            var br = BufferedReader(InputStreamReader(st!!))
            var line = br.readLine()
            while (line != null) {
                sb.append(line)
                line = br.readLine()
            }
            return sb.toString()
        } else {
            return "not found"
        }

    }

    fun kheader() : String {
        return "<!DOCTYPE html>\n<html lang=\"en\">" +
        "<head><meta charset=\"utf-8\">" +
        "<title>Kevoree Store</title>" +
        "<meta name=\"description\" content=\"Kevoree Store : Online sofware components store\">" +
        "<meta name=\"author\" content=\"Francois Fouquet\">" +
        "<meta name=\"author\" content=\"Gregory Nain\">" +
        "<!-- Le HTML5 shim, for IE6-8 support of HTML elements -->\n    <!--[if lt IE 9]>\n    <script src=\"//html5shim.googlecode.com/svn/trunk/html5.js\"></script>\n    <![endif]-->" +
        "<br /><script type=\"text/javascript\">\n\n  var _gaq = _gaq || [];\n  _gaq.push(['_setAccount', 'UA-23280515-1']);\n  _gaq.push(['_setDomainName', 'kevoree.org']);\n  _gaq.push(['_trackPageview']);\n\n  (function() {\n    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\n    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';\n    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);\n  })();\n\n</script>" +
        "<link href=\"{urlpattern}css/bootstrap.min.css\" rel=\"stylesheet\">" +
        "<link href=\"{urlpattern}css/bootstrap-responsive.min.css\" rel=\"stylesheet\">" +
        "<link href=\"{urlpattern}css/kevoree.css\" rel=\"stylesheet\">" +
        "<link href=\"{urlpattern}js/google-code-prettify/prettify.css\" type=\"text/css\" rel=\"stylesheet\"/>\n" +
        "<link href=\"{urlpattern}css/refineslide.css\" type=\"text/css\" rel=\"stylesheet\"/>\n" +
        "<script type=\"text/javascript\" src=\"{urlpattern}js/google-code-prettify/prettify.js\"></script>\n" +
        "<script type=\"text/javascript\" src=\"{urlpattern}js/jquery.min.js\"></script>\n" +
        "<script type=\"text/javascript\" src=\"{urlpattern}js/bootstrap.min.js\"></script>\n" +
        "<script type=\"text/javascript\" src=\"{urlpattern}js/jquery.refineslide.min.js\"></script>" +
        "</head>" +
        "<body onload=\"prettyPrint()\">\n"
    }


    fun replaceVariable(html: String, vars: Map<String, String>) : String {
        var content = html
        vars.entrySet().forEach { entry -> content = content.replace("{" + entry.key + "}", entry.value) }

        return content
    }


    fun footerScript() : String {
        return ""
    }

}






