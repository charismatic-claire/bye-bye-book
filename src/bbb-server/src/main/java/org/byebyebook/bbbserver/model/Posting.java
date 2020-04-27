package org.byebyebook.bbbserver.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String fromName;

    @Column(columnDefinition = "text", nullable = false)
    private String fromEmail;

    @Column(columnDefinition = "text", nullable = false)
    private String toName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "posting")
    private List<Story> stories;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "posting")
    private List<ImageUri> imageUris;

    @Transient
    private List<Long> imageIds;

    @Override
    public boolean equals(Object object) {
        if(this == object) return true;
        if(!(object instanceof Posting)) return false;
        Posting that = (Posting) object;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(this.getFromName(), that.getFromName());
        eb.append(this.getFromEmail(), that.getFromEmail());
        eb.append(this.getToName(), this.getToName());
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.getFromName());
        hcb.append(this.getFromEmail());
        hcb.append(this.getToName());
        return hcb.hashCode();
    }

}
