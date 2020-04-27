package org.byebyebook.bbbserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "postings")
public class PostingsPermissionConfig {

    private Boolean getRequestsAllowed;

}
