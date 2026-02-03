namespace db.entity.customer;

entity Customer {
    key CustomerNumber : String(10);
    Name : String(40);
    Street : String(40);
    Country : String(3);
    PostalCode : String(10);
    PhoneNumber : String(16);
}