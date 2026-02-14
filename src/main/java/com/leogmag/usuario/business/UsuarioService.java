package com.leogmag.usuario.business;

import com.leogmag.usuario.business.converter.UsuarioConverter;
import com.leogmag.usuario.business.dto.UsuarioDTO;
import com.leogmag.usuario.infrastructure.entity.Usuario;
import com.leogmag.usuario.infrastructure.exceptions.ConflictException;
import com.leogmag.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.leogmag.usuario.infrastructure.repository.UsuarioRepository;
import com.leogmag.usuario.infrastructure.security.JwtUtil;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){

        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        Usuario usuarioEntity = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuarioEntity)
        );
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictException("Email já cadastrado" + email);
            }
        } catch (ConflictException e){
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }
    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){
            return usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResolutionException("Email não encontrado" + email)
            );
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO){
        String email = jwtUtil.extractEmailToken(token.substring(7));
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("email não localizado!"));
        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);
        return  usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }
}
