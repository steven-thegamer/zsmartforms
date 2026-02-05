using db.index from '../db/index';

service MainService {
    entity lunchyDocumentHeaders as projection on index.Headers actions {
        action generateDocument();
    };
    entity lunchyDocumentItems as projection on index.Items;
    entity lunchyDocumentDocuments as projection on index.Documents;
    entity lunchyDocumentCustomers as projection on index.Customers;
    entity lunchyDocumentMaterialTypes as projection on index.MaterialTypes;
}