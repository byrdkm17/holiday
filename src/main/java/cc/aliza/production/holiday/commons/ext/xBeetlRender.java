package cc.aliza.production.holiday.commons.ext;


import com.jfinal.render.Render;
import com.jfinal.render.RenderException;
import org.apache.commons.lang.StringUtils;
import org.bee.tl.core.GroupTemplate;
import org.bee.tl.core.Template;
import org.bee.tl.ext.spring.SessionWrapper;
import org.bee.tl.ext.spring.WebVariable;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Enumeration;


/**
 * Created with IntelliJ IDEA.
 * User: Jing
 * Date: 13-10-19
 * Time: 下午11:20
 */
public class xBeetlRender extends Render {
    GroupTemplate gt = null;
    private transient static final String encoding = getEncoding();
    private transient static final String contentType = "text/html; charset=" + encoding;
    private transient static final String xmlContentType = "text/xml; charset=" + encoding;

    public xBeetlRender(GroupTemplate gt, String view) {
        this.gt = gt;
        this.view = view;
    }

    public void render() {
        Writer writer = null;
        OutputStream os = null;
        try {
            if (StringUtils.endsWith(view, ".xml")) {
                response.setContentType(xmlContentType);
            } else {
                response.setContentType(contentType);
            }
            Template template = gt.getFileTemplate(view);
            Enumeration enumeration = request.getAttributeNames();

            while (enumeration.hasMoreElements()) {
                String attrName = String.valueOf(enumeration.nextElement());
                if ("session".equals(attrName)) {
                    HttpSession session = (HttpSession) request.getAttribute(attrName);
                    template.setRawValue("session", new SessionWrapper(session));
                } else {
                    template.set(attrName, request.getAttribute(attrName));
                }
            }
            WebVariable webVariable = new WebVariable();
            webVariable.setRequest(request);
            webVariable.setResponse(response);
            webVariable.setSession(request.getSession());

            template.set("servlet", webVariable);
            template.set("request", request);
            template.set("ctxPath", request.getContextPath());

            StringBuffer url = request.getRequestURL();
            String domain = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
            template.set("domain", domain);

            if (gt.isDirectByteOutput()) {
                os = response.getOutputStream();
                template.getText(os);
            } else {
                writer = response.getWriter();
                template.getText(writer);
            }
        } catch (Exception e) {
            throw new RenderException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
