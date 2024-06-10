package filtros;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Login;


@WebFilter("/home/login.jsf")
public class LoginAuth extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;


	public LoginAuth() {
        super();
    }

	public void destroy() {
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		HttpSession session = (HttpSession)req.getSession();
		
		Login login = (Login)session.getAttribute("usuario");
		
		if(login == null) {
			chain.doFilter(request, response);
		}else {
			res.sendRedirect(req.getContextPath()+"/aluno/home.jsf");
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
