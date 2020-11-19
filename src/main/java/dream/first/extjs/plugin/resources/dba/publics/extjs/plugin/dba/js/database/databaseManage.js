Coe.initialize();
Co.initialize();

var apiPrefix = "/extjs/plugin/dba";

Ext.onReady(function() {
	var API = {
		saveDatabase : rootPath + apiPrefix + "/database/save",
		queryDatabase : rootPath + apiPrefix + "/database/query",
		deleteDatabase : rootPath + apiPrefix + "/database/delete",
		retrieveDatabase : rootPath + apiPrefix + "/database/retrieve"
	};
	
	//============================ Model =========================
	Co.defineModel("Database", ["name"]);
	//============================ Store =========================
	var databaseGridStore = Co.gridStore("databaseGridStore", API.queryDatabase, "Database", {
		autoLoad : false,
		output : "databaseTbar",
		sorters : [{
			property : "createTime",
			direction : "desc"
		}]
	});
		
	//============================ View =========================
	var databaseTbar = Co.toolbar("databaseTbar", [{
			type : "+", 
			handler : addDatabase,
			showAtContextMenu : true
		},{
			type : "*",
			handler : deleteDatabase,
			showAtContextMenu : true
		},{
			type : "-",
			handler : editDatabase,
			showAtContextMenu : true
		},"->",{
			type : "@",
			handler : searchDatabase,
			searchField : [],
			searchEmptyText : []
		}
	]);
	
	var databaseColumns = [
		Co.gridRowNumberer(),
		{header : "数据库名称", dataIndex : "name", width : 1000, hidden : false}
	];
	
	var databaseGrid = Co.grid("databaseGrid", databaseGridStore, databaseColumns, databaseTbar, null, {
		listeners : {
			itemdblclick : function(view, record) {
				editDatabase();
			}
		}
	});
	
	Co.load(databaseGridStore);
	
	var databaseForm = Co.form(API.saveDatabase, [{
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
				fieldLabel : "数据库名称",
				allowBlank : true,
				editable : true,
				readOnly : false
			}]
		}]
	}]);
	
	var databaseFormWindow = Co.formWindow("新增", databaseForm, 650, 150, "fit", {
		okHandler : saveDatabase
	});
	
	Ext.create("Ext.container.Viewport", {
		layout : "fit",
		items : databaseGrid
	});
	//============================ Function =========================
	function addDatabase() {
		Co.resetForm(databaseForm, true);
		databaseFormWindow.setTitle("新增");
		databaseFormWindow.show();
	}
	
	function saveDatabase() {
		Co.formSave(databaseForm, function(form, action){
			Co.alert("保存成功！", function(){
				databaseFormWindow.hide();
				Co.reload(databaseGridStore);
			});
		});
	}
	
	function editDatabase() {
		Co.formLoad(databaseForm, databaseGrid, API.retrieveDatabase, function(result, opts, selectedId){
			if (true === result.success) {
				databaseFormWindow.setTitle("修改");
				databaseFormWindow.show();
			} else {
				Co.showError(result.msg || "数据加载失败！");
			}
		});
	}
	
	function deleteDatabase() {
		Co.gridDelete(databaseGrid, API.deleteDatabase, function(result){
			if (result.success === true) {
				Co.alert("删除成功！", function(){
					Co.reload(databaseGridStore);
				});
			} else {
				Co.alert(result.msg);
			}
		});	
	}
	
	function searchDatabase() {
		Co.load(databaseGridStore);
	}
});