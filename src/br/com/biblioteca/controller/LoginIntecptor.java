package br.com.biblioteca.controller;

import java.util.Arrays;

import br.com.biblioteca.dao.UsuarioSession;
import br.com.biblioteca.entidades.Acao;
import br.com.biblioteca.entidades.Permissao;
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
	private UsuarioSession usuarioSession;
	
	public LoginIntecptor(Result result, UsuarioSession usuarioSession){
		this.result = result;
		this.usuarioSession = usuarioSession;
	}

	@SuppressWarnings("unchecked")
	public boolean accepts(ResourceMethod method) {
		return !Arrays.asList(LoginController.class).contains(method.getMethod().getDeclaredAnnotations());
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
		if(usuarioSession.getUsuario() != null || method.getResource().getType().equals(LoginController.class)){
			if(isAcessoMetodo(method)) {
				stack.next(method, resourceInstance);
			} else {
				result.redirectTo(LoginController.class).negado();
			}			
		} else{
			result.redirectTo(LoginController.class).login();
		}
	}
	
	private boolean isAcessoMetodo(ResourceMethod method) {
		Permissao permissaoList = method.getMethod().getAnnotation(Permissao.class);
		return isExistePermissao(permissaoList);
	}
	
	private boolean isExistePermissao(Permissao permissao) {
		if(permissao != null) {
			for(String grupoDePerfil : permissao.value()) {
				for(Acao acao : usuarioSession.getUsuario().getGrupoDePerfil().getAcoes()) {
					if(grupoDePerfil.equals(acao.getNome())) {
						return true;
					}
				}
			}
		} else {
			return true;
		}
		return false;
	}
}