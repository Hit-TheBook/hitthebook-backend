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

    public static final String[] PUBLIC_WHITELIST = {
            "/",
            "/login",
            "/login/**",
            "/temp/token",
            "join",
            "/join/**",
            "/mail/**",
            "/forget/**"
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

