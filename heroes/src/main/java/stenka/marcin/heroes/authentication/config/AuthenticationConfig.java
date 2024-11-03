package stenka.marcin.heroes.authentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Heroes")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/Heroes",
        callerQuery = "select password from users where name = ?",
        groupsQuery = "select role from users__roles where id = (select id from users where name = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class,
        hashAlgorithmParameters = {
                "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA256",
                "Pbkdf2PasswordHash.Iterations=2048",
                "Pbkdf2PasswordHash.SaltSizeBytes=32",
                "Pbkdf2PasswordHash.KeySizeBytes=32"
        }

)
public class AuthenticationConfig {
}
