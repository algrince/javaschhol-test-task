## Online store "The Store" information system
### Final task for T-System Javaschool

This is the multi-user client-server application with network connection.
The server logic is implemented by Java Spring Boot application while UI 
is provided by Angular application. For data storage MySQL database is used.

The project emulates the store page for horse care items shop. 
The functionality is provided for 3 roles of application users:
admin, employee, buyer. Additionally, it can be used by unauthorized users.

Unauthorized users can browse the homepage and the list of products, 
add products to the cart. Cart is saved if the browser tab is closed
or if user loges in.

In addition to that, buyers can place orders, add/update/delete info about them and
addresses.

Employees have expanded rights to view, add, update and delete products, categories,
properties, users, addresses and also view some statistics.

Admins have all the rights of employees but besides that they can change user
roles (promote them) and restore deleted user/products.

