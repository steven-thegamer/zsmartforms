using { Language } from '@sap/cds/common';

namespace db.entity.materialtype;

entity MaterialType {
    key language : Language;
    key materialType : String(4);
    MaterialTypeDescription : String(25);
}