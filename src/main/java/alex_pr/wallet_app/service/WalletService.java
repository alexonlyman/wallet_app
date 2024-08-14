package alex_pr.wallet_app.service;

import alex_pr.wallet_app.entity.WalletEntity;
import alex_pr.wallet_app.exception.NegativeBalanceException;
import alex_pr.wallet_app.exception.WalletNotFoundException;
/**
 * Service interface for managing wallet-related operations.
 */
public interface WalletService {
    /**
     * Deposits a specified amount into the wallet.
     *
     * @param id the ID of the wallet
     * @param amount the amount to deposit
     * @return the new balance of the wallet
     * @throws WalletNotFoundException if the wallet with the specified ID is not found
     */
    Integer deposit(Integer id,Integer amount) throws WalletNotFoundException;

    /**
     * Withdraws a specified amount from the wallet.
     *
     * @param id the ID of the wallet
     * @param amount the amount to withdraw
     * @return the new balance of the wallet
     * @throws NegativeBalanceException if the withdrawal would result in a negative balance
     * @throws WalletNotFoundException if the wallet with the specified ID is not found
     */
    Integer withdraw(Integer id,Integer amount) throws NegativeBalanceException, WalletNotFoundException;

    /**
     * Creates a new wallet associated with the current user.
     *
     * @return the created WalletEntity
     */
    WalletEntity createWallet();

    /**
     * Retrieves the balance of the wallet.
     *
     * @param id the ID of the wallet
     * @return the balance of the wallet
     * @throws WalletNotFoundException if the wallet with the specified ID is not found
     */
    Integer balance(Integer id) throws WalletNotFoundException;
}
