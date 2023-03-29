package com.algrince.finaltask;

import com.algrince.finaltask.controllers.CategoriesController;
import com.algrince.finaltask.dto.CategoryDTO;
import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.Category;
import com.algrince.finaltask.services.CategoriesService;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.validators.AccessValidator;
import com.algrince.finaltask.validators.CategoryValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;


import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;


import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CategoriesServiceTest {

    @Mock
    private AccessValidator accessValidator;

    @Mock
    private CategoriesService categoriesService;

    @Mock
    private DTOMapper dtoMapper;

    @Mock
    private CategoryValidator categoryValidator;

    @InjectMocks
    private CategoriesController categoriesController;

    @Captor
    private ArgumentCaptor<BindingResult> bindingResultCaptor;

    private MockMvc mockMvc;

    @BeforeEach
    public void setMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoriesController).build();
    }

    @Test
    public void testGetCategoriesListForAdmin() throws Exception {

        boolean isAdmin = true;
        List<Category> categories = createCategories();
        List<CategoryDTO> categoriesDTO = createCategoriesDTOs();

        when(accessValidator.authUserIsAdmin()).thenReturn(isAdmin);
        when(categoriesService.findAll(isAdmin)).thenReturn(categories);
        when(dtoMapper.mapList(categories, CategoryDTO.class)).thenReturn(categoriesDTO);


        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(3)))
                        .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                        .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                        .andExpect(jsonPath("$[2].id", Matchers.is(3)));


        verify(accessValidator).authUserIsAdmin();
        verify(categoriesService).findAll(isAdmin);
        verify(dtoMapper).mapList(categories, CategoryDTO.class);
    }

    @Test
    public void testGetCategoriesListForNotAdmin() throws Exception {

        boolean isAdmin = false;
        List<Category> categories = createCategories();
        categories.remove(1);
        List<CategoryDTO> categoriesDTO = createCategoriesDTOs();
        categoriesDTO.remove(1);

        when(accessValidator.authUserIsAdmin()).thenReturn(isAdmin);
        when(categoriesService.findAll(isAdmin)).thenReturn(categories);
        when(dtoMapper.mapList(categories, CategoryDTO.class)).thenReturn(categoriesDTO);


        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[1].id", Matchers.is(3)));


        verify(accessValidator).authUserIsAdmin();
        verify(categoriesService).findAll(isAdmin);
        verify(dtoMapper).mapList(categories, CategoryDTO.class);
    }

    @Test
    public void testGetCategoryByExistentId() throws Exception {
        Long categoryId = 1L;
        Category category = Category.builder()
                .id(1L).name("Category")
                .isDeleted(false).build();

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1L).name("Category")
                .isDeleted(false).build();

        when(categoriesService.findById(categoryId)).thenReturn(category);
        when(dtoMapper.mapClass(category, CategoryDTO.class)).thenReturn(categoryDTO);

        mockMvc.perform(get("/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(categoryId.intValue())));

        verify(categoriesService).findById(categoryId);
        verify(dtoMapper).mapClass(category, CategoryDTO.class);

    }

    @Test
    public void testGetCategoryByNonExistentId() throws Exception {
        Long categoryId = 88L;

        when(categoriesService.findById(categoryId)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/categories/{id}", categoryId))
                .andExpect(status().isNotFound());

        verify(categoriesService).findById(categoryId);

    }


    private List<Category> createCategories() {
        List<Category> categories = new ArrayList<>();

        Category category1 = Category.builder()
                .id(1L)
                .name("Category Uno")
                .isDeleted(false)
                .build();

        Category category2 = Category.builder()
                .id(2L)
                .name("Category Dos")
                .isDeleted(true)
                .build();

        Category category3 = Category.builder()
                .id(3L)
                .name("Category Tres")
                .isDeleted(false)
                .build();

        categories.add(category1);
        categories.add(category2);
        categories.add(category3);

        return categories;

    }

    private List<CategoryDTO> createCategoriesDTOs() {
        List<CategoryDTO> categoriesDTO = new ArrayList<>();

        CategoryDTO categoryDTO1 = CategoryDTO.builder()
                .id(1L)
                .name("Category Uno")
                .isDeleted(false)
                .build();

        CategoryDTO categoryDTO2 = CategoryDTO.builder()
                .id(2L)
                .name("Category Dos")
                .isDeleted(true)
                .build();

        CategoryDTO categoryDTO3 = CategoryDTO.builder()
                .id(3L)
                .name("Category Tres")
                .isDeleted(false)
                .build();

        categoriesDTO.add(categoryDTO1);
        categoriesDTO.add(categoryDTO2);
        categoriesDTO.add(categoryDTO3);

        return categoriesDTO;

    }
}
