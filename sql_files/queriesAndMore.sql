Use Inventory;

#QUERY, INSERT, DELETE, UPDATE INTO CUSTOMER
INSERT INTO Customer(email, credit_card)
VALUES('customerDEMO@gmail.com','fakefake');

#QUERY, INSERT, DELETE, UPDATE INTO SUPPLIER
INSERT INTO Supplier(email, account_balance, lead_time)
VALUES('supplierDEMO@gmail.com', 0, 6);

#QUERY, INSERT, DELETE, UPDATE INTO ITEM
INSERT INTO Item(item_id, quantity, price, category)
Values(99, 999, 999, 'demo');

#QUERY, INSERT, DELETE, UPDATE INTO SUPPLIERLIST
INSERT INTO SupplierList(supplier_email, item_id)
VALUES ('supplierDEMO@gmail.com',99);

#QUERY, INSERT, DELETE, UPDATE INTO SALESORDER
INSERT INTO SalesOrder(order_id, total, shipping_option, tracking_num, customer_email)
VALUES (1, 123, 'express', '123ship', 'customerDEMO@gmail.com');

#QUERY, INSERT, DELETE, UPDATE INTO REPLENISHORDER
INSERT INTO ReplenishOrder(replenish_id, total, shipping_option, tracking_num, customer_email)
VALUES (1, 123, 'standard', '321ship', 'supplierDEMO@gmail.com');

#QUERY, INSERT, DELETE, UPDATE INTO ITEMORDER
INSERT INTO ItemOrder(order_id, item_id, quantity)
VALUES(1, 1, 10);


#SPECIAL QUARIES

create view order_item_quantity_price(order_id, item_id, quantity, price, total_price) as
	SELECT ItemOrder.*, Item.price, (ItemOrder.quantity * Item.Price)
    FROM ItemOrder LEFT OUTER JOIN Item ON ItemOrder.item_id = Item.item_id;
    
#View the Salesorders and the total $Price
create view SalesOrder_total_price as
	SELECT order_id, sum(total_price)
    FROM order_item_quantity_price
    GROUP BY order_id;
    
#View the ReplenishOrders and the total $Cost
#Since cost can differ from supplier it is necissary to create a view that has supplier_email and replenish_id in it.
create view ItemReplenish_supplier_email as 
	SELECT ItemReplenish.*, ReplenishOrder.supplier_email
    FROM ItemReplenish LEFT OUTER JOIN ReplenishOrder ON ItemReplenish.replenish_id = ReplenishOrder.replenish_id;
    
create view ReplenishOrder_total as
	SELECT ItemReplenish_supplier_email.replenish_id, sum(SupplierList.cost * ItemReplenish_supplier_email.quantity)
    FROM ItemReplenish_supplier_email LEFT OUTER JOIN SupplierList ON (ItemReplenish_supplier_email.supplier_email = SupplierList.supplier_email) AND (ItemReplenish_supplier_email.item_id = SupplierList.item_id)
    GROUP BY ItemReplenish_supplier_email.replenish_id;

#View suppliers and the number items they supply, grouped by supplier
create view supplier_number_of_items(supplier_email, items_supplied) as
	SELECT supplier_email, count(item_id) FROM supplierList
	group by supplier_email;
    
#View items and the number of suppliers they have, grouped by item
create view item_number_of_suppliers(item_id, suppliers) as
	SELECT item_id, count(supplier_email) FROM supplierList
	group by item_id;

