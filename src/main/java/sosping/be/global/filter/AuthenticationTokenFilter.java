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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
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
    private final PlayerDetailsService playerDetailsService;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

    @Value("${host.develop.api.ant-match.uri}")
    private List<String> antMatchURIs = new ArrayList<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // 토큰 비검사 uri
        for (String antMatchURI : antMatchURIs) {
            if (path.startsWith(antMatchURI)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String token = tokenProvider.resolveToken(request);

        String aud = tokenProvider.getAudience(token);
        LOGGER.info("[] aud  {}", aud);
        if (aud == null || aud.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
        }


        UserDetails userDetails = playerDetailsService.loadUserByUsername(aud);
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //response.setHeader("Authorization", token);
        filterChain.doFilter(request, response);
    }
}
