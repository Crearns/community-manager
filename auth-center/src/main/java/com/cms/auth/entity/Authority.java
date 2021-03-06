package com.cms.auth.entity;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * @author Creams
 */
public class Authority implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -1L;

    private String authorityId;

    private String authority;

    private String name;

    //用于标识是否分配权限
    private boolean selected = false;

    @Override
    public String getAuthority() {
        return authority;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
