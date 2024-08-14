package alex_pr.wallet_app.service.impl;

import alex_pr.wallet_app.entity.UserEntity;
import alex_pr.wallet_app.repository.UserRepository;
import alex_pr.wallet_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * Implementation of the UserService interface.
 * Provides methods to manage user-related operations.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public UserEntity findUser() {
        int id = 1;
        return userRepository.findUserEntityById(id);
    }

}
