package alex_pr.wallet_app.repository;

import alex_pr.wallet_app.entity.UserEntity;
import alex_pr.wallet_app.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
/**
 * Repository interface for {@link UserEntity}.
 * Provides CRUD operations and query methods for {@link UserEntity}.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findUserEntityById(Integer id);
}
