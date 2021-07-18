$('#data').on('change blur', function () {
    if ($('#urlComponentEncode').prop("checked") === true){
        $("#decodedData").val("")
        $("#decodedData").val(decodeURIComponent($(this).val()));
    }else {
        $("#decodedData").val("")
        $("#decodedData").val(decodeURI($(this).val()));
    }
})