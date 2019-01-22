package com.onion.service.impl;

import com.google.common.collect.Lists;
import com.onion.common.ServerResponse;
import com.onion.service.IFileService;
import com.onion.util.FTPUtil;
import com.onion.util.PropertiesUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service(value = "iFileService")
public class FileServiceImpl implements IFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    public ServerResponse<String> fileUpload(MultipartFile file) throws IOException {
        // 得到文件扩展名
        String extensionName = FilenameUtils.getExtension(file.getOriginalFilename());

        // 生成上传后的文件名
        String uploadFilename = UUID.randomUUID().toString().replaceAll("-","") + "." + extensionName;

        // 上传到tomcat目录中
        File targetFile = new File("/Users/liguolong/Desktop/tomcat-8.5/img/" + uploadFilename);
        file.transferTo(targetFile);
        LOGGER.info("图片上传到tomcat成功");



        // 上传到FTP服务器
        ServerResponse<String> response;
        boolean isSuccess = FTPUtil.uploadFile(Lists.newArrayList(targetFile));
        if(isSuccess){
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFile.getName();
            response = ServerResponse.createBySuccess(url);
        }else{
            response = ServerResponse.createByErrorMessage("上传失败");
        }
        LOGGER.info("图片上传到FTP服务器成功");

        // 删除tomat目录中的文件
        targetFile.delete();
        LOGGER.info("删除tomcat上的图片成功");

        return response;
    }
}
