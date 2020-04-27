package org.byebyebook.bbbserver.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FilenameUtils;
import org.byebyebook.bbbserver.config.BbbConstants;
import org.byebyebook.bbbserver.config.ImageStorageConfig;
import org.byebyebook.bbbserver.exception.ImageNotFoundException;
import org.byebyebook.bbbserver.exception.ImageStorageException;
import org.byebyebook.bbbserver.model.ImageUri;
import org.byebyebook.bbbserver.repository.ImageUriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ImageStorageService
{

    private final Path imageStorageLocation;

    @Autowired
    private ImageUriRepository imageUriRepository;

    @Autowired
    public ImageStorageService(final ImageStorageConfig config)
    {
        // get image storage location
        imageStorageLocation = Paths.get(config.getUploadDir())
            .toAbsolutePath()
            .normalize();
        // initialize storage directory
        try
        {
            Files.createDirectories(imageStorageLocation);
        }
        catch (final Exception ex)
        {
            throw new ImageStorageException("Could not create image upload directory", ex);
        }
    }

    private String digestFileName(final String fileName)
    {
        final String firstName = FilenameUtils.removeExtension(fileName);
        final String lastName = FilenameUtils.getExtension(fileName);
        String newName = fileName;
        try
        {
            final MessageDigest md = MessageDigest.getInstance("sha-512");
            md.update(firstName.getBytes());
            final byte[] digested = md.digest();
            newName = DatatypeConverter.printHexBinary(digested).substring(0, 32).toLowerCase() + "." + lastName.toLowerCase();
        }
        catch (final NoSuchAlgorithmException ex)
        {
            log.info("Could not digest <{}>, sorry.", fileName);
        }
        return newName;
    }

    private String safelyRename(final String filename)
    {
        String oldName = filename;
        String newName = oldName;
        try
        {
            UrlResource resource;
            do
            {
                newName = digestFileName(oldName);
                oldName = newName;
                resource = new UrlResource(imageStorageLocation.resolve(newName).normalize().toUri());
            }
            while (resource.exists());
        }
        catch (final Exception ex)
        {
            log.info("Something went wrong, never mind...");
        }
        return newName;
    }

    public ImageUri saveImage(final MultipartFile file)
    {
        final String origFileName = StringUtils.cleanPath(file.getOriginalFilename());
        final String newFileName = safelyRename(origFileName);
        final String fileType = file.getContentType();
        final Long size = file.getSize();
        // set download url
        final String url = ServletUriComponentsBuilder.fromPath("/")
            .path(BbbConstants.DOWNLOAD_ROUTE + "/")
            .path(newFileName)
            .toUriString();
        // store the file
        try
        {
            // no path outside the file storage location is allowed
            if (origFileName.contains(".."))
            {
                throw new ImageStorageException("\"..\" not allowed in file name <" + origFileName + ">");
            }
            // get target location
            final Path filePath = imageStorageLocation.resolve(newFileName);
            // check if file exists
            if (new UrlResource(filePath.normalize().toUri()).exists())
            {
                throw new ImageStorageException("Could not write file <" + newFileName + ">, file already exists");
            }
            // copy the file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (final IOException ex)
        {
            throw new ImageStorageException("Could not store file <" + origFileName + ">", ex);
        }
        // store the image uri and return
        final ImageUri imageUri = new ImageUri(url, fileType, size);
        return imageUriRepository.save(imageUri);
    }

    public Resource loadFileAsResource(final String fileName)
    {
        try
        {
            final Path filePath = imageStorageLocation.resolve(fileName).normalize();
            final Resource resource = new UrlResource(filePath.toUri());
            // return if file found
            if (resource.exists())
            {
                return resource;
            }
            // exception otherwise
            else
            {
                throw new ImageNotFoundException("Could not read file <" + fileName + ">");
            }
        }
        catch (final MalformedURLException ex)
        {
            throw new ImageNotFoundException("Could not read file <" + fileName + ">", ex);
        }
    }

    public String resolveImageStoragePath(final String url)
    {
        return url.replace("/image-download", StringUtils.cleanPath(imageStorageLocation.toString()));
    }

}
