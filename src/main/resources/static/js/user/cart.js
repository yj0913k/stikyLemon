
$(function (){
    quantityChanged($("#minus_1"));
});

function btnCartOrderClicked(){
    location.href="/members/cartOrders";
}

function btnMinusClicked(el){
    var quantity = Number($(el).next().val())-1;
    if(quantity<1)
        quantity=1;
    $(el).next().val(quantity);

    quantityChanged(el);
}

function btnAddClicked(el){
    var quantity = Number($(el).prev().val())+1;
    if(quantity>99)
        quantity=99;
    $(el).prev().val(quantity);

    quantityChanged(el);
}

function quantityChanged(el){
    var no = Number($(el).siblings(".no").val());
    var quantity = Number($(el).siblings(".amount").val());
    var price = $(el).siblings(".price").val();
    //alert("no : "+no+"\nquantity: "+quantity);
    $(el).parents(".goods-detail").siblings(".sub-total")
        .children(".TP").val(Number(price)*Number(quantity));

    console.log(no+":"+quantity)
    $.ajax({
        url:"/members/cart/update",
        type:"post",
        data:{
            no:no,
            quantity:quantity
        },
        success:function (){
            console.log("장바구니 상품 숫자 업데이트 디비반영 성공")
        }
    });

    var goodsCount=$("#list-size").val();
    var sumPrice=0;
    for(var i=1;i<=goodsCount;i++){
        sumPrice+=Number($(`#TP_${i}`).val());
    }
    var totPrice = sumPrice+Number($("#tot-delivery").text());
    $("#sum-price").text(sumPrice+"원");

    $("#tot-price").text(totPrice+"원");
}
function btnRemoveClicked(el){
    if(confirm("장바구니에서 삭제하시겠습니까?")){
        var no = $(el).siblings(".no").val();
        $.ajax({
            url:"/members/cart",
            type: "delete",
            data:{no:no},
            success:function (){
                console.log("삭제 성공");
                location.href="/members/cart";
            }
        });

    }
}
/*

var amount;
$(function () {
    $("#add").click(function () {
        amount = $("#amount");
        if (parseInt(amount.val()) === 99)
            return;
        var plusval = parseInt(amount.val()) + 1;
        amount.val(plusval);
        settp(plusval);

    });
    $("#minus").click(function () {
        amount = $("#amount");
        if (parseInt(amount.val()) === 1)
            return;
        var minusval = parseInt(amount.val()) - 1;
        amount.val(minusval);
        settp(minusval);
    });
    $("#amount").change(function (){
        settp($(this).val());
    });
});
    function settp(amount){
        $("#TP").val(amount*$("#vprice").val());
    }
*/
