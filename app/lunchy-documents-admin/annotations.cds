using AdminService as service from '../../srv/services';
annotate service.Headers with @(
    UI.FieldGroup #GeneratedGroup : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Label : '{i18n>DocumentNumber}',
                Value : documentNumber,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Incoterms1}',
                Value : incoterms1,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Incoterms2}',
                Value : incoterms2,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>Route}',
                Value : route,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>DeliveryDate}',
                Value : deliveryDate,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>GoodsIssueDate}',
                Value : goodsIssueDate,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>GoodsIssueTime}',
                Value : goodsIssueTime,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>SoldToParty}',
                Value : soldToParty_CustomerNumber,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>ShipToParty}',
                Value : shipToParty_CustomerNumber,
            },
            {
                $Type : 'UI.DataField',
                Label : '{i18n>DeliveryStatus}',
                Value : deliveryStatus,
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
            Label : '{i18n>DocumentNumber}',
            Value : documentNumber,
        },
        {
            $Type : 'UI.DataField',
            Label : '{i18n>Incoterms}',
            Value : incoterms,
        },
        {
            $Type : 'UI.DataField',
            Label : '{i18n>Route}',
            Value : route,
        },
    ],
);

annotate service.Headers with {
    soldToParty @(
        Common.ValueList : {
            $Type : 'Common.ValueListType',
            CollectionPath : 'Customers',
            Parameters : [
                {
                    $Type : 'Common.ValueListParameterInOut',
                    LocalDataProperty : soldToParty_CustomerNumber,
                    ValueListProperty : 'CustomerNumber',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'Name',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'Street',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'Country',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'PostalCode',
                },
            ],
        PresentationVariantQualifier : 'vh_Headers_soldToParty',
        },
        Common.ValueListWithFixedValues : false,
    )
};

annotate service.Headers with {
    shipToParty @(
        Common.ValueList : {
            $Type : 'Common.ValueListType',
            CollectionPath : 'Customers',
            Parameters : [
                {
                    $Type : 'Common.ValueListParameterInOut',
                    LocalDataProperty : shipToParty_CustomerNumber,
                    ValueListProperty : 'CustomerNumber',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'Name',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'Street',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'Country',
                },
                {
                    $Type : 'Common.ValueListParameterDisplayOnly',
                    ValueListProperty : 'PostalCode',
                },
            ],
        PresentationVariantQualifier : 'vh_Headers_shipToParty',
        },
        Common.ValueListWithFixedValues : false,
    )
};

annotate service.Customers with @(
    UI.PresentationVariant #vh_Headers_soldToParty : {
        $Type : 'UI.PresentationVariantType',
        SortOrder : [
            {
                $Type : 'Common.SortOrderType',
                Property : CustomerNumber,
                Descending : true,
            },
        ],
    },
    UI.PresentationVariant #vh_Headers_shipToParty : {
        $Type : 'UI.PresentationVariantType',
        SortOrder : [
            {
                $Type : 'Common.SortOrderType',
                Property : CustomerNumber,
                Descending : true,
            },
        ],
    },
);

