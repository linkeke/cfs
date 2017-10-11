var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			showIcon: false,
			showLine: false
		}
	};

	
	var code;
	
	function setCheck() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		type = { "Y":"ps", "N":"ps"};
		zTree.setting.check.chkboxType = type;
		showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
	}
	function showCode(str) {
		if (!code) code = $("#code");
		code.empty();
		code.append("<li>"+str+"</li>");
	}
	
	$(document).ready(function(){
		   owl.ajaxRequest("/roleDetail",{},function(e){
		  	/* alert(e);
		  	 alert(JSON.stringify(e));*/
		  	 var roleId = e.data.roleDetail.nRoleId;
		  	 var roleCode = e.data.roleDetail.nRoleCode;
		  	 var roleName = e.data.roleDetail.cRoleName;
		  	 var roleDesc = e.data.roleDetail.cRoleDesc;
		  	 
		  	group.roleId=roleId;
			group.roleCode=roleCode;
			if(roleId==""){
				layer.msg("该群组不存在或已被删除!");
			}else{
				$("#groupName").val(roleName);
				$("#groupDesc").val(roleDesc);
				var param={};
				param.roleCode=roleCode;
				owl.ajaxRequest("/userMenuTree",param,function(e){
				  	  //alert(JSON.stringify(e));
				  	  var notes=e.data.dataList;
				  	  $.fn.zTree.init($("#treeDemo"), setting, notes);
				  	  setCheck();
				    });
				
					$("#ok").click(function(){
						group.modify();
					});
					$("#cancel").click(function(){
						  owl.closePage();
					});
			}
		  	 
		    });
		
		
			
	});
	
	///
	var group={};
	group.modify=function(){
		if(group.roleId==""){
			layer.msg("该群组不存在或已被删除！");
		}else{
			
			$('#new_group')
	        .bootstrapValidator({
	            message: 'This value is not valid',
	            feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'
	            },
	            fields: {
	            	groupName: {
	                    validators: {
	                        notEmpty: {
	                            message: '群组名称不能为空'
	                        }
	                    }
	                },
	                groupDesc: {
	                    validators: {
	                        notEmpty: {
	                            message: '描述不能为空'
	                        }
	                    }
	                }
	            }
	        })
	        .on('success.form.bv', function(e) {
	            // Prevent form submission
	            e.preventDefault();

	        	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = treeObj.getCheckedNodes(true);
				var checkedMenuIds=[];
				for(var i=0;i<nodes.length;i++){
					checkedMenuIds.push(nodes[i].id);
				}
				//alert(checkedMenuIds.join("#"));
				var groupName = $.trim($("#groupName").val());
				var groupDesc = $.trim($("#groupDesc").val());
				var param={};
				param.groupId=group.roleId;
				param.groupName=groupName;
				param.groupDesc=groupDesc;
				param.menuIdsStr=checkedMenuIds.join("#");
				owl.ajaxRequest("/modifyGroup",param,function(e){
				  	  //alert(JSON.stringify(e));
					layer.msg(e.info);
					window.top.location=getBasePath()+"/groupPage"; 
				});
	        });
			
		}
		
	};