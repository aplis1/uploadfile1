package com.example.uploadfile;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("hello")
public class UploadfileController {

    @Autowired
    AsposeUtility utility;

    @GetMapping("message")
    public ResponseEntity<String> hello(String msg){
        return new ResponseEntity<>("Hi there", HttpStatus.OK);
    }

    @PostMapping(value = "/file/add" , produces = { "application/json" })
    public ResponseEntity<Object> uploadFileRequest(@RequestBody MultipartFile multipartFile) throws IOException {
        List<HiddenData> hiddenDataList = null;
        if(!multipartFile.isEmpty()){
            File file = multipartToFile(multipartFile);

            try {
               hiddenDataList = utility.findHiddenData(file);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>(hiddenDataList, HttpStatus.OK);
    }

    public File multipartToFile(MultipartFile multipartFile) throws IllegalStateException, IOException
    {

        InputStream initialStream = multipartFile.getInputStream();
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        File targetFile = new File(multipartFile.getOriginalFilename());

        try (OutputStream outStream = new FileOutputStream(targetFile)) {
            outStream.write(buffer);
        }
        FileUtils.writeByteArrayToFile(targetFile, buffer);

        return targetFile;
    }
}
