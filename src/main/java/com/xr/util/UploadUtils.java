package com.xr.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author: xr
 * @create: 2020/7/29
 * @describe:
 */
public class UploadUtils {
    public static String upload(MultipartFile file, String path, String fileName) throws Exception {
        try{
            // 生成新的文件名
            String realPath = path + "/" + UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
            File dest = new File(realPath);
            // 判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            // 保存文件
            file.transferTo(dest);
            return dest.getName();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
