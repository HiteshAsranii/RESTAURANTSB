package com.restaurant.apis.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.apis.Model.MenuCategory;
import com.restaurant.apis.Service.MenuCategoryService;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MenuCategoryController.class})
@ExtendWith(SpringExtension.class)
class MenuCategoryControllerDiffblueTest {
    @Autowired
    private MenuCategoryController menuCategoryController;

    @MockBean
    private MenuCategoryService menuCategoryService;

    /**
     * Test {@link MenuCategoryController#addMenuCategory(List)}.
     * <p>
     * Method under test: {@link MenuCategoryController#addMenuCategory(List)}
     */
    @Test
    @DisplayName("Test addMenuCategory(List)")
    void testAddMenuCategory() throws Exception {
        // Arrange
        when(menuCategoryService.addMenuCategory(Mockito.<List<MenuCategory>>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/menu-categories/addMenuCategories")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(menuCategoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link MenuCategoryController#updMenuCategory(int, MenuCategory)}.
     * <p>
     * Method under test:
     * {@link MenuCategoryController#updMenuCategory(int, MenuCategory)}
     */
    @Test
    @DisplayName("Test updMenuCategory(int, MenuCategory)")
    void testUpdMenuCategory() throws Exception {
        // Arrange
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setMenuCategoryId(1);
        menuCategory.setMenuCategoryName("Menu Category Name");
        when(menuCategoryService.updateMenuCategory(anyInt(), Mockito.<MenuCategory>any())).thenReturn(menuCategory);

        MenuCategory menuCategory2 = new MenuCategory();
        menuCategory2.setMenuCategoryId(1);
        menuCategory2.setMenuCategoryName("Menu Category Name");
        String content = (new ObjectMapper()).writeValueAsString(menuCategory2);

        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/menu-categories/updateMenuCategory");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("menu_category_id", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        String actualResponse = MockMvcBuilders.standaloneSetup(menuCategoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Use Jackson ObjectMapper to compare JSON content ignoring order
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedResponse = "{\"menuCategoryId\":1,\"menuCategoryName\":\"Menu Category Name\"}";

        assertEquals(objectMapper.readTree(expectedResponse), objectMapper.readTree(actualResponse));
    }


    /**
     * Test {@link MenuCategoryController#getAllMenuCategories()}.
     * <p>
     * Method under test: {@link MenuCategoryController#getAllMenuCategories()}
     */
    @Test
    @DisplayName("Test getAllMenuCategories()")
    void testGetAllMenuCategories() throws Exception {
        // Arrange
        when(menuCategoryService.getAllMenuCategories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/menu-categories/getAllMenuCategories");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(menuCategoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link MenuCategoryController#deleteMenuCategory(int)}.
     * <p>
     * Method under test: {@link MenuCategoryController#deleteMenuCategory(int)}
     */
    @Test
    @DisplayName("Test deleteMenuCategory(int)")
    void testDeleteMenuCategory() throws Exception {
        // Arrange
        when(menuCategoryService.deleteMenuCategory(anyInt())).thenReturn("Delete Menu Category");
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/menu-categories/deleteMenuCategory");
        MockHttpServletRequestBuilder requestBuilder = deleteResult.param("MenuCategoryid", String.valueOf(1));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(menuCategoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Delete Menu Category"));
    }
}
