package io.github.photozynthesis.utils;

import org.junit.Test;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.io.*;

/**
 * html 拼接器
 */
public class HtmlBuilder {

    private static String NAV_ITEM_BEGIN_TEMPLATE = "<li class=\"navItem\">\n" +
            "<span class=\"navItem-title\"><div class=\"navItem-title-arrow\">&gt;</div><div class=\"navItem-title-content\">${title}</div></span>\n";
    private static String NAV_ITEM_END_TEMPLATE = "</li>\n";
    private static String SUBLIST_ITEM_BEGIN_TEMPLATE = "<ul class=\"navItem-subList\">\n";
    private static String SUBLIST_ITEM_MAIN_TEMPLATE = "<li class=\"navItem-subList-item\" id=\"${parent}/${title}\"><a>${title}</a></li>\n";
    private static String SUBLIST_ITEM_END_TEMPLATE = "</ul>\n";

    private static Reader reader;

    /**
     * 通过当前文件层级 与 bodyHtml 生成最终 markdown html
     * @param curretLevel 文件层级：0-根目录， 1-一级目录
     * @param bodyHtml
     * @return
     */
    public static String buildMarkdownHtml(int curretLevel, String bodyHtml, String title) throws IOException {

        StringBuilder sb = new StringBuilder();
        String temp = null;

        // 读取 part-1
        reader = new BufferedReader(new InputStreamReader(HtmlBuilder.class.getResourceAsStream("/html-markdown/part-1.html"), "UTF-8"));
        while((temp = ((BufferedReader) reader).readLine()) != null) {
            sb.append(temp);
            sb.append("\n");
        }

        // 添加 title 和 link
        sb.append("<title>" + title + "</title>\n");
        sb.append("<link rel='stylesheet' type='text/css' href='");
        for (int i = 0; i < curretLevel + 1; i ++) {
            sb.append("../");
        }
        sb.append("css/markdown.css'>\n");

        // 拼接 part-2
        reader = new BufferedReader(new InputStreamReader(HtmlBuilder.class.getResourceAsStream("/html-markdown/part-2.html"), "UTF-8"));
        while((temp = ((BufferedReader) reader).readLine()) != null) {
            sb.append(temp);
            sb.append("\n");
        }

        // 拼接 body Html
        sb.append(bodyHtml).append("\n");

        // 拼接 part-3
        reader = new BufferedReader(new InputStreamReader(HtmlBuilder.class.getResourceAsStream("/html-markdown/part-3.html"), "UTF-8"));
        while((temp = ((BufferedReader) reader).readLine()) != null) {
            sb.append(temp);
            sb.append("\n");
        }

        return sb.toString();
    }

//    @Test
//    public void test() {
////        System.out.println(new File("src/main/resources/html-markdown").getName());
//        File file = new File("F:\\GitHub\\Programming-learning\\md");
//        try {
//            System.out.println(HtmlBuilder.buildIndexHtml(file));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 通过
     * @param htmlFolder
     * @return
     */
    public static String buildIndexHtml(File htmlFolder) throws IOException {

        StringBuilder sb = new StringBuilder();
        String temp = null;

        // 拼接头部
        reader = new BufferedReader(new InputStreamReader(HtmlBuilder.class.getResourceAsStream("/html-main/part-1.html"), "UTF-8"));
        while((temp = ((BufferedReader) reader).readLine()) != null) {
            sb.append(temp);
            sb.append("\n");
        }

        // 添加列表节点
        File[] rootFiles = htmlFolder.listFiles();
        for (File file : rootFiles) {
            if (file.isDirectory()) {
                // 拼接文件夹条目开头
                sb.append(NAV_ITEM_BEGIN_TEMPLATE.replace("${title}", file.getName()));
                // 拼接条目列表
                File[] itemFiles = file.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        if (name.endsWith(".md")) {
                            return true;
                        }
                        return false;
                    }
                });
                if (itemFiles.length > 0) {
                    sb.append(SUBLIST_ITEM_BEGIN_TEMPLATE);
                    for (File htmlFile : itemFiles) {
                        sb.append(SUBLIST_ITEM_MAIN_TEMPLATE.replace("${parent}", file.getName()).replace("${title}", htmlFile.getName().substring(0, htmlFile.getName().lastIndexOf("."))));
                    }
                    sb.append(SUBLIST_ITEM_END_TEMPLATE);
                }
                // 拼接文件夹条目结尾
                sb.append(NAV_ITEM_END_TEMPLATE);
            }
        }

        // 添加根目录文章节点
        File[] rootMdFiles = htmlFolder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".md")) {
                    return true;
                }
                return false;
            }
        });
        if (rootMdFiles.length > 0) {
            sb.append(SUBLIST_ITEM_BEGIN_TEMPLATE);
            for (File rootMdFile : rootMdFiles) {
                sb.append(SUBLIST_ITEM_MAIN_TEMPLATE.replace("${parent}/", "").replace("${title}", rootMdFile.getName().substring(0, rootMdFile.getName().lastIndexOf("."))));
            }
            sb.append(SUBLIST_ITEM_END_TEMPLATE);
        }

        // 拼接尾部
        reader = new BufferedReader(new InputStreamReader(HtmlBuilder.class.getResourceAsStream("/html-main/part-2.html"), "UTF-8"));
        while((temp = ((BufferedReader) reader).readLine()) != null) {
            sb.append(temp);
            sb.append("\n");
        }

        return sb.toString();
    }

}
