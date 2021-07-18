package net.onlineutilities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.ui.Model;

@AllArgsConstructor
@Getter
public enum Pages {
    SVG_TO_PNG("ConvertSvgToPng", "svg-convert" ),
    SVG_TO_JPEG("ConvertSvgToJpeg", "svg-convert" ),
    SVG_TO_TIFF("ConvertSvgToTiff", "svg-convert" ),
    HEX_ENCODER("EncodeHexOnline", "hex-encode" ),
    HEX_DECODER("DecodeHexOnline", "hex-decode" ),
    URL_ENCODER("EncodeUrlOnline", "url-encoder" ),
    URL_DECODER("DecodeUrlOnline", "url-decoder" ),
    HTML_ENCODER("EncodeUrlOnline", "url-encoder" ),
    HTML_DECODER("DecodeUrlOnline", "url-decoder" ),
    SQL_FORMAT("FormatSqlOnline", "sql-format-online" ),
    CSS_FORMAT("FormatCssOnline", "css-format-online" ),
    XML_FORMAT("FormatXmlOnline", "xml-format-online" ),
    ;
    private String name;
    private String view;

    public void putUiModel(Model model){
        model.addAttribute("pageId", this.getView());
        model.addAttribute("pageName", this.getName());
    }
}
