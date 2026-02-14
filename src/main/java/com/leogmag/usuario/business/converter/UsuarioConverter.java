package com.leogmag.usuario.business.converter;

import com.leogmag.usuario.business.dto.EnderecoDTO;
import com.leogmag.usuario.business.dto.TelefoneDTO;
import com.leogmag.usuario.business.dto.UsuarioDTO;
import com.leogmag.usuario.infrastructure.entity.Endereco;
import com.leogmag.usuario.infrastructure.entity.Telefone;
import com.leogmag.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {
    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .senha(usuarioDTO.getSenha())
                .email(usuarioDTO.getEmail())
                .enderecos(paraEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraTelefone(usuarioDTO.getTelefones()))
        .build();
    }
    public List<Endereco> paraEndereco(List<EnderecoDTO> enderecoDTOList){
        return enderecoDTOList.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .cep(enderecoDTO.getCep())
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .estado(enderecoDTO.getEstado())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .build();
    }

    public List<Telefone> paraTelefone(List<TelefoneDTO> telefoneDTOList){
        return telefoneDTOList.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .senha(usuario.getSenha())
                .email(usuario.getEmail())
                .enderecos(paraEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraTelefoneDTO(usuario.getTelefones()))
                .build();
    }
    public List<EnderecoDTO> paraEnderecoDTO(List<Endereco> enderecoList){
        return enderecoList.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .estado(endereco.getEstado())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .build();
    }

    public List<TelefoneDTO> paraTelefoneDTO(List<Telefone> telefoneList){
        return telefoneList.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .telefones(entity.getTelefones())
                .enderecos(entity.getEnderecos())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .build();
    }
}
