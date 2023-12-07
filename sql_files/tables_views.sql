create database if not exists Inventory;
use Inventory;

create table if not exists Customer (
    email varchar(32),
    credit_card varchar(16),
    primary key(email)
    );


create table if not exists Supplier (
    email varchar(32),
    account_balance numeric(8,2),
    lead_time int,
    primary key(email)
    );

create table if not exists Item (
                                    item_id int,
                                    quantity int,
                                    price numeric(7,2),
    category varchar(32),
    primary key(item_id)
    );

create table if not exists SalesOrder (
                                          order_id int,
                                          shipping_option varchar(16),
    tracking_num varchar(32),
    customer_email varchar(32),
    primary key(order_id),
    foreign key(customer_email) references Customer(email)
    );

create table if not exists ReplenishOrder (
                                              replenish_id int,
                                              shipping_option varchar(16),
    tracking_num varchar(32),
    supplier_email varchar(32),
    primary key(replenish_id),
    foreign key(supplier_email) references Supplier(email)
    );

create table if not exists ItemOrder (
                                         order_id int,
                                         item_id int,
                                         quantity int,
                                         primary key(order_id, item_id),
    foreign key(item_id) references Item(item_id),
    foreign key(order_id) references SalesOrder(order_id)
    );

create table if not exists ItemReplenish (
                                             replenish_id int,
                                             item_id int,
                                             quantity int,
                                             primary key(replenish_id, item_id),
    foreign key(item_id) references Item(item_id),
    foreign key(replenish_id) references ReplenishOrder(replenish_id)
    );

create table if not exists ItemReplenish (
                                             replenish_id int,
                                             item_id int,
                                             quantity int,
                                             primary key(replenish_id, item_id),
    foreign key(item_id) references Item(item_id),
    foreign key(replenish_id) references ReplenishOrder(replenish_id)
    );

create table if not exists SupplierList (
                                            cost INT,
                                            supplier_email varchar(32) references Supplier(email),
    item_id INT references Item(item_id),
    primary key(supplier_email, item_id)
    );

create table if not exists MailingList(
    customer_email varchar(32),
    supplier_email varchar(32),
    address_1 varchar(128),
    address_2 varchar(128),
    address_3 varchar(128),
    city varchar(128),
    state char(2),
    country char(2),
    postal_code varchar(16),
    unique(customer_email,supplier_email),
    foreign key(customer_email) references Customer(email),
    foreign key(supplier_email) references Supplier(email),
    check(customer_email is null and supplier_email is not null or customer_email is not null and supplier_email is null)
    );

create table if not exists PhoneList(
    customer_email varchar(32),
    supplier_email varchar(32),
    work_phone varchar(16),
    home_phone varchar(16),
    cell_phone varchar(16),
    unique(customer_email,supplier_email),
    foreign key(customer_email) references Customer(email),
    foreign key(supplier_email) references Supplier(email),
    check(customer_email is null and supplier_email is not null or customer_email is not null and supplier_email is null)
    );


#VIEWS
use Inventory;


#make a view of total price for each product-quantity tuple
create or replace view order_item_quantity_price(order_id, item_id, quantity, price, total_price) as
SELECT ItemOrder.*, Item.price, (ItemOrder.quantity * Item.Price)
FROM ItemOrder LEFT OUTER JOIN Item ON ItemOrder.item_id = Item.item_id;

#view the total price for the entire customer order
create or replace view SalesOrder_total_price(order_id, total_price) as
SELECT order_id, sum(total_price)
FROM order_item_quantity_price
GROUP BY order_id;


create or replace view SalesOrder_All_Info(order_id, total_price, shipping_option, tracking_num, customer_email) as
SELECT SalesOrder.order_id, SalesOrder_total_price.total_price, SalesOrder.shipping_option, SalesOrder.tracking_num, SalesOrder.customer_email
FROM SalesOrder LEFT OUTER JOIN SalesOrder_total_price on SalesOrder.order_id = SalesOrder_total_price.order_id;

#Since cost can differ from supplier it is necissary to create a view that has supplier_email and replenish_id in it.
create or replace view ItemReplenish_supplier_email as
SELECT ItemReplenish.*, ReplenishOrder.supplier_email
FROM ItemReplenish LEFT OUTER JOIN ReplenishOrder ON ItemReplenish.replenish_id = ReplenishOrder.replenish_id;

#View the ReplenishOrders and the total $Cost
create or replace view ReplenishOrder_total as
SELECT ItemReplenish_supplier_email.replenish_id, sum(SupplierList.cost * ItemReplenish_supplier_email.quantity) as order_total
FROM ItemReplenish_supplier_email LEFT OUTER JOIN SupplierList ON (ItemReplenish_supplier_email.supplier_email = SupplierList.supplier_email) AND (ItemReplenish_supplier_email.item_id = SupplierList.item_id)
GROUP BY ItemReplenish_supplier_email.replenish_id;


create or replace view ReplenishOrder_All_Info(replenish_id, total_price, shipping_option, tracking_num, supplier_email) as
SELECT ReplenishOrder.replenish_id, ReplenishOrder_total.order_total, ReplenishOrder.shipping_option, ReplenishOrder.tracking_num, ReplenishOrder.supplier_email
FROM ReplenishOrder LEFT OUTER JOIN ReplenishOrder_total on ReplenishOrder.replenish_id = ReplenishOrder_total.replenish_id;

#View the ReplenishOrders and the itemized cost
create or replace view ReplenishOrder_itemized as
SELECT replenish_id, ItemReplenish_supplier_email.item_id, quantity, cost, (cost * quantity) as total_cost
FROM ItemReplenish_supplier_email LEFT OUTER JOIN SupplierList ON (ItemReplenish_supplier_email.supplier_email = SupplierList.supplier_email) AND (ItemReplenish_supplier_email.item_id = SupplierList.item_id);

#View suppliers and the number of items they supply, grouped by supplier
create or replace view supplier_number_of_items(supplier_email, items_supplied) as
SELECT supplier_email, count(item_id) FROM SupplierList
group by supplier_email;

#View items and the number of suppliers they have, grouped by item
create or replace view item_number_of_suppliers(item_id, suppliers) as
SELECT item_id, count(supplier_email) FROM SupplierList
group by item_id;

create or replace view customer_details(customer_email, address_1, address_2, address_3, city, state, country, postal_code) as
SELECT Customer.email, address_1, address_2, address_3, city, state, country, postal_code
FROM Customer LEFT OUTER JOIN MailingList ML on Customer.email = ML.customer_email;



