package br.com.alura.spring.data.service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.specification.SpecificationFuncionario;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static br.com.alura.spring.data.service.RelatoriosService.formatter;

@Service
public class RelatorioFuncionarioDinamico {

    private final FuncionarioRepository funcionarioRepository;

    public RelatorioFuncionarioDinamico(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner) {
        System.out.println("Digite o nome:");
        String nome = scanner.next();

        if (nome.equalsIgnoreCase("null")){
            nome = null;
        }
        System.out.println("Digite o CPF:");
        String cpf = scanner.next();

        if (cpf.equalsIgnoreCase("null")){
            cpf = null;
        }
        System.out.println("Digite o salario:");
        Double salario = scanner.nextDouble();

        if (salario == 0){
            salario = null;
        }

        System.out.println("Digite o nome:");
        String data = scanner.next();
        LocalDate dataContracao;
        if (data.equalsIgnoreCase("null")){
            dataContracao = null;
        }else{

            dataContracao = LocalDate.parse(data, formatter);
        }

        List<Funcionario> funcionarios = funcionarioRepository
                .findAll(Specification.where(SpecificationFuncionario.nome(nome))
                        .or(SpecificationFuncionario.cpf(cpf))
                        .or(SpecificationFuncionario.salario(salario))
                        .or(SpecificationFuncionario.dataContracao(dataContracao)));
        funcionarios.forEach(System.out::println);
    }
}
