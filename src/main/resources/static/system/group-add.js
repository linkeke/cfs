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
			owl.ajaxRequest("/userMenuTree",{},function(e){
		  	  //alert(JSON.stringify(e));
		  	  var notes=e.data.dataList;
		  	  $.fn.zTree.init($("#treeDemo"), setting, notes);
		  	  setCheck();
		    });
		
			$("#ok").click(function(){
				group.add();
			});
			  $("#cancel").click(function(){
				  owl.closePage();
			  });
	});
	
	///
	var group={};
	group.add=function(){
		
		
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
    		param.groupName=groupName;
    		param.groupDesc=groupDesc;
    		param.menuIdsStr=checkedMenuIds.join("#");
    		owl.ajaxRequest("/addGroup",param,function(e){
    		  	  //alert(JSON.stringify(e));
    			layer.msg(e.info);
    			window.top.location=getBasePath()+"/groupPage"; 
    		    });
        });
		
	};