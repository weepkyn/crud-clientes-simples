import dao.ClienteSetDAO;
import dao.IClienteDAO;
import domain.Cliente;

import javax.swing.*;

public class App {

    public static void main(String args[]) {

        IClienteDAO iClienteDAO = new ClienteSetDAO();
        String opcao = "";

        do {
            opcao = JOptionPane.showInputDialog(null,
                    "Digite \n 1- Para Cadastrar \n 2- Para Consultar \n 3- Para Exclusão \n 4-Para Alteração \n 5- Para Sair",
                    "CRUD Clientes", JOptionPane.INFORMATION_MESSAGE);

            if (opcao == null) {
                System.exit(0);
            }

            switch (opcao) {
                case "1":
                    String dados = JOptionPane.showInputDialog(null,
                            "Digite os dados separados por vírgula.\nExemplo: Nome, CPF, Telefone, \n" +
                                    " Endereço, Número, Cidade, Estado",
                            "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                    cadastrar(dados, iClienteDAO);
                    break;
                case "2":
                    consulta(iClienteDAO);
                    break;

                case "3":
                    excluir(iClienteDAO);
                    break;

                case "4":
                    alterar(iClienteDAO);
                    break;

                case "5":
                    JOptionPane.showMessageDialog(null, "Você decidiu sair");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção Inválida!");
            }

        } while (!opcao.equals("5"));
    }

    private static void alterar(IClienteDAO iClienteDAO) {

        String dados = JOptionPane.showInputDialog(null,
                "Digite os NOVOS DADOS separados por vírgula.\n" +
                         "Exemplo: Nome, CPF, Telefone, Endereço, Número, Cidade, Estado\n" +
                        "OBS: O CPF deve ser o mesmo do cliente que você quer alterar!\n",
                "Alteração", JOptionPane.INFORMATION_MESSAGE);


        String[] dadosSeparados = dados.split(",");

        if (dadosSeparados.length != 7) {
            JOptionPane.showMessageDialog(null, "Erro: Você deve passar todas as 7 informações para atualizar!");
            return;
        }

        try {
            Cliente cliente = new Cliente(
                    dadosSeparados[0].trim(),
                    dadosSeparados[1].trim(),
                    dadosSeparados[2].trim(),
                    dadosSeparados[3].trim(),
                    dadosSeparados[4].trim(),
                    dadosSeparados[5].trim(),
                    dadosSeparados[6].trim()
            );

            iClienteDAO.alterar(cliente);

            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro: CPF, Telefone e Número devem ser apenas números!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private static void excluir(IClienteDAO iClienteDAO) {

        String dadoCPF = JOptionPane.showInputDialog(null,
                "Digite o CPF para exclusão",
                "Exclusão", JOptionPane.INFORMATION_MESSAGE);


        try {
            Long cpf = Long.parseLong(dadoCPF.trim());

            // O DAO retorna boolean, a gente usa isso no IF
            Boolean sucesso = iClienteDAO.excluir(cpf);

            if (sucesso) {
                JOptionPane.showMessageDialog(null, "Cliente de CPF " + cpf + " excluido!");
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado para o CPF: " + cpf);
                String opcao = JOptionPane.showInputDialog(null,
                        "Digite \n 1- Para Cadastrar \n 2- Para Consultar \n 3- para exclusão \n 4-Para Alteração \n 5- Para Sair",
                        "CRUD Clientes", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro: Digite apenas números no CPF!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }



    private static void consulta(IClienteDAO iClienteDAO) {

        String dadoConsulta = JOptionPane.showInputDialog(null,
                "Digite o CPF para consulta",
                "Consulta", JOptionPane.INFORMATION_MESSAGE);


        try {
            Long cpf = Long.parseLong(dadoConsulta.trim());
            Cliente cliente = iClienteDAO.consultar(cpf);


            if (cliente != null) {
                JOptionPane.showMessageDialog(null,  cliente.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado para o CPF: " + cpf);
                String opcao = JOptionPane.showInputDialog(null,
                        "Digite \n 1- Para Cadastrar \n 2- Para Consultar \n 3- para exclusão \n 4-Para Alteração \n 5- Para Sair",
                        "CRUD Clientes", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro: Digite apenas números no CPF!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void cadastrar(String dados, IClienteDAO iClienteDAO) {
        String[] dadosSeparados = dados.split(",");


        if (dadosSeparados.length !=7) {
            JOptionPane.showMessageDialog(null, "Erro: Faltam informações! Você deve digitar os 7 campos.");
            return;
        }
        try {
            Cliente cliente = new Cliente(
                    dadosSeparados[0].trim(),
                    dadosSeparados[1].trim(),
                    dadosSeparados[2].trim(),
                    dadosSeparados[3].trim(),
                    dadosSeparados[4].trim(),
                    dadosSeparados[5].trim(),
                    dadosSeparados[6].trim()
            );

            Boolean cadastrado = iClienteDAO.cadastrar(cliente);

            if (cadastrado) {
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro: CPF, Telefone ou Número devem ser apenas números!");
        }
    }
}