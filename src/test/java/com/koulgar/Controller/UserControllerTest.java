package com.koulgar.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koulgar.Config.MessageSourceTestConfiguration;
import com.koulgar.Model.User.UserLoginRequest;
import com.koulgar.Model.User.UserDto;
import com.koulgar.Model.User.UserRegisterRequest;
import com.koulgar.Service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = MessageSourceTestConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserRegisterRequest> userRegisterRequestArgumentCaptor;

    @Captor
    private ArgumentCaptor<UserLoginRequest> loginRequestBodyArgumentCaptor;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void it_should_register() throws Exception {
        //given
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
                .username("koulgar")
                .password("123456")
                .passwordConfirm("123456")
                .build();


        //when
        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegisterRequest)))
                .andExpect(status().isCreated());

        //then
        verify(userService).register(userRegisterRequestArgumentCaptor.capture());
        UserRegisterRequest capturedRequest = userRegisterRequestArgumentCaptor.getValue();
        assertThat(capturedRequest).isEqualToComparingFieldByField(userRegisterRequest);
    }

    @Test
    public void it_should_throw_exception_when_register_credentials_are_wrong() throws Exception {
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
                .username("")
                .password("")
                .passwordConfirm("")
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegisterRequest)))
                .andExpect(status().isBadRequest());

        //then
        verifyNoInteractions(userService);
        resultActions
                .andExpect(jsonPath("$.exception", is("MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                        "Kullanici adi bos olamaz.",
                        "Sifre bos olamaz.",
                        "Sifre onay alani bos olamaz.",
                        "Lutfen 2 ile 15 karakter uzunlugunda kullanici adi girin.",
                        "Lutfen 2 ile 15 karakter uzunlugunda sifre girin.",
                        "Lutfen 2 ile 15 karakter uzunlugunda sifre onayi girin.")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void it_should_login() throws Exception {
        //given
        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .username("koulgar")
                .password("123456")
                .build();

        UserDto userDto = UserDto.builder().build();

        when(userService.userLogin(userLoginRequest)).thenReturn(userDto);

        //when
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginRequest)))
                .andExpect(status().isOk());

        //then
        verify(userService).userLogin(loginRequestBodyArgumentCaptor.capture());
        UserLoginRequest capturedRequest = loginRequestBodyArgumentCaptor.getValue();
        assertThat(capturedRequest).isEqualToComparingFieldByField(userLoginRequest);
    }

    @Test
    public void it_should_throw_exception_when_login_credentials_are_wrong() throws Exception {
        UserLoginRequest userLoginRequest = UserLoginRequest.builder()
                .username("")
                .password("")
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginRequest)))
                .andExpect(status().isBadRequest());

        //then
        verifyNoInteractions(userService);
        resultActions
                .andExpect(jsonPath("$.exception", is("MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                        "Kullanici adi bos olamaz.",
                        "Sifre bos olamaz.",
                        "Lutfen 2 ile 15 karakter uzunlugunda kullanici adi girin.",
                        "Lutfen 2 ile 15 karakter uzunlugunda sifre girin.")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}