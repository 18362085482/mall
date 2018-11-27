package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.common.ServerResponse;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
@Slf4j
public class FileServiceImpl implements IFileService {

    public ServerResponse<String> upload(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();
        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        log.info("开始上传文件，上传文件的文件名：{}，上传的路径：{},新文件名：{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);//设置可写权限
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件已经上传成功了
            //将targetFile上传到FTP服务器上
            boolean result = FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //上传完后，删除upload下的文件
            targetFile.delete();
            if(!result){
                return ServerResponse.createByErrorMessage("文件上传失败");
            }
        } catch (IOException e) {
            log.error("上传文件异常",e);
            return  null;
        }
        return  ServerResponse.createBySuccess(targetFile.getName());
    }
}
