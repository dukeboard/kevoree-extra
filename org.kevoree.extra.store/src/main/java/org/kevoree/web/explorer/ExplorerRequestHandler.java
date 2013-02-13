package org.kevoree.web.explorer;/*
* Author : Gregory Nain (developer.name@uni.lu)
* Date : 13/02/13
* (c) 2013 University of Luxembourg – Interdisciplinary Centre for Security Reliability and Trust (SnT)
* All rights reserved
*/


import org.kevoree.*;
import org.kevoree.web.ModelHandler;

public class ExplorerRequestHandler {

    private ModelHandler modelHandler ;

    public ExplorerRequestHandler(ModelHandler modelHandler) {
        this.modelHandler = modelHandler;

    }


    public String createRootExplorationPage() {

        StringBuilder sb = new StringBuilder();
        sb.append("Explore the store<br/>");
        sb.append("<table class=\"table table-hover\">");
        sb.append("<thead>");
        sb.append("<tr>");
        sb.append("<th>Name</th>");
        sb.append("<th>Type</th>");
        sb.append("<th>Library</th>");
        sb.append("<th>Version</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");
        for(TypeDefinition td : modelHandler.getBaseModel().getTypeDefinitions()) {
            sb.append("<tr>");
            sb.append("<td>" + td.getName() + "</td>");
            if(td instanceof ComponentType) {
                sb.append("<td>Component</td>");
            } else if(td instanceof GroupType) {
                sb.append("<td>Group</td>");
            } else if(td instanceof ChannelType) {
                sb.append("<td>Channel</td>");
            } else if(td instanceof NodeType) {
                sb.append("<td>Node</td>");
            }

            for(TypeLibrary library :modelHandler.getBaseModel().getLibraries()) {
                if(library.getSubTypes().contains(td)) {
                    sb.append("<td>" + library.getName() + "</td>");
                    break;
                }
            }
            if(td.getDeployUnits().size() > 0) {
                sb.append("<td>" + td.getDeployUnits().get(0).getVersion() + "</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("</table>");
        return sb.toString();
    }
}
