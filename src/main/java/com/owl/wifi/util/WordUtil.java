package com.owl.wifi.util;
import java.io.*;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.owl.wifi.oss.util.OSSUtil;
import org.apache.commons.lang.time.DateUtils;
import sun.misc.BASE64Encoder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2017/6/26.
 */
public class WordUtil {
    //配置信息,代码本身写的还是很可读的,就不过多注解了
    private static Configuration configuration = null;
    //这里注意的是利用WordUtils的类加载器动态获得模板文件的位置
    private static final String templateFolder = WordUtil.class.getClassLoader().getResource("/templates/ftl").getPath();
    static {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(templateFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WordUtil() {
        throw new AssertionError();
    }

    public static String exportMillCertificateWord(HttpServletRequest request, HttpServletResponse response, Map map) throws IOException {
        Template freemarkerTemplate = configuration.getTemplate("test.ftl");
        File file = null;
        String ossFilePath = "";
        try {
            // 调用工具类的createDoc方法生成Word文档
            file = createDoc(map,freemarkerTemplate);
            ossFilePath = OSSUtil.uploadFile(file, "report_document");
        } finally {
            if(file != null){
                file.delete(); // 删除临时文件
            }
            return ossFilePath;
        }
    }

    private static File createDoc(Map<?, ?> dataMap, Template template) {
        long time = System.currentTimeMillis();
        String fileName = String.valueOf(time)+".doc";
        File f = new File(fileName);
        Template t = template;
        try {
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
            t.process(dataMap, w);
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return f;
    }
}
