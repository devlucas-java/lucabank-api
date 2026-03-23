package com.github.devlucasjava.apilucabank.service;


import com.github.devlucasjava.apilucabank.dto.mapper.UserMapper;
import com.github.devlucasjava.apilucabank.dto.request.UserUpdate;
import com.github.devlucasjava.apilucabank.dto.response.UsersResponse;
import com.github.devlucasjava.apilucabank.exception.ResourceNotFoundException;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.repository.AccountRepository;
import com.github.devlucasjava.apilucabank.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserMapper userMapper;

    Users user;
    UsersResponse userResponse;
    String email = "lucas@lucas.com";

    @BeforeEach
    void setup() {
        user = new Users();
        user.setId(UUID.randomUUID());
        user.setFirstName("Lucas");
        user.setEmail(email);

        userResponse = new UsersResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(email);
        userResponse.setFirstName("Lucas");
    }

    @Test
    void shouldGetAuthenticatedUserSuccessFully() {

        when(usersRepository.findByEmailOrPassport(email))
                .thenReturn(Optional.of(user));
        when(userMapper.toUsersResponse(user))
                .thenReturn(userResponse);
        UsersResponse usersResponse = usersService.getAuthenticatedUser(user);

        assertNotNull(usersResponse);
        assertEquals(user.getId(), usersResponse.getId());
        verify(usersRepository).findByEmailOrPassport(email);
    }

    @Test
    void shouldGetAuthenticatedUserException(){

        when(usersRepository.findByEmailOrPassport(email))
                .thenReturn(Optional. empty());

        assertThrows(ResourceNotFoundException.class, () ->
                usersService.getAuthenticatedUser(user));

        verify(usersRepository).findByEmailOrPassport(email);
    }

    @Test
    void shouldUpdateUserSuccessfully() {

        UserUpdate update = new UserUpdate(
                "NewName",
                "NewLast",
                "new@email.com",
                LocalDate.now().minusYears(20),
                "123456"
        );

        when(usersRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        when(usersRepository.save(any()))
                .thenReturn(user);

        when(userMapper.toUsersResponse(user))
                .thenReturn(userResponse);

        UsersResponse result = usersService.updateMe(user, update);

        assertNotNull(result);
        assertEquals("NewName", user.getFirstName());
        assertEquals("NewLast", user.getLastName());
        assertEquals("new@email.com", user.getEmail());
        assertEquals("123456", user.getPassport());

        verify(usersRepository).findById(user.getId());
        verify(usersRepository).save(user);
        verify(userMapper).toUsersResponse(user);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnUpdate() {

        UserUpdate update = new UserUpdate(
                "Name",
                "Last",
                "email@email.com",
                LocalDate.now().minusYears(20),
                "123"
        );

        when(usersRepository.findById(user.getId()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                usersService.updateMe(user, update));

        verify(usersRepository).findById(user.getId());
        verify(usersRepository, never()).save(any());
    }

    @Test
    void shouldDeleteMeSuccessFully() {
        when(usersRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        usersService.deleteMe(user);

        verify(usersRepository).findById(user.getId());
        verify(usersRepository).deleteById(user.getId());
        verify(accountRepository).deleteByUser(user);
    }

    @Test
    void shouldDeleteMeException() {
        when(usersRepository.findById(user.getId()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                usersService.deleteMe(user));
    }
}
