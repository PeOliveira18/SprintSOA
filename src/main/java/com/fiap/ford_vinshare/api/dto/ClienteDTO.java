package com.fiap.ford_vinshare.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.ford_vinshare.api.model.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @NotBlank(message = "Nome e obrigatorio")
        @Size(max = 120, message = "Nome deve ter no maximo 120 caracteres")
        String nome,

        @NotBlank(message = "Email e obrigatorio")
        @Email(message = "Email deve ser valido")
        @Size(max = 120, message = "Email deve ter no maximo 120 caracteres")
        String email,

        @NotBlank(message = "Telefone e obrigatorio")
        @Pattern(regexp = "^[0-9+() -]{8,20}$", message = "Telefone deve conter entre 8 e 20 caracteres validos")
        String telefone,

        @NotBlank(message = "Documento e obrigatorio")
        @Pattern(regexp = "^[0-9]{11,14}$", message = "Documento deve conter 11 a 14 digitos")
        String documento
) {
    public static ClienteDTO from(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone(),
                cliente.getDocumento()
        );
    }
}
