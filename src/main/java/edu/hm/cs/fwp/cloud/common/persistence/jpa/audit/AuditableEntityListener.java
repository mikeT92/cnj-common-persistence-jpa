package edu.hm.cs.fwp.cloud.common.persistence.jpa.audit;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.security.Principal;
import java.time.LocalDateTime;

/**
 * {@code JPA lifecycle listener} that tracks creation and modification of
 * {@link AbstractAuditableEntity}s.
 *
 * @author Michael Theis (michael.theis@hm.edu)
 * @version 1.0
 * @since release SS2019
 */
@Named
public class AuditableEntityListener {

    @Inject
    private Principal principal;

    @PrePersist
    public void onPrePersist(AbstractAuditableEntity entity) {
        entity.trackCreation(getAuthenticatedUserId(), LocalDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate(AbstractAuditableEntity entity) {
        entity.trackModification(getAuthenticatedUserId(), LocalDateTime.now());
    }

    private String getAuthenticatedUserId() {
        return this.principal != null ? this.principal.getName() : "anonymous";
    }

}
