package com.xr.web.admin;

import com.xr.util.UploadUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

/**
 * @author: xr
 * @create: 2020/7/30
 * @describe:
 */
@Controller
@RequestMapping("/admin")
public class UploadController {

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping(value = "/upload")
    public String post(@RequestParam(value = "firstPicture") MultipartFile file){
        // 图片路径
        String imgUrl = null;
        try {
            //上传
            String filename = UploadUtils.upload(file, uploadPath, file.getOriginalFilename());
            if (!filename.isEmpty()) {
                imgUrl = new File(uploadPath) + "/" + filename;
                return imgUrl;
            } else {
                return "error";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}
