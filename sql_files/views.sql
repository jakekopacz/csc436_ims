use Inventory;
#VIEWS

##9 views

#make a view of total price for each product-quantity tuple
create view order_item_quantity_price(order_id, item_id, quantity, price, total_price) as
	SELECT ItemOrder.*, Item.price, (ItemOrder.quantity * Item.Price)
    FROM ItemOrder LEFT OUTER JOIN Item ON ItemOrder.item_id = Item.item_id;
    
#view the total price for the entire customer order
create view SalesOrder_total_price(order_id, total_price) as
	SELECT order_id, sum(total_price)
    FROM order_item_quantity_price
    GROUP BY order_id;

create view SalesOrder_All_Info(order_id, total_price, shipping_option, tracking_num, customer_email) as
    SELECT SalesOrder.order_id, SalesOrder_total_price.total_price, SalesOrder.shipping_option, SalesOrder.tracking_num, SalesOrder.customer_email
    FROM SalesOrder LEFT OUTER JOIN SalesOrder_total_price on SalesOrder.order_id = SalesOrder_total_price.order_id;

#Since cost can differ from supplier it is necessary to create a view that has supplier_email and replenish_id in it.
create view ItemReplenish_supplier_email as 
	SELECT ItemReplenish.*, ReplenishOrder.supplier_email
    FROM ItemReplenish LEFT OUTER JOIN ReplenishOrder ON ItemReplenish.replenish_id = ReplenishOrder.replenish_id;

#View the ReplenishOrders and the total $Cost
create view ReplenishOrder_total as
	SELECT ItemReplenish_supplier_email.replenish_id, sum(SupplierList.cost * ItemReplenish_supplier_email.quantity) as order_total
    FROM ItemReplenish_supplier_email LEFT OUTER JOIN SupplierList ON (ItemReplenish_supplier_email.supplier_email = SupplierList.supplier_email) AND (ItemReplenish_supplier_email.item_id = SupplierList.item_id)
    GROUP BY ItemReplenish_supplier_email.replenish_id;
    
#View the ReplenishOrders and the itemized cost
create view ReplenishOrder_itemized as 
	SELECT replenish_id, ItemReplenish_supplier_email.item_id, quantity, cost, (cost * quantity) as total_cost
    FROM ItemReplenish_supplier_email LEFT OUTER JOIN SupplierList ON (ItemReplenish_supplier_email.supplier_email = SupplierList.supplier_email) AND (ItemReplenish_supplier_email.item_id = SupplierList.item_id);

#View suppliers and the number of items they supply, grouped by supplier
create view supplier_number_of_items(supplier_email, items_supplied) as
	SELECT supplier_email, count(item_id) FROM SupplierList
	group by supplier_email;
    
#View items and the number of suppliers they have, grouped by item
create view item_number_of_suppliers(item_id, suppliers) as
	SELECT item_id, count(supplier_email) FROM SupplierList
	group by item_id;

create view customer_details(customer_email, address_1, address_2, address_3, city, state, country, postal_code) as
    SELECT customer_email, address_1, address_2, address_3, city, state, country, postal_code
    FROM Customer LEFT OUTER JOIN MailingList ML on Customer.email = ML.customer_email;

