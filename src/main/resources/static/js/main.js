hljs.initHighlightingOnLoad();
$(document).ready(function () {
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

    tinymce.init({
        selector: 'textarea.tinymce-editor',
        branding: false,
        elementpath: false,
        menubar:false,
        statusbar: false,
        toolbar:false,
        entity_encoding : "raw"
    });


})