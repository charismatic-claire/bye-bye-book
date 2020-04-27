package org.byebyebook.bbbserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.byebyebook.bbbserver.config.BbbConstants;
import org.byebyebook.bbbserver.model.ImageUri;
import org.byebyebook.bbbserver.service.ImageStorageService;
import org.byebyebook.bbbserver.service.ImageUriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class ImageController {

    @Autowired
    private ImageStorageService storageService;

    @Autowired
    private ImageUriService uriService;

    @PostMapping(BbbConstants.UPLOAD_ROUTE)
    public ImageUri uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("Saving image with file name <{}>", StringUtils.cleanPath(file.getOriginalFilename()));
        return storageService.saveImage(file);
    }

    @GetMapping(BbbConstants.DOWNLOAD_ROUTE + "/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        log.info("Returning image with file name <{}>", fileName);
        // get file as resource
        Resource resource = storageService.loadFileAsResource(fileName);
        // determine content type
        String contentType = null;
        try {
            contentType = request
                    .getServletContext()
                    .getMimeType(resource.getFile().getAbsolutePath());
        } catch(IOException ex) {
            log.info("Could not determine file type of file <" + fileName + ">");
            contentType = "application/octet-stream";
        }
        // return result
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/image-uris")
    public List<ImageUri> getImageUris() {
        log.info("Returning list of all image uris");
        return uriService.getAll();
    }

    @GetMapping("/image-uris/{id}")
    public ImageUri getImageUri(@PathVariable Long id) {
        log.info("Looking for image uri with id <{}>", id);
        return uriService.getById(id);
    }

}
