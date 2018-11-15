package com.mmall.service;

import com.mmall.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    ServerResponse<String> upload(MultipartFile file, String path);
}
