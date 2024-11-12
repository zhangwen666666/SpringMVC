package com.zw.springmvc.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

@Controller
public class FileController {
    @RequestMapping(value = "/fileup", method = RequestMethod.POST)
    public String fileUp(@RequestParam("fileName") MultipartFile multipartFile, HttpServletRequest request) {
        String name = multipartFile.getName();
        System.out.println(name);
        String originalFilename = multipartFile.getOriginalFilename();
        System.out.println(originalFilename);

        // 一边读一边写。
        InputStream fileInputStream = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            fileInputStream = multipartFile.getInputStream();
            bis = new BufferedInputStream(fileInputStream);

            ServletContext application = request.getServletContext();
            String realPath = application.getRealPath("/upload");
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File destFile = new File(file.getAbsolutePath() + "/" + UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf(".")));
            bos = new BufferedOutputStream(new FileOutputStream(destFile));

            byte[] bytes = new byte[1024 * 10];
            int readCount = 0;
            while ((readCount = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, readCount);
            }
            bos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "ok";
    }


    @GetMapping(value = "/download")
    public ResponseEntity<byte[]> downloadFile(HttpServletRequest request) throws IOException {
        File file = new File(request.getServletContext().getRealPath("/upload") + "/97f43c88-1706-4f0d-a3e5-c3a06b5106e3.jpg");

        if(!file.exists()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", file.getName());
        return new ResponseEntity<>(Files.readAllBytes(file.toPath()), httpHeaders, HttpStatus.OK);
    }
}
