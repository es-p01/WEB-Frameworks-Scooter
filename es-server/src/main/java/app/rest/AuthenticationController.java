package app.rest;

import app.APIConfig;
import app.exceptions.NotAcceptableException;
import app.models.User;
import app.models.View;
import app.repositories.EntityRepository;
import app.security.JWToken;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    APIConfig apiConfig;

    @Autowired
    EntityRepository<User> usersRepo;

    @JsonView(View.Summary.class)
    @PostMapping(path = "/login")
    public ResponseEntity<User> authenticateAccount(
            @RequestBody ObjectNode signInInfo,
            HttpServletRequest request) {

        String email = signInInfo.get("email").asText();
        String password = signInInfo.get("password").asText();

        List<User> users = usersRepo.findByQuery("Accounts_find_by_email", email);
        User user = users.size() > 0 ? users.get(0) : null;
        System.out.println(users);

        if (user == null || !user.verifyPassword(password)) {
            throw new NotAcceptableException("Cannot authenticate account with email=" + email);
        }
        // Issue a token for the account, valid for some time
        JWToken jwToken = new JWToken(user.getName(), user.getId(), user.getRole());
        String tokenString = jwToken.encode(this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),
                this.apiConfig.getTokenDurationOfValidity());

        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(user);
    }
}

