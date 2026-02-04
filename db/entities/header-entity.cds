using db.types from '../types';
using db.entity.item from '../entities/item-entity';
using db.entity.customer from '../entities/customer-entity';
using db.entity.document from '../entities/document-entity';

namespace db.entity.header;

entity Header {
    key documentNumber : String(10);
    incoterms1 : String(3);
    incoterms2 : String(28);
    incoterms : String = incoterms1 || ' ' || incoterms2;
    route : String(6);
    deliveryDate : Date;
    goodsIssueDate : Date;
    goodsIssueTime : Time;
    soldToParty : Association to customer.Customer;
    shipToParty : Association to customer.Customer;
    status : types.DocumentStatus default #Created;
    document : Association to document.Document;
    items : Composition of many item.Item on items.header = $self;

    deliveryStatus : types.DeliveryStatus =
    (case
        when goodsIssueDate <= deliveryDate then #OnTime
        when goodsIssueDate > deliveryDate then #Delayed
        else #Pending
    end) stored;

    deliveryStatusCriticality : Integer =
    (case
        when goodsIssueDate <= deliveryDate then 3
        when goodsIssueDate > deliveryDate then 1
        else 2
    end) stored;

    statusCriticality : Integer =
    (case
        when status = #Printed then 2
        when status = #Synced then 3
        else 0
    end) stored;
}