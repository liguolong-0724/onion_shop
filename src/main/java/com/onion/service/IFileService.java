package com.onion.service;

import com.onion.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    ServerResponse<String> fileUpload(MultipartFile file) throws Exception;
}
