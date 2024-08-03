package task.test.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Table(schema = "account_management", name = "user_info")
public class UserInfo implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username")
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "password")
    @NotNull
    @Size(min = 4, max = 20)
    private String password;

    @Column(name = "blocked")
    @NotNull
    private boolean blocked;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @NotNull
    private UserRole role;

    @Column(name = "balance")
    @NotNull
    private BigDecimal balance;

    public UserInfo(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.blocked = false;
        this.balance = new BigDecimal(0);
    }

    public UserInfo() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
