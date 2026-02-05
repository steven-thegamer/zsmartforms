using db.index from '../db/index';

service MainService {
    entity lunchyDocumentHeaders as projection on index.Headers;
    entity lunchyDocumentItems as projection on index.Items;
    entity lunchyDocumentDocuments as projection on index.Documents;
    entity lunchyDocumentCustomers as projection on index.Customers;
    entity lunchyDocumentMaterialTypes as projection on index.MaterialTypes;
}

service AdminService {
    @odata.draft.enabled
    @Common.DraftRoot.NewAction: 'AdminService.createDraft'
    entity lunchyDocumentHeaders as projection on index.Headers actions {
        action createDraft(in: many $self) returns lunchyDocumentHeaders;
    };
    entity lunchyDocumentItems as projection on index.Items;
    entity lunchyDocumentDocuments as projection on index.Documents;
    entity lunchyDocumentCustomers as projection on index.Customers;
    entity lunchyDocumentMaterialTypes as projection on index.MaterialTypes;
}

service CustomerService {
    @odata.draft.enabled
    @Common.DraftRoot.NewAction: 'CustomerService.createDraft'
    entity lunchyDocumentCustomers as projection on index.Customers actions {
        action createDraft(in: many $self) returns lunchyDocumentCustomers;
    };
}

service MaterialTypeService {
    @odata.draft.enabled
    @Common.DraftRoot.NewAction: 'MaterialTypeService.createDraft'
    entity lunchyDocumentMaterialTypes as projection on index.MaterialTypes actions {
        action createDraft(in: many $self) returns lunchyDocumentMaterialTypes;
    };
}