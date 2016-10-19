package org.asion.account;

/**
 * @author Asion
 * @since 16/4/30.
 */
public interface AccountRepository {

	Iterable<Account> findAll();

	Account save(Account account);

	Account findOne(Long id);

	void delete(Long id);

}
