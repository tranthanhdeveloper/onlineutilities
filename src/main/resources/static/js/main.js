(function () {
    "use strict";
    var app = window.app || {}

    $().ready(function () {
        if (typeof app !== 'undefined' && app.init) {
            app.init();
        }


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
                if (sel.toString() === '') { //no text selection
                    window.setTimeout(function () {
                        range = document.createRange(); //range object
                        range.selectNode(el); //sets Range
                        sel.removeAllRanges(); //remove all ranges from selection
                        sel.addRange(range);//add Range to a Selection.
                    }, 1);
                }
            } else if (document.selection) { //older ie
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
            element.setAttribute("href", "data:text/plain;charset=utf-8," + value);
            element.setAttribute("download", filename);
            element.click();
        })

        $('.btn-copy-clipboard').on('click', function (event) {
            event.preventDefault();
            let targetEl = $($(this).data('target-element'));
        })

        $(document).on('click', '.file-upload-option', function (event) {
            let targetEl = $(event.target);
            if (targetEl.is('input')) {
                let elToShow = $(targetEl.data("show-element"));
                let elToHide = $(targetEl.data("hide-element"))
                if (targetEl.prop('checked') === true) {
                    elToHide.hide();
                    elToShow.show();
                } else {
                    // Reset the element
                    elToShow.hide();
                    elToHide.show();
                }
            }
        })

        // Fix custom-file-input file name displaying
        $('.custom-file-input').on('change', function () {
            var fileName = $(this)[0].files[0].name
            console.log(fileName);
            $(this).next('.custom-file-label').html(fileName);
        })

        $.fn.extend({
            mimeTypeMap: {
                'text/html': 'html',
                'text/css': 'css',
                'text/xml': 'xml',
                'image/gif': 'gif',
                'image/jpeg': 'jpeg',
                'image/jpg': 'jpeg',
                'application/x-javascript': 'js',
                'application/atom+xml': 'atom',
                'application/rss+xml': 'rss',
                'text/mathml': 'mml',
                'text/plain': 'txt',
                'text/vnd.sun.j2me.app-descriptor': 'jad',
                'text/vnd.wap.wml': 'wml',
                'text/x-component': 'htc',
                'image/png': 'png',
                'image/tiff': 'tif',
                'image/vnd.wap.wbmp': 'wbmp',
                'image/x-icon': 'ico',
                'image/x-jng': 'jng',
                'image/x-ms-bmp': 'bmp',
                'image/svg+xml': 'svg',
                'image/webp': 'webp',
                'application/java-archive': 'jar',
                'application/mac-binhex40': 'hqx',
                'application/msword': 'doc',
                'application/pdf': 'pdf',
                'application/postscript': 'ps',
                'application/rtf': 'rtf',
                'application/vnd.ms-excel': 'xls',
                'application/vnd.ms-powerpoint': 'ppt',
                'application/vnd.wap.wmlc': 'wmlc',
                'application/vnd.google-earth.kml+xml': 'kml',
                'application/vnd.google-earth.kmz': 'kmz',
                'application/x-7z-compressed': '7z',
                'application/x-cocoa': 'cco',
                'application/x-java-archive-diff': 'jardiff',
                'application/x-java-jnlp-file': 'jnlp',
                'application/x-makeself': 'run',
                'application/x-perl': 'pl',
                'application/x-pilot': 'prc',
                'application/x-rar-compressed': 'rar',
                'application/x-redhat-package-manager': 'rpm',
                'application/x-sea': 'sea',
                'application/x-shockwave-flash': 'swf',
                'application/x-stuffit': 'sit',
                'application/x-tcl': 'tcl',
                'application/x-x509-ca-cert': 'der',
                'application/x-xpinstall': 'xpi',
                'application/xhtml+xml': 'xhtml',
                'application/zip': 'zip',
                'application/octet-stream': 'bin',
                'audio/midi': 'mid',
                'audio/mpeg': 'mp3',
                'audio/ogg': 'ogg',
                'audio/x-realaudio': 'ra',
                'video/3gpp': '3gpp',
                'video/mpeg': 'mpeg',
                'video/quicktime': 'mov',
                'video/x-flv': 'flv',
                'video/x-mng': 'mng',
                'video/x-ms-asf': 'asx',
                'video/x-ms-wmv': 'wmv',
                'video/x-msvideo': 'avi',
                'video/mp4': 'm4v'
            },
            escapeJSON: function (json) {
                let escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
                let meta = {    // table of character substitutions
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
                }) : json;
            },
            unescapeJSON: function (string) {
                string = string.replaceAll('\b', '\b')
                string = string.replaceAll('\t', '\t')
                string = string.replaceAll('\\n', '\n')
                string = string.replaceAll('\f', '\f')
                string = string.replaceAll('\r', '\r');
                string = string.replaceAll('\\"', '"')
                string = string.replaceAll('\\\\', '\\');
                return string;
            },
            detectMimeType: function (base64) {
                let signatures = {
                    JVBERi0: "application/pdf",
                    R0lGODdh: "image/gif",
                    "/9j/": "image/jpg",
                    iVBORw0KGgo: "image/png"
                };

                for (let s in signatures) {
                    if (base64.indexOf(s) === 0) {
                        return signatures[s];
                    }
                }
                return undefined;
            },
            downloadBlob: function (blob, name = 'file.txt') {
                // Convert your blob into a Blob URL (a special url that points to an object in the browser's memory)
                const blobUrl = URL.createObjectURL(blob);

                // Create a link element
                const link = document.createElement("a");

                // Set link's href to point to the Blob URL
                link.href = blobUrl;
                link.download = name;

                // Append link to the body
                document.body.appendChild(link);
                var evt = document.createEvent("MouseEvents");
                evt.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
                link.dispatchEvent(evt);
                // Remove link from body
                document.body.removeChild(link);
            }
        })
        $.extend({
            mimeTypeMap: {
                'text/html': 'html',
                'text/css': 'css',
                'text/xml': 'xml',
                'image/gif': 'gif',
                'image/jpeg': 'jpeg',
                'image/jpg': 'jpeg',
                'application/x-javascript': 'js',
                'application/atom+xml': 'atom',
                'application/rss+xml': 'rss',
                'text/mathml': 'mml',
                'text/plain': 'txt',
                'text/vnd.sun.j2me.app-descriptor': 'jad',
                'text/vnd.wap.wml': 'wml',
                'text/x-component': 'htc',
                'image/png': 'png',
                'image/tiff': 'tif',
                'image/vnd.wap.wbmp': 'wbmp',
                'image/x-icon': 'ico',
                'image/x-jng': 'jng',
                'image/x-ms-bmp': 'bmp',
                'image/svg+xml': 'svg',
                'image/webp': 'webp',
                'application/java-archive': 'jar',
                'application/mac-binhex40': 'hqx',
                'application/msword': 'doc',
                'application/pdf': 'pdf',
                'application/postscript': 'ps',
                'application/rtf': 'rtf',
                'application/vnd.ms-excel': 'xls',
                'application/vnd.ms-powerpoint': 'ppt',
                'application/vnd.wap.wmlc': 'wmlc',
                'application/vnd.google-earth.kml+xml': 'kml',
                'application/vnd.google-earth.kmz': 'kmz',
                'application/x-7z-compressed': '7z',
                'application/x-cocoa': 'cco',
                'application/x-java-archive-diff': 'jardiff',
                'application/x-java-jnlp-file': 'jnlp',
                'application/x-makeself': 'run',
                'application/x-perl': 'pl',
                'application/x-pilot': 'prc',
                'application/x-rar-compressed': 'rar',
                'application/x-redhat-package-manager': 'rpm',
                'application/x-sea': 'sea',
                'application/x-shockwave-flash': 'swf',
                'application/x-stuffit': 'sit',
                'application/x-tcl': 'tcl',
                'application/x-x509-ca-cert': 'der',
                'application/x-xpinstall': 'xpi',
                'application/xhtml+xml': 'xhtml',
                'application/zip': 'zip',
                'application/octet-stream': 'bin',
                'audio/midi': 'mid',
                'audio/mpeg': 'mp3',
                'audio/ogg': 'ogg',
                'audio/x-realaudio': 'ra',
                'video/3gpp': '3gpp',
                'video/mpeg': 'mpeg',
                'video/quicktime': 'mov',
                'video/x-flv': 'flv',
                'video/x-mng': 'mng',
                'video/x-ms-asf': 'asx',
                'video/x-ms-wmv': 'wmv',
                'video/x-msvideo': 'avi',
                'video/mp4': 'm4v'
            }});


        $('#inputFile[data-showlistfile="true"]').change(function () {
            let fileListEl = $('#selectedFileList');
            fileListEl.children("ul").empty();
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

        requirejs(['page/'+pageId], function(page) {
            console.log(page)
        });
    });
}());