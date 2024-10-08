package com.security.authservice.controller;

import com.security.authservice.dto.UpdateUserDTO;
import com.security.authservice.entity.User;
import com.security.authservice.infra.security.SecurityConfig;
import com.security.authservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts/manage")
@Tag(name = "Operações usuário", description = "Operações para gerenciamento de usuários.")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Realizar dados do usuário", description = "Método para buscar os dados de um usuário específico pelo ID. " +
            "Retorna os detalhes do usuário se encontrado."
    )
    @ApiResponse(responseCode = "200", description = "usuário encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Email não cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @GetMapping("{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        var user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Deleta dados do usuário", description = "Método para deletar um usuário específico pelo ID. " +
            "Retorna uma resposta sem conteúdo ao confirmar a exclusão do usuário."
    )
    @ApiResponse(responseCode = "204", description = "usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update de dados do usuário", description = "Método para atualizar os dados de um usuário específico pelo ID. " +
            "O corpo da requisição deve conter os dados atualizados do usuário."
    )
    @ApiResponse(responseCode = "200", description = "usuário atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "usuário não cadastrado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    @PutMapping({"/{userId}"})
    public ResponseEntity<Void> updateByUserId(@PathVariable("userId") Long userId,
                                               @RequestBody UpdateUserDTO updateUserDto) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

}
