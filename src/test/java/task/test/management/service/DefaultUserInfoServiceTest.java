package task.test.management.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import task.test.management.entity.UserInfo;
import task.test.management.entity.UserRole;
import task.test.management.repositoriy.UserInfoRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Модульные тесты DefaultUserInfoService")
class DefaultUserInfoServiceTest {
    @Mock
    UserInfoRepository repository;

    @InjectMocks
    DefaultUserInfoService service;

    @Test
    void loadUserByUsername_withExistingUser_ReturnUserInfo() {
        //given
        var user = new UserInfo("user", "user", UserRole.USER);

        doReturn(user).when(repository).findUserInfoByUsername("user");

        //when
        var result = service.loadUserByUsername("user");

        //then
        assertEquals(user, result);

        verify(repository).findUserInfoByUsername("user");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findUserByUsername_withNotExistingUser_ReturnBadRequestAndCorrectView() {
        //given
        doReturn(null).when(repository).findUserInfoByUsername("user");

        //then
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("user"));

    }

    @Test
    void getAllUsers_returnsValidUsersList() {
        //given
        var users = List.of(
                new UserInfo("user1", "user1", UserRole.USER),
                new UserInfo("user2", "user2", UserRole.USER)
        );
        doReturn(users).when(repository).findUserInfoByRoleOrderByUsername(UserRole.USER);

        //when
        var result = service.getAllUsers();

        //then
        assertEquals(users, result);

        verify(repository).findUserInfoByRoleOrderByUsername(UserRole.USER);
        verifyNoMoreInteractions(repository);

    }

    @Test
    void getUserBalance_ReturnsValidUserBalance() {
        //given
        var user = new UserInfo("user", "user", UserRole.USER);
        user.setBalance(new BigDecimal(100));
        doReturn(user).when(repository).findUserInfoByUsername("user");

        //when
        var result = service.getUserBalance("user");

        //then
        assertEquals(new BigDecimal(100), result);

        verify(repository).findUserInfoByUsername("user");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void saveUserInfo_withNotExistingUser() {
        //given
        var user = new UserInfo("user", "user", UserRole.USER);
        doReturn(null).when(repository).findUserInfoByUsername("user");

        //when
        service.saveUserInfo(user);

        //then

        verify(repository).findUserInfoByUsername("user");
        verify(repository).save(user);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void saveUserInfo_withExistingUser() {
        //given
        var user = new UserInfo("user", "user", UserRole.USER);
        doReturn(user).when(repository).findUserInfoByUsername("user");

        //when
        assertThrows(RuntimeException.class, () -> service.saveUserInfo(user));
    }

    @Test
    void changeBlockedStatus() {
        //when
        service.changeBlockedStatus("user1", true);

        //then
        verify(repository).setBlockedByUsername("user1", true);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void depositMoney_withValidAmount() {
        //given
        var user = new UserInfo("user", "user", UserRole.USER);
        user.setBalance(new BigDecimal(100));
        user.setBlocked(false);
        doReturn(user).when(repository).findUserInfoByUsername("user");

        //when
        service.depositMoney("user", new BigDecimal(100));

        //then

        verify(repository).setBalanceAmountByUsername("user", new BigDecimal(200));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void depositMoney_withBlockedUser_shouldThrowsRuntimeException() {
        //given
        var user = new UserInfo("user", "user", UserRole.USER);
        user.setBlocked(true);

        //then
        assertThrows(RuntimeException.class, () -> service.depositMoney("user", new BigDecimal(100)));
    }

    @Test
    void withdrawMoney_withEnoughFounds() {
        //given
        var user = new UserInfo("user", "user", UserRole.USER);
        user.setBalance(new BigDecimal(200));
        user.setBlocked(false);
        doReturn(user).when(repository).findUserInfoByUsername("user");

        //when
        service.withdrawMoney("user", new BigDecimal(100));

        //then

        verify(repository).setBalanceAmountByUsername("user", new BigDecimal(100));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void withdrawMoney_withBlockedUser() {
        //given
        var user = new UserInfo("user", "user", UserRole.USER);
        user.setBlocked(true);

        //then
        assertThrows(RuntimeException.class, () -> service.withdrawMoney("user", new BigDecimal(100)));
    }

    @Test
    void withdrawMoney_withNotEnoughFounds_shouldThrowsRuntimeException() {
        //given
        var user = new UserInfo("user", "user", UserRole.USER);
        user.setBalance(new BigDecimal(100));

        //then
        assertThrows(RuntimeException.class, () -> service.withdrawMoney("user", new BigDecimal(200)));
    }
}