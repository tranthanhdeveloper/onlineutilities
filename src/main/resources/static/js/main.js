hljs.initHighlightingOnLoad();

$(document).ready(function () {
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

    $('.btn-download-file').on('click', function (event) {
        event.preventDefault();
        let targetEl = $($(this).data("target-element"));
        let value = targetEl.text();
        window.location = "data:application/octet-stream,"+value;
    })
})