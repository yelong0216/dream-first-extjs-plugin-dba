package dream.first.extjs.plugin.dba.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yelong.core.jdbc.sql.ddl.DataDefinitionLanguage;
import org.yelong.core.jdbc.sql.ddl.Database;
import org.yelong.core.jdbc.sql.ddl.Table;
import org.yelong.core.jdbc.sql.function.DatabaseFunction;
import org.yelong.support.servlet.resource.response.ResourceResponseException;
import org.yelong.support.servlet.resource.response.ResourceResponseHandler;
import org.yelong.support.spring.mvc.HandlerResponseWay;
import org.yelong.support.spring.mvc.ResponseWay;

import com.github.pagehelper.PageInfo;

import dream.first.base.queryinfo.filter.DFQueryFilterInfo;
import dream.first.base.queryinfo.sort.DFQuerySortInfo;
import dream.first.extjs.base.controller.DFBaseExtJSCrudController;
import dream.first.extjs.plugin.dba.ExtJSPluginDBA;
import dream.first.extjs.plugin.dba.dto.TableDTO;

@Controller
@RequestMapping({ "table", "extjs/plugin/dba/table" })
public class TableController extends DFBaseExtJSCrudController<TableDTO> {

	@Resource
	private DatabaseFunction databaseFunction;

	@Resource
	private DataDefinitionLanguage dataDefinitionLanguage;

	ResourceResponseHandler resourceResponseHandler;

	@ResponseBody
	@RequestMapping("index")
	@ResponseWay(HandlerResponseWay.MODEL_AND_VIEW)
	public void index() throws ResourceResponseException, IOException {
		responseHtml(ExtJSPluginDBA.RESOURCE_PRIVATES_PACKAGE,
				ExtJSPluginDBA.RESOURCE_PREFIX + "/html/table/tableManage.html");
	}

	@Override
	public boolean deleteModel(String deleteIds) throws Exception {
		String[] tableNames = deleteIds.split(",");
		for (String tableName : tableNames) {
			dataDefinitionLanguage.dropTable(new Table(databaseFunction.getCurrentDatabase(), tableName));
		}
		return true;
	}

	@Override
	public PageInfo<?> queryModel(TableDTO model, Collection<DFQueryFilterInfo> queryFilterInfos,
			Collection<DFQuerySortInfo> querySortInfos, Integer pageNum, Integer pageSize) throws Exception {
		List<Table> tables = dataDefinitionLanguage.queryTable(new Database(databaseFunction.getCurrentDatabase()));
		return new PageInfo<>(tables);
	}

}
