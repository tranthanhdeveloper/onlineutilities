(function () {
    "user strict"

    $().ready(function () {
        window.resetPreviewSectionState = function resetState() {
            let previewEle = $("#imagePreview #previewArea");
            let errorDivision = $("#imagePreview .alert-warning");
            $('#saveBtn').unbind();
            errorDivision.removeClass("d-none");
            errorDivision.addClass("d-none");
            previewEle.empty();
        }
        $('#importModal').on('click', '#dataImportOkBtn', function (event) {
            var url = $('#dataImportUrl').val();
            $.ajax({
                url: url
            }).done(function (xmlData) {
                $('#importModal').modal('hide');
                editor.setValue(xmlData);
            }).fail(function () {
                alert("Error occurred during import data")
            });

        })

        $(document).on('change', '#fileSelect', function (event) {
            var file = document.getElementById("fileSelect").files[0];
            if (file) {
                var reader = new FileReader();
                reader.readAsText(file, "UTF-8");
                reader.onload = function (evt) {
                    editor.setValue(evt.target.result);
                }
                reader.onerror = function (evt) {
                    alert("error reading file");
                }
            }
        })

        $(document).on('change', "#base64DataHolder", async function (event) {
            window.resetPreviewSectionState();
            let previewEle = $("#imagePreview #previewArea");
            previewEle.empty();

            var base64Data = await $("textarea#base64DataHolder").val();
            var mimeType = await $().detectMimeType(base64Data);

            if (mimeType !== undefined) {
                let source = 'data:' + mimeType + ';base64,' + base64Data;
                previewEle.html('<img src="' + source + '" height="100%" width="100%">');
                $('#file-info').popover({
                    title: 'Image info',
                    html: true,
                    sanitize: false,
                    placement:"bottom",
                    content: '<div class="container">' +
                        '<div><p><b>Mimetype:&nbsp;&nbsp;</b>'+mimeType+' </p></div>' +
                        '<div><p><b>Potential extension:&nbsp;&nbsp;</b>'+$.mimeTypeMap[mimeType]+' </p></div>' +
                        '<div><p><b>Origin Base64 size:&nbsp;&nbsp;</b>'+Object.keys(base64Data).length+' bytes </p></div>' +
                        ' </div>'

                })

                $('#saveBtn').bind('click', async function () {
                    console.log("event fired");
                    const b64toBlob = await (await fetch(source)).blob();
                    $(this).downloadBlob(b64toBlob, 'image-from-base64-onlineutilities.' + $.mimeTypeMap[mimeType]);
                })
            } else {
                $("#imagePreview .alert-warning").removeClass("d-none")
            }
        })
    });
}())