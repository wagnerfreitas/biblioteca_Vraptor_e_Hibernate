package br.com.biblioteca.controller;

import java.util.Arrays;

import br.com.biblioteca.dao.AdminSession;
import br.com.biblioteca.entidades.Permissao;
import br.com.biblioteca.entidades.TipoDePerfil;
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
		if(adminSession.getUsuario() != null || method.getResource().getType().equals(LoginController.class)){
			stack.next(method, resourceInstance);
		} else {
			result.redirectTo(LoginController.class).login();
		} 
//		if(isAcessoMetodo(method)) {
//			stack.next(method, resourceInstance);
//		} else{
//			result.redirectTo(LoginController.class).negado();
//		}
	}
	
	
	private boolean isAcessoMetodo(ResourceMethod method) {
		Permissao permissaoList = method.getMethod().getAnnotation(Permissao.class);
		return isExistePermissao(permissaoList);
	}
	
	private boolean isExistePermissao(Permissao permissao) {
//		adminSession.getUsuario();
		if(permissao != null) {
			for(TipoDePerfil tipoDePerfil : permissao.value()) {
				if(tipoDePerfil.equals(adminSession.getUsuario().getTipoDePerfil())) {
					return true;
				}
			}
		} else {
			return true;
		}
		return false;
	}
}