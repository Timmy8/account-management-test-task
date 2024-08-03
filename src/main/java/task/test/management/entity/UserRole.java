package task.test.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
