package org.kevoree.web

/*
* Author : Gregory Nain (developer.name@uni.lu)
* Date : 13/02/13
* (c) 2013 University of Luxembourg – Interdisciplinary Centre for Security Reliability and Trust (SnT)
* All rights reserved
*/

object MenuRenderer {

    fun getMenuHtml() : String {

        return "<div class=\"navbar navbar-inverse navbar-fixed-top\">" +
        "<div class=\"navbar-inner\">" +
        "<div class=\"container\">" +
        "<a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">" +
        "<span class=\"i-bar\">" +
        "<i class=\"icon-chevron-down icon-white\"></i>" +
        "</span>" +
        "</a>" +
        "<a class=\"brand\" href=\"{urlpattern}\">Kevoree Store</a>" +
        "<div class=\"nav-collapse\">" +
        "<ul class=\"nav\">" +
        "<li><a href=\"/explore\">Explore</a></li>" +
        "<li><a href=\"/browse\">Browse</a></li>" +
        "</ul>" +
        "<ul class=\"nav pull-right\">" +
        "<li>" +
        "<a href=\"/admin\">Admin</a>" +
        "</li>" +
        "</ul>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>"
    }

}




