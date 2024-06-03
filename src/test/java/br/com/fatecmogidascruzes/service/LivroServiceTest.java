package br.com.fatecmogidascruzes.service;

import br.com.fatecmogidascruzes.model.entity.Livro;
import br.com.fatecmogidascruzes.model.repository.LivroRepository;
import br.com.fatecmogidascruzes.service.impl.LivroServiceImpl;
import br.com.fatecmogidascruzes.validator.LivroValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LivroServiceTest {

    private LivroRepository livroRepository;
    private LivroValidator livroValidator;
    private LivroServiceImpl service;

    @BeforeEach
    public void setUp() {
        livroRepository = mock(LivroRepository.class);
        livroValidator = mock(LivroValidator.class);
        service = new LivroServiceImpl(livroRepository, livroValidator);
    }

    @Test
    public void testAdicionarLivro() {
        LocalDate dataDePublicacao = LocalDate.of(0, 1, 1);
        Livro livro = new Livro(1, "0123456789", "", "Biblia", "Portugues", "Jesus", 7, "Reino do Ceus", 1000, dataDePublicacao, 300.00, "Religiao");

        when(livroValidator.validarAdicionar(livro)).thenReturn(true);
        service.adicionarLivro(livro);

        verify(livroRepository, times(1)).adicionarLivro(livro);
    }

    @Test
    public void testAtualizarLivro() {
        LocalDate dataDePublicacao = LocalDate.of(0, 1, 1);
        Livro livro = new Livro(1, "0123456789", "", "Biblia", "Portugues", "Jesus", 7, "Reino do Ceus", 1000, dataDePublicacao, 300.00, "Religiao");

        livro = new Livro(livro.getId(), livro.getIsbn10(), livro.getIsbn13(), livro.getTitulo(), livro.getIdioma(), "profeta", 0, livro.getEditora(), livro.getNumeroDePaginas(), livro.getDataDePublicacao(), livro.getPreco(), livro.getCategoria());
        when(livroValidator.validarAlterar(livro)).thenReturn(true);
        service.atualizarLivro(livro);

        verify(livroRepository, times(1)).alterarLivro(0, livro);
    }

    @Test
    public void testFindAllWhereExistEstoque() {
        LocalDate dataDePublicacao = LocalDate.of(0, 1, 1);
        Livro livro = new Livro(1, "0123456789", "", "Biblia", "Portugues", "Jesus", 7, "Reino do Ceus", 1000, dataDePublicacao, 300.00, "Religiao");
        Livro livro1 = new Livro(2, "0306406152", "", "O Alquimista", "Portugues", "Paulo Coelho", 10, "HarperCollins", 200, LocalDate.of(2020, 7, 20), 150.00, "Romance");

        List<Livro> livros = Arrays.asList(livro, livro1);

        when(livroRepository.findAllWhereExistEstoque()).thenReturn(livros);

        List<Livro> result = livroRepository.findAllWhereExistEstoque();

        assertEquals(2, result.size());
        verify(livroRepository, times(1)).findAllWhereExistEstoque();
    }
}