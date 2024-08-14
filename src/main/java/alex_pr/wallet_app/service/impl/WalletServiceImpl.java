package alex_pr.wallet_app.service.impl;

import alex_pr.wallet_app.entity.UserEntity;
import alex_pr.wallet_app.entity.WalletEntity;
import alex_pr.wallet_app.exception.NegativeBalanceException;
import alex_pr.wallet_app.exception.WalletNotFoundException;
import alex_pr.wallet_app.repository.WalletRepository;
import alex_pr.wallet_app.service.UserService;
import alex_pr.wallet_app.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the WalletService interface.
 * Provides methods to manage wallet-related operations.
 */
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserService userService;
    /**
     * Deposits a specified amount into a wallet.
     *
     * @param id     the ID of the wallet to deposit into
     * @param amount the amount to deposit
     * @return the new balance of the wallet
     * @throws WalletNotFoundException if the wallet is not found
     */

    @Override
    public Integer deposit(Integer id, Integer amount) throws WalletNotFoundException {

            WalletEntity wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("кошелек не найден"));
            Integer currentAmount = wallet.getAmount();
            Integer balance = currentAmount + amount;
            wallet.setAmount(balance);
            walletRepository.save(wallet);
            System.out.println("текущий баланс " + balance);
            return balance;
    }
    /**
     * Withdraws a specified amount from a wallet.
     *
     * @param id     the ID of the wallet to withdraw from
     * @param amount the amount to withdraw
     * @return the new balance of the wallet
     * @throws NegativeBalanceException if the withdrawal would result in a negative balance
     * @throws WalletNotFoundException   if the wallet is not found
     */
    @Override
    public Integer withdraw(Integer id, Integer amount) throws NegativeBalanceException, WalletNotFoundException {

        WalletEntity wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("кошелек не найден"));
        Integer currentAmount = wallet.getAmount();
        int balance = currentAmount - amount;
        if (balance >= 0) {
            wallet.setAmount(balance);
            walletRepository.save(wallet);
            System.out.println("текущий баланс " + balance);
            return balance;
        } else {
            throw new NegativeBalanceException("баланс отрицателен");

        }

    }

    /**
     * Creates a new wallet for the current user.
     *
     * @return the created WalletEntity
     */
    @Override
    public WalletEntity createWallet() {
        UserEntity user = userService.findUser();
        WalletEntity wallet = new WalletEntity();
        wallet.setAmount(0);
        wallet.setUser(user);
        walletRepository.save(wallet);
        return wallet;

    }

    /**
     * Retrieves the balance of a wallet.
     *
     * @param id the ID of the wallet
     * @return the current balance of the wallet
     * @throws WalletNotFoundException if the wallet is not found
     */
    @Override
    public Integer balance(Integer id) throws WalletNotFoundException {
        WalletEntity wallet = walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("кошелек не найден"));
        Integer amount = wallet.getAmount();
        System.out.println("баланс " + amount);
        return amount;
    }
}
