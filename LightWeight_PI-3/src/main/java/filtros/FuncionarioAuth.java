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


@WebFilter("/funcionario/*")
public class FuncionarioAuth extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;


	public FuncionarioAuth() {
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
			res.sendRedirect(req.getContextPath()+"/home/login.jsf");
		}else if(login.isEfuncionario()){	
			chain.doFilter(request, response);				
		}else {
			res.sendRedirect(req.getContextPath()+"/aluno/home.jsf");			
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
