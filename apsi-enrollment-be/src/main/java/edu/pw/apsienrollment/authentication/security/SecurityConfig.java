package edu.pw.apsienrollment.authentication.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] SWAGGER_ANT_PATTERNS = {
            "/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**",
    };

    private static final String[] PUBLIC_POST_ANT_PATTERNS = {
            "/auth/authenticate", "/auth/refresh", "/payment/payu/notify"
    };

    private final UserDetailsService userDetailsService;
    private final String secretKey;

    public SecurityConfig(UserDetailsService userServiceImpl,
                          @Value("${apsi.security.secretKey}") String secretKey) {
        this.userDetailsService = userServiceImpl;
        this.secretKey = secretKey;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(withDefaults());
        httpSecurity
                .authorizeRequests().antMatchers("/h2/**").permitAll().and()
                .authorizeRequests().antMatchers(SWAGGER_ANT_PATTERNS).permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, PUBLIC_POST_ANT_PATTERNS).permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.POST, PUBLIC_POST_ANT_PATTERNS).permitAll().and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), secretKey, userDetailsService))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity
                .csrf().disable();
        httpSecurity
                .headers().frameOptions().disable();
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("OPTIONS", "GET", "POST"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
