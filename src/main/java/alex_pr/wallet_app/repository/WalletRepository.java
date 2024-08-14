package alex_pr.wallet_app.repository;

import alex_pr.wallet_app.entity.UserEntity;
import alex_pr.wallet_app.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Repository interface for {@link WalletEntity}.
 * Provides CRUD operations and query methods for {@link WalletEntity}.
 */
public interface WalletRepository extends JpaRepository<WalletEntity, Integer> {

    Optional<WalletEntity> findById(Integer id);
}
