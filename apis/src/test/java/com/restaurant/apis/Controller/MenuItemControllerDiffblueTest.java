package com.restaurant.apis.Controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.apis.Model.MenuCategory;
import com.restaurant.apis.Model.MenuItem;
import com.restaurant.apis.Service.MenuItemService;

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

@ContextConfiguration(classes = {MenuItemController.class})
@ExtendWith(SpringExtension.class)
class MenuItemControllerDiffblueTest {
    @Autowired
    private MenuItemController menuItemController;

    @MockBean
    private MenuItemService menuItemService;

    /**
     * Test {@link MenuItemController#addMenuItems(List)}.
     * <p>
     * Method under test: {@link MenuItemController#addMenuItems(List)}
     */
    @Test
    @DisplayName("Test addMenuItems(List)")
    void testAddMenuItems() throws Exception {
        // Arrange
        when(menuItemService.addMenuItems(Mockito.<List<MenuItem>>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/menu-items/addMenuItems")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(menuItemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link MenuItemController#getAllMenuItems()}.
     * <p>
     * Method under test: {@link MenuItemController#getAllMenuItems()}
     */
    @Test
    @DisplayName("Test getAllMenuItems()")
    void testGetAllMenuItems() throws Exception {
        // Arrange
        when(menuItemService.getAllMenuItems()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/menu-items/getAllMenuItems");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(menuItemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link MenuItemController#updaMenuItem(int, MenuItem)}.
     * <p>
     * Method under test: {@link MenuItemController#updaMenuItem(int, MenuItem)}
     */
    @Test
    @DisplayName("Test updaMenuItem(int, MenuItem)")
    void testUpdaMenuItem() throws Exception {
        // Arrange
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setMenuCategoryId(1);
        menuCategory.setMenuCategoryName("Menu Category Name");

        MenuItem menuItem = new MenuItem();
        menuItem.setAvailable(true);
        menuItem.setMenuCategoryId(menuCategory);
        menuItem.setMenuItemDescription("Menu Item Description");
        menuItem.setMenuItemId(1);
        menuItem.setMenuItemName("Menu Item Name");
        menuItem.setMenuItemPrice(10.0d);

        when(menuItemService.updateMenuItem(anyInt(), Mockito.<MenuItem>any())).thenReturn(menuItem);

        String content = (new ObjectMapper()).writeValueAsString(menuItem);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/menu-items/updateMenuItem")
                .param("menu_item_id", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(menuItemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.available").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.menuItemDescription").value("Menu Item Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.menuItemPrice").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.menuItemName").value("Menu Item Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.menuItemId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.menuCategoryId.menuCategoryId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.menuCategoryId.menuCategoryName").value("Menu Category Name"));
    }

    /**
     * Test {@link MenuItemController#deleteMenuItem(int)}.
     * <p>
     * Method under test: {@link MenuItemController#deleteMenuItem(int)}
     */
    @Test
    @DisplayName("Test deleteMenuItem(int)")
    void testDeleteMenuItem() throws Exception {
        // Arrange
        when(menuItemService.deleteMenuItem(anyInt())).thenReturn("Delete Menu Item");
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/menu-items/deleteMenuItem");
        MockHttpServletRequestBuilder requestBuilder = deleteResult.param("menu_item_id", String.valueOf(1));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(menuItemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Delete Menu Item"));
    }
}
