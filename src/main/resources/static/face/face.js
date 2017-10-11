var upload_path = "facePhotos";
var curimg_style = "4px solid #2DD70F";
var faceTt;
var appendNumber = 4;
var prependNumber = 1;
var swiper = new Swiper('.swiper-container', {
    pagination: '.swiper-pagination',
    nextButton: '.swiper-button-next',
    prevButton: '.swiper-button-prev',
    slidesPerView: 6,
    /*  autoplay: 2000, */
    effect:'coverflow',
    centeredSlides: true,
    paginationClickable: true,
    spaceBetween: 30
});

var zdstPush = new zdstPush(window.document.domain,"8124");
owl.ajaxRequest("/shopListByUser.do","",function(e){
    $("#shop-select").empty("");
    $("#shopListTemplate").tmpl( e.data ).appendTo("#shop-select");

    var defaultvalue= $("#shop-select").val();
    zdstPush.connection(defaultvalue,"556");
    /* zdstPush.connection("1","556"); */


    owl.ajaxRequest("/get_recentshopface_list.do",{"shopId":defaultvalue,},function(result){
        if(result.status == -1) {
            alert("获取数据失败！");
            return;
        }
        var shopFaceList = result.data;

        for(var i = 0; i < shopFaceList.length; i++) {
            var face = shopFaceList[i];
            var fid = face.face_id;
            var imgs = face.images_txt;
            var img_name = upload_path + "/" + imgs.split(",")[0];
            var htmlStr = '<div class="swiper-slide"><img data="'+fid+'" src="'+img_name+'"  alt="猫投鹰人脸识别" width="100%"/></div>';
            swiper.prependSlide([
                    '<div class="swiper-slide">'+htmlStr+ '</div>'
            ]);

            $(".swiper-slide > img").unbind().click(function(){
                var faceId = $(this).attr("data");
                imgDetail(faceId);
            });
        }
    });

    zdstPush.onmessage(function(evnt){

        var faceData=JSON.parse(evnt.data);
        /*console.log(faceData);*/

        var faceImg = faceData.faceImg.split(",");
        $("#faceTemp").empty();
        for(var i=0;i< faceImg.length; i++){
            /*	console.log(faceImg[i]);*/
            var htmlS = '<div class="col-xs-3 col-md-3"><img src="facePhotos/'+faceImg[i]+'"  alt="猫投鹰人脸识别" height="190" width="190"/></div>';
            $("#faceTemp").append(htmlS);
            if(i==3){
            	break;
            }
        }

        $("#faceShopTemp").empty();
        $("#faceShopDesTemp").empty();
        var shopFaces=JSON.parse(faceData.shopFaces);
        for(var i=0;i< shopFaces.length; i++){
            var objTemp=shopFaces[i];
            /*var htmlS1 = '<div class="col-xs-3 col-md-3"><img src="'+objTemp.imgone+'"  alt="猫投鹰人脸识别" height="190" width="190"/></div>';
             $("#faceShopTemp").append(htmlS1);*/
            var htmlS2 = '<div><span>'+objTemp.visitdate+'</span></div><div class="name-div"><span>门店：</span><span>'+objTemp.shopName+'</span></div>';
            $("#faceShopTemp").append(htmlS2);
        }

        var recentFaces = JSON.parse(faceData.recentFace);
        faceTt=recentFaces;
        $(".swiper-container>.swiper-wrapper").empty();
        for(var i=0;i<recentFaces.length;i++){
            var faceObj = recentFaces[i];
            console.log(faceObj.images[0]);
            var htmlStr = '<div class="swiper-slide"><img data="'+faceObj.faceId+'" src="'+faceObj.images[0]+'"  alt="猫投鹰人脸识别" width="100%"/></div>';
            swiper.prependSlide([
                    '<div class="swiper-slide">'+htmlStr+ '</div>'
            ]);

            $(".swiper-slide > img").unbind().click(function(){
                var faceId = $(this).attr("data");
                imgDetail(faceId);
            });

        }

        $("#realtimeimg").attr("src",faceData.faceImg);

        $("#sex").html(faceData.sex == "0" ?"女":"男");
        $("#age").html(faceData.ageVal);
        $("#race").html(faceData.raceTxt);
        $("#lips").html(faceData.lipsTxt);
        $("#glass").html(faceData.glassesInd == "0"?"没戴眼镜":"带眼镜");
        $("#date").html(faceData.realTime);

    });

    $("#shop-select").change(function(){
        var value = $(this).val();

        zdstPush.connection(value,"556");
        /* zdstPush.connection("1","556"); */
        zdstPush.onmessage(function(evnt){

            var faceData=JSON.parse(evnt.data);
            console.log(faceData);

            var faceImg = faceData.faceImg.split(",");
            $("#faceTemp").empty();
            for(var i=0;i< faceImg.length; i++){
                /*	console.log(faceImg[i]);*/
                var htmlS = '<div class="col-xs-3 col-md-3"><img src="facePhotos/'+faceImg[i]+'"  alt="猫投鹰人脸识别" height="190" width="190"/></div>';
                $("#faceTemp").append(htmlS);
                if(i==3){
                	break;
                }
            }

            $("#faceShopTemp").empty();
            $("#faceShopDesTemp").empty();
            var shopFaces=JSON.parse(faceData.shopFaces);
            for(var i=0;i< shopFaces.length; i++){
                var objTemp=shopFaces[i];
                /*var htmlS1 = '<div class="col-xs-3 col-md-3"><img src="'+objTemp.imgone+'"  alt="猫投鹰人脸识别" height="190" width="190"/></div>';
                 $("#faceShopTemp").append(htmlS1);*/
                var htmlS2 = '<div class="col-xs-3 col-md-3"><div><span>'+objTemp.visitdate+'</span></div><div><span>门店：</span><span>'+objTemp.shopName+'</span></div></div>';
                $("#faceShopTemp").append(htmlS2);
            }

            var recentFaces = JSON.parse(faceData.recentFace);
            faceTt=recentFaces;
            $(".swiper-container>.swiper-wrapper").empty();

            for(var i=0;i<recentFaces.length;i++){
                var faceObj = recentFaces[i];
                /*console.log(faceObj);*/
                var htmlStr = '<div class="swiper-slide"><img data="'+faceObj.faceId+'" src="'+faceObj.images[0]+'"  alt="猫投鹰人脸识别" width="100%"/></div>';
                swiper.prependSlide([
                        '<div class="swiper-slide">'+htmlStr+ '</div>'
                ]);

                $(".swiper-slide > img").unbind().click(function(){
                    var faceId = $(this).attr("data");
                    imgDetail(faceId);
                });

            }

            $("#realtimeimg").attr("src",faceData.faceImg);

            $("#sex").html(faceData.sex == "0" ?"女":"男");
            $("#age").html(faceData.ageVal);
            $("#race").html(faceData.raceTxt);
            $("#lips").html(faceData.lipsTxt);
            $("#glass").html(faceData.glassesInd == "0"?"没戴眼镜":"带眼镜");
            $("#date").html(faceData.realTime);

        });
    });



});


function imgDetail(faceId) {

    var face;
    for(var i = 0; i < faceTt.length; i++) {
        var face_v = faceTt[i];
        var fid_v = face_v.faceId;
        if(faceId == fid_v) {
            face = face_v;
            break;
        }
    }

    var imgs =face.images;

    var html = "<div class='clearfix' style='padding:10px;'>";

    html += "<div style='display:block; width:70%; float:left;'><img id='curimg' src='#' style='width:100%; height:400px;' /></div>";
    html += "<div id='imglist' style='display:block; width:30%; float:left;'>";
    // 图片列表
    var curimg = null;
    for(var i = 0; i <  imgs.length; i++) {
        var img_name = imgs[i];
        if(i == 0) {
            curimg = img_name;
        }
        html += "<a href='javascript:void(0);' style='display:block; border-left:" + (i == 0 ? curimg_style : "none") + "' onclick='displayimg(this);'><img src='" + img_name + "' alt='无法显示图片' style='width:100%; height:100px; margin-bottom:1px;' /></a>";
    }
    html += "</div>";
    html += "<div style='display:block; width:100%; float:left; margin-top:10px;'>[访问时间：" + face.visitdate  + "]</div>";
    html += "</div>";

    /*console.log(faceObj.visitdate);*/
    layer.open({
        type: 1,
        title: '人物标签',
        area:['600px'],
        shade: 0.5,
        shift : 3,
        content: html
    });
    $("#curimg").attr("src", curimg);
}

function displayimg(obj) {
    imgSetting($(obj).find("img").attr("src"));
    var aImg = $("#imglist").find("a").css("border-left", "none");
    $(obj).css("border-left", curimg_style);
}

function imgSetting(imgName) {
    $("#curimg").animate({opacity:'toggle'},"slow",null,function(){
        $("#curimg").attr("src", imgName);
        $("#curimg").animate({opacity:'toggle'},"slow");
    });
}
  
  
