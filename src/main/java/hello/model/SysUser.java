package hello.model;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



/**

 * Created by sang on 2017/1/10.

 */

@Entity

public class SysUser implements UserDetails {

    @Id

    @GeneratedValue

    private Long id;

    @NotEmpty(message="用户名不能为空！")
    @NotNull(message="用户名不能为空")
    
    private String username;

    private String password;

    @NotNull(message="电子邮件不能为空")
    @NotEmpty(message="电子邮件不能为空")
	@Email(message="电子邮件格式不对")
    private String email;
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    @NotNull(message="手机号不能为空")
    @NotEmpty(message="手机号不能为空")
	@Column(length=11)
    private String mobile;
    
    public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

    



    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)

    private List<SysRole> roles;



    public Long getId() {

        return id;

    }



    public void setId(Long id) {

        this.id = id;

    }



    public void setUsername(String username) {

        this.username = username;

    }



    public void setPassword(String password) {

        this.password = password;

    }



    public List<SysRole> getRoles() {

        return roles;

    }



    public void setRoles(List<SysRole> roles) {

        this.roles = roles;

    }



    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> auths = new ArrayList<>();

        List<SysRole> roles = this.getRoles();

        for (SysRole role : roles) {

            auths.add(new SimpleGrantedAuthority(role.getName()));

        }

        return auths;

    }



    @Override

    public String getPassword() {

        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPass = bcryptPasswordEncoder.encode(this.password);


        return hashPass;

    }



    @Override

    public String getUsername() {

        return this.username;

    }



    @Override

    public boolean isAccountNonExpired() {

        return true;

    }



    @Override

    public boolean isAccountNonLocked() {

        return true;

    }



    @Override

    public boolean isCredentialsNonExpired() {

        return true;

    }



    @Override

    public boolean isEnabled() {

        return true;

    }

}