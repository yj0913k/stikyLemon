var quantity;
$(function () {
    $("#add").click(function () {
        quantity = $("#quantity");
        if (parseInt(quantity.val()) === 99)
            return;
        var plusval = parseInt(quantity.val()) + 1;
        quantity.val(plusval);
        settp(plusval);

    });
    $("#minus").click(function () {
        quantity = $("#quantity");
        if (parseInt(quantity.val()) === 1)
            return;
        var minusval = parseInt(quantity.val()) - 1;
        quantity.val(minusval);
        settp(minusval);
    });
    $("#quantity").change(function (){
        settp($(this).val());
    });
});
    function settp(quantity){
        $("#TP").val(quantity*$("#vprice").val());
    }
