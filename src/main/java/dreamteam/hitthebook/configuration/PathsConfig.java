package dreamteam.hitthebook.configuration;

import java.util.Arrays;

public class PathsConfig {
    public static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources/**"
    };

    // 필요한 경로만 추가
    public static final String[] PUBLIC_WHITELIST = {
            "/test/testToken/tempToken",
            "/mail/join/authorization",
            "/mail/forget/authorization",
            "/login",
            "/login/token/issue",
            "/join",
            "/forget/password/reset",
            "forget/password/current"
    };

    public static final String[] CORS_WHITELIST = {
    };

    public static final String[] ALL_WHITELIST = mergePaths(SWAGGER_WHITELIST, PUBLIC_WHITELIST);

    private static String[] mergePaths(String[]... paths) {
        return Arrays.stream(paths)
                .flatMap(Arrays::stream)
                .toArray(String[]::new);
    }
}

