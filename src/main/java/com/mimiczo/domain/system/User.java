package com.mimiczo.domain.system;

import com.mimiczo.support.helper.JoseJwtHelper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class User implements UserDetails {
	private static final long serialVersionUID = -5029098401129731778L;

	@Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;
	
    @Column(nullable = false, unique = true, length=50)
    private String username;
    @Column(nullable = false, length=100)
    private String password;
    @Column(nullable = false, length=20)
    private String name;
    @Column(nullable = false, length=200)
    private String token;
    @Column(nullable = false, columnDefinition="BOOLEAN default 1")
    private boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    private Date signDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @PrePersist
    public void prePersist() {
    	createDate = new Date();
    }
    
    /**********************************
     * 연관관계
     **********************************/

	/******************************************
     * Authority Setting
     ******************************************/
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private List<Authority> authorities;
    
    protected User(Authority role) {
        this.authorities = new ArrayList<>();
        this.authorities.add(Authority.USER);
        if(role!=null) this.authorities.add(role);
    }

    @Autowired
    @Transient
    JoseJwtHelper joseJwtHelper;

    public User(String username, String password, Authority role) {
        this(role);
        Assert.hasText(username, "user.required.username");
        Assert.hasText(password, "user.required.password");

        this.username = username;
        this.password = password;
        this.token = joseJwtHelper.create(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableList(this.authorities);
    }

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
