using db.entity.header from './header-entity';
using db.entity.materialtype from './materialtype-entity';

namespace db.entity.item;

entity Item {
    key header : Association to header.Header;
    key itemNumber : String(6);
    material : String(18);
    shortText : String(40);
    materialType : Association to one materialtype.MaterialType;
    quantityDeliveredSales : Decimal(13,3);
    quantityDeliveredStockKeeping : Decimal(13,3);
    uom : String(3);
}