package com.progress.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.progress.app.exception.EmailAlreadyUsedException;
import com.progress.app.model.User;
import com.progress.app.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }

  public List<User> serveAllUsers() {
    return userRepository.findAll();
  }

  public User serveAdduser(String username, String email, String password) throws Exception {
    if (userRepository.existsByEmail(email)) {
      throw new EmailAlreadyUsedException("email already used");
    }
    String encoderPassword = encodePassword(password);
    log.info("encode: {}",encoderPassword);
    return userRepository.save(new User(null,username,email,encoderPassword,null));
  }

  public Optional<User> serveFindUserById(@NonNull Long id) {
    return userRepository.findById(id);
  }

  public void serveDeleteUserById(@NonNull Long id) {
    userRepository.deleteById(id);
  }

  public void serveDeleteUsersById(@NonNull List<Long> ids) {
    userRepository.deleteAllById(ids);
    ;
  }
}
