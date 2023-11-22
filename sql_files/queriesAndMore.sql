create database Inventory;
use Inventory;

###tables that hold main entity data

create table Customer (
                          email varchar(32),
                          credit_card varchar(16),
                          primary key(email)
);

create table Supplier (
                          email varchar(32),
                          account_balance numeric(8,2),
                          lead_time int,
                          primary key(email)
);

create table Item (
                      item_id int,
                      quantity int,
                      price numeric(7,2),
                      category varchar(32),
                      primary key(item_id)
);

###tables that relate Item to the other main entities, essentially holding order data
#both incoming/outgoing orders are split into two tables, since items are multivalued attributes:
#	for customers and item, we have SalesOrder and ItemOrder
#	for suppliers and item, we have ReplenishOrder and ItemReplenish
#suppliers and items are many to many, so this data is stored in SupplierList


#table holds data on customer sales
create table SalesOrder (
                            order_id int,
                            shipping_option varchar(16),
                            tracking_num varchar(32),
                            customer_email varchar(32),
                            primary key(order_id),
                            foreign key(customer_email) references Customer(email)
);

#table holds data on supplier orders
create table ReplenishOrder (
                                replenish_id int,
                                shipping_option varchar(16),
                                tracking_num varchar(32),
                                supplier_email varchar(32),
                                primary key(replenish_id),
                                foreign key(supplier_email) references Supplier(email)
);


#table relates customer order to items ordered and their quantities
#one order can have many items, and one item can have many orders
create table ItemOrder (
                           order_id int,
                           item_id int,
                           quantity int,
                           primary key(order_id, item_id),
                           foreign key(item_id) references Item(item_id),
                           foreign key(order_id) references SalesOrder(order_id)
);

#table relates supplier order to items ordered and their quantities; similar many to many relation as above
create table ItemReplenish (
                               replenish_id int,
                               item_id int,
                               quantity int,
                               primary key(replenish_id, item_id),
                               foreign key(item_id) references Item(item_id),
                               foreign key(replenish_id) references ReplenishOrder(replenish_id)
);

#table relates suppliers to their respective items
#Many to many relationship between supplier and item.(i.e 1 item is supplied by 3 suppliers, 3 items are supplied by one supplier). Primary key is each supplier/item tuple.
#cost is in this table bc different suppliers may have different cost.
Create table SupplierList (
        cost INT,
        supplier_email varchar(32) references Supplier(email),
    item_id INT references Item(item_id),
    primary key(supplier_email, item_id)
);


###tables hold contact info for suppliers/customers and check for one unique email address
create table MailingList(
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

create table PhoneList(
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
