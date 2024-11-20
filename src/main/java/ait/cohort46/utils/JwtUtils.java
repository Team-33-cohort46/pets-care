package ait.cohort46.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class JwtUtils {
    private static final String SECRET_KEY = "mySecretKey";  // Секретный ключ для подписи JWT
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // Время действия токена (1 час)


    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)  // Устанавливаем email как subject
                .setIssuedAt(new Date())  // Время создания токена
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // Время истечения токена
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // Подписываем токен с использованием секретного ключа
                .compact();  // Сериализуем токен в строку
    }

    // Извлечение имени пользователя (email) из токена
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)  // Используем тот же секретный ключ для парсинга токена
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();  // Возвращаем subject (email)
    }

    // Валидация JWT токена
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            // Проверяем, не истек ли срок действия токена
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;  // Если произошла ошибка при валидации токена (например, токен поврежден или просрочен)
        }
    }
}
