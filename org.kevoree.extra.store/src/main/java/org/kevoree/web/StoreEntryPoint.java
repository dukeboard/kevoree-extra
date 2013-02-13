package org.kevoree.web;


import org.kevoree.annotation.ComponentType;
import org.kevoree.annotation.DictionaryAttribute;
import org.kevoree.annotation.DictionaryType;
import org.kevoree.library.javase.webserver.FileServiceHelper;
import org.kevoree.library.javase.webserver.KevoreeHttpRequest;
import org.kevoree.library.javase.webserver.KevoreeHttpResponse;
import org.kevoree.library.javase.webserver.ParentAbstractPage;
import org.kevoree.web.explorer.ExplorerRequestHandler;

import java.io.File;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 27/03/12
 * Time: 11:12
 */

@ComponentType
//@Provides({@ProvidedPort(name = "gitnews",type = PortType.MESSAGE)})
@DictionaryType({@DictionaryAttribute(name = "webSocketLocation", defaultValue = "http://localhost:8092"),@DictionaryAttribute(name = "folder")})
public class StoreEntryPoint extends ParentAbstractPage {

    protected String basePage = "home.html";
    protected PageRenderer krenderer = null;
    protected ModelHandler modelHandler = new ModelHandler();
    protected ExplorerRequestHandler explorerRequestHandler = new ExplorerRequestHandler(modelHandler);

    @Override
    public void startPage () {

        File f1 = new File((String) super.getDictionary().get("folder"));
        krenderer = new PageRenderer(true, f1);
        super.startPage();

    }

    @Override
    public void updatePage () {
        super.updatePage();
    }

    @Override
    public void stopPage () {
        super.stopPage();
    }

    @Override
    public KevoreeHttpResponse process (KevoreeHttpRequest request, KevoreeHttpResponse response) {

        if (FileServiceHelper.checkStaticFile(basePage, this, request, response)) {
            if (request.getUrl().equals("/") || request.getUrl().endsWith(".html") || request.getUrl().endsWith(".css") || request.getUrl().endsWith(".jnlp")) {
                replaceGlobalVariables(request, response);
            }
            return response;
        }

        if (krenderer.checkForTemplateRequest(basePage, this, request, response)) {
            replaceGlobalVariables(request, response);
            return response;
        }

        System.out.println("LastParam:" + getLastParam(request.getUrl()));

        if (getLastParam(request.getUrl()).equals("/explore") || getLastParam(request.getUrl()).equals("explore") ) {
            String urlPattern = getDictionary().get("urlpattern").toString();
            response.setContent(krenderer.renderContent(explorerRequestHandler.createRootExplorationPage(), urlPattern));
            replaceGlobalVariables(request, response);
            return response;
        }

        response.setContent("Bad request from " + getName() + "@" + getNodeName());
        return response;
    }

    protected KevoreeHttpResponse replaceGlobalVariables (KevoreeHttpRequest request, KevoreeHttpResponse response) {
        String pattern = getDictionary().get("urlpattern").toString();
        if (pattern.endsWith("**")) {
            pattern = pattern.replace("**", "");
        }
        if (!pattern.endsWith("/")) {
            pattern = pattern + "/";
        }
        response.setContent(response.getContent().replace("{urlpattern}", pattern));
        String urlSite = request.getCompleteUrl().replace(request.getUrl(), "");
        response.setContent(response.getContent().replace("{urlsite}", urlSite));
        return response;
    }
}
