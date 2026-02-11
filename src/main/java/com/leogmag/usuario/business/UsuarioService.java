package com.leogmag.usuario.business;

import com.leogmag.usuario.business.converter.UsuarioConverter;
import com.leogmag.usuario.business.dto.UsuarioDTO;
import com.leogmag.usuario.infrastructure.entity.Usuario;
import com.leogmag.usuario.infrastructure.repository.UsuarioRepository;
import lombok.*;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuarioEntity = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuarioEntity)
        );
    }
}
