package it.develhope.Servizi.File.Upload.controller;

import it.develhope.Servizi.File.Upload.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public List<String> upload(@RequestParam MultipartFile[] files) throws IOException {
        List<String> fileNames =new ArrayList<>();
        for (MultipartFile file: files) {
            String singleUploadedFileName =fileService.upload(file);
            fileNames.add(singleUploadedFileName);
        }
        return fileNames;
    }

    @GetMapping("/download")
    public @ResponseBody byte[] download(@RequestParam String fileName, HttpServletResponse response) throws Exception{
        System.out.println("Downloading : "+ fileName);
        String extension =  FilenameUtils.getExtension(fileName);
        switch (extension){
            case"gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
        return fileService.download(fileName);
    }

}
