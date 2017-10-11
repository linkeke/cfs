/**
 * Created by admin on 2017/5/17.
 */


$(document).ready(function(e) {
    owl.ajaxRequest("/userMenus", "", function(result) {
        if(result.status == -1) {
            alert("获取数据失败！");
            return;
        }
        var menuList = result.data.menus;
        var htmlText = "<ul class='nav side-menu'>";
        if(menuList!=null&&menuList.length>0){
            for(var i=0;i<menuList.length;i++){
                htmlText+="<li><a><i class='fa fa-edit'></i>"+menuList[i].menuName+
                    "<span class='fa fa-chevron-down'></span></a>";
                var subMenus = menuList[i].subMenus;
                if(subMenus!=null&&subMenus.length>0){
                    htmlText+="<ul class='nav child_menu'>";
                    for(var j=0;j<subMenus.length;j++){
                    htmlText +="<li ><a href="+"'"+subMenus[j].menuUrl+"'>"+subMenus[j].menuName+"</a></li>";
                    }
                    htmlText+="</ul>";
                }
                htmlText+="</li>";
            }
        }
        htmlText+="</ul>";

       $("#sidebar-menu .menu_section").append(htmlText);

    });
});