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

service CustomersService {
    @odata.draft.enabled
    @Common.DraftRoot.NewAction: 'CustomersService.createDraft'
    entity Customers as projection on index.Customers actions {
        action createDraft(in: many $self) returns Customers;
    };
}

service MaterialTypesService {
    @odata.draft.enabled
    @Common.DraftRoot.NewAction: 'MaterialTypesService.createDraft'
    entity MaterialTypes as projection on index.MaterialTypes actions {
        action createDraft(in: many $self) returns MaterialTypes;
    };
}

service AdminService {
    @odata.draft.enabled
    @Common.DraftRoot.NewAction: 'AdminService.createDraft'
    entity Headers as projection on index.Headers actions {
        action createDraft(in: many $self) returns Headers;
    };
    entity Items as projection on index.Items;
    entity Documents as projection on index.Documents;
    entity Customers as projection on index.Customers;
    entity MaterialTypes as projection on index.MaterialTypes;
}