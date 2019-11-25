package com.github.chenmingq.server.basic.common.banner;


import com.github.chenmingq.common.constant.CommonConst;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;

@Slf4j
public class Banner {


    public static void startBanner() {
        InputStream resourceAsStream = Banner.class.getClassLoader().getResourceAsStream(CommonConst.START_BANNER_NAME);
        if (resourceAsStream == null) {
            return;
        }
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new InputStreamReader(resourceAsStream));

            String bannerLineStr;
            // 一次读入一行，直到读入null为文件结束
            System.out.println("\n");
            while ((bannerLineStr = reader.readLine()) != null) {
                System.out.println("\t " + bannerLineStr);
            }
            System.out.println("\n");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * bufferedReader
     *
     * @return
     * @throws FileNotFoundException
     */
    private static BufferedReader bufferedReader() throws FileNotFoundException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(CommonConst.START_BANNER_NAME);
        if (null == resource) {
            return null;
        }
        File file = new File(resource.getFile());
        return new BufferedReader(new FileReader(file));
    }
}
