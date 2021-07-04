hljs.initHighlightingOnLoad();
$(document).ready(function () {
    $.gdprcookie.init({
        title: "Accept cookies & privacy policy?",
        message: "We use cookies to ensure that we give you the best experience on our website. If you continue to use this site we will assume that you are happy with it. Click the <strong>accept</strong> button below to confirm your acceptation. Learn more about our privacy here <a href=/privacy-policy.html>OnlineUtilities' privacy link</a>",
        acceptBtnLabel: "Accept cookies",
    });

    var clipboard = new ClipboardJS('.btn-clipboard-copy');
    $('.hide-by-default').hide();

    $(".text-selectable").on('dblclick', function () {
        var sel, range;
        var el = $(this)[0];
        if (window.getSelection && document.createRange) { //Browser compatibility
            sel = window.getSelection();
            if(sel.toString() === ''){ //no text selection
                window.setTimeout(function(){
                    range = document.createRange(); //range object
                    range.selectNode(el); //sets Range
                    sel.removeAllRanges(); //remove all ranges from selection
                    sel.addRange(range);//add Range to a Selection.
                },1);
            }
        }else if (document.selection) { //older ie
            sel = document.selection.createRange();
            if (sel.text === '') { //no text selection
                range = document.body.createTextRange();//Creates TextRange object
                range.moveToElementText(el);//sets Range
                range.select(); //make selection.
            }
        }
    })

    // Make downloadable for text value, using declarative model
    $('.btn-download-file').on('click', function (event) {
        event.preventDefault();
        let targetEl = $($(this).data("target-element"));
        let filename = $(this).data("download-filename");
        let value = targetEl.text();

        let element = document.createElement("a");
        element.setAttribute("href", "data:text/plain;charset=utf-8,"+value);
        element.setAttribute("download", filename);
        element.click();
    })

    $('.btn-copy-clipboard').on('click', function (event) {
        event.preventDefault();
        let targetEl = $($(this).data('target-element'));
    })

    $(document).on('click', '.file-upload-option', function (event) {
        let targetEl = $(event.target);
        if(targetEl.is('input')){
            let elToShow = $(targetEl.data("show-element"));
            let elToHide = $(targetEl.data("hide-element"))
            if (targetEl.prop('checked') === true){
                elToHide.hide();
                elToShow.show();
            }else {
                // Reset the element
                elToShow.hide();
                elToHide.show();
            }
        }
    })

	// Fix custom-file-input file name displaying
    $('.custom-file-input').on('change',function(){
        var fileName = $(this)[0].files[0].name
		console.log(fileName);
        $(this).next('.custom-file-label').html(fileName);
    })

    $.fn.extend({
        escapeJSON: function (json) {
            var escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
            var meta = {    // table of character substitutions
                '\b': '\\b',
                '\t': '\\t',
                '\n': '\\n',
                '\f': '\\f',
                '\r': '\\r',
                '"': '\\"',
                '\\': '\\\\'
            };

            escapable.lastIndex = 0;
            return escapable.test(json) ? json.replace(escapable, function (a) {
                let c = meta[a];
                return (typeof c === 'string') ? c : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
            }) :json;
        },
        unescapeJSON : function (string) {
            string = string.replaceAll('\b', '\b')
            string = string.replaceAll('\t', '\t')
            string = string.replaceAll('\\n','\n')
            string = string.replaceAll('\f','\f')
            string = string.replaceAll('\r', '\r');
            string = string.replaceAll('\\"', '"')
            string = string.replaceAll('\\\\', '\\');
            console.log(string);
            return string;
        }
    });

    $('#inputFile[data-showlistfile="true"]').change(function () {
        let fileListEl = $('#selectedFileList');
        let chosenFiles = $('#inputFile')[0].files;
        if (chosenFiles && chosenFiles.length > 0) {
            for (let i = 0; i < chosenFiles.length; i++) {
                fileListEl.children("ul").append('<li class="list-group-item">' + chosenFiles[i].name + '</li>')
                fileListEl.removeClass('d-none');
            }
        } else {
            fileListEl.removeClass('d-none');
            fileListEl.addClass('d-none')
        }
    });
})