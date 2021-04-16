package com.example.demo.dgut.util;

import org.commonmark.Extension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;


public class MarkdownUtil {
    // 使用基础功能
    public static String markdownToHtml(String markdown){
        // 新建一个解析器
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown) ;
        // 新建一个html代码渲染器
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        // 将解析出来的document进行渲染
        return renderer.render(document) ;
    }
}
