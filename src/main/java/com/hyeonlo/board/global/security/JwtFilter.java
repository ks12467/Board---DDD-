package com.hyeonlo.board.global.security;

import com.hyeonlo.board.domain.user.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String jwt = null;
        //Athorization 헤더에서 JWT 추출
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            //Bearer 부분 삭제
            jwt = jwtUtil.substringToken(authorizationHeader);
        } else {
            throw new AuthorizationServiceException("JWT 토큰이 필요합니다.");
        }

        //JWT 유효성 체크
        if (jwt != null && !jwt.isBlank()) {
            try {
                //JWT 파싱 후 Claims 추출
                Claims claims = jwtUtil.extractClaims(jwt);

                //subject에 저장된 유저 id 추출
                Long userId = Long.valueOf(claims.getSubject());
                //사용자 역할 추출
                String userRoleString = claims.get("userRole", String.class);

                if (userRoleString == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다.");
                    return;
                }

                //문자열 enum으로 변환
                UserRole userRole = UserRole.of(userRoleString);

                //인증 정보가 없을 때
                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    String loginId = claims.get("loginId", String.class);

                    AuthUser authUser = new AuthUser(userId, loginId, userRole);

                    JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authUser);

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }


            } catch (SecurityException | MalformedJwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다.");
            } catch (ExpiredJwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다.");
            } catch (UnsupportedJwtException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }

        filterChain.doFilter(request, response);
    }
}
