package doit.shop.repository.account;

import doit.shop.controller.account.domain.Account;
import doit.shop.controller.account.domain.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("Save 테스트")
    @Test
    void saveTest() {
        Account account1 = Account.builder()
                .accountNumber("1111-1111-1111")
                .accountName("강범서의 통장")
                .accountBankName("국민은행")
                .balance(10000000)
                .build();

        Account account2 = Account.builder()
                .accountNumber("2222-2222-2222")
                .accountName("조상래의 통장")
                .accountBankName("우리은행")
                .balance(20000000)
                .build();

        accountRepository.save(account1);
        accountRepository.save(account2);

        List<Account> accounts = accountRepository.findAll();
        Assertions.assertThat(accounts).hasSize(4);
        Assertions.assertThat(accounts.get(0).getAccountNumber()).isEqualTo("1111-1111-1111");
        Assertions.assertThat(accounts.get(0).getAccountName()).isEqualTo("강범서의 통장");
        Assertions.assertThat(accounts.get(0).getAccountBankName()).isEqualTo("국민은행");
        Assertions.assertThat(accounts.get(0).getBalance()).isEqualTo(10000000);

        Assertions.assertThat(accounts.get(1).getAccountNumber()).isEqualTo("2222-2222-2222");
        Assertions.assertThat(accounts.get(1).getAccountName()).isEqualTo("조상래의 통장");
        Assertions.assertThat(accounts.get(1).getAccountBankName()).isEqualTo("우리은행");
        Assertions.assertThat(accounts.get(1).getBalance()).isEqualTo(20000000);
    }

    @DisplayName("Save 후 아이디로 찾기 테스트")
    @Test
    void saveAndFindByIdTest() {
        Account account = Account.builder()
                .accountNumber("1111-1111-1111")
                .accountName("강범서의 통장")
                .accountBankName("국민은행")
                .balance(10000000)
                .build();

        Account savedAccount = accountRepository.save(account);
        Optional<Account> foundAccount = accountRepository.findById(savedAccount.getId());

        Assertions.assertThat(foundAccount).isPresent();
        Assertions.assertThat(foundAccount.get().getAccountNumber()).isEqualTo("1111-1111-1111");
    }

    @DisplayName("Update 테스트")
    @Test
    void UpdateBalanceTest() {
        Account account = Account.builder()
                .accountNumber("1111-1111-1111")
                .accountName("강범서의 통장")
                .accountBankName("국민은행")
                .balance(10000000)
                .build();

        Account savedAccount = accountRepository.save(account);
        savedAccount.setBalance(50000);
        accountRepository.save(savedAccount);

        Account updatedAccount = accountRepository.findById(savedAccount.getId()).get();
        Assertions.assertThat(updatedAccount.getBalance()).isEqualTo(50000);
    }

    @DisplayName("Delete 테스트")
    @Test
    void DeleteAccountTest() {
        Account account = Account.builder()
                .accountNumber("1111-1111-1111")
                .accountName("강범서의 통장")
                .accountBankName("국민은행")
                .balance(10000000)
                .build();

        Account savedAccount = accountRepository.save(account);
        accountRepository.delete(savedAccount);

        Optional<Account> deletedAccount = accountRepository.findById(savedAccount.getId());
        Assertions.assertThat(deletedAccount).isEmpty();
    }
}
