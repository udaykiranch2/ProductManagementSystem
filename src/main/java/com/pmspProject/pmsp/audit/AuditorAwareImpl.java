package com.pmspProject.pmsp.audit;

import com.pmspProject.pmsp.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectFactory<HttpServletRequest> requestFactory;


    @Override
    public Optional<String> getCurrentAuditor() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = requestFactory.getObject();
            String bearerToken = request.getHeader("x-authorization");
            // if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            if (StringUtils.isNotEmpty(bearerToken)) {
                // String token = bearerToken.substring(7);
                String token = bearerToken;
                try {
                    String username = tokenUtil.extractUsername(token);
                    return Optional.of(username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Optional.of("system");
    }
}
