package com.koulgar.Service;

import com.koulgar.Converter.LoginResponseConverter;
import com.koulgar.Converter.NoteDtoConverter;
import com.koulgar.Converter.RegisterUserConverter;
import com.koulgar.Domain.Note;
import com.koulgar.Domain.User;
import com.koulgar.Exception.PasswordDoesNotMatchException;
import com.koulgar.Exception.UserAlreadyExistsException;
import com.koulgar.Exception.UserNotFoundException;
import com.koulgar.Model.Note.NoteDto;
import com.koulgar.Model.User.UserDto;
import com.koulgar.Model.User.UserLoginRequest;
import com.koulgar.Model.User.UserRegisterRequest;
import com.koulgar.Repository.NoteRepository;
import com.koulgar.Repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoginResponseConverter loginResponseConverter;

    @Mock
    private RegisterUserConverter registerUserConverter;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NoteDtoConverter noteDtoConverter;


    @Test
    public void it_should_user_login() {
        //given
        UserLoginRequest userLoginRequest = UserLoginRequest.builder().build();

        User user = User.builder().build();

        UserDto userDto = UserDto.builder().build();

        when(userRepository.findByUsernameAndPassword(userLoginRequest.getUsername(), userLoginRequest.getPassword()))
                .thenReturn(java.util.Optional.ofNullable(user));

        when(loginResponseConverter.convert(Objects.requireNonNull(user))).thenReturn(userDto);


        //when
        UserDto userDtoResponse = userService.userLogin(userLoginRequest);

        //then
        verify(userRepository).findByUsernameAndPassword(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        verify(loginResponseConverter).convert(user);
        assertThat(userDtoResponse).isEqualTo(userDto);
    }

    @Test
    public void it_should_throw_exception_when_user_credentials_wrong() {
        //given
        UserLoginRequest userLoginRequest = UserLoginRequest.builder().build();

        when(userRepository.findByUsernameAndPassword(userLoginRequest.getUsername(), userLoginRequest.getPassword()))
                .thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> userService.userLogin(userLoginRequest));

        //then
        verify(userRepository).findByUsernameAndPassword(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        verifyNoInteractions(loginResponseConverter);
        assertThat(throwable).isInstanceOf(UserNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Kullanıcı adi ya da sifre yanlis.");
    }

    @Test
    public void it_should_user_register() {
        //given
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder().build();
        when(userRepository.findByUsername(userRegisterRequest.getUsername()))
                .thenReturn(Optional.empty());

        User user = User.builder().build();

        when(registerUserConverter.convert(userRegisterRequest)).thenReturn(user);

        //when
        userService.register(userRegisterRequest);

        //then
        verify(userRepository).findByUsername(userRegisterRequest.getUsername());
        verify(registerUserConverter).convert(userRegisterRequest);
        verify(userRepository).save(user);
    }

    @Test
    public void it_should_throw_exception_user_password_not_match() {
        //given
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
                .password("1234")
                .passwordConfirm("9876")
                .build();

        //when
        Throwable throwable = catchThrowable(() -> userService.register(userRegisterRequest));

        //then
        verifyNoInteractions(userRepository);
        verifyNoInteractions(registerUserConverter);
        assertThat(throwable).isInstanceOf(PasswordDoesNotMatchException.class);
        assertThat(throwable.getMessage()).isEqualTo("Şifre eşleşmiyor.");
    }

    @Test
    public void it_should_throw_exception_when_username_already_taken() {
        //given
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder().build();
        User user = User.builder().build();

        when(userRepository.findByUsername(userRegisterRequest.getUsername())).thenReturn(Optional.ofNullable(user));

        //when
        Throwable throwable = catchThrowable(() -> userService.register(userRegisterRequest));

        //then
        verify(userRepository).findByUsername(userRegisterRequest.getUsername());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(registerUserConverter);
        assertThat(throwable).isInstanceOf(UserAlreadyExistsException.class);
        assertThat(throwable.getMessage()).isEqualTo("Bu kullanici adi zaten alinmis.");
    }

    @Test
    public void it_should_get_user_notes() {
        Note note1 = Note.builder().id("1").ownerId("a").build();
        Note note2 = Note.builder().id("2").ownerId("a").build();

        NoteDto noteDto1 = NoteDto.builder().id("1111").build();
        NoteDto noteDto2 = NoteDto.builder().id("2222").build();

        List<Note> notes = Arrays.asList(note1, note2);
        List<NoteDto> noteDtoList = Arrays.asList(noteDto1, noteDto2);

        //given
        when(noteRepository.getNotesByOwnerId("a")).thenReturn(notes);
        when(noteDtoConverter.convertAll(notes)).thenReturn(noteDtoList);

        //when
        List<NoteDto> notesList = userService.getUserNotes("a");

        //then
        verify(noteRepository).getNotesByOwnerId("a");
        verify(noteDtoConverter).convertAll(notes);
        assertThat(notesList).isEqualTo(noteDtoList);
    }
}