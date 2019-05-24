package io.github.photozynthesis;

import io.github.photozynthesis.utils.HtmlBuilder;
import io.github.photozynthesis.utils.MarkdownConvertor;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * 博客生成器
 * 兼做入口类
 */
public class BlogGenerator {

    private static Writer writer;

    /**
     * 入口方法，调用即根据 md 文件夹生成 html 文件夹和 index.html
     * @param args
     */
    public static void main(String[] args) {

        // 1. 生成 index.html
        // 获取工作空间，并检测 md 文件夹的存在
        File workspace = new File(System.getProperty("user.dir")).getParentFile();
        File folder_md = new File(workspace, "md");
        if (!folder_md.exists()) {
            System.out.println("[BlogTools] 工程还没有 md 文件夹，请先创建。");
            System.exit(0);
        }
        // 生成 index.html 内容
        String str_index_html = null;
        try {
            str_index_html = HtmlBuilder.buildIndexHtml(folder_md);
        } catch (IOException e) {
            System.out.println("[ERROR] index.html 内容生成过程中出错。");
            e.printStackTrace();
            System.exit(0);
        }
        // 确保 index.html 存在，清空内容，并设置输出流
        File file_index_html = new File(workspace, "index.html");
        if (file_index_html.exists()) {
            try {
                // 清空
                writer = new BufferedWriter(new FileWriter(file_index_html));
                writer.write("");
                writer.flush();
            } catch (IOException e) {
                System.out.println("[ERROR] 清空 index.html 中出错。");
                e.printStackTrace();
                System.exit(0);
            }
        } else {
            try {
                file_index_html.createNewFile();
                writer = new BufferedWriter(new FileWriter(file_index_html));
            } catch (IOException e) {
                System.out.println("[ERROR] 创建 index.html 过程中出错。");
                e.printStackTrace();
                System.exit(0);
            }
        }
        // 写入内容
        try {
            writer.write(str_index_html);
            writer.flush();
        } catch (IOException e) {
            System.out.println("[ERROR] 向 index.html 写入内容时出错。");
            e.printStackTrace();
            System.exit(0);
        }

        // 2. 生成 html 文件夹及子文件
        // 判断 html 文件夹的存在，确保存在并清空内容
        File folder_html = new File(workspace, "html");
        if (folder_html.exists()) {
            try {
                FileUtils.cleanDirectory(folder_html);
            } catch (IOException e) {
                System.out.println("[ERROR] 清理 html 文件夹时出错。");
                e.printStackTrace();
                System.exit(0);
            }
        } else {
            folder_html.mkdir();
        }
        // 遍历 md 文件夹，遇到文件夹，生成相同目录；遇到 md 文件，生成 html；遇到其他文件，直接复制
        File[] folder_md_files = folder_md.listFiles();
        for (File folder_md_file : folder_md_files) {
            if (folder_md_file.isDirectory()) {
                // 是文件夹
                // 在目标文件夹创建文件夹
                File folderInHtml = new File(folder_html, folder_md_file.getName());
                if (!folderInHtml.exists()) {
                    folderInHtml.mkdir();
                }
                File[] subFiles = folder_md_file.listFiles();
                for (File subFile : subFiles) {
                    // 目前不考虑两级以上目录
                    if (subFile.isFile()) {
                        if (subFile.getName().toLowerCase().endsWith(".md")) {
                            // 是 md 文件（二级目录）
                            String fileName = subFile.getName().substring(0, subFile.getName().lastIndexOf("."));
                            String html = null;
                            try {
                                String html_body = MarkdownConvertor.convertMarkdownToHtml(subFile);
                                html = HtmlBuilder.buildMarkdownHtml(1, html_body, fileName);
                            } catch (IOException e) {
                                System.out.println("[ERROR] 在转换 md 文件时出现问题。");
                                e.printStackTrace();
                                System.exit(0);
                            }
                            File htmlFile = new File(folderInHtml, fileName + ".html");
                            try {
                                if (!htmlFile.exists()) {
                                    htmlFile.createNewFile();
                                }
                                writer = new BufferedWriter(new FileWriter(htmlFile));
                                writer.write(html);
                                writer.flush();
                            } catch (IOException e) {
                                System.out.println("[ERROR] 创建文章 html 文件时出错。");
                                e.printStackTrace();
                                System.exit(0);
                            }
                        } else {
                            // 是其他文件（二级目录）
                            try {
                                FileUtils.copyFileToDirectory(subFile, folderInHtml);
                            } catch (IOException e) {
                                System.out.println("[ERROR] 复制文件时出错。");
                                e.printStackTrace();
                                System.exit(0);
                            }
                        }
                    }
                }
            } else {
                if (folder_md_file.getName().toLowerCase().endsWith(".md")) {
                    // 是 md 文件（根目录）
                    String fileName = folder_md_file.getName().substring(0, folder_md_file.getName().lastIndexOf("."));
                    String html = null;
                    try {
                        String html_body = MarkdownConvertor.convertMarkdownToHtml(folder_md_file);
                        html = HtmlBuilder.buildMarkdownHtml(0, html_body, fileName);
                    } catch (IOException e) {
                        System.out.println("[ERROR] 在转换 md 文件时出现问题。");
                        e.printStackTrace();
                        System.exit(0);
                    }
                    File htmlFile = new File(folder_html, fileName + ".html");
                    try {
                        if (!htmlFile.exists()) {
                            htmlFile.createNewFile();
                        }
                        writer = new BufferedWriter(new FileWriter(htmlFile));
                        writer.write(html);
                        writer.flush();
                    } catch (IOException e) {
                        System.out.println("[ERROR] 创建文章 html 文件时出错。");
                        e.printStackTrace();
                        System.exit(0);
                    }
                } else {
                    // 是其他文件（根目录）
                    try {
                        FileUtils.copyFileToDirectory(folder_md_file, folder_html);
                    } catch (IOException e) {
                        System.out.println("[ERROR] 复制文件时出错。");
                        e.printStackTrace();
                        System.exit(0);
                    }
                }
            }
        }

        System.out.println("[SUCCESS] 目标文件已成功生成。");

    }

}
