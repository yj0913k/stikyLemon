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
