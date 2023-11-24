package com.ynov.boilerplate.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Service responsable de la génération, de la validation et de l'extraction des informations des jetons JWT.
 */
@Service
public class JwtService {
    // Clé secrète pour signer le jeton JWT
    private static final String SECRET_KEY = "63caf6f041dc55521aab55c0e72a9e885f0efd267a0a140c0ade5dd69a8ff4af";

    /**
     * Extrait le nom d'utilisateur à partir du jeton JWT.
     *
     * @param token Le jeton JWT à partir duquel extraire le nom d'utilisateur.
     * @return Le nom d'utilisateur extrait du jeton JWT.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait toutes les revendications (claims) du jeton JWT.
     *
     * @param token Le jeton JWT à partir duquel extraire toutes les revendications.
     * @return Les revendications extraites du jeton JWT.
     */
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    /**
     * Extrait une revendication spécifique du jeton JWT en utilisant la fonction de résolution des revendications.
     *
     * @param token          Le jeton JWT à partir duquel extraire la revendication.
     * @param claimsResolver La fonction de résolution des revendications.
     * @return La revendication spécifique extraite du jeton JWT.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Génère un jeton JWT pour l'utilisateur sans revendications supplémentaires.
     *
     * @param userDetails Les détails de l'utilisateur pour lesquels générer le jeton.
     * @return Le jeton JWT généré.
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Génère un jeton JWT pour l'utilisateur avec des revendications supplémentaires.
     *
     * @param extraClaims    Les revendications supplémentaires à inclure dans le jeton.
     * @param userDetails    Les détails de l'utilisateur pour lesquels générer le jeton.
     * @return Le jeton JWT généré.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
      return Jwts
              .builder()
              .setClaims(extraClaims)
              .setSubject(userDetails.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
              .signWith(getSignInKey(), SignatureAlgorithm.HS256)
              .compact();
    }

    /**
     * Vérifie si le jeton JWT est valide pour les détails de l'utilisateur fournis.
     *
     * @param token        Le jeton JWT à vérifier.
     * @param userDetails  Les détails de l'utilisateur pour lesquels vérifier le jeton.
     * @return true si le jeton est valide, false sinon.
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Vérifie si le jeton JWT a expiré.
     *
     * @param token Le jeton JWT à vérifier.
     * @return true si le jeton a expiré, false sinon.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrait la date d'expiration du jeton JWT.
     *
     * @param token Le jeton JWT à partir duquel extraire la date d'expiration.
     * @return La date d'expiration extraite du jeton JWT.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Obtient la clé de signature à partir de la clé secrète.
     *
     * @return La clé de signature utilisée pour signer le jeton JWT.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
