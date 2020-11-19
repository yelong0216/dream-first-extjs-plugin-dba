package dream.first.extjs.plugin.dba.servlet;

import org.yelong.support.servlet.resource.ResourceServlet;
import org.yelong.support.servlet.resource.response.ResourceResponseHandler;
import org.yelong.support.spring.boot.servlet.resource.ResourceServletRegistrationBean;

import dream.first.extjs.plugin.dba.servlet.DBAResourceServletRegistrationBean.DBAResourceServlet;

public class DBAResourceServletRegistrationBean extends ResourceServletRegistrationBean<DBAResourceServlet> {

	public static final String urlPrefix = "/resources/extjs/plugin/dba";

	public static final String resourceRootPath = "/dream/first/extjs/plugin/resources/dba/publics/extjs/plugin/dba";

	public DBAResourceServletRegistrationBean() {
		this(urlPrefix);
	}

	public DBAResourceServletRegistrationBean(String urlPrefix) {
		this(urlPrefix, resourceRootPath);
	}

	public DBAResourceServletRegistrationBean(String urlPrefix, String resourceRootPath) {
		super(urlPrefix, resourceRootPath, new DBAResourceServlet());
	}

	public static final class DBAResourceServlet extends ResourceServlet {

		private static final long serialVersionUID = -454745587938652439L;

		public DBAResourceServlet() {
		}
		
		public DBAResourceServlet(ResourceResponseHandler resourceResponseHandler) {
			super(resourceResponseHandler);
		}
		
	}

}
