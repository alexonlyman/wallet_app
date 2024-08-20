package alex_pr.wallet_app.repository;

import alex_pr.wallet_app.entity.WalletEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;
/**
 * Repository interface for {@link WalletEntity}.
 * Provides CRUD operations and query methods for {@link WalletEntity}.
 */
public interface WalletRepository extends JpaRepository<WalletEntity, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<WalletEntity> findWalletEntityById(Integer id);
}
