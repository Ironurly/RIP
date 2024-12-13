package main.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import main.entity.Session;
import main.entity.User;
import main.repository.SessionRepository;
import main.utils.MessageConstants;
import main.utils.HashUtils;
import main.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Builder
public class SessionService {
    private final SessionRepository sessionRepository;

    private final UserService userService;

    /**
     * Производит попытку логина пользователя.
     * @param login логин
     * @param password пароль
     * @return результат попытки логина
     */
    public Result login(String login, String password) {
        User user = userService.findByLogin(login);
        if (Objects.isNull(user)) {
            return Result.builder().success(false).error(MessageConstants.WRONG_LOGIN_PASSWORD).build();
        }
        String existingHash = user.getPassword();
        String loginHash = HashUtils.hash(login, password);
        if (Objects.equals(existingHash, loginHash)) {
            Session session = Session.builder()
                    .userId(user.getId())
                    .beginDate(new Date(System.currentTimeMillis()))
                    .build();
            sessionRepository.save(session);
            return Result.builder().success(true).build();
        } else {
            return Result.builder().success(false).error(MessageConstants.WRONG_LOGIN_PASSWORD).build();
        }
    }

    /**
     * Производит logout пользователя.
     * @param login логин пользователя
     * @return {@code true} если logout был успешным, @{code false} иначе.
     */
    public boolean logout(String login) {
        User user = userService.findByLogin(login);
        if (Objects.isNull(user)) {
            return false;
        }
        Session session = sessionRepository.findByUserId(user.getId());
        if (Objects.isNull(session)) {
            return false;
        } else {
            sessionRepository.delete(session);
            return true;
        }
    }

    /**
     * @return пользователь, который сейчас находится в системе
     */
    public User getCurrentUser() {
        List<Session> session = sessionRepository.findAll();
        if (session.isEmpty()) {
            return null;
        }
        Long userId = session.get(0).getUserId();
        return userService.findById(userId).orElseThrow();
    }

    public void setRole(Model model) {
        User user = getCurrentUser();
        if (Objects.isNull(user)) {
            model.addAttribute("role", "GUEST");
        } else {
            model.addAttribute("role", user.getRole().toString());
            model.addAttribute("name", user.getName());
        }
    }
}
