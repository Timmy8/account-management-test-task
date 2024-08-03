package task.test.management.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task.test.management.entity.UserInfo;
import task.test.management.entity.UserRole;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{
    UserInfo findUserInfoByUsername(String username);
    List<UserInfo> findUserInfoByRoleOrderByUsername(UserRole role);

    @Modifying
    @Query("update UserInfo u set u.balance = ?2 where u.username = ?1")
    void setBalanceAmountByUsername(String username, BigDecimal newAmount);

    @Modifying
    @Query("update UserInfo u set u.blocked = ?2 where u.username = ?1")
    void setBlockedByUsername(String username, boolean blocked);
}
