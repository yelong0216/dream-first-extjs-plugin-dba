Coe.initialize();
Co.initialize();

var apiPrefix = "/extjs/plugin/dba";

Ext.onReady(function() {
	var API = {
		saveTable : rootPath + apiPrefix + "/table/save",
		queryTable : rootPath + apiPrefix + "/table/query",
		deleteTable : rootPath + apiPrefix + "/table/delete",
		retrieveTable : rootPath + apiPrefix + "/table/retrieve"
	};
	
	//============================ Model =========================
	Co.defineModel("Table", ["name"]);
	//============================ Store =========================
	var tableGridStore = Co.gridStore("tableGridStore", API.queryTable, "Table", {
		autoLoad : false,
		pageSize : Co.maxInt,
		output : "tableTbar",
		sorters : [{
			property : "createTime",
			direction : "desc"
		}]
	});
		
	//============================ View =========================
	var tableTbar = Co.toolbar("tableTbar", [{
			type : "+", 
			handler : addTable,
			showAtContextMenu : true
		},{
			type : "*",
			handler : deleteTable,
			showAtContextMenu : true
		},{
			type : "-",
			handler : editTable,
			showAtContextMenu : true
		},"->",{
			type : "@",
			handler : searchTable,
			searchField : [],
			searchEmptyText : []
		}
	]);
	
	var tableColumns = [
		Co.gridRowNumberer(),
		{header : "表名称", dataIndex : "name", width : 1000, hidden : false}
	];
	
	var tableGrid = Co.grid("tableGrid", tableGridStore, tableColumns, tableTbar, null, {
		deleteId : "name",
		listeners : {
			itemdblclick : function(view, record) {
				editTable();
			}
		}
	});
	
	Co.load(tableGridStore);
	
	var tableForm = Co.form(API.saveTable, [{
		layout : "column",
		border : false,
		bodyCls : "panel-background-color",
		items : [{
			columnWidth : .5,
			border : false,
			bodyCls : "panel-background-color",
			layout : "form",
			items : [{
				xtype : "textfield",
				id : "name",
				name : "model.name",
				fieldLabel : "表名称",
				allowBlank : true,
				editable : true,
				readOnly : false
			}]
		}]
	}]);
	
	var tableFormWindow = Co.formWindow("新增", tableForm, 650, 150, "fit", {
		okHandler : saveTable
	});
	
	Ext.create("Ext.container.Viewport", {
		layout : "fit",
		items : tableGrid
	});
	//============================ Function =========================
	function addTable() {
		Co.resetForm(tableForm, true);
		tableFormWindow.setTitle("新增");
		tableFormWindow.show();
	}
	
	function saveTable() {
		Co.formSave(tableForm, function(form, action){
			Co.alert("保存成功！", function(){
				tableFormWindow.hide();
				Co.reload(tableGridStore);
			});
		});
	}
	
	function editTable() {
		Co.formLoad(tableForm, tableGrid, API.retrieveTable, function(result, opts, selectedId){
			if (true === result.success) {
				tableFormWindow.setTitle("修改");
				tableFormWindow.show();
			} else {
				Co.showError(result.msg || "数据加载失败！");
			}
		});
	}
	
	function deleteTable() {
		Co.gridDelete(tableGrid, API.deleteTable, function(result){
			if (result.success === true) {
				Co.alert("删除成功！", function(){
					Co.reload(tableGridStore);
				});
			} else {
				Co.alert(result.msg);
			}
		});	
	}
	
	function searchTable() {
		Co.load(tableGridStore);
	}
});