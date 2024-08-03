package task.test.management.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import task.test.management.entity.UserInfo;
import task.test.management.entity.UserRole;
import task.test.management.repositoriy.UserInfoRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class  DefaultUserInfoService implements UserInfoService{
    private final UserInfoRepository repository;

    public DefaultUserInfoService(UserInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = repository.findUserInfoByUsername(username);
        if (userInfo != null)
            return userInfo;
        else throw new UsernameNotFoundException("Can't find user '" + username +"'");
    }

    @Override
    public List<UserInfo> getAllUsers(){
        return repository.findUserInfoByRoleOrderByUsername(UserRole.USER);
    }

    @Override
    public BigDecimal getUserBalance(String username) {
        return repository.findUserInfoByUsername(username).getBalance();
    }

    @Override
    public void saveUserInfo(UserInfo userInfo) {
        if (repository.findUserInfoByUsername(userInfo.getUsername()) == null)
            repository.save(userInfo);
        else throw new RuntimeException("User already exists");
    }

    @Override
    public void changeBlockedStatus(String username, boolean newStatus) {
        repository.setBlockedByUsername(username, newStatus);
    }

    @Override
    public void depositMoney(String username, BigDecimal amount) {
        checkIfBlocked(username);
        var userBalance = repository.findUserInfoByUsername(username).getBalance();
        repository.setBalanceAmountByUsername(username, userBalance.add(amount));
    }

    @Override
    public void withdrawMoney(String username, BigDecimal amount){
        checkIfBlocked(username);
        var userBalance = repository.findUserInfoByUsername(username).getBalance();
        if (userBalance.compareTo(amount) >= 0)
            repository.setBalanceAmountByUsername(username, userBalance.subtract(amount));
        else throw new RuntimeException("There are not enough funds on the balance ");
    }

    private void checkIfBlocked(String username){
        if (repository.findUserInfoByUsername(username).isBlocked())
            throw  new RuntimeException("User blocked!");
    }
}
