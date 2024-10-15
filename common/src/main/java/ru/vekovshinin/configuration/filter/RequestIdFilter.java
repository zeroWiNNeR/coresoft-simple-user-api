package ru.vekovshinin.configuration.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import ru.vekovshinin.util.TraceConstants;

@Component
@Order(0)
public class RequestIdFilter implements Filter {

  @Override
  public void doFilter(
      ServletRequest request, ServletResponse response, FilterChain chain
  ) throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    String header = req.getHeader(TraceConstants.X_REQUEST_ID);
    if (ObjectUtils.isEmpty(header)) {
      header = UUID.randomUUID().toString();
    }

    resp.setHeader(TraceConstants.X_REQUEST_ID, header);
    MDC.put(TraceConstants.X_REQUEST_ID, header);

    chain.doFilter(request, response);

    MDC.remove(TraceConstants.X_REQUEST_ID);
  }

}
