package dream.first.extjs.plugin.dba.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import dream.first.extjs.plugin.dba.controller.DatabaseController;
import dream.first.extjs.plugin.dba.controller.TableController;
import dream.first.extjs.plugin.dba.servlet.DBAResourceServletRegistrationBean;

public class ExtJSPluginDBAConfiguration {

	@Bean
	public DatabaseController databaseController() {
		return new DatabaseController();
	}

	@Bean
	public TableController tableController() {
		return new TableController();
	}

	@Bean
	@ConditionalOnMissingBean
	public DBAResourceServletRegistrationBean dbaResourceServletRegistrationBean() {
		return new DBAResourceServletRegistrationBean();
	}

}
