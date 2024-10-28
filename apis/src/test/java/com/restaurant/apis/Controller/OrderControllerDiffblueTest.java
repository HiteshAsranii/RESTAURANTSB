package com.restaurant.apis.Controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.apis.Model.OrderItems;
import com.restaurant.apis.Model.OrderRequestWrapper;
import com.restaurant.apis.Model.Orders;
import com.restaurant.apis.Model.RestaurantTable;
import com.restaurant.apis.Service.Implementation.OrderServiceImplementation;
import com.restaurant.apis.Service.OrderService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;

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

@ContextConfiguration(classes = {OrderController.class})
@ExtendWith(SpringExtension.class)
class OrderControllerDiffblueTest {
    @Autowired
    private OrderController orderController;

    @MockBean
    private OrderService orderService;

    /**
     * Test {@link OrderController#createOrder(OrderRequestWrapper)}.
     * <p>
     * Method under test: {@link OrderController#createOrder(OrderRequestWrapper)}
     */
    @Test
    @DisplayName("Test createOrder(OrderRequestWrapper)")
    @Disabled("TODO: Complete this test")
    void testCreateOrder() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.restaurant.apis.Model.OrderRequestWrapper["order"]->com.restaurant.apis.Model.Orders["orderDate"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1308)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:479)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:318)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4719)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3964)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
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

        OrderRequestWrapper orderRequestWrapper = new OrderRequestWrapper();
        orderRequestWrapper.setOrder(order);
        orderRequestWrapper.setOrderItems(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(orderRequestWrapper);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders/createOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        MockMvcBuilders.standaloneSetup(orderController).build().perform(requestBuilder);
    }

    /**
     * Test {@link OrderController#createOrder(OrderRequestWrapper)}.
     * <ul>
     *   <li>Then return {@link Orders} (default constructor).</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderController#createOrder(OrderRequestWrapper)}
     */
    @Test
    @DisplayName("Test createOrder(OrderRequestWrapper); then return Orders (default constructor)")
    void testCreateOrder_thenReturnOrders() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
        OrderServiceImplementation orderService = mock(OrderServiceImplementation.class);
        when(orderService.createOrder(Mockito.<Orders>any(), Mockito.<List<OrderItems>>any())).thenReturn(orders);
        OrderController orderController = new OrderController(orderService);

        RestaurantTable TableId2 = new RestaurantTable();
        TableId2.setTableCapacity(1);
        TableId2.setTableId(1);
        TableId2.setTableStatus(true);

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
        order.setTableId(TableId2);
        order.setTax(10.0d);

        OrderRequestWrapper orderRequest = new OrderRequestWrapper();
        orderRequest.setOrder(order);
        orderRequest.setOrderItems(new ArrayList<>());

        // Act
        Orders actualCreateOrderResult = orderController.createOrder(orderRequest);

        // Assert
        verify(orderService).createOrder(isA(Orders.class), isA(List.class));
        assertSame(orders, actualCreateOrderResult);
    }

    /**
     * Test {@link OrderController#createOrder(OrderRequestWrapper)}.
     * <ul>
     *   <li>When {@link OrderRequestWrapper} {@link OrderRequestWrapper#getOrder()}
     * return {@link Orders} (default constructor).</li>
     *   <li>Then calls {@link OrderRequestWrapper#getOrder()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderController#createOrder(OrderRequestWrapper)}
     */
    @Test
    @DisplayName("Test createOrder(OrderRequestWrapper); when OrderRequestWrapper getOrder() return Orders (default constructor); then calls getOrder()")
    void testCreateOrder_whenOrderRequestWrapperGetOrderReturnOrders_thenCallsGetOrder() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
        OrderServiceImplementation orderService = mock(OrderServiceImplementation.class);
        when(orderService.createOrder(Mockito.<Orders>any(), Mockito.<List<OrderItems>>any())).thenReturn(orders);
        OrderController orderController = new OrderController(orderService);

        RestaurantTable TableId2 = new RestaurantTable();
        TableId2.setTableCapacity(1);
        TableId2.setTableId(1);
        TableId2.setTableStatus(true);

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
        order.setTableId(TableId2);
        order.setTax(10.0d);

        RestaurantTable TableId3 = new RestaurantTable();
        TableId3.setTableCapacity(1);
        TableId3.setTableId(1);
        TableId3.setTableStatus(true);

        Orders orders2 = new Orders();
        orders2.setCustomerName("Customer Name");
        orders2.setEmail("jane.doe@example.org");
        orders2.setOrderDate(LocalDate.of(1970, 1, 1));
        orders2.setOrderId(1);
        orders2.setOrderStatus("Order Status");
        orders2.setOrderSubTotal(10.0d);
        orders2.setOrderTime(LocalTime.MIDNIGHT);
        orders2.setOrderTotal(10.0d);
        orders2.setPhoneNo(1L);
        orders2.setTableId(TableId3);
        orders2.setTax(10.0d);
        OrderRequestWrapper orderRequest = mock(OrderRequestWrapper.class);
        when(orderRequest.getOrder()).thenReturn(orders2);
        when(orderRequest.getOrderItems()).thenReturn(new ArrayList<>());
        doNothing().when(orderRequest).setOrder(Mockito.<Orders>any());
        doNothing().when(orderRequest).setOrderItems(Mockito.<List<OrderItems>>any());
        orderRequest.setOrder(order);
        orderRequest.setOrderItems(new ArrayList<>());

        // Act
        Orders actualCreateOrderResult = orderController.createOrder(orderRequest);

        // Assert
        verify(orderRequest).getOrder();
        verify(orderRequest).getOrderItems();
        verify(orderRequest).setOrder(isA(Orders.class));
        verify(orderRequest).setOrderItems(isA(List.class));
        verify(orderService).createOrder(isA(Orders.class), isA(List.class));
        assertSame(orders, actualCreateOrderResult);
    }

    /**
     * Test {@link OrderController#updateOrder(OrderRequestWrapper)}.
     * <p>
     * Method under test: {@link OrderController#updateOrder(OrderRequestWrapper)}
     */
    @Test
    @DisplayName("Test updateOrder(OrderRequestWrapper)")
    @Disabled("TODO: Complete this test")
    void testUpdateOrder() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.restaurant.apis.Model.OrderRequestWrapper["order"]->com.restaurant.apis.Model.Orders["orderDate"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1308)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:479)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:318)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4719)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3964)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
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

        OrderRequestWrapper orderRequestWrapper = new OrderRequestWrapper();
        orderRequestWrapper.setOrder(order);
        orderRequestWrapper.setOrderItems(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(orderRequestWrapper);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders/updateOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        MockMvcBuilders.standaloneSetup(orderController).build().perform(requestBuilder);
    }

    /**
     * Test {@link OrderController#updateOrder(OrderRequestWrapper)}.
     * <ul>
     *   <li>Then return {@link Orders} (default constructor).</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderController#updateOrder(OrderRequestWrapper)}
     */
    @Test
    @DisplayName("Test updateOrder(OrderRequestWrapper); then return Orders (default constructor)")
    void testUpdateOrder_thenReturnOrders() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
        OrderServiceImplementation orderService = mock(OrderServiceImplementation.class);
        when(orderService.updateOrder(Mockito.<Orders>any(), Mockito.<List<OrderItems>>any())).thenReturn(orders);
        OrderController orderController = new OrderController(orderService);

        RestaurantTable TableId2 = new RestaurantTable();
        TableId2.setTableCapacity(1);
        TableId2.setTableId(1);
        TableId2.setTableStatus(true);

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
        order.setTableId(TableId2);
        order.setTax(10.0d);

        OrderRequestWrapper orderRequest = new OrderRequestWrapper();
        orderRequest.setOrder(order);
        orderRequest.setOrderItems(new ArrayList<>());

        // Act
        Orders actualUpdateOrderResult = orderController.updateOrder(orderRequest);

        // Assert
        verify(orderService).updateOrder(isA(Orders.class), isA(List.class));
        assertSame(orders, actualUpdateOrderResult);
    }

    /**
     * Test {@link OrderController#updateOrder(OrderRequestWrapper)}.
     * <ul>
     *   <li>When {@link OrderRequestWrapper} {@link OrderRequestWrapper#getOrder()}
     * return {@link Orders} (default constructor).</li>
     *   <li>Then calls {@link OrderRequestWrapper#getOrder()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderController#updateOrder(OrderRequestWrapper)}
     */
    @Test
    @DisplayName("Test updateOrder(OrderRequestWrapper); when OrderRequestWrapper getOrder() return Orders (default constructor); then calls getOrder()")
    void testUpdateOrder_whenOrderRequestWrapperGetOrderReturnOrders_thenCallsGetOrder() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
        OrderServiceImplementation orderService = mock(OrderServiceImplementation.class);
        when(orderService.updateOrder(Mockito.<Orders>any(), Mockito.<List<OrderItems>>any())).thenReturn(orders);
        OrderController orderController = new OrderController(orderService);

        RestaurantTable TableId2 = new RestaurantTable();
        TableId2.setTableCapacity(1);
        TableId2.setTableId(1);
        TableId2.setTableStatus(true);

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
        order.setTableId(TableId2);
        order.setTax(10.0d);

        RestaurantTable TableId3 = new RestaurantTable();
        TableId3.setTableCapacity(1);
        TableId3.setTableId(1);
        TableId3.setTableStatus(true);

        Orders orders2 = new Orders();
        orders2.setCustomerName("Customer Name");
        orders2.setEmail("jane.doe@example.org");
        orders2.setOrderDate(LocalDate.of(1970, 1, 1));
        orders2.setOrderId(1);
        orders2.setOrderStatus("Order Status");
        orders2.setOrderSubTotal(10.0d);
        orders2.setOrderTime(LocalTime.MIDNIGHT);
        orders2.setOrderTotal(10.0d);
        orders2.setPhoneNo(1L);
        orders2.setTableId(TableId3);
        orders2.setTax(10.0d);
        OrderRequestWrapper orderRequest = mock(OrderRequestWrapper.class);
        when(orderRequest.getOrder()).thenReturn(orders2);
        when(orderRequest.getOrderItems()).thenReturn(new ArrayList<>());
        doNothing().when(orderRequest).setOrder(Mockito.<Orders>any());
        doNothing().when(orderRequest).setOrderItems(Mockito.<List<OrderItems>>any());
        orderRequest.setOrder(order);
        orderRequest.setOrderItems(new ArrayList<>());

        // Act
        Orders actualUpdateOrderResult = orderController.updateOrder(orderRequest);

        // Assert
        verify(orderRequest).getOrder();
        verify(orderRequest).getOrderItems();
        verify(orderRequest).setOrder(isA(Orders.class));
        verify(orderRequest).setOrderItems(isA(List.class));
        verify(orderService).updateOrder(isA(Orders.class), isA(List.class));
        assertSame(orders, actualUpdateOrderResult);
    }

    /**
     * Test {@link OrderController#ordersAfterDate(LocalDate)}.
     * <ul>
     *   <li>Then content string a string.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderController#ordersAfterDate(LocalDate)}
     */
    @Test
    @DisplayName("Test ordersAfterDate(LocalDate); then content string a string")
    void testOrdersAfterDate_thenContentStringAString() throws Exception {
        // Arrange
        RestaurantTable tableId = new RestaurantTable();
        tableId.setTableCapacity(1);
        tableId.setTableId(1);
        tableId.setTableStatus(true);

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
        order.setTableId(tableId);
        order.setTax(10.0d);

        OrderRequestWrapper orderRequestWrapper = new OrderRequestWrapper();
        orderRequestWrapper.setOrder(order);
        orderRequestWrapper.setOrderItems(new ArrayList<>());

        ArrayList<OrderRequestWrapper> orderRequestWrapperList = new ArrayList<>();
        orderRequestWrapperList.add(orderRequestWrapper);
        when(orderService.getOrderByDuration(Mockito.<LocalDate>any())).thenReturn(orderRequestWrapperList);

        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/orders/getOrderAfterDate")
                .param("date", "1970-01-01");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$[0].order.phoneNo").value(1))
                .andExpect(jsonPath("$[0].order.email").value("jane.doe@example.org"))
                .andExpect(jsonPath("$[0].order.orderStatus").value("Order Status"))
                .andExpect(jsonPath("$[0].order.tax").value(10.0))
                .andExpect(jsonPath("$[0].order.customerName").value("Customer Name"))
                .andExpect(jsonPath("$[0].order.tableId.tableId").value(1))
                .andExpect(jsonPath("$[0].order.tableId.tableCapacity").value(1))
                .andExpect(jsonPath("$[0].order.tableId.tableStatus").value(true));
    }

    /**
     * Test {@link OrderController#ordersAfterDate(LocalDate)}.
     * <ul>
     *   <li>Then content string {@code []}.</li>
     * </ul>
     * <p>
     * Method under test: {@link OrderController#ordersAfterDate(LocalDate)}
     */
    @Test
    @DisplayName("Test ordersAfterDate(LocalDate); then content string '[]'")
    void testOrdersAfterDate_thenContentStringLeftSquareBracketRightSquareBracket() throws Exception {
        // Arrange
        when(orderService.getOrderByDuration(Mockito.<LocalDate>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/orders/getOrderAfterDate");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("date", String.valueOf(LocalDate.of(1970, 1, 1)));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link OrderController#getorderDetails(int)}.
     * <p>
     * Method under test: {@link OrderController#getorderDetails(int)}
     */
    @Test
    void testGetorderDetails() throws Exception {
        // Arrange
        RestaurantTable tableId = new RestaurantTable();
        tableId.setTableCapacity(1);
        tableId.setTableId(1);
        tableId.setTableStatus(true);

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
        order.setTableId(tableId);
        order.setTax(10.0d);

        OrderRequestWrapper orderRequestWrapper = new OrderRequestWrapper();
        orderRequestWrapper.setOrder(order);
        orderRequestWrapper.setOrderItems(new ArrayList<>());
        when(orderService.getOrderDetails(anyInt())).thenReturn(orderRequestWrapper);

        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/orders/getOrderDetails")
                .param("tableId", "1");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.order.phoneNo").value(1))
                .andExpect(jsonPath("$.order.email").value("jane.doe@example.org"))
                .andExpect(jsonPath("$.order.orderStatus").value("Order Status"))
                .andExpect(jsonPath("$.order.tax").value(10.0))
                .andExpect(jsonPath("$.order.orderDate").isArray())
                .andExpect(jsonPath("$.order.orderId").value(1))
                .andExpect(jsonPath("$.order.customerName").value("Customer Name"))
                .andExpect(jsonPath("$.order.tableId.tableId").value(1))
                .andExpect(jsonPath("$.order.tableId.tableCapacity").value(1))
                .andExpect(jsonPath("$.order.tableId.tableStatus").value(true));
    }

    /**
     * Test {@link OrderController#getItemsForKitchenScheduling()}.
     * <p>
     * Method under test: {@link OrderController#getItemsForKitchenScheduling()}
     */
    @Test
    @DisplayName("Test getItemsForKitchenScheduling()")
    void testGetItemsForKitchenScheduling() throws Exception {
        // Arrange
        when(orderService.getOrdersForKitchenScheduling()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/getItemsForKitchenScheduling");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link OrderController#getHighestSellingMenuItemByCategory()}.
     * <p>
     * Method under test:
     * {@link OrderController#getHighestSellingMenuItemByCategory()}
     */
    @Test
    @DisplayName("Test getHighestSellingMenuItemByCategory()")
    void testGetHighestSellingMenuItemByCategory() throws Exception {
        // Arrange
        when(orderService.getHighestSellingMenuItemByCategory()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/getHighestSellingMenuItems");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link OrderController#getYearlySales()}.
     * <p>
     * Method under test: {@link OrderController#getYearlySales()}
     */
    @Test
    @DisplayName("Test getYearlySales()")
    void testGetYearlySales() throws Exception {
        // Arrange
        when(orderService.getYearlySales()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/getYearlySales");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link OrderController#getMonthlySales()}.
     * <p>
     * Method under test: {@link OrderController#getMonthlySales()}
     */
    @Test
    @DisplayName("Test getMonthlySales()")
    void testGetMonthlySales() throws Exception {
        // Arrange
        when(orderService.getMonthlySales()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/getMonthlySales");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link OrderController#getDailySales()}.
     * <p>
     * Method under test: {@link OrderController#getDailySales()}
     */
    @Test
    @DisplayName("Test getDailySales()")
    void testGetDailySales() throws Exception {
        // Arrange
        when(orderService.getDailySales()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/getDailySales");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link OrderController#startPreparingOrder(int)}.
     * <p>
     * Method under test: {@link OrderController#startPreparingOrder(int)}
     */
    @Test
    @DisplayName("Test startPreparingOrder(int)")
    void testStartPreparingOrder() throws Exception {
        // Arrange
        RestaurantTable tableId = new RestaurantTable();
        tableId.setTableCapacity(1);
        tableId.setTableId(1);
        tableId.setTableStatus(true);

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
        orders.setTableId(tableId);
        orders.setTax(10.0d);

        when(orderService.changeOrderStatusToPreparing(anyInt())).thenReturn(orders);

        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/orders/startPreparingOrder")
                .param("orderId", "1");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(orderController)
                .build()
                .perform(postResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$.phoneNo").value(1))
                .andExpect(jsonPath("$.email").value("jane.doe@example.org"))
                .andExpect(jsonPath("$.orderStatus").value("Order Status"))
                .andExpect(jsonPath("$.tax").value(10.0))
                .andExpect(jsonPath("$.customerName").value("Customer Name"))
                .andExpect(jsonPath("$.tableId.tableId").value(1))
                .andExpect(jsonPath("$.tableId.tableCapacity").value(1))
                .andExpect(jsonPath("$.tableId.tableStatus").value(true));
    }

}
