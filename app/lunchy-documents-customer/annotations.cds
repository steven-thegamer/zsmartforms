using CustomerService as service from '../../srv/services';
annotate service.lunchyDocumentCustomers with @(
    UI.FieldGroup #GeneratedGroup : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Customernumber}',
                Value : CustomerNumber,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Name}',
                Value : Name,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Street}',
                Value : Street,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Country}',
                Value : Country,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>PostalCode}',
                Value : PostalCode,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>PhoneNumber}',
                Value : PhoneNumber,
            },
        ],
    },
    UI.Facets : [
        {
            $Type : 'UI.ReferenceFacet',
            ID : 'GeneratedFacet1',
            Label : 'General Information',
            Target : '@UI.FieldGroup#GeneratedGroup',
        },
    ],
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Label : '{i18n>Customernumber}',
            Value : CustomerNumber,
        },
        {
            $Type : 'UI.DataField',
            Label : '{i18n>Name}',
            Value : Name,
        },
        {
            $Type : 'UI.DataField',
            Label : '{i18n>Street}',
            Value : Street,
        },
        {
            $Type : 'UI.DataField',
            Label : '{i18n>Country}',
            Value : Country,
        },
        {
            $Type : 'UI.DataField',
            Label : '{i18n>PostalCode}',
            Value : PostalCode,
        },
    ],
    UI.HeaderInfo : {
        TypeName : '{i18n>Customer}',
        TypeNamePlural : '{i18n>Customers}',
        TypeImageUrl : 'sap-icon://account',
    },
);

annotate service.lunchyDocumentCustomers with {
    CustomerNumber @Common.FieldControl : #ReadOnly
};

