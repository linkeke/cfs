var oneUserArr = [],linksArr = [];
$(function(){
	basicObject.initIndustryPic();
	
			$('#demo-form')
        .bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
            	id: {
                    validators: {
                        notEmpty: {
                            message: '设备ID不能为空'
                        }
                    }
                },
                type: {
                    validators: {
                        notEmpty: {
                            message: '设备类型不能为空'
                        }
                    }
                }
            }
        })
        .on('success.form.bv', function(e) {
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');
            
            // Use Ajax to submit form data
            $.post(getBasePath()+"/getoneuserall.do", $form.serialize(), function(result) {
              
                if(result.status == 10000){
                	var data = result.data;
   				
    				var isMale = true;

    				for (var i=0; i<data.demographicTag.length; i++)
    				{
    					if (data.demographicTag[i].label == '030102')
    					{
    						isMale = false;
    						break;
    					}
                	}

    				if(isMale){
    					$("#sexImg").attr("src","images/male.gif");
    				}else{
    					$("#sexImg").attr("src","images/female.gif");
    				}

    				$("#userTag-tbody").empty("");
    				$("#userTagTemplate").tmpl( data ).appendTo("#userTag-tbody");	

    				$("#consumeTag-tbody").empty("");
    				$("#consumeTagTemplate").tmpl( data ).appendTo("#consumeTag-tbody");	

    				$("#appTag-tbody").empty("");
    				$("#appTagTemplate").tmpl( data ).appendTo("#appTag-tbody");	
    				
    				$("#gameTag-tbody").empty("");
    				$("#gameTagTemplate").tmpl( data ).appendTo("#gameTag-tbody");	 
    				
    				
    				/*
    				$("#financeTag-tbody").empty("");
    				$("#financeTagTemplate").tmpl( data ).appendTo("#financeTag-tbody");
    				*/
    				
//    				console.log(data.demographicTag);
//    				console.log(data.gameTag);
//    				console.log(data.consumeTag);
//    				console.log(data.appTag);
//    				console.log(data.financeTag);

    				$("#sex-container").css('display', 'table-cell');
    				$("#userTag-container").css('display', (data.demographicTag == null ? 'none' : 'table-cell'));
    				$("#gameTag-container").css('display', (data.gameTag == null ? 'none' : 'table-cell'));
    				$("#consumeTag-container").css('display', (data.consumeTag == null ? 'none' : 'table-cell'));
    				$("#appTag-container").css('display', (data.appTag == null ? 'none' : 'table-cell'));
    				
    				// $("#financeTag-container").style.display = (data.financeTag == null ? 'none' : 'inline');
                	
                }else{
                	layer.msg("请求talkingdata服务器失败！")
                }
                
            }, 'json'); 
        });
		
		
			var macID = $("#id").val();
			var idType = $("#type").val();
			if(macID != "" && idType!=""){
				$("#submit-userone").click();
			}
		
		
	});
