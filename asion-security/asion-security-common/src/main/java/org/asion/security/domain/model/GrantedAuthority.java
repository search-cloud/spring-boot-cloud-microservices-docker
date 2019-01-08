package org.asion.security.domain.model;

import java.io.Serializable;

public interface GrantedAuthority extends Serializable {
    String getAuthority();
}