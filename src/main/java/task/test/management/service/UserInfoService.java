package task.test.management.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import task.test.management.entity.UserInfo;

import java.math.BigDecimal;
import java.util.List;

public interface UserInfoService extends UserDetailsService {
    List<UserInfo> getAllUsers();

    BigDecimal getUserBalance(String username);

    @Transactional
    void saveUserInfo(UserInfo userInfo);

    @Transactional
    void changeBlockedStatus(String username, boolean newStatus);

    @Transactional
    void depositMoney(String username, BigDecimal amount);

    @Transactional
    void withdrawMoney(String username, BigDecimal amount);
}
