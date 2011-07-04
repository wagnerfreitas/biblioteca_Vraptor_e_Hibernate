package br.com.biblioteca.controller;

import java.util.Arrays;

import br.com.biblioteca.dao.AdminSession;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
@RequestScoped
public class LoginIntecptor implements Interceptor {
	
	private Result result;
	private AdminSession adminSession;
	
	public LoginIntecptor(Result result, AdminSession adminSession){
		this.result = result;
		this.adminSession = adminSession;
	}

	@SuppressWarnings("unchecked")
	public boolean accepts(ResourceMethod method) {
		return !Arrays.asList(LoginController.class).contains(method.getMethod().getDeclaredAnnotations());
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
		if(adminSession.getAdministrador() != null || method.getResource().getType().equals(LoginController.class)){
			stack.next(method, resourceInstance);
		} else{
			result.redirectTo(LoginController.class).login();
		}
	}
}