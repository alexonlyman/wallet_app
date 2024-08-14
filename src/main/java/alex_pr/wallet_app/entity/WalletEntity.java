package alex_pr.wallet_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
/**
 * Represents a wallet entity in the database.
 * This entity maps to the {@code t_wallet} table in the {@code private_schema} schema.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_wallet", schema = "private_schema")
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "amount")
    private Integer amount;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;
}
