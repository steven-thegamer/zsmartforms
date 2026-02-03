using db.types from '../types';
using db.entity.item from '../entities/item-entity';
using db.entity.customer from '../entities/customer-entity';
using db.entity.document from '../entities/document-entity';

namespace db.entity.header;

entity Header {
    key documentNumber : String(10);
    incoterms1 : String(3);
    incoterms2 : String(28);
    incoterms : String = incoterms1 + ' ' + incoterms2;
    route : String(6);
    deliveryDate : Date;
    goodsIssueDate : Date;
    goodsIssueTime : Time;
    soldToParty : Association to customer.Customer;
    shipToParty : Association to customer.Customer;
    status : types.DocumentStatus;
    document : Association to document.Document;
    items : Composition of many item.Item on items.header = $self;

    @readonly deliveryStatus : types.DeliveryStatus 
    @assert : (case
        when goodsIssueDate <= deliveryDate then 'On Time'
        when goodsIssueDate > deliveryDate then 'Delayed'
        else 'Pending'
    end);

    @readonly deliveryStatusCriticality : Integer
    @assert : (case
        when deliveryStatus = 'On Time' then 1
        when deliveryStatus = 'Delayed' then 3
        else 2
    end);

    @readonly statusCriticality : Integer
    @assert : (case
        when status = 'Printed' then 2
        when status = 'Emailed' then 3
        else 0
    end);

}