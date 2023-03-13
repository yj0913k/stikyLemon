

$(function (){
    $("#filter>ul>li>input").click(collectionListClickd);
    $("#aside-collection").click(collectionShowHide);
});

function collectionListClickd(){
    $("#filter>ul>li>input").removeClass("target");
    $(this).addClass("target");
    var id =  $(this).prop("id")
    id=id.slice(id.search('_')+1);
    var url;
    if(id=="all"){
        url ="/comm/goods/list/ajax";
    }else{
        url ="/comm/goods/list/ajax/"+id;
    }
    $.ajax({
        url:`${url}`,
        type:"get",
        success:function (result){
            $(".goods-list *").remove();
            $(".goods-list").html(result);
        }
    });

}
function collectionShowHide(){
    if($("#aside-collection span").last().text()=="+"){
        $("#aside-collection span").last().text("-");
        $("#coll-list").show();
    }else{
        $("#aside-collection span").last().text("+");
        $("#coll-list").hide();
    }
}