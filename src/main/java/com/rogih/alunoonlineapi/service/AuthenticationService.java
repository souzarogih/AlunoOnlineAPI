package com.rogih.alunoonlineapi.service;

import com.rogih.alunoonlineapi.dtos.LoginUserRequest;
import com.rogih.alunoonlineapi.dtos.RegisterUserRequest;
import com.rogih.alunoonlineapi.enums.UserStatus;
import com.rogih.alunoonlineapi.model.User;
import com.rogih.alunoonlineapi.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setNomeCompleto(registerUserRequest.getNomeCompleto());
        user.setEmail(registerUserRequest.getEmail());
        user.setCpf(registerUserRequest.getCpf());
        user.setUserStatus(UserStatus.ATIVO);

        //Codificando a senha antes de salvar
        user.setSenha(passwordEncoder.encode(registerUserRequest.getSenha()));

        return userRepository.save(user);
    }

    public User signin(LoginUserRequest loginUserRequest) {
        // O AuthenticationManager vai comparar a senha fornecida com a senha codificada no banco
        //validar se o usuario está com status ativo, se tiver bloqueado, tem que negar
        User user = userRepository.findByEmail(loginUserRequest.getEmail()).get();
//        if (user.getUserStatus().equals(UserStatus.BLOQUEADO)){
//            System.out.println("usuario bloqueado");
//            return new Throwable(ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autorizado"));
//        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserRequest.getEmail(),
                        loginUserRequest.getSenha()
                )
        );

        return userRepository.findByEmail(loginUserRequest.getEmail()).orElseThrow();
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public boolean blockUser(Long userId) {
        log.info("Bloqueando usuario: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            user.get().setUserStatus(UserStatus.BLOQUEADO);
            userRepository.save(user.get());

            // invalidar o token atual do usuario
            return true;
        }
        return  false;

    }

    public boolean unblockUser(Long userId) {
        log.info("Desbloquear usuario: {}", userId);
        Optional<User> usuario = userRepository.findById(userId);
        if (usuario.isPresent()){
            usuario.get().setUserStatus(UserStatus.ATIVO);
            userRepository.save(usuario.get());

            // invalidar o token atual do usuario
            return true;
        }
        return  false;

    }
}
