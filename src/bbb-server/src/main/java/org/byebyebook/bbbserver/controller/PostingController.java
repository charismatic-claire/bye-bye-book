package org.byebyebook.bbbserver.controller;

import java.util.List;

import org.byebyebook.bbbserver.config.BbbConstants;
import org.byebyebook.bbbserver.exception.ResourceNotFoundException;
import org.byebyebook.bbbserver.model.Posting;
import org.byebyebook.bbbserver.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@CrossOrigin
@RestController
public class PostingController
{

    @Autowired
    private PostingService service;

    @GetMapping(BbbConstants.POSTINGS_ROUTE)
    public List<Posting> getPostings()
    {
        log.info("Returning list of all postings");
        return service.getAll();
    }

    @GetMapping(BbbConstants.POSTING_ROUTE)
    public Posting getPosting(@PathVariable final Long id)
    {
        log.info("Looking for posting with id <{}>", id);
        try
        {
            return service.getById(id);
        }
        catch (final ResourceNotFoundException ex)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No posting with id <" + id + "> found", ex);
        }
    }

    @PostMapping(BbbConstants.POSTINGS_ROUTE)
    public Posting savePosting(@RequestBody final Posting posting)
    {
        log.info("Saving posting from <{}>", posting.getFromName());
        return service.savePosting(posting);
    }

}
