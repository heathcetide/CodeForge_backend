package com.cetide.codeforge.service.impl;


import com.cetide.codeforge.service.FileService;
import com.cetide.codeforge.util.JsonUtil;
import com.cetide.codeforge.util.TemporaryUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    TemporaryUtil temporaryUtil;

    @Resource
    JsonUtil jsonUtil;

    public String createTemporaryKey() {
        return jsonUtil.convertToJson(temporaryUtil.getTemporaryCredentials());
    }
}
