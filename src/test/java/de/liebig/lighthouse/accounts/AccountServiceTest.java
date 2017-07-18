/*
 * MIT License
 * 
 * Copyright 2017 Marc Liebig
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included 
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 * 
 */

package de.liebig.lighthouse.accounts;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import de.liebig.lighthouse.accounts.AccountService;

/**
 * AccountService test class
 * 
 * @author Marc Liebig
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	@InjectMocks
	@Spy
	private AccountService accountService;

	@Mock
	private AccountRepository accountRepository;

	/**
	 * Setup Method
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link de.liebig.lighthouse.accounts.AccountService#exists()}
	 * if account is present.
	 */
	@Test
	public void testExists() {
		Mockito.doReturn(1L).when(accountRepository).count();
		assertTrue(accountService.exists());
	}

	/**
	 * Test method for {@link de.liebig.lighthouse.accounts.AccountService#exists()}
	 * if no account is present.
	 */
	@Test
	public void testDoesNotExist() {
		Mockito.doReturn(0L).when(accountRepository).count();
		assertFalse(accountService.exists());
	}

	/**
	 * Test method for
	 * {@link de.liebig.lighthouse.accounts.AccountService#getAccount()}.
	 */
	@Test
	public void testGetAccount() {
		Account dummyAccount = new Account();
		Mockito.doReturn(Arrays.asList(dummyAccount)).when(accountRepository).findAll();
		Optional<Account> account =  accountService.getAccount();
		
		assertTrue(account.isPresent());
		assertEquals(dummyAccount, account.get());
	}
	
	/**
	 * Test method for
	 * {@link de.liebig.lighthouse.accounts.AccountService#getAccount()}
	 * if no account is present.
	 */
	@Test
	public void testGetAccountWithoutAccount() {
		Mockito.doReturn(Arrays.asList()).when(accountRepository).findAll();
		Optional<Account> account =  accountService.getAccount();
		
		assertFalse(account.isPresent());
	}
	
	/**
	 * Test method for
	 * {@link de.liebig.lighthouse.accounts.AccountService#getAccount()}
	 * if more than one account is present.
	 */
	@Test(expected = IllegalStateException.class)
	public void testGetAccountWithMultipleAccounst() {
		Account dummyAccount = new Account();
		Mockito.doReturn(Arrays.asList(dummyAccount, dummyAccount)).when(accountRepository).findAll();
		accountService.getAccount();
		fail("Exception should have been thrown before");
	}

	/**
	 * Test method for
	 * {@link de.liebig.lighthouse.accounts.AccountService#createAccount(java.lang.String)}.
	 */
	@Test
	public void testCreateAccount() {
		Account dummyAccount = new Account();
		Mockito.doReturn(dummyAccount).when(accountRepository).save(Mockito.any(Account.class));
		Optional<Account> createdAccount = accountService.createAccount("ApiKey");
		
		assertTrue(createdAccount.isPresent());
		assertEquals(dummyAccount, createdAccount.get());
	}
	
	/**
	 * Test method for
	 * {@link de.liebig.lighthouse.accounts.AccountService#createAccount(java.lang.String)}
	 * if a account already exists.
	 */
	@Test
	public void testCreateAccountWithAccountExists() {
		Mockito.doReturn(true).when(accountService).exists();
		Optional<Account> createdAccount = accountService.createAccount("ApiKey");
		
		assertFalse(createdAccount.isPresent());
		Mockito.verify(accountService, Mockito.times(1)).exists();
		Mockito.verify(accountRepository, Mockito.never()).save(Mockito.any(Account.class));
	}
}
