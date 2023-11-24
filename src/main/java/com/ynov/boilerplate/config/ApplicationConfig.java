package com.ynov.boilerplate.config;

import com.mongodb.AutoEncryptionSettings;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.ynov.boilerplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration de l'application, notamment la configuration de l'authentification et du chiffrement des mots de passe.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository;

    /**
     * Crée un service pour charger les informations de l'utilisateur à partir de la base de données.
     *
     * @return Un service UserDetailsService basé sur la recherche par e-mail dans la base de données.
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> (UserDetails) repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Crée un fournisseur d'authentification DAO utilisant le service UserDetailsService et un encodeur de mot de passe.
     *
     * @return Un AuthenticationProvider basé sur DaoAuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Crée un encodeur de mot de passe BCrypt.
     *
     * @return Un PasswordEncoder basé sur BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Crée un gestionnaire d'authentification en utilisant la configuration d'authentification fournie.
     *
     * @param config La configuration d'authentification.
     * @return Un AuthenticationManager basé sur la configuration d'authentification fournie.
     * @throws Exception Si une exception se produit lors de la création du gestionnaire d'authentification.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return  config.getAuthenticationManager();
    }

}

