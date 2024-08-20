package alex_pr.wallet_app.controller;
import alex_pr.wallet_app.entity.WalletEntity;
import alex_pr.wallet_app.service.WalletService;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing wallets.
 * Handles requests for creating a wallet, depositing, withdrawing funds, and retrieving balance.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class WalletController {
    private final WalletService walletService;
    /**
     * Object for rate limiting.
     */
    private final Bucket bucket;

    /**
     * Creates a new wallet.
     *
     * @return ResponseEntity with the created wallet information and status CREATED (201) if successful,
     *         or BAD_REQUEST (400) if an error occurs.
     */
    @PostMapping
    public ResponseEntity<WalletEntity> createWallet() {
        if (bucket.tryConsume(1)) {
            try {
                return ResponseEntity.status(HttpStatus.CREATED).body(walletService.createWallet());
            } catch (Exception e) {
                throw new RuntimeException("Не удалось создать кошелек", e);
            }
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }
    /**
     * Deposits funds into a wallet.
     *
     * @param wallet_id The ID of the wallet.
     * @param amount The amount to deposit.
     * @return ResponseEntity with the new balance and status ACCEPTED (202) if successful,
     *         or NOT_FOUND (404) if the wallet is not found,
     *         or BAD_REQUEST (400) if an error occurs.
     */

    @PostMapping("/deposit/{wallet_id}")
    public ResponseEntity<Integer> deposit(@PathVariable Integer wallet_id,@RequestParam Integer amount) {
        if (bucket.tryConsume(1)) {
            try {
                walletService.deposit(wallet_id, amount);
               return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            } catch (Exception e) {
                throw new RuntimeException("Не удалось пополнить кошелек", e);
            }
        } else {
          return  ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    /**
     * Withdraws funds from a wallet.
     *
     * @param wallet_id The ID of the wallet.
     * @param amount The amount to withdraw.
     * @return ResponseEntity with the new balance and status ACCEPTED (202) if successful,
     *         or BAD_REQUEST (400) if the balance is negative,
     *         or NOT_FOUND (404) if the wallet is not found,
     *         or INTERNAL_SERVER_ERROR (500) if an error occurs.
     */

    @PostMapping("/withdraw/{wallet_id}")
    public ResponseEntity<Integer> withdraw(@PathVariable Integer wallet_id, @RequestParam Integer amount) {
        if (bucket.tryConsume(1)) {
            try {
                walletService.withdraw(wallet_id, amount);
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            } catch (Exception e) {
                throw new RuntimeException("Не удалось снять средства с кошелька", e);
            }
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    /**
     * Retrieves the balance of a wallet.
     *
     * @param wallet_id The ID of the wallet.
     * @return ResponseEntity with the balance and status ACCEPTED (202) if successful,
     *         or BAD_REQUEST (400) if an error occurs.
     */
    @GetMapping("{wallet_id}")
    public ResponseEntity<Integer> getBalance(@PathVariable Integer wallet_id) {
        if (bucket.tryConsume(1)) {
            try {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(walletService.balance(wallet_id));
            } catch (Exception e) {
                throw new RuntimeException("Не удалось получить баланс кошелька", e);
            }
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }
}
