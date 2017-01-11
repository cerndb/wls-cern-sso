

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import weblogic.security.principal.WLSUserImpl;

/**
 * Servlet Filter implementation class HeadersDecorator
 */
@WebFilter("/secure/weblogic")
public class HeadersDecorator implements Filter {

    /**
     * Default constructor. 
     */
    public HeadersDecorator() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		WLSUserImpl wlsUserImpl = (WLSUserImpl) ((HttpServletRequest) request).getUserPrincipal();
		SampleHttpRequestWrapper sampleHttpRequestWrapper = new SampleHttpRequestWrapper((HttpServletRequest) request, wlsUserImpl);
		chain.doFilter(sampleHttpRequestWrapper, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
