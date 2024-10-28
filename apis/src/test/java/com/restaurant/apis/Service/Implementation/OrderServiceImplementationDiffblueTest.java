package com.restaurant.apis.Service.Implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.LoggerContext;
import com.restaurant.apis.Model.Orders;
import com.restaurant.apis.Model.RestaurantTable;
import com.restaurant.apis.WebSocket.WebSocketPublisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OrderServiceImplementation.class})
@ExtendWith(SpringExtension.class)
class OrderServiceImplementationDiffblueTest {
    @MockBean
    private EntityManager entityManager;

    @Autowired
    private OrderServiceImplementation orderServiceImplementation;

    @MockBean
    private WebSocketPublisher webSocketPublisher;

    /**
     * Test {@link OrderServiceImplementation#createOrder(Orders, List)}.
     * <ul>
     *   <li>Then return {@link Orders} (default constructor).</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link OrderServiceImplementation#createOrder(Orders, List)}
     */
    @Test
    @DisplayName("Test createOrder(Orders, List) to ensure it returns Orders and updates Table status to true")
    void testCreateOrder_thenReturnOrders() {
        // Arrange
        RestaurantTable restaurantTable = new RestaurantTable();
        restaurantTable.setTableCapacity(4);
        restaurantTable.setTableId(1);
        restaurantTable.setTableStatus(false); // Start with table status as false

        RestaurantTable updatedTable = new RestaurantTable();
        updatedTable.setTableCapacity(4);
        updatedTable.setTableId(1);
        updatedTable.setTableStatus(true); // Expect table status to be updated to true

        Orders order = new Orders();
        order.setCustomerName("Jane Doe");
        order.setEmail("jane.doe@example.org");
        order.setOrderDate(LocalDate.now());
        order.setOrderTime(LocalTime.now());
        order.setOrderStatus("New");
        order.setOrderSubTotal(100.0);
        order.setOrderTotal(110.0);
        order.setTax(10.0);
        order.setPhoneNo(123456789L);
        order.setTableId(restaurantTable);

        // Mock behaviors
        when(entityManager.find(RestaurantTable.class, restaurantTable.getTableId())).thenReturn(restaurantTable);
        when(entityManager.merge(restaurantTable)).thenReturn(updatedTable);
        doNothing().when(entityManager).persist(order);
        doNothing().when(webSocketPublisher).sendOrderPlacedMessage();

        // Act
        Orders createdOrder = orderServiceImplementation.createOrder(order, new ArrayList<>());

        // Assert
        assertSame(order, createdOrder);
        assertEquals(true, order.getTableId().isTableStatus());
        verify(entityManager).find(RestaurantTable.class, restaurantTable.getTableId());
        verify(entityManager).merge(restaurantTable);
        verify(entityManager).persist(order);
        verify(webSocketPublisher).sendOrderPlacedMessage();
    }


    /**
     * Test {@link OrderServiceImplementation#createOrder(Orders, List)}.
     * <ul>
     *   <li>Then throw {@link NoResultException}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link OrderServiceImplementation#createOrder(Orders, List)}
     */
    @Test
    @DisplayName("Test createOrder(Orders, List); then throw NoResultException")
    void testCreateOrder_thenThrowNoResultException() {
        // Arrange
        RestaurantTable restaurantTable = new RestaurantTable();
        restaurantTable.setTableCapacity(1);
        restaurantTable.setTableId(1);
        restaurantTable.setTableStatus(true);

        RestaurantTable restaurantTable2 = new RestaurantTable();
        restaurantTable2.setTableCapacity(1);
        restaurantTable2.setTableId(1);
        restaurantTable2.setTableStatus(true);
        when(entityManager.merge(Mockito.<RestaurantTable>any())).thenReturn(restaurantTable2);
        doNothing().when(entityManager).persist(Mockito.<Object>any());
        when(entityManager.find(Mockito.<Class<RestaurantTable>>any(), Mockito.<Object>any())).thenReturn(restaurantTable);
        doThrow(new NoResultException("An error occurred")).when(webSocketPublisher).sendOrderPlacedMessage();

        RestaurantTable TableId = new RestaurantTable();
        TableId.setTableCapacity(1);
        TableId.setTableId(1);
        TableId.setTableStatus(true);

        Orders order = new Orders();
        order.setCustomerName("Customer Name");
        order.setEmail("jane.doe@example.org");
        order.setOrderDate(LocalDate.of(1970, 1, 1));
        order.setOrderId(1);
        order.setOrderStatus("Order Status");
        order.setOrderSubTotal(10.0d);
        order.setOrderTime(LocalTime.MIDNIGHT);
        order.setOrderTotal(10.0d);
        order.setPhoneNo(1L);
        order.setTableId(TableId);
        order.setTax(10.0d);

        // Act and Assert
        assertThrows(NoResultException.class, () -> orderServiceImplementation.createOrder(order, new ArrayList<>()));
        verify(webSocketPublisher).sendOrderPlacedMessage();
        verify(entityManager).find(isA(Class.class), isA(Object.class));
        verify(entityManager).merge(isA(RestaurantTable.class));
        verify(entityManager).persist(isA(Object.class));
    }

    /**
     * Test {@link OrderServiceImplementation#getOrderDetails(int)}.
     * <p>
     * Method under test: {@link OrderServiceImplementation#getOrderDetails(int)}
     */


    /**
     * Test {@link OrderServiceImplementation#updateOrder(Orders, List)}.
     * <ul>
     *   <li>When {@link Orders} (default constructor) CustomerName is
     * {@code Customer Name}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link OrderServiceImplementation#updateOrder(Orders, List)}
     */

    /**
     * Test {@link OrderServiceImplementation#getOrderByDuration(LocalDate)}.
     * <p>
     * Method under test:
     * {@link OrderServiceImplementation#getOrderByDuration(LocalDate)}
     */

    /**
     * Test {@link OrderServiceImplementation#changeOrderStatusToPreparing(int)}.
     * <ul>
     *   <li>Then return {@link Orders} (default constructor).</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link OrderServiceImplementation#changeOrderStatusToPreparing(int)}
     */
    @Test
    @DisplayName("Test changeOrderStatusToPreparing(int); then return Orders (default constructor)")
    void testChangeOrderStatusToPreparing_thenReturnOrders() {
        // Arrange
        RestaurantTable TableId = new RestaurantTable();
        TableId.setTableCapacity(1);
        TableId.setTableId(1);
        TableId.setTableStatus(true);

        Orders orders = new Orders();
        orders.setCustomerName("Customer Name");
        orders.setEmail("jane.doe@example.org");
        orders.setOrderDate(LocalDate.of(1970, 1, 1));
        orders.setOrderId(1);
        orders.setOrderStatus("Order Status");
        orders.setOrderSubTotal(10.0d);
        orders.setOrderTime(LocalTime.MIDNIGHT);
        orders.setOrderTotal(10.0d);
        orders.setPhoneNo(1L);
        orders.setTableId(TableId);
        orders.setTax(10.0d);
        when(entityManager.find(Mockito.<Class<Orders>>any(), Mockito.<Object>any())).thenReturn(orders);
        doNothing().when(webSocketPublisher).sendKitchenStatusUpdated();

        // Act
        Orders actualChangeOrderStatusToPreparingResult = orderServiceImplementation.changeOrderStatusToPreparing(1);

        // Assert
        verify(webSocketPublisher).sendKitchenStatusUpdated();
        verify(entityManager).find(isA(Class.class), isA(Object.class));
        assertSame(orders, actualChangeOrderStatusToPreparingResult);
    }

    /**
     * Test {@link OrderServiceImplementation#changeOrderStatusToPreparing(int)}.
     * <ul>
     *   <li>Then throw {@link NoResultException}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link OrderServiceImplementation#changeOrderStatusToPreparing(int)}
     */
    @Test
    @DisplayName("Test changeOrderStatusToPreparing(int); then throw NoResultException")
    void testChangeOrderStatusToPreparing_thenThrowNoResultException() {
        // Arrange
        RestaurantTable TableId = new RestaurantTable();
        TableId.setTableCapacity(1);
        TableId.setTableId(1);
        TableId.setTableStatus(true);

        Orders orders = new Orders();
        orders.setCustomerName("Customer Name");
        orders.setEmail("jane.doe@example.org");
        orders.setOrderDate(LocalDate.of(1970, 1, 1));
        orders.setOrderId(1);
        orders.setOrderStatus("Order Status");
        orders.setOrderSubTotal(10.0d);
        orders.setOrderTime(LocalTime.MIDNIGHT);
        orders.setOrderTotal(10.0d);
        orders.setPhoneNo(1L);
        orders.setTableId(TableId);
        orders.setTax(10.0d);
        when(entityManager.find(Mockito.<Class<Orders>>any(), Mockito.<Object>any())).thenReturn(orders);
        doThrow(new NoResultException("An error occurred")).when(webSocketPublisher).sendKitchenStatusUpdated();

        // Act and Assert
        assertThrows(NoResultException.class, () -> orderServiceImplementation.changeOrderStatusToPreparing(1));
        verify(webSocketPublisher).sendKitchenStatusUpdated();
        verify(entityManager).find(isA(Class.class), isA(Object.class));
    }

    /**
     * Test {@link OrderServiceImplementation#changeOrderStatusToComplete(Orders)}.
     * <ul>
     *   <li>Then {@link Orders} (default constructor) OrderStatus is
     * {@code Completed}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link OrderServiceImplementation#changeOrderStatusToComplete(Orders)}
     */

    /**
     * Test {@link OrderServiceImplementation#changeOrderStatusToComplete(Orders)}.
     * <ul>
     *   <li>Then return {@link Orders}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link OrderServiceImplementation#changeOrderStatusToComplete(Orders)}
     */
    @Test
    @DisplayName("Test changeOrderStatusToComplete(Orders) updates OrderStatus to 'Completed'")
    void testChangeOrderStatusToComplete_thenOrderStatusIsCompleted() {
        // Arrange
        RestaurantTable table = new RestaurantTable();
        table.setTableCapacity(1);
        table.setTableId(1);
        table.setTableStatus(true);

        Orders order = new Orders();
        order.setOrderStatus("Pending"); // Initial status to be changed

        // Act
        Orders updatedOrder = orderServiceImplementation.changeOrderStatusToComplete(order);

        // Assert
        assertEquals("Completed", updatedOrder.getOrderStatus(), "OrderStatus should be 'Completed'");
    }


    /**
     * Test {@link OrderServiceImplementation#getOrdersForKitchenScheduling()}.
     * <p>
     * Method under test:
     * {@link OrderServiceImplementation#getOrdersForKitchenScheduling()}
     */

    /**
     * Test
     * {@link OrderServiceImplementation#getHighestSellingMenuItemByCategory()}.
     * <p>
     * Method under test:
     * {@link OrderServiceImplementation#getHighestSellingMenuItemByCategory()}
     */

    /**
     * Test {@link OrderServiceImplementation#getYearlySales()}.
     * <p>
     * Method under test: {@link OrderServiceImplementation#getYearlySales()}
     */


    /**
     * Test {@link OrderServiceImplementation#getMonthlySales()}.
     * <p>
     * Method under test: {@link OrderServiceImplementation#getMonthlySales()}
     */

    /**
     * Test {@link OrderServiceImplementation#getDailySales()}.
     * <p>
     * Method under test: {@link OrderServiceImplementation#getDailySales()}
     */

}
