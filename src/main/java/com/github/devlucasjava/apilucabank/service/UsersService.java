package com.github.devlucasjava.apilucabank.service;

import com.github.devlucasjava.apilucabank.dto.mapper.UserMapper;
import com.github.devlucasjava.apilucabank.dto.request.UserUpdate;
import com.github.devlucasjava.apilucabank.dto.response.UsersResponse;
import com.github.devlucasjava.apilucabank.exception.BadRequestException;
import com.github.devlucasjava.apilucabank.exception.ResourceNotFoundException;
import com.github.devlucasjava.apilucabank.model.Account;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.repository.AccountRepository;
import com.github.devlucasjava.apilucabank.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final AccountRepository accountRepository;
    private final UserMapper userMapper;

    public UsersResponse getAuthenticatedUser(Users user) {
        Users foundUser = usersRepository.findByEmailOrPassport(user.getEmail())
                .orElseThrow(() -> {
                    log.warn("Authenticated user not found: {}", user.getEmail());
                    return new ResourceNotFoundException("User not found");
                });

        log.debug("Authenticated user retrieved: {}", foundUser.getEmail());
        return userMapper.toUsersResponse(foundUser);
    }

    public UsersResponse updateMe(Users userAuth, UserUpdate userUpdate) {
        Users user = usersRepository.findById(userAuth.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        if (!userUpdate.getFirstName().isBlank()) {
            user.setFirstName(userUpdate.getFirstName());
        }
        if (!userUpdate.getLastName().isBlank()){
            user.setLastName(userUpdate.getLastName());
        }
        if (!userUpdate.getEmail().isBlank()){
            user.setEmail(userUpdate.getEmail());
        }
        if (!userUpdate.getPassport().isBlank()){
            user.setPassport(userUpdate.getPassport());
        };
        if (!userUpdate.getBirthDate().isBefore(
                LocalDate.now().minusYears(18))
                && userUpdate.getBirthDate() != null)
        {
            user.setBirthDate(userUpdate.getBirthDate());
        }
        Users u = usersRepository.save(user);
        return userMapper.toUsersResponse(u);
    }

    public void deleteMe(Users users) {
        Users u = usersRepository.findById(users.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        Optional<Account> a = accountRepository.findByUser(u);

        if (a.isPresent() && a.get().getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new BadRequestException("It's not possible to delete because user has a balance in their account");
        }
        u.setRole(null);

        usersRepository.deleteById(u.getId());
        accountRepository.deleteByUser(u);
    }
}
