package io.github.photozynthesis.utils;

import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.io.*;
import java.util.Arrays;

/**
 * markdown 转换器
 */
public class MarkdownConvertor {

    private static Reader reader;

    /**
     * 将 Markdown 文件转成 html 字符串，结果只包括 body 中的内容
     * @param markdownFile
     * @return
     */
    public static String convertMarkdownToHtml(File markdownFile) throws IOException {

        // 设置 reader
        reader = new BufferedReader(new FileReader(markdownFile));

        //  解析 markdown
        MutableDataSet options = new MutableDataSet();
        options.setFrom(ParserEmulationProfile.MARKDOWN);
        // 添加插件
        options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), TocExtension.create(), SimTocExtension.create(), JekyllTagExtension.create()));
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        // 进行解析
        Node document = null;
        document = parser.parseReader(reader);
        String html = renderer.render(document);

        return html;
    }

}
