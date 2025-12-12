package main;

import bo.PousadaBO;
import bo.QuartoBO;
import dao.FuncionarioDAO;
import dao.PessoaDAO;
import dao.PessoaFisicaDAO;
import dao.ReservaDAO;
import dto.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static PousadaBO pousadaBO = new PousadaBO();
    private static QuartoBO quartoBO = new QuartoBO();
    private static PessoaDAO pessoaDAO = new PessoaDAO();
    private static PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
    private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private static ReservaDAO reservaDAO = new ReservaDAO();
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GERENCIAMENTO DE POUSADAS  ║");
        System.out.println("╚══════════════════════════════════════════╝");
        
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerInteiro("Digite sua opção: ");
            
            switch (opcao) {
                case 1:
                    menuPousadas();
                    break;
                case 2:
                    menuQuartos();
                    break;
                case 3:
                    menuPessoas();
                    break;
                case 4:
                    menuFuncionarios();
                    break;
                case 5:
                    menuReservas();
                    break;
                case 0:
                    System.out.println("\n✓ Encerrando sistema... Até logo!");
                    break;
                default:
                    System.out.println("\n✗ Opção inválida!");
            }
        } while (opcao != 0);
        
        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│           MENU PRINCIPAL                 │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│ 1 - Gerenciar Pousadas                   │");
        System.out.println("│ 2 - Gerenciar Quartos                    │");
        System.out.println("│ 3 - Gerenciar Pessoas                    │");
        System.out.println("│ 4 - Gerenciar Funcionários               │");
        System.out.println("│ 5 - Gerenciar Reservas                   │");
        System.out.println("│ 0 - Sair                                 │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    // ========== MENU POUSADAS ==========
    private static void menuPousadas() {
        int opcao;
        do {
            System.out.println("\n┌──────── GERENCIAR POUSADAS ────────┐");
            System.out.println("│ 1 - Cadastrar Pousada              │");
            System.out.println("│ 2 - Listar Todas                   │");
            System.out.println("│ 3 - Buscar por ID                  │");
            System.out.println("│ 4 - Atualizar Pousada              │");
            System.out.println("│ 5 - Deletar Pousada                │");
            System.out.println("│ 6 - Estatísticas                   │");
            System.out.println("│ 0 - Voltar                         │");
            System.out.println("└────────────────────────────────────┘");
            
            opcao = lerInteiro("Opção: ");
            
            switch (opcao) {
                case 1: cadastrarPousada(); break;
                case 2: listarPousadas(); break;
                case 3: buscarPousada(); break;
                case 4: atualizarPousada(); break;
                case 5: deletarPousada(); break;
                case 6: estatisticasPousadas(); break;
                case 0: break;
                default: System.out.println("✗ Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarPousada() {
        System.out.println("\n=== CADASTRAR POUSADA ===");
        try {
            Pousada pousada = new Pousada();
            
            pousada.setNome(lerTextoObrigatorio("Nome: ", 3));
            
            System.out.print("Endereço: ");
            pousada.setEndereco(scanner.nextLine());
            
            System.out.print("Bairro: ");
            pousada.setBairro(scanner.nextLine());
            
            pousada.setCidade(lerTextoObrigatorio("Cidade: ", 3));
            
            pousada.setEstado(lerTextoObrigatorio("Estado (2 letras): ", 2));
            
            pousada.setTelefone(lerTextoObrigatorio("Telefone: ", 8));
            
            pousada.setEstrelas(lerInteiro("Estrelas (1-5): "));
            
            System.out.print("Observação: ");
            pousada.setObservacao(scanner.nextLine());
            
            System.out.print("Site: ");
            pousada.setSite(scanner.nextLine());
            
            System.out.print("Status (S/N): ");
            pousada.setStatus(scanner.nextLine());
            
            pousadaBO.adicionarPousada(pousada);
            System.out.println("✓ Pousada cadastrada com sucesso!");
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void listarPousadas() {
        System.out.println("\n=== LISTA DE POUSADAS ===");
        try {
            var pousadas = pousadaBO.listarTodas();
            if (pousadas.isEmpty()) {
                System.out.println("Nenhuma pousada cadastrada.");
            } else {
                for (Pousada p : pousadas) {
                    System.out.println("────────────────────────────────");
                    System.out.println("ID: " + p.getId());
                    System.out.println("Nome: " + p.getNome());
                    System.out.println("Cidade: " + p.getCidade() + " - " + p.getEstado());
                    System.out.println("Estrelas: " + p.getEstrelas() + " ⭐");
                    System.out.println("Telefone: " + p.getTelefone());
                    System.out.println("Status: " + (p.getStatus().equals("S") ? "Ativo" : "Inativo"));
                }
                System.out.println("────────────────────────────────");
                System.out.println("Total: " + pousadas.size() + " pousadas");
            }
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void buscarPousada() {
        System.out.println("\n=== BUSCAR POUSADA ===");
        try {
            int id = lerInteiro("ID da pousada: ");
            Pousada p = pousadaBO.obterPousadaPorId(id);
            
            System.out.println("\n" + p);
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void atualizarPousada() {
        System.out.println("\n=== ATUALIZAR POUSADA ===");
        try {
            int id = lerInteiro("ID da pousada: ");
            Pousada pousada = pousadaBO.obterPousadaPorId(id);
            
            System.out.println("Dados atuais: " + pousada.getNome());
            System.out.println("(Deixe em branco para manter o valor atual)");
            
            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) pousada.setNome(nome);
            
            System.out.print("Novo telefone: ");
            String tel = scanner.nextLine();
            if (!tel.isEmpty()) pousada.setTelefone(tel);
            
            pousadaBO.atualizarPousada(pousada);
            System.out.println("✓ Pousada atualizada com sucesso!");
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void deletarPousada() {
        System.out.println("\n=== DELETAR POUSADA ===");
        try {
            int id = lerInteiro("ID da pousada: ");
            Pousada p = pousadaBO.obterPousadaPorId(id);
            
            System.out.println("Deseja deletar: " + p.getNome() + "?");
            System.out.print("Confirmar (S/N): ");
            String confirma = scanner.nextLine();
            
            if (confirma.equalsIgnoreCase("S")) {
                pousadaBO.deletarPousada(id);
                System.out.println("✓ Pousada deletada com sucesso!");
            } else {
                System.out.println("Operação cancelada.");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void estatisticasPousadas() {
        System.out.println("\n=== ESTATÍSTICAS ===");
        try {
            int total = pousadaBO.listarTodas().size();
            double media = pousadaBO.calcularMediaEstrelas();
            
            System.out.println("Total de pousadas: " + total);
            System.out.println("Média de estrelas: " + String.format("%.2f", media) + " ⭐");
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    // ========== MENU QUARTOS ==========
    private static void menuQuartos() {
        int opcao;
        do {
            System.out.println("\n┌──────── GERENCIAR QUARTOS ─────────┐");
            System.out.println("│ 1 - Cadastrar Quarto               │");
            System.out.println("│ 2 - Listar por Pousada             │");
            System.out.println("│ 3 - Buscar por ID                  │");
            System.out.println("│ 4 - Atualizar Quarto               │");
            System.out.println("│ 5 - Deletar Quarto                 │");
            System.out.println("│ 6 - Calcular Valor Total           │");
            System.out.println("│ 0 - Voltar                         │");
            System.out.println("└────────────────────────────────────┘");
            
            opcao = lerInteiro("Opção: ");
            
            switch (opcao) {
                case 1: cadastrarQuarto(); break;
                case 2: listarQuartos(); break;
                case 3: buscarQuarto(); break;
                case 4: atualizarQuarto(); break;
                case 5: deletarQuarto(); break;
                case 6: calcularValorTotal(); break;
                case 0: break;
                default: System.out.println("✗ Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarQuarto() {
        System.out.println("\n=== CADASTRAR QUARTO ===");
        try {
            int pousadaId = lerInteiro("ID da pousada: ");
            
            System.out.println("Tipo de quarto:");
            System.out.println("1 - Standard");
            System.out.println("2 - Deluxe");
            System.out.println("3 - Presidencial");
            int tipo = lerInteiro("Opção: ");
            
            String nomeQuarto = lerTextoObrigatorio("Nome do quarto: ", 3);
            
            int camas = lerInteiro("Número de camas (1-10): ");
            
            int valorDia = lerInteiro("Valor por dia: ");
            
            Quarto quarto;
            switch (tipo) {
                case 2:
                    System.out.print("Possui jacuzzi? (S/N): ");
                    boolean jacuzzi = scanner.nextLine().equalsIgnoreCase("S");
                    quarto = new QuartoDeluxe(pousadaId, nomeQuarto, camas, valorDia, jacuzzi);
                    break;
                case 3:
                    System.out.print("Possui jacuzzi? (S/N): ");
                    boolean jac = scanner.nextLine().equalsIgnoreCase("S");
                    System.out.print("Possui sala de estar? (S/N): ");
                    boolean sala = scanner.nextLine().equalsIgnoreCase("S");
                    quarto = new QuartoPresidencial(pousadaId, nomeQuarto, camas, valorDia, jac, sala);
                    break;
                default:
                    quarto = new QuartoStandard(pousadaId, nomeQuarto, camas, valorDia);
            }
            
            quartoBO.adicionarQuarto(quarto);
            System.out.println("✓ Quarto cadastrado com sucesso!");
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void listarQuartos() {
        System.out.println("\n=== LISTA DE QUARTOS ===");
        try {
            int pousadaId = lerInteiro("ID da pousada: ");
            var quartos = quartoBO.listarQuartosPorPousada(pousadaId);
            
            if (quartos.isEmpty()) {
                System.out.println("Nenhum quarto cadastrado para esta pousada.");
            } else {
                for (Quarto q : quartos) {
                    System.out.println("────────────────────────────────");
                    System.out.println("ID: " + q.getId());
                    System.out.println("Nome: " + q.getNome());
                    System.out.println("Tipo: " + q.getClass().getSimpleName());
                    System.out.println("Camas: " + q.getCamas());
                    System.out.println("Valor/dia: R$ " + q.getValor_dia());
                    
                    if (q instanceof IQuartoLuxo) {
                        IQuartoLuxo luxo = (IQuartoLuxo) q;
                        System.out.println("Jacuzzi: " + (luxo.temJacuzzi() ? "Sim" : "Não"));
                        if (q instanceof QuartoPresidencial) {
                            System.out.println("Sala de estar: " + (luxo.temSalaDeEstar() ? "Sim" : "Não"));
                        }
                    }
                }
                System.out.println("────────────────────────────────");
                System.out.println("Total: " + quartos.size() + " quartos");
            }
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void buscarQuarto() {
        System.out.println("\n=== BUSCAR QUARTO ===");
        System.out.println("Funcionalidade não disponível.");
        System.out.println("Use 'Listar por Pousada' para ver os quartos.");
    }

    private static void atualizarQuarto() {
        System.out.println("\n=== ATUALIZAR QUARTO ===");
        System.out.println("Funcionalidade não disponível.");
        System.out.println("Delete e recrie o quarto se necessário.");
    }

    private static void deletarQuarto() {
        System.out.println("\n=== DELETAR QUARTO ===");
        try {
            int id = lerInteiro("ID do quarto: ");
            
            System.out.print("Confirmar exclusão do quarto ID " + id + "? (S/N): ");
            String confirma = scanner.nextLine();
            
            if (confirma.equalsIgnoreCase("S")) {
                quartoBO.deletarQuarto(id);
                System.out.println("✓ Quarto deletado com sucesso!");
            } else {
                System.out.println("Operação cancelada.");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void calcularValorTotal() {
        System.out.println("\n=== CALCULAR VALOR TOTAL ===");
        try {
            int pousadaId = lerInteiro("ID da pousada: ");
            var quartos = quartoBO.listarQuartosPorPousada(pousadaId);
            
            if (quartos.isEmpty()) {
                System.out.println("Nenhum quarto encontrado.");
                return;
            }
            
            System.out.println("\nQuartos disponíveis:");
            for (int i = 0; i < quartos.size(); i++) {
                System.out.println((i+1) + " - " + quartos.get(i).getNome() + " (ID: " + quartos.get(i).getId() + ")");
            }
            
            int escolha = lerInteiro("Escolha o quarto (número): ") - 1;
            if (escolha < 0 || escolha >= quartos.size()) {
                System.out.println("Opção inválida.");
                return;
            }
            
            Quarto quarto = quartos.get(escolha);
            int dias = lerInteiro("Quantidade de dias: ");
            
            double valor = quartoBO.calcularValorTotal(quarto, dias);
            
            System.out.println("\n💰 Valor total: R$ " + String.format("%.2f", valor));
            System.out.println("   (Descontos automáticos aplicados se houver)");
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    // ========== MENU PESSOAS ==========
    private static void menuPessoas() {
        int opcao;
        do {
            System.out.println("\n┌──────── GERENCIAR PESSOAS ─────────┐");
            System.out.println("│ 1 - Cadastrar Pessoa Física        │");
            System.out.println("│ 2 - Buscar Pessoa                  │");
            System.out.println("│ 3 - Listar Todas                   │");
            System.out.println("│ 4 - Atualizar Pessoa               │");
            System.out.println("│ 5 - Deletar Pessoa                 │");
            System.out.println("│ 0 - Voltar                         │");
            System.out.println("└────────────────────────────────────┘");
            
            opcao = lerInteiro("Opção: ");
            
            switch (opcao) {
                case 1: cadastrarPessoaFisica(); break;
                case 2: buscarPessoa(); break;
                case 3: listarPessoas(); break;
                case 4: atualizarPessoa(); break;
                case 5: deletarPessoa(); break;
                case 0: break;
                default: System.out.println("✗ Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarPessoaFisica() {
        System.out.println("\n=== CADASTRAR PESSOA FÍSICA ===");
        try {
            PessoaFisica pf = new PessoaFisica();
            
            pf.setUsuario(lerTextoObrigatorio("Usuário: ", 3));
            
            pf.setNome(lerTextoObrigatorio("Nome: ", 3));
            
            pf.setTelefone(lerTextoObrigatorio("Telefone: ", 8));
            
            pf.setCpf(lerInteiro("CPF (apenas números): "));
            
            pf.setSexo(lerTextoObrigatorio("Sexo (M/F): ", 1));
            
            pessoaFisicaDAO.adicionar(pf);
            System.out.println("✓ Pessoa cadastrada com sucesso!");
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void buscarPessoa() {
        System.out.println("\n=== BUSCAR PESSOA ===");
        try {
            System.out.print("Usuário: ");
            String usuario = scanner.nextLine();
            
            PessoaFisica pf = PessoaFisicaDAO.buscarPorUsuario(usuario);
            if (pf != null) {
                System.out.println("\n" + pf);
            } else {
                System.out.println("Pessoa não encontrada.");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void listarPessoas() {
        System.out.println("\n=== LISTA DE PESSOAS ===");
        try {
            var pessoas = pessoaFisicaDAO.listarTodos();
            if (pessoas.isEmpty()) {
                System.out.println("Nenhuma pessoa cadastrada.");
            } else {
                for (PessoaFisica p : pessoas) {
                    System.out.println("────────────────────────────────");
                    System.out.println("Usuário: " + p.getUsuario());
                    System.out.println("Nome: " + p.getNome());
                    System.out.println("Telefone: " + p.getTelefone());
                    System.out.println("CPF: " + p.getCpf());
                    System.out.println("Sexo: " + p.getSexo());
                }
                System.out.println("────────────────────────────────");
                System.out.println("Total: " + pessoas.size());
            }
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void atualizarPessoa() {
        System.out.println("\n=== ATUALIZAR PESSOA ===");
        try {
            System.out.print("Usuário: ");
            String usuario = scanner.nextLine();
            
            PessoaFisica pf = PessoaFisicaDAO.buscarPorUsuario(usuario);
            if (pf == null) {
                System.out.println("Pessoa não encontrada.");
                return;
            }
            
            System.out.print("Novo nome (ou Enter): ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) pf.setNome(nome);
            
            System.out.print("Novo telefone (ou Enter): ");
            String tel = scanner.nextLine();
            if (!tel.isEmpty()) pf.setTelefone(tel);
            
            pessoaFisicaDAO.atualizar(pf);
            System.out.println("✓ Pessoa atualizada!");
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void deletarPessoa() {
        System.out.println("\n=== DELETAR PESSOA ===");
        try {
            System.out.print("Usuário: ");
            String usuario = scanner.nextLine();
            
            System.out.print("Confirmar exclusão? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                PessoaFisicaDAO.deletarPorUsuario(usuario);
                System.out.println("✓ Pessoa deletada!");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    // ========== MENU FUNCIONÁRIOS ==========
    private static void menuFuncionarios() {
        int opcao;
        do {
            System.out.println("\n┌────── GERENCIAR FUNCIONÁRIOS ──────┐");
            System.out.println("│ 1 - Cadastrar Funcionário          │");
            System.out.println("│ 2 - Buscar Funcionário             │");
            System.out.println("│ 3 - Listar Todos                   │");
            System.out.println("│ 4 - Atualizar Funcionário          │");
            System.out.println("│ 5 - Deletar Funcionário            │");
            System.out.println("│ 0 - Voltar                         │");
            System.out.println("└────────────────────────────────────┘");
            
            opcao = lerInteiro("Opção: ");
            
            switch (opcao) {
                case 1: cadastrarFuncionario(); break;
                case 2: buscarFuncionario(); break;
                case 3: listarFuncionarios(); break;
                case 4: atualizarFuncionario(); break;
                case 5: deletarFuncionario(); break;
                case 0: break;
                default: System.out.println("✗ Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarFuncionario() {
        System.out.println("\n=== CADASTRAR FUNCIONÁRIO ===");
        try {
            Funcionario func = new Funcionario();
            
            func.setUsuario(lerTextoObrigatorio("Usuário: ", 3));
            
            func.setNome(lerTextoObrigatorio("Nome: ", 3));
            
            func.setTelefone(lerTextoObrigatorio("Telefone: ", 8));
            
            func.setCpf(lerInteiro("CPF (apenas números): "));
            
            func.setSexo(lerTextoObrigatorio("Sexo (M/F): ", 1));
            
            func.setCargo(lerTextoObrigatorio("Cargo: ", 3));
            
            func.setSalario(lerInteiro("Salário: "));
            
            funcionarioDAO.adicionar(func);
            System.out.println("✓ Funcionário cadastrado com sucesso!");
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void buscarFuncionario() {
        System.out.println("\n=== BUSCAR FUNCIONÁRIO ===");
        try {
            System.out.print("Usuário: ");
            String usuario = scanner.nextLine();
            
            Funcionario func = FuncionarioDAO.buscarPorUsuario(usuario);
            if (func != null) {
                System.out.println("\n" + func);
            } else {
                System.out.println("Funcionário não encontrado.");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void listarFuncionarios() {
        System.out.println("\n=== LISTA DE FUNCIONÁRIOS ===");
        try {
            var funcionarios = funcionarioDAO.listarTodos();
            if (funcionarios.isEmpty()) {
                System.out.println("Nenhum funcionário cadastrado.");
            } else {
                for (Funcionario f : funcionarios) {
                    System.out.println("────────────────────────────────");
                    System.out.println("Usuário: " + f.getUsuario());
                    System.out.println("Nome: " + f.getNome());
                    System.out.println("Cargo: " + f.getCargo());
                    System.out.println("Salário: R$ " + f.getSalario());
                    System.out.println("Telefone: " + f.getTelefone());
                }
                System.out.println("────────────────────────────────");
                System.out.println("Total: " + funcionarios.size());
            }
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void atualizarFuncionario() {
        System.out.println("\n=== ATUALIZAR FUNCIONÁRIO ===");
        try {
            System.out.print("Usuário: ");
            String usuario = scanner.nextLine();
            
            Funcionario func = FuncionarioDAO.buscarPorUsuario(usuario);
            if (func == null) {
                System.out.println("Funcionário não encontrado.");
                return;
            }
            
            System.out.print("Novo cargo (ou Enter): ");
            String cargo = scanner.nextLine();
            if (!cargo.isEmpty()) func.setCargo(cargo);
            
            System.out.print("Novo salário (ou 0): ");
            int salario = lerInteiro("");
            if (salario > 0) func.setSalario(salario);
            
            funcionarioDAO.atualizar(func);
            System.out.println("✓ Funcionário atualizado!");
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void deletarFuncionario() {
        System.out.println("\n=== DELETAR FUNCIONÁRIO ===");
        try {
            System.out.print("Usuário: ");
            String usuario = scanner.nextLine();
            
            System.out.print("Confirmar exclusão? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                FuncionarioDAO.deletarPorUsuario(usuario);
                System.out.println("✓ Funcionário deletado!");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    // ========== MENU RESERVAS ==========
    private static void menuReservas() {
        int opcao;
        do {
            System.out.println("\n┌──────── GERENCIAR RESERVAS ────────┐");
            System.out.println("│ 1 - Criar Reserva                  │");
            System.out.println("│ 2 - Listar Todas                   │");
            System.out.println("│ 3 - Listar por Pousada             │");
            System.out.println("│ 4 - Listar por Quarto              │");
            System.out.println("│ 5 - Verificar Disponibilidade      │");
            System.out.println("│ 6 - Cancelar Reserva               │");
            System.out.println("│ 0 - Voltar                         │");
            System.out.println("└────────────────────────────────────┘");
            
            opcao = lerInteiro("Opção: ");
            
            switch (opcao) {
                case 1: criarReserva(); break;
                case 2: listarReservas(); break;
                case 3: listarReservasPorPousada(); break;
                case 4: listarReservasPorQuarto(); break;
                case 5: verificarDisponibilidade(); break;
                case 6: cancelarReserva(); break;
                case 0: break;
                default: System.out.println("✗ Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void criarReserva() {
        System.out.println("\n=== CRIAR RESERVA ===");
        try {
            Reserva reserva = new Reserva();
            
            reserva.setPousadaId(lerInteiro("ID da pousada: "));
            reserva.setQuartoId(lerInteiro("ID do quarto: "));
            
            System.out.print("Usuário da pessoa: ");
            reserva.setUsuarioPessoa(scanner.nextLine());
            
            System.out.print("Data entrada (dd/MM/yyyy): ");
            reserva.setDataEntrada(LocalDate.parse(scanner.nextLine(), dateFormatter));
            
            System.out.print("Data saída (dd/MM/yyyy): ");
            reserva.setDataSaida(LocalDate.parse(scanner.nextLine(), dateFormatter));
            
            // Verificar disponibilidade
            if (!reservaDAO.verificarDisponibilidade(reserva.getQuartoId(), 
                    reserva.getDataEntrada(), reserva.getDataSaida())) {
                System.out.println("✗ Quarto não disponível neste período!");
                return;
            }
            
            reserva.setStatus("S");
            
            reservaDAO.adicionar(reserva);
            System.out.println("✓ Reserva criada com sucesso! ID: " + reserva.getId());
            
        } catch (DateTimeParseException e) {
            System.out.println("✗ Data inválida! Use o formato dd/MM/yyyy");
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void listarReservas() {
        System.out.println("\n=== LISTA DE RESERVAS ===");
        try {
            var reservas = reservaDAO.listarTodos();
            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva cadastrada.");
            } else {
                for (Reserva r : reservas) {
                    System.out.println("────────────────────────────────");
                    System.out.println("ID: " + r.getId());
                    System.out.println("Pousada: " + r.getPousadaId());
                    System.out.println("Quarto: " + r.getQuartoId());
                    System.out.println("Hóspede: " + r.getUsuarioPessoa());
                    System.out.println("Entrada: " + r.getDataEntrada().format(dateFormatter));
                    System.out.println("Saída: " + r.getDataSaida().format(dateFormatter));
                    System.out.println("Status: " + (r.getStatus().equals("S") ? "Ativa" : "Cancelada"));
                }
                System.out.println("────────────────────────────────");
                System.out.println("Total: " + reservas.size());
            }
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void listarReservasPorPousada() {
        System.out.println("\n=== RESERVAS POR POUSADA ===");
        try {
            int pousadaId = lerInteiro("ID da pousada: ");
            var reservas = reservaDAO.listarPorPousada(pousadaId);
            
            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva encontrada.");
            } else {
                for (Reserva r : reservas) {
                    System.out.println("ID: " + r.getId() + " | Quarto: " + r.getQuartoId() + 
                                     " | " + r.getDataEntrada().format(dateFormatter) + 
                                     " até " + r.getDataSaida().format(dateFormatter));
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void listarReservasPorQuarto() {
        System.out.println("\n=== RESERVAS POR QUARTO ===");
        try {
            int quartoId = lerInteiro("ID do quarto: ");
            var reservas = reservaDAO.listarPorQuarto(quartoId);
            
            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva encontrada.");
            } else {
                for (Reserva r : reservas) {
                    System.out.println("ID: " + r.getId() + " | Hóspede: " + r.getUsuarioPessoa() + 
                                     " | " + r.getDataEntrada().format(dateFormatter) + 
                                     " até " + r.getDataSaida().format(dateFormatter));
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void verificarDisponibilidade() {
        System.out.println("\n=== VERIFICAR DISPONIBILIDADE ===");
        try {
            int quartoId = lerInteiro("ID do quarto: ");
            
            System.out.print("Data entrada (dd/MM/yyyy): ");
            LocalDate entrada = LocalDate.parse(scanner.nextLine(), dateFormatter);
            
            System.out.print("Data saída (dd/MM/yyyy): ");
            LocalDate saida = LocalDate.parse(scanner.nextLine(), dateFormatter);
            
            boolean disponivel = reservaDAO.verificarDisponibilidade(quartoId, entrada, saida);
            
            if (disponivel) {
                System.out.println("✓ Quarto DISPONÍVEL no período!");
            } else {
                System.out.println("✗ Quarto NÃO disponível no período.");
            }
            
        } catch (DateTimeParseException e) {
            System.out.println("✗ Data inválida! Use o formato dd/MM/yyyy");
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    private static void cancelarReserva() {
        System.out.println("\n=== CANCELAR RESERVA ===");
        try {
            int id = lerInteiro("ID da reserva: ");
            
            System.out.print("Confirmar cancelamento? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                Reserva r = reservaDAO.obterPorId(id);
                r.setStatus("N");
                reservaDAO.atualizar(r);
                System.out.println("✓ Reserva cancelada!");
            }
            
        } catch (Exception e) {
            System.out.println("✗ Erro: " + e.getMessage());
        }
    }

    // ========== UTILITÁRIOS ==========
    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("✗ Por favor, digite um número válido!");
            }
        }
    }
    
    private static String lerTextoObrigatorio(String mensagem, int minCaracteres) {
        while (true) {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("✗ Campo obrigatório! Não pode estar vazio.");
            } else if (texto.length() < minCaracteres) {
                System.out.println("✗ Mínimo de " + minCaracteres + " caracteres!");
            } else {
                return texto;
            }
        }
    }
}
