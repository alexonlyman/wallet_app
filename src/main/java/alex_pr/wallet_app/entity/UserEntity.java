package alex_pr.wallet_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
/**
 * Represents a user entity in the database.
 * This entity maps to the {@code t_user} table in the {@code private_schema} schema.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user", schema = "private_schema")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @OneToMany
    private List<WalletEntity> walletEntities;
}
