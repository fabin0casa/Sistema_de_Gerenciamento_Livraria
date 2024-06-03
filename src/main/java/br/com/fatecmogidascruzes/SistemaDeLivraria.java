package br.com.fatecmogidascruzes;

import java.time.LocalDate;

import br.com.fatecmogidascruzes.model.entity.Cliente;
import br.com.fatecmogidascruzes.model.entity.Funcionario;
import br.com.fatecmogidascruzes.model.entity.Livro;
import br.com.fatecmogidascruzes.model.repository.ClienteRepository;
import br.com.fatecmogidascruzes.model.repository.FuncionarioRepository;
import br.com.fatecmogidascruzes.model.repository.LivroRepository;
import br.com.fatecmogidascruzes.service.impl.ClienteServiceImpl;
import br.com.fatecmogidascruzes.service.impl.FuncionarioServiceImpl;
import br.com.fatecmogidascruzes.service.impl.LivroServiceImpl;
import br.com.fatecmogidascruzes.validator.LivroValidator;
import br.com.fatecmogidascruzes.validator.UsuarioValidator;

public class SistemaDeLivraria {
    public static void main(String[] args) {

        LivroRepository livroRepository = new LivroRepository();
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
        ClienteRepository clienteRepository = new ClienteRepository();

        LivroValidator livroValidator = new LivroValidator();
        UsuarioValidator usuarioValidator = new UsuarioValidator(clienteRepository, funcionarioRepository);

        LivroServiceImpl service = new LivroServiceImpl(livroRepository, livroValidator);
        ClienteServiceImpl clienteService = new ClienteServiceImpl(clienteRepository, usuarioValidator);
        FuncionarioServiceImpl funcionarioService = new FuncionarioServiceImpl(funcionarioRepository, usuarioValidator);


        //Criação de livros
        LocalDate dataDePublicacao = LocalDate.of(0, 1, 1);
        Livro livro = new Livro(1, "0123456789", "", "Biblia", "Portugues", "Jesus", 7, "Reino do Ceus", 1000, dataDePublicacao, 300.00, "Religiao");

        LocalDate dataDePublicacao1 = LocalDate.of(2020, 7, 20);
        Livro livro1 = new Livro(2, "0306406152", "", "O Alquimista", "Portugues", "Paulo Coelho", 10, "HarperCollins", 200, dataDePublicacao1, 150.00, "Romance");

        LocalDate dataDePublicacao2 = LocalDate.of(2018, 5, 15);
        Livro livro2 = new Livro(3, "0060930314", "", "1984", "Ingles", "George Orwell", 5, "Secker & Warburg", 300, dataDePublicacao2, 200.00, "Ficção Científica");

        LocalDate dataDePublicacao3 = LocalDate.of(2016, 3, 10);
        Livro livro3 = new Livro(11, "0451524934", "", "Dom Quixote", "Espanhol", "Miguel de Cervantes", 7, "Francisco de Robles", 1000, dataDePublicacao3, 250.00, "Romance");

        //Adicionando livros
        service.adicionarLivro(livro);
        service.adicionarLivro(livro1);
        service.adicionarLivro(livro2);
        service.adicionarLivro(livro3);
      
        //testando atualizar livros
        System.out.println("ID: " + livro.getId() + "\n Título: " + livro.getTitulo() + "\n Autores: " + livro.getAutor() + "\n Estoque: " + livro.getEstoque() + "\n Categoria: " + livro.getCategoria());

        //atualizando livro erro
        livro = new Livro(1, "0123456789", "", "Biblia", "Portugues", "profeta", -1, "Reino do Ceus", 1000, dataDePublicacao, 300.00, "Religiao");
        service.atualizarLivro(livro);
        System.out.println(livroRepository.findAllWhereExistEstoque());
        
        //atualizando livro correto
        livro = new Livro(1, "0123456789", "", "Biblia", "Portugues", "profeta", 0, "Reino do Ceus", 1000, dataDePublicacao, 300.00, "Religiao");
        service.atualizarLivro(livro);

        System.out.println("\n---- BUSCAR ---" );
        System.out.println(service.buscarLivro(7,"Romance"));
        System.out.println(livroRepository.findAllWhereExistEstoque());
        
        //testando excluir livros
        LocalDate dataDePublicacao4 = LocalDate.of(2023, 8, 15);
        Livro livro4 = new Livro(5, "6584956245", "9786584956247", "O Pequeno Principe", "Portugues", "Antoine de Saint-Exupery", 5, "Editora Garnier", 96, dataDePublicacao4, 19.90, "Acao e Aventura");
        service.adicionarLivro(livro4);
        
        System.out.println("Tentando excluir livro com ID 1:");
        service.excluirLivroPorId(1);

        System.out.println("Tentando excluir livro com ISBN-10 0306406152:");
        service.excluirLivroPorIsbn10("0306406152");

        System.out.println("Tentando excluir livro com ISBN-13 9786584956247:");
        service.excluirLivroPorIsbn13("9786584956247");

        System.out.println("Tentando excluir livro com ID 6 (inexistente):");
        service.excluirLivroPorId(6);
        
        System.out.println(livroRepository.findAllWhereExistEstoque());
        
        // Testando a criação de Funcionario
        Funcionario ana = new Funcionario();
        ana.setId(1);
        ana.setNome("Ana");
        ana.setEndereco("Endereço da Ana");
        ana.setEmail("ana@email.com");
        ana.setTelefone("12934567890");
        ana.setCargo("Cargo da Ana");
        ana.setDataContratacao(LocalDate.now());

        funcionarioService.adicionarFuncionario(ana);
        System.out.println(funcionarioRepository.findAll());

        // Testando a criação de cliente
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Nome do Cliente");
        cliente.setEndereco("Endereço do Cliente");
        cliente.setEmail("cliente@email.com");
        cliente.setTelefone("12934567890");
        cliente.setMetodoPagamento("Cartão de Crédito");
        cliente.setDataCadastro(LocalDate.now());
        
        clienteService.adicionarCliente(cliente);
        System.out.println(clienteRepository.findAll());

    }
}
