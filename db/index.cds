using { db.entity.header.Header } from './entities/header-entity';
using { db.entity.item.Item } from './entities/item-entity';
using { db.entity.document.Document } from './entities/document-entity';
using { db.entity.customer.Customer } from './entities/customer-entity';
using { db.entity.materialtype.MaterialType } from './entities/materialtype-entity';

namespace db.index;

entity Headers as projection on Header;
entity Items as projection on Item;
entity Documents as projection on Document;
entity Customers as projection on Customer;
entity MaterialTypes as projection on MaterialType;