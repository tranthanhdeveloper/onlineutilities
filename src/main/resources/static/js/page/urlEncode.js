$('#data').on('change blur', function () {
    if ($('#urlComponentEncode').prop("checked") === true) {
        $("#encodedData").val("")
        $("#encodedData").val(encodeURIComponent($(this).val()));
    } else {
        $("#encodedData").val("")
        $("#encodedData").val(encodeURI($(this).val()));
    }
})