
use Inventory;
###BASIC QUERIES

#QUERY, INSERT, DELETE, UPDATE INTO CUSTOMER
INSERT INTO Customer(email, credit_card)
VALUES('customerDEMO@gmail.com','fakefake');
UPDATE Customer SET credit_card = 'updatedfake' WHERE email='customerDEMO@gmail.com';
DELETE FROM Customer WHERE email = 'customerDEMO@gmail.com';

#QUERY, INSERT, DELETE, UPDATE INTO SUPPLIER
INSERT INTO Supplier(email, account_balance, lead_time)
VALUES('supplierDEMO@gmail.com', 0, 6);
UPDATE Supplier SET account_balance = 1 WHERE email='supplierDEMO@gmail.com';
DELETE FROM Customer WHERE email='supplierDEMO@gmail.com';

#QUERY, INSERT, DELETE, UPDATE INTO ITEM
INSERT INTO Item(item_id, quantity, price, category)
Values(99, 999, 999, 'demo');
UPDATE Item SET price = 1000 WHERE item_id = 99;
DELETE FROM Item WHERE item_id = 99;

#QUERY, INSERT, DELETE, UPDATE INTO SALESORDER
INSERT INTO SalesOrder(order_id, shipping_option, tracking_num, customer_email)
#customer_email is a FK constraint here
VALUES (4, 'express', '123ship', 'cust2@gmail.com');
UPDATE SalesOrder SET shipping_option = 'regular' WHERE order_id = 4;
DELETE FROM SalesOrder WHERE order_id = 4;

#QUERY, INSERT, DELETE, UPDATE INTO REPLENISHORDER
INSERT INTO ReplenishOrder(replenish_id, shipping_option, tracking_num, supplier_email)
VALUES (4, 'standard', '321ship', 'supplierDEMO@gmail.com');
UPDATE ReplenishOrder SET tracking_num = 'ship321' WHERE replenish_id = 4;
DELETE FROM ReplenishOrder WHERE replenish_id = 4;

#QUERY, INSERT, DELETE, UPDATE INTO ITEMORDER
INSERT INTO ItemOrder(order_id, item_id, quantity)
VALUES(3, 1, 20);
#cancel item from order
UPDATE ItemOrder SET quantity = 0 WHERE order_id = 3 and ItemOrder.item_id = 1;
#cancel entire order without deleting
UPDATE ItemOrder SET quantity = 0 WHERE order_id = 3;
DELETE FROM ItemOrder WHERE order_id = 3;

#QUERY, INSERT, DELETE, UPDATE INTO ITEMREPLENISH
INSERT INTO ItemReplenish(replenish_id, item_id, quantity)
VALUES(1, 3, 20);
UPDATE ItemReplenish SET quantity = 30 WHERE replenish_id = 1 and ItemReplenish.item_id = 3;
DELETE FROM ItemReplenish WHERE replenish_id = 1;

#QUERY, INSERT, DELETE, UPDATE INTO SUPPLIERLIST
INSERT INTO SupplierList(supplier_email, item_id)
VALUES ('supplierDEMO@gmail.com',99);
UPDATE SupplierList SET item_id = 100 WHERE supplier_email = 'supplierDEMO@gmail.com';
DELETE FROM SupplierList WHERE supplier_email = 'supplierDEMO@gmail.com';


###SPECIAL QUERIES

#show all tables
show tables;

#altering a table
ALTER TABLE SalesOrder ADD shipping_fee numeric(5,2); 
ALTER TABLE ItemOrder RENAME column quantity to item_quantity;
#revert
ALTER TABLE ItemOrder RENAME column item_quantity to quantity;
ALTER TABLE SalesOrder DROP column shipping_fee;

#show items out of stock
INSERT INTO Item(item_id, quantity, price, category)
Values(99, 0, 999, 'demo');
SELECT item_id from Item where quantity = 0;

#show how many days it takes for a restocking to work through the supply chain
select item_id, sum(lead_time) from Supplier natural join SupplierList group by item_id;
