package com.banking.userservice;

import com.banking.userservice.dto.UserCreateDto;
import com.banking.userservice.dto.UserResponseDto;
import com.banking.userservice.exceptions.NoDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceApplicationTest {

    private InMemoryUserRepository inMemoryUserRepository;
    private UserService userService;
    private UserResponseDto userResponseDto;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private UserServiceValidator userValidator;

    @BeforeEach
    void setUp() throws NoDataException {
        jwtService = mock(JwtService.class);
        passwordEncoder = mock(PasswordEncoder.class);

        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> "encoded-" + invocation.getArgument(0));

        inMemoryUserRepository = new InMemoryUserRepository();
        userService = new UserService(inMemoryUserRepository, jwtService, passwordEncoder, userValidator);

        userResponseDto = userService.createUser(new UserCreateDto("JoeD", "joe@op.pl", "Joe", "Does", "password", "234567890"));
    }

    @Test
    public void shouldCreateUserSuccessfully() {
        assertNotNull(userResponseDto);
        assertEquals("joe@op.pl", userResponseDto.getEmail());
        assertEquals(UserStatus.ACTIVE, userResponseDto.getStatus());
    }

    @Test
    public void shouldNotCreateUserWithExistingEmail() {
        UserCreateDto duplicateEmailDto = new UserCreateDto("JoeD1", "joe@op.pl", "Joe", "Does","password", "234567890");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(duplicateEmailDto);
        });

        assertEquals("Email already exists: joe@op.pl", exception.getMessage());
    }

    @Test
    public void shouldReturnUserByUsername() {
        UserResponseDto userByUsername = userService.findUserByUsername("JoeD");

        assertNotNull(userByUsername);
        assertEquals("JoeD", userByUsername.getUsername());
        assertEquals("joe@op.pl", userByUsername.getEmail());
        assertEquals("Joe", userByUsername.getFirstName());
        assertEquals("Does", userByUsername.getLastName());
        assertEquals("234567890", userByUsername.getPhoneNumber());
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findUserByUsername("not_existing");
        });

        assertEquals("User not found: not_existing", exception.getMessage());
    }

    @Test
    public void shouldUpdateUserProfile() {
        UserResponseDto updatedUser = userService.updateUser(new UserCreateDto("JoeD", "joe1@op.pl", "Joe1", "Does1","password", "1111111111"));

        assertNotNull(updatedUser);
        assertEquals("JoeD", updatedUser.getUsername());
        assertEquals("joe1@op.pl", updatedUser.getEmail());
        assertEquals("Joe1", updatedUser.getFirstName());
        assertEquals("1111111111", updatedUser.getPhoneNumber());
    }

    @Test
    public void shouldMapDtoToEntityCorrectly() {
        UserCreateDto userCreateDto = new UserCreateDto("JoeD", "joe@op.pl", "Joe", "Does", "password", "234567890");

        User user = UserMapper.mapToEntity(userCreateDto);

        assertEquals(userCreateDto.getUsername(), user.getUsername());
        assertEquals(userCreateDto.getEmail(), user.getEmail());
        assertEquals(userCreateDto.getFirstName(), user.getFirstName());
        assertEquals(userCreateDto.getSurname(), user.getSurname());
        assertEquals(userCreateDto.getPhoneNumber(), user.getPhoneNumber());
    }
}
