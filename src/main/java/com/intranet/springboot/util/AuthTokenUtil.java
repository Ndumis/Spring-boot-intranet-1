package com.intranet.springboot.util;

import com.intranet.springboot.security.AuthToken;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AuthTokenUtil {
    public static Optional<AuthToken> getAuthToken(List<AuthToken> tokens){
        if (tokens.size() == 0) return Optional.empty();

        tokens.sort(Comparator.comparing(AuthToken::getDate).reversed());
        return Optional.of(tokens.get(0));
    }
}
