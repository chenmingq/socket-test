package com.github.chenmingq.msg.template;

import com.github.chenmingq.common.utils.DateUtil;
import com.google.common.collect.Lists;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : chengmingqin
 * @date : 2019/11/21 13:31
 * description :
 */
public class Main {

    /**
     * 消息id文件目录
     */
    private static final String ID_PATH = "./idMessage/";

    /**
     * 消息文件目录
     */
    private static final String MSG_PATH = "./message/";

    /**
     * 读取本地xml文件目录
     */
    private static final String PATH = "msg-id";

    private static final String USER_NAME = System.getenv("USERNAME");


    public static void main(String[] args) {
        String in = System.getProperty("in");
        if (null == in) {
            URL source = Thread.currentThread().getContextClassLoader().getResource(PATH);
            if (null == source) {
                throw new RuntimeException("the source is not");
            }
            in = source.getPath();
        }
        readStringXml(in);
    }

    private static void readStringXml(String path) {
        SAXReader reader = new SAXReader();
        File[] files = new File(path).listFiles();
        if (null == files) {
            throw new NullPointerException("文件目录不存在: " + path);
        }
        Set<Integer> moduleIds = new HashSet<>();
        int xmlFileSize = 0;
        for (File xmlFile : files) {
            String xmlFileName = xmlFile.getName();
            String suffixName = xmlFileName.substring(xmlFileName.length() - 3);
            if (!"xml".equalsIgnoreCase(suffixName)) {
                continue;
            }
            xmlFileSize++;
            System.out.println("发现文件-->" + xmlFileName);
            readAll(reader, xmlFile, moduleIds);
        }
        if (moduleIds.size() != xmlFileSize) {
            throw new RuntimeException("xml配置消息模块id有重复 ！！！");
        }
    }

    private static void readAll(SAXReader reader, File xmlFile, Set<Integer> moduleIds) {
        try {
            Document doc = reader.read(xmlFile);
            Map<String, String> rootNodeMap = new HashMap<>();

            //获取根节点
            Element root = doc.getRootElement();
            getNodeAttrs(root, rootNodeMap);
            List<SubNodeAttr> subNodeAttrs = getSubNodeAttrs(root);

            generateMsgId(rootNodeMap, xmlFile, moduleIds, subNodeAttrs);
            generateMessage(subNodeAttrs, rootNodeMap);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    /**
     * 生成消息id文件
     *
     * @param xmlFile
     * @param moduleIds
     */
    private static void generateMsgId(Map<String, String> rootNodeMap, File xmlFile, Set<Integer> moduleIds, List<SubNodeAttr> subNodeAttrs) {
        PrintWriter out = null;
        try {
            String aPackage = rootNodeMap.get("package");
            if (null == aPackage) {
                return;
            }
            int i = aPackage.lastIndexOf('.');
            aPackage = aPackage.substring(0, i) + ".id" + aPackage.substring(i, aPackage.length());
            String fileName = xmlFile.getName().replace(".xml", "");
            String moduleId = rootNodeMap.get("moduleId");
            if (null == moduleId) {
                throw new NullPointerException("moduleId IS NULL FILE " + xmlFile.getName());
            }

            int module = Integer.parseInt(moduleId);
            if (module < 100) {
                // 主要是为了在枚举值好区分
                throw new RuntimeException("moduleId 不要小于 100 " + xmlFile.getName());
            }

            String outPath = aPackage.replace(".", "/");
            outPath = ID_PATH + outPath;
            File file = new File(outPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            String moduleName = rootNodeMap.get("moduleName");

            StringBuilder readSbd = new StringBuilder("package ");
            readSbd.append(aPackage);
            readSbd.append(";\n\n");
            readSbd.append("import java.util.Arrays;");
            readSbd.append("\n\n");

            readSbd.append("/**\n ");
            readSbd.append("*");
            readSbd.append(" @author : ");
            readSbd.append(USER_NAME);
            readSbd.append("\n ");
            readSbd.append("*");
            readSbd.append(" @date : ");
            readSbd.append(DateUtil.format());
            readSbd.append("\n ");
            readSbd.append("*");
            readSbd.append(" caveat 不要手动修改 \n");
            readSbd.append(" *");
            readSbd.append(" description ");
            readSbd.append(rootNodeMap.get("desc"));
            readSbd.append("\n */\n");
            readSbd.append("public enum ");
            readSbd.append(fileName);
            readSbd.append(" {\n");

            readSbd.append("\n\t/**\n");
            readSbd.append("\t * ");
            readSbd.append(moduleName);
            readSbd.append("\n");
            readSbd.append("\t */");

            readSbd.append("\n\t");
            readSbd.append("MODULE_ID(");
            readSbd.append("\"");
            readSbd.append(moduleName);
            readSbd.append("\"");
            readSbd.append(", ");
            readSbd.append(module);
            readSbd.append("),");

            readSbd.append("\n\n");

            moduleIds.add(module);

            Set<Integer> cmdIds = new HashSet<>();

            for (SubNodeAttr subNodeAttr : subNodeAttrs) {

                readSbd.append("\n\t/**\n");
                readSbd.append("\t * ");
                readSbd.append(subNodeAttr.getDesc());
                readSbd.append("\n");
                readSbd.append("\t */");

                int cmdId = Integer.parseInt(subNodeAttr.getCmdId());
                readSbd.append("\n\t");
                readSbd.append(humpToLine(subNodeAttr.getName()));
                readSbd.append("(");
                readSbd.append("\"");
                readSbd.append(subNodeAttr.getDesc());
                readSbd.append("\"");
                readSbd.append(", ");
                readSbd.append(cmdId);
                readSbd.append("),");
                cmdIds.add(cmdId);
            }

            if (cmdIds.size() != subNodeAttrs.size()) {
                throw new RuntimeException(xmlFile.getName() + "配置cmdId有重复 ！！！");
            }

            readSbd.deleteCharAt(readSbd.length() - 1);
            readSbd.append(";");
            readSbd.append("\n\n");


            readSbd.append("\n\t/**\n");
            readSbd.append("\t * ");
            readSbd.append(moduleName);
            readSbd.append("\n");
            readSbd.append("\t */");

            readSbd.append("\n\t");
            readSbd.append("public static final int ");
            readSbd.append("MODULE_ID_VALUE");
            readSbd.append(" = ");
            readSbd.append(moduleId);
            readSbd.append(";");
            readSbd.append("\n");

            for (SubNodeAttr subNodeAttr : subNodeAttrs) {
                readSbd.append("\n\t/**\n");
                readSbd.append("\t * ");
                readSbd.append(subNodeAttr.getDesc());
                readSbd.append("\n");
                readSbd.append("\t */");

                readSbd.append("\n\t");
                readSbd.append("public static final int ");
                readSbd.append(humpToLine(subNodeAttr.getName()));
                readSbd.append("_VALUE");
                readSbd.append(" = ");
                readSbd.append(subNodeAttr.getCmdId());
                readSbd.append(";");
                readSbd.append("\n");
            }
            readSbd.append("\n");

            readSbd.append("\tprivate String desc;\n");
            readSbd.append("\tprivate int value;\n\n");
            readSbd.append("\tpublic String getDesc() {\n");
            readSbd.append("\t\treturn desc;\n");
            readSbd.append("\t}\n\n");
            readSbd.append("\tpublic int getValue() {\n");
            readSbd.append("\t\treturn value;\n");
            readSbd.append("\t}\n");
            readSbd.append("\n\t");
            readSbd.append(fileName);
            readSbd.append("(String desc, int value) {\n");
            readSbd.append("\t\tthis.desc = desc;\n");
            readSbd.append("\t\tthis.value = value;\n");
            readSbd.append("\t}");
            readSbd.append("\n\n");

            readSbd.append("\tpublic ");
            readSbd.append(fileName);
            readSbd.append(" valueOf(int val) { \n");
            readSbd.append("\t\treturn Arrays.stream(");
            readSbd.append(fileName);
            readSbd.append(".values()).filter(i -> val == i.value).findAny().orElse(null);");
            readSbd.append("\n\t}");
            readSbd.append("\n\n}");
            out = new PrintWriter(new FileWriter(outPath + "/" + fileName + ".java"));
            out.write(readSbd.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 生产消息文件
     *
     * @param subNodeAttrs
     */
    private static void generateMessage(List<SubNodeAttr> subNodeAttrs, Map<String, String> rootNodeMap) {
        String aPackage = rootNodeMap.get("package");
        if (null == aPackage) {
            return;
        }
        int i = aPackage.lastIndexOf('.');
        aPackage = aPackage.substring(0, i) + ".msg" + aPackage.substring(i, aPackage.length());
        String outPath = aPackage.replace(".", "/");
        outPath = MSG_PATH + outPath;
        File file = new File(outPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        for (SubNodeAttr subNodeAttr : subNodeAttrs) {

            PrintWriter out = null;

            StringBuilder readSbd = new StringBuilder("package ");
            readSbd.append(aPackage);
            readSbd.append(";\n\n");
            if (null != subNodeAttr.getMassageName()) {
                readSbd.append("import com.google.protobuf.InvalidProtocolBufferException;\n");
            }
            readSbd.append("import com.github.chenmingq.common.message.AbstractMessage;\n");
            readSbd.append("import lombok.Getter;\n");
            readSbd.append("import lombok.Setter;\n");
            readSbd.append("\n\n");

            readSbd.append("/**\n ");
            readSbd.append("*");
            readSbd.append(" @author : ");
            readSbd.append(USER_NAME);
            readSbd.append("\n ");
            readSbd.append("*");
            readSbd.append(" @date : ");
            readSbd.append(DateUtil.format());
            readSbd.append("\n ");
            readSbd.append("*");
            readSbd.append(" caveat 不要手动修改 \n");
            readSbd.append(" *");
            readSbd.append(" description ");
            readSbd.append(subNodeAttr.getDesc());
            readSbd.append("\n */\n");
            readSbd.append("@Setter\n");
            readSbd.append("@Getter\n");
            readSbd.append("public class ");
            readSbd.append(subNodeAttr.getName());
            readSbd.append(" extends AbstractMessage");
            readSbd.append(" {\n");

            readSbd.append("\n\t");

            if (null != subNodeAttr.getMassageName()) {
                readSbd.append("private ");
                readSbd.append(subNodeAttr.getMassageName());
                readSbd.append(" proto;\n\n");

                readSbd.append("\t@Override\n");
                readSbd.append("\tpublic void deCoder(byte[] body) {\n");
                readSbd.append("\t\ttry {\n");
                readSbd.append("\t\t\tthis.proto = ");
                readSbd.append(subNodeAttr.getMassageName());
                readSbd.append(".parseFrom(body);\n");
                readSbd.append("\t\t} catch (InvalidProtocolBufferException e) {\n");
                readSbd.append("\t\t\te.printStackTrace();\n");
                readSbd.append("\t\t}\n");
                readSbd.append("\t}\n");
                readSbd.append("\n");

                readSbd.append("\t@Override\n");
                readSbd.append("\tpublic byte[] enCoder() {\n");
                readSbd.append("\t\tif (null == this.proto) {\n");
                readSbd.append("\t\t\tthrow new NullPointerException(\"protoBuffer消息为null\");\n");
                readSbd.append("\t\t}\n");
                readSbd.append("\t\treturn this.proto.toByteArray();\n");
                readSbd.append("\t}\n");
            } else {
                readSbd.append("@Override\n");
                readSbd.append("\tpublic void deCoder(byte[] body) {\n");
                readSbd.append("\t}\n");
                readSbd.append("\n");

                readSbd.append("\t@Override\n");
                readSbd.append("\tpublic byte[] enCoder() {\n");
                readSbd.append("\t\treturn null;\n");
                readSbd.append("\t}\n");
            }

            String moduleId = rootNodeMap.get("moduleId");
            readSbd.append("\n\t@Override\n");
            readSbd.append("\tpublic int getModuleId() {\n");
            readSbd.append("\t\treturn ");
            readSbd.append(moduleId);
            readSbd.append(";\n");
            readSbd.append("\t}\n");
            readSbd.append("\n\t@Override\n");
            readSbd.append("\tpublic int getCmdId() {\n");
            readSbd.append("\t\treturn ");
            readSbd.append(subNodeAttr.getCmdId());
            readSbd.append(";\n");
            readSbd.append("\t}\n");

            readSbd.append("}");

            try {
                out = new PrintWriter(new FileWriter(outPath + "/" + subNodeAttr.getName() + ".java"));
                out.write(readSbd.toString());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
//            System.out.println(readSbd.toString());
        }
    }


    private static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线
     */
    private static String humpToLine(String str) {
        Matcher matcher = HUMP_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0));
        }
        matcher.appendTail(sb);
        return sb.toString().substring(1).toUpperCase();
    }

    /**
     * 获取所有节点的属性
     *
     * @param node
     * @param nodeMap
     */
    private static void getNodeAttrs(Element node, Map<String, String> nodeMap) {
        //当前节点的所有属性的list
        List<Attribute> listAttr = node.attributes();
        //遍历当前节点的所有属性
        for (Attribute attr : listAttr) {
            nodeMap.put(attr.getName(), attr.getValue());
        }
    }

    /**
     * 获取子节点属性
     *
     * @param node
     */
    private static List<SubNodeAttr> getSubNodeAttrs(Element node) {
        //递归遍历当前节点所有的子节点,;//所有一级子节点的list
        List<Element> listElement = node.elements();
        List<SubNodeAttr> resultList = Lists.newArrayList();
        Field[] fields = SubNodeAttr.class.getDeclaredFields();
        //遍历所有一级子节点
        for (Element e : listElement) {
            //递归
            List<Attribute> listAttr = e.attributes();
            SubNodeAttr subNodeAttr = new SubNodeAttr();
            //遍历当前节点的所有属性
            for (Attribute attr : listAttr) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    String name = attr.getName();
                    if (!name.equals(field.getName())) {
                        continue;
                    }
                    try {
                        field.set(subNodeAttr, attr.getValue());
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            List<Element> elements = e.elements();
            if (!elements.isEmpty()) {
                subNodeAttr.setMassageName(elements.get(0).getTextTrim());
            }
            resultList.add(subNodeAttr);
        }
        return resultList;
    }
}
