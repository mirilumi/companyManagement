package com.project;


import com.project.entities.Authority;
import com.project.entities.Client;
import com.project.entities.User;
import com.project.repository.AuthorityRepository;
import com.project.repository.ClientRepository;
import com.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Profile(value = {"generate-sample-data"})
@Component
@RequiredArgsConstructor
public class GenerateSampleData implements ApplicationListener<ApplicationReadyEvent> {
    public static final String API_VERSION = "API_VERSION";
    public static final String CLIENT_IP_ADDRESS = "CLIENT_API_ADDRESS";
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
            cleanup();
            populateSampleData();
    }
    @Transactional
    private void cleanup() {
        userRepository.deleteAll();
        authorityRepository.deleteAll();
        clientRepository.deleteAll();
    }
    @Transactional
    private void populateSampleData() {

//         populating authority table
        List<Authority> authorities = new ArrayList<>();
        Authority authority_user = new Authority();
        authority_user.setName("ROLE_USER");
        Authority authority_admin = new Authority();
        authority_admin.setName("ROLE_ADMIN");
        authorities.add(authority_user);
        authorities.add(authority_admin);
        authorityRepository.save(authorities);


//         populating user table
        User user_admin = new User();
        user_admin.setFirstName("Jetmir");
        user_admin.setLastName("Lumi");
        user_admin.setEmail("jetmirlumi2@gmail.com");
        user_admin.setUsername("admin");
        user_admin.setEnabled(true);
        user_admin.setAuthorities(authorityRepository.findAll());
        user_admin.setPhoneNumber("0692179451");
        user_admin.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user_admin);
        User user = new User();
        user.setFirstName("Jetmir");
        user.setLastName("Lumi");
        user.setEmail("jetmirlumi2@gmail.com");
        user.setUsername("user");
        user.setEnabled(true);
        user.setAuthorities(authorityRepository.findAllByName("ROLE_USER"));
        user.setPhoneNumber("0692179451");
        user.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user);


//         populating client table
        List<Client> clients = new ArrayList<>();
        Client client = new Client();
        client.setAddress("Tirane");
        client.setEmail("jetmirlumi2@gmail.com");
        client.setAddress("Tirane");
        client.setFirstName("Jetmir");
        client.setLastName("Lumi");
        client.setLocation("Tirane");
        client.setMunicipality("Tirane");
        client.setProvince("Tirane");
        client.setNote("I am e web developer. I have almost  2 years of experience as a web Developer. I have worked for around 1.5 years as a PhP developer. Afterwords i realized that Java is a great language. Now I have almost 6 months working  with JAVA");client.setAddress("Tirane");
        Client client1 = new Client();
        client1.setEmail("jetmirlumi1@gmail.com");
        client1.setAddress("Tirane1");
        client1.setFirstName("Jetmir1");
        client1.setLastName("Lumi1");
        client1.setLocation("Tirane1");
        client1.setMunicipality("Tirane1");
        client1.setNote("Test");
        client.setProvince("Tirnane1");
        clients.add(client);
        clients.add(client1);
        clientRepository.save(clients);
    }
}
