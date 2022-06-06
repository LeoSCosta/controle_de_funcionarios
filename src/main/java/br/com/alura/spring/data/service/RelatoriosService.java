package br.com.alura.spring.data.service;


import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatoriosService {

    private Boolean system = true;
    private final FuncionarioRepository funcionarioRepository;
    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RelatoriosService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("\nQual acao de cargo deseja executar");
            System.out.println("0 - Sair");
            System.out.println("1 - Buscar funcionario por nome");
            System.out.println("2 - Buscar funcionario como nome iniciando com");
            System.out.println("3 - Encontrar Por Nome e Salário Maior Que E DataContracao");
            System.out.println("4 - Encontrar Por Nome e Salário Maior Que E DataContracao (JPQL)");
            System.out.println("5 - Contratado apos determinada data");
            System.out.println("6 - Buscar funcionarios por maior salario");

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    buscarFuncionarioNome(scanner);
                    break;
                case 2:
                    findByNomeStartingWith(scanner);
                    break;
                case 3:
                    findByNomeAndSalarioGreaterThanAndDataContratacao(scanner);
                    break;
                case 4:
                    findNomeSalarioMaiorDataContratacao(scanner);
                    break;
                case 5:
                    contratadoAposEstaDAta(scanner);
                    break;
                case 6:
                    buscarFuncionariosPorMaiorSalario();
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    public void buscarFuncionarioNome(Scanner scanner){
        System.out.println("Qual o nome deseja pesquisar?");
        String nome = scanner.next();
        Iterable<Funcionario> list = funcionarioRepository.findByNome(nome);

        list.forEach(System.out::println);
    }

    public void findByNomeStartingWith(Scanner scanner){
        System.out.println("Buscar por nomes que iniciam com: ");
        String nome = scanner.next();
        List<Funcionario> list = funcionarioRepository.findByNomeStartingWith(nome);
        list.forEach(System.out::println);
    }

    public void findByNomeAndSalarioGreaterThanAndDataContratacao (Scanner scanner){
        System.out.println("Digite o nome:");
        String nome = scanner.next();

        System.out.println("Digite o salario:");
        Double salario = scanner.nextDouble();

        System.out.println("Digite a data de contratação");
        String data = scanner.next();
        LocalDate date = LocalDate.parse(data, formatter);

        List<Funcionario> list = funcionarioRepository
                .findByNomeAndSalarioGreaterThanAndDataContratacao(nome, salario, date);

        list.forEach(System.out::println);
    }

    public void findNomeSalarioMaiorDataContratacao (Scanner scanner){
        System.out.println("Digite o nome:");
        String nome = scanner.next();

        System.out.println("Digite o salario:");
        Double salario = scanner.nextDouble();

        System.out.println("Digite a data de contratação");
        String data = scanner.next();
        LocalDate date = LocalDate.parse(data, formatter);

        List<Funcionario> list = funcionarioRepository
                .findNomeSalarioMaiorDataContratacao(nome, salario, date);
        list.forEach(System.out::println);
    }

    public void contratadoAposEstaDAta(Scanner scanner){

        System.out.println("Digite a data:");
        String data = scanner.next();
        LocalDate date = LocalDate.parse(data, formatter);

        List<Funcionario> funcionarios = funcionarioRepository.contratadoAposEstaData(date);
        funcionarios.forEach(System.out::println);
    }

    public void buscarFuncionariosPorMaiorSalario(){

        List<FuncionarioProjecao> list = funcionarioRepository.BuscarFuncionariosPorMaiorSalario();
        list.forEach(f -> System.out.println
                ("Funcionario: Id: " + f.getId() + " Nome: " + f.getNome() + " Salario: " + f.getSalario()));
    }



}
