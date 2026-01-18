package dao;

import domain.Cliente;
import java.util.*;

public class ClienteSetDAO implements IClienteDAO {

    private Set<Cliente> set;

    public ClienteSetDAO(){
        this.set = new HashSet<>();
    }

    @Override
    public boolean cadastrar(Cliente cliente) {
        return this.set.add(cliente);
    }

    // CORREÇÃO AQUI
    @Override
    public Boolean excluir(Long cpf) {
        Cliente clienteProcurado = null;
        for(Cliente cliente : this.set){
            if(cliente.getCpf().equals(cpf)){
                clienteProcurado = cliente;
                break;
            }
        }

        if(clienteProcurado != null){
            this.set.remove(clienteProcurado);
            return true;
        }
        return false;
    }

    @Override
    public void alterar(Cliente cliente) {
        if(this.set.contains(cliente)){
            for (Cliente clienteCadastrado : this.set){
                // Importante: Só altera se o CPF for o mesmo
                if (clienteCadastrado.equals(cliente)) {
                    clienteCadastrado.setNome(cliente.getNome());
                    clienteCadastrado.setTel(cliente.getTel());
                    clienteCadastrado.setNumero(cliente.getNumero());
                    clienteCadastrado.setEnd(cliente.getEnd());
                    clienteCadastrado.setCidade(cliente.getCidade());
                    clienteCadastrado.setEstado(cliente.getEstado());
                    break;
                }
            }
        }
    }

    @Override
    public Cliente consultar(Long cpf) {
        for (Cliente clienteCadastrado : this.set){
            if(clienteCadastrado.getCpf().equals(cpf)){
                return clienteCadastrado;
            }
        }
        return null;
    }

    @Override
    public Collection<Cliente> buscarTodos() {
        return this.set;
    }
}