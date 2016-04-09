package com.mimiczo.domain.system;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    //시스템관리자
    ADMIN
    //기업회원
    , CORP
    //일반사용자
    , USER;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}