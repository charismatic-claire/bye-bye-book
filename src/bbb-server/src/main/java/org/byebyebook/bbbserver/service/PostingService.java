package org.byebyebook.bbbserver.service;

import lombok.extern.slf4j.Slf4j;
import org.byebyebook.bbbserver.config.PostingsPermissionConfig;
import org.byebyebook.bbbserver.exception.ResourceNotFoundException;
import org.byebyebook.bbbserver.model.ImageUri;
import org.byebyebook.bbbserver.model.Posting;
import org.byebyebook.bbbserver.repository.ImageUriRepository;
import org.byebyebook.bbbserver.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostingService {

    private final Boolean getRequestsAllowed;

    @Autowired
    public PostingService(PostingsPermissionConfig config) {
        getRequestsAllowed = config.getGetRequestsAllowed();
    }

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private ImageUriRepository imageUriRepository;

    public Posting getById(Long id) {
        if(getRequestsAllowed) {
            return postingRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("No posting with id <" + id + ">"));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied. Could not get post");
        }
    }

    public List<Posting> getAll() {
        if(getRequestsAllowed) {
            return postingRepository
                    .findAll()
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());
        }
        else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied. Could not get posts");
    }

    public Posting savePosting(Posting posting) {
        try {
            log.info("Checking for pictures");
            // find image uris
            List<ImageUri> imageUris = posting.getImageIds()
                    .stream()
                    .map(id -> imageUriRepository.findById(id).get())
                    .collect(Collectors.toList());
            // set image uris
            posting.setImageUris(imageUris);
            // set posting
            imageUris.forEach(uri -> uri.setPosting(posting));
        } catch(NullPointerException ignored) {
            log.info("No pictures found");
        }
        // save and return
        return postingRepository.save(posting);
    }

}
