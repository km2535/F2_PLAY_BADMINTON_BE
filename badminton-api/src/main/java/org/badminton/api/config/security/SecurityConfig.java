package org.badminton.api.config.security;

import java.util.Arrays;

import org.badminton.api.member.jwt.JwtFilter;
import org.badminton.api.member.jwt.JwtUtil;
import org.badminton.api.member.oauth2.CustomSuccessHandler;
import org.badminton.api.member.service.CustomOAuth2MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomOAuth2MemberService customOAuth2MemberService;
	private final CustomSuccessHandler customSuccessHandler;
	private final JwtUtil jwtUtil;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration configuration = new CorsConfiguration();
					configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://3.38.247.217:8080"));
					configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					configuration.setAllowedHeaders(Arrays.asList("JWT", "Content-Type"));
					configuration.setExposedHeaders(Arrays.asList("JWT", "Set-Cookie"));
					configuration.setAllowCredentials(true);
					configuration.setMaxAge(3600L);
					return configuration;
				}
			}));

		http
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

		http
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(
					userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2MemberService))
				.successHandler(customSuccessHandler));

		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/groups", "/oauth2/**", "/login/**", "/error", "/api/*", "/swagger-ui/**",
					"/v3/api-docs/**")
				.permitAll()
				.anyRequest()
				.authenticated());

		http
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOriginPattern("");
		corsConfiguration.addExposedHeader("Authorization");
		corsConfiguration.addExposedHeader("Set-Cookie");
		corsConfiguration.addAllowedHeader("");
		corsConfiguration.addAllowedMethod("*");
		// corsConfiguration.addAllowedOrigin(System.getenv("WEBSITE_DOMAIN"));
		// corsConfiguration.addAllowedOrigin(System.getenv("API_DOMAIN"));
		corsConfiguration.addAllowedOrigin("http://localhost:8080"); // JUST FOR LOCAL DEV
		corsConfiguration.addAllowedOrigin("http://localhost:3000"); // JUST FOR LOCAL DEV
		corsConfiguration.addAllowedHeader("http://3.38.247.217:8080"); // JUST FOR PROD
		corsConfiguration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}

}

