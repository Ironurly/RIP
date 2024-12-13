package main.service;

import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import main.entity.User;
import main.repository.UserRepository;
import main.utils.MessageConstants;
import main.utils.HashUtils;
import main.utils.Result;
import main.utils.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Builder
public class UserService extends BaseService<User> {
    private final UserRepository userRepository;

    @PostConstruct
    public void postConstruct() throws NoSuchAlgorithmException {
        register(User.builder()
                .login("admin")
                .name("admin")
                .password("admin")
                .role(Role.ADMIN)
                .phone("89373186099")
                .build());
    }

    @Override
    public JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    /**
     * Регистрирует нового пользователя.
     * @param user пользователь
     * @return результат операции
     */
    public Result register(User user) {
        if (Objects.isNull(findByLogin(user.getLogin()))) {
            if (StringUtils.isAnyBlank(user.getLogin(), user.getName(), user.getPassword(), user.getPhone())) {
                return Result.builder().success(false).error(MessageConstants.MISSING_REQUIRED_FIELDS).build();
            }
            user.setPassword(HashUtils.hash(user.getLogin(), user.getPassword()));
            if (Objects.isNull(user.getRole())) {
                user.setRole(Role.USER);
            }
            userRepository.save(user);
            return Result.builder().success(true).build();
        }
        return Result.builder().success(false).error(MessageConstants.DUPLICATE_LOGIN.formatted(user.getLogin())).build();
    }

    /**
     * Находит пользователя по логину.
     * @param login логин
     * @return пользователь
     */
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
