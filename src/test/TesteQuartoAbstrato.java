package test;

import dto.*;

public class TesteQuartoAbstrato {
    public static void main(String[] args) {
        System.out.println("=== Teste de Classe Abstrata - Quarto ===\n");
        
        // ❌ Tentando instanciar Quarto diretamente (deve dar erro)
        // Quarto q = new Quarto(1, "Teste", 2, 100, false, false); // Descomente para ver o erro
        
        // ✅ Instanciando as subclasses (deve funcionar)
        Quarto standard = new QuartoStandard(1, "Quarto 101", 2, 100);
        Quarto deluxe = new QuartoDeluxe(1, "Quarto 201", 2, 150, true);
        Quarto presidencial = new QuartoPresidencial(1, "Suíte 301", 3, 300, true, true);
        
        // Testando polimorfismo
        System.out.println("1. " + standard.getDescricaoTipo());
        System.out.println("2. " + deluxe.getDescricaoTipo());
        System.out.println("3. " + presidencial.getDescricaoTipo());
        
        System.out.println("\n=== Testando toString() ===");
        System.out.println(standard.toString());
        System.out.println(deluxe.toString());
        System.out.println(presidencial.toString());
        
        System.out.println("\n✅ Classe abstrata funcionando corretamente!");
    }
}
