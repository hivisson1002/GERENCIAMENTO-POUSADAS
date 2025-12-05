package test;

import dto.*;

public class TestePousada {
    public static void main(String[] args) {
        System.out.println("=== Teste da Classe Pousada ===\n");
        
        // Criando uma pousada
        Pousada pousada = new Pousada();
        pousada.setId(1);
        pousada.setNome("Pousada Mar Azul");
        pousada.setEndereco("Avenida Beira Mar, 1000");
        pousada.setBairro("Centro");
        pousada.setCidade("Maceió");
        pousada.setEstado("AL");
        pousada.setTelefone("(82) 3333-4444");
        pousada.setEstrelas(5);
        pousada.setSite("www.marazul.com.br");
        pousada.setObservacao("Café da manhã incluído");
        pousada.setStatus("S");
        
        System.out.println("=== Dados da Pousada ===");
        System.out.println("ID: " + pousada.getId());
        System.out.println("Nome: " + pousada.getNome());
        System.out.println("Endereço: " + pousada.getEndereco());
        System.out.println("Bairro: " + pousada.getBairro());
        System.out.println("Cidade: " + pousada.getCidade());
        System.out.println("Estado: " + pousada.getEstado());
        System.out.println("Telefone: " + pousada.getTelefone());
        System.out.println("Estrelas: " + pousada.getEstrelas());
        System.out.println("Site: " + pousada.getSite());
        System.out.println("Observação: " + pousada.getObservacao());
        System.out.println("Status: " + pousada.getStatus());
        
        // Testando adição de quartos
        System.out.println("\n=== Adicionando Quartos ===");
        Quarto quarto1 = new QuartoStandard(1, "Standard 101", 2, 150);
        Quarto quarto2 = new QuartoDeluxe(1, "Deluxe 201", 2, 250, true);
        Quarto quarto3 = new QuartoPresidencial(1, "Presidencial 301", 4, 500, true, true);
        
        pousada.adicionarQuarto(quarto1);
        pousada.adicionarQuarto(quarto2);
        pousada.adicionarQuarto(quarto3);
        
        System.out.println("Total de quartos: " + pousada.getQuartos().size());
        
        System.out.println("\n=== Listando Quartos da Pousada ===");
        for (int i = 0; i < pousada.getQuartos().size(); i++) {
            Quarto q = pousada.getQuartos().get(i);
            System.out.println((i+1) + ". " + q.getNome() + " - R$" + q.getValor_dia() + "/dia");
            System.out.println("   " + q.getDescricaoTipo());
        }
        
        // Testando remoção de quarto
        System.out.println("\n=== Removendo um Quarto ===");
        pousada.removerQuarto(quarto2);
        System.out.println("Total de quartos após remoção: " + pousada.getQuartos().size());
        
        System.out.println("\n=== Quartos Restantes ===");
        for (Quarto q : pousada.getQuartos()) {
            System.out.println("- " + q.getNome());
        }
        
        System.out.println("\n✅ Teste da classe Pousada concluído com sucesso!");
    }
}
