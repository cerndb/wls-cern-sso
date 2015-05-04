import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import weblogic.security.principal.WLSUserImpl;


public class SampleHttpRequestWrapper extends HttpServletRequestWrapper {
	
	private WLSUserImpl wlsUserImpl;

	public SampleHttpRequestWrapper(HttpServletRequest request, WLSUserImpl wlsUserImpl) {
		super(request);
		this.wlsUserImpl = wlsUserImpl;
	}

	@Override
	public String getHeader(String name) {
		if(name!=null && name.equals(Constants.USER_NAME_HEADER)){
			return this.wlsUserImpl.getName();
		}
		return super.getHeader(name);
	}
}
