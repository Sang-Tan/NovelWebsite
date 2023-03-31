package model.intermediate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;
@Embeddable

public class RestrictionKey implements Serializable {
    @Column(name = "restricted_user_id")
    int restrictedUserId;

    @Column(name = "restricted_type")
    String restrictedType;

}
