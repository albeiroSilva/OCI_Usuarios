package co.edu.unicauca.oci.backend.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import co.edu.unicauca.oci.backend.apirest.models.*;
import co.edu.unicauca.oci.backend.apirest.services.*;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

	@Autowired
	private IUserService usuarioService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		User usuario = usuarioService.findByUsername(authentication.getName());
		Map<String, Object> info = new HashMap<>();
		info.put("info_adicional", "Hola que tal!: ".concat(authentication.getName()));

		info.put("nombre", usuario.getObjPerson().getNames());
		info.put("apellido", usuario.getObjPerson().getSurnames());
		info.put("email", usuario.getObjPerson().getEmail());

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

		return accessToken;
	}

}
