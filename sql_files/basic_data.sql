use Inventory;

INSERT INTO Customer(email, credit_card)
VALUES('cust1@gmail.com','1');
INSERT INTO Customer(email, credit_card)
VALUES('cust2@gmail.com','2');
INSERT INTO Customer(email, credit_card)
VALUES('cust3@gmail.com','3');

INSERT INTO Supplier(email, account_balance, lead_time)
VALUES('supplier1@gmail.com', 0, 6);
INSERT INTO Supplier(email, account_balance, lead_time)
VALUES('supplier2@gmail.com', 0, 7);
INSERT INTO Supplier(email, account_balance, lead_time)
VALUES('supplier3@gmail.com', 0, 8);

INSERT INTO Item(item_id, quantity, price, category)
Values(1, 100, 1, 'general');
INSERT INTO SupplierList(supplier_email, item_id, cost)
VALUES ('supplier1@gmail.com',1, 1);

INSERT INTO Item(item_id, quantity, price, category)
Values(2, 101, 5, 'general');
INSERT INTO SupplierList(supplier_email, item_id, cost)
VALUES ('supplier1@gmail.com', 2, 3);
INSERT INTO SupplierList(supplier_email, item_id, cost)
VALUES ('supplier2@gmail.com', 2, 4);

INSERT INTO Item(item_id, quantity, price, category)
Values(3, 102, 100, 'special');
INSERT INTO SupplierList(supplier_email, item_id, cost)
VALUES ('supplier2@gmail.com',3, 50);
INSERT INTO SupplierList(supplier_email, item_id, cost)
VALUES ('supplier3@gmail.com',3, 75);

INSERT INTO SalesOrder(order_id, shipping_option, tracking_num, customer_email)
VALUES (1, 'express', '123ship', 'cust1@gmail.com');
INSERT INTO ItemOrder(order_id, item_id, quantity)
VALUES(1, 1, 10);
INSERT INTO ItemOrder(order_id, item_id, quantity)
VALUES(1, 2, 10);

INSERT INTO SalesOrder(order_id, shipping_option, tracking_num, customer_email)
VALUES (2, 'express', '123ship', 'cust1@gmail.com');
INSERT INTO ItemOrder(order_id, item_id, quantity)
VALUES(2, 1, 10);
INSERT INTO ItemOrder(order_id, item_id, quantity)
VALUES(2, 2, 10);

INSERT INTO SalesOrder(order_id, shipping_option, tracking_num, customer_email)
VALUES (3, 'express', '123ship', 'cust1@gmail.com');
INSERT INTO ItemOrder(order_id, item_id, quantity)
VALUES(3, 2, 10);
INSERT INTO ItemOrder(order_id, item_id, quantity)
VALUES(3, 3, 10);

INSERT INTO ReplenishOrder(replenish_id, shipping_option, tracking_num, supplier_email)
VALUES (1, 'express', '123ship', 'supplier1@gmail.com');
INSERT INTO ItemReplenish(replenish_id, item_id, quantity)
VALUES(1, 1, 10);
INSERT INTO ItemReplenish(replenish_id, item_id, quantity)
VALUES(1, 2, 10);

INSERT INTO ReplenishOrder(replenish_id, shipping_option, tracking_num, supplier_email)
VALUES (2, 'express', '123ship', 'supplier2@gmail.com');
INSERT INTO ItemReplenish(replenish_id, item_id, quantity)
VALUES(2, 2, 10);
INSERT INTO ItemReplenish(replenish_id, item_id, quantity)
VALUES(2, 3, 10);

INSERT INTO ReplenishOrder(replenish_id, shipping_option, tracking_num, supplier_email)
VALUES (3, 'express', '123ship', 'supplier1@gmail.com');
INSERT INTO ItemReplenish(replenish_id, item_id, quantity)
VALUES(3, 1, 10);
INSERT INTO ItemReplenish(replenish_id, item_id, quantity)
VALUES(3, 2, 10);

