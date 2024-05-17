package sosping.be.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sosping.be.domain.member.service.MemberDetailsService;
import sosping.be.global.exception.ErrorCode;
import sosping.be.global.exception.domain.BusinessException;
import sosping.be.global.jwt.TokenProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final MemberDetailsService memberDetailsService;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

    @Value("${host.develop.api.ant-match.uri}")
    private List<String> antMatchURIs = new ArrayList<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        LOGGER.info("Request URI: {}", path);
        LOGGER.info("Ant Match URIs: {}", antMatchURIs);

        // 토큰 비검사 uri
        for (String antMatchURI : antMatchURIs) {
            if (path.startsWith(antMatchURI)) {
                LOGGER.info("Matched ant URI: {}", antMatchURI);
                filterChain.doFilter(request, response);
                return;
            }
        }

        String token = tokenProvider.resolveToken(request);

        String aud = tokenProvider.getAudience(token);
        LOGGER.info("aud: {}", aud);
        if (aud == null || aud.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = memberDetailsService.loadUserByUsername(aud);
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
