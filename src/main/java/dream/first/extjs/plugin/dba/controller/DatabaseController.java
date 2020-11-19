package dream.first.extjs.plugin.dba.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yelong.core.jdbc.sql.ddl.DataDefinitionLanguage;
import org.yelong.core.jdbc.sql.ddl.Database;
import org.yelong.core.jdbc.sql.function.DatabaseFunction;
import org.yelong.support.servlet.resource.response.ResourceResponseException;
import org.yelong.support.spring.mvc.HandlerResponseWay;
import org.yelong.support.spring.mvc.ResponseWay;

import com.github.pagehelper.PageInfo;

import dream.first.base.queryinfo.filter.DFQueryFilterInfo;
import dream.first.base.queryinfo.sort.DFQuerySortInfo;
import dream.first.extjs.base.controller.DFBaseExtJSCrudController;
import dream.first.extjs.plugin.dba.ExtJSPluginDBA;
import dream.first.extjs.plugin.dba.dto.DatabaseDTO;

@Controller
@RequestMapping({ "database", "extjs/plugin/dba/database" })
public class DatabaseController extends DFBaseExtJSCrudController<DatabaseDTO> {

	@Resource
	private DatabaseFunction databaseFunction;

	@Resource
	private DataDefinitionLanguage dataDefinitionLanguage;

	@ResponseBody
	@RequestMapping("index")
	@ResponseWay(HandlerResponseWay.MODEL_AND_VIEW)
	public void index() throws ResourceResponseException, IOException {
		responseHtml(ExtJSPluginDBA.RESOURCE_PRIVATES_PACKAGE,
				ExtJSPluginDBA.RESOURCE_PREFIX + "/html/database/databaseManage.html");
	}

	@Override
	public PageInfo<?> queryModel(DatabaseDTO model, Collection<DFQueryFilterInfo> queryFilterInfos,
			Collection<DFQuerySortInfo> querySortInfos, Integer pageNum, Integer pageSize) throws Exception {
		Database database = new Database(databaseFunction.getCurrentDatabase());
		return new PageInfo<>(Arrays.asList(database));
	}

}
