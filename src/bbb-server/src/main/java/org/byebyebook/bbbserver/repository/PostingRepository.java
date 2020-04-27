package org.byebyebook.bbbserver.repository;

import org.byebyebook.bbbserver.model.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Posting, Long> {

}
